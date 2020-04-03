package restaurant.appUtils;

import restaurant.models.order.Order;
import restaurant.models.order.OrderDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AppUtilities {

    public boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        LocalDate localDate1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    public boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public boolean isBeforeDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        LocalDate localDate1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate1.isBefore(localDate2);
    }

    public Date getFullDate(LocalDate localDate, int hours, int mins) throws ParseException {
        String dateString = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String timeString = hours +":"+ mins +":00";
        String fullDate = dateString + " " + timeString;
        Date date =  new SimpleDateFormat("d-MM-yyyy hh:mm:ss").parse(fullDate);

        return date;
    }


    public boolean isTimeBetween(Date first, Date last, Date middleFirst, Date middleLast)
    {
        LocalTime firstTime = LocalDateTime.ofInstant(first.toInstant(), ZoneId.systemDefault()).toLocalTime();

        LocalTime lastTime = LocalDateTime.ofInstant(last.toInstant(), ZoneId.systemDefault()).toLocalTime();

        LocalTime middleFirstTime = LocalDateTime.ofInstant(middleFirst.toInstant(), ZoneId.systemDefault()).toLocalTime();

        LocalTime middleLastTime = LocalDateTime.ofInstant(middleLast.toInstant(), ZoneId.systemDefault()).toLocalTime();

        return (middleFirstTime.isAfter(firstTime) && middleFirstTime.isBefore(lastTime)) || (middleFirstTime.equals(firstTime)) || (middleLastTime.isAfter(firstTime)  && middleLastTime.isBefore(lastTime)) || (middleLastTime.equals(lastTime));

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
