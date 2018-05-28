import java.util.Random;

public class RandomNumberGenerator {
    private static Random random;
    private static int randomNumber;

    public RandomNumberGenerator()
    {
        random = new Random();
        randomNumber = 0;
    }

    void setRandomNumber(int maxNumber)
    {
        randomNumber = random.nextInt(maxNumber);
    }

    int getRandomNumber()
    {
        return randomNumber;
    }
}
