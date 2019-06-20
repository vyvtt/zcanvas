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
import java.sql.SQLException;
import javax.naming.NamingException;
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
}
