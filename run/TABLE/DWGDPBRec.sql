/* DWGDPB Table Create script */
CREATE TABLE DWGDPB(
    FACID varchar2(2,0) NOT NULL , 
    DWGNO varchar2(20,0) NOT NULL , 
    CHGDT date(0,0) NOT NULL , 
    CHGDS varchar2(200,0), 
    REVNO varchar2(3,0), 
    STSCD varchar2(3,0), 
    PRJNO varchar2(5,0), 
    DWGGR varchar2(3,0), 
    BLKNO varchar2(10,0), 
    DWGMP varchar2(3,0), 
    DWGSR varchar2(5,0), 
    DWGDS varchar2(200,0), 
    MKDEM varchar2(10,0), 
    DPTNO varchar2(10,0), 
    DWIPD number(8,0), 
    DSPDT number(8,0), 
    ADTPD number(8,0), 
    MKDPS number(8,0), 
    MKDPF number(8,0), 
    PORPD number(8,0), 
    DWIPT varchar2(3,0), 
    BASPD number(8,0), 
    DWIMG number(6,0), 
    INCBK varchar2(2000,0), 
    IPRBK varchar2(2000,0), 
    MKDPT varchar2(3,0), 
    ADTPT varchar2(3,0), 
    PORPT varchar2(3,0), 
    DWIND number(8,0), 
    DSPND number(8,0), 
    ADTND number(8,0), 
    MKSND number(8,0), 
    MKFND number(8,0), 
    PORND number(8,0), 
    REMRK varchar2(255,0), 
    ADATE number(8,0), 
    AUSER varchar2(30,0), 
    MDATE number(8,0), 
    MUSER varchar2(30,0), 
    APFPT varchar2(3,0), 
    APFND number(8,0), 
    APFPF number(8,0), 
    APOPF number(8,0), 
    APOND number(8,0), 
    APCPF number(8,0), 
    APCND number(8,0), 
    DWIPO varchar2(3,0), 
    DWIMO number(6,0), 
    BASOD number(8,0), 
    PRSST varchar2(3,0), 
    KEYCD varchar2(200,0), 
    Primary Key (FACID, DWGNO, CHGDT)
);