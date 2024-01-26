package models.enums;

public enum AdminRole {
    SUPER,
    EMPLOYEE;

    public static AdminRole toAdminRole(String role){
        System.out.println(role.toUpperCase());
        switch (role.toUpperCase()) {
            case "SUPER":
                return SUPER;
            case "EMPLOYEE":
                return EMPLOYEE;
            default:
                throw new IllegalArgumentException("INVALID ROLE");
        }
    }
}
