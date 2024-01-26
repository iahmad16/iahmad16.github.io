package models.classes;

import models.enums.AccountType;
import models.enums.Status;
import static models.enums.AccountType.*;
import static models.enums.Status.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Customer {

    public String uid;
    public Long joinDate;
    public AccountType accountType;
    public Status accountStatus;
    public String phoneNumber;
    public String emailAddress;
    public String firstName;
    public String lastName;
    public List<String> carListingsIDs;

    public Customer(String uid, Long joinDate, String accountType, String accountStatus, String phoneNumber, String emailAddress, String firstName, String lastName, List<String> carListingsIDs){
        this.uid = uid;
        this.joinDate = joinDate;
        this.accountType = toAccountType(accountType);
        this.accountStatus = stringtoStatus(accountStatus);
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.carListingsIDs = carListingsIDs;
    }

    public String fullName(){
        return this.firstName + " " + this.lastName;
    }

    public String formattedDate(){
        Instant instant = Instant.ofEpochSecond(this.joinDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(instant.atZone(ZoneId.systemDefault()));
    }
}
