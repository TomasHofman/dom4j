/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: LocalNameFunction.java,v 1.4 2001/05/22 12:24:11 jstrachan Exp $
 */


package org.dom4j.xpath.function;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.xpath.impl.Context;

import java.util.List;

/**
   <p><b>4.1</b> <code><i>string</i> local-name(<i>node-set?</i>)</code> 
   
   @author bob mcwhirter (bob @ werken.com)
*/
public class LocalNameFunction implements Function {

    public Object call(Context context, List args) {
        if (args.size() == 0) {
            return evaluate( context );
        }
        return nameOfList( args );
    }

    public static String evaluate(Context context) {
        return nameOfList( context.getNodeSet() );
    }
    
    public static String nameOfList(List list) {
        if ( ! list.isEmpty() ) {            
            Object first = list.get(0);
            if (first instanceof List) {
                return nameOfList( (List) first );
            }
            else if (first instanceof Document) {
                Document document = (Document) first;
                Element element = document.getRootElement();
                if (element != null) {
                    return element.getName();
                }
            }
            else if (first instanceof Element) {
                return ((Element) first).getName();
            }
            else if (first instanceof Attribute) {
                return ((Attribute) first).getName();
            }
        }
        return "";
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
 * $Id: LocalNameFunction.java,v 1.4 2001/05/22 12:24:11 jstrachan Exp $
 */
