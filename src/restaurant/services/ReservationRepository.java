package restaurant.services;

import restaurant.appUtils.AppUtilities;
import restaurant.reservation.Reservation;
import restaurant.reservation.Table;

import java.util.*;

public class ReservationRepository {

    private RestaurantService restaurantService = new RestaurantService();
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
        reservations = restaurantService.readReservations();
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
            if (!AppUtilities.isBeforeDay(reservation.getReservationDate(), Calendar.getInstance().getTime()))
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

        HashSet<Integer> reservedTablesSet = new HashSet<>();

        for(Reservation res: _reservations)
            reservedTablesSet.add(res.getTableNum());

        int tableNumber = -1;

        for (Table table:eligibleTables)
        {
            if(!reservedTablesSet.contains(table.getTableNumber())) {
                tableNumber = table.getTableNumber();
                break;
            }
        }

        if (tableNumber == -1) return null;

        return new Reservation(tableNumber, username, reservationDate, endReservationDate);
    }


    public void saveReservation(Reservation reservation)
    {
        restaurantService.writeReservation(reservation);
        loadReservations();
    }
}
