package deliveroo.cronparser.expressionParser;

import deliveroo.cronparser.TimeUnit;
import deliveroo.cronparser.exception.ExpressionNotApplicableException;

import java.util.List;

public interface ExpressionParser {

    public Boolean isApplicable(String expression);
    public List<Integer> toValues(String expression, TimeUnit timeUnit) throws Exception, ExpressionNotApplicableException;
}
