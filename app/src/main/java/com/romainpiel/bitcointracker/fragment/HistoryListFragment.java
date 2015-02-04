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
import com.romainpiel.bitcointracker.view.adapter.HistoryAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HistoryListFragment extends BaseFragment {

    @Inject
    BPIServiceClient bpiServiceClient;

    private CompositeSubscription compositeSubscription;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_history_list, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inject(this);
        compositeSubscription = new CompositeSubscription();

        fetchHistory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    private void fetchHistory() {
        Subscription subscription = bpiServiceClient.getHistory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BPI>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("blah", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("blah", "error " + e.getMessage());
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
