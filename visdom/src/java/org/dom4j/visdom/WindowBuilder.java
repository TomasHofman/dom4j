/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: WindowBuilder.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */

/*
 * WindowBuilder.java
 *
 * Created on 3. maj 2001, 21:40
 */

package org.dom4j.visdom;

import javax.swing.*;

import org.dom4j.visdom.file.FileSystemTreeBuilder;

/**
 *
 * @author  Administrator
 * @version 
 */
public class WindowBuilder extends Object {

    /** Creates new WindowBuilder */
    public WindowBuilder() {
    }

    public static JFrame buildDomTreeWindow(){
      JFrame l_frame = new JFrame("DOM Tree");
      JSplitPane l_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
      l_pane.setLeftComponent( new JPanel().add(new JLabel("DOM Tree")));
      l_pane.setRightComponent(new JPanel().add(new JLabel("XPath Panel")));
      l_pane.setDividerLocation(450);
      l_frame.getContentPane().add(l_pane);
      return l_frame;
    }
    
    public static JFrame buildXPathWindow(){
      JFrame l_frame = new JFrame("XPath");
      return l_frame;
    }
    
    public static JFrame buildEditorWindow(){
      JFrame l_frame = new JFrame("Editor");
      return l_frame;
    }
    
    public static JFrame buildFileSystemWindow(){
      JFrame      l_frame           = new JFrame("File Systems");
      JTree       l_fileSystemTree  = FileSystemTreeBuilder.build();

      JScrollPane l_scroll          = new JScrollPane(l_fileSystemTree);
      l_frame.getContentPane().add(l_scroll);
      return l_frame;
    }
    
    public static JFrame buildConfigWindow(){
      JFrame l_frame = new JFrame("Configurations");
      return l_frame;
    }
    
    public static JFrame buildToolBarWindow(){
      JFrame l_frame = new JFrame("FreeDOM 0.1");
      l_frame.setJMenuBar(MenuBuilder.buildMenuBar());
      return l_frame;
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
 * $Id: WindowBuilder.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */
