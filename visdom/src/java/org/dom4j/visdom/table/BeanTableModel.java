/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: BeanTableModel.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */

package org.dom4j.visdom.table;

import org.dom4j.visdom.util.Log;

import java.util.Iterator;
//import COM.odi.util.Iterator;

import javax.swing.table.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.table.*;

/**
 * @author Robert Davies and James Strachan
 */


public class BeanTableModel extends AbstractTableModel
{
    public BeanTableModel( Class beanClass )
    {
        initialise( beanClass );
    }

    public BeanTableModel( Class beanClass, String[] propertyNames )
    {
        if ( propertyNames == null )
        {
            initialise( beanClass );
        }
        else
        {
            initialise( beanClass, propertyNames );
        }
    }

    public BeanTableModel( Class beanClass, Vector propertyNames )
    {
        if ( propertyNames == null )
        {
            initialise( beanClass );
        }
        else
        {
            String[] array = new String[propertyNames.size()];
            propertyNames.copyInto( array );
            initialise( beanClass, array );
        }
    }


    //-------------------------------------------------------------------------
    // Properties API
    //-------------------------------------------------------------------------
    public boolean              getUseDisplayName()
    {
        return useDisplayName;
    }

    public void                 setUseDisplayName(boolean useDisplayName)
    {
        this.useDisplayName = useDisplayName;
    }

    /**
     * refreshes the contents of the table model with an enumeration of objects
     */

    public void                 setEnumeration( Enumeration enumeration )
    {
        int oldNumber = objects.size();

        objects = new Vector();
        while ( enumeration.hasMoreElements() )
        {
            objects.addElement( enumeration.nextElement() );
        }

        fireRowsChanged( oldNumber );
    }

    public void                 setIterator( Iterator iterator )
    {
        int oldNumber = objects.size();

        objects = new Vector();
        while ( iterator.hasNext() )
        {
            objects.addElement( iterator.next() );
        }

        int newNumber = objects.size();

        fireRowsChanged( oldNumber );
    }

    /**
     *  updates Table listeners ...
     */

    public void                 appendObject( Object obj )
    {
        int first = objects.size();

        objects.addElement( obj );

        fireTableRowsInserted( first, first );
    }



    //-------------------------------------------------------------------------
    // TableModel interface
    //-------------------------------------------------------------------------
    public Class                getColumnClass(int columnIndex)
    {
        Class answer = propertyDescriptors[columnIndex].getPropertyType();
        if ( answer == null )
        {
            Log.warning( this, "getColumnClass", "Class is null for columnIndex: " + columnIndex );
            return Object.class;
        }
        else
        if ( answer.isPrimitive() )
        {
            //Log.warning( this, "getColumnClass", "Class is primitive type: " + answer + " for columnIndex: " + columnIndex );
            return Object.class;
        }
        else
        if ( answer.isInterface() )
        {
            //Log.warning( this, "getColumnClass", "Class is interface: " + answer + " for columnIndex: " + columnIndex );
            return Object.class;
        }
        /*
        else
        if ( answer.equals( javax.jms.Destination.class ) )
        {
            Log.info( this, "getColumnClass", "Class is Destination for columnIndex: " + columnIndex );
            return Object.class;
        }
        else
        if ( answer.equals( java.util.Properties.class ) )
        {
            Log.info( this, "getColumnClass", "Class is Properties for columnIndex: " + columnIndex );
            return Object.class;
        }
        else
        {
            Log.info( this, "getColumnClass", "Class is " + answer + " for columnIndex: " + columnIndex );
        }
        */
        return answer;
    }

    public int                  getColumnCount()
    {
        if ( propertyDescriptors == null )
        {
            return 0;
        }
        return propertyDescriptors.length;
    }

    public String               getColumnName(int columnIndex)
    {
        if ( useDisplayName )
        {
            return propertyDescriptors[columnIndex].getDisplayName();
        }
        else
        {
            return propertyDescriptors[columnIndex].getName();
        }
    }

    public int                  getRowCount()
    {
        return objects.size();
    }

    public Object               getValueAt(int row, int col)
    {
        Object valObject = null;
        Object rowObject = objects.elementAt( row );

        if ( rowObject == null )
            return null;

        int index = col;
        //Method getMethod = propertyDescriptors[index].getReadMethod();
        Method getMethod = methods[index];

        try
        {
            valObject = getMethod.invoke ( rowObject, null ) ;
        }
        catch ( java.lang.Exception e)
        {
            System.out.println (e);
            valObject = null;
        }
        return valObject;
    }


    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    private void                initialise( Class beanClass )
    {
        try
        {
            BeanInfo beanInfo = Introspector.getBeanInfo( beanClass );
            propertyDescriptors = beanInfo.getPropertyDescriptors();

            if ( propertyDescriptors != null )
            {
                methods = new Method[propertyDescriptors.length];
                for ( int i = 0; i < propertyDescriptors.length; i++ )
                {
                    methods[i] = propertyDescriptors[i].getReadMethod();
                }
            }
        }
        catch ( java.beans.IntrospectionException ie)
        {
            Log.exception( ie );
        }
    }

    private void                initialise( Class beanClass, String[] propertyNames )
    {
        HashMap temp = new HashMap();
        for ( int i = propertyNames.length - 1; i >= 0; i-- )
        {
            String name = propertyNames[i];
            temp.put( name, new Integer( i ) );
        }

        try
        {
            BeanInfo beanInfo = Introspector.getBeanInfo( beanClass );
            propertyDescriptors = beanInfo.getPropertyDescriptors();

            if ( propertyDescriptors != null )
            {
                HashMap descriptors = new HashMap();
                for ( int i = 0; i < propertyDescriptors.length; i++ )
                {
                    PropertyDescriptor property = propertyDescriptors[i];
                    Object obj = temp.get( property.getName() );
                    if ( obj != null )
                    {
                        descriptors.put( property, obj );
                    }
                }

                int num = descriptors.size();
                methods = new Method[num];
                propertyDescriptors = new PropertyDescriptor[num];
                for ( Iterator iter = descriptors.entrySet().iterator(); iter.hasNext(); )
                {
                    Map.Entry entry = (Map.Entry) iter.next();
                    PropertyDescriptor property = (PropertyDescriptor) entry.getKey();
                    Integer idx = (Integer) entry.getValue();
                    int i = idx.intValue();
                    propertyDescriptors[i] = property;
                    methods[i] = property.getReadMethod();
                }
            }
        }
        catch ( java.beans.IntrospectionException ie)
        {
            Log.exception( ie );
        }
    }

    /** Fires the table changed events after the collection has changed
      * Depending upon the difference between the parameters oldNumber and newNumber
      * there may be a combination update, delete, insert events occurring.
      * (usually update event + either insert or delete)
      *
      * @param oldNumber is the old number of objects in
      *     the collection before the change
      */
    protected void fireRowsChanged( int oldNumber )
    {
        int newNumber = objects.size();

        if ( newNumber >= oldNumber )
        {
            if ( oldNumber > 0 )
            {
                fireTableRowsUpdated( 0, oldNumber-1 );
            }
            if ( newNumber > oldNumber )
            {
                fireTableRowsInserted( oldNumber, newNumber-1 );
            }
        }
        else
        {
            fireTableRowsUpdated( 0, newNumber-1 );
            fireTableRowsDeleted( newNumber, oldNumber-1 );
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private PropertyDescriptor[]    propertyDescriptors;
    private Method[]                methods;
    private boolean                 useDisplayName = false;

    // ### hack to protected for now
    protected Vector                objects = new Vector();

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
 * $Id: BeanTableModel.java,v 1.1 2001/05/22 08:12:53 jstrachan Exp $
 */
