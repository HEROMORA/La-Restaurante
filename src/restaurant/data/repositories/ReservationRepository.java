package restaurant.data.repositories;

import restaurant.appUtils.AppUtilities;
import restaurant.data.services.ReservationService;
import restaurant.data.services.Service;
import restaurant.models.reservation.Reservation;
import restaurant.models.reservation.Table;

import java.util.*;

public class ReservationRepository {

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

    private void loadReservations()
    {
        reservations = reservationService.readData();
    }

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

    public Reservation getReservationByCustomerUsername(String username)
    {
        for (Reservation reservation:reservations)
        {
            if (reservation.getCustomerUserName().equals(username))
                return reservation;
        }

        return null;
    }

    public Reservation makeReservation(String username, int numberOfSeats,
                                       Date reservationDate, Date endReservationDate, boolean isSmoking)
    {
        ArrayList<Reservation> _reservations = getUpcomingReservations();

        var eligibleTables = tableRepository.getTablesByEligibleNumberOfSeatsAndSmoking(numberOfSeats, isSmoking);
        Collections.sort(eligibleTables);

        HashMap<Integer, Reservation> reservedTablesMap = new HashMap<>();

        for(Reservation res: _reservations)
            reservedTablesMap.put(res.getTableNum(), res);

        int tableNumber = -1;

        for (Table table:eligibleTables)
        {

            if (reservedTablesMap.containsKey(table.getTableNumber()))
            {
                var res = reservedTablesMap.get(table.getTableNumber());
                if(!appUtilities.isTimeBetween(res.getReservationDate(), res.getEndReservationDate(), reservationDate)) {
                    tableNumber = table.getTableNumber();
                    break;
                }
            }

            if(!reservedTablesMap.containsKey(table.getTableNumber())) {
                tableNumber = table.getTableNumber();
                break;
            }
        }

        if (tableNumber == -1) return null;

        return new Reservation(tableNumber, username, reservationDate, endReservationDate);
    }


    public void saveReservation(Reservation reservation)
    {
        reservationService.writeData(reservation);
        loadReservations();
    }
}
