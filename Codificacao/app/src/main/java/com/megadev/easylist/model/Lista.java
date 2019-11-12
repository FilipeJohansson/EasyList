package com.megadev.easylist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Lista {

    @Expose
    @SerializedName("ID_LISTA") private int ID_LISTA;
    @Expose
    @SerializedName("NME_LISTA") private String NME_LISTA;
    @Expose
    @SerializedName("VLR_TOTAL") private float VLR_TOTAL;
    @Expose
    @SerializedName("ID_USUARIO") private int ID_USUARIO;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public int getID_LISTA() {
        return ID_LISTA;
    }

    public String getNME_LISTA() {
        return NME_LISTA;
    }

    public void setNME_LISTA(String NME_LISTA) {
        this.NME_LISTA = NME_LISTA;
    }

    public float getVLR_TOTAL() {
        return VLR_TOTAL;
    }

    public void setVLR_TOTAL(float VLR_TOTAL) {
        this.VLR_TOTAL = VLR_TOTAL;
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
