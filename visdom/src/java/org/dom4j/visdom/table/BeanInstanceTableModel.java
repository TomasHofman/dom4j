/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: BeanInstanceTableModel.java,v 1.1 2001/05/22 08:12:50 jstrachan Exp $
 */

package org.dom4j.visdom.table;

import org.dom4j.visdom.util.Log;

import org.dom4j.visdom.util.ObjectToString;


import javax.swing.table.*;

import java.beans.*;
import java.lang.reflect.*;

import javax.swing.table.*;
import javax.swing.table.*;
import javax.swing.table.*;


/** Implements the TableModel for a single Java Bean instance
  *
  * @author James Strachan
  */


public class BeanInstanceTableModel extends AbstractTableModel
{
    public BeanInstanceTableModel( Object               object,
                                   PropertyDescriptor[] propertyDescriptors )
    {
        this.object = object;
        this.propertyDescriptors = propertyDescriptors;

        initialiseFromDescriptors();
    }

    public BeanInstanceTableModel( Object object )
    {
        this.object = object;
        initialise();
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------
    public Object               getObject()
    {
        return object;
    }

    public void                 setObject(Object object)
    {
        this.object = object;
        initialise();

        fireTableDataChanged();
    }

    //-------------------------------------------------------------------------
    // TableModel interface
    //-------------------------------------------------------------------------
    public Class                getColumnClass(int columnIndex)
    {
        return String.class;
    }

    public int                  getColumnCount()
    {
        return 3;
    }

    public String               getColumnName(int columnIndex)
    {
        return columnNames[columnIndex];
    }

    public int                  getRowCount()
    {
        return propertyDescriptors.length;
    }

    public Object               getValueAt(int row, int col)
    {
        switch ( col )
        {
            case 0:
            {
                return propertyDescriptors[row].getName();
            }
            case 1:
            {
                Object object = getObjectAt( row );
                return ObjectToString.objectToString( object );
            }
            case 2:
            {
                Object object = getObjectAt( row );
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
        if ( object == null )
        {
            propertyDescriptors = new PropertyDescriptor[0];
            methods = new Method[0];
        }
        else
        {
            try
            {
                Class beanClass = object.getClass();
                BeanInfo beanInfo = Introspector.getBeanInfo( beanClass, null );
                propertyDescriptors = beanInfo.getPropertyDescriptors();

                initialiseFromDescriptors();
            }
            catch ( java.beans.IntrospectionException e)
            {
                Log.exception(this, e);
            }
        }
    }

    protected void              initialiseFromDescriptors()
    {
        if ( propertyDescriptors != null )
        {
            methods = new Method[propertyDescriptors.length];
            for ( int i = 0; i < propertyDescriptors.length; i++ )
            {
                methods[i] = propertyDescriptors[i].getReadMethod();
            }
        }
    }

    protected Object            getObjectAt( int row )
    {
        try
        {
            return methods[row].invoke( object, null );
        }
        catch ( InvocationTargetException e )
        {
            //Log.exception(e);
        }
        catch ( IllegalAccessException e )
        {
            //Log.exception(e);
        }
        catch ( NullPointerException e )
        {
        }
        return null;
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private Object                  object;
    private PropertyDescriptor[]    propertyDescriptors;
    private Method[]                methods;

    protected static String[]       columnNames =
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
 * $Id: BeanInstanceTableModel.java,v 1.1 2001/05/22 08:12:50 jstrachan Exp $
 */
