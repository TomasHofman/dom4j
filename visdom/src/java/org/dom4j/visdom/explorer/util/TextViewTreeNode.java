/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TextViewTreeNode.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */

package org.dom4j.visdom.explorer.util;

import org.dom4j.visdom.explorer.TreeModelAdapter;
import org.dom4j.visdom.explorer.ViewableNode;
import org.dom4j.visdom.explorer.util.ObjectTreeNode;
import org.dom4j.visdom.explorer.util.DefaultTreeNodeFactory;
import org.dom4j.visdom.explorer.util.TreeNodeFactory;
import org.dom4j.visdom.explorer.util.TreeChildrenSorter;
import org.dom4j.visdom.table.DefaultTablePanel;
import org.dom4j.visdom.table.ReflectionTableModel;
import org.dom4j.visdom.util.holder.FieldObjectHolder;
import org.dom4j.visdom.util.holder.ObjectHolder;

import javax.swing.*;
import javax.swing.tree.MutableTreeNode;

import java.awt.Component;
import java.beans.*;
import java.lang.reflect.*;

public class TextViewTreeNode extends ObjectTreeNode implements ViewableNode
{
    public TextViewTreeNode( String                 name,
                             Object                 object )
    {
        super( name, object, null );
    }

    //-------------------------------------------------------------------------
    // ViewableNode interface
    //-------------------------------------------------------------------------

    public Component  makeComponentView( final TreeModelAdapter treeModelAdapter )
    {
        String text = ( getObject() != null )
            ? getObject().toString() : "*null*";

        JTextArea textArea = new JTextArea( text );
        return new JScrollPane( textArea );
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
 * $Id: TextViewTreeNode.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */
