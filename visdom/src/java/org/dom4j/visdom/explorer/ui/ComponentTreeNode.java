/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: ComponentTreeNode.java,v 1.1 2001/05/22 08:12:47 jstrachan Exp $
 */

package org.dom4j.visdom.explorer.ui;

import org.dom4j.visdom.util.SwingUtils;
import org.dom4j.visdom.bean.PropertySheet;
import org.dom4j.visdom.explorer.TreeModelAdapter;
import org.dom4j.visdom.explorer.ViewableNode;
import org.dom4j.visdom.explorer.util.ObjectTreeNode;
import org.dom4j.visdom.explorer.util.DefaultTreeNodeFactory;
import org.dom4j.visdom.explorer.util.TreeNodeFactory;
import org.dom4j.visdom.util.Log;

import javax.swing.*;
import javax.swing.tree.MutableTreeNode;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.awt.*;
import javax.swing.*;

public class ComponentTreeNode extends ObjectTreeNode implements ViewableNode
{
    public ComponentTreeNode( Component              component,
                              TreeNodeFactory        factory )
    {
        super( getDefaultComponentName(component), component, factory );
    }

    public ComponentTreeNode( String                 name,
                              Component              component,
                              TreeNodeFactory        factory,
                              boolean                newOnlyShowNamedComponents )
    {
        super( name, component, factory );
        onlyShowNamedComponents = newOnlyShowNamedComponents;
    }

    //-------------------------------------------------------------------------
    // ViewableNode interface
    //-------------------------------------------------------------------------

    public Component
    makeComponentView( final TreeModelAdapter treeModelAdapter )
    {
        Component component = getComponent();
        if ( component != null )
        {
            PropertySheet panel = new PropertySheet();
            panel.setBean( component );
            return panel;
        }
        return null;
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------

    public Container
    getContainer()
    {
        Object object = getObject();

        if ( object != null && object instanceof Container )
        {
            return (Container) object;
        }
        return null;
    }

    public Component
    getComponent()
    {
        Object object = getObject();

        if ( object != null && object instanceof Component )
        {
            return (Component) object;
        }
        return null;
    }

    public String
    getComponentName( Component component )
    {
        String name = component.getName();
        if ( name == null && ! isOnlyShowNamedComponents() )
        {
            name = component.getClass().getName();
        }
        return name;
    }

    public static String
    getDefaultComponentName(Component component)
    {
        String name = component.getName();
        if ( name == null )
        {
            name = component.getClass().getName();
        }
        return name;
    }

    public boolean
    isOnlyShowNamedComponents()
    {
        return onlyShowNamedComponents;
    }

    public void
    setOnlyShowNamedComponents(boolean newOnlyShowNamedComponents)
    {
        onlyShowNamedComponents = newOnlyShowNamedComponents;
    }

    //-------------------------------------------------------------------------
    // Overriden methods
    //-------------------------------------------------------------------------
    protected void
    loadChildren()
    {
        Container container = getContainer();
        if ( container != null )
        {
            int pos = 0;

            Component[] components = container.getComponents();
            for ( int i = 0; i < container.getComponentCount(); i++ )
            {
                Component component = components[i];
                String name = getComponentName( component );
                if ( name != null )
                {
                    MutableTreeNode child
                        = new ComponentTreeNode( name,
                                                 component,
                                                 getTreeNodeFactory(),
                                                 isOnlyShowNamedComponents() );
                    insert( child, pos++ );
                }
                else
                {
                    // recurse until we find a named component
                    if ( component instanceof Container )
                    {
                        Container childContainer = (Container) component;
                        List list = new LinkedList();

                        findNamedComponents( childContainer, list );

                        for ( Iterator iter = list.iterator(); iter.hasNext(); )
                        {
                            Component childComponent = (Component) iter.next();
                            if ( ! childContainer.isAncestorOf( childComponent ) )
                            {
                                Log.info( "SHITE!" );
                            }

                            name = getComponentName( childComponent );
                            if ( name != null )
                            {
                                MutableTreeNode child
                                    = new ComponentTreeNode( name,
                                                             childComponent,
                                                             getTreeNodeFactory(),
                                                             isOnlyShowNamedComponents() );
                                insert( child, pos++ );
                            }
                        }
                    }
                }
            }
        }
    }

    /** Recures down the component tree finding all components that are
      * named (but excludes the descendants of any named Containers).
      */
    protected void
    findNamedComponents( Container container, List output )
    {
        Component[] components = container.getComponents();
        for ( int i = 0; i < container.getComponentCount(); i++ )
        {
            Component component = components[i];
            if ( component.getName() != null )
            {
                output.add( component );
            }
            else
            if ( component instanceof Container )
            {
                findNamedComponents( (Container) component, output );
            }
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private boolean             onlyShowNamedComponents = true;
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
 * $Id: ComponentTreeNode.java,v 1.1 2001/05/22 08:12:47 jstrachan Exp $
 */
