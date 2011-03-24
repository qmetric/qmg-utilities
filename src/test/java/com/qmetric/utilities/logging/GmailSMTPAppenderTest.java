package com.qmetric.utilities.logging;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.mail.MessagingException;
import javax.mail.Session;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(PowerMockRunner.class) @PrepareForTest(Session.class)
public class GmailSMTPAppenderTest
{
    private GmailSMTPAppender gmailSMTPAppender;

    @Before
    public void init()
    {
        gmailSMTPAppender = new GmailSMTPAppender();
        gmailSMTPAppender.setSMTPHost("localhost");
        gmailSMTPAppender.createSession();
    }

    @Test
    public void shouldCreateSession()
    {
        assertThat(gmailSMTPAppender.session, is(not(equalTo(null))));
    }

    @Test
        @Ignore
    public void shouldSendMessage() throws MessagingException
    {
//        Session spy = PowerMockito.spy(gmailSMTPAppender.session);
//        gmailSMTPAppender.session.setProtocolForAddress("rfc822", "smtps");
//
//         SMTPTransport transport = mock(SMTPTransport.class);
//
//        when(spy.getTransport("smtps")).thenReturn(transport);
//
//        Message message = mock(Message.class);
//        gmailSMTPAppender.send(message);
//
//        verify(transport).connect(anyString(), anyString(), anyString());
//        verify(transport).sendMessage(eq(message), (Address[]) anyObject());
    }
}
