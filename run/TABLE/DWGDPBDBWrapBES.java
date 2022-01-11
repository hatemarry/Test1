package com.bes_line.mst;

// DBWrapper Class for DWGDPB
/**
 *
 * @(#) DWGDPBDBWrapBES.java
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
 * @date    2014-5-13
 * @author  WonDeok Kim, wdkim(at)disc.co.kr.
 * @since   JDK1.2
 *
 */

import java.sql.*;
import org.jsn.jdf.db.*;

public class DWGDPBDBWrapBES extends DBWrapper{

public DWGDPBDBWrapBES(ConnectionContext ctx){
    super(ctx);
} // Constructor

/**
* Get one Record 
* @param String facid, String dwgno, String chgdt
* @return DWGDPBRec 
* @author besTeam 
* @date 2014-5-13
*/
public DWGDPBRec select(String facid, String dwgno, String chgdt) throws Exception{
    DWGDPBRec dwgdpb = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, dwgno, chgdt, chgds, revno, stscd, prjno, dwggr, blkno, " +
                              "dwgmp, dwgsr, dwgds, mkdem, dptno, dwipd, dspdt, adtpd, mkdps, " +
                              "mkdpf, porpd, dwipt, baspd, dwimg, incbk, iprbk, mkdpt, adtpt, " +
                              "porpt, dwind, dspnd, adtnd, mksnd, mkfnd, pornd, remrk, adate, " +
                              "auser, mdate, muser, apfpt, apfnd, apfpf, apopf, apond, apcpf, " +
                              "apcnd, dwipo, dwimo, basod, prsst, keycd " +
                       "  from DWGDPB  " +
                       "  where facid = ? and dwgno = ? and chgdt = ?  ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,dwgno); 
        pstmt.setString(3,chgdt); 
        rs = pstmt.executeQuery();

        if(rs.next()){
            dwgdpb = new DWGDPBRec(); // DWGDPBRec Constructor
                     dwgdpb.setFacid(rs.getString("facid"));
                     dwgdpb.setDwgno(rs.getString("dwgno"));
                     dwgdpb.setChgdt(rs.getString("chgdt"));
                     dwgdpb.setChgds(rs.getString("chgds"));
                     dwgdpb.setRevno(rs.getString("revno"));
                     dwgdpb.setStscd(rs.getString("stscd"));
                     dwgdpb.setPrjno(rs.getString("prjno"));
                     dwgdpb.setDwggr(rs.getString("dwggr"));
                     dwgdpb.setBlkno(rs.getString("blkno"));
                     dwgdpb.setDwgmp(rs.getString("dwgmp"));
                     dwgdpb.setDwgsr(rs.getString("dwgsr"));
                     dwgdpb.setDwgds(rs.getString("dwgds"));
                     dwgdpb.setMkdem(rs.getString("mkdem"));
                     dwgdpb.setDptno(rs.getString("dptno"));
                     dwgdpb.setDwipd(rs.getInt("dwipd"));
                     dwgdpb.setDspdt(rs.getInt("dspdt"));
                     dwgdpb.setAdtpd(rs.getInt("adtpd"));
                     dwgdpb.setMkdps(rs.getInt("mkdps"));
                     dwgdpb.setMkdpf(rs.getInt("mkdpf"));
                     dwgdpb.setPorpd(rs.getInt("porpd"));
                     dwgdpb.setDwipt(rs.getString("dwipt"));
                     dwgdpb.setBaspd(rs.getInt("baspd"));
                     dwgdpb.setDwimg(rs.getInt("dwimg"));
                     dwgdpb.setIncbk(rs.getString("incbk"));
                     dwgdpb.setIprbk(rs.getString("iprbk"));
                     dwgdpb.setMkdpt(rs.getString("mkdpt"));
                     dwgdpb.setAdtpt(rs.getString("adtpt"));
                     dwgdpb.setPorpt(rs.getString("porpt"));
                     dwgdpb.setDwind(rs.getInt("dwind"));
                     dwgdpb.setDspnd(rs.getInt("dspnd"));
                     dwgdpb.setAdtnd(rs.getInt("adtnd"));
                     dwgdpb.setMksnd(rs.getInt("mksnd"));
                     dwgdpb.setMkfnd(rs.getInt("mkfnd"));
                     dwgdpb.setPornd(rs.getInt("pornd"));
                     dwgdpb.setRemrk(rs.getString("remrk"));
                     dwgdpb.setAdate(rs.getInt("adate"));
                     dwgdpb.setAuser(rs.getString("auser"));
                     dwgdpb.setMdate(rs.getInt("mdate"));
                     dwgdpb.setMuser(rs.getString("muser"));
                     dwgdpb.setApfpt(rs.getString("apfpt"));
                     dwgdpb.setApfnd(rs.getInt("apfnd"));
                     dwgdpb.setApfpf(rs.getInt("apfpf"));
                     dwgdpb.setApopf(rs.getInt("apopf"));
                     dwgdpb.setApond(rs.getInt("apond"));
                     dwgdpb.setApcpf(rs.getInt("apcpf"));
                     dwgdpb.setApcnd(rs.getInt("apcnd"));
                     dwgdpb.setDwipo(rs.getString("dwipo"));
                     dwgdpb.setDwimo(rs.getInt("dwimo"));
                     dwgdpb.setBasod(rs.getInt("basod"));
                     dwgdpb.setPrsst(rs.getString("prsst"));
                     dwgdpb.setKeycd(rs.getString("keycd"));
        } else {
            throw new DataNotFoundException();
        } // end if
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return dwgdpb;
} // end select

/**
* Get All Record 
* @param  void 
* @return java.util.Vector 
* @author besTeam 
* @date 2014-5-13
*/
public java.util.Vector selectAll() throws Exception{
    java.util.Vector dwgdpbV = new java.util.Vector();
    DWGDPBRec dwgdpb = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, dwgno, chgdt, chgds, revno, stscd, prjno, dwggr, blkno, " +
                              "dwgmp, dwgsr, dwgds, mkdem, dptno, dwipd, dspdt, adtpd, mkdps, " +
                              "mkdpf, porpd, dwipt, baspd, dwimg, incbk, iprbk, mkdpt, adtpt, " +
                              "porpt, dwind, dspnd, adtnd, mksnd, mkfnd, pornd, remrk, adate, " +
                              "auser, mdate, muser, apfpt, apfnd, apfpf, apopf, apond, apcpf, " +
                              "apcnd, dwipo, dwimo, basod, prsst, keycd " +
                       "  from DWGDPB ";
        pstmt = connection.prepareStatement(query);
        rs = pstmt.executeQuery();

        while(rs.next()){
            dwgdpb = new DWGDPBRec(); // DWGDPBRec Constructor
                     dwgdpb.setFacid(rs.getString("facid"));
                     dwgdpb.setDwgno(rs.getString("dwgno"));
                     dwgdpb.setChgdt(rs.getString("chgdt"));
                     dwgdpb.setChgds(rs.getString("chgds"));
                     dwgdpb.setRevno(rs.getString("revno"));
                     dwgdpb.setStscd(rs.getString("stscd"));
                     dwgdpb.setPrjno(rs.getString("prjno"));
                     dwgdpb.setDwggr(rs.getString("dwggr"));
                     dwgdpb.setBlkno(rs.getString("blkno"));
                     dwgdpb.setDwgmp(rs.getString("dwgmp"));
                     dwgdpb.setDwgsr(rs.getString("dwgsr"));
                     dwgdpb.setDwgds(rs.getString("dwgds"));
                     dwgdpb.setMkdem(rs.getString("mkdem"));
                     dwgdpb.setDptno(rs.getString("dptno"));
                     dwgdpb.setDwipd(rs.getInt("dwipd"));
                     dwgdpb.setDspdt(rs.getInt("dspdt"));
                     dwgdpb.setAdtpd(rs.getInt("adtpd"));
                     dwgdpb.setMkdps(rs.getInt("mkdps"));
                     dwgdpb.setMkdpf(rs.getInt("mkdpf"));
                     dwgdpb.setPorpd(rs.getInt("porpd"));
                     dwgdpb.setDwipt(rs.getString("dwipt"));
                     dwgdpb.setBaspd(rs.getInt("baspd"));
                     dwgdpb.setDwimg(rs.getInt("dwimg"));
                     dwgdpb.setIncbk(rs.getString("incbk"));
                     dwgdpb.setIprbk(rs.getString("iprbk"));
                     dwgdpb.setMkdpt(rs.getString("mkdpt"));
                     dwgdpb.setAdtpt(rs.getString("adtpt"));
                     dwgdpb.setPorpt(rs.getString("porpt"));
                     dwgdpb.setDwind(rs.getInt("dwind"));
                     dwgdpb.setDspnd(rs.getInt("dspnd"));
                     dwgdpb.setAdtnd(rs.getInt("adtnd"));
                     dwgdpb.setMksnd(rs.getInt("mksnd"));
                     dwgdpb.setMkfnd(rs.getInt("mkfnd"));
                     dwgdpb.setPornd(rs.getInt("pornd"));
                     dwgdpb.setRemrk(rs.getString("remrk"));
                     dwgdpb.setAdate(rs.getInt("adate"));
                     dwgdpb.setAuser(rs.getString("auser"));
                     dwgdpb.setMdate(rs.getInt("mdate"));
                     dwgdpb.setMuser(rs.getString("muser"));
                     dwgdpb.setApfpt(rs.getString("apfpt"));
                     dwgdpb.setApfnd(rs.getInt("apfnd"));
                     dwgdpb.setApfpf(rs.getInt("apfpf"));
                     dwgdpb.setApopf(rs.getInt("apopf"));
                     dwgdpb.setApond(rs.getInt("apond"));
                     dwgdpb.setApcpf(rs.getInt("apcpf"));
                     dwgdpb.setApcnd(rs.getInt("apcnd"));
                     dwgdpb.setDwipo(rs.getString("dwipo"));
                     dwgdpb.setDwimo(rs.getInt("dwimo"));
                     dwgdpb.setBasod(rs.getInt("basod"));
                     dwgdpb.setPrsst(rs.getString("prsst"));
                     dwgdpb.setKeycd(rs.getString("keycd"));
            dwgdpbV.addElement(dwgdpb);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return dwgdpbV;
} // end selectAll

/**
* Get All Record(condition : last Key except) 
* @param String facid, String dwgno
* @return java.util.Vector 
* @author besTeam 
* @date 2014-5-13
*/
public java.util.Vector selectAll(String facid, String dwgno) throws Exception{
    java.util.Vector dwgdpbV = new java.util.Vector();
    DWGDPBRec dwgdpb = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, dwgno, chgdt, chgds, revno, stscd, prjno, dwggr, blkno, " +
                              "dwgmp, dwgsr, dwgds, mkdem, dptno, dwipd, dspdt, adtpd, mkdps, " +
                              "mkdpf, porpd, dwipt, baspd, dwimg, incbk, iprbk, mkdpt, adtpt, " +
                              "porpt, dwind, dspnd, adtnd, mksnd, mkfnd, pornd, remrk, adate, " +
                              "auser, mdate, muser, apfpt, apfnd, apfpf, apopf, apond, apcpf, " +
                              "apcnd, dwipo, dwimo, basod, prsst, keycd " +
                       "  from DWGDPB  " +
                       "  where facid = ? and dwgno = ?  " +
                       "  order by chgdt"; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,dwgno); 
        rs = pstmt.executeQuery();

        while(rs.next()){
            dwgdpb = new DWGDPBRec(); // DWGDPBRec Constructor
                     dwgdpb.setFacid(rs.getString("facid"));
                     dwgdpb.setDwgno(rs.getString("dwgno"));
                     dwgdpb.setChgdt(rs.getString("chgdt"));
                     dwgdpb.setChgds(rs.getString("chgds"));
                     dwgdpb.setRevno(rs.getString("revno"));
                     dwgdpb.setStscd(rs.getString("stscd"));
                     dwgdpb.setPrjno(rs.getString("prjno"));
                     dwgdpb.setDwggr(rs.getString("dwggr"));
                     dwgdpb.setBlkno(rs.getString("blkno"));
                     dwgdpb.setDwgmp(rs.getString("dwgmp"));
                     dwgdpb.setDwgsr(rs.getString("dwgsr"));
                     dwgdpb.setDwgds(rs.getString("dwgds"));
                     dwgdpb.setMkdem(rs.getString("mkdem"));
                     dwgdpb.setDptno(rs.getString("dptno"));
                     dwgdpb.setDwipd(rs.getInt("dwipd"));
                     dwgdpb.setDspdt(rs.getInt("dspdt"));
                     dwgdpb.setAdtpd(rs.getInt("adtpd"));
                     dwgdpb.setMkdps(rs.getInt("mkdps"));
                     dwgdpb.setMkdpf(rs.getInt("mkdpf"));
                     dwgdpb.setPorpd(rs.getInt("porpd"));
                     dwgdpb.setDwipt(rs.getString("dwipt"));
                     dwgdpb.setBaspd(rs.getInt("baspd"));
                     dwgdpb.setDwimg(rs.getInt("dwimg"));
                     dwgdpb.setIncbk(rs.getString("incbk"));
                     dwgdpb.setIprbk(rs.getString("iprbk"));
                     dwgdpb.setMkdpt(rs.getString("mkdpt"));
                     dwgdpb.setAdtpt(rs.getString("adtpt"));
                     dwgdpb.setPorpt(rs.getString("porpt"));
                     dwgdpb.setDwind(rs.getInt("dwind"));
                     dwgdpb.setDspnd(rs.getInt("dspnd"));
                     dwgdpb.setAdtnd(rs.getInt("adtnd"));
                     dwgdpb.setMksnd(rs.getInt("mksnd"));
                     dwgdpb.setMkfnd(rs.getInt("mkfnd"));
                     dwgdpb.setPornd(rs.getInt("pornd"));
                     dwgdpb.setRemrk(rs.getString("remrk"));
                     dwgdpb.setAdate(rs.getInt("adate"));
                     dwgdpb.setAuser(rs.getString("auser"));
                     dwgdpb.setMdate(rs.getInt("mdate"));
                     dwgdpb.setMuser(rs.getString("muser"));
                     dwgdpb.setApfpt(rs.getString("apfpt"));
                     dwgdpb.setApfnd(rs.getInt("apfnd"));
                     dwgdpb.setApfpf(rs.getInt("apfpf"));
                     dwgdpb.setApopf(rs.getInt("apopf"));
                     dwgdpb.setApond(rs.getInt("apond"));
                     dwgdpb.setApcpf(rs.getInt("apcpf"));
                     dwgdpb.setApcnd(rs.getInt("apcnd"));
                     dwgdpb.setDwipo(rs.getString("dwipo"));
                     dwgdpb.setDwimo(rs.getInt("dwimo"));
                     dwgdpb.setBasod(rs.getInt("basod"));
                     dwgdpb.setPrsst(rs.getString("prsst"));
                     dwgdpb.setKeycd(rs.getString("keycd"));
            dwgdpbV.addElement(dwgdpb);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return dwgdpbV;
} // end selectAll

/**
* Get between Record(condition : last Key from - to) 
* @param String facid, String dwgno, String f_chgdt, String t_chgdt
* @return java.util.Vector 
* @author besTeam 
* @date 2014-5-13
*/
public java.util.Vector selectBetween(String facid, String dwgno, String f_chgdt, String t_chgdt) throws Exception{
    return selectBetween(facid, dwgno, f_chgdt, t_chgdt, 0);
} // end selectBetween

/**
* Get between Record(condition : last Key from - to) 
* @param String facid, String dwgno, String f_chgdt, String t_chgdt, int lastKeyOrder(0 : ASC-Default, 1 : DESC)
* @return java.util.Vector 
* @author besTeam 
* @date 2014-5-13
*/
public java.util.Vector selectBetween(String facid, String dwgno, String f_chgdt, String t_chgdt, int lastKeyOrder) throws Exception{
    java.util.Vector dwgdpbV = new java.util.Vector();
    DWGDPBRec dwgdpb = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, dwgno, chgdt, chgds, revno, stscd, prjno, dwggr, blkno, " +
                              "dwgmp, dwgsr, dwgds, mkdem, dptno, dwipd, dspdt, adtpd, mkdps, " +
                              "mkdpf, porpd, dwipt, baspd, dwimg, incbk, iprbk, mkdpt, adtpt, " +
                              "porpt, dwind, dspnd, adtnd, mksnd, mkfnd, pornd, remrk, adate, " +
                              "auser, mdate, muser, apfpt, apfnd, apfpf, apopf, apond, apcpf, " +
                              "apcnd, dwipo, dwimo, basod, prsst, keycd " +
                       "  from DWGDPB  " +
                       "  where facid = ? and dwgno = ?  " +
                       "  and chgdt between ? and ?  ";
               if(lastKeyOrder == 1){
                   query += " order by DESC chgdt"; 
               } else {
                   query += " order by chgdt"; 
               } // end if(lastKeyOrder == 1)
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,dwgno); 
        pstmt.setString(3,f_chgdt); 
        pstmt.setString(4,t_chgdt); 
        rs = pstmt.executeQuery();

        while(rs.next()){
            dwgdpb = new DWGDPBRec(); // DWGDPBRec Constructor
                     dwgdpb.setFacid(rs.getString("facid"));
                     dwgdpb.setDwgno(rs.getString("dwgno"));
                     dwgdpb.setChgdt(rs.getString("chgdt"));
                     dwgdpb.setChgds(rs.getString("chgds"));
                     dwgdpb.setRevno(rs.getString("revno"));
                     dwgdpb.setStscd(rs.getString("stscd"));
                     dwgdpb.setPrjno(rs.getString("prjno"));
                     dwgdpb.setDwggr(rs.getString("dwggr"));
                     dwgdpb.setBlkno(rs.getString("blkno"));
                     dwgdpb.setDwgmp(rs.getString("dwgmp"));
                     dwgdpb.setDwgsr(rs.getString("dwgsr"));
                     dwgdpb.setDwgds(rs.getString("dwgds"));
                     dwgdpb.setMkdem(rs.getString("mkdem"));
                     dwgdpb.setDptno(rs.getString("dptno"));
                     dwgdpb.setDwipd(rs.getInt("dwipd"));
                     dwgdpb.setDspdt(rs.getInt("dspdt"));
                     dwgdpb.setAdtpd(rs.getInt("adtpd"));
                     dwgdpb.setMkdps(rs.getInt("mkdps"));
                     dwgdpb.setMkdpf(rs.getInt("mkdpf"));
                     dwgdpb.setPorpd(rs.getInt("porpd"));
                     dwgdpb.setDwipt(rs.getString("dwipt"));
                     dwgdpb.setBaspd(rs.getInt("baspd"));
                     dwgdpb.setDwimg(rs.getInt("dwimg"));
                     dwgdpb.setIncbk(rs.getString("incbk"));
                     dwgdpb.setIprbk(rs.getString("iprbk"));
                     dwgdpb.setMkdpt(rs.getString("mkdpt"));
                     dwgdpb.setAdtpt(rs.getString("adtpt"));
                     dwgdpb.setPorpt(rs.getString("porpt"));
                     dwgdpb.setDwind(rs.getInt("dwind"));
                     dwgdpb.setDspnd(rs.getInt("dspnd"));
                     dwgdpb.setAdtnd(rs.getInt("adtnd"));
                     dwgdpb.setMksnd(rs.getInt("mksnd"));
                     dwgdpb.setMkfnd(rs.getInt("mkfnd"));
                     dwgdpb.setPornd(rs.getInt("pornd"));
                     dwgdpb.setRemrk(rs.getString("remrk"));
                     dwgdpb.setAdate(rs.getInt("adate"));
                     dwgdpb.setAuser(rs.getString("auser"));
                     dwgdpb.setMdate(rs.getInt("mdate"));
                     dwgdpb.setMuser(rs.getString("muser"));
                     dwgdpb.setApfpt(rs.getString("apfpt"));
                     dwgdpb.setApfnd(rs.getInt("apfnd"));
                     dwgdpb.setApfpf(rs.getInt("apfpf"));
                     dwgdpb.setApopf(rs.getInt("apopf"));
                     dwgdpb.setApond(rs.getInt("apond"));
                     dwgdpb.setApcpf(rs.getInt("apcpf"));
                     dwgdpb.setApcnd(rs.getInt("apcnd"));
                     dwgdpb.setDwipo(rs.getString("dwipo"));
                     dwgdpb.setDwimo(rs.getInt("dwimo"));
                     dwgdpb.setBasod(rs.getInt("basod"));
                     dwgdpb.setPrsst(rs.getString("prsst"));
                     dwgdpb.setKeycd(rs.getString("keycd"));
            dwgdpbV.addElement(dwgdpb);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return dwgdpbV;
} // end selectBetween

/**
* Select Data Over the key value(s) and default return count(20) 
* @param String facid, String dwgno, String chgdt
* @return java.util.Vector 
* @author besTeam 
* @date 2014-5-13
*/
public java.util.Vector selectOver(String facid, String dwgno, String chgdt) throws Exception{
return selectOver(facid, dwgno, chgdt,20) ;
}// end selectOver
/**
* Select Data Over(Next) the key value(s) and return record count 
* @param String facid, String dwgno, String chgdt, int 
* @return java.util.Vector 
* @author besTeam 
* @date 2014-5-13
*/
public java.util.Vector selectOver(String facid, String dwgno, String chgdt, int page) throws Exception{
    java.util.Vector dwgdpbV = new java.util.Vector();
    DWGDPBRec dwgdpb = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, dwgno, chgdt, chgds, revno, stscd, prjno, dwggr, blkno, " +
                              "dwgmp, dwgsr, dwgds, mkdem, dptno, dwipd, dspdt, adtpd, mkdps, " +
                              "mkdpf, porpd, dwipt, baspd, dwimg, incbk, iprbk, mkdpt, adtpt, " +
                              "porpt, dwind, dspnd, adtnd, mksnd, mkfnd, pornd, remrk, adate, " +
                              "auser, mdate, muser, apfpt, apfnd, apfpf, apopf, apond, apcpf, " +
                              "apcnd, dwipo, dwimo, basod, prsst, keycd " +
                       "  from DWGDPB  " +
                       "  where facid = ?  and  dwgno = ?  and  chgdt >= ? order by chgdt "; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,dwgno); 
        pstmt.setString(3,chgdt); 
        rs = pstmt.executeQuery();
        int count = 0;//실제라면 SQL문장에서 Limit해야 되지만...
        while(rs.next()){
            count ++;
            if(count > page ) break;
            dwgdpb = new DWGDPBRec(); // DWGDPBRec Constructor
                     dwgdpb.setFacid(rs.getString("facid"));
                     dwgdpb.setDwgno(rs.getString("dwgno"));
                     dwgdpb.setChgdt(rs.getString("chgdt"));
                     dwgdpb.setChgds(rs.getString("chgds"));
                     dwgdpb.setRevno(rs.getString("revno"));
                     dwgdpb.setStscd(rs.getString("stscd"));
                     dwgdpb.setPrjno(rs.getString("prjno"));
                     dwgdpb.setDwggr(rs.getString("dwggr"));
                     dwgdpb.setBlkno(rs.getString("blkno"));
                     dwgdpb.setDwgmp(rs.getString("dwgmp"));
                     dwgdpb.setDwgsr(rs.getString("dwgsr"));
                     dwgdpb.setDwgds(rs.getString("dwgds"));
                     dwgdpb.setMkdem(rs.getString("mkdem"));
                     dwgdpb.setDptno(rs.getString("dptno"));
                     dwgdpb.setDwipd(rs.getInt("dwipd"));
                     dwgdpb.setDspdt(rs.getInt("dspdt"));
                     dwgdpb.setAdtpd(rs.getInt("adtpd"));
                     dwgdpb.setMkdps(rs.getInt("mkdps"));
                     dwgdpb.setMkdpf(rs.getInt("mkdpf"));
                     dwgdpb.setPorpd(rs.getInt("porpd"));
                     dwgdpb.setDwipt(rs.getString("dwipt"));
                     dwgdpb.setBaspd(rs.getInt("baspd"));
                     dwgdpb.setDwimg(rs.getInt("dwimg"));
                     dwgdpb.setIncbk(rs.getString("incbk"));
                     dwgdpb.setIprbk(rs.getString("iprbk"));
                     dwgdpb.setMkdpt(rs.getString("mkdpt"));
                     dwgdpb.setAdtpt(rs.getString("adtpt"));
                     dwgdpb.setPorpt(rs.getString("porpt"));
                     dwgdpb.setDwind(rs.getInt("dwind"));
                     dwgdpb.setDspnd(rs.getInt("dspnd"));
                     dwgdpb.setAdtnd(rs.getInt("adtnd"));
                     dwgdpb.setMksnd(rs.getInt("mksnd"));
                     dwgdpb.setMkfnd(rs.getInt("mkfnd"));
                     dwgdpb.setPornd(rs.getInt("pornd"));
                     dwgdpb.setRemrk(rs.getString("remrk"));
                     dwgdpb.setAdate(rs.getInt("adate"));
                     dwgdpb.setAuser(rs.getString("auser"));
                     dwgdpb.setMdate(rs.getInt("mdate"));
                     dwgdpb.setMuser(rs.getString("muser"));
                     dwgdpb.setApfpt(rs.getString("apfpt"));
                     dwgdpb.setApfnd(rs.getInt("apfnd"));
                     dwgdpb.setApfpf(rs.getInt("apfpf"));
                     dwgdpb.setApopf(rs.getInt("apopf"));
                     dwgdpb.setApond(rs.getInt("apond"));
                     dwgdpb.setApcpf(rs.getInt("apcpf"));
                     dwgdpb.setApcnd(rs.getInt("apcnd"));
                     dwgdpb.setDwipo(rs.getString("dwipo"));
                     dwgdpb.setDwimo(rs.getInt("dwimo"));
                     dwgdpb.setBasod(rs.getInt("basod"));
                     dwgdpb.setPrsst(rs.getString("prsst"));
                     dwgdpb.setKeycd(rs.getString("keycd"));
            dwgdpbV.addElement(dwgdpb);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return dwgdpbV;
} // end selectOver

/**
* Select Data Under(Previous) the key value(s) and default return count(20) 
* @param String facid, String dwgno, String chgdt
* @return java.util.Vector 
* @author besTeam 
* @date 2014-5-13
*/
public java.util.Vector selectUnder(String facid, String dwgno, String chgdt) throws Exception{
return selectUnder(facid, dwgno, chgdt,20) ;
}// end selectUnder
/**
* Select Data Under(Previous) the key value(s) and return record count 
* @param String facid, String dwgno, String chgdt, int
* @return java.util.Vector
* @author besTeam 
* @date 2014-5-13
*/
public java.util.Vector selectUnder(String facid, String dwgno, String chgdt, int page) throws Exception{
    java.util.Vector dwgdpbV = new java.util.Vector();
    DWGDPBRec dwgdpb = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "Select facid, dwgno, chgdt, chgds, revno, stscd, prjno, dwggr, blkno, " +
                              "dwgmp, dwgsr, dwgds, mkdem, dptno, dwipd, dspdt, adtpd, mkdps, " +
                              "mkdpf, porpd, dwipt, baspd, dwimg, incbk, iprbk, mkdpt, adtpt, " +
                              "porpt, dwind, dspnd, adtnd, mksnd, mkfnd, pornd, remrk, adate, " +
                              "auser, mdate, muser, apfpt, apfnd, apfpf, apopf, apond, apcpf, " +
                              "apcnd, dwipo, dwimo, basod, prsst, keycd " +
                       "  from DWGDPB  " +
                       "  where facid = ?  and dwgno = ?  and chgdt <= ? order by chgdt desc" ; 
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,dwgno); 
        pstmt.setString(3,chgdt); 
        rs = pstmt.executeQuery();
        int count = 0;//실제라면 SQL문장에서 Limit해야 되지만...
        while(rs.next()){
            count ++;
            if(count > page ) break;
            dwgdpb = new DWGDPBRec(); // DWGDPBRec Constructor
                     dwgdpb.setFacid(rs.getString("facid"));
                     dwgdpb.setDwgno(rs.getString("dwgno"));
                     dwgdpb.setChgdt(rs.getString("chgdt"));
                     dwgdpb.setChgds(rs.getString("chgds"));
                     dwgdpb.setRevno(rs.getString("revno"));
                     dwgdpb.setStscd(rs.getString("stscd"));
                     dwgdpb.setPrjno(rs.getString("prjno"));
                     dwgdpb.setDwggr(rs.getString("dwggr"));
                     dwgdpb.setBlkno(rs.getString("blkno"));
                     dwgdpb.setDwgmp(rs.getString("dwgmp"));
                     dwgdpb.setDwgsr(rs.getString("dwgsr"));
                     dwgdpb.setDwgds(rs.getString("dwgds"));
                     dwgdpb.setMkdem(rs.getString("mkdem"));
                     dwgdpb.setDptno(rs.getString("dptno"));
                     dwgdpb.setDwipd(rs.getInt("dwipd"));
                     dwgdpb.setDspdt(rs.getInt("dspdt"));
                     dwgdpb.setAdtpd(rs.getInt("adtpd"));
                     dwgdpb.setMkdps(rs.getInt("mkdps"));
                     dwgdpb.setMkdpf(rs.getInt("mkdpf"));
                     dwgdpb.setPorpd(rs.getInt("porpd"));
                     dwgdpb.setDwipt(rs.getString("dwipt"));
                     dwgdpb.setBaspd(rs.getInt("baspd"));
                     dwgdpb.setDwimg(rs.getInt("dwimg"));
                     dwgdpb.setIncbk(rs.getString("incbk"));
                     dwgdpb.setIprbk(rs.getString("iprbk"));
                     dwgdpb.setMkdpt(rs.getString("mkdpt"));
                     dwgdpb.setAdtpt(rs.getString("adtpt"));
                     dwgdpb.setPorpt(rs.getString("porpt"));
                     dwgdpb.setDwind(rs.getInt("dwind"));
                     dwgdpb.setDspnd(rs.getInt("dspnd"));
                     dwgdpb.setAdtnd(rs.getInt("adtnd"));
                     dwgdpb.setMksnd(rs.getInt("mksnd"));
                     dwgdpb.setMkfnd(rs.getInt("mkfnd"));
                     dwgdpb.setPornd(rs.getInt("pornd"));
                     dwgdpb.setRemrk(rs.getString("remrk"));
                     dwgdpb.setAdate(rs.getInt("adate"));
                     dwgdpb.setAuser(rs.getString("auser"));
                     dwgdpb.setMdate(rs.getInt("mdate"));
                     dwgdpb.setMuser(rs.getString("muser"));
                     dwgdpb.setApfpt(rs.getString("apfpt"));
                     dwgdpb.setApfnd(rs.getInt("apfnd"));
                     dwgdpb.setApfpf(rs.getInt("apfpf"));
                     dwgdpb.setApopf(rs.getInt("apopf"));
                     dwgdpb.setApond(rs.getInt("apond"));
                     dwgdpb.setApcpf(rs.getInt("apcpf"));
                     dwgdpb.setApcnd(rs.getInt("apcnd"));
                     dwgdpb.setDwipo(rs.getString("dwipo"));
                     dwgdpb.setDwimo(rs.getInt("dwimo"));
                     dwgdpb.setBasod(rs.getInt("basod"));
                     dwgdpb.setPrsst(rs.getString("prsst"));
                     dwgdpb.setKeycd(rs.getString("keycd"));
            dwgdpbV.add(0,dwgdpb);
        } // end While
    } finally {
        try{rs.close();}catch(Exception e){}
        try{pstmt.close();}catch(Exception e){}
    } // try-finally
    return dwgdpbV;
} // end selectUnder

// Insert Data 
/**
* Add Record 
* @param DWGDPBRec 
* @return void 
* @author besTeam 
* @date 2014-5-13
*/
public void insert(DWGDPBRec dwgdpb) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Insert into DWGDPB( " +
                              "facid, dwgno, chgdt, chgds, revno, stscd, prjno, dwggr, blkno, " +
                              "dwgmp, dwgsr, dwgds, mkdem, dptno, dwipd, dspdt, adtpd, mkdps, " +
                              "mkdpf, porpd, dwipt, baspd, dwimg, incbk, iprbk, mkdpt, adtpt, " +
                              "porpt, dwind, dspnd, adtnd, mksnd, mkfnd, pornd, remrk, adate, " +
                              "auser, mdate, muser, apfpt, apfnd, apfpf, apopf, apond, apcpf, " +
                              "apcnd, dwipo, dwimo, basod, prsst, keycd"+
                       " ) values ( "+
                              "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                              "?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                              "?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                              "?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                              "?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                              "?, ?, ?, ?, ?)";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, dwgdpb.getFacid());
        pstmt.setString(2, dwgdpb.getDwgno());
        pstmt.setString(3, dwgdpb.getChgdt());
        pstmt.setString(4, dwgdpb.getChgds());
        pstmt.setString(5, dwgdpb.getRevno());
        pstmt.setString(6, dwgdpb.getStscd());
        pstmt.setString(7, dwgdpb.getPrjno());
        pstmt.setString(8, dwgdpb.getDwggr());
        pstmt.setString(9, dwgdpb.getBlkno());
        pstmt.setString(10, dwgdpb.getDwgmp());
        pstmt.setString(11, dwgdpb.getDwgsr());
        pstmt.setString(12, dwgdpb.getDwgds());
        pstmt.setString(13, dwgdpb.getMkdem());
        pstmt.setString(14, dwgdpb.getDptno());
        pstmt.setInt(15, dwgdpb.getDwipd());
        pstmt.setInt(16, dwgdpb.getDspdt());
        pstmt.setInt(17, dwgdpb.getAdtpd());
        pstmt.setInt(18, dwgdpb.getMkdps());
        pstmt.setInt(19, dwgdpb.getMkdpf());
        pstmt.setInt(20, dwgdpb.getPorpd());
        pstmt.setString(21, dwgdpb.getDwipt());
        pstmt.setInt(22, dwgdpb.getBaspd());
        pstmt.setInt(23, dwgdpb.getDwimg());
        pstmt.setString(24, dwgdpb.getIncbk());
        pstmt.setString(25, dwgdpb.getIprbk());
        pstmt.setString(26, dwgdpb.getMkdpt());
        pstmt.setString(27, dwgdpb.getAdtpt());
        pstmt.setString(28, dwgdpb.getPorpt());
        pstmt.setInt(29, dwgdpb.getDwind());
        pstmt.setInt(30, dwgdpb.getDspnd());
        pstmt.setInt(31, dwgdpb.getAdtnd());
        pstmt.setInt(32, dwgdpb.getMksnd());
        pstmt.setInt(33, dwgdpb.getMkfnd());
        pstmt.setInt(34, dwgdpb.getPornd());
        pstmt.setString(35, dwgdpb.getRemrk());
        pstmt.setInt(36, dwgdpb.getAdate());
        pstmt.setString(37, dwgdpb.getAuser());
        pstmt.setInt(38, dwgdpb.getMdate());
        pstmt.setString(39, dwgdpb.getMuser());
        pstmt.setString(40, dwgdpb.getApfpt());
        pstmt.setInt(41, dwgdpb.getApfnd());
        pstmt.setInt(42, dwgdpb.getApfpf());
        pstmt.setInt(43, dwgdpb.getApopf());
        pstmt.setInt(44, dwgdpb.getApond());
        pstmt.setInt(45, dwgdpb.getApcpf());
        pstmt.setInt(46, dwgdpb.getApcnd());
        pstmt.setString(47, dwgdpb.getDwipo());
        pstmt.setInt(48, dwgdpb.getDwimo());
        pstmt.setInt(49, dwgdpb.getBasod());
        pstmt.setString(50, dwgdpb.getPrsst());
        pstmt.setString(51, dwgdpb.getKeycd());
        int affected = pstmt.executeUpdate();
        if ( affected == 0 ) throw new DataAlreadyExistException();

    } finally {
        try{pstmt.close();}catch(Exception e){}
    } // try-finally

} // end insert


// Update Data 
/**
* Update Record 
* @param DWGDPBRec 
* @return void 
* @author besTeam 
* @date 2014-5-13
*/
public void update(DWGDPBRec dwgdpb) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Update DWGDPB SET "+
                        "facid = ?, dwgno = ?, chgdt = ?, chgds = ?, revno = ?, stscd = ?, prjno = ?, dwggr = ?, blkno = ?, dwgmp = ?, " +
                              "dwgsr = ?, dwgds = ?, mkdem = ?, dptno = ?, dwipd = ?, dspdt = ?, adtpd = ?, mkdps = ?, mkdpf = ?, " +
                              "porpd = ?, dwipt = ?, baspd = ?, dwimg = ?, incbk = ?, iprbk = ?, mkdpt = ?, adtpt = ?, porpt = ?, " +
                              "dwind = ?, dspnd = ?, adtnd = ?, mksnd = ?, mkfnd = ?, pornd = ?, remrk = ?, adate = ?, auser = ?, " +
                              "mdate = ?, muser = ?, apfpt = ?, apfnd = ?, apfpf = ?, apopf = ?, apond = ?, apcpf = ?, apcnd = ?, " +
                              "dwipo = ?, dwimo = ?, basod = ?, prsst = ?, keycd = ?"+
                        " where facid = ? and dwgno = ? and chgdt = ? ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, dwgdpb.getFacid());
        pstmt.setString(2, dwgdpb.getDwgno());
        pstmt.setString(3, dwgdpb.getChgdt());
        pstmt.setString(4, dwgdpb.getChgds());
        pstmt.setString(5, dwgdpb.getRevno());
        pstmt.setString(6, dwgdpb.getStscd());
        pstmt.setString(7, dwgdpb.getPrjno());
        pstmt.setString(8, dwgdpb.getDwggr());
        pstmt.setString(9, dwgdpb.getBlkno());
        pstmt.setString(10, dwgdpb.getDwgmp());
        pstmt.setString(11, dwgdpb.getDwgsr());
        pstmt.setString(12, dwgdpb.getDwgds());
        pstmt.setString(13, dwgdpb.getMkdem());
        pstmt.setString(14, dwgdpb.getDptno());
        pstmt.setInt(15, dwgdpb.getDwipd());
        pstmt.setInt(16, dwgdpb.getDspdt());
        pstmt.setInt(17, dwgdpb.getAdtpd());
        pstmt.setInt(18, dwgdpb.getMkdps());
        pstmt.setInt(19, dwgdpb.getMkdpf());
        pstmt.setInt(20, dwgdpb.getPorpd());
        pstmt.setString(21, dwgdpb.getDwipt());
        pstmt.setInt(22, dwgdpb.getBaspd());
        pstmt.setInt(23, dwgdpb.getDwimg());
        pstmt.setString(24, dwgdpb.getIncbk());
        pstmt.setString(25, dwgdpb.getIprbk());
        pstmt.setString(26, dwgdpb.getMkdpt());
        pstmt.setString(27, dwgdpb.getAdtpt());
        pstmt.setString(28, dwgdpb.getPorpt());
        pstmt.setInt(29, dwgdpb.getDwind());
        pstmt.setInt(30, dwgdpb.getDspnd());
        pstmt.setInt(31, dwgdpb.getAdtnd());
        pstmt.setInt(32, dwgdpb.getMksnd());
        pstmt.setInt(33, dwgdpb.getMkfnd());
        pstmt.setInt(34, dwgdpb.getPornd());
        pstmt.setString(35, dwgdpb.getRemrk());
        pstmt.setInt(36, dwgdpb.getAdate());
        pstmt.setString(37, dwgdpb.getAuser());
        pstmt.setInt(38, dwgdpb.getMdate());
        pstmt.setString(39, dwgdpb.getMuser());
        pstmt.setString(40, dwgdpb.getApfpt());
        pstmt.setInt(41, dwgdpb.getApfnd());
        pstmt.setInt(42, dwgdpb.getApfpf());
        pstmt.setInt(43, dwgdpb.getApopf());
        pstmt.setInt(44, dwgdpb.getApond());
        pstmt.setInt(45, dwgdpb.getApcpf());
        pstmt.setInt(46, dwgdpb.getApcnd());
        pstmt.setString(47, dwgdpb.getDwipo());
        pstmt.setInt(48, dwgdpb.getDwimo());
        pstmt.setInt(49, dwgdpb.getBasod());
        pstmt.setString(50, dwgdpb.getPrsst());
        pstmt.setString(51, dwgdpb.getKeycd());
        // Key
        pstmt.setString(52, dwgdpb.getFacid()); 
        pstmt.setString(53, dwgdpb.getDwgno()); 
        pstmt.setString(54, dwgdpb.getChgdt()); 
        int affected = pstmt.executeUpdate();
        if ( affected == 0 ) throw new NoAffectedException();
        else if ( affected > 1 ) throw new TooManyAffectedException();
    } finally {
        try{pstmt.close();}catch(Exception e){}
    } // try-finally

} // end Update

/**
* Delete Record 
* @param String facid, String dwgno, String chgdt
* @return void 
* @author besTeam 
* @date 2014-5-13
*/
public void delete(String facid, String dwgno, String chgdt) throws Exception{
    PreparedStatement pstmt = null;
    try{
        String query = "Delete From DWGDPB "+
                       "where facid = ? and dwgno = ? and chgdt = ? ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,dwgno); 
        pstmt.setString(3,chgdt); 
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
* @param DWGDPBRec 
* @return void 
* @author besTeam 
* @date 2014-5-13
*/
public void delete(DWGDPBRec dwgdpb) throws Exception{
     delete(dwgdpb.getFacid(), dwgdpb.getDwgno(), dwgdpb.getChgdt());
} // end Delete

/**
* Get Rows Count 
* @param String facid, String dwgno, String chgdt
* @return int 
* @author besTeam 
* @date 2014-5-13
*/
public int count(String facid, String dwgno, String chgdt) throws Exception{
    int count = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "SELECT COUNT(*) from DWGDPB " +
                       " where facid = ? and dwgno = ? and chgdt = ?   ";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,facid); 
        pstmt.setString(2,dwgno); 
        pstmt.setString(3,chgdt); 
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
* @date 2014-5-13
*/
public int count() throws Exception{
    int count = 0;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try{
        String query = "SELECT COUNT(*) from DWGDPB  ";
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

}// end DWGDPBDBWrap class