/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: JGridLayout.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import java.awt.*;

public class JGridLayout extends GridLayout
{
    public JGridLayout(int numRows, int numCols)
    {
        this(numRows, numCols, 0, 0);
    }

    public JGridLayout(int numRows, int numCols, int heightGap, int vertGap)
    {
        super(numRows, numCols, heightGap, vertGap);
        row_heights = new int[0];
        col_widths = new int[0];
        rows = numRows;
        cols = numCols;
        hgap = heightGap;
        vgap = vertGap;
    }

    protected void getGridSizes(Container container, boolean flag)
    {
        int numComponents = container.getComponentCount();
        if (numComponents == 0)
            return;
        int r = rows;
        int c = cols;
        if (r > 0)
            c = (numComponents + r - 1) / r;
        else
            r = (numComponents + c - 1) / c;
        row_heights = new int[r];
        col_widths = new int[c];
        for (int i = 0; i < numComponents; i++)
        {
            Component component = container.getComponent(i);
            Dimension dimension = flag ? component.getMinimumSize() : component.getPreferredSize();
            int j2 = i / c;
            if (dimension.height > row_heights[j2])
                row_heights[j2] = dimension.height;
            int k2 = i % c;
            if (dimension.width > col_widths[k2])
                col_widths[k2] = dimension.width;
        }
    }

    final int sum(int an[])
    {
        if (an == null)
            return 0;
        int i = 0;
        for (int j = 0; j < an.length; j++)
            i += an[j];
        return i;
    }

    public Dimension preferredLayoutSize(Container container)
    {
        Insets insets = container.getInsets();
        getGridSizes(container, false);
        return new Dimension( insets.left + insets.right + sum(col_widths) + (col_widths.length + 2) * hgap,
                              insets.top + insets.bottom + sum(row_heights) + (row_heights.length + 2) * vgap );
    }

    public Dimension minimumLayoutSize(Container container)
    {
        Insets insets = container.getInsets();
        getGridSizes(container, true);
        return new Dimension( insets.left + insets.right + sum(col_widths) + (col_widths.length + 2) * hgap,
                              insets.top + insets.bottom + sum(row_heights) + (row_heights.length + 2) * vgap );
    }

    protected void setBounds(int i1, int j1, int k1, Component component, int i2, int j2, int k2, int i3)
    {
        Rectangle r = new Rectangle(i2, j2, k2, i3);
        component.setBounds(r);
    }

    public void layoutContainer(Container container)
    {
        int numComponents = container.getComponentCount();
        if (numComponents == 0)
            return;
        Insets insets = container.getInsets();
        getGridSizes(container, false);
        int r = rows;
        int c = cols;
        if (r > 0)
            c = (numComponents + r - 1) / r;
        else
            r = (numComponents + c - 1) / c;
        Dimension dimension = container.getSize();
        int x = insets.left + hgap;
        int y = 0;
        for (int i = 0; i < c; i++)
        {
            y = insets.top + vgap;
            for (int j = 0; j < r; j++)
            {
                int index = j * c + i;
                if (index < numComponents)
                {
                    int w = Math.max(0, Math.min(col_widths[i], dimension.width - insets.right - x - hgap));
                    int h = Math.max(0, Math.min(row_heights[j], dimension.height - insets.bottom - y - vgap));
                    setBounds(index, j, i, container.getComponent(index), x, y, w, h);
                }
                y += row_heights[j] + vgap;
            }
            x += col_widths[i] + hgap;
        }
    }

    protected int hgap;
    protected int vgap;
    protected int rows;
    protected int cols;
    protected int row_heights[];
    protected int col_widths[];
    public static final int VARIABLE = 0;

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
 * $Id: JGridLayout.java,v 1.1 2001/05/22 08:12:57 jstrachan Exp $
 */
