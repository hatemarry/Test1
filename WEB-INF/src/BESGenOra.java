import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class BESGenOra extends JFrame
{
    class My_tableHandler
        implements ListSelectionListener
    {

        public void valueChanged(ListSelectionEvent listselectionevent)
        {
            if(listselectionevent.getSource() == colTable)
            {
                keyVector.clear();
                int i = tableList.getSelectedIndex();
                if(i < 0)
                    return;
                String s = (String)dlmList.getElementAt(i);
                jlTableName.setText(s);
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
                    for(resultset1 = databasemetadata.getPrimaryKeys(null, null, s); resultset1.next(); vector.addElement(resultset1.getString("COLUMN_NAME").toUpperCase()));
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
                                keyVector.add(resultsetmetadata.getColumnLabel(k1).toUpperCase());
                                break;
                            }
                            s1 = "";
                        }

                        String as1[] = {
                            k1 + "", s1, resultsetmetadata.getColumnLabel(k1).toUpperCase(), resultsetmetadata.getColumnTypeName(k1), "(" + resultsetmetadata.getPrecision(k1) + "," + resultsetmetadata.getScale(k1) + ")"
                        };
                        dtmTable.addRow(as1);
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
        new BESGenOra();
    }

    public BESGenOra()
    {
        keyVector = new Vector();
        mainFrame = new JFrame("Bean Generator");
        mainPanel = new JPanel(new BorderLayout());
        jlDriver = new JLabel("Driver", 0);
        jlURL = new JLabel("URL", 0);
        jlUser = new JLabel("User", 0);
        jlPassword = new JLabel("Password", 0);
        String s = "C:/gen.properties";
        Properties properties = new Properties();
        try
        {
            FileInputStream fileinputstream = new FileInputStream(s);
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
        GridBagLayout gridbaglayout = new GridBagLayout();
        dbPanel = new JPanel(gridbaglayout);
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.fill = 1;
        gridbagconstraints.gridheight = 4;
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
        tablePanel = new JPanel(gridbaglayout);
        gridbagconstraints.gridwidth = 1;
        jltl = new JLabel("Table List", 0);
        tablePanel.add(jltl);
        jlxxx = new JLabel("  ", 0);
        gridbaglayout.setConstraints(jlxxx, gridbagconstraints);
        tablePanel.add(jlxxx);
        gridbagconstraints.gridwidth = 0;
        jlTableName = new JLabel("Table Name", 0);
        gridbaglayout.setConstraints(jlTableName, gridbagconstraints);
        tablePanel.add(jlTableName);
        dlmList = new DefaultListModel();
        Vector vector = getTableNames();
        Iterator iterator = vector.iterator();
        String s1 = "";
        String s2;
        for(; iterator.hasNext(); dlmList.addElement(s2))
            s2 = (String)iterator.next();

        tableList = new JList(dlmList);
        colTable = tableList.getSelectionModel();
        colTable.addListSelectionListener(new My_tableHandler());
        jsTableList = new JScrollPane(tableList);
        gridbagconstraints.gridwidth = 1;
        gridbaglayout.setConstraints(jsTableList, gridbagconstraints);
        tablePanel.add(jsTableList);
        jlxxx = new JLabel(">>", 0);
        gridbaglayout.setConstraints(jlxxx, gridbagconstraints);
        tablePanel.add(jlxxx);
        dtmTable = new DefaultTableModel();
        dtmTable.addColumn("Seq");
        dtmTable.addColumn("Key");
        dtmTable.addColumn("Field Name");
        dtmTable.addColumn("Type");
        dtmTable.addColumn("Size");
        tableDetail = new JTable(dtmTable);
        tableDetail.sizeColumnsToFit(4);
        tableDetail.setSelectionMode(0);
        jsTable = new JScrollPane(tableDetail);
        gridbagconstraints.gridwidth = 0;
        gridbaglayout.setConstraints(jsTable, gridbagconstraints);
        tablePanel.add(jsTable);
        jbGetScript = new JButton("Generate Scripts");
        gridbaglayout.setConstraints(jbGetScript, gridbagconstraints);
        tablePanel.add(jbGetScript);
        resultPanel = new JPanel(gridbaglayout);
        jtSQL = new JTextArea("SQL Script", 5, 5);
        jtRec = new JTextArea("Record Bean", 5, 5);
        jtDBWrapBES = new JTextArea("DBWrap Origin", 5, 5);
        jtDBWrap = new JTextArea("User DBWrap", 5, 5);
        jsSQL = new JScrollPane(jtSQL);
        jsRec = new JScrollPane(jtRec);
        jsDBWrapBES = new JScrollPane(jtDBWrapBES);
        jsDBWrap = new JScrollPane(jtDBWrap);
        gridbagconstraints.gridwidth = 1;
        gridbaglayout.setConstraints(jsSQL, gridbagconstraints);
        resultPanel.add(jsSQL);
        gridbagconstraints.gridwidth = 0;
        gridbaglayout.setConstraints(jsRec, gridbagconstraints);
        resultPanel.add(jsRec);
        gridbagconstraints.gridwidth = 1;
        gridbaglayout.setConstraints(jsDBWrapBES, gridbagconstraints);
        resultPanel.add(jsDBWrapBES);
        gridbagconstraints.gridwidth = 0;
        gridbaglayout.setConstraints(jsDBWrap, gridbagconstraints);
        resultPanel.add(jsDBWrap);
        mainPanel.add(dbPanel, "North");
        mainPanel.add(tablePanel, "Center");
        mainPanel.add(resultPanel, "South");
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
            "TABLE"
        };
        StringBuffer stringbuffer = new StringBuffer();
        try
        {
            Class.forName(jtDriver.getText()).newInstance();
            connection = DriverManager.getConnection(jtURL.getText(), jtUser.getText(), new String(jpPassword.getPassword()));
            DatabaseMetaData databasemetadata = null;
            databasemetadata = connection.getMetaData();
            for(resultset = databasemetadata.getTables(null, null, "%", as); resultset.next(); vector.add(resultset.getString("TABLE_NAME").toUpperCase()));
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
        if(s.equalsIgnoreCase("VARCHAR2") || s.equalsIgnoreCase("CHAR") || s.equalsIgnoreCase("DATE") || s.equalsIgnoreCase("VARCHAR") || s.equalsIgnoreCase("LONG VARCHAR") || s.equalsIgnoreCase("bpchar"))
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx : " + s);
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

    public void genScript()
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        ResultSet resultset1 = null;
        Object obj = null;
        String s13 = "com.bes_line.mst";
        String s14 = "";
        String s16 = "";
        String s18 = "";
        String s20 = "";
        StringBuffer stringbuffer = new StringBuffer();
        StringBuffer stringbuffer1 = new StringBuffer();
        StringBuffer stringbuffer2 = new StringBuffer();
        StringBuffer stringbuffer3 = new StringBuffer();
        try
        {
            connection = DriverManager.getConnection(jtURL.getText(), jtUser.getText(), new String(jpPassword.getPassword()));
            statement = connection.createStatement();
            String s22 = jlTableName.getText();
            s22 = s22.toUpperCase();
            String s23 = s22;
            s23 = s23 + "Rec";
            String s24 = s22;
            s24 = s24 + "DBWrap";
            DatabaseMetaData databasemetadata = connection.getMetaData();
            Vector vector = new Vector();
            for(resultset1 = databasemetadata.getPrimaryKeys(null, null, s22); resultset1.next(); vector.addElement(resultset1.getString("COLUMN_NAME").toUpperCase()));
            vector = keyVector;
            int i = vector.size();
            String as[] = new String[i + 1];
            String as1[] = new String[i + 1];
            String as2[] = new String[i + 1];
            for(int j = 1; j <= i; j++)
            {
                as[j] = (String)vector.elementAt(j - 1);
                as[j] = as[j].toLowerCase();
                as1[j] = s22.toLowerCase() + ".get" + initialCapital(as[j]);
            }

            resultset = statement.executeQuery("SELECT * FROM " + s22);
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            int k = resultsetmetadata.getColumnCount();
            String as3[] = new String[k];
            for(int l = 1; l <= k; l++)
                as3[l - 1] = resultsetmetadata.getColumnLabel(l);

            String as4[] = TimeZone.getAvailableIDs(0x1ee6280);
            SimpleTimeZone simpletimezone = new SimpleTimeZone(0x1ee6280, as4[0]);
            GregorianCalendar gregoriancalendar = new GregorianCalendar(simpletimezone);
            String s25 = gregoriancalendar.get(1) + "-" + (gregoriancalendar.get(2) + 1) + "-" + gregoriancalendar.get(5);
            stringbuffer.append("package com.bes_line.mst;\n\n");
            stringbuffer.append("// Entity Class for " + s22 + "\n");
            stringbuffer.append("/**\n");
            stringbuffer.append(" *\n");
            stringbuffer.append(" * @(#) " + s23 + ".java\n");
            stringbuffer.append(" * Copyright 1999-2001 by  Daewoo Information System, Inc.,\n");
            stringbuffer.append(" * BES(Best Enterprise System) Team,\n");
            stringbuffer.append(" * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea\n");
            stringbuffer.append(" * All rights reserved.\n");
            stringbuffer.append(" *\n");
            stringbuffer.append(" * NOTICE !  You cannot copy or redistribute this code,\n");
            stringbuffer.append(" * and you should not remove the information about the\n");
            stringbuffer.append(" * copyright notice and the author.\n");
            stringbuffer.append(" *\n");
            stringbuffer.append(" * @version v0.1\n");
            stringbuffer.append(" * @date    " + s25 + "\n");
            stringbuffer.append(" * @author  WonDeok Kim, wdkim(at)disc.co.kr.\n");
            stringbuffer.append(" * @since   JDK1.2\n");
            stringbuffer.append(" *\n");
            stringbuffer.append(" */\n\n");
            stringbuffer.append("import org.jsn.jdf.db.*;\n\n");
            stringbuffer.append("public class " + s22 + "Rec extends EntityData {\n");
            stringbuffer.append("    // NUMERIC = Zoned Decimal, DECIMAL = Packed Decimal. (7.3 = 1234.123) \n");
            String s = "";
            for(int i1 = 1; i1 <= k; i1++)
            {
                String s1 = dataType(resultsetmetadata.getColumnTypeName(i1), resultsetmetadata.getPrecision(i1), resultsetmetadata.getScale(i1));
                stringbuffer.append("    public " + s1 + " " + resultsetmetadata.getColumnLabel(i1).toLowerCase() + "; \t\t// (" + resultsetmetadata.getColumnTypeName(i1) + ", " + resultsetmetadata.getPrecision(i1) + "." + resultsetmetadata.getScale(i1) + ")\n");
                for(int j1 = 1; j1 <= i; j1++)
                    if(as[j1].equalsIgnoreCase(resultsetmetadata.getColumnLabel(i1)))
                        as2[j1] = s1;

            }

            stringbuffer.append("\n");
            stringbuffer.append("public " + s22 + "Rec(){ } // default constructor\n\n");
            stringbuffer.append("public " + s22 + "Rec(");
            for(int k1 = 1; k1 <= k; k1++)
            {
                if(k1 % 6 == 1)
                    stringbuffer.append("\n       ");
                String s2 = dataType(resultsetmetadata.getColumnTypeName(k1), resultsetmetadata.getPrecision(k1), resultsetmetadata.getScale(k1));
                stringbuffer.append(s2 + " " + resultsetmetadata.getColumnLabel(k1).toLowerCase());
                if(k1 < k)
                    stringbuffer.append(", ");
            }

            stringbuffer.append("){\n");
            for(int l1 = 1; l1 <= k; l1++)
                stringbuffer.append("    this." + resultsetmetadata.getColumnLabel(l1).toLowerCase() + " = " + resultsetmetadata.getColumnLabel(l1).toLowerCase() + ";\n");

            stringbuffer.append("} // Constructor\n");
            stringbuffer.append("\n");
            stringbuffer.append("\n// Getter \n");
            for(int i2 = 1; i2 <= k; i2++)
            {
                String s3 = dataType(resultsetmetadata.getColumnTypeName(i2), resultsetmetadata.getPrecision(i2), resultsetmetadata.getScale(i2));
                stringbuffer.append("public " + s3 + " get" + initialCapital(resultsetmetadata.getColumnLabel(i2)) + "(){ return " + resultsetmetadata.getColumnLabel(i2).toLowerCase() + ";}\n");
            }

            stringbuffer.append("\n// Setter \n");
            for(int j2 = 1; j2 <= k; j2++)
            {
                String s4 = dataType(resultsetmetadata.getColumnTypeName(j2), resultsetmetadata.getPrecision(j2), resultsetmetadata.getScale(j2));
                stringbuffer.append("public void set" + initialCapital(resultsetmetadata.getColumnLabel(j2)) + "(" + s4 + " " + resultsetmetadata.getColumnLabel(j2).toLowerCase() + "){ this." + resultsetmetadata.getColumnLabel(j2).toLowerCase() + " = " + resultsetmetadata.getColumnLabel(j2).toLowerCase() + ";}\n");
            }

            stringbuffer.append("\n/**\n");
            stringbuffer.append("* getString \n");
            stringbuffer.append("* @param int seq \n");
            stringbuffer.append("* @return field Value \n");
            stringbuffer.append("*/\n");
            stringbuffer.append("public String getString(int seq){ \n");
            stringbuffer.append(" String field=null;\n");
            stringbuffer.append("  switch (seq) {\n");
            for(int k2 = 1; k2 <= k; k2++)
                stringbuffer.append("  case  " + k2 + " : field = " + resultsetmetadata.getColumnLabel(k2).toLowerCase() + " + \"\" ; break;\n");

            stringbuffer.append("  } // end switch\n");
            stringbuffer.append("  return field;\n");
            stringbuffer.append("}// end getString (int seq)\n");
            stringbuffer.append("\n/**\n");
            stringbuffer.append("* getString \n");
            stringbuffer.append("* @param String fieldName \n");
            stringbuffer.append("* @return field Value \n");
            stringbuffer.append("*/\n");
            stringbuffer.append("public String getString(String rec){ \n");
            stringbuffer.append(" String field=null;\n");
            for(int l2 = 1; l2 <= k; l2++)
            {
                if(l2 == 1)
                    stringbuffer.append("     if       (rec.equalsIgnoreCase(\"" + resultsetmetadata.getColumnLabel(l2).toLowerCase() + "\"))");
                else
                    stringbuffer.append("     } else if(rec.equalsIgnoreCase(\"" + resultsetmetadata.getColumnLabel(l2).toLowerCase() + "\"))");
                stringbuffer.append("{ field = " + resultsetmetadata.getColumnLabel(l2).toLowerCase() + " + \"\" ; \n");
            }

            stringbuffer.append("}// end if\n");
            stringbuffer.append(" return field;\n");
            stringbuffer.append("}// end getString (String fieldName)\n");
            stringbuffer.append("\n");
            stringbuffer.append("/**\n");
            stringbuffer.append("* fieldNames \n");
            stringbuffer.append("* @param none \n");
            stringbuffer.append("* @return field Names[]\n");
            stringbuffer.append("*/\n");
            stringbuffer.append("public String[] fieldNames() {\n");
            stringbuffer.append("    String [] tempx = {\"\", ");
            for(int i3 = 1; i3 <= k; i3++)
            {
                stringbuffer.append("\"" + resultsetmetadata.getColumnLabel(i3).toUpperCase() + "\"");
                if(i3 < k)
                    stringbuffer.append(", ");
                if(i3 % 7 == 0)
                    stringbuffer.append("\n       ");
            }

            stringbuffer.append("};\n");
            stringbuffer.append("    return tempx;\n}\n\n");
            stringbuffer.append("/**\n");
            stringbuffer.append("* Key fieldNames \n");
            stringbuffer.append("* @param none \n");
            stringbuffer.append("* @return Key field Names[]\n");
            stringbuffer.append("*/\n");
            stringbuffer.append("public String[] keyFieldNames() {\n");
            stringbuffer.append("    String [] tempx = {\"\", ");
            for(int j3 = 1; j3 <= i; j3++)
            {
                stringbuffer.append("\"" + as[j3].toUpperCase() + "\"");
                if(j3 < i)
                    stringbuffer.append(", ");
                if(j3 % 7 == 0)
                    stringbuffer.append("\n       ");
            }

            stringbuffer.append("};\n");
            stringbuffer.append("    return tempx;\n}\n\n");
            stringbuffer.append("}// end " + s23 + " class");
            String s15 = stringbuffer.toString();
            stringbuffer1.append("package com.bes_line.mst;\n\n");
            stringbuffer1.append("// DBWrapper Class for " + s22 + "\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append(" *\n");
            stringbuffer1.append(" * @(#) " + s24 + "BES.java\n");
            stringbuffer1.append(" * Copyright 1999-2001 by  Daewoo Information System, Inc.,\n");
            stringbuffer1.append(" * BES(Best Enterprise System) Team,\n");
            stringbuffer1.append(" * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea\n");
            stringbuffer1.append(" * All rights reserved.\n");
            stringbuffer1.append(" *\n");
            stringbuffer1.append(" * NOTICE !  You cannot copy or redistribute this code,\n");
            stringbuffer1.append(" * and you should not remove the information about the\n");
            stringbuffer1.append(" * copyright notice and the author.\n");
            stringbuffer1.append(" *\n");
            stringbuffer1.append(" * @version v0.1\n");
            stringbuffer1.append(" * @date    " + s25 + "\n");
            stringbuffer1.append(" * @author  WonDeok Kim, wdkim(at)disc.co.kr.\n");
            stringbuffer1.append(" * @since   JDK1.2\n");
            stringbuffer1.append(" *\n");
            stringbuffer1.append(" */\n\n");
            stringbuffer1.append("import java.sql.*;\n");
            stringbuffer1.append("import org.jsn.jdf.db.*;\n\n");
            stringbuffer1.append("public class " + s22 + "DBWrapBES extends DBWrapper{\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("public " + s22 + "DBWrapBES(ConnectionContext ctx){\n");
            stringbuffer1.append("    super(ctx);\n");
            stringbuffer1.append("} // Constructor\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Get one Record \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(as2[1] + " " + as[1]);
            for(int k3 = 2; k3 <= i; k3++)
                stringbuffer1.append(", " + as2[k3] + " " + as[k3]);

            stringbuffer1.append("\n");
            stringbuffer1.append("* @return " + s22 + "Rec \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public " + s22 + "Rec select(");
            stringbuffer1.append(as2[1] + " " + as[1]);
            for(int l3 = 2; l3 <= i; l3++)
                stringbuffer1.append(", " + as2[l3] + " " + as[l3]);

            stringbuffer1.append(") throws Exception{\n");
            stringbuffer1.append("    " + s22 + "Rec " + s22.toLowerCase() + " = null;\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"Select ");
            for(int i4 = 1; i4 <= k; i4++)
            {
                if(i4 != 1 && i4 % 9 == 1)
                    stringbuffer1.append("\" +\n                              \"");
                stringbuffer1.append(resultsetmetadata.getColumnLabel(i4).toLowerCase());
                if(i4 < k)
                    stringbuffer1.append(", ");
            }

            stringbuffer1.append(" \" +\n                       \"  from " + s22 + " ");
            stringbuffer1.append(" \" +\n                       \"  where ");
            stringbuffer1.append(as[1] + " = ? ");
            for(int j4 = 2; j4 <= i; j4++)
                stringbuffer1.append("and " + as[j4] + " = ? ");

            stringbuffer1.append(" \";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            for(int k4 = 1; k4 <= i; k4++)
                stringbuffer1.append("        pstmt.set" + initialCapital(as2[k4]) + "(" + k4 + "," + as[k4] + "); \n");

            stringbuffer1.append("        rs = pstmt.executeQuery();\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("        if(rs.next()){\n");
            stringbuffer1.append("            " + s22.toLowerCase() + " = new " + s22 + "Rec(); // " + s22 + "Rec Constructor\n");
            for(int l4 = 1; l4 <= k; l4++)
            {
                String s5 = dataType(resultsetmetadata.getColumnTypeName(l4), resultsetmetadata.getPrecision(l4), resultsetmetadata.getScale(l4));
                stringbuffer1.append("                    ");
                stringbuffer1.append(" " + s22.toLowerCase() + ".set" + initialCapital(resultsetmetadata.getColumnLabel(l4).toLowerCase()) + "(rs.get" + initialCapital(s5) + "(\"" + resultsetmetadata.getColumnLabel(l4).toLowerCase() + "\"));\n");
            }

            stringbuffer1.append("        } else {\n");
            stringbuffer1.append("            throw new DataNotFoundException();\n");
            stringbuffer1.append("        } // end if\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("    return " + s22.toLowerCase() + ";\n");
            stringbuffer1.append("} // end select\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Get All Record \n");
            stringbuffer1.append("* @param  void \n");
            stringbuffer1.append("* @return java.util.Vector \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public java.util.Vector selectAll() throws Exception{\n");
            stringbuffer1.append("    java.util.Vector " + s22.toLowerCase() + "V = new java.util.Vector();\n");
            stringbuffer1.append("    " + s22 + "Rec " + s22.toLowerCase() + " = null;\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"Select ");
            for(int i5 = 1; i5 <= k; i5++)
            {
                if(i5 != 1 && i5 % 9 == 1)
                    stringbuffer1.append("\" +\n                              \"");
                stringbuffer1.append(resultsetmetadata.getColumnLabel(i5).toLowerCase());
                if(i5 < k)
                    stringbuffer1.append(", ");
            }

            stringbuffer1.append(" \" +\n                       \"  from " + s22 + " \";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            stringbuffer1.append("        rs = pstmt.executeQuery();\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("        while(rs.next()){\n");
            stringbuffer1.append("            " + s22.toLowerCase() + " = new " + s22 + "Rec(); // " + s22 + "Rec Constructor\n");
            for(int j5 = 1; j5 <= k; j5++)
            {
                String s6 = dataType(resultsetmetadata.getColumnTypeName(j5), resultsetmetadata.getPrecision(j5), resultsetmetadata.getScale(j5));
                stringbuffer1.append("                    ");
                stringbuffer1.append(" " + s22.toLowerCase() + ".set" + initialCapital(resultsetmetadata.getColumnLabel(j5).toLowerCase()) + "(rs.get" + initialCapital(s6) + "(\"" + resultsetmetadata.getColumnLabel(j5).toLowerCase() + "\"));\n");
            }

            stringbuffer1.append("            " + s22.toLowerCase() + "V.addElement(" + s22.toLowerCase() + ");\n");
            stringbuffer1.append("        } // end While\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("    return " + s22.toLowerCase() + "V;\n");
            stringbuffer1.append("} // end selectAll\n");
            stringbuffer1.append("\n");
            if(i > 1)
            {
                stringbuffer1.append("/**\n");
                stringbuffer1.append("* Get All Record(condition : last Key except) \n");
                stringbuffer1.append("* @param ");
                stringbuffer1.append(as2[1] + " " + as[1]);
                for(int k5 = 2; k5 < i; k5++)
                    stringbuffer1.append(", " + as2[k5] + " " + as[k5]);

                stringbuffer1.append("\n");
                stringbuffer1.append("* @return java.util.Vector \n");
                stringbuffer1.append("* @author besTeam \n");
                stringbuffer1.append("* @date " + s25 + "\n");
                stringbuffer1.append("*/\n");
                stringbuffer1.append("public java.util.Vector selectAll(");
                stringbuffer1.append(as2[1] + " " + as[1]);
                for(int i6 = 2; i6 < i; i6++)
                    stringbuffer1.append(", " + as2[i6] + " " + as[i6]);

                stringbuffer1.append(") throws Exception{\n");
                stringbuffer1.append("    java.util.Vector " + s22.toLowerCase() + "V = new java.util.Vector();\n");
                stringbuffer1.append("    " + s22 + "Rec " + s22.toLowerCase() + " = null;\n");
                stringbuffer1.append("    PreparedStatement pstmt = null;\n");
                stringbuffer1.append("    ResultSet rs = null;\n");
                stringbuffer1.append("    try{\n");
                stringbuffer1.append("        String query = \"Select ");
                for(int k6 = 1; k6 <= k; k6++)
                {
                    if(k6 != 1 && k6 % 9 == 1)
                        stringbuffer1.append("\" +\n                              \"");
                    stringbuffer1.append(resultsetmetadata.getColumnLabel(k6).toLowerCase());
                    if(k6 < k)
                        stringbuffer1.append(", ");
                }

                stringbuffer1.append(" \" +\n                       \"  from " + s22 + " ");
                stringbuffer1.append(" \" +\n                       \"  where ");
                stringbuffer1.append(as[1] + " = ? ");
                for(int i7 = 2; i7 < i; i7++)
                    stringbuffer1.append("and " + as[i7] + " = ? ");

                stringbuffer1.append(" \" +\n                       \"  order by " + as[i] + "\"; \n");
                stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
                for(int k7 = 1; k7 < i; k7++)
                    stringbuffer1.append("        pstmt.set" + initialCapital(as2[k7]) + "(" + k7 + "," + as[k7] + "); \n");

                stringbuffer1.append("        rs = pstmt.executeQuery();\n");
                stringbuffer1.append("\n");
                stringbuffer1.append("        while(rs.next()){\n");
                stringbuffer1.append("            " + s22.toLowerCase() + " = new " + s22 + "Rec(); // " + s22 + "Rec Constructor\n");
                for(int i8 = 1; i8 <= k; i8++)
                {
                    String s7 = dataType(resultsetmetadata.getColumnTypeName(i8), resultsetmetadata.getPrecision(i8), resultsetmetadata.getScale(i8));
                    stringbuffer1.append("                    ");
                    stringbuffer1.append(" " + s22.toLowerCase() + ".set" + initialCapital(resultsetmetadata.getColumnLabel(i8).toLowerCase()) + "(rs.get" + initialCapital(s7) + "(\"" + resultsetmetadata.getColumnLabel(i8).toLowerCase() + "\"));\n");
                }

                stringbuffer1.append("            " + s22.toLowerCase() + "V.addElement(" + s22.toLowerCase() + ");\n");
                stringbuffer1.append("        } // end While\n");
                stringbuffer1.append("    } finally {\n");
                stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
                stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
                stringbuffer1.append("    } // try-finally\n");
                stringbuffer1.append("    return " + s22.toLowerCase() + "V;\n");
                stringbuffer1.append("} // end selectAll\n");
                stringbuffer1.append("\n");
                stringbuffer1.append("/**\n");
                stringbuffer1.append("* Get between Record(condition : last Key from - to) \n");
                stringbuffer1.append("* @param ");
                stringbuffer1.append(as2[1] + " " + as[1]);
                for(int k8 = 2; k8 < i; k8++)
                    stringbuffer1.append(", " + as2[k8] + " " + as[k8]);

                stringbuffer1.append(", " + as2[i] + " f_" + as[i]);
                stringbuffer1.append(", " + as2[i] + " t_" + as[i]);
                stringbuffer1.append("\n");
                stringbuffer1.append("* @return java.util.Vector \n");
                stringbuffer1.append("* @author besTeam \n");
                stringbuffer1.append("* @date " + s25 + "\n");
                stringbuffer1.append("*/\n");
                stringbuffer1.append("public java.util.Vector selectBetween(");
                stringbuffer1.append(as2[1] + " " + as[1]);
                for(int i9 = 2; i9 < i; i9++)
                    stringbuffer1.append(", " + as2[i9] + " " + as[i9]);

                stringbuffer1.append(", " + as2[i] + " f_" + as[i]);
                stringbuffer1.append(", " + as2[i] + " t_" + as[i]);
                stringbuffer1.append(") throws Exception{\n");
                stringbuffer1.append("    return selectBetween(");
                stringbuffer1.append(as[1]);
                for(int k9 = 2; k9 < i; k9++)
                    stringbuffer1.append(", " + as[k9]);

                stringbuffer1.append(", f_" + as[i]);
                stringbuffer1.append(", t_" + as[i]);
                stringbuffer1.append(", 0);\n");
                stringbuffer1.append("} // end selectBetween\n");
                stringbuffer1.append("\n");
                stringbuffer1.append("/**\n");
                stringbuffer1.append("* Get between Record(condition : last Key from - to) \n");
                stringbuffer1.append("* @param ");
                stringbuffer1.append(as2[1] + " " + as[1]);
                for(int i10 = 2; i10 < i; i10++)
                    stringbuffer1.append(", " + as2[i10] + " " + as[i10]);

                stringbuffer1.append(", " + as2[i] + " f_" + as[i]);
                stringbuffer1.append(", " + as2[i] + " t_" + as[i]);
                stringbuffer1.append(", int lastKeyOrder(0 : ASC-Default, 1 : DESC)\n");
                stringbuffer1.append("* @return java.util.Vector \n");
                stringbuffer1.append("* @author besTeam \n");
                stringbuffer1.append("* @date " + s25 + "\n");
                stringbuffer1.append("*/\n");
                stringbuffer1.append("public java.util.Vector selectBetween(");
                stringbuffer1.append(as2[1] + " " + as[1]);
                for(int k10 = 2; k10 < i; k10++)
                    stringbuffer1.append(", " + as2[k10] + " " + as[k10]);

                stringbuffer1.append(", " + as2[i] + " f_" + as[i]);
                stringbuffer1.append(", " + as2[i] + " t_" + as[i]);
                stringbuffer1.append(", int lastKeyOrder) throws Exception{\n");
                stringbuffer1.append("    java.util.Vector " + s22.toLowerCase() + "V = new java.util.Vector();\n");
                stringbuffer1.append("    " + s22 + "Rec " + s22.toLowerCase() + " = null;\n");
                stringbuffer1.append("    PreparedStatement pstmt = null;\n");
                stringbuffer1.append("    ResultSet rs = null;\n");
                stringbuffer1.append("    try{\n");
                stringbuffer1.append("        String query = \"Select ");
                for(int i11 = 1; i11 <= k; i11++)
                {
                    if(i11 != 1 && i11 % 9 == 1)
                        stringbuffer1.append("\" +\n                              \"");
                    stringbuffer1.append(resultsetmetadata.getColumnLabel(i11).toLowerCase());
                    if(i11 < k)
                        stringbuffer1.append(", ");
                }

                stringbuffer1.append(" \" +\n                       \"  from " + s22 + " ");
                stringbuffer1.append(" \" +\n                       \"  where ");
                stringbuffer1.append(as[1] + " = ? ");
                for(int k11 = 2; k11 < i; k11++)
                    stringbuffer1.append("and " + as[k11] + " = ? ");

                stringbuffer1.append(" \" +\n                       \"  and " + as[i] + " between ? and ? ");
                stringbuffer1.append(" \";\n               if(lastKeyOrder == 1){\n");
                stringbuffer1.append("                   query += \" order by DESC " + as[i] + "\"; \n");
                stringbuffer1.append("               } else {\n");
                stringbuffer1.append("                   query += \" order by " + as[i] + "\"; \n");
                stringbuffer1.append("               } // end if(lastKeyOrder == 1)\n");
                stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
                for(int i12 = 1; i12 < i; i12++)
                    stringbuffer1.append("        pstmt.set" + initialCapital(as2[i12]) + "(" + i12 + "," + as[i12] + "); \n");

                stringbuffer1.append("        pstmt.set" + initialCapital(as2[i]) + "(" + i + ",f_" + as[i] + "); \n");
                stringbuffer1.append("        pstmt.set" + initialCapital(as2[i]) + "(" + (i + 1) + ",t_" + as[i] + "); \n");
                stringbuffer1.append("        rs = pstmt.executeQuery();\n");
                stringbuffer1.append("\n");
                stringbuffer1.append("        while(rs.next()){\n");
                stringbuffer1.append("            " + s22.toLowerCase() + " = new " + s22 + "Rec(); // " + s22 + "Rec Constructor\n");
                for(int k12 = 1; k12 <= k; k12++)
                {
                    String s8 = dataType(resultsetmetadata.getColumnTypeName(k12), resultsetmetadata.getPrecision(k12), resultsetmetadata.getScale(k12));
                    stringbuffer1.append("                    ");
                    stringbuffer1.append(" " + s22.toLowerCase() + ".set" + initialCapital(resultsetmetadata.getColumnLabel(k12).toLowerCase()) + "(rs.get" + initialCapital(s8) + "(\"" + resultsetmetadata.getColumnLabel(k12).toLowerCase() + "\"));\n");
                }

                stringbuffer1.append("            " + s22.toLowerCase() + "V.addElement(" + s22.toLowerCase() + ");\n");
                stringbuffer1.append("        } // end While\n");
                stringbuffer1.append("    } finally {\n");
                stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
                stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
                stringbuffer1.append("    } // try-finally\n");
                stringbuffer1.append("    return " + s22.toLowerCase() + "V;\n");
                stringbuffer1.append("} // end selectBetween\n");
                stringbuffer1.append("\n");
            }
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Select Data Over the key value(s) and default return count(20) \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(as2[1] + " " + as[1]);
            for(int l5 = 2; l5 <= i; l5++)
                stringbuffer1.append(", " + as2[l5] + " " + as[l5]);

            stringbuffer1.append("\n");
            stringbuffer1.append("* @return java.util.Vector \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public java.util.Vector selectOver(");
            stringbuffer1.append(as2[1] + " ");
            stringbuffer1.append(as[1]);
            for(int j6 = 2; j6 <= i; j6++)
                stringbuffer1.append(", " + as2[j6] + " " + as[j6]);

            stringbuffer1.append(") throws Exception{\n");
            stringbuffer1.append("return selectOver(");
            stringbuffer1.append(as[1]);
            for(int l6 = 2; l6 <= i; l6++)
                stringbuffer1.append(", " + as[l6]);

            stringbuffer1.append(",20) ;\n");
            stringbuffer1.append("}// end selectOver\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Select Data Over(Next) the key value(s) and return record count \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(as2[1] + " " + as[1]);
            for(int j7 = 2; j7 <= i; j7++)
                stringbuffer1.append(", " + as2[j7] + " " + as[j7]);

            stringbuffer1.append(", int \n");
            stringbuffer1.append("* @return java.util.Vector \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public java.util.Vector selectOver(");
            stringbuffer1.append(as2[1] + " ");
            stringbuffer1.append(as[1]);
            for(int l7 = 2; l7 <= i; l7++)
                stringbuffer1.append(", " + as2[l7] + " " + as[l7]);

            stringbuffer1.append(", int page) throws Exception{\n");
            stringbuffer1.append("    java.util.Vector " + s22.toLowerCase() + "V = new java.util.Vector();\n");
            stringbuffer1.append("    " + s22 + "Rec " + s22.toLowerCase() + " = null;\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"Select ");
            for(int j8 = 1; j8 <= k; j8++)
            {
                if(j8 != 1 && j8 % 9 == 1)
                    stringbuffer1.append("\" +\n                              \"");
                stringbuffer1.append(resultsetmetadata.getColumnLabel(j8).toLowerCase());
                if(j8 < k)
                    stringbuffer1.append(", ");
            }

            stringbuffer1.append(" \" +\n                       \"  from " + s22 + " ");
            stringbuffer1.append(" \" +\n                       \"  where ");
            for(int l8 = 1; l8 < i; l8++)
                stringbuffer1.append(as[l8] + " = ?  and  ");

            stringbuffer1.append(as[i] + " >= ? order by " + as[i] + " \"; \n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            for(int j9 = 1; j9 <= i; j9++)
                stringbuffer1.append("        pstmt.set" + initialCapital(as2[j9]) + "(" + j9 + "," + as[j9] + "); \n");

            stringbuffer1.append("        rs = pstmt.executeQuery();\n");
            stringbuffer1.append("        int count = 0;//\uC2E4\uC81C\uB77C\uBA74 SQL\uBB38\uC7A5\uC5D0\uC11C Limit\uD574\uC57C \uB418\uC9C0\uB9CC...\n");
            stringbuffer1.append("        while(rs.next()){\n");
            stringbuffer1.append("            count ++;\n");
            stringbuffer1.append("            if(count > page ) break;\n");
            stringbuffer1.append("            " + s22.toLowerCase() + " = new " + s22 + "Rec(); // " + s22 + "Rec Constructor\n");
            for(int l9 = 1; l9 <= k; l9++)
            {
                String s9 = dataType(resultsetmetadata.getColumnTypeName(l9), resultsetmetadata.getPrecision(l9), resultsetmetadata.getScale(l9));
                stringbuffer1.append("                    ");
                stringbuffer1.append(" " + s22.toLowerCase() + ".set" + initialCapital(resultsetmetadata.getColumnLabel(l9).toLowerCase()) + "(rs.get" + initialCapital(s9) + "(\"" + resultsetmetadata.getColumnLabel(l9).toLowerCase() + "\"));\n");
            }

            stringbuffer1.append("            " + s22.toLowerCase() + "V.addElement(" + s22.toLowerCase() + ");\n");
            stringbuffer1.append("        } // end While\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("    return " + s22.toLowerCase() + "V;\n");
            stringbuffer1.append("} // end selectOver\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Select Data Under(Previous) the key value(s) and default return count(20) \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(as2[1] + " " + as[1]);
            for(int j10 = 2; j10 <= i; j10++)
                stringbuffer1.append(", " + as2[j10] + " " + as[j10]);

            stringbuffer1.append("\n");
            stringbuffer1.append("* @return java.util.Vector \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public java.util.Vector selectUnder(");
            stringbuffer1.append(as2[1] + " ");
            stringbuffer1.append(as[1]);
            for(int l10 = 2; l10 <= i; l10++)
                stringbuffer1.append(", " + as2[l10] + " " + as[l10]);

            stringbuffer1.append(") throws Exception{\n");
            stringbuffer1.append("return selectUnder(");
            stringbuffer1.append(as[1]);
            for(int j11 = 2; j11 <= i; j11++)
                stringbuffer1.append(", " + as[j11]);

            stringbuffer1.append(",20) ;\n");
            stringbuffer1.append("}// end selectUnder\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Select Data Under(Previous) the key value(s) and return record count \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(as2[1] + " " + as[1]);
            for(int l11 = 2; l11 <= i; l11++)
                stringbuffer1.append(", " + as2[l11] + " " + as[l11]);

            stringbuffer1.append(", int\n");
            stringbuffer1.append("* @return java.util.Vector\n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public java.util.Vector selectUnder(");
            stringbuffer1.append(as2[1] + " ");
            stringbuffer1.append(as[1]);
            for(int j12 = 2; j12 <= i; j12++)
                stringbuffer1.append(", " + as2[j12] + " " + as[j12]);

            stringbuffer1.append(", int page) throws Exception{\n");
            stringbuffer1.append("    java.util.Vector " + s22.toLowerCase() + "V = new java.util.Vector();\n");
            stringbuffer1.append("    " + s22 + "Rec " + s22.toLowerCase() + " = null;\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"Select ");
            for(int l12 = 1; l12 <= k; l12++)
            {
                if(l12 != 1 && l12 % 9 == 1)
                    stringbuffer1.append("\" +\n                              \"");
                stringbuffer1.append(resultsetmetadata.getColumnLabel(l12).toLowerCase());
                if(l12 < k)
                    stringbuffer1.append(", ");
            }

            stringbuffer1.append(" \" +\n                       \"  from " + s22 + " ");
            stringbuffer1.append(" \" +\n                       \"  where ");
            for(int i13 = 1; i13 < i; i13++)
                stringbuffer1.append(as[i13] + " = ?  and ");

            stringbuffer1.append(as[i] + " <= ? order by " + as[i] + " desc\" ; \n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            for(int j13 = 1; j13 <= i; j13++)
                stringbuffer1.append("        pstmt.set" + initialCapital(as2[j13]) + "(" + j13 + "," + as[j13] + "); \n");

            stringbuffer1.append("        rs = pstmt.executeQuery();\n");
            stringbuffer1.append("        int count = 0;//\uC2E4\uC81C\uB77C\uBA74 SQL\uBB38\uC7A5\uC5D0\uC11C Limit\uD574\uC57C \uB418\uC9C0\uB9CC...\n");
            stringbuffer1.append("        while(rs.next()){\n");
            stringbuffer1.append("            count ++;\n");
            stringbuffer1.append("            if(count > page ) break;\n");
            stringbuffer1.append("            " + s22.toLowerCase() + " = new " + s22 + "Rec(); // " + s22 + "Rec Constructor\n");
            for(int k13 = 1; k13 <= k; k13++)
            {
                String s10 = dataType(resultsetmetadata.getColumnTypeName(k13), resultsetmetadata.getPrecision(k13), resultsetmetadata.getScale(k13));
                stringbuffer1.append("                    ");
                stringbuffer1.append(" " + s22.toLowerCase() + ".set" + initialCapital(resultsetmetadata.getColumnLabel(k13).toLowerCase()) + "(rs.get" + initialCapital(s10) + "(\"" + resultsetmetadata.getColumnLabel(k13).toLowerCase() + "\"));\n");
            }

            stringbuffer1.append("            " + s22.toLowerCase() + "V.add(0," + s22.toLowerCase() + ");\n");
            stringbuffer1.append("        } // end While\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{rs.close();}catch(Exception e){}\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("    return " + s22.toLowerCase() + "V;\n");
            stringbuffer1.append("} // end selectUnder\n");
            stringbuffer1.append("\n// Insert Data \n");
            stringbuffer1.append("/**\n");
            
            
            
            
            
            
            
            
            
            
            
            
            
            stringbuffer1.append("* Add Record \n");
            stringbuffer1.append("* @param " + s22 + "Rec \n");
            stringbuffer1.append("* @return void \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public void insert(" + s22 + "Rec " + s22.toLowerCase() + ") throws Exception{\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"Insert into " + s22 + "( ");
            for(int l13 = 1; l13 <= k; l13++)
            {
                if(l13 % 9 == 1)
                    stringbuffer1.append("\" +\n                              \"");
                stringbuffer1.append(resultsetmetadata.getColumnLabel(l13).toLowerCase());
                if(l13 < k)
                    stringbuffer1.append(", ");
            }

            stringbuffer1.append("\"+\n                       ");
            stringbuffer1.append("\" ) values ( \"+\n");
            stringbuffer1.append("                              \"");
            for(int i14 = 1; i14 <= k; i14++)
            {
                stringbuffer1.append("?");
                if(i14 < k)
                    stringbuffer1.append(", ");
                if(i14 % 9 == 1 && i14 != 1)
                {
                    stringbuffer1.append("\"+\n");
                    stringbuffer1.append("                              \"");
                }
            }

            stringbuffer1.append(")\";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            for(int j14 = 1; j14 <= k; j14++)
            {
                String s11 = dataType(resultsetmetadata.getColumnTypeName(j14), resultsetmetadata.getPrecision(j14), resultsetmetadata.getScale(j14));
                stringbuffer1.append("        pstmt.set" + initialCapital(s11) + "(" + j14 + ", " + s22.toLowerCase() + ".get" + initialCapital(resultsetmetadata.getColumnLabel(j14)) + "());\n");
            }

            stringbuffer1.append("        int affected = pstmt.executeUpdate();\n");
            stringbuffer1.append("        if ( affected == 0 ) throw new DataAlreadyExistException();\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("} // end insert\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("\n// Update Data \n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Update Record \n");
            stringbuffer1.append("* @param " + s22 + "Rec \n");
            stringbuffer1.append("* @return void \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public void update(" + s22 + "Rec " + s22.toLowerCase() + ") throws Exception{\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"Update " + s22 + " SET \"+\n                        \"");
            for(int k14 = 1; k14 <= k; k14++)
            {
                stringbuffer1.append(resultsetmetadata.getColumnLabel(k14).toLowerCase() + " = ?");
                if(k14 < k)
                    stringbuffer1.append(", ");
                if(k14 % 9 == 1 && k14 != 1)
                    stringbuffer1.append("\" +\n                              \"");
            }

            stringbuffer1.append("\"+\n                        ");
            stringbuffer1.append("\" where ");
            stringbuffer1.append(as[1] + " = ? ");
            for(int l14 = 2; l14 <= i; l14++)
                stringbuffer1.append("and " + as[l14] + " = ? ");

            stringbuffer1.append("\";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            for(int i15 = 1; i15 <= k; i15++)
            {
                String s12 = dataType(resultsetmetadata.getColumnTypeName(i15), resultsetmetadata.getPrecision(i15), resultsetmetadata.getScale(i15));
                stringbuffer1.append("        pstmt.set" + initialCapital(s12) + "(" + i15 + ", " + s22.toLowerCase() + ".get" + initialCapital(resultsetmetadata.getColumnLabel(i15)) + "());\n");
            }

            stringbuffer1.append("        // Key\n");
            for(int j15 = 1; j15 <= i; j15++)
                stringbuffer1.append("        pstmt.set" + initialCapital(as2[j15]) + "(" + (j15 + k) + ", " + s22.toLowerCase() + ".get" + initialCapital(as[j15]) + "()); \n");

            stringbuffer1.append("        int affected = pstmt.executeUpdate();\n");
            stringbuffer1.append("        if ( affected == 0 ) throw new NoAffectedException();\n");
            stringbuffer1.append("        else if ( affected > 1 ) throw new TooManyAffectedException();\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("} // end Update\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Delete Record \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(as2[1] + " " + as[1]);
            for(int k15 = 2; k15 <= i; k15++)
                stringbuffer1.append(", " + as2[k15] + " " + as[k15]);

            stringbuffer1.append("\n");
            stringbuffer1.append("* @return void \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public void delete(");
            stringbuffer1.append(as2[1] + " ");
            stringbuffer1.append(as[1]);
            for(int l15 = 2; l15 <= i; l15++)
                stringbuffer1.append(", " + as2[l15] + " " + as[l15]);

            stringbuffer1.append(") throws Exception{\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"Delete From " + s22 + " \"+\n");
            stringbuffer1.append("                       \"where ");
            stringbuffer1.append(as[1] + " = ? ");
            for(int i16 = 2; i16 <= i; i16++)
                stringbuffer1.append("and " + as[i16] + " = ? ");

            stringbuffer1.append("\";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            for(int j16 = 1; j16 <= i; j16++)
                stringbuffer1.append("        pstmt.set" + initialCapital(as2[j16]) + "(" + j16 + "," + as[j16] + "); \n");

            stringbuffer1.append("        int affected = pstmt.executeUpdate();\n");
            stringbuffer1.append("        if ( affected == 0 ){\n");
            stringbuffer1.append("            throw new NoAffectedException();\n");
            stringbuffer1.append("        } else if ( affected > 1 ) {\n");
            stringbuffer1.append("            throw new TooManyAffectedException();\n");
            stringbuffer1.append("        } // end if affection\n");
            stringbuffer1.append("    } finally {\n");
            stringbuffer1.append("        try{pstmt.close();}catch(Exception e){}\n");
            stringbuffer1.append("    } // try-finally\n");
            stringbuffer1.append("} // end Delete\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("/**\n");
            stringbuffer1.append("* Delete Record \n");
            stringbuffer1.append("* @param " + s22 + "Rec \n");
            stringbuffer1.append("* @return void \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public void delete(" + s22 + "Rec " + s22.toLowerCase() + ") throws Exception{\n");
            stringbuffer1.append("     delete(");
            stringbuffer1.append(as1[1] + "()");
            for(int k16 = 2; k16 <= i; k16++)
                stringbuffer1.append(", " + as1[k16] + "()");

            stringbuffer1.append(");");
            stringbuffer1.append("\n");
            stringbuffer1.append("} // end Delete\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("/**\n");
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            stringbuffer1.append("* Get Rows Count \n");
            stringbuffer1.append("* @param ");
            stringbuffer1.append(as2[1] + " " + as[1]);
            for(int l16 = 2; l16 <= i; l16++)
                stringbuffer1.append(", " + as2[l16] + " " + as[l16]);

            stringbuffer1.append("\n");
            stringbuffer1.append("* @return int \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public int count(");
            stringbuffer1.append(as2[1] + " ");
            stringbuffer1.append(as[1]);
            for(int i17 = 2; i17 <= i; i17++)
                stringbuffer1.append(", " + as2[i17] + " " + as[i17]);

            stringbuffer1.append(") throws Exception{\n");
            stringbuffer1.append("    int count = 0;\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"SELECT COUNT(*) from " + s22 + " \" +\n");
            stringbuffer1.append("                       \" where ");
            stringbuffer1.append(as[1] + " = ? ");
            for(int j17 = 2; j17 <= i; j17++)
                stringbuffer1.append("and " + as[j17] + " = ? ");

            stringbuffer1.append("  \";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
            for(int k17 = 1; k17 <= i; k17++)
                stringbuffer1.append("        pstmt.set" + initialCapital(as2[k17]) + "(" + k17 + "," + as[k17] + "); \n");

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
            stringbuffer1.append("} // end count\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("\n/**\n");
            stringbuffer1.append("* Get All Rows Count \n");
            stringbuffer1.append("* @param void \n");
            stringbuffer1.append("* @return int \n");
            stringbuffer1.append("* @author besTeam \n");
            stringbuffer1.append("* @date " + s25 + "\n");
            stringbuffer1.append("*/\n");
            stringbuffer1.append("public int count() throws Exception{\n");
            stringbuffer1.append("    int count = 0;\n");
            stringbuffer1.append("    PreparedStatement pstmt = null;\n");
            stringbuffer1.append("    ResultSet rs = null;\n");
            stringbuffer1.append("    try{\n");
            stringbuffer1.append("        String query = \"SELECT COUNT(*) from " + s22 + "  \";\n");
            stringbuffer1.append("        pstmt = connection.prepareStatement(query);\n");
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
            stringbuffer1.append("} // end count\n");
            stringbuffer1.append("\n");
            stringbuffer1.append("}// end " + s24 + " class");
            String s17 = stringbuffer1.toString();
            stringbuffer2.append("/* " + s22 + " Table Create script */\n");
            stringbuffer2.append("CREATE TABLE " + s22 + "(\n");
            for(int l17 = 1; l17 <= k; l17++)
            {
                if(resultsetmetadata.getColumnTypeName(l17).equalsIgnoreCase("CHAR"))
                {
                    stringbuffer2.append("    " + resultsetmetadata.getColumnLabel(l17).toUpperCase() + " var" + resultsetmetadata.getColumnTypeName(l17).toLowerCase());
                    stringbuffer2.append("(" + resultsetmetadata.getPrecision(l17) + ")");
                } else
                if(resultsetmetadata.getColumnTypeName(l17).equalsIgnoreCase("VARCHAR"))
                {
                    stringbuffer2.append("    " + resultsetmetadata.getColumnLabel(l17).toUpperCase() + " " + resultsetmetadata.getColumnTypeName(l17).toLowerCase());
                    stringbuffer2.append("(" + resultsetmetadata.getPrecision(l17) + ")");
                } else
                {
                    stringbuffer2.append("    " + resultsetmetadata.getColumnLabel(l17).toUpperCase() + " " + resultsetmetadata.getColumnTypeName(l17).toLowerCase());
                    stringbuffer2.append("(" + resultsetmetadata.getPrecision(l17) + "," + resultsetmetadata.getScale(l17) + ")");
                }
                for(int i18 = 1; i18 <= i; i18++)
                    if(resultsetmetadata.getColumnLabel(l17).equalsIgnoreCase(as[i18]))
                        stringbuffer2.append(" NOT NULL ");

                stringbuffer2.append(", \n");
            }

            stringbuffer2.append("    Primary Key (" + as[1].toUpperCase());
            for(int j18 = 2; j18 <= i; j18++)
                stringbuffer2.append(", " + as[j18].toUpperCase());

            stringbuffer2.append(")\n");
            stringbuffer2.append(");");
            String s19 = stringbuffer2.toString();
            stringbuffer3.append("package com.bes_line.mst;\n\n");
            stringbuffer3.append("// DBWrapper's Wrapper  Class for " + s22 + "\n");
            stringbuffer3.append("/**\n");
            stringbuffer3.append(" *\n");
            stringbuffer3.append(" * @(#) " + s24 + ".java\n");
            stringbuffer3.append(" * Copyright 1999-2001 by Daewoo Information System, Inc.,\n");
            stringbuffer3.append(" * BES(Best Enterprise System) Team,\n");
            stringbuffer3.append(" * 526, 5-Ga, NamDaeMoon-Ro, Jung-Gu, Seoul, 100-095, Korea\n");
            stringbuffer3.append(" * All rights reserved.\n");
            stringbuffer3.append(" *\n");
            stringbuffer3.append(" * NOTICE !  You cannot copy or redistribute this code,\n");
            stringbuffer3.append(" * and you should not remove the information about the\n");
            stringbuffer3.append(" * copyright notice and the author.\n");
            stringbuffer3.append(" *\n");
            stringbuffer3.append(" * @version v0.1\n");
            stringbuffer3.append(" * @date    " + s25 + "\n");
            stringbuffer3.append(" * @author  WonDeok Kim, wdkim(at)disc.co.kr.\n");
            stringbuffer3.append(" * @since   JDK1.2\n");
            stringbuffer3.append(" *\n");
            stringbuffer3.append(" */\n\n");
            stringbuffer3.append("import java.sql.*;\n");
            stringbuffer3.append("import org.jsn.jdf.db.*;\n\n");
            stringbuffer3.append("public class " + s22 + "DBWrap extends " + s22 + "DBWrapBES{\n");
            stringbuffer3.append("public " + s22 + "DBWrap(ConnectionContext ctx){\n");
            stringbuffer3.append("    super(ctx);\n");
            stringbuffer3.append("} // Constructor\n");
            stringbuffer3.append("\n");
            stringbuffer3.append("////////////////// User Define Code Start //////////////////\n\n");
            stringbuffer3.append("////////////////// User Define  Code  End //////////////////\n");
            stringbuffer3.append("}// end " + s24 + "Bes class");
            String s21 = stringbuffer3.toString();
            jtSQL.setText(s19);
            jtRec.setText(s15);
            jtDBWrapBES.setText(s17);
            jtDBWrap.setText(s21);
            File file = null;
            String s26 = "./TABLE/";
            file = new File(s26);
            file.mkdir();
            PrintWriter printwriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(s26 + s23 + ".sql", false)));
            printwriter.print(s19);
            printwriter.close();
            printwriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(s26 + s23 + ".java", false)));
            printwriter.print(s15);
            printwriter.close();
            printwriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(s26 + s24 + "BES.java", false)));
            printwriter.print(s17);
            printwriter.close();
            printwriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(s26 + s24 + ".java", false)));
            printwriter.print(s21);
            printwriter.close();
        }
        catch(Exception exception)
        {
            System.out.println("error :: " + exception.toString());
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

    JFrame mainFrame;
    JPanel mainPanel;
    JPanel dbPanel;
    JPanel tablePanel;
    JPanel resultPanel;
    JLabel jlDriver;
    JLabel jlURL;
    JLabel jlUser;
    JLabel jlPassword;
    JTextField jtDriver;
    JTextField jtURL;
    JTextField jtUser;
    JPasswordField jpPassword;
    JButton jbGetTable;
    JButton jbGetScript;
    JList tableList;
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
    Vector keyVector;
}
