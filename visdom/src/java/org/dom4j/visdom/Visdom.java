/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: Visdom.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */

/*
 * Dom4jGUI.java
 *
 * Created on 11. april 2001, 22:02
 */

package org.dom4j.visdom;

import org.dom4j.swing.TreeModelBuilder;
import org.dom4j.examples.*;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.net.*;

/**
 *
 * @author  Pipo
 * @version
 */
public class Visdom extends JFrame {

    /** Creates new Dom4jGUI */
    public Visdom() {
        initComponents();
    }

    private void exitForm(java.awt.event.WindowEvent p_evt){
        Action l_exit = Actions.get(Actions.EXIT);
        l_exit.actionPerformed(null);

    }

    private void addWindowCloseListener(){
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        }
        );
    }

    private TreeModel createTreeModelFromDocument(){
        return TreeModelBuilder.build(
        ExampleDocumentBuilder.buildDocument());
    }

    private TreeModel createTreeModelFromFile(){
        return TreeModelBuilder.build((org.dom4j.Document) Components.get(Components.CONFIG_DOC));
    }


    private JTree createTree(){
        //JTree l_tree = new JTree(createTreeModelFromDocument());
        JTree l_tree = new JTree(createTreeModelFromFile());
        l_tree.setEditable(true);
        return l_tree;
    }

    private void setSplitPane(){
        JSplitPane l_split = new JSplitPane();
        l_split.setLeftComponent(new JScrollPane(createTree()));
        l_split.setRightComponent(new JLabel("VisDOM v0.01a"));
        getContentPane().add(l_split);
    }

    public void initComponents(){
        addWindowCloseListener();
        setJMenuBar(MenuBuilder.buildMenuBar());
        setSplitPane();
        setSize(800, 600);
    }

    //******************************
    // STATIC INITIALZATION METHODS
    //******************************

    private static void init(String args[]) throws MalformedURLException{
        setConfigFile(args[0]);
        setConfigDoc();
        openToolBarWindow();
        openFileSystemWindow();
        openDomTreeWindow();
        openXPathWindow();
        openEditorWindow();
        createConfigWindow();
    }

    private static void setConfigFile(String p_file){
        Components.put(Components.CONFIG_FILE, p_file);
        Components.lockKey(Components.CONFIG_FILE);
    }

    private static void setConfigDoc() throws MalformedURLException{
        Components.put(Components.CONFIG_DOC, ExampleDocumentBuilder.readDocument(
        (String) Components.get(Components.CONFIG_FILE) ));
    }

    private static void openToolBarWindow(){
        JFrame l_toolBarWindow = WindowBuilder.buildToolBarWindow();
        l_toolBarWindow.pack();
        l_toolBarWindow.setSize(1000, 75);
        l_toolBarWindow.setLocation(0,0);
        l_toolBarWindow.show();
        Components.put(Components.TOOLBAR_WINDOW, l_toolBarWindow);
        Components.lockKey(Components.TOOLBAR_WINDOW);
    }

    private static void openFileSystemWindow(){
        JFrame l_fileSystemWindow = WindowBuilder.buildFileSystemWindow();
        l_fileSystemWindow.pack();
        l_fileSystemWindow.setSize(300, 600);
        l_fileSystemWindow.setLocation(0, 75);
        l_fileSystemWindow.show();
        Components.put(Components.FILESYSTEM_WINDOW, l_fileSystemWindow);
        Components.lockKey(Components.FILESYSTEM_WINDOW);

    }

    private static void openDomTreeWindow(){
        JFrame l_domTreeWindow = WindowBuilder.buildDomTreeWindow();
        l_domTreeWindow.pack();
        l_domTreeWindow.setSize(300, 600);
        l_domTreeWindow.setLocation(300, 75);
        l_domTreeWindow.show();
        Components.put(Components.DOMTREE_WINDOW, l_domTreeWindow);
        Components.lockKey(Components.DOMTREE_WINDOW);

    }

    private static void openXPathWindow(){
        JFrame l_xpathWindow = WindowBuilder.buildXPathWindow();
        l_xpathWindow.pack();
        l_xpathWindow.setSize(300, 200);
        l_xpathWindow.setLocation(300, 675);
        l_xpathWindow.show();
        Components.put(Components.XPATH_WINDOW, l_xpathWindow);
        Components.lockKey(Components.XPATH_WINDOW);

    }

    private static void openEditorWindow(){
        JFrame l_editorWindow = WindowBuilder.buildEditorWindow();
        l_editorWindow.pack();
        l_editorWindow.setSize(400,600);
        l_editorWindow.setLocation(600, 75);
        l_editorWindow.show();
        Components.put(Components.EDITOR_WINDOW, l_editorWindow);
        Components.lockKey(Components.EDITOR_WINDOW);

    }

    private static void createConfigWindow(){
    }
    /**
     * Starts the Dom4jGui. Takes a configuration file path as argument.
     * @param args the command line arguments
     */

    public static void main (String args[]) {
        try{
            if(args.length == 1){
                init(args);
            }
            printWelcomeText();
            //Dom4jGUI l_frame = new Dom4jGUI();
            //l_frame.pack();
            //l_frame.setSize(800,600);
            //l_frame.show();

        }catch(Exception e){
            System.out.println("Error starting FreeDOM: " + e.toString());
        }
    }

    private static void printWelcomeText(){
        System.out.println("*****************");
        System.out.println("  VisDOM   1.00a ");
        System.out.println("*****************");
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
 * $Id: Visdom.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */
