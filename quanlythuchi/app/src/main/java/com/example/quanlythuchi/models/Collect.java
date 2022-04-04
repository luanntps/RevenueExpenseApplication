package com.example.quanlythuchi.models;

public class Collect {
    private int id;
    private String date;
    private int collectAmount;
    private String username;
    private int idCollectType;
    private String note;
    private int imgCollectSrc;
    public Collect(int id,int imgCollectSrc, int collectAmount,String date, String username, int idCollectType,String note) {
        this.id = id;
        this.imgCollectSrc=imgCollectSrc;
        this.collectAmount = collectAmount;
        this.date=date;
        this.username = username;
        this.idCollectType = idCollectType;
        this.note=note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(int collectAmount) {
        this.collectAmount = collectAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdCollectType() {
        return idCollectType;
    }

    public void setIdCollectType(int idCollectType) {
        this.idCollectType = idCollectType;
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

    public int getImgCollectSrc() {
        return imgCollectSrc;
    }

    public void setImgCollectSrc(int imgCollectSrc) {
        this.imgCollectSrc = imgCollectSrc;
    }
}
