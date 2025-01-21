package deliveroo.cronparser.expressionParser;

import deliveroo.cronparser.TimeUnit;
import deliveroo.cronparser.exception.ExpressionNotApplicableException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class IntervalExpressionParser implements ExpressionParser {

    private final String SEPERATOR = "/";
    private WildcardExpressionParser wildcardExpressionParser = new WildcardExpressionParser();
    private RangeExpressionParser rangeExpressionParser = new RangeExpressionParser();

    @Override
    public Boolean isApplicable(String expression) {
        String[] expressionSplit = expression.split(SEPERATOR);
        if (expressionSplit.length == 2) {
            return isIntWildcardRangeOrIntegers(expressionSplit[0]) && StringUtils.isNumeric(expressionSplit[1]);
        }
        return false;
    }

    @Override
    public List<Integer> toValues(String expression, TimeUnit timeUnit) throws ExpressionNotApplicableException {
        List<Integer> values;

        Integer start = getStartValue(expression, timeUnit), end = getEndValue(expression, timeUnit);

        timeUnit.validate(Arrays.asList(start, end));

        String[] expressionLimits = expression.split(SEPERATOR);
        values = IntStream
                    .iterate(start, x -> x<=end, x -> x+Integer.parseInt(expressionLimits[1]))
                    .boxed()
                    .toList();
        return values;
    }

    private Integer getStartValue(String expression, TimeUnit timeUnit) throws ExpressionNotApplicableException {
        String[] expressionSplit = expression.split(SEPERATOR);

        if (wildcardExpressionParser.isApplicable(expressionSplit[0]))
            return timeUnit.getLowerBound();
        else if (rangeExpressionParser.isApplicable(expressionSplit[0]))
            return rangeExpressionParser.getStartValue(expressionSplit[0], timeUnit);

        return Integer.parseInt(expressionSplit[0]);
    }

    private Integer getEndValue(String expression, TimeUnit timeUnit) throws ExpressionNotApplicableException {
        String[] expressionSplit = expression.split(SEPERATOR);

        if (rangeExpressionParser.isApplicable(expressionSplit[0]))
            return rangeExpressionParser.getEndValue(expressionSplit[0], timeUnit);

        return timeUnit.getUpperBound();
    }

    private boolean isIntWildcardRangeOrIntegers(String value) {
        return wildcardExpressionParser.isApplicable(value) ||
                rangeExpressionParser.isApplicable(value) ||
                StringUtils.isNumeric(value);
    }
}
