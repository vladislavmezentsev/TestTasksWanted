import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsuranceCalendarTest {

    @Test
    public void testGetNextSubmissionDate_onSubmissionDay() throws Exception {
        InsuranceCalendar calendar = new InsuranceCalendar();

        // Тестируем 1-е число, которое является днем отправки
        String inputDateString = "2024-08-01 12:00:00"; // 1 августа 2024
        Date inputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDateString);
        Date expectedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-08-01 18:00:00");

        assertEquals(expectedDate, calendar.getNextSubmissionDate(inputDate));
    }

    @Test
    public void testGetNextSubmissionDate_onHoliday() throws Exception {
        InsuranceCalendar calendar = new InsuranceCalendar();

        // Тестируем 9-е число, которое перед праздником
        String inputDateString = "2024-05-08 12:00:00"; // 8 мая 2024
        Date inputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDateString);
        Date expectedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-05-10 18:00:00");

        assertEquals(expectedDate, calendar.getNextSubmissionDate(inputDate));
    }

    @Test
    public void testGetNextSubmissionDate_betweenSubmissionDays() throws Exception {
        InsuranceCalendar calendar = new InsuranceCalendar();

        // Тестируем 5-е число, попадающее между отправками
        String inputDateString = "2024-08-05 12:00:00"; // 5 октября 2024
        Date inputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDateString);
        Date expectedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-08-09 18:00:00");

        assertEquals(expectedDate, calendar.getNextSubmissionDate(inputDate));
    }

    @Test
    public void testGetNextSubmissionDate_endOfMonth() throws Exception {
        InsuranceCalendar calendar = new InsuranceCalendar();

        // Тестируем 30-е число
        String inputDateString = "2024-08-30 12:00:00"; // 30 августа 2024
        Date inputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDateString);
        Date expectedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-08-30 18:00:00");

        assertEquals(expectedDate, calendar.getNextSubmissionDate(inputDate));
    }

    @Test
    public void testIsWeekend() {
        InsuranceCalendar calendar = new InsuranceCalendar();

        // Проверяем выходные
        Calendar cal = Calendar.getInstance();

        cal.set(2024, Calendar.AUGUST, 3); // Суббота
        assertEquals(true, calendar.isWeekend(cal.getTime()));

        cal.set(2024, Calendar.AUGUST, 4); // Воскресенье
        assertEquals(true, calendar.isWeekend(cal.getTime()));

        cal.set(2024, Calendar.AUGUST, 5); // Понедельник
        assertEquals(false, calendar.isWeekend(cal.getTime()));
    }

    @Test
    public void testIsHoliday() {
        InsuranceCalendar calendar = new InsuranceCalendar();

        // Проверяем праздничные дни
        Calendar cal = Calendar.getInstance();

        cal.set(2024, Calendar.JANUARY, 1); // 1 января
        assertEquals(true, calendar.isHoliday(cal.getTime()));

        cal.set(2024, Calendar.FEBRUARY, 23); // 23 февраля
        assertEquals(true, calendar.isHoliday(cal.getTime()));

        cal.set(2024, Calendar.MARCH, 8); // 8 марта
        assertEquals(true, calendar.isHoliday(cal.getTime()));

        cal.set(2024, Calendar.MAY, 9); // 9 мая
        assertEquals(true, calendar.isHoliday(cal.getTime()));

        cal.set(2024, Calendar.MARCH, 9); // 9 марта (не праздник)
        assertEquals(false, calendar.isHoliday(cal.getTime()));
    }
}