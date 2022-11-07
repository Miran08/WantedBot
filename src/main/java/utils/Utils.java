package utils;


public class Utils {

    private Utils() {
    }

    //I know there is probably better way to do this...
    public static void wait(int toWait) {
        long millis = System.currentTimeMillis();
        while (System.currentTimeMillis() - millis < toWait) {
        }
    }

}
