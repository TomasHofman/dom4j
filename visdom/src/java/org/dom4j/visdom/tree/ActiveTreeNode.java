/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: ActiveTreeNode.java,v 1.1 2001/05/22 08:12:55 jstrachan Exp $
 */

package org.dom4j.visdom.tree;

import javax.swing.tree.*;

/** A MutableTreeNode which fires events on a DefaultTreeModel so that performing
  * regular add and remove of TreeNodes from each other will generate the correct events.
  */

public class ActiveTreeNode extends DefaultMutableTreeNode
{
    public ActiveTreeNode()
    {
    }

    public ActiveTreeNode(Object userObject)
    {
        super( userObject );
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------

    public DefaultTreeModel
    getTreeModel()
    {
        return treeModel;
    }

    public void
    setTreeModel(DefaultTreeModel newTreeModel)
    {
        treeModel = newTreeModel;
    }

    public boolean
    isRaisingEvents()
    {
        return raisingEvents && treeModel != null;
    }

    public void
    setRaisingEvents(boolean newRaisingEvents)
    {
        raisingEvents = newRaisingEvents;
    }


    //-------------------------------------------------------------------------
    // Overriden methods
    //-------------------------------------------------------------------------

    public void
    add(MutableTreeNode newChild)
    {
        super.add( newChild );
        if ( isRaisingEvents() )
        {
            int idx = getChildCount() - 1;

/*
            int expectedIdx = getChildCount() - 1;
            int idx = this.getIndex( newChild );
            if ( idx != expectedIdx )
            {
                System.out.println( "Expected: " + expectedIdx + " but got: " + idx );
            }
*/
            if ( idx >= 0 )
            {
                int[] idxs = { idx };
                fireNodesWereInserted( idxs );
            }
        }
    }

    public void
    insert(MutableTreeNode newChild, int childIndex)
    {
        super.insert( newChild, childIndex );
        if ( isRaisingEvents() )
        {
            int[] idxs = { childIndex };
            fireNodesWereInserted( idxs );
        }
    }

    public void
    remove(int childIndex)
    {
        MutableTreeNode aChild = (MutableTreeNode) this.getChildAt( childIndex );
        if ( aChild != null )
        {
            if ( isRaisingEvents() )
            {
                int idx = this.getIndex( aChild );
                int[] idxs = { childIndex };
                MutableTreeNode[] removed = { aChild };

                super.remove( childIndex );
                fireNodesWereRemoved( idxs, removed );
            }
            else
            {
                super.remove( childIndex );
            }
        }
    }

    public void
    remove(MutableTreeNode aChild)
    {
        if ( isRaisingEvents() )
        {
            int idx = this.getIndex( aChild );
            int[] idxs = { idx };
            MutableTreeNode[] removed = { aChild };

            super.remove( aChild );
            fireNodesWereRemoved( idxs, removed );
        }
        else
        {
            super.remove( aChild );
        }
    }

    public void
    removeAllChildren()
    {
        if ( isRaisingEvents() )
        {
            int size = this.getChildCount();
            int[] idxs = new int[size];
            MutableTreeNode[] removed = new MutableTreeNode[size];

            for ( int i = 0; i < size; i++ )
            {
                idxs[i] = i;
                removed[i] = (MutableTreeNode) getChildAt(i);
            }
            super.removeAllChildren();
            fireNodesWereRemoved( idxs, removed );
        }
        else
        {
            super.removeAllChildren();
        }
    }

    public void
    setParent(MutableTreeNode newParent)
    {
        super.setParent( newParent );
        if ( newParent instanceof ActiveTreeNode )
        {
            this.treeModel = ((ActiveTreeNode) newParent).treeModel;
        }
    }

    //-------------------------------------------------------------------------
    // Event raising methods
    //-------------------------------------------------------------------------
    protected void
    fireNodeChanged()
    {
        if ( isRaisingEvents() )
        {
            getTreeModel().nodeChanged(this);
        }
    }

    protected void
    fireNodesChanged(int[] childIndices)
    {
        if ( isRaisingEvents() )
        {
            getTreeModel().nodesChanged(this, childIndices);
        }
    }


    protected void
    fireNodesWereInserted( int[] childIndices )
    {
        if ( isRaisingEvents() )
        {
            getTreeModel().nodesWereInserted(this, childIndices);
        }
    }

    protected void
    fireNodesWereRemoved( int[] childIndices, Object[]  removedChildren )
    {
        if ( isRaisingEvents() )
        {
            getTreeModel().nodesWereRemoved(this, childIndices, removedChildren);
        }
    }

    protected void
    fireNodeStructureChanged()
    {
        if ( isRaisingEvents() )
        {
            getTreeModel().nodeStructureChanged(this);
        }
    }

    protected void
    fireReload()
    {
        if ( isRaisingEvents() )
        {
            getTreeModel().reload(this);
        }
    }


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private DefaultTreeModel    treeModel;
    private boolean             raisingEvents = true;

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
 * $Id: ActiveTreeNode.java,v 1.1 2001/05/22 08:12:55 jstrachan Exp $
 */
