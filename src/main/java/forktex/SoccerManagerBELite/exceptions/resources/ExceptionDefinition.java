package forktex.SoccerManagerBELite.exceptions.resources;

import org.springframework.http.HttpStatus;

public enum ExceptionDefinition {
    INVALID_CREDENTIALS_EXCEPTION("Your credentials are invalid!", HttpStatus.UNAUTHORIZED),
    NULL_PRINCIPAL_EXCEPTION("Principal object is null!", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_ALREADY_EXISTS_EXCEPTION("User with email [{}] already exists!", HttpStatus.CONFLICT),
    USER_NOT_FOUND_BY_EMAIL_EXCEPTION("User with email [{}] was not found!", HttpStatus.CONFLICT),
    USER_NOT_FOUND_BY_ID_EXCEPTION("User with id [{}] was not found!", HttpStatus.CONFLICT),

    PLAYER_NOT_FOUND_BY_ID_EXCEPTION("Player with id [{}] was not found!", HttpStatus.CONFLICT),
    PLAYER_NOT_OWNED_BY_USER_EXCEPTION("Player with id [{}] is not owned by user with id [{}]!", HttpStatus.FORBIDDEN),
    PLAYER_ALREADY_ACTIVE_ON_MARKET_EXCEPTION("Player with id [{}] is already active on market!", HttpStatus.CONFLICT),
    PLAYER_NOT_ACTIVE_ON_MARKET_EXCEPTION("Player with id [{}] is not active on market!", HttpStatus.CONFLICT),

    TEAM_NOT_OWNED_BY_USER_EXCEPTION("Team with id [{}] is not owned by user with id = [{}]!", HttpStatus.FORBIDDEN),
    TEAM_NOT_FOUND_BY_ID_EXCEPTION("Team with id [{}] was not found!", HttpStatus.CONFLICT),
    TEAM_NOT_FOUND_BY_PLAYER_ID_EXCEPTION("Team with id [{}] was not found!", HttpStatus.CONFLICT),
    SAME_TEAM_TRANSFER_EXCEPTION("Team cannot buy his own player!", HttpStatus.CONFLICT),

    INSUFFICIENT_FUNDS_EXCEPTION("Team has insufficient funds [{}] to buy the player [{}]!", HttpStatus.CONFLICT),

    ;

    ExceptionDefinition(String format, HttpStatus httpStatus) {
        this.format = format;
        this.httpStatus = httpStatus;
    }

    private final String format;
    private final HttpStatus httpStatus;

    public String getFormat() {
        return format;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
