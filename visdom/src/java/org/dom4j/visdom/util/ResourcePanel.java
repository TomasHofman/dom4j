/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: ResourcePanel.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import org.dom4j.visdom.util.Log;
import org.dom4j.visdom.util.SystemExitManager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.*;
import javax.swing.*;

/**
 * Base class for a panel which implements general resource
 * finding methods plus specific helper methods for menus and toolBars
 */
public class ResourcePanel extends JPanel
{
    public ResourcePanel()
    {
    	super(true);
    }

    public ResourcePanel(ResourceBundle resources)
    {
    	super(true);
        this.resources = resources;
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

    /**
     * Fetch the menu item that was created for the given
     * command.
     * @param cmd  Name of the action.
     * @returns item created for the given command or null
     *  if one wasn't created.
     */
    protected JMenuItem         getMenuItem(String cmd)
    {
	    return (JMenuItem) menuItems.get(cmd);
    }


    /**
     * get an Action object by the Action.NAME attribute
     *
     */
    public Action               getAction(String cmd)
    {
    	// install the command table via lazy construction
        // rather than in the constructor
        if ( commands == null )
        {
        	commands = new Hashtable();
        	Action[] actions = getActions();
        	for (int i = 0; i < actions.length; i++)
            {
        	    Action a = actions[i];
        	    commands.put(a.getValue(Action.NAME), a);
        	}
        }
	    return (Action) commands.get(cmd);
    }

    /**
     * Fetch the list of actions supported by this
     * editor.  It is implemented to return the list
     * of actions supported by the embedded JTextComponent
     * augmented with the actions defined locally.
     */
    public Action[]             getActions()
    {
	    return new Action[0];
    }

    /**
     * Returns the status bar for this component
     * if one has been created. May return null.
     *
     * @return the statusBar for this component; may be null
     * if one hasn't been created
     */
    public Container            getStatusBar()
    {
        return statusBar;
    }

    //-------------------------------------------------------------------------
    // public make methods: adorn existing toolBar/menuBars
    //-------------------------------------------------------------------------

    /**
     * adds component specific menu items to the given menuBar
     *
     */
    public void                 makeMenuBar(JMenuBar menuBar)
    {
    	JMenuItem mi;
        String temp = getResourceString("menubar");
        if ( temp != null )
        {
        	String[] menuKeys = tokenize(temp);
        	for (int i = 0; i < menuKeys.length; i++)
            {
        	    JMenu m = createMenu(menuKeys[i]);
        	    if (m != null)
                {
            		menuBar.add(m);
                }
            }
        }
    }

    /**
     * adds component specific buttons to the given toolBar
     *
     */

    public void                 makeToolBar(JToolBar toolBar)
    {
        String temp = getResourceString("toolbar");
        if ( temp != null )
        {
        	String[] toolKeys = tokenize(temp);
            for (int i = 0; i < toolKeys.length; i++)
            {
                if (toolKeys[i].equals("-"))
                {
                    toolBar.add(Box.createHorizontalStrut(5));
                }
                else
                {
                    toolBar.add(createTool(toolKeys[i]));
                }
            }
            toolBar.add(Box.createHorizontalGlue());
        }
    }



    //-------------------------------------------------------------------------
    // factory methods
    //-------------------------------------------------------------------------

    /**
     * Create the menuBar for the app.  By default this pulls the
     * definition of the menu from the associated resource file.
     */
    public JMenuBar             createMenuBar()
    {
    	JMenuBar mb = new JMenuBar();
        mb.setName( "MenuBar" );
        makeMenuBar(mb);
        return mb;
    }

    /**
     * Create the toolBar.  By default this reads the
     * resource file for the definition of the toolBar.
     */
    public Component            createToolBar()
    {
	JToolBar toolBar = new JToolBar();
        toolBar.setName( "ToolBar" );
        makeToolBar(toolBar);
        return toolBar;
    }

    /**
     * Create a status bar
     */
    public Component            createStatusBar()
    {
	    // need to do something reasonable here
    	statusBar = new StatusBar();
    	statusBar.setName( "StatusBar" );
    	return statusBar;
    }

    /**
     * To shutdown when run as an application.  This is a
     * fairly lame implementation.   A more self-respecting
     * implementation would at least check to see if a save
     * was needed.
     */
    public static WindowAdapter createApplicationCloser()
    {
        return new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                SystemExitManager.exit(0);
        	}
        };
    }


    //-------------------------------------------------------------------------
    // menu implmentation methods
    //-------------------------------------------------------------------------

    /**
     * Create a menu for the app.  By default this pulls the
     * definition of the menu from the associated resource file.
     */
    protected JMenu             createMenu(String key)
    {
        /*
        if ( key.equals( GoMenuFactory.GO_MENU_KEY ) )
        {
            GoMenuFactory factory = new GoMenuFactory( getResources() );
            return factory.createMenu();
        }
        */
        String label = getResourceString(key + LABEL_SUFFIX);
        JMenu menu = new JMenu( label );
        makeMenu(menu, key);
    	return menu;
    }

    protected void              makeMenu(JMenu menu, String key)
    {
        menu.setName( key );
        String temp = getResourceString(key);
        if ( temp != null )
        {
    	    String[] itemKeys = tokenize(temp);
        	for (int i = 0; i < itemKeys.length; i++)
            {
        	    if (itemKeys[i].equals("-"))
                {
        		    menu.addSeparator();
        	    }
                else
                {
            		JMenuItem mi = createMenuItem(itemKeys[i]);
            		menu.add(mi);
                }
        	}
        }
    }

    /**
     * This is the hook through which all menu items are
     * created.  It registers the result with the menuitem
     * hashtable so that it can be fetched with getMenuItem().
     * @see #getMenuItem
     */
    protected JMenuItem         createMenuItem(String cmd)
    {
        String label = getLabelString(cmd );
	    final JMenuItem mi = new JMenuItem( label );
        mi.setName( cmd );
        Icon icon = getResourceIcon(cmd);
        //URL url = getResource(cmd + IMAGE_SUFFIX);
    	//if (url != null)
        if ( icon != null )
        {
	        mi.setHorizontalTextPosition(JButton.RIGHT);
	        mi.setIcon(icon);
	        //mi.setIcon(new ImageIcon(url));
    	}
    	String astr = getResourceString(cmd + ACTION_SUFFIX);
    	if (astr == null)
        {
    	    astr = cmd;
    	}
    	mi.setActionCommand(astr);
    	Action a = getAction(astr);
    	if (a != null)
        {
    	    mi.addActionListener(a);

            // #### swing should do this but I'll add it anyway
            if ( ! a.isEnabled() )
            {
	            mi.setEnabled(false);
            }

            a.addPropertyChangeListener(
                new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent evt)
                    {
                        String name = evt.getPropertyName();
                        Object value = evt.getNewValue();
                        if ( name.equals( ACTION_ENABLED ) &&
                             value instanceof Boolean )
                        {
                            mi.setEnabled( ((Boolean)value).booleanValue() );
                        }
                    }
                }
            );
    	}
        else
        {
	        mi.setEnabled(false);
    	}
	    menuItems.put(cmd, mi);
    	return mi;
    }

    // #### should be protected
    public JMenuItem            createActionMenuItem(Action action)
    {
        String cmd = (String) action.getValue(Action.NAME);

	    final JMenuItem mi = new JMenuItem( getLabelString(cmd ));
        mi.setName( cmd );
        Icon icon = getResourceIcon(cmd);
        //URL url = getResource(cmd + IMAGE_SUFFIX);
    	//if (url != null)
        if ( icon != null )
        {
	        mi.setHorizontalTextPosition(JButton.RIGHT);
	        mi.setIcon(icon);
	        //mi.setIcon(new ImageIcon(url));
    	}
    	String astr = getResourceString(cmd + ACTION_SUFFIX);
    	if (astr == null)
        {
    	    astr = cmd;
    	}
    	mi.setActionCommand(astr);
        mi.addActionListener(action);

        // #### swing should do this but I'll add it anyway
        if ( ! action.isEnabled() )
        {
            mi.setEnabled(false);
        }

        action.addPropertyChangeListener(
            new PropertyChangeListener()
            {
                public void propertyChange(PropertyChangeEvent evt)
                {
                    String name = evt.getPropertyName();
                    Object value = evt.getNewValue();
                    if ( name.equals( ACTION_ENABLED ) &&
                         value instanceof Boolean )
                    {
                        mi.setEnabled( ((Boolean)value).booleanValue() );
                    }
                }
            }
        );
        menuItems.put(cmd, mi);
    	return mi;
    }


    //-------------------------------------------------------------------------
    // toolBar implmentation methods
    //-------------------------------------------------------------------------

    /**
     * Hook through which every toolBar item is created.
     */
    protected Component         createTool(String key)
    {
	    return createToolBarButton(key);
    }

    /**
     * Create a button to go inside of the toolBar.  By default this
     * will load an image resource.  The image filename is relative to
     * the classpath (including the '.' directory if its a part of the
     * classpath), and may either be in a JAR file or a separate file.
     *
     * @param key The key in the resource file to serve as the basis
     *  of lookups.
     */
    protected JButton           createToolBarButton(String key)
    {
        final JButton b = new JButton(getResourceIcon(key));
        b.setName( key );

        /*
	    //URL url = getResource(key + IMAGE_SUFFIX);
        //JButton b = new JButton(new ImageIcon(url))
        {
            public float getAlignmentY() { return 0.5f; }
    	};
        */

        b.setRequestFocusEnabled(false);
        b.setMargin(new Insets(1,1,1,1));

    	String astr = getResourceString(key + ACTION_SUFFIX);
    	if (astr == null)
        {
    	    astr = key;
    	}
    	Action a = getAction(astr);
    	if (a != null)
        {
    	    b.setActionCommand(astr);
    	    b.addActionListener(a);

            // #### swing should do this but I'll add it anyway
            if ( ! a.isEnabled() )
            {
	            b.setEnabled(false);
            }

            a.addPropertyChangeListener(
                new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent evt)
                    {
                        String name = evt.getPropertyName();
                        Object value = evt.getNewValue();
                        if ( name.equals( ACTION_ENABLED ) &&
                             value instanceof Boolean )
                        {
                            b.setEnabled( ((Boolean)value).booleanValue() );
                        }
                    }
                }
            );

    	}
        else
        {
    	    b.setEnabled(false);
    	}

	    String tip = getResourceString(key + TOOLTIP_SUFFIX);
    	if (tip != null)
        {
    	    b.setToolTipText(tip);
    	}
        return b;
    }



    //-------------------------------------------------------------------------
    // Resource fetching methods
    //-------------------------------------------------------------------------

    protected String            getResourceString(String nm)
    {
	    String str;
    	try
        {
    	    str = getResources().getString(nm);
    	}
        catch (MissingResourceException mre)
        {
    	    str = null;
    	}
        catch (NullPointerException e)
        {
    	    str = null;
    	}
    	return str;
    }

    protected String            getLabelString(String cmd)
    {
        String answer = getResourceString(cmd + LABEL_SUFFIX);
        if ( answer == null )
            return cmd;
        return answer;
    }


    protected Icon              getResourceIcon(String key)
    {
        String s = getResourceString(key + IMAGE_SUFFIX);
        if ( s != null )
        {
            String name = getResourceString(key + IMAGE_SUFFIX);
            URL url = ClassLoader.getSystemResource( name );
            if ( url != null )
            {
                return new ImageIcon(url);
            }
            else
            {
                Log.info( "Could not load system resource: " + name );
            }
            /*
            try
            {
                String name = getResourceString(key + IMAGE_SUFFIX);
	            URL url = ClassLoader.getSystemResource( name );
	            //URL url = new URL(name);
                if ( url != null )
                {
                return new ImageIcon(url);
            }
            catch (MalformedURLException e)
            {
                Log.exception(e);
            }
            //return new ImageIcon(getResourceString(key + IMAGE_SUFFIX));
            */
        }
        return null;
    }

    protected URL               getResource(String key)
    {
	    String name = getResourceString(key);
    	if (name != null)
        {
    	    URL url = this.getClass().getResource(name);
            System.out.println( "Resource for key: " + key +
                                " is name: " + name +
                                " and URL: " + url );
    	    return url;
    	}
    	return null;
    }

    /**
     * Take the given string and chop it up into a series
     * of strings on whitespace boundries.  This is useful
     * for trying to get an array of strings out of the
     * resource file.
     */
    protected String[]          tokenize(String input)
    {
    	Vector v = new Vector();
    	StringTokenizer t = new StringTokenizer(input);
    	String cmd[];

    	while (t.hasMoreTokens())
    	    v.addElement(t.nextToken());
    	cmd = new String[v.size()];
    	for (int i = 0; i < cmd.length; i++)
    	    cmd[i] = (String) v.elementAt(i);
    	return cmd;
    }


    //-------------------------------------------------------------------------
    // StatusBar
    //-------------------------------------------------------------------------

    /**
     * #### FIXME - I'm not very useful yet
     */
    class StatusBar extends Container
    {

        public StatusBar()
        {
    	    super();
    	    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    }

        public void paint(Graphics g)
        {
	        super.paint(g);
    	}
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    private Hashtable           menuItems = new Hashtable();
    private ResourceBundle      resources;
    private Hashtable           commands;
    private JMenuBar            menuBar;
    private JToolBar            toolBar;
    private Container           statusBar;

    /**
     * Suffix applied to the key used in resource file
     * lookups for an image.
     */
    public static final String IMAGE_SUFFIX = ".image";

    /**
     * Suffix applied to the key used in resource file
     * lookups for a label.
     */
    public static final String LABEL_SUFFIX = ".label";

    /**
     * Suffix applied to the key used in resource file
     * lookups for an action.
     */
    public static final String ACTION_SUFFIX = ".action";

    /**
     * Suffix applied to the key used in resource file
     * lookups for tooltip text.
     */
    public static final String TOOLTIP_SUFFIX = ".tooltip";

    // #### should be moved into Swing
    public static final String ACTION_ENABLED = "enabled";
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
 * $Id: ResourcePanel.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */
