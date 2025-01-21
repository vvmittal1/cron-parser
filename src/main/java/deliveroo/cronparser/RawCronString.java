package deliveroo.cronparser;

import deliveroo.cronparser.expressionParser.*;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

/*
            Cron Expression format:
            Parses Cron Expressions of the following format:
                (Minute) (hour) (day of month) (month) (day of week) (command)
                * means all possible time units -> Wildcardexpression
                - a range of time units -> RangeSubExpression
                , a comma separated list of individual time units -> CommaSubExpression
                / intervals time units, the left value is the starting value and the right value is the step value -> IntervalSubExpression
            - Special operators can also get intermingled, like "*\/15" or "3,45\/15" or "10,15-30"
            - Every cron expression will have a list of subexpressions each corresponding to a unique time unit
                - all seperated by a seperator(" " in this case, but can change),
            - Keep a map of time units to its index, we'll use that to see which subexpression is for which time unit
                - If more time units get added, we'll just need to add it to the map
            -
            Tasks:
            - Parse the expression and extract the subExpressions for each time unit
            - Parse the sub expression and calculate the respective values for each time unit
            - New "operators" might be getting added, so keep that flexible and extensible
            - Display all the time units using a display class
         */
@RequiredArgsConstructor
public class RawCronString {

    private String rawcronString;
    private List<String> expressions;
    public final String EXPRESSION_SEPARATOR = " ";

    private List<ExpressionParser> expressionParsers = Arrays.asList(new SimpleExpressionParser(), new RangeExpressionParser(), new WildcardExpressionParser(), new IntervalExpressionParser());

    public RawCronString(String rawcronString) {
        this.rawcronString = rawcronString;
    }

    public List<String> extractExpressions() {
        //Add validations

        expressions = Arrays.stream(rawcronString.split(EXPRESSION_SEPARATOR)).toList();
        return expressions;
    }

    public SimplifiedCronString simplifyCronString() {
        extractExpressions();
        SimplifiedCronString simplifiedCronString =
                SimplifiedCronString.builder()
                        .minutes(parseExpression(expressions.get(TimeUnit.MINUTE.ordinal()), TimeUnit.MINUTE))
                        .hours(parseExpression(expressions.get(TimeUnit.HOUR.ordinal()), TimeUnit.HOUR))
                        .daysOfTheMonth(parseExpression(expressions.get(TimeUnit.DAY_OF_MONTH.ordinal()), TimeUnit.DAY_OF_MONTH))
                        .month(parseExpression(expressions.get(TimeUnit.MONTH.ordinal()), TimeUnit.MONTH))
                        .daysOfTheWeek(parseExpression(expressions.get(TimeUnit.DAY_OF_WEEK.ordinal()), TimeUnit.DAY_OF_WEEK))
                        .command(expressions.get(expressions.size()-1))
                        .build();

        return simplifiedCronString;
    }

    public List<Integer> parseExpression(String expression, TimeUnit timeUnit) {
        return expressionParsers.stream()
                            .filter(parser -> parser.isApplicable(expression))
                            .map(parser -> {
                                try {
                                    return parser.toValues(expression, timeUnit);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .flatMap(List::stream)
                            .toList();
    }
}
