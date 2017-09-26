/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.excepciones;

/**
 * Excepcion de almacenamiento
 * @author WorkNest9
 */
public class storageException extends RuntimeException{
    public storageException(String mensaje){
        super(mensaje);
    }
    
    public storageException(String mensaje, Throwable causa){
        super(mensaje, causa);
    }
    
}
