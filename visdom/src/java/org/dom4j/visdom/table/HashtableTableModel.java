/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: HashtableTableModel.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */

package org.dom4j.visdom.table;

import org.dom4j.visdom.util.ObjectToString;

import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Hashtable;

/** Implementation of the TableModel for the meta data in a MapMessage
  */
public class HashtableTableModel extends AbstractTableModel
{
    public HashtableTableModel()
    {
        initialise();
    }

    public HashtableTableModel(Hashtable hashtable)
    {
        initialise(hashtable);
    }


    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------
    public void                 setHashtable(Hashtable hashtable)
    {
        initialise(hashtable);

        fireTableDataChanged();
    }


    //-------------------------------------------------------------------------
    // TableModel interface
    //-------------------------------------------------------------------------
    public Class                getColumnClass(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return String.class;
            default:
                return Object.class;
        }
    }

    public int                  getColumnCount()
    {
        return columnNames.length;
    }

    public String               getColumnName(int columnIndex)
    {
        return columnNames[columnIndex];
    }

    public int                  getRowCount()
    {
        return keys.length;
    }

    public Object               getValueAt(int row, int col)
    {
        switch ( col )
        {
            case 0:
            {
                return keys[row];
            }
            case 1:
            {
                Object object = values[row];
                return ObjectToString.objectToString( object );
            }
            case 2:
            {
                Object object = values[row];
                if ( object != null )
                {
                    return object.getClass().getName();
                }
                return null;
            }
            default:
            {
                return null;
            }
        }
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected void              initialise()
    {
        keys = new Object[0];
        values = new Object[0];
    }

    protected void              initialise(Hashtable hashtable)
    {
        if ( hashtable == null )
        {
            initialise();
        }
        else
        {
            int number = hashtable.size();

            keys = new Object[number];
            values = new Object[number];

            int index = 0;
            for ( Enumeration e = hashtable.keys(); e.hasMoreElements(); index++ )
            {
                Object key = e.nextElement();
                Object value = hashtable.get( key );

                keys[ index ] = key;
                values[ index ] = value;
            }
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private Object[]            keys;
    private Object[]            values;

    protected static String[]   columnNames =
    {
        "Name", "Value", "Type"
    };

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
 * $Id: HashtableTableModel.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */
