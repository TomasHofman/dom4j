/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: Operator.java,v 1.9 2001/07/17 10:31:41 jstrachan Exp $
 */


package org.dom4j.xpath.impl;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

import org.dom4j.xpath.impl.Context;

import java.util.List;

class Operator {
    
    static Object evaluate(
        Context context, Op op, Object lhsValue, Object rhsValue
    ) {
        if ( op == Op.OR ) {
            return null;
        }
        else if ( op == Op.AND ) {
            return null;
        }
        else if ( op == Op.PLUS ) {
            return OpNumberAny.plus( lhsValue, rhsValue );
        }
        else if ( op == Op.MINUS ) {
            return OpNumberAny.minus( lhsValue, rhsValue );
        }
        else if ( op == Op.DIV ) {
            return OpNumberAny.div( lhsValue, rhsValue );
        }
        else if ( op == Op.MULTIPLY ) {
            return OpNumberAny.multiply( lhsValue, rhsValue );
        }
        else if ( op == Op.MOD ) {
            return OpNumberAny.mod( lhsValue, rhsValue );
        }
        else {
            // This cascading-if implments section 3.4 ("Booleans") of
            // the XPath-REC spec, for all operators which do not perform
            // short-circuit evaluation (meaning everything except AND and OR);
            
            if (Operator.bothAreNodeSets(lhsValue, rhsValue)) {
                // return opNodeSetNodeSet();
                return OpNodeSetNodeSet.evaluate(
                    context, op, lhsValue, rhsValue
                );
            }
            else if (Operator.eitherIsNodeSet(lhsValue, rhsValue)) {
                return OpNodeSetAny.evaluate(
                    context, op, lhsValue, rhsValue
                );
            }
            else if (Operator.eitherIsBoolean(lhsValue, rhsValue)) {
                return OpBooleanAny.evaluate(
                    context, op, lhsValue, rhsValue
                );
            }
            else if (Operator.eitherIsNumber(lhsValue, rhsValue)) {
                return OpNumberAny.evaluate(
                    context, op, lhsValue, rhsValue
                );
            }
            else if (Operator.eitherIsString(lhsValue, rhsValue)) {
                return OpStringAny.evaluate(
                    context, op, lhsValue, rhsValue
                );
            }
            else {
/*                
                System.out.println( 
                    "#### I don't know how to handle operator: " 
                    + op + " on lhs: " + lhsValue + " and rhs: " + rhsValue 
                );
*/
            }
        }        
        //return Collections.EMPTY_LIST;
        return null;
    }
    
    protected static boolean eitherIsNodeSet(Object lhs, Object rhs) {
        return ( (lhs instanceof List) || (rhs instanceof List) );
    }
    
    protected static boolean bothAreNodeSets(Object lhs, Object rhs) {
        return ( (lhs instanceof List) && (rhs instanceof List) );
    }
    
    protected static boolean eitherIsNumber(Object lhs, Object rhs) {
        return ( (lhs instanceof Number) || (rhs instanceof Number) );
    }
    
    protected static boolean eitherIsString(Object lhs, Object rhs ) {
        return ( (lhs instanceof String) || (rhs instanceof String) );
    }
    
    protected static boolean eitherIsBoolean(Object lhs, Object rhs) {
        return ( (lhs instanceof Boolean) || (rhs instanceof Boolean) );
    }
    
    protected static String convertToString(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return node.getStringValue();
        }
        else {
            return (obj != null ) ? obj.toString() : "";
        }
    }
    
    protected static Double convertToNumber(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        }
        else {
            String s = convertToString(obj);
            return Double.valueOf( s );
/*            
            if ( s != null && s.length() > 0 ) {
                System.out.println( "Converting: " + s + " to number" );
                return Double.valueOf( s );
            }
            else {
                System.out.println( "#### converting blank string to number!" );
                RuntimeException e = new RuntimeException();
                e.printStackTrace();
            }
            return null;
*/
        }
    }
    
    protected static Boolean convertToBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        else {
            return Boolean.valueOf( convertToString(obj) );
        }        
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
 * $Id: Operator.java,v 1.9 2001/07/17 10:31:41 jstrachan Exp $
 */
