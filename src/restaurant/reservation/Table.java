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

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

}
