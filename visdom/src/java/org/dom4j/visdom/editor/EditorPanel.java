/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: EditorPanel.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */

package org.dom4j.visdom.editor;

import org.dom4j.visdom.util.*;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.text.*;
import javax.swing.*;

/**
 * Base class for an editor panel
 */
public class EditorPanel extends ResourcePanel
{
    public EditorPanel(ResourceBundle resources)
    {
    	super(resources);

    	setBorder(BorderFactory.createEtchedBorder());
    	setLayout(new BorderLayout());

    	// create the embedded JTextComponent
    	editor = createEditor();

        JScrollPane scroller = new JScrollPane();
        JViewport port = scroller.getViewport();
    	port.add(editor);
    	try
        {
    	    String vpFlag = getResources().getString("ViewportBackingStore");
    	    Boolean bs = new Boolean(vpFlag);
    	    port.setBackingStoreEnabled(bs.booleanValue());
    	}
        catch (MissingResourceException mre)
        {
    	    // just use the viewport default
    	}

        add( scroller, BorderLayout.CENTER );
    }



    //-------------------------------------------------------------------------
    // getters and setters
    //-------------------------------------------------------------------------

    /**
     * Fetch the list of actions supported by this
     * editor.  It is implemented to return the list
     * of actions supported by the embedded JTextComponent
     * augmented with the actions defined locally.
     */
    public Action[]             getActions()
    {
	    return TextAction.augmentList(editor.getActions(), defaultActions);
    }

    public String               getEditorText()
    {
        return getEditor().getText();
    }
    
    /**
     * Fetch the editor contained in this panel
     */
    protected JTextComponent    getEditor()
    {
        if ( editor == null )
        {
            editor = createEditor();
        }
	    return editor;
    }

    //-------------------------------------------------------------------------
    // factory methods
    //-------------------------------------------------------------------------

    /**
     * Factory method to create an editor to represent the given document.
     */
    protected JTextComponent    createEditor()
    {
	    return new JTextArea();
    }



    //-------------------------------------------------------------------------
    // Overriding base class methods
    //-------------------------------------------------------------------------

    /**
     * Create a menu for the app.  This is redefined to trap
     * a couple of special entries for now.
     */
    protected JMenu             createMenu(String key)
    {
    	if (key.equals(LookAndFeelMenuProducer.LOOKANDFEEL_KEY))
        {
            LookAndFeelMenuProducer producer =
                new LookAndFeelMenuProducer( getResources(),
                                             new DefaultFrameResolver( this ) );
    	    return producer.createMenu();
    	}
    	return super.createMenu(key);
    }



    //-------------------------------------------------------------------------
    // Actions
    //-------------------------------------------------------------------------

    class NewAction extends AbstractAction
    {
    	NewAction()
        {
    	    super(NEW_ACTION);
	    }

	    NewAction(String nm)
        {
	        super(nm);
    	}

        public void actionPerformed(ActionEvent e)
        {
    	    getEditor().setDocument(new PlainDocument());
    	    validate();
    	}
    }

    class OpenAction extends NewAction
    {
    	OpenAction()
        {
    	    super(OPEN_ACTION);
    	}

        public void actionPerformed(ActionEvent e)
        {
    	    Frame frame = SwingUtils.getFrame( EditorPanel.this );

    	    if (fileDialog == null)
            {
    		    fileDialog = new FileDialog(frame);
    	    }
    	    fileDialog.setMode(FileDialog.LOAD);
    	    fileDialog.show();

    	    String file = fileDialog.getFile();
    	    if (file == null)
            {
        		return;
    	    }
    	    String directory = fileDialog.getDirectory();
    	    File f = new File(directory, file);
    	    if (f.exists())
            {
        		super.actionPerformed(e);
        		frame.setTitle(file);
    	    	Thread loader = new DocumentLoader(f, editor.getDocument(), getStatusBar());
    		    loader.start();
	        }
    	}
    }

    /**
     * Really lame implementation of an exit command
     */
    class ExitAction extends AbstractAction
    {
    	ExitAction()
        {
    	    super(EXIT_ACTION);
    	}

        public void actionPerformed(ActionEvent e)
        {
	        System.exit(0);
    	}
    }


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    /**
     * Actions defined by the Notepad class
     */
    private Action[] defaultActions =
    {
    	new NewAction(),
    	new OpenAction(),
    	new ExitAction()
    };

    private JTextComponent      editor;

    protected FileDialog        fileDialog;

    public static final String  OPEN_ACTION = "open";
    public static final String  NEW_ACTION  = "new";
    public static final String  SAVE_ACTION = "save";
    public static final String  EXIT_ACTION = "exit";



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
 * $Id: EditorPanel.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */
