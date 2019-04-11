/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ma.macrcfx;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author maria
 */
public class Prop extends Properties{
    static final Path PROP_PATH = 
            Paths.get(System.getProperty("user.home"),
                    ".maprops",
                    //cambiarla a seconda del progetto
                    "macrcfx");
    
    static final Path PROP_FILE = 
            Paths.get(PROP_PATH.toString(),"properties.xml"); 

    public Prop() {
        if(Files.notExists(PROP_FILE)){
            try {
                Files.createDirectories(PROP_PATH);
            } catch (IOException ex) {
                Logger.getLogger(Prop.class.getName()).log(Level.SEVERE, null, ex);
                //non riesco a creare il file di proprieta...impossibile...
                Platform.exit();
            }
        }
    }
    
    //use overload
    public synchronized void loadFromXML() throws IOException, InvalidPropertiesFormatException {
        loadFromXML(new FileInputStream(PROP_FILE.toFile())); //To change body of generated methods, choose Tools | Templates.
    }
    //use overload
    public void storeToXML() throws IOException {
        storeToXML(new FileOutputStream(PROP_FILE.toFile()), "Property File", "UTF-8");
    }
}
