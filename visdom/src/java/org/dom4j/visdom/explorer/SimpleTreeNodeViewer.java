/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: SimpleTreeNodeViewer.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import org.dom4j.visdom.explorer.util.DefaultTreeNodeViewFactory;
import org.dom4j.visdom.util.Log;

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
 * Represents a viewer of the currently selected TreeNode of some JTree.
 *
 */
public class SimpleTreeNodeViewer extends JPanel
    implements TreeNodeViewer
{
    public SimpleTreeNodeViewer()
    {
        super( true );      // double buffered

        setLayout(new BorderLayout());
	    setBorder( new BevelBorder(BevelBorder.LOWERED) );
    }

    //-------------------------------------------------------------------------
    // TreeNodeViewer interface
    //-------------------------------------------------------------------------
    public void                 setTree(JTree tree)
    {
        if ( this.tree != tree )
        {
            if ( this.tree != null )
            {
                this.tree.getSelectionModel().removeTreeSelectionListener( this );
            }
            this.tree = tree;
            if ( this.tree != null )
            {
                this.tree.getSelectionModel().addTreeSelectionListener( this );
            }
        }
    }

    public JComponent           getViewerComponent()
    {
        return this;
    }

    public Action[]             getActions()
    {
        return new Action[0];
    }

    //-------------------------------------------------------------------------
    // TreeSelectionListener interface
    //-------------------------------------------------------------------------
    public void                 valueChanged(TreeSelectionEvent e)
    {
        setCurrentTreeNode( e.getPath().getLastPathComponent() );
        if ( currentTreeNode != null )
        {
            Log.info( this, "Viewing node: " + currentTreeNode );
            
            Component newViewComponent = createViewComponent();
            setCurrentViewComponent( newViewComponent );
        }
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------

    public Object               getCurrentTreeNode()
    {
        return currentTreeNode;
    }

    public void                 setCurrentTreeNode( Object currentTreeNode )
    {
        this.currentTreeNode = currentTreeNode;
    }

    /** @returns the current view component for the currently selected tree node
      */
    public Component            getCurrentViewComponent()
    {
        return currentViewComponent;
    }

    protected void              setCurrentViewComponent(Component newViewComponent)
    {
        if ( newViewComponent != currentViewComponent )
        {
            if ( currentViewComponent != null )
            {
                remove( currentViewComponent );
            }
            currentViewComponent = newViewComponent;
            if ( currentViewComponent != null )
            {
                add(currentViewComponent, BorderLayout.CENTER);
            }
            validate();
            repaint();
        }
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected TreeModelAdapter  getTreeModelAdapter()
    {
        if ( drillDownAdapter == null )
        {
            drillDownAdapter = new DefaultTreeModelAdapter();
        }
        drillDownAdapter.setTree( tree );
        return drillDownAdapter;
    }

    protected Component         createViewComponent()
    {
        Component newViewComponent = null;

        // see if the treeNode supports a ViewableNode interface
        if ( currentTreeNode instanceof ViewableNode )
        {
            ViewableNode viewNode = (ViewableNode) currentTreeNode;
            newViewComponent = viewNode.makeComponentView( getTreeModelAdapter() );
        }

        if ( newViewComponent == null )
        {
            DefaultTreeNodeViewFactory factory
                = new DefaultTreeNodeViewFactory( (TreeNode) currentTreeNode );

            newViewComponent = factory.makeComponentView( getTreeModelAdapter() );
        }
        return newViewComponent;
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private Object                      currentTreeNode;
    protected Component                 currentViewComponent;
    private DefaultTreeModelAdapter     drillDownAdapter;
    private JTree                       tree;
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
 * $Id: SimpleTreeNodeViewer.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */
