package org.usadellab.trimmomatic;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class TestUtils {

    static void assertTwoFiles(File expected, File actual) throws IOException {
        assertEquals(FileUtils.readFileToString(expected, Charset.defaultCharset()), FileUtils.readFileToString(actual, Charset.defaultCharset()));

    }

    static void assertTwoFiles(String expected, File actual) throws IOException {
        assertTwoFiles(new File(expected), actual);

    }
}
