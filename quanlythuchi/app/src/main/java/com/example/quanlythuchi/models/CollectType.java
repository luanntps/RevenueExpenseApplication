package com.example.quanlythuchi.models;

public class CollectType {
    private int idCollectType;
    private String nameCollectType;
    private int collectTypeIMG;
    public CollectType(int idCollectType, int collectTypeIMG,  String nameCollectType) {
        this.idCollectType = idCollectType;
        this.nameCollectType = nameCollectType;
        this.collectTypeIMG=collectTypeIMG;
    }

    public int getIdCollectType() {
        return idCollectType;
    }

    public void setIdCollectType(int idCollectType) {
        this.idCollectType = idCollectType;
    }

    public String getNameCollectType() {
        return nameCollectType;
    }

    public void setNameCollectType(String nameCollectType) {
        this.nameCollectType = nameCollectType;
    }

    public int getCollectTypeIMG() {
        return collectTypeIMG;
    }

    public void setCollectTypeIMG(int collectTypeIMG) {
        this.collectTypeIMG = collectTypeIMG;
    }
}
