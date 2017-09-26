/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.servicios;

import java.util.Collection;
import com.example.entidad.sql;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author WorkNest9
 */
public interface sqlServicio {
    
    Collection<sql> findAll();
    List<sql> buscarTodoSQL();
    
    sql buscarUno(Long id);    
    List<sql> buscarUnoSQL(Long id);
    
    sql agregar(sql persona);
    void agregarSQL(sql persona);
    
    sql actualizar(sql persona);
    void actualizarSQL(sql persona);
    
    sql eliminar(Long id);    
    sql eliminarSQL(Long id);
    
    sql insertar(Long id, String fname, String lname);
    
    void insertarProce(sql persona);
    
    //***********archivos
    Stream<Path> cargarTodo();
    
    void guardar(MultipartFile archivo);
}
