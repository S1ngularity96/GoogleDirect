package googledirect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;





public class JsonReader {

      
    private JSONObject info;
    private JSONObject distance;
    private JSONObject duration;
    
    
  /**
   * Lese den Inhalt einer JSON-Response
   * @param rd Reader
   * @return Inhalt der Datei
   * @throws IOException 
   */    
  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  
  /**
   * Erstelle ein JSONObject mit allen Informationen aus der Google-Maps API
   * @param url
   * @return
   * @throws IOException
   * @throws JSONException 
   */
  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
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
   * 
   * @param URL Google- MAPS API Request URL
   * @throws IOException
   * @throws JSONException 
   */
  public JsonReader(String URL)throws IOException, JSONException {
      JSONObject json = readJsonFromUrl(URL);
      collectData(json);
      
  }
  
  // leerer Default Konstruktor
  public JsonReader(){}
  
  
  public void setNewRequest(String URL) throws IOException, JSONException{
      JSONObject json =  readJsonFromUrl(URL);
      collectData(json);
  }
  
  /**
   * Parse das JSONObjekt und speichere Routen-Informationen in weiteren 
   * JSON Objects
   * @param object
   * @throws JSONException 
   */
  private void collectData(JSONObject object) throws JSONException{
      JSONObject json = object;
      
      
      
      JSONArray routen = json.getJSONArray("routes");
      JSONObject inner = routen.getJSONObject(0);
      
      JSONArray legs = inner.getJSONArray("legs");
      info = legs.optJSONObject(0);
     
      distance = info.getJSONObject("distance");
      duration = info.getJSONObject("duration");
  }
  
  /**
   * Gib die Start- Adresse aus.
   * @return Start Adresse
   * @throws JSONException 
   */
  public String getStartAddress() throws JSONException{
      return info.get("start_address").toString();
  }
  
  /**
   * Gib die Ziel- Adresse aus.
   * @return Ziel- Adresse
   * @throws JSONException 
   */
  public String getTargetAdress() throws JSONException{
     return  info.get("end_address").toString();
  }
  
  /**
   * Gib die Fahrzeitdauer aus
   * @return Fahrzeitdauer
   * @throws JSONException 
   */
  public double getDuration() throws JSONException{
      
      System.out.println(duration.get("text")); 
      return Double.parseDouble(duration.get("text").toString().replaceAll("[^\\d.]", ""));
  }
  
  /**
   * Gib die Entfernung zwischen zwei Orten aus.
   * @return Entfernung
   * @throws JSONException 
   */
  public double getDistance() throws JSONException{
      
      
      if(distance.getString("text").contains("km")){
          return Double.parseDouble(distance.getString("text").replaceAll("[^\\d.]", ""));
      }else{
          return (Double.parseDouble(distance.getString("text").replaceAll("[^\\d.]", ""))/1000.0);
      }
      
  }
  
  
  /**
   * Gibt die Route zwischen zwei Orten aus.
   * @return Route
   */
      @Override
  public String toString(){
      
          try {
              return "Start: "+info.get("start_address").toString() +"\n"+
                      "Ziel: "+info.get("end_address").toString() + "\n" +
                      "Entfernung in Kilometer: "+getDistance()+"\n"+
                      "Fahrtdauer: "+duration.get("text").toString();
          } catch (JSONException ex) {
              return "Error: Couldn't parse JSONObject!";
          }
              
      
      
  }
  
  
}