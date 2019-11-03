package com.megadev.easylist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("UID_USUARIO") private String UID_USUARIO;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

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
