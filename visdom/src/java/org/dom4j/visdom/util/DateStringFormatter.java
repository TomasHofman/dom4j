/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DateStringFormatter.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import java.util.Date;
import java.text.DateFormat;


/**
 * Formats the given Date object as a string using java.text.DateFormat.
 */
public class DateStringFormatter implements StringFormatter
{
    /**
     * Default constructor uses the java.text.DateFormat.MEDIUM style
     * formats the given Date object as a string using java.text.DateFormat.
     */
    public DateStringFormatter()
    {
        this( DateFormat.MEDIUM );
    }

    /**
     * Constructor to use a specific format style from java.text.DateFormat
     *
     * @param formatStyle is the java.text.DateFormat style of the formatting
     *      such as DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL
     *
     */
    public DateStringFormatter(int formatStyle)
    {
        formatter = DateFormat.getDateInstance( formatStyle );
    }


    //-------------------------------------------------------------------------
    // StringFormatter interface
    //-------------------------------------------------------------------------
    public String               valueToString( Object object )
    {
        if ( object instanceof Date )
        {
            // format the date
            Date date = (Date) object;
            return formatter.format( date );

        }
        return object.toString();
    }


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    DateFormat                  formatter;

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
 * $Id: DateStringFormatter.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */
