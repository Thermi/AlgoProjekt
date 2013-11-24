/**
 *
 * @author thermi
 */

package AlgoProjekt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import static AlgoProjekt.PrecomputationPhase.makeDigest;

public class OnlinePhase {

    /* Load the rainbow table and print out the amount of hash entries.
     * To be implemented.
     */
    public static void deserialize(String filename) {
    }

    /* This method takes a password from stdin, calculates the 32 bit digest
     * and looks it up in the hashtable. It then prints a corresponding message
     * to stdout.
     */
    public static void testRainbowTable(Hashtable<String, String> ht) {
        //variables
        String passwd, digest;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        /*
         * Returns a MessageDigest object that implements the specified digest algorithm.
         */
        System.out.println("Enter a password:");
        try {
            passwd = br.readLine();
        } catch (IOException IOe) {
            System.err.println("Sorry, something bad happened when we tried to read from the terminal. :( ");
            IOe.printStackTrace();
            return;
        }
        digest = makeDigest(passwd, "Sha-1");

        // check whether the hash table contains a fitting entry.

        if (ht.get(digest) == null) {
            System.out.println("No password found.");
        } else {
            System.out.println("A password with the same hash value has been found: " + ht.get(digest));
        }
        try {
            br.close();
        } catch (IOException IOe) {
            System.err.println("Sorry, couldn't close the Buffered Reader. :(");
            IOe.printStackTrace();
        }
    }
}