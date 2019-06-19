/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author thuyv
 */
public class ImageHelper {

    public static void getDominantColor(String url) throws IOException {
        BufferedImage image = ImageIO.read(new URL(url));

        int height = image.getHeight();
        int width = image.getWidth();

        Map m = new HashMap();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int[] rgbArr = getRGBArr(rgb);
                // Filter out grays....                
                if (!isGray(rgbArr)) {
                    Integer counter = (Integer) m.get(rgb);
                    if (counter == null) {
                        counter = 0;
                    }
                    counter++;
                    m.put(rgb, counter);
                }
            }
        }
        String colourHex = getMostCommonColour(m);
        System.out.println(colourHex);
    }

    public static String getMostCommonColour(Map map) {
        List<Map.Entry> list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

//        int i = 0;
//        for (Map.Entry entry : list) {
//            int tmpRGB = (Integer) entry.getKey();
//            int[] rgb = getRGBArr((Integer) entry.getKey());
//            System.out.println(i + ": #" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]));
//            getHSBFromRGB(tmpRGB);
//            i++;
//        }
        List<String> commonColorList = new ArrayList<>();

        int count = 1;
        int countPixel = 0;
        int totalR = 0, totalG = 0, totalB = 0;
        while (commonColorList.size() <= 3 && count < list.size()) {
            Map.Entry entry = (Map.Entry) list.get(list.size() - count);
            int rgb = (Integer) entry.getKey();
            int[] rgbArr = getRGBArr(rgb);
            totalR += rgbArr[0];
            totalG += rgbArr[1];
            totalB += rgbArr[2];
            countPixel++;

            String hueDegree = getHSBFromRGB(rgb);
            if (!commonColorList.contains(hueDegree)) {
                commonColorList.add(hueDegree);
                System.out.println(hueDegree);
                System.out.println("average of " + countPixel + " - #"
                        + Integer.toHexString(totalR / countPixel)
                        + Integer.toHexString(totalG / countPixel)
                        + Integer.toHexString(totalB / countPixel));
                totalB = totalG = totalR = countPixel = 0;
            }
            count++;
        }

        System.out.println("count: " + count);

//        int minCount = 100;
//        if (list.size() < 100) {
//            minCount = list.size();
//        }
//        
//        int startColor;
//        for (int j = 1; j < minCount; j++) {
//            Map.Entry entry = (Map.Entry) list.get(list.size() - j);
//            int rgb = (Integer) entry.getKey();
//
//            String hueDegree = getHSBFromRGB(rgb);
//            if (!commonColorList.contains(hueDegree)) {
//                commonColorList.add(hueDegree);
//            }
//        }
        System.out.println("///////////");
        for (String string : commonColorList) {
            System.out.println(string);
        }

//        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
//        int tmpRGB = (Integer) me.getKey();
//        int[] rgb = getRGBArr((Integer) me.getKey());
//        System.out.println("1: " + "#" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]));
//
//        
//
//        me = (Map.Entry) list.get(list.size() - 2);
//        rgb = getRGBArr((Integer) me.getKey());
//        System.out.println("2 " + "#" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]));
//
//        me = (Map.Entry) list.get(list.size() - 3);
//        rgb = getRGBArr((Integer) me.getKey());
//        System.out.println("3: " + "#" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]));
//        return "#" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]);
        return "#";
    }

    private static float[] RGBToHSB(int rgb) {
        // hsb
        float hsb[] = new float[3];
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb) & 0xFF;
        Color.RGBtoHSB(r, g, b, hsb);
        return hsb;
    }

    private static String getHSBFromRGB(int rgb) {
        // hsb
        float hsb[] = new float[3];
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb) & 0xFF;
        Color.RGBtoHSB(r, g, b, hsb);
        //
        if (hsb[1] < 0.1 && hsb[2] > 0.9) {
//            System.out.println("nearly while");
            return "white";
        } else if (hsb[2] < 0.1) {
            return "black";
        } else {
            float deg = hsb[0] * 360;
//            System.out.println("degree: " + deg);
            if (deg >= 0 && deg < 30) {
                return "red";
            } else if (deg >= 30 && deg < 60) {
                return "orange";
            } else if (deg >= 60 && deg < 90) {
                return "yellow";
            } else if (deg >= 90 && deg < 150) {
                return "green";
            } else if (deg >= 150 && deg < 210) {
                return "cyan";
            } else if (deg >= 210 && deg < 270) {
                return "blue";
            } else if (deg >= 270 && deg < 330) {
                return "megatan";
            } else {
                return "red";
            }
        }
    }

    public static int[] getRGBArr(int pixel) {
//        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue};
    }

    private static boolean isBackground(int[] checkRGBArray, int baseR, int baseG, int baseB) {
        int tolerance = 50;
        return (checkRGBArray[0] > (baseR - tolerance) && checkRGBArray[0] < (baseR + tolerance))
                && (checkRGBArray[1] > (baseG - tolerance) && checkRGBArray[1] < (baseG + tolerance))
                && (checkRGBArray[2] > (baseB - tolerance) && checkRGBArray[2] < (baseB + tolerance));
    }

    public static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance) {
            if (rbDiff > tolerance || rbDiff < -tolerance) {
                return false;
            }
        }
        return true;
    }

    private static BufferedImage resize(final URL url, final Dimension size) throws IOException {
        final BufferedImage image = ImageIO.read(url);
        final BufferedImage resized = new BufferedImage(size.width, size.height, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D g = resized.createGraphics();
        g.drawImage(image, 0, 0, size.width, size.height, null);
        g.dispose();
        return resized;
    }

    private static List<int[]> getPixelFromImage(String url) throws IOException {

        // -------------
        BufferedImage image = ImageIO.read(new URL(url));

        int height = image.getHeight();
        int width = image.getWidth();

        List<int[]> list = new ArrayList<>();
        int pixelLength = 3;
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        int argb = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        int backgroundR = 0;
        int backgroundG = 0;
        int backgroundB = 0;

        boolean isFristPixel = true;

        for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
            argb = 0;
            argb += -16777216; // 255 alpha
            argb += ((int) pixels[pixel] & 0xff); // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red

            int alpha = (argb & 0xff000000) >> 24;
            red = (argb & 0x00ff0000) >> 16;
            green = (argb & 0x0000ff00) >> 8;
            blue = argb & 0x000000ff;
            int[] rgbArr = new int[]{red, green, blue};

            if (isFristPixel) {
                backgroundR = rgbArr[0];
                backgroundG = rgbArr[1];
                backgroundB = rgbArr[2];
                isFristPixel = false;
//                System.out.println("backfround: " + getColorString(firstRGBArr));
            }
            // Filter out grays                
//            if (!isGray(rgbArr)) {
////                if (!isBackground(firstRGBArr, rgbArr)) {
////                    list.add(rgbArr);
////                }
//                list.add(rgbArr);
//            }

            if (!isBackground(rgbArr, backgroundR, backgroundG, backgroundB)) {
                list.add(rgbArr);
            }

            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }
        return list;
    }

    private final static int DIMENTION_MAX = 256;
    private final static int BUCKET_PER_DIMENSION = 3;
    private final static int BUCKET_SIZE = DIMENTION_MAX / BUCKET_PER_DIMENSION;

    private static String getKeyForPixel(int[] rbg) {
        int redBucket = (int) Math.floor(rbg[0] / BUCKET_SIZE);
        int greenBucket = (int) Math.floor(rbg[1] / BUCKET_SIZE);
        int blueBucket = (int) Math.floor(rbg[2] / BUCKET_SIZE);
        return redBucket + ":" + greenBucket + ":" + blueBucket;
    }

    public static void binPixels(String url) throws IOException {

        Map<String, List<int[]>> bucketMap = new HashMap();
        List<int[]> pixels = getPixelFromImage(url);

        // Nhảy 30 pixel 1 lần
        List<int[]> listPixelsInBucket = null;
        for (int i = 0; i < pixels.size() - 31; i = i + 30) {
            int[] rbgArr = pixels.get(i);
            String key = getKeyForPixel(rbgArr);

            listPixelsInBucket = bucketMap.get(key);
            if (listPixelsInBucket == null) {
                listPixelsInBucket = new ArrayList<>();
            }
            listPixelsInBucket.add(rbgArr);
            bucketMap.put(key, listPixelsInBucket);
        }

        System.out.println(pixels.size());
        for (Map.Entry<String, List<int[]>> entry : bucketMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue().size());
        }

        // sort bucket by size of list pixels
        List<Map.Entry> listBucket = new ArrayList<>(bucketMap.entrySet());
        Collections.sort(listBucket, (Object o1, Object o2) -> {
            int size1 = ((List<int[]>) (((Map.Entry) o1).getValue())).size();
            int size2 = ((List<int[]>) (((Map.Entry) o2).getValue())).size();
            return ((Comparable) size1).compareTo(size2);
        });

        List<int[]> averageColorList = new ArrayList<>();

        // lấy 5 bucket có số lượng pixel cao nhất -> tính màu trung bình của bucket đó
        int countPalleteColor = 6;
        if (listBucket.size() < 6) {
            countPalleteColor = listBucket.size();
        }
        for (int i = 1; i < countPalleteColor; i++) {
            Map.Entry<String, List<int[]>> bucket = listBucket.get(listBucket.size() - i);
            int[] averageColor = calculateAverageColor(bucket.getValue());
            System.out.println("Average: " + averageColor[0] + "-" + averageColor[1] + "-" + averageColor[2]);
            System.out.println(getColorString(averageColor));
            averageColorList.add(averageColor);
        }
    }

    private static String getColorString(int[] rgb) {
        return String.format("#%02x%02x%02x", rgb[0], rgb[1], rgb[2]);
    }

    private static int[] calculateAverageColor(List<int[]> pixels) {
        int size = pixels.size();
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;

        for (int[] pixel : pixels) {
            totalRed += pixel[0];
            totalGreen += pixel[1];
            totalBlue += pixel[2];
        }

        return new int[]{totalRed / size, totalGreen / size, totalBlue / size};
    }

}
