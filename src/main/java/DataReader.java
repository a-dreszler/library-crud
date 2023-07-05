import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

class DataReader {

    private static final int INVALID_OPTION = -1;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = LocalDateTime.now().getYear();

    static int readOptionNumber() {
        try {
            int givenValue = SCANNER.nextInt();
            SCANNER.nextLine();
            return givenValue;
        } catch (InputMismatchException e) {
            SCANNER.nextLine();
            return INVALID_OPTION;
        }
    }

    static String readString() {
        return SCANNER.nextLine();
    }

    public static int readYear() {
        while (true) {
            try {
                int year = SCANNER.nextInt();
                SCANNER.nextLine();
                if (year >= MIN_YEAR && year <= MAX_YEAR) {
                    return year;
                } else {
                    System.out.println("Podano nieprawidłowy rok. Rok musi zawierać się między " + MIN_YEAR + ", a " + MAX_YEAR);
                }
            } catch (InputMismatchException e) {
                System.out.println("Rok musi być wyrażony liczbą");
                SCANNER.nextLine();
            }
        }
    }
}
