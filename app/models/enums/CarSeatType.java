package models.enums;

public enum CarSeatType {
    LEATHER,
    CLOTH;

    public static CarSeatType toCarSeatType(String seatType){
        switch (seatType.toUpperCase()) {
            case "LEATHER":
                return LEATHER;
            case "CLOTH":
                return CLOTH;
            default:
                throw new IllegalArgumentException("INVALID SEAT TYPE");
        }
    }
}
