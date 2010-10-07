package com.qmetric.utilities.file;

import org.junit.Test;

import static com.qmetric.utilities.file.FileUtils.textFrom;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA. User: dfarr Date: Jul 28, 2010 Time: 11:21:01 AM To change this template use File | Settings | File Templates.
 */
public class FileUtilsTest
{
    private static final String EXPECTED_FILE_CONTENTS = "some test text";

    @Test
	public void shouldReadTextFromVfsLocation() {
		assertThat( textFrom( "res:test-file.txt" ), equalTo(EXPECTED_FILE_CONTENTS) );
	}
}
