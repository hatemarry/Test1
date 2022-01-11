package com.bes_line.mst;

// DBWrapper Class for QMTDTL
/**
 *
 * @(#) QMTDTLDBWrapBES.java
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
 * @date    2014-3-14
 * @author  WonDeok Kim, wdkim(at)disc.co.kr.
 * @since   JDK1.2
 *
 */

import java.sql.*;
import org.jsn.jdf.db.*;

public class QMTDTLDBWrapBES extends DBWrapper{

public QMTDTLDBWrapBES(ConnectionContext ctx){
    super(ctx);
} // Constructor

/**
* Get one Record 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt
* @return QMTDTLRec 
* @author besTeam 
* @date 2014-3-14
*/
public QMTDTLRec select(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt) throws Exception{
    QMTDTLRec qmtdtl = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt, code1, " +
                              "seqnm, rptno, inrcd, ldval, cdval, dftps, dftlt, cfmlt, wldno, " +
                              "orglv, remrk, adate, mdate, auser, muser, trmid " +
                       "  from QMTDTL  " +
                       "  where facid = ? and prjno = ? and ndtkd = ? and insit = ? and insgp = ? and instl = ? and inpkn = ? and ipcnt = ?  ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,prjno); 
        pstmt.setString(3,ndtkd); 
        pstmt.setString(4,insit); 
        pstmt.setString(5,insgp); 
        pstmt.setString(6,instl); 
        pstmt.setString(7,inpkn); 
        pstmt.setString(8,ipcnt); 
        rs = pstmt.executeQuery();

        if(rs.next()){
            qmtdtl = new QMTDTLRec(); // QMTDTLRec Constructor
                     qmtdtl.setFacid(rs.getString("facid"));
                     qmtdtl.setPrjno(rs.getString("prjno"));
                     qmtdtl.setNdtkd(rs.getString("ndtkd"));
                     qmtdtl.setInsit(rs.getString("insit"));
                     qmtdtl.setInsgp(rs.getString("insgp"));
                     qmtdtl.setInstl(rs.getString("instl"));
                     qmtdtl.setInpkn(rs.getString("inpkn"));
                     qmtdtl.setIpcnt(rs.getString("ipcnt"));
                     qmtdtl.setCode1(rs.getString("code1"));
                     qmtdtl.setSeqnm(rs.getString("seqnm"));
                     qmtdtl.setRptno(rs.getString("rptno"));
                     qmtdtl.setInrcd(rs.getString("inrcd"));
                     qmtdtl.setLdval(rs.getString("ldval"));
                     qmtdtl.setCdval(rs.getString("cdval"));
                     qmtdtl.setDftps(rs.getString("dftps"));
                     qmtdtl.setDftlt(rs.getDouble("dftlt"));
                     qmtdtl.setCfmlt(rs.getDouble("cfmlt"));
                     qmtdtl.setWldno(rs.getString("wldno"));
                     qmtdtl.setOrglv(rs.getString("orglv"));
                     qmtdtl.setRemrk(rs.getString("remrk"));
                     qmtdtl.setAdate(rs.getInt("adate"));
                     qmtdtl.setMdate(rs.getInt("mdate"));
                     qmtdtl.setAuser(rs.getString("auser"));
                     qmtdtl.setMuser(rs.getString("muser"));
                     qmtdtl.setTrmid(rs.getString("trmid"));
        } else {
            throw new DataNotFoundException();
        } // end if
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return qmtdtl;
} // end select

/**
* Get All Record 
* @param  void 
* @return java.util.Vector 
* @author besTeam 
* @date 2014-3-14
*/
public java.util.Vector selectAll() throws Exception{
    java.util.Vector qmtdtlV = new java.util.Vector();
    QMTDTLRec qmtdtl = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt, code1, " +
                              "seqnm, rptno, inrcd, ldval, cdval, dftps, dftlt, cfmlt, wldno, " +
                              "orglv, remrk, adate, mdate, auser, muser, trmid " +
                       "  from QMTDTL ";
        pstmt = connection.prepareStatement(query);
        rs = pstmt.executeQuery();

        while(rs.next()){
            qmtdtl = new QMTDTLRec(); // QMTDTLRec Constructor
                     qmtdtl.setFacid(rs.getString("facid"));
                     qmtdtl.setPrjno(rs.getString("prjno"));
                     qmtdtl.setNdtkd(rs.getString("ndtkd"));
                     qmtdtl.setInsit(rs.getString("insit"));
                     qmtdtl.setInsgp(rs.getString("insgp"));
                     qmtdtl.setInstl(rs.getString("instl"));
                     qmtdtl.setInpkn(rs.getString("inpkn"));
                     qmtdtl.setIpcnt(rs.getString("ipcnt"));
                     qmtdtl.setCode1(rs.getString("code1"));
                     qmtdtl.setSeqnm(rs.getString("seqnm"));
                     qmtdtl.setRptno(rs.getString("rptno"));
                     qmtdtl.setInrcd(rs.getString("inrcd"));
                     qmtdtl.setLdval(rs.getString("ldval"));
                     qmtdtl.setCdval(rs.getString("cdval"));
                     qmtdtl.setDftps(rs.getString("dftps"));
                     qmtdtl.setDftlt(rs.getDouble("dftlt"));
                     qmtdtl.setCfmlt(rs.getDouble("cfmlt"));
                     qmtdtl.setWldno(rs.getString("wldno"));
                     qmtdtl.setOrglv(rs.getString("orglv"));
                     qmtdtl.setRemrk(rs.getString("remrk"));
                     qmtdtl.setAdate(rs.getInt("adate"));
                     qmtdtl.setMdate(rs.getInt("mdate"));
                     qmtdtl.setAuser(rs.getString("auser"));
                     qmtdtl.setMuser(rs.getString("muser"));
                     qmtdtl.setTrmid(rs.getString("trmid"));
            qmtdtlV.addElement(qmtdtl);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return qmtdtlV;
} // end selectAll

/**
* Get All Record(condition : last Key except) 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn
* @return java.util.Vector 
* @author besTeam 
* @date 2014-3-14
*/
public java.util.Vector selectAll(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn) throws Exception{
    java.util.Vector qmtdtlV = new java.util.Vector();
    QMTDTLRec qmtdtl = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt, code1, " +
                              "seqnm, rptno, inrcd, ldval, cdval, dftps, dftlt, cfmlt, wldno, " +
                              "orglv, remrk, adate, mdate, auser, muser, trmid " +
                       "  from QMTDTL  " +
                       "  where facid = ? and prjno = ? and ndtkd = ? and insit = ? and insgp = ? and instl = ? and inpkn = ?  " +
                       "  order by ipcnt"; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,prjno); 
        pstmt.setString(3,ndtkd); 
        pstmt.setString(4,insit); 
        pstmt.setString(5,insgp); 
        pstmt.setString(6,instl); 
        pstmt.setString(7,inpkn); 
        rs = pstmt.executeQuery();

        while(rs.next()){
            qmtdtl = new QMTDTLRec(); // QMTDTLRec Constructor
                     qmtdtl.setFacid(rs.getString("facid"));
                     qmtdtl.setPrjno(rs.getString("prjno"));
                     qmtdtl.setNdtkd(rs.getString("ndtkd"));
                     qmtdtl.setInsit(rs.getString("insit"));
                     qmtdtl.setInsgp(rs.getString("insgp"));
                     qmtdtl.setInstl(rs.getString("instl"));
                     qmtdtl.setInpkn(rs.getString("inpkn"));
                     qmtdtl.setIpcnt(rs.getString("ipcnt"));
                     qmtdtl.setCode1(rs.getString("code1"));
                     qmtdtl.setSeqnm(rs.getString("seqnm"));
                     qmtdtl.setRptno(rs.getString("rptno"));
                     qmtdtl.setInrcd(rs.getString("inrcd"));
                     qmtdtl.setLdval(rs.getString("ldval"));
                     qmtdtl.setCdval(rs.getString("cdval"));
                     qmtdtl.setDftps(rs.getString("dftps"));
                     qmtdtl.setDftlt(rs.getDouble("dftlt"));
                     qmtdtl.setCfmlt(rs.getDouble("cfmlt"));
                     qmtdtl.setWldno(rs.getString("wldno"));
                     qmtdtl.setOrglv(rs.getString("orglv"));
                     qmtdtl.setRemrk(rs.getString("remrk"));
                     qmtdtl.setAdate(rs.getInt("adate"));
                     qmtdtl.setMdate(rs.getInt("mdate"));
                     qmtdtl.setAuser(rs.getString("auser"));
                     qmtdtl.setMuser(rs.getString("muser"));
                     qmtdtl.setTrmid(rs.getString("trmid"));
            qmtdtlV.addElement(qmtdtl);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return qmtdtlV;
} // end selectAll

/**
* Get between Record(condition : last Key from - to) 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String f_ipcnt, String t_ipcnt
* @return java.util.Vector 
* @author besTeam 
* @date 2014-3-14
*/
public java.util.Vector selectBetween(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String f_ipcnt, String t_ipcnt) throws Exception{
    return selectBetween(facid, prjno, ndtkd, insit, insgp, instl, inpkn, f_ipcnt, t_ipcnt, 0);
} // end selectBetween

/**
* Get between Record(condition : last Key from - to) 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String f_ipcnt, String t_ipcnt, int lastKeyOrder(0 : ASC-Default, 1 : DESC)
* @return java.util.Vector 
* @author besTeam 
* @date 2014-3-14
*/
public java.util.Vector selectBetween(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String f_ipcnt, String t_ipcnt, int lastKeyOrder) throws Exception{
    java.util.Vector qmtdtlV = new java.util.Vector();
    QMTDTLRec qmtdtl = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt, code1, " +
                              "seqnm, rptno, inrcd, ldval, cdval, dftps, dftlt, cfmlt, wldno, " +
                              "orglv, remrk, adate, mdate, auser, muser, trmid " +
                       "  from QMTDTL  " +
                       "  where facid = ? and prjno = ? and ndtkd = ? and insit = ? and insgp = ? and instl = ? and inpkn = ?  " +
                       "  and ipcnt between ? and ?  ";
               if(lastKeyOrder == 1){
                   query += " order by DESC ipcnt"; 
               } else {
                   query += " order by ipcnt"; 
               } // end if(lastKeyOrder == 1)
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,prjno); 
        pstmt.setString(3,ndtkd); 
        pstmt.setString(4,insit); 
        pstmt.setString(5,insgp); 
        pstmt.setString(6,instl); 
        pstmt.setString(7,inpkn); 
        pstmt.setString(8,f_ipcnt); 
        pstmt.setString(9,t_ipcnt); 
        rs = pstmt.executeQuery();

        while(rs.next()){
            qmtdtl = new QMTDTLRec(); // QMTDTLRec Constructor
                     qmtdtl.setFacid(rs.getString("facid"));
                     qmtdtl.setPrjno(rs.getString("prjno"));
                     qmtdtl.setNdtkd(rs.getString("ndtkd"));
                     qmtdtl.setInsit(rs.getString("insit"));
                     qmtdtl.setInsgp(rs.getString("insgp"));
                     qmtdtl.setInstl(rs.getString("instl"));
                     qmtdtl.setInpkn(rs.getString("inpkn"));
                     qmtdtl.setIpcnt(rs.getString("ipcnt"));
                     qmtdtl.setCode1(rs.getString("code1"));
                     qmtdtl.setSeqnm(rs.getString("seqnm"));
                     qmtdtl.setRptno(rs.getString("rptno"));
                     qmtdtl.setInrcd(rs.getString("inrcd"));
                     qmtdtl.setLdval(rs.getString("ldval"));
                     qmtdtl.setCdval(rs.getString("cdval"));
                     qmtdtl.setDftps(rs.getString("dftps"));
                     qmtdtl.setDftlt(rs.getDouble("dftlt"));
                     qmtdtl.setCfmlt(rs.getDouble("cfmlt"));
                     qmtdtl.setWldno(rs.getString("wldno"));
                     qmtdtl.setOrglv(rs.getString("orglv"));
                     qmtdtl.setRemrk(rs.getString("remrk"));
                     qmtdtl.setAdate(rs.getInt("adate"));
                     qmtdtl.setMdate(rs.getInt("mdate"));
                     qmtdtl.setAuser(rs.getString("auser"));
                     qmtdtl.setMuser(rs.getString("muser"));
                     qmtdtl.setTrmid(rs.getString("trmid"));
            qmtdtlV.addElement(qmtdtl);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return qmtdtlV;
} // end selectBetween

/**
* Select Data Over the key value(s) and default return count(20) 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt
* @return java.util.Vector 
* @author besTeam 
* @date 2014-3-14
*/
public java.util.Vector selectOver(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt) throws Exception{
return selectOver(facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt,20) ;
}// end selectOver
/**
* Select Data Over(Next) the key value(s) and return record count 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt, int 
* @return java.util.Vector 
* @author besTeam 
* @date 2014-3-14
*/
public java.util.Vector selectOver(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt, int page) throws Exception{
    java.util.Vector qmtdtlV = new java.util.Vector();
    QMTDTLRec qmtdtl = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt, code1, " +
                              "seqnm, rptno, inrcd, ldval, cdval, dftps, dftlt, cfmlt, wldno, " +
                              "orglv, remrk, adate, mdate, auser, muser, trmid " +
                       "  from QMTDTL  " +
                       "  where facid = ?  and  prjno = ?  and  ndtkd = ?  and  insit = ?  and  insgp = ?  and  instl = ?  and  inpkn = ?  and  ipcnt >= ? order by ipcnt "; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,prjno); 
        pstmt.setString(3,ndtkd); 
        pstmt.setString(4,insit); 
        pstmt.setString(5,insgp); 
        pstmt.setString(6,instl); 
        pstmt.setString(7,inpkn); 
        pstmt.setString(8,ipcnt); 
        rs = pstmt.executeQuery();
        int count = 0;//실제라면 SQL문장에서 Limit해야 되지만...
        while(rs.next()){
            count ++;
            if(count > page ) break;
            qmtdtl = new QMTDTLRec(); // QMTDTLRec Constructor
                     qmtdtl.setFacid(rs.getString("facid"));
                     qmtdtl.setPrjno(rs.getString("prjno"));
                     qmtdtl.setNdtkd(rs.getString("ndtkd"));
                     qmtdtl.setInsit(rs.getString("insit"));
                     qmtdtl.setInsgp(rs.getString("insgp"));
                     qmtdtl.setInstl(rs.getString("instl"));
                     qmtdtl.setInpkn(rs.getString("inpkn"));
                     qmtdtl.setIpcnt(rs.getString("ipcnt"));
                     qmtdtl.setCode1(rs.getString("code1"));
                     qmtdtl.setSeqnm(rs.getString("seqnm"));
                     qmtdtl.setRptno(rs.getString("rptno"));
                     qmtdtl.setInrcd(rs.getString("inrcd"));
                     qmtdtl.setLdval(rs.getString("ldval"));
                     qmtdtl.setCdval(rs.getString("cdval"));
                     qmtdtl.setDftps(rs.getString("dftps"));
                     qmtdtl.setDftlt(rs.getDouble("dftlt"));
                     qmtdtl.setCfmlt(rs.getDouble("cfmlt"));
                     qmtdtl.setWldno(rs.getString("wldno"));
                     qmtdtl.setOrglv(rs.getString("orglv"));
                     qmtdtl.setRemrk(rs.getString("remrk"));
                     qmtdtl.setAdate(rs.getInt("adate"));
                     qmtdtl.setMdate(rs.getInt("mdate"));
                     qmtdtl.setAuser(rs.getString("auser"));
                     qmtdtl.setMuser(rs.getString("muser"));
                     qmtdtl.setTrmid(rs.getString("trmid"));
            qmtdtlV.addElement(qmtdtl);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return qmtdtlV;
} // end selectOver

/**
* Select Data Under(Previous) the key value(s) and default return count(20) 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt
* @return java.util.Vector 
* @author besTeam 
* @date 2014-3-14
*/
public java.util.Vector selectUnder(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt) throws Exception{
return selectUnder(facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt,20) ;
}// end selectUnder
/**
* Select Data Under(Previous) the key value(s) and return record count 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt, int
* @return java.util.Vector
* @author besTeam 
* @date 2014-3-14
*/
public java.util.Vector selectUnder(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt, int page) throws Exception{
    java.util.Vector qmtdtlV = new java.util.Vector();
    QMTDTLRec qmtdtl = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt, code1, " +
                              "seqnm, rptno, inrcd, ldval, cdval, dftps, dftlt, cfmlt, wldno, " +
                              "orglv, remrk, adate, mdate, auser, muser, trmid " +
                       "  from QMTDTL  " +
                       "  where facid = ?  and prjno = ?  and ndtkd = ?  and insit = ?  and insgp = ?  and instl = ?  and inpkn = ?  and ipcnt <= ? order by ipcnt desc" ; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,prjno); 
        pstmt.setString(3,ndtkd); 
        pstmt.setString(4,insit); 
        pstmt.setString(5,insgp); 
        pstmt.setString(6,instl); 
        pstmt.setString(7,inpkn); 
        pstmt.setString(8,ipcnt); 
        rs = pstmt.executeQuery();
        int count = 0;//실제라면 SQL문장에서 Limit해야 되지만...
        while(rs.next()){
            count ++;
            if(count > page ) break;
            qmtdtl = new QMTDTLRec(); // QMTDTLRec Constructor
                     qmtdtl.setFacid(rs.getString("facid"));
                     qmtdtl.setPrjno(rs.getString("prjno"));
                     qmtdtl.setNdtkd(rs.getString("ndtkd"));
                     qmtdtl.setInsit(rs.getString("insit"));
                     qmtdtl.setInsgp(rs.getString("insgp"));
                     qmtdtl.setInstl(rs.getString("instl"));
                     qmtdtl.setInpkn(rs.getString("inpkn"));
                     qmtdtl.setIpcnt(rs.getString("ipcnt"));
                     qmtdtl.setCode1(rs.getString("code1"));
                     qmtdtl.setSeqnm(rs.getString("seqnm"));
                     qmtdtl.setRptno(rs.getString("rptno"));
                     qmtdtl.setInrcd(rs.getString("inrcd"));
                     qmtdtl.setLdval(rs.getString("ldval"));
                     qmtdtl.setCdval(rs.getString("cdval"));
                     qmtdtl.setDftps(rs.getString("dftps"));
                     qmtdtl.setDftlt(rs.getDouble("dftlt"));
                     qmtdtl.setCfmlt(rs.getDouble("cfmlt"));
                     qmtdtl.setWldno(rs.getString("wldno"));
                     qmtdtl.setOrglv(rs.getString("orglv"));
                     qmtdtl.setRemrk(rs.getString("remrk"));
                     qmtdtl.setAdate(rs.getInt("adate"));
                     qmtdtl.setMdate(rs.getInt("mdate"));
                     qmtdtl.setAuser(rs.getString("auser"));
                     qmtdtl.setMuser(rs.getString("muser"));
                     qmtdtl.setTrmid(rs.getString("trmid"));
            qmtdtlV.add(0,qmtdtl);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return qmtdtlV;
} // end selectUnder

// Insert Data 
/**
* Add Record 
* @param QMTDTLRec 
* @return void 
* @author besTeam 
* @date 2014-3-14
*/
public void insert(QMTDTLRec qmtdtl) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Insert into QMTDTL( " +
                              "facid, prjno, ndtkd, insit, insgp, instl, inpkn, ipcnt, code1, " +
                              "seqnm, rptno, inrcd, ldval, cdval, dftps, dftlt, cfmlt, wldno, " +
                              "orglv, remrk, adate, mdate, auser, muser, trmid"+
                       " ) values ( "+
                              "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                              "?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                              "?, ?, ?, ?, ?, ?)";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, qmtdtl.getFacid());
        pstmt.setString(2, qmtdtl.getPrjno());
        pstmt.setString(3, qmtdtl.getNdtkd());
        pstmt.setString(4, qmtdtl.getInsit());
        pstmt.setString(5, qmtdtl.getInsgp());
        pstmt.setString(6, qmtdtl.getInstl());
        pstmt.setString(7, qmtdtl.getInpkn());
        pstmt.setString(8, qmtdtl.getIpcnt());
        pstmt.setString(9, qmtdtl.getCode1());
        pstmt.setString(10, qmtdtl.getSeqnm());
        pstmt.setString(11, qmtdtl.getRptno());
        pstmt.setString(12, qmtdtl.getInrcd());
        pstmt.setString(13, qmtdtl.getLdval());
        pstmt.setString(14, qmtdtl.getCdval());
        pstmt.setString(15, qmtdtl.getDftps());
        pstmt.setDouble(16, qmtdtl.getDftlt());
        pstmt.setDouble(17, qmtdtl.getCfmlt());
        pstmt.setString(18, qmtdtl.getWldno());
        pstmt.setString(19, qmtdtl.getOrglv());
        pstmt.setString(20, qmtdtl.getRemrk());
        pstmt.setInt(21, qmtdtl.getAdate());
        pstmt.setInt(22, qmtdtl.getMdate());
        pstmt.setString(23, qmtdtl.getAuser());
        pstmt.setString(24, qmtdtl.getMuser());
        pstmt.setString(25, qmtdtl.getTrmid());
        int affected = pstmt.executeUpdate();
        if ( affected == 0 ) throw new DataAlreadyExistException();

    } finally {
        try{pstmt.close();}catch(Exception e){}
    } // try-finally

} // end insert


// Update Data 
/**
* Update Record 
* @param QMTDTLRec 
* @return void 
* @author besTeam 
* @date 2014-3-14
*/
public void update(QMTDTLRec qmtdtl) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Update QMTDTL SET "+
                        "facid = ?, prjno = ?, ndtkd = ?, insit = ?, insgp = ?, instl = ?, inpkn = ?, ipcnt = ?, code1 = ?, seqnm = ?, " +
                              "rptno = ?, inrcd = ?, ldval = ?, cdval = ?, dftps = ?, dftlt = ?, cfmlt = ?, wldno = ?, orglv = ?, " +
                              "remrk = ?, adate = ?, mdate = ?, auser = ?, muser = ?, trmid = ?"+
                        " where facid = ? and prjno = ? and ndtkd = ? and insit = ? and insgp = ? and instl = ? and inpkn = ? and ipcnt = ? ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, qmtdtl.getFacid());
        pstmt.setString(2, qmtdtl.getPrjno());
        pstmt.setString(3, qmtdtl.getNdtkd());
        pstmt.setString(4, qmtdtl.getInsit());
        pstmt.setString(5, qmtdtl.getInsgp());
        pstmt.setString(6, qmtdtl.getInstl());
        pstmt.setString(7, qmtdtl.getInpkn());
        pstmt.setString(8, qmtdtl.getIpcnt());
        pstmt.setString(9, qmtdtl.getCode1());
        pstmt.setString(10, qmtdtl.getSeqnm());
        pstmt.setString(11, qmtdtl.getRptno());
        pstmt.setString(12, qmtdtl.getInrcd());
        pstmt.setString(13, qmtdtl.getLdval());
        pstmt.setString(14, qmtdtl.getCdval());
        pstmt.setString(15, qmtdtl.getDftps());
        pstmt.setDouble(16, qmtdtl.getDftlt());
        pstmt.setDouble(17, qmtdtl.getCfmlt());
        pstmt.setString(18, qmtdtl.getWldno());
        pstmt.setString(19, qmtdtl.getOrglv());
        pstmt.setString(20, qmtdtl.getRemrk());
        pstmt.setInt(21, qmtdtl.getAdate());
        pstmt.setInt(22, qmtdtl.getMdate());
        pstmt.setString(23, qmtdtl.getAuser());
        pstmt.setString(24, qmtdtl.getMuser());
        pstmt.setString(25, qmtdtl.getTrmid());
        // Key
        pstmt.setString(26, qmtdtl.getFacid()); 
        pstmt.setString(27, qmtdtl.getPrjno()); 
        pstmt.setString(28, qmtdtl.getNdtkd()); 
        pstmt.setString(29, qmtdtl.getInsit()); 
        pstmt.setString(30, qmtdtl.getInsgp()); 
        pstmt.setString(31, qmtdtl.getInstl()); 
        pstmt.setString(32, qmtdtl.getInpkn()); 
        pstmt.setString(33, qmtdtl.getIpcnt()); 
        int affected = pstmt.executeUpdate();
        if ( affected == 0 ) throw new NoAffectedException();
        else if ( affected > 1 ) throw new TooManyAffectedException();
    } finally {
        try{pstmt.close();}catch(Exception e){}
    } // try-finally

} // end Update

/**
* Delete Record 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt
* @return void 
* @author besTeam 
* @date 2014-3-14
*/
public void delete(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Delete From QMTDTL "+
                       "where facid = ? and prjno = ? and ndtkd = ? and insit = ? and insgp = ? and instl = ? and inpkn = ? and ipcnt = ? ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,prjno); 
        pstmt.setString(3,ndtkd); 
        pstmt.setString(4,insit); 
        pstmt.setString(5,insgp); 
        pstmt.setString(6,instl); 
        pstmt.setString(7,inpkn); 
        pstmt.setString(8,ipcnt); 
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
* @param QMTDTLRec 
* @return void 
* @author besTeam 
* @date 2014-3-14
*/
public void delete(QMTDTLRec qmtdtl) throws Exception{
     delete(qmtdtl.getFacid(), qmtdtl.getPrjno(), qmtdtl.getNdtkd(), qmtdtl.getInsit(), qmtdtl.getInsgp(), qmtdtl.getInstl(), qmtdtl.getInpkn(), qmtdtl.getIpcnt());
} // end Delete

/**
* Get Rows Count 
* @param String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt
* @return int 
* @author besTeam 
* @date 2014-3-14
*/
public int count(String facid, String prjno, String ndtkd, String insit, String insgp, String instl, String inpkn, String ipcnt) throws Exception{
    int count = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "SELECT COUNT(*) from QMTDTL " +
                       " where facid = ? and prjno = ? and ndtkd = ? and insit = ? and insgp = ? and instl = ? and inpkn = ? and ipcnt = ?   ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,prjno); 
        pstmt.setString(3,ndtkd); 
        pstmt.setString(4,insit); 
        pstmt.setString(5,insgp); 
        pstmt.setString(6,instl); 
        pstmt.setString(7,inpkn); 
        pstmt.setString(8,ipcnt); 
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
* @date 2014-3-14
*/
public int count() throws Exception{
    int count = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "SELECT COUNT(*) from QMTDTL  ";
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

}// end QMTDTLDBWrap class