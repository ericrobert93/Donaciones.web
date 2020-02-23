package com.donaciones.web.repositorios;

import com.donaciones.web.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>
//El primer parametro indica la clase entidad de la que es repositorio (en este caso Usuario)
//El segundo es el tipo de dato de la clave primaria de la clase entidad (en este caso es String)
{
//    @Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
//    //Busco todos los objetos c de tipo Usuario donde el mail de ese usuario sea igual al mail pasado por parámetros
//    public Usuario buscarPorMail(@Param("mail") String mail); //Método
//    //El "mail" entre comillas tiene que tener el mismo nombre que el mail de c.mail
}
