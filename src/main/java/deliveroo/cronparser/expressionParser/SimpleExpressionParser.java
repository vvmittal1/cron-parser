package deliveroo.cronparser.expressionParser;


import deliveroo.cronparser.TimeUnit;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

//Comma seperated expression
// 3,5,15,20
public class SimpleExpressionParser implements ExpressionParser {

    private final String SEPERATOR = ",";
    private RangeExpressionParser rangeSubExpParser = new RangeExpressionParser();

    @Override
    // 1 or 1,2 or 1,4-5
    public Boolean isApplicable(String expression) {
        String[] split = expression.split(SEPERATOR);
        return Arrays.stream(split)
                .allMatch(
                        str -> StringUtils.isNumeric(str) || (split.length==2 && rangeSubExpParser.isApplicable(str))
                );
    }

    /**
     *
     * @param expression
     * @param timeUnit
     * @return
     * @throws Exception
     */
    @Override
    public List<Integer> toValues(String expression, TimeUnit timeUnit) throws Exception{
        List<Integer> values = new java.util.ArrayList<>(Arrays.stream(expression
                        .split(SEPERATOR))
                .filter(StringUtils::isNumeric)
                .map(Integer::parseInt)
                .toList());

        values.addAll(Arrays.stream(expression
                        .split(SEPERATOR))
                .filter(rangeSubExpParser::isApplicable)
                .flatMap(
                        str -> rangeSubExpParser.toValues(str, timeUnit).stream()
                )
                .toList());

        if (timeUnit.validate(values))
            return values;
        else
            throw new Exception("Specified expression does not lie in the bounds of the timeunit");
    }
}
