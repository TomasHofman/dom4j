/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DefaultTreeCellRenderer.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */

package org.dom4j.visdom.explorer;

import org.dom4j.visdom.util.Log;

import javax.swing.*;
import javax.swing.tree.*;

import java.awt.Component;
import javax.swing.tree.*;
import javax.swing.*;

public class DefaultTreeCellRenderer implements TreeCellRenderer
{
    public DefaultTreeCellRenderer(TreeCellRenderer delegate)
    {
        this.delegate = delegate;
    }

    //-------------------------------------------------------------------------
    // TreeCellRenderer interface
    //-------------------------------------------------------------------------
    public Component
    getTreeCellRendererComponent( JTree     tree,
                                  Object    value,
                                  boolean   selected,
                                  boolean   expanded,
                                  boolean   leaf,
                                  int       row,
                                  boolean   hasFocus )
    {
        Component answer = delegate.getTreeCellRendererComponent( tree,
                                                                  value,
                                                                  selected,
                                                                  expanded,
                                                                  leaf,
                                                                  row,
                                                                  hasFocus );

        IconProducer iconProducer = null;

        if ( value instanceof IconProducerHolder )
        {
            iconProducer = ((IconProducerHolder) value).getIconProducer();
        }
        else
        if ( value instanceof IconProducer )
        {
            iconProducer = (IconProducer) value;
        }
        else
        {
            return answer;
        }

        if ( iconProducer != null )
        {
            if (expanded)
            {
                setIcon( answer, iconProducer.getOpenedIcon() );
            }
            else
            {
                setIcon( answer, iconProducer.getClosedIcon() );
            }
        }
        if ( answer instanceof JComponent )
        {
            JComponent component = (JComponent) answer;
            if ( component.getToolTipText() == null )
            {
                component.setToolTipText( tree.getToolTipText() );
            }
        }
        return answer;
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected void
    setIcon(Component renderComponent, Icon icon)
    {
        if ( delegate instanceof JLabel )
        {
            ((JLabel) delegate).setIcon( icon );
        }
        else
        if ( renderComponent instanceof JLabel )
        {
            ((JLabel) renderComponent).setIcon( icon );
        }
        else
        {
            Log.info( this, "Cannot set the icon in TreeCellRenderer" );
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private TreeCellRenderer    delegate;
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
 * $Id: DefaultTreeCellRenderer.java,v 1.1 2001/05/22 08:12:42 jstrachan Exp $
 */
