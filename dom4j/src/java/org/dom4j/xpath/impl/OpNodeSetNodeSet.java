/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: OpNodeSetNodeSet.java,v 1.2 2001/03/01 20:48:14 jstrachan Exp $
 */


package org.dom4j.xpath.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

class OpNodeSetNodeSet extends Operator 
{
    static Object evaluate( 
        Context context, Op op, Object lhsValue, Object rhsValue 
    ) {        
        Object result = null;
        // We should *know* absolutely that these are both Lists.
        
        List lhs = (List) lhsValue;
        List rhs = (List) rhsValue;
        
        List lhsStrList = new ArrayList(lhs.size());
        String tmpValue = null;
        
        Iterator itemIter = lhs.iterator();
        
        while ( itemIter.hasNext() ) {
            lhsStrList.add( convertToString( itemIter.next() ) );
        }
        
        Collections.sort(lhsStrList);
        
        itemIter = rhs.iterator();
        
        int index = -1;
        
        while ( itemIter.hasNext() ) {
            tmpValue = convertToString( itemIter.next() );
            
            index = Collections.binarySearch(lhsStrList,
            tmpValue);
            
            if ( op == Op.EQUAL ) {
                // We found one, equality achieved
                if (index >= 0) {
                    return Boolean.TRUE;
                }
            }
            else if ( op == Op.NOT_EQUAL ) {
                // We didn't find one
                if (index < 0) {
                    return Boolean.TRUE;
                }
            }
        }        
        return Boolean.FALSE;
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
 * $Id: OpNodeSetNodeSet.java,v 1.2 2001/03/01 20:48:14 jstrachan Exp $
 */
