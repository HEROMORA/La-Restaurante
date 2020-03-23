package restaurant.reservation;

import java.util.Date;


public class Reservation {
    private int id;
    private int tableID;
    private int customerID;
    private Date reservationDate;

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public void makeResevation(int numberOfSeats,boolean isSmoking){
        Table table = new Table();
        if(table.getAvailability(numberOfSeats,isSmoking)){
            this.tableID = table.getTableNumber();
        }
        else
            this.tableID = Integer.parseInt(null);

    }
}
