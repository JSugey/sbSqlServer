/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repositorio;

import com.example.entidad.producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author WorkNest9
 */
public interface productoRepositorio extends JpaRepository<producto, String> {
    
}
