/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: BeanTable.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */

package org.dom4j.visdom.table;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.*;



public class BeanTable extends JFrame
{
    public BeanTable(String title, BeanTableModel model)
    {
    	super(title);

        setSize(WIDTH, HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setJMenuBar( createMenuBar() );

        try 
        {

          table = new BeanTablePanel(model);
          getContentPane().setLayout(new BorderLayout());
          getContentPane().add (table, BorderLayout.CENTER);
        }
        catch (Exception e)
        {
          System.out.println (e);
        }
        
    }

    public void                 setInvisibleColumnNames( String[] columnNames )
    {
        table.setInvisibleColumnNames( columnNames );
    }


    /**
     * MenuBar
     */
    JMenuBar createMenuBar()
    {
    	// MenuBar
        JMenuBar menuBar = new JMenuBar();

    	JMenuItem mi;

    	// File Menu
    	JMenu file = (JMenu) menuBar.add(new JMenu("File"));
        mi = (JMenuItem) file.add(new JMenuItem("About"));

        ToolTipManager.sharedInstance().setEnabled(true);

    	mi.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if(aboutBox == null)
                    {
                        aboutBox = new Dialog(BeanTable.this,
                        	 "Displays a table of properties for Beans", false);
                        JPanel groupPanel = new JPanel(new BorderLayout());

                        aboutBox.add("Center", groupPanel);

                        JPanel buttonPanel = (JPanel) groupPanel.add("South", new JPanel(true));
                        JButton button = (JButton) buttonPanel.add(new JButton("OK"));

                        button.addActionListener(
                            new ActionListener()
                            {
                                public void actionPerformed(ActionEvent e)
                                {
                                    aboutBox.setVisible(false);
                                }
                            });
                    }
                    aboutBox.pack();
                    aboutBox.show();
                }
            });

        file.add(new JSeparator());
        mi = (JMenuItem) file.add(new JMenuItem("Open"));
    	mi.setEnabled(true);
        mi = (JMenuItem) file.add(new JMenuItem("Save"));
    	mi.setEnabled(true);
        mi = (JMenuItem) file.add(new JMenuItem("Save As..."));
    	mi.setEnabled(true);
        file.add(new JSeparator());
        mi = (JMenuItem) file.add(new JMenuItem("Exit"));
    	mi.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    System.exit(0);
                }
            });

    	// File Menu
    	selectedMenu = (JMenu) menuBar.add( new JMenu("Selected") );
        selectedMenu.setEnabled(false);
        

    	return menuBar;
    }


    public static void main(String s[])
    {
        WindowListener l = new WindowAdapter()
        {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        };

        try
        {
            BeanTableModel model = new BeanTableModel( Class.forName("com.dresdner.tradebus.cream.CreamTrade") );

            BeanTable frame = new BeanTable( "Testing ...", model );
            frame.setForeground(Color.black);
            frame.setBackground(Color.lightGray);
            frame.addWindowListener(l);

            frame.show();
            frame.validate();
        }

        catch (Exception e)
        {
          System.out.println (e);
        }
       /*
          could be useful ...
        // check for config file
        String fileName = System.getProperty( "CONFIG_FILE" );
        if ( fileName != null && fileName.length() > 0 )
        {
            Value config = ValueReader.readFromFile( fileName );
            if ( config != null )
            {
                System.out.println("Info: using config: ");
                config.printOn( System.out );
                
                rootNode = ExplorerModelValueAdapter.readFromValue( config );
                if ( rootNode == null )
                {
                    System.out.println( "Warning: couldn't create explorer from config file: " +
                                        fileName );
                }
            }
        }
        if ( rootNode == null )
        {
            rootNode = new DirectoryRootTreeNode();
        }
        else if ( rootNode.getChildCount() <= 0 )
        {
            System.out.println("Warning: No explorer model defined in the config file");
        }

        */
        


        

    }
    // Attributes

    
    protected BeanTablePanel    table = null;
    protected JMenu             selectedMenu = null;
    protected Dialog            aboutBox = null;
    protected JCheckBoxMenuItem cb = null;

    
    // The width and height of the frame
    public static int           WIDTH = 400;
    public static int           HEIGHT = 300;


}




/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "DOM4J" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of MetaStuff, Ltd.  For written permission,
 *    please contact dom4j-info@metastuff.com.
 *
 * 4. Products derived from this Software may not be called "DOM4J"
 *    nor may "DOM4J" appear in their names without prior written
 *    permission of MetaStuff, Ltd. DOM4J is a registered
 *    trademark of MetaStuff, Ltd.
 *
 * 5. Due credit should be given to the DOM4J Project
 *    (http://dom4j.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * METASTUFF, LTD. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * $Id: BeanTable.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */
