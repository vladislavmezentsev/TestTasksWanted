import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumbersToWords {

    private static final String[] units = {
            "", "один", "два", "три", "четыре", "пять", "шесть",
            "семь", "восемь", "девять", "десять", "одиннадцать",
            "двенадцать", "тринадцать", "четырнадцать", "пятнадцать",
            "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
    };

    private static final String[] tens = {
            "", "", "двадцать", "тридцать", "сорок", "пятьдесят",
            "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
    };

    private static final String[] hundreds = {
            "", "сто", "двести", "триста", "четыреста", "пятьсот",
            "шестьсот", "семьсот", "восемьсот", "девятьсот"
    };

    public static String convertToWords(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return "минус " + convertToWords(amount.negate());
        }

        amount = amount.setScale(2, RoundingMode.HALF_UP);
        String[] parts = amount.toString().split("\\.");
        int rubles = Integer.parseInt(parts[0]);
        int kopecks = Integer.parseInt(parts[1]);

        String rublesPart = convertRubles(rubles);
        String kopecksPart = convertKopecks(kopecks);

        return rublesPart + " " + kopecksPart;
    }

    private static String convertRubles(int rubles) {
        if (rubles == 0) {
            return "ноль рублей";
        }

        String words = "";

        if (rubles >= 1000) {
            int thousands = rubles / 1000;
            words += convertThousands(thousands) + " ";
            rubles %= 1000;
        }

        if (rubles >= 100) {
            words += hundreds[rubles / 100] + " ";
            rubles %= 100;
        }

        if (rubles >= 20) {
            words += tens[rubles / 10] + " ";
            rubles %= 10;
        }

        if (rubles > 0) {
            words += units[rubles] + " ";
        }

        String rubleEnding;
        if (rubles % 10 == 1 && rubles % 100 != 11) {
            rubleEnding = "рубль";
        } else if (rubles % 10 >= 2 && rubles % 10 <= 4 && (rubles % 100 < 10 || rubles % 100 >= 20)) {
            rubleEnding = "рубля";
        } else {
            rubleEnding = "рублей";
        }

        return words.trim() + " " + rubleEnding;
    }

    private static String convertThousands(int thousands) {
        if (thousands == 1) {
            return "одна тысяча";
        } else if (thousands == 2) {
            return "две тысячи";
        } else if (thousands < 20) {
            return units[thousands] + " тысяч";
        } else {
            String words = hundreds[thousands / 100] + " ";
            int tensPart = (thousands % 100) / 10;
            int unitsPart = thousands % 10;

            if (tensPart > 0) {
                words += tens[tensPart] + " ";
            }
            if (unitsPart > 0) {
                words += units[unitsPart] + " ";
            }
            return words.trim() + " тысяч";
        }
    }

    private static String convertKopecks(int kopecks) {
        if (kopecks == 0) {
            return "ноль копеек";
        }

        String words = "";

        if (kopecks >= 20) {
            words += tens[kopecks / 10] + " ";
            kopecks %= 10;
        }

        if (kopecks > 0) {
            words += units[kopecks] + " ";
        }

        String kopeckEnding;
        if (kopecks % 10 == 1 && kopecks % 100 != 11) {
            kopeckEnding = "копейка";
        } else if (kopecks % 10 >= 2 && kopecks % 10 <= 4 && (kopecks % 100 < 10 || kopecks % 100 >= 20)) {
            kopeckEnding = "копейки";
        } else {
            kopeckEnding = "копеек";
        }

        return words.trim() + " " + kopeckEnding;
    }

}