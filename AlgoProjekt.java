
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
import static AlgoProjekt.PrecomputationPhase.makeDigest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        String loadTablePath = null; /* Path for -f argument */

        String storeTablePath = null; /* Path for -o argument */

        String legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmno"
                + "pqrstuvwxyz1234567890!§$%&/()=?`'+*#-.:,;"; /* String for -c argument */

        String password = null;
        long time1, time2; /* Two longs for time measurement. */
        /*
         * Using underscores is allowed in Java. You can format numbers into
         * easily readable strings with it.
         */

        int amount = 10_000_000; /* sane default amount of 5 million entries */

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
        Hashtable<String, String> table = null;


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
            System.out.println("Generating " + amount+" hash table entries ...");
            time1 = System.currentTimeMillis();
            table = phase.makeTable(amount, legalChars, length);
            time2 = System.currentTimeMillis();
            System.out.println("Generating the entries took " + (time2 - time1) + " ms.");
            System.out.println("The table now holds " + table.size() + " entries.");
        }
        if (interactive) {

            boolean abort = false, isInteger = false, wrong_option = true; /* booleans for interactive mode user control */

            String option = null;
            int value = 0;
            /* Available options here:
             * 1 - Create a new hash table.
             * 2 - Load a hash table.
             * Stuff below here is only available, if a hash table is loaded.
             * 3 - save a hash table (if one was generated).
             * 4 - enter a password and look it up in the hash table.
             * 5 - generate a password and look it up in the hash table.
             * 6 - test the hash table with a number of generated passwords.
             * This is always available:
             * 7 - quit.
             */
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            while (!abort) {

                while (wrong_option) {
                    System.out.println("Please choose an option:");
                    System.out.println("1 - Create a new hash table.");
                    System.out.println("2 - Load a hash table.");
                    System.out.println("3 - Save the hash table.");
                    System.out.println("4 - Look up a password in the hash table.");
                    System.out.println("5 - Generate a password and look it up in the hash table.");
                    System.out.println("6 - Test the hash table with a number of generated passwords.");
                    System.out.println("7 - Quit.");
                    while (!isInteger) {
                        /* We try to read a line from the buffered reader.
                         */
                        try {
                            option = br.readLine();
                        } catch (IOException ex) {
                            System.out.println("We couldn't read an integer from the terminal. Sorry :(");
                            System.out.println("Stacktrace:");
                            ex.getStackTrace();
                            isInteger = false;
                        } finally {

                        }
                        /* We try to  */
                        try {
                            value = Integer.parseInt(option);
                        } catch (NumberFormatException NFe) {
                            System.out.println("Please enter a valid integer.");
                            isInteger = false;
                        }
                        /*
                         This checks if the value chosen from the user is applicable to the current state of the program.
                         E.g.: Whether a hashtable was loaded or not
                         */
                        if ((value == 3 || value == 4 || value == 5 || value == 6) && table == null) {
                            System.out.println("Load a hash table before trying to use those!");
                            wrong_option = true;
                        } else {
                            wrong_option = false;
                        }
                    }
                }
                switch (value) {
                    case 1:
                        System.out.println("Generating " + amount + " hash table entries ...");
                        time1 = System.currentTimeMillis();
                        table = phase.makeTable(amount, legalChars, length);
                        time2 = System.currentTimeMillis();
                        System.out.println("Generating the hash table entries took " + (time2 - time1) + " ms.");
                        System.out.println("The table now holds " + table.size() + " entries.");
                        break;
                    case 2:
                        /*
                         * TODO:
                         * Do stuff to deserialize and load a hash table
                         * and measure the time it took to do it.
                         */
                        break;
                    case 3:
                        /*
                         * TODO:
                         * DO stuff to serialize a hashtable and write it to a file.
                         */
                        break;
                    case 4:
                        /* We do this, because having two InputStreamReaders on the same
                         * stream is bad and having two buffered readers on the same one is bad as well.
                         */
                        try {
                            br.close();
                        } catch (IOException IOe) {
                            System.out.println("Couldn't close the buffered reader.");
                            System.out.println("Stacktrace:");
                            IOe.printStackTrace();
                        }
                        try {
                            isr.close();
                        } catch (IOException IOe) {
                            System.out.println("Couldn't close the buffered reader.");
                            System.out.println("Stacktrace:");
                            IOe.printStackTrace();
                        }
                        OnlinePhase.testRainbowTable(table);
                        isr = new InputStreamReader(System.in);
                        br = new BufferedReader(isr);
                        break;
                    case 5:
                        try {
                            br.close();
                        } catch (IOException IOe) {
                            System.out.println("Couldn't close the buffered reader.");
                            System.out.println("Stacktrace:");
                            IOe.printStackTrace();
                        }
                        try {
                            isr.close();
                        } catch (IOException IOe) {
                            System.out.println("Couldn't close the buffered reader.");
                            System.out.println("Stacktrace:");
                            IOe.printStackTrace();
                        }
                        testHashtable(table, legalChars, length);
                        isr = new InputStreamReader(System.in);
                        br = new BufferedReader(isr);
                        break;
                    case 6:
                        /* We do this, because having two InputStreamReaders on the same
                         * stream is bad and having two buffered readers on the same one is bad as well.
                         */
                        try {
                            br.close();
                        } catch (IOException IOe) {
                            System.out.println("Couldn't close the buffered reader.");
                            System.out.println("Stacktrace:");
                            IOe.printStackTrace();
                        }
                        try {
                            isr.close();
                        } catch (IOException IOe) {
                            System.out.println("Couldn't close the input stream reader.");
                            System.out.println("Stacktrace:");
                            IOe.printStackTrace();
                        }
                        testHashtable(table, legalChars, length);
                        isr = new InputStreamReader(System.in);
                        br = new BufferedReader(isr);
                        break;
                    case 7:
                        System.out.println("Quitting...");
                        abort = true;
                        return;

                }
            }
            /*
             * TODO:
             * User interactive entering of passwords until the user
             * enters an escape sequence that should stop the program.
             */
        } else {
            if (generateOnly) {

                time1 = System.currentTimeMillis();
                table = phase.makeTable(amount, legalChars, length);
                time2 = System.currentTimeMillis();
                System.out.println("Generating the hash table entries took " + (time2 - time1) + " ms.");
                System.out.println("The table now holds " + table.size() + " entries.");

                /*
                 * TODO:
                 * Write the hashtable to a file, then stop the program.
                 */
                return;
            }

            /* TODO:
             * use the password from the command line or ask for one (just one!)
             */
        }
    }

    /* This function is there to be able to test the hit rate on the hash table.
     * It generates random passwords and try to find a collision in the table.
     */
    public static void testHashtable(Hashtable<String, String> ht, String legalChars, int length) {
        PrecomputationPhase phase = new PrecomputationPhase();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line, digest;
        int number, hits = 0, i;
        System.out.println("Enter the number of random passwords you want to test the hash table on: ");
        try {
            line = br.readLine();
        } catch (IOException ex) {
            System.out.println("We couldn't read an integer from the terminal. Sorry :(");
            System.out.println("Stacktrace:");
            ex.printStackTrace();
            return;
        }
        try {
            number = Integer.parseInt(line);
        } catch (NumberFormatException NFe) {
            System.out.println("Sorry, couldn't parse the string as an integer. :( ");
            System.out.println("Stacktrace:");
            NFe.printStackTrace();
            return;
        }
        /*
         We try to find collisions for "number" passwords. We increment a counter and
         then print out the hit rate.
         */
        for (i = 0; i < number; i++) {
            line = phase.generatePassword_2(legalChars, length);
            digest = makeDigest(line, "SHA-1");
            line = ht.get(digest);
            if (line != null) {
                System.out.println("Found password: \"" + line + "\"");
                hits++;
            }
        }
        System.out.println("The hit rate was " + " percent.");
        try {
            br.close();
        } catch (IOException IOe) {
            System.out.println("Couldn't close the buffered reader.");
            System.out.println("Stacktrace:");
            IOe.printStackTrace();
        }
        try {
            isr.close();
        } catch (IOException IOe) {
            System.out.println("Couldn't close the input stream reader.");
            System.out.println("Stacktrace:");
            IOe.printStackTrace();
        }
    }

    /* This method offers the possibility to
     * test the hash table for just one password.
     */
    public static void testHashtableOnce(Hashtable<String, String> ht, String legalChars, int length) {
        PrecomputationPhase phase = new PrecomputationPhase();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String passwd, digest, foundPassword;
        passwd = phase.generatePassword_2(legalChars, length);
        digest = makeDigest(passwd, "SHA-1");
        foundPassword = ht.get(digest);
        if (foundPassword != null) {
            System.out.println("Found a collision/matchin password \"" + foundPassword + "\" to \"" + passwd + "\"");
        }
        try {
            br.close();
        } catch (IOException IOe) {
            System.out.println("Couldn't close the buffered reader.");
            System.out.println("Stacktrace:");
            IOe.printStackTrace();
        }
        try {
            isr.close();
        } catch (IOException IOe) {
            System.out.println("Couldn't close the input stream reader.");
            System.out.println("Stacktrace:");
            IOe.printStackTrace();
        }
    }

}
