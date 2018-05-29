import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        DataBaseFill fill = new DataBaseFill();
        int numberOfValues;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Maksymalnie mozna wygenerowac ok 160 000 rekordow");
        System.out.println("OGRANICZENIE SQL, NIE PRZEKRACZAJ 12 000 rekordow !");
        System.out.println("Upewnij sie ze wszystkie poprzednio dodane rekordy zostaly usuniete");
        System.out.println("Wprowadz liczbe rekordow jaka chcesz dodac");

        numberOfValues = scanner.nextInt();

        fill.createInsertIntoQuery(numberOfValues, DataBaseFill.EMPLOYEES_TABLE);
        fill.createInsertIntoQuery(numberOfValues, DataBaseFill.CLIENTS_TABLE);
        fill.createInsertIntoQuery(numberOfValues, DataBaseFill.EQUIPMENT_TABLE);

        System.out.println("dodano "+ numberOfValues + " rekordow");

        fill.closeConnection();
    }
}
