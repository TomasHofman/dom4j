/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: SimpleInternalFrameTreeNodeViewer.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import org.dom4j.visdom.explorer.action.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.*;


/**
 * Represents a viewer of the currently selected TreeNode of some JTree.
 * <br>
 * Uses a different internal frame for each tree node selected.
 *
 */
public class SimpleInternalFrameTreeNodeViewer extends SimpleTreeNodeViewer
    implements MultiTreeNodeViewer
{
    public SimpleInternalFrameTreeNodeViewer()
    {
        desktopPane.setOpaque(true);

        add(desktopPane, BorderLayout.CENTER);

        newWindowAction.setEnabled(false);
    }

    //-------------------------------------------------------------------------
    // MultiTreeNodeViewer interface
    //-------------------------------------------------------------------------
    public void
    doNewWindow()
    {
        // unmaximise the first one
        if ( currentFrame != null && makeCount == 1 )
        {
            try
            {
                currentFrame.setMaximum( false );
            }
            catch ( PropertyVetoException e )
            {
            }
        }

        Object treeNode = getCurrentTreeNode();
        JInternalFrame frame = makeViewFrame();

        Component newComponent = createViewComponent();

        selectFrame(frame, newComponent);
    }

    public Action[]
    getActions()
    {
        Action[] myActions =
        {
            newWindowAction
        };
        return myActions;
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------
    public void
    setCurrentTreeNode( Object currentTreeNode )
    {
        super.setCurrentTreeNode(currentTreeNode);
        if ( ! isNewWindowEachTreeNodeMode )
        {
            boolean state = getCurrentTreeNode() != null;
            newWindowAction.setEnabled( state );
        }
    }

    protected void
    setCurrentViewComponent(Component newViewComponent)
    {
        if ( newViewComponent != currentViewComponent && newViewComponent != null )
        {
            currentViewComponent = newViewComponent;

            JInternalFrame frame = getViewFrame();
            selectFrame(frame, currentViewComponent);
        }
    }

    //-------------------------------------------------------------------------
    // Internal frame manipulation methods
    //-------------------------------------------------------------------------
    public String
    getTitle( int index )
    {
        return ((JInternalFrame)frames.elementAt( index )).getTitle();
    }

    public Component
    getViewAt( int index )
    {
        return (Component)views.elementAt( index );
    }

    public int
    getViewsCount()
    {
        return views.size();
    }

    public int
    getSelectedIndex()
    {
        for( int index = 0; index < frames.size(); index++ )
	    {
            if ( ((JInternalFrame)frames.elementAt( index )).isSelected() )
    		{
			    return index;
		    }
	    }
	    return -1;
    }

    public Component[]
    getViews()
    {
        Component[] views = new Component[ getViewsCount() ];
        for ( int index = 0; index < views.length; index++ )
        {
            views[index] = getViewAt( index );
        }
        return views;
    }

    public String[]
    getTitles()
    {
        String[] titles = new String[ getViewsCount() ];
        for ( int index = 0; index < titles.length; index++ )
        {
            titles[index] = getTitle( index );
        }
        return titles;
    }


    public void
    removeViewAt( int index )
    {
        JInternalFrame frame = (JInternalFrame)frames.elementAt( index );
        frame.removeAll();

        Object treeNode = treeNodes.elementAt( index );

        frames.removeElementAt( index );
        views.removeElementAt( index );
        treeNodes.removeElementAt( index );

        cache.remove( treeNode );
    }


    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected JInternalFrame
    getViewFrame()
    {
        Object treeNode = getCurrentTreeNode();
        if ( isNewWindowEachTreeNodeMode || currentFrame == null )
        {
            currentFrame = (JInternalFrame) cache.get( treeNode );
        }
        if ( currentFrame == null )
        {
            currentFrame = makeViewFrame();
            cache.put( treeNode, currentFrame );
        }
        return currentFrame;
    }

    protected JInternalFrame
    makeViewFrame()
    {
        //JInternalFrame frame = new ComponentInternalFrame( currentViewComponent );
        final JInternalFrame frame = new JInternalFrame();

        frame.setClosable( true );
        frame.setIconifiable( true );
        frame.setMaximizable( true );
        frame.setResizable( true );
        frame.setResizable( true );

        frame.setBounds(20*(makeCount%10), 20*(makeCount%10), 225, 150);

        frames.addElement( frame );
        views.addElement( currentViewComponent );
        treeNodes.addElement( getCurrentTreeNode() );

        //desktopPane.add( frame, JLayeredPane.PALETTE_LAYER );
        //desktopPane.add( frame, JLayeredPane.DEFAULT_LAYER );
        desktopPane.add( frame, defaultLayer );

        frame.addInternalFrameListener(
            new InternalFrameAdapter ()
            {
                public void
                internalFrameClosed(InternalFrameEvent event)
                {
                    int index = frames.indexOf( frame );

                    System.out.println( "Removing component: " + index );

                    if ( frame.equals( currentFrame ) )
                        currentFrame = null;
                    removeViewAt( index );
                }
            }
        );

        // maximise the first one
        if ( makeCount++ == 0 )
        {
            try
            {
                frame.setMaximum( true );
            }
            catch ( PropertyVetoException e )
            {
            }
        }

        return frame;
    }

    protected void
    selectFrame( JInternalFrame frame,
                 Component      viewComponent )
    {
        Container contentPane = frame.getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(viewComponent, BorderLayout.CENTER);

        frame.setTitle( getCurrentTreeNode().toString() );
        try
        {
            frame.setIcon( false );
        }
        catch ( PropertyVetoException e )
        {
        }
        frame.moveToFront();
        frame.setVisible( true );
        try
        {
            frame.setSelected( true );
        }
        catch ( PropertyVetoException e )
        {
        }

        frame.validate();
        frame.repaint();

        validate();
        repaint();
    }


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private JLayeredPane        desktopPane = new JDesktopPane();
    private Vector              frames      = new Vector();
    private Vector              views       = new Vector();
    private Vector              treeNodes   = new Vector();
    private Hashtable           cache       = new Hashtable();

    private int                 makeCount;
    private JInternalFrame      currentFrame;

    protected static Integer    defaultLayer = new Integer(5);

    // modes
    private boolean             isNewWindowEachTreeNodeMode = false;

    // actions
    protected NewWindowAction   newWindowAction = new NewWindowAction( this );

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
 * $Id: SimpleInternalFrameTreeNodeViewer.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */
