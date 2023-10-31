package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class NullPrincipalException extends ApiException {
    public NullPrincipalException() {
        super(ExceptionDefinition.NULL_PRINCIPAL_EXCEPTION);
    }
}
