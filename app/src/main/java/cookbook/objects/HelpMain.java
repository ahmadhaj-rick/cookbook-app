package cookbook.objects;

/**
 * The HelpMain class represents a help item with its title and body content.
 */

public class HelpMain {
  private final String title;
  private final String body;

  /**
   * Constructs a new HelpMain item with the specified title and body content.
   *
   * @param title the title of the help item
   * @param body  the body content of the help item
   */

  public HelpMain(String title, String body) {
    this.title = title;
    this.body = body;
  }

  /**
   * Gets the title of the help item.
   *
   * @return the title of the help item
   */

  public String getTitle() {
    return title;
  }

  /**
   * Gets the body content of the help item.
   *
   * @return the body content of the help item
   */

  public String getBody() {
    return body;
  }
}
