package restaurant.reservation;

import java.util.Date;


public class Reservation {
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
}
