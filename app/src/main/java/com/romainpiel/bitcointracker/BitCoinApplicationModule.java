package com.romainpiel.bitcointracker;

import com.romainpiel.bitcointracker.network.BPIService;
import com.romainpiel.bitcointracker.network.BPIServiceClient;
import com.romainpiel.bitcointracker.network.JacksonConverter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        complete = false,
        library = true
)
public class BitCoinApplicationModule {

    @Provides
    @Singleton
    public RestAdapter provideCoinDeskRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint("https://api.coindesk.com")
                .setConverter(new JacksonConverter())
                .build();
    }

    @Provides
    public BPIService provideBPIService(RestAdapter restAdapter) {
        return restAdapter.create(BPIService.class);
    }

    @Provides
    public BPIServiceClient provideBPIServiceClient(BPIService bpiService) {
        return new BPIServiceClient(bpiService);
    }
}
