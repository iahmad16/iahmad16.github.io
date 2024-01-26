package models.enums;
public enum CarBodyType {
    SEDAN,
    SUV,
    HATCH_BACK;

    public static CarBodyType toCarBodyType(String bodyType){
        switch (bodyType.toUpperCase()) {
            case "SEDAN":
                return SEDAN;
            case "SUV":
                return SUV;
            case "HATCH_BACK" :
            case "HATCH BACK":
                return HATCH_BACK;
            default:
                throw new IllegalArgumentException("INVALID BODY TYPE");
        }
    }
}

