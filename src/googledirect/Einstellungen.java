/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;

/**
 *
 * @author Livem
 */
public class Einstellungen {
    String dateiName;
    boolean automatischLaden;
    boolean automatischSpeichern;
    String speicherOrt;
    int xFensterGroesse;
    int yFensterGroesse;
    
    public Einstellungen(){
        
    }

    public String getDateiName() {
        return dateiName;
    }

    public boolean isAutomatischLaden() {
        return automatischLaden;
    }

    public String getSpeicherOrt() {
        return speicherOrt;
    }

    public void setDateiName(String dateiName) {
        this.dateiName = dateiName;
    }
    
    

    public void setAutomatischLaden(boolean automatischLaden) {
        this.automatischLaden = automatischLaden;
    }

    public void setSpeicherOrt(String speicherOrt) {
        this.speicherOrt = speicherOrt;
    }

    public void setxFensterGroesse(int xFensterGroesse) {
        this.xFensterGroesse = xFensterGroesse;
    }

    public void setyFensterGroesse(int yFensterGroesse) {
        this.yFensterGroesse = yFensterGroesse;
    }

    public int getxFensterGroesse() {
        return xFensterGroesse;
    }

    public int getyFensterGroesse() {
        return yFensterGroesse;
    }
    
    
    
    
    
    
    
}
