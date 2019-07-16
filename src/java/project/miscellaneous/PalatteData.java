package project.miscellaneous;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import project.crawler.Demo;
import project.dao.CanvasDAO;
import project.jaxb.Canvas;
import project.utils.ColorHelper;
import project.utils.Constant;
import project.utils.ImageHelper;

/**
 *
 * @author thuyv
 */
public class PalatteData {

    private static List<Canvas> listCanvas;
    public static String palatteImage;
    public static List<String> palatteColor;    // palette màu của img dưới dạng Hex
    public static List<Canvas> topCanvas;

    public static String imgAuth;
    public static String imgLink;

    /**
     * Load toàn bộ canvas dưới DB, lưu vào listCanvas
     */
    public static void initListCanvas() {
        CanvasDAO canvasDAO = new CanvasDAO();
        listCanvas = canvasDAO.getAllCanvas();

        System.out.println("Init list canvas size for Unspash: " + listCanvas.size());

        topCanvas = new ArrayList<>();
        imgAuth = null;
        imgLink = null;
    }

    /**
     * Gọi API random image Unsplash (config-unsplash.xml)
     */
    public static void getRandomImgFromUnsplash() {
        try {
            URL url = new URL(Constant.API);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            StringBuilder content;

            if (responseCode >= 200 && responseCode <= 300) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                }
                parseJSON(content.toString());
            }
            con.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(PalatteData.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Parse JSON response từ Unsplash. "html" : link chi tiết "name" : tên tác
     * giả "regular" : link ảnh size với width 1080px
     *
     * @param json response từ api
     */
    private static void parseJSON(String json) {
        Pattern pattern = Pattern.compile("\"html\"\\s*:\\s*\"([^,]*)\",");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            imgLink = matcher.group(1);
        } else {
            imgLink = null;
        }

        pattern = Pattern.compile("\"name\"\\s*:\\s*\"([^,]*)\",");
        matcher = pattern.matcher(json);
        if (matcher.find()) {
            imgAuth = matcher.group(1);
        } else {
            imgAuth = null;
        }

        pattern = Pattern.compile("\"regular\"\\s*:\\s*\"([^,]*)\",");
        matcher = pattern.matcher(json);
        if (matcher.find()) {
            palatteImage = matcher.group(1);
            processImg();
        }
    }

    /**
     * Lấy palette từ link image
     */
    private static void processImg() {
        try {
            BufferedImage image = ImageIO.read(new URL(palatteImage));
            String palatteColorsStr = ImageHelper.getColorPaletteFromImage(image, true);

            List<String> inputPalatte = Arrays.asList(palatteColorsStr.split("\\s*;\\s*"));

            palatteColor = new ArrayList<>();
            for (String colorInt : inputPalatte) {
                palatteColor.add(ColorHelper.convertInt2Hex(Integer.parseInt(colorInt)));
            }

            if (!listCanvas.isEmpty()) {
                updateCanvasMatchingPalatte();
            }

        } catch (IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Lấy top 10 các canvas dựa trên palette màu trên và deltaE. Nếu ko có
     * canvas nào phù hợp thì xét theo từng màu
     */
    private static void updateCanvasMatchingPalatte() {

        List<Canvas> result = new ArrayList<>();
        List<String> inputColor = new ArrayList<>();
        for (String tmp : palatteColor) {
            int intColor = ColorHelper.convertHex2Int(tmp);
            inputColor.add(String.valueOf(intColor));
        }

        for (Canvas canvas : listCanvas) {

            List<String> currentPalette = Arrays.asList(canvas.getColorPalatte().split("\\s*;\\s*"));

            double deltaE = ImageHelper.comparePalette2Palette(inputColor, currentPalette);

            if (deltaE != -1) {
                canvas.setDeltaE(deltaE);
                List<String> currentCanvasColor = new ArrayList<>();

                for (String colorInt : currentPalette) {
                    currentCanvasColor.add(ColorHelper.convertInt2Hex(Integer.parseInt(colorInt)));
                }
                canvas.setCanvasColors(currentCanvasColor);
                result.add(canvas);
            }
        }

        Collections.sort(result, (c1, c2) -> {
            return ((Comparable) c1.getDeltaE()).compareTo(c2.getDeltaE());
        });
        List<Canvas> topResult = result.subList(0, Math.min(result.size(), 10));

        if (topResult.isEmpty()) {
            System.out.println("EMPTY spotlight by palette -> Search by each color");
            for (String colorHex : palatteColor) {
                int currentColor = ColorHelper.convertHex2Int(colorHex);

                List<Canvas> tmpList = new ArrayList<>();
                for (Canvas canvas : listCanvas) {
                    List<String> currentPalette = Arrays.asList(canvas.getColorPalatte().split("\\s*;\\s*"));
                    double deltaE = ImageHelper.compareColor2Palette(currentColor, currentPalette);

                    if (deltaE != -1) {
                        canvas.setDeltaE(deltaE);
                        List<String> currentCanvasColor = new ArrayList<>();

                        for (String colorInt : currentPalette) {
                            currentCanvasColor.add(ColorHelper.convertInt2Hex(Integer.parseInt(colorInt)));
                        }
                        canvas.setCanvasColors(currentCanvasColor);
                        tmpList.add(canvas);
                    }
                }

                result.removeAll(tmpList);
                result.addAll(tmpList);
            }

            Collections.sort(result, (c1, c2) -> {
                return ((Comparable) c1.getDeltaE()).compareTo(c2.getDeltaE());
            });
            topResult = result.subList(0, Math.min(result.size(), 10));
        }

        System.out.println("Total match img from Unsplash (select top 10): " + result.size());
        for (Canvas canvas : topResult) {
            System.out.println(canvas.getDeltaE() + " - " + canvas.getImage());
        }

        topCanvas = new ArrayList<>(topResult);
    }

    public static boolean isReady() {
        if (palatteImage == null || palatteColor == null || topCanvas == null) {
            System.out.println("Something null -> spotlight is not ready -> skip");
            return false;
        }
        return (!palatteImage.isEmpty() && !palatteColor.isEmpty() && !topCanvas.isEmpty());
    }

}
