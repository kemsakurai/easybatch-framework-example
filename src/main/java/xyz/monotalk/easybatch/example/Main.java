package xyz.monotalk.easybatch.example;

import java.beans.IntrospectionException;
import java.io.FileWriter;
import java.io.IOException;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.writer.FileRecordWriter;
import org.easybatch.flatfile.DelimitedRecordMarshaller;
import org.easybatch.tools.reporting.HtmlJobReportFormatter;

/**
 * Main
 *
 * @author Kem
 */
public class Main {

    public static void main(String[] args) throws IOException, IntrospectionException {
        System.out.println(">>>>>>>start echonest-frame-work example");

        // Execute jobs
        JobReport jobReport = JobBuilder.aNewJob()
                .reader(new EchonestReader())
                .mapper(new ArtistCsvRowMapper())
                .marshaller(new DelimitedRecordMarshaller(ArtistCsvRowMapper.Result.class, new String[]{"name", "familiarity", "hotttnesss"}))
                .writer(new FileRecordWriter("./outputs/echonest_result.csv"))
                .call();

        // Output Reports
        HtmlJobReportFormatter htmlJobReportFormatter = new HtmlJobReportFormatter();
        String html = htmlJobReportFormatter.formatReport(jobReport);
        try (FileWriter fw = new FileWriter("./outputs/job_report.html")) {
            fw.write(html);
        }

        System.out.println(">>>>>>>end echonest-frame-work example");
    }
}
