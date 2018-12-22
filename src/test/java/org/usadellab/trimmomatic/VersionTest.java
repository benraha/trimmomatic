package org.usadellab.trimmomatic;

import org.junit.Test;

import java.io.IOException;

public class VersionTest {

    public VersionTest() {

    }

    @Test
    public void checkVersion() throws IOException {
        Trimmomatic.main(new String[]{"-version"});
    }

}
