package models.classes;

public class DiscountedService {

    public String serviceName;
    public Double discountPercent;

    public DiscountedService(String serviceName, Double discountPercent) {
        this.serviceName = serviceName;
        this.discountPercent = discountPercent;
    }

}
