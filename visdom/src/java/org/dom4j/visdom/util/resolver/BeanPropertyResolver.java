/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: BeanPropertyResolver.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */

package org.dom4j.visdom.util.resolver;

import org.dom4j.visdom.util.Log;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;





/**
  * An ObjectResolver for a property of a Bean
  *
  */



public class BeanPropertyResolver implements ObjectResolver
{
    public BeanPropertyResolver( PropertyDescriptor propertyDescriptor )
	{
        this.propertyName = propertyDescriptor.getName();
        this.readMethod = propertyDescriptor.getReadMethod();
        this.writeMethod = propertyDescriptor.getWriteMethod();
        this.propertyType = propertyDescriptor.getPropertyType();
    }


    public BeanPropertyResolver( Class  beanClass,
                                 String propertyName )
    {
        this.propertyName = propertyName;

        try
        {
            //BeanInfo beanInfo = Introspector.getBeanInfo( bean, null );
            BeanInfo beanInfo = Introspector.getBeanInfo( beanClass );
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            if ( propertyDescriptors != null )
            {
                for ( int i = 0; i < propertyDescriptors.length; i++ )
                {
                    PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                    if ( propertyDescriptor != null &&
                         propertyDescriptor.getName().equals( propertyName ) )
                    {
                        readMethod = propertyDescriptor.getReadMethod();
                        writeMethod = propertyDescriptor.getWriteMethod();
                        propertyType = propertyDescriptor.getPropertyType();
                        if ( readMethod != null )
                            break;
                    }
                }
            }
        }
        catch ( IntrospectionException e )
        {
            Log.exception( this, e );
        }
    }


    //-------------------------------------------------------------------------
    // ObjectResolver interface
    //-------------------------------------------------------------------------

    public Object               getObject( Object source )
    {
        try
        {
            return readMethod.invoke( source, NULL_ARGUMENTS );
        }
        catch (  IllegalAccessException e )
        {
            //Log.exception( this, e );
        }
        catch ( IllegalArgumentException e )
        {
            //Log.exception( this, e );
        }
        catch ( InvocationTargetException e )
        {
            //Log.exception( this, e );
        }
        catch ( NullPointerException e )
        {
            //Log.exception( this, e );
        }
        return null;
    }

    /** @param object is the bean to have its value changed
      * @param newValue is the new value of the property
      * @returns true if the set worked, false if the newValue could
      *             not be set for some reason.
      */
    public boolean              setObject(Object object, Object newValue)
    {
        if ( object != null && writeMethod != null )
        {
            Object[] arguments = { newValue };

            try
            {
                writeMethod.invoke( object, arguments );
                return true;
            }
            catch ( InvocationTargetException e )
            {
                Log.exception( this, e );
            }
            catch ( IllegalAccessException e )
            {
                Log.exception( this, e );
            }
            catch ( IllegalArgumentException e )
            {
                Log.exception( this, e );
            }
        }
        return false;
    }


    //-------------------------------------------------------------------------
    // Properties API
    //-------------------------------------------------------------------------
    public String               getPropertyName()
    {
        return propertyName;
    }

    public Class                getPropertyType()
    {
        if ( propertyType != null )
        {
            return propertyType;
        }
        return readMethod.getReturnType();
    }

    //-------------------------------------------------------------------------
    // Utility methods
    //-------------------------------------------------------------------------
    public String               toString()
    {
        return super.toString() + " property [ " + propertyName + " ]";
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected Method            getReadMethod()
    {
        return readMethod;
    }

    protected Method            getWriteMethod()
    {
        return writeMethod;
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private String              propertyName;
    private Method              readMethod;
    private Method              writeMethod;
    private Class               propertyType;

    public static final Object[] NULL_ARGUMENTS = { };
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
 * $Id: BeanPropertyResolver.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */
