/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: BeanTablePanel.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */

package org.dom4j.visdom.table;

import org.dom4j.visdom.util.StringFormatter;
import org.dom4j.visdom.util.DateStringFormatter;
import org.dom4j.visdom.util.NumberStringFormatter;
import org.dom4j.visdom.util.DecimalNumberStringFormatter;
import org.dom4j.visdom.util.Log;
import org.dom4j.visdom.util.StringCellRenderer;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.*;


public class BeanTablePanel extends JPanel
{
    public BeanTablePanel( Class beanClass )
    {
        this( new BeanTableModel( beanClass ) );
    }

    public BeanTablePanel(BeanTableModel tableModel)
    {
        this.tableModel = tableModel;

    	setLayout(new BorderLayout());

        // Create the table
    	//table = new JTable(tableModel);

        /*
        columnMappingTableModel = new TableColumnMapper( this.tableModel,
                                                         invisibleColumnNames );

        sorter = new TableSorter( columnMappingTableModel );
        */
        sorter = new TableSorter( this.tableModel );

        /*
        table = new JTable(sorter);

        sorter.addMouseListenerToHeaderInTable( table );

        applyDefaultClassRenderers();
        */

        table = new JTable(this.tableModel);

	    JScrollPane scrollpane = new JScrollPane( table );

        add(scrollpane, BorderLayout.CENTER);

        sorter.sortByColumn(0);
    }


    //-------------------------------------------------------------------------
    // Properties API
    //-------------------------------------------------------------------------

    public JTable               getTable()
    {
        return table;
    }

    public void                 setInvisibleColumnNames( String[] columnNames )
    {
        //columnMappingTableModel.setInvisibleColumnNames( columnNames );
    }


    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected void              applyDefaultClassRenderers()
    {
        // ###################################################
        // should read the following from a properties file
        // ###################################################
        StringFormatter             stringFormatter;
        DefaultTableCellRenderer    renderer;
        TableColumn                 column;

        // common formatting
        stringFormatter = new DateStringFormatter();
        renderer = new StringCellRenderer( stringFormatter );
        centerRenderer(renderer);
        table.setDefaultRenderer( java.util.Date.class, renderer );

        stringFormatter = new DecimalNumberStringFormatter( "#,###,###,##0.000'M'", 1000000 );
        renderer = new StringCellRenderer( stringFormatter );
        rightAlignRenderer(renderer);


        try
        {
            column = table.getColumn("amount");
            column.setCellRenderer( renderer );
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    protected void              centerRenderer(DefaultTableCellRenderer renderer)
    {
        renderer.setHorizontalAlignment( JLabel.CENTER );
        renderer.setHorizontalTextPosition( JLabel.CENTER );
        renderer.setBorder(new EmptyBorder(0, 2, 0, 2));
    }

    protected void              leftAlignRenderer(DefaultTableCellRenderer renderer)
    {
        renderer.setBorder(new EmptyBorder(0, 2, 0, 2));
    }

    protected void              rightAlignRenderer(DefaultTableCellRenderer renderer)
    {
	    renderer.setHorizontalAlignment(JLabel.RIGHT);
	    renderer.setHorizontalTextPosition(JLabel.RIGHT);
        renderer.setBorder(new EmptyBorder(0, 2, 0, 2));
    }


    // Attributes
    //TableColumnMapper           columnMappingTableModel;
    TableSorter                 sorter;
    BeanTableModel              tableModel;
    JTable                      table;

    public static String[]      invisibleColumnNames = { "class" };
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
 * $Id: BeanTablePanel.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */
