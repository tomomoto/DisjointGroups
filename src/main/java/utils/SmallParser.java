package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 26.03.2017.
 */
public class SmallParser {

    public static String parse(String stringToParse) throws InvalidStringException {
        return getParsedLine(stringToParse);
    }

    public static List<String> getStringsList(String currentString) {
        List<String> items = new ArrayList<>();
        items.addAll(Arrays.asList(currentString.split(";")));
        if (items.size() < 3) {
            switch (items.size()) {
                case 1:
                    items.add("");
                    items.add("");
                    break;
                case 2:
                    items.add("");
                    break;
            }
        }
        return items;
    }

    private static String getParsedLine(String stringToParse) throws InvalidStringException {
        String line = stringToParse.replaceAll("\"", "");
        if (checkSplittersCount(StringUtils.countMatches(line, ";"))) {
            List<String> items = getStringsList(line);
            if (checkNotNull(items) && checkSize(items))
                return line;
        }
        throw new InvalidStringException(line);
    }

    private static boolean checkSplittersCount(int count) {
        if (count == 2)
            return true;
        return false;
    }

    private static boolean checkSize(List<String> items) {
        if (items.size() == 3)
            return true;
        return false;
    }

    private static boolean checkNotNull(List<String> items) {
        if (items != null)
            return true;
        return false;
    }

}
