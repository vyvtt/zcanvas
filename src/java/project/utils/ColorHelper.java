package project.utils;

import java.awt.Color;
import java.util.List;

/**
 *
 * @author thuyv
 */
public class ColorHelper {

    // White D50 (Standard daylight) trong hệ XYZ 
    // -> hệ số illuminant (chiếu sáng) cần thiết khi convert CIELab (Chromatic Adaptation)
    // ref: http://www.brucelindbloom.com/index.html?Eqn_ChromAdapt.html
    private static final float Xr = 0.964221f;
    private static final float Yr = 1.0f;
    private static final float Zr = 0.825211f;

    // Constant given by the CIE standards
    private static final float eps = 216.f / 24389.f;
    private static final float k = 24389.f / 27.f;

    /**
     * Convert RGB color space to XYZ color space. Assuming input color is sRGB.
     * Ref: http://www.brucelindbloom.com/index.html?Eqn_RGB_to_XYZ.html 2 main
     * step: (1) Inverse Companding; (2) Linear RGB to XYZ
     *
     * @param R red integer of color [0,255]
     * @param G green integer of color [0,255]
     * @param B blue integer of color [0,255]
     * @return XYZ
     */
    private static float[] convertRGBtoXYZ(int R, int G, int B) {
        float r, g, b, X, Y, Z;

        // input RGB có range [0;255] --> convert thành range [0;1]
        r = R / 255.f;
        g = G / 255.f;
        b = B / 255.f;

        /**
         * (1) Inverse sRGB Companding. Ref:
         * https://en.wikipedia.org/wiki/SRGB#The_reverse_transformation for
         * constant
         */
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
         * (2) Linear RGB to XYZ: X, Y and Z output refer to a matrix of sRGB
         * D50/2° for standard illuminant. Matrix:
         * http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
         *
         * X    0.436052025     0.385081593     0.143087414     R 
         * Y =  0.222491598     0.71688606      0.060621486     G 
         * Z    0.013929122     0.097097002     0.71418547      B
         */
        X = 0.436052025f * r + 0.385081593f * g + 0.143087414f * b;
        Y = 0.222491598f * r + 0.71688606f * g + 0.060621486f * b;
        Z = 0.013929122f * r + 0.097097002f * g + 0.71418547f * b;

        return new float[]{X, Y, Z};
    }

    /**
     * http://www.brucelindbloom.com/index.html?Eqn_XYZ_to_Lab.html
     * @param XYZ
     * @return 
     */
    private static int[] convertXYZtoLab(float[] XYZ) {
        float fx, fy, fz, xr, yr, zr;
        float Ls, as, bs;
        
        // XYZ to Lab
        //Reference-X, Y and Z refer to specific illuminants and observers. (White D50 in this case)
        xr = XYZ[0] / Xr;
        yr = XYZ[1] / Yr;
        zr = XYZ[2] / Zr;

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
    
    public static int[] convertRGBtoLab(int R, int G, int B) {
        return convertXYZtoLab(convertRGBtoXYZ(R, G, B));
    }

    public static String convertInt2Hex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    public static int convertHex2Int(String hex) {
        Color color = Color.decode(hex);
        int red = (color.getRed() << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        int green = (color.getGreen() << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        int blue = color.getBlue() & 0x000000FF; //Mask out anything not blue.
        return 0xFF000000 | red | green | blue;
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
    
    public static int[] calculateAverageColor(List<int[]> pixels) {
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
