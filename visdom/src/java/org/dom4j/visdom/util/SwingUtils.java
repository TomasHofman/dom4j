/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: SwingUtils.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import javax.swing.*;

import java.awt.*;
import javax.swing.*;

/** A Utilities class for Swing
  */
public class SwingUtils extends UIUtils
{
    /** Creates a new JSplitPane, sets the name to a default value
      * and sets the 2 child components to a zero minimum size to allow
      * the split pane to resize itself.
      */
    public static JSplitPane
    createSplitPane( int newOrientation,
                     Component newLeftComponent,
                     Component newRightComponent )
    {
        // allow the splitPane to completely resize!
        setZeroMinimumSize(newLeftComponent);
        setZeroMinimumSize(newRightComponent);

        JSplitPane answer = new JSplitPane( newOrientation,
                                            newLeftComponent,
                                            newRightComponent );
        answer.setName( "SplitPane" );
        answer.setOneTouchExpandable( true );
        return answer;
    }

    /** Sets the component to a minimum size of zero to allow split panes
      * and layout managers from shrinking the components
      */
    public static void
    setZeroMinimumSize(Component component)
    {
        if ( component instanceof JComponent )
        {
            ((JComponent) component).setMinimumSize( ZERO_DIMENSION );
        }
    }

    public static final Dimension ZERO_DIMENSION = new Dimension( 0, 0 );
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
 * $Id: SwingUtils.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */
