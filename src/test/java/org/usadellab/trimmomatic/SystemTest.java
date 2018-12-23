package org.usadellab.trimmomatic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class SystemTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    public SystemTest() {

    }


    @Test
    public void checkLeading30() throws IOException {
        File outFile = folder.newFile("fastq_out");

        Trimmomatic.main(new String[]{"SE", "-phred33",
                "src/test/resources/SP1.fq", outFile.getAbsolutePath(), "LEADING:30"});

        TestUtils.assertTwoFiles("src/test/resources/SP1_30_expected.fq", outFile);
    }

    @Test
    public void checkLeadingTrailing20() throws IOException {
        File outFile = folder.newFile("fastq_out");

        Trimmomatic.main(new String[]{"SE", "-phred33",
                "src/test/resources/SP1.fq", outFile.getAbsolutePath(), "LEADING:20", "TRAILING:20"});

        TestUtils.assertTwoFiles("src/test/resources/SP1_20_expected.fq", outFile);
    }

    @Test
    public void checkIlluminaClipper() throws IOException {
        File outFile = folder.newFile("fastq_out");

        Trimmomatic.main(new String[]{"SE", "-phred33",
                "src/test/resources/SP1.fq", outFile.getAbsolutePath(), "ILLUMINACLIP:NexteraPE-PE.fa:5:10:2",});

        TestUtils.assertTwoFiles("src/test/resources/illuminaClippingExpected.fq", outFile);
    }

}
