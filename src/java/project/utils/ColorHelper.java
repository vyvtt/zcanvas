/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

/**
 *
 * @author thuyv
 */
public class ColorHelper {

    // White D50 (Standard daylight) trong hệ XYZ 
    // -> hệ số illuminant (chiếu sáng) cần thiết khi convert CIELab (Chromatic Adaptation)
    // ref: http://www.brucelindbloom.com/index.html?Eqn_ChromAdapt.html
    private float Xr = 0.964221f;
    private float Yr = 1.0f;
    private float Zr = 0.825211f;

    /**
     * Convert RGB color space to XYZ color space. Assuming input color is sRGB.
     * Ref: http://www.brucelindbloom.com/index.html?Eqn_RGB_to_XYZ.html
     * 2 main step: (1) Inverse Companding; (2) Linear RGB to XYZ
     * @param R red integer of color [0,255]
     * @param G green integer of color [0,255]
     * @param B blue integer of color [0,255]
     * @return XYZ
     */
    private float[] convertRGBtoXYZ(int R, int G, int B) {
        float r, g, b, X, Y, Z;
        
        // input RGB có range [0;255] --> convert thành range [0;1]
        r = R / 255.f;
        g = G / 255.f;
        b = B / 255.f;
        
        /**
         * (1) Inverse sRGB Companding. 
         * Ref: https://en.wikipedia.org/wiki/SRGB#The_reverse_transformation for constant
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
         * (2) Linear RGB to XYZ: X, Y and Z output refer to a matrix of sRGB D50/2° for standard illuminant.
         * Matrix: http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
         * 
         * X         0.436052025     0.385081593     0.143087414     R
         * Y    =    0.222491598     0.71688606      0.060621486     G
         * Z         0.013929122     0.097097002     0.71418547      B
         */
        X = 0.436052025f * r + 0.385081593f * g + 0.143087414f * b;
        Y = 0.222491598f * r + 0.71688606f * g + 0.060621486f * b;
        Z = 0.013929122f * r + 0.097097002f * g + 0.71418547f * b;
        
        return new float[] {X,Y,Z}; 
    }
}
