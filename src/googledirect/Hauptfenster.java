/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Livem
 */
public class Hauptfenster implements  Initializable{

    /**
     * Wichtige Buttons im Fenster
     * 
     */
    
   
    private File speicherOrtTour;
    private File speichertOrtFavoriten;
    private boolean filtermodus;
    private Verwaltung verwaltung;
    private Tour tour;
    private final String personHinzufuegenFenster = "personHinzufuegenLayout.fxml";
    
    @FXML ListView<String> stationenPersonenListe;
    @FXML ListView<String> tourListe;
    @FXML TextArea ausgabeTextArea;
    @FXML TextField suchfensterPersonStation;
    @FXML ProgressBar progressBar;
    
    
    //Hauptfunktionen des Programms
    
    
    //Betreffend zur Verwaltung von Stationen / Personen
    
    /**
     * Eine Person wird in die Verwaltung hinzugefügt
     */
    @FXML public void personStationHinzufuegenAction(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(personHinzufuegenFenster));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PersonHinzufuegen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        PersonHinzufuegen ctrl = loader.getController();
        ctrl.setVerwaltung(verwaltung);
        ctrl.übergebeStationenListe(stationenPersonenListe);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Person hinzufügen");
        stage.show();
    }
    
    
    /**
     * Die Person wird bearbeitet
     */
        public void personBearbeiten(){
            FXMLLoader loader = new FXMLLoader(getClass().getResource(personHinzufuegenFenster));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PersonHinzufuegen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        PersonHinzufuegen ctrl = loader.getController();
        ctrl.setVerwaltung(verwaltung);
        ctrl.übergebeStationenListe(stationenPersonenListe);
        ctrl.stationBearbeitenKonfiguration(stationenPersonenListe.getSelectionModel().getSelectedIndex());
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Person hinzufügen");
        stage.show();
        }
    
    /**
     * Die Stationenliste wird nach Personen/Stationen gefiltert
     */
    @FXML public void sucheNachPersonAction(){
        if(!suchfensterPersonStation.getText().isEmpty()){
            filtermodus = true;
            verwaltung.suchePerson(suchfensterPersonStation.getText());
            stationenPersonenListe.setItems(FXCollections.observableArrayList(verwaltung.namensListeSuche));
        }else{
            stationenPersonenListe.setItems(FXCollections.observableArrayList(verwaltung.namensListe));
            filtermodus = false;
        }
        
    }
    
    //Betreffend zur Erstellung von Touren
    
    
    @FXML public void tourListeAction(){
        tourListe.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                 {
                if(event.isControlDown()){
                    if(event.getCode().equals(KeyCode.DOWN)){
                        tour.moveDown(tourListe.getSelectionModel().getSelectedIndex());
                        tourListe.setItems(FXCollections.observableArrayList(tour.namensListe));
                    }
                    
                    if(event.getCode().equals(KeyCode.UP)){
                        tour.moveUp(tourListe.getSelectionModel().getSelectedIndex());
                        tourListe.setItems(FXCollections.observableArrayList(tour.namensListe));
                    }
                }
             }
                
                
            }
        });
    }
    
    /**
     * Die Liste mit der Tour wird geleert.
     */
    @FXML public void tourLeeren(){
         tour.clearList();
         tourListe.setItems(FXCollections.observableArrayList(tour.namensListe));
         
    }
    
    /**
     * Die ausgewählte Person in der Tour kann gelöscht werden.
     */
    @FXML public void stationAusTourEntfernen(){
        if(tourListe.getSelectionModel().getSelectedIndex() != -1){
            tour.deletePerson(tourListe.getSelectionModel().getSelectedIndex());
            tourListe.setItems(FXCollections.observableArrayList(tour.namensListe));
            
        }
    }
    
    /**
     * Stationen oder Personen werden in die Tourenliste übertragen.
     */
    @FXML public void stationInTourUebertragen(){
       
        
        if(stationenPersonenListe.getSelectionModel().getSelectedIndex() != -1){
            if(filtermodus){
                tour.addPerson(verwaltung.getFromFilteredList(stationenPersonenListe.getSelectionModel().getSelectedIndex()));
                tourListe.setItems(FXCollections.observableArrayList(tour.namensListe));
            }else{
                tour.addPerson(verwaltung.getPerson(stationenPersonenListe.getSelectionModel().getSelectedIndex()));
                tourListe.setItems(FXCollections.observableArrayList(tour.namensListe));
            }
        }
    }
    
    //Betreffend der Ausgabe 
    
    /**
     * Das Ausgabefenster mit Ergebnisen wird geleert.
     */
    @FXML public void ausgabeLeerenAction(){
        ausgabeTextArea.setText("");
    }
    
    /**
     * Das Ergebnis von einer Tour kann als Textdatei gespeichert werden.
     */
    @FXML public void inTXTspeichernAction(){
        //Muss noch implementiert werden
    }
    
    //<Menü vom Hauptfenster>> 
    //<Erstes Menü Datei>
    
    /**
     * Eine Stationen/Personen - Liste kann aus einer Datei geladen werden.
     */
    @FXML public void datenLadenAction(){
        File datei = null;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Favoriten laden");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Meine Datenbank", "*.db"));
        
        if(speichertOrtFavoriten != null){
            fileChooser.setInitialDirectory(speichertOrtFavoriten);
        }
        
        datei = fileChooser.showOpenDialog((Stage)stationenPersonenListe.getScene().getWindow());
        
        
        if(datei !=null){
            speichertOrtFavoriten = datei.getParentFile();
            
            try{
                verwaltung.openDb(datei.toString());
            }catch(IOException e){
                //Datei konnte nicht geladen werden
            }
            
            stationenPersonenListe.setItems(FXCollections.observableArrayList(verwaltung.namensListe));
        }
        
        
        
        
        
    }
    
    /**
     * Eine Stationen/Personen - Liste kann in einer Datei exportiert werden.
     */
    @FXML public void datenSpeichernAction(){
        File datei  = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Favoriten speichern");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Meine Datenbank", "*.db"));
       
        if(speichertOrtFavoriten != null){
            fileChooser.setInitialDirectory(speichertOrtFavoriten);
        }
        
        datei = fileChooser.showSaveDialog(stationenPersonenListe.getScene().getWindow());
        
        if(datei !=null){
            speichertOrtFavoriten =  datei.getParentFile();
            verwaltung.saveDb(datei.toString());
        }
        
    }
    
    //<Zweites Menü Tour>
    
    /**
     * Eine gespeicherte Tour kann aus einer Datei geladen werden.
     */
    @FXML public void tourAusDateiLadenAction(){
        File datei = null;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Tour laden");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tour", "*.tr"));
        
        if(speicherOrtTour != null){
            fileChooser.setInitialDirectory(speicherOrtTour);
        }
        
        datei = fileChooser.showOpenDialog((Stage)stationenPersonenListe.getScene().getWindow());
        
        
        if(datei !=null){
            speicherOrtTour = datei.getParentFile();
            tour.ladeTour(datei.toString());
            tourListe.setItems(FXCollections.observableArrayList(tour.namensListe));
        }
    }
    
    /**
     * Eine Tour kann in einer Datei gespeichert werden.
     */
    @FXML public void tourInDateiSpeichernAction(){
        File datei  = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Tour speichern");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tour", "*.tr"));
        
        if(speicherOrtTour != null){
            fileChooser.setInitialDirectory(speicherOrtTour);
        }
        
        datei = fileChooser.showSaveDialog(stationenPersonenListe.getScene().getWindow());
        
        if(datei !=null){
            
            speicherOrtTour = datei.getParentFile();
            System.out.println(speicherOrtTour.toString());
            tour.speichereTour(datei.toString());
        }
    }
    
    /**
     * Greift auf die Tour zu, rechnet die Strecke aus und gibt 
     * das Ergebnis im Ausgabefenster aus
     */
    @FXML public void tourAussrechnenAction(){
        
        
        ausgabeTextArea.setText(tour.calcTour());
        ausgabeTextArea.setScrollTop(Double.MAX_VALUE);
        
       
        
      
    }
    
    
    /**
     * Zeigt die Metadaten von einer Person an.
     * @param person Personobject
     * @param info ContextMenu
     * @param x x-Mausposition
     * @param y y-Mausposition
     */
    public void zeigeMetaDaten(Person person,ContextMenu info,double x, double y){
        String titel = "Station: "+person.getVorname() + " " + person.getNachname();
        String land = "Land: " +person.getLand();
        String stadt = "Stadt: " +person.getStadt();
        String strasse = "Straße: "+person.getStraße();
        String hausnummer = "Hausnummer: "+String.valueOf(person.getStraßennummer());
        
        MenuItem titelItem = new MenuItem(titel);
        MenuItem landItem = new MenuItem(land);
        MenuItem stadtItem = new MenuItem(stadt);
        MenuItem strasseItem = new MenuItem(strasse);
        MenuItem hausnummerItem = new MenuItem(hausnummer);

        
        
        
        
        info.getItems().clear();
        info.getItems().addAll(titelItem,landItem,stadtItem,strasseItem,hausnummerItem);
        
        info.show(stationenPersonenListe,x,y);
        
        
        
        
    }
    
    /**
     * Option zum Importieren von KlickTel Favoriten
     */
    
    @FXML public void klickTelImportieren(){
        File datei = null;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("KlickTel Favoriten laden");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Favoriten JSON", "*.json"));
        
        
        
        datei = fileChooser.showOpenDialog((Stage)stationenPersonenListe.getScene().getWindow());
        
        
        if(datei !=null){
            verwaltung.clearDb();
            verwaltung.klickTelImportieren(datei.getAbsolutePath());
            stationenPersonenListe.setItems(FXCollections.observableArrayList(verwaltung.namensListe));
        }
    }
    
    
    
    /**
     * Übergibt die Referenzen von Verwaltung und Tour
     * @param verwaltung 
     * @param tour 
     */
    public void setClasses(Verwaltung verwaltung,Tour tour){
        this.verwaltung = verwaltung;
        this.tour = tour;
        
        stationenPersonenListe.setItems(FXCollections.observableArrayList(verwaltung.namensListe));
        
        
    }
    
    
    
  
    /**
     * Beim Start des Hauptfensters werden einige EventHandler definiert
     * und außerdem weitere Einstellungen
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Create ContextMenu
        ContextMenu context = new ContextMenu();
        ContextMenu infoBox = new ContextMenu();
        
        
       
        
        //Create MenuItems
        MenuItem metaDatenAnzeigen = new MenuItem("Anzeigen");
        MenuItem stationBearbeiten = new MenuItem("Bearbeiten");
        MenuItem deleteFromList = new MenuItem("Löschen");   
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem abbrechen = new MenuItem("Abbrechen");
                   
        //Add MenuItem to ContextMenu
        context.getItems().addAll(metaDatenAnzeigen,stationBearbeiten,deleteFromList,separator,abbrechen);
        
        
        //Für den Linksklick in der Liste
        //Kontextmenü für die Stationen/Personen
        stationenPersonenListe.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
            //In diesem Teil wird der Index des Elements aus der Liste gespeichert
           int selectedElement = stationenPersonenListe.getSelectionModel().getSelectedIndex();
            
            
               
               if(MouseButton.SECONDARY.equals(event.getButton())){
                  
                  stationenPersonenListe.setOnContextMenuRequested((ContextMenuEvent event1) -> {   
                    
                     
                      if(!verwaltung.daten.isEmpty() && !filtermodus){
                     context.show(stationenPersonenListe,event1.getScreenX(),event1.getScreenY());
                      }
                  });
                 }
           
            //Event für Löschen aus der Liste.
            deleteFromList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                verwaltung.personLöschen(selectedElement);
                stationenPersonenListe.setItems(FXCollections.observableArrayList(verwaltung.namensListe));
            }
            });
            
            
            //Zum Anzeigen der Metadaten
            metaDatenAnzeigen.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent t) {
                   
                   context.hide();
                   zeigeMetaDaten(verwaltung.getPerson(stationenPersonenListe.getSelectionModel().getSelectedIndex()),
                           infoBox,event.getScreenX(),event.getScreenY());
                   
               }
           });
            
			//Zum Bearbeiten von einer Station
            stationBearbeiten.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent t) {
                   personBearbeiten();
               }
           });
            
            
            //Klick man neben das KontextMenü, dann wird dieses ausgeblendet
            if(MouseButton.PRIMARY.equals(event.getButton())){
                infoBox.hide();
                context.hide();
            }
            
            
            
        });
        
        
        
        //---------------------------------------------------------------------
        //Für die Liste der Touren --> Kommt noch :) 
        
        
        
        
        
        
        
        
        
        
        
        
    }
    
}
