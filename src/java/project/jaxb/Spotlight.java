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
@XmlRootElement(name = "spotlight")
@XmlAccessorType(XmlAccessType.FIELD)
public class Spotlight {
    @XmlElement(name = "paletteImg")
    private String paletteImg;
    
    @XmlElement(name = "paletteColor")
    private List<String> paletteColor = null;
    
    @XmlElement(name = "imgName")
    private String imgName = null;
    
    @XmlElement(name = "imgAuth")
    private String imgAuth = null;
    
    @XmlElement(name = "imgLink")
    private String imgLink = null;
    
    @XmlElement(name = "canvases")
    private Canvases canvases = null;

    public Spotlight() {
    }

    public String getPaletteImg() {
        return paletteImg;
    }

    public void setPaletteImg(String paletteImg) {
        this.paletteImg = paletteImg;
    }

    public List<String> getPaletteColor() {
        return paletteColor;
    }

    public void setPaletteColor(List<String> paletteColor) {
        this.paletteColor = paletteColor;
    }

    public Canvases getCanvases() {
        return canvases;
    }

    public void setCanvases(Canvases canvases) {
        this.canvases = canvases;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgAuth() {
        return imgAuth;
    }

    public void setImgAuth(String imgAuth) {
        this.imgAuth = imgAuth;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
    
    
}
