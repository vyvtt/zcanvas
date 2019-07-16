/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import project.jaxb.Canvas;
import project.jaxb.Categories;
import project.utils.DBUtils;
import project.utils.StringHelper;

/**
 *
 * @author thuyv
 */
public class CanvasDAO implements Serializable {

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
            // store procudure: trả về id của canvas (id = select nếu canvas existed, insert new canvas nếu chưa existed)
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
            if (this.isExisted) {
                System.out.println("DB SKIP: " + canvas.getName());
            } else {
                System.out.println("DB: " + canvas.getName());
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

    public List<Canvas> getAllCanvasByCategory(int categoryId) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "Select * from Canvas where Canvas.id in "
                    + "(select CanvasId from CategoryCanvas where CategoryCanvas.CategoryId = ?)";
//            String sql = "Select * from Canvas";
            stm = con.prepareStatement(sql);
            stm.setInt(1, categoryId);

            rs = stm.executeQuery();

            List<Canvas> result = new ArrayList<>();
            while (rs.next()) {
                Canvas canvas = new Canvas();
                canvas.setName(rs.getNString("name"));
                canvas.setUrl(rs.getString("url"));
                canvas.setImage(rs.getString("image"));
                canvas.setColorPalatte(rs.getString("color"));
                result.add(canvas);
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

    public List<Canvas> getAllCanvasByCategory(List<Categories> listCategory) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

            String s = "Select * from Canvas where Canvas.id in \n"
                    + "(select CanvasId from CategoryCanvas where CategoryCanvas.CategoryId = ?)";

            List<Canvas> result = new ArrayList<>();
            List<Integer> canvasIds = new ArrayList<>();

            Map<Integer, Canvas> map = new HashMap<>();

            for (Categories category : listCategory) {

                stm = con.prepareStatement(s);
                stm.setInt(1, category.getId());
                rs = stm.executeQuery();

                while (rs.next()) {

                    if (map.containsKey(rs.getInt("id"))) {
                        map.get(rs.getInt("id")).getCanvasCategories().add(category.getId());
                    } else {
                        Canvas canvas = new Canvas();
                        canvas.setName(rs.getNString("name").replace("'", "\\'"));
                        canvas.setUrl(rs.getString("url"));
                        canvas.setImage(rs.getString("image"));
                        canvas.setColorPalatte(rs.getString("color"));
                        List<Integer> tmp = new ArrayList<>();
                        tmp.add(category.getId());
                        canvas.setCanvasCategories(tmp);
                        map.put(rs.getInt("id"), canvas);
                    }

                }
            }
            System.out.println("total map: " + map.size());
            return new ArrayList<Canvas>(map.values());
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

    public List<Canvas> getAllCanvas() {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();

//            String s = "SELECT name, url, image, color FROM Canvas";
            String s = "SELECT name, url, image, color FROM Canvas WHERE id NOT IN \n"
                    + "(SELECT CanvasId FROM CategoryCanvas WHERE CategoryId IN \n"
                    + "(SELECT id FROM Category WHERE rtrim(name) like '%phòng%'))";
            stm = con.prepareStatement(s);
            rs = stm.executeQuery();

            List<Canvas> result = new ArrayList<>();

            while (rs.next()) {
                Canvas canvas = new Canvas();
                canvas.setName(rs.getNString("name").replace("'", "\\'"));
                canvas.setUrl(rs.getString("url"));
                canvas.setImage(rs.getString("image"));
                canvas.setColorPalatte(rs.getString("color"));
                result.add(canvas);
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

}
