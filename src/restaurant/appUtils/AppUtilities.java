package restaurant.appUtils;

import restaurant.models.order.Order;
import restaurant.models.order.OrderDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AppUtilities {

    public boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public boolean isBeforeDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isBeforeDay(cal1, cal2);
    }


    public boolean isBeforeDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return true;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return false;
        return cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR);
    }

    public Date getFullDate(LocalDate localDate, int hours, int mins) throws ParseException {
        String dateString = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String timeString = hours +":"+ mins +":00";
        String fullDate = dateString + " " + timeString;
        Date date =  new SimpleDateFormat("d-MM-yyyy hh:mm:ss").parse(fullDate);

        return date;
    }

    // FIXME
    public boolean isTimeBetween(Date first, Date last, Date middle)
    {

        Calendar cFirst = Calendar.getInstance();
        cFirst.setTime(first);

        Calendar cLast = Calendar.getInstance();
        cLast.setTime(last);

        Calendar cMiddle = Calendar.getInstance();
        cMiddle.setTime(middle);

        if (cMiddle.after(first.getTime()) && cMiddle.before(last.getTime()) && cMiddle.getTime().equals(cFirst.getTime()))
            return middle.after(first) && middle.before(last) && !middle.equals(first);
        return false;
    }

    // TILL HANDLING IT IN THE VIEW WITH MORE FRIENDLY WAY
    public String getOrderDetailsForCook(Order order)
    {
        String s = String.format("Table: %d", order.getTableNumber());
        for (OrderDetails orderDetail:order.getOrdersDetails())
        {
            s += String.format(", Dish: %s, Quantity: %d", orderDetail.getDishName(), orderDetail.getQuantity());
        }

        return s;
    }


}
