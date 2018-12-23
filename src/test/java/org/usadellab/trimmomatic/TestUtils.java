package org.usadellab.trimmomatic;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestUtils {

    static void assertTwoFiles(File expected, File actual) throws IOException {
        assertEquals(FileUtils.readFileToString(expected), FileUtils.readFileToString(actual));

    }

    static void assertTwoFiles(String expected, File actual) throws IOException {
        assertTwoFiles(new File(expected), actual);

    }
}
