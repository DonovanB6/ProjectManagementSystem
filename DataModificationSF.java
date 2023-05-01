import java.sql.*;

import javax.naming.AuthenticationException;
public class DataModificationSF {


    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
   

   public DataModificationSF() throws Exception {
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



   public void insertStudent(int sid, String sname, String major, String level, int byear, String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }

    String sql = "INSERT INTO Student (sid, sname, major, level, byear) " +
                 "VALUES (?, ?, ?, ?, ?)";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setInt(1, sid);
    stmt.setString(2, sname);
    stmt.setString(3, major);
    stmt.setString(4, level);
    stmt.setInt(5, byear);
    stmt.executeUpdate();
    System.out.println("Inserted student " + sid);
    stmt.close();
}

public void updateStudent(int sid, String sname, String major, String level, int byear, String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }



    String sql = "UPDATE Student " +
                 "SET sname = ?, major = ?, level = ?, byear = ? " +
                 "WHERE sid = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setString(1, sname);
    stmt.setString(2, major);
    stmt.setString(3, level);
    stmt.setInt(4, byear);
    stmt.setInt(5, sid);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected > 0) {
        System.out.println("Updated student " + sid);
    } else {
        System.out.println("No student with id " + sid + " found");
    }
    stmt.close();
}

public void deleteStudent(int sid, String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }


    String sql = "DELETE FROM Student WHERE sid = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setInt(1, sid);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected > 0) {
        System.out.println("Deleted student " + sid);
    } else {
        System.out.println("No student with id " + sid + " found");
    }
    stmt.close();
}

public void insertFaculty(int fid, String fname, String department, String username, String password) throws SQLException {

    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }


    String sql = "INSERT INTO Faculty (fid, fname, department) " +
                 "VALUES (?, ?, ?)";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setInt(1, fid);
    stmt.setString(2, fname);
    stmt.setString(3, department);
    stmt.executeUpdate();
    System.out.println("Inserted faculty " + fid);
    stmt.close();
}

public void updateFaculty(int fid, String fname, String department, String username, String password) throws SQLException {


    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }


    String sql = "UPDATE Faculty " +
                 "SET fname = ?, department = ? " +
                 "WHERE fid = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setString(1, fname);
    stmt.setString(2, department);
    stmt.setInt(3, fid);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected > 0) {
        System.out.println("Updated faculty " + fid);
    } else {
        System.out.println("No faculty with id " + fid + " found");
    }
    stmt.close();
}

public void deleteFaculty(int fid, String username, String password) throws SQLException {


    if (!authenticateUser(username, password)) {
        throw new Error("Invalid username or password");
    }


    String sql = "DELETE FROM Faculty WHERE fid = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setInt(1, fid);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected > 0) {
        System.out.println("Deleted faculty " + fid);
    } else {
        System.out.println("No faculty with id " + fid + " found");
    }
    stmt.close();
}



public static void main(String[] args) throws Exception {
    DataModificationSF con = new DataModificationSF();
    con.insertFaculty(9, "Kevin Smith", "Computer Science", "wssu", "pass");
}
}


