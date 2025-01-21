package deliveroo.cronparser.exception;

public class ExpressionNotApplicableException extends RuntimeException {
    public ExpressionNotApplicableException(String expression) {
        super(String.format("The Expression - %s is not valid", expression));
    }
}
