/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * This software is open source.
 * See the bottom of this file for the licence.
 *
 * $Id: TestPriority.java,v 1.2 2003/04/07 22:25:12 jstrachan Exp $
 */

package org.dom4j.rule;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.dom4j.DocumentFactory;

/** Tests the priority behaviour of Pattern.
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 1.2 $
  */
public class TestPriority extends TestCase
{
    public TestPriority(String name) {
        super( name );
    }
    
    public static void main(String[] args) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite( TestPriority.class );
    }
    
    public void testNameNode() throws Exception {
        testPriority( "foo", 0 );
    }
    
    public void testQNameNode() throws Exception {
        //testPriority( "foo:bar", 0 );
    }
    
    public void testFilter() throws Exception {
        testPriority( "foo[@id='123']", 0.5 );
    }
    
    public void testURI() throws Exception {
        testPriority( "foo:*", -0.25);
    }
    
    public void testAnyNode() throws Exception {
        testPriority( "*", -0.5 );
    }
    
    protected void testPriority(String expr, double priority) throws Exception {
        System.out.println( "parsing: " + expr );
        
        Pattern pattern = DocumentFactory.getInstance().createPattern( expr );
        double d = pattern.getPriority();
        
        System.out.println( "expr: " + expr + " has priority: " + d );
        System.out.println( "pattern: " + pattern );
        
        assertEquals( "expr: " + expr, new Double(priority), new Double(d) );
    }
}
