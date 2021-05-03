package com.geekhive.foodeydeliveryboy.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.activities.NewOrderAlertActivity;
import com.geekhive.foodeydeliveryboy.adapters.HomeListAdapter;
import com.geekhive.foodeydeliveryboy.beans.newOrderList.NewOrderList;
import com.geekhive.foodeydeliveryboy.notifications.MyFirebaseMessagingService;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;

public class HomeFragment extends Fragment implements OnResponseListner {

    View v;
    //BaseActivityInterface setToolBarTitle;
    RecyclerView homeRecyclerView;
    ConnectionDetector mDetector;
    String message;
    //ConnectionDetector mDetector;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment, container, false);
        initialiseView(v, this.getActivity());
        setHasOptionsMenu(false);

        /*setToolBarTitle = (BaseActivityInterface) getActivity();
        setToolBarTitle.setToolBarTitle("Home");*/


        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        homeRecyclerView = v.findViewById(R.id.homeRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(linearLayoutManager);

        CallService();
        //pushNotification();

    }

    private void CallService() {
        String id = Prefs.getUserId(getActivity());
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).NewOrderList(WebServices.Foodey_Services,
                    WebServices.ApiType.newOrderList, Prefs.getUserId(getActivity()), Prefs.getCityId(getActivity()));
        }
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.newOrderList) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                NewOrderList newOrderList = (NewOrderList) response;
                if (!isSucces || newOrderList == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (newOrderList.getOrder() != null) {
                        if (newOrderList.getOrder().getResturant() != null) {
                            if (newOrderList.getOrder().getResturant().size() != 0) {
                                HomeListAdapter homeListAdapter = new HomeListAdapter(getActivity(), newOrderList);
                                homeRecyclerView.setAdapter(homeListAdapter);
                            }
                        }
                    } else {
                        SnackBar.makeText(getActivity(), newOrderList.getMessage(), SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

    /*@Override
    public void onResume() {
        super.onResume();
        pushNotification();
    }*/

//    private void pushNotification() {
//
//        try {
//
//            Intent intent = getActivity().getIntent();
//            message = intent.getExtras().getString("ms");
//            if (!message.equals("")) {
//                Intent intenti = new Intent(getActivity(), NewOrderAlertActivity.class);
//                intenti.putExtra("position", 0);
//                startActivity(intenti);
//
//            } else {
//
//                Log.e("Message", "No Message To Display");
//
//            }
//
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }

}
