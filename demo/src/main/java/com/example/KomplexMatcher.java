package com.example;

public class KomplexMatcher implements IMatcher {
    public boolean match(Patient patient, Heart heart){
        String pType = patient.getBloodType();
        String hType = heart.getBloodType();
        if (pType == null || hType == null) return false;

        // Normalize (e.g. "A+", "O-")
        pType = pType.trim().toUpperCase();
        hType = hType.trim().toUpperCase();

        // Fast path: exact match
        if (pType.equals(hType)) return true;

        // Parse ABO and Rh
        char pRh = pType.charAt(pType.length()-1);
        char hRh = hType.charAt(hType.length()-1);
        String pAbo = pType.substring(0, pType.length()-1);
        String hAbo = hType.substring(0, hType.length()-1);

        // Rh compatibility: recipient '+' accepts both, recipient '-' accepts only '-'
        if (pRh == '-' && hRh == '+') return false;

        // ABO compatibility rules (donor -> recipient)
        // Recipient A: donors A, O
        // Recipient B: donors B, O
        // Recipient AB: donors A,B,AB,O (universal recipient)
        // Recipient O: donors O only

        switch (pAbo) {
            case "A":
                return hAbo.equals("A") || hAbo.equals("O");
            case "B":
                return hAbo.equals("B") || hAbo.equals("O");
            case "AB":
                return hAbo.equals("A") || hAbo.equals("B") || hAbo.equals("AB") || hAbo.equals("O");
            case "O":
                return hAbo.equals("O");
            default:
                return false;
        }
    }
}
