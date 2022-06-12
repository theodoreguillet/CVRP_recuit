package Utils;

public class Random {
    public static int randrange(int min, int max) {
        return min + (int)Math.floor(Math.random() * (max - min));
    }
}
