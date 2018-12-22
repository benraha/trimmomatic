package org.usadellab.trimmomatic;

import org.apache.log4j.Logger;
import org.usadellab.trimmomatic.trim.Trimmer;
import org.usadellab.trimmomatic.trim.TrimmerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Trimmomatic {
    private static final int MAX_AUTO_THREADS_THRESHOLD = 8;
    private static final int MAX_AUTO_THREADS_ALLOC = 4;

    private static final Logger logger = Logger.getLogger(Trimmomatic.class);


    static void showVersion() {
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream("version.properties");

            Properties props = new Properties();
            props.load(is);

            System.out.println(props.getProperty("version"));
        } catch (Exception e) {
            throw new RuntimeException("Unable to determine version", e);
        }
    }

    static int calcAutoThreadCount() {
        int cpus = Runtime.getRuntime().availableProcessors();

        if (cpus <= MAX_AUTO_THREADS_THRESHOLD) {
            if (cpus < MAX_AUTO_THREADS_ALLOC)
                return cpus;

            return MAX_AUTO_THREADS_ALLOC;
        }

        return 1;
    }


    static Trimmer[] createTrimmers(Iterator<String> nonOptionArgsIter) throws IOException {
        TrimmerFactory fac = new TrimmerFactory();

        List<Trimmer> trimmerList = new ArrayList<Trimmer>();
        while (nonOptionArgsIter.hasNext())
            trimmerList.add(fac.makeTrimmer(nonOptionArgsIter.next()));

        Trimmer trimmers[] = trimmerList.toArray(new Trimmer[0]);

        return trimmers;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        boolean showUsage = true;

        if (args.length > 0) {
            String mode = args[0];
            String restOfArgs[] = Arrays.copyOfRange(args, 1, args.length);

            if (mode.equals("PE")) {
                if (TrimmomaticPE.run(restOfArgs))
                    showUsage = false;
            } else if (mode.equals("SE")) {
                if (TrimmomaticSE.run(restOfArgs))
                    showUsage = false;
            } else if (mode.equals("-version")) {
                showVersion();
                showUsage = false;
            }
        }

        if (showUsage) {
            logger.error("Usage: ");
            logger.error("       PE [-version] [-threads <threads>] [-phred33|-phred64] [-trimlog <trimLogFile>] [-summary <statsSummaryFile>] [-quiet] [-validatePairs] [-basein <inputBase> | <inputFile1> <inputFile2>] [-baseout <outputBase> | <outputFile1P> <outputFile1U> <outputFile2P> <outputFile2U>] <trimmer1>...");
            logger.error("   or: ");
            logger.error("       SE [-version] [-threads <threads>] [-phred33|-phred64] [-trimlog <trimLogFile>] [-summary <statsSummaryFile>] [-quiet] <inputFile> <outputFile> <trimmer1>...");
            logger.error("   or: ");
            logger.error("       -version");
            System.exit(1);
        }
    }

}
