/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: BeanExplorer.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */

package org.dom4j.visdom.explorer.bean;

import org.dom4j.visdom.explorer.Explorer;
import org.dom4j.visdom.explorer.ExplorerMutator;
import org.dom4j.visdom.explorer.TreeNodeFactory;

import javax.swing.JFrame;
import javax.swing.tree.TreeNode;

public class BeanExplorer
{
    //-------------------------------------------------------------------------
    // Main
    //-------------------------------------------------------------------------
    public static void          main(String args[])
    {
        runOnExplorer(args);
        //runFromProperties(args);
    }

    public static void          runExplorerForObject(final Object bean)
    {
        TreeNodeFactory factory = new TreeNodeFactory()
        {
            public TreeNode
            createTreeNode()
            {
                return BeanTreeNodeFactory.makeTreeNodeForObject( "Object Explorer",
                                                                  bean );
            }
        };
        String args[] = {};
        Explorer.run( args,
                          "BeanExplorer",
                          factory );
    }

    public static void          runOnExplorer(String args[])
    {
        ExplorerMutator mutator =
            new ExplorerMutator()
            {
                public void     mutate(Explorer explorer)
                    throws Exception
                {
                    makeExplorerTree(explorer);
                }
            };

        Explorer.run( args,
                          "BeanExplorer",
                          mutator );
    }

    public static void          runFromProperties(final String args[])
    {
        TreeNodeFactory treeNodeFactory =
            new TreeNodeFactory()
            {
                public TreeNode     createTreeNode() throws Exception
                {
                    return BeanTreeNodeFactory.makeTreeNode( args );
                }
            };

        Explorer.run( args,
                          "BeanExplorer",
                          treeNodeFactory );
    }


    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected static void       makeExplorerTree(Explorer explorer)
        throws Exception
    {
        System.out.println( "Creating explorer tree on a JFrame" );

        TreeNode rootTreeNode
            = BeanTreeNodeFactory.makeTreeNodeForObject( "JFrame",
                                                         new JFrame() );
        /*
        TreeNode rootTreeNode
            = BeanTreeNodeFactory.makeReflectionTreeNodeForObject( "JFrame",
                                                         new JFrame() );
        */
        explorer.setRootNode( rootTreeNode );
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
 * $Id: BeanExplorer.java,v 1.1 2001/05/22 08:12:44 jstrachan Exp $
 */
