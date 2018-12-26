package org.usadellab.trimmomatic.trim;

import org.usadellab.trimmomatic.fastq.FastqRecord;

public class LeadingTrimmer extends AbstractSingleRecordTrimmer {
    private int qual;

    public LeadingTrimmer(String args) {
        qual = Integer.parseInt(args);
    }

    public LeadingTrimmer(int qual) {
        this.qual = qual;
    }


    @Override
    public FastqRecord processRecord(FastqRecord in) {
        int quals[] = in.getQualityAsInteger(true);

        for (int i = 0; i < quals.length; i++) {
            if (quals[i] >= qual)
                return new FastqRecord(in, i, quals.length - i);
        }

        return null;
    }

}
