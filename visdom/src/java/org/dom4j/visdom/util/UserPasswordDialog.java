/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: UserPasswordDialog.java,v 1.1 2001/05/22 08:13:04 jstrachan Exp $
 */

package org.dom4j.visdom.util;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;

/**
 * This class implements a simple username and password dialog
 */
public class UserPasswordDialog extends JDialog implements ActionListener
{
	public UserPasswordDialog( String title )
	{
		super( new JFrame(), title );
		getContentPane().setLayout( new BorderLayout() );

		JPanel userPanel = new JPanel();
		userPanel.setLayout( new BorderLayout() );
		userPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		userPanel.add( "West", userLabel );
		userPanel.add( "Center", userField );

		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout( new BorderLayout() );
		passwordPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		passwordPanel.add( "West", passwordLabel );
		passwordPanel.add( "Center", passwordField );


		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new BorderLayout() );
		buttonPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		buttonPanel.add( "West", okButton );
		buttonPanel.add( "East", cancelButton );

		okButton.addActionListener( this );
		cancelButton.addActionListener( this );

		getContentPane().add( "North", userPanel );
		getContentPane().add( "Center", passwordPanel );
		getContentPane().add( "South", buttonPanel );

		pack();
	}

	public void setUser( String user )
	{
		userField.setText( user );
	}

	public String getUser()
	{
		return userField.getText();
	}

	public void setPassword( String password )
	{
		passwordField.setText( password );
	}

	public String getPassword()
	{
		return new String( passwordField.getPassword() );
	}

    public boolean isOk()
    {
        return ok;
    }


    // ACTION LISTENER /////////////////////////////////////

    public void actionPerformed( ActionEvent event )
    {
        ok = ( event.getSource() == okButton );
        dispose();

    }

	// PRIVATE //////////////////////////////////////////////


	private JLabel userLabel = new JLabel( "User Name  " );
	private JTextField userField = new JTextField();
	private JLabel passwordLabel = new JLabel( "Password  " );
	private JPasswordField passwordField = new JPasswordField();

    private boolean ok;
    private JButton okButton = new JButton( "OK" );
    private JButton cancelButton = new JButton( "CANCEL" );

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
 * $Id: UserPasswordDialog.java,v 1.1 2001/05/22 08:13:04 jstrachan Exp $
 */
