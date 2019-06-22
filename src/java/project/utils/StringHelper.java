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

    public static String HOST_SOYN = "";
    public static String HOST_MOPI = "";

    public static String getHostFromConfigFile(String filePath) {
        try {

            // parse file to DOM
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            // get XPath
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            // get config
            String expression = "/host";
            String host = (String) xPath.evaluate(expression, document, XPathConstants.STRING);
            return host.trim();

        } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) {
            Logger.getLogger(StringHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
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

//    public static boolean isAlphaCharacter(char x) {
//        return (x >= 'a' && x <= 'z');
//    }
//    
//    public static String getTagName(String content) {
//        // Thẻ tự đóng => trả về null
//        if (content.charAt(content.length() - 2) == '/') {
//            return null;
//        }
//        
//        String tagName = "";
//        int i = 1; //skip 0 là "<"
//        
//        if (content.charAt(1) == '/') {
//            tagName = "/";
//            i++;
//        }
//        
//        while (isAlphaCharacter(content.charAt(i))) {            
//            tagName = tagName + content.charAt(i);
//            i++;
//        }
//        
//        if (tagName.length() == 0) {
//            return null;
//        } else if ((tagName.length() == 1) && tagName.charAt(0) == '/') {
//            return null;
//        }
//        
//        return tagName;
//    }
//    
//    public static String fixString(String content) {
//        List<String> stack = new ArrayList<>();
//        List<Integer> li = new ArrayList<>();
//        List<String> tagToAdd = new ArrayList<>();
//        
//        int size = content.length();
//        int mark[] = new int[size];
//        Arrays.fill(mark, -1);
//        
//        int i = 0;
//        while (i < content.length()) {
//            if (content.charAt(i) == '<') { // tag đang mở, lấy hết content từ <...>
//                int j = i + 1;
//                String tmpTag = "" + content.charAt(j);
//                
//                while ((j < content.length()) && content.charAt(j) != '>') {                    
//                    tmpTag += content.charAt(j);
//                    j++;
//                }
//                
//                tmpTag += '>';
//                int current = j; // current là j, hiện tại là vị trí của >
//                i = j + 1;
//                
//                String tagName = getTagName(tmpTag);
//                
//                if (tagName != null) {
//                    if (tagName.charAt(0) != '/') { //thẻ mở
//                        stack.add(tagName);
//                        li.add(current);
//                    } else { // thẻ đóng
//                        while (stack.size() > 0) {                            
//                            if (stack.get(stack.size() - 1).equals(tagName.substring(1))) { // thẻ mở = thẻ đóng
//                                stack.remove(stack.size() - 1);
//                                li.remove(li.size() - 1);
//                                break;
//                            } else { // thẻ mở ko trùng thẻ đóng
//                                tagToAdd.add(stack.get(stack.size() - 1));
//                                mark[li.get(li.size() - 1)] = tagToAdd.size() - 1;
//                                
//                                stack.remove(stack.size() - 1);
//                                li.remove(li.size() - 1);
//                            }   
//                        } // end while
//                    } // end if else tagName là thẻ mở hay đóng
//                } // end if tagName null     
//            } // end if < 
//            else {
//                i++;
//            }
//        } // end while content.length
//        
//        while (stack.size() > 0) {
//            tagToAdd.add(stack.get(stack.size() - 1));
//            mark[li.get(li.size() - 1)] = tagToAdd.size() - 1;
//            stack.remove(stack.size() - 1);
//            li.remove(li.size() - 1);
//        }
//        
//        String newContent = "";
//        
//        for (int j = 0; j < content.length(); j++) {
//            newContent += content.charAt(j);
//            if (mark[j] != -1) {
//                newContent += "</" + tagToAdd.get(mark[j]) + ">";
//            }
//        }
//        
//        newContent = "<root>" + newContent + "</root>";
////        System.out.println("add root");
//        
//        return newContent;
//    }
}
