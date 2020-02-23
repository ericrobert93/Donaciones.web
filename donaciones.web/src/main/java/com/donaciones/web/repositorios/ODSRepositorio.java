package com.donaciones.web.repositorios;

import com.donaciones.web.entidades.ODS;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ODSRepositorio extends JpaRepository<ODS, String>
{ 
// 
//    @Query("SELECT o FROM ODS o WHERE o.usuario.id = :id")
//    public List<ODS> Mostrar(@Param("id") String id);
    
    
}