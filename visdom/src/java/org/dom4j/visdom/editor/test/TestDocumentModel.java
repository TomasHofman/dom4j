/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestDocumentModel.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */

package org.dom4j.visdom.editor.test;

import org.dom4j.visdom.util.Log;

import javax.swing.*;
import javax.swing.text.*;

import java.io.*;
import javax.swing.text.*;
import javax.swing.*;

public class TestDocumentModel
{
    public static void
    main( String[] args )
    {
        if ( args.length <= 0 )
        {
            System.out.println( "Arguments: fileName" );
            System.out.println( "Where the fileName contains a serialized Document object" );
            return;
        }

        String file = args[0];

        Log.info( "Loading document: " + file );

        Document document = loadDocument( file );
        if ( document == null )
        {
            Log.warning( "No document found!" );
        }
        else
        {
            testDocument( document );
        }
    }


    protected static void
    testDocument(Document document)
    {
        Element[] elements = document.getRootElements();
        for ( int i = 0; i < elements.length; i++ )
        {
            Log.info( "Root Element: " + i );
            dumpElement( elements[i], 0 );
        }
    }

    protected static void
    dumpElement(Element element, int level)
    {
        int numberOfChildren = element.getElementCount();

        String prefix = "";
        for ( int i = 0; i < level; i++ )
        {
            prefix += "    ";
        }

        String text = null;
        try
        {
            Document document = element.getDocument();
            int start = element.getStartOffset();
            int end = element.getEndOffset();
            text = document.getText( start, end - start );
        }
        catch (BadLocationException e)
        {
            text = e.getMessage();
        }

        String display = prefix + "Name: " + element.getName();
        display += " Attributes: " + element.getAttributes();
        if ( element.isLeaf() )
        {
            display += " Text: " + text;
        }
        System.out.println( display );

        for ( int i = 0; i < numberOfChildren; i++ )
        {
            Element childElement = element.getElement( i );
            dumpElement( childElement, level + 1 );
        }
    }


    protected static Document
    loadDocument(String file)
    {
        {
            // create a dummy document
            StyleContext sc = new StyleContext();
            DefaultStyledDocument doc = new DefaultStyledDocument(sc);
            JTextPane dummy = new JTextPane(doc);
        }

        try
        {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream istrm = new ObjectInputStream(fin);
            Document doc = (Document) istrm.readObject();
            return doc;
        }
        catch (IOException io)
        {
            // should put in status panel
            System.err.println("IOException: " + io.getMessage());
        }
        catch (ClassNotFoundException cnf)
        {
            // should put in status panel
            System.err.println("Class not found: " + cnf.getMessage());
        }
        return null;
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
 * $Id: TestDocumentModel.java,v 1.1 2001/05/22 08:12:41 jstrachan Exp $
 */
