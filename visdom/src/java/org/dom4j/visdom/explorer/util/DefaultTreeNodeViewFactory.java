/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultTreeNodeViewFactory.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */

package org.dom4j.visdom.explorer.util;

import org.dom4j.visdom.util.IconRepository;
import org.dom4j.visdom.explorer.TreeModelAdapter;
import org.dom4j.visdom.explorer.ViewableNode;
import org.dom4j.visdom.table.DefaultTablePanel;
import org.dom4j.visdom.table.TreeNodeTableModel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import java.util.Enumeration;
import java.util.Vector;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class DefaultTreeNodeViewFactory extends DefaultMutableTreeNode
    implements ViewableNode
{
    public DefaultTreeNodeViewFactory(TreeNode treeNode)
    {
        this.treeNode = treeNode;
    }

    //-------------------------------------------------------------------------
    // ViewableNode interface
    //-------------------------------------------------------------------------
    public Component  makeComponentView( final TreeModelAdapter drillDownAdapter )
    {
        TreeNodeTableModel model = new TreeNodeTableModel( treeNode );
        return new DefaultTablePanel( model );
    }

/*
        final JTable table = createTable();

        table.addMouseListener(
            createMouseListener( drillDownAdapter, table ) );

        makeTableColumns( table );

        makeTableData( table );

        setTableData( table );

        return new JScrollPane(table);
    }

    protected void  makeTableColumns( JTable table )
    {
        table.setAutoCreateColumnsFromModel(false);

        TableColumn column;

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setIcon( IconRepository.getInstance().getTreeFolderIcon() );
        renderer.setToolTipText( NAME_TIPTEXT );
        column = addColumn( table,
                            NAME_COLUMN_TITLE,
                            NAME_COLUMN_WIDTH,
                            renderer, null );
    }

    protected void makeTableData( JTable table )
    {
        tableData = null;

        int numberOfRows = treeNode.getChildCount();
        if ( numberOfRows > 0 )
        {
            tableData = new Object[numberOfRows][getNumberOfColumns()];

            for ( int row = 0; row < numberOfRows; row++ )
            {
                Object o = treeNode.getChildAt( row );
                if ( o instanceof TreeNode )
                {
                    TreeNode aNode = (TreeNode)o;
                    makeTableRowData(tableData[row], aNode);
                }
            }
        }
    }

    protected TableColumn       addColumn( JTable               table,
                                           String               title,
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

    protected void              makeTableRowData(Object[] row, TreeNode aNode)
    {
        row[0] = aNode.toString();
    }

    protected int               getNumberOfColumns()
    {
        return 1;
    }

    protected JTable            createTable()
    {
        JTable table = new JTable();
        //table.setReadOnly( true );
        return table;
    }

    protected MouseListener     createMouseListener( final TreeModelAdapter drillDownAdapter,
                                                     final JTable           table )
    {
        return new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if ( e.getClickCount() == 2 )
                {
                    int rowIndex = table.rowAtPoint( e.getPoint() );
                    if ( rowIndex >= 0 )
                    {
                        TreeNode node = drillDownAdapter.getLastSelectedNode();
                        if ( node != null )
                        {
                            drillDownAdapter.drillDownInNode( rowIndex );
                        }
                    }
                }
            }
        };
    }


    private Object[][]          tableData;

    protected static String     NAME_TIPTEXT        = "Name";
    protected static String     NAME_COLUMN_TITLE   = "Name";
    protected static int        NAME_COLUMN_WIDTH   = 100;
*/


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    private TreeNode            treeNode;
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
 * $Id: DefaultTreeNodeViewFactory.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */
