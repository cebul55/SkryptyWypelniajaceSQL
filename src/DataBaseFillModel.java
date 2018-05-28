import java.sql.SQLException;

public class DataBaseFillModel {

    OracleConnection oracleConnection;

    DataBaseFillModel(){
        this.establishConnection();
        this.closeConnection();
    }

    private void establishConnection(){
        try{
            oracleConnection = new OracleConnection();
        }
        catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            oracleConnection.CloseConnection();
        }
        catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }
}
