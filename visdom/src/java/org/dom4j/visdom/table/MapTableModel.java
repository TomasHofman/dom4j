/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: MapTableModel.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */

package org.dom4j.visdom.table;

import java.util.Iterator;
import java.util.Map;
//import COM.odi.util.Iterator;
//import COM.odi.util.Map;

import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;
import javax.swing.table.*;

/** Implements the table model for the Map interface showing key and value columns
  *
  * @author James Strachan
  */


public class MapTableModel extends AbstractTableModel
{
    public MapTableModel()
    {
        keys = new Vector();
        values = new Vector();
    }

    public MapTableModel( Map map )
    {
        this.map = map;

        generateIndex();
    }


    public void
    setMap( Map map )
    {
        this.map = map;

        generateIndex();
    }

    //-------------------------------------------------------------------------
    // TableModel interface
    //-------------------------------------------------------------------------
/*
    public Class                getColumnClass(int columnIndex)
    {
        return String.class;
    }
*/

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
        return values.size();
    }

    public Object               getValueAt(int row, int col)
    {
        switch ( col )
        {
            case 0:
                return keys.elementAt( row );
            case 1:
                return values.elementAt( row );
            default:
                return null;
        }
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected void                  generateIndex()
    {
        keys = new Vector();
        values = new Vector();
        for ( Iterator i = map.entrySet().iterator(); i.hasNext(); )
        {
            Map.Entry entry = (Map.Entry) i.next();
            keys.addElement( entry.getKey() );
            values.addElement( entry.getValue() );
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private Map                 map;
    private Vector              keys;
    private Vector              values;

    protected static String[]   columnNames =
    {
        "Key", "Value"
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
 * $Id: MapTableModel.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */
