/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TreeComboBox.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.tree.*;

import java.awt.*;
import java.util.Vector;
import javax.swing.tree.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.*;

public class TreeComboBox extends JComboBox
{
    public TreeComboBox(TreeModel aTreeModel)
    {
        super();
        treeModel = aTreeModel;
        setModel(new TreeToListModel(aTreeModel));
        super.setUI(new TreeComboUI());
        setRenderer(new ListEntryRenderer());
    }

    public void setUI(ComboBoxUI ui)
    {
        /* The ui of this component cannot change */
    }

    public TreeModel getTreeModel()
    {
        return treeModel;
    }

    public TreeToListModel getTreeToListModel()
    {
        return (TreeToListModel)getModel();
    }

    class TreeToListModel extends AbstractListModel
        implements ComboBoxModel, TreeModelListener
    {
        public TreeToListModel(TreeModel aTreeModel)
        {
            source = aTreeModel;
            aTreeModel.addTreeModelListener(this);
            setRenderer(new ListEntryRenderer());
        }

        public void setSelectedItem(Object anObject)
        {
            currentValue = anObject;
            fireContentsChanged(this, -1, -1);
        }

        public Object getSelectedItem()
        {
            return currentValue;
        }

        public int getSize()
        {
            validate();
            return cache.size();
        }

        public Object getElementAt(int index)
        {
            return cache.elementAt(index);
        }

        public void treeNodesChanged(TreeModelEvent e)
        {
            invalid = true;
        }

        public void treeNodesInserted(TreeModelEvent e)
        {
            invalid = true;
        }

        public void treeNodesRemoved(TreeModelEvent e)
        {
            invalid = true;
        }

        public void treeStructureChanged(TreeModelEvent e)
        {
            invalid = true;
        }

        void validate()
        {
            if(invalid)
            {
                cache = new Vector();
                cacheTree(source.getRoot(),0);
                if(cache.size() > 0)
                {
                    // check that currentValue is not in the collection
                    if ( currentValue == null || ! cache.contains( currentValue ) )
                    {
                        currentValue = cache.elementAt(0);
                    }
                }
                invalid = false;
                fireContentsChanged(this, 0, 0);
            }
        }

        void cacheTree(Object anObject,int level)
        {
            if(source.isLeaf(anObject))
                addListEntry(anObject,level,false);
            else
            {
                int c = source.getChildCount(anObject);
                int i;
                Object child;

                addListEntry(anObject,level,true);
                level++;

                for(i=0;i<c;i++) {
                    child = source.getChild(anObject,i);
                    cacheTree(child,level);
                }

                level--;
            }
        }

        void addListEntry(Object anObject,int level,boolean isNode)
        {
            cache.addElement(new ListEntry(anObject,level,isNode));
        }

        TreeModel source;
        boolean invalid = true;
        Object currentValue;
        Vector cache = new Vector();
    }

    class ListEntry
    {
        Object object;
        int    level;
        boolean isNode;

        public ListEntry(Object anObject,int aLevel,boolean isNode)
        {
            object = anObject;
            level = aLevel;
            this.isNode = isNode;
        }

        public Object object()
        {
            return object;
        }

        public int level()
        {
            return level;
        }

        public boolean isNode()
        {
            return isNode;
        }
    }

    class ListEntryRenderer extends JLabel implements ListCellRenderer
    {
        public ListEntryRenderer()
        {
            setOpaque(true);
        }

        public Component getListCellRendererComponent( JList listbox,
            	                                       Object value,
            	                                       int index,
	        	                                       boolean isSelected,
	        	                                       boolean cellHasFocus )
	    {
            ListEntry listEntry = (ListEntry)value;
            if(listEntry != null)
            {
                Border border;
                setText( listEntry.object().toString() );
		        setIcon( listEntry.isNode() ? nodeIcon : leafIcon );
                if(index != -1)
                    border = new EmptyBorder(0, OFFSET * listEntry.level(), 0, 0);
                else
                    border = emptyBorder;
		        setBorder(border);
                if (isSelected)
                {
                    setBackground(selectedCellBackground);
                    setForeground(selectedCellForeground);
                }
                else
                {
                    setBackground(defaultCellBackground);
                    setForeground(defaultCellForeground);
                }
            }
            else
            {
                setText("");
            }
	        return this;
    	}

        Icon leafIcon = IconRepository.getInstance().getTreeLeafIcon();
        Icon nodeIcon = IconRepository.getInstance().getTreeFolderIcon();
    }


    class TreeComboUI extends BasicComboBoxUI
    {
        protected JList createListBox(ComboBoxModel m)
        {
            JList res = new JList(m);
            res.setCellRenderer(new ListEntryRenderer());
            return res;
        }
    }

    private TreeModel treeModel;

    static final int OFFSET = 16;
    static Border emptyBorder = new EmptyBorder(0,0,0,0);
    final static Color selectedCellBackground = new Color(0,0,128);
    final static Color selectedCellForeground = Color.white;
    final static Color defaultCellBackground = Color.white;
    final static Color defaultCellForeground = Color.black;
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
 * $Id: TreeComboBox.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */
