/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import project.jaxb.Categories;
import project.jaxb.Location;
import project.utils.DBUtils;

/**
 *
 * @author thuyv
 */
public class LocationDAO implements Serializable {

    public LocationDAO() {
    }

    public String getAllLocationCategories() {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "SELECT Location.id, Location.name,\n"
                    + "	(SELECT Category.id, Category.name\n"
                    + "	FROM LocationCategory \n"
                    + "	JOIN Category \n"
                    + "	ON LocationCategory.CategoryId = Category.id \n"
                    + "	WHERE LocationCategory.LocationId = Location.id\n"
                    + "	FOR XML PATH('category'), ROOT('categories'), TYPE\n"
                    + "	)\n"
                    + "FROM Location\n"
                    + "WHERE Location.id in (SELECT Location.id FROM Location)\n"
                    + "FOR XML PATH('location'), TYPE, ROOT('locations')";
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
    
    public String getAllLocationXML() {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "SELECT * FROM Location FOR XML PATH('location'), ROOT('locations')";
            stm = con.prepareStatement(sql);

            rs = stm.executeQuery();
            
            String result = "";
            if (rs.next()) {
                result = rs.getString(1);
            }
            return result;

//            List<Location> result = new ArrayList<>();
//            while (rs.next()) {
//                result.add(new Location(
//                        rs.getInt("id"), 
//                        rs.getNString("name"), 
//                        rs.getString("image")));
//            }
//            return result;

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

    public List<Categories> getCategoriesByLocation(int locationId) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "SELECT Category.id, Category.name\n"
                    + "  FROM LocationCategory JOIN Category ON LocationCategory.CategoryId = Category.id\n"
                    + "  WHERE LocationCategory.LocationId = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, locationId);

            rs = stm.executeQuery();

            List<Categories> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Categories(rs.getInt(1), rs.getNString(2)));
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

    public void updateLocationCategory(int locationId, List<Integer> categoryIds) {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBUtils.getConnection();

            String sql = "DELETE FROM LocationCategory WHERE LocationId = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, locationId);

            int result = stm.executeUpdate();
            
            System.out.println("Delete " + result + " records");

            sql = "INSERT INTO LocationCategory(LocationId, CategoryId) VALUES (?,?)";

            con.setAutoCommit(false);
            stm = con.prepareStatement(sql);

            for (Integer categoryId : categoryIds) {
                stm.setInt(1, locationId);
                stm.setInt(2, categoryId);
                stm.addBatch();
            }
            stm.executeBatch();
            con.commit();
            
        } catch (NamingException | SQLException e) {
            Logger.getLogger(CanvasDAO.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
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
    }
    
    public void updateLocationName(int locationId, String locationName) {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBUtils.getConnection();

            String sql = " UPDATE Location SET name = ? WHERE id = ?;";
            stm = con.prepareStatement(sql);
            stm.setNString(1, locationName);
            stm.setInt(2, locationId);

            int result = stm.executeUpdate();
            if (result > 0) {
                System.out.println("update done");
            }
            
            
        } catch (NamingException | SQLException e) {
            Logger.getLogger(CanvasDAO.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
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
    }
}
