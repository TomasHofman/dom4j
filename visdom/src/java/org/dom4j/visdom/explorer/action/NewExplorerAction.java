/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: NewExplorerAction.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */

package org.dom4j.visdom.explorer.action;

import org.dom4j.visdom.explorer.AbstractTreeNodeAction;
import org.dom4j.visdom.explorer.Explorer;
import org.dom4j.visdom.explorer.Refreshable;

import javax.swing.AbstractAction;
import javax.swing.tree.TreeNode;

import java.awt.event.ActionEvent;


public class NewExplorerAction extends AbstractTreeNodeAction
{
    public NewExplorerAction()
    {
        super("newExplorer");
    }

    //-------------------------------------------------------------------------
    // Action interface
    //-------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e)
    {
        TreeNode treeNode = getTreeNode();
        if ( treeNode != null )
        {
            Explorer newExplorer = Explorer.createClone( treeNode );
            newExplorer.setVisible(true);
        }
    }

    //-------------------------------------------------------------------------
    // Overriden methods
    //-------------------------------------------------------------------------
    public void
    setTreeNode(TreeNode treeNode)
    {
        super.setTreeNode( treeNode );
        boolean isEnabled = (treeNode != null );
        setEnabled( isEnabled );
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
 * $Id: NewExplorerAction.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */
