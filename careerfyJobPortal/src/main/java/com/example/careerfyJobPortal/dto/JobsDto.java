package com.example.careerfyJobPortal.dto;

public class JobsDto {
    private String title;

    private String description;
    private String location;
    private String type;
    private String companyName;
    private String logo;

    public JobsDto() {
    }

    public JobsDto(String title, String description, String location, String type, String companyName, String logo) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.companyName = companyName;
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {

        this.logo = logo;
    }

    @Override
    public String toString() {
        return "JobsDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", companyName='" + companyName + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}

