package restaurant.models.reservation;

public class Table implements Comparable<Table> {
    private int tableNumber;
    private int numberOfSeats;
    private boolean isSmoking;

    public int getTableNumber(){
        return this.tableNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean getIsSmoking() {
        return isSmoking;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public int compareTo(Table table) {
        if (getNumberOfSeats() == table.getNumberOfSeats()) return 0;
        return getNumberOfSeats() > table.getNumberOfSeats() ? 1 : -1;
    }
}
