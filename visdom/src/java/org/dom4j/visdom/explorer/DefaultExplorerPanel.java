/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultExplorerPanel.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import org.dom4j.visdom.util.DefaultResourcePanel;
import org.dom4j.visdom.util.MenuProxy;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 * Contains the tree panel and a simple tree node viewer together.
 *
 */
public class DefaultExplorerPanel extends DefaultResourcePanel
{
    public DefaultExplorerPanel()
    {
        this( singletonResources );
    }

    public DefaultExplorerPanel(ResourceBundle resources)
    {
    	super(resources);
        singletonResources = resources;

        setName( "ExplorerPanel" );
        setLayout(new BorderLayout());

        treePanel = new TreePanel();
        treeViewer = createTreeNodeViewer();
        treeViewer.setTree( treePanel.getTree() );
        JComponent treeViewComponent = treeViewer.getViewerComponent();

        treePanel.setMinimumSize( MINIMUM_DIMENSION );
        treeViewComponent.setMinimumSize( MINIMUM_DIMENSION );

        splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                                    continuousLayout,
                                    treePanel,
                                    treeViewComponent );
        splitPane.setName( "SplitPanel" );
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);


        // create the menu factory
        DefaultTreeNodeMenuFactory menuFactory
            = new DefaultTreeNodeMenuFactory( createContextMenuProxy(),
                                              createPopupMenuProxy(),
                                              treePanel.getTree() );

        // for pull down menus
        treePanel.getTree().getSelectionModel().addTreeSelectionListener( menuFactory );

        // for popup menus
        treePanel.addPopupMenuFactory( menuFactory );

        // create toolbar
/*        
        treeComboBox = new TreeSelectionPathComboBox();
        treeComboBox.setViewedTreeModel( tree.getSelectionModel(), tree.getModel() );

        toolBar = new JToolBar();
        toolBar.setBorderPainted(true);
        toolBar.add( treeComboBox );

        add(toolBar, BorderLayout.NORTH);
*/

        // attempts to sort out the divider....
        //double size = 0.5;
        //splitPane.setDividerLocation( size );
        //splitPane.setLastDividerLocation( splitPane.getWidth() / 2 );

/*
        SwingUtilities.invokeLater(
            new Runnable()
            {
                public void run()
                {
                    splitPane.setDividerLocation( 0.5 );
//                    splitPane.setDividerLocation( (int) (splitPane.getWidth() / 2 ) );
                }
            }
        );
*/
    }

    //-------------------------------------------------------------------------
    // Model interface
    //-------------------------------------------------------------------------

    public TreeModel
    getTreeModel()
    {
        return treePanel.getTreeModel();
    }

    public void
    setTreeModel(TreeModel treeModel)
    {
        treePanel.setTreeModel(treeModel);
    }

    public TreeNode
    getRootNode()
    {
        return treePanel.getRootNode();
    }

    public void
    setRootNode(TreeNode rootNode)
    {
        treePanel.setRootNode(rootNode);
    }


    //-------------------------------------------------------------------------
    // getters and setters
    //-------------------------------------------------------------------------
    public Action[]
    getActions()
    {
        return treeViewer.getActions();
    }

    //-------------------------------------------------------------------------
    // UI Compoenent interface
    //-------------------------------------------------------------------------
    public TreePanel
    getTreePanel()
    {
        return treePanel;
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected TreeNodeViewer
    createTreeNodeViewer()
    {
        //return new SimpleTreeNodeViewer();
        return new SimpleInternalFrameTreeNodeViewer();
    }

    protected MenuProxy
    createContextMenuProxy()
    {
        return new MenuProxy()
        {
            public Object
            getMenu()
            {
                return selectedMenu;
            }

            public void
            add(Action action)
            {
                if ( selectedMenu != null )
                {
                    JMenuItem menuItem = createActionMenuItem( action );
                    selectedMenu.add(menuItem);
                }
            }

            public void
            add(JMenuItem menuItem)
            {
                if ( selectedMenu != null )
                {
                    selectedMenu.add(menuItem);
                }
            }

            public void
            addSeparator()
            {
                if ( selectedMenu != null )
                {
                    selectedMenu.addSeparator();
                }
            }

            public void
            invalidate()
            {
                if ( selectedMenu != null )
                {
                    selectedMenu.invalidate();
                }
            }

            public void
            removeAll()
            {
                if ( selectedMenu != null )
                {
                    try
                    {
                        selectedMenu.removeAll();
                    }
                    catch (NullPointerException e)
                    {
                        System.out.println( "Swing bug??: " + e );
                        e.printStackTrace();
                    }
                    makeMenu( selectedMenu, SELECTED_MENU_KEY );
                }
            }

        };
    }

    protected MenuProxy
    createPopupMenuProxy()
    {
        return new MenuProxy()
        {
            public Object
            getMenu()
            {
                return menu;
            }

            public void
            add(Action action)
            {
                JMenuItem menuItem = createActionMenuItem( action );
                menu.add(menuItem);
            }

            public void
            add(JMenuItem menuItem)
            {
                menu.add(menuItem);
            }

            public void
            addSeparator()
            {
                menu.addSeparator();
            }

            public void
            invalidate()
            {
                menu.invalidate();
            }

            public void
            removeAll()
            {
                menu.removeAll();
                //makeMenu( menu, "treePopupMenu" );
            }

            // Attributes
            JPopupMenu          menu = new JPopupMenu();
        };
    }

    protected void
    makeMenu(JMenu menu, String key)
    {
        if ( key.equals( SELECTED_MENU_KEY ) )
        {
            selectedMenu = menu;
        }
        super.makeMenu(menu, key);
    }


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private TreePanel               treePanel;
    private TreeNodeViewer          treeViewer;
    private JSplitPane              splitPane;
    private JMenu                   selectedMenu;
    private boolean                 continuousLayout = DEFAULT_CONTINUOUS_LAYOUT;

    protected static Dimension      MINIMUM_DIMENSION = new Dimension( 0, 0 );
    protected static String         SELECTED_MENU_KEY = "selected";
    protected static boolean        DEFAULT_CONTINUOUS_LAYOUT = false;
    protected static ResourceBundle singletonResources;
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
 * $Id: DefaultExplorerPanel.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */
