package com.donaciones.web.repositorios;


import com.donaciones.web.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String>
{
    
}
