package models.classes;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import models.enums.AccountType;
import models.enums.Status;
import static models.enums.AccountType.*;
import static models.enums.Status.*;

public class Admin {

    public String uid;
    public Long joinDate;
    public String name;
    public String emailAddress;
    public AccountType accountType;
    public String role;
    public Status status;

    public Admin(String uid, Long joinDate, String name, String emailAddress, String accountType, String role, String status){
        this.uid = uid;
        this.joinDate = joinDate;
        this.name = name;
        this.emailAddress = emailAddress;
        this.accountType = toAccountType(accountType);
        this.role = role;
        this.status = stringtoStatus(status);
    }

    public long getJoinDate(){
        return this.joinDate;
    }

    public boolean isEmployee(){
        return this.role.equals("EMPLOYEE");
    }

    public String formattedDate(){
        Instant instant = Instant.ofEpochSecond(this.joinDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(instant.atZone(ZoneId.systemDefault()));
    }

    public String statusToString(){
        return statustoString(this.status);
    }
}
