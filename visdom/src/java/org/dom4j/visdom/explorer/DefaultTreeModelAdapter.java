/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultTreeModelAdapter.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/*
 * Adapter to implement drill down functionality
 *
 */

public class DefaultTreeModelAdapter implements TreeModelAdapter
{
    public DefaultTreeModelAdapter()
    {
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    /*
     * Returns the currently viewed tree node
     * or null if no node is selected or the selected
     * object is not a TreeNode
     */
    public TreeNode             getLastSelectedNode()
    {
        TreePath currentSelection = tree.getSelectionPath();
        Object last = currentSelection.getLastPathComponent();
        if ( last instanceof TreeNode )
        {
            return (TreeNode)last;
        }
        return null;
    }

    /*
     * Drills down from current tree selection into node nodeIndex
     */
    public void                 drillDownInNode(int nodeIndex)
    {
        if ( nodeIndex >= 0 && tree != null )
        {
            System.out.println( "Handling drill down of node: " + nodeIndex );

            TreePath currentSelection = tree.getSelectionPath();
            Object last = currentSelection.getLastPathComponent();
            if ( last instanceof TreeNode )
            {
                TreeNode treeNode = (TreeNode)last;
                if ( nodeIndex < treeNode.getChildCount() )
                {
                    TreeNode child = treeNode.getChildAt(nodeIndex);
                    if ( child != null )
                    {
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

    /*
     * Allows access to the tree model for mutating tree nodes
     * (allows listeners to get events)
     */
    public DefaultTreeModel     getTreeModel()
    {
        if ( tree != null )
        {
            TreeModel model = tree.getModel();
            if ( model instanceof DefaultTreeModel )
            {
                return (DefaultTreeModel) model;
            }
        }
        return null;
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------
    public JTree                getTree()
    {
        return tree;
    }

    public void                 setTree(JTree tree)
    {
        this.tree = tree;
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private JTree               tree;
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
 * $Id: DefaultTreeModelAdapter.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */
