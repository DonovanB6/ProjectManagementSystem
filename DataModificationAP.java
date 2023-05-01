import java.sql.*;
public class DataModificationAP {


    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
   

   public DataModificationAP() throws Exception {
       // Get connection
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
       connection = DriverManager.getConnection("jdbc:sqlserver://wssu.database.windows.net:1433;database=ProjectManagement;user=dinno6@wssu;password=Ota170#N!;encrypt=true;trustServerCertificate=True;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"); 
   }
    
   public boolean authenticateUser(String username, String password) throws SQLException {
    String sql = "SELECT COUNT(*) FROM [User] WHERE username = ? AND password = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setString(1, username);
    stmt.setString(2, password);
    ResultSet rs = stmt.executeQuery();
    boolean result = false;
    if (rs.next() && rs.getInt(1) > 0) {
        result = true;
    }
    rs.close();
    stmt.close();
    return result;
}


   
   public void insertProject(int pid, String pname, Date sdate, Date edate, int pi, int copi, String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }


    String sql = "INSERT INTO Project (pid, pname, sdate, edate, pi, copi) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setInt(1, pid);
    stmt.setString(2, pname);
    stmt.setDate(3, sdate);
    stmt.setDate(4, edate);
    stmt.setInt(5, pi);
    stmt.setInt(6, copi);
    stmt.executeUpdate();
    System.out.println("Inserted project " + pid);
    stmt.close();
}

public void updateProject(int pid, String pname, Date sdate, Date edate, int pi, int copi, String username, String password) throws SQLException {


    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }


    String sql = "UPDATE Project " +
                 "SET pname = ?, sdate = ?, edate = ?, pi = ?, copi = ? " +
                 "WHERE pid = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setString(1, pname);
    stmt.setDate(2, sdate);
    stmt.setDate(3, edate);
    stmt.setInt(4, pi);
    stmt.setInt(5, copi);
    stmt.setInt(6, pid);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected > 0) {
        System.out.println("Updated project " + pid);
    } else {
        System.out.println("No project with id " + pid + " found");
    }
    stmt.close();
}

public void deleteProject(int pid, String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }


    String sql = "DELETE FROM Project WHERE pid = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setInt(1, pid);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected > 0) {
        System.out.println("Deleted project " + pid);
    } else {
        System.out.println("No project with id " + pid + " found");
    }
    stmt.close();
}

public void insertAssigned(int sid, int pid, Date sdate, Date edate,String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }

    String sql = "INSERT INTO Assigned (sid, pid, sdate, edate) " +
                 "VALUES (?, ?, ?, ?)";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setInt(1, sid);
    stmt.setInt(2, pid);
    stmt.setDate(3, sdate);
    stmt.setDate(4, edate);
    stmt.executeUpdate();
    System.out.println("Inserted assignment for student " + sid + " and project " + pid);
    stmt.close();
}

public void updateAssigned(int sid, int pid, Date sdate, Date edate, String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }


    String sql = "UPDATE Assigned " +
                 "SET sdate = ?, edate = ? " +
                 "WHERE sid = ? AND pid = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setDate(1, sdate);
    stmt.setDate(2, edate);
    stmt.setInt(3, sid);
    stmt.setInt(4, pid);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected > 0) {
        System.out.println("Updated assignment for student " + sid + " and project " + pid);
    } else {
        System.out.println("No assignment found for student " + sid + " and project " + pid);
    }
    stmt.close();
}

public void deleteAssigned(int sid, int pid, String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }

    String sql = "DELETE FROM Assigned WHERE sid = ? AND pid = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setInt(1, sid);
    stmt.setInt(2, pid);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected > 0) {
        System.out.println("Deleted assignment for student " + sid + " and project " + pid);
    } else {
        System.out.println("No assignment found for student " + sid + " and project " + pid);
    }
    stmt.close();
}
    public static void main(String[] args) throws Exception {
        DataModificationAP con = new DataModificationAP();
        con.insertProject(10, "Machine Learning", Date.valueOf("2022-06-05"), Date.valueOf("2023-02-08"),1,2,"wssu","pass");
    }



    
}