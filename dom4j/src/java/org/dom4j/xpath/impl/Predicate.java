/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: Predicate.java,v 1.4 2001/07/17 10:31:41 jstrachan Exp $
 */


package org.dom4j.xpath.impl;

import org.dom4j.Node;
import org.dom4j.xpath.ContextSupport;
import org.dom4j.xpath.function.BooleanFunction;
import org.dom4j.xpath.impl.Context;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

public class Predicate implements org.jaxen.expr.Predicate {
    
    private Expr _expr = null;
    
    public String getText() {
        return "[N/I]";
    }

    public org.jaxen.expr.Expr simplify() {
        return this;
    }
    
    public Predicate(Expr expr) {
        _expr = expr;
    }
    
    public org.jaxen.expr.Expr getExpr() {
        return _expr;
    }
    
    public void setExpr(org.jaxen.expr.Expr expr) {
        _expr = (Expr) expr;
    }
        
    
    public List evaluateOn(List nodeSet, ContextSupport support, int axis) {
        Context context = new Context( nodeSet, support );
        int max = context.getSize();
        List results = new ArrayList();        
        
        for ( int position = 1; position <= max; position++ ) {
            Context duplicateContext = context.duplicate();
            duplicateContext.setPosition( position );
            
            if ( evaluateOnNode( duplicateContext, axis ) ) {
                results.add( context.getNode( position ) );
            }
        }        
        return results;
    }
    
    public boolean evaluateOnNode(Context context, int axis) {
        Object exprResult = _expr.evaluate( context );
        
        //System.err.println("pred-expr == " + _expr);
        
        if ( exprResult instanceof Number ) {
            Number n = (Number) exprResult;
            int i = n.intValue();
            return ( i == context.getPosition() );
        } 
        else  {
            return BooleanFunction.evaluate(exprResult).booleanValue();
        }
    }    

    public boolean matches(Context context, Node node) {
        Object exprResult = _expr.evaluate( context );
        if ( exprResult instanceof Number ) {
            Number n = (Number) exprResult;
            int i = n.intValue();
            return ( i == context.getPosition() );
        } 
        else  {
            return BooleanFunction.evaluate(exprResult).booleanValue();
        }
    }    
    
    
    public String toString()
    {
        return "[Prediate: " + _expr + "]";
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
 * $Id: Predicate.java,v 1.4 2001/07/17 10:31:41 jstrachan Exp $
 */
