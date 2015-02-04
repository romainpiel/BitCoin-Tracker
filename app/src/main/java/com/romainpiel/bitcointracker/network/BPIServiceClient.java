package com.romainpiel.bitcointracker.network;

import com.romainpiel.bitcointracker.model.BPI;
import com.romainpiel.bitcointracker.network.model.HistoryDto;
import com.romainpiel.bitcointracker.network.model.PriceDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class BPIServiceClient {

    private BPIService bpiService;

    public BPIServiceClient(BPIService bpiService) {
        this.bpiService = bpiService;
    }

    public Observable<BPI> getCurrentUSDPrice() {
        return bpiService.currentPrice().map(new Func1<PriceDto, BPI>() {
            @Override
            public BPI call(PriceDto priceDto) {
                return new BPI(priceDto.getTime().getUpdatedISO(), priceDto.getBpi().get("USD").getRate());
            }
        });
    }

    public Observable<List<BPI>> getHistory() {
        return bpiService.getHistory().flatMap(new Func1<HistoryDto, Observable<List<BPI>>>() {
            @Override
            public Observable<List<BPI>> call(HistoryDto historyDto) {
                return Observable.from(historyDto.getBpi().entrySet())
                        .map(new Func1<Map.Entry<Date, Float>, BPI>() {
                            @Override
                            public BPI call(Map.Entry<Date, Float> entry) {
                                return new BPI(entry.getKey(), entry.getValue());
                            }
                        })
                        .scan(new Func2<BPI, BPI, BPI>() {
                            @Override
                            public BPI call(BPI bpi, BPI bpi2) {
                                if (bpi.getClose() != 0) {
                                    bpi2.setChange((bpi2.getClose() - bpi.getClose()) / bpi.getClose());
                                }
                                return bpi2;
                            }
                        })
                        .filter(new Func1<BPI, Boolean>() {
                            @Override
                            public Boolean call(BPI bpi) {
                                return bpi.getChange() != null;
                            }
                        })
                        .toSortedList(new Func2<BPI, BPI, Integer>() {
                            @Override
                            public Integer call(BPI bpi, BPI bpi2) {
                                // DESC order
                                return - bpi.getDate().compareTo(bpi2.getDate());
                            }
                        });
            }
        });
    }

}
