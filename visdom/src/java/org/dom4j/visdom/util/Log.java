/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: Log.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */

package org.dom4j.visdom.util;



public class Log
{
    //-------------------------------------------------------------------------
    // Info logging methods
    //-------------------------------------------------------------------------
    public static void
    info(String text)
    {
        if ( loggingInfo )
        {
            System.out.println( "INFO: " + text );
        }
    }

    public static void
    info(Object instance, String text)
    {
        if ( loggingInfo )
        {
            info( instance.getClass() + ": " + text );
        }
    }

    public static void
    info(Object instance, String text1, String text2)
    {
        if ( loggingInfo )
        {
            info( instance.getClass() + ": " + text1 + text2 );
        }
    }

    //-------------------------------------------------------------------------
    // Warning logging methods
    //-------------------------------------------------------------------------
    public static void
    warning(String text)
    {
        if ( loggingWarning )
        {
            System.out.println( "WARNING: " + text );
        }
    }

    public static void
    warning(Object instance, String text)
    {
        if ( loggingWarning )
        {
            warning( instance.getClass() + ": " + text );
        }
    }

    public static void
    warning(Object instance, String method, String text)
    {
        if ( loggingWarning )
        {
            warning( instance.getClass() + "." + method + ": " + text );
        }
    }

    //-------------------------------------------------------------------------
    // Error logging methods
    //-------------------------------------------------------------------------
    public static void
    error(String text)
    {
        if ( loggingError )
        {
            System.out.println( "ERROR: " + text );
        }
    }

    public static void
    error(String text, Object value)
    {
        if ( loggingError )
        {
            System.out.println( "ERROR: " + text + value );
        }
    }

    //-------------------------------------------------------------------------
    // Throwable logging methods
    //-------------------------------------------------------------------------
    public static void
    exception(Throwable e)
    {
        if ( loggingThrowable )
        {
            System.out.println( "EXCEPTION: " + e );
            e.printStackTrace();
        }
    }

    public static void
    exception(Object instance, Throwable e)
    {
        if ( loggingThrowable )
        {
            System.out.println( "EXCEPTION: " + instance.getClass() + ": " + e );
            e.printStackTrace();
        }
    }


    //-------------------------------------------------------------------------
    // General methods
    //-------------------------------------------------------------------------
    public static void
    setAllLogging(boolean state)
    {
        loggingInfo         = state;
        loggingWarning      = state;
        loggingError        = state;
        loggingThrowable    = state;
    }
    
    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private static boolean      loggingInfo         = true;
    private static boolean      loggingWarning      = true;
    private static boolean      loggingError        = true;
    private static boolean      loggingThrowable    = true;
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
 * $Id: Log.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */
