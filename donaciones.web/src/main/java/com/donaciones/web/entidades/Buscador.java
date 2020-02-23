package com.donaciones.web.entidades;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Buscador 
{
    //Atributos
    @Id
    private String zona;
    private String ods;
    private String donacion;
    private String nombreONG;

    /**
     * @return the zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * @param zona the zona to set
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * @return the ods
     */
    public String getOds() {
        return ods;
    }

    /**
     * @param ods the ods to set
     */
    public void setOds(String ods) {
        this.ods = ods;
    }

    /**
     * @return the donacion
     */
    public String getDonacion() {
        return donacion;
    }

    /**
     * @param donacion the donacion to set
     */
    public void setDonacion(String donacion) {
        this.donacion = donacion;
    }

    /**
     * @return the nombreONG
     */
    public String getNombreONG() {
        return nombreONG;
    }

    /**
     * @param nombreONG the nombreONG to set
     */
    public void setNombreONG(String nombreONG) {
        this.nombreONG = nombreONG;
    }

}