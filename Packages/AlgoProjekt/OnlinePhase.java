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
    }
    /* This function is there to be able to test the hit rate on the hash table.
     * It generates random passwords and try to find a collision in the table.
     */

    public static void testHashtable(Hashtable<String, String> ht, String legalChars, int length) {
        PrecomputationPhase phase = new PrecomputationPhase();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line, digest, password;
        int number, hits = 0, collisions = 0, i;
        System.out.println("Enter the number of random passwords you want to test the hash table on: ");
        try {
            line = br.readLine();
        } catch (IOException ex) {
            System.out.println("We couldn't read an integer from the terminal. Sorry :(");
            System.out.println("Stacktrace:");
            ex.printStackTrace(System.err);
            return;
        }
        try {
            number = Integer.parseInt(line);
        } catch (NumberFormatException NFe) {
            System.out.println("Sorry, couldn't parse the string as an integer. :( ");
            System.out.println("Stacktrace:");
            NFe.printStackTrace(System.err);
            return;
        }
        /*
         * We try to find collisions for "number" passwords. We increment a counter and
         * then print out the hit rate.
         */
        for (i = 0; i < number; i++) {
            password = phase.generatePassword_1(legalChars, length);
            digest = makeDigest(password, "SHA-1");
            line = ht.get(digest);
            if (line != null) {
                System.out.println("Found matching password \"" + line + "\" to \"" + password + "\"");
                hits++;
                if (!line.equals(password)) {
                    collisions++;
                }
            }
        }
        System.out.println("The hit rate was " + hits + " out of " + number + " with " + collisions + " collisions.");
    }

    /* This method offers the possibility to
     * test the hash table for just one password.
     */
    public static void testHashtableOnce(Hashtable<String, String> ht, String legalChars, int length) {
        PrecomputationPhase phase = new PrecomputationPhase();
        String passwd, digest, foundPassword;
        passwd = phase.generatePassword_1(legalChars, length);
        digest = makeDigest(passwd, "SHA-1");
        foundPassword = ht.get(digest);
        if (foundPassword != null) {
            System.out.println("Found a collision/matchin password \"" + foundPassword + "\" to \"" + passwd + "\"");
        } else {
            System.out.println("Sorry, no matching entry in the table for password \"" + passwd + "\"");
        }
    }
}
