package cookbook.objectControllers;

import cookbook.objects.QuanitityIngredients;
import cookbook.objects.ScheduledRecipeObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class WeekCalendar {


  /**
   * retrieves the alendar object that is the first week.
   */
  public static Calendar getFirstWeek(){
    Calendar c = Calendar.getInstance();
      int numDay = c.get(Calendar.DAY_OF_WEEK);
      int difference = Calendar.MONDAY - numDay;

      c.add(Calendar.DATE, difference);

      return c;

  }

  /**
   * gets a list of strings that represent the weeks.
   * @param numOfWeeks
   * @return
   */
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

  /**
   * returns list of scheduled recipes for the specified number of weeks.
   */
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

  // Insert a scheduled Recipe record

  /**
   * adds the scheduled recipe.
   */
  public static void addScheduledRecipe(String user_id, String recipe_id, java.sql.Date week_date) throws SQLException {

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    // Insert records into db
    String insertString ="INSERT INTO weekly_list (user_id, recipe_id, week_date) VALUES (?, ?, ?);";

    try (PreparedStatement preparedStmnt = conn.prepareStatement(insertString)) {
      preparedStmnt.setString(1, user_id);
      preparedStmnt.setString(2, recipe_id);
      preparedStmnt.setDate(3, week_date);
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e);
      // If there's an error inserting records, return false.
    }
  }


  // Delete a scheduled recipe from the db
  //Ahmed fix this class ------------------------------------------------------------------------

  /**
   * deletes a scheduled recipe.
   */
  public static void deleteScheduledRecipe(ScheduledRecipeObject schedRec) throws SQLException {
    String recipeId = schedRec.getRecipeId();
    java.sql.Date date = schedRec.getDate();

    String deleteString = "DELETE FROM weekly_list WHERE date = (?) AND recipe_id = (?);"; //<-- <-- <--

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement prepStatement = conn.prepareStatement(deleteString)) {
      prepStatement.setDate(1, date);
      prepStatement.setString(2, recipeId);
      prepStatement.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e);
      // If there's an error inserting records, return false.
    }
  }

  /**
   * generates the shopping list with the given weeks scheduled recipes.
   * @param week
   * @return
   */
  public static List<QuanitityIngredients> generateShoppingList(List<List<ScheduledRecipeObject>> week) {

    List<QuanitityIngredients> weekIngredients = new ArrayList<>();

    // Flatten the WeekList
    List<ScheduledRecipeObject> weekList = week.stream().flatMap(List::stream).collect(Collectors.toList());

    for( ScheduledRecipeObject meal : weekList) {
      for (QuanitityIngredients ingredient : meal.getIngredients()) {
        if (weekIngredients.contains(ingredient)) {
          // Find the index of the Ingredient in the ingredient tuple list
          int index = weekIngredients.indexOf(ingredient);
          // Add the ingredient amount to existing list amount
          weekIngredients.get(index).addAmount(ingredient.getAmount());
          continue;
        } else {
          // Add ingredient to ingredients list
          weekIngredients.add(ingredient);
        }
      }
    }
    // Retrun that week's ingredient
    return weekIngredients;
  }


}
