/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: EditorFrame.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */

package org.dom4j.visdom.editor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


/**
 * Base class for a simple editor
 *
 */
public class EditorFrame extends JFrame
{
    public EditorFrame(ResourceBundle resources)
    {
        setTitle(resources.getString("Title"));

        editorPanel = createEditorPanel( resources );

        // add window closer!
        addWindowListener( editorPanel.createApplicationCloser() );

     	JPanel panel = new JPanel();
    	panel.setLayout(new BorderLayout());
    	panel.add( createToolBar(), BorderLayout.NORTH );
    	panel.add( editorPanel, BorderLayout.CENTER );

    	getContentPane().setLayout(new BorderLayout());
    	getContentPane().add( createMenuBar(), BorderLayout.NORTH );
    	getContentPane().add( panel, BorderLayout.CENTER );
    	getContentPane().add( createStatusBar(), BorderLayout.SOUTH );

        pack();
    }



    //-------------------------------------------------------------------------
    // Factory methods
    //-------------------------------------------------------------------------

    /**
     * Factory method to create the editor panel to use
     *
     */
    protected EditorPanel       createEditorPanel(ResourceBundle resources)
    {
        return new EditorPanel(resources);
    }

    protected JMenuBar          createMenuBar()
    {
        return editorPanel.createMenuBar();
    }

    protected Component         createToolBar()
    {
        return editorPanel.createToolBar();
    }

    protected Component         createStatusBar()
    {
        return editorPanel.createStatusBar();
    }



    //-------------------------------------------------------------------------
    // Main
    //-------------------------------------------------------------------------
    public static void main(String[] args)
    {
        try
        {
            // try setting the locale
            Locale locale = Locale.getDefault();

            if ( args.length > 0 )
            {
                String country = args[0];
                if ( country.equals( "france" ) )
                {
                    System.out.println( "Using French" );
                    locale = new Locale( Locale.FRENCH.getLanguage(), Locale.FRANCE.getCountry() );
                }
                else if ( country.equals( "germany" ) )
                {
                    System.out.println( "Using German" );
                    locale = new Locale( Locale.GERMAN.getLanguage(), Locale.GERMANY.getCountry() );
                }
            }

            ResourceBundle resources;
            try
            {
                resources = ResourceBundle.getBundle( "Editor", locale );
                
                EditorFrame frame = new EditorFrame(resources);
                frame.setSize(500, 600);
                frame.show();
            }
            catch (MissingResourceException mre)
            {
                System.err.println("Editor.properties not found");
                System.exit(0);
            }
        }
        catch (Throwable t)
        {
            System.out.println("uncaught exception: " + t);
            t.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    protected EditorPanel       editorPanel;
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
 * $Id: EditorFrame.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */
