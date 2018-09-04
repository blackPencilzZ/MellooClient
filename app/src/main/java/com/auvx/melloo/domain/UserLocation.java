package com.auvx.melloo.domain;

public class UserLocation {
    private Double longitude;
    private Double latitude;
    private String countryCode;
    private String cityCode;


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", countryCode='" + countryCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }
}
