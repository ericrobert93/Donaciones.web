package com.donaciones.web.servicios;

import com.donaciones.web.entidades.Foto;
import com.donaciones.web.entidades.Usuario;
import com.donaciones.web.entidades.Zona;
import com.donaciones.web.errores.ErrorServicio;
import com.donaciones.web.repositorios.UsuarioRepositorio;
import com.donaciones.web.repositorios.ZonaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/*LOS SERVICIOS VAN A EJECUTAR LAS FUNCIONALIDADES NECESARIAS PARA QUE LA APLICACION
CUMPLA LAS PETICIONES DEL USUARIO*/

/*Esta clase va a tener todas las reglas de negocio que tienen que ver con la
gestion de los usuarios, es decir con el loggeo de usuario al sistema, con el
registro de ese usuario o modificacion de sus datos o con la activacion o
desactivacion del perfil de ese usuario*/

@Service
public class UsuarioServicio implements UserDetailsService
/*Vamos a conectar el servicio usuario a la seguridad spring para poder
autentificar usuarios mediante el servicio usuario. Para esto, el servicio de usuario
implementa una interfaz que establece spring security a través de "implements UserDetailsService".
La interfaz nos obliga a usar un metodo abstracto que es "loadUserByUsername (está más abajo)"*/
{
    //Cuando un atributo es Autowired significa que la variable la inicializa el servidor de aplicaciones
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    //Vinculamos el servicio "notificacionServicio" en el servicio de usuario(esta clase)
    @Autowired(required = true)
    private NotificacionServicio notificacionServicio; //Notificacion mail

    @Autowired
    private ZonaRepositorio zonaRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;

    /*Recibimos los datos, los verificamos, se transforman en entidad de tipo usuario, el
    repositorio almacena el objeto en la base de datos. El repositorio es el
    encargado de transformar ese objeto en una o más tablas de la base de datos*/
    
    /*TRANSACTIONAL: explicacion en FotoServicio*/
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave, String clave2, String idZona) throws ErrorServicio
    {
        System.out.println("/n/nEN METODO REGISTRAR DE USUARIO SERVICIO ANTES DE VALIDAR");
        
        Zona zona = zonaRepositorio.getOne(idZona);
        
        validar(nombre, apellido, mail, clave, clave2, zona);
    
        System.out.println("/n/nEN METODO REGISTRAR DE USUARIO SERVICIO DESPUES DE VALIDAR");
        
        Usuario usuario = new Usuario();
        //usuario.setNombre(nombre);
        //usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setZona(zona);
        
        //Seteamos el mismo encriptador de contraseñas que en la clase WebApplication
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada); //El usuario se persiste con la clave encriptada
        System.out.println("/n/nCLAVE ENCRIPTADA");
        usuario.setAlta(new Date()); //Fecha en la que se registra el usuario
        System.out.println("/n/nFECHA DE ALTA");
        /*Cuando estamos generando un usuario, se genera un id porque cuando se
        mapeo los atributos de la entidad usuario pusimos que el id del usuario
        sea generado con una estrategia de uuid*/

        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        System.out.println("/n/nFOTOOOOOOOOOOO");
        usuarioRepositorio.save(usuario); //Persistencia
        System.out.println("/n/nPERSISTENCIAAAA");
        /*Persis tencia: accion de preservar la info de un objeto de forma
        permanente(guardado), pero a su vez tambien se refiere a poder recuperar
        la info del mismo (leerlo) para que pueda ser nuevamente utilizado*/
        
        //notificacionServicio.enviar("Bienvenidos al Tinder de Mascota!", "Tinder de mascota", usuario.getMail());

    }

    /*TRANSACTIONAL: explicacion en FotoServicio*/
    @Transactional
    public void modificar(MultipartFile archivo, String id, String nombre, String apellido, String mail, String clave, String clave2, String idZona) throws ErrorServicio
    {
        Zona zona = zonaRepositorio.getOne(idZona);
        
        validar(nombre, apellido, mail, clave, clave2, zona);


        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        /*El método findById nos devuelve una clase Optional. A esa clase le
        podemos preguntar si el resultado está presente, o sea si encontró
        un usuario con ese id*/
        if(respuesta.isPresent())
        {
            /*Más arriba buscamos el usuario con el id usando el repositorio. El get() nos devuelve
        el usuario que encontró*/
            Usuario usuario = respuesta.get();
            //usuario.setNombre(nombre);
            //usuario.setApellido(apellido);
            usuario.setMail(mail);
            usuario.setZona(zona);
            
            //Seteamos el mismo encriptador de contraseñas que en la clase WebApplication
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            usuario.setClave(encriptada); //El usuario se persiste con la clave encriptada

            String idFoto = null;
            if(usuario.getFoto() != null) //Si ya tenia una foto recupera su id
            {
                idFoto = usuario.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            usuario.setFoto(foto);

            usuarioRepositorio.save(usuario); //Persistencia
            /*El id del usuario se modifica*/
        }
        else
        {
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
    }

    /*TRANSACTIONAL: explicacion en FotoServicio*/
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio
    {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent())
        {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());

            usuarioRepositorio.save(usuario); //Persistencia
        }
        else
        {
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }

    }

    /*TRANSACTIONAL: explicacion en FotoServicio*/
    @Transactional
    public void habilitar(String id) throws ErrorServicio
    {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent())
        {
            Usuario usuario = respuesta.get();
            usuario.setBaja(null); //Se le borra la fecha de baja que tenía

            usuarioRepositorio.save(usuario); //Persistencia
        }
        else
        {
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }

    }

    private void validar(String nombre, String apellido, String mail, String clave, String clave2, Zona zona) throws ErrorServicio
    {
        /*Si alguno de los datos que el usuario ingreso no es válido, se dispara
        el error de servicio y no se crea la entidad , no se setean los atributos
        y no se persiste en la base de datos*/
        if( nombre == null || nombre.isEmpty())
        {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }

        if( apellido == null || apellido.isEmpty())
        {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }

        if( mail == null || mail.isEmpty())
        {
            throw new ErrorServicio("El mail del usuario no puede ser nulo");
        }

        if( clave == null || clave.isEmpty() || clave.length() <= 6)
        {
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener más de 6 dígitos");
        }
        
        if( !clave.equals(clave2) )
        {
            throw new ErrorServicio("Las claves deben ser iguales");
        }
        
        if(zona == null)
        {
            throw new ErrorServicio("No se encontró la zona solicitada");
        }
    }

    //Método abstracto que nos obliga a usar la interfaz "UserDetailsService"
    @Override //"loadUserByUsername" Es un metodo de spring security que tenemos que sobreescribir
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException
    {
        /*Si nosotros trabajaramos con varios tipos de usuario, por ej un usuario
        admin que pudiese crear las zonas, en ese caso se puede verificar a través
        de algún atributo de usuario, el tipo de usuario que es y a partir de eso
        determina que persmios le asigna*/
        
        Usuario usuario = usuarioRepositorio.getOne(mail);//.buscarPorMail(mail);
        if(usuario != null)
        {
            //Vamos a generar la clase "garanted autority" que va a contener al listado de permisos
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            //PERMISOS
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
            
            /*Vamos a insertar en este punto una llamada para guardar el usuario
            que ya esta autenticado y meterlo en la sesion web, o sea poder usar 
            sus datos para mostrar mensajes con ese dato, etc*/
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true); 
            session.setAttribute("usuariosession", usuario);//guarda el usuario que trajo de la DB
            
            /*GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
            permisos.add(p2);
            
            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
            permisos.add(p3);*/
            
            /*Este método recibe el nombre del usuario (en nuestro caso es el mail), con
            ese mail lo busca en el repositorio (base de datos) y lo transforma en un
            usuario de spring security*/
            /*Este método se va a llamar cuando un usuario quiere autentificarse en la plataforma*/
            //User(mail, clave, listado de permisos) -> Constructor de usuario de spring security
            User user = new User(usuario.getMail(), usuario.getClave(), permisos); //Todo esto se lo pasa a Spring Security
            
            /*Hasta acá hemos creado un usuario del dominio spring security y sólo falta retornarlo*/
            return user;
        }
        else
            return null;

    }
}