package restaurant.reservation;

import java.util.Date;


public class Reservation {
    private int tableNum;
    private String customerUserName;
    private Date reservationDate;
    private Date endDate;

    public Reservation()
    {

    }

    public Reservation(int tableNum, String customerUserName, Date reservationDate, Date endDate)
    {
        this.tableNum = tableNum;
        this.customerUserName = customerUserName;
        this.reservationDate = reservationDate;
        this.endDate = endDate;
    }

    public Date getEndDate() { return endDate; }

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

}
