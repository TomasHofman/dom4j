/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: OpNumberAny.java,v 1.3 2001/04/02 18:24:31 jstrachan Exp $
 */


package org.dom4j.xpath.impl;

class OpNumberAny extends Operator {
    
    static Object evaluate(
        Context context, Op op, Object lhsValue, Object rhsValue
    ) {
        Double lhs = convertToNumber(lhsValue);
        Double rhs = convertToNumber(rhsValue);
        
        if ( (op == Op.EQUAL)
            || (op == Op.LT_EQUAL)
            || (op == Op.GT_EQUAL) ) {
            return ( ( lhs.equals(rhs) )
                ? Boolean.TRUE
                : Boolean.FALSE
            );
            
        }
        else if ( op == Op.NOT_EQUAL ) {
            return ( ( ! lhs.equals(rhs) )
                ? Boolean.TRUE
                : Boolean.FALSE
            );
        }
        else if ( (op == Op.LT) || (op == Op.LT_EQUAL) ) {
            return ( ( lhs.compareTo(rhs) < 0 )
                ? Boolean.TRUE
                : Boolean.FALSE
            );
        }
        else if ( (op == Op.GT)
            || (op == Op.GT_EQUAL ) ) {
            return ( ( lhs.compareTo(rhs) > 0 )
                ? Boolean.TRUE
                : Boolean.FALSE
            );
        }
        else {
            System.out.println( "#### I can't handle operator: " + op );
        }
        return null;
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
 * $Id: OpNumberAny.java,v 1.3 2001/04/02 18:24:31 jstrachan Exp $
 */
