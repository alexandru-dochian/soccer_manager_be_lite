package forktex.SoccerManagerBELite.exceptions;

import forktex.SoccerManagerBELite.exceptions.resources.ExceptionDefinition;

public class SameTeamTransferException extends ApiException {
    public SameTeamTransferException() {
        super(ExceptionDefinition.SAME_TEAM_TRANSFER_EXCEPTION);
    }
}
