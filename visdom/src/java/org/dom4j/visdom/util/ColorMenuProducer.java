/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: ColorMenuProducer.java,v 1.1 2001/05/22 08:12:55 jstrachan Exp $
 */

package org.dom4j.visdom.util;

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
public class ColorMenuProducer
{
    public ColorMenuProducer(ResourceBundle resources)
    {
        this.resources = resources;
    }

    //-------------------------------------------------------------------------
    // action methods
    //-------------------------------------------------------------------------
    // this will soon be replaced
    public JMenu                createMenu()
    {
    	JMenu menu = new JMenu(getResourceLabel(COLOR_KEY));

        addColorMenuItem( menu, "LightGray", "set-foreground-lightGray", Color.lightGray );
        addColorMenuItem( menu, "DarkGray", "set-foreground-darkGray", Color.darkGray );
        addColorMenuItem( menu, "Gray", "set-foreground-gray", Color.gray );
        addColorMenuItem( menu, "Black", "set-foreground-black", Color.black );
        addColorMenuItem( menu, "Magenta", "set-foreground-magenta", Color.magenta );
        addColorMenuItem( menu, "Blue", "set-foreground-blue", Color.blue );
        addColorMenuItem( menu, "Cyan", "set-foreground-blue", Color.cyan );
        addColorMenuItem( menu, "Green", "set-foreground-green", Color.green );
        addColorMenuItem( menu, "Yellow", "set-foreground-yellow", Color.yellow );
        addColorMenuItem( menu, "Pink", "set-foreground-pink", Color.pink );
        addColorMenuItem( menu, "Red", "set-foreground-red", Color.red );
    	return menu;
    }




    //-------------------------------------------------------------------------
    // ColoredSquare - an Icon for menus
    //-------------------------------------------------------------------------
    class ColoredSquare implements Icon
    {
    	public ColoredSquare(Color c)
        {
	        this.color = c;
    	}

    	public void paintIcon(Component c, Graphics g, int x, int y)
        {
	        Color oldColor = g.getColor();
    	    g.setColor(color);
    	    g.fill3DRect(x,y,getIconWidth(), getIconHeight(), true);
    	    g.setColor(oldColor);
	    }

    	public int getIconWidth() { return 12; }
    	public int getIconHeight() { return 12; }

	    Color color;
    }


    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    void                        addColorMenuItem( JMenu     menu,
                                                  String    menuNameResourceName,
                                                  String    editorActionCommand,
                                                  Color     color )
    {
        String menuName = menuNameResourceName;
        try
        {
            menuName = getResources().getString( menuNameResourceName );
        }
        catch ( MissingResourceException exn )
        {
        }

    	JMenuItem mi = new JMenuItem( menuName );
    	mi.setHorizontalTextPosition(JButton.RIGHT);
    	mi.setIcon(new ColoredSquare(color));
    	ActionListener a = new StyledEditorKit.ForegroundAction( editorActionCommand,
                                                                 color );
    	mi.addActionListener(a);
    	menu.add(mi);
    }

    //-------------------------------------------------------------------------
    // getters and setters
    //-------------------------------------------------------------------------

    public ResourceBundle       getResources()
    {
        return resources;
    }

    public void                 setResources(ResourceBundle resources)
    {
        this.resources = resources;
    }



    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected String            getResourceLabel(String key)
    {
    	try
        {
    	    return getResources().getString(key + ResourcePanel.LABEL_SUFFIX);
    	}
        catch (MissingResourceException mre)
        {
    	}
    	return null;
    }


    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    protected ResourceBundle    resources;

    public static final String  COLOR_KEY = "color";
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
 * $Id: ColorMenuProducer.java,v 1.1 2001/05/22 08:12:55 jstrachan Exp $
 */
