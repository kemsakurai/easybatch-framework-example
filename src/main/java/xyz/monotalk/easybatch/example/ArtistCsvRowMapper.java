/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.monotalk.easybatch.example;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.easybatch.core.mapper.RecordMapper;
import org.easybatch.core.mapper.RecordMappingException;
import org.easybatch.core.record.GenericRecord;
import org.easybatch.core.record.Header;
import org.easybatch.core.record.Record;

/**
 * ArtistCsvRowMapper
 *
 * @author Kem
 */
public class ArtistCsvRowMapper implements RecordMapper<Record<Artist>, Record<ArtistCsvRowMapper.Result>> {

    @Override
    public GenericRecord<Result> processRecord(Record<Artist> record) throws RecordMappingException {
        Result result = null;
        try {
            Artist artist = record.getPayload();
            result = new Result();
            result.name = artist.getName();
            result.familiarity = String.valueOf(artist.getFamiliarity());
            result.hotttnesss = String.valueOf(artist.getHotttnesss());
        } catch (EchoNestException ex) {
            Logger.getLogger(ArtistCsvRowMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        Header header = record.getHeader();
        return new GenericRecord<>(header, result);
    }

    public static class Result {

        private String name;
        private String familiarity;
        private String hotttnesss;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFamiliarity() {
            return familiarity;
        }

        public void setFamiliarity(String familiarity) {
            this.familiarity = familiarity;
        }

        public String getHotttnesss() {
            return hotttnesss;
        }

        public void setHotttnesss(String hotttnesss) {
            this.hotttnesss = hotttnesss;
        }
    }
}
