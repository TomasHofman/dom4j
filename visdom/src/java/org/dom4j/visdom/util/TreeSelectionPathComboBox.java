/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TreeSelectionPathComboBox.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import javax.swing.border.*;
import javax.swing.event.*;

import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.*;
import javax.swing.tree.*;
import javax.swing.tree.*;
import javax.swing.plaf.*;
import javax.swing.event.*;
import javax.swing.border.*;

//
// #### rename some of these horrible named functions
//
//

public class TreeSelectionPathComboBox extends TreeComboBox
{
    public TreeSelectionPathComboBox()
    {
        super( new DefaultTreeModel( new DefaultMutableTreeNode("") ) );

        treeSelectionListener = new TreeSelectionListener ()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                handleTreeSelection( e.getPaths() );
            }
        };

        treeModelListener = new TreeModelListener()
        {
            public void treeNodesChanged(TreeModelEvent e)
            {
            }

            public void treeNodesInserted(TreeModelEvent e)
            {
            }

            public void treeNodesRemoved(TreeModelEvent e)
            {
            }

            public void treeStructureChanged(TreeModelEvent e)
            {
                handleTreeModelChanged();
            }
        };

        comboBoxListener = new ItemListener()
        {
                public void itemStateChanged(ItemEvent e)
                {
                    handleComboBoxSelection( e.getItem() );
                }
        };
        addComboBoxListener();
    }

    /*
     * set the tree selection model that this combo box will
     * simulate (and possibly select nodes in)
     */
    public void setViewedTreeModel( TreeSelectionModel selectionModel,
                                    TreeModel          aTreeModel )
    {
        if ( curTreeModel != null )
        {
            //curTreeModel.removeTreeModelListener( treeModelListener );
        }
        curTreeModel = aTreeModel;
        //curTreeModel.addTreeModelListener( treeModelListener );

        removeTreeSelectionListener();
        curSelectionModel = selectionModel;
        addTreeSelectionListener();

        handleTreeModelChanged();
        //handleTreeSelection( selectionModel.getSelectionPaths() );
    }

    protected void addTreeSelectionListener()
    {
        if ( curSelectionModel != null )
        {
            curSelectionModel.addTreeSelectionListener( treeSelectionListener );
        }
    }

    protected void removeTreeSelectionListener()
    {
        if ( curSelectionModel != null )
        {
            curSelectionModel.removeTreeSelectionListener( treeSelectionListener );
        }
    }

    protected void addComboBoxListener()
    {
        addItemListener( comboBoxListener );
    }

    protected void removeComboBoxListener()
    {
        removeItemListener( comboBoxListener );
    }


    /*
     * handle a selection made in the combo box list
     * (i.e. the tree summary)
     */
    protected void handleComboBoxSelection( Object listEntry )
    {
        // get the real tree node
        if ( listEntry instanceof ListEntry )
        {
            Object proxyNode = ((ListEntry)listEntry).object();
            if ( proxyNode instanceof DefaultMutableTreeNode )
            {
                Object realNode = ((DefaultMutableTreeNode)proxyNode).getUserObject();

                // OK now we need to get the paths
                System.out.println("ComboBox selected: " + realNode );
                selectObjectInTreeSelectionModel( realNode );
                return;
            }
        }
        System.out.println("Warning: TreeSelectionPathComboBox.handleComboBoxSelection() not valid selection!");
    }

    /*
     * selects a path in the viewed selection model due to
     * a selection in my combo box list
     */
    protected void selectObjectInTreeSelectionModel( Object observedNode )
    {
        // let's look in the current paths
        if ( curSelectionModelPath != null &&
             curSelectionModelLastObject != observedNode )
        {
            for ( int i = 0; i < curSelectionModelPath.length; i++ )
            {
                if ( curSelectionModelPath[i] == observedNode )
                {
                    int numberInPath = i+1;
                    Object[] newPath = new Object[numberInPath];
                    for ( int j = 0; j < numberInPath; j++ )
                    {
                        newPath[j] = curSelectionModelPath[j];
                    }

                    //removeTreeSelectionListener();
                    //curSelectionModelPath = newPath;
                    //curSelectionModelLastObject = newPath[i];
                    curSelectionModel.setSelectionPath( new TreePath( newPath ) );
                    //addTreeSelectionListener();
                    return;
                }
            }
            System.out.println("Warning: TreeSelectionPathComboBox.selectObjectInTreeSelectionModel() couldn't make TreePath!");
        }
    }

    /*
     * handle the viewed tree model changing
     *
     */
    protected void handleTreeModelChanged()
    {
        // curTreeModel should not be null!
        if ( curTreeModel != null )
        {
            Object newRoot = curTreeModel.getRoot();
            if ( newRoot != null )
            {
                getRoot().setUserObject( newRoot );
            }
        }
    }

    /*
     * handles a tree selection from the tree selection model
     * I'm simulating
     */
    protected void handleTreeSelection( TreePath[] paths )
    {
        removeComboBoxListener();
        if ( paths != null && paths.length > 0 )
        {
            curSelectionModelPath = null;
            curSelectionModelLastObject = null;

            DefaultTreeModel treeModel = (DefaultTreeModel)getTreeModel();
            DefaultMutableTreeNode rootNode = getRoot();
            rootNode.removeAllChildren();

            TreePath first = paths[0];
            Object[] objects = first.getPath();

            if ( objects != null && objects.length > 0 )
            {
                rootNode.setUserObject( objects[0] );

                DefaultMutableTreeNode node = rootNode;
                for ( int i = 1; i < objects.length; i++ )
                {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode( objects[i] );
                    treeModel.insertNodeInto( newNode, node, 0 );
                    //node.insert(newNode, 0);

                    node = newNode;
                }

                int indexOfLastObject = objects.length - 1;

                curSelectionModelPath = objects;
                curSelectionModelLastObject = objects[indexOfLastObject];

                treeModel.nodeChanged(rootNode);
                setSelectedIndex( indexOfLastObject );
            }
        }
        addComboBoxListener();
    }

    protected DefaultMutableTreeNode getRoot()
    {
        DefaultTreeModel treeModel = (DefaultTreeModel)getTreeModel();
        return (DefaultMutableTreeNode) treeModel.getRoot();
    }

    TreeSelectionListener   treeSelectionListener = null;
    TreeModelListener       treeModelListener = null;
    ItemListener            comboBoxListener = null;

    // these are all in the external tree
    TreeModel               curTreeModel = null;
    TreeSelectionModel      curSelectionModel = null;
    Object[]                curSelectionModelPath = null;
    Object                  curSelectionModelLastObject = null;
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
 * $Id: TreeSelectionPathComboBox.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */
