/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TestDatatype2.java,v 1.4 2001/11/02 13:27:03 jstrachan Exp $
 */

package org.dom4j.datatype;

import java.io.StringReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Attribute;

import org.dom4j.io.SAXReader;

/** Test harness for XML Schema Datatypes support
  *
  * @author Yuxin Ruan
  * @version $Revision: 1.4 $
  */
public class TestDatatype2 extends TestCase {
    
    public static void main( String[] args ) {
        TestRunner.run( suite() );
    }
    
    public static Test suite() {
        return new TestSuite(TestDatatype2.class);
    }
    
    public TestDatatype2(String name) {
        super(name);
    }
    
    public void setUp() {
    }
    
    public void tearDown() {
    }
    
    public void testLongAttribute() throws Exception {
        Document doc=getSource();
        Element root=doc.getRootElement();
        Attribute attr=root.attribute("longAttribute");
        Object attrData=attr.getData();
        validateData("testLongAttribute",attrData,new Long(123));
        System.out.println("retrieved attribute "+attrData);
    }
    
    public void testFloatElement() throws Exception {
        Document doc=getSource();
        Element root=doc.getRootElement();
        Element elem=root.element("floatElement");
        Object elemData=elem.getData();
        validateData("testFloatElement",elemData,new Float(1.23));
        System.out.println("retrieved element:"+elemData);
    }
    
    public void testDateElement() throws Exception {
        Document doc=getSource();
        Element root=doc.getRootElement();
        Element elem=root.element("dateElement");
        //Not working for now
        Object elemData=elem.getData();
        validateData("testFloatElement",elemData,getDate());
        System.out.println("retrieved element:"+getDate().getTime());
    }
    
    private void validateData(String testName,Object retrieved,Object expected)
    throws Exception {
        Class retrievedClass=retrieved.getClass();
        Class expectedClass=expected.getClass();
        
        //compare class
        if (!expectedClass.equals(retrievedClass)) {
            String msg="class mismatch in "+testName+
            ":expected "+expectedClass+
            ", retrieved "+retrievedClass;
            throw new Exception(msg);
        }
        
        //compare value
        if (!expected.equals(retrieved)) {
            String msg="value mismatch in "+testName+
            ":expected "+expected+
            ", retrieved "+retrieved;
            throw new Exception(msg);
        }
    }
    
    private Document getSource() throws Exception {
        StringBuffer buffer=new StringBuffer();
        buffer.append("<?xml version='1.0' ?>");
        buffer.append("<test xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'");
        buffer.append("			xsi:noNamespaceSchemaLocation='long.xsd'");
        buffer.append("			longAttribute='123' >");
        buffer.append("		<floatElement>1.23</floatElement>");
        buffer.append("		<dateElement>"+getDateString()+"</dateElement>");
        buffer.append("</test>");
        
        StringReader in=new StringReader(buffer.toString());
        DatatypeDocumentFactory docFactory=new DatatypeDocumentFactory();
        docFactory.loadSchema(getSchema());
        SAXReader parser=new SAXReader(docFactory);
        return parser.read(in);
    }
    
    private Document getSchema() throws Exception {
        StringBuffer buffer=new StringBuffer();
        buffer.append("<?xml version='1.0' encoding='UTF-8'?>");
        buffer.append("<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema'>");
        buffer.append("		<xsd:element name='test'>");
        buffer.append("			<xsd:complexType>");
        buffer.append("				<xsd:sequence>");
        buffer.append("					<xsd:element name='floatElement' type='xsd:float' />");
        buffer.append("					<xsd:element name='dateElement' type='xsd:date' />");
        buffer.append("				</xsd:sequence>");
        buffer.append("				<xsd:attribute name='longAttribute' type='xsd:long' />");
        buffer.append("			</xsd:complexType>");
        buffer.append("		</xsd:element>");
        buffer.append("</xsd:schema>");
        
        StringReader in=new StringReader(buffer.toString());
        SAXReader parser=new SAXReader();
        return parser.read(in);
    }
    
    
    private static String getDateString() {
        //return dateTime in ISO8601 format
        String yyyy=Integer.toString(year);
        String mm=Integer.toString(month);
        String dd=Integer.toString(date);
        return yyyy+"-"+mm+"-"+dd;
    }
    
    private static Calendar getDate() {
        Calendar calendar=new GregorianCalendar();
        calendar.clear();
        calendar.set( Calendar.YEAR, year );
        calendar.set( Calendar.MONTH, month-1 );
        calendar.set( Calendar.DAY_OF_MONTH, date );
        return calendar;
    }
    
    public static final int year=2001;
    public static final int month=10;
    public static final int date=31;
    
}
