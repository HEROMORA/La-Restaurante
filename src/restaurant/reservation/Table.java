package restaurant.reservation;

public class Table {
    private int tableNumber;
    private int numberOfSeats;
    private boolean isSmoking;
    private boolean isAvailable;
    public boolean getAvailability(){
        return this.isAvailable;
    }
    public int getTableNumber(){
        return this.tableNumber;
    }
}
