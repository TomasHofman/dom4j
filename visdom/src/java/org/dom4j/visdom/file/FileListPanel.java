/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: FileListPanel.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */

package org.dom4j.visdom.file;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;

// DirectoryModel
import org.dom4j.visdom.util.file.DirectoryModel;
import org.dom4j.visdom.util.file.TypedFile;

import java.io.File;
import java.util.Vector;


/**
 * abstract class <code>FileListPanel</code> implements a file
 * lister given a <code>DirectoryModel</code>
 *
 */
public abstract class FileListPanel extends JPanel
{
    public FileListPanel(DirectoryModel aModel)
    {
        super( true ); // double buffer

        model = aModel;
    }

    /**
     * abstract method to return the list selection model
     * for the file list panel
     */
    public abstract ListSelectionModel getSelectionModel();

    /**
     * add listener interface for file open events
     * (e.g. by double clicking on a file)
     */
    public void addFileOpenListener( FileOpenListener listener )
    {
        if ( listenerList == null )
            listenerList = new EventListenerList();
        listenerList.add( FileOpenListener.class, listener );
    }

    /**
     * remove listener interface for file open events
     * (e.g. by double clicking on a file)
     */
    public void removeFileOpenListener( FileOpenListener listener )
    {
        listenerList.remove( FileOpenListener.class, listener );
    }

    protected void fireFileOpenEvent( int listIndex )
    {
        File aFile = getFileAtIndex( listIndex );
        if ( aFile != null )
        {
            FileOpenEvent event = null;
            // Guaranteed to return a non-null array
            Object[] listeners = listenerList.getListenerList();
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for ( int i = listeners.length - 2; i >= 0; i -= 2 )
            {
                if ( listeners[i]==FileOpenListener.class )
                {
                    if ( event == null )
                    {
                        event = new FileOpenEvent( this, aFile, listIndex );
                    }
                    ((FileOpenListener)listeners[i+1]).fileOpen(event);
                }
            }
        }
    }

    /**
     * Returns the <code>File</code> object at the specified
     * index in the directory model.
     */
    public File getFileAtIndex(int index)
    {
        if ( model != null )
        {
            Vector files = model.getTypedFiles();
            if ( files != null && index >= 0 && index < files.size() )
            {
                return (File)files.elementAt( index );
            }
        }
        return null;
    }

    public DirectoryModel getDirectoryModel()
    {
        return model;
    }

    protected DirectoryModel    model;
    private EventListenerList   listenerList = null;
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
 * $Id: FileListPanel.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */
