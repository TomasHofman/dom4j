/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: CollapsePanel.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */

package org.dom4j.visdom.widget;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CollapsePanel extends JPanel
{
    public CollapsePanel()
    {
        collapsed = false;
        //x = i;
        //y = j;
        //oy = j;
        contentPane = new JPanel();
        //contentPane.setLayout(new FlowLayout(0, 4, 0));

        collapsedPanel = new JPanel();
        //collapsedPanel.setLayout(new FlowLayout(0, 4, 0));
        //collapsedPanel.setInsets( new Insets(0, 16, 16, 0) );

        setLayout( new FlowLayout(FlowLayout.LEFT, 0, 0) );
        add( new ButtonPanel() );
        add( contentPane );

    }



    //-------------------------------------------------------------------------
    // Container delegation
    //-------------------------------------------------------------------------
/*
    public Component add(Component component)
    {
        return contentPane.add(component);
    }

    public Component add(Component component, int index)
    {
        return contentPane.add(component, index);
    }

    public void add(Component component, Object constraints)
    {
        contentPane.add(component, constraints);
    }

    public void add(Component component, Object constraints, int index)
    {
        contentPane.add(component, constraints, index);
    }

    public Component add(String name, Component component)
    {
        return contentPane.add(name, component);
    }

    public void remove(Component component)
    {
        contentPane.remove(component);
    }

    public void remove(int index)
    {
        contentPane.remove(index);
    }

    public void removeAll()
    {
        contentPane.removeAll();
    }
*/


    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------

    public JPanel
    getContentPane()
    {
        return contentPane;
    }

    public boolean
    isCollapsed()
    {
        return collapsed;
    }

    public void
    setCollapsed(boolean collapsed)
    {
        this.collapsed = collapsed;
        if ( collapsed )
        {
            remove( contentPane );
            add( collapsedPanel );

/*
            Dimension size;

            size = getPreferredSize();
            preferredHeight = size.height;
            size.height = collapsedMinimumHeight;
            setPreferredSize( size );

            size = getMinimumSize();
            minimumHeight = size.height;
            size.height = collapsedMinimumHeight;
            setMinimumSize( size );
*/

            /*
            if(separator)
            {
                y = 12;
            }
            else
            {
                y = 10;
            }
            */
        }
        else
        {
            remove( collapsedPanel );
            add( contentPane );

            /*
            if(separator)
            {
                oy = 12;
            }
            else
            {
                oy = 10;
            }
            */
/*
            //y = oy;
            Dimension size;

            size = getPreferredSize();
            size.height = preferredHeight;
            setPreferredSize( size );

            size = getMinimumSize();
            size.height = minimumHeight;
            setMinimumSize( size );
*/
        }
        invalidate();
        validate();
        getParent().validate();
        repaint();
    }



    //-------------------------------------------------------------------------
    // Overridden methods
    //-------------------------------------------------------------------------
/*
    public Dimension preferredSize()
    {
        return new Dimension(x, y);
    }

    public Dimension minimumSize()
    {
        return new Dimension(x, y);
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(collapsed)
        {
            if(i <= oy + 7)
            {
                setCollapsed( false );
                //dispatchEvent(new Event(this, event.when, 1001, event.x, event.y, event.key, event.modifiers, null));
                return true;
            }
        }
        else
        if(i <= 8)
        {
            setCollapsed( true );
            //dispatchEvent(new Event(this, event.when, 1001, event.x, event.y, event.key, event.modifiers, null));
            return true;
        }
        return false;
    }
*/

    public class ButtonPanel extends JPanel
    {
        public ButtonPanel()
        {
            setPreferredSize( new Dimension( oy + 8, oy ) );

            addMouseListener(
                new MouseAdapter()
                {
                    public void mouseReleased(MouseEvent e)
                    {
                        if (collapsed)
                        {
                            setCollapsed( false );
                        }
                        else
                        {
                            setCollapsed( true );
                        }
                    }
                }
            );
        }

        public void
        paint(Graphics g)
        {
            super.paint(g);
            Dimension dimension = getSize();
            if(collapsed)
            {
                g.setColor(Color.lightGray);
                g.draw3DRect(0, 0, oy + 7, dimension.height - 2, true);

                g.setColor(Color.blue.darker());
                g.drawLine(3, 1, 3, 7);
                g.drawLine(4, 2, 4, 6);
                g.drawLine(5, 3, 5, 5);
                g.setColor(Color.white);
                g.drawLine(3, 7, 6, 4);
                g.setColor(Color.lightGray);
                g.fillRect(oy, 2, oy + 8, dimension.height - 2);
                g.setColor(Color.gray);
                g.drawLine(oy + 7, 1, oy, dimension.height - 2);
            }
            else
            {
                g.setColor(Color.lightGray);
                g.draw3DRect(0, 0, 8, dimension.height - 2, true);

                g.setColor(Color.blue.darker());
                g.drawLine(1, 3, 7, 3);
                g.drawLine(2, 4, 6, 4);
                g.drawLine(3, 5, 5, 5);
                g.setColor(Color.white);
                g.drawLine(7, 3, 4, 6);
            }
        }
    }

/*
    public Insets
    insets()
    {
        if(separator)
        {
            return new Insets(0, 16, 2, 0);
        }
        else
        {
            return new Insets(0, 14, 0, 0);
        }
    }
*/
    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private JPanel              contentPane;
    private JPanel              collapsedPanel;
    private boolean             collapsed;
    //private int                 x;
    //private int                 y;
    private int                 oy = 12;
    private int                 minimumHeight;
    private int                 preferredHeight;

    private int                 collapsedMinimumHeight = 10;
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
 * $Id: CollapsePanel.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */
