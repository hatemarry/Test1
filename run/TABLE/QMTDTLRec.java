package com.bes_line.mst;

// Entity Class for QMTDTL
/**
 *
 * @(#) QMTDTLRec.java
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

import org.jsn.jdf.db.*;

public class QMTDTLRec extends EntityData {
    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) 
    public String facid; 		// (VARCHAR2, 2.0)
    public String prjno; 		// (VARCHAR2, 10.0)
    public String ndtkd; 		// (VARCHAR2, 3.0)
    public String insit; 		// (VARCHAR2, 3.0)
    public String insgp; 		// (VARCHAR2, 3.0)
    public String instl; 		// (VARCHAR2, 50.0)
    public String inpkn; 		// (VARCHAR2, 3.0)
    public String ipcnt; 		// (VARCHAR2, 3.0)
    public String code1; 		// (VARCHAR2, 6.0)
    public String seqnm; 		// (VARCHAR2, 30.0)
    public String rptno; 		// (VARCHAR2, 20.0)
    public String inrcd; 		// (VARCHAR2, 3.0)
    public String ldval; 		// (VARCHAR2, 30.0)
    public String cdval; 		// (VARCHAR2, 30.0)
    public String dftps; 		// (VARCHAR2, 30.0)
    public double dftlt; 		// (NUMBER, 12.3)
    public double cfmlt; 		// (NUMBER, 12.3)
    public String wldno; 		// (VARCHAR2, 15.0)
    public String orglv; 		// (VARCHAR2, 3.0)
    public String remrk; 		// (VARCHAR2, 255.0)
    public int adate; 		// (NUMBER, 8.0)
    public int mdate; 		// (NUMBER, 8.0)
    public String auser; 		// (VARCHAR2, 30.0)
    public String muser; 		// (VARCHAR2, 30.0)
    public String trmid; 		// (VARCHAR2, 20.0)

public QMTDTLRec(){ } // default constructor

public QMTDTLRec(
       String facid, String prjno, String ndtkd, String insit, String insgp, String instl, 
       String inpkn, String ipcnt, String code1, String seqnm, String rptno, String inrcd, 
       String ldval, String cdval, String dftps, double dftlt, double cfmlt, String wldno, 
       String orglv, String remrk, int adate, int mdate, String auser, String muser, 
       String trmid){
    this.facid = facid;
    this.prjno = prjno;
    this.ndtkd = ndtkd;
    this.insit = insit;
    this.insgp = insgp;
    this.instl = instl;
    this.inpkn = inpkn;
    this.ipcnt = ipcnt;
    this.code1 = code1;
    this.seqnm = seqnm;
    this.rptno = rptno;
    this.inrcd = inrcd;
    this.ldval = ldval;
    this.cdval = cdval;
    this.dftps = dftps;
    this.dftlt = dftlt;
    this.cfmlt = cfmlt;
    this.wldno = wldno;
    this.orglv = orglv;
    this.remrk = remrk;
    this.adate = adate;
    this.mdate = mdate;
    this.auser = auser;
    this.muser = muser;
    this.trmid = trmid;
} // Constructor


// Getter 
public String getFacid(){ return facid;}
public String getPrjno(){ return prjno;}
public String getNdtkd(){ return ndtkd;}
public String getInsit(){ return insit;}
public String getInsgp(){ return insgp;}
public String getInstl(){ return instl;}
public String getInpkn(){ return inpkn;}
public String getIpcnt(){ return ipcnt;}
public String getCode1(){ return code1;}
public String getSeqnm(){ return seqnm;}
public String getRptno(){ return rptno;}
public String getInrcd(){ return inrcd;}
public String getLdval(){ return ldval;}
public String getCdval(){ return cdval;}
public String getDftps(){ return dftps;}
public double getDftlt(){ return dftlt;}
public double getCfmlt(){ return cfmlt;}
public String getWldno(){ return wldno;}
public String getOrglv(){ return orglv;}
public String getRemrk(){ return remrk;}
public int getAdate(){ return adate;}
public int getMdate(){ return mdate;}
public String getAuser(){ return auser;}
public String getMuser(){ return muser;}
public String getTrmid(){ return trmid;}

// Setter 
public void setFacid(String facid){ this.facid = facid;}
public void setPrjno(String prjno){ this.prjno = prjno;}
public void setNdtkd(String ndtkd){ this.ndtkd = ndtkd;}
public void setInsit(String insit){ this.insit = insit;}
public void setInsgp(String insgp){ this.insgp = insgp;}
public void setInstl(String instl){ this.instl = instl;}
public void setInpkn(String inpkn){ this.inpkn = inpkn;}
public void setIpcnt(String ipcnt){ this.ipcnt = ipcnt;}
public void setCode1(String code1){ this.code1 = code1;}
public void setSeqnm(String seqnm){ this.seqnm = seqnm;}
public void setRptno(String rptno){ this.rptno = rptno;}
public void setInrcd(String inrcd){ this.inrcd = inrcd;}
public void setLdval(String ldval){ this.ldval = ldval;}
public void setCdval(String cdval){ this.cdval = cdval;}
public void setDftps(String dftps){ this.dftps = dftps;}
public void setDftlt(double dftlt){ this.dftlt = dftlt;}
public void setCfmlt(double cfmlt){ this.cfmlt = cfmlt;}
public void setWldno(String wldno){ this.wldno = wldno;}
public void setOrglv(String orglv){ this.orglv = orglv;}
public void setRemrk(String remrk){ this.remrk = remrk;}
public void setAdate(int adate){ this.adate = adate;}
public void setMdate(int mdate){ this.mdate = mdate;}
public void setAuser(String auser){ this.auser = auser;}
public void setMuser(String muser){ this.muser = muser;}
public void setTrmid(String trmid){ this.trmid = trmid;}

/**
* getString 
* @param int seq 
* @return field Value 
*/
public String getString(int seq){ 
 String field=null;
  switch (seq) {
  case  1 : field = facid + "" ; break;
  case  2 : field = prjno + "" ; break;
  case  3 : field = ndtkd + "" ; break;
  case  4 : field = insit + "" ; break;
  case  5 : field = insgp + "" ; break;
  case  6 : field = instl + "" ; break;
  case  7 : field = inpkn + "" ; break;
  case  8 : field = ipcnt + "" ; break;
  case  9 : field = code1 + "" ; break;
  case  10 : field = seqnm + "" ; break;
  case  11 : field = rptno + "" ; break;
  case  12 : field = inrcd + "" ; break;
  case  13 : field = ldval + "" ; break;
  case  14 : field = cdval + "" ; break;
  case  15 : field = dftps + "" ; break;
  case  16 : field = dftlt + "" ; break;
  case  17 : field = cfmlt + "" ; break;
  case  18 : field = wldno + "" ; break;
  case  19 : field = orglv + "" ; break;
  case  20 : field = remrk + "" ; break;
  case  21 : field = adate + "" ; break;
  case  22 : field = mdate + "" ; break;
  case  23 : field = auser + "" ; break;
  case  24 : field = muser + "" ; break;
  case  25 : field = trmid + "" ; break;
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
     } else if(rec.equalsIgnoreCase("prjno")){ field = prjno + "" ; 
     } else if(rec.equalsIgnoreCase("ndtkd")){ field = ndtkd + "" ; 
     } else if(rec.equalsIgnoreCase("insit")){ field = insit + "" ; 
     } else if(rec.equalsIgnoreCase("insgp")){ field = insgp + "" ; 
     } else if(rec.equalsIgnoreCase("instl")){ field = instl + "" ; 
     } else if(rec.equalsIgnoreCase("inpkn")){ field = inpkn + "" ; 
     } else if(rec.equalsIgnoreCase("ipcnt")){ field = ipcnt + "" ; 
     } else if(rec.equalsIgnoreCase("code1")){ field = code1 + "" ; 
     } else if(rec.equalsIgnoreCase("seqnm")){ field = seqnm + "" ; 
     } else if(rec.equalsIgnoreCase("rptno")){ field = rptno + "" ; 
     } else if(rec.equalsIgnoreCase("inrcd")){ field = inrcd + "" ; 
     } else if(rec.equalsIgnoreCase("ldval")){ field = ldval + "" ; 
     } else if(rec.equalsIgnoreCase("cdval")){ field = cdval + "" ; 
     } else if(rec.equalsIgnoreCase("dftps")){ field = dftps + "" ; 
     } else if(rec.equalsIgnoreCase("dftlt")){ field = dftlt + "" ; 
     } else if(rec.equalsIgnoreCase("cfmlt")){ field = cfmlt + "" ; 
     } else if(rec.equalsIgnoreCase("wldno")){ field = wldno + "" ; 
     } else if(rec.equalsIgnoreCase("orglv")){ field = orglv + "" ; 
     } else if(rec.equalsIgnoreCase("remrk")){ field = remrk + "" ; 
     } else if(rec.equalsIgnoreCase("adate")){ field = adate + "" ; 
     } else if(rec.equalsIgnoreCase("mdate")){ field = mdate + "" ; 
     } else if(rec.equalsIgnoreCase("auser")){ field = auser + "" ; 
     } else if(rec.equalsIgnoreCase("muser")){ field = muser + "" ; 
     } else if(rec.equalsIgnoreCase("trmid")){ field = trmid + "" ; 
}// end if
 return field;
}// end getString (String fieldName)

/**
* fieldNames 
* @param none 
* @return field Names[]
*/
public String[] fieldNames() {
    String [] tempx = {"", "FACID", "PRJNO", "NDTKD", "INSIT", "INSGP", "INSTL", "INPKN", 
       "IPCNT", "CODE1", "SEQNM", "RPTNO", "INRCD", "LDVAL", "CDVAL", 
       "DFTPS", "DFTLT", "CFMLT", "WLDNO", "ORGLV", "REMRK", "ADATE", 
       "MDATE", "AUSER", "MUSER", "TRMID"};
    return tempx;
}

/**
* Key fieldNames 
* @param none 
* @return Key field Names[]
*/
public String[] keyFieldNames() {
    String [] tempx = {"", "FACID", "PRJNO", "NDTKD", "INSIT", "INSGP", "INSTL", "INPKN", 
       "IPCNT"};
    return tempx;
}

}// end QMTDTLRec class