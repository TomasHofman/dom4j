/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TableColumnMapper.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */

/**
 * Allows columns to be hidden or shown and the order of columns to be changed
 * programmatically
 */

package org.dom4j.visdom.table;

import java.util.*;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


public class TableColumnMapper extends TableMap implements TableModel
{
    public TableColumnMapper()
    {
        indices = new int[0]; // For consistency.
    }

    public TableColumnMapper(TableModel model)
    {
        super.setModel(model);
        reallocateIndexes();
    }

    public TableColumnMapper( TableModel model, String[] invisibleColumnNames )
    {
        super.setModel(model);
        createInvisibleColumnNames( invisibleColumnNames );
        reallocateIndexes();
    }


    public void                 setModel(TableModel model)
    {
        super.setModel(model);
        reallocateIndexes();
        fireTableStructureChanged();
    }

    public void                 setInvisibleColumnNames( String[] names )
    {
        createInvisibleColumnNames( names );
        reallocateIndexes();
        fireTableStructureChanged();
    }



    //-------------------------------------------------------------------------
    // TableModel interface
    //-------------------------------------------------------------------------

    public int getColumnCount()
    {
        return indices.length;
    }

    public String getColumnName(int columnIndex)
    {
        return model.getColumnName( indices[ columnIndex ] );
    }

    public Class getColumnClass(int columnIndex)
    {
        return model.getColumnClass( indices[ columnIndex ] );
    }

    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return model.isCellEditable( rowIndex, indices[ columnIndex ] );
    }

    public Object               getValueAt(int aRow, int aColumn)
    {
        return model.getValueAt( aRow, indices[aColumn] );
    }

    public void                 setValueAt(Object aValue, int aRow, int aColumn)
    {
        model.setValueAt( aValue, aRow, indices[aColumn] );
    }


    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    public void                 tableChanged(TableModelEvent e)
    {
	    /* System.out.println("ColumnMapper: tableChanged"); */
        
        reallocateIndexes();
        super.tableChanged(e);
    }

    public void                 reallocateIndexes()
    {
        // only copy the indices for columns that should be displayed

        int realRowCount = model.getColumnCount();
        Vector v = new Vector();
        for ( int i = 0; i < realRowCount; i++ )
        {
            //Object key = new Integer( i );
            Object key = model.getColumnName( i );

            if ( ! invisibleColumnNames.containsKey( key ) )
            {
                v.addElement( new Integer( i ) );
            }
        }


        // OK now we have the visible vector - let's make the index map
        indices = new int[ v.size() ];
        int pos = 0;
        for ( Enumeration e = v.elements(); e.hasMoreElements(); pos++ )
        {
            indices[pos] = ((Integer)e.nextElement()).intValue();
        }
    }

    private void                createInvisibleColumnNames( String[] names )
    {
        invisibleColumnNames.clear();
        for ( int i = 0; i < names.length; i++ )
        {
            String name = names[i];
            invisibleColumnNames.put( name, name );
        }
    }



    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    int                         indices[];
    Hashtable                   invisibleColumnNames = new Hashtable();

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
 * $Id: TableColumnMapper.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */
