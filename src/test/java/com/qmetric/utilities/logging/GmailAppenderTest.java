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

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Helps testing {@link GmailSMTPAppender}. Not used Junit to reduce the jar
 * dependencies and download size of project via Google SVN.
 *
 * @author abhinav@tgerm.com
 * @see <a href="http://code.google.com/p/log4j-gmail-smtp-appender/">Google Code Project</a> <br/>
 *      <a href="http://www.tgerm.com">My Blog</a>
 */
public class GmailAppenderTest
{
	private static Logger log = Logger.getLogger(GmailAppenderTest.class);

    @Test
    public void emptyTest()
    {
        //This is to stop compilation errors
        //The main method can be used to find problems

    }

	public static void main(String[] args) {
		// Do a simple debug log
		log.debug("Message");
		// Do an error log to send email
		log.error("Failed to do foo bar at blah blah blah !");
	}
}
