package cookbook.javaFX;

import cookbook.objectControllers.ScheduledRecipeController;
import cookbook.objects.ScheduledRecipeObject;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeekCalendar {

  public static Calendar getFirstWeek(){
    Calendar c = Calendar.getInstance();
      int numDay = c.get(Calendar.DAY_OF_WEEK);
      int difference = Calendar.MONDAY - numDay;

      c.add(Calendar.DATE, difference);

      return c;

  }

  public static List<String> getFollowingWeeks(int numOfWeeks) {
    List<String> stringList = new ArrayList<>();
    final ZonedDateTime in = ZonedDateTime.now();

    for(int n = 1; n <numOfWeeks; n++) {
      StringBuilder builder = new StringBuilder();
      final ZonedDateTime lastWeekStart = in.plusWeeks(n).with(DayOfWeek.MONDAY);
      final ZonedDateTime lastWeekEnd = lastWeekStart.plusDays(6);
      builder.append(lastWeekStart.format(DateTimeFormatter.ISO_LOCAL_DATE));
      builder.append(" - ").append(lastWeekEnd.format(DateTimeFormatter.ISO_LOCAL_DATE));
      stringList.add(builder.toString());
    }
    return stringList;
  }

  public static List<List<ScheduledRecipeObject>> getListOfWeeks (int numOfWeek) throws SQLException {
    List<List<ScheduledRecipeObject>> week = new ArrayList<>();

    Calendar thisMonday = getFirstWeek();
    System.out.println("This Monday: " +thisMonday.getTime());

    thisMonday.add(Calendar.DATE, numOfWeek*7);
    Calendar newMonday = thisMonday;

    java.util.Date dateOfMondayWeek = newMonday.getTime();
    java.sql.Date weekListMonday = new java.sql.Date(dateOfMondayWeek.getTime());

    week.add(ScheduledRecipeController.getDateSchedule((weekListMonday)));

    java.util.Date thisDay;
    java.sql.Date thisDate;

    for (int day = 1; day < 7; day ++) {
      newMonday.add(Calendar.DATE, 1);
      thisDay = newMonday.getTime();
      thisDate = new java.sql.Date(thisDay.getTime());

      week.add(ScheduledRecipeController.getDateSchedule(thisDate));
    }
    return week;
  }





}
