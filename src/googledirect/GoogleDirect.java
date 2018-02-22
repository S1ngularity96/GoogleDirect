
package googledirect;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Livem
 */
public class GoogleDirect extends Application{

    Stage meineStage;
    Verwaltung personenVerwaltung;
    final String  hauptfenster = "hauptfenster.fxml";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
    System.setProperty("file.encoding","UTF-8");

        
    
    launch(args);
  }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(hauptfenster));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        double xSize =screen.getWidth();
        double ySize = screen.getHeight();
        
        
        
        //Referenz von der Stage
        
        meineStage = primaryStage;
        
        //Controller refenrenz
        
        Hauptfenster hauptfenster = (Hauptfenster) loader.getController();
        hauptfenster.setClasses(new Verwaltung(), new Tour());
        
        
        //Scene setzen, Titel, Fenster zentrieren
        //Minimale Fenstergroeße
        //Anzeigen
        primaryStage.setScene(scene);
        primaryStage.setTitle("GoogleDirection - Hauptfenster");
        primaryStage.centerOnScreen();
        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(900);
        primaryStage.show();
        
        
        
    }
    
   
    
}
