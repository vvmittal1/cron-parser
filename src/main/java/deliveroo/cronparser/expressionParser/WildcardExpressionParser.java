package deliveroo.cronparser.expressionParser;

import deliveroo.cronparser.TimeUnit;

import java.util.List;
import java.util.stream.IntStream;

public class WildcardExpressionParser implements ExpressionParser {
    @Override
    public Boolean isApplicable(String expression) {
        return expression.equals("*");
    }

    @Override
    public List<Integer> toValues(String expression, TimeUnit timeUnit) {
        return IntStream.rangeClosed(timeUnit.getLowerBound(), timeUnit.getUpperBound()).boxed().toList();
    }
}
