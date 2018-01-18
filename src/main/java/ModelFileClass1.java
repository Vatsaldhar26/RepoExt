import com.opencsv.bean.CsvBindByName;

public class ModelFileClass1 {

  @CsvBindByName(column = "tx_email_address")
  private String boLogin;

  @CsvBindByName(column = "id_employee")
  private int empID;

  @CsvBindByName(column = "bo_employee_id")
  private int boEmpID;

  public String getBoLogin() {
    return boLogin;
  }

  public void setBoLogin(String boLogin) {
    this.boLogin = boLogin;
  }

  public int getEmpID() {
    return empID;
  }

  public void setEmpID(int empID) {
    this.empID = empID;
  }

  public int getBoEmpID() {
    return boEmpID;
  }

  public void setBoEmpID(int boEmpID) {
    this.boEmpID = boEmpID;
  }

  @Override
  public String toString() {
    return boLogin + "," + empID + "," + boEmpID;
  }
}