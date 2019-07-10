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
public class CategoriesHelper {
    public static String mappingCategoryName(String rawName) {
        String standardName = "";
        
//        bst sài gòn
//    x    art quote
//    x    food, drink, cooking, kitchen
//    x    cao minh huy
//    x    mix and match
//    x    fashion, life
//    x    flower, tree, tropical
//    x    lê rin
//    x    animal
//    x    landscape, nature, city
//    x    abstract, geometric
//    x    đốm illustration

//   x     cửa hàng
//   x     động vật
//        xe cộ
//   x     mopi office - tranh văn phòng
//   x     hội họa
//        văn hóa
//   x     spa
//        digital art
//   x     du lịch
//   x     quotes
//        phim ảnh
//        bản đồ
//        lowpoly
//   x     nghệ thuật
//   x     thiên nhiên
//   x     typography
//        giáo dục
//        âm nhạc
//    x    ẩm thực


//Tranh Premium x
//Typography    x
//Saigon        x
//Graphics
//Landscape     x
//Botanicals    x
//Abstract      x
//Animal        x
//Lifestyle     x
//Monochrome
        
        String name = rawName.toLowerCase();
        
        if (
                name.contains("art quote") || 
                name.contains("quotes") || 
                name.contains("typography")) {
            return "Quotes, typography";
        } else if (
                name.contains("food, drink, cooking, kitchen") || 
                name.contains("ẩm thực")) {
            return "Ẩm thực";
        } else if (
                name.contains("flower, tree, tropical") || 
                name.contains("botanicals") || 
                name.contains("thiên nhiên")) {
            return "Thiên nhiên";
        } else if (
                name.contains("animal") || 
                name.contains("động vật")) {
            return "Động vật";
        } else if (
                name.contains("landscape, nature, city") || 
                name.contains("saigon") || 
                name.contains("landscape") || 
                name.contains("du lịch")) {
            return ("Thành phố, landscape");
        } else if (
                name.contains("abstract, geometric") || 
                name.contains("abstract") ||
                name.contains("hội họa") || 
                name.contains("nghệ thuật")) {
            return "Trừu tượng, nghệ thuật, hội họa";
        } else if (
                name.contains("spa") || 
                name.contains("cửa hàng")) {
            return "Tranh treo cửa hàng";
        } else if (
                name.contains("mopi office")) {
            return "Tranh treo văn phòng";
        } else if (
                name.contains("cao minh huy") || 
                name.contains("mix and match") || 
                name.contains("lê rin") || 
                name.contains("đốm illustration")) {
            return "BST " + rawName;
        } else if (
                name.contains("fashion, life") ||
                name.contains("lifestyle")) {
            return "Cuộc sống";
        } else if (
                name.contains("monochrome")) {
            return "Tranh đơn sắc";
        } else if (
                name.contains("digital art") ||
                name.contains("lowpoly") ||
                name.contains("graphics")) {
            return "Tranh Digital | Graphics";
        } else {
            return rawName;
        }
    }
}
