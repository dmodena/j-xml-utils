/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmodena.main;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Doug Modena
 */
public class JXmlUtils {
    /**
     * Creates xml file from object
     * @param object Input object
     * @param outputFile Output file
     * @throws JXmlException Exception
     */
    public static void create(Object object, String outputFile) throws JXmlException {
        doXml(object, outputFile);
    }
    
    /**
     * Creates xml byte array from object
     * @param object Input object
     * @return Output byte array
     * @throws JXmlException Exception
     */
    public static byte[] create(Object object) throws JXmlException {
        return doXml(object);
    }
    
    /**
     * Reads xml from file
     * @param c Output class type
     * @param inputFile Input file
     * @return Output object
     * @throws JXmlException Exception
     */
    public static Object read(Class c, String inputFile) throws JXmlException {
        byte[] inputBytes;
        
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            inputBytes = IOUtils.toByteArray(inputStream);
        } catch (Exception ex) {
            throw new JXmlException(ex.getMessage(), ex);
        }
        
        return doXml(c, inputBytes);
    }
    
    /**
     * Reads xml from byte array
     * @param c Output class type
     * @param inputBytes Input byte array
     * @return Output object
     * @throws JXmlException Exception 
     */
    public static Object read(Class c, byte[] inputBytes) throws JXmlException {
        return doXml(c, inputBytes);
    }
    
    
    private static void doXml(Object object, String outputFile) throws JXmlException {
        try {
            File file = new File(outputFile);
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, file);            
        } catch (Exception ex) {
            throw new JXmlException(ex.getMessage(), ex);
        }
    }
    
    private static byte[] doXml(Object object) throws JXmlException {
        StringWriter sw = new StringWriter();
        
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, sw);
            
        } catch (Exception ex) {
            throw new JXmlException(ex.getMessage(), ex);
        }
        
        return sw.toString().getBytes();
    }
    
    private static Object doXml(Class c, byte[] inputBytes) throws JXmlException {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(new ByteArrayInputStream(inputBytes));
        } catch (Exception ex) {
            throw new JXmlException(ex.getMessage(), ex);
        }     
    }
}
