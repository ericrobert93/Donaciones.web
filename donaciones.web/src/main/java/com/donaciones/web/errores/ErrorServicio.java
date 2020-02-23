package com.donaciones.web.errores;

public class ErrorServicio extends Exception
{
    /*Se crea esta clase para diferencias los errores que surgen de nuestra logica
    de negocios de los que ocurren en el sistema*/
    public ErrorServicio(String msn)
    {
        super(msn);        
    }
}