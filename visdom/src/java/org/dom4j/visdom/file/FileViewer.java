/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: FileViewer.java,v 1.1 2001/05/22 08:12:50 jstrachan Exp $
 */

package org.dom4j.visdom.file;

import javax.swing.*;

import javax.activation.CommandInfo;
import javax.activation.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.net.*;
import java.io.File;
import javax.swing.*;


public class FileViewer //implements ViewableNode
{
    public FileViewer( File aFile )
    {
        file = aFile;
    }

    public String getFilename()
    {
        return file.getAbsolutePath();
    }

    public Frame openFrame( String openMode )
    {
        Component component = getComponent( openMode );
        if ( component != null  )
        {
            final Frame frame = new Frame( getFilename() );
            frame.addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        System.out.println( "Closing..." );
                    	frame.setVisible(false);
                        frame.dispose();
                    }
                }
            );

            frame.add( component );
             //frame.getContentPane().add( component );

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dim = component.getPreferredSize();
    	    if ( dim.width != 0 && dim.height != 0)
            {
        		// this is what we do under normal conditions
        		dim.height += 40;
        		dim.width += 15;
        		frame.setSize( dim );

                frame.setLocation( screenSize.width/2 - dim.width/2,
                                   screenSize.height/2 - dim.height/2 );

		        component.invalidate();
        		component.validate();
        		component.doLayout();
                frame.setVisible( true );
       	    }
            else
            {
        		// we get here if for some reason our child's
        		// getPref size needs to have it's peer created
        		// first...
                frame.setVisible( true );
        		dim = component.getPreferredSize();
        		dim.height += 40;
        		dim.width += 15;
        		frame.setSize( dim );

                frame.setLocation( screenSize.width/2 - dim.width/2,
                                   screenSize.height/2 - dim.height/2 );
                                   
        		component.validate();
    	    }
	        frame.setSize(frame.getSize());
            return frame;
        };
        return null;
    }

    public Component getComponent(String openMode)
    {
      	FileDataSource fds = new FileDataSource( getFilename() );
      	DataHandler dh = new DataHandler(fds);

        // comment out previous two lines, and uncomment next
    	// line and pass in a URL on the command line.
    	// DataHandler df = new DataHandler( new URL(filename));

        /*
        System.out.println("Using DataHandler contentType: " + dh.getContentType() + " name: " + dh.getName() );

        CommandInfo[] preferredCommands = dh.getPreferredCommands();
        for ( int i = 0; i < preferredCommands.length; i++ )
        {
            CommandInfo ci = preferredCommands[i];
            System.out.println( "Preferred command name: " + ci.getCommandName()
                                + " class: " + ci.getCommandClass() );
        }
        */

        CommandInfo commandInfo = dh.getCommand( openMode );
    	if (commandInfo == null)
        {
    	    System.out.println("no viewer found for file: " + getFilename());
            return null;
    	}
        Object bean = dh.getBean( commandInfo );
        if ( bean == null )
        {
            System.out.println("NO BEAN FOUND!");
        }
        else
        {
            System.out.println("Using Bean: " + bean.getClass() + " : " + bean );
        }
    	return (Component) bean;
    }

    /*
     * like getComponent() but returns a JComponent
     * so the setMinimumSize() method can be used.
     *
     */
    public JComponent getJComponent(String openMode)
    {
        Component component = getComponent( openMode );
        if ( component instanceof JComponent )
        {
            return (JComponent)component;
        }
        if ( component != null ) {
            JPanel answer = new JPanel(true); // double buffer
            answer.setLayout( new BorderLayout() );
            answer.add( component, BorderLayout.CENTER );
            return answer;
        }
        return null;
    }

    protected File file = null;

    public static String FILE_VIEW = "view";
    public static String FILE_EDIT = "view";

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
 * $Id: FileViewer.java,v 1.1 2001/05/22 08:12:50 jstrachan Exp $
 */
