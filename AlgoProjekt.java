
package algoprojekt;

/**
 *
 * @author thermi
 */
import java.util.*;
import java.lang.*;
import java.security.MessageDigest;
import java.io.FileInputStream;
public class AlgoProjekt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Do some parameter handling here.
         * allowed parameters should be:
         * -f FILE to load the hashtable from a file (long option: --file)
         * -o FILE to store the hashtable in this file (long option: --output)
         * -g to only generate the hashtable (must be used in conjunction with -o) (long option: --generate)
         * -l INTEGER to generate passwords of the length INTEGER (long option: --length)
         * -c STRING to generate passwords consisting of the given STRING (long option: --characters)
         */
        /* we create a hashtable which uses the hash of the password as the key
         * to get the password from the hashtable */
        Hashtable<byte[], String> table = new Hashtable<byte[], String>();
        // more is to be written.
    }
}
