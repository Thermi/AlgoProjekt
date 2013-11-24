        
 import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.io.Writer;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
         
public class Hashtables {
        
        
        Hashtable<String,String> ht = new Hashtable<String,String>();
        
        public static Scanner userInput = new Scanner(System.in);
        //convert the byte to hex format method 2
    static StringBuffer hexString = new StringBuffer();
        
        public static void main(String[] args)throws Exception
         {
                //variables                
                 Random r = new Random();
                 
                 String password;
                 long start = System.currentTimeMillis();
                 long heapSize = Runtime.getRuntime().totalMemory();
                 
                 // start
                 System.out.println("Creating rainbow table: ");
                 
                 
                 ------------------------------------------------------
                
                
                String password;
                System.out.println("Passwort: ");
                password = userInput.nextLine();
         
     MessageDigest md = MessageDigest.getInstance("SHA-1");
     md.update(password.getBytes());
     System.out.println(md);

     byte byteData[] = md.digest();

     //convert the byte to hex format
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < byteData.length; i++) {
      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
     }
     System.out.println(md);
     System.out.println("Hex format : " + sb.toString());

     
         for (int i = 0; i < byteData.length; i++) {
                 String hex=Integer.toHexString(0xff & byteData[i]);
                     if(hex.length()==1) hexString.append('0');
                     hexString.append(hex);
         }
         
         //gehashten Wert ausgeben
         System.out.println("Hex format : " + hexString.toString());
         
         
         //den Hash in die Datei fileWriter.txt schreiben
         Writer fw = null;

         try
         {
           fw = new FileWriter( "/home/mariella/Dokumente/fileWriter.txt" );
           fw.write(hexString.toString());
           fw.append( System.getProperty("line.separator") ); // e.g. "\n"
           System.out.println("Erfolg");
         }
         catch ( IOException e ) {
           System.err.println( "Konnte Datei nicht erstellen" );
         }
         finally {
           if ( fw != null )
             try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
         }
         
         
         
 }
                 
        
        
         public void login(String username, String password)
         {
                 
                 
                 System.out.println("Bitte geben Sie Ihren Namen ein: ");
                 username = userInput.nextLine();
                 
                 System.out.println("Bitte geben Sie Ihr Passwort ein: ");
                 password = userInput.nextLine();
                 
                
                 
                 
         }
         
         public void register(String usernameR, String passwordR)
         {
                 
         }
         
        //schneidet die letzten vier bytes der Prüfsumme ab- shortString => key
                //        String shortString = hexString.substring(32, 40);
                        
         
         //aufruf der methode
         // fillTable(shortString, password);
        public void fillTable(String key, String value)
        {
                //bedingungen festlegen
                //map füllen
                ht.put(key, value);
                
                
                
                // key zugehöriges value paar im string pass abspeichern- wird erst beim auslesen gebraucht
                //String pass = ht.get(key);
                
                
                //console out hashtable vlt mit toString()
                System.out.println(ht);
                
                
        }
            
        // method creates a new password        
public static String generate(String characters, int length){
                  
                         String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                         //String aus dem obigen Alphabet mit 28 Zeichen
                         System.out.println(generate(alphabet, 28));

                         //String mit Großbuchstaben und 2 Zeichen
                         System.out.println(generate("ABC", 2));
                        System.out.println("----------------");
                        
                     //String mit Groß- und Kleinbuchstaben mit 10 Zeichen
                         System.out.println(generate("abcABC", 10));
                        System.out.println("----------------");

                        // String aus Zahlen
                         String someDigits = "0123";
                        System.out.println("----------------");
                        
                        //String aus Zeichen
                         String someSpecial = "!%&*+-@";
                        System.out.println("----------------");
                        
                        /*
                         * Gibt ein String aus, Länge 10 und aus Zahlen(someDigits), Zeichen(someSpecial)
                         * und Buchstaben(alphabet)
                         */
                        // generate(length, digits, special, alphabet);
                         return generate(characters, length);
        
}
}
