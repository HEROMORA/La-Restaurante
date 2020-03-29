package restaurant.reservation;

import java.util.Date;


public class Reservation {
    private int tableNum;
    private String customerUserName;
    private Date reservationDate;

    public Reservation()
    {

    }

    public Reservation(int tableNum, String customerUserName, Date reservationDate)
    {
        this.tableNum = tableNum;
        this.customerUserName = customerUserName;
        this.reservationDate = reservationDate;
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

    public void makeReservation(int numberOfSeats, boolean isSmoking){
        Table table = new Table();
        if(table.getAvailability()){
            this.tableNum = table.getTableNumber();
        }
        else
            this.tableNum = Integer.parseInt(null);

    }
}
