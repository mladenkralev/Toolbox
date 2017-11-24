import exceptions.ReadXMLTestException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by MLKR on 23.11.2017 г..
 */
public class ReadXMLTests {
    private static Pattern p = Pattern.compile("tests=\"(\\d+)\"");

    public static void main(String[] args) throws ReadXMLTestException {
        if (args.length == 1) {

            for (String argument : args) {
                System.out.println("Arguments passed " + argument);
            }

            final InnerSummingClass a = new InnerSummingClass();

            try (Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
                paths.filter(Files::isRegularFile).forEach(file -> {
                    Scanner s = null;
                    try {
                        s = new Scanner(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String nextMatch = s.findWithinHorizon(p, 0);
                    if (nextMatch == null) {
                        throw new IllegalArgumentException("In the dir that is provided, no match was found!");
                    }

                    System.out.println(String.format("\nFile: %s regex found: %s", file.toAbsolutePath(), nextMatch));

                    Matcher matcher = p.matcher(nextMatch);
                    boolean find = matcher.find();
                    String number = matcher.group(1);

                    int temp = Integer.parseInt(number);

                    System.out.println(String.format("%s + %s =", a.number, temp));

                    a.number += Integer.parseInt(number);

                    System.out.print(a.number);
                });
            } catch (IOException e) {
                new ReadXMLTestException("Canno't oppen stream to " + Paths.get(args[0]));
            }

            System.out.println("\nEverything is: " + a.number);
        } else {
            System.out.println("Directory argument is not supplied");
        }
    }

    static class InnerSummingClass {
        int number;
    }
}
