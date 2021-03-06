
/**
 *
 * @author thermi
 */
import java.util.Hashtable;
import java.io.File;
import AlgoProjekt.*;
import static AlgoProjekt.OnlinePhase.deserialize;
import static AlgoProjekt.OnlinePhase.testHashtable;
import static AlgoProjekt.OnlinePhase.testHashtableOnce;
import static AlgoProjekt.PrecomputationPhase.serialize;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgoProjekt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Do some parameter handling here.
         * allowed parameters should be:
         * -h to display a help message
         *  (long option: --help)
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
         * Parameter handling.
         */

        int length = 6; /* length for -l argument */

        int reSeed = 150000;

        boolean generateOnly = false; /* boolean for -g argument */

        boolean interactive = false; /* boolean for -i argument*/

        String loadTablePath = null; /* Path for -f argument */

        String storeTablePath = null; /* Path for -o argument */

        String legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmno"
                + "pqrstuvwxyz1234567890!§$%&/()=?`'+*#-.:,;\""; /* String for -c argument */

        String password = null;
        /*
         * Using underscores is allowed in Java. You can format numbers into
         * easily readable strings with it.
         */

        int amount = 10_000_000; /* sane default amount of 5 million entries */

        int i;
        /* Look at all the parameters and */
        for (i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                    printHelpMessage();
                    return;

                case "--help":
                    printHelpMessage();
                    return;

                case "-f":
                    if (i < args.length - 1) {
                        String path = args[i + 1];
                        File inputpathdescriptor = new File(path);
                        if (!inputpathdescriptor.exists()) {
                            System.out.println("The specified file for the -f option doesn't exist!");
                            return;
                        }
                        if (!inputpathdescriptor.canRead()) {
                            System.out.println("The specified file for the -f option can't be read!");
                            return;
                        }
                        if (!inputpathdescriptor.isFile()) {
                            System.out.println("The specified path doesn't point to a file!");
                            return;
                        }
                        loadTablePath = path;
                        i++;
                    } else {
                        System.out.println("Using the -f option requires a file after it!");
                        return;
                    }
                    break;

                case "--file":
                    /* We check for file existence, read permissions and if the specified path is actually a file (Not a unix socket, folder,
                     * symlink or some other soft of madness
                     */
                    if (i < args.length - 1) {
                        String path = args[i + 1];
                        File pathdescriptor = new File(path);
                        if (!pathdescriptor.exists()) {
                            System.out.println("The specified file for the --file option doesn't exist!");
                            return;
                        }
                        if (!pathdescriptor.canRead()) {
                            System.out.println("The specified file for the --file option can't be read!");
                            return;
                        }
                        if (!pathdescriptor.isFile()) {
                            System.out.println("The specified path doesn't point to a file!");
                            return;
                        }
                        loadTablePath = path;
                        i++;
                    } else {
                        System.out.println("Using the --file option requires a file after it!");
                        return;
                    }
                    break;

                case "-o":
                    /* We can only check if the output file already exists, as more checks
                     * would require us to actually write a file there. (We don't operate
                     * on the file system, unless we can assure, that everything works okay.
                     */
                    if (i < args.length - 1) {
                        String path = args[i + 1];
                        /* Ruben told me to add ".ser" to the file. */
                        File pathdescriptor = new File(path + ".ser");
                        System.out.println("Saving in \"" + path + "\"");
                        if (pathdescriptor.exists()) {
                            System.out.println("The specified file for the -o option already exists!");
                            return;
                        }
                        storeTablePath = path;
                        i++;
                    } else {
                        System.out.println("Using the -o option requires a file after it!");
                        return;
                    }
                    break;

                case "--output":
                    /* We can only check if the output file already exists, as more checks
                     * would require us to actually write a file there. (We don't operate
                     * on the file system, unless we can assure, that everything works okay.
                     */
                    if (i < args.length - 1) {
                        String path = args[i + 1];
                        /* Ruben told me to add ".ser" to the file. */
                        File pathdescriptor = new File(path + ".ser");
                        System.out.println("Saving in \"" + path + "\"");
                        if (pathdescriptor.exists()) {
                            System.out.println("The specified file for the --output option already exists!");
                            return;
                        }
                        storeTablePath = path;
                        i++;
                    } else {
                        System.out.println("Using the --output option requires a file after it!");
                        return;
                    }
                    break;

                case "-l":
                    if (i < args.length - 1) {
                        try {
                            length = Integer.parseInt(args[i + 1]);

                        } catch (NumberFormatException Ne) {
                            System.err.println("Using the -l option requires an integer after it!");
                            return;
                        }
                        finally {
                            i++;
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
                        } finally {
                            i++;
                        }

                    } else {
                        System.err.println("Using the --length option requires an integer after it!");
                        return;
                    }
                    break;

                case "-c":
                    if (i < args.length - 1) {
                        legalChars = args[i + 1];
                        i++;
                    } else {
                        System.err.println("Using the -c option requires a string after it!");
                        return;
                    }
                    break;

                case "--characters":
                    if (i < args.length - 1) {
                        legalChars = args[i + 1];
                        i++;
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
                        } finally {
                            i++;
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
                        } finally {
                            i++;
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
                        } finally {
                            i++;
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
                        } finally {
                            i++;
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
                        i++;
                    } else {
                        System.err.println("Using the -p option requires a string after it!");
                        return;
                    }
                    break;

                case "--password":
                    if (i < args.length - 1) {
                        password = args[i + 1];
                        i++;
                    } else {
                        System.err.println("Using the --password option requires a string after it!");
                        return;
                    }
                    break;

                default:
                    System.err.println("Option " + args[i] + " isn't known!");
                    return;

            }
        }
        /* we create a hashtable which uses the hash of the password as the key
         * to get the password from the hashtable */
        PrecomputationPhase phase = new PrecomputationPhase(reSeed);
        Hashtable<String, String> table = null;


        /* Load the hash table from the input file,
         * if it is specified and validated.
         */
        if (loadTablePath != null && !loadTablePath.isEmpty()) {
            table = deserialize(loadTablePath);
            if(table == null)
                System.out.println("Couldn't load the hash table from \"" + loadTablePath + "\"!");

        } else if (generateOnly && ( storeTablePath == null || storeTablePath.isEmpty())) {
            table = phase.makeTable(amount, legalChars, length);
        }
        if (interactive) {

            /* booleans for interactive mode user control */
            boolean isInteger = false, wrong_option = true, accepted = false ;
            File pathdescriptor;
            String option = null, path = null;
            int value;
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
            while (true) {
                value = 0;
                while (wrong_option || value == 0) {
                    System.out.print(
                            "Please choose an option:\n"
                            + "1 - Create a new hash table.\n"
                            + "2 - Load a hash table.\n"
                            + "3 - Save the hash table.\n"
                            + "4 - Look up a password in the hash table.\n"
                            + "5 - Generate a password and look it up in the hash table.\n"
                            + "6 - Test the hash table with a number of generated passwords.\n"
                            + "7 - Quit.\n"
                    );
                    /* A loop to check the user input, so the user can't enter any non-integer numbers.
                     */
                    while (wrong_option || !isInteger || value == 0) {
                        /* We try to read a line from the buffered reader.
                         */
                        try {
                            option = br.readLine();
                        } catch (IOException ex) {
                            System.err.println("We couldn't read from the terminal. Sorry :(");
                            System.err.println("Stacktrace:");
                            ex.getStackTrace();
                            return;
                        } finally {
                            /* We check for the range of options, so you can't enter -1 or 8. */
                            if (value > 0 && value <= 7) {
                                isInteger = true;
                            }
                        }
                        /* We try to  parse the input line as an integer */
                        try {
                            value = Integer.parseInt(option);
                        } catch (NumberFormatException NFe) {
                            System.err.println("Please enter a valid integer.");
                            isInteger = false;
                        } finally {
                            /* We check for the range of options, so you can't enter -1 or 8. */
                            if (value > 0 && value <= 7) {
                                isInteger = true;
                            }
                        }
                        /*
                         This checks if the value chosen from the user is applicable to the current state of the program.
                         E.g.: Whether a hashtable was loaded or not
                         */
                        if ((value == 3 || value == 4 || value == 5 || value == 6) && table == null) {
                            System.err.println("Load a hash table before you try to use those!");
                            wrong_option = true;
                        } else {
                            wrong_option = false;
                        }
                    }
                }
                switch (value) {
                    case 1:
                        table = phase.makeTable(amount, legalChars, length);
                        break;
                    case 2:
                        accepted = false;
                        while (!accepted) {
                            accepted = true;
                            System.out.println("Please enter the path to the file: ");
                    try {
                        path = br.readLine();
                    } catch (IOException IOex) {
                        System.err.println("Sorry, couldn't read the path from the terminal!\n");
                        System.err.println("Stacktrace:");
                        IOex.printStackTrace(System.err);
                        return;
                    }
                            pathdescriptor = new File(path);
                            if (!pathdescriptor.exists()) {
                                System.err.println("The specified file doesn't exist!");
                                accepted = false;
                            }
                            if (!pathdescriptor.canRead()) {
                                System.err.println("The specified file can't be read!");
                                accepted = false;
                            }
                            if (!pathdescriptor.isFile()) {
                                System.err.println("The specified path doesn't point to a file!");
                                accepted = false;
                            }

                        }
                        table = deserialize(path);

                        break;

                    case 3:
                        /* Ruben told me to add ".ser" to the file. */
                        while (!accepted) {
                            accepted = true;
                            System.out.print("Please enter a path: ");
                    try {
                        path = br.readLine();
                    } catch (IOException IOEx) {
                        System.err.println("Sorry, couldn't read the path from the terminal!");
                        System.err.println("Stacktrace:");
                        IOEx.printStackTrace(System.err);
                    }
                            path += ".ser";
                            pathdescriptor = new File(path);
                            System.out.println("Saving in \"" + path + "\"");
                            if (pathdescriptor.exists()) {
                                System.err.println("The specified file already exists");
                                accepted = false;
                            }
                        }
                        serialize(table, path);

                        break;
                    case 4:
                        OnlinePhase.testRainbowTable(table);
                        break;
                    case 5:
                        testHashtableOnce(table, legalChars, length);
                        break;
                    case 6:
                        testHashtable(table, legalChars, length);
                        break;
                    case 7:
                        System.out.println("Quitting...");
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
                table = phase.makeTable(amount, legalChars, length);
                if (storeTablePath != null || !storeTablePath.isEmpty()) {
                    serialize(table, storeTablePath);
                }
                return;
            }
            /* If no table was generated yet, we need to generate one. */
            if (table == null) {
                table = phase.makeTable(amount, legalChars, length);
            }
            /* If no password is supplied from the command line,
             * we read it from the terminal
             */
            if (password == null) {
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String result;
                System.out.println("Please enter the password you want to search for: ");
                try {
                    password = br.readLine();
                } catch (IOException IOex) {
                    System.err.println("Sorry, couldn't read the password from the terminal. :(");
                    System.err.println("Stacktrace:");
                    IOex.printStackTrace(System.err);
                    return;
                }
                result = table.get(password);
                if (result != null) {
                    System.out.println("Matchin entry to \"" + password + "\" found: \""
                            + result + "\".");
                } else {
                    System.out.println("Sorry, no matching entry in the table.");
                }

            } else {
                /* A password was supplied by command line, so we use it. */
                String result;
                result = table.get(password);
                if (result != null) {
                    System.out.println("Matchin entry to \"" + password + "\" found: \""
                            + result + "\".");
                } else {
                    System.out.println("Sorry, no matching entry in the table.");
                }
            }
        }
    }

    /**
     * This method prints the help message.
     */
    public static void printHelpMessage() {
        System.out.print(
                "AlgoProjekt Version 1.0: \n"
                + "Parameters: [{-h, --help]}\n"
                + "[{-f FILE, --file FILE}]\n"
                + "[{-o FILE, --output FILE}]\n"
                + "[{-l LENGTH, --length LENGTH}]\n"
                + "[{-c CHARACTERS, --characters CHARACTERS]}\n"
                + "[{-g, --generate}]\n"
                + "[{-r THRESHOLD, --reseed THRESHOLD}]\n"
                + "[{-n AMOUNT, --number AMOUNT}]\n"
                + "[{-i, --interactive]}\n"
                + "[{-p PASSWORD, --password PASSWORD}]\n"
                + "-h, --help: Print this help message.\n"
                + "-f FILE, --file FILE: The preset path to the file the hash table is to be read from.\n"
                + "-o FILE, --output FILE: The preset path to the file the hash table is to be written to.\n"
                + "-l LENGTH, --length LENGTH: The preset length you want the generated passwords to be.\n"
                + "-c CHARACTERS --characters: CHARACTERS The characters you want the generated passwords to consist of.\n"
                + "-g, --generate: Tells the program to generate a hash table with AMOUNT passwords and then write it to the value of the -o or --output argument.\n"
                + "-r THRESHOLD, --reseed THRESHOLD: The threshold for reseeding the internal PRNG.\n"
                + "-n AMOUNT, --number AMOUNT: The number of passwords to generate and put into the hash table.\n"
                + "-i, --interactive: Makes the program go into interactive mode with a text based menu to control it.\n"
                + "-p PASSWORD, --password PASSWORD: The preset password for testing the hash table when generating.\n"
        );
    }

}
