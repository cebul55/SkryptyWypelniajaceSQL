import java.sql.*;

public class OracleConnection {

    private Connection con;
    private Statement stmnt;
    private boolean connected = false ;

    //used for general
    public OracleConnection(String User, String Password) throws SQLException, ClassNotFoundException {

        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection
                ("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf",User,Password);
        System.out.println("Connection established");
        stmnt = con.createStatement();
        connected = true;
        con.setAutoCommit(false);

    }

    //used only in current project
    public OracleConnection() throws SQLException, ClassNotFoundException {

        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection
                ("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf","bcybulsk","bcybulsk");
        System.out.println("Connection established");
        stmnt = con.createStatement();
        connected = true;
        con.setAutoCommit(false);

    }

    public boolean isConnected(){
        return connected;
    }

    void CloseConnection() throws SQLException
    {
        con.close();
        System.out.println("Connection closed");
    }

    ResultSet DoQuery(String query) throws SQLException {
        return stmnt.executeQuery(query);
    }
    Connection getcon()
    {
        return con;
    }
}
