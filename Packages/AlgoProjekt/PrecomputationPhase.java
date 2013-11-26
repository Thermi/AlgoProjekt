/**
 *
 * @author thermi
 */
package AlgoProjekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Random;
import java.security.SecureRandom;

/**
 * This class needs to be instanciated first before it can be used.
 * <P>
 * When it is instantiated, it can be used to generate passwords according to a
 * number of parameters.
 */
public class PrecomputationPhase {

    Random gen = new Random();
    SecureRandom secgen = new SecureRandom();
    int reSeedThreshold, counter;

    /**
     * This constructor sets the threshold.
     *
     * @param threshold The threshold specifies when the class should reSeed
     * itself. When it is 0, it disables reSeeding.
     */
    public PrecomputationPhase(int threshold) {
        this.reSeedThreshold = threshold;
        counter = 0;
    }

    /**
     * This is the default constructor, it initializes all reSeedThreshold and
     * counter to 0.
     */
    public PrecomputationPhase() {
        reSeedThreshold = 0;
        counter = 0;
    }

    /**
     * This method reseeds the PRNG in the object a value from the RNG.
     */
    public void reSeed() {
        gen.setSeed(ByteBuffer.wrap(secgen.generateSeed(8)).getLong());
    }

    /**
     * This method sets the reSeedThreshold to the given value.
     *
     * @param treshold The threshold specifies when the class should reSeed
     * itself. When it is 0, it disables reSeeding.
     */
    public void setReSeedThreshold(int treshold) {
        this.reSeedThreshold = treshold;
    }

    /**
     * This method returns the set threshold.
     *
     * @return Returns the threshold of this object.
     */
    public int getReSeedThreshold() {
        return this.reSeedThreshold;
    }

    /**
     * This will generate a password of the given length and consisting of the
     * characters in the given string.
     * <P>
     * In contrast to generatePassword_2, this method does reSeed, if
     * reSeedthreshold ist > 0!
     *
     * @param characters an array of characters you want the passwords to be
     * made up of.
     * @param length The length of the password you want to generate.
     * @return A string of the given length made up of the given characters.
     */
    public String generatePassword_1(String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            if (reSeedThreshold > 0 && counter == reSeedThreshold) {
                this.reSeed();
                counter = 0;
            }
            text[i] = characters.charAt(gen.nextInt(characters.length()));
            counter++;
        }
        return new String(text);
    }

    /**
     * This function generates a password consisting of the given characters and
     * of the given length.
     * <P>
     * It does no reSeeding. Implemented on behalf of Maria.
     *
     * @param characters A string made up of characters you want the password to
     * be made up of.
     * @param length The length of the password you want to generate.
     * @return A string of the given length made up of the given characters.
     */
    public String generatePassword_2(String characters, int length) {
        char[] text = new char[length];

        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(gen.nextInt(characters.length()));
        }
        return new String(text);
    }

    /**
     * This method generates the digest of the given string using the given
     * algorithm.
     *
     * @param passwd A string to make the digest of.
     * @param algorithm The algorithm you want to use.
     * @return A hexadecimal presentation of the last 32 bit of the message
     * digest
     */
    public static String makeDigest(String passwd, String algorithm) {
        byte digest[];
        byte shortenedDigest[] = new byte[4];
        int i;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Sorry, Java doesn't know that algorithm: " + algorithm);
            e.printStackTrace();
            return null;
        }

        digest = md.digest(passwd.getBytes());
        /* We only need the first 32 bit (the first 4 byte), so we copy the
         * last 4 bytes to our own array
         */
        for (i = 0; i < 4; i++) {
            shortenedDigest[i] = digest[digest.length - 4 + i];
        }
        /* We need to translate the bytes to hexadecimal strings, because
         * HashMaps and Hashtables use object.equals to find matching objects
         * in the table, which a byte array doesn't provide.
         */
        StringBuffer sb = new StringBuffer();
        for (i = 0; i < shortenedDigest.length; i++) {
            sb.append(Integer.toString((shortenedDigest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }

    /**
     * This method will generate a hashtable that maps byte arrays to strings
     * and fill it with the given amount of digest - password pairs.
     *
     * @param amount This is the amount of entries you want to have in the
     * table.
     * @param legalChars This is a string consisting of the characters you want
     * the passwords to consist of.
     * @return A hash table with "amount" entries that consist of passwords that
     * are made up of the given characters.
     */
    public Hashtable<String, String> makeTable(int amount, String legalChars, int length) {
        int i;
        String password;
        float time1, time2;
        Hashtable<String, String> table = new Hashtable<String, String>(amount,95);
        System.out.println("Generating " + amount + " hash table entries ...");
        time1 = System.currentTimeMillis();
        for (i = 0; i < amount; i++) {
            password = generatePassword_1(legalChars, length);
            table.put(makeDigest(password, "SHA-1"), password);
        }
        time2 = System.currentTimeMillis();
        System.out.println("Generating the hash table entries took " + (time2 - time1) + " ms.");
        System.out.println("The table now holds " + table.size() + " entries.");
        return table;
    }

    public static void serialize(Hashtable<String, String> table, String path) {
        System.out.println("Please wait while the file is being created.");
        FileOutputStream f_out = null;
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException IOEx) {
            System.out.println("Something bad happened!");
            System.out.println("Stacktrace:");
            IOEx.printStackTrace();
        }
        if (!file.canWrite()) {
            System.out.println("Can't write to the file. Aborting.");
        }
        try {
            f_out = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        ObjectOutputStream obj_out = null;
        try {
            obj_out = new ObjectOutputStream(f_out);
        } catch (IOException | NullPointerException e) {
            System.out.println("Something bad happened!");
            System.out.println("Stacktrace:");
            e.printStackTrace();
        }
        //Schreibt das Aray in eine Datei
        try {
            obj_out.writeObject(table);
        } catch (IOException | NullPointerException e) {
            System.out.println("Couldn't write the hash table to the file!");
        }
    }
}
