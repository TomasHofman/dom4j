/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: StringUtils.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import java.util.StringTokenizer;
import java.util.Vector;


/** Various string utility methods.
  */
public class StringUtils
{
    /** Returns true if the string is made up completely of white space
      * characters (CR, LF, TAB, space etc.).
      *
      * @param text is the text to check for whitespace
      * @returns true if the text is made up completely of white space
      * or false if the string contains at least one non-white space characters
      */
    public static boolean
    isWhiteSpace( String text )
    {
        StringTokenizer tokenizer = new StringTokenizer( text );
        return ! tokenizer.hasMoreTokens();
        /* Does not include carriage returns
        String trimmed = data.trim();
        return trimmed.length() <= 0;
        */
    }

    /** Pretty print an array of objects as [ object1.toString,() object2.toString(), ... ]
      */
    public static String
    arrayToString(Object[] objects)
    {
        int num = objects.length;
        if ( num == 0 )
        {
            return "[]";
        }
        else
        {
            StringBuffer buffer = new StringBuffer( "[ " );
            for ( int i = 0; i < num; i++ )
            {
                if ( i > 0 )
                {
                    buffer.append( ", " );
                }
                Object object = objects[i];
                if ( object != null )
                {
                    buffer.append( object.toString() );
                }
                else
                {
                    buffer.append( "null" );
                }

            }
            buffer.append( " ]" );
            return buffer.toString();
        }
    }

    public static String[]
    tokenize(String source)
    {
        return tokenize( source, null );
    }
    
    /** Tokenises the source string into seperate strings given the separator
      * @param source the source string to tokenize
      * @param separater the separater string. If null the default separater is
      *     used stripping whitespace.
      */
    public static String[]
    tokenize(String source, String separater)
    {
        StringTokenizer t = ( separater != null )
                                ? new StringTokenizer( source, separater )
                                : new StringTokenizer( source );

        Vector v = new Vector();
        while ( t.hasMoreElements() )
        {
            v.addElement( t.nextElement() );
        }
        int num = v.size();
        String[] answer = new String[num];
        v.copyInto( (Object[]) answer );
        return answer;
    }

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
 * $Id: StringUtils.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */
