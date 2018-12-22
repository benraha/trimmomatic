package org.usadellab.trimmomatic.fastq;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

public class PairingValidator {
    private static final Logger logger = Logger.getLogger(PairingValidator.class);


    private boolean complainedAlready;
    private long offset;

    public PairingValidator() {
        complainedAlready = false;
        offset = 0;
    }


    private boolean validateNames(String name1, String name2) {
        String canon1 = FastqNamePattern.canonicalize(name1);
        String canon2 = FastqNamePattern.canonicalize(name2);

        if (canon1 != null && canon2 != null)
            return canon1.equals(canon2);

        String tokens1[] = name1.split(" ");
        String tokens2[] = name2.split(" ");

        String tok1 = tokens1[0];
        String tok2 = tokens2[0];

        if (tok1.length() != tok2.length())
            return false;

        int len = tok1.length();

        for (int i = 0; i < len; i++) {
            char ch1 = tok1.charAt(i);
            char ch2 = tok2.charAt(i);

            if ((ch1 != ch2) && (ch1 != '1' || ch2 != '2'))
                return false;
        }

        return true;
    }


    public boolean validatePair(FastqRecord rec1, FastqRecord rec2) {
        if (rec1 != null) {
            if (rec2 != null) {
                String name1 = rec1.getName();
                String name2 = rec2.getName();

                if (!validateNames(name1, name2)) {
                    if (!complainedAlready) {
                        complainedAlready = true;
                        logger.warn("WARNING: Pair validation failed at record: " + offset);
                        logger.warn("         Forward read: " + name1);
                        logger.warn("         Reverse read: " + name2);
                    }

                    return false;
                }
            } else {
                if (!complainedAlready) {
                    complainedAlready = true;
                    String name1 = rec1.getName();
                    logger.warn("WARNING: Pair validation failed at record: " + offset);
                    logger.warn("         Forward read: " + name1);
                    logger.warn("         No more reverse reads");
                }
                return false;

            }
        } else if (rec2 != null) {
            if (!complainedAlready) {
                complainedAlready = true;
                String name2 = rec2.getName();
                logger.warn("WARNING: Pair validation failed at record: " + offset);
                logger.warn("         No more forward reads");
                logger.warn("         Reverse read: " + name2);
            }

            return false;
        }

        offset++;
        return true;
    }


    public boolean validatePairs(Collection<FastqRecord> col1, Collection<FastqRecord> col2) {
        Iterator<FastqRecord> iter1 = col1.iterator();
        Iterator<FastqRecord> iter2 = col2.iterator();

        while (iter1.hasNext() || iter2.hasNext()) {
            FastqRecord rec1 = iter1.hasNext() ? iter1.next() : null;
            FastqRecord rec2 = iter2.hasNext() ? iter2.next() : null;

            if (!validatePair(rec1, rec2))
                return false;

        }

        return true;
    }


}
