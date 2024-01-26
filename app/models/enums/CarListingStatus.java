package models.enums;

public enum CarListingStatus {
    BOOKED,
    UNDER_PROCESSING,
    FOR_SALE,
    SOLD;

    public static CarListingStatus toCarListingStatus(String listingStatus){
        switch (listingStatus.toUpperCase()){
            case "BOOKED":
                return BOOKED;
            case "UNDER_PROCESSING":
                return UNDER_PROCESSING;
            case "FOR_SALE":
                return FOR_SALE;
            case "SOLD":
                return SOLD;
            default:
                throw new IllegalArgumentException("INVALID LISTING STATUS");
        }
    }
}
