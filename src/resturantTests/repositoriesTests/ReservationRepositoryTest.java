package resturantTests.repositoriesTests;

import org.junit.jupiter.api.Test;
import restaurant.reservation.Reservation;
import restaurant.data.repositories.ReservationRepository;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationRepositoryTest {
    private ReservationRepository reservationRepository = new ReservationRepository();


    @Test
    public void testAddingReservation()
    {
        int tableNumber = 5;
        String customerName = "brian";
        Date startDate = Calendar.getInstance().getTime();
        Date endDate = Calendar.getInstance().getTime();

        Reservation reservation = new Reservation(tableNumber, customerName, startDate, endDate);
        reservationRepository.saveReservation(reservation);
        var res = reservationRepository.getReservationByCustomerUsername(customerName);
        assertEquals(tableNumber, res.getTableNum());
    }
}
