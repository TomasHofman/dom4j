/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TreeChildrenSorter.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */

package org.dom4j.visdom.explorer.util;

import javax.swing.tree.MutableTreeNode;

import java.util.Vector;

public class TreeChildrenSorter
{
    public TreeChildrenSorter( MutableTreeNode treeNode )
    {
        this.treeNode = treeNode;
    }

    public void
    add( MutableTreeNode treeNode )
    {
        children.addElement( treeNode );
    }

    public void
    sort()
    {
        reallocateIndices();

        // n2sort();
        // qsort(0, indices.length-1);
        shuttlesort((int[])indices.clone(), indices, 0, indices.length);
        /* System.out.println("Compares: "+compares); */

        for ( int i = 0; i < indices.length; i++ )
        {
            int index = indices[i];
            treeNode.insert( (MutableTreeNode) children.elementAt( index ), i );
        }
    }


    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected int
    compare(int index1, int index2)
    {
        MutableTreeNode n1 = (MutableTreeNode) children.elementAt( index1 );
        MutableTreeNode n2 = (MutableTreeNode) children.elementAt( index2 );

        /* ####
        String lhs = n1.getUserObject().toString();
        String rhs = n2.getUserObject().toString();
        */
        String lhs = n1.toString();
        String rhs = n2.toString();
        if  ( rhs == lhs )
            return 0;
        else
        if ( lhs == null )
            return -1;
        else
        if ( rhs == null )
            return 1;
        else
            return lhs.compareTo( rhs );
    }

    protected void
    reallocateIndices()
    {
        int count = children.size();

        // Set up a new array of indices with the right number of elements
        // for the new data model.
        indices = new int[count];

        // Initialise with the identity mapping.
        for(int row = 0; row < count; row++)
            indices[row] = row;
    }

    protected void
    n2sort()
    {
        int count = children.size();

        for(int i = 0; i < count; i++) {
            for(int j = i+1; j < count; j++) {
                if (compare(indices[i], indices[j]) == -1) {
                    swap(i, j);
                }
            }
        }
    }

    // This is a home-grown implementation which we have not had time
    // to research - it may perform poorly in some circumstances. It
    // requires twice the space of an in-place algorithm and makes
    // NlogN assigments shuttling the values between the two
    // arrays. The number of compares appears to vary between N-1 and
    // NlogN depending on the initial order but the main reason for
    // using it here is that, unlike qsort, it is stable.
    protected void
    shuttlesort(int from[], int to[], int low, int high)
    {
        if (high - low < 2) {
            return;
        }
        int middle = (low + high)/2;
        shuttlesort(to, from, low, middle);
        shuttlesort(to, from, middle, high);

        int p = low;
        int q = middle;

        /* This is an optional short-cut; at each recursive call,
        check to see if the elements in this subset are already
        ordered.  If so, no further comparisons are needed; the
        sub-array can just be copied.  The array must be copied rather
        than assigned otherwise sister calls in the recursion might
        get out of sinc.  When the number of elements is three they
        are partitioned so that the first set, [low, mid), has one
        element and and the second, [mid, high), has two. We skip the
        optimisation when the number of elements is three or less as
        the first compare in the normal merge will produce the same
        sequence of steps. This optimisation seems to be worthwhile
        for partially ordered lists but some analysis is needed to
        find out how the performance drops to Nlog(N) as the initial
        order diminishes - it may drop very quickly.  */

        if (high - low >= 4 && compare(from[middle-1], from[middle]) <= 0) {
            for (int i = low; i < high; i++) {
                to[i] = from[i];
            }
            return;
        }

        // A normal merge.

        for(int i = low; i < high; i++) {
            if (q >= high || (p < middle && compare(from[p], from[q]) <= 0)) {
                to[i] = from[p++];
            }
            else {
                to[i] = from[q++];
            }
        }
    }

    protected void
    swap(int i, int j)
    {
        int tmp = indices[i];
        indices[i] = indices[j];
        indices[j] = tmp;
    }



    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------
    private MutableTreeNode         treeNode;
    private int[]                   indices;
    private Vector                  children = new Vector();

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
 * $Id: TreeChildrenSorter.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */
