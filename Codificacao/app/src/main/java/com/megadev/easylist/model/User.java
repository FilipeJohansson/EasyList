package com.megadev.easylist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("ID_USUARIO") private int ID_USUARIO;
    @Expose
    @SerializedName("UID_USUARIO") private String UID_USUARIO;
    @Expose
    @SerializedName("EMAIL") private String EMAIL;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public String getUID_USUARIO() {
        return UID_USUARIO;
    }

    public void setUID_USUARIO(String UID_USUARIO) {
        this.UID_USUARIO = UID_USUARIO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
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
