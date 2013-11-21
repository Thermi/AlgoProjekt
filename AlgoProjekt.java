
/**
 *
 * @author thermi
 */
import java.util.*;
import java.lang.*;
import java.security.MessageDigest;
import java.io.FileDescriptor;
import java.io.File;
import java.io.FileInputStream;
import AlgoProjekt.*;
public class AlgoProjekt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Do some parameter handling here.
         * allowed parameters should be:
         *
         * -f FILE to load the hashtable from a file
         *  (long option: --file)
         *
         * -o FILE to store the hashtable in this file
         *  (long option: --output)
         *
         * -g to only generate the hashtable (must be used in conjunction with -o)
         *  (long option: --generate)
         *
         * -l INTEGER to generate passwords of the length INTEGER
         *  (long option: --length)
         *
         * -c STRING to generate passwords consisting of the given STRING
         *  (long option: --characters)
         *
         * -n INTEGER to generate the given amount of passwords
         *  (long option: --number)
         *
         * -r INTEGER to reseed the PRNG after INTEGER reads from the PRNG
         *  (long option: --reseed)
         *
         * -i to enable user interactivity in the program, so several passwords can be searched for.
         *  (long option: --interactive)
         *
         * -p STRING to search in the hash table for the given STRING
         *  (long option: --password)
         */

        /*
         * Parameter handling
         */
        int length = 4; /* length for -l argument */

        int reSeed = 150000;
        boolean generateOnly = false; /* boolean for -g argument */

        boolean interactive = false; /* boolean for -i argument*/

        boolean abort = false; /* boolean for interactive mode user control */

        String loadTablePath = null; /* Path for -f argument */

        String storeTablePath = null; /* Path for -o argument */

        String legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmno"
                + "pqrstuvwxyz1234567890!§$%&/()=?`'+*#-.:,;"; /* String for -c argument */

        String password = null;
        long time1, time2; /* Two longs for time measurement. */
        /*
         * Using underscores is allowed in Java. You can format numbers into
         * easily readable strings.
         */

        int amount = 300_000;
        int i;
        /* Look at all the parameters and */
        for (i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-f":
                    /*
                     * TODO:
                     * Validate the path and load the hash table from the file.
                     */
                    break;
                case "--file":
                    /*
                     * TODO:
                     * Validate the path and load the hash table from the file.
                     */
                    break;
                case "-o":
                    /*
                     * TODO:
                     * Store the hash table in the file.
                     */
                    break;
                case "--output":
                    /*
                     * TODO:
                     * Store the hash table in the file.
                     */
                    break;
                case "-l":
                    if (i < args.length - 1) {
                        try {
                            length = Integer.parseInt(args[i + 1]);

                        } catch (NumberFormatException Ne) {
                            System.err.println("Using the -l option requires an integer after it!");
                            return;
                        }
                    } else {
                        System.err.println("Using the -l option requires an integer after it!");
                        return;
                    }
                    break;
                case "--length":
                    if (i < args.length - 1) {
                        try {
                            length = Integer.parseInt(args[i + 1]);

                        } catch (NumberFormatException Ne) {
                            System.err.println("Using the --length option requires an integer after it!");
                            return;
                        }

                    } else {
                        System.err.println("Using the --length option requires an integer after it!");
                        return;
                    }
                    break;
                case "-c":
                    if (i < args.length - 1) {
                        legalChars = args[i + 1];
                    } else {
                        System.err.println("Using the -c option requires a string after it!");
                        return;
                    }
                    break;
                case "--characters":
                    if (i < args.length - 1) {
                        legalChars = args[i + 1];
                    } else {
                        System.err.println("Using the --characters option requires a string after it!");
                        return;
                    }
                    break;
                case "-g":
                    generateOnly = true;
                    break;
                case "--generate":
                    generateOnly = true;
                    break;
                case "-r":
                    if (i < args.length - 1) {
                        try {
                            reSeed = Integer.parseInt(args[i + 1]);

                        } catch (NumberFormatException Ne) {
                            System.err.println("Using the -r option requires an integer after it!");
                            return;
                        }

                    } else {
                        System.err.println("Using the -r option requires an integer after it!");
                        return;
                    }
                    break;
                case "--reseed":
                    if (i < args.length - 1) {
                        try {
                            reSeed = Integer.parseInt(args[i + 1]);

                        } catch (NumberFormatException Ne) {
                            System.err.println("Using the --reseed option requires an integer after it!");
                            return;
                        }

                    } else {
                        System.err.println("Using the --reseed option requires an integer after it!");
                        return;
                    }
                    break;
                case "-n":
                    if (i < args.length - 1) {
                        try {
                            amount = Integer.parseInt(args[i + 1]);

                        } catch (NumberFormatException Ne) {
                            System.err.println("Using the -n option requires an integer after it!");
                            return;
                        }

                    } else {
                        System.err.println("Using the -n option requires an integer after it!");
                        return;
                    }
                    break;
                case "--number":
                    if (i < args.length - 1) {
                        try {
                            amount = Integer.parseInt(args[i + 1]);

                        } catch (NumberFormatException Ne) {
                            System.err.println("Using the --number option requires an integer after it!");
                            return;
                        }

                    } else {
                        System.err.println("Using the --number option requires an integer after it!");
                        return;
                    }
                    break;
                case "-i":
                    interactive = true;
                    break;
                case "--interactive":
                    interactive = true;
                    break;
                case "-p":
                    if (i < args.length - 1) {
                        password = args[i + 1];
                    } else {
                        System.err.println("Using the -p option requires a string after it!");
                        return;
                    }
                    break;
                case "--password":
                    if (i < args.length - 1) {
                        password = args[i + 1];
                    } else {
                        System.err.println("Using the --password option requires a string after it!");
                        return;
                    }
                    break;

            }
        }
        /* we create a hashtable which uses the hash of the password as the key
         * to get the password from the hashtable */
        PrecomputationPhase phase = new PrecomputationPhase();
        Hashtable<byte[], String> table;

        if (generateOnly) {
            time1 = System.currentTimeMillis();
            table = phase.makeTable(amount, legalChars);
            time2 = System.currentTimeMillis();
            System.out.println("Generating the hash table entries took " + (time1 - time2) + "seconds");

            /*
             * TODO:
             * Write the hashtable to a file, then stop the program.
             */
            return;
        }
        /*
         * Do stuff when the path is set and valid.
         * (Validation should be done in the switch case)
         */
        if (loadTablePath != null && !loadTablePath.isEmpty()) {
            /*
             * TODO:
             * Write code to load the hashtable from a file, measure the time
             * and display it in the terminal.
             */
        } else {
            /*
             * TODO:
             * Measure the time it takes to generate the entries and store them,
             * if storeTablePath is not null and validated.
             */
            time1 = System.currentTimeMillis();
            table = phase.makeTable(amount, legalChars);
            time2 = System.currentTimeMillis();
            System.out.println("Generating the hash table entries took " + ((time1 + time2) / 2) + "seconds");
        }
        if (interactive) {

            /* Available options here:
             * 1 - Create a new hash table
             * 2 - Load a hash table
             * Stuff below here is only available, if a hash table is loaded
             * 3 - save a hash table (if one was generated)
             * 4 - enter a password and look it up in the hash table
             * 5 - generate a password and look it up in the hash table
             * This is always available:
             * 6 - quit
            */
            while(!abort) {
                System.out.println("");
            }
            /*
             * TODO:
             * User interactive entering of passwords until the user
             * enters an escape sequence that should stop the program.
             */
        } else {
            /* TODO:
             * use the password from the command line or ask for one (just one!)
             */
        }
    }
}
