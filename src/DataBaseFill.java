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
            case 0:
                pesel = new BigInteger("50010199999");
                break;
            case 1:
                pesel = new BigInteger("50010188888");
                break;
            case 2:
                pesel = new BigInteger("50010177777");
                break;
            case 3:
                pesel = new BigInteger("50010166666");
                break;
            case 4:
                pesel = new BigInteger("50010155555");
                break;
            case 5:
                pesel = new BigInteger("50010144444");
                break;
            case 6:
                pesel = new BigInteger("50010133333");
                break;
            case 7:
                pesel = new BigInteger("50010122222");
                break;
            case 8:
                pesel = new BigInteger("50010111111");
                break;
            case 9:
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

        for (int i = 0 ; i < numberOfValues ; i++)
        {
            query = baseQuery;
            query += Integer.toString(baseID + i) + " , " + getRandomYearOfProduction() + " , " + getRandomSchoolNo() + ")";

            this.doQuery(query);
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

        BigInteger basePesel = this.getNewPesel(lap);
        BigInteger pesel = basePesel;

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

            this.doQuery(query);

            //System.out.println(query);
            //System.out.println(pesel + " "+ year + " " + month +  " " + day);
        }
    }


}
