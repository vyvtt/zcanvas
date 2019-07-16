package project.utils;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author thuyv
 */
public class StringHelper implements Serializable {
    
    /**
     * Un-escaped special character in a String before insert to DB
     * @param input
     * @return Normal readable String
     */
    public static String unescapedSpecialCharacters(String input) {
        if (input != null) {
            return input
                    .replace("&amp;", "&")
                    .replace("&quot;", "\"")
                    .replace("&#8220;", "\"")
                    .replace("&#8221;", "\"")
                    .replace("&#8217;", "'")
                    .replace("&#8211;", "-")
                    .replace("&#8216;", "'")
                    .replace("&#8230;", "...")
                    .replace("&#038;", "&");
        }
        return null;
    }

    /**
     * Well-formed HTML tag
     * @param src
     * @return 
     */
    public static String refineHtml(String src) {
        src = removeMiscellaneousTags(src);

        XmlSyntaxChecker checker = new XmlSyntaxChecker();
        src = checker.checkSyntax(src);
        return src;
    }

    /**
     * Remove unnecessary tag (script, comment, tab)
     * @param src
     * @return 
     */
    public static String removeMiscellaneousTags(String src) {
        String result = src;

        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");

        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");

        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");

        return result;
    }

    /**
     * Convert String to BigInteger
     * @param s
     * @return BigInter or ZERO if exception caught
     */
    public static BigInteger convertStringToBigInteger(String s) {
        try {
            if (s == null) {
                Logger.getLogger(StringHelper.class.getName()).log(Level.INFO, "NULL string to convert to BigInteger => set to BigInteger.ZERO");
                return BigInteger.ZERO;
            }
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            Logger.getLogger(StringHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return BigInteger.ZERO;
        }
    }    

    /**
     * Hash product's URL for checking for duplicate later
     * @param content Product's URL
     * @return 
     */
    public static int hashString(String content) {
        int mod = 1000000007;
        int base = 30757; // random prime number

        int hashValue = 0;
        for (int i = 0; i < content.length(); i++) {
            hashValue = (int) (((long) hashValue + base + (long) content.charAt(i)) % mod);
        }
        return hashValue;
    }
}
