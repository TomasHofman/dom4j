/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: LoadableTreeNode.java,v 1.1 2001/05/22 08:12:55 jstrachan Exp $
 */

package org.dom4j.visdom.tree;

import javax.swing.tree.*;

/** A TreeNode which performs demand loading of its children. <p>
  *
  * Derived classes should override the protected <CODE>loadChildren()</CODE> method.
  */

public abstract class LoadableTreeNode  extends ActiveTreeNode
{
    public LoadableTreeNode()
    {
    }

    public LoadableTreeNode(Object userObject)
    {
        super( userObject );
    }

    //-------------------------------------------------------------------------
    // Overriden methods
    //-------------------------------------------------------------------------
    public int
    getChildCount()
    {
        checkLoaded();
        return super.getChildCount();
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected abstract void
    loadChildren();

    //-------------------------------------------------------------------------
    // Private methods
    //-------------------------------------------------------------------------
    private final void
    checkLoaded()
    {
        if ( loadedState == STATE_NOT_LOADED )
        {
            loadedState = STATE_LOADING;

            loadChildren();

            loadedState = STATE_LOADED;
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private int                 loadedState = STATE_NOT_LOADED;

    protected static final int  STATE_NOT_LOADED = 0;
    protected static final int  STATE_LOADING = 1;
    protected static final int  STATE_LOADED = 2;

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
 * $Id: LoadableTreeNode.java,v 1.1 2001/05/22 08:12:55 jstrachan Exp $
 */
