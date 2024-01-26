package models.enums;

public enum CarWheelDrive {
    FRONT_WHEEL_DRIVE,
    REAR_WHEEL_DRIVE,
    FOUR_WHEEL_DRIVE,
    ALL_WHEEL_DRIVE;

    public static CarWheelDrive toCarWheelDrive (String wheelDrive){
        switch (wheelDrive.toUpperCase()) {
            case "FRONT_WHEEL_DRIVE":
            case "FRONT WHEEL DRIVE":
                return FRONT_WHEEL_DRIVE;
            case "REAR_WHEEL_DRIVE":
            case "REAR WHEEL DRIVE":
                return REAR_WHEEL_DRIVE;
            case "FOUR_WHEEL_DRIVE":
            case "FOUR WHEEL DRIVE":
                return FOUR_WHEEL_DRIVE;
            case "ALL_WHEEL_DRIVE":
            case "ALL WHEEL DRIVE":
                return ALL_WHEEL_DRIVE;
            default:
                throw new IllegalArgumentException("INVALID CAR WHEEL DRIVE");
        }
    }
}
