/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import project.jaxb.Detail;
import project.utils.DBUtils;

/**
 *
 * @author thuyv
 */
public class DetailDAO implements Serializable{
    
    public void insert(List<Detail> listDetail, int canvasId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        
        if (listDetail == null || listDetail.isEmpty()) {
            System.out.println("Lits Detail of canvas " + canvasId + " null/empty -> skip");
            return;
        }
        
//        id int IDENTITY(1,1) PRIMARY KEY,
//        canvasId int NOT NULL FOREIGN KEY REFERENCES Canvas(id),
//        width int NULL,
//        length int NULL,
//        unit VARCHAR(10) NULL,
//        price int NULL

        try {
            con = DBUtils.getConnection();
            String sql = "INSERT INTO Detail(canvasId, width, length, unit, price) "
                    + "VALUES (?,?,?,?,?)";
            
            con.setAutoCommit(false);
            stm = con.prepareStatement(sql);
            
            for (Detail detail : listDetail) {
                
                
                
                stm.setInt(1, canvasId);
                stm.setInt(2, detail.getWidth() == null ? 0 : detail.getWidth().intValue());
                stm.setInt(3, detail.getLength() == null ? 0 : detail.getLength().intValue());
                stm.setString(4, detail.getUnit());
                stm.setInt(5, detail.getPrice()== null ? 0 : detail.getPrice().intValue());
                stm.addBatch();
            }
            stm.executeBatch();
            con.commit();
        } catch (Exception e) {
            Logger.getLogger(DetailDAO.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }
    
}
