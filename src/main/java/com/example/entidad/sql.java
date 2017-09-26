/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 *
 * @author WorkNest9
 */
@Entity
@Table(name="personOliver")//nombre de la tabla de la base de datos
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery( name ="insertarPro",
                                procedureName = "insertPerson",
                                parameters={
                                    @StoredProcedureParameter(mode = ParameterMode.IN, name="fname", type=String.class),
                                    @StoredProcedureParameter(mode = ParameterMode.IN, name="lname", type=String.class)
                                })
})
//define el objeto sql(persona)
public class sql implements Serializable{

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "fname")
    private String fname;
    @Column(name = "lname")
    private String lname;  
    
    
    public sql(){}
    
    public sql(Long id, String fname, String lname){
        this.id = id;
        this.fname = fname;
        this.lname = lname; 
    }
    public sql(String fname, String lname){
        this.fname = fname;
        this.lname = lname;
    }
}
