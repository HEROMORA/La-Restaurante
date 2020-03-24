package resturantTests.servicesTests;

import org.junit.jupiter.api.Test;
import restaurant.reservation.Table;
import restaurant.services.TableRepository;

import static org.junit.jupiter.api.Assertions.*;

public class TableRepositoryTest {

    private TableRepository tableRepository = new TableRepository();

    @Test
    public void testAllTablesExist()
    {
        var tables = tableRepository.getTables();

        assertEquals(7, tables.size());
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
    public void testGetTablesByAvailability()
    {
        var tables = tableRepository.getAvailableTables();
        for(Table table:tables)
        {
            assertTrue(table.getAvailability());
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
