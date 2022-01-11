package com.bes_line.mst;

// DBWrapper Class for CDSRMG
/**
 *
 * @(#) CDSRMGDBWrapBES.java
 * Copyright 1999-2001 by  Daewoo Information System, Inc.,
 * BES(Best Enterprise System) Team,
 * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea
 * All rights reserved.
 *
 * NOTICE !  You cannot copy or redistribute this code,
 * and you should not remove the information about the
 * copyright notice and the author.
 *
 * @version v0.1
 * @date    2014-1-10
 * @author  WonDeok Kim, wdkim(at)disc.co.kr.
 * @since   JDK1.2
 *
 */

import java.sql.*;
import org.jsn.jdf.db.*;

public class CDSRMGDBWrapBES extends DBWrapper{

public CDSRMGDBWrapBES(ConnectionContext ctx){
    super(ctx);
} // Constructor

/**
* Get one Record 
* @param String facid, String cattp, String shpkd, String catcd, String upcod
* @return CDSRMGRec 
* @author besTeam 
* @date 2014-1-10
*/
public CDSRMGRec select(String facid, String cattp, String shpkd, String catcd, String upcod) throws Exception{
    CDSRMGRec cdsrmg = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, cattp, shpkd, catcd, upcod, catnm, useyn, lvlid, remrk, " +
                              "adate, auser, mdate, muser " +
                       "  from CDSRMG  " +
                       "  where facid = ? and cattp = ? and shpkd = ? and catcd = ? and upcod = ?  ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,cattp); 
        pstmt.setString(3,shpkd); 
        pstmt.setString(4,catcd); 
        pstmt.setString(5,upcod); 
        rs = pstmt.executeQuery();

        if(rs.next()){
            cdsrmg = new CDSRMGRec(); // CDSRMGRec Constructor
                     cdsrmg.setFacid(rs.getString("facid"));
                     cdsrmg.setCattp(rs.getString("cattp"));
                     cdsrmg.setShpkd(rs.getString("shpkd"));
                     cdsrmg.setCatcd(rs.getString("catcd"));
                     cdsrmg.setUpcod(rs.getString("upcod"));
                     cdsrmg.setCatnm(rs.getString("catnm"));
                     cdsrmg.setUseyn(rs.getString("useyn"));
                     cdsrmg.setLvlid(rs.getInt("lvlid"));
                     cdsrmg.setRemrk(rs.getString("remrk"));
                     cdsrmg.setAdate(rs.getInt("adate"));
                     cdsrmg.setAuser(rs.getString("auser"));
                     cdsrmg.setMdate(rs.getInt("mdate"));
                     cdsrmg.setMuser(rs.getString("muser"));
        } else {
            throw new DataNotFoundException();
        } // end if
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return cdsrmg;
} // end select

/**
* Get All Record 
* @param  void 
* @return java.util.Vector 
* @author besTeam 
* @date 2014-1-10
*/
public java.util.Vector selectAll() throws Exception{
    java.util.Vector cdsrmgV = new java.util.Vector();
    CDSRMGRec cdsrmg = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, cattp, shpkd, catcd, upcod, catnm, useyn, lvlid, remrk, " +
                              "adate, auser, mdate, muser " +
                       "  from CDSRMG ";
        pstmt = connection.prepareStatement(query);
        rs = pstmt.executeQuery();

        while(rs.next()){
            cdsrmg = new CDSRMGRec(); // CDSRMGRec Constructor
                     cdsrmg.setFacid(rs.getString("facid"));
                     cdsrmg.setCattp(rs.getString("cattp"));
                     cdsrmg.setShpkd(rs.getString("shpkd"));
                     cdsrmg.setCatcd(rs.getString("catcd"));
                     cdsrmg.setUpcod(rs.getString("upcod"));
                     cdsrmg.setCatnm(rs.getString("catnm"));
                     cdsrmg.setUseyn(rs.getString("useyn"));
                     cdsrmg.setLvlid(rs.getInt("lvlid"));
                     cdsrmg.setRemrk(rs.getString("remrk"));
                     cdsrmg.setAdate(rs.getInt("adate"));
                     cdsrmg.setAuser(rs.getString("auser"));
                     cdsrmg.setMdate(rs.getInt("mdate"));
                     cdsrmg.setMuser(rs.getString("muser"));
            cdsrmgV.addElement(cdsrmg);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return cdsrmgV;
} // end selectAll

/**
* Get All Record(condition : last Key except) 
* @param String facid, String cattp, String shpkd, String catcd
* @return java.util.Vector 
* @author besTeam 
* @date 2014-1-10
*/
public java.util.Vector selectAll(String facid, String cattp, String shpkd, String catcd) throws Exception{
    java.util.Vector cdsrmgV = new java.util.Vector();
    CDSRMGRec cdsrmg = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, cattp, shpkd, catcd, upcod, catnm, useyn, lvlid, remrk, " +
                              "adate, auser, mdate, muser " +
                       "  from CDSRMG  " +
                       "  where facid = ? and cattp = ? and shpkd = ? and catcd = ?  " +
                       "  order by upcod"; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,cattp); 
        pstmt.setString(3,shpkd); 
        pstmt.setString(4,catcd); 
        rs = pstmt.executeQuery();

        while(rs.next()){
            cdsrmg = new CDSRMGRec(); // CDSRMGRec Constructor
                     cdsrmg.setFacid(rs.getString("facid"));
                     cdsrmg.setCattp(rs.getString("cattp"));
                     cdsrmg.setShpkd(rs.getString("shpkd"));
                     cdsrmg.setCatcd(rs.getString("catcd"));
                     cdsrmg.setUpcod(rs.getString("upcod"));
                     cdsrmg.setCatnm(rs.getString("catnm"));
                     cdsrmg.setUseyn(rs.getString("useyn"));
                     cdsrmg.setLvlid(rs.getInt("lvlid"));
                     cdsrmg.setRemrk(rs.getString("remrk"));
                     cdsrmg.setAdate(rs.getInt("adate"));
                     cdsrmg.setAuser(rs.getString("auser"));
                     cdsrmg.setMdate(rs.getInt("mdate"));
                     cdsrmg.setMuser(rs.getString("muser"));
            cdsrmgV.addElement(cdsrmg);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return cdsrmgV;
} // end selectAll

/**
* Get between Record(condition : last Key from - to) 
* @param String facid, String cattp, String shpkd, String catcd, String f_upcod, String t_upcod
* @return java.util.Vector 
* @author besTeam 
* @date 2014-1-10
*/
public java.util.Vector selectBetween(String facid, String cattp, String shpkd, String catcd, String f_upcod, String t_upcod) throws Exception{
    return selectBetween(facid, cattp, shpkd, catcd, f_upcod, t_upcod, 0);
} // end selectBetween

/**
* Get between Record(condition : last Key from - to) 
* @param String facid, String cattp, String shpkd, String catcd, String f_upcod, String t_upcod, int lastKeyOrder(0 : ASC-Default, 1 : DESC)
* @return java.util.Vector 
* @author besTeam 
* @date 2014-1-10
*/
public java.util.Vector selectBetween(String facid, String cattp, String shpkd, String catcd, String f_upcod, String t_upcod, int lastKeyOrder) throws Exception{
    java.util.Vector cdsrmgV = new java.util.Vector();
    CDSRMGRec cdsrmg = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, cattp, shpkd, catcd, upcod, catnm, useyn, lvlid, remrk, " +
                              "adate, auser, mdate, muser " +
                       "  from CDSRMG  " +
                       "  where facid = ? and cattp = ? and shpkd = ? and catcd = ?  " +
                       "  and upcod between ? and ?  ";
               if(lastKeyOrder == 1){
                   query += " order by DESC upcod"; 
               } else {
                   query += " order by upcod"; 
               } // end if(lastKeyOrder == 1)
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,cattp); 
        pstmt.setString(3,shpkd); 
        pstmt.setString(4,catcd); 
        pstmt.setString(5,f_upcod); 
        pstmt.setString(6,t_upcod); 
        rs = pstmt.executeQuery();

        while(rs.next()){
            cdsrmg = new CDSRMGRec(); // CDSRMGRec Constructor
                     cdsrmg.setFacid(rs.getString("facid"));
                     cdsrmg.setCattp(rs.getString("cattp"));
                     cdsrmg.setShpkd(rs.getString("shpkd"));
                     cdsrmg.setCatcd(rs.getString("catcd"));
                     cdsrmg.setUpcod(rs.getString("upcod"));
                     cdsrmg.setCatnm(rs.getString("catnm"));
                     cdsrmg.setUseyn(rs.getString("useyn"));
                     cdsrmg.setLvlid(rs.getInt("lvlid"));
                     cdsrmg.setRemrk(rs.getString("remrk"));
                     cdsrmg.setAdate(rs.getInt("adate"));
                     cdsrmg.setAuser(rs.getString("auser"));
                     cdsrmg.setMdate(rs.getInt("mdate"));
                     cdsrmg.setMuser(rs.getString("muser"));
            cdsrmgV.addElement(cdsrmg);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return cdsrmgV;
} // end selectBetween

/**
* Select Data Over the key value(s) and default return count(20) 
* @param String facid, String cattp, String shpkd, String catcd, String upcod
* @return java.util.Vector 
* @author besTeam 
* @date 2014-1-10
*/
public java.util.Vector selectOver(String facid, String cattp, String shpkd, String catcd, String upcod) throws Exception{
return selectOver(facid, cattp, shpkd, catcd, upcod,20) ;
}// end selectOver
/**
* Select Data Over(Next) the key value(s) and return record count 
* @param String facid, String cattp, String shpkd, String catcd, String upcod, int 
* @return java.util.Vector 
* @author besTeam 
* @date 2014-1-10
*/
public java.util.Vector selectOver(String facid, String cattp, String shpkd, String catcd, String upcod, int page) throws Exception{
    java.util.Vector cdsrmgV = new java.util.Vector();
    CDSRMGRec cdsrmg = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, cattp, shpkd, catcd, upcod, catnm, useyn, lvlid, remrk, " +
                              "adate, auser, mdate, muser " +
                       "  from CDSRMG  " +
                       "  where facid = ?  and  cattp = ?  and  shpkd = ?  and  catcd = ?  and  upcod >= ? order by upcod "; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,cattp); 
        pstmt.setString(3,shpkd); 
        pstmt.setString(4,catcd); 
        pstmt.setString(5,upcod); 
        rs = pstmt.executeQuery();
        int count = 0;//실제라면 SQL문장에서 Limit해야 되지만...
        while(rs.next()){
            count ++;
            if(count > page ) break;
            cdsrmg = new CDSRMGRec(); // CDSRMGRec Constructor
                     cdsrmg.setFacid(rs.getString("facid"));
                     cdsrmg.setCattp(rs.getString("cattp"));
                     cdsrmg.setShpkd(rs.getString("shpkd"));
                     cdsrmg.setCatcd(rs.getString("catcd"));
                     cdsrmg.setUpcod(rs.getString("upcod"));
                     cdsrmg.setCatnm(rs.getString("catnm"));
                     cdsrmg.setUseyn(rs.getString("useyn"));
                     cdsrmg.setLvlid(rs.getInt("lvlid"));
                     cdsrmg.setRemrk(rs.getString("remrk"));
                     cdsrmg.setAdate(rs.getInt("adate"));
                     cdsrmg.setAuser(rs.getString("auser"));
                     cdsrmg.setMdate(rs.getInt("mdate"));
                     cdsrmg.setMuser(rs.getString("muser"));
            cdsrmgV.addElement(cdsrmg);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return cdsrmgV;
} // end selectOver

/**
* Select Data Under(Previous) the key value(s) and default return count(20) 
* @param String facid, String cattp, String shpkd, String catcd, String upcod
* @return java.util.Vector 
* @author besTeam 
* @date 2014-1-10
*/
public java.util.Vector selectUnder(String facid, String cattp, String shpkd, String catcd, String upcod) throws Exception{
return selectUnder(facid, cattp, shpkd, catcd, upcod,20) ;
}// end selectUnder
/**
* Select Data Under(Previous) the key value(s) and return record count 
* @param String facid, String cattp, String shpkd, String catcd, String upcod, int
* @return java.util.Vector
* @author besTeam 
* @date 2014-1-10
*/
public java.util.Vector selectUnder(String facid, String cattp, String shpkd, String catcd, String upcod, int page) throws Exception{
    java.util.Vector cdsrmgV = new java.util.Vector();
    CDSRMGRec cdsrmg = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, cattp, shpkd, catcd, upcod, catnm, useyn, lvlid, remrk, " +
                              "adate, auser, mdate, muser " +
                       "  from CDSRMG  " +
                       "  where facid = ?  and cattp = ?  and shpkd = ?  and catcd = ?  and upcod <= ? order by upcod desc" ; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,cattp); 
        pstmt.setString(3,shpkd); 
        pstmt.setString(4,catcd); 
        pstmt.setString(5,upcod); 
        rs = pstmt.executeQuery();
        int count = 0;//실제라면 SQL문장에서 Limit해야 되지만...
        while(rs.next()){
            count ++;
            if(count > page ) break;
            cdsrmg = new CDSRMGRec(); // CDSRMGRec Constructor
                     cdsrmg.setFacid(rs.getString("facid"));
                     cdsrmg.setCattp(rs.getString("cattp"));
                     cdsrmg.setShpkd(rs.getString("shpkd"));
                     cdsrmg.setCatcd(rs.getString("catcd"));
                     cdsrmg.setUpcod(rs.getString("upcod"));
                     cdsrmg.setCatnm(rs.getString("catnm"));
                     cdsrmg.setUseyn(rs.getString("useyn"));
                     cdsrmg.setLvlid(rs.getInt("lvlid"));
                     cdsrmg.setRemrk(rs.getString("remrk"));
                     cdsrmg.setAdate(rs.getInt("adate"));
                     cdsrmg.setAuser(rs.getString("auser"));
                     cdsrmg.setMdate(rs.getInt("mdate"));
                     cdsrmg.setMuser(rs.getString("muser"));
            cdsrmgV.add(0,cdsrmg);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return cdsrmgV;
} // end selectUnder

// Insert Data 
/**
* Add Record 
* @param CDSRMGRec 
* @return void 
* @author besTeam 
* @date 2014-1-10
*/
public void insert(CDSRMGRec cdsrmg) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Insert into CDSRMG( " +
                              "facid, cattp, shpkd, catcd, upcod, catnm, useyn, lvlid, remrk, " +
                              "adate, auser, mdate, muser"+
                       " ) values ( "+
                              "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                              "?, ?, ?)";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, cdsrmg.getFacid());
        pstmt.setString(2, cdsrmg.getCattp());
        pstmt.setString(3, cdsrmg.getShpkd());
        pstmt.setString(4, cdsrmg.getCatcd());
        pstmt.setString(5, cdsrmg.getUpcod());
        pstmt.setString(6, cdsrmg.getCatnm());
        pstmt.setString(7, cdsrmg.getUseyn());
        pstmt.setInt(8, cdsrmg.getLvlid());
        pstmt.setString(9, cdsrmg.getRemrk());
        pstmt.setInt(10, cdsrmg.getAdate());
        pstmt.setString(11, cdsrmg.getAuser());
        pstmt.setInt(12, cdsrmg.getMdate());
        pstmt.setString(13, cdsrmg.getMuser());
        int affected = pstmt.executeUpdate();
        if ( affected == 0 ) throw new DataAlreadyExistException();

    } finally {
        try{pstmt.close();}catch(Exception e){}
    } // try-finally

} // end insert


// Update Data 
/**
* Update Record 
* @param CDSRMGRec 
* @return void 
* @author besTeam 
* @date 2014-1-10
*/
public void update(CDSRMGRec cdsrmg) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Update CDSRMG SET "+
                        "facid = ?, cattp = ?, shpkd = ?, catcd = ?, upcod = ?, catnm = ?, useyn = ?, lvlid = ?, remrk = ?, adate = ?, " +
                              "auser = ?, mdate = ?, muser = ?"+
                        " where facid = ? and cattp = ? and shpkd = ? and catcd = ? and upcod = ? ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, cdsrmg.getFacid());
        pstmt.setString(2, cdsrmg.getCattp());
        pstmt.setString(3, cdsrmg.getShpkd());
        pstmt.setString(4, cdsrmg.getCatcd());
        pstmt.setString(5, cdsrmg.getUpcod());
        pstmt.setString(6, cdsrmg.getCatnm());
        pstmt.setString(7, cdsrmg.getUseyn());
        pstmt.setInt(8, cdsrmg.getLvlid());
        pstmt.setString(9, cdsrmg.getRemrk());
        pstmt.setInt(10, cdsrmg.getAdate());
        pstmt.setString(11, cdsrmg.getAuser());
        pstmt.setInt(12, cdsrmg.getMdate());
        pstmt.setString(13, cdsrmg.getMuser());
        // Key
        pstmt.setString(14, cdsrmg.getFacid()); 
        pstmt.setString(15, cdsrmg.getCattp()); 
        pstmt.setString(16, cdsrmg.getShpkd()); 
        pstmt.setString(17, cdsrmg.getCatcd()); 
        pstmt.setString(18, cdsrmg.getUpcod()); 
        int affected = pstmt.executeUpdate();
        if ( affected == 0 ) throw new NoAffectedException();
        else if ( affected > 1 ) throw new TooManyAffectedException();
    } finally {
        try{pstmt.close();}catch(Exception e){}
    } // try-finally

} // end Update

/**
* Delete Record 
* @param String facid, String cattp, String shpkd, String catcd, String upcod
* @return void 
* @author besTeam 
* @date 2014-1-10
*/
public void delete(String facid, String cattp, String shpkd, String catcd, String upcod) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Delete From CDSRMG "+
                       "where facid = ? and cattp = ? and shpkd = ? and catcd = ? and upcod = ? ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,cattp); 
        pstmt.setString(3,shpkd); 
        pstmt.setString(4,catcd); 
        pstmt.setString(5,upcod); 
        int affected = pstmt.executeUpdate();
        if ( affected == 0 ){
            throw new NoAffectedException();
        } else if ( affected > 1 ) {
            throw new TooManyAffectedException();
        } // end if affection
    } finally {
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
} // end Delete

/**
* Delete Record 
* @param CDSRMGRec 
* @return void 
* @author besTeam 
* @date 2014-1-10
*/
public void delete(CDSRMGRec cdsrmg) throws Exception{
     delete(cdsrmg.getFacid(), cdsrmg.getCattp(), cdsrmg.getShpkd(), cdsrmg.getCatcd(), cdsrmg.getUpcod());
} // end Delete

/**
* Get Rows Count 
* @param String facid, String cattp, String shpkd, String catcd, String upcod
* @return int 
* @author besTeam 
* @date 2014-1-10
*/
public int count(String facid, String cattp, String shpkd, String catcd, String upcod) throws Exception{
    int count = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "SELECT COUNT(*) from CDSRMG " +
                       " where facid = ? and cattp = ? and shpkd = ? and catcd = ? and upcod = ?   ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,cattp); 
        pstmt.setString(3,shpkd); 
        pstmt.setString(4,catcd); 
        pstmt.setString(5,upcod); 
        rs = pstmt.executeQuery();

        if(rs.next()){
            count = rs.getInt(1);
        } // end if
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return count;
} // end count


/**
* Get All Rows Count 
* @param void 
* @return int 
* @author besTeam 
* @date 2014-1-10
*/
public int count() throws Exception{
    int count = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "SELECT COUNT(*) from CDSRMG  ";
        pstmt = connection.prepareStatement(query);
        rs = pstmt.executeQuery();

        if(rs.next()){
            count = rs.getInt(1);
        } // end if
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return count;
} // end count

}// end CDSRMGDBWrap class