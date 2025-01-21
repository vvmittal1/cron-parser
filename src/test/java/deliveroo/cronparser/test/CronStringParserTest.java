package deliveroo.cronparser.test;

import deliveroo.cronparser.RawCronString;
import deliveroo.cronparser.SimplifiedCronString;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CronStringParserTest {

    private static final String CRON_STRING1 = "2/15 0 1,15 * 1-5 /usr/bin/find";
    private static final String CRON_STRING2 = "2/25 */5 1,15 * 1-5 /usr/bin/find";
    private static final String CRON_STRING3 = "2/15 1-17/5 1,15-20 * 1-5 /usr/bin/find";
    private static final String CRON_STRING4 = "*/15 0 1,15 * 1-5 /usr/bin/find";
    private RawCronString rawCronString1 = new RawCronString(CRON_STRING1);
    private RawCronString rawCronString2 = new RawCronString(CRON_STRING2);
    private RawCronString rawCronString3 = new RawCronString(CRON_STRING3);
    private RawCronString rawCronString4 = new RawCronString(CRON_STRING4);

    @Test
    void shouldParseMinutesOfExpressions() {
        SimplifiedCronString simplifiedCronString = rawCronString1.simplifyCronString();
        assertThat(simplifiedCronString.getMinutes()).containsExactly(2, 17, 32, 47);
        simplifiedCronString = rawCronString2.simplifyCronString();
        assertThat(simplifiedCronString.getMinutes()).containsExactly(2, 27, 52);
        simplifiedCronString = rawCronString3.simplifyCronString();
        assertThat(simplifiedCronString.getMinutes()).containsExactly(2, 17, 32, 47);
        simplifiedCronString = rawCronString4.simplifyCronString();
        assertThat(simplifiedCronString.getMinutes()).containsExactly(0,15,30,45);
    }

    @Test
    void shouldParseHoursOfExpressions() {
        SimplifiedCronString simplifiedCronString = rawCronString1.simplifyCronString();
        assertThat(simplifiedCronString.getHours()).containsExactly(0);
        simplifiedCronString = rawCronString2.simplifyCronString();
        assertThat(simplifiedCronString.getHours()).containsExactly(0,5,10,15,20);
        simplifiedCronString = rawCronString3.simplifyCronString();
        assertThat(simplifiedCronString.getHours()).containsExactly(1,6,11,16);
        simplifiedCronString = rawCronString4.simplifyCronString();
        assertThat(simplifiedCronString.getHours()).containsExactly(0);
    }

    @Test
    void shouldParseDaysOfMonthOfExpressions() {
        SimplifiedCronString simplifiedCronString = rawCronString1.simplifyCronString();
        assertThat(simplifiedCronString.getDaysOfTheMonth()).containsExactly(1,15);
        simplifiedCronString = rawCronString2.simplifyCronString();
        assertThat(simplifiedCronString.getDaysOfTheMonth()).containsExactly(1,15);
        simplifiedCronString = rawCronString3.simplifyCronString();
        assertThat(simplifiedCronString.getDaysOfTheMonth()).containsExactly(1,15,16,17,18,19,20);
        simplifiedCronString = rawCronString4.simplifyCronString();
        assertThat(simplifiedCronString.getDaysOfTheMonth()).containsExactly(1,15);
    }

    @Test
    void shouldParseMonthsOfExpressions() {

        SimplifiedCronString simplifiedCronString = rawCronString1.simplifyCronString();
        assertThat(simplifiedCronString.getMonth()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        simplifiedCronString = rawCronString2.simplifyCronString();
        assertThat(simplifiedCronString.getMonth()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        simplifiedCronString = rawCronString3.simplifyCronString();
        assertThat(simplifiedCronString.getMonth()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        simplifiedCronString = rawCronString4.simplifyCronString();
        assertThat(simplifiedCronString.getMonth()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    }

    @Test
    void shouldParseDaysOfWeekOfExpressions() {

        SimplifiedCronString simplifiedCronString = rawCronString1.simplifyCronString();
        assertThat(simplifiedCronString.getDaysOfTheWeek()).containsExactly(1, 2, 3, 4, 5);
        simplifiedCronString = rawCronString2.simplifyCronString();
        assertThat(simplifiedCronString.getDaysOfTheWeek()).containsExactly(1, 2, 3, 4, 5);
        simplifiedCronString = rawCronString3.simplifyCronString();
        assertThat(simplifiedCronString.getDaysOfTheWeek()).containsExactly(1, 2, 3, 4, 5);
        simplifiedCronString = rawCronString4.simplifyCronString();
        assertThat(simplifiedCronString.getDaysOfTheWeek()).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void shouldParseCommandOfExpressions() {
        SimplifiedCronString simplifiedCronString = rawCronString1.simplifyCronString();
        assertThat(simplifiedCronString.getCommand()).isEqualTo("/usr/bin/find");
    }
}
