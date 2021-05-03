package com.geekhive.foodeydeliveryboy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.adapters.CakeOrderListAdapter;
import com.geekhive.foodeydeliveryboy.beans.cakeorderHistory.CakeOrderHistory;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;

public class CakeOrderFragment extends Fragment implements OnResponseListner {

    View v;
    RecyclerView orderRecyclerView;
    ConnectionDetector mDetector;

    public CakeOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.cake_order_fragment, container, false);
        initialiseView(v, this.getActivity());

        setHasOptionsMenu(false);


        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        orderRecyclerView = v.findViewById(R.id.orderRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        orderRecyclerView.setLayoutManager(linearLayoutManager);

        CallService();

    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {

            if (!Prefs.getUserId(getActivity()).isEmpty() || !Prefs.getUserId(getActivity()).equals("")){
                new WebServices(this).OrderCakeHistory(WebServices.Foodey_Services,
                        WebServices.ApiType.orderHistory, Prefs.getUserId(getActivity()));
            } else {

                SnackBar.makeText(getActivity(), (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
            }

            return;
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.orderHistory) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                CakeOrderHistory orderHistory = (CakeOrderHistory) response;
                if (orderHistory != null){
                    if (orderHistory.getOrderHistory() != null){
                        CakeOrderListAdapter customAdapter = new CakeOrderListAdapter(getActivity(), orderHistory);
                        orderRecyclerView.setAdapter(customAdapter);
                    } else {
                        SnackBar.makeText(getActivity(), "No record found", SnackBar.LENGTH_SHORT).show();
                    }
                } else {
                    SnackBar.makeText(getActivity(), "No record found", SnackBar.LENGTH_SHORT).show();
                }


            }
        }

    }
}
