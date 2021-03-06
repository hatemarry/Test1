// Generate Class 
/**      
 *
 * @(#) BESGenDDM.java
 * Copyright 1999-2002 by  Daewoo Information System, Inc.,
 * BES(Best Enterprise System) Team,
 * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea
 * All rights reserved.
 *
 * NOTICE !  You cannot copy or redistribute this code,
 * and you should not remove the information about the
 * copyright notice and the author.
 *
 * @version v1.0
 * @date    2003-01-07
 * @modifyDate 2021-01-07
 * @author  JinYong Jeong, jch(at)disc.co.kr.
 * @since   JDK1.2
 * @modifiy v0.1    ǥ�� �wα׷� ��
 *                - �����ڵ�; '�� �⺻ jsp/Servlet �ڵ带 ���Ѵ�.
 *
**/


 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class BESGenDDM extends JFrame{
    JFrame      mainFrame;
    JPanel      mainPanel, subP1, subP2, subP3;
    JPanel      dbPanel, tablePanel,tablePanel2,tablePanel3, resultPanel, queryPanel;
    JLabel      jlDriver, jlURL, jlUser, jlPassword, jlPgmName, jlPackName,jlUseTableName, jlBaseTableName;
    JTextField  jtDriver, jtURL, jtUser, jtPgmName, jtPackName,jtUseTableName, jtBaseTableName, jtTableName;
    JPasswordField   jpPassword;
    JButton     jbGetTable, jbGetScript, jbSaveFile, jbLoadFile, jbLoadQuery;
    JList       tableList, dsptableList, returntableList;
    JTable      tableDetail;
    JScrollPane jsTableList, jsTable;
    JLabel      jltl, jlxxx;
    DefaultListModel  dlmList;
    DefaultTableModel dtmTable;
    ListSelectionModel colTable;

    JTextArea   jtSQL, jtRec, jtDBWrapBES, jtDBWrap, jtQuery, jtAddQuery;
    JScrollPane jsSQL, jsRec, jsDBWrapBES, jsDBWrap, jsQuery, jsAddQuery;

    Checkbox cbframeType, cbflowType, cbscrollType, cbsearchType, cbeditType;

//*********** �wα׷� ���� d�� ���� **************************************//

	// File Path ==> C:/gen.properties 
	String prjRoot; 
	String prjMst; 
	String packageUsed; 
	String type="";
	
    String fileName ;
    String fullTableName;
    String fileName1 ;
    String entityName ;
    String dbWrapName ;
    String entity ;
    String dbwrap ;
    String entityName1 ;
    String dbWrapName1 ;
    String entity1 ;
    String dbwrap1 ;
    String allPgmName ;
    String pgmTitle ;
    String packageName ;
    String useTableName; 
    String baseTableName; 
    String frameType;
    String flowType;
    String scrollType;
    String searchType;
    String editType;
    
    String baseTableAlias; 

	int COL_IS_KEY = 0; 
	int COL_FIELD_TYPE = 1; 
	int COL_FIELD_KEY = 2; 
	int COL_FIELD_SIZE = 3; 
	int COL_FIELD_NAME = 4; 
	int COL_LIST_MAKE = 5; 
	int COL_EDIT_MAKE = 6; 
	int COL_IS_READONLY = 7; 
	int COL_IS_ERRORCHK = 8; 
	int COL_IS_SEARCH = 9; 
	int COL_IS_DATE = 10; 
	int COL_ACT_PROMPT = 11; 
	
    int primeCount;       
    int listCount ;       
    int promptCount ;       
    int actCount  ;      
    int trowCount; 

    String[] primeKey;
    String[] listKey;
    String[] promptKey;
    String[] actKey;

    String[] getprimeKey;
    String[] getlistKey;
    String[] getpromptKey;
    String[] getactKey;

    String [] primeKeyType ;
    String [] primeKeyOrder;
    String [] listKeyType  ;
    String [] promptKeyType ;
    String [] actKeyType ;

       int [] primeKeySize ;
       int [] listKeySize ;
       int [] promptKeySize ;
       int [] actKeySize ;

    String [] actTitle ;
    String [] listTitle ;
    String [] viewTitle ;
    String [] viewKey ;
    String [] actReqChk ;
    String [] actDspChk ;
    String [] actFndChk ;
    String [] actDatChk ;
    String [] actFormat ;
    String [] actPmtPgm ;

    String [] colLabel ;
    String [] colType ;
    String [] colTypeName ;
    int colCount ;
    int [] colPrecision ;
    int [] colScale ;
    
    
    // Query parse add ... 
    Vector g_colNameV; 
    Vector g_colFieldV; 
    String g_viewQuery; 
    //String g_viewAddQuery; 
    String g_writeQuery; 
    String g_writeAddQuery; 
    String g_countQuery; 
    boolean g_isFromQuery; 
    boolean g_isWhereExist; 
    private static boolean g_isPrompt = false; 
    
    // DB Conversion Variables 
    Vector dbTypesV = null;
    
    //Vector keyVector;
    


//*********** �wα׷� ���� d�� �� **************************************//

public static void main(String args[]){
	if( args != null && args.length > 0 && args[0] != null && args[0].equalsIgnoreCase("PROMPT") ) { 
		g_isPrompt = true; 
		System.out.println("Program generator......"); 
	}

	new BESGenDDM();
	//bes.show();
} // main

public BESGenDDM(){
    mainFrame = new JFrame("Gerate!׷� ��");
    // Main JPanel

// ��� DB d�� �ǳ� Start
    jlDriver    = new JLabel("Driver", JLabel.CENTER);
    jlURL       = new JLabel("URL", JLabel.CENTER);
    jlUser      = new JLabel("User",JLabel.CENTER);
    jlPassword  = new JLabel("Password",JLabel.CENTER);
    jlPackName  = new JLabel("Package",JLabel.CENTER);
    jlPgmName   = new JLabel("Program Name",JLabel.CENTER);
    jlUseTableName = new JLabel("Use Table",JLabel.CENTER);
    jlBaseTableName= new JLabel("Base Table",JLabel.CENTER);

	String configFile = "C:/" + "gen.properties"; 
	java.util.Properties p = new java.util.Properties();
	try {
		java.io.FileInputStream fis = new java.io.FileInputStream(configFile);
		p.load(fis); 
		fis.close();
	} catch(Exception ie) {
		showMessage("can't open file : " + configFile); 
		return; 
	}
	prjRoot = p.getProperty("package.root"); 
	prjMst = p.getProperty("package.mst"); 
	packageUsed = p.getProperty("package.used").toUpperCase();  
	if( p.getProperty("program.runtype").equals("PROMPT") ) g_isPrompt = true; 


	jtDriver    = new JTextField(p.getProperty("db.driver"));
    jtURL       = new JTextField(p.getProperty("db.url"));
    jtUser      = new JTextField(p.getProperty("db.user"));
    jpPassword  = new JPasswordField(p.getProperty("db.password"));
    
    jbGetTable  = new JButton("Get Tables");
    jbSaveFile  = new JButton("Save File");
    jbLoadFile  = new JButton("Load File");

    jtPackName   = new JTextField("");
    jtPgmName    = new JTextField("");
//    jtUseTableName  = new JTextField("");
    jtTableName = new JTextField("");
    
    jtBaseTableName = new JTextField("");
    
    GridBagLayout gridbag = new GridBagLayout();
    dbPanel     = new JPanel(gridbag);
    
    GridBagConstraints gbcTemp = new GridBagConstraints();
    gbcTemp.fill = GridBagConstraints.BOTH;
    
    gbcTemp.gridheight = 6;
    gbcTemp.weighty = 1.0;
    gridbag.setConstraints(jbGetTable,gbcTemp);
    dbPanel.add(jbGetTable);
    
    // File save button add ... 
    gbcTemp.gridheight = 6;
    gbcTemp.weighty = 1.0;
    gridbag.setConstraints(jbSaveFile,gbcTemp);
    dbPanel.add(jbSaveFile);

	gbcTemp.gridheight = 6;
	gridbag.setConstraints(jbLoadFile,gbcTemp);
    dbPanel.add(jbLoadFile);

    
    gbcTemp.weightx = 1.0;
    gbcTemp.gridheight = 1;
    gridbag.setConstraints(jlDriver,gbcTemp);
    dbPanel.add(jlDriver);
    
    gbcTemp.gridwidth = 1; // default to 
    gridbag.setConstraints(jtDriver,gbcTemp);
    dbPanel.add(jtDriver);
       
    gbcTemp.gridwidth = 1; // default to 
    gridbag.setConstraints(jlURL,gbcTemp);
    dbPanel.add(jlURL);
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gridbag.setConstraints(jtURL,gbcTemp);
    dbPanel.add(jtURL);
    
    gbcTemp.gridwidth = 1; // default to 
    gridbag.setConstraints(jlUser,gbcTemp);
    dbPanel.add(jlUser);
    
    gridbag.setConstraints(jtUser,gbcTemp);
    dbPanel.add(jtUser);
    
    gridbag.setConstraints(jlPassword,gbcTemp);
    dbPanel.add(jlPassword);
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gridbag.setConstraints(jpPassword,gbcTemp);
    dbPanel.add(jpPassword);
    
    gbcTemp.gridwidth = 1; // default to 
    gridbag.setConstraints(jlPackName,gbcTemp);
    dbPanel.add(jlPackName);
    
    gridbag.setConstraints(jtPackName,gbcTemp);
    dbPanel.add(jtPackName);
    
    gridbag.setConstraints(jlPgmName,gbcTemp);
    dbPanel.add(jlPgmName);
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gridbag.setConstraints(jtPgmName,gbcTemp);
    dbPanel.add(jtPgmName);

   
    gbcTemp.weightx = 1.0;
    gbcTemp.weighty = 0.2;
    gbcTemp.gridwidth = 1; // default to 
    gridbag.setConstraints(jlUseTableName,gbcTemp);
    dbPanel.add(jlUseTableName);

    gridbag.setConstraints(jtTableName,gbcTemp);
    dbPanel.add(jtTableName);
    
    gridbag.setConstraints(jlBaseTableName,gbcTemp);
    dbPanel.add(jlBaseTableName);
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gridbag.setConstraints(jtBaseTableName,gbcTemp);
    dbPanel.add(jtBaseTableName);

// Check Fields.. 
    gbcTemp.gridwidth = 1; // default to 
    gbcTemp.weighty = 0.2;
    jltl        = new JLabel("Frame ����", JLabel.CENTER);
    dbPanel.add(jltl);
   
    cbframeType = new Checkbox(" 2�� Frame");
    gbcTemp.weighty = 0.2;
    gridbag.setConstraints(cbframeType,gbcTemp);
    dbPanel.add(cbframeType);

    gbcTemp.weighty = 0.2;
    JLabel jlt2        = new JLabel("Flow ����", JLabel.CENTER);
    dbPanel.add(jlt2);
   
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER; 
    cbflowType = new Checkbox(" List => Update");
    gbcTemp.weighty = 0.2;
    gridbag.setConstraints(cbflowType,gbcTemp);
    dbPanel.add(cbflowType);
    
    // List Scroll Tyle Add 
    gbcTemp.gridwidth = 1; // default to 
    gbcTemp.weighty = 0.2;
    JLabel jlt3        = new JLabel("List Gubun", JLabel.CENTER);
    dbPanel.add(jlt3);
   
    cbscrollType = new Checkbox(" Scroll Type");
    gbcTemp.weighty = 0.2;
    gridbag.setConstraints(cbscrollType,gbcTemp);
    dbPanel.add(cbscrollType);
    
    //Search change add  
    gbcTemp.weighty = 0.2;
    JLabel jlt4        = new JLabel("Header Search", JLabel.CENTER);
    dbPanel.add(jlt4);
   
    gbcTemp.gridwidth = 1;//end Row
    cbsearchType = new Checkbox(" �˻�v�Ǻ���");
    gbcTemp.weighty = 0.2;
    gridbag.setConstraints(cbsearchType,gbcTemp);
    dbPanel.add(cbsearchType);
    
    gbcTemp.weighty = 0.2;
    JLabel jlt5        = new JLabel("List Edit", JLabel.CENTER);
    dbPanel.add(jlt5);
   
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    cbeditType = new Checkbox("List �Է�");
    gbcTemp.weighty = 0.2;
    gridbag.setConstraints(cbeditType,gbcTemp);
    dbPanel.add(cbeditType);
    


// ��� DB d�� �ǳ� End

// �ߴ� Table d�� �ǳ� Start

    tablePanel2  = new JPanel(gridbag);
    
    dlmList     = new DefaultListModel();
    
    Vector v = getTableNames();
    Iterator it = v.iterator();
    String xTemp = "";
    while(it.hasNext()){
        xTemp = (String) it.next();
        dlmList.addElement(xTemp);
    } // end while
    
    //tableList   = new JList(dlmList);
    tableList   = new JList(dlmList);
    colTable    = tableList.getSelectionModel();
    colTable.addListSelectionListener(new My_tableHandler());
    jsTableList = new JScrollPane(tableList);
    
    gbcTemp.gridwidth = 1; // default to 
    gbcTemp.weightx = 0.5;
    gbcTemp.weighty = 3.0;
    gridbag.setConstraints(jsTableList,gbcTemp);
    tablePanel2.add(jsTableList);
    
    dtmTable= new DefaultTableModel();
    tableDetail = new JTable(dtmTable);
    tableDetail.setPreferredScrollableViewportSize(new Dimension(700,350));

    dtmTable.addColumn("Key");
    dtmTable.addColumn("Field type");
    dtmTable.addColumn("Field Name");
    dtmTable.addColumn("Field Size");
    dtmTable.addColumn("Screen Name");
    dtmTable.addColumn("List");
    dtmTable.addColumn("Edit");
    dtmTable.addColumn("Out field at update");
    dtmTable.addColumn("Mandatory Input");
    dtmTable.addColumn("Search Condition");
    dtmTable.addColumn("Data");
    dtmTable.addColumn("Ref Program");
    
    tableDetail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

       setUpCheckBoxColumn(tableDetail.getColumnModel().getColumn(7));
       setUpCheckBoxColumn(tableDetail.getColumnModel().getColumn(8));
       setUpCheckBoxColumn(tableDetail.getColumnModel().getColumn(9));
       setUpCheckBoxColumn(tableDetail.getColumnModel().getColumn(10));


    jsTable     = new JScrollPane(tableDetail);
    
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gbcTemp.weightx = 3.0;
    gbcTemp.weighty = 3.0;
    gridbag.setConstraints(jsTable,gbcTemp);
    tablePanel2.add(jsTable);
    
    resultPanel = new JPanel(gridbag);
    jtRec       = new JTextArea("Record Bean", 5,5);
//    jtDBWrap    = new JTextArea("User DBWrap", 5,5);
    
    jsRec       = new JScrollPane(jtRec);
//    jsDBWrap    = new JScrollPane(jtDBWrap);

    jbGetScript = new JButton("Generate Scripts");
    gbcTemp.weightx = 10;
    gbcTemp.weighty = 0.3;
    gridbag.setConstraints(jbGetScript,gbcTemp);
    resultPanel.add(jbGetScript);

    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gbcTemp.weightx = 10;
    gbcTemp.weighty = 2;
    gridbag.setConstraints(jsRec,gbcTemp);
    resultPanel.add(jsRec);
//    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
//    gbcTemp.weightx = 5;
//    gbcTemp.weighty = 1;
//    gridbag.setConstraints(jsDBWrap,gbcTemp);
//    resultPanel.add(jsDBWrap);
// �ϴ� ��� d�� �ǳ� End


// �ϴ� Query Edit �ǳ� Start
    
    jtQuery     = new JTextArea("select query ", 6,5);
    jtAddQuery  = new JTextArea("option query not used.. ", 3,3);
    
    jsQuery     = new JScrollPane(jtQuery);
    jsAddQuery     = new JScrollPane(jtAddQuery);
	jbLoadQuery = new JButton("Load Query");
    
    queryPanel = new JPanel(new BorderLayout(1,1));
    
    queryPanel.add(jbLoadQuery, BorderLayout.NORTH);
	queryPanel.add(jsQuery, BorderLayout.CENTER);
    queryPanel.add(jsAddQuery, BorderLayout.SOUTH);
    
// ��� ���̱�

    mainPanel = new JPanel(gridbag);

    subP1 = new JPanel(gridbag);
    subP2 = new JPanel(new GridLayout(1,1));
    subP3 = new JPanel(new GridLayout(1,1));
    
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gbcTemp.weightx = 1;
    gbcTemp.weighty = 1;
    gridbag.setConstraints(dbPanel,gbcTemp);
    subP1.add(dbPanel);

    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gbcTemp.weightx = 1;
    gbcTemp.weighty = 20;
    gridbag.setConstraints(tablePanel2,gbcTemp);
    subP1.add(tablePanel2);
    
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gbcTemp.weightx = 1;
    gbcTemp.weighty = 20;
    gridbag.setConstraints(subP3,gbcTemp);
    subP1.add(subP3);
    

    subP2.add(resultPanel);
    
    
    gridbag.setConstraints(queryPanel,gbcTemp);
    subP3.add(queryPanel);


    gbcTemp.gridwidth = 1;
    gbcTemp.weightx = 5;
    gbcTemp.weighty = 1;
    gridbag.setConstraints(subP1,gbcTemp);
    mainPanel.add(subP1);


    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gbcTemp.weightx = 1;
    gbcTemp.weighty = 1;
    gridbag.setConstraints(subP2,gbcTemp);
    mainPanel.add(subP2);
/*    
    gbcTemp.gridwidth = GridBagConstraints.REMAINDER;//end Row
    gbcTemp.gridheight = 1; 
    gbcTemp.weightx = 2;
    gbcTemp.weighty = 1;
    gridbag.setConstraints(subP3,gbcTemp);
    mainPanel.add(subP3);
*/

    mainFrame.setContentPane(mainPanel);

    addListener();
    mainFrame.pack();
    mainFrame.setVisible(true);
    
    // DB Converting.... 
    dbTypesV = new Vector(); 
    
    DbType mssqlDb = new DbType("mssql"); 
    DbType oracleDb = new DbType("oracle");
    DbType informixDb = new DbType("informix"); 
    
    dbTypesV.add( mssqlDb ); 
    dbTypesV.add( oracleDb ); 
    dbTypesV.add( informixDb ); 
    
    mssqlDb.addKey("ISNULL"); 
    oracleDb.addKey("NVL"); 
    informixDb.addKey("NVL"); 
    
    mssqlDb.addKey("TOP"); 
    oracleDb.addKey("FIRST"); 
    informixDb.addKey("FIRST"); 
    
    baseTableAlias = ""; 
    
    
} // BESGenDDM()


public Vector getTableNames(){
    Vector v = new Vector();
    Vector v1=new Vector();
    String tableName        = null;
    Connection conn   = null;
    Statement stmt = null;
    ResultSet rs = null;
    String ColumnType = null;
    String types[]  = {"TABLE","VIEW"}; // table(PF), view(LF ?), System(?)
    StringBuffer tempbz = new StringBuffer();

    try {
    	System.out.println(jtDriver.getText());
        Class.forName(jtDriver.getText()).newInstance();
        
        conn = DriverManager.getConnection(jtURL.getText(),jtUser.getText(),new String(jpPassword.getPassword()));
        DatabaseMetaData meta = null;
        meta = conn.getMetaData();
        
        //rs = meta.getTables(null, null, "%", types);
       rs = meta.getTables(null, jtUser.getText().toUpperCase().trim(), "%", types);
        while ( rs.next() ){
        	//System.out.println(rs.getString("TABLE_SCHEM"));
        	//v1.add(rs.getString("TABLE_SCHEM").toUpperCase());
        	
            //v.add(rs.getString("TABLE_SCHEM").toUpperCase()+"."+rs.getString("TABLE_NAME").toUpperCase());
        	
        	v.add(rs.getString("TABLE_NAME").toUpperCase());
        } // end while
        
        cbframeType.setState(true); 
        cbflowType.setState(true); 
        cbsearchType.setState(true); 
        cbeditType.setState(true); 
        
    } catch (Exception e) {
    	e.printStackTrace(); 
        System.out.println ("ERROR: " + e.toString());
    } finally {
        try { if (rs != null) rs.close (); } catch (SQLException e) { }
        try { if (stmt != null) stmt.close (); } catch (SQLException e) { }
        try { if (conn != null) conn.close (); } catch (SQLException e) { }
    } // try-catch-finally
    
    return v;    
} // end getTables

public void addListener() {
    mainFrame.addWindowListener(new My_WindowHandler());
    jbGetTable.addActionListener(new My_ButtonHandler());
    jbSaveFile.addActionListener(new My_ButtonHandler());
    jbLoadFile.addActionListener(new My_ButtonHandler());
    jbGetScript.addActionListener(new My_ButtonHandler());
    jbLoadQuery.addActionListener(new My_ButtonHandler());
}// addListener

class CheckCellRenderer extends JCheckBox implements TableCellRenderer
{
  protected Border noFocusBorder;

  public CheckCellRenderer(){

    noFocusBorder = new EmptyBorder(1,2,1,2);

  }// ����

  public Component getTableCellRendererComponent(JTable table,
    Object value,boolean isSelected,boolean hasFocus,  int row,int col)

  {
    if(value instanceof Boolean){
      Boolean b = (Boolean)value;
      this.setSelected(b.booleanValue());

    }// if

    setBackground(isSelected && !hasFocus ?  table.getSelectionBackground() : table.getBackground() );
    setForeground(isSelected && !hasFocus ?  table.getSelectionForeground() : table.getForeground() );
    setBorder(hasFocus ? UIManager.getBorder("Table.focusCellHighlightBorder") : noFocusBorder );

    return this;
  }// getTableCellRendererComponent

}// class CheckCellRenderer


// Window Listener
class My_WindowHandler extends WindowAdapter{
    public void windowClosing(WindowEvent e){
        mainFrame.setVisible(false);
        mainFrame.dispose();
        System.exit(0);
    } // windowClosing
}//WindowHandler

 // Action Listener
class  My_ButtonHandler implements ActionListener{
    public void actionPerformed (ActionEvent e) {
    
    if (e.getSource() == jbGetTable){
        dlmList.clear();
        baseTableAlias = ""; 
        Vector v = getTableNames();
        Iterator it = v.iterator();
        String xTemp = "";
        while(it.hasNext()){
            xTemp = (String) it.next();
            dlmList.addElement(xTemp);
        } // end while        
    }// end if jbGetTable
    
    else if (e.getSource() == jbSaveFile){
        int myRows = tableDetail.getRowCount();
	    int myCols = tableDetail.getColumnCount();
		
		if( myRows < 1 || myCols < 1 ) {
			showMessage("There is not data which it will store..");
			return; 
		} 
		
		allPgmName = jtPgmName.getText();
		packageName = jtPackName.getText();
		baseTableName = jtBaseTableName.getText();
		useTableName = jtTableName.getText();
		frameType =  cbframeType.getState() +"";
		flowType  =  cbflowType.getState() +"";
		scrollType  =  cbscrollType.getState() +"";
		searchType  =  cbsearchType.getState() +"";
		editType  =  cbeditType.getState() +"";
		
		if( allPgmName.equals("") ) {
			showMessage("Please, Input Program Name!");
			return; 
		}
		if( useTableName.equals("") ) {
			showMessage("Please, Input Wrapper Name!");
			return; 
		}
		if( packageName.equals("") ) {
			showMessage("Please, Input Package Name!");
			return; 
		}
		String isFromQuery = ""; 
		if( g_isFromQuery ) isFromQuery = "true"; 
		else isFromQuery = "false"; 
		StringBuffer buf = new StringBuffer(); 
		buf.append(packageName + "\t" + allPgmName + "\t" + baseTableName + "\t" + useTableName + "\t" + frameType + "\t" + flowType + "\t" + scrollType + "\t" + searchType + "\t" + editType + "\t" + isFromQuery + "\t\n"); 
		
	    for( int i=0 ; i < myRows ; i++ ) {
	    	for( int j=0; j<myCols; j++ ) {
	    		Object obj = tableDetail.getValueAt(i,j); 
	    		if( obj.getClass().getName().indexOf("String") >= 0 ) { 
	    			buf.append((String)obj + "\t"); 
	    			//System.out.println("" + i + ", " + j + " : " + (String)obj); 
	    		}
	    		
				else { 
					buf.append(obj.toString() + "\t"); 
					//System.out.println("" + i + ", " + j + " : " + obj.toString()); 
				}
				
	       }
	       buf.append("\n"); 
	    }
	    
	    
	    try {
		    File f = null;
	
		    f = new File("./PGM");
		    f.mkdir();
		    f = new File("./PGM/" + packageName) ;
		    f.mkdir();
		    f = new File("./PGM/" + packageName + "/" + allPgmName);
		    f.mkdir();
		    						
			String i_fileName = "./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+".pgm"; 
			
	        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(i_fileName,false)));
	        out.print(buf);
	        out.close();
	        
	        String lquery = jtQuery.getText(); 
			String laddQuery = jtAddQuery.getText(); 
			if( !lquery.equals("") ) { 
		        i_fileName = "./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+".sql"; 
				
		        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(i_fileName,false)));
		        out.print(lquery);
		        out.close();
		    }
		    if( !laddQuery.equals("") ) { 
		        i_fileName = "./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"Add.sql"; 
				
		        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(i_fileName,false)));
		        out.print(laddQuery);
		        out.close();
		    }
		    
	        showMessage("It was stored. \n" + i_fileName);
	        
	    } catch( Exception xe ) {
	    	showMessage("It wasn't stored. \n => " + xe.toString());
	    	xe.printStackTrace(); 
	    }
	    
        
        
    }// end if jbSaveFile
    
    else if (e.getSource() == jbLoadFile){
    	
    	String lineStr = ""; 
		int lNum = 1; 
				
		allPgmName = jtPgmName.getText();
		packageName = jtPackName.getText();
		useTableName = jtTableName.getText();
		String i_fileName = "./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+".pgm"; 
		
		if( useTableName.equals("") ) {
			showMessage("Please, Input Wrapper Name!");
			return; 
		}
		if( packageName.equals("") ) {
			showMessage("Please, Input Package Name!");
			return; 
		}
		
		
		
		try { 
			
			LineNumberReader lreader = null; 
			try { 
				lreader = new LineNumberReader( new FileReader( new File( i_fileName ) ) ); 
				if( lreader == null ) {
					showMessage("There is not possibility of reading the file!");
					return ; 
				}
			} catch(Exception ie) {
				showMessage("There is not possibility of reading the file! " + i_fileName);
				return; 
			}
			
			
			
	                   
			lreader.setLineNumber(lNum++); 
			lineStr = lreader.readLine(); 
			
			int cnt = 0; 
			StringTokenizer st = new StringTokenizer(lineStr, "\t");
			useTableName = ""; 
			
			while (st.hasMoreTokens()) {
				String data = st.nextToken();
				boolean bdata = false; 
				if( data.equals("true") ) bdata = true; 
				
		        if( cnt == 0 ) jtPackName.setText(data); 
		        else if( cnt == 1 ) jtPgmName.setText(data); 
		        else if( cnt == 2 ) jtBaseTableName.setText(data); 
		        else if( cnt == 3 ) { 
		        	jtTableName.setText(data); 
		        	useTableName = data; 
		        }
		        else if( cnt == 4 ) cbframeType.setState(bdata); 
		        else if( cnt == 5 ) cbflowType.setState(bdata); 
		        else if( cnt == 6 ) cbscrollType.setState(bdata); 
		        else if( cnt == 7 ) cbsearchType.setState(bdata); 
		        else if( cnt == 8 ) cbeditType.setState(bdata); 
		        else if( cnt == 9 ) g_isFromQuery = bdata; 
		        cnt++; 
		    }
			
			// query file read.. 
			i_fileName = "./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+".sql"; 
			
			FileReader fr = new FileReader( new File( i_fileName ) ); 
			StringBuffer rbuf = new StringBuffer(); 
			if( fr != null ) {
				int res = -1; 
				while( (res = fr.read()) > 0 ) rbuf.append((char)res); 
				fr.close(); 
			}
			jtQuery.setText(new String(rbuf)); 
			
			i_fileName = "./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"Add.sql"; 
			
			fr = new FileReader( new File( i_fileName ) ); 
			rbuf = new StringBuffer(); 
			if( fr != null ) {
				int res = -1; 
				while( (res = fr.read()) > 0 ) rbuf.append((char)res); 
				fr.close(); 
			}
			jtAddQuery.setText(new String(rbuf)); 
			
			
			if( !g_isFromQuery ) { 
				boolean isExistTable = false; 
				int tableCount =  dlmList.getSize();
				for( int i=0; i<tableCount; i++ ) {
					if( ((String)dlmList.getElementAt(i)).equals( useTableName ) ) {
						tableList.setSelectedIndex(i); 
						isExistTable = true; 
					}
				}
				
				if( !isExistTable ) {
					showMessage("There is not table!");
					return ; 
				}
			} else settingQueryData(); 
			
			
			for( int i=dtmTable.getRowCount()-1; i>=0; i-- ) {
				dtmTable.removeRow(i); 
			}
			
			
			lreader.setLineNumber(lNum); 
			lineStr = lreader.readLine(); 
			
			Object fieldValue[] = new Object[12]; 
			
			while( lineStr != null && !lineStr.equals("") ) {
				st = new StringTokenizer(lineStr, "\t");
				cnt = 0; 
				int prev_pos = 0; 
				int pos = lineStr.indexOf("\t", 0); 
				boolean isEnd = false; 
				boolean isNoRow = false; 
				
				if( pos < 0 ) { 
					isEnd = true; 
					isNoRow = true; 
				}
				
				while( !isEnd ) {
			        String data = ""; 
			        if( pos >= 0 ) { 
						data = lineStr.substring(prev_pos, pos); 
						prev_pos = pos + 1; 
						pos = lineStr.indexOf("\t", pos+1); 
						if( pos < 0 ) isEnd = true; 
					}
					
			        Object odata = data; 
			        if( data.equals("true") ) {
			        	odata = new Boolean(true); 
			        } else if( data.equals("false") ) {
			        	odata = new Boolean(false); 
			        }
			        fieldValue[cnt] = odata; 
			        
			        cnt++; 
			    }
			    if( !isNoRow ) dtmTable.addRow(fieldValue);
			    lNum++; 
			    lreader.setLineNumber(lNum); 
				lineStr = lreader.readLine(); 
			}
			
			
		} catch( Exception xe ){
			xe.printStackTrace(); 
		}
									 
        
    }// end if jbLoadFile

    if (e.getSource() == jbGetScript){
    	
        genScript();
    }// end if jbGetScript
    
    if (e.getSource() == jbLoadQuery ){
    	
        settingQueryData(); 
    }// end if jbGetScript
    
    }// actionPerformaned
}// ButtonHandler
/**
 * list Select event
 */
class My_tableHandler implements ListSelectionListener{
	
    public void valueChanged (ListSelectionEvent e) {
        int chk;
    
    if(e.getSource() == colTable){
        int selectedIndex = tableList.getSelectedIndex();
        if (selectedIndex < 0) return;
        g_isFromQuery = false; 
        baseTableAlias = ""; 
        
        String tableName = (String) dlmList.getElementAt(selectedIndex);
        //jtTableName.setText(tableName);
        jtTableName.setText(tableName);
        //jtBaseTableName.setText(tableName.substring(0,6));
        //jtBaseTableName.setText(tableName);
        jtBaseTableName.setText(tableName);

        int dtmTableCount =  dtmTable.getRowCount();
        for(int i = 0 ; i < dtmTableCount ; i++) dtmTable.removeRow(0);
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null, rsKey = null;
        try {
            conn = DriverManager.getConnection(jtURL.getText(),jtUser.getText(),new String(jpPassword.getPassword()));
            stmt = conn.createStatement ();
            rs = stmt.executeQuery ("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData ();
            DatabaseMetaData meta = conn.getMetaData();
            Vector keyV = new Vector();
            //rsKey = meta.getPrimaryKeys(null, null, tableName.substring(0,6));
            rsKey = meta.getPrimaryKeys(null, null, tableName.substring(tableName.indexOf(".")+1));
            while ( rsKey.next() ) keyV.addElement(rsKey.getString("COLUMN_NAME").toUpperCase());
            int keyCount = keyV.size();
            int keySeq = 0;
            int keySize = 0;
            String keyValue="", chkKey="";
            String keyType="";
            Boolean keybool = new Boolean(false);
            Boolean datebool = new Boolean(false);

            int columnCount = rsmd.getColumnCount ();
            String[] columnLabels = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnLabels[i-1] = rsmd.getColumnLabel(i);
                
                for(int k = 0 ; k < keyCount; k++) {
                    chkKey = (String) keyV.elementAt(k);
                    if(chkKey.equals(rsmd.getColumnLabel(i).toUpperCase())) { 
                        keySeq++; 
                        keyValue = keySeq+"";
                        keybool = new Boolean(true);
                        break;
                    } else {
                        keyValue = "";
                        keybool = new Boolean(false);
                    } // end if
                } // end for

               keyType = dataType(rsmd.getColumnTypeName(i), rsmd.getPrecision(i), rsmd.getScale(i)) ;
               keySize = rsmd.getPrecision(i);


              if ((keyType.equals("int")) && ( keySize == 8 ) ) {
                        datebool = new Boolean(true);
                    } else {
                        datebool = new Boolean(false);
                    } // end if

                Object fieldValue[] = { keyValue,
                                        keyType,
                                        rsmd.getColumnLabel(i).toUpperCase(), 
                                        keySize+"" , 
                                        rsmd.getColumnLabel(i).toUpperCase(), 
                                        keyValue,
                                        keyValue,
                                        keybool,
                                        keybool,
                                        keybool,
                                        datebool,  
                                        ""};
     
                   dtmTable.addRow(fieldValue);

     

            }

                colCount = rsmd.getColumnCount();
                colLabel = new String[colCount+1];
                colType  = new String[colCount+1];
                colTypeName = new String[colCount+1];
                colPrecision = new int[colCount+1];
                colScale = new int[colCount+1];

            for (int i = 1; i <= colCount; i++) {
                colLabel[i] = rsmd.getColumnLabel(i);
                colType[i]  = dataType(rsmd.getColumnTypeName(i), rsmd.getPrecision(i), rsmd.getScale(i));
                colTypeName[i] = rsmd.getColumnTypeName(i);
                colPrecision[i] = rsmd.getPrecision(i);
                colScale[i] = rsmd.getScale(i);
            }        


        } catch (Exception esql) {
            System.out.println ("ERROR: " + esql.toString());
        } finally {
            try { if (rsKey != null) rsKey.close (); } catch (SQLException ex) { }
            try { if (rs != null) rs.close (); } catch (SQLException ex) { }
            try { if (stmt != null) stmt.close (); } catch (SQLException ex) { }
            try { if (conn != null) conn.close (); } catch (SQLException ex) { }
        } // try-catch-finally

    } // end if itemTable

    } // end TableStatus
} // end Table Handler


public String dataType(String sqlType, int pscale, int cscale){
    String ColumnType = "OtherXXX";

    if(sqlType.equalsIgnoreCase("CHAR") || sqlType.equalsIgnoreCase("DATE") || sqlType.equalsIgnoreCase("VARCHAR") || sqlType.equalsIgnoreCase("VARCHAR2") || sqlType.equalsIgnoreCase("LONG VARCHAR")  || sqlType.equalsIgnoreCase("bpchar")) { 
            ColumnType = "String"; 
    	} else
    if(sqlType.equalsIgnoreCase("DECIMAL") || sqlType.equalsIgnoreCase("NUMERIC") || sqlType.equalsIgnoreCase("NUMBER") || sqlType.equalsIgnoreCase("FLOAT") || sqlType.equalsIgnoreCase("INT")){
        if(cscale>0) {
            ColumnType = "double";
        } else {
            if(pscale > 15){
                ColumnType = "java.math.BigDecimal";
            } else if (9 < pscale && pscale <= 15){
                ColumnType = "long";
            } else {
                ColumnType = "int";
            }
        }
    } else {
        ColumnType = "OtherXXX";
    } // end if
    return ColumnType;
} // end data Type

public String initialCapital(String _word){
    if(_word == null) {
        return "";
    } else if(_word.length()==1){
        return _word.toUpperCase();
    } else {
        _word = _word.substring(0,1).toUpperCase() + _word.substring(1).toLowerCase();
    }
    return _word;
} // end data Type

public String entityInitCap(String _word){
    return  entity+".get"+initialCapital( _word)+"()" ; 
}    


public String entityFormat(String entityStr, String entityType, int entitySize){

   String eformat = "";

    if(entityType.equalsIgnoreCase("String")) {
      eformat = entityStr;      
    } else {
      if ( entitySize == 8 ) {
        eformat = "bu.day3Date("+entityStr+")";     
      } else {
        eformat = "nf.format("+entityStr+")";     
      }
    }

    return eformat;
} // end entityFormat


//검색조건 동적생성을 위한 위치 계산.. 
public int getSearchInt(int base, int sCnt, int row, int col, boolean isSearch ) {
	// 1 : baseStr, 2:searchStr, 3:emptyStr, 4:searchButton, 5:addButton... 
	
	
	int totRow = (base+sCnt)/3 + 1; 
	if( row > totRow ) return 0; 
	
	//System.out.println("base : " + base + "sCnt : " + sCnt + "row : " + row + "col : " + col); 
	
	if( base == 1 && row == 1 && col == 1 ) return 0; 
	if( col < 4 ) {
		if( row == 1 ) {
			if( col > base + sCnt ) { 
				if( base + sCnt < 3 ) {
					if( col == 3 ) return 4; 
					else return 3; 
				}
			}
			else return 2; 
		}		
		else if( (row-1)*3 + col <= base + sCnt ) return 2; 
		else return 3; 
	} else {
		if( base + sCnt < 3 ) return 5; 
		else if( row == 1 ) { 
			if( isSearch ) return 5; 
			else return 4; 
		}
		
		if( row == totRow ) return 5; 
		
		return 3; 		
	}
	return 0; 
		
}

//검색조건 동적 배치를 위한 Source 생성.. ( lsNum : 검색필드 순서 )
public String writeSearchStr( int type, int lsNum ) {
	StringBuffer sbird = new StringBuffer(); 
	if( type == 1 ) {
 sbird.append("	<td class=i align=center width=13% ><%=posName%></td>    \n");
 sbird.append("	<td align=left class=\"c\" width=13% > \n");
 sbird.append("	<input type=text name=posValue size=15 value=\"<%=posValue%>\">  \n	</td>\n\n");
 
	} else if( type == 2 ) { 
 sbird.append("	<td class=i align=center width=13% ><%=" + actFndChk[lsNum].substring(1).toLowerCase() + "HG%>	</td>  \n\n");
 sbird.append("	<td align=left class=\"c\" width=13% >\n"); 
 sbird.append("	<input type=text name=" + actFndChk[lsNum] + " size=15 value=\"<%=" + actFndChk[lsNum] + "%>\">\n	</td>  \n\n");
 
	} else if( type == 3 ) { 
 sbird.append("	<td align=left class=\"c\" width=13% ></td>\n");
 sbird.append("	<td align=left class=\"c\" width=13% ></td>\n");
	} else if( type == 4 ) { 
sbird.append("	<td align=center class=\"c\"  >\n <input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_Inquiry.gif); border:0; width:85;height:22;cursor:hand;\"  onClick='javascript:submit_s2()'> \n</td> \n\n"); 					
	} else if( type == 5 ) {
 sbird.append("	<td  align=right > \n");
 sbird.append("	<input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_Add.gif); border:0; width:85;height:22;cursor:hand;\" onClick='javascript:submit_s1(\"addscreen\")'>  \n");
 		if( editType.equals("true") ) sbird.append("	<input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_Modify.gif); border:0; width:85;height:22;cursor:hand;\" onClick='javascript:check_data(); '>\n"); 
 sbird.append("	\n	</td> \n\n");  
	}


	return new String(sbird); 
}

// Show MessageBox.... 
public void showMessage(String msg) {
	JOptionPane.showMessageDialog(mainFrame, msg); 
}


public void settingQueryData() {
	    
	    this.type="query";
        baseTableName = jtBaseTableName.getText().trim();
		useTableName = jtTableName.getText();
		
		if( baseTableName.equals("") ) {
			showMessage("Please, Input Base Table; �Է��ϼ���.."); 
			jtTableName.setCaretPosition(0);
			return; 
		}
		
		if( baseTableName.indexOf(" ") > 0 ) {
			int nameSize = baseTableName.length(); 
			baseTableAlias = baseTableName.substring(nameSize-1).toUpperCase() + "."; 
			baseTableName = baseTableName.substring(0, nameSize-1).trim(); 
		}
		
        parsingQuery(); 
        
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null, rsKey = null;
        
        try {
            conn = DriverManager.getConnection(jtURL.getText(),jtUser.getText(),new String(jpPassword.getPassword()));
            stmt = conn.createStatement ();
            
            rs = stmt.executeQuery ( g_viewQuery );
            ResultSetMetaData rsmd = rs.getMetaData ();
            DatabaseMetaData meta = conn.getMetaData();
            
            //ridData를 모두 지운다..  
	        for( int i=dtmTable.getRowCount()-1; i>=0; i-- ) {
				dtmTable.removeRow(i); 
			}
			g_isFromQuery = true; 
            
            Vector keyV = new Vector();
            rsKey = meta.getPrimaryKeys(null, null, baseTableName);
            
            while ( rsKey.next() ) { 
            	keyV.addElement(rsKey.getString("COLUMN_NAME").toUpperCase());
            }
            
            
            int keyCount = keyV.size();
            int keySeq = 0;
            int keySize = 0;
            
            String keyValue="", chkKey="";
            String keyType="";
            Boolean keybool = new Boolean(false);
            Boolean datebool = new Boolean(false);

            int columnCount = rsmd.getColumnCount ();
            String[] columnLabels = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnLabels[i-1] = rsmd.getColumnLabel(i);
                
                for(int k = 0 ; k < keyCount; k++) {
                    chkKey = (String) keyV.elementAt(k);
                    if(chkKey.equals(rsmd.getColumnLabel(i).toUpperCase())) { 
                        keySeq++; 
                        keyValue = keySeq+"";
                        keybool = new Boolean(true);
                        break;
                    } else {
                        keyValue = "";
                        keybool = new Boolean(false);
                    } // end if
                } // end for

               keyType = dataType(rsmd.getColumnTypeName(i), rsmd.getPrecision(i), rsmd.getScale(i)) ;
               keySize = rsmd.getPrecision(i);


              if ((keyType.equals("int")) && ( keySize == 8 ) ) {
                        datebool = new Boolean(true);
                    } else {
                        datebool = new Boolean(false);
                    } // end if

                Object fieldValue[] = { keyValue,
                                        keyType,
                                        rsmd.getColumnLabel(i).toUpperCase(), 
                                        keySize+"" , 
                                        rsmd.getColumnLabel(i).toUpperCase(), 
                                        keyValue,
                                        keyValue,
                                        keybool,
                                        keybool,
                                        keybool,
                                        datebool,  
                                        ""};
     
                   dtmTable.addRow(fieldValue);

     

            }

                colCount = rsmd.getColumnCount();
                colLabel = new String[colCount+1];
                colType  = new String[colCount+1];
                colTypeName = new String[colCount+1];
                colPrecision = new int[colCount+1];
                colScale = new int[colCount+1];

            for (int i = 1; i <= colCount; i++) {
                colLabel[i] = rsmd.getColumnLabel(i);
                colType[i]  = dataType(rsmd.getColumnTypeName(i), rsmd.getPrecision(i), rsmd.getScale(i));
                colTypeName[i] = rsmd.getColumnTypeName(i);
                colPrecision[i] = rsmd.getPrecision(i);
                colScale[i] = rsmd.getScale(i);
            }        


        } catch (Exception esql) {
        	showMessage("SQL Exception : Check Query..\n"+ esql.toString());  
            System.out.println( g_viewQuery ); 
            esql.printStackTrace(); 
        } finally {
            try { if (rsKey != null) rsKey.close (); } catch (SQLException ex) { }
            try { if (rs != null) rs.close (); } catch (SQLException ex) { }
            try { if (stmt != null) stmt.close (); } catch (SQLException ex) { }
            try { if (conn != null) conn.close (); } catch (SQLException ex) { }
        } // try-catch-finally
}


public String convertKeyStr( String fndKey , int ind ) {
	String retStr = " \";\n "; 
	for( int i=0; i<dbTypesV.size(); i++ ) {
		DbType dbType = (DbType)dbTypesV.get(i);
		if( i != 0 ) retStr += " 		else "; 
		else retStr += "		"; 
		retStr += " if( " + prjRoot + ".base.BesUtilMrn.getDbType().equals(\"" + dbType.getName() + "\") ) query += \" " + dbType.getKey(ind) + " \";\n"; 
	}
	retStr += "		query += \" "; 
	return retStr; 	
}

public String convertAnyDb( String src ) {
	DbType mssqlDb = (DbType)dbTypesV.get(0); 
	int dbCount = dbTypesV.size(); 
	int keyCount = mssqlDb.getSize(); 
	
		
	for( int i=0; i<keyCount; i++ ) {
		String msKey = mssqlDb.getKey(i); 
		String fndKey = " " + msKey + " "; 
		
		src = CommonUtils.Replace( src, msKey + "(", msKey + " ("); 
		
		if( src.indexOf( fndKey ) > 0 ) { 
			src = CommonUtils.Replace( src, fndKey, convertKeyStr( fndKey, i ) ); 
		}
	}
	
	return src; 
	
}

public String getLineString( String src ) {
//	 Query 로 작성된 Query String을 뿌려주는 String 생성  
	StringBuffer writeBuf = new StringBuffer( src ); 
	StringBuffer outBuf = new StringBuffer(); 
	String lineStr = "";
	boolean isFirst = true; 
	while( !(lineStr = CommonUtils.GetToken(writeBuf, "\n")).equals("") ) {
		
		if( isFirst ) isFirst = false; 
		else outBuf.append("		+ "); 
		
		outBuf.append( "\" " + convertAnyDb(lineStr) + " \"\n" ); 
	}
	outBuf.append("\n"); 
	
	return new String( outBuf ); 
}
 
public void parsingQuery() {
	String query = jtQuery.getText(); 
	String addQuery = jtAddQuery.getText(); 
	
	//query = "aaa\r\n bb	cc\tdd\r"); 
	query = CommonUtils.Replace(query, "\r\n", "\n"); 
	query = CommonUtils.Replace(query, "\r", "\n"); 
	query = CommonUtils.Replace(query, "\n", " \n "); 
	query = CommonUtils.Replace(query, "\t", " "); 
	
//	 대소문자 구분을 어떻게 해야할까??? ==> 대문자로 모두 바꾼다. 'xxx' 제외하고,	
	g_viewQuery = CommonUtils.MakeQueryUpper(query); 
	//g_viewAddQuery = CommonUtils.MakeQueryUpper(addQuery); 
	int fromInd = g_viewQuery.lastIndexOf(" FROM "); 
	if( g_viewQuery.indexOf(" WHERE ", fromInd ) > 0 ) g_isWhereExist = true; 
	
	g_writeQuery = getLineString( g_viewQuery ); 	
	//g_writeAddQuery = getLineString( g_viewAddQuery ); 	
	
	
//	 Parsing 시작.. Field Name과 DB Scheme 메칭.. 
	StringBuffer queryBuf = new StringBuffer( g_viewQuery ); 
	StringBuffer head = new StringBuffer( CommonUtils.GetToken(queryBuf, " FROM ") ); 
	g_countQuery = "\" Select COUNT(*) FROM \" + " + getLineString(queryBuf.toString()) ; 
	String token = ""; 
	g_colNameV = new Vector(); 
	g_colFieldV = new Vector(); 
	
	token = CommonUtils.GetToken(head, " "); // for SELECT skip.. 

	while( !token.trim().equals("") ) {
		String colName = ""; 
		String colField = ""; 
		
		token = parseToken( head ); 
		if( token.equals("") ) break; 

		int pos = -1; 
		if( (pos = token.indexOf(" AS ")) > 0 ) { 
			colName = token.substring(pos+4).trim(); 
			colField = token.substring(0, pos).trim(); 
		} else {
			pos = -1; 
			if( (pos = token.indexOf(".")) > 0 ) {
				colName = token.substring(pos+1).trim(); 
			} else { 
				colName = token; 
			}
			colField = token; 
		}
		g_colNameV.add( colName ); 
		g_colFieldV.add( colField ); 
		
		
	}
	
}

public String parseToken( StringBuffer head ) {
	int level = 0; // '(' 처리되지 않은 갯수 ���� 
	String token = CommonUtils.GetToken(head, ",");

//	 컬럼에 '(' 가 있는경우 ','를 무시하고 하나의 column으로 인식..
	String tempStr = token; 
	int start = CommonUtils.CountChar(tempStr, '('); 
	int end = CommonUtils.CountChar(tempStr, ')'); 
	
	while( start - end > 0 ) {
		token = CommonUtils.GetToken(head, ",").trim();
		tempStr += ", " + token; 

		start = CommonUtils.CountChar(tempStr, '('); 
		end = CommonUtils.CountChar(tempStr, ')'); 
	}

	return tempStr.trim(); 

}

public String getSearchField( String FfieldName ) {
	String colName = FfieldName.toUpperCase(); 
	if( g_colNameV == null ) return FfieldName; 
	for( int i=0; i<g_colNameV.size(); i++ ) {
		if( colName.equals( (String)g_colNameV.get(i) ) ) return (String)g_colFieldV.get(i);
	}
	return FfieldName; 
	
}


//기존 Source Generating 시작........................
public void musikCal(){

    try {

     primeCount = 0;       
     listCount = 0;       
     promptCount = 0;       
     actCount = 0;       




    packageName = jtPackName.getText().toUpperCase();
    allPgmName = jtPgmName.getText();
    pgmTitle = jtPgmName.getText();

   frameType =  cbframeType.getState() +"";
   flowType  =  cbflowType.getState() +"";
   scrollType  =  cbscrollType.getState() +"";
   searchType  =  cbsearchType.getState() +"";
   editType  =  cbeditType.getState() +"";

	baseTableName = jtBaseTableName.getText().trim().substring(jtBaseTableName.getText().trim().indexOf(".")+1);
	useTableName = jtTableName.getText().trim().substring(jtTableName.getText().trim().indexOf(".")+1);
		
	if( baseTableName.equals("") ) {
		showMessage("Please, Input Base Table.."); 
		jtTableName.setCaretPosition(0);
		return; 
	}
		
	baseTableAlias = ""; 
	if( baseTableName.indexOf(" ") > 0 ) {
		int nameSize = baseTableName.length(); 
		baseTableAlias = baseTableName.substring(nameSize-1).toUpperCase() + "."; 
		baseTableName = baseTableName.substring(0, nameSize-1).trim(); 
	}


    fileName = jtTableName.getText();
	fullTableName = jtUser.getText().toUpperCase().trim() + "." + jtTableName.getText();
    entityName = fileName + "Rec";
    dbWrapName = fileName + "DBWrap";
    entity = fileName.toLowerCase();
    dbwrap = entity + "dbw";

	fileName1 = baseTableName.toUpperCase(); 
    entityName1 = fileName1 + "Rec";
    dbWrapName1 = fileName1 + "DBWrap";
    entity1 = baseTableName.toLowerCase();
    dbwrap1 = entity1 + "dbw";

		
    int tempCountx = 0;       
    int tempCounty = 0;       
    int tempValx = 0;       
    int tempValy = 0;       

    int primeCol = 0;       
    int typeCol = 1;       
    int fieldCol = 2;       
    int sizeCol = 3;       
    int titleCol = 4;       
    int listCol = 5;       
    int actCol = 6;       
    int promptCol = 6;       
    int actDspCol = 7;       
    int actReqCol = 8;       
    int actFndCol = 9;       
    int actDatCol = 10;       
    int actPmtCol = 11;       

    int myRows = tableDetail.getRowCount();
    int myCols = tableDetail.getColumnCount();

	trowCount = myRows; 
	
    for( int i=0 ; i < myRows ; i++ ) {
       if ( !tableDetail.getValueAt(i,primeCol).equals("")) {   primeCount ++;    }
       if ( !tableDetail.getValueAt(i,listCol).equals(""))  {   listCount ++;     }
       if ( !tableDetail.getValueAt(i,promptCol).equals("")){   promptCount ++;   }
       if ( !tableDetail.getValueAt(i,actCol).equals(""))   {   actCount ++;      }
    }

    int[] primeTemp = new int[primeCount+1];
    int[] listTemp = new int[listCount+1];
    int[] promptTemp = new int[promptCount+1];
    int[] actTemp = new int[actCount+1];

    tempCountx = 0;       
    tempCounty = 0;       
    tempValx = 0;       
    tempValy = 0;       


//  *************밑에꺼 단순무식계산 *******************//

    for( int i=0 ; i < myRows ; i++ ) {
      if ( !tableDetail.getValueAt(i,primeCol).equals("")) { 
         tempCountx++; 
         tempValx = Integer.parseInt((String)tableDetail.getValueAt(i,primeCol));

         tempCounty = 0;       
         for( int j=0 ; j < myRows ; j++ ) {
            if ( !tableDetail.getValueAt(j,primeCol).equals("")) { 
               tempValy = Integer.parseInt((String)tableDetail.getValueAt(j,primeCol));

                if ((tempValx > tempValy) || (( tempValx == tempValy ) && ( i >= j ))) {
                   tempCounty++;
                }
             }
          }
         primeTemp[tempCountx]  = tempCounty ;
      }                      
    }


    tempCountx = 0;       
    tempCounty = 0;       
    tempValx = 0;       
    tempValy = 0;       

    for( int i=0 ; i < myRows ; i++ ) {
      if ( !tableDetail.getValueAt(i,listCol).equals("")) { 
         tempCountx++; 
         tempValx = Integer.parseInt((String)tableDetail.getValueAt(i,listCol));

         tempCounty = 0;       
         for( int j=0 ; j < myRows ; j++ ) {
            if ( !tableDetail.getValueAt(j,listCol).equals("")) { 
               tempValy = Integer.parseInt((String)tableDetail.getValueAt(j,listCol));

                if ((tempValx > tempValy) || (( tempValx == tempValy ) && ( i >= j ))) {
                   tempCounty++;
                }
             }
          }
         listTemp[tempCountx]  = tempCounty ;
      }                      
    }

    tempCountx = 0;       
    tempCounty = 0;       
    tempValx = 0;       
    tempValy = 0;       

    for( int i=0 ; i < myRows ; i++ ) {
      if ( !tableDetail.getValueAt(i,promptCol).equals("")) { 
         tempCountx++; 
         tempValx = Integer.parseInt((String)tableDetail.getValueAt(i,promptCol));

         tempCounty = 0;       
         for( int j=0 ; j < myRows ; j++ ) {
            if ( !tableDetail.getValueAt(j,promptCol).equals("")) { 
               tempValy = Integer.parseInt((String)tableDetail.getValueAt(j,promptCol));

                if ((tempValx > tempValy) || (( tempValx == tempValy ) && ( i >= j ))) {
                   tempCounty++;
                }
             }
          }
         promptTemp[tempCountx]  = tempCounty ;
      }                      
    }


    tempCountx = 0;       
    tempCounty = 0;       
    tempValx = 0;       
    tempValy = 0;       

    for( int i=0 ; i < myRows ; i++ ) {
      if ( !tableDetail.getValueAt(i,actCol).equals("")) { 
         tempCountx++; 
         tempValx = Integer.parseInt((String)tableDetail.getValueAt(i,actCol));

         tempCounty = 0;       
         for( int j=0 ; j < myRows ; j++ ) {
            if ( !tableDetail.getValueAt(j,actCol).equals("")) { 
               tempValy = Integer.parseInt((String)tableDetail.getValueAt(j,actCol));

                if ((tempValx > tempValy) || (( tempValx == tempValy ) && ( i >= j ))) {
                   tempCounty++;
                }
             }
          }
         actTemp[tempCountx]  = tempCounty ;
      }                      
    }

//  *************위에거 단순무식 계산 *****************************//



//  *************여기서부터 변수 계산 시작 *******************//


          primeKey = new String[primeCount+1];
          getprimeKey = new String[primeCount+1];
          primeKeyType = new String[primeCount+1];
          primeKeySize = new int[primeCount+1];

          listKey = new String[listCount+1];
          getlistKey = new String[listCount+1];
          listKeyType = new String[listCount+1];
          listKeySize = new int[listCount+1];
          listTitle = new String[listCount+1];
          viewTitle = new String[myRows+1];
          viewKey = new String[myRows+1];

          promptKey = new String[promptCount+1];
          getpromptKey = new String[promptCount+1];
          promptKeyType = new String[promptCount+1];
          promptKeySize = new int[promptCount+1];

          actKey = new String[actCount+1];
          getactKey = new String[actCount+1];
          actKeyType = new String[actCount+1];
          actKeySize = new int[actCount+1];

          actTitle = new String[actCount+1];
          actReqChk = new String[actCount+1];
          actDspChk = new String[actCount+1];
          actDatChk = new String[actCount+1];
          actFormat = new String[actCount+1];
          actPmtPgm = new String[actCount+1];
          
          actFndChk = new String[getFindCnt()];


          //***************primeKey 계산***************************//
    tempCountx = 0;   
    primeKeyOrder= new String[primeCount+1];

    for( int i=0 ; i < myRows ; i++ ) {
       if ( !tableDetail.getValueAt(i,primeCol).equals("")) {  
        tempCountx ++; 
        tableDetail.setValueAt(primeTemp[tempCountx]+"",i,primeCol)    ;
        primeKey[primeTemp[tempCountx]] = ((String) tableDetail.getValueAt(i,fieldCol)).toLowerCase();
        getprimeKey[primeTemp[tempCountx]] =entityInitCap((String) tableDetail.getValueAt(i,fieldCol));
        primeKeyType[primeTemp[tempCountx]] =(String) tableDetail.getValueAt(i,typeCol);
        primeKeySize[primeTemp[tempCountx]] =Integer.parseInt((String) tableDetail.getValueAt(i,sizeCol)) ;
        primeKeyOrder[primeTemp[tempCountx]] = fileName.toLowerCase() + ".get" + initialCapital(((String) tableDetail.getValueAt(i,fieldCol)).toLowerCase());
       }
    }

    //***************listKey 계산***************************//
    tempCountx = 0;       
    for( int i=0 ; i < myRows ; i++ ) {
       if ( !tableDetail.getValueAt(i,listCol).equals("")) {  
        tempCountx ++; 
        tableDetail.setValueAt(listTemp[tempCountx]+"",i,listCol)    ;
        listKey[listTemp[tempCountx]] =((String) tableDetail.getValueAt(i,fieldCol)).toLowerCase();
        getlistKey[listTemp[tempCountx]] =entityInitCap((String) tableDetail.getValueAt(i,fieldCol));
        listKeyType[listTemp[tempCountx]] =(String) tableDetail.getValueAt(i,typeCol);
        listKeySize[listTemp[tempCountx]] =Integer.parseInt((String) tableDetail.getValueAt(i,sizeCol)) ;
        listTitle[listTemp[tempCountx]] =(String) tableDetail.getValueAt(i,titleCol);
       }
       colType[i+1] =(String) tableDetail.getValueAt(i,typeCol);
       viewTitle[i] =(String) tableDetail.getValueAt(i,titleCol);
       viewKey[i] =((String) tableDetail.getValueAt(i,fieldCol)).toLowerCase();
    }

  //  //***************promptKey 계산***************************//
    tempCountx = 0;       
    for( int i=0 ; i < myRows ; i++ ) {
       if ( !tableDetail.getValueAt(i,promptCol).equals("")) {  
        tempCountx ++; 
//      tableDetail.setValueAt(promptTemp[tempCountx]+"",i,promptCol)    ;
        promptKey[promptTemp[tempCountx]] =((String) tableDetail.getValueAt(i,fieldCol)).toLowerCase();
        getpromptKey[promptTemp[tempCountx]] =entityInitCap((String) tableDetail.getValueAt(i,fieldCol));
        promptKeyType[promptTemp[tempCountx]] =(String) tableDetail.getValueAt(i,typeCol);
        promptKeySize[promptTemp[tempCountx]] =Integer.parseInt((String) tableDetail.getValueAt(i,sizeCol)) ;
       }
    }

    //***************actKey 계산***************************//
    tempCountx = 0;       
    int tempFindCnt = 0; 
    
    for( int i=0 ; i < myRows ; i++ ) {
       
        if( ((Boolean) tableDetail.getValueAt(i,actFndCol)).toString().equals("true") ) {
        	actFndChk[tempFindCnt++] = "F" + (String)tableDetail.getValueAt(i,COL_FIELD_KEY);
        	
        }
        
       if ( !tableDetail.getValueAt(i,actCol).equals("")) {  
        tempCountx ++; 
        tableDetail.setValueAt(actTemp[tempCountx]+"",i,actCol)    ;
        actKey[actTemp[tempCountx]] =((String) tableDetail.getValueAt(i,fieldCol)).toLowerCase();
        getactKey[actTemp[tempCountx]] =entityInitCap((String) tableDetail.getValueAt(i,fieldCol));
        actKeyType[actTemp[tempCountx]] =(String) tableDetail.getValueAt(i,typeCol);
        actKeySize[actTemp[tempCountx]] =Integer.parseInt((String) tableDetail.getValueAt(i,sizeCol)) ;

        actTitle[actTemp[tempCountx]] =(String) tableDetail.getValueAt(i,titleCol);
        actReqChk[actTemp[tempCountx]] = ((Boolean) tableDetail.getValueAt(i,actReqCol)).toString();
        actDspChk[actTemp[tempCountx]] = ((Boolean) tableDetail.getValueAt(i,actDspCol)).toString();
        
        
                
        actDatChk[actTemp[tempCountx]] = ((Boolean) tableDetail.getValueAt(i,actDatCol)).toString();
        actPmtPgm[actTemp[tempCountx]] =(String) tableDetail.getValueAt(i,actPmtCol);
        actFormat[actTemp[tempCountx]] =entityFormat(getactKey[actTemp[tempCountx]],actKeyType[actTemp[tempCountx]],actKeySize[actTemp[tempCountx]]);
       } else {
        tableDetail.setValueAt(new Boolean(false),i,actDspCol)    ;
        tableDetail.setValueAt(new Boolean(false),i,actReqCol)    ;
        tableDetail.setValueAt(new Boolean(false),i,actFndCol)    ;
        tableDetail.setValueAt(new Boolean(false),i,actDatCol)    ;
        tableDetail.setValueAt("",i,actPmtCol)    ;
       }
    }

//  *************여기서까지 변수 계산 끝 *******************//


    } catch(Exception e){
        System.out.println("error :: " + e.toString());
        e.printStackTrace(); 
    } // try-catch-finally

}// musikCal()

public int getFindCnt() {
	int cnt = 0; 
	for( int i=0; i<tableDetail.getRowCount(); i++ ) {
		String chkFind = ((Boolean)tableDetail.getValueAt(i,COL_IS_SEARCH)).toString().toLowerCase();
		if( chkFind.equals( "true" ) ) cnt++; 
	}
	return cnt; 
}

public String getTableValue( int row, int col ) {
	String retStr = ""; 
	try {
		retStr = (String)tableDetail.getValueAt(row,col);
	} catch(Exception re) {
		retStr = ((Boolean)tableDetail.getValueAt(row,col)).toString();
	}
	return retStr; 
}

public String getTableValue( String colKey, int col ) {
	for( int i=0; i<tableDetail.getRowCount(); i++ ) {
		String tcolKey = ((String) tableDetail.getValueAt(i,COL_FIELD_KEY)).toLowerCase();
		if( tcolKey.equals( colKey.toLowerCase() ) ) return getTableValue(i, col); 
	}
	return ""; 
}

public int getCommonCnt() {
	int cnt = 0; 
	for (int i = 1; i <= actCount; ++i) {
		if( !actPmtPgm[i].equals("") && actPmtPgm[i].length() == 6 ) cnt++; 
	}
	return cnt; 
}


public void genScript() {

	Connection connection = null;
    Statement statement = null;
    ResultSet resultset = null;
    ResultSet resultset1 = null;
    
try {

	connection = DriverManager.getConnection(jtURL.getText(), jtUser.getText(), new String(jpPassword.getPassword()));
    statement = connection.createStatement();
    
    String index_jsp="";
    String include_jsp="";
    String list1_jsp="";
    String data1_jsp="";
    String add1_jsp="";
    String update1_jsp="";
    String detail1_jsp="";
    String besScript_js="";
    String action_java="";
    String dbrec_java="";
    String dbwrapbes_java="";
    String dbwrap_java="";

    String display_txt="";

    StringBuffer sbird = new StringBuffer(); 
    StringBuffer promptBuff = new StringBuffer(); 
    StringBuffer promptBuff_d = new StringBuffer(); 

    String keydupchk = "";     
    String inclasstype ="";
    String input_err = "n";
    
//  **********변수 계산 시작 ******************************************************//

    musikCal(); // 무식하게 계산한부분으로 보면 죽음-_-;;
    
    String tableName = fileName;
    String ColumnType = "";
    
    DatabaseMetaData databasemetadata = connection.getMetaData();
    Vector vector = new Vector();
    for(
            resultset1 = databasemetadata.getPrimaryKeys(null, null, tableName); 
    		resultset1.next();
    		vector.addElement(resultset1.getString("COLUMN_NAME").toUpperCase())
    ){;}
    //vector = keyVector;
    int ivv = vector.size();
    String as[] = new String[ivv + 1];
    String as1[] = new String[ivv + 1];
    String as2[] = new String[ivv + 1];
    
    for(int j = 1; j <= ivv; j++)
    {
        as[j] = (String)vector.elementAt(j - 1);
        as[j] = as[j].toLowerCase();
        as1[j] = tableName.toLowerCase() + ".get" + initialCapital(as[j]);
        System.out.println(as1[j]);
    }
    
    String tmp = "";
    for(int ix=0; ix<as1.length; ix++){
    	
    }
    
    resultset = statement.executeQuery("SELECT * FROM " + tableName);
    ResultSetMetaData resultsetmetadata = resultset.getMetaData();
    int k = resultsetmetadata.getColumnCount();
    
    String[] ids = TimeZone.getAvailableIDs(+9 * 60 * 60 * 1000);
    SimpleTimeZone pdt = new SimpleTimeZone(+9 * 60 * 60 * 1000, ids[0]);
    Calendar date = new GregorianCalendar(pdt);
    String nowDate = date.get(Calendar.YEAR)+"-"+(date.get(Calendar.MONTH)+1)+"-"+date.get(Calendar.DAY_OF_MONTH);


//**********���� ��� �� ******************************************************//

	String whereInsertStr = " query += \" where \"; \n"; 
	String andInsertStr = " query += \" and \"; \n"; 
   


	if( !primeKey[1].equals("facid") )	{ 
		whereInsertStr = " if( !whereOption.equals(\"\")) query += \" where \";\n "; 
		andInsertStr = " if( !whereOption.equals(\"\")) query += \" and \"; \n"; 
	}

	
//************ include.jsp ************************************

if( packageUsed.equals("YES") ) sbird.append("<%@ page import=\"" + prjRoot + "." + prjMst + "." + packageName + ".*\"%>\n");
    
 if( !g_isPrompt ) {
 sbird.append("    <% \nString PGMDIR    = HTTPSERVER + \"/webpages/"+packageName+"/"+allPgmName+"/\" ;  \n");
 sbird.append("    String homeJsp   = PGMDIR + \"index.jsp\" ;  \n");
 sbird.append("    String addJsp    = PGMDIR + \"add1.jsp\" ;  \n");
 sbird.append("    String updateJsp = PGMDIR + \"update1.jsp\" ;  \n");
 sbird.append("    String listJsp   = PGMDIR + \"list1.jsp\" ;  \n\n");

 if( scrollType.equals("true") ) { 
sbird.append("    String dataJsp   = PGMDIR + \"data1.jsp\" ;  \n");
	String strSize = String.valueOf((int)700/(listCount+1)); 
sbird.append("    int colSize0 = 40 ;  \n");	
 	for( int i=1; i<listCount; i++ ) {
sbird.append("    int colSize" + i + "   = " + strSize + " ;  \n");
	}
 }

 sbird.append("    String actionJava= SERVLETSERVER + \"" + prjRoot + "."+packageName+"."+allPgmName+"\" ;  \n");
 
 sbird.append("  \n");
 sbird.append("    String facid = ui.getFacid();  \n");
 sbird.append("    String usrid = ui.getUsrid();  \n");
 sbird.append("    String house = ui.getHouse();  \n");
        for (int i = 1; i <= primeCount; ++i) {
         if ( !primeKey[i].equals("facid")) {
 sbird.append("    "+primeKeyType[i]+" "+primeKey[i]+" = box.get"+initialCapital(primeKeyType[i])+"(\"" +primeKey[i]+"\"); \n");
         }
        }
 sbird.append("  \n");
     
 sbird.append("    String posName = request.getParameter(\"posName\");   \n");
 sbird.append("    String posField = request.getParameter(\"posField\");   \n");
 sbird.append("    String posValue = request.getParameter(\"posValue\");   \n");
 sbird.append("    String posOrder = request.getParameter(\"posOrder\");   \n");
 sbird.append("    String current_Page = request.getParameter(\"current_Page\");   \n");
 sbird.append("   String errMsg  = BesUtil.chNull(request.getParameter(\"errMsg\"));   \n");
 sbird.append(" \n");
 
        for (int i = 0; i < trowCount; i++ ) {
 sbird.append("    String "+viewKey[i]+"HG = BesDBUtil.getElementName(\""+viewTitle[i]+"\",ui);\n");
    	}
 sbird.append("    String callParameter = \"\";\n");    	
    	for (int i = 0; i < actFndChk.length; i++ ) {
 sbird.append("    String "+actFndChk[i] + " = BesUtil.chNull(request.getParameter(\"" + actFndChk[i] + "\"));   \n");
 sbird.append("    callParameter += \"&" + actFndChk[i] + "=\" + " + actFndChk[i] + ";\n"); 
    	}
 sbird.append("    String parm1 = BesUtil.chNull(request.getParameter(\"parm1\"));   \n");
 sbird.append("    callParameter += \"&parm1=\" + parm1;\n"); 
    
 sbird.append(" \n");
 sbird.append("    if(posName == null) { posName = "+listTitle[1].toLowerCase()+"HG; } \n");
 sbird.append("    if(posField == null) { posField = \""+baseTableAlias+listKey[1]+"\"; } \n");
 sbird.append("    if(posValue == null) { posValue = \"\"; } \n");
 sbird.append("    if(posOrder == null) { posOrder = \"Asc\"; } \n");
 sbird.append("    if(current_Page == null) { current_Page = \"1\"; } \n");
     //} // frameType if end
 sbird.append("  \n");
 sbird.append("%>  \n");

    include_jsp = sbird.toString();
    sbird = new StringBuffer(); // ����� bird�� ���y��....


// Prompt......... 
} else {
 promptBuff.append("    <% \nString listJsp    = HTTPSERVER + \"/webpages/common/prompt/" + allPgmName + ".jsp\";  \n");
 
 if( scrollType.equals("true") ) { 
promptBuff.append("    String dataJsp   = PGMDIR + \"data1.jsp\" ;  \n");
	String strSize = String.valueOf((int)700/(listCount+1)); 
promptBuff.append("    int colSize0 = 40 ;  \n");	
 	for( int i=1; i<listCount; i++ ) {
promptBuff.append("    int colSize" + i + "   = " + strSize + " ;  \n");
	}
 }

 promptBuff.append("  \n");
 promptBuff.append("    String facid = ui.getFacid();  \n");
 promptBuff.append("    String usrid = ui.getUsrid();  \n");
 promptBuff.append("    String house = ui.getHouse();  \n");
        for (int i = 1; i <= primeCount; ++i) {
         if ( !primeKey[i].equals("facid")) {
 promptBuff.append("    "+primeKeyType[i]+" "+primeKey[i]+" = box.get"+initialCapital(primeKeyType[i])+"(\"" +primeKey[i]+"\"); \n");
         }
        }
 promptBuff.append("  \n");
     
 promptBuff.append("    String posName = request.getParameter(\"posName\");   \n");
 promptBuff.append("    String posField = request.getParameter(\"posField\");   \n");
 promptBuff.append("    String posValue = request.getParameter(\"posValue\");   \n");
 promptBuff.append("    String posOrder = request.getParameter(\"posOrder\");   \n");
 promptBuff.append("    String current_Page = request.getParameter(\"current_Page\");   \n");
 
 sbird.append("   String errMsg  = BesUtil.chNull(request.getParameter(\"errMsg\"));   \n");
 
 promptBuff.append(" \n");
        for (int i = 0; i < trowCount; i++ ) {
 promptBuff.append("    String "+viewKey[i]+"HG = BesDBUtil.getElementName(\""+viewTitle[i]+"\",ui);\n");
    	}
 promptBuff.append("    String callParameter = \"\";\n");    	
    	for (int i = 0; i < actFndChk.length; i++ ) {
 promptBuff.append("    String "+actFndChk[i] + " = BesUtil.chNull(request.getParameter(\"" + actFndChk[i] + "\"));   \n");
 promptBuff.append("    callParameter += \"&" + actFndChk[i] + "=\" + " + actFndChk[i] + ";\n"); 
    	}
    
 promptBuff.append(" \n");
 promptBuff.append("    if(posName == null) { posName = "+listTitle[1].toLowerCase()+"HG; } \n");
 promptBuff.append("    if(posField == null) { posField = \""+baseTableAlias+listKey[1]+"\"; } \n");
 promptBuff.append("    if(posValue == null) { posValue = \"\"; } \n");
 promptBuff.append("    if(posOrder == null) { posOrder = \"Asc\"; } \n");
 promptBuff.append("    if(current_Page == null) { current_Page = \"1\"; } \n");
     //} // frameType if end
 promptBuff.append("  \n");
 promptBuff.append("%>  \n");

}


//************ index ����***********************************
if( !g_isPrompt ) {
     if ( frameType.equals("false")) {
 sbird.append("<%@ page contentType=\"text/html;charset=utf-8\" %>  \n");
 sbird.append("<%@ include file = \"../../common/include.jsp\" %> \n");
 sbird.append("<%@ include file = \"include.jsp\" %> \n");
 sbird.append("<% \n");
 sbird.append("String screen = request.getParameter(\"screen\"); \n");
 sbird.append("if(screen==null || screen.trim().equals(\"\") ){ screen = \"defaultscreen\"; } \n");
 sbird.append(" \n");
 sbird.append("if(screen.equals(\"defaultscreen\")){ \n");
 sbird.append("%> \n");
 sbird.append("<html> \n");
 sbird.append("<head> \n");
 sbird.append("<title>" + pgmTitle + "</title> \n");
 sbird.append("</head> \n");
 sbird.append("<FRAMESET border=0 rows=\"22,*\" frameBorder=0 frameSpacing=0 >   \n");
 sbird.append("  <frame name=\"bestitle\" scrolling=\"NO\" noresize src=\"../../common/prg_title.jsp\" > \n");
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlLst src=\"list1.jsp?errMsg=<%=errMsg%>&parm1=<%=parm1%>&posValue=<%=" + listKey[1] + "%>\">   \n");
 sbird.append("</FRAMESET>  \n");
 sbird.append("</html> \n");
 sbird.append("<% \n");
 sbird.append("  }else if(screen.equals(\"display\")){ \n");
 sbird.append("%> \n");
 sbird.append("<html> \n");
 sbird.append("<head> \n");
 sbird.append("<title>" + pgmTitle + "</title> \n");
 sbird.append("</head> \n");
 sbird.append(" \n");
 sbird.append("<FRAMESET border=0 rows=\"22,*\" frameBorder=0 frameSpacing=0 >   \n");
 sbird.append("  <frame name=\"bestitle\" scrolling=\"NO\" noresize src=\"../../common/prg_title.jsp\" > \n");
     
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlLst src=\"update1.jsp?"+ primeKey[1] + "=<%="+ primeKey[1] + "%>&parm1=<%=parm1%>");
     
        for (int i = 2; i <= primeCount; ++i) {
 sbird.append("&"+ primeKey[i] + "=<%="+ primeKey[i] + "%>");
        } // end for
 sbird.append("\" noresize > \n");
 sbird.append("</FRAMESET>  \n");
 sbird.append("</html> \n");
 sbird.append("<% \n");
 sbird.append("  }else if(screen.equals(\"addscreen\")){ \n");
 sbird.append("%> \n");
 sbird.append("<html> \n");
 sbird.append("<head> \n");
 sbird.append("<title>" + pgmTitle + "</title> \n");
 sbird.append("</head> \n");
 sbird.append(" \n");
 sbird.append("<FRAMESET border=0 rows=\"22,*\" frameBorder=0 frameSpacing=0 >   \n");
 sbird.append("  <frame name=\"bestitle\" scrolling=\"NO\" noresize src=\"../../common/prg_title.jsp\" > \n");
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlLst src=\"add1.jsp?parm1=<%=parm1%>\" noresize > \n");
 sbird.append("</FRAMESET>  \n");
 sbird.append("</html> \n");
 sbird.append("<%}%> \n");

     } else if( frameType.equals("true")) {

 sbird.append("<%@ page contentType=\"text/html;charset=utf-8\" %>  \n");
 sbird.append("<%@ include file = \"../../common/include.jsp\" %> \n");
 sbird.append("<%@ include file = \"include.jsp\" %> \n");
 sbird.append("<% \n");
 sbird.append("String screen = request.getParameter(\"screen\"); \n");
 sbird.append("if(screen==null||screen.trim().equals(\"\")){ screen = \"defaultscreen\"; } \n");
 sbird.append(" \n");
 sbird.append("if(screen.equals(\"defaultscreen\")){ \n");
 sbird.append("%> \n");
 sbird.append("<html> \n");
 sbird.append("<head> \n");
 sbird.append("<title>" + pgmTitle + "</title> \n");
 sbird.append("</head> \n");
 sbird.append("<FRAMESET border=0 rows=\"22,340,*\" frameBorder=0 frameSpacing=0 >   \n");
 sbird.append("  <frame name=\"bestitle\" scrolling=\"NO\" noresize src=\"../../common/prg_title.jsp\" > \n");
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlLst src=\"list1.jsp?errMsg=<%=errMsg%>&parm1=<%=parm1%>&posName=<%=posName%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&current_Page=<%=current_Page%>&"+ primeKey[1] + "=<%="+ primeKey[1] + "%>");
        for (int i = 2; i <= primeCount; ++i) {
 sbird.append("&"+ primeKey[i] + "=<%="+ primeKey[i] + "%>");
        } // end for
 sbird.append("\" noresize > \n");
 
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlCtl src=\"add1.jsp?posName=<%=posName%>&parm1=<%=parm1%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&current_Page=<%=current_Page%>\" noresize > \n");
 sbird.append("</FRAMESET>  \n");
 sbird.append("</html> \n");
 sbird.append("<% \n");
 sbird.append("  }else if(screen.equals(\"display\")){ \n");
 sbird.append("%> \n");
 sbird.append("<html> \n");
 sbird.append("<head> \n");
 sbird.append("<title>" + pgmTitle + "</title> \n");
 sbird.append("</head> \n");
 sbird.append(" \n");
 sbird.append("<FRAMESET border=0 rows=\"22,340,*\" frameBorder=0 frameSpacing=0 >   \n");
 sbird.append("  <frame name=\"bestitle\" scrolling=\"NO\" noresize src=\"../../common/prg_title.jsp\" > \n");
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlLst src=\"list1.jsp?errMsg=<%=errMsg%>&parm1=<%=parm1%>&posName=<%=posName%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&current_Page=<%=current_Page%>&"+ primeKey[1] + "=<%="+ primeKey[1] + "%>");
        for (int i = 2; i <= primeCount; ++i) {
 sbird.append("&"+ primeKey[i] + "=<%="+ primeKey[i] + "%>");
        } // end for
 sbird.append("\" noresize > \n");
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlCtl src=\"update1.jsp?posName=<%=posName%>&parm1=<%=parm1%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&current_Page=<%=current_Page%>&"+ primeKey[1] + "=<%="+ primeKey[1] + "%>");
     
        for (int i = 2; i <= primeCount; ++i) {
 sbird.append("&"+ primeKey[i] + "=<%="+ primeKey[i] + "%>");
        } // end for
 sbird.append("\" noresize > \n");
 sbird.append("</FRAMESET>  \n");
 sbird.append("</html> \n");
 sbird.append("<% \n");
 sbird.append("  }else if(screen.equals(\"addscreen\")){ \n");
 sbird.append("%> \n");
 sbird.append("<html> \n");
 sbird.append("<head> \n");
 sbird.append("<title>" + pgmTitle + "</title> \n");
 sbird.append("</head> \n");
 sbird.append(" \n");
 sbird.append("<FRAMESET border=0 rows=\"22,340,*\" frameBorder=0 frameSpacing=0 >   \n");
 sbird.append("  <frame name=\"bestitle\" scrolling=\"NO\" noresize src=\"../../common/prg_title.jsp\" > \n");
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlLst src=\"list1.jsp?errMsg=<%=errMsg%>&parm1=<%=parm1%>&posName=<%=posName%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&current_Page=<%=current_Page%>&"+ primeKey[1] + "=<%="+ primeKey[1] + "%>");
        for (int i = 2; i <= primeCount; ++i) {
 sbird.append("&"+ primeKey[i] + "=<%="+ primeKey[i] + "%>");
        } // end for
 sbird.append("\" noresize > \n");
 sbird.append("  <frame border=0 scrolling = \"auto\" name=dtlCtl src=\"add1.jsp?posName=<%=posName%>&parm1=<%=parm1%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&current_Page=<%=current_Page%>\" noresize > \n");
 sbird.append("</FRAMESET>  \n");
 sbird.append("</html> \n");
 sbird.append("<%}%> \n");

     } // frame type if

    index_jsp = sbird.toString();
    sbird = new StringBuffer(); // ����� bird�� ���y��....
} // end of !prompt


//************ besScript.jsp ����***********************************
 sbird.append("<script language=\"javascript\">\n");
 if( editType.equals("true") ) {
 	sbird.append("function check_change(obj) {\n");
	 sbird.append("	obj.checked = true;\n"); 
	 sbird.append("}\n"); 
	 
	 
 	if( scrollType.equals("true") ) { 
	 sbird.append("function check_data() {\n");
	 sbird.append("	var frm;\n");
	 sbird.append("	frm = document.dataframe.besform;\n");	
	 
	 sbird.append("	\n"); 
	 sbird.append("	if( !document.dataframe.count_check_sub() ) { \n"); 
	 sbird.append("		alert(\"<%= BesDBUtil.getMessage(\"bsc180\",ui) %>\"); \n"); 

	 sbird.append("		return; \n"); 
	 sbird.append("	}\n"); 
	 sbird.append("	\n"); 
	 
	 sbird.append("	frm.cmd.value = \"update1_list\";\n"); 
	 sbird.append("	frm.submit();\n"); 
	 sbird.append("}\n");  		
	 
sbird.append("function check_all() {\n");  		
sbird.append("\n");  		
sbird.append("	var frm;\n");  		
sbird.append("	frm = document.dataframe.besform;\n");  		
sbird.append("\n");  		
sbird.append("	document.dataframe.check_all_sub(document.besform.checkAll.checked); \n");  		
sbird.append("\n");  		
sbird.append("}\n");  		

sbird.append("function check_all_sub(ch) {\n");  		
sbird.append("\n");  		
sbird.append("	var frm;\n");  		
sbird.append("	frm = document.besform;\n");  		
sbird.append("	\n");  		
sbird.append("	var rowSize = frm.listCount.value; \n");  		
sbird.append("\n");  		
sbird.append("	for( var i=1; i<=rowSize; i++ ) {\n");  		
sbird.append("		var checkName = \"check__\" + i; \n");  		
sbird.append("		var obj = document.getElementById(checkName);		\n");  		
sbird.append("		\n");  		
sbird.append("		if( ch )\n");  		
sbird.append("			obj.checked = true; \n");  		
sbird.append("		else obj.checked = false; \n");  		
sbird.append("	}\n");  		
sbird.append(" }\n");  	

sbird.append("\n");  	
sbird.append("function count_check_sub() {\n");  	
sbird.append("	var frm;\n");  	
sbird.append("	frm = document.besform;\n");  	
sbird.append("	var rowSize = frm.listCount.value; \n");  	
sbird.append("	for( var i=1; i<=rowSize; i++ ) {\n");  	
sbird.append("		var checkName = \"check__\" + i; \n");  	
sbird.append("		var obj = document.getElementById(checkName);		\n");  	
sbird.append("		if( obj.checked ) return true; \n");  	
sbird.append("	}\n");  	
sbird.append("	return false; \n");  	
sbird.append("}\n");  	
sbird.append("\n");  	


 	} else {
 		
sbird.append("	\n"); 
sbird.append("function check_data() {\n");
sbird.append("	var frm;\n");
sbird.append("	frm = document.besform;\n");	

sbird.append("	\n"); 
sbird.append("	if( !count_check() ) { \n"); 
sbird.append("		alert('<%=BesDBUtil.getMessage(\"bsc180\", ui)%>'); \n"); 
sbird.append("		return; \n"); 
sbird.append("	}\n"); 
sbird.append("	\n"); 

sbird.append("	frm.cmd.value = \"update1_list\";\n"); 
sbird.append("	frm.submit();\n"); 
sbird.append("}\n"); 


sbird.append("function check_all() {\n"); 
sbird.append("\n"); 
sbird.append("	var frm;\n"); 
sbird.append("	frm = document.besform;\n");	
sbird.append("	var rowSize = frm.listCount.value; \n"); 
sbird.append("	for( var i=1; i<=rowSize; i++ ) {\n"); 
sbird.append("		var checkName = \"check__\" + i; \n"); 
sbird.append("		var obj = document.getElementById(checkName);		\n"); 
sbird.append("		\n"); 
sbird.append("		if( frm.checkAll.checked )\n"); 
sbird.append("			obj.checked = true; \n"); 
sbird.append("		else obj.checked = false; \n"); 
sbird.append("	}\n"); 
sbird.append(" }\n"); 

sbird.append("\n");  	
sbird.append("function count_check() {\n");  	
sbird.append("	var frm;\n");  	
sbird.append("	frm = document.besform;\n");  	
sbird.append("	var rowSize = frm.listCount.value; \n");  	
sbird.append("	for( var i=1; i<=rowSize; i++ ) {\n");  	
sbird.append("		var checkName = \"check__\" + i; \n");  	
sbird.append("		var obj = document.getElementById(checkName);		\n");  	
sbird.append("		if( obj.checked ) return true; \n");  	
sbird.append("	}\n");  	
sbird.append("	return false; \n");  	
sbird.append("}\n");  	
sbird.append("\n");  	

	}
 
 }
 
 
 sbird.append("  \n");
 
 sbird.append("function submit_p1(page, posField, posValue, "); 
 //for( int j=0; j<actFndChk.length; j++) sbird.append(actFndChk[j] + ", "); 
 sbird.append(" posName)  \n");
 sbird.append("{  \n");
 sbird.append("   var pagechk = page;\n");
 sbird.append("   if ( pagechk == '0' ) {\n");
 sbird.append("    var orders = document.besform.posOrder.value ;\n");
 sbird.append("     if (( orders == 'Asc' )&&( besform.posField.value == posField )) { document.besform.posOrder.value='Desc'; }\n");
 sbird.append("     if ( orders == 'Desc' ) { document.besform.posOrder.value='Asc'; }\n");
 
 if( scrollType.equals("false") ) sbird.append("   document.besform.current_Page.value = '1' ; \n");
 sbird.append("   } "); 
 if( scrollType.equals("false") ) { 
 	sbird.append("   else {\n");
 	sbird.append("   document.besform.current_Page.value = page ; \n");
 	sbird.append("   }\n\n");
}

 sbird.append("   document.besform.posField.value = posField ; \n");
 sbird.append("   document.besform.posValue.value = posValue ; \n");
 
 sbird.append("   document.besform.posName.value = posName ; \n");
 sbird.append("\n");
 if( editType.equals("true") ) {
	 sbird.append("document.besform.action = \"list1.jsp\"; \n");
 }
 sbird.append("document.besform.target = \"_self\";  \n");
 sbird.append("  document.besform.submit();      \n");
 sbird.append("}  \n");
 sbird.append("\n");
 
 sbird.append("\n");
 sbird.append(" \n");
 
 sbird.append("  \n");
 
 sbird.append("function submit_a1(cmd)  \n");
 sbird.append("{  \n");
 sbird.append("   document.besform.cmd.value = cmd ; //  ��� 'add1_ok';  \n");
 sbird.append("  \n");

           for (int i = 1; i <= actCount; ++i) {
             if ( actDatChk[i].equals("true")) {
 sbird.append("   if( !js_dateReqCheck(document.besform." + actKey[i] + "1, document.besform." + actKey[i] + ", '<%=BesDBUtil.getMessage(\"FilterEnter\", ui, " + actTitle[i].toLowerCase() + "HG)%>', '<%=BesDBUtil.getMessage(\"FilterReEnter\", ui, " + actTitle[i].toLowerCase() + "HG)%>', '<%=BesDBUtil.getMessage(\"FilterCheckNum\", ui, " + actTitle[i].toLowerCase() + "HG)%>') ) \n"); 
 sbird.append("   		return false; \n"); 
 
             } else if ( actReqChk[i].equals("true")) {
                 if ( actKeyType[i].equals("String")) {
 sbird.append("    if(besform."+actKey[i]+".value==''){ alert('<%=BesDBUtil.getMessage(\"FilterEnter\", ui, " + actTitle[i].toLowerCase() + "HG)%>'); besform."+actKey[i]+".focus(); return false;}  \n");
                 } else {
 sbird.append("   if( !js_numberReqCheck(document.besform." + actKey[i] + ", '<%=BesDBUtil.getMessage(\"FilterEnter\", ui, " + actTitle[i].toLowerCase() + "HG)%>', '<%=BesDBUtil.getMessage(\"FilterCheckNum\", ui, " + actTitle[i].toLowerCase() + "HG)%>') ) \n"); 
 sbird.append("   		return false; \n"); 
 
                 }
            }
          } //end for
 sbird.append("  \n");
 sbird.append("  document.besform.submit();      \n");
 sbird.append("}  \n");
 sbird.append("  \n");
 
 sbird.append("  \n");
 sbird.append(" \n");
 
 
  sbird.append("  \n");
 sbird.append("function submit_u1(cmd)  \n");
 sbird.append("{  \n");
 sbird.append("   document.besform.cmd.value = cmd ; //  ��d 'update1_ok';  \n");
 sbird.append("  \n");

           for (int i = 1; i <= actCount; ++i) {
             if ( actDatChk[i].equals("true")) {
 sbird.append("   if( !js_dateReqCheck(document.besform." + actKey[i] + "1, document.besform." + actKey[i] + ", '<%=BesDBUtil.getMessage(\"FilterEnter\", ui, " + actTitle[i].toLowerCase() + "HG)%>', '<%=BesDBUtil.getMessage(\"FilterReEnter\", ui, " + actTitle[i].toLowerCase() + "HG)%>', '<%=BesDBUtil.getMessage(\"FilterCheckNum\", ui, " + actTitle[i].toLowerCase() + "HG)%>') ) \n"); 
 sbird.append("   		return false; \n"); 
 
             } else if ( actReqChk[i].equals("true")) {
                 if ( actKeyType[i].equals("String")) {
 sbird.append("    if(besform."+actKey[i]+".value==''){ alert('<%=BesDBUtil.getMessage(\"FilterEnter\", ui, " + actTitle[i].toLowerCase() + "HG)%>'); besform."+actKey[i]+".focus(); return false;}  \n");
                 } else {
 sbird.append("   if( !js_numberReqCheck(document.besform." + actKey[i] + ", '<%=BesDBUtil.getMessage(\"FilterEnter\", ui, " + actTitle[i].toLowerCase() + "HG)%>', '<%=BesDBUtil.getMessage(\"FilterCheckNum\", ui, " + actTitle[i].toLowerCase() + "HG)%>') ) \n"); 
 sbird.append("   		return false; \n"); 
 
                 }
            }
          } //end for
 sbird.append("  \n");
 sbird.append("  document.besform.submit();      \n");
 sbird.append("}  \n");
 sbird.append("  \n");
 sbird.append("  \n");
 sbird.append("   \n");
 
 
 sbird.append("   \n");
 sbird.append("function submit_d1(actions)   \n");
 sbird.append("{   \n");
 sbird.append(" \n");
 sbird.append("   besform.target = \"_self\" ;  \n");
 sbird.append(" \n");
 sbird.append("   besform.action = actions ;  // �߰�ȭ��: addJsp   \n");
 sbird.append("                               // ��dȭ��: updateJsp   \n");
 
 sbird.append("   besform.submit();       \n");
 sbird.append("}   \n");
 
 sbird.append("   \n");
 sbird.append("   \n");
 
 sbird.append("   \n");
 sbird.append("function submit_x1(cmd)   \n");
 sbird.append("{   \n");
 sbird.append("   if( !confirm('<%=BesDBUtil.getMessage(\"bsc114\", ui)%>') ) return;   \n");
 sbird.append("   besform.cmd.value = cmd;  // ��fȮ�� : delete1_ok   \n");
 sbird.append("   besform.submit();       \n");
 sbird.append("}   \n");
 
 sbird.append(" \n");
 sbird.append(" \n");
 
 sbird.append("   \n");
 sbird.append("function submit_s1(screen)   \n");
 sbird.append("{   \n");
 sbird.append(" \n");
 sbird.append("  document.besform.screen.value = screen ; \n");
 sbird.append(" \n");
 sbird.append("  document.besform.target = \"_parent\" ;  \n");
 sbird.append("  document.besform.action = \"index.jsp\" ; \n");
 sbird.append("  document.besform.submit();       \n");
 sbird.append("}   \n");
 
 sbird.append(" \n");
 
 
 
 sbird.append("   \n");
 sbird.append("function submit_s2()   \n");
 sbird.append("{   \n");
 sbird.append(" \n");
        for (int i = 1; i <= primeCount; ++i) {
 sbird.append("  document.besform."+ primeKey[i] + ".value = \"\" ; \n");
        } // end for
 sbird.append(" \n");
 sbird.append("  document.besform.current_Page.value='1';\n"); 
 sbird.append("  document.besform.submit();       \n");
 sbird.append("}   \n");
 
 sbird.append(" \n");
 
 
 
 sbird.append("   \n");
 sbird.append("function reload("); 
 
 for (int i = 1; i <= primeCount; ++i) {
 	sbird.append(primeKey[i]); 
 	if( i != primeCount ) sbird.append(", "); 
 }

 sbird.append(") {   \n");
 
        for (int i = 1; i <= primeCount; ++i) {
 sbird.append("  document.besform."+ primeKey[i] + ".value = " + primeKey[i] + " ; \n");
        } // end for
 sbird.append(" \n");
 sbird.append("  document.besform.submit();       \n");
 sbird.append("}   \n");
 
 sbird.append("</script> \n");
  
    besScript_js = sbird.toString();
 if( g_isPrompt ) promptBuff.append(besScript_js); 
    sbird = new StringBuffer(); // ����� bird�� ���y��....
 
 
 
 
 
 
//************ list1.jsp ����(Page Type)***********************************

if( scrollType.equals("false") ) {
 sbird.append("<%@ page contentType=\"text/html;charset=utf-8\" %>  \n");
 sbird.append("<%@ include file = \"../../common/include.jsp\" %> \n");
 sbird.append("<%@ include file = \"include.jsp\" %> \n");
 sbird.append("<%  \n");
     
 sbird.append("   \n");
 sbird.append("   String whereOption  = \"\";   \n");
 
 sbird.append("    int currentPage  = 1;   \n");
 sbird.append("    int totalCount = 0;   \n");
     if ( frameType.equals("false")) {
 sbird.append("    int pageSize = 20;   \n");
     } else if ( frameType.equals("true")) {
 sbird.append("    int pageSize = 10;   \n");
    } // frameType if end
    
 sbird.append("    if ( current_Page != null ) {  currentPage = Integer.parseInt(current_Page);  }  \n");
 sbird.append("       \n");
 sbird.append("    " + entityName + " " + entity + " = new " + entityName + "() ; \n");
 sbird.append("    Vector " + entity + "V = new Vector(); // null;  \n");
 sbird.append("    ConnectionResource resource = null;  \n");
 sbird.append("    try {  \n");
 sbird.append("        resource = new " + prjRoot + ".db.BesConnResource();  \n");
 sbird.append("        " + dbWrapName + " " + dbwrap + " = new "+ dbWrapName + "(resource); \n");
 sbird.append("\n");
 sbird.append("        if(!posValue.equals(\"\")) { \n");
 sbird.append("             whereOption += \" and \" + posField + \" like '%\" + posValue + \"%'\"; \n");
 sbird.append("         }\n");
 
		 for( int i=0; i<actFndChk.length; i++ ) {
 sbird.append("        if(!" + actFndChk[i] + ".equals(\"\")) { \n");
 sbird.append("             whereOption += \" and " + getSearchField(actFndChk[i].substring(1)) + "  like '%\" + " + actFndChk[i] + " + \"%'\"; \n");
 sbird.append("        } \n"); 		 	
		 }

         if( primeKey[1].equals("facid")) {
 sbird.append("        totalCount = " + dbwrap+".countPage(facid, whereOption);     \n");
         } else {
 sbird.append("        totalCount = " + dbwrap+".countPage( whereOption);     \n");
         }
 sbird.append("        " + entity + "V = " + dbwrap+".selectPage(");
         if( primeKey[1].equals("facid")) {
 sbird.append(" facid,");
         }
         
 sbird.append(" posField,currentPage,pageSize,posOrder,whereOption);  \n");
 sbird.append("    } catch(Exception e) {  \n");
 sbird.append("        out.println(e.getMessage()+\"<br>\"+e.toString());  \n");
 sbird.append("        e.printStackTrace();  \n");
 sbird.append("    } finally {  \n");
 sbird.append("        if ( resource != null ) resource.release();  \n");
 sbird.append("    } // end try-catch-finally  \n");
 sbird.append("\n");
 sbird.append("%>\n");                                           
 sbird.append("  \n");
 sbird.append("<html>   \n");
 sbird.append("<head>   \n");
 sbird.append("<META HTTP-EQUIV=\"content-type\" content=\"text/html; charset=utf-8\">   \n");
 sbird.append("<LINK REL=\"STYLESHEET\" HREF=\"<%=HTTPSERVER%>/webpages/style/BesWeb.css\" TYPE=\"text/css\">   \n");
 sbird.append("<script src=\"<%=HTTPSERVER%>/webpages/common/BesWeb.js\"></script>   \n");
 
 // for i18n .. 
 //sbird.append("<script language=\"javascript\" src=\"<%=scriptJs%>\"></script>   \n");	
 sbird.append("<%@ include file = \"besScript.jsp\" %>\n"); 
 
 sbird.append("<script language=\"javascript\" src=\"<%=HTTPSERVER%>/webpages/Calendar/__CalendarControlForWeb__.js\"></script> \n");
 sbird.append("<script src=\"<%=HTTPSERVER%>/webpages/common/enterMove.js\"></script>\n"); 
 sbird.append("<title>"+ pgmTitle+ "</title>    \n");
 sbird.append("</head>  \n");
 sbird.append("<body background=\"<%=HTTPSERVER%>/webpages/<%=imageFolder%>/sys_back.gif\">  \n");
 if( editType.equals("true") )
 sbird.append("<form name=besform method=post action=\"<%=actionJava%>\"  target=\"dtlLst\" >    \n");
 else 
 sbird.append("<form name=besform method=post action=\"<%=listJsp%>\"  target=\"dtlLst\" >    \n");
 sbird.append("<input type=hidden name=screen value=\"\"> \n");
 sbird.append("<input type=hidden name=cmd value=\"\"> \n");
// sbird.append("<input type=hidden name=facid value=\"<%=facid%>\"> \n");
 sbird.append("<input type=hidden name=posName value=\"<%=posName%>\"> \n");
 sbird.append("<input type=hidden name=posField value=\"<%=posField%>\"> \n");
 sbird.append("<input type=hidden name=posOrder value=\"<%=posOrder%>\"> \n");
 sbird.append("<input type=hidden name=current_Page value=\"<%=currentPage%>\"> \n");
 sbird.append("<input type=hidden name=parm1 value=\"<%=parm1%>\"> \n");
 
 sbird.append("    \n");
           for (int i = 1; i <= primeCount; ++i) {
 sbird.append("<input type=hidden name=" + primeKey[i] + " value='<%="+primeKey[i]+"%>' > \n");
           }
 sbird.append("<center>    \n");
 sbird.append("<table border=0 width=98% > \n");
 sbird.append("<tr>"); 
 
 
 			boolean firstRow = false; 
 			int base = 0; 
 			if( searchType.equals("true") ) base = 1; 
 			int lsCnt = actFndChk.length;
 			int lrows = (base+lsCnt)/3 + 1; 
 			int lsNum = 0; 
 			
 			if( base == 1 ) {
 sbird.append("	<td class=i align=center width=13% ><%=posName%></td>    \n");
 sbird.append("	<td align=left class=\"c\" width=13% > \n");
 sbird.append("	<input type=text name=posValue value=\"<%=posValue%>\">	</td>\n\n");			
			} else {
sbird.append("	<input type=hidden name=posValue value=\"<%=posValue%>\">  \n");						
			}
			
 			boolean isSearch = false; 
 			for( int ir=1; ir<=lrows; ir++ ) {
 				if( ir != 1 ) sbird.append("</tr>\n\n<tr>"); 
 				
 				for( int ic=1; ic<=4; ic++ ) {
 					int res = getSearchInt( base, lsCnt, ir, ic, isSearch); 
 					if( res == 4 ) isSearch = true; 
 					if( res != 0 ) sbird.append(writeSearchStr( res, lsNum)); 
 					if( res == 2 ) lsNum++; 
 				}
 				
 			}
 			
 sbird.append("</tr>   \n");
 sbird.append("</table>   \n");
 sbird.append("    \n");
 
 
 sbird.append("<table border=0 width=98% > \n");
 sbird.append(" <tr>         \n");
 sbird.append("  <th ><%=posOrder%></th>   \n");
 if( editType.equals("true") ) {
	sbird.append("  <th width=50 >ALL <input type=checkbox name=checkAll onClick=\"javascript:check_all();\" ></th>   \n"); 
 }
         for (int i = 1; i <= listCount; i++ ) {
 sbird.append("  <th ><a href=\"javascript:submit_p1(0,'"+getSearchField(listKey[i])+"','<%=posValue%>',"); 
 		//for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append("  '<%="+listKey[i]+"HG%>')\" ><%="+listKey[i]+"HG.equals(posName)?\"<font color='red'>\":\"\"%><%="+listKey[i]+"HG%><%="+listKey[i]+"HG.equals(posName)?\"</font>\":\"\"%></a></th> \n");
         }
 sbird.append(" </tr>\n");
 sbird.append("<%   \n");
 sbird.append("     String list_no = \"\"  ;    \n");
 sbird.append("     String clsx = \"\", idx = \"\"  ;    \n");
 sbird.append("        int vSize = " + entity + "V.size()  ;   \n");
 sbird.append("%><input type=hidden name=listCount value=\"<%=vSize%>\"> <%\n");
 sbird.append("   for( int i = 1 ; i <= vSize ; i++) {  \n");
 sbird.append("     if( i%2 == 1) { clsx=\"b\"; }  else  { clsx=\"a\";  }// end if  \n");
 sbird.append("     " + entity + " = (" + entityName + ") " + entity + "V.elementAt(i-1); \n");
 sbird.append("     Utility.fixNullAndTrimRead(" + entity + "); \n");
 sbird.append("     if( (posOrder.equals(\"Asc\")) ){  \n");
 sbird.append("       list_no = (((currentPage-1)*pageSize)+i) +\"\"; \n");
 sbird.append("     } else if( (posOrder.equals(\"Desc\")) ){  \n");
 sbird.append("       list_no = (totalCount- (((currentPage-1)*pageSize)+i)+1) +\"\"; \n");
 sbird.append("     } \n");
 sbird.append("     if( ("+ primeKey[1] + ".equals("+ getprimeKey[1] + "))" );
        for (int i = 2; i <= primeCount; ++i) {
        	if( primeKeyType[i].equals("String") )
 sbird.append("&&"+ "("+ primeKey[i] + ".equals("+ getprimeKey[i] + "))");
 			else 
sbird.append("&&"+ "("+ primeKey[i] + " == "+ getprimeKey[i] + ")"); 			
    	} // end for
    
 sbird.append(")  { \n");
 sbird.append("      clsx=\"c\"; \n");
 sbird.append("     } \n");
 sbird.append("%>  \n");
 sbird.append("     <tr  class='<%=clsx%>'  onmouseover=\"trMouseOver(this,'<%=clsx%>')\" onmouseout=\"trMouseOut(this,'<%=clsx%>')\"> \n");
 if( editType.equals("false") ) {
 sbird.append("         <a href=\"<%=homeJsp%>?posName=<%=posName%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&current_Page=<%=currentPage%>&screen=display&"+ primeKey[1] + "=<%="+ getprimeKey[1] + "%>");
        for (int i = 2; i <= primeCount; ++i) {
 sbird.append("&"+ primeKey[i] + "=<%="+ getprimeKey[i] + "%>");
        } // end for
 sbird.append("\"  target=\"_parent\" >   \n");
 } 
 sbird.append("         <td  align=\"center\"> <%=list_no%></td>  \n");
         
         boolean isKeyWrite = false; 
         for (int i = 1; i <= listCount; i++ ) {
         	if( i == 1 && editType.equals("true") ) {
         		sbird.append("		 <td  width=53 align=center  ><input type=checkbox name=check__<%=String.valueOf(i)%> id=check__<%=String.valueOf(i)%> value=\"Y\" ></td>\n"); 
         	}
         	
		if( !isKeyWrite && editType.equals("true") && !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
			for (int j = 1; j <= primeCount; j++) {
sbird.append("		<input type=hidden name=" + primeKey[j] + "<%=String.valueOf(i)%> value=\"<%=" + getprimeKey[j] + "%>\" >\n"); 
			} // end for
			isKeyWrite = true; 
		}
                
             // ������ �ʵ�.. 
             if ( listKeyType[i].equals("String") ) {
             	// list���� ��d������ ����Ʈ ��.. 
             	if( editType.equals("true") && !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
sbird.append("		 <td  align=left >\n"); 
sbird.append("		 <input type=text name=" + listKey[i] + "<%=String.valueOf(i)%> size=10 value=\"<%="+ getlistKey[i] + "%>\" style=\"text-align:right\" onblor='check_change(check__<%=String.valueOf(i)%>);' onFocus=\"nextfield = '" + listKey[i] + "<%=String.valueOf(i%vSize+1)%>'; javascript:selStr(besform." + listKey[i] + "<%=String.valueOf(i)%>)\" onblur=\"javascript:check_change(check__<%=String.valueOf(i)%>);\">\n"); 
sbird.append("		 </td>\n"); 

sbird.append("\n"); 
             	} else {
 sbird.append("         <td  align=\"left\">&nbsp;<%="+getlistKey[i]+"%>&nbsp;</td> \n");
 				}
        	
        	// ��¥�� �ʵ�.. 
        	} else if ( getTableValue(listKey[i], COL_IS_DATE).equals("true") ) {
        		// list���� ��d������ ����Ʈ ��.. 
             	if( editType.equals("true") && !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
sbird.append("		<td  align=center >\n"); 
sbird.append("		<input type=text name=" + listKey[i] + "<%=String.valueOf(i)%> id=" + listKey[i] + "<%=String.valueOf(i)%> value=\"<%=bu.day3Date("+ getlistKey[i] + ")%>\" onclick=__Calendar_Show_Right("+ listKey[i] + "<%=i%>); readonly >\n"); 
sbird.append("		 </td> \n"); 
             	} else {
sbird.append("         <td  align=\"left\">&nbsp;<%=bu.day3Date("+getlistKey[i]+")%>&nbsp;</td> \n");            
            	}
            // ������ �ʵ�.. 
            } else {
            	// list���� ��d������ ����Ʈ ��.. 
             	if( editType.equals("true") && !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
sbird.append("		 <td  align=left >\n"); 
sbird.append("		 <input type=text name=" + listKey[i] + "<%=String.valueOf(i)%> size=10 value=\"<%=nf.format("+ getlistKey[i] + ")%>\" style=\"text-align:right\" onFocus=\"nextfield = '" + listKey[i] + "<%=String.valueOf(i%vSize+1)%>'; javascript:selStr(besform." + listKey[i] + "<%=String.valueOf(i)%>)\" onblur=\"javascript:check_change(check__<%=String.valueOf(i)%>);\">\n"); 
sbird.append("		 </td>\n"); 

sbird.append("\n"); 
             	
             	} else {
 sbird.append("         <td  align=\"right\">&nbsp;<%=nf.format("+getlistKey[i]+")%>&nbsp;</td> \n");
 				}
             }             
         }
if( editType.equals("false") ) sbird.append("    </a>");
 sbird.append("    </tr>   \n");
 sbird.append("<%    } // end for  %>\n");
 sbird.append("    </table>  \n");
 sbird.append("<%  \n");
 sbird.append("int pageWidth = 10;  \n");
 sbird.append("int endPage = ( (totalCount-1) / pageSize ) +1 ;  \n");
 sbird.append("  if ( currentPage > endPage )\n");
 sbird.append("  {\n");
 sbird.append("    currentPage = endPage ;  \n");
 sbird.append("  }\n");
 sbird.append("int prevPage =  currentPage - 1 ;  \n");
 sbird.append("  if ( prevPage < 1 )\n");
 sbird.append("  {\n");
 sbird.append("    prevPage = 1 ;  \n");
 sbird.append("  }\n");
 sbird.append("int nextPage =  currentPage + 1 ;  \n");
 sbird.append("  if ( nextPage > endPage )\n");
 sbird.append("  {\n");
 sbird.append("    nextPage = endPage ;  \n");
 sbird.append("  }\n");
 sbird.append("int fromPage = ( (currentPage-1) / pageWidth )*pageWidth +1 ;  \n");
 sbird.append("int toPage =  fromPage + pageWidth - 1 ;  \n");
 sbird.append("  if ( toPage > endPage )\n");
 sbird.append("  {\n");
 sbird.append("    toPage = endPage ;  \n");
 sbird.append("  }\n");
 sbird.append("  \n");
 sbird.append("\n");
 sbird.append("%>  \n");
 sbird.append("<table>\n");
 sbird.append("  <tr>\n");
 sbird.append("    <td> ��&nbsp;<%=totalCount%>&nbsp;��&nbsp;&nbsp; </td>\n");
 sbird.append("    <% if ( currentPage != 1 ){    %>\n");
 sbird.append("    <td><a href=\"javascript:submit_p1(1,'<%=posField%>','<%=posValue%>',"); 
 	//for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append("  '<%=posName%>')\" > ��</a></td>\n");
 sbird.append("    <td><a href=\"javascript:submit_p1('<%=prevPage%>','<%=posField%>','<%=posValue%>',"); 
 	//for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append(" '<%=posName%>')\" > ��</a></td>\n");
 sbird.append("    <% }else{    %>\n");
 sbird.append("    <td> ��</td>\n");
 sbird.append("    <td> ��</td>\n");
 sbird.append("    <% }    %>\n");
 sbird.append("    <% if ( fromPage != 1 ){    %>\n");
 sbird.append("    <td><a href=\"javascript:submit_p1('<%=(fromPage-1)%>','<%=posField%>','<%=posValue%>'"); 
 //for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append(" ,'<%=posName%>')\" > ��</a></td>\n");
 sbird.append("    <% }    %>\n");
 sbird.append("    <% for ( int i = fromPage ; i <= toPage ; i++ ) {  %>\n");
 sbird.append("    <%   if ( i == currentPage ){    %>\n");
 sbird.append("    <td><font color = red>[<%=i%>]</font></td>\n");
 sbird.append("    <%   }else{    %>\n");
 sbird.append("    <td><a href=\"javascript:submit_p1('<%=i%>','<%=posField%>','<%=posValue%>',"); 
 //for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append(" '<%=posName%>')\" >[<%=i%>]</a></td>\n");
 sbird.append("    <%   }  %> \n");
 sbird.append("    <% }    %>\n");
 sbird.append("    <% if ( toPage != endPage ){    %>\n");
 sbird.append("    <td><a href=\"javascript:submit_p1('<%=(toPage+1)%>','<%=posField%>','<%=posValue%>',"); 
 //for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append(" '<%=posName%>')\" > ��</a></td>\n");
 sbird.append("    <% } %>\n");
 sbird.append("    <% if ( currentPage != endPage ){    %>\n");
 sbird.append("    <td><a href=\"javascript:submit_p1('<%=(nextPage)%>','<%=posField%>','<%=posValue%>',"); 
 //for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append(" '<%=posName%>')\" > ��</a></td>\n");
 sbird.append("    <td><a href=\"javascript:submit_p1('<%=(endPage)%>','<%=posField%>','<%=posValue%>',"); 
 //for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append(" '<%=posName%>')\" > ��</a></td>\n");
 sbird.append("    <% }else{    %>\n");
 sbird.append("    <td> ��</td>\n");
 sbird.append("    <td> ��</td>\n");
 sbird.append("    <% } %>\n");
 sbird.append("        <td>&nbsp;&nbsp; ���� ������:<%=currentPage%>/<%=endPage%></td>\n");
 sbird.append("  </tr>\n");
 sbird.append("</table> \n");
 sbird.append("</form>  \n");
 sbird.append("</body>\n");
 sbird.append("</html>\n");
 
 sbird.append("\n");
 sbird.append("<%\n");
 sbird.append("	if( !errMsg.equals(\"\") ) { %>\n");
 sbird.append("<script language=\"javascript\" >\n");
 sbird.append("	alert('<%=errMsg%>'); \n");
 sbird.append("</script>\n");
 sbird.append("<% } %>\n");
 sbird.append("\n");
 

    list1_jsp = sbird.toString();
    if( g_isPrompt ) promptBuff.append(list1_jsp); 
    
    sbird = new StringBuffer(); // ����� bird�� ���y��....
    
} 



//************ list1.jsp ����(Scorll Type)***********************************
else if( scrollType.equals("true") ) {
	
 sbird.append("<%@ page contentType=\"text/html;charset=utf-8\" %>  \n");
 sbird.append("<%@ include file = \"../../common/include.jsp\" %> \n");
 sbird.append("<%@ include file = \"include.jsp\" %> \n");
 sbird.append("<%  \n");
     
 sbird.append("   String whereOption  = \"\";   \n");
 
sbird.append("\n");
 sbird.append("%>\n");                                           
 sbird.append("  \n");
 sbird.append("<html>   \n");
 sbird.append("<head>   \n");
 sbird.append("<META HTTP-EQUIV=\"content-type\" content=\"text/html; charset=utf-8\">   \n");
 sbird.append("<LINK REL=\"STYLESHEET\" HREF=\"<%=HTTPSERVER%>/webpages/style/BesWeb.css\" TYPE=\"text/css\">   \n");
 sbird.append("<script src=\"<%=HTTPSERVER%>/webpages/common/BesWeb.js\"></script>   \n");
 //sbird.append("<script language=\"javascript\" src=\"<%=scriptJs%>\"></script>   \n");
 sbird.append("<%@ include file = \"besScript.jsp\" %>\n"); 
 sbird.append("<script language=\"javascript\" src=\"<%=HTTPSERVER%>/webpages/Calendar/__CalendarControlForWeb__.js\"></script> \n");
 sbird.append("<title>"+ pgmTitle+ "</title>    \n");
 sbird.append("</head>  \n");
 sbird.append("<body background=\"<%=HTTPSERVER%>/webpages/<%=imageFolder%>/sys_back.gif\">  \n");
 sbird.append("<form name=besform method=post action=\"<%=listJsp%>\"  target=\"dtlLst\" >    \n");
 sbird.append("<input type=hidden name=screen value=\"\"> \n");
 sbird.append("<input type=hidden name=cmd value=\"\"> \n");
 sbird.append("<input type=hidden name=posName value=\"<%=posName%>\"> \n");
 sbird.append("<input type=hidden name=posField value=\"<%=posField%>\"> \n");
 sbird.append("<input type=hidden name=posOrder value=\"<%=posOrder%>\"> \n");
 sbird.append("<input type=hidden name=parm1 value=\"<%=parm1%>\"> \n");

 
 sbird.append("    \n");
           for (int i = 1; i <= primeCount; ++i) {
 sbird.append("<input type=hidden name=" + primeKey[i] + " value='<%="+primeKey[i]+"%>' > \n");
           }
 sbird.append("<center>    \n");
 sbird.append("<table border=0 width=98% > \n");
 sbird.append("<tr>"); 
 			
 			boolean firstRow = false; 
 			int base = 0; 
 			if( searchType.equals("true") ) base = 1; 
 			int lsCnt = actFndChk.length;
 			int lrows = (base+lsCnt)/3 + 1; 
 			int lsNum = 0; 
 			
 			if( base == 1 ) {
 sbird.append("	<td class=i align=center width=13% ><%=posName%></td>    \n");
 sbird.append("	<td align=left class=\"c\" width=13% > \n");
 sbird.append("	<input type=text name=posValue value=\"<%=posValue%>\">	</td>\n");			
			} else {
sbird.append("	<input type=hidden name=posValue value=\"<%=posValue%>\">  \n");						
			}
			
 			boolean isSearch = false; 
 			for( int ir=1; ir<=lrows; ir++ ) {
 				if( ir != 1 ) sbird.append("</tr>\n\n<tr>"); 
 				
 				for( int ic=1; ic<=4; ic++ ) {
 					int res = getSearchInt( base, lsCnt, ir, ic, isSearch); 
 					if( res == 4 ) isSearch = true; 
 					if( res != 0 ) sbird.append(writeSearchStr( res, lsNum)); 
 					if( res == 2 ) lsNum++; 
 				}
 				
 			}
 			
 sbird.append("</tr>   \n");
 sbird.append("</table>   \n");
 sbird.append("    \n");
 
 sbird.append("<table border=0 width=98% > \n");
 sbird.append(" <tr>         \n");
 sbird.append("  <th width=<%=String.valueOf(colSize0)%> ><%=posOrder%></th>   \n");
 if( editType.equals("true") ) {
	sbird.append("  <th  width=50 >ALL <input type=checkbox name=checkAll onClick=\"javascript:check_all();\" ></th>   \n"); 
 }
         for (int i = 1; i <= listCount; i++ ) {
         	String strSize = "width=<%=String.valueOf(colSize" + i + ")%> "; 
         	if( i == listCount ) strSize = " "; 
 sbird.append("  <th " + strSize + " ><a href=\"javascript:submit_p1(0,'"+getSearchField(listKey[i])+"','<%=posValue%>',"); 
 			//for( int j=0; j<actFndChk.length; j++) sbird.append("'<%=" + actFndChk[j] + "%>',"); 
 sbird.append("  '<%="+listKey[i]+"HG%>')\" ><%="+listKey[i]+"HG.equals(posName)?\"<font color='red'>\":\"\"%><%="+listKey[i]+"HG%><%="+listKey[i]+"HG.equals(posName)?\"</font>\":\"\"%></a></th> \n");
         }
 sbird.append(" </tr>\n");
 
 sbird.append(" <tr>\n");
 if( editType.equals("true") ) sbird.append("		<td colspan=\"" + String.valueOf(listCount+2) + "\" class=\"c\">\n"); 
 else sbird.append("		<td colspan=\"" + String.valueOf(listCount+1) + "\" class=\"c\">\n"); 
 sbird.append("			<iframe scrolling=\"yes\" frameborder=\"0\" width=\"100%\" height=\"430\" frame name=\"dataframe\" src=\"<%=dataJsp%>?posName=<%=posName%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&"+ primeKey[1] + "=<%="+ primeKey[1] + "%>"); 
 	for (int i = 2; i <= primeCount; ++i) {
 sbird.append("&"+ primeKey[i] + "=<%="+ primeKey[i] + "%>");
    } // end for
 sbird.append("    \" ></iframe>\n"); 
 sbird.append("		</td>\n"); 
 sbird.append(" </tr>\n");
 
 sbird.append("</table> \n");
 sbird.append("</form>  \n");
 sbird.append("</body>\n");
 sbird.append("</html>\n");
 
 sbird.append("\n");
 sbird.append("<%\n");
 sbird.append("	if( !errMsg.equals(\"\") ) { %>\n");
 sbird.append("<script language=\"javascript\" >\n");
 sbird.append("	alert('<%=errMsg%>'); \n");
 sbird.append("</script>\n");
 sbird.append("<% } %>\n");
 sbird.append("\n");
 
    list1_jsp = sbird.toString();
    if( g_isPrompt ) promptBuff.append(list1_jsp); 
    sbird = new StringBuffer(); // ����� bird�� ���y��....
    
        
 // *********** data1.jsp( Scroll Type)   *******************************
    
 sbird.append("<%@ page contentType=\"text/html;charset=utf-8\" %>  \n");
 sbird.append("<%@ include file = \"../../common/include.jsp\" %> \n");
 sbird.append("<%@ include file = \"include.jsp\" %> \n");
 sbird.append("<%  \n");
     
 sbird.append("   \n");
 sbird.append("   String whereOption  = BesUtil.chNull(request.getParameter(\"whereOption\"));   \n");
 sbird.append("    int totalCount = 0;   \n");
     
 sbird.append("    " + entityName + " " + entity + " = new " + entityName + "() ; \n");
 sbird.append("    Vector " + entity + "V = new Vector(); // null;  \n");
 sbird.append("    ConnectionResource resource = null;  \n");
 sbird.append("    try {  \n");
 sbird.append("        resource = new " + prjRoot + ".db.BesConnResource();  \n");
 sbird.append("        " + dbWrapName + " " + dbwrap + " = new "+ dbWrapName + "(resource); \n");
 sbird.append("\n");
 sbird.append("        if(!posValue.equals(\"\")) { \n");
 sbird.append("             whereOption += \" and \" + posField + \" like '%\" + posValue + \"%'\"; \n");
 sbird.append("         }\n");
 
		 for( int i=0; i<actFndChk.length; i++ ) {
 sbird.append("        if(!" + actFndChk[i] + ".equals(\"\")) { \n");
 sbird.append("             whereOption += \" and " + getSearchField(actFndChk[i].substring(1)) + "  like '%\" + " + actFndChk[i] + " + \"%'\"; \n");
 sbird.append("        } \n"); 		 	
		 }

 sbird.append("        " + entity + "V = " + dbwrap+".selectWhere("); 
         if( primeKey[1].equals("facid")) {
 sbird.append(" facid,");
         }
 sbird.append("posField,posOrder,whereOption);  \n");

 sbird.append("    } catch(Exception e) {  \n");
 sbird.append("        out.println(e.getMessage()+\"<br>\"+e.toString());  \n");
 sbird.append("        e.printStackTrace();  \n");
 sbird.append("    } finally {  \n");
 sbird.append("        if ( resource != null ) resource.release();  \n");
 sbird.append("    } // end try-catch-finally  \n");
 sbird.append("\n");
 sbird.append("%>\n");                                           
 sbird.append("  \n");
 sbird.append("<html>   \n");
 sbird.append("<head>   \n");
 sbird.append("<META HTTP-EQUIV=\"content-type\" content=\"text/html; charset=utf-8\">   \n");
 sbird.append("<LINK REL=\"STYLESHEET\" HREF=\"<%=HTTPSERVER%>/webpages/style/BesWeb.css\" TYPE=\"text/css\">   \n");
 sbird.append("<script src=\"<%=HTTPSERVER%>/webpages/common/BesWeb.js\"></script>   \n");
 //sbird.append("<script language=\"javascript\" src=\"<%=scriptJs%>\"></script>   \n");
 sbird.append("<%@ include file = \"besScript.jsp\" %>\n"); 
 sbird.append("<script language=\"javascript\" src=\"<%=HTTPSERVER%>/webpages/Calendar/__CalendarControlForWeb__.js\"></script> \n");
 sbird.append("<title>"+ pgmTitle+ "</title>    \n");
 sbird.append("</head>  \n");
 sbird.append("<body leftmargin=\"0\" topmargin=\"0\">  \n");
 if( editType.equals("true") ) {
 sbird.append("<form name=besform method=post action=\"<%=actionJava%>\"  target=\"dtlLst\" >    \n");
 sbird.append("<input type=hidden name=trmid value=\"<%=request.getRemoteAddr()%>\"> \n");
 sbird.append("<input type=hidden name=usrid value=\"<%=usrid%>\"> \n");
 
 }
 else 
 sbird.append("<form name=besform method=post action=\"<%=listJsp%>\"  target=\"dtlLst\" >    \n");
 sbird.append("<input type=hidden name=screen value=\"\"> \n");
 sbird.append("<input type=hidden name=cmd value=\"\"> \n");
 sbird.append("<input type=hidden name=posName value=\"<%=posName%>\"> \n");
 sbird.append("<input type=hidden name=posField value=\"<%=posField%>\"> \n");
 sbird.append("<input type=hidden name=posOrder value=\"<%=posOrder%>\"> \n");
 sbird.append("<input type=hidden name=parm1 value=\"<%=parm1%>\"> \n");
 //sbird.append("<input type=hidden name=current_Page value=\"<%=currentPage%>\"> \n");
 
 sbird.append("    \n");
           for (int i = 1; i <= primeCount; ++i) {
 sbird.append("<input type=hidden name=" + primeKey[i] + " value='<%="+primeKey[i]+"%>' > \n");
           }
 sbird.append("<input type=hidden name=posValue value=\"<%=posValue%>\">  </td>\n");
 			for( int j=0; j<actFndChk.length; j++)  {
 sbird.append("<input type=hidden name=" + actFndChk[j] + " value=\"<%=" + actFndChk[j] + "%>\">  \n");
 			}
 sbird.append("    \n");
 
 sbird.append("<table border=0 width=100% > \n");
 sbird.append("<%   \n");
 sbird.append("     String list_no = \"\"  ;    \n");
 sbird.append("     String clsx = \"\", idx = \"\"  ;    \n");
 sbird.append("        int vSize = " + entity + "V.size()  ;   \n");
 sbird.append("%><input type=hidden name=listCount value=\"<%=vSize%>\"> <%\n");
 sbird.append("   for( int i = 1 ; i <= vSize ; i++) {  \n");
 sbird.append("     if( i%2 == 1) { clsx=\"b\"; }  else  { clsx=\"a\";  }// end if  \n");
 sbird.append("     " + entity + " = (" + entityName + ") " + entity + "V.elementAt(i-1); \n");
 sbird.append("     Utility.fixNullAndTrimRead(" + entity + "); \n");
 sbird.append("     list_no = String.valueOf(i);\n"); 
 
 sbird.append("     if( ("+ primeKey[1] + ".equals("+ getprimeKey[1] + "))" );
        for (int i = 2; i <= primeCount; ++i) {
if( primeKeyType[i].equals("String") )
 sbird.append("&&"+ "("+ primeKey[i] + ".equals("+ getprimeKey[i] + "))");
 			else 
sbird.append("&&"+ "("+ primeKey[i] + " == "+ getprimeKey[i] + ")"); 			
        } // end for
 sbird.append(")  { \n");
 sbird.append("      clsx=\"c\"; \n");
 sbird.append("     } \n");
 sbird.append("%>  \n");
 sbird.append("     <tr  class='<%=clsx%>'  onmouseover=\"trMouseOver(this,'<%=clsx%>')\" onmouseout=\"trMouseOut(this,'<%=clsx%>')\"> \n");

 if( editType.equals("false") ) { 
 sbird.append("         <a href=\"<%=updateJsp%>?posName=<%=posName%>&posField=<%=posField%>&posValue=<%=posValue%><%=callParameter%>&posOrder=<%=posOrder%>&screen=display&"+ primeKey[1] + "=<%="+ getprimeKey[1] + "%>");
        for (int i = 2; i <= primeCount; ++i) {
 sbird.append("&"+ primeKey[i] + "=<%="+ getprimeKey[i] + "%>");
        } // end for
 sbird.append("\"  target=\"dtlCtl\" onClick='javascript:reload("); 
 		for (int i = 1; i <= primeCount; ++i) {
 sbird.append("\"" + "<%="+ getprimeKey[i] + "%>\"");
 			if( i != primeCount ) sbird.append(", ");
        } // end for
 sbird.append(")'>   \n");
} 

 sbird.append("         <td  width=<%=String.valueOf(colSize0-4)%>  align=\"center\"> <%=list_no%></td>  \n");
         
         boolean isKeyWrite = false; 
         for (int i = 1; i <= listCount; i++ ) {
         	if( i == 1 && editType.equals("true") ) {
         		sbird.append("		 <td  width=53 align=center ><input type=checkbox name=check__<%=String.valueOf(i)%> id=check__<%=String.valueOf(i)%> value=\"Y\" ></td>\n"); 
         	}
			if( !isKeyWrite && editType.equals("true") && !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
				for (int j = 1; j <= primeCount; j++) {
sbird.append("		<input type=hidden name=" + primeKey[j] + "<%=String.valueOf(i)%> value=\"<%=" + getprimeKey[j] + "%>\" >\n"); 
				} // end for
				isKeyWrite = true; 
			}
            String strSize = " width=<%=String.valueOf(colSize" + i + "+1)%> "; 
         	if( i == listCount ) strSize = ""; 
         	    
             // ������ �ʵ�.. 
             if ( listKeyType[i].equals("String") ) {
             	// list���� ��d������ ����Ʈ ��.. 
             	if( editType.equals("true") && !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
sbird.append("		 <td  " + strSize + " align=left >\n"); 
sbird.append("		 <input type=text name=" + listKey[i] + "<%=String.valueOf(i)%> size=10 value=\"<%="+ getlistKey[i] + "%>\" style=\"text-align:right\" onFocus=\"nextfield = '" + listKey[i] + "<%=String.valueOf(i%vSize+1)%>'; javascript:selStr(besform." + listKey[i] + "<%=String.valueOf(i)%>)\" onblur=\"javascript:check_change(check__<%=String.valueOf(i)%>);\">\n"); 
sbird.append("		 </td>\n"); 

sbird.append("\n"); 
             	} else {
 sbird.append("         <td " + strSize + " align=\"left\">&nbsp;<%="+getlistKey[i]+"%>&nbsp;</td> \n");
 				}
        	
        	// ��¥�� �ʵ�.. 
        	} else if ( getTableValue(listKey[i], COL_IS_DATE).equals("true") ) {
        		// list���� ��d������ ����Ʈ ��.. 
             	if( editType.equals("true") && !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
sbird.append("		<td " + strSize + " align=center >\n"); 
sbird.append("		<input type=text name=" + listKey[i] + "<%=String.valueOf(i)%> id=" + listKey[i] + "<%=String.valueOf(i)%> value=\"<%=bu.day3Date("+ getlistKey[i] + ")%>\" onclick=__Calendar_Show_Right("+ listKey[i] + "<%=i%>); readonly >\n"); 
sbird.append("		 </td> \n"); 
             	} else {
sbird.append("         <td " + strSize + " align=\"left\">&nbsp;<%=bu.day3Date("+getlistKey[i]+")%>&nbsp;</td> \n");            
            	}
            // ������ �ʵ�.. 
            } else {
            	// list���� ��d������ ����Ʈ ��.. 
             	if( editType.equals("true") && !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
sbird.append("		 <td " + strSize + " align=left >\n"); 
sbird.append("		 <input type=text name=" + listKey[i] + "<%=String.valueOf(i)%> size=10 value=\"<%=nf.format("+ getlistKey[i] + ")%>\" style=\"text-align:right\" onFocus=\"nextfield = '" + listKey[i] + "<%=String.valueOf(i%vSize+1)%>'; javascript:selStr(besform." + listKey[i] + "<%=String.valueOf(i)%>)\" onblur=\"javascript:check_change(check__<%=String.valueOf(i)%>);\">\n"); 
sbird.append("		 </td>\n"); 

sbird.append("\n"); 
             	
             	} else {
 sbird.append("         <td " + strSize + " align=\"right\">&nbsp;<%=nf.format("+getlistKey[i]+")%>&nbsp;</td> \n");
 				}
             }             
         }
if( editType.equals("false") ) sbird.append("    </a>");         
 sbird.append("    </tr>   \n");
 sbird.append("<%    } // end for  %>\n");
 sbird.append("    </table>  \n");

 
 sbird.append("</form>  \n");
 sbird.append("</body>\n");
 sbird.append("</html>\n");

    data1_jsp = sbird.toString();
    if( g_isPrompt ) promptBuff_d.append(data1_jsp); 
    sbird = new StringBuffer(); // ����� bird�� ���y��....    
    
} // end of else if( scrollType.equals("true").. 

//************ add1.jsp ����***********************************

 sbird.append("<%@ page contentType=\"text/html;charset=utf-8\" %>  \n");
 sbird.append("<%@ include file = \"../../common/include.jsp\" %> \n");
 sbird.append("<%@ include file = \"include.jsp\" %>   \n");
 sbird.append("  \n");
 sbird.append("<%   \n");
 sbird.append(" \n");
 sbird.append("	" + entityName + " " + entity + " = new " + entityName + "() ; \n");
 sbird.append("	Utility.fixNullAndTrimRead(" + entity + "); \n");
 
		int cnt_a1 = 0; 
 		for( int i=1; i<actCount; i++ ) {
 			if( !actPmtPgm[i].equals("") && actPmtPgm[i].length() == 6 ) {
 				cnt_a1++; 
 				sbird.append("	Vector codeV" + String.valueOf(cnt_a1) + " = null; \n"); 
 sbird.append("		codeV" + String.valueOf(cnt_a1) + " = " + prjRoot + ".base.BesUtilMrn.selectCommonCd(\"" + actPmtPgm[i].substring(0,3) + "\", \"" + actPmtPgm[i].substring(3,6)+"\"); \n"); 
 			}
 		}
 
 sbird.append("%>   \n");
 

 sbird.append("   \n");
 sbird.append("<html>  \n");
 sbird.append("<head>  \n");
 sbird.append("<META HTTP-EQUIV=\"content-type\" content=\"text/html; charset=utf-8\">  \n");
 sbird.append("<LINK REL=\"STYLESHEET\" HREF=\"<%=HTTPSERVER%>/webpages/style/BesWeb.css\" TYPE=\"text/css\">  \n");
 sbird.append("<script src=\"<%=HTTPSERVER%>/webpages/common/BesWeb.js\"></script>  \n");
 //sbird.append("<script language=\"javascript\" src=\"<%=scriptJs%>\"></script>   \n");
 sbird.append("<%@ include file = \"besScript.jsp\" %>\n"); 
 sbird.append("<script language=\"javascript\" src=\"<%=HTTPSERVER%>/webpages/Calendar/__CalendarControlForWeb__.js\"></script> \n");
 sbird.append("</head>  \n");
 sbird.append("  \n");
 sbird.append("<body background=\"<%=HTTPSERVER%>/webpages/<%=imageFolder%>/sys_back.gif\">  \n");
 sbird.append("<center>  \n");
 sbird.append("  \n");
 sbird.append("<form name=besform method=post  action=\"<%=actionJava%>\" target=\"_parent\" >  \n");

     if ( frameType.equals("true")) {
 sbird.append("<input type=hidden name=posName value=\"<%=posName%>\"> \n");
 sbird.append("<input type=hidden name=posField value=\"<%=posField%>\"> \n");
 	for( int j=0; j<actFndChk.length; j++) { 
sbird.append("<input type=hidden name=" + actFndChk[j] + " value=\"<%=" + actFndChk[j] + "%>\"> \n");
	}

 sbird.append("<input type=hidden name=posValue value=\"<%=posValue%>\"> \n");
 sbird.append("<input type=hidden name=posOrder value=\"<%=posOrder%>\"> \n");
 sbird.append("<input type=hidden name=current_Page value=\"<%=current_Page%>\"> \n");
     } // frameType if end
 sbird.append("<input type=hidden name=trmid value='<%= request.getRemoteAddr() %>' >  \n");
 sbird.append("<input type=hidden name=usrid value=\"<%=usrid%>\" >  \n");
 sbird.append("<input type=hidden name=parm1 value=\"<%=parm1%>\"> \n");

 sbird.append("<input type=hidden name=cmd value='' >  \n");

           for (int i = 1; i <= primeCount; ++i) {
             keydupchk="ok";
             for (int j = 1; j <= actCount; ++j) {
               if (primeKey[i].equals(actKey[j])) { 
                  keydupchk = "no";
               }
             } // end for j
             if (keydupchk.equals("ok")) { 
 sbird.append("<input type=hidden name=" + primeKey[i] + " value='<%="+primeKey[i]+"%>' > \n");
             }
           } // end for i 

 sbird.append("  \n");
 sbird.append("  <table border=\"0\" width=\"98%\" cellpadding=\"2\"> \n");
 sbird.append(" \n");

// edit cols start.......... 
			int commonCnt = 0; //  �����ڵ� ��n�4� Vector d�Ǹ� '�� counter.. 
            for (int i = 1; i <= actCount; ++i) {
           	
 if( i%3 == 1 ) sbird.append("   <tr> \n");
			if ( actReqChk[i].equals("true")) {
 sbird.append("    <td width=\"13%\" class=t  colspan=1 ><%="+actKey[i]+"HG%></td> \n");
			} else {
sbird.append("    <td width=\"13%\" class=i  colspan=1 ><%="+actKey[i]+"HG%></td> \n");			
			}

 sbird.append("    <td width=\"20%\" class=c  colspan=1 > \n");

             if ( actReqChk[i].equals("true")) {
                inclasstype =" ";
             } else {  
                inclasstype ="";
             }     

             if ( actDatChk[i].equals("true") ) {
 sbird.append("     <input type=text " + inclasstype + " name="+actKey[i]+"1 size="+(actKeySize[i]+2)+" value='<%=bu.day3Date(bu.getDay())%>' > \n");
 sbird.append("     <input type=hidden  name="+actKey[i]+" value='' >  \n");
 sbird.append("     <img src='<%=HTTPSERVER%>/webpages/<%=imageFolder%>/icon/calendar.gif' border=0 align='absmiddle' onclick=__Calendar_Show("+actKey[i]+"1); style=cursor:hand> \n");
             
             } else if( !actPmtPgm[i].equals("") && actPmtPgm[i].length() == 6 ) {
 				commonCnt++; 
sbird.append("	<select name="+actKey[i]+">\n"); 
sbird.append("	<% \n"); 
sbird.append("	for(int i=0; i<codeV" + String.valueOf(commonCnt) + ".size(); i++){      \n"); 
sbird.append("		BSCDTLRec bscdtl = (BSCDTLRec) codeV" + String.valueOf(commonCnt) + ".elementAt(i); \n"); 
sbird.append("		Utility.fixNullAndTrimRead(bscdtl);\n"); 
sbird.append("	%>     \n"); 
sbird.append("		<option value=\"<%=bscdtl.getDtlcd()%>\" <% if(bscdtl.getDtlcd().equals("+getactKey[i]+")){%>selected<%}%>><%=bscdtl.getDtlcd() + \" : \" + bu.fromDB(bscdtl.getDtlnm())%></option>\n"); 
sbird.append("	<% } %>\n"); 
sbird.append("	</select>\n"); 

            } else {
sbird.append("     <input type=text " + inclasstype + " name="+actKey[i]+" size="+actKeySize[i]+" maxlength="+actKeySize[i]+" value='<%="+getactKey[i]+"%>' >\n");
            }
            

             if ( !actPmtPgm[i].equals("") && actPmtPgm[i].length() != 6 ) {
 sbird.append("      <a href=\"javascript:open_window('win1','<%=HTTPSERVER%>/webpages/common/prompt/"+actPmtPgm[i]+"',50,50,700,350,0,0,0,1,0);\" onfocus=this.blur()> \n");
 sbird.append("      <img src=\"<%=HTTPSERVER%>/webpages/<%=imageFolder%>/icon/arrow.gif\" border=0 align=\"absmiddle\" alt=����></a> \n");
             }     

 sbird.append("    </td> \n");
 if( i%3 == 0 || i == actCount ) sbird.append("   </tr> \n");
 sbird.append(" \n");
            }
 sbird.append("  </table> \n");

 sbird.append("  <table border=\"0\" width=\"98%\" cellpadding=\"2\"> \n");
 sbird.append("    <tr> \n");
 sbird.append("    <td width=\"100%\" align=\"right\"> \n");
     if ( frameType.equals("false")) {
 sbird.append("    <input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_List.gif); border:0; width:85;height:22;cursor:hand;\" onClick='javascript:submit_d1(\"<%=listJsp%>\")'>  \n");
     } // frameType if end
 sbird.append("    <input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_Confirm.gif); border:0; width:85;height:22;cursor:hand;\" onClick='javascript:submit_a1(\"add1_ok\")'>  \n");
 sbird.append("    <input type=reset name='reset' style=\"background: url(../../<%=imageFolder%>/icon/btn_Refresh.gif); border:0; width:85;height:22;cursor:hand;\" value=''> \n");
 sbird.append("    </td> \n");
 sbird.append("    </tr> \n");
 sbird.append("  </table> \n");
 sbird.append("</form>  \n");
 sbird.append("</body>  \n");
 sbird.append("</html>  \n");


    add1_jsp = sbird.toString();
    sbird = new StringBuffer(); // ����� bird�� ���y��....


//************ update1.jsp ����***********************************
 sbird.append("<%@ page contentType=\"text/html;charset=utf-8\" %>  \n");
 sbird.append("<%@ include file = \"../../common/include.jsp\" %> \n");
 sbird.append("<%@ include file = \"include.jsp\" %>   \n");
 sbird.append("  \n");
 sbird.append("<%   \n");
 sbird.append(" \n");
 sbird.append("    " + entityName + " " + entity + " = new " + entityName + "() ; \n");
 sbird.append("     \n");
 sbird.append("    ConnectionResource resource = null;   \n");
 		for( int i=0; i<getCommonCnt(); i++ ) {
 sbird.append("	Vector codeV" + String.valueOf(i+1) + " = null; \n"); 
  		}
 sbird.append("    try {   \n");
 sbird.append("        resource = new " + prjRoot + ".db.BesConnResource();   \n");
 sbird.append("        " + dbWrapName + " " + dbwrap + " = new "+ dbWrapName + "(resource); \n");
 sbird.append("        " + entity + " = " + dbwrap+".select(");
        for (int i = 1; i <= primeCount; ++i) {
          if ( i != 1 )  sbird.append(", ") ;
 sbird.append( primeKey[i]);
        } // end for
 sbird.append(" );     \n");
 		int cnt_b1 = 0; 
 		for( int i=1; i<actCount; i++ ) {
 			if( !actPmtPgm[i].equals("") && actPmtPgm[i].length() == 6 ) {
 				cnt_b1++; 
 sbird.append("		codeV" + String.valueOf(cnt_b1) + " = " + prjRoot + ".base.BesUtilMrn.selectCommonCd(\"" + actPmtPgm[i].substring(0,3) + "\", \"" + actPmtPgm[i].substring(3,6)+"\"); \n"); 
 			}
 		}
 sbird.append("    } catch(Exception e) {   \n");
 sbird.append("        out.println(e.getMessage()+\"<br>\"+e.toString());   \n");
 sbird.append("        e.printStackTrace();   \n");
 sbird.append("        out.println(box.toString());   \n");
 sbird.append("    } finally {   \n");
 sbird.append("        if ( resource != null ) resource.release();   \n");
 sbird.append("    } // end try-catch-finally   \n");
 sbird.append("     Utility.fixNullAndTrimRead(" + entity + "); \n");
 sbird.append("%>   \n");
 sbird.append("   \n");
  
 sbird.append("   \n");
 sbird.append("<html>  \n");
 sbird.append("<head>  \n");
 sbird.append("<META HTTP-EQUIV=\"content-type\" content=\"text/html; charset=utf-8\">  \n");
 sbird.append("<LINK REL=\"STYLESHEET\" HREF=\"<%=HTTPSERVER%>/webpages/style/BesWeb.css\" TYPE=\"text/css\">  \n");
 sbird.append("<script src=\"<%=HTTPSERVER%>/webpages/common/BesWeb.js\"></script>  \n");
 //sbird.append("<script language=\"javascript\" src=\"<%=scriptJs%>\"></script>   \n");
 sbird.append("<%@ include file = \"besScript.jsp\" %>\n"); 
 sbird.append("<script language=\"javascript\" src=\"<%=HTTPSERVER%>/webpages/Calendar/__CalendarControlForWeb__.js\"></script> \n");
 sbird.append("</head>  \n");
 sbird.append("  \n");
 sbird.append("<body background=\"<%=HTTPSERVER%>/webpages/<%=imageFolder%>/sys_back.gif\">  \n");
 sbird.append("<center>  \n");
 sbird.append("  \n");
 sbird.append("<form name=besform method=post  action=\"<%=actionJava%>\" target=\"_parent\" >  \n");

     if ( frameType.equals("true")) {
 sbird.append("<input type=hidden name=posName value=\"<%=posName%>\"> \n");
 sbird.append("<input type=hidden name=posField value=\"<%=posField%>\"> \n");
 sbird.append("<input type=hidden name=posValue value=\"<%=posValue%>\"> \n");
 	for( int j=0; j<actFndChk.length; j++) { 
sbird.append("<input type=hidden name=" + actFndChk[j] + " value=\"<%=" + actFndChk[j] + "%>\"> \n");
	}
 sbird.append("<input type=hidden name=posOrder value=\"<%=posOrder%>\"> \n");
 sbird.append("<input type=hidden name=current_Page value=\"<%=current_Page%>\"> \n");
     } // frameType if end
 sbird.append("<input type=hidden name=trmid value='<%= request.getRemoteAddr() %>' >  \n");
 sbird.append("<input type=hidden name=usrid value=\"<%=usrid%>\" >  \n");
 sbird.append("<input type=hidden name=parm1 value=\"<%=parm1%>\"> \n");

 sbird.append("<input type=hidden name=cmd value='' >  \n");

           for (int i = 1; i <= primeCount; ++i) {
             keydupchk="ok";
             for (int j = 1; j <= actCount; ++j) {
               if (primeKey[i].equals(actKey[j])) { 
                  keydupchk = "no";
               }
             } // end for j
             if (keydupchk.equals("ok")) { 
 sbird.append("<input type=hidden name=" + primeKey[i] + " value='<%="+getprimeKey[i]+"%>' > \n");
             }
           } // end for i 

 sbird.append("  \n");
 sbird.append("  <table border=\"0\" width=\"98%\" cellpadding=\"2\"> \n");
 sbird.append(" \n");
			
			commonCnt = 0; 
           for (int i = 1; i <= actCount; ++i) {
 if( i%3 == 1 ) sbird.append("   <tr> \n");
			if ( actReqChk[i].equals("true")) {
 sbird.append("    <td width=\"13%\" class=t  colspan=1 ><%="+actKey[i]+"HG%></td> \n");
			} else {
 sbird.append("    <td width=\"13%\" class=i  colspan=1 ><%="+actKey[i]+"HG%></td> \n");			
			}
 sbird.append("    <td width=\"20%\" class=c  colspan=1 > \n");

             if ( actDspChk[i].equals("true")) {
                inclasstype =" class=nobrd readonly ";
             } else if ( actReqChk[i].equals("true")) {
                inclasstype =" ";
             } else {  
                inclasstype ="";
             }     

             if ( actDatChk[i].equals("true") ) {
 sbird.append("     <input type=text " + inclasstype + " name="+actKey[i]+"1 size="+(actKeySize[i]+2)+" value='<%=bu.day3Date("+getactKey[i]+")%>' > \n");
 sbird.append("     <input type=hidden  name="+actKey[i]+"  value='<%="+getactKey[i]+"%>' >  \n");
 sbird.append("     <img src='<%=HTTPSERVER%>/webpages/<%=imageFolder%>/icon/calendar.gif' border=0 align='absmiddle' onclick=__Calendar_Show("+actKey[i]+"1); style=cursor:hand> \n");
            
			} else if( !actPmtPgm[i].equals("") && actPmtPgm[i].length() == 6 ) {
 				commonCnt++; 
sbird.append("	<select name="+actKey[i]+">\n"); 
sbird.append("	<% \n"); 
sbird.append("	for(int i=0; i<codeV" + String.valueOf(commonCnt) + ".size(); i++){      \n"); 
sbird.append("		BSCDTLRec bscdtl = (BSCDTLRec) codeV" + String.valueOf(commonCnt) + ".elementAt(i); \n"); 
sbird.append("		Utility.fixNullAndTrimRead(bscdtl);\n"); 
sbird.append("	%>     \n"); 
sbird.append("		<option value=\"<%=bscdtl.getDtlcd()%>\" <% if(bscdtl.getDtlcd().equals("+getactKey[i]+")){%>selected<%}%>><%=bscdtl.getDtlcd() + \" : \" + bu.fromDB(bscdtl.getDtlnm())%></option>\n"); 
sbird.append("	<% } %>\n"); 
sbird.append("	</select>\n"); 

            }            
            else  {
 sbird.append("     <input type=text " + inclasstype + " name="+actKey[i]+" size="+actKeySize[i]+" maxlength="+actKeySize[i]+" value='<%="+getactKey[i]+"%>' >\n");
             }

             if ( !actPmtPgm[i].equals("") && actPmtPgm[i].length() != 6 &&  actDspChk[i].equals("false")  ) {
 sbird.append("      <a href=\"javascript:open_window('win1','<%=HTTPSERVER%>/webpages/common/prompt/"+actPmtPgm[i]+"',50,50,700,350,0,0,0,1,0);\" onfocus=this.blur()> \n");
 sbird.append("      <img src=\"<%=HTTPSERVER%>/webpages/<%=imageFolder%>/icon/arrow.gif\" border=0 align=\"absmiddle\" alt=����></a> \n");
             }     

 sbird.append("    </td> \n");
 if( i%3 == 0 || i == actCount ) sbird.append("   </tr> \n");
 sbird.append(" \n");
            }
 sbird.append("  </table> \n");

 sbird.append("  <table border=\"0\" width=\"98%\" cellpadding=\"2\"> \n");
 sbird.append("    <tr> \n");
 sbird.append("    <td width=\"100%\" align=\"right\"> \n");
     if ( frameType.equals("false")) {
 sbird.append("    <input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_List.gif); border:0; width:85;height:22;cursor:hand;\" onClick='javascript:submit_d1(\"<%=listJsp%>\")'>  \n");
     } // frameType if end
 sbird.append("    <input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_Modify.gif); border:0; width:85;height:22;cursor:hand;\" onClick='javascript:submit_u1(\"update1_ok\")'>  \n");
     if ( flowType.equals("false")) {
 sbird.append("    <input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_Previous.gif); border:0; width:85;height:22;cursor:hand;\" onClick='javascript:history.back(1)'> \n");
     } else if( flowType.equals("true")) {
 sbird.append("    <input type=button value='' style=\"background: url(../../<%=imageFolder%>/icon/btn_Delete.gif); border:0; width:85;height:22;cursor:hand;\" onClick='javascript:submit_x1(\"delete1_ok\")'> \n");
     }
 sbird.append("    </td> \n");
 sbird.append("    </tr> \n");
 sbird.append("  </table> \n");
 sbird.append("</form>  \n");
 sbird.append("</body>  \n");
 sbird.append("</html>  \n");

    update1_jsp = sbird.toString();
    sbird = new StringBuffer(); // ����� bird�� ���y��....




 
//************ action java.java ����***********************************
 sbird.append("package " + prjRoot + "."+packageName+";  \n");
 sbird.append("  \n");
 sbird.append("import org.jsn.jdf.*;  \n");
 sbird.append("import org.jsn.jdf.db.*;  \n");
 sbird.append("import org.jsn.jdf.jtx.*;  \n");
 sbird.append("import org.jsn.jdf.servlet.*;  \n");
 sbird.append("  \n");
 sbird.append("import javax.servlet.*;  \n");
 sbird.append("import javax.servlet.http.*;  \n");
 sbird.append("import java.io.*;  \n");
 sbird.append("import java.net.*;  \n");
 sbird.append("import java.util.Vector;  \n");
 sbird.append("  \n");
 sbird.append("import " + prjRoot + "." + prjMst + ".*;  \n");
 if( packageUsed.equals("YES") ) sbird.append("import " + prjRoot + "." + prjMst + "." + packageName + ".*;  \n");
 
 sbird.append("import " + prjRoot + ".base.*;  \n");
 sbird.append("  \n");
 sbird.append("public class "+allPgmName+" extends HttpServlet{  \n");
 sbird.append("  \n");
 sbird.append("public void init(ServletConfig sc) throws ServletException {  \n");
 sbird.append("    super.init(sc);  \n");
 sbird.append("} // init  \n");
 sbird.append("  \n");
 sbird.append("public void destroy(){  \n");
 sbird.append("    super.destroy();  \n");
 sbird.append("}// destory  \n");
 sbird.append("  \n");
 sbird.append("public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  \n");
 sbird.append("    try {  \n");
 sbird.append("        performTask(request,response);  \n");
 sbird.append("    } catch(Throwable e ) {  \n");
 sbird.append("        e.printStackTrace(System.err);  \n");
 sbird.append("        System.err.flush();  \n");
 sbird.append("  } // end try                     \n");
 sbird.append("}//end doGet  \n");
 sbird.append("  \n");
 sbird.append("public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  \n");
 sbird.append("    try {  \n");
 sbird.append("        performTask(request,response);  \n");
 sbird.append("    } catch(Throwable e) {  \n");
 sbird.append("        e.printStackTrace(System.err);  \n");
 sbird.append("        System.err.flush();  \n");
 sbird.append("    } // try-catch  \n");
 sbird.append("}//end doPost  \n");
 sbird.append("  \n");
 sbird.append("//=============================================================================  \n");
 sbird.append("protected void performTask (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  \n");
 sbird.append("String cmd = request.getParameter(\"cmd\");  \n");
 sbird.append("try {        if ( cmd.equals( \"add1_ok\" ) )     { insert"+fileName1+"(request, response);  \n");
 sbird.append("      } else if ( cmd.equals( \"update1_ok\" ) )  { update"+fileName1+"(request, response);   \n");
 sbird.append("      } else if ( cmd.equals( \"delete1_ok\" ) )  { delete"+fileName1+"(request, response);  \n");
 if( editType.equals("true") ) 
 sbird.append("      } else if ( cmd.equals( \"update1_list\" ) )  { update1_list(request, response);  \n");
 sbird.append("      }  \n");
 sbird.append(" } catch(Throwable e ) {  \n");
 sbird.append("    e.printStackTrace(System.err);  \n");
 sbird.append("     System.err.flush();  \n");
 sbird.append(" } // end try  \n");
 sbird.append("}//end performTask  \n");
 sbird.append("  \n");
 sbird.append("//================================================================================  \n");
 
 // ����Ʈ���� ��b update�� .. 
 
if( editType.equals("true") ) {
 sbird.append("public  void update1_list(HttpServletRequest request, HttpServletResponse response) throws Exception {  \n");
 sbird.append("    response.setContentType(\"text/html;charset=utf-8\");  \n");
 sbird.append("    PrintWriter out = response.getWriter();  \n");
 sbird.append("    BesUtil bu = new BesUtil();   \n");
 sbird.append("    Box box = HttpUtility.getBox(request);   \n");
 sbird.append("    String homeJsp   = bu.HTTPSERVER + \"/webpages/"+packageName+"/"+allPgmName+"/index.jsp\" ;  \n");
 sbird.append("    String listJsp   = bu.HTTPSERVER + \"/webpages/"+packageName+"/"+allPgmName+"/list1.jsp\" ;  \n");
 sbird.append("    String listCount = request.getParameter(\"listCount\");   \n");
        
 sbird.append("    String screen = \"display\";  \n");
     
 sbird.append("    String posName = request.getParameter(\"posName\");   \n");
 sbird.append("    String posField = request.getParameter(\"posField\");   \n");
 sbird.append("    String posValue = request.getParameter(\"posValue\");   \n");
 sbird.append("    String posOrder = request.getParameter(\"posOrder\");   \n");
 sbird.append("    String current_Page = request.getParameter(\"current_Page\");   \n");
 sbird.append("    String trmid = request.getParameter(\"trmid\");   \n");
 sbird.append("    String usrid = request.getParameter(\"usrid\");   \n");
 sbird.append("    String callParameter = \"\";\n");    	
    	for (int i = 0; i < actFndChk.length; i++ ) {
 sbird.append("    String "+actFndChk[i] + " = BesUtil.chNull(request.getParameter(\"" + actFndChk[i] + "\"));   \n");
 sbird.append("    callParameter += \"&" + actFndChk[i] + "=\" + " + actFndChk[i] + ";\n"); 
    	}
 sbird.append("    String parm1 = BesUtil.chNull(request.getParameter(\"parm1\"));   \n");
 sbird.append("    callParameter += \"&parm1=\" + parm1;\n"); 
    	
 sbird.append(" \n");
 sbird.append("    String errMsg = \"\";\n");    	
 sbird.append("    int curdate = BesUtil.getDay();\n");    	
 sbird.append(" \n");
 
 sbird.append("	TransactionalResource resource = null;  \n");
 sbird.append("	UserTransaction tx = null;  \n");
 
 
 for (int i = 1; i <= primeCount; ++i) {
 sbird.append("	String "+primeKey[i]+" = \"\";\n"); 
        }
 sbird.append("	try {  \n");
 sbird.append("		resource = new " + prjRoot + ".db.BesConnResourceTx();   \n");
 sbird.append("		tx = resource.getUserTransaction();  \n");
 sbird.append("		" + entityName1 + " " + entity1 + " = new " + entityName1 +"() ; \n");
 sbird.append("		" + dbWrapName1 + " " + dbwrap1 + " = new "+ dbWrapName1 + "(resource); \n");
 sbird.append("		for( int lc=1; lc<=Integer.parseInt(listCount); lc++ ) { \n");
 			
		for (int i = 1; i <= primeCount; ++i) {
 sbird.append("			" + primeKey[i] + " = request.getParameter(\"" +primeKey[i]+"\"+String.valueOf(lc)); \n");
        }
 sbird.append("			String check = request.getParameter(\"check__\"+String.valueOf(lc));\n"); 
 sbird.append("			if(check == null || !check.equals(\"Y\")) continue; \n\n"); 
 
 
 sbird.append("			" + entity1 + " = " + dbwrap1 + ".select(");
 
 for (int i = 1; i <= primeCount; ++i) {
 	if( i != 1 ) sbird.append(", ");
 	if( primeKeyType[i].equals("int") ) sbird.append("Integer.parseInt(" + primeKey[i] + ")" ); 
 	else if( primeKeyType[i].equals("double") ) sbird.append("Double.parseDouble(" + primeKey[i] + ")" ); 
 	else if( primeKeyType[i].equals("long") ) sbird.append("Long.parseLong(" + primeKey[i] + ")" ); 
 	else sbird.append(primeKey[i]); 
 
 }
 sbird.append("); \n");
 sbird.append("			Utility.fixNullAndTrim("+ entity1 + "); \n");
 
 for (int i = 1; i <= listCount; i++ ) {
 	if( !getTableValue(listKey[i], COL_EDIT_MAKE).equals("") ) {
 		String gettingStr = ""; 
 		
 		if( listKeyType[i].trim().equals("double") ) gettingStr = " Double.parseDouble(request.getParameter(\"" +listKey[i] + "\" + String.valueOf(lc)))"; 
 		else if( listKeyType[i].trim().equals("int") ) gettingStr =  " Integer.parseInt(request.getParameter(\"" +listKey[i] + "\" + String.valueOf(lc)))"; 
 		else gettingStr = " request.getParameter(\"" +listKey[i] + "\" + String.valueOf(lc))"; 
 sbird.append("			" + entity1 + ".set" + initialCapital(listKey[i]) + "( " + gettingStr + "); \n");
 
 	}
 }
 sbird.append("			" + dbwrap1 + ".update("+ entity1+"); \n");
 sbird.append("		}\n"); 
 sbird.append("		tx.commit(); \n"); 

 sbird.append("    } catch(Exception e) {  \n");
 sbird.append("		tx.rollback(); \n"); 
 sbird.append("        if( errMsg.equals(\"\") ) {   \n");
 sbird.append("        	e.printStackTrace();  \n");
 sbird.append("        	errMsg = e.toString();   \n");
 sbird.append("        }  \n");
 sbird.append("    } finally {  \n");
 sbird.append("        if ( resource != null ) resource.release();  \n");
 sbird.append("    } // end try-catch-finally  \n");
 sbird.append("  \n");
     if ( frameType.equals("false")) {
 sbird.append("       response.sendRedirect(listJsp+\"?screen=\"+screen");
     } else if( frameType.equals("true")) {
 sbird.append("       response.sendRedirect(listJsp+\"?posName=\"+URLEncoder.encode(posName,\"utf-8\")+\"&posField=\"+posField+\"&posValue=\"+URLEncoder.encode(posValue,\"utf-8\")+callParameter+\"&posOrder=\"+posOrder+\"&current_Page=\"+current_Page+\"&screen=\" + screen + \"&errMsg=\"+URLEncoder.encode(errMsg,\"utf-8\")");
     } // frameType if end
        for (int i = 1; i <= primeCount; ++i) {
 sbird.append("+\"&"+primeKey[i] + "=\"+" + primeKey[i] );
        } // end for
 sbird.append(") ; \n");
 sbird.append("  \n");
 sbird.append("} // end update"+fileName1+"  \n");
 sbird.append("  \n");
 } // end if editType.. 
 
 sbird.append("//================================================================================  \n"); 		
 sbird.append("public  void insert"+fileName1+"(HttpServletRequest request, HttpServletResponse response) throws Exception {  \n");
 sbird.append("    response.setContentType(\"text/html;charset=utf-8\");  \n");
 sbird.append("    PrintWriter out = response.getWriter();  \n");
 sbird.append("    BesUtil bu = new BesUtil();   \n");
 sbird.append("    Box box = HttpUtility.getBox(request);   \n");
 sbird.append("    String homeJsp   = bu.HTTPSERVER + \"/webpages/"+packageName+"/"+allPgmName+"/index.jsp\" ;  \n");
        for (int i = 1; i <= primeCount; ++i) {
 sbird.append("    "+primeKeyType[i]+" "+primeKey[i]+" = box.get"+initialCapital(primeKeyType[i])+"(\"" +primeKey[i]+"\"); \n");
        }
 sbird.append("    String screen = \"display\";  \n");
     
 sbird.append("    String posName = request.getParameter(\"posName\");   \n");
 sbird.append("    String posField = request.getParameter(\"posField\");   \n");
 sbird.append("    String posValue = request.getParameter(\"posValue\");   \n");
 sbird.append("    String posOrder = request.getParameter(\"posOrder\");   \n");
 sbird.append("    String current_Page = request.getParameter(\"current_Page\");   \n");
 sbird.append("    String usrid = request.getParameter(\"usrid\");   \n");
 sbird.append("    String callParameter = \"\";\n");    	
    	for (int i = 0; i < actFndChk.length; i++ ) {
 sbird.append("    String "+actFndChk[i] + " = BesUtil.chNull(request.getParameter(\"" + actFndChk[i] + "\"));   \n");
 sbird.append("    callParameter += \"&" + actFndChk[i] + "=\" + " + actFndChk[i] + ";\n"); 
    	}
 sbird.append("    String parm1 = BesUtil.chNull(request.getParameter(\"parm1\"));   \n");
 sbird.append("    callParameter += \"&parm1=\" + parm1;\n"); 

 sbird.append(" \n");
 sbird.append(" \n");
 sbird.append("    String errMsg = \"\";\n");    
 sbird.append("    int curdate = BesUtil.getDay();\n");    		
 sbird.append(" \n");
 

 sbird.append("	" + entityName1 + " " + entity1 + " = new " + entityName1 +"() ; \n");
 sbird.append("	box.copyToEntity("+ entity1 + "); \n");
 sbird.append("	Utility.fixNullAndTrim("+ entity1 + "); \n");
 
 sbird.append("	TransactionalResource resource = null;  \n");
 sbird.append("	UserTransaction tx = null;  \n");
 sbird.append("	BesInit bi = new BesInit();  \n");
 sbird.append("	UserInfo ui = bi.checkSession(request , response); \n");
 
 sbird.append("	try {  \n");
 sbird.append("		resource = new " + prjRoot + ".db.BesConnResourceTx();   \n");
 sbird.append("		tx = resource.getUserTransaction();  \n");
 sbird.append("        " + dbWrapName1 + " " + dbwrap1 + " = new "+ dbWrapName1 + "(resource); \n");
 sbird.append("/** \n");
 sbird.append(" * ������ �ߺ��� alert â; ��� ��� �� ������ ���� \n");
 sbird.append(" */ \n");
 sbird.append("    int cnt = "+dbwrap1+".count(");
 for(int i = 1; i<=primeCount; i++){
   sbird.append(""+primeKey[i]+"");
   if(i < primeCount){
     sbird.append(",");
   }
 }
 sbird.append("); \n");
 sbird.append("	if(cnt == 0){ \n");
 sbird.append("        // System Colulms.. \n"); 
 sbird.append("        " + entity1 + ".setAdate(curdate); \n"); 
 sbird.append("        " + entity1 + ".setAuser(usrid); \n"); 
 sbird.append("        " + entity1 + ".setMdate(curdate); \n"); 
 sbird.append("        " + entity1 + ".setMuser(usrid); \n"); 
 sbird.append("		" + dbwrap1 + ".insert("+ entity1+"); \n");
 sbird.append("		tx.commit(); \n"); 

     if ( frameType.equals("false")) {
 sbird.append("      response.sendRedirect(homeJsp+\"?screen=\"+screen");
     } else if( frameType.equals("true")) {
 sbird.append("      response.sendRedirect(homeJsp+\"?posName=\"+URLEncoder.encode(posName,\"utf-8\")+\"&posField=\"+posField+\"&posValue=\"+URLEncoder.encode(posValue,\"utf-8\")+callParameter+\"&posOrder=\"+posOrder+\"&current_Page=\"+current_Page+\"&screen=\"+screen + \"&errMsg=\"+URLEncoder.encode(errMsg,\"utf-8\")");
     } // frameType if end
        for (int i = 1; i <= primeCount; ++i) {
 sbird.append("+\"&"+primeKey[i] + "=\"+" + primeKey[i] );
        } // end for
 sbird.append(") ; \n");
 sbird.append("    }else{ \n");
 sbird.append("      tx.rollback(); \n"); 
 
 sbird.append("      out.println(\"<script language='javascript'>\"); \n");
 sbird.append("      out.println(\"  alert('\" + BesDBUtil.getMessage(\"bsc128\",ui) + \"');\"); \n");
 sbird.append("      out.println(\"  history.back(-1);\"); \n");
 sbird.append("      out.println(\"</script>\"); \n");
 sbird.append("    } \n");
 sbird.append("    } catch(Exception e) {  \n");
 sbird.append("        tx.rollback(); \n"); 
 sbird.append("        out.println(e.getMessage()+\"<br>\"+e.toString());  \n");
 sbird.append("        e.printStackTrace();  \n");
 sbird.append("        out.println(box.toString());  \n");
 sbird.append("    } finally {  \n");
 sbird.append("        if ( resource != null ) resource.release();  \n");
 sbird.append("    } // end try-catch-finally  \n");
 sbird.append("  \n");
 sbird.append("} // end insert"+fileName1+"  \n");
 sbird.append("  \n");
 
 
 sbird.append("//================================================================================  \n");
 sbird.append("public  void update"+fileName1+"(HttpServletRequest request, HttpServletResponse response) throws Exception {  \n");
 sbird.append("    response.setContentType(\"text/html;charset=utf-8\");  \n");
 sbird.append("    PrintWriter out = response.getWriter();  \n");
 sbird.append("    BesUtil bu = new BesUtil();   \n");
 sbird.append("    Box box = HttpUtility.getBox(request);   \n");
 sbird.append("    String homeJsp   = bu.HTTPSERVER + \"/webpages/"+packageName+"/"+allPgmName+"/index.jsp\" ;  \n");
        for (int i = 1; i <= primeCount; ++i) {
 sbird.append("    "+primeKeyType[i]+" "+primeKey[i]+" = box.get"+initialCapital(primeKeyType[i])+"(\"" +primeKey[i]+"\"); \n");
        }
 sbird.append("    String screen = \"display\";  \n");
     
 sbird.append("    String posName = request.getParameter(\"posName\");   \n");
 sbird.append("    String posField = request.getParameter(\"posField\");   \n");
 sbird.append("    String posValue = request.getParameter(\"posValue\");   \n");
 sbird.append("    String posOrder = request.getParameter(\"posOrder\");   \n");
 sbird.append("    String current_Page = request.getParameter(\"current_Page\");   \n");
 sbird.append("    String usrid = request.getParameter(\"usrid\");   \n");
 sbird.append("    String callParameter = \"\";\n");    	
    	for (int i = 0; i < actFndChk.length; i++ ) {
 sbird.append("    String "+actFndChk[i] + " = BesUtil.chNull(request.getParameter(\"" + actFndChk[i] + "\"));   \n");
 sbird.append("    callParameter += \"&" + actFndChk[i] + "=\" + " + actFndChk[i] + ";\n"); 
    	}
 sbird.append("    String parm1 = BesUtil.chNull(request.getParameter(\"parm1\"));   \n");
 sbird.append("    callParameter += \"&parm1=\" + parm1;\n"); 
    	
 sbird.append(" \n");
     
 sbird.append("    String errMsg = \"\";\n");    	
 sbird.append("    int curdate = BesUtil.getDay();\n");    	
 sbird.append(" \n");
 
 sbird.append("    " + entityName1 + " " + entity1 + "Box = new " + entityName1 +"() ; \n");
 sbird.append("    box.copyToEntity("+ entity1 + "Box); \n");
 sbird.append("    Utility.fixNullAndTrim("+ entity1 + "Box); \n");
 
 sbird.append("	TransactionalResource resource = null;  \n");
 sbird.append("	UserTransaction tx = null;  \n");
 
 sbird.append("    try {  \n");
 
 sbird.append("		resource = new " + prjRoot + ".db.BesConnResourceTx();   \n");
 sbird.append("		tx = resource.getUserTransaction();  \n");
 
 sbird.append("        " + dbWrapName1 + " " + dbwrap1 + " = new "+ dbWrapName1 + "(resource); \n");
 
 sbird.append("        " + entityName1 + " " + entity1 + " = " + dbwrap1 + ".select("); 
 		for (int i = 1; i <= primeCount; ++i) {
 			if( i != 1 ) sbird.append(", "); 
 sbird.append(primeKey[i]); 
 		}
 sbird.append(");\n ");  	
		 
sbird.append("        // System Colulms.. \n"); 
sbird.append("        " + entity1 + ".setMdate(curdate); \n"); 
sbird.append("        " + entity1 + ".setMuser(usrid); \n"); 

//sbird.append("        " + entity1 + ".setTrmid(" + entity1 + "Box.getTrmid()); \n\n"); 
sbird.append("        // Editable Colulms.. \n"); 		
		for (int i = 1; i <= actCount; ++i) {
			if ( actDspChk[i].equals("false") ) {
sbird.append("        " + entity1 + ".set" + initialCapital(actKey[i]) + "(" + entity1 + "Box.get" + initialCapital(actKey[i]) + "()); \n"); 
            }
        }
        
 sbird.append("        " + dbwrap1 + ".update("+ entity1+"); \n");
 sbird.append("        tx.commit(); \n"); 
 
 sbird.append("    } catch(Exception e) {  \n");
 sbird.append("        tx.rollback(); \n"); 
 sbird.append("        if( errMsg.equals(\"\") ) {   \n");
 sbird.append("        	e.printStackTrace();  \n");
 sbird.append("        	errMsg = e.toString();   \n");
 sbird.append("        }  \n");
 sbird.append("    } finally {  \n");
 sbird.append("        if ( resource != null ) resource.release();  \n");
 sbird.append("    } // end try-catch-finally  \n");
 sbird.append("  \n");
     if ( frameType.equals("false")) {
 sbird.append("       response.sendRedirect(homeJsp+\"?screen=\"+screen");
     } else if( frameType.equals("true")) {
 sbird.append("       response.sendRedirect(homeJsp+\"?posName=\"+URLEncoder.encode(posName,\"utf-8\")+\"&posField=\"+posField+\"&posValue=\"+URLEncoder.encode(posValue,\"utf-8\")+callParameter+\"&posOrder=\"+posOrder+\"&current_Page=\"+current_Page+\"&screen=\"+screen + \"&errMsg=\"+URLEncoder.encode(errMsg,\"utf-8\")");
     } // frameType if end
        for (int i = 1; i <= primeCount; ++i) {
 sbird.append("+\"&"+primeKey[i] + "=\"+" + primeKey[i] );
        } // end for
 sbird.append(") ; \n");
 sbird.append("  \n");
 sbird.append("} // end update"+fileName1+"  \n");
 sbird.append("  \n");
 sbird.append("//================================================================================  \n");
 sbird.append("public  void delete"+fileName1+"(HttpServletRequest request, HttpServletResponse response) throws Exception {  \n");
 sbird.append("    response.setContentType(\"text/html;charset=utf-8\");  \n");
 sbird.append("    PrintWriter out = response.getWriter();  \n");
 sbird.append("    BesUtil bu = new BesUtil();   \n");
 sbird.append("    Box box = HttpUtility.getBox(request);   \n");
 sbird.append("    String homeJsp   = bu.HTTPSERVER + \"/webpages/"+packageName+"/"+allPgmName+"/index.jsp\" ;  \n");
        for (int i = 1; i <= primeCount; ++i) {
 sbird.append("    "+primeKeyType[i]+" "+primeKey[i]+" = box.get"+initialCapital(primeKeyType[i])+"(\"" +primeKey[i]+"\"); \n");
        }
 sbird.append("    String screen = \"addscreen\";  \n");
     
 sbird.append("    String posName = request.getParameter(\"posName\");   \n");
 sbird.append("    String posField = request.getParameter(\"posField\");   \n");
 sbird.append("    String posValue = request.getParameter(\"posValue\");   \n");
 sbird.append("    String posOrder = request.getParameter(\"posOrder\");   \n");
 sbird.append("    String current_Page = request.getParameter(\"current_Page\");   \n");
 sbird.append("    String callParameter = \"\";\n");    	
 sbird.append("    String errMsg = \"\";\n");    	
    	for (int i = 0; i < actFndChk.length; i++ ) {
 sbird.append("    String "+actFndChk[i] + " = BesUtil.chNull(request.getParameter(\"" + actFndChk[i] + "\"));   \n");
 sbird.append("    callParameter += \"&" + actFndChk[i] + "=\" + " + actFndChk[i] + ";\n"); 
    	}
 sbird.append("    String parm1 = BesUtil.chNull(request.getParameter(\"parm1\"));   \n");
 sbird.append("    callParameter += \"&parm1=\" + parm1;\n"); 
    	
 sbird.append(" \n");
     
 sbird.append("  \n");
 sbird.append("  \n");

 sbird.append("    " + entityName1 + " " + entity1 + " = new " + entityName1 +"() ; \n");
 sbird.append("    box.copyToEntity("+ entity1 + "); \n");
 sbird.append("    Utility.fixNullAndTrim("+ entity1 + "); \n");
 sbird.append("	TransactionalResource resource = null;  \n");
 sbird.append("	UserTransaction tx = null;  \n");
 sbird.append("    try {  \n");
 sbird.append("		resource = new " + prjRoot + ".db.BesConnResourceTx();   \n");
 sbird.append("		tx = resource.getUserTransaction();  \n");
 sbird.append("        " + dbWrapName1 + " " + dbwrap1 + " = new "+ dbWrapName1 + "(resource); \n");
 sbird.append("        " + dbwrap1 + ".delete("+ entity1+"); \n");
 sbird.append("        tx.commit(); \n"); 
 sbird.append("    } catch(Exception e) {  \n");
 sbird.append("        tx.rollback(); \n"); 
 sbird.append("        if( errMsg.equals(\"\") ) {   \n");
 sbird.append("        	e.printStackTrace();  \n");
 sbird.append("        	errMsg = e.toString();   \n");
 sbird.append("        }  \n");
 sbird.append("    } finally {  \n");
 sbird.append("        if ( resource != null ) resource.release();  \n");
 sbird.append("    } // end try-catch-finally  \n");
 sbird.append("  \n");
     if ( frameType.equals("false")) {
 sbird.append("       response.sendRedirect(homeJsp) ;");
     } else if( frameType.equals("true")) {
 sbird.append("       response.sendRedirect(homeJsp+\"?posName=\"+URLEncoder.encode(posName,\"utf-8\")+\"&posField=\"+posField+\"&posValue=\"+URLEncoder.encode(posValue,\"utf-8\")+callParameter+\"&posOrder=\"+posOrder+\"&current_Page=\"+current_Page+\"&screen=\"+screen + \"&errMsg=\"+URLEncoder.encode(errMsg,\"utf-8\"));");
     } // frameType if end
 sbird.append("  \n");
 sbird.append("//================================================================================  \n");
 sbird.append(" } \n");
 sbird.append("}// end Class  \n");

    action_java = sbird.toString();
    sbird = new StringBuffer(); // ����� bird�� ���y��....



//=====Rec.java dbrec method==========================================

    if( g_isFromQuery )
    {
    	tableName=allPgmName.toUpperCase().trim();
    	entity=allPgmName.toUpperCase().trim();
    	entityName=allPgmName.toUpperCase().trim()+"Rec";
    }
    if( packageUsed.equals("YES") ) sbird.append("package " + prjRoot + "." + prjMst + "." + packageName + " ;\n\n");
    else  sbird.append("package " + prjRoot + "." + prjMst + " ;\n\n");
    if(g_isFromQuery)
    {
    	sbird.append("// Entity Class for " + allPgmName + "\n");
    }
    else
    {
    	sbird.append("// Entity Class for " + tableName + "\n");
    }
    sbird.append("/**\n");
    sbird.append(" *\n");
    if(g_isFromQuery)
    {
    	sbird.append(" * @(#) "+allPgmName+".java\n");
    }
    else
    {
    	sbird.append(" * @(#) "+entityName+".java\n");
    }
    sbird.append(" * Copyright 1999-2001 by  Daewoo Information System, Inc.,\n");
    sbird.append(" * BES(Best Enterprise System) Team,\n");
    sbird.append(" * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea\n");
    sbird.append(" * All rights reserved.\n");
    sbird.append(" *\n");
    sbird.append(" * NOTICE !  You cannot copy or redistribute this code,\n");
    sbird.append(" * and you should not remove the information about the\n");
    sbird.append(" * copyright notice and the author.\n");
    sbird.append(" *\n");
    sbird.append(" * @version v0.1\n");
    sbird.append(" * @date    "+nowDate+"\n");
    sbird.append(" * @author  WonDeok Kim, wdkim(at)disc.co.kr.\n");
    sbird.append(" * @since   JDK1.2\n");
    sbird.append(" *\n");
    sbird.append(" */\n\n");
    sbird.append("import org.jsn.jdf.db.*;\n\n");

// Class Define, JDF�� ��� ������ EntityData�� extends�մϴ�.
    sbird.append("public class "+tableName+"Rec extends EntityData {\n");
    sbird.append("    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) \n");
// Var Define
    ColumnType ="";
    for (int i = 1; i <= colCount; i++) {
        ColumnType = colType[i];
        sbird.append("    public " + ColumnType + " " + colLabel[i].toLowerCase()+"; \t\t// ("+colTypeName[i]+", "+colPrecision[i]+"."+colScale[i]+")\n");

    } // end for
    sbird.append("\n");

// Constructor
 
        sbird.append("public "+tableName+"Rec(){ } // default constructor\n\n");
        sbird.append("public "+tableName+"Rec(");

    for (int i = 1; i <= colCount; i++) {
        if(i%6 == 1) {sbird.append("\n       ");}
        ColumnType = colType[i];
        sbird.append(ColumnType + " " + colLabel[i].toLowerCase());
        if(i < colCount) {sbird.append(", ");}
    } // end for
    sbird.append("){\n");
    for (int i = 1; i <= colCount; i++){
        sbird.append("    this."+colLabel[i].toLowerCase() + " = " + colLabel[i].toLowerCase() + ";\n");
    }// end for
    sbird.append("} // Constructor\n");
    sbird.append("\n");
    sbird.append("\n// Getter \n");
// getter
    for (int i = 1; i <= colCount; i++) {
        ColumnType = colType[i];
        sbird.append("public " + ColumnType + " get" +initialCapital(colLabel[i])+"(){ return " + colLabel[i].toLowerCase()+";}\n");

    } // end for
    sbird.append("\n// Setter \n");
// setter
    for (int i = 1; i <= colCount; i++) {
        ColumnType = colType[i];
        sbird.append("public void set" +initialCapital(colLabel[i])+"("+ColumnType+" "+colLabel[i].toLowerCase()+"){ this." + colLabel[i].toLowerCase()+" = "+colLabel[i].toLowerCase()+";}\n");
    } // end for

// getString(int)
    sbird.append("\n/**\n");
    sbird.append("* getString \n");
    sbird.append("* @param int seq \n");
    sbird.append("* @return field Value \n");
    sbird.append("*/\n");
    sbird.append("public String getString(int seq){ \n");
    sbird.append(" String field=null;\n");
    sbird.append("  switch (seq) {\n");
        for (int i = 1; i <= colCount; i++) {
            sbird.append("  case  "+i+" : field = " +colLabel[i].toLowerCase()+" + \"\" ; break;\n");
        } // end for
    sbird.append("  } // end switch\n");
    sbird.append("  return field;\n");
    sbird.append("}// end getString (int seq)\n");

// getString(String)
    sbird.append("\n/**\n");
    sbird.append("* getString \n");
    sbird.append("* @param String fieldName \n");
    sbird.append("* @return field Value \n");
    sbird.append("*/\n");
    sbird.append("public String getString(String rec){ \n");
    sbird.append(" String field=null;\n");
    for (int i = 1; i <= colCount; i++) {
        if(i == 1) {
            sbird.append("     if       (rec.equalsIgnoreCase(\""+colLabel[i].toLowerCase()+"\"))");
         } else {
            sbird.append("     } else if(rec.equalsIgnoreCase(\""+colLabel[i].toLowerCase()+"\"))");
         } //end if
            sbird.append("{ field = " +colLabel[i].toLowerCase()+" + \"\" ; \n");
     } // end for
    sbird.append("}// end if\n");
    sbird.append(" return field;\n");
    sbird.append("}// end getString (String fieldName)\n");
    sbird.append("\n");

// return Field Name
    sbird.append("/**\n");
    sbird.append("* fieldNames \n");
    sbird.append("* @param none \n");
    sbird.append("* @return field Names[]\n");
    sbird.append("*/\n");
    sbird.append("public String[] fieldNames() {\n");
    sbird.append("    String [] tempx = {\"\", ");
    for (int i = 1; i <= colCount; i++) {
        sbird.append("\"" + colLabel[i].toUpperCase() + "\"");
        if(i < colCount) {sbird.append(", ");}
        if(i%7 == 0) {sbird.append("\n       ");}
    } // end for
    sbird.append("};\n");
    sbird.append("    return tempx;\n}\n\n");

// return Key Field Name
    sbird.append("/**\n");
    sbird.append("* Key fieldNames \n");
    sbird.append("* @param none \n");
    sbird.append("* @return Key field Names[]\n");
    sbird.append("*/\n");
    sbird.append("public String[] keyFieldNames() {\n");
    sbird.append("    String [] tempx = {\"\", ");
    for(int i = 1 ; i <= primeCount ; i++){
        sbird.append("\"" + primeKey[i].toUpperCase() + "\"");
        if(i < primeCount) {sbird.append(", ");}
        if(i%7 == 0) {sbird.append("\n       ");}
    } // end for
    sbird.append("};\n");
    sbird.append("    return tempx;\n}\n\n");
    sbird.append("}// end "+entityName+" class");

    dbrec_java = sbird.toString();
    sbird = new StringBuffer(); // ����� bird�� ���y��....



//==================================================
// start
// DBWrapper Class xxWrapBES.java start 
// Header

    if( packageUsed.equals("YES") ) sbird.append("package " + prjRoot + "." + prjMst + "." + packageName + ";\n\n");
    else sbird.append("package " + prjRoot + "." + prjMst + ";\n\n");
    sbird.append("// DBWrapper Class for " + tableName + "\n");
    sbird.append("/**\n");
    sbird.append(" *\n");
    sbird.append(" * @(#) "+fileName+"DBWrapBES.java\n");
    sbird.append(" * Copyright 1999-2001 by  Daewoo Information System, Inc.,\n");
    sbird.append(" * BES(Best Enterprise System) Team,\n");
    sbird.append(" * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea\n");
    sbird.append(" * All rights reserved.\n");
    sbird.append(" *\n");
    sbird.append(" * NOTICE !  You cannot copy or redistribute this code,\n");
    sbird.append(" * and you should not remove the information about the\n");
    sbird.append(" * copyright notice and the author.\n");
    sbird.append(" *\n");
    sbird.append(" * @version v0.1\n");
    sbird.append(" * @date    "+nowDate+"\n");
    sbird.append(" * @author  WonDeok Kim, wdkim(at)disc.co.kr.\n");
    sbird.append(" * @since   JDK1.2\n");
    sbird.append(" *\n");
    sbird.append(" */\n\n");
    sbird.append("import java.sql.*;\n");
    sbird.append("import org.jsn.jdf.db.*;\n\n");
        
// Class Define
    sbird.append("public class "+tableName+"DBWrapBES extends DBWrapper{\n");
    sbird.append("\n");

// Constructor
    sbird.append("public "+tableName+"DBWrapBES(ConnectionContext ctx){\n");
    sbird.append("    super(ctx);\n");
    sbird.append("} // Constructor\n");
    sbird.append("\n");
//  typecheck
    if(g_isFromQuery)
    {
    	   //selectall1
    	   sbird.append("/**\n");
    	    sbird.append("* Get selectAllWhere Record \n");
    	    sbird.append("* @param  String \n");
    	    sbird.append("* @return java.util.Vector \n");
    	    sbird.append("* @author besTeam \n");
    	    sbird.append("* @date " + nowDate + "\n");
    	    sbird.append("*/\n");
    	    sbird.append("public java.util.Vector selectAll"+tableName+"(String whereOption,String sortOption) throws Exception{\n");
    	    sbird.append("    java.util.Vector " + tableName.toLowerCase() + "V = new java.util.Vector();\n");
    	    sbird.append("    "+tableName+"Rec " + tableName.toLowerCase() + " = null;\n");
    	    sbird.append("    PreparedStatement pstmt = null;\n");
    	    sbird.append("    whereOption = whereOption.trim();\n");    
    	    sbird.append("    ResultSet rs = null;\n");
    	    sbird.append("    try{\n");
    	if( g_isFromQuery ) {
    		sbird.append("        String query = " );
    		sbird.append( g_writeQuery + "        + whereOption; \n" ); 
       	    sbird.append("        if(sortOption.equals(\"\")) " );
    	    sbird.append(" query += \" order by  ");
    	    sbird.append(baseTableAlias + primeKey[1]);
    	    for (int i = 2; i < primeCount; i++) sbird.append(","+baseTableAlias + primeKey[i]);
    	    sbird.append(" \" ;\n");
    	    sbird.append("        else query+=sortOption ;");
    	    sbird.append("\n");
    	    
    	} else {
    	    sbird.append("        String query = \"Select ");
    	    for (int i = 1; i <= colCount; i++) {
    	        if((i != 1 ) && (i%9 == 1)) {  sbird.append("\" +\n                              \"");}
    	        sbird.append(colLabel[i].toLowerCase());
    	        if(i < colCount) {sbird.append(", ");}
    	    } // end for
    	    sbird.append(" \" +\n                       \"  from "+fullTableName+" ");
    	    sbird.append(" where 1=1 ");
    	    sbird.append(" \" + whereOption ;\n");
    	    sbird.append(" \n                      if(sortOption.equals(\"\")) " );
    	    sbird.append(" query += \" order by  ");
    	    sbird.append(baseTableAlias + primeKey[1]);
    	    for (int i = 2; i < primeCount; i++) sbird.append(","+baseTableAlias + primeKey[i]);
    	    sbird.append(" \" ;\n");
    	    sbird.append("                         else query+=sortOption ;");
    	    sbird.append("\n");
    	}

    	    sbird.append("        pstmt = connection.prepareStatement(query); \n");
    	    sbird.append("        rs = pstmt.executeQuery();\n");
    	    sbird.append("\n");
    	    sbird.append("        while(rs.next()){\n");
    	    sbird.append("            " + tableName.toLowerCase() + " = new "+tableName+"Rec(); // "+tableName+"Rec Constructor\n");
    	        for (int i = 1; i <= colCount; i++) {
    	        ColumnType = colType[i];
    	            sbird.append("                    "); // Source���� �p�
    	            sbird.append(" " + tableName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(ColumnType)+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
    	        } // end for
    	    sbird.append("            " + tableName.toLowerCase() + "V.addElement(" + tableName.toLowerCase() + ");\n");
    	    sbird.append("        } // end While\n");
    	    sbird.append("    } finally {\n");
    	    sbird.append("        try{rs.close();}catch(Exception e){}\n");
    	    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    	    sbird.append("    } // try-finally\n");
    	    sbird.append("    return " + tableName.toLowerCase() + "V;\n");
    	    sbird.append("} // end selectAll\n");
    	    sbird.append("\n");
//    	  besselect1
    	    sbird.append("/**\n");
    	    sbird.append("* Select\n");
    	    sbird.append("* @param ");
    	    sbird.append(primeKeyType[1] + " " + primeKey[1]);
    	    for (int i = 2; i <= primeCount; i++) {
    	      sbird.append(", " + primeKeyType[i] + " " + primeKey[i] );
    	    } // end for
    	    sbird.append("\n");
    	    sbird.append("* @return "+tableName+"Rec \n");
    	    sbird.append("* @author besTeam \n");
    	    sbird.append("* @date " + nowDate + "\n");
    	    sbird.append("*/\n");
    	    sbird.append("public "+tableName+"Rec select"+tableName+"(");
    	    sbird.append(primeKeyType[1] + " " + primeKey[1]);
    	    for (int i = 2; i <= primeCount; i++) {
    	      sbird.append(", " + primeKeyType[i] + " " + primeKey[i] );
    	    } // end for   	       
    	    sbird.append(") throws Exception{\n");
    	    sbird.append("    java.util.Vector " + entity + "V = new java.util.Vector();\n");
    	    sbird.append("    " + entityName + " " + entity.toLowerCase() + " = null;\n");
    	    sbird.append("    PreparedStatement pstmt = null;\n");
    	    sbird.append("    ResultSet rs = null;\n");
    	    sbird.append("    try{\n");
       		sbird.append("        String query = " );
    		sbird.append( g_writeQuery); 
          	sbird.append("       +\""+ " and "+baseTableAlias + primeKey[1]+" = ? ");
    	    for (int i = 2; i < primeCount; i++) sbird.append(" and "+baseTableAlias + primeKey[i]+" = ? ");
    	    sbird.append(" \" ;\n");
    	    sbird.append("        pstmt = connection.prepareStatement(query);\n");

    	    for (int i = 1; i <= primeCount; i++) {
    	         sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
    	    } // end for

    	    sbird.append("        rs = pstmt.executeQuery();\n");
    	    sbird.append("        if(rs.next()) { \n");
    	    sbird.append("            " + entity.toLowerCase() + " = new "+entity+"Rec(); // "+entity+"Rec Constructor\n");
    	        for (int i = 1; i <= colCount; i++) {
    	            sbird.append("                    "); // Source���� �p�
    	            sbird.append(" " + entity.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(colType[i].toLowerCase())+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
    	        } // end for
    	    //sbird.append("            " + fileName.toLowerCase() + "V.addElement(" + fileName.toLowerCase() + ");\n");
    	    sbird.append("        } // end While\n");
    	    sbird.append("        else {  throw new DataNotFoundException();} \n");  
    	    sbird.append("    } finally {\n");
    	    sbird.append("        try{rs.close();}catch(Exception e){}\n");
    	    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    	    sbird.append("    } // try-finally\n");
    	    sbird.append("    return " + entity.toLowerCase() + ";\n");
    	    sbird.append("} // end select\n");
    	    sbird.append("\n");
    	    //count1
    	    sbird.append("/**\n");
    	    sbird.append("* Get Rows Count \n");
    	    sbird.append("* @param ");
    	    sbird.append(primeKeyType[1] + " " + primeKey[1]);
    	    for (int i = 2; i <= primeCount; i++) {
    	        sbird.append(", "+primeKeyType[i] + " " + primeKey[i]);
    	    } // end for
    	    sbird.append("\n");
    	    sbird.append("* @return int \n");
    	    sbird.append("* @author besTeam \n");
    	    sbird.append("* @date " + nowDate + "\n");
    	    sbird.append("*/\n");
    	    sbird.append("public int count"+tableName+"(");
    	    sbird.append(primeKeyType[1] + " ");
    	    sbird.append(primeKey[1]);
    	    for (int i = 2; i <= primeCount; i++) {
    	        sbird.append(", "+primeKeyType[i]+" " +primeKey[i]);
    	    } // end for

    	    sbird.append(") throws Exception{\n");
    	    sbird.append("    int count = 0;\n");
    	    sbird.append("    PreparedStatement pstmt = null;\n");
    	    sbird.append("    ResultSet rs = null;\n");
    	    sbird.append("    try{\n");
    	if( g_isFromQuery ) {
    		sbird.append("        String query = " );
    		sbird.append( g_countQuery ); 
    	    
    	    if( !g_isWhereExist ) sbird.append("		+ \" where ");
    	    else sbird.append("		+ \" and ");
    	} else {
    	    sbird.append("        String query = \"SELECT COUNT(*) from "+fullTableName+" \" +\n");
    	    sbird.append("                       \" where ");
    	}
    	    sbird.append(baseTableAlias + primeKey[1]+" = ? ");
    	    for (int i = 2; i <= primeCount; i++) {
    	        sbird.append("and "+baseTableAlias + primeKey[i]+" = ? ");
    	    } // end for

    	    sbird.append("  \";\n");
    	    sbird.append("        pstmt = connection.prepareStatement(query);\n");
    	    for (int i = 1; i <= primeCount; i++) {
    	        sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
    	    } // end for

    	    sbird.append("        rs = pstmt.executeQuery();\n");
    	    sbird.append("\n");
    	    sbird.append("        if(rs.next()){\n");
    	    sbird.append("            count = rs.getInt(1);\n");
    	    sbird.append("        } // end if\n");
    	    sbird.append("    } finally {\n");
    	    sbird.append("        try{rs.close();}catch(Exception e){}\n");
    	    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    	    sbird.append("    } // try-finally\n");
    	    sbird.append("    return count;\n");
    	    sbird.append("} // end count\n");
    	    sbird.append("\n}");
    	    



    }
    else
    {
//Select Page
// sbird.append("/**\n");
// sbird.append("* Select Page \n");
// sbird.append("* @param ");
// sbird.append(primeKeyType[1] + " " + primeKey[1]);
// sbird.append("\n");
// sbird.append("* @return java.util.Vector \n");
// sbird.append("* @author besTeam \n");
// sbird.append("* @date " + nowDate + "\n");
// sbird.append("*/\n");
// sbird.append("public java.util.Vector selectPage(");
//         if( primeKey[1].equals("facid")) {
// sbird.append(" String facid, ");
//         }
// sbird.append("String fldname, int page, int pagesize, String whereOption ) throws Exception{\n");
// sbird.append("return selectPage(");
//         if( primeKey[1].equals("facid")) {
// sbird.append("facid,");
//         }
// sbird.append("fldname,page,pagesize, \"Asc\", whereOption) ;\n");
// sbird.append("}// end selectPage\n");
//
// sbird.append("/**\n");
// sbird.append("* Select Page\n");
// sbird.append("* @param ");
// sbird.append(primeKeyType[1] + " " + primeKey[1]);
// sbird.append("* @return java.util.Vector \n");
// sbird.append("* @author besTeam \n");
// sbird.append("* @date " + nowDate + "\n");
// sbird.append("*/\n");
// sbird.append("public java.util.Vector selectPage(");
//  if(primeKey[1].equals("facid"))
//     sbird.append(" String facid,");
// sbird.append("String fldname, int page, int pagesize, String keyorder, String whereOption) throws Exception{\n");
// sbird.append("    java.util.Vector " + entity + "V = new java.util.Vector();\n");
// sbird.append("    " + entityName + " " + entity + " = null;\n");
// sbird.append("    PreparedStatement pstmt = null;\n");
// sbird.append("    ResultSet rs = null;\n");
// sbird.append("    try{\n");
// sbird.append("        String query = \"Select ");
// for(int j22 = 1; j22 <= colCount; j22++)
// {
//     if(j22 != 1 && j22 % 9 == 1)
//         sbird.append("\" +\n                              \"");
//     sbird.append(colLabel[j22].toLowerCase());
//     if(j22 < colCount)
//         sbird.append(", ");
// }
//
// sbird.append(" \" +\n                       \"  from " + fullTableName + " ");
// if(primeKey[1].equals("facid"))
// {
//     sbird.append(" \" +\n                       \"  where ");
//     sbird.append(primeKey[1] + " = ?    ");
//     sbird.append(" \" + whereOption");
// }else{
//		sbird.append(" where 1 = 1 \" +	whereOption");
//	}
// sbird.append("  +\n                       \"  order by \"+fldname+\" \"+keyorder; \n");
// 
// sbird.append("        pstmt = connection.prepareStatement(query);\n");
//         if( primeKey[1].equals("facid")) {
// sbird.append("        pstmt.set"+initialCapital(primeKeyType[1])+"(1,"+primeKey[1]+"); \n");
//         }
// sbird.append("        rs = pstmt.executeQuery();\n");
// sbird.append("        int count = 0;\n");
// sbird.append("        while((count < (page-1)*pagesize ) && ( rs.next())){  count ++; } // page��ŭ ��ŵ�ϱ�\n");
// sbird.append("            count = 0;\n");
// sbird.append("        while(rs.next()){\n");
// sbird.append("            count ++;\n");
// sbird.append("            if(count > pagesize ) break;\n");
// sbird.append("            " + fileName.toLowerCase() + " = new "+fileName+"Rec(); // "+fileName+"Rec Constructor\n");
//     for (int i = 1; i <= colCount; i++) {
//         sbird.append("                    "); // Source���� �p�
//         sbird.append(" " + fileName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(colType[i].toLowerCase())+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
//     } // end for
// sbird.append("            " + fileName.toLowerCase() + "V.addElement(" + fileName.toLowerCase() + ");\n");
// sbird.append("        } // end While\n");
// sbird.append("    } finally {\n");
// sbird.append("        try{rs.close();}catch(Exception e){}\n");
// sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
// sbird.append("    } // try-finally\n");
// sbird.append("    return " + fileName.toLowerCase() + "V;\n");
// sbird.append("} // end selectPage\n");
 sbird.append("\n");

 
//besselect
 sbird.append("/**\n");
 sbird.append("* Select\n");
 sbird.append("* @param ");
 //sbird.append(primeKeyType[1] + " " + primeKey[1]);
 sbird.append(primeKeyType[1] + " " + primeKey[1]);
 for (int i = 2; i <= primeCount; i++) {
   sbird.append(", " + primeKeyType[i] + " " + primeKey[i] );
 } // end for
 sbird.append("\n");
 sbird.append("* @return "+tableName+"Rec \n");
 sbird.append("* @author besTeam \n");
 sbird.append("* @date " + nowDate + "\n");
 sbird.append("*/\n");
 sbird.append("public "+tableName+"Rec select(");
//         if( primeKey[1].equals("facid")) {
// sbird.append(" String facid,");
//         }
// sbird.append("String fldname, String whereOption ) throws Exception{\n");
// sbird.append("return selectWhere(");
//         if( primeKey[1].equals("facid")) {
// sbird.append("facid,");
//         }
// sbird.append("fldname, \"Asc\", whereOption) ;\n");
// sbird.append("}// end selectWhere\n");
 sbird.append(primeKeyType[1] + " " + primeKey[1]);
 for (int i = 2; i <= primeCount; i++) sbird.append(", "+primeKeyType[i]+" " + primeKey[i]);
    
 sbird.append(") throws Exception{\n");
 sbird.append("    java.util.Vector " + entity + "V = new java.util.Vector();\n");
 sbird.append("    " + entityName + " " + entity + " = null;\n");
 sbird.append("    PreparedStatement pstmt = null;\n");
 sbird.append("    ResultSet rs = null;\n");
 sbird.append("    try{\n");
 sbird.append("        String query = \"Select ");
 for(int j22 = 1; j22 <= colCount; j22++)
 {
     if(j22 != 1 && j22 % 9 == 1)
         sbird.append("\" +\n                              \"");
     sbird.append(colLabel[j22].toLowerCase());
     if(j22 < colCount)
         sbird.append(", ");
 }
 sbird.append(" \" +\n                       \"  from " + fullTableName + " ");
 sbird.append(" \" +\n                       \"  where ");
 sbird.append(baseTableAlias + primeKey[1]+" = ? ");
 for (int i = 2; i <= primeCount; i++) sbird.append("and "+baseTableAlias + primeKey[i]+" = ? ");
 sbird.append(" \";\n");
 sbird.append("        pstmt = connection.prepareStatement(query);\n");

 for (int i = 1; i <= primeCount; i++) {
      sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
 } // end for

 sbird.append("        rs = pstmt.executeQuery();\n");
 sbird.append("       if(rs.next()) { \n");
 sbird.append("            " + fileName.toLowerCase() + " = new "+fileName+"Rec(); // "+fileName+"Rec Constructor\n");
     for (int i = 1; i <= colCount; i++) {
         sbird.append("                    "); // Source���� �p�
         sbird.append(" " + fileName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(colType[i].toLowerCase())+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
     } // end for
 //sbird.append("            " + fileName.toLowerCase() + "V.addElement(" + fileName.toLowerCase() + ");\n");
 sbird.append("        } // end While\n");
 sbird.append("        else {  throw new DataNotFoundException();} \n");
 sbird.append("    } finally {\n");
 sbird.append("        try{rs.close();}catch(Exception e){}\n");
 sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
 sbird.append("    } // try-finally\n");
 sbird.append("    return " + fileName.toLowerCase() + ";\n");
 sbird.append("} // end select\n");
 sbird.append("\n");


//  besselectAll
    sbird.append("/**\n");
    sbird.append("* Get All Record \n");
    sbird.append("* @param  void \n");
    sbird.append("* @return java.util.Vector \n");
    sbird.append("* @author besTeam \n");
    sbird.append("* @date " + nowDate + "\n");
    sbird.append("*/\n");
    sbird.append("public java.util.Vector selectAll() throws Exception{\n");
    sbird.append("    java.util.Vector " + tableName.toLowerCase() + "V = new java.util.Vector();\n");
    sbird.append("    "+tableName+"Rec " + tableName.toLowerCase() + " = null;\n");
    sbird.append("    PreparedStatement pstmt = null;\n");
    sbird.append("    ResultSet rs = null;\n");
    sbird.append("    try{\n");
if( g_isFromQuery ) {
	sbird.append("        String query = " );
	sbird.append( g_writeQuery + ";"); 
    
} else {    
    sbird.append("        String query = \"Select ");
    for (int i = 1; i <= colCount; i++) {
        if((i != 1 ) && (i%9 == 1)) {  sbird.append("\" +\n                              \"");}
        sbird.append(colLabel[i].toLowerCase());
        if(i < colCount) {sbird.append(", ");}
    } // end for
    sbird.append(" \" +\n                       \"  from "+fullTableName+" \"+\n");
    sbird.append("                       \"  order by ");
    sbird.append(baseTableAlias + primeKey[1]+"");
    for (int i = 2; i <= primeCount; i++) sbird.append(" , "+baseTableAlias + primeKey[i]+"");
    sbird.append(" \";\n");
}

    sbird.append("        pstmt = connection.prepareStatement(query);\n");
    sbird.append("        rs = pstmt.executeQuery();\n");
    sbird.append("\n");
    sbird.append("        while(rs.next()){\n");
    sbird.append("            " + tableName.toLowerCase() + " = new "+tableName+"Rec(); // "+tableName+"Rec Constructor\n");
        for (int i = 1; i <= colCount; i++) {
        ColumnType = colType[i];
            sbird.append("                    "); // Source���� �p�
            sbird.append(" " + tableName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(ColumnType)+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
        } // end for
    sbird.append("            " + tableName.toLowerCase() + "V.addElement(" + tableName.toLowerCase() + ");\n");
    sbird.append("        } // end While\n");
    sbird.append("    } finally {\n");
    sbird.append("        try{rs.close();}catch(Exception e){}\n");
    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    sbird.append("    } // try-finally\n");
    sbird.append("    return " + tableName.toLowerCase() + "V;\n");
    sbird.append("} // end selectAll\n");
    sbird.append("\n");
    
// sbird.append("/**\n");
// sbird.append("* SelectWhere\n");
// sbird.append("* @param ");
//  sbird.append(primeKeyType[1] + " " + primeKey[1]);
// sbird.append("* @return java.util.Vector \n");
// sbird.append("* @author besTeam \n");
// sbird.append("* @date " + nowDate + "\n");
// sbird.append("*/\n");
// sbird.append("public java.util.Vector selectWhere(");
// 
// if(primeKey[1].equals("facid"))
//     sbird.append(" String facid,");
// sbird.append("String fldname, String keyorder, String whereOption) throws Exception{\n");
// sbird.append("    java.util.Vector " + entity + "V = new java.util.Vector();\n");
// sbird.append("    " + entityName + " " + entity + " = null;\n");
// sbird.append("    PreparedStatement pstmt = null;\n");
// sbird.append("    ResultSet rs = null;\n");
// sbird.append("    try{\n");
// sbird.append("        String query = \"Select ");
// for(int j22 = 1; j22 <= colCount; j22++)
// {
//     if(j22 != 1 && j22 % 9 == 1)
//         sbird.append("\" +\n                              \"");
//     sbird.append(colLabel[j22].toLowerCase());
//     if(j22 < colCount)
//         sbird.append(", ");
// }
//
// sbird.append(" \" +\n                       \"  from " + fullTableName + " ");
// if(primeKey[1].equals("facid"))
// {
//     sbird.append(" \" +\n                       \"  where ");
//     sbird.append(primeKey[1] + " = ?    ");
//     sbird.append(" \" + whereOption");
// }else{
//		sbird.append(" where 1 = 1 \" +	whereOption");
//	}
// sbird.append("  +\n                       \"  order by \"+fldname+\" \"+keyorder; \n");
// 
// sbird.append("        pstmt = connection.prepareStatement(query);\n");
//         if( primeKey[1].equals("facid")) {
// sbird.append("        pstmt.set"+initialCapital(primeKeyType[1])+"(1,"+primeKey[1]+"); \n");
//         }
// sbird.append("        rs = pstmt.executeQuery();\n");
// sbird.append("        while(rs.next()) { \n");
// sbird.append("            " + fileName.toLowerCase() + " = new "+fileName+"Rec(); // "+fileName+"Rec Constructor\n");
//     for (int i = 1; i <= colCount; i++) {
//         sbird.append("                    "); // Source���� �p�
//         sbird.append(" " + fileName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(colType[i].toLowerCase())+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
//     } // end for
// sbird.append("            " + fileName.toLowerCase() + "V.addElement(" + fileName.toLowerCase() + ");\n");
// sbird.append("        } // end While\n");
// sbird.append("    } finally {\n");
// sbird.append("        try{rs.close();}catch(Exception e){}\n");
// sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
// sbird.append("    } // try-finally\n");
// sbird.append("    return " + fileName.toLowerCase() + "V;\n");
// sbird.append("} // end selectWhere\n");
// sbird.append("\n");


//get Rows CountPage
// sbird.append("/**\n");
// sbird.append("* Get Rows CountPage \n");
// sbird.append("* @param ");
//         if( primeKey[1].equals("facid")) {
// sbird.append(primeKeyType[1] + " " + primeKey[1]);
//         }
// sbird.append("\n");
// sbird.append("* @return int \n");
// sbird.append("* @author besTeam \n");
// sbird.append("* @date " + nowDate + "\n");
// sbird.append("*/\n");
// sbird.append("public int countPage(");
// 
// if(primeKey[1].equals("facid"))
//     sbird.append(" String facid,");
// sbird.append("String whereOption) throws Exception{\n");
// sbird.append("    int count = 0;\n");
// sbird.append("    PreparedStatement pstmt = null;\n");
// sbird.append("    ResultSet rs = null;\n");
// sbird.append("    try{\n");
// sbird.append("        String query = \"Select COUNT(*) from "+fullTableName + " ");
// 
// if(primeKey[1].equals("facid"))
// {
//     sbird.append(" \" +\n                       \"  where ");
//     sbird.append(primeKey[1] + " = ?    ");
//     sbird.append(" \" + whereOption ;\n");
// }else{
//		sbird.append(" where 1 = 1 \" +	whereOption ;\n");
//	}
// sbird.append("        pstmt = connection.prepareStatement(query);\n");
//         if( primeKey[1].equals("facid")) {
// sbird.append("        pstmt.set"+initialCapital(primeKeyType[1])+"(1,"+primeKey[1]+"); \n");
//         }
// sbird.append("        rs = pstmt.executeQuery();\n");
// sbird.append("\n");
// sbird.append("        if(rs.next()){\n");
// sbird.append("            count = rs.getInt(1);\n");
// sbird.append("        } // end if\n");
// sbird.append("    } finally {\n");
// sbird.append("        try{rs.close();}catch(Exception e){}\n");
// sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
// sbird.append("    } // try-finally\n");
// sbird.append("    return count;\n");
// sbird.append("} // end countPage\n");
// sbird.append("\n");
 
 
 
// get One Data
//    sbird.append("/**\n");
//    sbird.append("* Get one Record \n");
//    sbird.append("* @param ");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", " + primeKeyType[i] + " " + primeKey[i] );
//    } // end for
//    sbird.append("\n");
//    sbird.append("* @return "+tableName+"Rec \n");
//    sbird.append("* @author besTeam \n");
//    sbird.append("* @date " + nowDate + "\n");
//    sbird.append("*/\n");
//    sbird.append("public "+tableName+"Rec select(");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) sbird.append(", "+primeKeyType[i]+" " + primeKey[i]);
//    
//    sbird.append(") throws Exception{\n");
//    sbird.append("    "+tableName+"Rec " + tableName.toLowerCase() + " = null;\n");
//    sbird.append("    PreparedStatement pstmt = null;\n");
//    sbird.append("    ResultSet rs = null;\n");
//    sbird.append("    try{\n");
//    
//if( g_isFromQuery ) {
//	sbird.append("        String query = " );
//	sbird.append( g_writeQuery ); 
//    
//    if( !g_isWhereExist ) sbird.append("		+ \" where ");
//    else sbird.append("		+ \" and ");
//} else {
//    
//    sbird.append("        String query = \"Select ");
//    for (int i = 1; i <= colCount; i++) {
//        if((i != 1 ) && (i%9 == 1)) {  sbird.append("\" +\n                              \"");}
//        sbird.append(colLabel[i].toLowerCase());
//        if(i < colCount) {sbird.append(", ");}
//    } // end for
//    sbird.append(" \" +\n                       \"  from "+fullTableName+" ");
//    sbird.append(" \" +\n                       \"  where ");
//}
//    sbird.append(baseTableAlias + primeKey[1]+" = ? ");
//    for (int i = 2; i <= primeCount; i++) sbird.append("and "+baseTableAlias + primeKey[i]+" = ? ");
//    sbird.append(" \";\n");
//    sbird.append("        pstmt = connection.prepareStatement(query);\n");
//
//    for (int i = 1; i <= primeCount; i++) {
//        sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
//    } // end for
//
//    sbird.append("        rs = pstmt.executeQuery();\n");
//    sbird.append("\n");
//    sbird.append("        if(rs.next()){\n");
//    sbird.append("            " + tableName.toLowerCase() + " = new "+tableName+"Rec(); // "+tableName+"Rec Constructor\n");
//        for (int i = 1; i <= colCount; i++) {
//        ColumnType = colType[i];
//            sbird.append("                    "); // Source���� �p�
//            sbird.append(" " + tableName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(ColumnType)+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
//        } // end for
//    sbird.append("        } else {\n");
//    sbird.append("            throw new DataNotFoundException();\n");
//    sbird.append("        } // end if\n");
//    sbird.append("    } finally {\n");
//    sbird.append("        try{rs.close();}catch(Exception e){}\n");
//    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
//    sbird.append("    } // try-finally\n");
//    sbird.append("    return " + tableName.toLowerCase() + ";\n");
//    sbird.append("} // end select\n");
//    sbird.append("\n");

// besselectAllWhere( String whereOption )
    sbird.append("/**\n");
    sbird.append("* Get selectAllWhere Record \n");
    sbird.append("* @param  String \n");
    sbird.append("* @return java.util.Vector \n");
    sbird.append("* @author besTeam \n");
    sbird.append("* @date " + nowDate + "\n");
    sbird.append("*/\n");
    sbird.append("public java.util.Vector selectAllWhere(String whereOption,String sortOption) throws Exception{\n");
    sbird.append("    java.util.Vector " + tableName.toLowerCase() + "V = new java.util.Vector();\n");
    sbird.append("    "+tableName+"Rec " + tableName.toLowerCase() + " = null;\n");
    sbird.append("    PreparedStatement pstmt = null;\n");
    sbird.append("    whereOption = whereOption.trim();\n");    
    sbird.append("    ResultSet rs = null;\n");
    sbird.append("    try{\n");
if( g_isFromQuery ) {
	sbird.append("        String query = " );
	sbird.append( g_writeQuery + "  + whereOption; \n" ); 
    
} else {
    sbird.append("        String query = \"Select ");
    for (int i = 1; i <= colCount; i++) {
        if((i != 1 ) && (i%9 == 1)) {  sbird.append("\" +\n                              \"");}
        sbird.append(colLabel[i].toLowerCase());
        if(i < colCount) {sbird.append(", ");}
    } // end for
    sbird.append(" \" +\n                       \"  from "+fullTableName+" ");
    sbird.append(" where 1=1 ");
    sbird.append(" \" + whereOption ;\n");
    sbird.append(" \n                      if(sortOption.equals(\"\")) " );
    sbird.append(" query += \" order by  ");
    sbird.append(baseTableAlias + primeKey[1]);
    for (int i = 2; i < primeCount; i++) sbird.append(","+baseTableAlias + primeKey[i]);
    sbird.append(" \" ;\n");
    sbird.append("                         else query+=sortOption ;");
    sbird.append("\n");
}

    sbird.append("        pstmt = connection.prepareStatement(query); \n");
    sbird.append("        rs = pstmt.executeQuery();\n");
    sbird.append("\n");
    sbird.append("        while(rs.next()){\n");
    sbird.append("            " + tableName.toLowerCase() + " = new "+tableName+"Rec(); // "+tableName+"Rec Constructor\n");
        for (int i = 1; i <= colCount; i++) {
        ColumnType = colType[i];
            sbird.append("                    "); // Source���� �p�
            sbird.append(" " + tableName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(ColumnType)+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
        } // end for
    sbird.append("            " + tableName.toLowerCase() + "V.addElement(" + tableName.toLowerCase() + ");\n");
    sbird.append("        } // end While\n");
    sbird.append("    } finally {\n");
    sbird.append("        try{rs.close();}catch(Exception e){}\n");
    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    sbird.append("    } // try-finally\n");
    sbird.append("    return " + tableName.toLowerCase() + "V;\n");
    sbird.append("} // end selectAll\n");
    sbird.append("\n");

//    if(primeCount > 1){
//    	
//// get All Data : selectAll( primaryKeys )
//    
//    sbird.append("/**\n");
//    sbird.append("* Get All Record(condition : last Key except) \n");
//    sbird.append("* @param ");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i < primeCount; i++) sbird.append(", "+primeKeyType[i] + " " + primeKey[i]);
//    sbird.append("\n");
//    sbird.append("* @return java.util.Vector \n");
//    sbird.append("* @author besTeam \n");
//    sbird.append("* @date " + nowDate + "\n");
//    sbird.append("*/\n");
//    sbird.append("public java.util.Vector selectAll(");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i < primeCount; i++) sbird.append(", "+primeKeyType[i]+" " + primeKey[i]);
//    sbird.append(") throws Exception{\n");
//    sbird.append("    java.util.Vector " + tableName.toLowerCase() + "V = new java.util.Vector();\n");
//    sbird.append("    "+tableName+"Rec " + tableName.toLowerCase() + " = null;\n");
//    sbird.append("    PreparedStatement pstmt = null;\n");
//    sbird.append("    ResultSet rs = null;\n");
//    sbird.append("    try{\n");
//if( g_isFromQuery ) {
//	sbird.append("        String query = " );
//	sbird.append( g_writeQuery ); 
//    
//    if( !g_isWhereExist ) sbird.append("		+ \" where ");
//    else sbird.append("		+ \" and ");
//} else {    
//    sbird.append("        String query = \"Select ");
//    for (int i = 1; i <= colCount; i++) {
//        if((i != 1 ) && (i%9 == 1)) {  sbird.append("\" +\n                              \"");}
//        sbird.append(colLabel[i].toLowerCase());
//        if(i < colCount) {sbird.append(", ");}
//    } // end for
//    sbird.append(" \" +\n                       \"  from "+fullTableName+" ");
//    sbird.append(" \" +\n                       \"  where ");
//}
//   
//    sbird.append(baseTableAlias + primeKey[1]+" = ? ");
//    for (int i = 2; i < primeCount; i++) sbird.append("and "+baseTableAlias + primeKey[i]+" = ? ");
//    sbird.append(" \" +\n                       \"  order by " + baseTableAlias + primeKey[primeCount] + "\"; \n");
//       
//    sbird.append("        pstmt = connection.prepareStatement(query);\n");
//    for (int i = 1; i < primeCount; i++) {
//        sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
//    } // end for
//    sbird.append("        rs = pstmt.executeQuery();\n");
//    sbird.append("\n");
//    sbird.append("        while(rs.next()){\n");
//    sbird.append("            " + tableName.toLowerCase() + " = new "+tableName+"Rec(); // "+tableName+"Rec Constructor\n");
//        for (int i = 1; i <= colCount; i++) {
//        ColumnType = colType[i];
//            sbird.append("                    "); // Source���� �p�
//            sbird.append(" " + tableName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(ColumnType)+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
//        } // end for
//    sbird.append("            " + tableName.toLowerCase() + "V.addElement(" + tableName.toLowerCase() + ");\n");
//    sbird.append("        } // end While\n");
//    sbird.append("    } finally {\n");
//    sbird.append("        try{rs.close();}catch(Exception e){}\n");
//    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
//    sbird.append("    } // try-finally\n");
//    sbird.append("    return " + tableName.toLowerCase() + "V;\n");
//    sbird.append("} // end selectAll\n");
//    sbird.append("\n");
//    
//// get Between Data
//    sbird.append("/**\n");
//    sbird.append("* Get between Record(condition : last Key from - to) \n");
//    sbird.append("* @param ");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i < primeCount; i++) sbird.append(", "+primeKeyType[i] + " " + primeKey[i]);
//    sbird.append(", "+primeKeyType[primeCount] + " f_" + primeKey[primeCount]);
//    sbird.append(", "+primeKeyType[primeCount] + " t_" + primeKey[primeCount]);
//    sbird.append("\n");
//        sbird.append("* @return java.util.Vector \n");
//    sbird.append("* @author besTeam \n");
//    sbird.append("* @date " + nowDate + "\n");
//    sbird.append("*/\n");
//    sbird.append("public java.util.Vector selectBetween(");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i < primeCount; i++) sbird.append(", "+primeKeyType[i]+" " + primeKey[i]);
//    sbird.append(", "+primeKeyType[primeCount] + " f_" + primeKey[primeCount]);
//    sbird.append(", "+primeKeyType[primeCount] + " t_" + primeKey[primeCount]);
//    sbird.append(") throws Exception{\n");
//    sbird.append("    return selectBetween(");
//    sbird.append(primeKey[1]);
//    for (int i = 2; i < primeCount; i++) sbird.append(", "+ primeKey[i]);
//    sbird.append(", f_" + primeKey[primeCount]);
//    sbird.append(", t_" + primeKey[primeCount]);
//    sbird.append(", 0);\n");
//    sbird.append("} // end selectBetween\n");
//    sbird.append("\n");
//    
//    sbird.append("/**\n");
//    sbird.append("* Get between Record(condition : last Key from - to) \n");
//    sbird.append("* @param ");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i < primeCount; i++) sbird.append(", "+primeKeyType[i] + " " + primeKey[i]);
//    sbird.append(", "+primeKeyType[primeCount] + " f_" + primeKey[primeCount]);
//    sbird.append(", "+primeKeyType[primeCount] + " t_" + primeKey[primeCount]);
//    sbird.append(", int lastKeyOrder(0 : ASC-Default, 1 : DESC)\n");
//    sbird.append("* @return java.util.Vector \n");
//    sbird.append("* @author besTeam \n");
//    sbird.append("* @date " + nowDate + "\n");
//    sbird.append("*/\n");
//    sbird.append("public java.util.Vector selectBetween(");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i < primeCount; i++) sbird.append(", "+primeKeyType[i]+" " + primeKey[i]);
//    sbird.append(", "+primeKeyType[primeCount] + " f_" + primeKey[primeCount]);
//    sbird.append(", "+primeKeyType[primeCount] + " t_" + primeKey[primeCount]);
//    sbird.append(", int lastKeyOrder) throws Exception{\n");
//    sbird.append("    java.util.Vector " + tableName.toLowerCase() + "V = new java.util.Vector();\n");
//    sbird.append("    "+tableName+"Rec " + tableName.toLowerCase() + " = null;\n");
//    sbird.append("    PreparedStatement pstmt = null;\n");
//    sbird.append("    ResultSet rs = null;\n");
//    sbird.append("    try{\n");
//if( g_isFromQuery ) {
//	sbird.append("        String query = " );
//	sbird.append( g_writeQuery ); 
//    
//    if( !g_isWhereExist ) sbird.append("		+ \" where ");
//    else sbird.append("		+ \" and ");
//} else {
//    sbird.append("        String query = \"Select ");
//    for (int i = 1; i <= colCount; i++) {
//        if((i != 1 ) && (i%9 == 1)) {  sbird.append("\" +\n                              \"");}
//        sbird.append(colLabel[i].toLowerCase());
//        if(i < colCount) {sbird.append(", ");}
//    } // end for
//    sbird.append(" \" +\n                       \"  from "+fullTableName+" ");
//    sbird.append(" \" +\n                       \"  where ");
//}
//    sbird.append(baseTableAlias + primeKey[1]+" = ? ");
//    for (int i = 2; i < primeCount; i++) sbird.append("and "+baseTableAlias + primeKey[i]+" = ? ");
//    sbird.append(" \" +\n                       \"  and " + baseTableAlias + primeKey[primeCount] + " between ? and ? ");
//    sbird.append(" \";\n               if(lastKeyOrder == 1){\n");
//    sbird.append("                   query += \" order by " + baseTableAlias + primeKey[primeCount] + " DESC \"; \n");
//    sbird.append("               } else {\n");
//    sbird.append("                   query += \" order by " + baseTableAlias + primeKey[primeCount] + "\"; \n");
//    sbird.append("               } // end if(lastKeyOrder == 1)\n");
//    
//    sbird.append("        pstmt = connection.prepareStatement(query);\n");
//    for (int i = 1; i < primeCount; i++) {
//        sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
//    } // end for
//    sbird.append("        pstmt.set"+initialCapital(primeKeyType[primeCount])+"("+(primeCount)+",f_"+primeKey[primeCount]+"); \n");
//    sbird.append("        pstmt.set"+initialCapital(primeKeyType[primeCount])+"("+(primeCount+1)+",t_"+primeKey[primeCount]+"); \n");
//    sbird.append("        rs = pstmt.executeQuery();\n");
//    sbird.append("\n");
//    sbird.append("        while(rs.next()){\n");
//    sbird.append("            " + tableName.toLowerCase() + " = new "+tableName+"Rec(); // "+tableName+"Rec Constructor\n");
//        for (int i = 1; i <= colCount; i++) {
//        ColumnType = colType[i];
//            sbird.append("                    "); // Source���� �p�
//            sbird.append(" " + tableName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(ColumnType)+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
//        } // end for
//    sbird.append("            " + tableName.toLowerCase() + "V.addElement(" + tableName.toLowerCase() + ");\n");
//    sbird.append("        } // end While\n");
//    sbird.append("    } finally {\n");
//    sbird.append("        try{rs.close();}catch(Exception e){}\n");
//    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
//    sbird.append("    } // try-finally\n");
//    sbird.append("    return " + tableName.toLowerCase() + "V;\n");
//    sbird.append("} // end selectBetween\n");
//    sbird.append("\n");
//    } // end if(primeCount > 1)
//
//// Select Over and Under
//    sbird.append("/**\n");
//    sbird.append("* Select Data Over the key value(s) and default return count(20) \n");
//    sbird.append("* @param ");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", "+primeKeyType[i] + " " + primeKey[i]);
//    } // end for
//    sbird.append("\n");
//    sbird.append("* @return java.util.Vector \n");
//    sbird.append("* @author besTeam \n");
//    sbird.append("* @date " + nowDate + "\n");
//    sbird.append("*/\n");
//    sbird.append("public java.util.Vector selectOver(");
//    sbird.append(primeKeyType[1] + " ");
//    sbird.append(primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", "+primeKeyType[i]+" " +primeKey[i]);
//    } // end for
//    sbird.append(") throws Exception{\n");
//    sbird.append("return selectOver(");
//    sbird.append(primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", " +primeKey[i]);
//    } // end for
//    sbird.append(",20) ;\n");
//
//    sbird.append("}// end selectOver\n");
//    sbird.append("/**\n");
//    sbird.append("* Select Data Over(Next) the key value(s) and return record count \n");
//    sbird.append("* @param ");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", " + primeKeyType[i] + " " + primeKey[i]);
//    } // end for
//    sbird.append(", int \n");
//    sbird.append("* @return java.util.Vector \n");
//    sbird.append("* @author besTeam \n");
//    sbird.append("* @date " + nowDate + "\n");
//    sbird.append("*/\n");
//    sbird.append("public java.util.Vector selectOver(");
//    sbird.append(primeKeyType[1]+" ");
//    sbird.append(primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", "+primeKeyType[i]+" " +primeKey[i]);
//    } // end for
//    sbird.append(", int page) throws Exception{\n");
//
//    sbird.append("    java.util.Vector " + tableName.toLowerCase() + "V = new java.util.Vector();\n");
//    sbird.append("    "+tableName+"Rec " + tableName.toLowerCase() + " = null;\n");
//    sbird.append("    PreparedStatement pstmt = null;\n");
//    sbird.append("    ResultSet rs = null;\n");
//    sbird.append("    try{\n");
//if( g_isFromQuery ) {
//	sbird.append("        String query = " );
//	sbird.append( g_writeQuery ); 
//    
//    if( !g_isWhereExist ) sbird.append("		+ \" where ");
//    else sbird.append("		+ \" and ");
//} else {
//    sbird.append("        String query = \"Select ");
//    for (int i = 1; i <= colCount; i++) {
//        if((i != 1 ) && (i%9 == 1)) {  sbird.append("\" +\n                              \"");}
//        sbird.append(colLabel[i].toLowerCase());
//        if(i < colCount) {sbird.append(", ");}
//    } // end for
//    sbird.append(" \" +\n                       \"  from "+fullTableName+" ");
//    sbird.append(" \" +\n                       \"  where ");
//}
//    for (int i = 1; i < primeCount; i++) {
//        sbird.append(baseTableAlias + primeKey[i]+" = ?  and  ");
//    } // end for
//    sbird.append(baseTableAlias + primeKey[primeCount]+" >= ? order by "+baseTableAlias + primeKey[primeCount]+" \"; \n");
//    sbird.append("        pstmt = connection.prepareStatement(query);\n");
//    for (int i = 1; i <= primeCount; i++) {
//        sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
//    } // end for
//
//    sbird.append("        rs = pstmt.executeQuery();\n");
//    sbird.append("        int count = 0;//��f��� SQL���忡�� Limit�ؾ� ����...\n");
//    sbird.append("        while(rs.next()){\n");
//    sbird.append("            count ++;\n");
//    sbird.append("            if(count > page ) break;\n");
//    sbird.append("            " + tableName.toLowerCase() + " = new "+tableName+"Rec(); // "+tableName+"Rec Constructor\n");
//        for (int i = 1; i <= colCount; i++) {
//        ColumnType = colType[i];
//            sbird.append("                    "); // Source���� �p�
//            sbird.append(" " + tableName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(ColumnType)+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
//        } // end for
//    sbird.append("            " + tableName.toLowerCase() + "V.addElement(" + tableName.toLowerCase() + ");\n");
//    sbird.append("        } // end While\n");
//    sbird.append("    } finally {\n");
//    sbird.append("        try{rs.close();}catch(Exception e){}\n");
//    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
//    sbird.append("    } // try-finally\n");
//    sbird.append("    return " + tableName.toLowerCase() + "V;\n");
//    sbird.append("} // end selectOver\n");
//    sbird.append("\n");
//    sbird.append("/**\n");
//    sbird.append("* Select Data Under(Previous) the key value(s) and default return count(20) \n");
//    sbird.append("* @param ");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", "+primeKeyType[i] + " " + primeKey[i]);
//    } // end for
//    sbird.append("\n");
//    sbird.append("* @return java.util.Vector \n");
//    sbird.append("* @author besTeam \n");
//    sbird.append("* @date " + nowDate + "\n");
//    sbird.append("*/\n");
//    sbird.append("public java.util.Vector selectUnder(");
//    sbird.append(primeKeyType[1] + " ");
//    sbird.append(primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", "+primeKeyType[i]+" " +primeKey[i]);
//    } // end for
//    sbird.append(") throws Exception{\n");
//
//    sbird.append("return selectUnder(");
//
//    sbird.append(primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", " +primeKey[i]);
//    } // end for
//    sbird.append(",20) ;\n");
//
//    sbird.append("}// end selectUnder\n");
//    sbird.append("/**\n");
//    sbird.append("* Select Data Under(Previous) the key value(s) and return record count \n");
//    sbird.append("* @param ");
//    sbird.append(primeKeyType[1] + " " + primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", "+primeKeyType[i] + " " + primeKey[i]);
//    } // end for
//    sbird.append(", int\n");
//    sbird.append("* @return java.util.Vector\n");
//    sbird.append("* @author besTeam \n");
//    sbird.append("* @date " + nowDate + "\n");
//    sbird.append("*/\n");
//    sbird.append("public java.util.Vector selectUnder(");
//    sbird.append(primeKeyType[1] + " ");
//    sbird.append(primeKey[1]);
//    for (int i = 2; i <= primeCount; i++) {
//        sbird.append(", "+primeKeyType[i]+" " +primeKey[i]);
//    } // end for
//    sbird.append(", int page) throws Exception{\n");
//
//    sbird.append("    java.util.Vector " + tableName.toLowerCase() + "V = new java.util.Vector();\n");
//    sbird.append("    "+tableName+"Rec " + tableName.toLowerCase() + " = null;\n");
//    sbird.append("    PreparedStatement pstmt = null;\n");
//    sbird.append("    ResultSet rs = null;\n");
//    sbird.append("    try{\n");
//if( g_isFromQuery ) {
//	sbird.append("        String query = " );
//	sbird.append( g_writeQuery ); 
//    
//    if( !g_isWhereExist ) sbird.append("		+ \" where ");
//    else sbird.append("		+ \" and ");
//} else {
//    sbird.append("        String query = \"Select ");
//    for (int i = 1; i <= colCount; i++) {
//        if((i != 1 ) && (i%9 == 1)) {  sbird.append("\" +\n                              \"");}
//        sbird.append(colLabel[i].toLowerCase());
//        if(i < colCount) {sbird.append(", ");}
//    } // end for
//
//    sbird.append(" \" +\n                       \"  from "+fullTableName+" ");
//    sbird.append(" \" +\n                       \"  where ");
//}    
//    for (int i = 1; i < primeCount; i++) {
//        sbird.append(baseTableAlias + primeKey[i]+" = ?  and ");
//    } // end for
//    sbird.append(baseTableAlias + primeKey[primeCount]+" <= ? order by "+baseTableAlias + primeKey[primeCount]+" desc\" ; \n");
//
//    sbird.append("        pstmt = connection.prepareStatement(query);\n");
//
//    for (int i = 1; i <= primeCount; i++) {
//        sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
//    } // end for
//
//    sbird.append("        rs = pstmt.executeQuery();\n");
//    sbird.append("        int count = 0;//��f��� SQL���忡�� Limit�ؾ� ����...\n");
//    sbird.append("        while(rs.next()){\n");
//    sbird.append("            count ++;\n");
//    sbird.append("            if(count > page ) break;\n");
//    sbird.append("            " + tableName.toLowerCase() + " = new "+tableName+"Rec(); // "+tableName+"Rec Constructor\n");
//        for (int i = 1; i <= colCount; i++) {
//        ColumnType = colType[i];
//            sbird.append("                    "); // Source���� �p�
//            sbird.append(" " + tableName.toLowerCase() + ".set"+initialCapital(colLabel[i].toLowerCase())+"(rs.get"+initialCapital(ColumnType)+"(\""+colLabel[i].toLowerCase()+"\"));\n");            
//        } // end for
//    sbird.append("            " + tableName.toLowerCase() + "V.add(0," + tableName.toLowerCase() + ");\n");
//    sbird.append("        } // end While\n");
//    sbird.append("    } finally {\n");
//    sbird.append("        try{rs.close();}catch(Exception e){}\n");
//    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
//    sbird.append("    } // try-finally\n");
//    sbird.append("    return " + tableName.toLowerCase() + "V;\n");
//    sbird.append("} // end selectUnder\n");

//  bescount
    sbird.append("/**\n");
    sbird.append("* Get Rows Count \n");
    sbird.append("* @param ");
    sbird.append(primeKeyType[1] + " " + primeKey[1]);
    for (int i = 2; i <= primeCount; i++) {
        sbird.append(", "+primeKeyType[i] + " " + primeKey[i]);
    } // end for
    sbird.append("\n");
    sbird.append("* @return int \n");
    sbird.append("* @author besTeam \n");
    sbird.append("* @date " + nowDate + "\n");
    sbird.append("*/\n");
    sbird.append("public int count(");
    sbird.append(primeKeyType[1] + " ");
    sbird.append(primeKey[1]);
    for (int i = 2; i <= primeCount; i++) {
        sbird.append(", "+primeKeyType[i]+" " +primeKey[i]);
    } // end for

    sbird.append(") throws Exception{\n");
    sbird.append("    int count = 0;\n");
    sbird.append("    PreparedStatement pstmt = null;\n");
    sbird.append("    ResultSet rs = null;\n");
    sbird.append("    try{\n");
if( g_isFromQuery ) {
	sbird.append("        String query = " );
	sbird.append( g_countQuery ); 
    
    if( !g_isWhereExist ) sbird.append("		+ \" where ");
    else sbird.append("		+ \" and ");
} else {
    sbird.append("        String query = \"SELECT COUNT(*) from "+fullTableName+" \" +\n");
    sbird.append("                       \" where ");
}
    sbird.append(baseTableAlias + primeKey[1]+" = ? ");
    for (int i = 2; i <= primeCount; i++) {
        sbird.append("and "+baseTableAlias + primeKey[i]+" = ? ");
    } // end for

    sbird.append("  \";\n");
    sbird.append("        pstmt = connection.prepareStatement(query);\n");
    for (int i = 1; i <= primeCount; i++) {
        sbird.append("        pstmt.set"+initialCapital(primeKeyType[i])+"("+i+","+primeKey[i]+"); \n");
    } // end for

    sbird.append("        rs = pstmt.executeQuery();\n");
    sbird.append("\n");
    sbird.append("        if(rs.next()){\n");
    sbird.append("            count = rs.getInt(1);\n");
    sbird.append("        } // end if\n");
    sbird.append("    } finally {\n");
    sbird.append("        try{rs.close();}catch(Exception e){}\n");
    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    sbird.append("    } // try-finally\n");
    sbird.append("    return count;\n");
    sbird.append("} // end count\n");
    sbird.append("\n");

// bescountWhere
    sbird.append("\n/**\n");
    sbird.append("* Get All Rows Count \n");
    sbird.append("* @param String whereOption \n");
    sbird.append("* @return int \n");
    sbird.append("* @author besTeam \n");
    sbird.append("* @date " + nowDate + "\n");
    sbird.append("*/\n");
    sbird.append("public int countWhere(String whereOption) throws Exception{\n");
    sbird.append("    int count = 0;\n");
    sbird.append("    PreparedStatement pstmt = null;\n");
    sbird.append("    ResultSet rs = null;\n");
    sbird.append("    try{\n");
if( g_isFromQuery ) {
	sbird.append("        String query = " );
	sbird.append( g_countQuery + ";"); 

} else {
    sbird.append("        String query = \"SELECT COUNT(*) from "+fullTableName+"  \" +\n");
    sbird.append("                       \" where 1=1 ");
     sbird.append(" \" + whereOption ;\n");
}

    sbird.append("        pstmt = connection.prepareStatement(query);\n");
    sbird.append("        rs = pstmt.executeQuery();\n");
    sbird.append("\n");
    sbird.append("        if(rs.next()){\n");
    sbird.append("            count = rs.getInt(1);\n");
    sbird.append("        } // end if\n");
    sbird.append("    } finally {\n");
    sbird.append("        try{rs.close();}catch(Exception e){}\n");
    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    sbird.append("    } // try-finally\n");
    sbird.append("    return count;\n");
    sbird.append("} // end count\n");
    sbird.append("\n");
    
    
    
    
    
    /***********************************************************************************/
    /******************* insert, update, delete ****************************************/
    /***********************************************************************************/
    
    
    
    if(ivv>0){	//View Except
        
    
    
    sbird.append("/*\n");
    sbird.append("* Add Record \n");
    sbird.append("* @param " + tableName + "Rec \n");
    sbird.append("* @return void \n");
    sbird.append("* @author besTeam \n");
    sbird.append("* @date " + nowDate + "\n");
    sbird.append("*/\n");
    sbird.append("public void insert(" + tableName + "Rec " + tableName.toLowerCase() + ") throws Exception{\n");
    sbird.append("    PreparedStatement pstmt = null;\n");
    sbird.append("    try{\n");
    sbird.append("        String query = \"Insert into " + fullTableName + "( ");
    for(int l13 = 1; l13 <= k; l13++)
    {
        if(l13 % 9 == 1)
            sbird.append("\" +\n                              \"");
        sbird.append(resultsetmetadata.getColumnLabel(l13).toLowerCase());
        if(l13 < k)
            sbird.append(", ");
    }

    sbird.append("\"+\n                       ");
    sbird.append("\" ) values ( \"+\n");
    sbird.append("                              \"");
    for(int i14 = 1; i14 <= k; i14++)
    {
        sbird.append("?");
        if(i14 < k)
            sbird.append(", ");
        if(i14 % 9 == 1 && i14 != 1)
        {
            sbird.append("\"+\n");
            sbird.append("                              \"");
        }
    }

    sbird.append(")\";\n");
    sbird.append("        pstmt = connection.prepareStatement(query);\n");
    for(int j14 = 1; j14 <= k; j14++)
    {
        String s11 = dataType(resultsetmetadata.getColumnTypeName(j14), resultsetmetadata.getPrecision(j14), resultsetmetadata.getScale(j14));
        sbird.append("        pstmt.set" + initialCapital(s11) + "(" + j14 + ", " + tableName.toLowerCase() + ".get" + initialCapital(resultsetmetadata.getColumnLabel(j14)) + "());\n");
    }

    sbird.append("        int affected = pstmt.executeUpdate();\n");
    sbird.append("        if ( affected == 0 ) throw new DataAlreadyExistException();\n");
    sbird.append("\n");
    sbird.append("    } finally {\n");
    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    sbird.append("    } // try-finally\n");
    sbird.append("\n");
    sbird.append("} // end insert\n");
    sbird.append("\n");
    // besupdate
    sbird.append("\n// Update Data \n");
    sbird.append("/**\n");
    sbird.append("* Update Record \n");
    sbird.append("* @param " + tableName + "Rec \n");
    sbird.append("* @return void \n");
    sbird.append("* @author besTeam \n");
    sbird.append("* @date " + nowDate + "\n");
    sbird.append("*/\n");
    sbird.append("public void update(" + tableName + "Rec " + tableName.toLowerCase() + ") throws Exception{\n");
    sbird.append("    PreparedStatement pstmt = null;\n");
    sbird.append("    try{\n");
    sbird.append("        String query = \"Update " + fullTableName + " SET \"+\n                        \"");
    int temp=0;
    for(int k14 = 1; k14 <= k; k14++)
    {
    	if(resultsetmetadata.getColumnLabel(k14).toLowerCase().equals("rgsr_emp_no")||resultsetmetadata.getColumnLabel(k14).toLowerCase().equals("rgsr_date")||resultsetmetadata.getColumnLabel(k14).toLowerCase().equals("rgsr_time"))
    	{  
    		temp=temp+1;
    	}
    	else
    	{
    		sbird.append(resultsetmetadata.getColumnLabel(k14).toLowerCase() + " = ?");
            if(k14 < k)
                sbird.append(", ");
            if(k14 % 9 == 1 && k14 != 1)
                sbird.append("\" +\n                              \"");
    	}

    }

    sbird.append("\"+\n                        ");
    sbird.append("\" where ");
    
    sbird.append(primeKey[1] + " = ? ");
    for(int l14 = 2; l14 <= ivv; l14++)
        sbird.append("and " + primeKey[l14] + " = ? ");
    //sbird.append(as[1] + " = ? ");
    //for(int l14 = 2; l14 <= ivv; l14++)
    //    sbird.append("and " + as[l14] + " = ? ");

    sbird.append("\";\n");
    sbird.append("        pstmt = connection.prepareStatement(query);\n");
    int temp1=0;
    for(int i15 = 1; i15 <= k;i15++ )
    {
    	
    	temp1=temp1+1;
    	if(resultsetmetadata.getColumnLabel(i15).toLowerCase().equals("rgsr_emp_no")||resultsetmetadata.getColumnLabel(i15).toLowerCase().equals("rgsr_date")||resultsetmetadata.getColumnLabel(i15).toLowerCase().equals("rgsr_time"))
    	{  
    		temp1=temp1-1;
    	}
    	else{
        String s12 = dataType(resultsetmetadata.getColumnTypeName(i15), resultsetmetadata.getPrecision(i15), resultsetmetadata.getScale(i15));
        sbird.append("        pstmt.set" + initialCapital(s12) + "(" + (temp1) + ", " + tableName.toLowerCase() + ".get" + initialCapital(resultsetmetadata.getColumnLabel(i15)) + "());\n");
     	}
    }

    sbird.append("        // Key\n");
    
    
    for (int j15 = 1; j15 <= primeCount;j15++ ) {
 
    	String s12 = dataType(resultsetmetadata.getColumnTypeName(j15), resultsetmetadata.getPrecision(j15), resultsetmetadata.getScale(j15));
        sbird.append("        pstmt.set" + initialCapital(primeKeyType[j15]) + "(" + (j15 + temp1) + ", " + tableName.toLowerCase() + ".get" + initialCapital(resultsetmetadata.getColumnLabel(j15)) + "());\n");
    	;
    } // end for
    
    sbird.append("        int affected = pstmt.executeUpdate();\n");
    sbird.append("        if ( affected == 0 ) throw new NoAffectedException();\n");
    sbird.append("        else if ( affected > 1 ) throw new TooManyAffectedException();\n");
    sbird.append("    } finally {\n");
    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    sbird.append("    } // try-finally\n");
    sbird.append("\n");
    sbird.append("} // end Update\n");
    sbird.append("\n");
    sbird.append("/**\n");
    //besdelete
    sbird.append("* Delete Record \n");
    sbird.append("* @param ");
    
    sbird.append(primeKeyType[1] + " " + primeKey[1]);
    for (int i = 2; i <= primeCount; i++) {
        sbird.append(", " + primeKeyType[i] + " " + primeKey[i] );
    } // end for
    sbird.append("\n");
    
    sbird.append("* @return void \n");
    sbird.append("* @author besTeam \n");
    sbird.append("* @date " + nowDate + "\n");
    sbird.append("*/\n");
    sbird.append("public void delete(");
    
    sbird.append(primeKeyType[1] + " " + primeKey[1]);
    for (int i = 2; i <= primeCount; i++) {
        sbird.append(", " + primeKeyType[i] + " " + primeKey[i] );
    } // end for
    
    sbird.append(") throws Exception{\n");
    sbird.append("    PreparedStatement pstmt = null;\n");
    sbird.append("    try{\n");
    sbird.append("        String query = \"Delete From " + fullTableName + " \"+\n");
    sbird.append("                       \"where ");
    sbird.append(primeKey[1] + " = ? ");
    for(int i16 = 2; i16 <= ivv; i16++)
        sbird.append("and " + primeKey[i16] + " = ? ");

    sbird.append("\";\n");
    sbird.append("        pstmt = connection.prepareStatement(query);\n");
    
    
    for (int j16 = 1; j16 <= primeCount; j16++) {
        sbird.append("        pstmt.set"+initialCapital(primeKeyType[j16])+"("+(j16)+","+primeKey[j16]+"); \n");
    } // end for
    
    sbird.append("        int affected = pstmt.executeUpdate();\n");
    sbird.append("        if ( affected == 0 ){\n");
    sbird.append("            throw new NoAffectedException();\n");
    sbird.append("        } else if ( affected > 1 ) {\n");
    sbird.append("            throw new TooManyAffectedException();\n");
    sbird.append("        } // end if affection\n");
    sbird.append("    } finally {\n");
    sbird.append("        try{pstmt.close();}catch(Exception e){}\n");
    sbird.append("    } // try-finally\n");
    sbird.append("} // end Delete\n");
    sbird.append("\n");
    sbird.append("/**\n");

    
    sbird.append("* Delete Record \n");
    sbird.append("* @param " + tableName + "Rec \n");
    sbird.append("* @return void \n");
    sbird.append("* @author besTeam \n");
    sbird.append("* @date " + nowDate + "\n");
    sbird.append("*/\n");
    sbird.append("public void delete(" + tableName + "Rec " + tableName.toLowerCase() + ") throws Exception{\n");
    
    
    sbird.append("     delete(");
    for(int k16 = 1; k16 <= ivv; k16++){
    	if(k16==1)
    		sbird.append(primeKeyOrder[k16] + "()");
    	else
    		sbird.append(", " + primeKeyOrder[k16] + "()");
    }
    sbird.append(");");
    sbird.append("\n");
    sbird.append("} // end Delete\n");
    sbird.append("\n");
    
    
    
    }//end if(ivv>0)
    
        
    sbird.append("}// end "+fileName+"DBWrapBES class");
    }
// endmeng

    dbwrapbes_java = sbird.toString();
    sbird = new StringBuffer(); // ����� bird�� ���y��....


//=====dbwraper method xxWrap.java ==========================================
    if( packageUsed.equals("YES") ) sbird.append("package " + prjRoot + "." + prjMst + "." + packageName + ";\n\n");
    else  sbird.append("package " + prjRoot + "." + prjMst + " ;\n\n");
        
    sbird.append("// DBWrapper's Wrapper  Class for " + tableName + "\n");
    sbird.append("/**\n");
    sbird.append(" *\n");
    sbird.append(" * @(#) "+tableName+"DBWrap.java\n");
    sbird.append(" * Copyright 1999-2001 by Daewoo Information System, Inc.,\n");
    sbird.append(" * BES(Best Enterprise System) Team,\n");
    sbird.append(" * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea\n");
    sbird.append(" * All rights reserved.\n");
    sbird.append(" *\n");
    sbird.append(" * NOTICE !  You cannot copy or redistribute this code,\n");
    sbird.append(" * and you should not remove the information about the\n");
    sbird.append(" * copyright notice and the author.\n");
    sbird.append(" *\n");
    sbird.append(" * @version v0.1\n");
    sbird.append(" * @date    "+nowDate+"\n");
    sbird.append(" * @author  WonDeok Kim, wdkim(at)disc.co.kr.\n");
    sbird.append(" * @since   JDK1.2\n");
    sbird.append(" *\n");
    sbird.append(" */\n\n");
    sbird.append("import java.sql.*;\n");
    sbird.append("import org.jsn.jdf.db.*;\n\n");
// Class Define
    sbird.append("public class "+tableName+"DBWrap extends "+tableName+"DBWrapBES{\n");
// Constructor
    sbird.append("public "+tableName+"DBWrap(ConnectionContext ctx){\n");
    sbird.append("    super(ctx);\n");
    sbird.append("} // Constructor\n");
    sbird.append("\n");
 sbird.append("\n");
 sbird.append("\n");
 sbird.append("////////////////// User Define Code Start //////////////////\n\n");
sbird.append("\n");
sbird.append("//****** You should delete this line when you edit this source ^&*%%^%%(*&%(^%(*^^ \n");
sbird.append("\n");


 sbird.append("////////////////// User Define  Code  End //////////////////\n");
 sbird.append("\n");
 sbird.append("}// end  class");

    dbwrap_java = sbird.toString();

    sbird = new StringBuffer(); // ����� bird�� ���y��....


//************ ȭ�鿡 ���� ����  ( display_txt) *******************************


     if ( packageName.equals("") ) {
         input_err = "y";
 sbird.append("\n");
 sbird.append(" Package �Է�!!  \n");
     }          

     if ( allPgmName.equals("") ) {
         input_err = "y";
 sbird.append("\n");
 sbird.append(" Promgram Name �Է�!!  \n");
     }          

     if ( input_err.equals("n") ) {
 sbird.append("\n");
 sbird.append(" �wα׷� SET   \n");
 sbird.append("  : "+packageName+"  \n");
 sbird.append("\n");
 sbird.append(" ��� �wα׷� ��   \n");
 sbird.append("  : "+allPgmName+"  \n");
 sbird.append("\n");
 sbird.append(" \\ PGM \\ "+allPgmName+" \\  \n");
 sbird.append("\n");
 sbird.append("       include.jsp ��. \n");
 sbird.append("\n");
 sbird.append("       index.jsp ��. \n");
 sbird.append("\n");
 sbird.append("       list1.jsp ��. \n");
 sbird.append("\n");               
 sbird.append("       add1.jsp ��. \n");
 sbird.append("\n");
 sbird.append("       update1.jsp ��. \n");
 sbird.append("\n");
 sbird.append("       besScript.js ��. \n");
 sbird.append("\n");
 sbird.append("       "+allPgmName+".java ��. \n");
 sbird.append("\n");
 sbird.append("       "+dbWrapName+".java+ ��. \n");
        
        } // input_err if
        
    display_txt = sbird.toString();
    sbird = new StringBuffer(); // ����� bird�� ���y��....

    jtRec.setText(display_txt);


//**********��ȯ  ******************************************************//



    File f = null;

    // Create Default Directory
    if( g_isPrompt ) {
    	f = new File("./PRMT");
    	f.mkdir();
    	
    	PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PRMT/" + allPgmName + ".jsp",false)));
        out.print(promptBuff.toString());
        out.close();
        if( scrollType.equals("true")) {
        	out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PRMT/" + allPgmName +  "_d.jsp" ,false)));
	        out.print(promptBuff_d.toString());
	        out.close();
        }

        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PRMT/"+tableName+"Rec.java",false)));
        out.print(dbrec_java);
        out.close();

        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PRMT/"+dbWrapName+"BES.java",false)));
        out.print(dbwrapbes_java);
        out.close();

        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PRMT/"+dbWrapName+".java",false)));
        out.print(dbwrap_java);
        out.close();
        
	} else {
	
		if(g_isFromQuery)
		{
			tableName=allPgmName.toUpperCase();
			dbWrapName=allPgmName.toUpperCase()+"DBWrap";
			
		}
    f = new File("./PGM");
    f.mkdir();
    f = new File("./PGM/" + packageName) ;
	f.mkdir();
    f = new File("./PGM/" + packageName + "/" + allPgmName);
    f.mkdir();

        if ( input_err.equals("n") ) {

    f = new File("./PGM/" + packageName + "/" + allPgmName+ "/" + useTableName);
    f.mkdir();

    f = new File("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/src");
    f.mkdir();

        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/include.jsp",false)));
        out.print(include_jsp);
        out.close();
        
        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/index.jsp",false)));
        out.print(index_jsp);
        out.close();
        
        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/list1.jsp",false)));
        out.print(list1_jsp);
        out.close();
        
        if( scrollType.equals("true")) {
	        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/data1.jsp",false)));
	        out.print(data1_jsp);
	        out.close();
	    }
	            
        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/add1.jsp",false)));
        out.print(add1_jsp);
        out.close();
        
        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/update1.jsp",false)));
        out.print(update1_jsp);
        out.close();
        
        
        // for i18n .. 
        //out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/besScript.js",false)));
        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/besScript.jsp",false)));
        out.print(besScript_js);
        out.close();
        
        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/src/"+allPgmName+".java",false)));
        out.print(action_java);
        out.close();
        
        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/src/"+tableName.toUpperCase()+"Rec.java",false)));
        out.print(dbrec_java);
        out.close();

        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/src/"+dbWrapName+"BES.java",false)));
        out.print(dbwrapbes_java);
        out.close();

        out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./PGM/" + packageName + "/" + allPgmName + "/" + useTableName+"/src/"+dbWrapName+".java",false)));
        out.print(dbwrap_java);
        out.close();
	} // prompt....... 

        } // input_err if

    } catch(Exception e){
        System.out.println("error :: " + e.toString());
        
        showMessage(e.toString());
        try
        {
            if(resultset1 != null)
                resultset1.close();
        }
        catch(SQLException sqlexception) { }
        try
        {
            if(resultset != null)
                resultset.close();
        }
        catch(SQLException sqlexception1) { }
        try
        {
            if(statement != null)
                statement.close();
        }
        catch(SQLException sqlexception2) { }
        try
        {
            if(connection != null)
                connection.close();
        }
        catch(SQLException sqlexception3) { }
        
        
    } // try-catch-finally
    this.type="";
} // end getScript

    public void setUpCheckBoxColumn(TableColumn checkBoxColumn) {
        //Set up the editor for the sport cells.
        JCheckBox checkBox = new JCheckBox();
        checkBoxColumn.setCellEditor(new DefaultCellEditor(checkBox));

        CheckCellRenderer renderer =  new CheckCellRenderer();
        checkBoxColumn.setCellRenderer(renderer);

    }



	class DbType extends Object {
		public String dbname; 
		public Vector keys = null; 
		public DbType() {
			keys = new Vector(); 
		}
		public DbType(String dbname) {
			keys = new Vector(); 
			this.dbname = dbname; 
		}
		public String getName() { return dbname; }
		public void addKey( String key ) { keys.add( key ); }
		public int getSize() {
			if( keys != null ) return keys.size(); 
			return 0; 
		}
		public String getKey(int ind) {
			return (String)keys.get(ind); 
		}
		
	}

	
} // BESGenDDM Classe


