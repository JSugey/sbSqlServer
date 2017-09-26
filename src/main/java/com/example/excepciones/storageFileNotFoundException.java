/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.excepciones;

/**
 * Excepcion de almacenaiento no se encotro el archivo
 * @author WorkNest9
 */
public class storageFileNotFoundException extends storageException {
    public storageFileNotFoundException(String mensaje){
        super(mensaje);
    }
    
    public storageFileNotFoundException(String mensaje, Throwable causa){
        super(mensaje, causa);
    }
}
