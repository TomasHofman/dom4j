/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultTreeNodeMenuFactory.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import org.dom4j.visdom.explorer.action.*;
import org.dom4j.visdom.widget.TreeMenu;
import org.dom4j.visdom.widget.TreePopupMenu;
import org.dom4j.visdom.util.MenuProxy;
import org.dom4j.visdom.util.JPopupMenuProxy;
import org.dom4j.visdom.util.Log;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.TextAction;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.*;


/**
 * Produces a context-sensitive menu for TreeNodes
 *
 */
public class DefaultTreeNodeMenuFactory extends AbstractTreeModelListener
    implements TreePopupMenuFactory
{
    public DefaultTreeNodeMenuFactory( MenuProxy    contextMenuProxy,
                                       MenuProxy    popupMenuProxy,
                                       JTree        tree )
    {
        super( tree );
        this.contextMenuProxy = contextMenuProxy;
        this.popupMenuProxy = popupMenuProxy;
    }

    //-------------------------------------------------------------------------
    // TreeSelectionListener
    //-------------------------------------------------------------------------
    public void                 valueChanged(TreeSelectionEvent e)
    {
        Object object = e.getPath().getLastPathComponent();
        addActionMenus( contextMenuProxy, object );
        contextMenuProxy.invalidate();
    }

    //-------------------------------------------------------------------------
    // TreePopupMenuFactory interface
    //-------------------------------------------------------------------------
    public JPopupMenu           createPopupMenu(TreePath treePath)
    {
        Object object = treePath.getLastPathComponent();
        if ( object != null )
        {
            addActionMenus( popupMenuProxy, object );
        }
        JPopupMenu menu = (JPopupMenu) popupMenuProxy.getMenu();
        return menu;
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------
    public Action[]             getDefaultActions()
    {
        Action[] actions =
        {
            new NewExplorerAction(),
            //new NavigateAction( getTree() ),
            new RefreshAction(),
            new OpenUIExplorerAction()
        };
        return actions;
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected void              addActionMenus(MenuProxy proxy, Object treeNode)
    {
        proxy.removeAll();

        JMenu navigateMenu = createNavigationMenu( (TreeNode) treeNode );
        proxy.add( navigateMenu );

        // see if the treeNode supports a ActionProducer interface
        Action[] actions = getDefaultActions();
        addActionMenus(proxy, treeNode, actions);

        if ( treeNode != null && treeNode instanceof ActionProducer )
        {
            ActionProducer actionNode = (ActionProducer) treeNode;
	        actions = actionNode.getActions();

            proxy.addSeparator();
            addActionMenus(proxy, treeNode, actions);
        }
    }

    protected void              addActionMenus(MenuProxy proxy, Object treeNode, Action[] actions)
    {
        for ( int i = 0; i < actions.length; i++ )
        {
            Action action = actions[i];
            if ( action instanceof AbstractTreeNodeAction )
            {
                AbstractTreeNodeAction treeAction = (AbstractTreeNodeAction) action;

                treeAction.setTreeModelAdapter( getTreeModelAdapter() );
                treeAction.setTreeNode( (TreeNode) treeNode );
            }
            proxy.add(action);
        }
    }

    protected JMenu
    createNavigationMenu(TreeNode treeNode)
    {
        TreeMenu menu = new TreeMenu( "Navigate", treeNode );

        TreeSelectionListener listener = new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                final TreePath path = e.getPath();
                final JTree tree = getTree();

                Log.info( "Setting selection path to: " + path );

                tree.setSelectionPath( path );
                tree.makeVisible( path );
                tree.scrollPathToVisible( path );
            }
        };
        menu.getSelectionModel().addTreeSelectionListener( listener );
        return menu;
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private MenuProxy           contextMenuProxy;
    private MenuProxy           popupMenuProxy;

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
 * $Id: DefaultTreeNodeMenuFactory.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */
