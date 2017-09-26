/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controlador;

import com.example.entidad.producto;
import com.example.repositorio.productoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author WorkNest9
 */
@RestController
@RequestMapping("/pruebaProducto")
public class productoControlador {
    
    public productoRepositorio repositorio;
    
    @Autowired
    public productoControlador(productoRepositorio productoRepositorio){
        this.repositorio = productoRepositorio;
    }
    
    @GetMapping("/insertar")
    public String insProducto(@RequestParam(value="id", defaultValue="0") String id,
                              @RequestParam(value="nombre", defaultValue="sugey") String nombre,
                              @RequestParam(value="number", defaultValue="111") int numbers){
        repositorio.save(new producto(id,nombre,numbers));
        return "Agregado "+id+" "+nombre+" "+numbers;
    }
}
