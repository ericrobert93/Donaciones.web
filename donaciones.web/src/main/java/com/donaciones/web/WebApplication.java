package com.donaciones.web;

import com.donaciones.web.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer
{
    @Autowired
    private UsuarioServicio usuarioServicio; //Servicio de usuario que va a hacer la validacion
    
    public static void main(String[] args) throws Exception //El throws Exceptionlo copie de tinder
    {
        SpringApplication.run(WebApplication.class, args);
    }
    
    /*El metodo "configureGlobal" le dice a Spring Security cual es el servicio
    (en nuestro caso es usuarioServicio) que vamos a usar para autentificar el 
    usuario y setea un encriptador de contraseñas ("passwordEncoder(new BCryptPasswordEncoder()"
    al servicio de usuario. O sea que cada vez que se chequea una clave se va a 
    usar ese encriptador. En la clase UsuarioServicio hay que setear el mismo 
    encriptador de contraseñas*/
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        //"BCryptPasswordEncoder" es el metodo de encriptacion que va a usar Spring y usuarioServicio
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(WebApplication.class);
    }
}
