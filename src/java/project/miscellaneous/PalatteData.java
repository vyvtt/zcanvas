/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.miscellaneous;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import project.crawler.Demo;
import project.dao.CanvasDAO;
import project.jaxb.Canvas;
import project.jaxb.Categories;
import project.utils.Constant;
import project.utils.ImageHelper;
import project.utils.StringHelper;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class PalatteData {

    public static String palatteImage;
    public static List<String> palatteColor;
    public static List<Canvas> topCanvas;

    private static List<Canvas> listCanvas;

    public static void initListCanvas() {
        CanvasDAO canvasDAO = new CanvasDAO();
        listCanvas = canvasDAO.getAllCanvas();
        topCanvas = new ArrayList<>();
    }
    
    public static void getRandomImgFromUnsplash() {
        try {
            String urlStr = "https://api.unsplash.com/photos/random";
            urlStr += "?client_id=b303f5c156451e2af3a7ba22ad662f8b8e90b276998a563e464b682cb4403e2b";
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            parseJSON(content.toString());

        } catch (IOException ex) {
            Logger.getLogger(PalatteData.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    private static void parseJSON(String json) {
        System.out.println("json: " + json);

//        Pattern codePattern = Pattern.compile("\"description\"\\s*:\\s*\"([^,]*)\",");
        Pattern messagePattern = Pattern.compile("\"small\"\\s*:\\s*\"([^,]*)\",");

//        Matcher code_matcher = codePattern.matcher(json);
        Matcher message_matcher = messagePattern.matcher(json);

        if (message_matcher.find()) {
            System.out.println("match!");
            palatteImage = message_matcher.group(1);
            processImg();
        }
    }
    
    private static void processImg() {
        try {
            BufferedImage image = ImageIO.read(new URL(palatteImage));
            String palatteColorsStr = ImageHelper.getColorPaletteFromImage(image, true);
            
            System.out.println("input palette: " + palatteColorsStr);
            System.out.println("done get palatte");
            
            List<String> inputPalatte = Arrays.asList(palatteColorsStr.split("\\s*;\\s*"));
            
            palatteColor = new ArrayList<>();
            for (String colorInt : inputPalatte) {
                palatteColor.add(ImageHelper.convertColorInt2Hex(Integer.parseInt(colorInt)));
            }

            for (String string : palatteColor) {
                System.out.println(string);
            }
            
            System.out.println("select canvas ---");
            updateCanvasMatchingPalatte();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void updateNewPalatte() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 99 + 1);

        String url = "https://www.millionshade.com/color-palette-" + String.format("%04d", randomNum) + "/";

        System.out.println("New palatte: " + url);

        String htmlContent = XMLHelper.parseHTML(url, "class=\"site-content clr\"", "class=\"post-tags clr\"");
        htmlContent = StringHelper.refineHtml(htmlContent);

        System.out.println(htmlContent);

        String tmpImage = "";
        List<String> tmpColor = new ArrayList<>();

        XMLEventReader reader = null;
        boolean isInsideColor = false;

        try {
            reader = XMLHelper.getXMLEventReaderFromString(htmlContent);
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();
                    String tagName = element.getName().getLocalPart();

                    if ("img".equals(tagName)) {
                        Attribute attSrc = element.getAttributeByName(new QName("src"));
                        if (attSrc != null) {
                            tmpImage = attSrc.getValue();
                        }
                    }

                    if ("ul".equals(tagName)) {
                        Attribute att = element.getAttributeByName(new QName("class"));
                        if (att != null) {
                            if ("color".equals(att.getValue())) {
                                isInsideColor = true;
                            }
                        }
                    }

                    if ("span".equals(tagName) && isInsideColor) {
                        Attribute att = element.getAttributeByName(new QName("style"));
                        if (att != null) {
                            String tmp = att.getValue();
//                            int intColor = ImageHelper.convertHex2Int(tmp.substring(tmp.indexOf(":") + 1, tmp.indexOf(";")));
//                            String strColor = String.valueOf(intColor);
                            tmpColor.add(tmp.substring(tmp.indexOf(":") + 1, tmp.indexOf(";")));
                        }
                    }
                } // end if start
            } // end while reader

            System.out.println("-------------");
            System.out.println(tmpImage);
            for (String string : tmpColor) {
                System.out.println(string);
            }

            if (!tmpImage.isEmpty() && !tmpColor.isEmpty()) {
                palatteImage = new String(tmpImage);
                palatteColor = new ArrayList<>(tmpColor);

                updateCanvasMatchingPalatte();
            }

        } catch (UnsupportedEncodingException | XMLStreamException e) {
            Logger.getLogger(PalatteData.class.getName()).log(Level.SEVERE, "Fail to update palette", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException ex) {
                    Logger.getLogger(PalatteData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void updateCanvasMatchingPalatte() {
        System.out.println("begin update canvas list");

        List<Canvas> result = new ArrayList<>();

        List<String> inputColor = new ArrayList<>();
        for (String tmp : palatteColor) {
            int intColor = ImageHelper.convertHex2Int(tmp);
            inputColor.add(String.valueOf(intColor));
        }

        for (Canvas canvas : listCanvas) {

            List<String> currentPalette = Arrays.asList(canvas.getColorPalatte().split("\\s*;\\s*"));

            double deltaE = ImageHelper.comparePalette2Palette(inputColor, currentPalette);

            if (deltaE != -1) {
                System.out.println("Delta E != 1");
                canvas.setDeltaE(deltaE);
                List<String> currentCanvasColor = new ArrayList<>();

                for (String colorInt : currentPalette) {
                    currentCanvasColor.add(ImageHelper.convertColorInt2Hex(Integer.parseInt(colorInt)));
                }
                canvas.setCanvasColors(currentCanvasColor);
                result.add(canvas);
            }
        }

        System.out.println("done delta E -> sort");
        Collections.sort(result, (c1, c2) -> {
            return ((Comparable) c1.getDeltaE()).compareTo(c2.getDeltaE());
        });
        System.out.println("done sort");
        List<Canvas> topResult = result.subList(0, Math.min(result.size(), 10));

        System.out.println("palatte total: " + result.size());
        System.out.println("palatte top: " + topResult.size());

        if (topResult.isEmpty()) {
            System.out.println("enpty ---> find by each color");
            for (String colorHex : palatteColor) {
                System.out.println(colorHex);
                int currentColor = ImageHelper.convertHex2Int(colorHex);

                List<Canvas> tmpList = new ArrayList<>();
                for (Canvas canvas : listCanvas) {
                    List<String> currentPalette = Arrays.asList(canvas.getColorPalatte().split("\\s*;\\s*"));
                    double deltaE = ImageHelper.compareColor2Palette(currentColor, currentPalette);

                    if (deltaE != -1) {
                        canvas.setDeltaE(deltaE);
                        List<String> currentCanvasColor = new ArrayList<>();

                        for (String colorInt : currentPalette) {
                            currentCanvasColor.add(ImageHelper.convertColorInt2Hex(Integer.parseInt(colorInt)));
                        }
                        canvas.setCanvasColors(currentCanvasColor);
                        tmpList.add(canvas);
                    }
                }
                
                result.removeAll(tmpList);
                result.addAll(tmpList);
                
                System.out.println("result after hex: " + result.size());
            }

            System.out.println("done delta E EACH -> sort");
            Collections.sort(result, (c1, c2) -> {
                return ((Comparable) c1.getDeltaE()).compareTo(c2.getDeltaE());
            });
            System.out.println("done sort");
            topResult = result.subList(0, Math.min(result.size(), 10));
            System.out.println("palatte total: " + result.size());
            System.out.println("palatte top: " + topResult.size());
        }
        
        System.out.println(palatteImage);
        for (Canvas canvas : topResult) {
            System.out.println(canvas.getName());
            System.out.println(canvas.getDeltaE());
            System.out.println(canvas.getImage());
        }

        topCanvas = new ArrayList<>(topResult);
    }

    public static boolean isReady() {
        
        if (palatteImage == null || palatteColor == null || topCanvas == null) {
            System.out.println("something null");
            return false;
        }
        
        return (!palatteImage.isEmpty() && !palatteColor.isEmpty() && !topCanvas.isEmpty());
    }
}
