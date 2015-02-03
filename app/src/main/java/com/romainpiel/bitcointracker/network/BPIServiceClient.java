package com.romainpiel.bitcointracker.network;

import com.romainpiel.bitcointracker.model.BPI;
import com.romainpiel.bitcointracker.network.model.HistoryDto;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

public class BPIServiceClient {

    private BPIService bpiService;

    public BPIServiceClient(BPIService bpiService) {
        this.bpiService = bpiService;
    }

    public Observable<List<BPI>> getHistory() {
        return bpiService.getHistory().flatMap(new Func1<HistoryDto, Observable<List<BPI>>>() {
            @Override
            public Observable<List<BPI>> call(HistoryDto historyDto) {
                return Observable.from(historyDto.getBpi().entrySet())
                        .map(new Func1<Map.Entry<String, Double>, BPI>() {
                            @Override
                            public BPI call(Map.Entry<String, Double> entry) {
                                return new BPI(entry.getKey(), entry.getValue());
                            }
                        })
                        .toList();
            }
        });
    }

}
