/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: FileViewPanel.java,v 1.1 2001/05/22 08:12:50 jstrachan Exp $
 */

package org.dom4j.visdom.file;

//import com.jsoft.explorer.*;
//import com.jsoft.explorer.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.table.*;

// DirectoryModel
import org.dom4j.visdom.util.file.DirectoryModel;
import org.dom4j.visdom.util.file.TypedFile;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.*;


public class FileViewPanel extends JPanel
{
    public FileViewPanel(DirectoryModel aModel)
    {
        super( true ); // double buffer

        setLayout( new BorderLayout() );

        fileView = new FileTablePanel( aModel );


        // set minimum sizes of split pane
        int minimumSize = 0;
        fileView.setMinimumSize( new Dimension(minimumSize,minimumSize) );
        if ( viewComponent instanceof JComponent )
        {
            ((JComponent)viewComponent).setMinimumSize( new Dimension(minimumSize,minimumSize) );
        }

        viewComponent = getEmptyViewComponent();

        splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
                                    false,
                                    fileView,
                                    viewComponent );
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);
        splitPane.invalidate();

//            SwingUtilities.invokeLater( new Runnable()
//                                        {
//                                            public void run()
//                                            {
//                                                splitPane.setDividerLocation( (int) (splitPane.getHeight() / 3 ) );
//                                            }
//                                        } );


        // add listeners to file list view
        ListSelectionModel listSelectionModel = fileView.getSelectionModel();
        if ( fileView.getSelectionModel() == null )
        {
            System.out.println( "No selection model!" );
        }
        else
        {
            listSelectionModel.addListSelectionListener(
                new ListSelectionListener()
                {
                    public void valueChanged(ListSelectionEvent e)
                    {
                        int index = e.getFirstIndex();
                        File aFile = fileView.getFileAtIndex( index );
                        handleFileView( aFile );
                    }
                }
            );
        }

        fileView.addFileOpenListener(
            new FileOpenListener()
            {
                public void fileOpen( FileOpenEvent event )
                {
                    handleFileOpen( event.getFile() );
                }
            }
        );
    }

    /**
     * add listener interface for file open events
     * (e.g. by double clicking on a file)
     */
    public void addFileOpenListener( FileOpenListener listener )
    {
        fileView.addFileOpenListener( listener );
    }

    /**
     * remove listener interface for file open events
     * (e.g. by double clicking on a file)
     */
    public void removeFileOpenListener( FileOpenListener listener )
    {
        fileView.removeFileOpenListener( listener );
    }




    public void handleFileOpen( File aFile )
    {
        if ( aFile != null && ! aFile.isDirectory() )
        {
            String fileName = aFile.getAbsolutePath();

            // selecting a file
            System.out.println("Editing file: " + fileName);
            FileViewer viewer = new FileViewer( aFile );
            viewer.openFrame( FileViewer.FILE_EDIT );
        }
    }

    public void handleFileView( File aFile )
    {
        if ( aFile == null )
        {
            setViewComponent( null );
        }
        else if ( aFile.isDirectory() )
        {
            if ( isShowDirectoriesInViewPanel )
            {
                DirectoryModel newModel = new DirectoryModel( aFile );
                setViewComponent( new FileTablePanel( newModel ) );
            }
            else
            {
                setViewComponent( null );
            }
        }
        else
        {
            String fileName = aFile.getAbsolutePath();

            // selecting a file
            System.out.println("Viewing file: " + fileName);
            FileViewer viewer = new FileViewer( aFile );
            setViewComponent( viewer.getJComponent( FileViewer.FILE_VIEW ) );
        }
    }

    protected void setViewComponent( Component component )
    {
        if ( viewComponent != component )
        {
            viewComponent = component;
            if ( viewComponent == null )
            {
                viewComponent = getEmptyViewComponent();
            }
            if ( viewComponent instanceof JComponent )
            {
                int minimumSize = 0;
                ((JComponent)viewComponent).setMinimumSize(
                    new Dimension(minimumSize,minimumSize) );
            }
            splitPane.setBottomComponent( viewComponent );
            viewComponent.repaint();
            validate();
        }
    }

    public Component getEmptyViewComponent()
    {
        return new JPanel();
    }

    //
    // Attributes
    //
    protected FileTablePanel    fileView = null;
    protected JSplitPane        splitPane;
    protected Component         viewComponent;

    public static boolean isShowDirectoriesInViewPanel = false;
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
 * $Id: FileViewPanel.java,v 1.1 2001/05/22 08:12:50 jstrachan Exp $
 */
