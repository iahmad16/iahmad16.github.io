package models.classes;

import com.fasterxml.jackson.databind.JsonNode;

public class CarModel {

    public String make;
    public String logo;
    public JsonNode classes;

    public CarModel(String make, String logo, JsonNode classes){
        this.make = make;
        this.logo = logo;
        this.classes = classes;
    }

    public JsonNode getClassModels(String className){
        return this.classes.get(className);
    }
}
