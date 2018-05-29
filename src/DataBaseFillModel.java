import java.sql.SQLException;

public class DataBaseFillModel {

    OracleConnection oracleConnection;

    DataBaseFillModel(){
        this.establishConnection();

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

    void closeConnection() {
        try {
            oracleConnection.CloseConnection();
        }
        catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    int doQuery(String query)
    {
        String error;
        try {
            oracleConnection.DoQuery(query);
        }
        catch (java.sql.SQLIntegrityConstraintViolationException e)
        {
            if(e.getErrorCode()==1)
                error = "W bazie jest juz wpis z taka wartoscia pola unikatowego!";
            else
                error = "Napotkano blad. Przyczyna: \n" + e.getMessage();
            System.out.println(error);
            return e.getErrorCode();
        }
        catch (SQLException e) {
            error = "Napotkano blad. Przyczyna: \n" + e.getMessage();
            System.out.println(error);
            return e.getErrorCode();
        }
        return 0;
    }
}
