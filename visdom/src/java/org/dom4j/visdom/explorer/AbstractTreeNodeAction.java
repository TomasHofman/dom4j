/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: AbstractTreeNodeAction.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import java.awt.event.ActionEvent;


/**
 * Abstract base class for an action on a TreeNode which contains the
 * TreeModelAdapter allowing the action to mutate the tree model, drill down or whatever.
 *
 */

public abstract class AbstractTreeNodeAction extends AbstractAction
{
    public AbstractTreeNodeAction(String actionName)
    {
        super( actionName );
    }


    //-------------------------------------------------------------------------
    // Abstract methods
    //-------------------------------------------------------------------------
    public abstract void        actionPerformed(ActionEvent e);


    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------
    public TreeNode
    getTreeNode()
    {
        return treeNode;
    }

    public void
    setTreeNode(TreeNode treeNode)
    {
        this.treeNode = treeNode;
    }

    public TreeModelAdapter
    getTreeModelAdapter()
    {
        return treeModelAdapter;
    }

    public void
    setTreeModelAdapter(TreeModelAdapter treeModelAdapter)
    {
        this.treeModelAdapter = treeModelAdapter;
    }


    public DefaultTreeModel

    getTreeModel()

    {

        return getTreeModelAdapter().getTreeModel();

    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private TreeModelAdapter    treeModelAdapter;
    private TreeNode            treeNode;

    public static final String  NEW_ACTION      = "new";
    public static final String  REFRESH_ACTION  = "refresh";
    public static final String  RENAME_ACTION   = "rename";
    public static final String  DELETE_ACTION   = "delete";

    public static final String  CUT_ACTION      = "cut";
    public static final String  COPY_ACTION     = "copy";
    public static final String  PASTE_ACTION    = "paste";
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
 * $Id: AbstractTreeNodeAction.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */
