/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: StyledEditorPanel.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
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
 * Base class for a styled editor panel
 */
public class StyledEditorPanel extends EditorPanel
{
    public StyledEditorPanel(ResourceBundle resources)
    {
        super( resources );

    }


    //-------------------------------------------------------------------------
    // Overriding of base class methods
    //-------------------------------------------------------------------------


    /**
     * Fetch the list of actions supported by this
     * editor.  It is implemented to return the list
     * of actions supported by the superclass
     * augmented with the actions defined locally.
     */
    public Action[] getActions()
    {
	    Action[] defaultActions = {
    	    new NewAction(),
    	    new OpenAction(),
    	    new SaveAction()
    	};
	    return TextAction.augmentList(super.getActions(), defaultActions);
    }

    /**
     * Create an editor to represent the given document.
     */
    protected JTextComponent    createEditor()
    {
    	StyleContext sc = new StyleContext();
    	DefaultStyledDocument doc = new DefaultStyledDocument(sc);
    	initDocument(doc, sc);
    	return new JTextPane(doc);
    }

    /**
     * Create a menu for the app.  This is redefined to trap
     * a couple of special entries for now.
     */
    protected JMenu             createMenu(String key)
    {
    	if (key.equals(ColorMenuProducer.COLOR_KEY))
        {
            ColorMenuProducer producer = new ColorMenuProducer( getResources() );
    	    return producer.createMenu();
    	}
    	return super.createMenu(key);
    }



    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    void                        initDocument( DefaultStyledDocument doc,
                                              StyleContext          sc)
    {
	    //Wonderland w = new Wonderland(doc, sc);
	    //Icon alice = new ImageIcon(resources.getString("aliceGif"));
	    //w.loadDocument();
    }



    //-------------------------------------------------------------------------
    // Actions
    //-------------------------------------------------------------------------

    /**
     * Trys to read a file which is assumed to be a
     * serialization of a document.
     */
    class OpenAction extends AbstractAction
    {
	    OpenAction()
        {
	        super(OPEN_ACTION);
    	}

        public void actionPerformed(ActionEvent e)
        {
    	    Frame frame = SwingUtils.getFrame( StyledEditorPanel.this );
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
    		    try
                {
                    FileInputStream fin = new FileInputStream(f);
                    ObjectInputStream istrm = new ObjectInputStream(fin);
                    Document doc = (Document) istrm.readObject();
                    getEditor().setDocument(doc);
                    frame.setTitle(file);
                    validate();
        		}
                catch (IOException io)
                {
        		    // should put in status panel
        		    System.err.println("IOException: " + io.getMessage());
		        }
                catch (ClassNotFoundException cnf)
                {
        		    // should put in status panel
        		    System.err.println("Class not found: " + cnf.getMessage());
        		}
            }
            else
            {
        		// should put in status panel
        		System.err.println("No such file: " + f);
    	    }
    	}
    }

    /**
     * Trys to write the document as a serialization.
     */
    class SaveAction extends AbstractAction
    {
	    SaveAction()
        {
    	    super(SAVE_ACTION);
	    }

        public void actionPerformed(ActionEvent e)
        {
	        Frame frame = SwingUtils.getFrame( StyledEditorPanel.this );
    	    if (fileDialog == null)
            {
    		    fileDialog = new FileDialog(frame);
	        }
    	    fileDialog.setMode(FileDialog.SAVE);
    	    fileDialog.show();
    	    String file = fileDialog.getFile();
    	    if (file == null)
            {
        		return;
    	    }
    	    String directory = fileDialog.getDirectory();
    	    File f = new File(directory, file);
    	    try
            {
                FileOutputStream fstrm = new FileOutputStream(f);
                ObjectOutput ostrm = new ObjectOutputStream(fstrm);
                ostrm.writeObject(getEditor().getDocument());
                ostrm.flush();
    	    }
            catch (IOException io)
            {
        		// should put in status panel
        		System.err.println("IOException: " + io.getMessage());
    	    }
    	}
    }

    /**
     * Creates an empty document.
     */
    class NewAction extends AbstractAction
    {
    	NewAction()
        {
	        super(NEW_ACTION);
    	}

        public void actionPerformed(ActionEvent e)
        {
    	    getEditor().setDocument(new DefaultStyledDocument());
    	    validate();
    	}
    }


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

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
 * $Id: StyledEditorPanel.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */
