package com.example.model.heart;

public class Heart implements IHeart{
    private final String id;
    private final String donorName;
    private final String bloodType;

    public Heart(String id, String donorName, String bloodType) {
        this.id = id;
        this.donorName = donorName;
        this.bloodType = bloodType;
    }

    public String getId() {
        return id;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getBloodType() {
        return bloodType;
    }

    @Override
    public String toString() {
        return "Heart{" +
                "id='" + id + '\'' +
                ", donorName='" + donorName + '\'' +
                ", bloodType='" + bloodType + '\'' +
                '}';
    }
}
