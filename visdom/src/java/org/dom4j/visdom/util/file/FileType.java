/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: FileType.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */

/*
 * HACKED VERSION OF SWING PREVIEW
 *
 */
package org.dom4j.visdom.util.file;

import javax.swing.*;

import java.util.*;
import java.io.File;
import java.net.*;
import javax.swing.*;
/**
 * @version 1.5 02/02/98
 * @author Ray Ryan
 */
public interface FileType {
    /**
     * @return a human readable name for this type, e.g. "HyperText Markup Language Document (HTML)"
     */
    String getPresentationName();

    /**
     * Return true if it would be appropriate to set this as the
     * type of the given file. Does not necessarily imply
     * that the file exists.
     */
    boolean testFile(File file);

    /**
     * May return null. In this case the UI must provide an icon
     */
    Icon getIcon();

    /**
     * @return true if file choosers should descend into this type.
     *
     * Note that returning false from this is a perfectly acceptable
     * way to tell a FileChooser that a given type of directory is
     * actually a document, and not to be descended.
     */
    boolean isContainer();

    static public final GenericFile SharedGenericFile = new GenericFile();
    public class GenericFile implements FileType {
	protected GenericFile() { }

	public String getPresentationName() { return "File"; }
	public Icon getIcon() { return null; }

	/**
	 * Kind of an expensive test (hits the disk, I think).
	 * If you know already, don't ask
	 */
	public boolean testFile(File file) {
	    return true;
	}

	public boolean isContainer() { return false; }
    }

    static public final Folder SharedFolder = new Folder();
    public class Folder implements FileType {
	protected Folder() { }

	public String getPresentationName() { return "Folder"; }
	public Icon getIcon() { return null; }

	/**
	 * Kind of an expensive test (hits the disk, I think).
	 * If you know already, don't ask. 
	 */
	public boolean testFile(File file) {
	    return file.isDirectory();
	}

	public boolean isContainer() { return true; }
    }

    /**
     * Used more as a rule than as a file type. Its test returns true
     * for any file whose first character is '.'.  Not inteneded to be
     * used as the type of a specific file, rather for use as the
     * special <code>hiddenRule</code> of a
     * <code>FileChooserModel</code>.
     */
    static public final Hidden SharedHidden = new Hidden();
    public class Hidden implements FileType {
	protected Hidden() { }

	public String getPresentationName() { return "Hidden"; }
	public Icon getIcon() { return null; }
	public boolean testFile(File file) {
	    String n = file.getName();
	    return n.length() > 0 && n.charAt(0) == '.';
	}
	public boolean isContainer() { return false; }
    }
	

    static public final Computer SharedComputer = new Computer();
    public class Computer implements FileType {
	protected Computer() { }

	public String getPresentationName() { return "Computer"; }
	public Icon getIcon() { return null; }
	public boolean testFile(File file) { return false; }
	public boolean isContainer() { return true; }
    }

    static public final FloppyDrive SharedFloppyDrive = new FloppyDrive();
    public class FloppyDrive implements FileType {
	protected FloppyDrive() { }

	public String getPresentationName() {
	    return "3" + '\u00BD' + " Inch Floppy Disk";
	    // '\u00BD' is unicode for "vulgar one half mark"
	    // http://www.unicode.org/Unicode.charts/glyphless/U+0080.html
	}
	public Icon getIcon() { return null; }
	public boolean testFile(File file) {
	    String path = file.getPath();
	    if (path.length() == 3 && path.charAt(1) == ':'
		&& path.charAt(2) == '\\') 
	    {
		char c = path.charAt(0); 
		return c == 'A' || c == 'a' || c == 'B' || c == 'b';
	    } else {
		return false;
	    }
	}
	public boolean isContainer() { return true; }
    }

    static public final HardDrive SharedHardDrive = new HardDrive();
    public class HardDrive implements FileType {
	protected HardDrive() { }

	public String getPresentationName() { return "Disk Drive"; }
	public Icon getIcon() { return null; }
	public boolean testFile(File file) {
	    String path = file.getPath();
	    if (path.length() == 3 && path.charAt(1) == ':'
		&& path.charAt(2) == '\\') 
	    {
		char c = path.charAt(0);
		return (c >= 'C' && c <= 'Z') || (c >= 'c' && c <= 'z');
	    } else {
		return false;
	    }
	}
	public boolean isContainer() { return true; }
    }

    public class ExtensionBased implements FileType {
	private String[] extensions;
	private String presentationName;
	private Icon icon;

	public ExtensionBased(String presentationName, String extension,
			      Icon icon) {
	    super();

	    this.presentationName = presentationName;
	    this.extensions = new String[1];
	    this.extensions[0] = extension;
	    this.icon = icon;
	}
	    
	public ExtensionBased(String presentationName, String[] extensions,
			      Icon icon) {
	    super();
	    if (extensions.length < 1) {
		throw new IllegalArgumentException("Empty extensions array");
	    }
	    this.presentationName = presentationName;
	    this.extensions = (String[])extensions.clone();
	    this.icon = icon;
	}
	    
	/**
	 * Return true if the file ends in one of this type's extensions.
	 * Note that this will succeed whether or not file is a directory.
	 * That's a feature.
	 */
	public boolean testFile(File file) {
	    int count = extensions.length;
	    String path = file.getPath();
	    for (int i =0; i < count; i++) {
		String ext = extensions[i];
		if (path.endsWith(ext) 
		    && path.charAt(path.length()-ext.length()) == '.') {
		    return true;
		}
	    }
	    return false;
	}		

	public String getPresentationName() {
	    // PENDING(rjrjr) Better default name
	    return presentationName == null ? extensions[0] : presentationName;
	}

	public Icon getIcon() {
	    return this.icon;
	}

	public void setIcon(Icon icon) {
	    this.icon = icon;
	}

	/**
	 * @return false
	 */
    	public boolean isContainer() {
	    return false;
	}
    }

//     public class MIMEBased implements FileType {
// 	private String mimeType;
// 	private String presentationName;

// 	public MIMEBased(String presentationName,
// 			 String mimeType) {
// 	    super();
// 	    if (mimeType == null || presentationName == null) {
// 		throw new
// 		    IllegalArgumentException("Empty MIME type or description");
// 	    }
// 	}

// 	public String getPresentationName() {
// 	    return presentationName;
// 	}

// 	public String getMimeType() {
// 	    return mimeType;
// 	}

// 	public boolean testFile(File f) {
// 	    try {
// 		return new URL("file://localhost/" + f.getPath()).
// 		    openConnection().getContentType().
// 		    equals(this.getMimeType());
// 		//    Too slow. Why? Should be local operation. 
// 	    } catch (Exception e) {
// 		return false;
// 	    }
// 	}
//     }
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
 * $Id: FileType.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */
