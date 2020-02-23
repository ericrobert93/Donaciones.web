package com.donaciones.web;

import com.donaciones.web.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter{

	@Autowired
	public UsuarioServicio usuarioServicio; //Instancia del servicio de usuario. Se va a usar para buscar en la DB a un usuario por su nombre de usuario
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
            auth
                    .userDetailsService(usuarioServicio) /*Esto es una configuracion del manejador de seguridad (spring security) 
            al cual le decimos cual es el servicio que debe usar para autentificar 
            un usuario*/
                    .passwordEncoder(new BCryptPasswordEncoder());
            /*Se le dice cual es el encoder que va a usar para comparar las contraseñas
            . El encoder es el que encripta las contraseñas ingresadas.
            Se tiene que usar siempre el mismo encoder para poder comparar contraseñas*/
            
            
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
            System.out.println("=======================================================");
		http.headers().frameOptions().sameOrigin().and()
			.authorizeRequests()
				.antMatchers("/css/*", "/js/*", "/img/*", "/login", "/registro", "/registrar" )
                        /*Significa que los recursos que estan en esas carpetas pueden
                        ser accedidos por cualquier usuario, sin necesidad de loggearse.*/
				.permitAll()
			.and().formLogin() //Configuracion del metodo login
				.loginPage("/login") //Significa que el formulario de login va a ser retornado a nuestra plataforma cuando se acceda a la url "/login"
					.loginProcessingUrl("/logincheck") //url que utiliza un usuario para autenticar(validar) un usuario
					.usernameParameter("username") //nombre con el que viaja el nombre de usuario ingresado
					.passwordParameter("password") //idem para la contraseña
					.defaultSuccessUrl("/inicio") //Si hay exito de autentificacion se envia a esta url
					.permitAll()
				.and().logout()
					.logoutUrl("/logout") //url con la que el usuario de desloggea de la plataforma
					.logoutSuccessUrl("/") //redirige a la landing page si se desconecta con exito
					.permitAll().and().csrf().disable();
	}

}