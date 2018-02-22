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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author Livem
 */
public class Tour implements Serializable{
    ArrayList<Person> stationen;
    ArrayList<String> namensListe;
    
    GoogleDirect main;
    
    public Tour(){
        stationen = new ArrayList<>();
        namensListe = new ArrayList<>();
    }
    
    
    //Eine Station wird der Liste hinzugefügt
     public void addPerson(Person person){
        stationen.add(person);
        
        if(person.getNachname().isEmpty()){
            namensListe.add(person.getVorname());
        }else{
            namensListe.add(person.getVorname() + ", "+person.getNachname());
        }
        
     }
     
     //Die Liste wird geleert
     public void clearList(){
         stationen.clear();
         namensListe.clear();
     }
    
     
     private Person getPerson(int index){
         return stationen.get(index);
     } 
     
     public void deletePerson(int index){
         stationen.remove(index);
         namensListe.remove(index);
     }
     
     //Element soll eins nach Oben verschoben werden.
     public void moveUp(int draggedElement){
         if(draggedElement == -1){
             return;
         }
         
         if(draggedElement != 0){
             Person person = stationen.get(draggedElement);
             String name = namensListe.get(draggedElement);
             
             stationen.add(draggedElement-1,person);
             namensListe.add(draggedElement-1,name);
             
             stationen.remove(draggedElement+1);
             namensListe.remove(draggedElement+1);
         }
     }
     
     //Element soll um eins nach unten verschoben werden.
     public void moveDown(int draggedElement){
         if(draggedElement == -1){
             return;
         }
         
         if(draggedElement != stationen.size()-1){
             
             Person person = stationen.get(draggedElement);
             String name = namensListe.get(draggedElement);
             
             stationen.add(draggedElement+2,person);
             namensListe.add(draggedElement+2,name);
             
             stationen.remove(draggedElement);
             namensListe.remove(draggedElement);
         }
     }
     
     
    
     /**
      * Die Main Klasse wird übergeben
      * @param main 
      */
     public void setMainRef(GoogleDirect main){
         this.main = main;
     }
     
     
     /**
      * Rechnet die Strecke und die Fahrtauer aus
      * @return 
      */
     public String calcTour(){
         String ergebnis = "";
         double distanz = 0.0;
         
         
         if(stationen.size() <= 1){
             //Gib Text aus, dass die Distanz gleich 0 ist.
             ergebnis = "Sie haben eine oder keine Station ausgewählt.\n";
             ergebnis = ergebnis +"Distanz: "+ distanz +"\n\n\n";
             
             
             return ergebnis;
         }else if(stationen.size() == 2){
             Direction dir = new Direction(getPerson(0).getStandort(), getPerson(1).getStandort());
             try {
                 JsonReader reader = new JsonReader(dir.generateRequest());
                 distanz = reader.getDistance();
                 ergebnis = ergebnis + reader.toString();
             } catch (IOException ex) {
                 System.out.println(ex.toString());
                 return "Fehler: Überprüfen Sie Ihre Internetverbindung";
             } catch (JSONException ex) {
                 System.out.println(ex.toString());
                 return "Fehler: Überprüfen Sie Ihre Eingaben";
             }
             
             
            
             
             /**
              * Am Ende wird das Ergebnis ausgegeben.
              */
             
             return ergebnis;
         }else{
             
             JsonReader reader = new JsonReader();
             Direction thisDirection = new Direction();
             for(int i = 0; i < stationen.size()-1;i++){ // Iterreriere durch jede Station.
              
              thisDirection.setNewPlaces(getPerson(i).getStandort(),getPerson(i+1).getStandort());
                 try {
                     String request = thisDirection.generateRequest();
                     reader.setNewRequest(request);
                     ergebnis = ergebnis + (i+1)+". Station:\n";
                     ergebnis = ergebnis + reader.toString() + "\n\n\n";
                     distanz = distanz + reader.getDistance();
                     
                 } catch (IOException ex) {
                     System.out.println(ex.toString());
                     return "Fehler: Überprüfen Sie Ihre Internetverbindung";
                 } catch (JSONException ex) {
                     System.out.println(ex.toString());
                     return "Fehler: Überprüfen Sie Ihre Eingaben";
                 }
              
                
                 
                 
             
             
             
         }
             
             /**
              * Nach der Schleife kommt dann hier das Ergebnis rein.
              */
             ergebnis = ergebnis + "\n"+
                     "Ergebnis der Rechnung:\n"+"Distanz in Km: "+ distanz +"\n";
         
             return ergebnis;
     }
}
     
     
     /**
      * Eine Tour wird gespeichert
      * @param path 
      */
     public void speichereTour(String path){
         ObjectOutputStream dataOut = null;
        try {
            FileOutputStream out = new FileOutputStream(path);
            dataOut = new ObjectOutputStream(out);
            dataOut.writeObject(stationen);
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
     
     /**
      * Eine Tour wird geladen
      * @param path 
      */
     public void ladeTour(String path){
         ObjectInputStream dataIn = null;
        try {
            FileInputStream in = new FileInputStream(path);
            dataIn = new ObjectInputStream(in);
            stationen = (ArrayList<Person>) dataIn.readObject();
            namensListe = (ArrayList<String>) dataIn.readObject();
            dataIn.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Verwaltung.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Verwaltung.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dataIn.close();
            } catch (IOException ex) {
                Logger.getLogger(Verwaltung.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }

}


    