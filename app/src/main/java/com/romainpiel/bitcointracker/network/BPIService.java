package com.romainpiel.bitcointracker.network;

import com.romainpiel.bitcointracker.network.model.PriceDto;
import com.romainpiel.bitcointracker.network.model.HistoryDto;

import retrofit.http.GET;
import rx.Observable;

public interface BPIService {

    static final String BASE_SERVICE = "/v1/bpi";

    @GET(BASE_SERVICE + "/currentprice.json")
    Observable<PriceDto> currentPrice();

    @GET(BASE_SERVICE + "/historical/close.json")
    Observable<HistoryDto> getHistory();
}
