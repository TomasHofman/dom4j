/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: DirectoryModelTableModel.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */

package org.dom4j.visdom.file;

import org.dom4j.visdom.util.IconRepository;
import org.dom4j.visdom.util.Log;
import org.dom4j.visdom.util.file.DirectoryModel;
import org.dom4j.visdom.util.file.TypedFile;

import javax.swing.*;
import javax.swing.table.*;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.*;
import javax.swing.*;

public class DirectoryModelTableModel extends AbstractTableModel
{
    public DirectoryModelTableModel()
    {
    }

    public DirectoryModelTableModel( DirectoryModel model )
    {
        setDirectoryModel(model);
    }


    //-------------------------------------------------------------------------
    // Properties API
    //-------------------------------------------------------------------------

    public DirectoryModel       getDirectoryModel()
    {
        return model;
    }

    /**
     * refreshes the contents of the table model with an enumeration of objects
     */

    public void                 setDirectoryModel( DirectoryModel model )
    {
        this.model = model;
        int oldNumber = 0;
        if ( objects != null )
            oldNumber = objects.length;

        files = model.getTypedFiles();
        int newNumber = files.size();

        objects = new Object[ newNumber ][];
        fireRowsChanged( oldNumber );
    }


    //-------------------------------------------------------------------------
    // TableModel interface
    //-------------------------------------------------------------------------
    public Class                getColumnClass(int columnIndex)
    {
        return columnTypes[columnIndex];
    }

    public int                  getColumnCount()
    {
        return NUMBER_OF_COLUMNS;
    }

    public String               getColumnName(int columnIndex)
    {
        return columnNames[columnIndex];
    }

    public int                  getRowCount()
    {
        return files.size();
    }

    public Object               getValueAt(int row, int col)
    {
        if ( objects != null )
        {
            Object[] rowData = objects[row];
            if ( rowData != null )
                return rowData[col];

            Object fileObject = files.elementAt(row);
            if ( fileObject != null && fileObject instanceof File )
            {
                rowData = makeFileData( (File) fileObject );
                objects[row] = rowData;
                return rowData[col];
            }
        }
        return null;
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected Object[]          makeFileData(File aFile)
    {
        Object[]    rowData         = new Object[ NUMBER_OF_COLUMNS ];
        boolean     isDirectory     = aFile.isDirectory();
        String      fileTypeName    = (isDirectory) ? "Folder" : "File";
        String      name            = getFileName( aFile );
        Icon        icon            = null;

        if ( aFile instanceof TypedFile )
        {
            //System.out.println("Found type'd icon!");
            TypedFile aTypedFile = (TypedFile)aFile;
            icon = aTypedFile.getIcon();
            if ( icon == null )
            {
            //    System.out.println("But it is null!!");
            }
            fileTypeName = aTypedFile.getType().getPresentationName();
        }

        if ( icon == null )
        {
            if ( isDirectory )
            {
                icon = getDirectoryIcon( aFile );
            }
            else
            {
                icon = getFileIcon( aFile );
            }
        }

        rowData[0] = name;
        if ( ! isDirectory )
        {
            long length = aFile.length();
            long sizeInK = length / 1024;
            if ( ( length % 1024) != 0 )
                ++sizeInK;

            rowData[1] = new String( sizeInK + " KB  " );
        }
        rowData[2] = fileTypeName;
        rowData[3] = dateFormat.format( new Date( aFile.lastModified() ) );
        return rowData;
    }

    protected String            getFileName(File aFile)
    {
        return aFile.getName();
    }

    protected static Icon       getDirectoryIcon( File aFile )
    {
        return IconRepository.getInstance().getTreeFolderIcon();
    }

    protected static Icon       getFileIcon( File aFile )
    {
        return IconRepository.getInstance().getTreeLeafIcon();
    }


    /** Fires the table changed events after the collection has changed
      * Depending upon the difference between the parameters oldNumber and newNumber
      * there may be a combination update, delete, insert events occurring.
      * (usually update event + either insert or delete)
      *
      * @param oldNumber is the old number of objects in
      *     the collection before the change
      */
    protected void fireRowsChanged( int oldNumber )
    {
        int newNumber = objects.length;

        if ( newNumber >= oldNumber )
        {
            if ( oldNumber > 0 )
            {
                fireTableRowsUpdated( 0, oldNumber-1 );
            }
            if ( newNumber > oldNumber )
            {
                fireTableRowsInserted( oldNumber, newNumber-1 );
            }
        }
        else
        {
            fireTableRowsUpdated( 0, newNumber-1 );
            fireTableRowsDeleted( newNumber, oldNumber-1 );
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    private DirectoryModel          model;
    private Object[][]              objects;

    // cache of files
    private Vector                  files = new Vector();

    //-------------------------------------------------------------------------

    private static DateFormat       dateFormat = DateFormat.getDateTimeInstance();

    private static final int        NUMBER_OF_COLUMNS = 4;
    private static String[]         columnNames =
    {
        "Name", "Size", "Type", "Modified"
    };
    private static Class[]          columnTypes =
    {
        String.class, String.class, String.class, String.class
    };

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
 * $Id: DirectoryModelTableModel.java,v 1.1 2001/05/22 08:12:49 jstrachan Exp $
 */
