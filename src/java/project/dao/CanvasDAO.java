/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import project.jaxb.Canvas;
import project.utils.DBUtils;
import project.utils.StringHelper;

/**
 *
 * @author thuyv
 */
public class CanvasDAO implements Serializable{
    
    private boolean isExisted;

    public CanvasDAO() {
        this.isExisted = false;
    }

    public int insert(Canvas canvas) throws SQLException, NamingException {
        
        this.isExisted = false;
        
        Connection con = null;
        CallableStatement statement = null;
        try {
            con = DBUtils.getConnection();
            String sql = "{call InsertCanvas(?,?,?,?,?,?,?,?)}";
            statement = con.prepareCall(sql);
            
//            @name NVARCHAR(250),
//            @url VARCHAR(250),
//            @hashURL INT,
//            @image VARCHAR(250),
//            @designer NVARCHAR(250),
//            @color VARCHAR(250),
//            @id INT OUTPUT
            
            statement.setNString(1, StringHelper.unescapedSpecialCharacters(canvas.getName()));
            statement.setString(2, canvas.getUrl());
            statement.setInt(3, StringHelper.hashString(canvas.getUrl()));
            statement.setString(4, canvas.getImage());
            statement.setNString(5, StringHelper.unescapedSpecialCharacters(canvas.getDesigner()));
            statement.setString(6, canvas.getColorPalatte());

            statement.registerOutParameter(7, java.sql.Types.INTEGER);
            statement.registerOutParameter(8, java.sql.Types.BIT);
            statement.execute();
            
            if (statement.getBoolean("isExisted")) {
                this.isExisted = true;
            }
            return statement.getInt("Id"); // store procedure OUTPUT value registe above
            
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
//        Connection con = null;
//        PreparedStatement stm = null;
//
//        try {
//            con = DBUtils.getConnection();
//            String sql = "INSERT INTO Canvas(name, url, hashURL, image, designer, color) "
//                    + "VALUES (?,?,?,?,?,?)";
//            
//            con.setAutoCommit(false);
//            stm = con.prepareStatement(sql);
//            
//            for (Canvas canvas : listCanvas) {
//                stm.setString(1, canvas.getName());
//                stm.setString(2, canvas.getUrl());
//                stm.setInt(3, StringHelper.hashString(canvas.getUrl()));
//                stm.setString(4, canvas.getImage());
//                stm.setString(5, canvas.getDesigner());
//                stm.setString(6, ImageHelper.getColorPaletteFromImage(canvas.getUrl()));
//                stm.addBatch();
//            }
//            stm.executeBatch();
//            con.commit();
//        } finally {
//            if (stm != null) {
//                stm.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }

    }
    
    public void insertCanvasCategory(int categoryId, int canvasId) throws SQLException, NamingException {
        Connection con = null;
        CallableStatement statement = null;
        try {
            con = DBUtils.getConnection();
            String sql = "{call InsertCategoryCanvas(?,?)}";
            statement = con.prepareCall(sql);

            statement.setInt(1, categoryId);
            statement.setInt(2, canvasId);
            
            statement.execute();            
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean isIsExisted() {
        return isExisted;
    }

    public void setIsExisted(boolean isExisted) {
        this.isExisted = isExisted;
    }
    
    
}
