package resturantTests.repositoriesTests;

import org.junit.jupiter.api.Test;
import restaurant.models.reservation.Table;
import restaurant.data.repositories.TableRepository;

import static org.junit.jupiter.api.Assertions.*;

public class TableRepositoryTest {

    // Unit test cases for the TableRepository Class

    private TableRepository tableRepository = new TableRepository();

    @Test
    public void testAllTablesExist()
    {
        var numberOfTables = 7; // Bad Practice and in testing should be sit to constants
        var tables = tableRepository.getTables();

        assertEquals(numberOfTables, tables.size());
    }

    @Test
    public void testGetTableByTableNumber()
    {
        var tableNumber = 3;
        var table = tableRepository.getTableByTableNumber(tableNumber);
        assertEquals(tableNumber, table.getTableNumber());
    }

    @Test
    public void testGetTablesBySeatNumbers()
    {
        var numberOfSeats = 4;
        var tables = tableRepository.getTablesByNumberOfSeats(numberOfSeats);
        for(Table table:tables)
        {
            assertEquals(4, table.getNumberOfSeats());
        }
    }

    @Test
    public void testGetTablesByEligibleSeatNumbersAndSmoking()
    {
        var numberOfSeats = 6;
        var isSmoking = false;
        var tables = tableRepository.getTablesByEligibleNumberOfSeatsAndSmoking(numberOfSeats, isSmoking);
        for (Table table:tables)
        {
            assertTrue(table.getNumberOfSeats() >= numberOfSeats && table.getIsSmoking() == isSmoking);
        }
    }

    @Test
    public void testGetSmokingTables()
    {
        var tables = tableRepository.getSmokingTables();
        for(Table table:tables)
        {
            assertTrue(table.isSmoking());
        }
    }

    @Test
    public void testGetNonSmokingTables()
    {
        var tables = tableRepository.getNonSmokingTables();
        for(Table table:tables)
        {
            assertFalse(table.isSmoking());
        }
    }
}
