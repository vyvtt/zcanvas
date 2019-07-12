/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thuyv
 */
public class ImageHelper {

//    public static void getDominantColor(String url) throws IOException {
//        BufferedImage image = ImageIO.read(new URL(url));
//
//        int height = image.getHeight();
//        int width = image.getWidth();
//
//        Map m = new HashMap();
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                int rgb = image.getRGB(i, j);
//                int[] rgbArr = getRGBArr(rgb);
//                // Filter out grays....                
//                if (!isGray(rgbArr)) {
//                    Integer counter = (Integer) m.get(rgb);
//                    if (counter == null) {
//                        counter = 0;
//                    }
//                    counter++;
//                    m.put(rgb, counter);
//                }
//            }
//        }
//        String colourHex = getMostCommonColour(m);
//        System.out.println(colourHex);
//    }
//    public static String getMostCommonColour(Map map) {
//        List<Map.Entry> list = new LinkedList(map.entrySet());
//        Collections.sort(list, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                return ((Comparable) ((Map.Entry) (o1)).getValue())
//                        .compareTo(((Map.Entry) (o2)).getValue());
//            }
//        });
//
////        int i = 0;
////        for (Map.Entry entry : list) {
////            int tmpRGB = (Integer) entry.getKey();
////            int[] rgb = getRGBArr((Integer) entry.getKey());
////            System.out.println(i + ": #" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]));
////            getHSBFromRGB(tmpRGB);
////            i++;
////        }
//        List<String> commonColorList = new ArrayList<>();
//
//        int count = 1;
//        int countPixel = 0;
//        int totalR = 0, totalG = 0, totalB = 0;
//        while (commonColorList.size() <= 3 && count < list.size()) {
//            Map.Entry entry = (Map.Entry) list.get(list.size() - count);
//            int rgb = (Integer) entry.getKey();
//            int[] rgbArr = getRGBArr(rgb);
//            totalR += rgbArr[0];
//            totalG += rgbArr[1];
//            totalB += rgbArr[2];
//            countPixel++;
//
//            String hueDegree = getHSBFromRGB(rgb);
//            if (!commonColorList.contains(hueDegree)) {
//                commonColorList.add(hueDegree);
//                System.out.println(hueDegree);
//                System.out.println("average of " + countPixel + " - #"
//                        + Integer.toHexString(totalR / countPixel)
//                        + Integer.toHexString(totalG / countPixel)
//                        + Integer.toHexString(totalB / countPixel));
//                totalB = totalG = totalR = countPixel = 0;
//            }
//            count++;
//        }
//
//        System.out.println("count: " + count);
//
////        int minCount = 100;
////        if (list.size() < 100) {
////            minCount = list.size();
////        }
////        
////        int startColor;
////        for (int j = 1; j < minCount; j++) {
////            Map.Entry entry = (Map.Entry) list.get(list.size() - j);
////            int rgb = (Integer) entry.getKey();
////
////            String hueDegree = getHSBFromRGB(rgb);
////            if (!commonColorList.contains(hueDegree)) {
////                commonColorList.add(hueDegree);
////            }
////        }
//        System.out.println("///////////");
//        for (String string : commonColorList) {
//            System.out.println(string);
//        }
//
////        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
////        int tmpRGB = (Integer) me.getKey();
////        int[] rgb = getRGBArr((Integer) me.getKey());
////        System.out.println("1: " + "#" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]));
////
////        
////
////        me = (Map.Entry) list.get(list.size() - 2);
////        rgb = getRGBArr((Integer) me.getKey());
////        System.out.println("2 " + "#" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]));
////
////        me = (Map.Entry) list.get(list.size() - 3);
////        rgb = getRGBArr((Integer) me.getKey());
////        System.out.println("3: " + "#" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]));
////        return "#" + Integer.toHexString(rgb[0]) + Integer.toHexString(rgb[1]) + Integer.toHexString(rgb[2]);
//        return "#";
//    }
//    public static boolean isGray(int[] rgbArr) {
//        int rgDiff = rgbArr[0] - rgbArr[1];
//        int rbDiff = rgbArr[0] - rgbArr[2];
//        // Filter out black, white and grays...... (tolerance within 10 pixels)
//        int tolerance = 10;
//        if (rgDiff > tolerance || rgDiff < -tolerance) {
//            if (rbDiff > tolerance || rbDiff < -tolerance) {
//                return false;
//            }
//        }
//        return true;
//    }
//    private static BufferedImage resize(final URL url, final Dimension size) throws IOException {
//        final BufferedImage image = ImageIO.read(url);
//        final BufferedImage resized = new BufferedImage(size.width, size.height, BufferedImage.TYPE_4BYTE_ABGR);
//        final Graphics2D g = resized.createGraphics();
//        g.drawImage(image, 0, 0, size.width, size.height, null);
//        g.dispose();
//        return resized;
//    }
//    private static float[] RGBToHSB(int rgb) {
//        // hsb
//        float hsb[] = new float[3];
//        int r = (rgb >> 16) & 0xFF;
//        int g = (rgb >> 8) & 0xFF;
//        int b = (rgb) & 0xFF;
//        Color.RGBtoHSB(r, g, b, hsb);
//        return hsb;
//    }
//    private static String getHSBFromRGB(int rgb) {
//        // hsb
//        float hsb[] = new float[3];
//        int r = (rgb >> 16) & 0xFF;
//        int g = (rgb >> 8) & 0xFF;
//        int b = (rgb) & 0xFF;
//        Color.RGBtoHSB(r, g, b, hsb);
//        //
//        if (hsb[1] < 0.1 && hsb[2] > 0.9) {
////            System.out.println("nearly while");
//            return "white";
//        } else if (hsb[2] < 0.1) {
//            return "black";
//        } else {
//            float deg = hsb[0] * 360;
////            System.out.println("degree: " + deg);
//            if (deg >= 0 && deg < 30) {
//                return "red";
//            } else if (deg >= 30 && deg < 60) {
//                return "orange";
//            } else if (deg >= 60 && deg < 90) {
//                return "yellow";
//            } else if (deg >= 90 && deg < 150) {
//                return "green";
//            } else if (deg >= 150 && deg < 210) {
//                return "cyan";
//            } else if (deg >= 210 && deg < 270) {
//                return "blue";
//            } else if (deg >= 270 && deg < 330) {
//                return "megatan";
//            } else {
//                return "red";
//            }
//        }
//    }
    public static int[] getRGBArr(int pixel) {
//        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue};
    }

    private static boolean isBackground(int[] checkRGBArray, int baseR, int baseG, int baseB) {
        int tolerance = Constant.IMG_BG_TOLERANCE;
        return (checkRGBArray[0] > (baseR - tolerance) && checkRGBArray[0] < (baseR + tolerance))
                && (checkRGBArray[1] > (baseG - tolerance) && checkRGBArray[1] < (baseG + tolerance))
                && (checkRGBArray[2] > (baseB - tolerance) && checkRGBArray[2] < (baseB + tolerance));
    }

    public static List<int[]> getPixelFromImage(BufferedImage image, boolean getBackground) {

        if (image == null) {
            System.out.println("Null BufferedImage");
            return new ArrayList<>();
        }
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

            if (!getBackground) { // ko lấy background => loại những pixel tương đồng pixel đầu tiên
                if (isFristPixel) {
                    backgroundR = rgbArr[0];
                    backgroundG = rgbArr[1];
                    backgroundB = rgbArr[2];
                    isFristPixel = false;
                }

                if (!isBackground(rgbArr, backgroundR, backgroundG, backgroundB)) {
                    list.add(rgbArr);
                }
            } else {
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

    // Split the RGB space into a 3x3x3 grid that produces 27 total histogram buckets
//    private final static int DIMENTION_MAX = 256;
//    private final static int BUCKET_PER_DIMENSION = 3;
//    private final static int BUCKET_SIZE = DIMENTION_MAX / BUCKET_PER_DIMENSION;
    private static String getKeyForPixel(int[] rbg) {
        int redBucket = (int) Math.floor(rbg[0] / Constant.IMG_BUCKET_SIZE);
        int greenBucket = (int) Math.floor(rbg[1] / Constant.IMG_BUCKET_SIZE);
        int blueBucket = (int) Math.floor(rbg[2] / Constant.IMG_BUCKET_SIZE);
        return redBucket + ":" + greenBucket + ":" + blueBucket;
    }

    public static String getColorPaletteFromImage(BufferedImage image, boolean getBackground) {

        Map<String, List<int[]>> bucketMap = new HashMap();
        List<int[]> pixels = null;
        List<int[]> pixelsInBucket = null;
        int[] pixel = new int[3];
        String pixelKey = null;

        pixels = getPixelFromImage(image, getBackground);

        // Skip 30 pixel 1 lần
        int skip = Constant.IMG_SKIP_PIXEL;
        for (int i = 0; i < pixels.size() - (skip + 1); i = i + skip) {
            pixel = pixels.get(i);
            pixelKey = getKeyForPixel(pixel);

            pixelsInBucket = bucketMap.get(pixelKey);
            if (pixelsInBucket == null) {
                pixelsInBucket = new ArrayList<>();
            }
            pixelsInBucket.add(pixel);
            bucketMap.put(pixelKey, pixelsInBucket);
        }

        // sort bucket by size of list pixels
        List<Map.Entry> listBucket = new ArrayList<>(bucketMap.entrySet());
        Collections.sort(listBucket, (Object o1, Object o2) -> {
            int size1 = ((List<int[]>) (((Map.Entry) o1).getValue())).size();
            int size2 = ((List<int[]>) (((Map.Entry) o2).getValue())).size();
            return ((Comparable) size1).compareTo(size2);
        });

        // lấy 5 bucket có số lượng pixel cao nhất -> tính màu trung bình của bucket đó
        int countPalleteColor = Constant.IMG_PALETTE_SIZE + 1;
        Map.Entry<String, List<int[]>> bucket = null;
        int[] averageColor = new int[3];

        if (listBucket.size() < countPalleteColor) {
            countPalleteColor = listBucket.size();
        }

        StringBuilder paletteString = new StringBuilder();
        for (int i = 1; i < countPalleteColor; i++) {
            bucket = listBucket.get(listBucket.size() - i);
            averageColor = calculateAverageColor(bucket.getValue());

//            Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
//            Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
//            Blue = Blue & 0x000000FF; //Mask out anything not blue.
            int color = 0xFF000000 | (averageColor[0] << 16) & 0x00FF0000 | (averageColor[1] << 8) & 0x0000FF00 | averageColor[2] & 0x000000FF; //0xFF000000 for 100% Alpha. Bitwise OR everything together.

//            paletteString.append(getColorString(averageColor));
            paletteString.append(String.valueOf(color));
            paletteString.append(";");

        }
        System.out.println("in: " + paletteString.toString());
        return paletteString.toString();
    }

    public static int convertHex2Int(String hex) {
        Color color = Color.decode(hex);
        int red = (color.getRed() << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        int green = (color.getGreen() << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        int blue = color.getBlue() & 0x000000FF; //Mask out anything not blue.
        return 0xFF000000 | red | green | blue;
    }

    private static String getColorString(int[] rgb) {
        return String.format("#%02x%02x%02x", rgb[0], rgb[1], rgb[2]);
    }

    public static String convertColorInt2Hex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
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

    public static int[] rgb2lab(int R, int G, int B) {

        float r, g, b, X, Y, Z, fx, fy, fz, xr, yr, zr;
        float Ls, as, bs;
        
        // Constant
        float eps = 216.f / 24389.f;
        float k = 24389.f / 27.f;

        // White D50 (standard daylight) trong hệ XYZ 
        // -> hệ số illuminant (chiếu sáng) cần thiết khi convert CIELab (Chromatic Adaptation)
        // ref: http://www.brucelindbloom.com/index.html?Eqn_ChromAdapt.html
        float Xr = 0.964221f;  
        float Yr = 1.0f;
        float Zr = 0.825211f;

        // RGB to XYZ ---------------------------------------
        // http://www.brucelindbloom.com/index.html?Eqn_RGB_to_XYZ.html
        // 1. Inverse Companding
        // 2. 
        
        // input RGB có range [0;255] --> convert thành range [0;1]
        r = R / 255.f;
        g = G / 255.f;
        b = B / 255.f;

        // Giả định sử dụng hệ standard RGB aka sRGB (D65)
        // Inverse sRGB Companding
        if (r <= 0.04045) {
            r = r / 12;
        } else {
            r = (float) Math.pow((r + 0.055) / 1.055, 2.4);
        }

        if (g <= 0.04045) {
            g = g / 12;
        } else {
            g = (float) Math.pow((g + 0.055) / 1.055, 2.4);
        }

        if (b <= 0.04045) {
            b = b / 12;
        } else {
            b = (float) Math.pow((b + 0.055) / 1.055, 2.4);
        }
        
        /**
         * Matrix: http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
         * sRGB and D50 white
         * 
         * X         0.436052025     0.385081593     0.143087414     R
         * Y    =    0.222491598     0.71688606      0.060621486     G
         * Z         0.013929122     0.097097002     0.71418547      B
         */
        X = 0.436052025f * r + 0.385081593f * g + 0.143087414f * b;
        Y = 0.222491598f * r + 0.71688606f * g + 0.060621486f * b;
        Z = 0.013929122f * r + 0.097097002f * g + 0.71418547f * b;

        // XYZ to Lab
        xr = X / Xr;
        yr = Y / Yr;
        zr = Z / Zr;

        if (xr > eps) {
            fx = (float) Math.pow(xr, 1 / 3.);
        } else {
            fx = (float) ((k * xr + 16.) / 116.);
        }

        if (yr > eps) {
            fy = (float) Math.pow(yr, 1 / 3.);
        } else {
            fy = (float) ((k * yr + 16.) / 116.);
        }

        if (zr > eps) {
            fz = (float) Math.pow(zr, 1 / 3.);
        } else {
            fz = (float) ((k * zr + 16.) / 116);
        }

        Ls = (116 * fy) - 16;
        as = 500 * (fx - fy);
        bs = 200 * (fy - fz);

        int[] lab = new int[3];
        lab[0] = (int) (2.55 * Ls + .5);
        lab[1] = (int) (as + .5);
        lab[2] = (int) (bs + .5);
        return lab;
    }

    /**
     * Computes the difference between two RGB colors by converting them to the
     * L*a*b scale and comparing them using the CIE76 algorithm {
     * http://en.wikipedia.org/wiki/Color_difference#CIE76}
     */
    public static double getColorDifference(int a, int b) {
        int r1, g1, b1, r2, g2, b2;

        Color color1 = new Color(a);
        Color color2 = new Color(b);

        // Returns the color r,g,b component in the range 0-255 in the default sRGB
        r1 = color1.getRed();
        g1 = color1.getGreen();
        b1 = color1.getBlue();
        r2 = color2.getRed();
        g2 = color2.getGreen();
        b2 = color2.getBlue();

        int[] lab1 = rgb2lab(r1, g1, b1);
        int[] lab2 = rgb2lab(r2, g2, b2);
        
        return Math.sqrt(
                  Math.pow(lab2[0] - lab1[0], 2) 
                + Math.pow(lab2[1] - lab1[1], 2) 
                + Math.pow(lab2[2] - lab1[2], 2));
    }

    // inputPalatte, currentPalette
    public static double comparePalette2Palette(List<String> palette1, List<String> palette2) {

        int tolerance = Constant.IMG_DELTA_E_TOLERANCE;
        
        if (palette2.size() <= 3) {
            return -1;
        } else if (palette2.size() == 4) {
            tolerance = 10;
        }

        int count = 0;
        double totalDeltaE = 0;

        for (int i = 0; i < palette1.size(); i++) {
            for (int j = 0; j < palette2.size(); j++) {
                double diff = getColorDifference(Integer.parseInt(palette1.get(i)), Integer.parseInt(palette2.get(j)));
                totalDeltaE += diff;
                if (diff <= tolerance) {
                    j = palette2.size();
                    count++;
                }
            }
        }

        // lấy delta E của những palette có (tổng màu -1) màu giống palette input
        if (count >= palette2.size() - 1) {
            return totalDeltaE;
        }
        return -1;
    }

    public static double compareColor2Palette(int color, List<String> palette2) {

        if (palette2.size() <= 2) {
            return -1;
        }

        double smallestDeltaE = 0;

        for (int j = 0; j < palette2.size(); j++) {
            double diff = getColorDifference(color, Integer.parseInt(palette2.get(j)));

            if (j == 0) {
                smallestDeltaE = diff;
            }

            if (diff < smallestDeltaE) {
                smallestDeltaE = diff;
            }
        }
        
        System.out.println(smallestDeltaE);
        // lấy delta E nhỏ nhất của palette và nhỏ hơn config 
        if (smallestDeltaE <= Constant.IMG_DELTA_E_TOLERANCE_SINGLE) {
            return smallestDeltaE;
        }
        return -1;
    }
}
