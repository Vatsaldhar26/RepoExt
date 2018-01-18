import java.io.Serializable;

public class UserRepos implements Serializable {
  private String name;
  private String description;
  private String url;
  private String language;
  private boolean fork;
  private String git_url;
  private String ssh_url;
  private String clone_url;
  private int size;
  private String created_at;
  private String updated_at;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = (name != null)?name:"null";
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = (language != null)? language:"null";
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = (description != null)?description:"null";
  }

  public boolean isFork() {
    return fork;
  }

  public void setFork(boolean fork) {
    this.fork = fork;
  }

  public String getGit_url() {
    return git_url;
  }

  public void setGit_url(String git_url) {
    this.git_url = git_url;
  }

  public String getSsh_url() {
    return ssh_url;
  }

  public void setSsh_url(String ssh_url) {
    this.ssh_url = ssh_url;
  }

  public String getClone_url() {
    return clone_url;
  }

  public void setClone_url(String clone_url) {
    this.clone_url = clone_url;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(String updated_at) {
    this.updated_at = updated_at;
  }

  @Override
  public String toString() {
    return name + "," + url + "," + description.replaceAll(",", "-") + "," + language + "," + fork + "," + git_url + "," + ssh_url + ","
        + clone_url + "," + size + "," + created_at + "," + updated_at;
  }

}