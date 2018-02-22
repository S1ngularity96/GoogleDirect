/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googledirect;

/**
 *
 * @author Andrei
 */
public class test {
    
    public static void main(String[] args){
        Person person = new Person("Andrei", "Alexeev", "Deutschland", "Aachen", "Rosstraße", 28);
        Person person2 = new Person("Andrei", "Alexeev", "Deutschland", "Aachen", "Rosstraße", 28);
        
        System.out.println(person.equals(person));
        
    }
}
