import exceptions.ReadXMLTestException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by MLKR on 23.11.2017 г..
 */
public class ReadXMLTests {
    private static Pattern p = Pattern.compile("tests=\"(\\d+)\"");

    public static void main(String[] args) throws ReadXMLTestException, IOException {
        Path filePath = Paths.get("C:\\test\\toolbox\\ssss.txt");
        File file = new File(filePath.toAbsolutePath().toString());
        if(!file.exists())
        System.out.println(file);
    }

    static class InnerSummingClass {
        int number;
    }
}
