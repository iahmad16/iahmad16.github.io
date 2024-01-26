package models.enums;

public enum CarFuelType {
    GASOLINE,
    DIESEL,
    BATTERY;

    public static CarFuelType toCarFuelType(String fuelType){
        switch (fuelType.toUpperCase()) {
            case "GASOLINE":
                return GASOLINE;
            case "DIESEL":
                return DIESEL;
            case "BATTERY":
                return BATTERY;
            default:
                throw new IllegalArgumentException("INVALID FUEL TYPE");
        }
    }
}
