/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: PropertyPanel.java,v 1.1 2001/05/22 08:12:39 jstrachan Exp $
 */

package org.dom4j.visdom.bean;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.*;


public class PropertyPanel extends JPanel
{
    public PropertyPanel( Object             bean,
                          PropertyDescriptor propertyDescriptor,
                          PropertyEditor     propertyEditor )
    {
        this.bean = bean;
        this.propertyDescriptor = propertyDescriptor;
        this.propertyEditor = propertyEditor;

        setLayout( new BorderLayout() );
        //Class beanType = propertyDescriptor.getPropertyType();

        originalValue = getPropertyValue();
        setEditorValue( originalValue );

        Component editor = propertyEditor.getCustomEditor();
        if (editor == null)
        {
            String[] tags = propertyEditor.getTags();
            if (tags != null && tags.length > 0)
            {
                editor = createTagsComboBox(tags);
            }
            else
            {
                editor = createTextField();
            }
        }
        add( editor, BorderLayout.CENTER );

        propertyEditor.addPropertyChangeListener( createPropertyChangeListener() );
    }


    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------
    protected Component
    createTagsComboBox(String[] tags)
    {
        JComboBox answer = new JComboBox();
        answer.setLightWeightPopupEnabled(false);

        for ( int i = 0; i < tags.length; i++ )
        {
            String tag = tags[i];
            System.out.println("Adding tag: " + tag + " for property: " + propertyDescriptor.getDisplayName() );
            answer.addItem( tag );
        }
        answer.setSelectedItem( propertyEditor.getAsText() );
        answer.addItemListener(
            new ItemListener()
            {
                public void itemStateChanged(ItemEvent evt)
                {
                    Object[] selected=evt.getItemSelectable().getSelectedObjects();
                    if (selected == null)
                    {
                        setEditorValueAsText(null);
                    }
                    else
                    if (selected.length == 1)
                    {
                        setEditorValueAsText( (String) selected[0] );
                    }
                    else
                    {
                        setEditorValue(selected);
                    }
                }
            }
        );
        answer.setName( propertyDescriptor.getName() );
        return answer;
    }

    protected JTextField
    createTextField()
    {
        final JTextField answer = new JTextField();
        answer.setText( propertyEditor.getAsText() );
        answer.getDocument().addDocumentListener(
            new DocumentListener()
            {
                public void  changedUpdate(DocumentEvent e)
                {
                    setEditorValueAsText( answer, e );
                }

                public void  insertUpdate(DocumentEvent e)
                {
                    setEditorValueAsText( answer, e );
                }

                public void  removeUpdate(DocumentEvent e)
                {
                    setEditorValueAsText( answer, e );
                }

            }
        );
        answer.setName( propertyDescriptor.getName() );
        return answer;
    }



    protected PropertyChangeListener
    createPropertyChangeListener()
    {
        return new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                Object value = e.getNewValue();
                System.out.println( "Setting property: " + propertyDescriptor.getDisplayName() + " to value: " + value );
                setPropertyValue( value );
            }
        };
    }

    protected Object
    getPropertyValue()
    {
        try
        {
            Method readMethod = propertyDescriptor.getReadMethod();
            return readMethod.invoke(bean, null);
        }
        catch (Throwable e)
        {
            System.out.println( "Failed to get value of: " + propertyDescriptor.getDisplayName()
                                + " : " + e );
        }
        return null;
    }

    public void
    setPropertyValue(Object value)
    {
        try
        {
            Method method = propertyDescriptor.getWriteMethod();
            Object[] parameters = { value };
            method.invoke(bean, parameters);
        }
        catch (Throwable e)
        {
            System.out.println( "Failed to set property: " + propertyDescriptor.getDisplayName()
                                + " to value: " + value + " : " + e );
        }
    }

    protected void
    setEditorValue( Object value )
    {
        try
        {
            propertyEditor.setValue(originalValue);
        }
        catch (Throwable e)
        {
            System.out.println( "Failed to set editor for: " + propertyDescriptor.getDisplayName()
                                + " to value: " + value + " : " + e );
        }
    }



    protected void
    setEditorValueAsText(String text)
    {
        try
        {
            propertyEditor.setAsText( text );
        }
        catch( NumberFormatException e )
        {
            if (text.length() == 0)
            {
                propertyEditor.setAsText("0");
            }
            else
            {
                logException(e);
            }
        }
    }

    protected void
    setEditorValueAsText( JTextField textField, DocumentEvent event )
    {
        Document document = event.getDocument();
        try
        {
            String text = document.getText( 0, document.getLength() );
            //String text = textField.getText();
            setEditorValueAsText( text );
        }
        catch (BadLocationException e)
        {
            logException(e);
        }
    }

    protected void
    logException(Exception e)
    {
        System.out.println( e );
        e.printStackTrace();
    }
    //-------------------------------------------------------------------------
    // CRAP------------
    //-------------------------------------------------------------------------

    /*
    public void applyChange(Object target) {
        if (m_propertyChanged //&& !m_originalValue.equals(propertyEditor.getPropertyValue())
                    ) {
            Method method = m_property.getWriteMethod();
            try {
                Object[] parameters=new Object[1];
                parameters[0] = propertyEditor.getPropertyValue();
                method.invoke(target, parameters);
            } catch (InvocationTargetException e) {
                System.err.println("Invocation error: "+e);
                System.err.println("Thrown exception is: "+e.getTargetException());
            } catch (IllegalAccessException e) {
                System.err.println("Illegal access error: "+e);
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt)
    {
        if (m_listener != null)
        {
            m_listener.propertyChange(new PropertyChangeEvent(evt.getSource(),
                    m_property.getName(), evt.getOldValue(), evt.getNewValue()));
        }
        m_propertyChanged = true;
    }

    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        m_listener = l;
    }

    public void removePropertyChangeListener(PropertyChangeListener l)
    {
        if (m_listener == l) {
            m_listener = null;
        }
    }

    private PropertyChangeListener m_listener=null;
    private PropertyDescriptor m_property=null;
    private Object  m_originalValue=null;
    private boolean m_propertyChanged=false;
    private java.awt.Component m_value;
    private java.awt.Label  m_label;
    */



    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    private Object              bean;
    private Object              originalValue;

    private PropertyDescriptor  propertyDescriptor;
    private PropertyEditor      propertyEditor;

    protected static Object[]   NULL_ARGUMENTS = { };

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
 * $Id: PropertyPanel.java,v 1.1 2001/05/22 08:12:39 jstrachan Exp $
 */
