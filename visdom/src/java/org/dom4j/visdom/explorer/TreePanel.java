/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TreePanel.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import org.dom4j.visdom.tree.ActiveTreeModel;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.*;


/**
 * Tree View!
 *
 */
public class TreePanel extends JPanel
{
    public TreePanel()
    {
        setLayout(new BorderLayout());
	    setBorder( new BevelBorder(BevelBorder.LOWERED) );
        setName( "TreePanel" );

        tree = new JTree();
        tree.setName( "Tree" );

        // set custom renderer
/*
        TreeCellRenderer oldRenderer = tree.getCellRenderer();
        if ( oldRenderer != null )
        {
            tree.setCellRenderer( new DefaultTreeCellRenderer( oldRenderer ) );
        }
*/
        JScrollPane scrollpane = new JScrollPane(tree);
        scrollpane.setName( "ScrollPane" );
        add(scrollpane, BorderLayout.CENTER);

        // create listeners
        treeModelListener = createTreeModelListener();
    }

    //-------------------------------------------------------------------------
    // Listener style interface
    //-------------------------------------------------------------------------
    public void                 addPopupMenuFactory(TreePopupMenuFactory factory)
    {
        tree.addMouseListener( createMouseListener( factory ) );
    }

    //-------------------------------------------------------------------------
    // Model interface
    //-------------------------------------------------------------------------

    public TreeModel            getTreeModel()
    {
        return tree.getModel();
    }

    public void                 setTreeModel(TreeModel treeModel)
    {
        getTreeModel().removeTreeModelListener( treeModelListener );

        tree.setModel( treeModel );

        treeModel.addTreeModelListener( treeModelListener );
    }

    public TreeNode             getRootNode()
    {
        return (TreeNode) getTreeModel().getRoot();
    }

    public void                 setRootNode(TreeNode rootNode)
    {
        setTreeModel( new ActiveTreeModel( rootNode ) );
    }


    //-------------------------------------------------------------------------
    // Properties API
    //-------------------------------------------------------------------------
    public JTree                getTree()
    {
        return tree;
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected TreeModelListener createTreeModelListener()
    {
        return new TreeModelListener()
        {
            public void treeNodesChanged(TreeModelEvent e)
            {
            }

            public void treeNodesInserted(TreeModelEvent e)
            {
            }

            public void treeNodesRemoved(TreeModelEvent e)
            {
                System.out.println("Children removed!");

                // select the parent of deleted notes
                TreePath currentSelection = tree.getSelectionPath();
                Object[] curSelObjects = currentSelection.getPath();

                if ( curSelObjects.length > 1 )
                {
                    Object curSelParent = curSelObjects[curSelObjects.length - 2];

                    Object[] parentPath = e.getPath();
                    if ( parentPath.length > 0 )
                    {
                        if ( curSelParent == parentPath[ parentPath.length - 1 ] )
                        {
                            System.out.println("Selecting parent");

                            TreePath newSelection = new TreePath( parentPath );
                            tree.setSelectionPath( newSelection );
                            // #### should we put this back in?
                            tree.scrollPathToVisible( newSelection );
                        }
                    }
                }
            }

            public void treeStructureChanged(TreeModelEvent e)
            {
                System.out.println("Tree structure changed!");
            }
        };
    }

    protected MouseListener     createMouseListener(final TreePopupMenuFactory factory)
    {
        return new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if ( e.isPopupTrigger() || SwingUtilities.isRightMouseButton( e ) )
                {
                    int row = tree.getRowForLocation(e.getX(), e.getY());
                    if ( row >= 0 )
                    {
                        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                        int selRow = tree.getRowForLocation(e.getX(), e.getY());
        	            if(selRow != -1)
                        {
                            onTreeNodePopupMenu( e, selPath, factory );
                            return;
                        }
                    }

                    System.out.println("#### Popup menu trigger missed a node");
                }
            }
        };
    }

    //-------------------------------------------------------------------------
    // Event handlers
    //-------------------------------------------------------------------------

    protected void              onDrillDown(int nodeIndex)
    {
        if ( nodeIndex >= 0 )
        {
            System.out.println( "Handling drill down of node: " + nodeIndex );

            TreePath currentSelection = tree.getSelectionPath();
            Object last = currentSelection.getLastPathComponent();
            if ( last instanceof TreeNode )
            {
                //System.out.println("Selected TreeNode!");
                TreeNode treeNode = (TreeNode)last;

                if ( nodeIndex < treeNode.getChildCount() )
                {
                    TreeNode child = treeNode.getChildAt(nodeIndex);
                    if ( child != null )
                    {
                        //System.out.println("Selected valid child");
                        Object[] curPath = currentSelection.getPath();
                        int numInCurPath = curPath.length;

                        Object[] newPath = new Object[ numInCurPath+1 ];
                        for ( int i = 0; i < numInCurPath; i++ )
                        {
                            newPath[i] = curPath[i];
                        }
                        newPath[numInCurPath] = child;
                        TreePath newSelection = new TreePath(newPath);

                        tree.setSelectionPath( newSelection );
                        // #### should we put this back in?
                        tree.scrollPathToVisible( newSelection );
                    }
                }
                else
                {
                    System.out.println("Invalid drill down index!");
                }
            }
        }
    }

    protected void              onTreeNodePopupMenu( MouseEvent           event,
                                                     TreePath             selectionPath,
                                                     TreePopupMenuFactory factory )
    {
        System.out.println("Creating popup menu");
        JPopupMenu menu = factory.createPopupMenu( selectionPath );
        if ( menu != null )
        {
            menu.setInvoker( getTree() );
            menu.show( getTree(), event.getX(), event.getY() );
            //menu.setLocation( event.getX(), event.getY() );
            //menu.setVisible(true);
        }
        return;
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private JScrollPane         scrollPane;
    private JTree               tree;

    private TreeModelListener   treeModelListener;
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
 * $Id: TreePanel.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */
