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

            int color = 0xFF000000 | (averageColor[0] << 16) & 0x00FF0000 | (averageColor[1] << 8) & 0x0000FF00 | averageColor[2] & 0x000000FF; //0xFF000000 for 100% Alpha. Bitwise OR everything together.

            paletteString.append(String.valueOf(color));
            paletteString.append(";");

        }
        return paletteString.toString();
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
    
    /**
     * Computes the difference between two RGB colors by converting them to 
     * the L*a*b scale and comparing them using the CIE76 algorithm
     * Ref: http://en.wikipedia.org/wiki/Color_difference#CIE76
     * @param a Color 1, dạng int
     * @param b Color 2, dạng int
     * @return 
     */
    public static double getColorDifference(int a, int b) {
        Color color1 = new Color(a);
        Color color2 = new Color(b);

        // getReb/Green/Blue() returns the color r,g,b component in the range 0-255 in the default sRGB
        int[] lab1 = ColorHelper.convertRGBtoLab(color1.getRed(), color1.getGreen(), color1.getBlue());
        int[] lab2 = ColorHelper.convertRGBtoLab(color2.getRed(), color2.getGreen(), color2.getBlue());
        
        return Math.sqrt(
                  Math.pow(lab2[0] - lab1[0], 2) 
                + Math.pow(lab2[1] - lab1[1], 2) 
                + Math.pow(lab2[2] - lab1[2], 2));
    }

    /**
     * Computes the difference between two palette
     * @param palette1 Palette inputPalatte (input, thường là 5 color)
     * @param palette2 Palette cần so sánh, currentPalette (số color tùy thuộc)
     * @return tổng delta E cho những palette có độ phù hợp 4/5, hoặc -1 nếu ko phì hợp
     */
    public static double comparePalette2Palette(List<String> palette1, List<String> palette2) {

        int tolerance = Constant.IMG_DELTA_E_TOLERANCE;
        
        // Số màu: thường là 5
        if (palette2.size() <= 3) {
            // Màu trong palette ít hơn 3 -> return để tránh các trường hợp màu đơn sắc với deltaE nhỏ
            return -1;
        } else if (palette2.size() == 4) {
            // Màu trong palette ít = 4 -> hạ tolerance cho độ chính xác cao hơn (default trong config = 20)
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

    /**
     * Computes the difference between a single color and palette
     * @param color
     * @param palette2
     * @return delta E nhỏ nhất giữa color và các màu trong palette input
     */
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
        
        // lấy delta E nhỏ nhất của palette và nhỏ hơn config 
        if (smallestDeltaE <= Constant.IMG_DELTA_E_TOLERANCE_SINGLE) {
            return smallestDeltaE;
        }
        return -1;
    }
}
