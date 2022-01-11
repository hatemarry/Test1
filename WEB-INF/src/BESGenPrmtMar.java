// Decompiled by Jad v1.5.8d. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BESGenPrmtMar.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

public class BESGenPrmtMar extends JFrame
{
    class My_tableHandler
        implements ListSelectionListener
    {

        public void valueChanged(ListSelectionEvent listselectionevent)
        {
            if(listselectionevent.getSource() == colTable)
            {
                int i = tableList.getSelectedIndex();
                if(i < 0)
                    return;
                String s = (String)dlmList.getElementAt(i);
                jlTableName.setText(s);
                jtPgmName.setText(s + "Prmt.jsp");
                int j = dtmTable.getRowCount();
                for(int k = 0; k < j; k++)
                    dtmTable.removeRow(0);

                Connection connection = null;
                Statement statement = null;
                ResultSet resultset = null;
                ResultSet resultset1 = null;
                try
                {
                    connection = DriverManager.getConnection(jtURL.getText(), jtUser.getText(), new String(jpPassword.getPassword()));
                    statement = connection.createStatement();
                    resultset = statement.executeQuery("SELECT * FROM " + s);
                    ResultSetMetaData resultsetmetadata = resultset.getMetaData();
                    DatabaseMetaData databasemetadata = connection.getMetaData();
                    Vector vector = new Vector();
                    for(resultset1 = databasemetadata.getPrimaryKeys(null, null, s.substring(0, 6)); resultset1.next(); vector.addElement(resultset1.getString("COLUMN_NAME").toUpperCase()));
                    int l = vector.size();
                    int i1 = 0;
                    String s1 = "";
                    String s2 = "";
                    int j1 = resultsetmetadata.getColumnCount();
                    String as[] = new String[j1];
                    for(int k1 = 1; k1 <= j1; k1++)
                    {
                        as[k1 - 1] = resultsetmetadata.getColumnLabel(k1);
                        for(int l1 = 0; l1 < l; l1++)
                        {
                            String s3 = (String)vector.elementAt(l1);
                            if(s3.equals(resultsetmetadata.getColumnLabel(k1).toUpperCase()))
                            {
                                i1++;
                                s1 = i1 + "";
                                break;
                            }
                            s1 = "";
                        }

                        String as1[] = {
                            s1, dataType(resultsetmetadata.getColumnTypeName(k1), resultsetmetadata.getPrecision(k1), resultsetmetadata.getScale(k1)), resultsetmetadata.getColumnLabel(k1).toUpperCase(), resultsetmetadata.getPrecision(k1) + "", resultsetmetadata.getColumnLabel(k1).toUpperCase(), s1, s1
                        };
                        dtmTable.addRow(as1);
                    }

                    colCount = resultsetmetadata.getColumnCount();
                    colLabel = new String[colCount + 1];
                    colType = new String[colCount + 1];
                    colTypeName = new String[colCount + 1];
                    colPrecision = new int[colCount + 1];
                    colScale = new int[colCount + 1];
                    for(int i2 = 1; i2 <= colCount; i2++)
                    {
                        colLabel[i2] = resultsetmetadata.getColumnLabel(i2);
                        colType[i2] = dataType(resultsetmetadata.getColumnTypeName(i2), resultsetmetadata.getPrecision(i2), resultsetmetadata.getScale(i2));
                        colTypeName[i2] = resultsetmetadata.getColumnTypeName(i2);
                        colPrecision[i2] = resultsetmetadata.getPrecision(i2);
                        colScale[i2] = resultsetmetadata.getScale(i2);
                    }

                }
                catch(Exception exception)
                {
                    System.out.println("ERROR: " + exception.toString());
                }
                finally
                {
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
                }
            }
        }

        My_tableHandler()
        {
        }
    }

    class My_ButtonHandler
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            if(actionevent.getSource() == jbGetTable)
            {
                dlmList.clear();
                Vector vector = getTableNames();
                Iterator iterator = vector.iterator();
                String s = "";
                String s1;
                for(; iterator.hasNext(); dlmList.addElement(s1))
                    s1 = (String)iterator.next();

            }
            if(actionevent.getSource() == jbGetScript)
                genScript();
        }

        My_ButtonHandler()
        {
        }
    }

    class My_WindowHandler extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            mainFrame.setVisible(false);
            mainFrame.dispose();
            System.exit(0);
        }

        My_WindowHandler()
        {
        }
    }


    public static void main(String args[])
    {
        new BESGenPrmtMar();
    }

    public BESGenPrmtMar()
    {
        mainFrame = new JFrame("Prompt Generator");
        jlDriver = new JLabel("Driver", 0);
        jlURL = new JLabel("URL", 0);
        jlUser = new JLabel("User", 0);
        jlPassword = new JLabel("Password", 0);
        jlPgmName = new JLabel("\uD504\uB85C\uADF8\uB7A8\uBA85", 0);

        String path = "C:/gen.properties";
        Properties properties = new Properties();
        try
        {
            FileInputStream fileinputstream = new FileInputStream(path);
            properties.load(fileinputstream);
            fileinputstream.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return;
        }
        jtDriver = new JTextField(properties.getProperty("db.driver"));
        jtURL = new JTextField(properties.getProperty("db.url"));
        jtUser = new JTextField(properties.getProperty("db.user"));
        jpPassword = new JPasswordField(properties.getProperty("db.password"));
        
        jbGetTable = new JButton("Get Tables");
        jtPgmName = new JTextField("");
        GridBagLayout gridbaglayout = new GridBagLayout();
        dbPanel = new JPanel(gridbaglayout);
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.fill = 1;
        gridbagconstraints.gridheight = 5;
        gridbagconstraints.weighty = 1.0D;
        gridbaglayout.setConstraints(jbGetTable, gridbagconstraints);
        dbPanel.add(jbGetTable);
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.gridheight = 1;
        gridbaglayout.setConstraints(jlDriver, gridbagconstraints);
        dbPanel.add(jlDriver);
        gridbagconstraints.gridwidth = 0;
        gridbaglayout.setConstraints(jtDriver, gridbagconstraints);
        dbPanel.add(jtDriver);
        gridbagconstraints.gridwidth = 1;
        gridbaglayout.setConstraints(jlURL, gridbagconstraints);
        dbPanel.add(jlURL);
        gridbagconstraints.gridwidth = 0;
        gridbaglayout.setConstraints(jtURL, gridbagconstraints);
        dbPanel.add(jtURL);
        gridbagconstraints.gridwidth = 1;
        gridbaglayout.setConstraints(jlUser, gridbagconstraints);
        dbPanel.add(jlUser);
        gridbagconstraints.gridwidth = 0;
        gridbaglayout.setConstraints(jtUser, gridbagconstraints);
        dbPanel.add(jtUser);
        gridbagconstraints.gridwidth = 1;
        gridbaglayout.setConstraints(jlPassword, gridbagconstraints);
        dbPanel.add(jlPassword);
        gridbagconstraints.gridwidth = 0;
        gridbaglayout.setConstraints(jpPassword, gridbagconstraints);
        dbPanel.add(jpPassword);
        gridbagconstraints.gridwidth = 1;
        gridbaglayout.setConstraints(jlPgmName, gridbagconstraints);
        dbPanel.add(jlPgmName);
        gridbagconstraints.gridwidth = 0;
        gridbaglayout.setConstraints(jtPgmName, gridbagconstraints);
        dbPanel.add(jtPgmName);
        tablePanel2 = new JPanel(gridbaglayout);
        gridbagconstraints.gridwidth = 1;
        gridbagconstraints.weightx = 0.5D;
        gridbagconstraints.weighty = 0.20000000000000001D;
        jltl = new JLabel("Table List", 0);
        tablePanel2.add(jltl);
        gridbagconstraints.gridwidth = 0;
        jlTableName = new JLabel("Table Name", 0);
        gridbagconstraints.weightx = 3D;
        gridbagconstraints.weighty = 0.20000000000000001D;
        gridbaglayout.setConstraints(jlTableName, gridbagconstraints);
        tablePanel2.add(jlTableName);
        dlmList = new DefaultListModel();
        Vector vector = getTableNames();
        Iterator iterator = vector.iterator();
        String s = "";
        String s1;
        for(; iterator.hasNext(); dlmList.addElement(s1))
            s1 = (String)iterator.next();

        tableList = new JList(dlmList);
        colTable = tableList.getSelectionModel();
        colTable.addListSelectionListener(new My_tableHandler());
        jsTableList = new JScrollPane(tableList);
        gridbagconstraints.gridwidth = 1;
        gridbagconstraints.weightx = 0.5D;
        gridbagconstraints.weighty = 1.0D;
        gridbaglayout.setConstraints(jsTableList, gridbagconstraints);
        tablePanel2.add(jsTableList);
        dtmTable = new DefaultTableModel();
        tableDetail = new JTable(dtmTable);
        tableDetail.setPreferredScrollableViewportSize(new Dimension(500, 600));
        dtmTable.addColumn("Key");
        dtmTable.addColumn("Field type");
        dtmTable.addColumn("Field Name");
        dtmTable.addColumn("Field Size");
        dtmTable.addColumn("\uD544\uB4DC\uBA85");
        dtmTable.addColumn("\uD654\uBA74\uAD6C\uC131");
        dtmTable.addColumn("\uB9AC\uD134\uD544\uB4DC");
        tableDetail.setSelectionMode(0);
        jsTable = new JScrollPane(tableDetail);
        gridbagconstraints.gridwidth = 0;
        gridbagconstraints.weightx = 3D;
        gridbagconstraints.weighty = 1.0D;
        gridbaglayout.setConstraints(jsTable, gridbagconstraints);
        tablePanel2.add(jsTable);
        resultPanel = new JPanel(gridbaglayout);
        jtRec = new JTextArea("Record Bean", 5, 5);
        jtDBWrap = new JTextArea("User DBWrap", 5, 5);
        jsRec = new JScrollPane(jtRec);
        jsDBWrap = new JScrollPane(jtDBWrap);
        jbGetScript = new JButton("Generate Scripts");
        gridbagconstraints.weightx = 5D;
        gridbagconstraints.weighty = 0.29999999999999999D;
        gridbaglayout.setConstraints(jbGetScript, gridbagconstraints);
        resultPanel.add(jbGetScript);
        gridbagconstraints.gridwidth = 0;
        gridbagconstraints.weightx = 5D;
        gridbagconstraints.weighty = 1.0D;
        gridbaglayout.setConstraints(jsRec, gridbagconstraints);
        resultPanel.add(jsRec);
        gridbagconstraints.gridwidth = 0;
        gridbagconstraints.weightx = 5D;
        gridbagconstraints.weighty = 1.0D;
        gridbaglayout.setConstraints(jsDBWrap, gridbagconstraints);
        resultPanel.add(jsDBWrap);
        mainPanel = new JPanel(new GridLayout(1, 2));
        subP1 = new JPanel(gridbaglayout);
        subP2 = new JPanel(new GridLayout(1, 1));
        gridbagconstraints.gridwidth = 0;
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.weighty = 1.0D;
        gridbaglayout.setConstraints(dbPanel, gridbagconstraints);
        subP1.add(dbPanel);
        gridbagconstraints.gridwidth = 0;
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.weighty = 20D;
        gridbaglayout.setConstraints(tablePanel2, gridbagconstraints);
        subP1.add(tablePanel2);
        subP2.add(resultPanel);
        mainPanel.add(subP1);
        mainPanel.add(subP2);
        mainFrame.setContentPane(mainPanel);
        addListener();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public Vector getTableNames()
    {
        Vector vector = new Vector();
        Object obj = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        Object obj1 = null;
        String as[] = {
            "TABLE", "VIEW"
        };
        StringBuffer stringbuffer = new StringBuffer();
        try
        {
            Class.forName(jtDriver.getText()).newInstance();
            connection = DriverManager.getConnection(jtURL.getText(), jtUser.getText(), new String(jpPassword.getPassword()));
            DatabaseMetaData databasemetadata = null;
            databasemetadata = connection.getMetaData();

			String temp=null;
            for(resultset = databasemetadata.getTables(null, null, "%", as); resultset.next(); ){
					temp=resultset.getString("TABLE_NAME").toUpperCase();

					if((temp.length() == 6 || temp.length() == 9 ) && temp.indexOf("_") == -1 && temp.indexOf("$") == -1)
							if(temp.length()==9){
									if(temp.substring(6, 7).equals("J")){
											try{Integer.parseInt(temp.substring(7, 9));}catch(Exception ee){continue;}
												vector.add(resultset.getString("TABLE_NAME").toUpperCase());
									}
							}else{
											vector.add(resultset.getString("TABLE_NAME").toUpperCase());
							}
			}//end for
        }
        catch(Exception exception)
        {
            System.out.println("ERROR: " + exception.toString());
        }
        finally
        {
            try
            {
                if(resultset != null)
                    resultset.close();
            }
            catch(SQLException sqlexception) { }
            try
            {
                if(statement != null)
                    statement.close();
            }
            catch(SQLException sqlexception1) { }
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException sqlexception2) { }
        }
        return vector;
    }

    public void addListener()
    {
        mainFrame.addWindowListener(new My_WindowHandler());
        jbGetTable.addActionListener(new My_ButtonHandler());
        jbGetScript.addActionListener(new My_ButtonHandler());
    }

    public String dataType(String s, int i, int j)
    {
        String s1 = "OtherXXX";
        if(s.equalsIgnoreCase("CHAR") || s.equalsIgnoreCase("DATE") || s.equalsIgnoreCase("VARCHAR") || s.equalsIgnoreCase("VARCHAR2") || s.equalsIgnoreCase("LONG VARCHAR") || s.equalsIgnoreCase("bpchar"))
            s1 = "String";
        else
        if(s.equalsIgnoreCase("DECIMAL") || s.equalsIgnoreCase("NUMERIC") || s.equalsIgnoreCase("NUMBER") || s.equalsIgnoreCase("FLOAT") || s.equalsIgnoreCase("INT"))
        {
            if(j > 0)
                s1 = "double";
            else
            if(i > 18)
                s1 = "java.math.BigDecimal";
            else
            if(9 < i && i <= 18)
                s1 = "long";
            else
                s1 = "int";
        } else
        {
            s1 = "OtherXXX";
        }
        return s1;
    }

    public String initialCapital(String s)
    {
        if(s == null)
            return "";
        if(s.length() == 1)
        {
            return s.toUpperCase();
        } else
        {
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            return s;
        }
    }

    public String entityInitCap(String s)
    {
        return entity + ".get" + initialCapital(s) + "()";
    }

    public String entityFormat(String s, String s1, int i)
    {
        String s2 = "";
        if(s1.equalsIgnoreCase("String"))
            s2 = s;
        else
        if(i == 8)
            s2 = "bu.day3Date(" + s + ")";
        else
            s2 = "nf.format(" + s + ")";
        return s2;
    }

    public void musikCal()
    {
        try
        {
            primeCount = 0;
            listCount = 0;
            promptCount = 0;
            actCount = 0;
            fileName = jlTableName.getText();
            entityName = fileName + "Rec";
            dbWrapName = fileName + "DBWrap";
            entity = fileName.toLowerCase();
            dbwrap = entity + "dbw";
            fileName1 = fileName.substring(0, 6);
            entityName1 = fileName1 + "Rec";
            dbWrapName1 = fileName1 + "DBWrap";
            entity1 = fileName1.toLowerCase();
            dbwrap1 = entity1 + "dbw";
            allPgmName = jtPgmName.getText();
            pgmTitle = jtPgmName.getText();
            int i = 0;
            int j = 0;
            int l = 0;
            int j1 = 0;
            int l1 = 0;
            int i2 = 0;
            int j2 = 1;
            byte byte0 = 2;
            byte byte1 = 3;
            byte byte2 = 4;
            byte byte3 = 5;
            byte byte4 = 6;
            int k2 = tableDetail.getRowCount();
            int l2 = tableDetail.getColumnCount();
            for(int i3 = 0; i3 < k2; i3++)
            {
                if(!tableDetail.getValueAt(i3, l1).equals(""))
                    primeCount++;
                if(!tableDetail.getValueAt(i3, i2).equals(""))
                    listCount++;
                if(!tableDetail.getValueAt(i3, byte4).equals(""))
                    promptCount++;
                if(!tableDetail.getValueAt(i3, byte3).equals(""))
                    actCount++;
            }

            int ai[] = new int[primeCount + 1];
            int ai1[] = new int[listCount + 1];
            int ai2[] = new int[promptCount + 1];
            int ai3[] = new int[actCount + 1];
            i = 0;
            j = 0;
            l = 0;
            j1 = 0;
            for(int j3 = 0; j3 < k2; j3++)
                if(!tableDetail.getValueAt(j3, l1).equals(""))
                {
                    i++;
                    l = Integer.parseInt((String)tableDetail.getValueAt(j3, l1));
                    j = 0;
                    for(int k3 = 0; k3 < k2; k3++)
                        if(!tableDetail.getValueAt(k3, l1).equals(""))
                        {
                            j1 = Integer.parseInt((String)tableDetail.getValueAt(k3, l1));
                            if(l > j1 || l == j1 && j3 >= k3)
                                j++;
                        }

                    ai[i] = j;
                }

            i = 0;
            j = 0;
            l = 0;
            j1 = 0;
            for(int l3 = 0; l3 < k2; l3++)
                if(!tableDetail.getValueAt(l3, i2).equals(""))
                {
                    i++;
                    l = Integer.parseInt((String)tableDetail.getValueAt(l3, i2));
                    j = 0;
                    for(int i4 = 0; i4 < k2; i4++)
                        if(!tableDetail.getValueAt(i4, i2).equals(""))
                        {
                            j1 = Integer.parseInt((String)tableDetail.getValueAt(i4, i2));
                            if(l > j1 || l == j1 && l3 >= i4)
                                j++;
                        }

                    ai1[i] = j;
                }

            i = 0;
            j = 0;
            l = 0;
            j1 = 0;
            for(int j4 = 0; j4 < k2; j4++)
                if(!tableDetail.getValueAt(j4, byte4).equals(""))
                {
                    i++;
                    l = Integer.parseInt((String)tableDetail.getValueAt(j4, byte4));
                    j = 0;
                    for(int k4 = 0; k4 < k2; k4++)
                        if(!tableDetail.getValueAt(k4, byte4).equals(""))
                        {
                            j1 = Integer.parseInt((String)tableDetail.getValueAt(k4, byte4));
                            if(l > j1 || l == j1 && j4 >= k4)
                                j++;
                        }

                    ai2[i] = j;
                }

            i = 0;
            j = 0;
            l = 0;
            j1 = 0;
            for(int l4 = 0; l4 < k2; l4++)
                if(!tableDetail.getValueAt(l4, byte3).equals(""))
                {
                    i++;
                    int i1 = Integer.parseInt((String)tableDetail.getValueAt(l4, byte3));
                    int k = 0;
                    for(int i5 = 0; i5 < k2; i5++)
                        if(!tableDetail.getValueAt(i5, byte3).equals(""))
                        {
                            int k1 = Integer.parseInt((String)tableDetail.getValueAt(i5, byte3));
                            if(i1 > k1 || i1 == k1 && l4 >= i5)
                                k++;
                        }

                    ai3[i] = k;
                }

            primeKey = new String[primeCount + 1];
            getprimeKey = new String[primeCount + 1];
            primeKeyType = new String[primeCount + 1];
            primeKeySize = new int[primeCount + 1];
            listKey = new String[listCount + 1];
            getlistKey = new String[listCount + 1];
            listKeyType = new String[listCount + 1];
            listKeySize = new int[listCount + 1];
            promptKey = new String[promptCount + 1];
            getpromptKey = new String[promptCount + 1];
            promptKeyType = new String[promptCount + 1];
            promptKeySize = new int[promptCount + 1];
            actKey = new String[actCount + 1];
            getactKey = new String[actCount + 1];
            actKeyType = new String[actCount + 1];
            actKeySize = new int[actCount + 1];
            actTitle = new String[actCount + 1];
            actReqChk = new String[actCount + 1];
            actDspChk = new String[actCount + 1];
            actDatChk = new String[actCount + 1];
            actFormat = new String[actCount + 1];
            i = 0;
            for(int j5 = 0; j5 < k2; j5++)
                if(!tableDetail.getValueAt(j5, l1).equals(""))
                {
                    i++;
                    tableDetail.setValueAt(ai[i] + "", j5, l1);
                    primeKey[ai[i]] = ((String)tableDetail.getValueAt(j5, byte0)).toLowerCase();
                    getprimeKey[ai[i]] = entityInitCap((String)tableDetail.getValueAt(j5, byte0));
                    primeKeyType[ai[i]] = (String)tableDetail.getValueAt(j5, j2);
                    primeKeySize[ai[i]] = Integer.parseInt((String)tableDetail.getValueAt(j5, byte1));
                }

            i = 0;
            for(int k5 = 0; k5 < k2; k5++)
                if(!tableDetail.getValueAt(k5, i2).equals(""))
                {
                    i++;
                    listKey[ai1[i]] = ((String)tableDetail.getValueAt(k5, byte0)).toLowerCase();
                    getlistKey[ai1[i]] = entityInitCap((String)tableDetail.getValueAt(k5, byte0));
                    listKeyType[ai1[i]] = (String)tableDetail.getValueAt(k5, j2);
                    listKeySize[ai1[i]] = Integer.parseInt((String)tableDetail.getValueAt(k5, byte1));
                }

            i = 0;
            for(int l5 = 0; l5 < k2; l5++)
                if(!tableDetail.getValueAt(l5, byte4).equals(""))
                {
                    i++;
                    tableDetail.setValueAt(ai2[i] + "", l5, byte4);
                    promptKey[ai2[i]] = ((String)tableDetail.getValueAt(l5, byte0)).toLowerCase();
                    getpromptKey[ai2[i]] = entityInitCap((String)tableDetail.getValueAt(l5, byte0));
                    promptKeyType[ai2[i]] = (String)tableDetail.getValueAt(l5, j2);
                    promptKeySize[ai2[i]] = Integer.parseInt((String)tableDetail.getValueAt(l5, byte1));
                }

            i = 0;
            for(int i6 = 0; i6 < k2; i6++)
                if(!tableDetail.getValueAt(i6, byte3).equals(""))
                {
                    i++;
                    tableDetail.setValueAt(ai3[i] + "", i6, byte3);
                    actKey[ai3[i]] = ((String)tableDetail.getValueAt(i6, byte0)).toLowerCase();
                    getactKey[ai3[i]] = entityInitCap((String)tableDetail.getValueAt(i6, byte0));
                    actKeyType[ai3[i]] = (String)tableDetail.getValueAt(i6, j2);
                    actKeySize[ai3[i]] = Integer.parseInt((String)tableDetail.getValueAt(i6, byte1));
                    actTitle[ai3[i]] = (String)tableDetail.getValueAt(i6, byte2);
                    actReqChk[ai3[i]] = "N";
                    actDspChk[ai3[i]] = "N";
                    actDatChk[ai3[i]] = "N";
                    actFormat[ai3[i]] = entityFormat(getactKey[ai3[i]], actKeyType[ai3[i]], actKeySize[ai3[i]]);
                }

        }
        catch(Exception exception)
        {
            System.out.println("error :: " + exception.toString());
        }
    }

    public void genScript()
    {
        try
        {
            String s = "";
            String s1 = "";
            StringBuffer stringbuffer = new StringBuffer();
            StringBuffer stringbuffer1 = new StringBuffer();
            musikCal();
            String as[] = TimeZone.getAvailableIDs(0x1ee6280);
            SimpleTimeZone simpletimezone = new SimpleTimeZone(0x1ee6280, as[0]);
            GregorianCalendar gregoriancalendar = new GregorianCalendar(simpletimezone);
            String s2 = gregoriancalendar.get(1) + "-" + (gregoriancalendar.get(2) + 1) + "-" + gregoriancalendar.get(5);
            stringbuffer.append("<% \n");
            stringbuffer.append("/**\n");
            stringbuffer.append(" *\n");
            stringbuffer.append(" * @(#) " + allPgmName + "\n");
            stringbuffer.append(" * Table : " + fileName + "\n");
            stringbuffer.append(" *\n");
            for(int i = 1; i <= promptCount; i++)
                stringbuffer.append(" * Return Field" + i + " : " + promptKey[i] + " \n");

            stringbuffer.append(" *\n");
            for(int j = 1; j <= actCount; j++)
                stringbuffer.append(" * Display Field" + j + " : " + actKey[j] + " [ " + actTitle[j] + " ] \n");

            stringbuffer.append(" *\n");
            stringbuffer.append(" * Copyright 1999-2001 by Daewoo Information System, Inc.,\n");
            stringbuffer.append(" * BES(Best Enterprise System) Team,\n");
            stringbuffer.append(" * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea\n");
            stringbuffer.append(" * All rights reserved.\n");
            stringbuffer.append(" *\n");
            stringbuffer.append(" * NOTICE !  You cannot copy or redistribute this code,\n");
            stringbuffer.append(" * and you should not remove the information about the\n");
            stringbuffer.append(" * copyright notice and the author.\n");
            stringbuffer.append(" *\n");
            stringbuffer.append(" * @version v1.0\n");
            stringbuffer.append(" * @date    " + s2 + "\n");
            stringbuffer.append(" * @author  Jeong Chang Hyun, jch(at)disc.co.kr.\n");
            stringbuffer.append(" * @since   JDK1.2\n");
            stringbuffer.append(" *\n");
            stringbuffer.append(" */\n");
            stringbuffer.append("%> \n\n");
            stringbuffer.append("<%@ page contentType=\"text/html;charset=euc-kr\" %> \n");
            stringbuffer.append("<%@ include file = \"../../common/include.jsp\" %> \n");
            stringbuffer.append("<% \n");
            stringbuffer.append("  \n");
            stringbuffer.append("    String facid = ui.getFacid();\n");
            stringbuffer.append("\n");
            stringbuffer.append("    String current_Page = request.getParameter(\"current_Page\");  \n");
            stringbuffer.append("    String posName = request.getParameter(\"posName\");  \n");
            stringbuffer.append("    String posField = request.getParameter(\"posField\");  \n");
            stringbuffer.append("    String posValue = request.getParameter(\"posValue\");  \n");
            stringbuffer.append("    String posOrder = request.getParameter(\"posOrder\");  \n");
            stringbuffer.append("\n");
            for(int k = 1; k <= actCount; k++)
                stringbuffer.append("    String " + actKey[k] + "HG = \"" + actTitle[k] + "\";\n");

            stringbuffer.append("\n");
            stringbuffer.append("    String pgmTitleHG = \"" + pgmTitle + "\uAC80\uC0C9\";  \n");
            stringbuffer.append("\n");
            stringbuffer.append("    if(posName == null) { posName = " + actKey[1] + "HG; }\n");
            stringbuffer.append("    if(posField == null) { posField = \"" + actKey[1] + "\"; }\n");
            stringbuffer.append("    if(posValue == null) { posValue = \"\"; }\n");
            stringbuffer.append("    if(posOrder == null) { posOrder = \"Asc\"; }\n");
            stringbuffer.append("  \n");
            stringbuffer.append("    int currentPage  = 1;  \n");
            stringbuffer.append("    int totalCount = 0;  \n");
            stringbuffer.append("    int pageSize = 6;  \n");
            stringbuffer.append("    if ( current_Page != null ) {  currentPage = Integer.parseInt(current_Page);  }  \n");
            stringbuffer.append("       \n");
            stringbuffer.append("    " + entityName + " " + entity + " = new " + entityName + "() ; \n");
            stringbuffer.append("    Vector " + entity + "V = new Vector(); // null;  \n");
            stringbuffer.append("    ConnectionResource resource = null;  \n");
            stringbuffer.append("    try {  \n");
            stringbuffer.append("        resource = new com.bes_line.db.BesConnResource();  \n");
            stringbuffer.append("        " + dbWrapName + " " + dbwrap + " = new " + dbWrapName + "(resource); \n");
            stringbuffer.append("\n");
            stringbuffer.append("        if(!posValue.equals(\"\")) { \n");
            stringbuffer.append("             currentPage = " + dbwrap + ".currentPage(facid,posField,posValue,pageSize,posOrder);\n");
            stringbuffer.append("         }\n");
            stringbuffer.append("        totalCount = " + dbwrap + ".countPage(facid);     \n");
            stringbuffer.append("        " + entity + "V = " + dbwrap + ".selectPage(facid,posField,currentPage,pageSize,posOrder);  \n");
            stringbuffer.append("    } catch(Exception e) {  \n");
            stringbuffer.append("        out.println(e.getMessage()+\"<br>\"+e.toString());  \n");
            stringbuffer.append("        Logger.err.println(this, e.toString());  \n");
            stringbuffer.append("    } finally {  \n");
            stringbuffer.append("        if ( resource != null ) resource.release();  \n");
            stringbuffer.append("    } // end try-catch-finally  \n");
            stringbuffer.append("\n");
            stringbuffer.append("%>\n");
            stringbuffer.append("  \n");
            stringbuffer.append("<html>\n");
            stringbuffer.append("<head>\n");
            stringbuffer.append("<META HTTP-EQUIV=\"content-type\" content=\"text/html; charset=euc-kr\">  \n");
            stringbuffer.append("<LINK REL=\"STYLESHEET\" HREF=\"<%=HTTPSERVER%>/webpages/style/BesWeb.css\" TYPE=\"text/css\">   \n");
            stringbuffer.append("<script src=\"<%=HTTPSERVER%>/webpages/common/BesWeb.js\"></script>\n");
            stringbuffer.append("<title><%=pgmTitleHG%></title>    \n");
            stringbuffer.append("<script language=javascript> \n");
            stringbuffer.append("\n");
            stringbuffer.append("function trMouseOver(obj,classtype){\n");
            stringbuffer.append("   var type =classtype;\n");
            stringbuffer.append("   if ( type == 'a' ) {\n");
            stringbuffer.append("    obj.style.backgroundColor='#7fffd4';\n");
            stringbuffer.append("   } else {\n");
            stringbuffer.append("    obj.style.backgroundColor='#7fffd4';\n");
            stringbuffer.append("   }\n");
            stringbuffer.append("}\n");
            stringbuffer.append("\n");
            stringbuffer.append("function trMouseOut(obj,classtype){\n");
            stringbuffer.append("   var type =classtype;\n");
            stringbuffer.append("   if ( type == 'a' ) {\n");
            stringbuffer.append("    obj.style.backgroundColor='#EFF7EE';\n");
            stringbuffer.append("   } else {\n");
            stringbuffer.append("    obj.style.backgroundColor='#C3E5FF';\n");
            stringbuffer.append("   }\n");
            stringbuffer.append("}\n");
            stringbuffer.append("  \n");
            stringbuffer.append("function submit_p1(page,posField,posName)  \n");
            stringbuffer.append("{  \n");
            stringbuffer.append("   var pagechk = page;\n");
            stringbuffer.append("   if ( pagechk == '0' ) {\n");
            stringbuffer.append("    var orders = document.besform.posOrder.value ;\n");
            stringbuffer.append("     if (( orders == 'Asc' )&&( besform.posField.value == posField )) { document.besform.posOrder.value='Desc'; }\n");
            stringbuffer.append("     if ( orders == 'Desc' ) { document.besform.posOrder.value='Asc'; }\n");
            stringbuffer.append("   document.besform.current_Page.value = '1' ; \n");
            stringbuffer.append("   } else {\n");
            stringbuffer.append("   document.besform.current_Page.value = page ; \n");
            stringbuffer.append("   }\n");
            stringbuffer.append("   document.besform.posField.value = posField ; \n");
            stringbuffer.append("   document.besform.posName.value = posName ; \n");
            stringbuffer.append("   document.besform.posValue.value = '' ; \n");
            stringbuffer.append("\n");
            stringbuffer.append("  document.besform.submit();      \n");
            stringbuffer.append("}  \n");
            stringbuffer.append("\n");
            stringbuffer.append("function prompt_use(" + promptKey[1]);
            for(int l = 2; l <= promptCount; l++)
                stringbuffer.append("," + promptKey[l]);

            stringbuffer.append(") {\n");
            stringbuffer.append("    self.close(); \n");
            for(int i1 = 1; i1 <= promptCount; i1++)
                stringbuffer.append("    opener.document.besform." + promptKey[i1] + ".value = " + promptKey[i1] + ";\n");

            stringbuffer.append("    opener.document.besform." + promptKey[1] + ".focus();\n");
            stringbuffer.append("} \n");
            stringbuffer.append("</script>\n");
            stringbuffer.append("</head>\n");
            stringbuffer.append("\n");
            stringbuffer.append("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\"  onload=\"javascript:besform.posValue.focus();\"  >    \n");
            stringbuffer.append("<form name=besform method=post action=\"" + allPgmName + "\" >    \n");
            stringbuffer.append("<table border=0 width=100% align=\"center\" cellpadding=\"0\" cellspacing=\"0\"  >\n");
            stringbuffer.append("    <tr><td > <img src=\"../../images/popup.gif\" > </td></tr>\n");
            stringbuffer.append("    <tr><td bgcolor=\"#000000\" height=\"1\"></td></tr>\n");
            stringbuffer.append("    <tr><td bgcolor=\"#cfcfcf\" height=\"5\"></td></tr>\n");
            stringbuffer.append("    <tr><td bgcolor=\"#000000\" height=\"1\"></td></tr>\n");
            stringbuffer.append(" <tr><td width=98% align=\"center\"> <br>     \n");
            stringbuffer.append("\n");
            stringbuffer.append("<table border=0 width=98% >\n");
            stringbuffer.append("  <tr><td align=center colspan=2 height=\"30\" class=c><font color=\"blue\" size=\"4\"><%=pgmTitleHG%></font></td></tr>    \n");
            stringbuffer.append("  <tr><td class=i align=center width=20% ><%=posName%></td>   \n");
            stringbuffer.append("      <td align=left class=\"c\" width=60% >\n");
            stringbuffer.append("        <input type=hidden name=facid value=\"<%=facid%>\">\n");
            stringbuffer.append("        <input type=hidden name=posName value=\"<%=posName%>\">\n");
            stringbuffer.append("        <input type=hidden name=posField value=\"<%=posField%>\">\n");
            stringbuffer.append("        <input type=hidden name=posOrder value=\"<%=posOrder%>\">\n");
            stringbuffer.append("        <input type=hidden name=current_Page value=\"<%=currentPage%>\">\n");
            stringbuffer.append("        <input type=text name=posValue value=\"<%=posValue%>\"> \n");
            stringbuffer.append("        <input type=submit value='' style=\"background: url(../../images/icon/move.jpg); border:0; width:60;height:23;cursor:hand;\"></td>\n");
            stringbuffer.append("  </tr>  \n");
            stringbuffer.append("</table>  \n");
            stringbuffer.append("\n");
            stringbuffer.append("<table border=0 width=98% >\n");
            stringbuffer.append(" <TR>        \n");
            stringbuffer.append("  <th ><%=posOrder%></th>   \n");
            for(int j1 = 1; j1 <= actCount; j1++)
                stringbuffer.append("  <th ><a href=\"javascript:submit_p1(0,'" + actKey[j1] + "','<%=" + actKey[j1] + "HG%>')\" ><%=" + actKey[j1] + "HG%></a></th> \n");

            stringbuffer.append(" </tr>\n");
            stringbuffer.append("<%  \n");
            stringbuffer.append("     String clsx = \"\", idx = \"\"  ;   \n");
            stringbuffer.append("        int vSize = " + entity + "V.size()  ;   \n");
            stringbuffer.append("   for( int i = 1 ; i <= vSize ; i++) {  \n");
            stringbuffer.append("     if( i%2 == 1) { clsx=\"b\"; }  else  { clsx=\"a\";  }// end if  \n");
            stringbuffer.append("     " + entity + " = (" + entityName + ") " + entity + "V.elementAt(i-1); \n");
            stringbuffer.append("     Utility.fixNullAndTrim(" + entity + "); \n");
            stringbuffer.append("%>  \n");
            stringbuffer.append("     <tr  class='<%=clsx%>'  onmouseover=\"trMouseOver(this,'<%=clsx%>')\" onmouseout=\"trMouseOut(this,'<%=clsx%>')\">\n");
            stringbuffer.append("         <a href=\"javascript:prompt_use('<%=bu.fromDB(" + getpromptKey[1] + ")%>'");
            for(int k1 = 2; k1 <= promptCount; k1++)
                stringbuffer.append(",'<%=bu.fromDB(" + getpromptKey[k1] + ")%>'");

            stringbuffer.append("); \" >  \n");
            stringbuffer.append("\n");
            stringbuffer.append("     <%if(posOrder.equalsIgnoreCase(\"Desc\")){%>\n");
            stringbuffer.append("         <td  align=\"center\"> <%=totalCount- (((currentPage-1)*pageSize)+i)+1%></td> \n");
            stringbuffer.append("     <%}else{%>\n");
            stringbuffer.append("         <td  align=\"center\"> <%=(((currentPage-1)*pageSize)+i)%></td> \n");
            stringbuffer.append("     <%}%>\n");
            stringbuffer.append("\n");
            for(int l1 = 1; l1 <= actCount; l1++)
                stringbuffer.append("         <td  align=\"left\">&nbsp;<%=" + actFormat[l1] + "%>&nbsp;</td> \n");

            stringbuffer.append("    </a></tr>   \n");
            stringbuffer.append("<%    } // end for  %>\n");
            stringbuffer.append("    </table>  \n");
            stringbuffer.append("<%  \n");
            stringbuffer.append("int pageWidth = 10;  \n");
            stringbuffer.append("int endPage = ( (totalCount-1) / pageSize ) +1 ;  \n");
            stringbuffer.append("  if ( currentPage > endPage )\n");
            stringbuffer.append("  {\n");
            stringbuffer.append("    currentPage = endPage ;  \n");
            stringbuffer.append("  }\n");
            stringbuffer.append("int prevPage =  currentPage - 1 ;  \n");
            stringbuffer.append("  if ( prevPage < 1 )\n");
            stringbuffer.append("  {\n");
            stringbuffer.append("    prevPage = 1 ;  \n");
            stringbuffer.append("  }\n");
            stringbuffer.append("int nextPage =  currentPage + 1 ;  \n");
            stringbuffer.append("  if ( nextPage > endPage )\n");
            stringbuffer.append("  {\n");
            stringbuffer.append("    nextPage = endPage ;  \n");
            stringbuffer.append("  }\n");
            stringbuffer.append("int fromPage = ( (currentPage-1) / pageWidth )*pageWidth +1 ;  \n");
            stringbuffer.append("int toPage =  fromPage + pageWidth - 1 ;  \n");
            stringbuffer.append("  if ( toPage > endPage )\n");
            stringbuffer.append("  {\n");
            stringbuffer.append("    toPage = endPage ;  \n");
            stringbuffer.append("  }\n");
            stringbuffer.append("  \n");
            stringbuffer.append("\n");
            stringbuffer.append("%>  \n");
            stringbuffer.append("<table>\n");
            stringbuffer.append("  <tr>\n");
            stringbuffer.append("    <td> \uCD1D&nbsp;<%=totalCount%>&nbsp;\uAC74&nbsp;&nbsp; </td>\n");
            stringbuffer.append("    <% if ( currentPage != 1 ){    %>\n");
            stringbuffer.append("    <td><a href=\"javascript:submit_p1(1,'<%=posField%>','<%=posName%>')\" > \u2225</a></td>\n");
            stringbuffer.append("    <td><a href=\"javascript:submit_p1('<%=prevPage%>','<%=posField%>','<%=posName%>')\" > \u25C0</a></td>\n");
            stringbuffer.append("    <% }else{    %>\n");
            stringbuffer.append("    <td> \u2225</td>\n");
            stringbuffer.append("    <td> \u25C1</td>\n");
            stringbuffer.append("    <% }    %>\n");
            stringbuffer.append("    <% if ( fromPage != 1 ){    %>\n");
            stringbuffer.append("    <td><a href=\"javascript:submit_p1('<%=(fromPage-1)%>','<%=posField%>','<%=posName%>')\" > \u226A</a></td>\n");
            stringbuffer.append("    <% }    %>\n");
            stringbuffer.append("    <% for ( int i = fromPage ; i <= toPage ; i++ ) {  %>\n");
            stringbuffer.append("    <%   if ( i == currentPage ){    %>\n");
            stringbuffer.append("    <td><font color = red>[<%=i%>]</font></td>\n");
            stringbuffer.append("    <%   }else{    %>\n");
            stringbuffer.append("    <td><a href=\"javascript:submit_p1('<%=i%>','<%=posField%>','<%=posName%>')\" >[<%=i%>]</a></td>\n");
            stringbuffer.append("    <%   }  %> \n");
            stringbuffer.append("    <% }    %>\n");
            stringbuffer.append("    <% if ( toPage != endPage ){    %>\n");
            stringbuffer.append("    <td><a href=\"javascript:submit_p1('<%=(toPage+1)%>','<%=posField%>','<%=posName%>')\" > \u226B</a></td>\n");
            stringbuffer.append("    <% } %>\n");
            stringbuffer.append("    <% if ( currentPage != endPage ){    %>\n");
            stringbuffer.append("    <td><a href=\"javascript:submit_p1('<%=(nextPage)%>','<%=posField%>','<%=posName%>')\" > \u25B6</a></td>\n");
            stringbuffer.append("    <td><a href=\"javascript:submit_p1('<%=(endPage)%>','<%=posField%>','<%=posName%>')\" > \u2225</a></td>\n");
            stringbuffer.append("    <% }else{    %>\n");
            stringbuffer.append("    <td> \u25B7</td>\n");
            stringbuffer.append("    <td> \u2225</td>\n");
            stringbuffer.append("    <% } %>\n");
            stringbuffer.append("        <td>&nbsp;&nbsp; \uD604\uC7AC \uD398\uC774\uC9C0:<%=currentPage%>/<%=endPage%></td>\n");
            stringbuffer.append("  </tr>\n");
            stringbuffer.append("</table> \n");
            stringbuffer.append("</form>  \n");
            stringbuffer.append("  <%=bu.FOOTER%>\n");
            stringbuffer.append("</td>\n");
            stringbuffer.append("    </tr>\n");
            stringbuffer.append("  </table>\n");
            stringbuffer.append("</body>\n");
            stringbuffer.append("</html>\n");
            s = stringbuffer.toString();
            stringbuffer1.append("\n");
            stringbuffer1.append("package com.bes_line.mst;\n\n");
            stringbuffer1.append("// DBWrapper's Wrapper  Class for " + fileName + "\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append(" *\n");
            stringbuffer1.append(" * @(#) " + dbWrapName + ".java\n");
            stringbuffer1.append(" * Copyright 1999-2001 by Daewoo Information System, Inc.,\n");
            stringbuffer1.append(" * BES(Best Enterprise System) Team,\n");
            stringbuffer1.append(" * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea\n");
            stringbuffer1.append(" * All rights reserved.\n");
            stringbuffer1.append(" *\n");
            stringbuffer1.append(" * NOTICE !  You cannot copy or redistribute this code,\n");
            stringbuffer1.append(" * and you should not remove the information about the\n");
            stringbuffer1.append(" * copyright notice and the author.\n");
            stringbuffer1.append(" *\n");
            stringbuffer1.append(" * @version v0.1\n");
            stringbuffer1.append(" * @date    " + s2 + "\n");
            stringbuffer1.append(" * @author  WonDeok Kim, wdkim(at)disc.co.kr.\n");
            stringbuffer1.append(" * @since   JDK1.2\n");
            stringbuffer1.append(" *\n");
            stringbuffer1.append(" */\n\n");
            stringbuffer1.append("import java.sql.*;\n");
            stringbuffer1.append("import org.jsn.jdf.db.*;\n\n");
            stringbuffer1.append("public class " + fileName + "DBWrap extends " + fileName + "DBWrapBES{\n");
            stringbuffer1.append("public " + fileName + "DBWrap(ConnectionContext ctx){\n");
            stringbuffer1.append("    super(ctx);\n");
            stringbuffer1.append("} // Constructor\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("////////////////// User Define Code Start //////////////////\n\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Select Page \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(primeKeyType[1] + " " + primeKey[1]);
            stringbuffer1.append("\n");
            stringbuffer1.append("* @return java.util.Vector \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s2 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public java.util.Vector selectPage(");
            stringbuffer1.append(primeKeyType[1] + " ");
            stringbuffer1.append(primeKey[1]);
            stringbuffer1.append(",String fldname, int page, int pagesize) throws Exception{\n");
            stringbuffer1.append("return selectPage(");
            stringbuffer1.append(primeKey[1]);
            stringbuffer1.append(",fldname,page,pagesize,\"Asc\") ;\n");
            stringbuffer1.append("}// end selectPage\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Select Page\n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(primeKeyType[1] + " " + primeKey[1]);
            stringbuffer1.append("* @return java.util.Vector \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s2 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public java.util.Vector selectPage(");
            stringbuffer1.append(primeKeyType[1] + " ");
            stringbuffer1.append(primeKey[1]);
            stringbuffer1.append(",String fldname, int page, int pagesize, String keyorder) throws Exception{\n");
            stringbuffer1.append("    java.util.Vector " + entity + "V = new java.util.Vector();\n");
            stringbuffer1.append("    " + entityName + " " + entity + " = null;\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"Select ");
            for(int i2 = 1; i2 <= colCount; i2++)
            {
                if(i2 != 1 && i2 % 9 == 1)
                    stringbuffer1.append("\" +\n                              \"");
                stringbuffer1.append(colLabel[i2].toLowerCase());
                if(i2 < colCount)
                    stringbuffer1.append(", ");
            }

            stringbuffer1.append(" \" +\n                       \"  from " + fileName + " ");
            stringbuffer1.append(" \" +\n                       \"  where ");
            stringbuffer1.append(primeKey[1] + " = ?    ");
            stringbuffer1.append(" \" +\n                       \"  order by \"+fldname+\" \"+keyorder; \n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            stringbuffer1.append("        pstmt.set" + initialCapital(primeKeyType[1]) + "(1," + primeKey[1] + "); \n");
            stringbuffer1.append("        rs = pstmt.executeQuery();\n");
            stringbuffer1.append("        int count = 0;\n");
            stringbuffer1.append("        while((count < (page-1)*pagesize ) && ( rs.next())){  count ++; } // page\uB9CC\uD07C \uC2A4\uD0B5\uD558\uAE30\n");
            stringbuffer1.append("            count = 0;\n");
            stringbuffer1.append("        while(rs.next()){\n");
            stringbuffer1.append("            count ++;\n");
            stringbuffer1.append("            if(count > pagesize ) break;\n");
            stringbuffer1.append("            " + fileName.toLowerCase() + " = new " + fileName + "Rec(); // " + fileName + "Rec Constructor\n");
            for(int j2 = 1; j2 <= colCount; j2++)
            {
                stringbuffer1.append("                    ");
                stringbuffer1.append(" " + fileName.toLowerCase() + ".set" + initialCapital(colLabel[j2].toLowerCase()) + "(rs.get" + initialCapital(colType[j2].toLowerCase()) + "(\"" + colLabel[j2].toLowerCase() + "\"));\n");
            }

            stringbuffer1.append("            " + fileName.toLowerCase() + "V.addElement(" + fileName.toLowerCase() + ");\n");
            stringbuffer1.append("        } // end While\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("    return " + fileName.toLowerCase() + "V;\n");
            stringbuffer1.append("} // end selectPage\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Current Page \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(primeKeyType[1] + " " + primeKey[1]);
            stringbuffer1.append("\n");
            stringbuffer1.append("* @return int \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s2 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public int currentPage(");
            stringbuffer1.append(primeKeyType[1] + " ");
            stringbuffer1.append(primeKey[1]);
            stringbuffer1.append(",String fldname, String fldval, int pagesize) throws Exception{\n");
            stringbuffer1.append("return currentPage(");
            stringbuffer1.append(primeKey[1]);
            stringbuffer1.append(",fldname,fldval,pagesize,\"Asc\") ;\n");
            stringbuffer1.append("}// end currentPage\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Current Page\n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(primeKeyType[1] + " " + primeKey[1]);
            stringbuffer1.append("* @return int \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s2 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public int currentPage(");
            stringbuffer1.append(primeKeyType[1] + " ");
            stringbuffer1.append(primeKey[1]);
            stringbuffer1.append(",String fldname, String fldval, int pagesize, String keyorder) throws Exception{\n");
            stringbuffer1.append("    int count = 0;\n");
            stringbuffer1.append("    int page = 0;\n");
            stringbuffer1.append("    String compare = \"\";\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        if(keyorder.equalsIgnoreCase(\"Desc\")){ compare = \" > \";\n");
            stringbuffer1.append("        }else { compare = \" < \";\n");
            stringbuffer1.append("        } // end if (keyorder) \n");
            stringbuffer1.append("        String query = \"SELECT COUNT(*) from " + fileName + " \" +\n");
            stringbuffer1.append("                       \" where ");
            stringbuffer1.append(primeKey[1] + " = ? and \"+fldname+compare+\" ? ");
            stringbuffer1.append("  \";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            stringbuffer1.append("        pstmt.set" + initialCapital(primeKeyType[1]) + "(1," + primeKey[1] + "); \n");
            stringbuffer1.append("        pstmt.setString(2,fldval); \n");
            stringbuffer1.append("        rs = pstmt.executeQuery();\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("        if(rs.next()){\n");
            stringbuffer1.append("            count = rs.getInt(1);\n");
            stringbuffer1.append("            page = ((count)/pagesize)+1;\n");
            stringbuffer1.append("        } // end if\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("    return page;\n");
            stringbuffer1.append("} // end currentPage\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Get Rows CountPage \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(primeKeyType[1] + " " + primeKey[1]);
            stringbuffer1.append("\n");
            stringbuffer1.append("* @return int \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s2 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public int countPage(");
            stringbuffer1.append(primeKeyType[1] + " ");
            stringbuffer1.append(primeKey[1]);
            stringbuffer1.append(") throws Exception{\n");
            stringbuffer1.append("    int count = 0;\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"SELECT COUNT(*) from " + fileName + " \" +\n");
            stringbuffer1.append("                       \" where ");
            stringbuffer1.append(primeKey[1] + " = ? ");
            stringbuffer1.append("  \";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            stringbuffer1.append("        pstmt.set" + initialCapital(primeKeyType[1]) + "(1," + primeKey[1] + "); \n");
            stringbuffer1.append("        rs = pstmt.executeQuery();\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("        if(rs.next()){\n");
            stringbuffer1.append("            count = rs.getInt(1);\n");
            stringbuffer1.append("        } // end if\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("    return count;\n");
            stringbuffer1.append("} // end countPage\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("////////////////// User Define  Code  End //////////////////\n");
            stringbuffer1.append("}// end " + dbWrapName + "Bes class");
            s1 = stringbuffer1.toString();
            jtRec.setText(s);
            jtDBWrap.setText(s1);
            File file = null;
            file = new File("./prompt");
            file.mkdir();
            PrintWriter printwriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./prompt/" + allPgmName, false)));
            printwriter.print(s);
            printwriter.close();
            printwriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream("./prompt/" + dbWrapName + ".java+", false)));
            printwriter.print(s1);
            printwriter.close();
        }
        catch(Exception exception)
        {
            System.out.println("error :: " + exception.toString());
        }
    }

    public void setUpSportColumn(TableColumn tablecolumn)
    {
        JComboBox jcombobox = new JComboBox();
        jcombobox.addItem("");
        jcombobox.addItem("\uD544\uC218/\uD788\uB4E0");
        jcombobox.addItem("\uD544\uC218/\uC785\uB825");
        jcombobox.addItem("\uC785\uB825");
        jcombobox.addItem("\uCCB4\uD06C\uBC15\uC2A4");
        jcombobox.addItem("\uB9AC\uC2A4\uD2B8");
        tablecolumn.setCellEditor(new DefaultCellEditor(jcombobox));
    }

    JFrame mainFrame;
    JPanel mainPanel;
    JPanel subP1;
    JPanel subP2;
    JPanel dbPanel;
    JPanel tablePanel;
    JPanel tablePanel2;
    JPanel tablePanel3;
    JPanel resultPanel;
    JLabel jlDriver;
    JLabel jlURL;
    JLabel jlUser;
    JLabel jlPassword;
    JLabel jlPgmName;
    JTextField jtDriver;
    JTextField jtURL;
    JTextField jtUser;
    JTextField jtPgmName;
    JPasswordField jpPassword;
    JButton jbGetTable;
    JButton jbGetScript;
    JList tableList;
    JList dsptableList;
    JList returntableList;
    JTable tableDetail;
    JScrollPane jsTableList;
    JScrollPane jsTable;
    JLabel jltl;
    JLabel jlTableName;
    JLabel jlxxx;
    DefaultListModel dlmList;
    DefaultTableModel dtmTable;
    ListSelectionModel colTable;
    JTextArea jtSQL;
    JTextArea jtRec;
    JTextArea jtDBWrapBES;
    JTextArea jtDBWrap;
    JScrollPane jsSQL;
    JScrollPane jsRec;
    JScrollPane jsDBWrapBES;
    JScrollPane jsDBWrap;
    String fileName;
    String fileName1;
    String entityName;
    String dbWrapName;
    String entity;
    String dbwrap;
    String entityName1;
    String dbWrapName1;
    String entity1;
    String dbwrap1;
    String allPgmName;
    String pgmTitle;
    int primeCount;
    int listCount;
    int promptCount;
    int actCount;
    String primeKey[];
    String listKey[];
    String promptKey[];
    String actKey[];
    String getprimeKey[];
    String getlistKey[];
    String getpromptKey[];
    String getactKey[];
    String primeKeyType[];
    String listKeyType[];
    String promptKeyType[];
    String actKeyType[];
    int primeKeySize[];
    int listKeySize[];
    int promptKeySize[];
    int actKeySize[];
    String actTitle[];
    String actReqChk[];
    String actDspChk[];
    String actDatChk[];
    String actFormat[];
    String colLabel[];
    String colType[];
    String colTypeName[];
    int colCount;
    int colPrecision[];
    int colScale[];
}
