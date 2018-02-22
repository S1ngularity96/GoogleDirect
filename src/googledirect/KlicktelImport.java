/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Andrei
 */
public class KlicktelImport {
    
    private Verwaltung verwaltung;
    private String jsonDatei;
    
    public KlicktelImport(Verwaltung referenz,String json){
        this.verwaltung = referenz;
        this.jsonDatei = json;
        
        JSONObject favoriten = null;
        try {
          favoriten = readJsonFrom();
        } catch (IOException ex) {
            Logger.getLogger(KlicktelImport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(KlicktelImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            collectData(favoriten);
        } catch (JSONException ex) {
            Logger.getLogger(KlicktelImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    /**
     * Bildet einen String aus einem Reader-Objekt
     * @param rd
     * @return
     * @throws IOException 
     */
    private  String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }
    
    
    /**
     * Liest die JSON KlickTel Datei ein.
     * @return
     * @throws IOException
     * @throws JSONException 
     */
    private JSONObject readJsonFrom() throws IOException, JSONException {
    FileInputStream is = new FileInputStream(jsonDatei);
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }
    
    /**
     * Sammelt alle Informationen aus der JSON-KlickTel 
     * Datei
     * @param object
     * @throws JSONException 
     */
    private void collectData(JSONObject object) throws JSONException{
      
      
      verwaltung.clearDb();
      JSONObject json = object;
      
      JSONObject favorites = json.getJSONObject("kDRoute");
      
      JSONObject fav = favorites.getJSONObject("Favorites");
      JSONArray station = fav.getJSONArray("Favorite");
      
      ArrayList<Person> stationen = new ArrayList<>();
      

      
      for(int start = 0; start < station.length();start ++){
          JSONObject personAt = station.getJSONObject(start);
      
          
          
          
          try{
              verwaltung.personEintragen(new Person(personAt.getString("Name"), "", personAt.getString("CountryShortcut"), 
                  personAt.getString("City"), personAt.getString("Street"), Integer.parseInt(personAt.getString("HouseNumber"))));
          }catch(JSONException e){
              //Fehler
          }
             
          
          
      
      } 
        System.out.println("Daten- Importiert");
  }
  }
