/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;

import java.io.Serializable;

/**
 *
 * @author Livem
 */
public class Standort implements Serializable{
   private String Country;
   private String City;
   private String Street;
   private int StreetNr;

   
   /**
    * Ein Objekt mit den Standortdaten
    * @param Country
    * @param City
    * @param Street
    * @param StreetNr 
    */
    public Standort(String Country, String City, String Street, int StreetNr) {
        this.Country = Country;
        this.City = City;
        this.Street = Street;
        this.StreetNr = StreetNr;
    }

    public String getCountry() {
        return Country;
    }

    public String getCity() {
        return City;
    }

    public String getStreet() {
        return Street;
    }

    public int getStreetNr() {
        return StreetNr;
    }

    
    public void update(String Country, String City, String Street, int StreetNr){
        this.Country = Country;
        this.City = City;
        this.Street = Street;
        this.StreetNr = StreetNr;
    }
    
   
   
   
}
