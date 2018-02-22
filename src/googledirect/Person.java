/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Livem
 */
public class Person implements Serializable{
    
    private String vorname;
    private String nachname;
    private String Land;
    private String Stadt;
    private String Straße;
    private int Straßennummer;

    private Standort ort;
    
    /**
     * Ein Objekt mit Personendaten
     * @param vorname
     * @param nachname
     * @param Land
     * @param Stadt
     * @param Straße
     * @param Straßennummer 
     */
    public Person(String vorname, String nachname, String Land, String Stadt, String Straße, int Straßennummer) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.Land = Land;
        this.Stadt = Stadt;
        this.Straße = Straße;
        this.Straßennummer = Straßennummer;
        
        ort = new Standort(Land, Stadt, Straße, Straßennummer);
        
        
    }
    
    public void bearbeitePerson(String vorname, String nachname, String Land, String Stadt, String Straße, int Straßennummer){
        this.vorname = vorname;
        this.nachname = nachname;
        this.Land = Land;
        this.Stadt = Stadt;
        this.Straße = Straße;
        this.Straßennummer = Straßennummer;
        
        aktualisiereStandort();
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getLand() {
        return Land;
    }

    public String getStadt() {
        return Stadt;
    }

    public String getStraße() {
        return Straße;
    }

    public int getStraßennummer() {
        return Straßennummer;
    }

    @Override
    public String toString() {
        return "Person{" + "vorname=" + vorname + ", nachname=" + nachname + ", Land=" + Land + ", Stadt=" + Stadt + ", Strasse=" + Straße + ", Strassennummer=" + Straßennummer + '}';
    }
    
    
    public Standort getStandort(){
        
        return ort;
    }
    
    public void aktualisiereStandort(){
        ort.update(this.Land, this.Stadt, this.Straße, this.Straßennummer);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.vorname);
        hash = 83 * hash + Objects.hashCode(this.nachname);
        hash = 83 * hash + Objects.hashCode(this.Land);
        hash = 83 * hash + Objects.hashCode(this.Stadt);
        hash = 83 * hash + Objects.hashCode(this.Straße);
        hash = 83 * hash + this.Straßennummer;
        hash = 83 * hash + Objects.hashCode(this.ort);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.Straßennummer != other.Straßennummer) {
            return false;
        }
        if (!Objects.equals(this.vorname, other.vorname)) {
            return false;
        }
        if (!Objects.equals(this.nachname, other.nachname)) {
            return false;
        }
        if (!Objects.equals(this.Land, other.Land)) {
            return false;
        }
        if (!Objects.equals(this.Stadt, other.Stadt)) {
            return false;
        }
        if (!Objects.equals(this.Straße, other.Straße)) {
            return false;
        }
        if (!Objects.equals(this.ort, other.ort)) {
            return false;
        }
        
        if(hashCode() != obj.hashCode()){
            return false;
        }
        return true;
    }
    
    
    
    
}

