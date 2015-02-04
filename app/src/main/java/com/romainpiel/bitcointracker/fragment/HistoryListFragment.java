package com.romainpiel.bitcointracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;
import com.romainpiel.bitcointracker.network.BPIServiceClient;
import com.romainpiel.bitcointracker.view.DividerItemDecoration;
import com.romainpiel.bitcointracker.view.adapter.HistoryAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HistoryListFragment extends BaseFragment {

    private static final String STATE_HISTORY_ADAPTER = "state_history_adapter";

    public static final int INITIAL_DELAY = 0;
    public static final int POLLING_INTERVAL = 30;

    @Inject
    BPIServiceClient bpiServiceClient;

    private RecyclerView recyclerView;
    private CompositeSubscription compositeSubscription;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_history_list, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inject(this);
        compositeSubscription = new CompositeSubscription();

        if (savedInstanceState == null) {
            adapter = new HistoryAdapter();
        } else {
            HistoryAdapter.State adapterState = savedInstanceState.getParcelable(STATE_HISTORY_ADAPTER);
            adapter = new HistoryAdapter(adapterState);
        }
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            fetchCurrentPeriodically();
            fetchHistory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_HISTORY_ADAPTER, adapter.getState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    private void fetchCurrentPeriodically() {
        compositeSubscription.add(Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> observer) {
                Schedulers.newThread().createWorker() //
                        .schedulePeriodically(new Action0() {
                            @Override
                            public void call() {
                                fetchCurrent();
                            }
                        }, INITIAL_DELAY, POLLING_INTERVAL, TimeUnit.SECONDS);
            }
        }).subscribe());
    }

    private void fetchCurrent() {
        Subscription subscription = bpiServiceClient.getCurrentUSDPrice()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BPI>() {
                    @Override
                    public void onCompleted() {
                        Log.d("blah", "current complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("blah", "current error " + e.getMessage());
                    }

                    @Override
                    public void onNext(BPI bpi) {
                        Log.d("blah", "current next ");
                        adapter.setCurrent(bpi);
                        adapter.notifyDataSetChanged();
                    }
                });
        compositeSubscription.add(subscription);
    }

    private void fetchHistory() {
        Subscription subscription = bpiServiceClient.getHistory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BPI>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("blah", "history complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("blah", "history error " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<BPI> bpis) {
                        Log.d("blah", String.valueOf(bpis.size()));
                        adapter.setItems(bpis);
                        adapter.notifyDataSetChanged();
                    }
                });
        compositeSubscription.add(subscription);
    }
}
