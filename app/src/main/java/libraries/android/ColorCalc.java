package libraries.android;

import android.graphics.Color;

public class ColorCalc {
    public static int setBrightness(float brightness, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[2] = Math.min(1, Math.max(0, brightness));
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int multiplyBrightnessBy(float brightness, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[2] = Math.min(1, Math.max(0, hsv[2] * brightness));
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int setSaturation(float saturation, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[1] = Math.min(1, Math.max(0, saturation));
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int multiplySaturationBy(float saturation, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[1] = hsv[1] * saturation;
        hsv[1] = Math.max(0, Math.min(1f, hsv[1]));
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int setAlpha(float newAlpha, int color) {
        return Color.argb((int) (newAlpha * 255), Color.red(color), Color.green(color), Color.blue(color));
    }

    public static int inverseColor(int color) {
        return Color.argb(
                Color.alpha(color),
                255 - Color.red(color),
                255 - Color.green(color),
                255 - Color.blue(color)
        );
    }

    public static int addHue(int hueAmount, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[0] = (hsv[0] + hueAmount) % 360;
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int addHSV(float[] hsvToAdd, int color) {
        float[] hsvOrigColor = new float[3];
        Color.colorToHSV(color, hsvOrigColor);

        return Color.HSVToColor(
                Color.alpha(color),
                new float[]{
                        (hsvOrigColor[0] + hsvToAdd[0]),
                        hsvOrigColor[1] + hsvToAdd[1],
                        hsvOrigColor[2] + hsvToAdd[2]
                }
        );
    }

    public static int mixColors(int color1, int color2, float fractionColor2) {
        return Color.argb(
                (int) ((1f - fractionColor2) * Color.alpha(color1) + fractionColor2 * Color.alpha(color2)),
                (int) ((1f - fractionColor2) * Color.red(color1) + fractionColor2 * Color.red(color2)),
                (int) ((1f - fractionColor2) * Color.green(color1) + fractionColor2 * Color.red(color2)),
                (int) ((1f - fractionColor2) * Color.blue(color1) + fractionColor2 * Color.blue(color2))
        );
    }

    public static int mixColors(int color1, int color2) {
        return mixColors(color1, color2, 0.5f);
    }

    public static int mixColors(int color1, Integer[] argbToMix) {
        return Color.argb(
                (Color.alpha(color1) + argbToMix[0]) / 2,
                (Color.red(color1) + argbToMix[1]) / 2,
                (Color.green(color1) + argbToMix[2]) / 2,
                (Color.blue(color1) + argbToMix[3]) / 2
        );
    }

    public static int color2Mix(int startColor, int finalColor) {
        return Color.argb(
                2 * Color.alpha(finalColor) - Color.alpha(startColor),
                2 * Color.red(finalColor) - Color.red(startColor),
                2 * Color.green(finalColor) - Color.green(startColor),
                2 * Color.blue(finalColor) - Color.blue(startColor)
        );

    }

    public static Float[] getSaturationAndBrightnessFraction(int startColor, int finalColor){
        float[] hsvStartColor = new float[3];
        float[] hsvFinalColor = new float[3];

        Color.colorToHSV(startColor,hsvStartColor);
        Color.colorToHSV(finalColor,hsvFinalColor);
        return new Float[]{
                hsvFinalColor[1] / hsvStartColor[1],
                hsvFinalColor[2] / hsvStartColor[2]
        };

    }

    public static int multiplySatAndBrightBy(Float[] values, int color){
        return multiplyBrightnessBy(values[1],multiplySaturationBy(values[0],color));
    }

    /** returns the rgb values to mix in first color to get final color */
    public static Integer[] color2Mix2(int startColor, int finalColor) {
        return new Integer[]{
                2 * Color.alpha(finalColor) - Color.alpha(startColor),
                2 * Color.red(finalColor) - Color.red(startColor),
                2 * Color.green(finalColor) - Color.green(startColor),
                2 * Color.blue(finalColor) - Color.blue(startColor)
        };

    }

    public static String toHex(int color) {
        return ""
                + Integer.toHexString(Color.alpha(color))
                + Integer.toHexString(Color.red(color))
                + Integer.toHexString(Color.green(color))
                + Integer.toHexString(Color.blue(color))
                ;
    }

    public static String toHexNoAlpha(int color) {
        return ""
                + Integer.toHexString(Color.red(color))
                + Integer.toHexString(Color.green(color))
                + Integer.toHexString(Color.blue(color))
                ;
    }

    public static float[] colorDifferenceHSV(int color1, int color2) {
        float[] hsvColor1 = new float[3];
        Color.colorToHSV(color1, hsvColor1);

        float[] hsvColor2 = new float[3];
        Color.colorToHSV(color2, hsvColor2);

        return new float[]{
                hsvColor2[0] - hsvColor1[0],
                hsvColor2[1] - hsvColor1[1],
                hsvColor2[2] - hsvColor1[2]
        };


    }

    public static float getBrightness(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[2];
    }

    public static float getSaturation(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[1];
    }
    public static float getHue(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[0];
    }

    public static int getAlpha(Integer color) {
        return Color.alpha(color);
    }

    public static int getRed(Integer color) {
        return Color.red(color);
    }

    public static int getGreen(Integer color) {
        return Color.green(color);
    }

    public static int getBlue(Integer color) {
        return Color.blue(color);
    }

    public static Integer getGrayscale(Integer color) {
        int channelValue =( (int)(0.30 * getRed(color)) + (int)(0.59 * getGreen(color)) + (int)(0.11 * getBlue(color)) ) / 3;
        return Color.argb(
                getAlpha(color),
                channelValue,
                channelValue,
                channelValue
        );
    }

    public static double getContrastRatio(int color1, int color2){
        double relativeLuminanceColor1 = getRelativeLuminance(color1);
        double relativeLuminanceColor2 = getRelativeLuminance(color2);
        return (Math.max(relativeLuminanceColor1,relativeLuminanceColor2) + 0.05) / (Math.min(relativeLuminanceColor1,relativeLuminanceColor2) + 0.05);
    }

    public static double getRelativeLuminance(Integer color){

        return (
                0.2126 * getAdjustedValue(getRed(color)) +
                        0.7152 * getAdjustedValue(getGreen(color)) +
                        0.0722 * getAdjustedValue(getBlue(color))
        );

    }

    private static double getAdjustedValue(int channelValue){
        double rawValue = channelValue / 255d;
        return  rawValue <= 0.03928 ? (rawValue / 12.92f) : Math.pow((rawValue+0.055)/1.055d,2.4d);
    }

    public static int makeColorFromRGB(int red, int green, int blue) {
        return Color.rgb(red,green,blue);
    }

    public static int makeColorFromHSB(float hue, float sat, float brightness) {
        return Color.HSVToColor(new float[]{hue,sat,brightness});
    }
}
