/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.monotalk.easybatch.example;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Params;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.reader.RecordReaderClosingException;
import org.easybatch.core.reader.RecordReaderOpeningException;
import org.easybatch.core.reader.RecordReadingException;
import org.easybatch.core.record.GenericRecord;
import org.easybatch.core.record.Header;
import org.easybatch.core.record.Record;

/**
 * EchonestReader
 *
 * @author Kem
 */
public class EchonestReader implements RecordReader {

    private final List<Artist> artists = new ArrayList<>();
    private int index = 0;
    private EchoNestAPI en;
    private final String key = "XXXXXXXXXXXXXXXXX";
    
    public EchonestReader() {
        en = new EchoNestAPI(key);
    }

    public boolean hasNextRecord() {
        return index < artists.size();
    }

    public Long getTotalRecords() {
        return (long) artists.size();
    }

    public void open() throws RecordReaderOpeningException {
        int start = 0;
        while (start % 100 == 0) {
            try {
                Params param = new Params();
                param.add("genre", "hip house");
                param.add("start", start);
                param.add("results", 100);
                param.add("bucket", "familiarity");
                param.add("bucket", "hotttnesss");
                List<Artist> results = en.searchArtists(param);
                start += results.size();
                artists.addAll(results);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(EchonestReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (EchoNestException ex) {
                Logger.getLogger(EchonestReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Record readNextRecord() throws RecordReadingException {
        Artist artist = artists.get(index);
        Header header = new Header((long) index, artist.toString(), new Date());
        index++;
        return new GenericRecord<>(header, artist);
    }

    public String getDataSourceName() {
        return "Echonest result";
    }

    public void close() throws RecordReaderClosingException {
        en = null;
    }
}
