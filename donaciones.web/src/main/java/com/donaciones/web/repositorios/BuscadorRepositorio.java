package com.donaciones.web.repositorios;

import com.donaciones.web.entidades.Buscador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BuscadorRepositorio extends JpaRepository<Buscador, String>{
    
//    @Query("SELECT c FROM ONG c WHERE c.juguete = :juguete")
//    public Buscador buscarPorJuguete (@Param("juguete")String juguete);
//    
//    @Query("SELECT c FROM ONG c WHERE c.ropa = :ropa")
//    public Buscador buscarPorRopa (@Param("ropa")String ropa);
//    
//    @Query("SELECT c FROM ONG c WHERE c.alimento = :alimento")
//    public Buscador buscarPorAlimento (@Param("alimento")String alimento);
//    
//    @Query("SELECT c FROM ONG c WHERE c.utileria = :utileria")
//    public Buscador buscarPorUtileria (@Param("utileria")String utileria);
}