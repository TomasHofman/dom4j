/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: NodeFactory.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */

/*
 * NodeFactory.java
 *
 * Created on 18. april 2001, 21:03
 */

package org.dom4j.swing;

import java.util.*;

import org.dom4j.*;
import org.dom4j.swing.*;
/**
 *
 * @author  Pipo
 * @version 
 */
public class NodeFactory extends Object {

  public static org.dom4j.swing.Node createNode(org.dom4j.Node p_node){
      if(p_node instanceof org.dom4j.Text)      return createText     (p_node);
      if(p_node instanceof org.dom4j.Comment)   return createComment  (p_node);
      if(p_node instanceof org.dom4j.CDATA)     return createCDATA    (p_node);
      if(p_node instanceof org.dom4j.Element)   return createElement  (p_node);
      if(p_node instanceof org.dom4j.Document)  return createDocument (p_node);
      if(p_node instanceof org.dom4j.Attribute) return createAttribute(p_node);
      if(p_node instanceof org.dom4j.Entity)    return createEntity   (p_node);
      if(p_node instanceof org.dom4j.DocumentType)          
        return createDocumentType(        p_node);      
      if(p_node instanceof org.dom4j.ProcessingInstruction) 
        return createProcessingInstruction(p_node);
      
      return new Node(p_node.getClass().getName());
    }
    
    private static org.dom4j.swing.Text createText(org.dom4j.Node p_node){
      return new org.dom4j.swing.Text(((org.dom4j.Text)p_node).getText().trim());
    }
    
    private static org.dom4j.swing.Comment createComment(org.dom4j.Node p_node){
      return new org.dom4j.swing.Comment("Comment");
    }
    
    private static org.dom4j.swing.CDATA createCDATA(org.dom4j.Node p_node){
      return new org.dom4j.swing.CDATA("CDATA");
    }
     
    private static org.dom4j.swing.Element createElement(org.dom4j.Node p_node){
      StringBuffer l_buf = new StringBuffer();
      
      l_buf.append("<");
      l_buf.append(((org.dom4j.Element)p_node).getQualifiedName());
      Iterator l_attrIterator = ((org.dom4j.Element)p_node).attributeIterator();
      while(l_attrIterator.hasNext()){
        org.dom4j.Attribute l_attribute = (org.dom4j.Attribute) l_attrIterator.next();
        l_buf.append(" ");
        l_buf.append( l_attribute.getQualifiedName());
        l_buf.append("=\"");
        l_buf.append( l_attribute.getValue());
        l_buf.append("\"");
      }
      l_buf.append(">");
      return new org.dom4j.swing.Element(l_buf.toString());
    }
    
    private static org.dom4j.swing.Document createDocument(org.dom4j.Node p_node){
      org.dom4j.DocumentType l_type = ((org.dom4j.Document) p_node).getDocType();
      return new org.dom4j.swing.Document("<?xml >");
    }
    
    private static org.dom4j.swing.Attribute createAttribute(org.dom4j.Node p_node){
      return new org.dom4j.swing.Attribute("Attribute");
    }
    
    private static org.dom4j.swing.DocumentType createDocumentType(org.dom4j.Node p_node){
      return new org.dom4j.swing.DocumentType("DocumentType");
    }
    
    private static org.dom4j.swing.Entity createEntity(org.dom4j.Node p_node){
      return new org.dom4j.swing.Entity("Entity");
    }
    
    private static org.dom4j.swing.ProcessingInstruction createProcessingInstruction(org.dom4j.Node p_node){
      return new org.dom4j.swing.ProcessingInstruction("Processing Instruction");
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
 * $Id: NodeFactory.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */
