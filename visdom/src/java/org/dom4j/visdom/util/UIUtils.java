/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: UIUtils.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import java.util.LinkedList;

import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;

/**
 * A utility class with some useful static methods for working with AWT and Swing
 *
 */

public class UIUtils
{
    /**
     * Returns the frame associated with the given Component
     * #### SHOULD USE JComponent.getTopLevelAncestor
     */
    public static Frame
    getFrame(Component component)
    {
        while ( component != null )
        {
            if ( component instanceof Frame  )
                break;
            component = getParent( component );
        }
        return (Frame)component;
    }

    /** Performs a validate on the root parent component that !isValid()
      *
      */

    public static void
    validateAll(Container component)
    {
	    synchronized (component.getTreeLock())
        {
	        Container c = component;
    	    for ( Container p = getParent( c );
                  p != null && ! p.isValid();
                  p = p.getParent())
            {
		        c = p;
    	    }
    	    c.validate();
    	}
    }


    /** Creates an array of components representing the cointainment hierarchy
      * of the specified component.
      * The first element of the array will be the root Container
      * and the last element will be the component passed as an argument to this method.
      *
      * @param component is the leaf of the hierachy for the required path
      * @returns the component path to the component parameter such that
      * path[0] is the root, and
      * path[path.length - 1] is the component parameter.
      * Also the elements from index 0 to path.length - 2 inclusive will be Containers.
      */

    public static Component[]
    getComponentPath( Component component )
    {
        LinkedList list = new LinkedList();
        for ( Component c = component; c != null; c = getParent(c) )
        {
            list.addFirst( c );
        }
        Component[] array = new Component[ list.size() ];
        list.toArray( array );
        return array;
    }


    /** This method returns the parent of a Component. It is similar to the
      * Component.getParent() method exception it allows for wierd breakages in
      * the containment hierarchy like those introduced by JPopupMenu (if it has no
      * parent it will use the invoker).
      *
      * @param the component for which the parent is required
      * @returns the logical parent component. This may be the invoker for classes
      * like JPopupMenu if the parent is null.
      */
    //public static Component
    public static Container
    getParent( Component component )
    {
        Container answer = component.getParent();
        if ( answer == null && component instanceof JPopupMenu )
        {
            Component invoker = ((JPopupMenu) component).getInvoker();
            if ( invoker instanceof Container )
            {
                answer = (Container) invoker;
            }
            else
            {
                answer = invoker.getParent();
            }
        }
        return answer;
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
 * $Id: UIUtils.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */
