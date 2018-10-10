package org.stockexchange.inout.reader;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IFileReader {

    Stream<String> readLines(Path path) throws Exception;
    List<String> readAllLines(Path path) throws Exception;
}
