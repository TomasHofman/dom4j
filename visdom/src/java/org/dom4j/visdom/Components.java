/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: Components.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */

/*
 * Components.java
 *
 * Created on 23. april 2001, 00:17
 */

package org.dom4j.visdom;

import java.util.*;
/**
 *
 * @author  Administrator
 * @version
 */
public class Components extends java.applet.Applet {

    public static final String CONFIG_FILE        = "dom4j.configFile";
    public static final String CONFIG_DOC         = "dom4j.configDoc";
    public static final String CONFIG_WINDOW      = "dom4j.configWindow";

    public static final String TOOLBAR_WINDOW     = "dom4j.toolBarWindow";
    public static final String EDITOR_WINDOW      = "dom4j.editorWindow";
    public static final String DOMTREE_WINDOW     = "dom4j.domTreeWindow";
    public static final String FILESYSTEM_WINDOW  = "dom4j.fileSystemWindow";
    public static final String XPATH_WINDOW       = "dom4j.xpathWindow";

    private static HashMap s_components = new HashMap();
    private static HashMap s_lockedKeys = new HashMap();

    public static Object get(Object p_key){
        return s_components.get(p_key);
    }

    public static void put(Object p_key, Object p_value){
        if(!s_lockedKeys.containsKey(p_key)){
            s_components.put(p_key, p_value);
        }
    }

    public static void remove(Object p_key){
        if(!s_lockedKeys.containsKey(p_key)){
            s_components.remove(p_key);
        }
    }

    public static void lockKey(Object p_key){
        s_lockedKeys.put(p_key, p_key);
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
 * $Id: Components.java,v 1.1 2001/05/22 08:12:37 jstrachan Exp $
 */
