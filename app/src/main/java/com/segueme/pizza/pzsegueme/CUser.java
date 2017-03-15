package com.segueme.pizza.pzsegueme;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

/**
 * Created by ELDON on 09/03/2017.
 */

public  class CUser implements Serializable{

    private String sId;
    private String sNome;
    private String sTel;
    private String sEnd;
    private String sData_Entrega;
    private String sTipo;
    private String sSabor;
    private String sPago;

//
    public CUser(){}
//


    public CUser(String sId, String sNome, String sTel, String sEnd, String sData_Entrega, String sTipo, String sSabor, String sPago) {
        this.sId = sId;
        this.sNome = sNome;
        this.sTel = sTel;
        this.sEnd = sEnd;
        this.sData_Entrega = sData_Entrega;
        this.sTipo = sTipo;
        this.sSabor = sSabor;
        this.sPago = sPago;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsPago() {
        return sPago;
    }

    public void setsPago(String sPago) {
        this.sPago = sPago;
    }

    public String getsNome() {
        return sNome;
    }

    public void setsNome(String sNome) {
        this.sNome = sNome;
    }

    public String getsTel() {
        return sTel;
    }

    public void setsTel(String sTel) {
        this.sTel = sTel;
    }

    public String getsEnd() {
        return sEnd;
    }

    public void setsEnd(String sEnd) {
        this.sEnd = sEnd;
    }

    public String getsData_Entrega() {
        return sData_Entrega;
    }

    public void setsData_Entrega(String sData_Entrega) {
        this.sData_Entrega = sData_Entrega;
    }

    public String getsTipo() {
        return sTipo;
    }

    public void setsTipo(String sTipo) {
        this.sTipo = sTipo;
    }

    public String getsSabor() {
        return sSabor;
    }

    public void setsSabor(String sSabor) {
        this.sSabor = sSabor;
    }
}