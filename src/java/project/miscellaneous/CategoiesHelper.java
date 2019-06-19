/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.miscellaneous;

/**
 *
 * @author thuyv
 */
public class CategoiesHelper {
    public static String mappingCategoryName(String rawName) {
        String standardName = "";
        
//        bst sài gòn
//    x    art quote
//    x    food, drink, cooking, kitchen
//        cao minh huy
//        mix and match
//        fashion, life
//    x    flower, tree, tropical
//        lê rin
//    x    animal
//        landscape, nature, city
//        abstract, geometric
//        đốm illustration

//        cửa hàng
//   x     động vật
//        xe cộ
//        mopi office - tranh văn phòng
//        hội họa
//        văn hóa
//        spa
//        digital art
//        du lịch
//   x     quotes
//        phim ảnh
//        bản đồ
//        lowpoly
//        nghệ thuật
//   x     thiên nhiên
//        typography
//        giáo dục
//        âm nhạc
//    x    ẩm thực

        rawName = rawName.toLowerCase().trim();
        
        if (rawName.contains(""))
        
        if (rawName.contains("art quote") || rawName.contains("quotes")) {
            return "Quotes";
        } else if (rawName.contains("food, drink, cooking, kitchen") || rawName.contains("ẩm thực")) {
            return "Ẩm thực";
        } else if (rawName.contains("flower, tree, tropical") || rawName.contains("thiên nhiên")) {
            return "Thiên nhiên";
        } else if (rawName.contains("animal") || rawName.contains("động vật")) {
            return "Động vật";
        }
        return standardName;
    }
}
