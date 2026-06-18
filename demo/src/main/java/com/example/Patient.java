package com.example;

public class Patient implements IPatient{
    private final String id;
    private final String name;
    private final String bloodType;

    public Patient(String id, String name, String bloodType) {
        this.id = id;
        this.name = name;
        this.bloodType = bloodType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBloodType() {
        return bloodType;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bloodType='" + bloodType + '\'' +
                '}';
    }
}
