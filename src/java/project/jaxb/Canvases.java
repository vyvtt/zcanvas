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
@XmlRootElement(name = "canvases")
@XmlAccessorType(XmlAccessType.FIELD)
public class Canvases {

    @XmlElement(name = "canvas")
    private List<Canvas> canvases = null;


    public List<Canvas> getCanvases() {
        return canvases;
    }

    public void setCanvases(List<Canvas> canvases) {
        this.canvases = canvases;
    }

    
}
