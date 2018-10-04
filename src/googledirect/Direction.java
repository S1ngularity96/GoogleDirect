/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;



/**
 * Ein Objekt mit einem Request für eine Route mit Start und Ziel
 * @author Livem
 */
public class Direction {
    
   final String googleAPIkey = "";
   private String Country= "";
   private String City = "";
   private String Street = "";
   private String StreetNr = "";
    
   private  String desCountry= "";
   private String desCity = "";
   private String desStreet = "";
   private String desStreetNr = "";
    
    
   private String resultRequest = "";
    
    
   /**
    * Standart Konstruktor
    */
   public Direction(){
       
   }
    
   /**
    * Initialisiere das DirectionObjekt mit Standorten von Start und Ziel
    * @param start
    * @param target 
    */
    
   public Direction(Standort start, Standort target){
        this.Country = start.getCountry().replace(' ', '+');
        this.City = start.getCity().replace(' ', '+');
        this.Street = start.getStreet().replace(' ', '+');
        this.StreetNr = Integer.toString(start.getStreetNr());
        
        this.desCountry = target.getCountry().replace(' ', '+');
        this.desCity = target.getCity().replace(' ', '+');
        this.desStreet = target.getStreet().replace(' ', '+');
        this.desStreetNr = Integer.toString(target.getStreetNr());
   } 
   
   /**
     * Initialisiere das DirectionObjects mit Start- und Zielort.
     * @param Cou Start Land
     * @param Cit Start Stadt
     * @param Str Start Straße
     * @param StrNum Start Straßennummer
     * @param desCou Ziel Land
     * @param desCit Ziel Stadt
     * @param desStr Ziel Straße
     * @param desStrNum Ziel Straßennummer
     */
   
    public Direction(String Cou,String Cit,String Str, int StrNum,String desCou,String desCit, String desStr, int desStrNum){
        this.Country = Cou.replace(' ', '+');
        this.City = Cit.replace(' ', '+');
        this.Street = Str.replace(' ', '+');
        this.StreetNr = Integer.toString(StrNum);
        
        this.desCountry = desCou.replace(' ', '+');
        this.desCity = desCit.replace(' ', '+');
        this.desStreet =  desStr.replace(' ', '+');
        this.desStreetNr= Integer.toString(desStrNum);
        
        
    }
    
    /**
     * Setze Start und Ziel 
     */
    
    public void setNewDirection(String Cou,String Cit,String Str, int StrNum,String desCou,String desCit, String desStr, int desStrNum){
        this.Country = Cou.replace(' ', '+');
        this.City = Cit.replace(' ', '+');
        this.Street = Str.replace(' ', '+');
        this.StreetNr = Integer.toString(StrNum);
        
        this.desCountry = desCou.replace(' ', '+');
        this.desCity = desCit.replace(' ', '+');
        this.desStreet =  desStr.replace(' ', '+');
        this.desStreetNr= Integer.toString(desStrNum);
        
        
    }
    
    /**
     * Setze Standortdaten für Start und Ziel
     */
    
    public void setNewPlaces(Standort start, Standort target){
        this.Country = start.getCountry().replace(' ', '+');
        this.City = start.getCity().replace(' ', '+');
        this.Street = start.getStreet().replace(' ', '+');
        this.StreetNr = Integer.toString(start.getStreetNr());
        
        this.desCountry = target.getCountry().replace(' ', '+');
        this.desCity = target.getCity().replace(' ', '+');
        this.desStreet = target.getStreet().replace(' ', '+');
        this.desStreetNr = Integer.toString(target.getStreetNr());
    }
  
    /**
     * Generiere die Anfrage mit Start- und Zielinformationen für Google- Maps API
     * @return JSONRequest - String
     */
    
public String generateRequest(){
    return resultRequest = "https://maps.googleapis.com/maps/api/directions/json?origin="+Country+"+"+City+"+"+Street+"+"+StreetNr
                + "&destination="+desCountry+"+"+desCity+"+"+desStreet+"+"+desStreetNr
                + "&key="+googleAPIkey;
}
    
    
 
/**+
 * Inhalt eines DirectionObjekts
 * @return Direction- String 
 */

    @Override
    public String toString() {
        return "Direction{" + "googleAPIkey=" + googleAPIkey + ", Country=" + Country + ", City=" + City + ", Street=" + Street + ", StreetNr=" + StreetNr + ", desCountry=" + desCountry + ", desCity=" + desCity + ", desStreet=" + desStreet + ", desStreetNr=" + desStreetNr + ", resultRequest=" + resultRequest + '}';
    }
    
    
    
}
