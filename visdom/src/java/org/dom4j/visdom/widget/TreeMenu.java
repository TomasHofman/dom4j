/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TreeMenu.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */

package org.dom4j.visdom.widget;

import org.dom4j.visdom.util.IconRepository;
import org.dom4j.visdom.explorer.*;
import org.dom4j.visdom.tree.TreeNodeHelper;
import org.dom4j.visdom.tree.TreeNodeHolder;
import org.dom4j.visdom.util.Log;

import javax.swing.*;
import javax.swing.tree.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.*;
import javax.swing.*;

public class TreeMenu extends JMenu implements TreeNodeHolder
{
    public TreeMenu(String name)
    {
        super(name);
        this.treeActionListener = createTreeMenuActionListener();
        selectionModel = new DefaultTreeSelectionModel();
        addActionListener( treeActionListener );

        setHorizontalAlignment( LEFT );
        setHorizontalTextPosition( RIGHT );
    }

    public TreeMenu( String name, TreeNode treeNode )
    {
        this(name);
        this.treeNode = treeNode;
    }

    /** Used internally to create my children */
    protected TreeMenu( TreeMenu parent, String name, Icon icon, TreeNode treeNode )
    {
        super(name);
        this.treeNode = treeNode;
        this.parent = parent;
        this.treeActionListener = parent.treeActionListener;
        this.selectionModel = parent.selectionModel;

        addActionListener( treeActionListener );

        setHorizontalAlignment( LEFT );
        setHorizontalTextPosition( RIGHT );
        
        setIcon(icon);
    }

    //-------------------------------------------------------------------------
    // TreeNodeHolder interface
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
        removeAll();
        hasLoaded = false;
    }

    //-------------------------------------------------------------------------
    // Overridden methods
    //-------------------------------------------------------------------------

    public void
    setPopupMenuVisible( boolean visible )
    {
    /*
        if ( visible )
        {
            Log.info( "Showing node: " + treeNode.toString() );
        }
    */
        if ( visible == true && hasLoaded == false )
        {
            loadChildren();
            hasLoaded = true;
        }
        super.setPopupMenuVisible( visible );
    }

    public MenuElement[] getSubElements()
    {
        if ( hasLoaded == false )
        {
            loadChildren();
            hasLoaded = true;
        }
        return super.getSubElements();
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------

    public TreeSelectionModel
    getSelectionModel()
    {
        return selectionModel;
    }

    public void
    setSelectionModel(TreeSelectionModel selectionModel)
    {
        this.selectionModel = selectionModel;
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected void
    loadChildren()
    {
        int num = treeNode.getChildCount();

        //Log.info( "Loading children for node: " + treeNode.toString() + " number: " + num );

        for ( int i = 0; i < num; i++ )
        {
            TreeNode child = treeNode.getChildAt( i );
            String name = child.toString();
            Icon icon = getDefaultIcon( child );

            if ( child.getChildCount() <= 0 )
            {
                TreeMenuItem item = new TreeMenuItem( this, name, icon, child );
                item.addActionListener( treeActionListener );
                add(item);

                //Log.info( "Added Item for: " + treeNode.toString() + " child: " + child.toString() );
            }
            else
            {
                TreeMenu item = new TreeMenu( this, name, icon, child );
                add(item);

                //Log.info( "Added Menu for: " + treeNode.toString() + " child: " + child.toString() );
            }
        }
    }


    protected ActionListener
    createTreeMenuActionListener()
    {
        return new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Object source = e.getSource();
                //Log.info( "Popup selected" );
                if ( source instanceof TreeNodeHolder )
                {
                    //Log.info( "TreeNodeHolder selected!");

                    TreeNode treeNode = ((TreeNodeHolder) source).getTreeNode();
                    //Log.info( "Selected node: " + treeNode.toString() );

                    TreePath treePath = TreeNodeHelper.makeTreePath( treeNode );
                    getSelectionModel().addSelectionPath( treePath );
                }
            }
        };
    }


    protected static Icon
    getDefaultIcon( TreeNode node )
    {
        IconProducer iconProducer = null;
        if ( node instanceof IconProducerHolder )
        {
            iconProducer = ((IconProducerHolder) node).getIconProducer();
        }
        else
        if ( node instanceof IconProducer )
        {
            iconProducer = (IconProducer) node;
        }
        else
        {
            return IconRepository.getInstance().getTreeFolderIcon();
        }
        if ( iconProducer != null )
        {
            return iconProducer.getDefaultIcon();
        }
        return IconRepository.getInstance().getTreeFolderIcon();
    }


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private boolean             hasLoaded;
    private TreeNode            treeNode;
    private TreeMenu            parent;
    private ActionListener      treeActionListener;
    private TreeSelectionModel  selectionModel;
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
 * $Id: TreeMenu.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */
