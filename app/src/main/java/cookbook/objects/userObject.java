// Declaring a package

package cookbook.objects;

public class userObject {
  private String Id;
  private String name;
  private String username;
  private String pass;
  private Boolean admin;

  // making the constructor for the user project
  public userObject(String i, String name, String username, String pass, Boolean isAdmin) {
    setUserId(i);
    setName(name);
    setUserName(username);
    setPass(pass);
    setAdminPrivelages(isAdmin);
  }
  // Using Setters and getters
  public String getId() {
    return Id;
  }

  private void setUserId(String inputId) {
    this.Id = inputId;
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  public String getUser_name() {
    return username;
  }

  private void setUserName(String user_name) {
    this.username = user_name;
  }

  public String getPass() {
    return pass;
  }

  private void setPass(String pass) {
    this.pass = pass;
  }

  private void setAdminPrivelages(Boolean isAdmin) {
    if(isAdmin == true) {
      this.admin = isAdmin;
    } else {
      this.admin = false;
    }
  }

  public Boolean getAdminPrivelages(){
    return admin;
  }

  

  
}
