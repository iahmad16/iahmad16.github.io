package models.enums;

public enum AccountType {
    SHOP,
    CUSTOMER,
    ADMIN;

    public static AccountType toAccountType(String stringAccountType){
        switch (stringAccountType.toUpperCase()) {
            case "SHOP":
                return SHOP;
            case "CUSTOMER":
                return CUSTOMER;
            case "ADMIN":
                return ADMIN;
            default:
                throw new IllegalArgumentException("INVALID ACCOUNT TYPE: " + stringAccountType);
        }
    }
}
