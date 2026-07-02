package com.example.matcher;

import com.example.model.heart.Heart;
import com.example.model.patient.Patient;

public class KomplexMatcher implements IMatcher {
    @Override
    public boolean match(Patient patient, Heart heart) {
        BloodType recipientBloodType = parseBloodType(patient.getBloodType());
        BloodType donorBloodType = parseBloodType(heart.getBloodType());

        if (recipientBloodType == null || donorBloodType == null) {
            return false;
        }

        if (!isRhCompatible(recipientBloodType.rhFactor(), donorBloodType.rhFactor())) {
            return false;
        }

        return isAboCompatible(recipientBloodType.aboGroup(), donorBloodType.aboGroup());
    }

    private BloodType parseBloodType(String rawBloodType) {
        if (rawBloodType == null) {
            return null;
        }

        String normalizedBloodType = rawBloodType.trim().toUpperCase();
        if (normalizedBloodType.isBlank() || !normalizedBloodType.matches("^(AB|A|B|O)[+-]$")) {
            return null;
        }

        String aboGroup = normalizedBloodType.substring(0, normalizedBloodType.length() - 1);
        char rhFactor = normalizedBloodType.charAt(normalizedBloodType.length() - 1);
        return new BloodType(aboGroup, rhFactor);
    }

    private boolean isRhCompatible(char recipientRhFactor, char donorRhFactor) {
        return recipientRhFactor == '+' || donorRhFactor == '-';
    }

    private boolean isAboCompatible(String recipientAboGroup, String donorAboGroup) {
        return switch (recipientAboGroup) {
            case "A" -> donorAboGroup.equals("A") || donorAboGroup.equals("O");
            case "B" -> donorAboGroup.equals("B") || donorAboGroup.equals("O");
            case "AB" -> true;
            case "O" -> donorAboGroup.equals("O");
            default -> false;
        };
    }

    private record BloodType(String aboGroup, char rhFactor) {
    }
}
