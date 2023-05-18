package cookbook.objects;

public class HelpMain {
  private final String title;
  private final String body;

  public HelpMain(String title, String body) {
    this.title = title;
    this.body = body;
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }
}
