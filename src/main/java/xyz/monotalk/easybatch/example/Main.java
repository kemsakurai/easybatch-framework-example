package xyz.monotalk.easybatch.example;

import java.io.IOException;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.writer.FileRecordWriter;

/**
 * Main
 *
 * @author Kem
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("start echonest-frame-work example");

        // Execute jobs
        JobBuilder.aNewJob()
                .reader(new EchonestReader())
                .mapper(new ArtistCsvRowMapper())
                .writer(new FileRecordWriter("echonest_result.csv"))
                .call();

        System.out.println("end echonest-frame-work example");
    }
}
