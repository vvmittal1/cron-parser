package deliveroo.cronparser.expressionParser;

import deliveroo.cronparser.TimeUnit;
import deliveroo.cronparser.exception.ExpressionNotApplicableException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RangeExpressionParser implements ExpressionParser {

    private final String SEPERATOR = "-";
    @Override
    public Boolean isApplicable(String expression) {
        String[] expressionSplit = expression.split(SEPERATOR);
        return expressionSplit.length==2 && Arrays.stream(expressionSplit).allMatch(StringUtils::isNumeric);
    }

    @Override
    public List<Integer> toValues(String expression, TimeUnit timeUnit) {
        List<Integer> range;
        range = Arrays.stream(expression.split(SEPERATOR)).map(Integer::parseInt).toList();
        return IntStream
                .rangeClosed(range.get(0), range.get(1))
                .boxed()
                .toList();
    }

    public Integer getStartValue(String expression, TimeUnit timeUnit) throws ExpressionNotApplicableException {
        if (!this.isApplicable(expression))
            throw new ExpressionNotApplicableException(expression);

        return Integer.parseInt(expression.split(SEPERATOR)[0]);
    }

    public Integer getEndValue(String expression, TimeUnit timeUnit) throws ExpressionNotApplicableException {
        if (!this.isApplicable(expression))
            throw new ExpressionNotApplicableException(expression);

        return Integer.parseInt(expression.split(SEPERATOR)[1]);
    }
}
