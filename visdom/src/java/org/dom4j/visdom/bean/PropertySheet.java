/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: PropertySheet.java,v 1.1 2001/05/22 08:12:39 jstrachan Exp $
 */

package org.dom4j.visdom.bean;

import org.dom4j.visdom.util.JFormLayout;
import org.dom4j.visdom.util.Log;

import javax.swing.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;
import javax.swing.*;
//import java.util.*;


public class PropertySheet extends JPanel
{
    public PropertySheet()
    {
        setLayout( new BorderLayout() );

        JScrollPane scrollPane
            = new JScrollPane( gridPanel,
                               ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                               ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        add( scrollPane, BorderLayout.CENTER );
        //add( gridPanel, BorderLayout.CENTER );

		//gridPanel.setLayout(gridBagLayout);
        gridPanel.setLayout( new JFormLayout() );

        labelBagConstraints.insets = insets;
        fieldBagConstraints.insets = insets;

        labelBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        fieldBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        labelBagConstraints.fill = GridBagConstraints.BOTH;
        fieldBagConstraints.fill = GridBagConstraints.BOTH;

        // #### HACKSVILLE ####
        //registerDefaultEditors();
    }

    //-------------------------------------------------------------------------
    // Properties
    //-------------------------------------------------------------------------
    public Object
    getBean()
    {
        return bean;
    }

    public void
    setBean(Object bean)
    {
        this.bean = bean;

        editorToInfoMap.clear();
        infoList.clear();
        try
        {
            BeanInfo beanInfo = Introspector.getBeanInfo( bean.getClass() );
            propertyDescriptors = beanInfo.getPropertyDescriptors();

            if ( propertyDescriptors != null )
            {
                for ( int i = 0; i < propertyDescriptors.length; i++ )
                {
                    PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                    if ( propertyDescriptor != null )
                    {
                        addProperty( bean, propertyDescriptor );
                    }
                }
            }
        }
        catch ( IntrospectionException e )
        {
            Log.exception( this, e );
        }
    }


    //-------------------------------------------------------------------------
    // Implementation classes
    //-------------------------------------------------------------------------
    protected static class PropertyInfo
    {
	    public String               name;
        public PropertyDescriptor   descriptor;
        public PropertyEditor       editor;
        public Object               value;
        public Component            view;
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected void
    addProperty( Object bean, PropertyDescriptor propertyDescriptor )
    {
        PropertyInfo info = new PropertyInfo();
	    info.descriptor = propertyDescriptor;
	    info.name = propertyDescriptor.getDisplayName();

	    Class type = propertyDescriptor.getPropertyType();
	    Method getter = propertyDescriptor.getReadMethod();
	    Method setter = propertyDescriptor.getWriteMethod();
        try
        {
		    info.value = getter.invoke(bean, NULL_ARGUMENTS);

	        Class pec = propertyDescriptor.getPropertyEditorClass();
		    if (pec != null)
            {
		        try
                {
			        info.editor = (PropertyEditor) pec.newInstance();
		        }
                catch (Exception ex)
                {
    			    // Drop through.
		        }
		    }
            if (info.editor == null)
            {
                info.editor = PropertyEditorManager.findEditor(type);
            }

	        // If we can't edit this component, skip it.
	        if (info.editor == null)
            {
                Log.warning( "Warning: Can't find public property editor for property \""
                             + info.name + "\".  Skipping." );
                return;
		    }

            // Don't try to set null values:
            if (info.value == null)
            {
                Log.warning( "Warning: Property \"" + info.name + "\" has null initial value." );
                //Log.warning( "Warning: Property \"" + info.name + "\" has null initial value.  Skipping." );
                //return;
            }

            Log.info( "For property: " + info.name
                      + " type: " + type.getName()
                      + " using editor: " + info.editor.getClass().getName() );

            setEditorValue( info.editor, info.value );

            info.editor.addPropertyChangeListener( createPropertyChangeListener() );

            // Now figure out how to display it...
            if ( info.editor.isPaintable() && info.editor.supportsCustomEditor() )
            {
                info.view = new PropertyCanvas(info.editor);
            }
            else
            if (info.editor.getTags() != null)
            {
                info.view = new PropertyComboBox(info.editor);
            }
            else
            if (info.editor.getAsText() != null)
            {
                //String init = info.editor.getAsText();
                info.view = new PropertyTextField(info.editor);
                info.view.setName( propertyDescriptor.getName() );
            }
            else
            {
                Log.warning( "Warning: Property \"" + info.name
                             + "\" has non-displayabale editor.  Skipping." );
                return;
            }


            addPropertyView( info );
	    }
        catch (InvocationTargetException ex)
        {
		    Log.warning( "Skipping property " + info.name + " ; exception on target: " + ex.getTargetException());
		    ex.getTargetException().printStackTrace();
		    return;
	    }
        catch (Exception ex)
        {
    		Log.warning( "Skipping property " + info.name + " ; exception: " + ex);
	    	ex.printStackTrace();
		    return;
	    }
    }






    protected void

    addPropertyView( PropertyInfo info )

    {

        editorToInfoMap.put( info.editor, info );

        infoList.add( info );

        JLabel label = new JLabel( info.name );

        label.setHorizontalAlignment( SwingConstants.LEFT );
        label.setHorizontalTextPosition( SwingConstants.LEFT );

        //gridBagLayout.setConstraints( label, labelBagConstraints );
        //gridBagLayout.setConstraints( info.view, fieldBagConstraints );
        gridPanel.add( label );
        gridPanel.add( info.view );
    }




    /*
    protected void
    addPropertyEditor( Object bean, PropertyDescriptor propertyDescriptor )
    {
        Class beanType = propertyDescriptor.getPropertyType();
        if ( beanType == null )
        {
            System.out.println( "No type for property: " + propertyDescriptor.getDisplayName() );
        }
        else
        {
            PropertyEditor propertyEditor = PropertyEditorManager.findEditor( beanType );
            if ( isEditable( propertyDescriptor, propertyEditor ) )
            {
                if ( propertyEditor == null  )
                {
                    System.out.println( "No property editor for type: " + beanType );
                }
                else
                {
                    JLabel label = new JLabel( propertyDescriptor.getDisplayName() );
                    label.setHorizontalAlignment( SwingConstants.LEFT);
                    label.setHorizontalTextPosition( SwingConstants.LEFT);

                    PropertyPanel propertyPanel = new PropertyPanel( bean,
                                                                     propertyDescriptor,
                                                                     propertyEditor );

                    gridBagLayout.setConstraints( label, labelBagConstraints );
                    gridBagLayout.setConstraints( propertyPanel, fieldBagConstraints );
                    add( label );
                    add( propertyPanel );
                }
            }
        }
    }

    */

    protected PropertyChangeListener
    createPropertyChangeListener()
    {
        return new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                onPropertyChange( e );
            }
        };
    }



    protected void
    onPropertyChange(PropertyChangeEvent evt)
    {
        Log.info( "Property changed!!: " + evt );
        if (evt.getSource() instanceof PropertyEditor)
        {
            PropertyEditor editor = (PropertyEditor) evt.getSource();
            PropertyInfo info = (PropertyInfo) editorToInfoMap.get( editor );
            if ( info == null )
            {
                Log.warning( "No information for editor: " + editor );
            }
            else
            {
                Object value = editor.getValue();
                Method setter = info.descriptor.getWriteMethod();
                try
                {
                    Log.info( "Setting property: " + info.name + " to: " + value );
                    Object args[] = { value };
                    //args[0] = value;
                    setter.invoke(bean, args);

                     // We add the changed property to the targets wrapper
                     // so that we know precisely what bean properties have
                     // changed for the target bean and we're able to
                     // generate initialization statements for only those
                     // modified properties at code generation time.
                     //targetWrapper.getChangedProperties().addElement(properties[i]);

                }
                catch (InvocationTargetException ex)
                {
                    if (ex.getTargetException() instanceof PropertyVetoException)
                    {
                        Log.warning( "Vetoed; reason is: "
                                     + ex.getTargetException().getMessage());
                    }
                    else
                    {
                        Log.error( "InvocationTargetException while updating "
                                   + info.name, ex.getTargetException());
                    }
                }
                catch (Exception ex)
                {
                    Log.error( "Unexpected exception while updating "
                               + info.name, ex );
                }
                // #### don't need to repaint as the next loop will do that!
                info.view.repaint();
            }
        }

        // Now re-read all the properties and update the editors
        // for any other properties that have changed.
        for ( Iterator i = infoList.iterator(); i.hasNext(); )
        {
            PropertyInfo info = (PropertyInfo) i.next();
            Object currentValue = null;
            try
            {
                Method getter = info.descriptor.getReadMethod();
                currentValue = getter.invoke( bean, NULL_ARGUMENTS );
            }
            catch (Exception ex)
            {
            }

            if ( currentValue == info.value
                 || (currentValue != null && currentValue.equals(info.value)) )
            {
                continue;
            }

            // Make sure we have an editor for this property...
            info.value = currentValue;
            if ( info.editor == null )
            {
                continue;
            }
            // The property has changed!  Update the editor.
            info.editor.setValue( currentValue );
            if ( info.view != null )
            {
                info.view.repaint();
            }
        }

        // Make sure the target bean gets repainted.
        if (Beans.isInstanceOf(bean, Component.class))
        {
            ((Component)(Beans.getInstanceOf(bean, Component.class))).repaint();
        }
    }


    protected boolean
    isEditable( PropertyDescriptor  propertyDescriptor,
                PropertyEditor      propertyEditor )
    {
        //#### BETTER WAY?
        return propertyDescriptor.getWriteMethod() != null;
    }

    protected void
    setEditorValue( PropertyEditor editor, Object value )
    {
        try
        {
            editor.setValue(value);
        }
        catch (Exception e)
        {
        }
    }

    protected static void
    registerEditor( Class type, Class editor )
    {
        Log.info ( "Registering PropertyEditor for type: " + type + " editor: " + editor );

        try
        {
            Object o = editor.newInstance();
        }
        catch (Exception e)
        {
            Log.exception(e);
        }

        //PropertyEditorManager.registerEditor( type, null );
        PropertyEditorManager.registerEditor( type, editor );
    }

    //-------------------------------------------------------------------------
    // Static constructor
    //-------------------------------------------------------------------------
    static
    {
        registerEditor( Color.class, org.dom4j.visdom.bean.editor.ColorEditor.class );
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private Object                  bean;
    private PropertyDescriptor[]    propertyDescriptors;
    private HashMap                 editorToInfoMap = new HashMap();
    private LinkedList              infoList = new LinkedList();

    private JPanel                  gridPanel = new JPanel();
	private GridBagLayout           gridBagLayout = new GridBagLayout();
	private GridBagConstraints      labelBagConstraints = new GridBagConstraints();
	private GridBagConstraints      fieldBagConstraints = new GridBagConstraints();
    private Insets                  insets = new Insets( 5, 5, 5, 5 );

    protected static Object[]       NULL_ARGUMENTS = { };
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
 * $Id: PropertySheet.java,v 1.1 2001/05/22 08:12:39 jstrachan Exp $
 */
