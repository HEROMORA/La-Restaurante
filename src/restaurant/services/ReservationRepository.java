package restaurant.services;

import restaurant.Utils.AppUtilities;
import restaurant.reservation.Reservation;

import java.util.ArrayList;
import java.util.Calendar;

public class ReservationRepository {

    private RestaurantService restaurantService = new RestaurantService();
    private UserRepository userRepository = new UserRepository();
    private ArrayList<Reservation> reservations;
    private AppUtilities appUtilities = new AppUtilities();

    public ReservationRepository()
    {
        reservations = restaurantService.readReservations();
    }

    public ArrayList<Reservation> getReservations()
    {
        return reservations;
    }

    public ArrayList<Reservation> getTodayReservations()
    {
        ArrayList<Reservation> _reservations = new ArrayList<>();

        for (Reservation reservation:reservations)
        {
            if (appUtilities.isSameDay(reservation.getReservationDate() ,Calendar.getInstance().getTime()))
                _reservations.add(reservation);
        }

        return  _reservations;
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

    public void saveReservation(Reservation reservation)
    {
        restaurantService.writeReservation(reservation);
    }
}
