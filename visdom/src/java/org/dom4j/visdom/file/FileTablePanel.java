/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: FileTablePanel.java,v 1.1 2001/05/22 08:12:50 jstrachan Exp $
 */

package org.dom4j.visdom.file;

import org.dom4j.visdom.util.*;
import org.dom4j.visdom.table.DefaultTablePanel;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.table.*;

// DirectoryModel
import org.dom4j.visdom.util.file.DirectoryModel;
import org.dom4j.visdom.util.file.TypedFile;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.*;


public class FileTablePanel extends FileListPanel
{
    public FileTablePanel(DirectoryModel aModel)
    {
        super( aModel );

        setLayout( new BorderLayout() );

        DirectoryModelTableModel tableModel = new DirectoryModelTableModel( aModel );
        DefaultTablePanel panel = new DefaultTablePanel( tableModel );
        table = panel.getTable();

        add( panel, BorderLayout.CENTER );

/*
        makeTableColumns();
*/

        table.addMouseListener(
            new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    if ( e.getClickCount() == 2 )
                    {
                        int row = table.rowAtPoint( e.getPoint() );
                        if ( row >= 0 )
                        {
                            fireFileOpenEvent( row );
                        }
                    }
                }
            } );

/*
        // should do later!
        makeTableData();

        setTableData(table);

        scrollPane = new JScrollPane(table);
        add( scrollPane, BorderLayout.CENTER );
*/
    }

    //
    // required non-abstract methods
    //

    public ListSelectionModel   getSelectionModel()
    {
        return table.getSelectionModel();
    }


    /*
    //
    // implementation details
    //

    protected void              makeTableColumns()
    {
        table.setAutoCreateColumnsFromModel(false);

        // Create the custom renderes etc.
        TableColumn column;

        DefaultTableCellRenderer myRenderer = new DefaultTableCellRenderer();
        //myRenderer.setBackgroundColor(Color.green);
        myRenderer.setToolTipText( FILENAME_TIPTEXT );
        column = addColumn( FILENAME_COLUMN_TITLE,
                            FILENAME_COLUMN_WIDTH,
                            myRenderer, null );

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalTextPosition( DefaultTableCellRenderer.RIGHT );
        renderer.setToolTipText( FILESIZE_TIPTEXT );
        column = addColumn( FILESIZE_COLUMN_TITLE,
                            FILESIZE_COLUMN_WIDTH,
                            renderer, null );
        TableCellRenderer renderer2 = column.getHeaderRenderer();

        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText( FILETYPE_TIPTEXT );
        column = addColumn( FILETYPE_COLUMN_TITLE,
                            FILETYPE_COLUMN_WIDTH,
                            renderer, null );

        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText( FILEMODIFIED_TIPTEXT );
        column = addColumn( FILEMODIFIED_COLUMN_TITLE,
                            FILEMODIFIED_COLUMN_WIDTH,
                            renderer, null );
    }



    protected TableColumn       addColumn( String               title,
                                           int                  width,
                                           TableCellRenderer    cellRenderer,
                                           TableCellEditor      cellEditor)
    {
        // #### should we have our own counter?
        int modelIndex = table.getColumnCount();
        TableColumn column = new TableColumn( modelIndex, width,
                                              cellRenderer, cellEditor );
        column.setHeaderValue( title );
        column.setIdentifier( title );
        table.addColumn( column );
        return column;
    }

    protected void              setTableData(JTable table)
    {
        TableModel model = table.getModel();
        if (model instanceof DefaultTableModel)
        {
            DefaultTableModel defaultModel = (DefaultTableModel)model;
            if ( tableData != null )
            {
                defaultModel.setDataVector( tableData, null );
            }
            else
            {
                // #### this is a hack but I can't remove all rows
                // #### from a table any other way!!

                defaultModel.setDataVector( new Object[1][getNumberOfColumns()], null );
                defaultModel.removeRow(0);
                //table.setModel( new DefaultTableModel(NUMBER_OF_COLUMNS, 0) );
            }
        }
    }

    protected void              makeTableData()
    {
        if ( model != null )
        {
            Vector files = model.getTypedFiles();

            int numberOfRows = files.size();
            if ( numberOfRows > 0 && tableData == null )
            {
                tableData = new Object[numberOfRows][ getNumberOfColumns() ];

                int row = 0;
                for (Enumeration e = files.elements(); e.hasMoreElements(); row++ )
                {
                    File aFile = (File)e.nextElement();

                    boolean isDirectory = aFile.isDirectory();

                    setTableRowData( row,
                                     aFile,
                                     (isDirectory) ? "Folder" : "File",
                                     isDirectory );
                }
            }
        }
    }

    public int                  getNumberOfColumns()
    {
        return 4;
    }

    public void                 handleTableDrillDown( int rowIndex )
    {
        File aFile = getFileAtIndex( rowIndex );
        if ( aFile.isDirectory() )
        {
        }
        else
        {
        }
    }


    protected void              setTableRowData( int     row,
                                                 File    aFile,
                                                 String  fileTypeName,
                                                 boolean isDirectory)
    {
        JLabel label = null;
        Icon icon = null;

        String name = getFileName( aFile );

        if ( aFile instanceof TypedFile )
        {
            //System.out.println("Found type'd icon!");
            TypedFile aTypedFile = (TypedFile)aFile;
            if ( aTypedFile.getIcon() == null )
            {
            //    System.out.println("But it is null!!");
            }
            else
            {
                icon = aTypedFile.getIcon();
            }
            fileTypeName = aTypedFile.getType().getPresentationName();
        }

        if ( icon == null )
        {
            if ( isDirectory )
            {
                icon = getDirectoryIcon( aFile );
            }
            else
            {
                icon = getFileIcon( aFile );
            }
        }

        if ( icon != null )
        {
            //System.out.println("Got icon: " + icon +
            //                   ", " + icon.getIconWidth() + ", " + icon.getIconHeight());

            label = new JLabel( name, icon, SwingConstants.LEFT );
        }
        else
        {
            System.out.println("No icon!!");

            label = new JLabel( name,  SwingConstants.LEFT );
        }
        tableData[row][0] = name;
        //tableData[row][0] = label;
        if ( ! isDirectory )
        {
            long length = aFile.length();
            long sizeInK = length / 1024;
            if ( ( length % 1024) != 0 )
                ++sizeInK;
            tableData[row][1] = new String( sizeInK + " KB  " );
        }
        tableData[row][2] = fileTypeName;
        tableData[row][3] = dateFormat.format( new Date( aFile.lastModified() ) );
    }


    // allows implementations to override this
    public String               getFileName(File aFile)
    {
        return aFile.getName();
    }

    //
    // getters and setters
    //
    public static Icon          getDirectoryIcon( File aFile )
    {
        return IconRepository.getInstance().getTreeFolderIcon();
    }

    public static Icon          getFileIcon( File aFile )
    {
        return IconRepository.getInstance().getTreeLeafIcon();
    }

*/

    //
    // Attributes
    //
    JTable                      table = null;

/*
    JScrollPane                 scrollPane = null;

    Object[][]                  tableData = null;

    static DateFormat           dateFormat = DateFormat.getDateTimeInstance();

    static int                  NUMBER_OF_COLUMNS = 4;

    static String               FILENAME_TIPTEXT = "Name of the file";
    static String               FILENAME_COLUMN_TITLE = "Name";
    static int                  FILENAME_COLUMN_WIDTH = 100;

    static String               FILESIZE_TIPTEXT = "Size of the file in KiloBytes";
    static String               FILESIZE_COLUMN_TITLE = "Size";
    static int                  FILESIZE_COLUMN_WIDTH = 50;

    static String               FILETYPE_TIPTEXT = "Type of the file";
    static String               FILETYPE_COLUMN_TITLE = "Type";
    static int                  FILETYPE_COLUMN_WIDTH = 100;

    static String               FILEMODIFIED_TIPTEXT = "The last time the file was modified";
    static String               FILEMODIFIED_COLUMN_TITLE = "Modified";
    static int                  FILEMODIFIED_COLUMN_WIDTH = 80;
*/
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
 * $Id: FileTablePanel.java,v 1.1 2001/05/22 08:12:50 jstrachan Exp $
 */
