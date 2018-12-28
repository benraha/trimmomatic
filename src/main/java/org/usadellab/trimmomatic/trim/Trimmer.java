package org.usadellab.trimmomatic.trim;

import org.usadellab.trimmomatic.fastq.FastqRecord;

import java.io.Serializable;

public interface Trimmer extends Serializable{
    public FastqRecord[] processRecords(FastqRecord in[]);
}
