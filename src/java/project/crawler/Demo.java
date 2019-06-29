/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import project.jaxb.Canvas;
import project.jaxb.Detail;
import project.utils.Constant;
import project.utils.ImageHelper;
import project.utils.StringHelper;
import project.utils.XMLHelper;

/**
 *
 * @author thuyv
 */
public class Demo {

    public static void main(String[] args) throws UnsupportedEncodingException, XMLStreamException, IOException {
        
        String contentString = "abc";
        System.out.println(contentString.substring(0, contentString.length() - 1));

//        String url = "https://paint.com.ph/wp-content/uploads/2018/08/House-Painting.jpg";
//        BufferedImage image = ImageIO.read(new URL(url));
//        String palette = ImageHelper.getColorPaletteFromImage(image);
////        System.out.println(ImageHelper.getColorPaletteFromImage(image));
////        ImageHelper.getColorPaletteFromImageNew(image);
//
//        String urlYellow = "https://cdn1.mopi.vn/wp-content/uploads/2018/05/OF-289.jpg";
//        image = ImageIO.read(new URL(urlYellow));
//        String paletteYellow = ImageHelper.getColorPaletteFromImage(image);
//
//        String urlGreen = "https://mopi.vn/wp-content/uploads/2019/05/OF-1.jpg";
//        urlGreen = "https://cdn1.mopi.vn/wp-content/uploads/2018/05/OF-296.jpg";
//        image = ImageIO.read(new URL(urlGreen));
//        String paletteGreen = ImageHelper.getColorPaletteFromImage(image);
//
//        List<String> p = Arrays.asList(palette.split("\\s*;\\s*"));
//        List<String> pYellow = Arrays.asList(paletteYellow.split("\\s*;\\s*"));
//        List<String> pGreen = Arrays.asList(paletteGreen.split("\\s*;\\s*"));
//
//        ImageHelper.comparePalette(p, pYellow);
//        ImageHelper.comparePalette(p, pGreen);

//        String url = "https://mopi.vn/bo-suu-tap/am-thuc/page/2/";
//        String htmlContent = "";
//        String beginSign = " class=\"products\"";
//        String endSign = "after-loop-wrapper";
//
//        htmlContent = XMLHelper.parseHTML(url, beginSign, endSign);
//
//        System.out.println(htmlContent);
//
//        htmlContent = StringHelper.refineHtml(htmlContent);
//
//        System.out.println("///////////////////");
//        System.out.println(htmlContent);
//        String content = "<div class=\"product-wrapper\"><div class=\"thumbnail-wrapper lazy-loading\"><a href=\"https://mopi.vn/shop/am-thuc/its-always-coffee-time-cf-005/\"><figure class=\"has-back-image \"><img alt=\"\" class=\"attachment-shop_catalog wp-post-image ts-lazy-load\" data-src=\"https://cdn1.mopi.vn/wp-content/uploads/2019/03/CF-005-230x264.jpg\" height=\"264\" src=\"https://cdn1.mopi.vn/wp-content/themes/boxshop/images/prod_loading.gif\" width=\"230\"/><img alt=\"\" class=\"product-image-back ts-lazy-load\" data-src=\"https://cdn1.mopi.vn/wp-content/uploads/2019/03/CF-005-1-230x264.jpg\" height=\"264\" src=\"https://cdn1.mopi.vn/wp-content/themes/boxshop/images/prod_loading.gif\" width=\"230\"/></figure></a><div class=\"product-label\"/><div class=\"product-group-button two-button\"><div class=\"loop-add-to-cart\"><a aria-label=\"Lựa chọn cho &amp;ldquo;It&amp;#039;s Always Coffee Time | CF-005&amp;rdquo;\" class=\"button product_type_variable add_to_cart_button\" data-product_id=\"16941\" data-product_sku=\"CF-005\" data-quantity=\"1\" href=\"https://mopi.vn/shop/am-thuc/its-always-coffee-time-cf-005/\" rel=\"nofollow\"><span class=\"ts-tooltip button-tooltip\">Lựa chọn các tùy chọn</span></a></div><div class=\"button-in quickshop\"><a class=\"quickshop\" href=\"https://mopi.vn/wp-admin/admin-ajax.php?ajax=true&amp;#038;action=boxshop_load_quickshop_content&amp;#038;product_id=16941\"><i class=\"pe-7s-search\"/><span class=\"ts-tooltip button-tooltip\">Quick view</span></a></div></div></div><div class=\"meta-wrapper\"><h class=\"heading-title product-name\"><a href=\"https://mopi.vn/shop/am-thuc/its-always-coffee-time-cf-005/\">It&amp;#8217;s Always Coffee Time | CF-005</a></h><span class=\"price\"><span class=\"woocommerce-Price-amount amount\">190.000<span class=\"woocommerce-Price-currencySymbol\">VNĐ</span></span>&amp;ndash;<span class=\"woocommerce-Price-amount amount\">290.000<span class=\"woocommerce-Price-currencySymbol\">VNĐ</span></span><small class=\"woocommerce-price-suffix\">(chưa VAT)</small></span><div class=\"short-description list\" style=\"display: none\">Trang trí quán cafe với tranh treo tường luôn là lựa chọn hàng đầu, tranh treo tường dành cho quán cafe thường mang những cá tính riêng.</div><div class=\"loop-add-to-cart\"><a aria-label=\"Lựa chọn cho &amp;ldquo;It&amp;#039;s Always Coffee Time | CF-005&amp;rdquo;\" class=\"button product_type_variable add_to_cart_button\" data-product_id=\"16941\" data-product_sku=\"CF-005\" data-quantity=\"1\" href=\"https://mopi.vn/shop/am-thuc/its-always-coffee-time-cf-005/\" rel=\"nofollow\"><span class=\"ts-tooltip button-tooltip\">Lựa chọn các tùy chọn</span></a></div></div></div>";
//        
//        List<Canvas> list = MopiPageCrawler.crawlListCanvasEachPage(content);
//        
//        for (Canvas canvas : list) {
//            System.out.println(StringHelper.unescapedSpecialCharacters(canvas.getName()));
//        }
//        List<Detail> listDetail = new ArrayList<>();
//            Canvas currentCanvas = new Canvas();
//        Map<String, String> sizeAndPrice = new HashMap<>();
//        Map<String, String> sizeAndString = new HashMap<>();
//        boolean isEnding = false;
//
//        String url = "https://mopi.vn/shop/cua-hang/bookstore-persevere-and-study-harder-bo-012/";
//
//        String htmlContent = "";
////        String beginSign = "class=\"price-box\"";
//        String beginSign = "class=\"variations_form cart\"";
//        String endSign = "class=\"woocommerce-tabs wc-tabs-wrapper\"";
//
//        htmlContent = XMLHelper.parseHTML(url, beginSign, endSign);
//        htmlContent = StringHelper.refineHtml(htmlContent);
//        
//        System.out.println(htmlContent);
//        XMLEventReader reader = XMLHelper.getXMLEventReaderFromString(htmlContent);
//
//        while (reader.hasNext()) {
//            XMLEvent event = reader.nextEvent();
//
//            if (event.isStartElement()) {
//                StartElement element = event.asStartElement();
//                String tagName = element.getName().getLocalPart().trim();
//
//                if ("form".equals(tagName)) {
//                    Attribute attribute = element.getAttributeByName(new QName("data-product_variations"));
//                    if (attribute != null) {
//                        String rawJson = attribute.getValue();
//                        sizeAndPrice = getSizeAndPriceFromJSON(rawJson);
//
//                    }
//                } //                    else if ("select".equals(tagName)) {
//                //                        Attribute attribute = element.getAttributeByName(new QName("name"));
//                //                        if (attribute != null) {
//                //                            if ("attribute_pa_size".equals(attribute.getValue())) {
//                //                                isInsideSizeOption = true;
//                //                            }
//                //                        }
//                //                    } 
//                else if ("option".equals(tagName)) {
//                    Attribute attribute = element.getAttributeByName(new QName("value"));
//                    if (attribute != null) {
//                        String value = attribute.getValue().trim();
//                        if (!value.isEmpty()) {
//                            event = reader.nextEvent();
//                            if (event.isCharacters()) {
//                                Characters characters = event.asCharacters();
//                                sizeAndString.put(value, characters.getData().trim());
//                            }
//                        }
//                    }
//                } else if ("div".equals(tagName)) {
//                    if (!isEnding) {
//                        Attribute attribute = element.getAttributeByName(new QName("class"));
//                        if (attribute != null) {
//                            if ("tags-link".equals(attribute.getValue())) {
//                                isEnding = true;
//                            }
//                        }
//                    } else { // <div>Thiết kế bởi: Thúy Nguyễn</div>
//                        event = reader.nextEvent();
//                        if (event.isCharacters()) {
//                            Characters characters = event.asCharacters();
//                            String designer = characters.getData().trim();
//                            currentCanvas.setDesigner(designer.substring(designer.indexOf(":") + 2));
//                            isEnding = false;
//                        }
//                    }
//
//                }
//            } // end if event asStartElement
////                else if (event.isEndElement()) {
////                    EndElement element = event.asEndElement();
////                    String tagName = element.getName().getLocalPart().trim();
////
////                    if ("select".equals(tagName)) {
////                        isInsideSizeOption = false;
////                    }
////                }
//        } // end while reader
//
//        System.out.println(sizeAndPrice);
//        System.out.println(sizeAndString);
//        System.out.println(currentCanvas.getDesigner());
//        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
//        MopiMainCrawler.crawl();
//        SoynMainCrawler.crawl();
// nền trắng, chủ đạo tím
//        String url = "https://bizweb.dktcdn.net/thumb/1024x1024/100/296/956/products/se244.jpg?v=1558972277513";
// nền xanh
//        String url = "https://bizweb.dktcdn.net/thumb/1024x1024/100/296/956/products/s0121.jpg?v=1557669636327";
//        String url = "https://cdn1.mopi.vn/wp-content/uploads/2019/03/KO-001-230x264.jpg";
// nền trắng chữ đen, lệch 50 so với nền -> đúng
//        String url = "https://bizweb.dktcdn.net/thumb/1024x1024/100/296/956/products/s0152.jpg?v=1528344387657";
// nền trắng với màu sắc
//        String url = "https://bizweb.dktcdn.net/thumb/1024x1024/100/296/956/products/s0157.jpg?v=1538321974993";
// nền trắng, tranh đen, chữ xám trắng
//String url = "https://bizweb.dktcdn.net/thumb/1024x1024/100/296/956/products/s0130.jpg?v=1520755092880";
//        String url = "https://bizweb.dktcdn.net/thumb/1024x1024/100/296/956/products/td005.jpg?v=1560331954523";
//        try {
//            ImageHelper.binPixels(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        // Get host from config file
//            StringHelper.HOST_MOPI = StringHelper.getHostFromConfigFile(Constant.XML_CONFIG_MOPI);
//            
//            // Crawl list categories
//            MopiCategoriesCrawler categoriesCrawler = new MopiCategoriesCrawler();
//            Map<String, String> categories = categoriesCrawler.crawlCategories();
//            
//            for (Map.Entry<String, String> entry : categories.entrySet()) {
//                System.out.println(entry.getValue().toLowerCase());
//        }
//        String beginSign = "class=\"variations_form cart\"";
//        String endSign = "class=\"woocommerce-tabs wc-tabs-wrapper\"";
//        String htmlContent = "";
//        htmlContent = XMLHelper.parseHTML("https://mopi.vn/shop/mopi-office/chi-co-hanh-dong-moi-tao-ra-ket-qua-of-526/", beginSign, endSign);
//        
//        System.out.println("/////////////////");
//        System.out.println(htmlContent);
//        System.out.println("/////////////////");
//        htmlContent = StringHelper.refineHtml(htmlContent);
//        System.out.println(htmlContent);
//        String tmp = "<select id=\"pa_size\" class=\"hidden\" name=\"attribute_pa_size\" data-attribute_name=\"attribute_pa_size\" data-show_option_none=\"yes\"><option value=\"\">Chọn một tùy chọn</option><option value=\"3040\"  selected='selected'>30x40cm</option><option value=\"4560\" >45x60cm</option><option value=\"6090\" >60x90cm</option></select><a class=\"reset_variations\" href=\"#\">Clear selection</a>						</td>";
//        StringBuffer result = new StringBuffer();
//        Matcher m = Pattern
//                .compile("(\\s+[\\w:.-]+=\")([^\"]*(?:\"(?!\\s+[\\w:.-]+=\"|\\s*(?:/?|\\?)>)[^\"]*)*)\"")
//                .matcher(tmp);
//        while (m.find()) {
//            m.appendReplacement(result, m.group(1) + m.group(2).replace("\"", "&quot;") + "\"");
//        }
//        m.appendTail(result);
//        System.out.println(result.toString());
//        String s = "<div $$$ class=\"product-group-button two-button\" ><div class='loop-add-to-cart'><a href=\"https://mopi.vn/shop/mopi-office/make-friend-before-make-of-322/\" data-quantity=\"1\" class=\"button product_type_variable add_to_cart_button\" data-product_id=\"14304\" data-product_sku=\"OF-322\" aria-label=\"Lựa chọn cho &ldquo;Make Friend Before Make $$$ | OF-322&rdquo;\" rel=\"nofollow\"><span class=\"ts-tooltip button-tooltip\">Lựa chọn các tùy chọn</span></a></div>div class=\"button-in quickshop\"><a class=\"quickshop\" href=\"https://mopi.vn/wp-admin/admin-ajax.php?ajax=true&#038;action=boxshop_load_quickshop_content&#038;product_id=14304\"><i class=\"pe-7s-search\"></i><span class=\"ts-tooltip button-tooltip\">Quick view</span></a>div></div>			";
//        System.out.println(s);
//        s = s.replaceAll("\\s+\\$\\$\\$", "\\$\\$\\$");
//        System.out.println(s);
//        s = java.util.regex.Pattern.quote("$");
//s = s.replaceAll("\\$", "");
//        
//        System.out.println(s);
//        
//        StringBuffer result = new StringBuffer();
//                    Matcher m = Pattern
//                            .compile("(\\s+[\\w:.-]+=\")([^\"]*(?:\"(?!\\s+[\\w:.-]+=\"|\\s*(?:/?|\\?)>)[^\"]*)*)\"")
//                            .matcher(s);
//                    while (m.find()) {
//                        m.appendReplacement(result, m.group(1) + m.group(2).replace("\"", "&quot;") + "\"");
//                    }
//                    m.appendTail(result);
//        System.out.println(result.toString());
//        String url = "https://mopi.vn/bo-suu-tap/mopi-office/page/14/";
//        String beginSign = " class=\"products\"";
//        String endSign = "after-loop-wrapper";
//
//        System.out.println("page url: " + url);
//
//        String htmlContent = "";
//        htmlContent = XMLHelper.parseHTML(url, beginSign, endSign);
//        htmlContent = StringHelper.refineHtml(htmlContent);
//        String htmlContent = XMLHelper.parseHTML("https://mopi.vn/shop/cua-hang/bookstore-reading-is-dream-with-open-eyes-bo-005/",
//                "id=\"main-content\"",
//                "class=\"woocommerce-tabs wc-tabs-wrapper\"");
//        System.out.println(htmlContent);
//        System.out.println("");
//        htmlContent = StringHelper.refineHtml(htmlContent);
//        System.out.println(htmlContent);
//        String to = "[{&amp;quot;attributes&amp;quot;:{&amp;quot;attribute_pa_size&amp;quot;:&amp;quot;4560&amp;quot;},&amp;quot;availability_html&amp;quot;:&amp;quot;&amp;lt;p class=&amp;quot;stock in-stock&amp;quot;&amp;gt;Cu00f2n Hu00e0ng!&amp;lt;/p&amp;gt;n&amp;quot;,&amp;quot;backorders_allowed&amp;quot;:false,&amp;quot;dimensions&amp;quot;:{&amp;quot;length&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;width&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;height&amp;quot;:&amp;quot;&amp;quot;},&amp;quot;dimensions_html&amp;quot;:&amp;quot;N/A&amp;quot;,&amp;quot;display_price&amp;quot;:290000,&amp;quot;display_regular_price&amp;quot;:290000,&amp;quot;image&amp;quot;:{&amp;quot;title&amp;quot;:&amp;quot;BO-005&amp;quot;,&amp;quot;caption&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;url&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005.jpg&amp;quot;,&amp;quot;alt&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-550x629.jpg&amp;quot;,&amp;quot;srcset&amp;quot;:false,&amp;quot;sizes&amp;quot;:false,&amp;quot;full_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005.jpg&amp;quot;,&amp;quot;full_src_w&amp;quot;:700,&amp;quot;full_src_h&amp;quot;:800,&amp;quot;gallery_thumbnail_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-100x100.jpg&amp;quot;,&amp;quot;gallery_thumbnail_src_w&amp;quot;:100,&amp;quot;gallery_thumbnail_src_h&amp;quot;:100,&amp;quot;thumb_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-230x264.jpg&amp;quot;,&amp;quot;thumb_src_w&amp;quot;:230,&amp;quot;thumb_src_h&amp;quot;:264,&amp;quot;src_w&amp;quot;:550,&amp;quot;src_h&amp;quot;:629},&amp;quot;image_id&amp;quot;:&amp;quot;20285&amp;quot;,&amp;quot;is_downloadable&amp;quot;:false,&amp;quot;is_in_stock&amp;quot;:true,&amp;quot;is_purchasable&amp;quot;:true,&amp;quot;is_sold_individually&amp;quot;:&amp;quot;no&amp;quot;,&amp;quot;is_virtual&amp;quot;:false,&amp;quot;max_qty&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;min_qty&amp;quot;:1,&amp;quot;price_html&amp;quot;:&amp;quot;&amp;lt;span class=&amp;quot;price&amp;quot;&amp;gt;&amp;lt;span class=&amp;quot;woocommerce-Price-amount amount&amp;quot;&amp;gt;290.000&amp;amp;nbsp;&amp;lt;span class=&amp;quot;woocommerce-Price-currencySymbol&amp;quot;&amp;gt;VNu0110&amp;lt;/span&amp;gt;&amp;lt;/span&amp;gt; &amp;lt;small class=&amp;quot;woocommerce-price-suffix&amp;quot;&amp;gt;(chu01b0a VAT)&amp;lt;/small&amp;gt;&amp;lt;/span&amp;gt;&amp;quot;,&amp;quot;sku&amp;quot;:&amp;quot;BO-005white-4560&amp;quot;,&amp;quot;variation_description&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;variation_id&amp;quot;:20314,&amp;quot;variation_is_active&amp;quot;:true,&amp;quot;variation_is_visible&amp;quot;:true,&amp;quot;weight&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;weight_html&amp;quot;:&amp;quot;N/A&amp;quot;},{&amp;quot;attributes&amp;quot;:{&amp;quot;attribute_pa_size&amp;quot;:&amp;quot;3040&amp;quot;},&amp;quot;availability_html&amp;quot;:&amp;quot;&amp;lt;p class=&amp;quot;stock in-stock&amp;quot;&amp;gt;Cu00f2n Hu00e0ng!&amp;lt;/p&amp;gt;n&amp;quot;,&amp;quot;backorders_allowed&amp;quot;:false,&amp;quot;dimensions&amp;quot;:{&amp;quot;length&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;width&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;height&amp;quot;:&amp;quot;&amp;quot;},&amp;quot;dimensions_html&amp;quot;:&amp;quot;N/A&amp;quot;,&amp;quot;display_price&amp;quot;:190000,&amp;quot;display_regular_price&amp;quot;:190000,&amp;quot;image&amp;quot;:{&amp;quot;title&amp;quot;:&amp;quot;BO-005&amp;quot;,&amp;quot;caption&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;url&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005.jpg&amp;quot;,&amp;quot;alt&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-550x629.jpg&amp;quot;,&amp;quot;srcset&amp;quot;:false,&amp;quot;sizes&amp;quot;:false,&amp;quot;full_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005.jpg&amp;quot;,&amp;quot;full_src_w&amp;quot;:700,&amp;quot;full_src_h&amp;quot;:800,&amp;quot;gallery_thumbnail_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-100x100.jpg&amp;quot;,&amp;quot;gallery_thumbnail_src_w&amp;quot;:100,&amp;quot;gallery_thumbnail_src_h&amp;quot;:100,&amp;quot;thumb_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-230x264.jpg&amp;quot;,&amp;quot;thumb_src_w&amp;quot;:230,&amp;quot;thumb_src_h&amp;quot;:264,&amp;quot;src_w&amp;quot;:550,&amp;quot;src_h&amp;quot;:629},&amp;quot;image_id&amp;quot;:&amp;quot;20285&amp;quot;,&amp;quot;is_downloadable&amp;quot;:false,&amp;quot;is_in_stock&amp;quot;:true,&amp;quot;is_purchasable&amp;quot;:true,&amp;quot;is_sold_individually&amp;quot;:&amp;quot;no&amp;quot;,&amp;quot;is_virtual&amp;quot;:false,&amp;quot;max_qty&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;min_qty&amp;quot;:1,&amp;quot;price_html&amp;quot;:&amp;quot;&amp;lt;span class=&amp;quot;price&amp;quot;&amp;gt;&amp;lt;span class=&amp;quot;woocommerce-Price-amount amount&amp;quot;&amp;gt;190.000&amp;amp;nbsp;&amp;lt;span class=&amp;quot;woocommerce-Price-currencySymbol&amp;quot;&amp;gt;VNu0110&amp;lt;/span&amp;gt;&amp;lt;/span&amp;gt; &amp;lt;small class=&amp;quot;woocommerce-price-suffix&amp;quot;&amp;gt;(chu01b0a VAT)&amp;lt;/small&amp;gt;&amp;lt;/span&amp;gt;&amp;quot;,&amp;quot;sku&amp;quot;:&amp;quot;BO-005white-3040&amp;quot;,&amp;quot;variation_description&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;variation_id&amp;quot;:20315,&amp;quot;variation_is_active&amp;quot;:true,&amp;quot;variation_is_visible&amp;quot;:true,&amp;quot;weight&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;weight_html&amp;quot;:&amp;quot;N/A&amp;quot;},{&amp;quot;attributes&amp;quot;:{&amp;quot;attribute_pa_size&amp;quot;:&amp;quot;6090&amp;quot;},&amp;quot;availability_html&amp;quot;:&amp;quot;&amp;lt;p class=&amp;quot;stock in-stock&amp;quot;&amp;gt;Cu00f2n Hu00e0ng!&amp;lt;/p&amp;gt;n&amp;quot;,&amp;quot;backorders_allowed&amp;quot;:false,&amp;quot;dimensions&amp;quot;:{&amp;quot;length&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;width&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;height&amp;quot;:&amp;quot;&amp;quot;},&amp;quot;dimensions_html&amp;quot;:&amp;quot;N/A&amp;quot;,&amp;quot;display_price&amp;quot;:550000,&amp;quot;display_regular_price&amp;quot;:550000,&amp;quot;image&amp;quot;:{&amp;quot;title&amp;quot;:&amp;quot;BO-005&amp;quot;,&amp;quot;caption&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;url&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005.jpg&amp;quot;,&amp;quot;alt&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-550x629.jpg&amp;quot;,&amp;quot;srcset&amp;quot;:false,&amp;quot;sizes&amp;quot;:false,&amp;quot;full_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005.jpg&amp;quot;,&amp;quot;full_src_w&amp;quot;:700,&amp;quot;full_src_h&amp;quot;:800,&amp;quot;gallery_thumbnail_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-100x100.jpg&amp;quot;,&amp;quot;gallery_thumbnail_src_w&amp;quot;:100,&amp;quot;gallery_thumbnail_src_h&amp;quot;:100,&amp;quot;thumb_src&amp;quot;:&amp;quot;https://mopi.vn/wp-content/uploads/2019/05/BO-005-230x264.jpg&amp;quot;,&amp;quot;thumb_src_w&amp;quot;:230,&amp;quot;thumb_src_h&amp;quot;:264,&amp;quot;src_w&amp;quot;:550,&amp;quot;src_h&amp;quot;:629},&amp;quot;image_id&amp;quot;:&amp;quot;20285&amp;quot;,&amp;quot;is_downloadable&amp;quot;:false,&amp;quot;is_in_stock&amp;quot;:true,&amp;quot;is_purchasable&amp;quot;:true,&amp;quot;is_sold_individually&amp;quot;:&amp;quot;no&amp;quot;,&amp;quot;is_virtual&amp;quot;:false,&amp;quot;max_qty&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;min_qty&amp;quot;:1,&amp;quot;price_html&amp;quot;:&amp;quot;&amp;lt;span class=&amp;quot;price&amp;quot;&amp;gt;&amp;lt;span class=&amp;quot;woocommerce-Price-amount amount&amp;quot;&amp;gt;550.000&amp;amp;nbsp;&amp;lt;span class=&amp;quot;woocommerce-Price-currencySymbol&amp;quot;&amp;gt;VNu0110&amp;lt;/span&amp;gt;&amp;lt;/span&amp;gt; &amp;lt;small class=&amp;quot;woocommerce-price-suffix&amp;quot;&amp;gt;(chu01b0a VAT)&amp;lt;/small&amp;gt;&amp;lt;/span&amp;gt;&amp;quot;,&amp;quot;sku&amp;quot;:&amp;quot;BO-005white-6090&amp;quot;,&amp;quot;variation_description&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;variation_id&amp;quot;:20316,&amp;quot;variation_is_active&amp;quot;:true,&amp;quot;variation_is_visible&amp;quot;:true,&amp;quot;weight&amp;quot;:&amp;quot;&amp;quot;,&amp;quot;weight_html&amp;quot;:&amp;quot;N/A&amp;quot;}]";
//
//        to = to.replace("&amp;", "&")
//                .replace("&quot;", "\"");
//        
//        while(to.contains("attribute_pa_size")) {
//            to = to.substring(to.indexOf("attribute_pa_size"));
//            System.out.println("1 " + to);
//            to = to.replaceFirst("attribute_pa_size\":\"", "");
//            System.out.println("2 " + to);
//            
//            String size = to.substring(0, to.indexOf("\""));
//            
//            to = to.substring(to.indexOf("display_price"));
//            System.out.println("3 " + to);
//            to = to.replaceFirst("display_price\":", "");
//            System.out.println("4 " + to);
//            
//            String price = to.substring(0, to.indexOf(","));
//            System.out.println("size: " + size);
//            System.out.println("price: " + price);
//        }
//        System.out.println(to);
//        Thread thread = new Thread(new SoynThread());
//        thread.start();
//        String uri = "https://www.sephora.com/shop/facial-treatments";
//        String uri = "https://www.paulaschoice.com/ingredient-dictionary";
//        String uri = "https://soyncanvas.vn/tranh-treo-tuong";
//        String uri = "https://soyncanvas.vn";
//        String beginSign = "class=\"products-view products-view-grid\"";
//        String endSign = "class=\"text-xs-center\"";
//        String beginSign = "href=\"/tranh-treo-tuong\"";
//        String endSign = "</ul>";
//
//        Crawler.parseHTML_getPageCount(uri, beginSign, endSign);
//        String oldContent = Crawler.htmlContent;
//        String newContent = StringUtilities.fixString(oldContent);
//        try {
//            Map<String,String> listCategories = Crawler.getCategories(newContent);
//            System.out.println(listCategories);
//        System.out.println("-------------------------------------");
//        int pageCount = Crawler.pageCount;
//        System.out.println(pageCount);
//        
//        System.out.println(Crawler.htmlContent);
//        
//        System.out.println("-------------------------------------");
//        
//        String oldContent = Crawler.htmlContent;
//        String newContent = StringUtilities.fixString(oldContent);
//        System.out.println(newContent);
//        Crawler.parseHTML(uri, beginSign, endSign);
//        System.out.println(Crawler.htmlContent);
//        } catch (XMLStreamException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private static Map<String, String> getSizeAndPriceFromJSON(String to) {
        System.out.println("in get price size ---------------");
        System.out.println(to);
        Map<String, String> sizeAndPrice = new HashMap<>();
        to = to.replace("&amp;", "&")
                .replace("&quot;", "\"");

        System.out.println(to);

        while (to.contains("attribute_pa_size")) {
            to = to.substring(to.indexOf("attribute_pa_size"));
//            System.out.println("1 " + to);
            to = to.replaceFirst("attribute_pa_size\":\"", "");
//            System.out.println("2 " + to);

            String size = to.substring(0, to.indexOf("\""));

            to = to.substring(to.indexOf("display_price"));
//            System.out.println("3 " + to);
            to = to.replaceFirst("display_price\":", "");
//            System.out.println("4 " + to);

            String price = to.substring(0, to.indexOf(","));
            sizeAndPrice.put(price, size);
//            System.out.println("size: " + size);
//            System.out.println("price: " + price);
        }
        return sizeAndPrice;
    }
}
