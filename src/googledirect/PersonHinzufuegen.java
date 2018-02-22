/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Livem
 */
public class PersonHinzufuegen implements Initializable{
    
    
    
    //Für Bearbeiten
    private boolean bearbeitet;
    int bearbeitungIndex = -1;
    Person personBearbeiten = null;
    //Datenbank
    private Verwaltung verwaltung;
    //ListView in dem Hauptfenster
    private ListView<String> stationenPersonenListe;
    //TextFelder
    @FXML private TextField vornameTextField;
    @FXML private TextField nachnameTextField;
    @FXML private TextField landTextField;
    @FXML private TextField StadtTextField;
    @FXML private TextField StrasseTextField;
    @FXML private TextField HausnummerTextField;
    @FXML private Button addPersonButton;
    @FXML private Label ueberschrift;
    
    
    //Stern für Fehler
    @FXML private Label error1;
    @FXML private Label error2;
    @FXML private Label error3;
    @FXML private Label error4;
    @FXML private Label error5;
    
    
    
    @FXML public boolean alleTextFelderAusgefuelt(){
       boolean missed = false;
        
        if(vornameTextField.getText().isEmpty()){
            error1.setVisible(true);
            missed = true;
        }else{
            error1.setVisible(false);
        }
        
        
        
        if(landTextField.getText().isEmpty()){
            error3.setVisible(true);
            missed = true;
        }else{
            error3.setVisible(false);
        }
        
        if(StadtTextField.getText().isEmpty()){
            error4.setVisible(true);
            missed = true;
        }else{
            error4.setVisible(false);
            
        }
        
        if(StrasseTextField.getText().isEmpty()){
            error5.setVisible(true);
            missed = true;
        }else{
            error5.setVisible(false);
        }
        
        return missed;
    }
    
    /**
     * Eventhandling für den Button beim Hinzufügen/Speichern einer Station
     */
    @FXML public void personHinzufuegenAction(){
        int hausnummer = -1;
        
        
        
        if(!(HausnummerTextField.getText().isEmpty())){
            hausnummer = Integer.parseInt(HausnummerTextField.getText());
        }
       
       if(!alleTextFelderAusgefuelt()){
        //Zum bearbeiten von Stationen
        if(!(vornameTextField.getText().isEmpty() && nachnameTextField.getText().isEmpty() && landTextField.getText().isEmpty()
                && StadtTextField.getText().isEmpty() && StrasseTextField.getText().isEmpty())){
            
          
        if(bearbeitet){
               //Element bearbeitet Speichern
                
               personBearbeiten.bearbeitePerson(vornameTextField.getText(), nachnameTextField.getText(), 
                           landTextField.getText(), StadtTextField.getText(), StrasseTextField.getText(), hausnummer);
               namensListeAktualisieren(bearbeitungIndex);
               
               
               
           }else{
            //Zum neu-einfügen
            //Neues Element Hinzufügen
            verwaltung.personEintragen(new Person(vornameTextField.getText(), nachnameTextField.getText(),
                    landTextField.getText(), StadtTextField.getText(), StrasseTextField.getText(),hausnummer));
            
            
            
            
            
          }
        }
           
        
        
        
       }
        stationenPersonenListe.setItems(FXCollections.observableArrayList(verwaltung.namensListe));
        Stage stage = (Stage)vornameTextField.getScene().getWindow();
        stage.close();
    }
    
    /**
     * 
     * @param person 
     */
    private void fillTextFields(Person person){
        vornameTextField.setText(person.getVorname());
        nachnameTextField.setText(person.getNachname());
        landTextField.setText(person.getLand());
        StadtTextField.setText(person.getStadt());
        StrasseTextField.setText(person.getStraße());
        HausnummerTextField.setText(String.valueOf(person.getStraßennummer()));
    }
    
    private void namensListeAktualisieren(int index){
        if(personBearbeiten.getNachname().isEmpty()){
             verwaltung.namensListe.set(index,personBearbeiten.getVorname());
        }else{
            verwaltung.namensListe.set(index,personBearbeiten.getVorname() + ", "+personBearbeiten.getNachname());
            
        }
    }
    
    public void stationBearbeitenKonfiguration(int index){
        addPersonButton.setText("Speichern");
        ueberschrift.setText("Station bearbeiten");
        bearbeitet = true;
        bearbeitungIndex = index;
        personBearbeiten = verwaltung.getPerson(bearbeitungIndex);
        fillTextFields(personBearbeiten);
        
    }
    
    @FXML public void abbrechenButtonAction(){
        Stage stage = (Stage)vornameTextField.getScene().getWindow();
        stage.close();
    }
    
    public void setVerwaltung(Verwaltung verwaltung){
        this.verwaltung = verwaltung;
    }
    
    public void übergebeStationenListe(ListView<String> liste){
        this.stationenPersonenListe = liste;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
