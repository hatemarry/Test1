package com.bes_line.mst;

// Entity Class for DWGDPB
/**
 *
 * @(#) DWGDPBRec.java
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

import org.jsn.jdf.db.*;

public class DWGDPBRec extends EntityData {
    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) 
    public String facid; 		// (VARCHAR2, 2.0)
    public String dwgno; 		// (VARCHAR2, 20.0)
    public String chgdt; 		// (DATE, 0.0)
    public String chgds; 		// (VARCHAR2, 200.0)
    public String revno; 		// (VARCHAR2, 3.0)
    public String stscd; 		// (VARCHAR2, 3.0)
    public String prjno; 		// (VARCHAR2, 5.0)
    public String dwggr; 		// (VARCHAR2, 3.0)
    public String blkno; 		// (VARCHAR2, 10.0)
    public String dwgmp; 		// (VARCHAR2, 3.0)
    public String dwgsr; 		// (VARCHAR2, 5.0)
    public String dwgds; 		// (VARCHAR2, 200.0)
    public String mkdem; 		// (VARCHAR2, 10.0)
    public String dptno; 		// (VARCHAR2, 10.0)
    public int dwipd; 		// (NUMBER, 8.0)
    public int dspdt; 		// (NUMBER, 8.0)
    public int adtpd; 		// (NUMBER, 8.0)
    public int mkdps; 		// (NUMBER, 8.0)
    public int mkdpf; 		// (NUMBER, 8.0)
    public int porpd; 		// (NUMBER, 8.0)
    public String dwipt; 		// (VARCHAR2, 3.0)
    public int baspd; 		// (NUMBER, 8.0)
    public int dwimg; 		// (NUMBER, 6.0)
    public String incbk; 		// (VARCHAR2, 2000.0)
    public String iprbk; 		// (VARCHAR2, 2000.0)
    public String mkdpt; 		// (VARCHAR2, 3.0)
    public String adtpt; 		// (VARCHAR2, 3.0)
    public String porpt; 		// (VARCHAR2, 3.0)
    public int dwind; 		// (NUMBER, 8.0)
    public int dspnd; 		// (NUMBER, 8.0)
    public int adtnd; 		// (NUMBER, 8.0)
    public int mksnd; 		// (NUMBER, 8.0)
    public int mkfnd; 		// (NUMBER, 8.0)
    public int pornd; 		// (NUMBER, 8.0)
    public String remrk; 		// (VARCHAR2, 255.0)
    public int adate; 		// (NUMBER, 8.0)
    public String auser; 		// (VARCHAR2, 30.0)
    public int mdate; 		// (NUMBER, 8.0)
    public String muser; 		// (VARCHAR2, 30.0)
    public String apfpt; 		// (VARCHAR2, 3.0)
    public int apfnd; 		// (NUMBER, 8.0)
    public int apfpf; 		// (NUMBER, 8.0)
    public int apopf; 		// (NUMBER, 8.0)
    public int apond; 		// (NUMBER, 8.0)
    public int apcpf; 		// (NUMBER, 8.0)
    public int apcnd; 		// (NUMBER, 8.0)
    public String dwipo; 		// (VARCHAR2, 3.0)
    public int dwimo; 		// (NUMBER, 6.0)
    public int basod; 		// (NUMBER, 8.0)
    public String prsst; 		// (VARCHAR2, 3.0)
    public String keycd; 		// (VARCHAR2, 200.0)

public DWGDPBRec(){ } // default constructor

public DWGDPBRec(
       String facid, String dwgno, String chgdt, String chgds, String revno, String stscd, 
       String prjno, String dwggr, String blkno, String dwgmp, String dwgsr, String dwgds, 
       String mkdem, String dptno, int dwipd, int dspdt, int adtpd, int mkdps, 
       int mkdpf, int porpd, String dwipt, int baspd, int dwimg, String incbk, 
       String iprbk, String mkdpt, String adtpt, String porpt, int dwind, int dspnd, 
       int adtnd, int mksnd, int mkfnd, int pornd, String remrk, int adate, 
       String auser, int mdate, String muser, String apfpt, int apfnd, int apfpf, 
       int apopf, int apond, int apcpf, int apcnd, String dwipo, int dwimo, 
       int basod, String prsst, String keycd){
    this.facid = facid;
    this.dwgno = dwgno;
    this.chgdt = chgdt;
    this.chgds = chgds;
    this.revno = revno;
    this.stscd = stscd;
    this.prjno = prjno;
    this.dwggr = dwggr;
    this.blkno = blkno;
    this.dwgmp = dwgmp;
    this.dwgsr = dwgsr;
    this.dwgds = dwgds;
    this.mkdem = mkdem;
    this.dptno = dptno;
    this.dwipd = dwipd;
    this.dspdt = dspdt;
    this.adtpd = adtpd;
    this.mkdps = mkdps;
    this.mkdpf = mkdpf;
    this.porpd = porpd;
    this.dwipt = dwipt;
    this.baspd = baspd;
    this.dwimg = dwimg;
    this.incbk = incbk;
    this.iprbk = iprbk;
    this.mkdpt = mkdpt;
    this.adtpt = adtpt;
    this.porpt = porpt;
    this.dwind = dwind;
    this.dspnd = dspnd;
    this.adtnd = adtnd;
    this.mksnd = mksnd;
    this.mkfnd = mkfnd;
    this.pornd = pornd;
    this.remrk = remrk;
    this.adate = adate;
    this.auser = auser;
    this.mdate = mdate;
    this.muser = muser;
    this.apfpt = apfpt;
    this.apfnd = apfnd;
    this.apfpf = apfpf;
    this.apopf = apopf;
    this.apond = apond;
    this.apcpf = apcpf;
    this.apcnd = apcnd;
    this.dwipo = dwipo;
    this.dwimo = dwimo;
    this.basod = basod;
    this.prsst = prsst;
    this.keycd = keycd;
} // Constructor


// Getter 
public String getFacid(){ return facid;}
public String getDwgno(){ return dwgno;}
public String getChgdt(){ return chgdt;}
public String getChgds(){ return chgds;}
public String getRevno(){ return revno;}
public String getStscd(){ return stscd;}
public String getPrjno(){ return prjno;}
public String getDwggr(){ return dwggr;}
public String getBlkno(){ return blkno;}
public String getDwgmp(){ return dwgmp;}
public String getDwgsr(){ return dwgsr;}
public String getDwgds(){ return dwgds;}
public String getMkdem(){ return mkdem;}
public String getDptno(){ return dptno;}
public int getDwipd(){ return dwipd;}
public int getDspdt(){ return dspdt;}
public int getAdtpd(){ return adtpd;}
public int getMkdps(){ return mkdps;}
public int getMkdpf(){ return mkdpf;}
public int getPorpd(){ return porpd;}
public String getDwipt(){ return dwipt;}
public int getBaspd(){ return baspd;}
public int getDwimg(){ return dwimg;}
public String getIncbk(){ return incbk;}
public String getIprbk(){ return iprbk;}
public String getMkdpt(){ return mkdpt;}
public String getAdtpt(){ return adtpt;}
public String getPorpt(){ return porpt;}
public int getDwind(){ return dwind;}
public int getDspnd(){ return dspnd;}
public int getAdtnd(){ return adtnd;}
public int getMksnd(){ return mksnd;}
public int getMkfnd(){ return mkfnd;}
public int getPornd(){ return pornd;}
public String getRemrk(){ return remrk;}
public int getAdate(){ return adate;}
public String getAuser(){ return auser;}
public int getMdate(){ return mdate;}
public String getMuser(){ return muser;}
public String getApfpt(){ return apfpt;}
public int getApfnd(){ return apfnd;}
public int getApfpf(){ return apfpf;}
public int getApopf(){ return apopf;}
public int getApond(){ return apond;}
public int getApcpf(){ return apcpf;}
public int getApcnd(){ return apcnd;}
public String getDwipo(){ return dwipo;}
public int getDwimo(){ return dwimo;}
public int getBasod(){ return basod;}
public String getPrsst(){ return prsst;}
public String getKeycd(){ return keycd;}

// Setter 
public void setFacid(String facid){ this.facid = facid;}
public void setDwgno(String dwgno){ this.dwgno = dwgno;}
public void setChgdt(String chgdt){ this.chgdt = chgdt;}
public void setChgds(String chgds){ this.chgds = chgds;}
public void setRevno(String revno){ this.revno = revno;}
public void setStscd(String stscd){ this.stscd = stscd;}
public void setPrjno(String prjno){ this.prjno = prjno;}
public void setDwggr(String dwggr){ this.dwggr = dwggr;}
public void setBlkno(String blkno){ this.blkno = blkno;}
public void setDwgmp(String dwgmp){ this.dwgmp = dwgmp;}
public void setDwgsr(String dwgsr){ this.dwgsr = dwgsr;}
public void setDwgds(String dwgds){ this.dwgds = dwgds;}
public void setMkdem(String mkdem){ this.mkdem = mkdem;}
public void setDptno(String dptno){ this.dptno = dptno;}
public void setDwipd(int dwipd){ this.dwipd = dwipd;}
public void setDspdt(int dspdt){ this.dspdt = dspdt;}
public void setAdtpd(int adtpd){ this.adtpd = adtpd;}
public void setMkdps(int mkdps){ this.mkdps = mkdps;}
public void setMkdpf(int mkdpf){ this.mkdpf = mkdpf;}
public void setPorpd(int porpd){ this.porpd = porpd;}
public void setDwipt(String dwipt){ this.dwipt = dwipt;}
public void setBaspd(int baspd){ this.baspd = baspd;}
public void setDwimg(int dwimg){ this.dwimg = dwimg;}
public void setIncbk(String incbk){ this.incbk = incbk;}
public void setIprbk(String iprbk){ this.iprbk = iprbk;}
public void setMkdpt(String mkdpt){ this.mkdpt = mkdpt;}
public void setAdtpt(String adtpt){ this.adtpt = adtpt;}
public void setPorpt(String porpt){ this.porpt = porpt;}
public void setDwind(int dwind){ this.dwind = dwind;}
public void setDspnd(int dspnd){ this.dspnd = dspnd;}
public void setAdtnd(int adtnd){ this.adtnd = adtnd;}
public void setMksnd(int mksnd){ this.mksnd = mksnd;}
public void setMkfnd(int mkfnd){ this.mkfnd = mkfnd;}
public void setPornd(int pornd){ this.pornd = pornd;}
public void setRemrk(String remrk){ this.remrk = remrk;}
public void setAdate(int adate){ this.adate = adate;}
public void setAuser(String auser){ this.auser = auser;}
public void setMdate(int mdate){ this.mdate = mdate;}
public void setMuser(String muser){ this.muser = muser;}
public void setApfpt(String apfpt){ this.apfpt = apfpt;}
public void setApfnd(int apfnd){ this.apfnd = apfnd;}
public void setApfpf(int apfpf){ this.apfpf = apfpf;}
public void setApopf(int apopf){ this.apopf = apopf;}
public void setApond(int apond){ this.apond = apond;}
public void setApcpf(int apcpf){ this.apcpf = apcpf;}
public void setApcnd(int apcnd){ this.apcnd = apcnd;}
public void setDwipo(String dwipo){ this.dwipo = dwipo;}
public void setDwimo(int dwimo){ this.dwimo = dwimo;}
public void setBasod(int basod){ this.basod = basod;}
public void setPrsst(String prsst){ this.prsst = prsst;}
public void setKeycd(String keycd){ this.keycd = keycd;}

/**
* getString 
* @param int seq 
* @return field Value 
*/
public String getString(int seq){ 
 String field=null;
  switch (seq) {
  case  1 : field = facid + "" ; break;
  case  2 : field = dwgno + "" ; break;
  case  3 : field = chgdt + "" ; break;
  case  4 : field = chgds + "" ; break;
  case  5 : field = revno + "" ; break;
  case  6 : field = stscd + "" ; break;
  case  7 : field = prjno + "" ; break;
  case  8 : field = dwggr + "" ; break;
  case  9 : field = blkno + "" ; break;
  case  10 : field = dwgmp + "" ; break;
  case  11 : field = dwgsr + "" ; break;
  case  12 : field = dwgds + "" ; break;
  case  13 : field = mkdem + "" ; break;
  case  14 : field = dptno + "" ; break;
  case  15 : field = dwipd + "" ; break;
  case  16 : field = dspdt + "" ; break;
  case  17 : field = adtpd + "" ; break;
  case  18 : field = mkdps + "" ; break;
  case  19 : field = mkdpf + "" ; break;
  case  20 : field = porpd + "" ; break;
  case  21 : field = dwipt + "" ; break;
  case  22 : field = baspd + "" ; break;
  case  23 : field = dwimg + "" ; break;
  case  24 : field = incbk + "" ; break;
  case  25 : field = iprbk + "" ; break;
  case  26 : field = mkdpt + "" ; break;
  case  27 : field = adtpt + "" ; break;
  case  28 : field = porpt + "" ; break;
  case  29 : field = dwind + "" ; break;
  case  30 : field = dspnd + "" ; break;
  case  31 : field = adtnd + "" ; break;
  case  32 : field = mksnd + "" ; break;
  case  33 : field = mkfnd + "" ; break;
  case  34 : field = pornd + "" ; break;
  case  35 : field = remrk + "" ; break;
  case  36 : field = adate + "" ; break;
  case  37 : field = auser + "" ; break;
  case  38 : field = mdate + "" ; break;
  case  39 : field = muser + "" ; break;
  case  40 : field = apfpt + "" ; break;
  case  41 : field = apfnd + "" ; break;
  case  42 : field = apfpf + "" ; break;
  case  43 : field = apopf + "" ; break;
  case  44 : field = apond + "" ; break;
  case  45 : field = apcpf + "" ; break;
  case  46 : field = apcnd + "" ; break;
  case  47 : field = dwipo + "" ; break;
  case  48 : field = dwimo + "" ; break;
  case  49 : field = basod + "" ; break;
  case  50 : field = prsst + "" ; break;
  case  51 : field = keycd + "" ; break;
  } // end switch
  return field;
}// end getString (int seq)

/**
* getString 
* @param String fieldName 
* @return field Value 
*/
public String getString(String rec){ 
 String field=null;
     if       (rec.equalsIgnoreCase("facid")){ field = facid + "" ; 
     } else if(rec.equalsIgnoreCase("dwgno")){ field = dwgno + "" ; 
     } else if(rec.equalsIgnoreCase("chgdt")){ field = chgdt + "" ; 
     } else if(rec.equalsIgnoreCase("chgds")){ field = chgds + "" ; 
     } else if(rec.equalsIgnoreCase("revno")){ field = revno + "" ; 
     } else if(rec.equalsIgnoreCase("stscd")){ field = stscd + "" ; 
     } else if(rec.equalsIgnoreCase("prjno")){ field = prjno + "" ; 
     } else if(rec.equalsIgnoreCase("dwggr")){ field = dwggr + "" ; 
     } else if(rec.equalsIgnoreCase("blkno")){ field = blkno + "" ; 
     } else if(rec.equalsIgnoreCase("dwgmp")){ field = dwgmp + "" ; 
     } else if(rec.equalsIgnoreCase("dwgsr")){ field = dwgsr + "" ; 
     } else if(rec.equalsIgnoreCase("dwgds")){ field = dwgds + "" ; 
     } else if(rec.equalsIgnoreCase("mkdem")){ field = mkdem + "" ; 
     } else if(rec.equalsIgnoreCase("dptno")){ field = dptno + "" ; 
     } else if(rec.equalsIgnoreCase("dwipd")){ field = dwipd + "" ; 
     } else if(rec.equalsIgnoreCase("dspdt")){ field = dspdt + "" ; 
     } else if(rec.equalsIgnoreCase("adtpd")){ field = adtpd + "" ; 
     } else if(rec.equalsIgnoreCase("mkdps")){ field = mkdps + "" ; 
     } else if(rec.equalsIgnoreCase("mkdpf")){ field = mkdpf + "" ; 
     } else if(rec.equalsIgnoreCase("porpd")){ field = porpd + "" ; 
     } else if(rec.equalsIgnoreCase("dwipt")){ field = dwipt + "" ; 
     } else if(rec.equalsIgnoreCase("baspd")){ field = baspd + "" ; 
     } else if(rec.equalsIgnoreCase("dwimg")){ field = dwimg + "" ; 
     } else if(rec.equalsIgnoreCase("incbk")){ field = incbk + "" ; 
     } else if(rec.equalsIgnoreCase("iprbk")){ field = iprbk + "" ; 
     } else if(rec.equalsIgnoreCase("mkdpt")){ field = mkdpt + "" ; 
     } else if(rec.equalsIgnoreCase("adtpt")){ field = adtpt + "" ; 
     } else if(rec.equalsIgnoreCase("porpt")){ field = porpt + "" ; 
     } else if(rec.equalsIgnoreCase("dwind")){ field = dwind + "" ; 
     } else if(rec.equalsIgnoreCase("dspnd")){ field = dspnd + "" ; 
     } else if(rec.equalsIgnoreCase("adtnd")){ field = adtnd + "" ; 
     } else if(rec.equalsIgnoreCase("mksnd")){ field = mksnd + "" ; 
     } else if(rec.equalsIgnoreCase("mkfnd")){ field = mkfnd + "" ; 
     } else if(rec.equalsIgnoreCase("pornd")){ field = pornd + "" ; 
     } else if(rec.equalsIgnoreCase("remrk")){ field = remrk + "" ; 
     } else if(rec.equalsIgnoreCase("adate")){ field = adate + "" ; 
     } else if(rec.equalsIgnoreCase("auser")){ field = auser + "" ; 
     } else if(rec.equalsIgnoreCase("mdate")){ field = mdate + "" ; 
     } else if(rec.equalsIgnoreCase("muser")){ field = muser + "" ; 
     } else if(rec.equalsIgnoreCase("apfpt")){ field = apfpt + "" ; 
     } else if(rec.equalsIgnoreCase("apfnd")){ field = apfnd + "" ; 
     } else if(rec.equalsIgnoreCase("apfpf")){ field = apfpf + "" ; 
     } else if(rec.equalsIgnoreCase("apopf")){ field = apopf + "" ; 
     } else if(rec.equalsIgnoreCase("apond")){ field = apond + "" ; 
     } else if(rec.equalsIgnoreCase("apcpf")){ field = apcpf + "" ; 
     } else if(rec.equalsIgnoreCase("apcnd")){ field = apcnd + "" ; 
     } else if(rec.equalsIgnoreCase("dwipo")){ field = dwipo + "" ; 
     } else if(rec.equalsIgnoreCase("dwimo")){ field = dwimo + "" ; 
     } else if(rec.equalsIgnoreCase("basod")){ field = basod + "" ; 
     } else if(rec.equalsIgnoreCase("prsst")){ field = prsst + "" ; 
     } else if(rec.equalsIgnoreCase("keycd")){ field = keycd + "" ; 
}// end if
 return field;
}// end getString (String fieldName)

/**
* fieldNames 
* @param none 
* @return field Names[]
*/
public String[] fieldNames() {
    String [] tempx = {"", "FACID", "DWGNO", "CHGDT", "CHGDS", "REVNO", "STSCD", "PRJNO", 
       "DWGGR", "BLKNO", "DWGMP", "DWGSR", "DWGDS", "MKDEM", "DPTNO", 
       "DWIPD", "DSPDT", "ADTPD", "MKDPS", "MKDPF", "PORPD", "DWIPT", 
       "BASPD", "DWIMG", "INCBK", "IPRBK", "MKDPT", "ADTPT", "PORPT", 
       "DWIND", "DSPND", "ADTND", "MKSND", "MKFND", "PORND", "REMRK", 
       "ADATE", "AUSER", "MDATE", "MUSER", "APFPT", "APFND", "APFPF", 
       "APOPF", "APOND", "APCPF", "APCND", "DWIPO", "DWIMO", "BASOD", 
       "PRSST", "KEYCD"};
    return tempx;
}

/**
* Key fieldNames 
* @param none 
* @return Key field Names[]
*/
public String[] keyFieldNames() {
    String [] tempx = {"", "FACID", "DWGNO", "CHGDT"};
    return tempx;
}

}// end DWGDPBRec class