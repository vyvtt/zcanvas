/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.jaxb;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author thuyv
 */

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "Location", propOrder = {
//    "name",
//    "image",
//    "category"
//})
@XmlRootElement
public class Location implements Serializable{
    
    protected int id;
    protected String name;
    protected String image;
    protected List<Categories> category;

    public Location() {
    }

    public Location(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Location(int id, String name, String image, List<Categories> category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Categories> getCategory() {
        return category;
    }

    public void setCategory(List<Categories> category) {
        this.category = category;
    }
    
    
}
