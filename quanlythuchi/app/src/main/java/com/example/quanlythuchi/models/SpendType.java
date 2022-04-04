package com.example.quanlythuchi.models;

public class SpendType {
    private int idSpendType;
    private String nameSpendType;
    private int spendTypeIMG;
    public SpendType(int idSpendType,int spendTypeIMG, String nameSpendType) {
        this.spendTypeIMG=spendTypeIMG;
        this.idSpendType = idSpendType;
        this.nameSpendType = nameSpendType;
    }

    public int getIdSpendType() {
        return idSpendType;
    }

    public void setIdSpendType(int idSpendType) {
        this.idSpendType = idSpendType;
    }

    public String getNameSpendType() {
        return nameSpendType;
    }

    public void setNameSpendType(String nameSpendType) {
        this.nameSpendType = nameSpendType;
    }

    public int getSpendTypeIMG() {
        return spendTypeIMG;
    }

    public void setSpendTypeIMG(int collectTypeIMG) {
        this.spendTypeIMG = collectTypeIMG;
    }
}
