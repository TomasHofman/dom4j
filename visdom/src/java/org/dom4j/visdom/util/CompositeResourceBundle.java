/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: CompositeResourceBundle.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import org.dom4j.visdom.util.Log;

import java.io.*;
import java.util.*;


/**
 * A composite resource bundle tree where the child is used
 * first then the parent (i.e. allows override and sharing of bundles)
 */
public class CompositeResourceBundle extends ResourceBundle
{
    /**
     * Constructor takes a child bundle first, which has the lowest priority
     * then the parent which has the highest priority
     */
    public CompositeResourceBundle( ResourceBundle child,
                                    ResourceBundle parent )
    {
        loadBundle( child );
        loadBundle( parent );
    }

    /**
     * Constructor takes an array of filenames for resource bundles
     * in low priority (child) bundles first such that the bundle with the
     * hightest index into the array is the top parent; zero index is the leaf child
     */
    public CompositeResourceBundle( ResourceBundle[] bundles )
    {
        int num = bundles.length;
        for ( int i = 0; i < num; i++ )
        {
            loadBundle( bundles[i] );
        }
    }

    public CompositeResourceBundle( String[] resourceFileNames, Locale locale )
    {
        int num = resourceFileNames.length;
        for ( int i = 0; i < num; i++ )
        {
            ResourceBundle b = ResourceBundle.getBundle( resourceFileNames[i], locale );
            loadBundle( b );
        }
    }

    //-------------------------------------------------------------------------
    // Implementation of abstract methods
    //-------------------------------------------------------------------------

    /**
     * Override of ResourceBundle, same semantics
     */
    protected Object            handleGetObject(String key)
        throws MissingResourceException
    {
        Object obj = lookup.get(key);
        if ( obj == null )
        {
            Log.warning( "Can't find resource from: " + this.getClass().getName() + " key: " + key );
            return "";
/*            
            throw new MissingResourceException( "Can't find resource",
                                                this.getClass().getName(),
                                                key );
*/            
        }
        return obj; // once serialization is in place, you can do non-strings
    }

    /**
     * Implementation of ResourceBundle.getKeys.
     */
    public Enumeration          getKeys()
    {
        return lookup.keys();
/*
	    Enumeration result = null;
    	if (parent != null)
        {
	        Hashtable temp = new Hashtable();

	        for ( Enumeration parentKeys = parent.getKeys();
		          parentKeys.hasMoreElements(); )
            {
        		temp.put(parentKeys.nextElement(), this);
    	    }
    	    for ( Enumeration thisKeys = lookup.keys();
		          thisKeys.hasMoreElements(); )
            {
        		temp.put(thisKeys.nextElement(), this);
    	    }
    	    result = temp.keys();
    	}
        else
        {
	        result = lookup.keys();
    	}
        return result;
*/
    }



    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected void              loadBundle(ResourceBundle bundle)
    {
        for ( Enumeration e = bundle.getKeys(); e.hasMoreElements(); )
        {
            String key = (String)e.nextElement();
            Object value = bundle.getObject( key );
            lookup.put( key, value );
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private Properties          lookup = new Properties();
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
 * $Id: CompositeResourceBundle.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */
