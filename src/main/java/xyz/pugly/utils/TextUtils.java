package xyz.pugly.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    // Converts to title case
    public static String convertToTitle(String text) {
        String[] words = text.split("_");
        String out = "";

        for (String s : words) {
            out += Character.toTitleCase(s.toCharArray()[0]);
            out += s.toLowerCase().substring(1, s.length()) + " ";
        }
        return out.substring(0, out.length()-1);
    }

    private static Pattern HEX_PATTERN = Pattern.compile("#[1234567890ABCDEFabcdef]{6}");

    //#123123 for hex and & for normal mc colors
    public static String colorize(String message) {
        Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', message));
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of(matcher.group(0)).toString());
        }

        return matcher.appendTail(buffer).toString();
    }

    public static List<String> splitIntoLine(String input, int maxCharInLine, String prefix){

        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        ArrayList<String> out = new ArrayList<>();
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            while(word.length() > maxCharInLine){
                output.append(word.substring(0, maxCharInLine - lineLen)).append("\n");
                word = word.substring(maxCharInLine-lineLen);
                lineLen = 0;
            }

            if (lineLen + word.length() > maxCharInLine) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word).append(" ");

            lineLen += word.length() + 1;
        }
        for (String s : output.toString().split("\n"))
            out.add(prefix + s);
        return out;
    }

}
