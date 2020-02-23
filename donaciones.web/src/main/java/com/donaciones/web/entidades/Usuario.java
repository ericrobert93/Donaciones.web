package com.donaciones.web.entidades;

import com.donaciones.web.enumeraciones.TipoDonacion;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario 
{
    //Atributos
    @Id
    @GeneratedValue(generator = "uuid") //Genera una cadena de texto que no se va a repetir nunca.
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    //private String nombre;
    //private String apellido;
    private String mail;
    private String clave;
    private int telefono;
    //Agregar el captcha
    
    @ManyToOne
    private Zona zona;
    
    @OneToOne
    private Foto foto;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
    
    @Enumerated(EnumType.STRING) //STRING guarda la palabra macho o hembra.
                                 //ORDINAL guarda el numero según el orden en el que esten declarados.
    private TipoDonacion tipoDonacion;
    
    @OneToOne
    private ODS ods;

    //Getters&Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Date getBaja() {
        return baja;
    }

    public void setBaja(Date baja) {
        this.baja = baja;
    }
    
    public TipoDonacion getTipoDonacion() {
        return tipoDonacion;
    }

    public void setTipoDonacion(TipoDonacion tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    public ODS getOds() {
        return ods;
    }

    public void setOds(ODS ods) {
        this.ods = ods;
    }
    
}
    