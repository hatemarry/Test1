/* CDSRMG Table Create script */
CREATE TABLE CDSRMG(
    FACID varchar2(2,0) NOT NULL , 
    CATTP varchar2(3,0) NOT NULL , 
    SHPKD varchar2(3,0) NOT NULL , 
    CATCD varchar2(10,0) NOT NULL , 
    UPCOD varchar2(10,0) NOT NULL , 
    CATNM varchar2(100,0), 
    USEYN varchar2(1,0), 
    LVLID number(4,0), 
    REMRK varchar2(500,0), 
    ADATE number(8,0), 
    AUSER varchar2(30,0), 
    MDATE number(8,0), 
    MUSER varchar2(30,0), 
    Primary Key (FACID, CATTP, SHPKD, CATCD, UPCOD)
);