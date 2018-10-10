package org.stockexchange.inout.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Component
public class FileReader implements IFileReader {

    public static final Logger LOG = LoggerFactory.getLogger(FileReader.class);

    public Stream<String> readLines(Path path) throws Exception {
        checkPath(path);
        Stream<String> stream = null;
        try {
            stream = Files.lines(path);
        } catch (IOException e) {
            new RuntimeException(e);
        }
        return stream;
    }

    public List<String> readAllLines(Path path) throws Exception {
        checkPath(path);
        List<String> list = null;
        try {
            list = Files.readAllLines(path);
        } catch (IOException e) {
            new RuntimeException(e);
        }
        return list;
    }

    private void checkPath(Path path) throws Exception {
        if (!Files.exists(path)) {
            Exception e = new Exception("File not found " + path.toAbsolutePath().toString());
            LOG.error("", e);
            throw e;
        }
    }
}
