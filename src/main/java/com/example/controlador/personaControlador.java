/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controlador;

import com.example.entidad.sql;
import com.example.repositorio.sqlRepositorio;
import com.example.servicios.sqlServicio;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author WorkNest9
 */
//controlador de recursos maneja una peticion GET /prueba y devuelve un recurso
@RestController
@RequestMapping("/prueba")
public class personaControlador {
    
    private final sqlRepositorio sqlRepositorio;
    
    @Autowired
    private sqlServicio personaServicio;
    
    @Autowired
    public personaControlador(sqlRepositorio sqlRepositorio){
        this.sqlRepositorio = sqlRepositorio;
    }
    //recurso que devuelve /prueba
    @GetMapping
    public List<sql> listSql(){
        return sqlRepositorio.findAll();
    }
    //*************************GET**************************AGREGAR
    //peticion/insertar devuelve el String "Agregado"
    //RequestParam indica  un parametro esperado, el valor que espera un un valor default guardado en una variable
    @GetMapping("/insertar")
    public String insertar(@RequestParam(value="id",defaultValue="0") Long id, 
                           @RequestParam(value="fname", defaultValue="fname") String fname, 
                           @RequestParam(value="lname", defaultValue="lname") String lname){
        sqlRepositorio.save(new sql(id,fname,lname));
        return "Agregado";
    }
    
    //**************************GET***********************INSERTAR
    @RequestMapping(value="/insertarpersona", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<sql> insertarPersona(@RequestParam(value="id",defaultValue="0") Long id, 
                           @RequestParam(value="fname", defaultValue="fname") String fname, 
                           @RequestParam(value="lname", defaultValue="lname") String lname){
        sql insertarPersona = personaServicio.insertar(id, fname, lname);
        if(insertarPersona == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(insertarPersona,HttpStatus.CREATED);
    }
    
    //*****************************POST**********************AGREGAR
    @RequestMapping(value="/nuevapersona",
                    method = RequestMethod.POST,
                    consumes  = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<sql> nuevaPersona(@RequestBody sql persona){
        sql guardarPersona = personaServicio.agregar(persona);
        return new ResponseEntity<>(guardarPersona, HttpStatus.CREATED);
    }
    
    //*************************LECTURA******************** TODOS
    @RequestMapping(value="/datospersonas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<sql>> obtenerPersonas(){
        Collection<sql> personas = personaServicio.findAll();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }
    //*************************LECTURA******************** ID ESPECIFICA
    @RequestMapping(value="datospersona/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<sql> obtenerPersona(@PathVariable("id") Long id){
        sql persona = personaServicio.buscarUno(id);
        if(persona == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//no encuentra el id
        return new ResponseEntity<>(persona,HttpStatus.OK);
    }
    
    //**************************MODIFICACION****************************
    @RequestMapping(value="/modificar", method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modificarPersona(@RequestBody sql persona){
        sql actualizarPersona = personaServicio.actualizar(persona);
        if(actualizarPersona == null)//no existe la persona en la base de datos
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(actualizarPersona,HttpStatus.OK);
    }
    
    //************************ELIMINAR************************
    @RequestMapping(value="/eliminar/{id}",method = RequestMethod.DELETE)
    public ResponseEntity eliminarPersona(@PathVariable Long id){
        sql eliminarPersona = personaServicio.eliminar(id);
        if(eliminarPersona == null)//no existe la persona con ese id
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(eliminarPersona, HttpStatus.OK);
    }
    
    //************************SQL*****************************
    //********************ESCRITURA****************************    
    @RequestMapping(value="/nuevasql",
                    method = RequestMethod.POST,
                    consumes  = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public void nuevaPersonaSQL(@RequestBody sql persona){
        personaServicio.agregarSQL(persona);        
        //if(guardarPersona == null)
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //return new ResponseEntity<>(guardarPersona, HttpStatus.CREATED);
    }
    //********************LECTURA*******************************
    @RequestMapping(value="/buscartodos", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<sql>> obtenerPersonasSQL(){
        List<sql> personas = personaServicio.buscarTodoSQL();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }
    @RequestMapping(value="/buscartodos/{id}", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<sql>> obtenerPersonaSQL(@PathVariable("id") Long id){
        List<sql> persona = personaServicio.buscarUnoSQL(id);
        if(persona == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//no encuentra el id
        return new ResponseEntity<>(persona, HttpStatus.OK);
    }
    
    //****************************ACTUALIZAR**************************
    @RequestMapping(value="/modificarsql", method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modificarPersonaSQL(@RequestBody sql persona){
        //try{
            personaServicio.actualizarSQL(persona);
            return new ResponseEntity<>(HttpStatus.OK);
        //}catch(Exception e){
           // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }
    
    //************************ELIMINAR************************
    @RequestMapping(value="/eliminarsql/{id}",method = RequestMethod.DELETE)
    public ResponseEntity eliminarPersonaSQL(@PathVariable Long id){
        try{
        sql eliminarPersona = personaServicio.eliminarSQL(id);
        if(eliminarPersona == null)//no existe la persona con ese id
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(eliminarPersona, HttpStatus.OK);
        }catch(Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
        
    }
    
    //********************ESCRITURA CON PROCEDIMIENTOS ALMACENADOS****************************    
    @RequestMapping(value="/insertarprocedimiento",
                    method = RequestMethod.POST,
                    consumes  = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public void insertarper(@RequestBody sql persona){
        personaServicio.insertarProce(persona);  
    }
    
    //*****************************ARCHIVOS*****************************
    @PostMapping("/archivos")
    public String manejadorSubirArchivos(@RequestParam("archivo") MultipartFile archivo, RedirectAttributes atributos){
        personaServicio.guardar(archivo);
        atributos.addFlashAttribute("mensage", "Haz subido Exitosamente "+archivo.getOriginalFilename()+"!");
                return "redirect:/";
    }
}
