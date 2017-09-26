/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.servicios;

import com.example.entidad.sql;
import com.example.excepciones.storageException;
import com.example.repositorio.sqlRepositorio;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author WorkNest9
 */

@Service
public class sqlServicioBean implements sqlServicio{
    
    @Autowired
    private sqlRepositorio repositorio;
    
    @Override
    public sql buscarUno(Long id){//lectura
        sql persona = repositorio.findOne(id);
        return persona;
    }
    
    @Override
    public sql agregar(sql persona){//escritura
        if(persona.getId() !=null)
            return null;//no se crea la persona con el ID especificado
        sql guardarPersona = repositorio.save(persona);
        return guardarPersona;
    }
    
    @Override
    public sql actualizar(sql persona){
        sql encuentraPersona = buscarUno(persona.getId());
        if (encuentraPersona == null) 
            return null;//no actualza a personas que no se encuentran en la base de datos
        sql actualizarPersona = repositorio.save(persona);
        return actualizarPersona;
    }

    @Override
    public Collection<sql> findAll() {
        Collection<sql> personas = repositorio.findAll();
        return personas;
    }

    @Override
    public sql eliminar(Long id) {
        sql persona = repositorio.findOne(id);
        if(id==null)
            return null;//la persona no existe
        repositorio.delete(id);
        return persona;
    }

    @Override
    public sql insertar(Long id, String fname, String lname) {
        if(id ==null)
            return null;
        sql insertarPersona = repositorio.save(new sql(id,fname,lname));
        return insertarPersona;
    }

    @Override
    public List<sql> buscarTodoSQL() {
        List<sql> personas = repositorio.buscarPersonas();
        return personas;
    }

    @Override
    public List<sql> buscarUnoSQL(Long id) {
        List<sql> personas = repositorio.buscarPersonaPorID(id);
        return personas;
    }

    @Override
    public void agregarSQL(sql persona) {        
       
        repositorio.agregarPersona(persona.getFname(), persona.getLname());
    }

    @Override
    public void actualizarSQL(sql persona) {
        repositorio.actualizarPersona(persona.getId(),persona.getFname(),persona.getLname());
    }

    @Override
    public sql eliminarSQL(Long id) {
        sql persona = repositorio.findOne(id);
        if(id==null)
            return null;//la persona no existe
        repositorio.eliminarPersona(id);
        return persona;
    }
    
    @Override
    public void insertarProce(sql persona){
        repositorio.insertPerson(persona.getFname(), persona.getLname());
    }
    //====================ARCHIVOS===========================
    
    private final Path dirRoot = Paths.get("upload-dir");
    private final File baseFolder= new File("backend" + File.separator + "rifa");
    private static final Logger LOGGER = LoggerFactory.getLogger(sqlServicioBean.class);

    @Override
    public Stream<Path> cargarTodo() {
        try {
            return Files.walk(this.dirRoot,1)
                        .filter(path -> !path.equals(this.dirRoot))
                        .map(path -> this.dirRoot.relativize(path));
        } catch (IOException e) {
            throw new storageException("Fallo al leer almacenamiento de archivos", e);
        }
    }

    @Override
    public void guardar(MultipartFile archivo) {
        String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());
        ConfigurableApplicationContext conAtx = new ClassPathXmlApplicationContext("META-INF/spring/integration/FtpOutboundChannelAdapterSample-context.xml");
        
        MessageChannel ftpChannel = conAtx.getBean("ftpChannel",MessageChannel.class);
        
        baseFolder.mkdirs();
        
        final File archivoAenviar = new File(baseFolder,nombreArchivo);
        
        try {
            FileUtils.copyInputStreamToFile(archivo.getInputStream(), archivoAenviar);
        } catch (IOException e) {
            LOGGER.info("Ya valio");
        }
        
        final Message<File> messageOrders = MessageBuilder.withPayload(archivoAenviar).build();
        ftpChannel.send(messageOrders);
        
        LOGGER.info("Tranferencia de archivo exitosa 'order.txt' y 'vendors.txt' a una locacion FTP remota");
        conAtx.close();
    }

}
