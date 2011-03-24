/*
Copyright (c) 2010 tgerm.com
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.qmetric.utilities.logging;

import com.sun.mail.smtp.SMTPTransport;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.net.SMTPAppender;
import org.apache.log4j.spi.LoggingEvent;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import java.util.Date;
import java.util.Properties;

/**
 * Extension of Log4j {@link org.apache.log4j.net.SMTPAppender} for Gmail support
 *
 * @author abhinav@tgerm.com
 * @see <a href="http://code.google.com/p/log4j-gmail-smtp-appender/">Google Code Project</a> <br/>
 *      <a href="http://www.tgerm.com">My Blog</a>
 */
public class GmailSMTPAppender extends SMTPAppender
{
	/**
	 * Cached session for later use i.e. while sending emails
	 */
	protected Session session;

	public GmailSMTPAppender() {
		super();
	}

	/**
	 * Create mail session.
	 *
	 * @return mail session, may not be null.
	 */
	protected Session createSession() {
		Properties props = new Properties();
		props.put("mail.smtps.host", getSMTPHost());
		props.put("mail.smtps.auth", "true");

		Authenticator auth = null;
		if (getSMTPPassword() != null && getSMTPUsername() != null) {
			auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(getSMTPUsername(),
							getSMTPPassword());
				}
			};
		}
		session = Session.getInstance(props, auth);
		if (getSMTPProtocol() != null) {
			session.setProtocolForAddress("rfc822", getSMTPProtocol());
		}
		if (getSMTPDebug()) {
			session.setDebug(getSMTPDebug());
		}
		return session;
	}

	/**
	 * Send the contents of the cyclic buffer as an e-mail message.
	 */
	protected void sendBuffer() {
		try {
			MimeBodyPart part = new MimeBodyPart();

			StringBuffer sbuf = new StringBuffer();
			String t = layout.getHeader();
			if (t != null)
				sbuf.append(t);
			int len = cb.length();
			for (int i = 0; i < len; i++) {
				LoggingEvent event = cb.get();
				sbuf.append(layout.format(event));
				if (layout.ignoresThrowable()) {
					String[] s = event.getThrowableStrRep();
					if (s != null) {
                        for (String value : s)
                        {
                            sbuf.append(value);
                            sbuf.append(Layout.LINE_SEP);
                        }
					}
				}
			}
			t = layout.getFooter();
			if (t != null)
				sbuf.append(t);
			part.setContent(sbuf.toString(), layout.getContentType());

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(part);
			msg.setContent(mp);

			msg.setSentDate(new Date());
			send(msg);
		} catch (Exception e) {
			LogLog.error("Error occured while sending e-mail notification.", e);
		}
	}

	/**
	 * Pulled email send stuff i.e. Transport.send()/Transport.sendMessage(). So
	 * that on required this logic can be enhanced.
	 *
	 * @param msg
	 *            Email Message
	 * @throws javax.mail.MessagingException
	 */
	protected void send(Message msg) throws MessagingException
    {
		SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
		try {
			t.connect(getSMTPHost(), getSMTPUsername(), getSMTPPassword());
			t.sendMessage(msg, msg.getAllRecipients());
		} finally {
			t.close();
		}
	}
}
