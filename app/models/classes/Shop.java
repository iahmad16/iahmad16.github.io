package models.classes;

import models.enums.*;
import java.util.List;

public class Shop {

    public String uid;
    public Long joinDate;
    public Status status;
    public AccountType accountType;
    public String emailAddress;
    public String name;
    public String phoneNumber;
    public String address;
    public String logo;
    public Location location;
    public List<SocialMediaAccount> socialMediaAccounts;
    public List<DiscountedService> discountedServices;


    public Shop(String uid, Long joinDate, Status status, AccountType accountType, String emailAddress, String name, String phoneNumber, String address, String logo, Location location, List<SocialMediaAccount> socialMediaAccounts, List<DiscountedService> discountedServices){
        this.uid = uid;
        this.joinDate = joinDate;
        this.status = status;
        this.accountType = accountType;
        this.emailAddress = emailAddress;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.logo = logo;
        this.location = location;
        this.socialMediaAccounts = socialMediaAccounts;
        this.discountedServices = discountedServices;
    }
}
