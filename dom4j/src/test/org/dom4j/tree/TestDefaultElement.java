/*
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestDefaultElement.java,v 1.1 2004/03/12 16:44:21 maartenc Exp $
 */

package org.dom4j.tree;

import junit.framework.*;
import junit.textui.TestRunner;
import org.dom4j.*;

/**
 *
 * @author Maarten Coene
 */
public class TestDefaultElement extends AbstractTestCase {

    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestDefaultElement.class );
    }
    
    public TestDefaultElement(String name) {
        super(name);
    }

    // Test case(s)
    //-------------------------------------------------------------------------
    public void testBug894878() {
        Element foo = DocumentFactory.getInstance().createElement("foo");
        foo.addText("bla").addAttribute("foo", "bar");
        assertEquals("<foo foo=\"bar\">bla</foo>", foo.asXML());
        
        foo = DocumentFactory.getInstance().createElement("foo");
        foo.addAttribute("foo", "bar").addText("bla");
        assertEquals("<foo foo=\"bar\">bla</foo>", foo.asXML());
    }
    
}
