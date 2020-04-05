package restaurant.models.reservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Reservation {

    // This class handles the reservation between the customer and table

    private int tableNum;
    private String customerUserName;
    private Date reservationDate;
    private Date endReservationDate;

    public Reservation()
    {

    }

    public Reservation(int tableNum, String customerUserName, Date reservationDate, Date endReservationDate)
    {
        this.tableNum = tableNum;
        this.customerUserName = customerUserName;
        this.reservationDate = reservationDate;
        this.endReservationDate = endReservationDate;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public int getTableNum() {
        return tableNum;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public Date getEndReservationDate() {
        return endReservationDate;
    }

    public void setEndReservationDate(Date endReservationDate) {
        this.endReservationDate = endReservationDate;
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String reservationTime = dateFormat.format(reservationDate);
        String endTime = dateFormat.format(endReservationDate);

        return String.format("Customer Name: %s, Table Number: %d, from: %s to: %s",
                customerUserName, tableNum, reservationTime, endTime);
    }
}
