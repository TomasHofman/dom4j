/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TreeModelBuilder.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */

/*
 * TreeModelBuilder.java
 *
 * Created on 18. april 2001, 20:13
 */

package org.dom4j.swing;

import org.dom4j.*;

import javax.swing.tree.*;
import java.util.*;

/**
 *
 * @author  Pipo
 * @version 
 */
public class TreeModelBuilder extends Object {

    
    public static TreeModel build(org.dom4j.Node p_node){
      return new DefaultTreeModel(buildSubTree(p_node));
    }
    
    private static DefaultMutableTreeNode buildSubTree(org.dom4j.Node p_node){
      DefaultMutableTreeNode l_subNode = NodeFactory.createNode(p_node);
      
      if(p_node instanceof org.dom4j.Branch){
        Iterator l_nodeIterator = ((org.dom4j.Branch) p_node).nodeIterator();
        while(l_nodeIterator.hasNext()){
          org.dom4j.Node l_child = (org.dom4j.Node) l_nodeIterator.next();
          if(l_child == null) continue;
          if((l_child instanceof org.dom4j.Text) && ((org.dom4j.Text)l_child).getText().trim().equals("")) continue;
          l_subNode.add(buildSubTree(l_child));
        }
      }
      return l_subNode;
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
 * $Id: TreeModelBuilder.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */
