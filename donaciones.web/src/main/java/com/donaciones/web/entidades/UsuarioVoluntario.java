package com.donaciones.web.entidades;

public class UsuarioVoluntario extends Usuario
{
    //Atributos
    private String nombre;
    private String apellido;
    
    //Getters&Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
}
