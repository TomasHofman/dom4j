/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: LookAndFeelMenuProducer.java,v 1.1 2001/05/22 08:12:59 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class LookAndFeelMenuProducer implements ItemListener
{
    public LookAndFeelMenuProducer( ResourceBundle  resources,
                                    FrameResolver   frameResolver )
    {
        this.resources = resources;
        this.frameResolver = frameResolver;
    }

    //-------------------------------------------------------------------------
    // ItemListener interface
    //-------------------------------------------------------------------------
	public void                 itemStateChanged(ItemEvent e)
    {
        Frame frame = frameResolver.getFrame();

	    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

	    JRadioButtonMenuItem rb = (JRadioButtonMenuItem) e.getSource();

        try
        {
            if ( rb.isSelected() )
            {
                String text = rb.getText();

                System.out.println("Setting the look and feel to: " + text);

                if ( isResourceLabel( WINDOWS_LOOK_AND_FEEL, text ) )
                {
	    	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }
                else
                if ( isResourceLabel( MOTIF_LOOK_AND_FEEL, text ) )
                {
	    	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                }
                else
                if ( isResourceLabel( MAC_LOOK_AND_FEEL, text ) )
                {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
                }
                else
                if ( isResourceLabel( ORGANIC_SANTAFE_LOOK_AND_FEEL, text ) )
                {
                    //com.sun.java.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(
                    //    new com.sun.java.swing.plaf.metal.DefaultMetalTheme() );

	                UIManager.setLookAndFeel("com.sun.java.swing.plaf.metal.MetalLookAndFeel");
                }
                else
                if ( isResourceLabel( ORGANIC_VANCOUVER_LOOK_AND_FEEL, text ) )
                {
	                UIManager.setLookAndFeel("com.sun.java.swing.plaf.multi.MultiLookAndFeel");
                }
                else
                {
                    System.out.println("Couldn't map the look and feel: " + text );
                }
	    	    SwingUtilities.updateComponentTreeUI(frame);
            }
        }
        catch (Exception exc)
        {
            rb.setEnabled(false);
            System.err.println("Could not load LookAndFeel: " + rb.getText());
        }

	    frame.invalidate();
	    frame.validate();
	    frame.repaint();
	    frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}



    //-------------------------------------------------------------------------
    // action methods
    //-------------------------------------------------------------------------
    public JMenu                createMenu()
    {
        JMenu menu = new JMenu( getResourceLabel( LOOKANDFEEL_KEY ) );
        makeMenu(menu, LOOKANDFEEL_KEY );
        return menu;
    }

    public void                 makeMenu(JMenu menu, String key)
    {
	    ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rb;

        rb = (JRadioButtonMenuItem) menu.add(
            new JRadioButtonMenuItem( getResourceLabel( WINDOWS_LOOK_AND_FEEL )));
	    rb.setSelected(UIManager.getLookAndFeel().getName().equals("Windows"));
	    group.add(rb);
	    rb. addItemListener(this);

        rb = (JRadioButtonMenuItem) menu.add(
            new JRadioButtonMenuItem( getResourceLabel( MOTIF_LOOK_AND_FEEL )));
	    rb.setSelected(UIManager.getLookAndFeel().getName().equals("CDE/Motif"));
	    group.add(rb);
	    rb.addItemListener(this);

        rb = (JRadioButtonMenuItem) menu.add(
            new JRadioButtonMenuItem( getResourceLabel( MAC_LOOK_AND_FEEL )));
	    rb.setSelected(UIManager.getLookAndFeel().getName().equals("Macintosh"));
	    rb.setEnabled(false); // ####
	    group.add(rb);
	    rb.addItemListener(this);

        rb = (JRadioButtonMenuItem) menu.add(
            new JRadioButtonMenuItem( getResourceLabel( ORGANIC_SANTAFE_LOOK_AND_FEEL )));
	    rb.setSelected(UIManager.getLookAndFeel().getName().equals("Java"));
	    group.add(rb);
	    rb.addItemListener(this);

        rb = (JRadioButtonMenuItem) menu.add(
            new JRadioButtonMenuItem( getResourceLabel( ORGANIC_VANCOUVER_LOOK_AND_FEEL )));
	    group.add(rb);
	    rb.addItemListener(this);

        // show tooltips
        menu.add(new JSeparator());

        final JCheckBoxMenuItem cb;
        cb = (JCheckBoxMenuItem) menu.add(
            new JCheckBoxMenuItem(getResourceLabel( SHOW_TOOLTIPS ) ));
	    cb.setSelected(true);

	    ActionListener l =
            new ActionListener()
            {
        	    public void actionPerformed(ActionEvent e)
                {
            		if(cb.isSelected())
                    {
                        ToolTipManager.sharedInstance().setEnabled(true);
            		}
                    else
                    {
                        ToolTipManager.sharedInstance().setEnabled(false);
                    }
                }
    		};
	    cb.addActionListener(l);
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

    protected boolean           isResourceLabel(String key, String value)
    {
        String str = getResourceLabel(key);
        if ( str != null )
        {
            return str.equals( value );
    	}
    	return false;
    }



    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    protected ResourceBundle    resources;
    protected FrameResolver     frameResolver;

    public static final String  WINDOWS_LOOK_AND_FEEL           = "windowsLookAndFeel";
    public static final String  MOTIF_LOOK_AND_FEEL             = "motifLookAndFeel";
    public static final String  MAC_LOOK_AND_FEEL               = "macLookAndFeel";
    public static final String  ORGANIC_SANTAFE_LOOK_AND_FEEL   = "organicSFLookAndFeel";
    public static final String  ORGANIC_VANCOUVER_LOOK_AND_FEEL = "organicVLookAndFeel";

    public static final String  SHOW_TOOLTIPS                   = "showToolTips";

    public static final String  LOOKANDFEEL_KEY                 = "lookAndFeel";
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
 * $Id: LookAndFeelMenuProducer.java,v 1.1 2001/05/22 08:12:59 jstrachan Exp $
 */
