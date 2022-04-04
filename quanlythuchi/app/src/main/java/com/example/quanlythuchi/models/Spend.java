package com.example.quanlythuchi.models;

public class Spend {
    private int id;
    private String date;
    private int spendingAmount;
    private String username;
    private int idSpendType;
    private String note;
    private int imgSpendSrc;

    public Spend(int id, int imgSpendSrc, int spendingAmount, String date, String username, int idSpendType, String note) {
        this.id = id;
        this.imgSpendSrc=imgSpendSrc;
        this.spendingAmount = spendingAmount;
        this.date=date;
        this.username = username;
        this.idSpendType = idSpendType;
        this.note=note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpendingAmount() {
        return spendingAmount;
    }

    public void setSpendingAmount(int spendingAmount) {
        this.spendingAmount = spendingAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdSpendType() {
        return idSpendType;
    }

    public void setIdSpendType(int idSpendType) {
        this.idSpendType = idSpendType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getImgSpendSrc() {
        return imgSpendSrc;
    }

    public void setImgSpendSrc(int imgSpendSrc) {
        this.imgSpendSrc = imgSpendSrc;
    }
}
