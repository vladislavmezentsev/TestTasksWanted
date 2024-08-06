import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class InsuranceCalendar {

    private static final Set<String> HOLIDAY_SET = new HashSet<>();

    static {
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        HOLIDAY_SET.add(currentYear + "-01-01");
        HOLIDAY_SET.add(currentYear + "-02-23");
        HOLIDAY_SET.add(currentYear + "-03-08");
        HOLIDAY_SET.add(currentYear + "-05-09"); //etc
    }

    public Date getNextSubmissionDate(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int[] submissionDays = {1, 10, 20};
        Date nearestSubmissionDate = null;

        for (int submissionDay : submissionDays) {
            if (submissionDay >= day) {
                calendar.set(year, month, submissionDay, 18, 0, 0);
                nearestSubmissionDate = calendar.getTime();
                break;
            }
        }

        if (nearestSubmissionDate == null) {
            calendar.set(year, month + 1, submissionDays[0], 18, 0, 0);
            nearestSubmissionDate = calendar.getTime();
        }

        return getVacCheck(nearestSubmissionDate);
    }

    public boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    public boolean isHoliday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String formattedDate = String.format("%04d-%02d-%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
        return HOLIDAY_SET.contains(formattedDate);

    }

    public Date getVacCheck(Date modDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(modDate);

        while (isWeekend(cal.getTime()) || isHoliday(cal.getTime())) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        return cal.getTime();
    }
}