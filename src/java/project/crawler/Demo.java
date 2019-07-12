/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import project.utils.Constant;
import project.utils.ImageHelper;

/**
 *
 * @author thuyv
 */
public class Demo {

    public static String palatteImg;
    public static String palatteDes;

    public static void main(String[] args) {

        parseJSON("");

//        try {
//            String urlStr = "https://api.unsplash.com/photos/random";
//            urlStr += "?client_id=b303f5c156451e2af3a7ba22ad662f8b8e90b276998a563e464b682cb4403e2b";
//            URL url = new URL(urlStr);
//
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            int responseCode = con.getResponseCode();
//            System.out.println("Sending 'GET' request to URL : " + url);
//            System.out.println("Response Code : " + responseCode);
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder content = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//            con.disconnect();
//            
//            parseJSON(content.toString());
//            
//
//        } catch (IOException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private static void parseJSON(String json) {
        System.out.println("json: " + json);

        json = "{\"id\":\"NljTy5Y15JM\",\"created_at\":\"2019-06-25T14:45:54-04:00\",\"updated_at\":\"2019-07-07T01:10:19-04:00\",\"width\":5184,\"height\":3456,\"color\":\"#1C1315\",\"description\":null,\"alt_description\":\"birds on sky\",\"urls\":{\"raw\":\"https://images.unsplash.com/photo-1561487767-1f32d174cce0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjgxMDk1fQ\",\"full\":\"https://images.unsplash.com/photo-1561487767-1f32d174cce0?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjgxMDk1fQ\",\"regular\":\"https://images.unsplash.com/photo-1561487767-1f32d174cce0?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjgxMDk1fQ\",\"small\":\"https://images.unsplash.com/photo-1561487767-1f32d174cce0?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjgxMDk1fQ\",\"thumb\":\"https://images.unsplash.com/photo-1561487767-1f32d174cce0?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjgxMDk1fQ\"},\"links\":{\"self\":\"https://api.unsplash.com/photos/NljTy5Y15JM\",\"html\":\"https://unsplash.com/photos/NljTy5Y15JM\",\"download\":\"https://unsplash.com/photos/NljTy5Y15JM/download\",\"download_location\":\"https://api.unsplash.com/photos/NljTy5Y15JM/download\"},\"categories\":[],\"sponsored\":false,\"sponsored_by\":null,\"sponsored_impressions_id\":null,\"likes\":57,\"liked_by_user\":false,\"current_user_collections\":[],\"user\":{\"id\":\"z2gJArAFgrY\",\"updated_at\":\"2019-07-03T02:56:12-04:00\",\"username\":\"centelm\",\"name\":\"Cl\\u00e9ment Falize\",\"first_name\":\"Cl\\u00e9ment\",\"last_name\":\"Falize\",\"twitter_username\":\"centelm\",\"portfolio_url\":null,\"bio\":\"Le monde est une grande salle de shoot.\",\"location\":\"France\",\"links\":{\"self\":\"https://api.unsplash.com/users/centelm\",\"html\":\"https://unsplash.com/@centelm\",\"photos\":\"https://api.unsplash.com/users/centelm/photos\",\"likes\":\"https://api.unsplash.com/users/centelm/likes\",\"portfolio\":\"https://api.unsplash.com/users/centelm/portfolio\",\"following\":\"https://api.unsplash.com/users/centelm/following\",\"followers\":\"https://api.unsplash.com/users/centelm/followers\"},\"profile_image\":{\"small\":\"https://images.unsplash.com/profile-1548357808205-2538ff1e9a7e?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32\",\"medium\":\"https://images.unsplash.com/profile-1548357808205-2538ff1e9a7e?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64\",\"large\":\"https://images.unsplash.com/profile-1548357808205-2538ff1e9a7e?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128\"},\"instagram_username\":\"centelm\",\"total_collections\":0,\"total_likes\":21,\"total_photos\":27,\"accepted_tos\":true},\"exif\":{\"make\":null,\"model\":null,\"exposure_time\":null,\"aperture\":null,\"focal_length\":null,\"iso\":null},\"location\":{\"title\":\"Sydney, Australia\",\"name\":null,\"city\":\"Sydney\",\"country\":\"Australia\",\"position\":{\"latitude\":null,\"longitude\":null}},\"views\":307859,\"downloads\":1351}";

        System.out.println(json);

//        Pattern codePattern = Pattern.compile("\"description\"\\s*:\\s*\"([^,]*)\",");
        Pattern messagePattern = Pattern.compile("\"small\"\\s*:\\s*\"([^,]*)\",");

//        Matcher code_matcher = codePattern.matcher(json);
        Matcher message_matcher = messagePattern.matcher(json);

        if (message_matcher.find()) {
            System.out.println("match!");
//            System.out.println("\n" + desMatcher.group(1));
//            System.out.println("\n" + imgMatcher.group(1));
            palatteImg = message_matcher.group(1);
//            palatteDes = code_matcher.group(1);

            System.out.println(palatteDes);
            System.out.println(palatteImg);

            processImg();
        }
    }

    private static void processImg() {

        Constant.IMG_BUCKET_SIZE = 256 / 3;
        Constant.IMG_SKIP_PIXEL = 30;
        Constant.IMG_PALETTE_SIZE = 5;

        try {
            System.out.println(palatteImg);

//            BufferedImage image = ImageIO.read(new URL(palatteImg));
//            List<int[]> pixels = ImageHelper.getPixelFromImage(image, true);
//
//            System.out.println(pixels.size());
//
//            for (int i = 0; i < pixels.size() - (30 + 1); i = i + 30) {
//                System.out.println(i);
//            }

            BufferedImage image = ImageIO.read(new URL(palatteImg));
            String palatteColorsStr = ImageHelper.getColorPaletteFromImage(image, true);
            System.out.println("input palette: " + palatteColorsStr);
            System.out.println("done get palatte");
            

            List<String> inputPalatte = Arrays.asList(palatteColorsStr.split("\\s*;\\s*"));
            

            System.out.println("parse hex");
            List<String> colorHex = new ArrayList<>();
            for (String colorInt : inputPalatte) {
                colorHex.add(ImageHelper.convertColorInt2Hex(Integer.parseInt(colorInt)));
            }

            for (String string : colorHex) {
                System.out.println(string);
            }
        } catch (IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}
