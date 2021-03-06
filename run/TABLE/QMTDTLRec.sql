/* QMTDTL Table Create script */
CREATE TABLE QMTDTL(
    FACID varchar2(2,0) NOT NULL , 
    PRJNO varchar2(10,0) NOT NULL , 
    NDTKD varchar2(3,0) NOT NULL , 
    INSIT varchar2(3,0) NOT NULL , 
    INSGP varchar2(3,0) NOT NULL , 
    INSTL varchar2(50,0) NOT NULL , 
    INPKN varchar2(3,0) NOT NULL , 
    IPCNT varchar2(3,0) NOT NULL , 
    CODE1 varchar2(6,0), 
    SEQNM varchar2(30,0), 
    RPTNO varchar2(20,0), 
    INRCD varchar2(3,0), 
    LDVAL varchar2(30,0), 
    CDVAL varchar2(30,0), 
    DFTPS varchar2(30,0), 
    DFTLT number(12,3), 
    CFMLT number(12,3), 
    WLDNO varchar2(15,0), 
    ORGLV varchar2(3,0), 
    REMRK varchar2(255,0), 
    ADATE number(8,0), 
    MDATE number(8,0), 
    AUSER varchar2(30,0), 
    MUSER varchar2(30,0), 
    TRMID varchar2(20,0), 
    Primary Key (FACID, PRJNO, NDTKD, INSIT, INSGP, INSTL, INPKN, IPCNT)
);