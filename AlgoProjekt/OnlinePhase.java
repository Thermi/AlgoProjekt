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
    public static void testRainbowTable(Hashtable<byte[], String> ht) {
        //variables
        String passwd, algorithm = "SHA-1";
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        byte digest[];
        byte shortenedDigest[] = new byte[4];
        int i;
        /*
         * Returns a MessageDigest object that implements the specified digest algorithm.
         */
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Sorrby, Java doesn't know that algorithm: " + algorithm);
            e.printStackTrace();
            return;
        }
        System.out.println("Enter a password:");
        try {
            passwd = br.readLine();
        } catch (IOException IOe) {
            System.err.println("Sorry, something bad happened when we tried to read from the terminal. :( ");
            IOe.printStackTrace();
            return;
        }
        /* md.digest() returns and takes a byte array, so we need to transform the string
         * into a byte array. We do this with passwd.getBytes()
         */

        digest = md.digest(passwd.getBytes());

        /* We only need the first 32 bit (the first 4 byte), so we copy the
         * last 4 bytes to our own array
         */
        for (i = 0; i < 4; i++) {
            shortenedDigest[i] = digest[digest.length - 4 + i];
        }
        // check whether the hash table contains a fitting entry.
        if (ht.get(shortenedDigest) == null) {
            System.out.println("No password found.");
        } else {
            System.out.println("A password with the same hash value has been found: " + ht.get(shortenedDigest));
        }
        try {
            br.close();
        } catch (IOException IOe) {
            System.err.println("Sorry, couldn't close the Buffered Reader. :(");
            IOe.printStackTrace();
        }
    }
}