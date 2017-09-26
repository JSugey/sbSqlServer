/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repositorio;

import com.example.entidad.sql;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author WorkNest9
 */
public interface sqlRepositorio extends JpaRepository<sql, Long> {
    
    @Query(value="SELECT * FROM personOliver", nativeQuery=true)
    List<sql> buscarPersonas();
    
    @Query(value="SELECT * FROM person where id = ?1", nativeQuery=true)
    List<sql> buscarPersonaPorID(Long id);
    
    @Modifying
    @Query(value="INSERT INTO person(fname,lname) VALUES(:fname,:lname)", nativeQuery=true)
    @Transactional
    void agregarPersona(@Param("fname") String fname, @Param("lname") String lname);
    
    @Modifying
    @Query(value="UPDATE person SET fname=:nombre, lname=:apellido where id=:idPersona", nativeQuery=true)
    @Transactional
        void actualizarPersona(@Param("idPersona") Long idPersona,@Param("nombre") String nombre, @Param("apellido") String apellido);
    
    @Query(value="DELETE FROM person WHERE id =?1", nativeQuery = true)
    void eliminarPersona(Long id);
    
    @Procedure
    void insertPerson(String fname, String lname);
}
