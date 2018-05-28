import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
//        DataBaseFillModel dataBaseFillModel;
//        dataBaseFillModel = new DataBaseFillModel();
        EmployeesFill fill = new EmployeesFill();
        fill.createStatments(34000);
    }
}
