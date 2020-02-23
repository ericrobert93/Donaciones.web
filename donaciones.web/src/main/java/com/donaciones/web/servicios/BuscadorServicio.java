package com.donaciones.web.servicios;

import com.donaciones.web.repositorios.BuscadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BuscadorServicio 
{
    @Autowired
    private BuscadorRepositorio buscadorRepositorio;  
}