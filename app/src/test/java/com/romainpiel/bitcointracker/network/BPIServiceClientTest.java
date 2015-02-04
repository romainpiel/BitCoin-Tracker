package com.romainpiel.bitcointracker.network;

import com.romainpiel.bitcointracker.model.BPI;
import com.romainpiel.bitcointracker.network.model.HistoryDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BPIServiceClientTest {

    private static HistoryDto newHistory(int max) {
        HistoryDto historyDto = new HistoryDto();
        LinkedHashMap<String, Double> bpi = new LinkedHashMap<>();
        for (int i = 1; i <= max; i++) {
            bpi.put(String.format("date %d", i), (double) i);
        }
        historyDto.setBpi(bpi);
        return historyDto;
    }

    private static List<BPI> newBPIList(int max) {
        List<BPI> list = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            BPI bpi = new BPI(String.format("date %d", i), (double) i);
            if (i > 1) {
                bpi.setChange(((float) i - (float) (i - 1)) / (float) (i - 1));
            }
            list.add(bpi);
        }
        return list;
    }

    @Test
    public void getHistory_oneResponse() {
        BPIService service = mock(BPIService.class);
        when(service.getHistory()).thenReturn(Observable.from(new HistoryDto[] {
                newHistory(3)
        }));
        BPIServiceClient client = new BPIServiceClient(service);

        TestSubscriber<List<BPI>> subscriber = new TestSubscriber<>();
        client.getHistory().subscribe(subscriber);

        List<List<BPI>> expectedResults = new ArrayList<>();
        expectedResults.add(newBPIList(3));
        subscriber.assertReceivedOnNext(expectedResults);
        subscriber.assertTerminalEvent();
        subscriber.assertNoErrors();
    }
}
