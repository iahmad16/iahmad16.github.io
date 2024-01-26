package models.enums;

public enum CarGearType {

    MANUAL,
    AUTOMATIC;

    public static CarGearType toCarGearType(String gearType){
        switch (gearType.toUpperCase()) {
            case "MANUAL":
                return MANUAL;
            case "AUTOMATIC":
                return AUTOMATIC;
            default:
                throw new IllegalArgumentException("INVALID GEAR TYPE");
        }
    }

    public static String gearTypeToWordCase(CarGearType type){
        switch (type){
            case MANUAL:
                return "Manual";
            case AUTOMATIC:
                return "Automatic";
            default:
                throw new IllegalArgumentException("INVALID GEAR TYPE");
        }
    }
}
