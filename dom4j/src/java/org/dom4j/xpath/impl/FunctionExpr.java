/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: FunctionExpr.java,v 1.3 2001/04/20 09:23:14 jstrachan Exp $
 */


package org.dom4j.xpath.impl;

import org.dom4j.xpath.function.Function;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class FunctionExpr extends Expr {
    
    private String _name = null;
    private List   _args = null;
    
    public FunctionExpr(String name, List args) {
        _name = name;
        _args = args;
    }
    
    public Object evaluate(Context context) {
        Function func = context.getContextSupport().getFunction(_name);
        
        if (func == null) {
            // FIXME: toss an exception
            return null;
        }
        
        List resolvedArgs = resolveArgs( context );
        
        return func.call( context, resolvedArgs );
    }
    
    private List resolveArgs(Context context){
        if ( _args == null || _args.size() == 0 )
        {
            return Collections.EMPTY_LIST;
        }
        
        List resolved = new ArrayList(_args.size());

        for ( Iterator iter = _args.iterator(); iter.hasNext(); ) {
            Expr each = (Expr) iter.next();
            Context newContext = context.duplicate();
            resolved.add( each.evaluate( newContext ) );
        }
        return resolved;
    }
    
    public String toString() {
        String argsText = (_args != null) ? _args.toString() : "()";
        return "[FunctionExpr: " + _name + argsText + " ]";
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
 * $Id: FunctionExpr.java,v 1.3 2001/04/20 09:23:14 jstrachan Exp $
 */
