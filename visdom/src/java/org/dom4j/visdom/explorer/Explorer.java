/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: Explorer.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import org.dom4j.visdom.widget.CollapsePanel;
import org.dom4j.visdom.util.CompositeResourceBundle;
import org.dom4j.visdom.util.SystemExitManager;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.tree.TreeNode;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Explorer extends JFrame
{
    /** This constructor is used by the createClone() method
      */
    protected Explorer()
    {
        this( singletonResources );
    }

    public Explorer(ResourceBundle resources)
    {
        this.resources = resources;
        singletonResources = resources;


        setName( "Frame" );

        numberOfInstances++;
        instanceNumber++;

        setTitle(resources.getString("Title"));

        explorerPanel = createExplorerPanel(resources);

        // add closer!
        addWindowListener( createWindowListener() );

        //CollapsePanel toolBarPanel = new CollapsePanel();
        //toolBarPanel.getContentPane().add( createToolBar() );

    	JPanel panel = new JPanel();
    	panel.setLayout(new BorderLayout());
    	panel.add( createToolBar(), BorderLayout.NORTH );
    	panel.add( explorerPanel, BorderLayout.CENTER );

    	getContentPane().setLayout(new BorderLayout());
    	getContentPane().add( createMenuBar(), BorderLayout.NORTH );
    	getContentPane().add( panel, BorderLayout.CENTER );
    	getContentPane().add( createStatusBar(), BorderLayout.SOUTH );

        pack();

         // size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int widthOffset = TILE_WIDTH_OFFSET * (instanceNumber % TILE_MAX_NUMBER);
        int heigthOffset = TILE_HEIGHT_OFFSET * (instanceNumber % TILE_MAX_NUMBER);

        setLocation( widthOffset + screenSize.width/2 - INITIAL_WIDTH/2,
                     heigthOffset + screenSize.height/2 - INITIAL_HEIGHT/2);

        setSize(INITIAL_WIDTH, INITIAL_HEIGHT);



    }

    /**
     * Constructor takes an array of filenames for resource bundles
     * in low priority (child) bundles first such that the bundle with the
     * hightest index into the array is the top parent; zero index is the leaf child
     */
    public Explorer(String[] resourceFileNames,Locale locale)
    {
        this( new CompositeResourceBundle( resourceFileNames, locale ) );
    }


    //-------------------------------------------------------------------------
    // Properties API
    //-------------------------------------------------------------------------

    public TreeNode             getRootNode()
    {
        return explorerPanel.getRootNode();
    }

    public void                 setRootNode(TreeNode rootNode)
    {
        explorerPanel.setRootNode(rootNode);
    }

    public ResourceBundle       getResourceBundle()
    {
        return resources;
    }

    //-------------------------------------------------------------------------
    // Factory methods
    //-------------------------------------------------------------------------

    /**
     * Factory method to create the editor panel to use
     *
     */
/*
    protected ExplorerPanel  createExplorerPanel(ResourceBundle resources)
    {
        return new ExplorerPanel(resources);
    }
*/
    protected DefaultExplorerPanel  createExplorerPanel(ResourceBundle resources)
    {
        return new DefaultExplorerPanel(resources);
    }

    protected JMenuBar          createMenuBar()
    {
        return explorerPanel.createMenuBar();
    }

    protected Component         createToolBar()
    {
        return explorerPanel.createToolBar();
    }

    protected Component         createStatusBar()
    {
        return explorerPanel.createStatusBar();
    }



    //-------------------------------------------------------------------------
    // Main
    //-------------------------------------------------------------------------
    public static void          main(String args[])
    {
        run( args, null, (TreeNodeFactory) null );
    }

    //-------------------------------------------------------------------------
    // Utility methods
    //-------------------------------------------------------------------------
    public static Explorer  createClone( TreeNode treeNode )
    {
        Explorer newExplorer = new Explorer();
        newExplorer.setRootNode( treeNode );
        return newExplorer;
    }


    public static void          run( String                 args[],
                                     String                 resourceFileName,
                                     final TreeNodeFactory  treeNodeFactory )
    {
        ExplorerMutator mutator =
            new ExplorerMutator()
            {
                public void     mutate(Explorer explorer)
                    throws Exception
                {
                    if ( treeNodeFactory != null )
                    {
                        System.out.println( "Creating explorer tree from: " + treeNodeFactory );

                        TreeNode rootTreeNode = treeNodeFactory.createTreeNode();
                        if ( rootTreeNode != null )
                        {
                            explorer.setRootNode( rootTreeNode );
                        }
                        else
                        {
                            System.out.println("WARNING: No explorer tree created");
                        }
                    }
                }
            };

        run( args, resourceFileName, mutator );
    }

    public static void          run( String                 args[],
                                     String                 resourceFileName,
                                     ExplorerMutator    mutator )
    {
        String[] resourceFileNames = defaultResourceFileNames;
        if ( resourceFileName != null )
        {
            int length = defaultResourceFileNames.length;
            resourceFileNames = new String[length + 1];
            System.arraycopy( defaultResourceFileNames, 0,
                              resourceFileNames, 1, length );
            resourceFileNames[0] = resourceFileName;
        }

        try
        {
            try
            {
                Explorer frame = new Explorer( resourceFileNames,
                                                       Locale.getDefault() );

                if ( mutator != null )
                {
                    mutator.mutate( frame );
                }
                frame.setVisible( true );
            }
            catch (MissingResourceException e)
            {
                System.err.println( "Properties file not found in: " +
                                    resourceFileNames + " exception: " + e );
                e.printStackTrace();
                System.exit(0);
            }
        }
        catch (Throwable t)
        {
            System.out.println("Unhandled exception: " + t);
            t.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
/*
    protected static void
    registerConfigurationFinder()
    {
        String configFile = System.getProperty( "org.dom4j.visdom.util.configFile",
                                                "config/com/push/ui/Explorer.xml" );
        IbmDocumentLoader documentLoader = null;
        URL url = ClassLoader.getSystemResource( configFile );
        if ( url != null )
        {
            documentLoader = new IbmDocumentLoader( url );
        }
        else
        {
            documentLoader = new IbmDocumentLoader( configFile );
        }
        XmlConfigurationManager configManager = new XmlConfigurationManager( documentLoader );
        //configManager.registerComponent( frame );

        // configure
        FrameFinder finder = new FrameFinder( configManager );




        //ConfigContainerListener containerListener = new ConfigContainerListener("root");
        //containerListener.setRootContainer( this );
        //addContainerListener( containerListener );
        //getContentPane().addContainerListener( new ConfigContainerListener("contentPane") );


    }
*/

    protected WindowListener    createWindowListener()
    {
        return new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                if ( --numberOfInstances == 0 )
                {
                    SystemExitManager.exit(0);
                }
        	}
        };
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private ResourceBundle          resources;
    private DefaultExplorerPanel    explorerPanel;

    // The width and height of the frame
    protected static int            INITIAL_WIDTH = 800;
    protected static int            INITIAL_HEIGHT = 600;

    // tiling details
    protected static int            TILE_MAX_NUMBER = 10;
    protected static int            TILE_WIDTH_OFFSET = 40;
    protected static int            TILE_HEIGHT_OFFSET = 40;


    protected static final String[] defaultResourceFileNames = { "Explorer" };
    protected static ResourceBundle singletonResources;
    protected static int            numberOfInstances;
    protected static int            instanceNumber;


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
 * $Id: Explorer.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */
