package com.megadev.easylist;

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
    @SerializedName("UID_USUARIO") private String UID_USUARIO;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;


    public int getID_LISTA() {
        return ID_LISTA;
    }

    public void setID_LISTA(int ID_LISTA) {
        this.ID_LISTA = ID_LISTA;
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

    public String getUID_USUARIO() {
        return UID_USUARIO;
    }

    public void setUID_USUARIO(String UID_USUARIO) {
        this.UID_USUARIO = UID_USUARIO;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
