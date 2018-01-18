import com.opencsv.bean.CsvBindByName;

public class ModelFileClass2 {

  @CsvBindByName(column = "sys_email")
  private String boLogin;

  @CsvBindByName(column = "git_login")
  private String gitUserName;

  @CsvBindByName(column = "git_url")
  private String gitUserProfile;

  public String getBoLogin() {
    return boLogin;
  }

  public void setBoLogin(String boLogin) {
    this.boLogin = boLogin;
  }

  public String getGitUserName() {
    return gitUserName;
  }

  public void setGitUserName(String gitUserName) {
    this.gitUserName = gitUserName;
  }

  public String getGitUserProfile() {
    return gitUserProfile;
  }

  public void setGitUserProfile(String gitUserProfile) {
    this.gitUserProfile = gitUserProfile;
  }

  @Override
  public String toString() {
    return boLogin + "," + gitUserName + "," + gitUserProfile;
  }
}
