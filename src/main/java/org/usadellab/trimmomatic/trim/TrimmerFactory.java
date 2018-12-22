package org.usadellab.trimmomatic.trim;

import org.apache.log4j.Logger;

import java.io.IOException;


public class TrimmerFactory {
    private static final Logger logger = Logger.getLogger(TrimmerFactory.class);

    public TrimmerFactory() {
    }

    public Trimmer makeTrimmer(String desc) throws IOException {
        String trimmerName = desc;
        String args = "";

        int idx = desc.indexOf(':');

        if (idx > 0) {
            trimmerName = desc.substring(0, idx);
            if (idx < desc.length() - 1)
                args = desc.substring(idx + 1);
        }

        if (trimmerName.equals("ILLUMINACLIP"))
            return IlluminaClippingTrimmer.makeIlluminaClippingTrimmer(args);

        if (trimmerName.equals("LEADING"))
            return new LeadingTrimmer(args);

        if (trimmerName.equals("TRAILING"))
            return new TrailingTrimmer(args);

        if (trimmerName.equals("HEADCROP"))
            return new HeadCropTrimmer(args);

        if (trimmerName.equals("TAILCROP"))
            return new TailCropTrimmer(args);

        if (trimmerName.equals("CROP"))
            return new CropTrimmer(args);

        if (trimmerName.equals("SLIDINGWINDOW"))
            return new SlidingWindowTrimmer(args);

        if (trimmerName.equals("MAXINFO"))
            return new MaximumInformationTrimmer(args);

        if (trimmerName.equals("MINLEN"))
            return new MinLenTrimmer(args);

        if (trimmerName.equals("MAXLEN"))
            return new MaxLenTrimmer(args);

        if (trimmerName.equals("AVGQUAL"))
            return new AvgQualTrimmer(args);

        if (trimmerName.equals("BASECOUNT"))
            return new BaseCountTrimmer(args);

        if (trimmerName.equals("TOPHRED33"))
            return new ToPhred33Trimmer(args);

        if (trimmerName.equals("TOPHRED64"))
            return new ToPhred64Trimmer(args);

        throw new RuntimeException("Unknown trimmer: " + trimmerName);
    }
}
