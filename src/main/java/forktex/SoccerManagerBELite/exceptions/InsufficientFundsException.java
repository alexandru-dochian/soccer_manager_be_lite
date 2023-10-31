package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class InsufficientFundsException extends ApiException {
    public InsufficientFundsException(Double funds, Double itemPrice) {
        super(ExceptionDefinition.INSUFFICIENT_FUNDS_EXCEPTION, funds, itemPrice);
    }
}
