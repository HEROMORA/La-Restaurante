package restaurant.reservation;

public class Table {
    private int tableNumber;
    private int numberOfSeats;
    private boolean isSmoking;
    private boolean isAvailable;
    public boolean getAvailability(int numberOfSeats, boolean isSmoking){
        if(numberOfSeats == this.numberOfSeats && isSmoking == this.isSmoking)
            isAvailable = true;
        else
            isAvailable = false;
        return this.isAvailable;
    }
    public int getTableNumber(){
        return this.tableNumber;
    }
}
