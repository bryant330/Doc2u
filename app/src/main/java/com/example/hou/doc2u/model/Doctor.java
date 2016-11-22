package com.example.hou.doc2u.model;

/**
 * Created by Hou on 11/22/2016.
 */

public class Doctor {
    private String id, name, area, speciality, currency, thumbnailUrl, schedule, description;
    private int rate, recommendation, experience;

    public Doctor() {

    }

    public Doctor(String id,String thumbnailUrl, String name, String area, String speciality, String currency, int rate ) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.area = area;
        this.speciality = speciality;
        this.currency = currency;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(int recommendation) {
        this.recommendation = recommendation;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
