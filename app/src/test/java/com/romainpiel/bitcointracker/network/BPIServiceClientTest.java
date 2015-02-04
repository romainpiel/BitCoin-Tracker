package com.romainpiel.bitcointracker.network;

import com.google.common.collect.Lists;
import com.romainpiel.bitcointracker.model.BPI;
import com.romainpiel.bitcointracker.network.model.HistoryDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BPIServiceClientTest {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");

    private static Date newDate(int day) throws ParseException {
        return dateFormat.parse(String.format("2015-02-%d", day));
    }

    private static HistoryDto newHistory(int max) throws ParseException {
        HistoryDto historyDto = new HistoryDto();
        LinkedHashMap<Date, Double> bpi = new LinkedHashMap<>();
        for (int i = 1; i <= max; i++) {
            bpi.put(newDate(i), (double) i);
        }
        historyDto.setBpi(bpi);
        return historyDto;
    }

    @Test
    public void getHistory_oneResponse() throws ParseException {
        BPIService service = mock(BPIService.class);
        when(service.getHistory()).thenReturn(Observable.from(new HistoryDto[] {
                newHistory(3)
        }));
        BPIServiceClient client = new BPIServiceClient(service);

        TestSubscriber<List<BPI>> subscriber = new TestSubscriber<>();
        client.getHistory().subscribe(subscriber);

        List<List<BPI>> expectedResults = new ArrayList<>();
        expectedResults.add(Lists.newArrayList(
                new BPI(newDate(3), 3d, 1f/2f),
                new BPI(newDate(2), 2d, 1f)
        ));
        subscriber.assertReceivedOnNext(expectedResults);
        subscriber.assertTerminalEvent();
        subscriber.assertNoErrors();
    }
}
