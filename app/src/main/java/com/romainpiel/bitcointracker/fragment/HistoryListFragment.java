package com.romainpiel.bitcointracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;
import com.romainpiel.bitcointracker.network.BPIServiceClient;
import com.romainpiel.bitcointracker.view.DividerItemDecoration;
import com.romainpiel.bitcointracker.view.adapter.HistoryAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
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

    @InjectView(R.id.emptyView)
    public TextView emptyView;

    @InjectView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @InjectViews({R.id.progress, R.id.emptyView, R.id.recyclerView})
    public List<View> triplet;

    private CompositeSubscription compositeSubscription;
    private Subscription pollingWorker;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        ButterKnife.inject(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        return view;
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
        updateRecyclerViewVisibility();

        if (savedInstanceState == null) {
            fetchHistory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_HISTORY_ADAPTER, adapter.getState());
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribePolling();
    }

    @Override
    public void onPause() {
        super.onPause();
        unsubscribePolling();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    private void subscribePolling() {
        pollingWorker = Schedulers.newThread().createWorker()
                .schedulePeriodically(new Action0() {
                    @Override
                    public void call() {
                        fetchCurrent();
                    }
                }, INITIAL_DELAY, POLLING_INTERVAL, TimeUnit.SECONDS);
        compositeSubscription.add(pollingWorker);
    }

    private void unsubscribePolling() {
        compositeSubscription.remove(pollingWorker);
    }

    private void fetchCurrent() {
        Subscription subscription = bpiServiceClient.getCurrentUSDPrice()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BPI>() {
                    @Override
                    public void onCompleted() {
                        updateRecyclerViewVisibility();
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateRecyclerViewVisibility();
                    }

                    @Override
                    public void onNext(BPI bpi) {
                        if (adapter.getItems().size() > 0) {
                            bpi.setChange(adapter.getItems().get(0));
                        }
                        adapter.setCurrent(bpi);
                        adapter.notifyDataSetChanged();
                    }
                });
        compositeSubscription.add(subscription);
    }

    private void fetchHistory() {
        emptyView.setText(null);
        Subscription subscription = bpiServiceClient.getHistory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BPI>>() {
                    @Override
                    public void onCompleted() {
                        updateRecyclerViewVisibility();
                    }

                    @Override
                    public void onError(Throwable e) {
                        emptyView.setText(R.string.request_error);
                        updateRecyclerViewVisibility();
                    }

                    @Override
                    public void onNext(List<BPI> bpis) {
                        if (bpis.size() > 0 && adapter.getCurrent() != null) {
                            adapter.getCurrent().setChange(bpis.get(0));
                        }
                        adapter.setItems(bpis);
                        adapter.notifyDataSetChanged();
                    }
                });
        compositeSubscription.add(subscription);
    }

    private void updateRecyclerViewVisibility() {
        if (adapter.getItemCount() > 0) {
            showOnly(R.id.recyclerView);
        } else {
            if (TextUtils.isEmpty(emptyView.getText())) {
                showOnly(R.id.progress);
            } else {
                showOnly(R.id.emptyView);
            }
        }
    }

    private void showOnly(int viewId) {
        for (View view : triplet) {
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
        }
    }
}
