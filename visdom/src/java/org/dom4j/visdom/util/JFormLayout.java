/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: JFormLayout.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import javax.swing.JLabel;

import java.awt.*;
import java.util.Hashtable;

public class JFormLayout extends JGridLayout
{
    Hashtable alignment;
    Hashtable resize_width;
    Hashtable resize_height;
    int default_alignment;

    public JFormLayout()
    {
        this(2, 5, 5);
    }

    public JFormLayout(int numCols, int heightGap, int vertGap)
    {
        super(0, numCols, heightGap, vertGap);
        default_alignment = -999;
    }

    private int get(Hashtable hashtable, Component component, int i)
    {
        Object object = (hashtable != null) ? hashtable.get(String.valueOf(component.hashCode())) : null;
        if (object != null)
            return ((Integer)object).intValue();
        else
            return i;
    }

    private boolean get(Hashtable hashtable, Component component, boolean flag)
    {
        Object object = (hashtable != null) ? hashtable.get(String.valueOf(component.hashCode())) : null;
        if (object != null)
            return ((Boolean)object).booleanValue();
        else
            return flag;
    }

    public int getLabelDefaultAlignment()
    {
        return default_alignment;
    }

    public synchronized void setLabelDefaultAlignment(int i)
    {
        //LabelConverter.checkAlignment(i);
        default_alignment = i;
    }

    public int getLabelVerticalAlignment(Component component)
    {
        return get(alignment, component, 4);
    }

    public void setLabelVerticalAlignment(Component component, int i)
    {
        //LabelConverter.checkAlignment(i);
        if (alignment == null)
            alignment = new Hashtable(5);
        alignment.put(String.valueOf(component.hashCode()), new Integer(i));
    }

    public boolean getResizeWidth(Component component)
    {
        boolean defaultResize = (component instanceof JLabel) ? false : true;
        return get(resize_width, component, defaultResize);
    }

    public void setResizeWidth(Component component, boolean flag)
    {
        if (resize_width == null)
            resize_width = new Hashtable(5);
        resize_width.put(String.valueOf(component.hashCode()), new Boolean(flag));
    }

    public boolean getResizeHeight(Component component)
    {
        boolean defaultResize = true;
        //boolean defaultResize = (component instanceof JLabel) ? false : true;
        return get(resize_height, component, defaultResize);
    }

    public void setResizeHeight(Component component, boolean flag)
    {
        if (resize_height == null)
            resize_height = new Hashtable(5);
        resize_height.put(String.valueOf(component.hashCode()), new Boolean(flag));
    }

    private boolean isLabel(int i)
    {
        if (i % 2 != 0)
            return false;
        else
            return true;
    }

    protected void setBounds(int index, int j1, int k1, Component component1, int x, int y, int w, int h)
    {
        int newWidth = w;
        int newHeight = h;
        if (isLabel(k1) || !getResizeHeight(component1))
        {
            newHeight = component1.getPreferredSize().height;
//####
//            if (component1 != null && component1 instanceof JLabel && default_alignment == -999)
//                ((JLabel)component1).setAlignment(5);
        }
        if (!isLabel(k1))
        {
            if (k1 >= col_widths.length - 1)
            {
                if (getResizeWidth(component1))
                {
                    Container container = component1.getParent();
                    newWidth = container.getSize().width - container.getInsets().right - x - hgap;
                }
                else
                    newWidth = component1.getPreferredSize().width;
            }
            component1.setBounds(new Rectangle( x, y, newWidth, newHeight) );
            return;
        }
        int i4 = h;
        if (index < component1.getParent().getComponentCount() - 1)
        {
            Component component2 = component1.getParent().getComponents()[index + 1];
            if (component2 != null && !getResizeHeight(component2))
                i4 = component2.getPreferredSize().height;
        }
        switch (getLabelVerticalAlignment(component1))
        {
        case 4:
            y += (i4 - newHeight) / 2;
            break;

        case 5:
            y += i4 - newHeight;
            break;
        }
        Rectangle r = new Rectangle(x, y, newWidth, newHeight);
        component1.setBounds(r);
    }
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
 * $Id: JFormLayout.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */
