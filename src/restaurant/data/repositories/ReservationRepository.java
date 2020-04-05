package restaurant.data.repositories;

import restaurant.appUtils.AppUtilities;
import restaurant.data.services.ReservationService;
import restaurant.data.services.Service;
import restaurant.models.reservation.Reservation;
import restaurant.models.reservation.Table;

import java.util.*;

public class ReservationRepository {

    // This Class provides the querying and data manipulating for the reservation objects

    private Service<Reservation> reservationService = new ReservationService();
    private TableRepository tableRepository = new TableRepository();
    private ArrayList<Reservation> reservations;
    private AppUtilities appUtilities = new AppUtilities();

    public ReservationRepository()
    {
        loadReservations();
    }

    public ArrayList<Reservation> getReservations()
    {
        return reservations;
    }

    // This function refreshes the orders list from the file  and to listen for new added elements
    private void loadReservations()
    {
        reservations = reservationService.readData();
    }

    // Gets the reservations for today
    public ArrayList<Reservation> getTodayReservations()
    {
        ArrayList<Reservation> _reservations = new ArrayList<>();

        for (Reservation reservation:getReservations())
        {
            if (appUtilities.isSameDay(reservation.getReservationDate() ,Calendar.getInstance().getTime()))
                _reservations.add(reservation);
        }

        return  _reservations;
    }

    // Gets the reservation the it's date hasn't came yet
    public ArrayList<Reservation> getUpcomingReservations()
    {
        ArrayList<Reservation> _reservations = new ArrayList<>();
        for (Reservation reservation:getReservations())
        {
            if (!appUtilities.isBeforeDay(reservation.getReservationDate(), Calendar.getInstance().getTime()))
                _reservations.add(reservation);
        }

        return _reservations;
    }

    public ArrayList<Reservation> getReservationsByDate(Date date){
        ArrayList<Reservation> _reservations = new ArrayList<>();
        for(Reservation reservation: getReservations()){
            if(appUtilities.isSameDay(reservation.getReservationDate(),date))
                _reservations.add(reservation);
        }
        return _reservations;
    }

    public Reservation getReservationByCustomerUsername(String username)
    {
        Reservation res = null;
        for (Reservation reservation:reservations)
        {
            if (reservation.getCustomerUserName().equals(username))
                res = reservation;
        }

        return res;
    }

    // WRITING FUNCTIONS


    // Note: This function is complicated due to our trial to create an O(n) solution
    // In Database application we would join tables
    // This function handles the process of creation an Reservation object
    public Reservation makeReservation(String username, int numberOfSeats,
                                       Date reservationDate, Date endReservationDate, boolean isSmoking)
    {
        ArrayList<Reservation> _reservations = getReservationsByDate(reservationDate);

        var eligibleTables = tableRepository.getTablesByEligibleNumberOfSeatsAndSmoking(numberOfSeats, isSmoking);
        Collections.sort(eligibleTables);

        HashMap<Integer, ArrayList<Reservation>> reservedTablesMap = new HashMap<>();

        // Handles if the table has more the one reservation at different times
        for(Reservation res: _reservations) {
            ArrayList<Reservation> _reservs = new ArrayList<>();

            // Adds the previous reservation to the same table if they key exists
            if (reservedTablesMap.containsKey(res.getTableNum())) {
                var reservsForSameTable = reservedTablesMap.get(res.getTableNum());
                _reservs.addAll(reservsForSameTable);
            }

            _reservs.add(res);
            reservedTablesMap.put(res.getTableNum(), _reservs);
        }

        int tableNumber = -1;

        // Looping through the eligible tables for user's requirements
        for (Table table:eligibleTables)
        {
            if (reservedTablesMap.containsKey(table.getTableNumber()))
            {
                var reservations = reservedTablesMap.get(table.getTableNumber());
                // for each reservation in this key check if there is a collision between the times
                for (Reservation res: reservations) {

                    if (!appUtilities.isTimeBetween(res.getReservationDate(), res.getEndReservationDate(),
                            reservationDate, endReservationDate)) {

                        tableNumber = table.getTableNumber();
                        break;
                    }
                }
            }

            // Reserve the table if it does not have any reservation constraints
            if (!reservedTablesMap.containsKey(table.getTableNumber())) {
                tableNumber = table.getTableNumber();
                break;
            }
        }

        if (tableNumber == -1) return null;

        return new Reservation(tableNumber, username, reservationDate, endReservationDate);
    }

    // Saves the reservation to the file and refreshes the list
    public void saveReservation(Reservation reservation)
    {
        reservationService.writeData(reservation);
        loadReservations();
    }
}
