/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: IconManager.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import javax.swing.*;

import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;


public class IconManager
{
    public static ImageIcon getIcon( String file)
    {
        return getIcon( thisClass, file );
    }

    /**
     * Make an Icon
     * The path is below the base class directory
     */
    public static ImageIcon getIcon( Class baseClass, String file)
    {
        String key = file + baseClass.getName();

        if( cache.get( key ) != null )
            return (ImageIcon)cache.get( key );

        ImageIcon icon = getIconWithoutCache( baseClass, file );

        if( icon != null ) cache.put( key, icon );

        return icon;

    }

    public static ImageIcon getIconWithoutCache( Class baseClass, String file)
    {
		byte[] buffer = null;
        try
        {
			InputStream resource = baseClass.getResourceAsStream(file);
            if (resource == null)
            {
                        System.err.println(baseClass.getName() + "/" +
                                           file + " not found.");
                        return null;
            }
            BufferedInputStream in = new BufferedInputStream(resource);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) > 0)
            {
            	out.write(buffer, 0, n);
            }
            in.close();
            out.flush();

            buffer = out.toByteArray();
            if (buffer.length == 0)
            {
            	System.err.println("warning: " + file +
                                           " is zero-length");
                return null;
            }
        }
        catch (IOException ioe)
        {
        	System.err.println(ioe.toString());
            return null;
        }

        return new ImageIcon(buffer);
    }

    private static Hashtable cache = new Hashtable();
    private static Class thisClass = IconManager.class;
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
 * $Id: IconManager.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */
