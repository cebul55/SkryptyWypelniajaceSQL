import java.math.BigInteger;

public class DataBaseFill extends DataBaseFillModel {

    public static final int EMPLOYEES_TABLE = 0;
    public static final int CLIENTS_TABLE = 1;
    public static final int EQUIPMENT_TABLE = 2;

    private static EnumStrings.firstName[] firstNamesArray;
    private static EnumStrings.lastName[] lastNamesArray;
    private static EnumStrings.adrress[] adrressesArray;
    private static EnumStrings.jobType[] jobTypesArray;

    private static RandomNumberGenerator numberGenerator;

    DataBaseFill(){
        super();

        firstNamesArray = new EnumStrings.firstName[EnumStrings.firstName.values().length];
        firstNamesArray = EnumStrings.firstName.values();

        lastNamesArray = new EnumStrings.lastName[EnumStrings.lastName.values().length];
        lastNamesArray = EnumStrings.lastName.values();

        adrressesArray = new EnumStrings.adrress[EnumStrings.adrress.values().length];
        adrressesArray = EnumStrings.adrress.values();

        jobTypesArray = new EnumStrings.jobType[EnumStrings.jobType.values().length];
        jobTypesArray = EnumStrings.jobType.values();

        numberGenerator = new RandomNumberGenerator();
    }

    private String getRandomFirstName()
    {
        numberGenerator.setRandomNumber(firstNamesArray.length);
        return firstNamesArray[numberGenerator.getRandomNumber()].toString();
    }

    private String getRandomLastName()
    {
        numberGenerator.setRandomNumber(lastNamesArray.length);
        return lastNamesArray[numberGenerator.getRandomNumber()].toString();
    }

    private String getRandomAdrress(){
        numberGenerator.setRandomNumber(adrressesArray.length);
        return adrressesArray[numberGenerator.getRandomNumber()].toString();
    }

    private String getRandomJobType(){
        numberGenerator.setRandomNumber(jobTypesArray.length);
        return jobTypesArray[numberGenerator.getRandomNumber()].toString();
    }

    private String getRandomSalary(){
        int salary = 4000 ;
        numberGenerator.setRandomNumber(1000);
        salary += numberGenerator.getRandomNumber();
        return Integer.toString(salary);
    }

    private String getRandomSchoolNo(){
        numberGenerator.setRandomNumber(10);
        return Integer.toString(numberGenerator.getRandomNumber());
    }

    private String getRandomYearOfProduction(){
        numberGenerator.setRandomNumber(19);
        return Integer.toString(numberGenerator.getRandomNumber() + 2000 );
    }

    public void printRandomValues(){
        String randomString;
        randomString = getRandomFirstName() + " " + getRandomLastName() + " " + getRandomAdrress() + " " + getRandomJobType()
        + " " + getRandomSalary() + " School:" + getRandomSchoolNo();
        System.out.println(randomString);
    }

    private BigInteger getNewPesel(int lap){
        BigInteger pesel = new BigInteger("0");
        switch (lap){
            case 9:
                pesel = new BigInteger("50010199999");
                break;
            case 8:
                pesel = new BigInteger("50010188888");
                break;
            case 7:
                pesel = new BigInteger("50010177777");
                break;
            case 6:
                pesel = new BigInteger("50010166666");
                break;
            case 5:
                pesel = new BigInteger("50010155555");
                break;
            case 4:
                pesel = new BigInteger("50010144444");
                break;
            case 3:
                pesel = new BigInteger("50010133333");
                break;
            case 2:
                pesel = new BigInteger("50010122222");
                break;
            case 1:
                pesel = new BigInteger("50010111111");
                break;
            case 0:
                pesel = new BigInteger("50010100000");
                break;
        }
        return pesel;
    }

    private void insertEquipment(int numberOfValues)
    {
        String baseQuery = "INSERT INTO EQUIPMENT(EQUIPMENT_ID, PRODUCTIONYEAR, SCHOOL_ID)\n" +
                " VALUES (";
        String query;
        int baseID = 200;

        if(this.findOldestPK(EQUIPMENT_TABLE) != "null")
        {
            baseID = Integer.parseInt(this.findOldestPK(EQUIPMENT_TABLE));
        }

        //System.out.println(baseID);

        for (int i = 0 ; i < numberOfValues ; i++)
        {
            query = baseQuery;
            query += Integer.toString(baseID + i) + " , " + getRandomYearOfProduction() + " , " + getRandomSchoolNo() + ")";

            if(this.doQuery(query) == -127)
                numberOfValues++;
            //System.out.println(query);
        }
    }

    public void createInsertIntoQuery(int numberOfValues, int numberOfTable){
        int lap = 0;
        int day = 1;
        int month = 1;
        int year = 50;


        String baseQuery = "";
        String query;
        switch (numberOfTable){
            case EMPLOYEES_TABLE:
                baseQuery = "INSERT INTO EMPLOYEES (PESEL, FIRSTNAME, SECONDNAME, ADDRESS, JOBTYPE, SALARY, SCHOOL_ID)\n" +
                        " VALUES (";
                break;
            case CLIENTS_TABLE:
                baseQuery = "INSERT INTO CLIENTS(CLIENT_PESEL, FIRSTNAME, SECONDNAME, ADDRESS)\n" +
                        "    VALUES (";
                break;
            case EQUIPMENT_TABLE:
                baseQuery = "INSERT INTO EQUIPMENT(EQUIPMENT_ID, PRODUCTIONYEAR, SCHOOL_ID)\n" +
                        " VALUES (";
                this.insertEquipment(numberOfValues);
                return;
        }

        //BigInteger basePesel = this.getNewPesel(lap);
        BigInteger basePesel;
        BigInteger pesel;
        StringBuilder builder;

        if(this.findOldestPK(numberOfTable) != "null")
        {
            basePesel = new BigInteger(this.findOldestPK(numberOfTable));
            pesel = basePesel;

            lap = this.setLap(basePesel);

            builder = new StringBuilder(basePesel.toString());

            //zmiana dni na odpowiednie
            String dayString = Character.toString(builder.charAt(4)) + Character.toString(builder.charAt(5));
            String monthString = Character.toString(builder.charAt(2)) + Character.toString(builder.charAt(3));
            String yearString = Character.toString(builder.charAt(0)) + Character.toString(builder.charAt(1));

            day = Integer.parseInt(dayString);
            month = Integer.parseInt(monthString);
            year = Integer.parseInt(yearString);

            //zamiana dnia w dacie na 01
            builder.setCharAt(4,'0');
            builder.setCharAt(5,'1');
            basePesel = new BigInteger(builder.toString());

        }
        else{
            basePesel = this.getNewPesel(lap);
            pesel = basePesel;
        }







//start of creating different pesels
        for(int i = 0 ; i  < numberOfValues ; i++)
        {
            query = baseQuery;
            if( day == 28 ){
                day = 1;
                month++;
                if( month == 13){
                    month = 1;
                    year++ ;
                    if(year < 100) {
                        basePesel = basePesel.add(new BigInteger("1000000000"));
                        basePesel = basePesel.subtract(new BigInteger("110000000"));
                        pesel = basePesel;
                    }
                }
                else{
                    basePesel = basePesel.add(new BigInteger("10000000"));
                    pesel = basePesel;
                }
            }
            else if( day < 28){
                day++;
                pesel = pesel.add(new BigInteger("100000"));
            }


            if(year == 100)
            {
                year = 50;
                lap++;
                //breaks the loop in order not to destroy values
                if(lap == 10)
                    return;
                basePesel = this.getNewPesel(lap);
                pesel = basePesel;
            }

//end of creating different pesel
            switch (numberOfTable) {
                case EMPLOYEES_TABLE: {
                    query   += "'" + pesel + "','" + getRandomFirstName() + "','" + getRandomLastName() + "','"
                            + getRandomAdrress() + "','" + getRandomJobType() + "', "
                            + getRandomSalary() + ", " + getRandomSchoolNo() + " )";
                    break;
                }
                case CLIENTS_TABLE: {
                    query   += "'" + pesel + "','" + getRandomFirstName() + "','" + getRandomLastName() + "','"
                            + getRandomAdrress() + "' )";
                    break;
                }
            }
////Jezeli w bazie jest identyczny wpis, petla wykona sie o raz wiecej
            if(this.doQuery(query) == -127)
                numberOfValues++;
            if(numberOfValues >= 165000 || (lap == 9 && year == 99))
            {
                System.out.println("nie mozna wygenerowac wiecej unikatowych peseli. Koniec dzialania programu");
                return;
            }

            //System.out.println(query);
            //System.out.println(pesel + " "+ year + " " + month +  " " + day);
        }
    }

    public String findOldestPK(int table)
    {
        String query = "";
        String pesel = "";
        switch (table)
        {
            case EMPLOYEES_TABLE:
            {
                query = "SELECT PESEL,  SUBSTR(PESEL,11,1) As lastChar  FROM EMPLOYEES\n" +
                        "WHERE (SUBSTR(PESEL, 7, 5) = '99999' OR SUBSTR(PESEL, 7, 5) = '88888' OR SUBSTR(PESEL, 7, 5) = '77777'\n" +
                        "OR SUBSTR(PESEL, 7, 5) = '66666' OR SUBSTR(PESEL, 7, 5) = '55555' OR SUBSTR(PESEL, 7, 5) = '44444'\n" +
                        "OR SUBSTR(PESEL, 7, 5) = '33333' OR SUBSTR(PESEL, 7, 5) = '22222' OR SUBSTR(PESEL, 7, 5) = '11111'\n" +
                        "OR SUBSTR(PESEL, 7, 5) = '00000')\n" +
                        "ORDER BY lastChar DESC, PESEL DESC , lastChar DESC";
                break;
            }
            case CLIENTS_TABLE:
            {
                query = "SELECT CLIENT_PESEL , SUBSTR(CLIENT_PESEL,11,1) As lastChar FROM CLIENTS\n" +
                        "WHERE (SUBSTR(CLIENT_PESEL, 7, 5) = '99999' OR SUBSTR(CLIENT_PESEL, 7, 5) = '88888' OR SUBSTR(CLIENT_PESEL, 7, 5) = '77777'\n" +
                        "OR SUBSTR(CLIENT_PESEL, 7, 5) = '66666' OR SUBSTR(CLIENT_PESEL, 7, 5) = '55555' OR SUBSTR(CLIENT_PESEL, 7, 5) = '44444'\n" +
                        "OR SUBSTR(CLIENT_PESEL, 7, 5) = '33333' OR SUBSTR(CLIENT_PESEL, 7, 5) = '22222' OR SUBSTR(CLIENT_PESEL, 7, 5) = '11111'\n" +
                        "OR SUBSTR(CLIENT_PESEL, 7, 5) = '00000')\n" +
                        "ORDER BY lastChar Desc, CLIENT_PESEL DESC , lastChar DESC";
                break;
            }
            case EQUIPMENT_TABLE:
            {
                query = "SELECT EQUIPMENT_ID FROM EQUIPMENT\n" +
                        "WHERE ROWNUM <= 1 AND EQUIPMENT_ID > 199\n" +
                        "ORDER BY EQUIPMENT_ID DESC ";
                break;
            }
        }
        pesel = this.returnFromQueryString(query);
        return pesel;
    }

    public int setLap(BigInteger pesel) {
        char remainder = pesel.toString().charAt(10);
        switch ( remainder ){
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
        }
        return 0;
    }
}
