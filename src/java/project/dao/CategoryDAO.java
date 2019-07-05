/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.dao;

import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import project.jaxb.Canvas;
import project.jaxb.Categories;
import project.utils.DBUtils;

/**
 *
 * @author thuyv
 */
public class CategoryDAO implements Serializable{
    
    public int insert(Categories category) throws SQLException, NamingException, IOException {
        
        Connection con = null;
        CallableStatement statement = null;
        try {
            con = DBUtils.getConnection();
            String sql = "{call InsertCategory(?,?)}";
            statement = con.prepareCall(sql);
            
            statement.setNString(1, category.getName());

            statement.registerOutParameter(2, java.sql.Types.INTEGER);
            statement.execute();
            
            return statement.getInt("CategoryId"); // store procedure OUTPUT value registe above
            
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public List<Categories> getAllCategories() {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "SELECT id, name FROM Category";
            stm = con.prepareStatement(sql);

            rs = stm.executeQuery();

            List<Categories> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Categories(rs.getInt("id"), rs.getNString("name")));
            }
            return result;
            
        } catch (SQLException | NamingException e) {
            Logger.getLogger(CanvasDAO.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sQLException) {
                Logger.getLogger(CanvasDAO.class.getName()).log(Level.SEVERE, sQLException.getMessage(), sQLException);
            }
        }
        return null;
    }
    
    public String getAllCategoriesAsXML() {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "SELECT id, name FROM Category ORDER BY name ASC FOR XML PATH('category'), ROOT('categories')";
            stm = con.prepareStatement(sql);

            rs = stm.executeQuery();

            String result = "";
            if (rs.next()) {
                result = rs.getString(1);
            }
            return result;
            
        } catch (SQLException | NamingException e) {
            Logger.getLogger(CanvasDAO.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sQLException) {
                Logger.getLogger(CanvasDAO.class.getName()).log(Level.SEVERE, sQLException.getMessage(), sQLException);
            }
        }
        return null;
    }
    
    public int getCategoryIDByName(String name) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "";
            stm = con.prepareStatement(sql);

            rs = stm.executeQuery();

            List<Canvas> result = new ArrayList<>();
            while (rs.next()) {
                
            }
            
        } catch (SQLException | NamingException e) {
            Logger.getLogger(CanvasDAO.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sQLException) {
                Logger.getLogger(CanvasDAO.class.getName()).log(Level.SEVERE, sQLException.getMessage(), sQLException);
            }
        }
        return -1;
    }
}
