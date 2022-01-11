package com.bes_line.mst;

// Entity Class for CDSRMG
/**
 *
 * @(#) CDSRMGRec.java
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

import org.jsn.jdf.db.*;

public class CDSRMGRec extends EntityData {
    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) 
    public String facid; 		// (VARCHAR2, 2.0)
    public String cattp; 		// (VARCHAR2, 3.0)
    public String shpkd; 		// (VARCHAR2, 3.0)
    public String catcd; 		// (VARCHAR2, 10.0)
    public String upcod; 		// (VARCHAR2, 10.0)
    public String catnm; 		// (VARCHAR2, 100.0)
    public String useyn; 		// (VARCHAR2, 1.0)
    public int lvlid; 		// (NUMBER, 4.0)
    public String remrk; 		// (VARCHAR2, 500.0)
    public int adate; 		// (NUMBER, 8.0)
    public String auser; 		// (VARCHAR2, 30.0)
    public int mdate; 		// (NUMBER, 8.0)
    public String muser; 		// (VARCHAR2, 30.0)

public CDSRMGRec(){ } // default constructor

public CDSRMGRec(
       String facid, String cattp, String shpkd, String catcd, String upcod, String catnm, 
       String useyn, int lvlid, String remrk, int adate, String auser, int mdate, 
       String muser){
    this.facid = facid;
    this.cattp = cattp;
    this.shpkd = shpkd;
    this.catcd = catcd;
    this.upcod = upcod;
    this.catnm = catnm;
    this.useyn = useyn;
    this.lvlid = lvlid;
    this.remrk = remrk;
    this.adate = adate;
    this.auser = auser;
    this.mdate = mdate;
    this.muser = muser;
} // Constructor


// Getter 
public String getFacid(){ return facid;}
public String getCattp(){ return cattp;}
public String getShpkd(){ return shpkd;}
public String getCatcd(){ return catcd;}
public String getUpcod(){ return upcod;}
public String getCatnm(){ return catnm;}
public String getUseyn(){ return useyn;}
public int getLvlid(){ return lvlid;}
public String getRemrk(){ return remrk;}
public int getAdate(){ return adate;}
public String getAuser(){ return auser;}
public int getMdate(){ return mdate;}
public String getMuser(){ return muser;}

// Setter 
public void setFacid(String facid){ this.facid = facid;}
public void setCattp(String cattp){ this.cattp = cattp;}
public void setShpkd(String shpkd){ this.shpkd = shpkd;}
public void setCatcd(String catcd){ this.catcd = catcd;}
public void setUpcod(String upcod){ this.upcod = upcod;}
public void setCatnm(String catnm){ this.catnm = catnm;}
public void setUseyn(String useyn){ this.useyn = useyn;}
public void setLvlid(int lvlid){ this.lvlid = lvlid;}
public void setRemrk(String remrk){ this.remrk = remrk;}
public void setAdate(int adate){ this.adate = adate;}
public void setAuser(String auser){ this.auser = auser;}
public void setMdate(int mdate){ this.mdate = mdate;}
public void setMuser(String muser){ this.muser = muser;}

/**
* getString 
* @param int seq 
* @return field Value 
*/
public String getString(int seq){ 
 String field=null;
  switch (seq) {
  case  1 : field = facid + "" ; break;
  case  2 : field = cattp + "" ; break;
  case  3 : field = shpkd + "" ; break;
  case  4 : field = catcd + "" ; break;
  case  5 : field = upcod + "" ; break;
  case  6 : field = catnm + "" ; break;
  case  7 : field = useyn + "" ; break;
  case  8 : field = lvlid + "" ; break;
  case  9 : field = remrk + "" ; break;
  case  10 : field = adate + "" ; break;
  case  11 : field = auser + "" ; break;
  case  12 : field = mdate + "" ; break;
  case  13 : field = muser + "" ; break;
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
     } else if(rec.equalsIgnoreCase("cattp")){ field = cattp + "" ; 
     } else if(rec.equalsIgnoreCase("shpkd")){ field = shpkd + "" ; 
     } else if(rec.equalsIgnoreCase("catcd")){ field = catcd + "" ; 
     } else if(rec.equalsIgnoreCase("upcod")){ field = upcod + "" ; 
     } else if(rec.equalsIgnoreCase("catnm")){ field = catnm + "" ; 
     } else if(rec.equalsIgnoreCase("useyn")){ field = useyn + "" ; 
     } else if(rec.equalsIgnoreCase("lvlid")){ field = lvlid + "" ; 
     } else if(rec.equalsIgnoreCase("remrk")){ field = remrk + "" ; 
     } else if(rec.equalsIgnoreCase("adate")){ field = adate + "" ; 
     } else if(rec.equalsIgnoreCase("auser")){ field = auser + "" ; 
     } else if(rec.equalsIgnoreCase("mdate")){ field = mdate + "" ; 
     } else if(rec.equalsIgnoreCase("muser")){ field = muser + "" ; 
}// end if
 return field;
}// end getString (String fieldName)

/**
* fieldNames 
* @param none 
* @return field Names[]
*/
public String[] fieldNames() {
    String [] tempx = {"", "FACID", "CATTP", "SHPKD", "CATCD", "UPCOD", "CATNM", "USEYN", 
       "LVLID", "REMRK", "ADATE", "AUSER", "MDATE", "MUSER"};
    return tempx;
}

/**
* Key fieldNames 
* @param none 
* @return Key field Names[]
*/
public String[] keyFieldNames() {
    String [] tempx = {"", "FACID", "CATTP", "SHPKD", "CATCD", "UPCOD"};
    return tempx;
}

}// end CDSRMGRec class