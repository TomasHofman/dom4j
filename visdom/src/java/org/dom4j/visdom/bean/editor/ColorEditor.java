/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: ColorEditor.java,v 1.1 2001/05/22 08:12:39 jstrachan Exp $
 */

package org.dom4j.visdom.bean.editor;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyEditor;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class ColorEditor implements PropertyEditor
{
    public ColorEditor()
    {
    }

    // PropertyEditor interface
    public Object getValue()
    {
        return getColor();
    }

    public void setValue(Object object)
    {
        setColor( (Color) object );
    }

    public String getAsText()
    {
        Color c = getColor();
        if ( c != null )
        {
            return c.toString();
        }
        return "";
    }

    public void setAsText(String text) throws IllegalArgumentException
    {
        setColor( Color.getColor( text ) );
    }

    public String[] getTags()
    {
        return null;
    }

    /*
    public Dimension preferredSize() {
    // implementation not available
    }

    public boolean keyUp(Event p0, int p1) {
    // implementation not available
    }

    public boolean action(Event p0, Object p1)
    {
    // implementation not available
    }
    */

    public String getJavaInitializationString()
    {
        StringBuffer buffer = new StringBuffer( "new java.awt.Color( " );
        Color c = getColor();
        if ( c != null )
        {
            buffer.append( getColor().getRed() );
            buffer.append( ", " );
            buffer.append( getColor().getGreen() );
            buffer.append( ", " );
            buffer.append( getColor().getBlue() );
        }
        buffer.append( " )" );
        return buffer.toString();
    }

    public boolean isPaintable()
    {
        return true;
    }

    public void paintValue(Graphics graphics, Rectangle rectangle)
    {
        graphics.setColor( getColor() );
        graphics.fill3DRect( rectangle.x, rectangle.y, rectangle.width, rectangle.height, true );
    }

    public Component getCustomEditor()
    {
        return chooser;
    }

    public boolean supportsCustomEditor()
    {
        return true;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChanges.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChanges.removePropertyChangeListener(listener);
    }

    // Implementation methods
    protected Color
    getColor()
    {
        return chooser.getSelectionModel().getSelectedColor();
    }

    protected void
    setColor(Color color)
    {
    /*
        if ( color == null )
        {
            throw new NullPointerException();
        }
        else
    */
        {
            Color oldValue = getColor();
            if ( ! color.equals( oldValue ) )
            {
                chooser.getSelectionModel().setSelectedColor( color );
                propertyChanges.firePropertyChange( "color", oldValue, color );
            }
        }
    }

    private PropertyChangeSupport   propertyChanges = new PropertyChangeSupport(this);
    private JColorChooser           chooser = new JColorChooser();
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
 * $Id: ColorEditor.java,v 1.1 2001/05/22 08:12:39 jstrachan Exp $
 */
