/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: TypedFile.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */

/*
 * HACKED VERSION OF SWING PREVIEW
 *
 */
package org.dom4j.visdom.util.file;

import javax.swing.*;

import java.io.File;
import javax.swing.*;

/**
 * Typed File Objects, returned by FileChooserModel
 * <p>
 * Warning: serialized objects of this class will not be compatible with
 * future swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between Swing1.0 applications.  It will
 * not be possible to load serialized Swing1.0 objects with future releases
 * of Swing.  The JDK1.2 release of Swing will be the compatibility
 * baseline for the serialized form of Swing objects.
 *
 * @version 1.8 02/02/98
 * @author Ray Ryan
 */
public class TypedFile extends File {
    private FileType type = null;

    /**
     * Package constructor, used only by FileChooserModel
     */
    TypedFile(String path) {
        this(path, (FileType)null);
    }
        
    /**
     * Package constructor, used only by FileChooserModel.
     */
    TypedFile(String path, String name) {
        this(path, name, null);
    }

    /**
     * Return a TypedFile of <code>type</code> for
     * <code>path</code>. If you need a TypedFile and you don't know
     * the type, use <code>FileChooserModel.getType</code>.
     */
    public TypedFile(String path, FileType type) {
        super(path);
        this.setType(type);
    }

    /**
     * Return a TypedFile of <code>type</code> for
     * <code>path</code> and <code>name</code>. If you need a
     * TypedFile and you don't know the type, use
     * <code>FileChooserModel.getType</code>. 
     */
    public TypedFile(String path, String name, FileType type) {
        super(path, name);
        if (type != null) {
            this.setType(type);
        }
    }

    /**
     * Guaranteed not to return null. RuntimeException thrown if this
     * is called before a type has been set.
     */
    public FileType getType() {
        if (type == null) {
            throw new RuntimeException("Asked for type before it was set");
        }
        return type;
    }

    /**
     * Must be called once (and only once) before getType is called.
     * RuntimeException thrown if this is violated.
     */
    public void setType(FileType type) {
        if (this.type != null) {
            throw new RuntimeException("Tried to set type twice. 1) "
                                       + this.type + " 2) " + type);
        }
        this.type = type;
    }

    /**
     * May return null, in which case UI must provide a default icon
     */
    public Icon getIcon() {
        return this.getType().getIcon();
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     */
    public String toString() {
        return super.toString() + " type: " + type;
    }

} // End class TypedFile





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
 * $Id: TypedFile.java,v 1.1 2001/05/22 08:13:06 jstrachan Exp $
 */
