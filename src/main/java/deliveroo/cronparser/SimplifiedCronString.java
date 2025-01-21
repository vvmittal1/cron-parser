package deliveroo.cronparser;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SimplifiedCronString {
    private List<Integer> minutes;
    private List<Integer> hours;
    private List<Integer> daysOfTheMonth;
    private List<Integer> month;
    private List<Integer> daysOfTheWeek;
    private String command;

    public String toString() {

        StringBuilder simplifiedCronString = new StringBuilder("minute         ");
        for (int min: minutes)
            simplifiedCronString.append(min + " ");
        simplifiedCronString.append("\n");

        simplifiedCronString.append("hour");
        for (int h: hours)
            simplifiedCronString.append(h + " ");
        simplifiedCronString.append("\n");

        simplifiedCronString.append("day of month");
        for (int day: daysOfTheMonth)
            simplifiedCronString.append(day + " ");
        simplifiedCronString.append("\n");

        simplifiedCronString.append("month");
        for (int m: month)
            simplifiedCronString.append(m + " ");
        simplifiedCronString.append("\n");

        simplifiedCronString.append("day of the week");
        for (int day: daysOfTheWeek)
            simplifiedCronString.append(day + " ");
        simplifiedCronString.append("\n");

        simplifiedCronString.append("comamnd " + command);

        return simplifiedCronString.toString();
    }
}
