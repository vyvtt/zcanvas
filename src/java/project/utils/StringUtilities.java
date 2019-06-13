/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thuyv
 */
public class StringUtilities {
    
    public static boolean isAlphaCharacter(char x) {
        return (x >= 'a' && x <= 'z');
    }
    
    public static String getTagName(String content) {
        // Thẻ tự đóng => trả về null
        if (content.charAt(content.length() - 2) == '/') {
            return null;
        }
        
        String tagName = "";
        int i = 1; //skip 0 là "<"
        
        if (content.charAt(1) == '/') {
            tagName = "/";
            i++;
        }
        
        while (isAlphaCharacter(content.charAt(i))) {            
            tagName = tagName + content.charAt(i);
            i++;
        }
        
        if (tagName.length() == 0) {
            return null;
        } else if ((tagName.length() == 1) && tagName.charAt(0) == '/') {
            return null;
        }
        
        return tagName;
    }
    
    public static String fixString(String content) {
        List<String> stack = new ArrayList<>();
        List<Integer> li = new ArrayList<>();
        List<String> tagToAdd = new ArrayList<>();
        
        int size = content.length();
        int mark[] = new int[size];
        Arrays.fill(mark, -1);
        
        int i = 0;
        while (i < content.length()) {            
            if (content.charAt(i) == '<') { // tag đang mở, lấy hết content từ <...>
                int j = i + 1;
                String tmpTag = "" + content.charAt(j);
                
                while ((j < content.length()) && content.charAt(j) != '>') {                    
                    tmpTag += content.charAt(j);
                    j++;
                }
                
                tmpTag += '>';
                int current = j; // current là j, hiện tại là vị trí của >
                i = j + 1;
                
                String tagName = getTagName(tmpTag);
                
                if (tagName != null) {
                    if (tagName.charAt(0) != '/') { //thẻ mở
                        stack.add(tagName);
                        li.add(current);
                    } else { // thẻ đóng
                        while (stack.size() > 0) {                            
                            if (stack.get(stack.size() - 1).equals(tagName.substring(1))) { // thẻ mở = thẻ đóng
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                                break;
                            } else { // thẻ mở ko trùng thẻ đóng
                                tagToAdd.add(stack.get(stack.size() - 1));
                                mark[li.get(li.size() - 1)] = tagToAdd.size() - 1;
                                
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                            }   
                        } // end while
                    } // end if else tagName là thẻ mở hay đóng
                } // end if tagName null     
            } // end if < 
            else {
                i++;
            }
        } // end while content.length
        
        while (stack.size() > 0) {
            tagToAdd.add(stack.get(stack.size() - 1));
            mark[li.get(li.size() - 1)] = tagToAdd.size() - 1;
            stack.remove(stack.size() - 1);
            li.remove(li.size() - 1);
        }
        
        System.out.println(stack);
        System.out.println(li);
        System.out.println(tagToAdd);
        System.out.println(mark);
        
        String newContent = "";
        
        for (int j = 0; j < content.length(); j++) {
            newContent += content.charAt(j);
            if (mark[j] != -1) {
                newContent += "</" + tagToAdd.get(mark[j]) + ">";
            }
        }
        
        return "<root>" + newContent + "</root>";
    }
}
