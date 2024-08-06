import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("99999.99");
        NumbersToWords numbersToWords = new NumbersToWords();
        System.out.println(numbersToWords.convertToWords(amount));
    }
}
