package com.megadev.easylist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @Expose
    @SerializedName("ID_ITEM") private int ID_ITEM;
    @Expose
    @SerializedName("STA_CHECK") private int STA_CHECK;
    @Expose
    @SerializedName("QUANTIDADE") private float QUANTIDADE;
    @Expose
    @SerializedName("VLR_UNITARIO") private float VLR_UNITARIO;
    @Expose
    @SerializedName("VLR_TOTAL") private float VLR_TOTAL;
    @Expose
    @SerializedName("ID_LISTA") private int ID_LISTA;
    @Expose
    @SerializedName("NME_PRODUTO") private String NME_PRODUTO;
    @Expose
    @SerializedName("DSC_PRODUTO") private String DSC_PRODUTO;
    @Expose
    @SerializedName("UN_MEDIDA") private String UN_MEDIDA;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public int getID_ITEM() {
        return ID_ITEM;
    }

    public void setID_ITEM(int ID_ITEM) {
        this.ID_ITEM = ID_ITEM;
    }

    public int getSTA_CHECK() {
        return STA_CHECK;
    }

    public void setSTA_CHECK(int STA_CHECK) {
        this.STA_CHECK = STA_CHECK;
    }

    public float getQUANTIDADE() {
        return QUANTIDADE;
    }

    public void setQUANTIDADE(float QUANTIDADE) {
        this.QUANTIDADE = QUANTIDADE;
    }

    public float getVLR_UNITARIO() {
        return VLR_UNITARIO;
    }

    public void setVLR_UNITARIO(float VLR_UNITARIO) {
        this.VLR_UNITARIO = VLR_UNITARIO;
    }

    public float getVLR_TOTAL() {
        return VLR_TOTAL;
    }

    public void setVLR_TOTAL(float VLR_TOTAL) {
        this.VLR_TOTAL = VLR_TOTAL;
    }

    public int getID_LISTA() {
        return ID_LISTA;
    }

    public void setID_LISTA(int ID_LISTA) {
        this.ID_LISTA = ID_LISTA;
    }

    public String getNME_PRODUTO() {
        return NME_PRODUTO;
    }

    public void setNME_PRODUTO(String NME_PRODUTO) {
        this.NME_PRODUTO = NME_PRODUTO;
    }

    public String getDSC_PRODUTO() {
        return DSC_PRODUTO;
    }

    public void setDSC_PRODUTO(String DSC_PRODUTO) {
        this.DSC_PRODUTO = DSC_PRODUTO;
    }

    public String getUN_MEDIDA() {
        return UN_MEDIDA;
    }

    public void setUN_MEDIDA(String UN_MEDIDA) {
        this.UN_MEDIDA = UN_MEDIDA;
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
