package org.usadellab.trimmomatic.fasta;

import org.apache.log4j.Logger;

import java.io.*;

public class FastaParser {
    private static final Logger logger = Logger.getLogger(FastaParser.class);
    private BufferedReader reader;
    private String currentLine;
    private FastaRecord current;


    public FastaParser() {

    }

    public void parseOne() throws IOException {
        current = null;

        if (currentLine == null)
            currentLine = reader.readLine();

        while (currentLine != null && !currentLine.startsWith(">"))
            currentLine = reader.readLine();

        if (currentLine != null && currentLine.startsWith(">")) {
            String fullName = currentLine.substring(1).trim();
            String tokens[] = fullName.split("[\\| ]");
            String name = tokens[0];

            StringBuilder builder = new StringBuilder();

            currentLine = reader.readLine();
            while (currentLine != null && !currentLine.startsWith(">")) {
                if (!currentLine.startsWith(";"))
                    builder.append(currentLine.trim());
                currentLine = reader.readLine();
            }

            current = new FastaRecord(name, builder.toString().trim(), fullName);
        }
    }

    public void parse(String fileName) throws IOException {
        File file = new File(fileName);
//        check if the file is in the file system
        logger.info("Checking for adapter file " + fileName + "on the file system");
        if (file.exists()) {
            reader = new BufferedReader(new FileReader(file), 1000000);
        } else {
//     let's try to read it from the classpath
            logger.info("Checking for adapter file " + fileName + "in the class path");
            InputStream is = FastaParser.class.getResourceAsStream("/adapters/" + fileName);
            if (is != null) {
                reader = new BufferedReader(new InputStreamReader(is));
            } else
                throw new FileNotFoundException("Couldn't find the adapter file " + fileName);
        }
        parseOne();
    }

    public void close() throws IOException {
        reader.close();
    }

    public boolean hasNext() {
        return current != null;
    }

    public FastaRecord next() throws IOException {
        FastaRecord current = this.current;
        parseOne();

        return current;
    }
}
