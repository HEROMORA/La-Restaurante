package restaurant.data.services;

import java.util.ArrayList;

public interface Service<T> {
    void writeData(T object);
    ArrayList<T> readData();
}
