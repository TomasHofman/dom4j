/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: MenuBuilder.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */

/*
 * MenuBuilder.java
 *
 * Created on 3. maj 2001, 21:18
 */

package org.dom4j.visdom;

import org.dom4j.visdom.action.*;

import javax.swing.*;

/**
 *
 * @author  Administrator
 * @version 
 */
public class MenuBuilder extends Object {

    /** Creates new MenuBuilder */
    public MenuBuilder() {
    }
    
    public static JMenuBar buildMenuBar(){
      JMenuBar l_menuBar = new JMenuBar();
      l_menuBar.add(buildFileMenu());
      l_menuBar.add(buildEditMenu());
      l_menuBar.add(buildSearchMenu());
      l_menuBar.add(buildConfigMenu());
      l_menuBar.add(buildWindowMenu());
      return l_menuBar;
}
    
    public static JMenu buildFileMenu(){
      JMenu l_file = new JMenu("File");
      l_file.add(new JMenuItem("Open XML"));
      l_file.add(new JMenuItem("Open Other Format"));
      l_file.add(new JSeparator());
      l_file.add(new JMenuItem("Save"));
      l_file.add(new JMenuItem("Save As..."));
      l_file.add(new JMenuItem("Save As Other Format"));
      l_file.add(new JSeparator());
      BaseAction l_exit = new ExitAction("Exit");
      Actions.put(Actions.EXIT, l_exit);
      l_file.add(new JMenuItem(l_exit));
      return l_file;
    }
    
    public static JMenu buildEditMenu(){
      JMenu l_edit = new JMenu("Edit");
      l_edit.add(new JMenuItem("Copy"));
      l_edit.add(new JMenuItem("Cut"));
      l_edit.add(new JMenuItem("Paste"));
      l_edit.add(new JSeparator());
      l_edit.add(new JMenu("Element Editors..."));
      return l_edit;
    }
    
    public static JMenu buildConfigMenu(){
      JMenu l_config = new JMenu("Config");
      l_config.add(new JMenuItem("FreeDOM configurations"));
      return l_config;
    }
    
    public static JMenu buildWindowMenu(){
      JMenu l_window = new JMenu("Window");
      l_window.add(new JSeparator());
      l_window.add(new JMenuItem("DOM Tree Window"));
      l_window.add(new JMenuItem("File System Window"));
      l_window.add(new JMenuItem("XPath Window"));
      l_window.add(new JMenuItem("Editor Window"));
      return l_window;
    }
    
    public static JMenu buildSearchMenu(){
      JMenu l_search = new JMenu("Search");
      l_search.add(new JMenuItem("Find"));
      l_search.add(new JMenuItem("Find Next"));
      l_search.add(new JMenuItem("Replace"));
      return l_search;
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
 * $Id: MenuBuilder.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */
