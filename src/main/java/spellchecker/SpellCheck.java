package spellchecker;

import java.io.IOException;
import java.io.PrintStream;

public class SpellCheck {
    public SpellCheck() {
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            showUsageMessage();

        } else {
            String filepath = "src/main/resources/spellchecker/";
            String inputFilepath = filepath + args[args.length - 1];
            String wordListFilepath = filepath + "wordlist.txt";
            Object hasher = new LousyStringHasher();
            PrintStream printStreamer = System.out;
            boolean var5 = false;

            for(int i = 0; i < args.length - 1; ++i) {
                if (args[i].equals("-degenerate")) {
                    hasher = new DegenerateStringHasher();
                } else if (args[i].equals("-lousy")) {
                    hasher = new LousyStringHasher();
                } else if (args[i].equals("-better")) {
                    hasher = new BetterStringHasher();
                } else if (args[i].equals("-quiet")) {
                    printStreamer = new PrintStream(new NullOutputStream());
                    var5 = true;
                } else if (args[i].equals("-wordlist")) {
                    ++i;
                    if (i >= args.length - 1) {
                        showUsageMessage();
                        return;
                    }
                    wordListFilepath = args[i];
                }
            }

            if (args[args.length - 1].charAt(0) == '-') {
                showUsageMessage();
            } else {
                try {
                    long startTime = System.currentTimeMillis();
                    (new Checker()).check(inputFilepath, wordListFilepath, (StringHasher)hasher, printStreamer);
                    long endTime = System.currentTimeMillis();
                    if (var5) {
                        System.out.println("Checker ran in " + (endTime - startTime) + "ms");
                    }
                } catch (IOException var11) {
                    var11.printStackTrace();
                }

            }
        }
    }

    private static void showUsageMessage() {
        System.out.println("Usage: java SpellCheck [options] inputFilename");
        System.out.println();
        System.out.println("    options");
        System.out.println("    -------");
        System.out.println("    -degenerate");
        System.out.println("        runs the spell checker with the degenerate word hashing algorithm");
        System.out.println();
        System.out.println("    -lousy");
        System.out.println("        runs the spell checker with a lousy word hashing algorithm (default)");
        System.out.println();
        System.out.println("    -better");
        System.out.println("        runs the spell checker with a better word hashing algorithm");
        System.out.println();
        System.out.println("    -quiet");
        System.out.println("        runs the spell checker without any output, reporting the total time");
        System.out.println("        taken to load the dictionary and perform the spell check");
        System.out.println();
        System.out.println("    -wordlist wordlistFilename");
        System.out.println("        runs the spell checker using the wordlist specified, rather than");
        System.out.println("        the default (wordlist.txt)");
        System.out.println();
        System.out.println("    example");
        System.out.println("    -------");
        System.out.println("    java SpellCheck -wordlist biglist.txt -better -quiet big-input.txt");
        System.out.println("        executes the spell checker using the wordlist 'biglist.txt', the");
        System.out.println("        better word hashing algorithm, in quiet mode (i.e. no output),");
        System.out.println("        on the input file 'big-input.txt'");
    }
}
