/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: SystemExitManager.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import java.util.Enumeration;
import java.util.Vector;



/** Utility class to allow Runnable objects to be registered that are called
  * before a call to exit() which calls
  * System.exit() after running all of the Runnable objects.
  * <br>
  * This allows a finer grain of control over what clearup code gets called
  * before a program terminates.
  * <br>
  * Any Throwable exceptions thrown during the execution of the pre-exit-Runnables
  * is caught and ignored.
  *
  */
public class SystemExitManager
{
    public static void          exit(int value)
    {
        System.out.println( "SystemExitManager: exit(" + value + ")" );
        runPreExitRunnables();
        System.exit( value );
    }

    /** Registers a pre-system-exit runnable object to be executed
      * before the call to System.exit()
      *
      * @param preExitRunnable is the Runnable object to be executed before
      * the System.exit() call.
      */
    public static void          registerPreExit(Runnable preExitRunnable)
    {
        preExitRunnables.addElement( preExitRunnable );
    }

    //-------------------------------------------------------------------------
    // Implementation methods
    //-------------------------------------------------------------------------

    protected static void       runPreExitRunnables()
    {
        System.out.println( "SystemExitManager: running "
                            + preExitRunnables.size()
                            + "preExitRunnable(s)" );

        for ( Enumeration e = preExitRunnables.elements(); e.hasMoreElements(); )
        {
            try
            {
                Runnable preExit = (Runnable) e.nextElement();
                preExit.run();
            }
            catch (Throwable exn)
            {
                System.out.println("SystemExitManager: Caught exception: " + exn );
                exn.printStackTrace();
            }
        }
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    private static Vector       preExitRunnables = new Vector();
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
 * $Id: SystemExitManager.java,v 1.1 2001/05/22 08:13:01 jstrachan Exp $
 */
