/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Livem
 */
public class Verwaltung implements Serializable{
    ArrayList<String> namensListe;
    ArrayList<String> namensListeSuche;
    ArrayList<Person> daten;
    ArrayList<Person> datenSuche;
    Einstellungen einstellungen;
    
    
    public Verwaltung(){
        namensListe = new ArrayList<>(); //Liste für Namen
        daten = new ArrayList<>();
        datenSuche = new ArrayList<>();
        namensListeSuche = new ArrayList<>();
        
        
       
        
        
        
        try {
            openDb("default.db");
        } catch (IOException ex) {
            saveDb("default.db");
        }
        
        
        
        
    }
    
    
    public void klickTelImportieren(String path){
        KlicktelImport klickImp = new KlicktelImport(this,path);
    }
    
    public void personEintragen(Person person){
         daten.add(person);
         if(person.getNachname().isEmpty()){
            namensListe.add(person.getVorname());
        }else{
            namensListe.add(person.getVorname() + ", "+person.getNachname());
        }
         
         saveDb("default.db");
         
    }
    
    /**
     * Die Person wird gelöscht mithilfe des Objekts
     * @param person 
     */
    public void personLöschenUeber(Person person){
        daten.indexOf(person);
        
    }
    
    /**
     * Person wird gelöscht über den Index
     * @param index 
     */
            
    public void personLöschen(int index){
        daten.remove(index);
        namensListe.remove(index);
        
        saveDb("default.db");
    }
    
    
    public void clearDb(){
        daten.clear();
        namensListe.clear();
        
        saveDb("default.db");
    }
    
    
    public void saveDb(String path){
        ObjectOutputStream dataOut = null;
        try {
            FileOutputStream out = new FileOutputStream(path);
            dataOut = new ObjectOutputStream(out);
            dataOut.writeObject(daten);
            dataOut.writeObject(namensListe);
            dataOut.close();
        } catch (IOException ex) {
            Logger.getLogger(Verwaltung.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dataOut.close();
            } catch (IOException ex) {
                Logger.getLogger(Verwaltung.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    public void openDb(String path) throws IOException{
        ObjectInputStream dataIn = null;
        try {
            FileInputStream in = new FileInputStream(path);
            dataIn = new ObjectInputStream(in);
            daten = (ArrayList<Person>) dataIn.readObject();
            namensListe = (ArrayList<String>) dataIn.readObject();
            dataIn.close();
            saveDb("default.db");
        } catch (IOException ex) {
            throw new IOException("Fehler");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Verwaltung.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dataIn.close();
            } catch (IOException ex) {
                throw new IOException("Fehler");
            } catch(NullPointerException e){
                throw new IOException("Fehler");
            }
        }
    }

    @Override
    public String toString() {
        String liste = "Inhalt der Verwaltung:";
        
        for(Person person:daten){
            liste = liste +"\n" + person.toString() ;
        }
        
        return liste;
    }
    
    
    /**
     * Die Liste mit Stationen wird mit übereinstimmenden Wörtern gefiltert.
     * @param wort 
     */
    public void suchePerson(String suche){
       namensListeSuche.clear();
       datenSuche.clear();
       
        
        
            Predicate<Person> filter = new Predicate<Person>() {
            @Override
            public boolean test(Person t) {
                return (t.getVorname() + t.getNachname()).contains(suche);
            }
            };
        
        daten.stream().filter(filter).forEach(p ->addToFilterList(p));
        
        
        
        
    }
    
    /**
     * Fügt eine Person zur Suchliste hinzu, dessen Parameter übereinstimmen.
     * @param person 
     */
    private void addToFilterList(Person person){
        datenSuche.add(person);
        
        
        if(person.getNachname().isEmpty()){
            namensListeSuche.add(person.getVorname());
        }else{
            namensListeSuche.add(person.getVorname() + ", "+person.getNachname());
        }
    }
    
    
    /**
     * Person wird aus der Suche zurück gegeben
     * @param index
     * @return 
     */
    public Person getFromFilteredList( int index){
        return datenSuche.get(index);
    }
    
    public Person getPerson(int index){
        return daten.get(index);
    }
    
}
