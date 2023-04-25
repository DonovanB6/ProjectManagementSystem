import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class DataRetrieval
{
    Connection connection = null;
     Statement stmt = null;
     ResultSet rs = null;
    

    public DataRetrieval() throws Exception {
        // Get connection
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection("jdbc:sqlserver://wssu.database.windows.net:1433;database=ProjectManagement;user=dinno6@wssu;password=Ota170#N!;encrypt=true;trustServerCertificate=True;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
		if (connection != null) {
            System.out.println();
            System.out.println("Successfully connected");
            System.out.println();
            // Meta data
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println("\nDriver Information");
            System.out.println("Driver Name: "+ meta.getDriverName());
            System.out.println("Driver Version: "+ meta.getDriverVersion());
            System.out.println("\nDatabase Information ");
            System.out.println("Database Name: "+ meta.getDatabaseProductName());
            System.out.println("Database Version: "+meta.getDatabaseProductVersion());
        }

        
    }
    public void printProjectsForStudent(int studentId) throws Exception {
        // Create and execute an SQL statement that returns the project names for a specified student.
        String sql = "SELECT Project.pname FROM Assigned JOIN Project ON Assigned.pid = Project.pid WHERE Assigned.sid = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, studentId);
        ResultSet rs = stmt.executeQuery();
        // Iterate through the data in the result set and display it.
        System.out.println("Projects for student with id " + studentId + ":");
        while (rs.next()) {
            String projectName = rs.getString("pname");
            System.out.println(projectName);
        }
        rs.close();
        stmt.close();
    }
    
    public void printPeopleForProject(int projectId) throws Exception {
        // Create and execute an SQL statement that returns the student and faculty names for a specified project.
        String sql = "SELECT DISTINCT Student.sname, Faculty.fname " +
                     "FROM Assigned " +
                     "JOIN Project ON Assigned.pid = Project.pid " +
                     "JOIN Student ON Assigned.sid = Student.sid " +
                     "JOIN Faculty ON (Project.pi = Faculty.fid OR Project.copi = Faculty.fid) " +
                     "WHERE Assigned.pid = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, projectId);
        ResultSet rs = stmt.executeQuery();
        // Iterate through the data in the result set and display it.
        System.out.println("People working on project with id " + projectId + ":");
        
        List<String> printedStudentNames = new ArrayList<>();
        List<String> printedFacultyNames = new ArrayList<>();
        while (rs.next()) {
            String studentName = rs.getString("sname");
            String facultyName = rs.getString("fname");
            if (!printedStudentNames.contains(studentName)) {
              
                printedStudentNames.add(studentName);
            } 
            if (!printedFacultyNames.contains(facultyName)) {
                
                printedFacultyNames.add(facultyName);
            } 
        }
        System.out.println("Students:" + printedStudentNames);
        System.out.println("Faculty:" + printedFacultyNames);
        rs.close();
        stmt.close();
    }
    
    
    
    public void printProjectsForFaculty(int facultyId, Date startDate, Date endDate) throws Exception {
        // Create and execute an SQL statement that returns the project names for a specified faculty within a specified time range.
        String sql = "SELECT Project.pname FROM Project WHERE Project.pi = ? AND Project.sdate >= ? AND Project.edate <= ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, facultyId);
        stmt.setDate(2, startDate);
        stmt.setDate(3, endDate);
        ResultSet rs = stmt.executeQuery();
        // Iterate through the data in the result set and display it.
        System.out.println("Projects supervised by faculty with id " + facultyId + " between " + startDate.toString() + " and " + endDate.toString() + ":");
        while (rs.next()) {
            String projectName = rs.getString("pname");
            System.out.println(projectName);
        }
        rs.close();
        stmt.close();
    }
    
    public void closeCon() throws Exception
    {
        connection.close();
    }
    public static void main(String args[]) throws Exception {
        DataRetrieval con = new DataRetrieval();
        con.printPeopleForProject(1);
    }
}