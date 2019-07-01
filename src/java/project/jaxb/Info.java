/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.jaxb;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author thuyv
 */
@XmlRootElement(name = "info")
@XmlAccessorType(XmlAccessType.FIELD)
public class Info {
    
    @XmlElement(name = "total")
    private int total = 0;
    
    @XmlElement(name = "inputColor")
    private List<String> inputColors = null;
    
    @XmlElement(name = "category")
    private List<Categories> categories = null;
    
    @XmlElement(name = "canvases")
    private Canvases canvases = null;

    public Info() {
    }

    public List<String> getInputColors() {
        return inputColors;
    }

    public void setInputColors(List<String> inputColors) {
        this.inputColors = inputColors;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public Canvases getCanvases() {
        return canvases;
    }

    public void setCanvases(Canvases canvases) {
        this.canvases = canvases;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
    
}
