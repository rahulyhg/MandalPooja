package com.spps.mandal.Model;

public class YaatraItems {

    public String mandalName;
    public String guruswamiName;
    public String dateOfMandalPooja;
    public String dateOfMandalYaatra;
    public String dateOfMandalDarshan;


    public YaatraItems() {
    }

    public YaatraItems(String mandalName,String guruswamiName, String dateOfMandalPooja  ,String dateOfMandalYaatra ,String dateOfMandalDarshan) {

        this.mandalName = mandalName;
        this.dateOfMandalPooja = dateOfMandalPooja;
        this.guruswamiName = guruswamiName;
        this.dateOfMandalYaatra = dateOfMandalYaatra;
        this.dateOfMandalDarshan = dateOfMandalDarshan;
    }



    public String getmandalName() {
        return mandalName;
    }

    public void setmandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getguruswamiNames() {
        return guruswamiName;
    }

    public void setguruswamiName(String guruswamiName) {
        this.guruswamiName = guruswamiName;
    }

    public String getdateOfMandalPooja() {
        return dateOfMandalPooja;
    }

    public void setdateOfMandalPooja(String dateOfMandalPooja) {
        this.dateOfMandalPooja = dateOfMandalPooja;
    }

    public String getdateOfMandalYaatra() {
        return dateOfMandalYaatra;
    }
    public void setdateOfMandalYaatra(String dateOfMandalYaatra) {
        this.dateOfMandalYaatra = dateOfMandalYaatra;
    }

    public String getdateOfMandalDarshan() {
        return dateOfMandalDarshan;
    }
    public void setdateOfMandalDarshan(String dateOfMandalDarshan) {
        this.dateOfMandalDarshan = dateOfMandalDarshan;
    }
}
