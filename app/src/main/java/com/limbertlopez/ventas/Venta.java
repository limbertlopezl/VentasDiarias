package com.limbertlopez.ventas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limbert on 06/10/2017.
 */

public class Venta {
    private String cod;
    private String monto;
    private String Fecha;
    public static List<Venta> VENTAS = new ArrayList<>();

    public Venta(String cod, String monto, String fecha) {
        this.cod = cod;
        this.monto = monto;
        Fecha = fecha;
    }
    //Metodos Get Objeto
    public static Venta getItem(String Cod) {
        for (Venta item : VENTAS) {
            if (item.getCod() == Cod) {
                return item;
            }
        }
        return null;
    }
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }


}
