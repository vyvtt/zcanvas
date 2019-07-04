/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import project.xmlchecker.XmlSyntaxChecker;

/**
 *
 * @author thuyv
 */
public class StringHelper implements Serializable {
    
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

    public static String refineHtml(String src) {
        src = removeMiscellaneousTags(src);

        XmlSyntaxChecker checker = new XmlSyntaxChecker();
        src = checker.checkSyntax(src);
        return src;
    }

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

    public static BigInteger convertStringToBigInteger(String s) {
        try {
            if (s == null) {
                Logger.getLogger(StringHelper.class.getName()).log(Level.SEVERE, "null string to convert to BigInteger => set to BigInteger.ZERO");
                return BigInteger.ZERO;
            }
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            Logger.getLogger(StringHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return BigInteger.ZERO;
        }
    }    

    // kiểm tra trùng url => nếu đã có url trong db => sp đã tồn tại => return id
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
