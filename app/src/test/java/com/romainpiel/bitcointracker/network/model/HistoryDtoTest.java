package com.romainpiel.bitcointracker.network.model;

import com.romainpiel.bitcointracker.network.JacksonConverter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import retrofit.converter.ConversionException;
import retrofit.mime.TypedString;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class HistoryDtoTest {

    @Test
    public void deserializeBean() throws ConversionException, ParseException {
        JacksonConverter jacksonConverter = new JacksonConverter();
        HistoryDto historyDto = (HistoryDto) jacksonConverter.fromBody(new TypedString("{\"bpi\":{\"2015-01-03\":182.5614,\"2015-01-04\":172.7237}}"), HistoryDto.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HistoryDto expectedHistoryDto = new HistoryDto();
        expectedHistoryDto.bpi = new LinkedHashMap<>();
        expectedHistoryDto.bpi.put(dateFormat.parse("2015-01-03"), 182.5614);
        expectedHistoryDto.bpi.put(dateFormat.parse("2015-01-04"), 172.7237);

        assertThat(historyDto.bpi).isEqualTo(expectedHistoryDto.bpi);
    }
}
