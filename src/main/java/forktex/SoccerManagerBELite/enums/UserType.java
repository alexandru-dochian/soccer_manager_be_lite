package forktex.SoccerManagerBELite.enums;

public enum UserType {
    UNKNOWN(null);

    UserType(Long value) {
        this.value = value;
    }

    private final Long value;

    public Long getValue() {
        return value;
    }
}
