/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: IconRepository.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import org.dom4j.visdom.util.Log;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.applet.Applet;
import java.net.*;

public class IconRepository
{
    /*
     * Accessor to singleton
     *
     */
    public static IconRepository
    getInstance()
    {
        if ( singleton == null )
        {
            singleton = new IconRepository();
        }
        return singleton;
    }

    public static void
    setInstance(IconRepository aValue)
    {
        singleton = aValue;
    }


    //
    // accessors to various icons
    //

    public Icon                 getTreeFolderIcon()
    {
        // defaultDirectoryIcon = loadIcon( "images//directory.gif", "Folder icon" );
        return treeCellRenderer.getClosedIcon();
    }

    public Icon                 getTreeLeafIcon()
    {
        // defaultFileIcon = loadIcon( "images//file.gif", "File icon" );
        return treeCellRenderer.getLeafIcon();
    }

    /**
      * returns an icon for query buttons or menu items.
      *
      */
    public Icon                 getQueryIcon()
    {
        return loadIcon( "images/20x20/Magnify.gif" );
    }

    /**
      * returns an icon for forward/next buttons
      *
      */
    public Icon                 getForwardIcon()
    {
        return loadIcon( "images/20x20/Right.gif" );
    }


    //
    // implementation methods
    //

    public Icon                 loadIcon( String filename )
    {
        URL url = ClassLoader.getSystemResource( filename );
        if ( url != null )
        {
            return new ImageIcon(url);
        }
        else
        {
            Log.info( "Could not load system resource: " + filename );
            return null;
        }
/*
        if ( applet == null )
        {
            return new ImageIcon(filename, description );
        }
        else
        {
            URL url;
            try
            {
                url = new URL(applet.getCodeBase(),filename);
            }
            catch (MalformedURLException e)
            {
                System.err.println("Couldn't load file: " + filename + " exception: " + e);
                return null;
            }
            return new ImageIcon(url, description);
        }
*/
    }


    private static      IconRepository          singleton = null;
    protected static    DefaultTreeCellRenderer treeCellRenderer = new DefaultTreeCellRenderer();
    protected static    Applet                  applet = null; // are we in an applet?
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
 * $Id: IconRepository.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */
