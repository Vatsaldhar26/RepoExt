public class BaseDetection {

  private void concatIssue(Connection connection, String urlParameter){

    int a;
    Integer i = 0;
    String var2 = "something";
    String accountBalanceQuery = "SELECT * FROM accounts WHERE account_owner_id = " + var2;
    try {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(accountBalanceQuery);                                    // not vulnerable
      while (rs.next()) {
        System.out.println("Account number : " + rs.getInt("account_number") +
            "\tBalance : " + rs.getFloat("balance") + "\tOwner Id : " + rs
            .getInt("account_owner_id"));
      }
      ResultSet rs1 = statement.executeQuery(accountBalanceQuery + urlParameter);                    // vulnerable
      /**
       * different execute versions
       */
      boolean result = statement.execute(accountBalanceQuery);                                       // not vulnerable
      int result1 = statement.executeUpdate("UPDATE table1 SET id = 0 WHERE url = " + urlParameter); // vulnerable

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
