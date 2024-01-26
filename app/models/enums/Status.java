package models.enums;

public enum Status {
    ACTIVE,
    DELETED;

    public static Status stringtoStatus(String status){
        switch (status) {
            case "ACTIVE":
                return ACTIVE;
            case "DELETED":
                return DELETED;
            default:
                throw new IllegalArgumentException("INVALID STATUS: " + status);
        }
    }

    public static String statustoString(Status status){
        switch (status) {
            case ACTIVE:
                return "Active";
            case DELETED:
                return "Deleted";
            default:
                throw new IllegalArgumentException("INVALID STATUS");
        }
    }
}
