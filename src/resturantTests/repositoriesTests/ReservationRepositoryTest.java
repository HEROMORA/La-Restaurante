package resturantTests.repositoriesTests;

import restaurant.data.repositories.ReservationRepository;

public class ReservationRepositoryTest {
    private ReservationRepository reservationRepository = new ReservationRepository();

    // Because we dont have a mocking file for the testing so running this function will mess up the systems
    // so this function should be ran either of mock file, or the added reservation should be deleted right after

 /*
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
  */
}
