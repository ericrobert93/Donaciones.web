package com.donaciones.web.controladores;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErroresController implements ErrorController { //ErrorController es una clase del framework de spring

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error"); //Esta es la vista que se muestra cuando hay alguno de los errores de abajo
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) { 
            /*En cada case se reucpera el num de error que viene del servidor,
            se muestra un msj particular para cada uno y se muestra la vista
            correspondiente que se ha configurado (es el html "error.html"*/
            case 400: {
                errorMsg = "El recurso solicitado no existe.";
                break;
            }
            case 403: {
                errorMsg = "No tiene permisos para acceder al recurso.";
                break;
            }
            case 401: {
                errorMsg = "No se encuentra autorizado.";
                break;
            }
            case 404: {
                errorMsg = "El recurso solicitado no fue encontrado.";
                break;
            }
            case 500: {
                errorMsg = "Ocurrió un error interno.";
                break;
            }
        }

        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        
        Map mapa = httpRequest.getParameterMap();
        for(Object key : mapa.keySet()){
            String[] valores = (String[]) mapa.get(key);
            for(String valor : valores){
                System.out.println(key.toString() + ": " + valor);
            }
        }
        
        
        Enumeration<String> atributos = httpRequest.getAttributeNames();
        while(atributos.hasMoreElements()){
            String key = atributos.nextElement();
            System.out.println(key + ":" + httpRequest.getAttribute(key));
        }
        
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
