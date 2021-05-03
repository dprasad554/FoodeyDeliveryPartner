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
import com.geekhive.foodeydeliveryboy.adapters.GroceryHomeListAdapter;
import com.geekhive.foodeydeliveryboy.adapters.HomeListAdapter;
import com.geekhive.foodeydeliveryboy.beans.newOrderList.NewOrderList;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;

public class GroceryFragment extends Fragment implements OnResponseListner {

    View v;
    //BaseActivityInterface setToolBarTitle;
    RecyclerView groceryRecyclerView;
    ConnectionDetector mDetector;
    String message;
    //ConnectionDetector mDetector;

    public GroceryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.grocery_fragment, container, false);
        initialiseView(v, this.getActivity());
        setHasOptionsMenu(false);

        /*setToolBarTitle = (BaseActivityInterface) getActivity();
        setToolBarTitle.setToolBarTitle("Home");*/


        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        groceryRecyclerView = v.findViewById(R.id.groceryRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        groceryRecyclerView.setLayoutManager(linearLayoutManager);

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
                        if (newOrderList.getOrder().getGrocery() != null){
                            if(newOrderList.getOrder().getGrocery().size() != 0){
                                GroceryHomeListAdapter homeListAdapter = new GroceryHomeListAdapter(getActivity(), newOrderList);
                                groceryRecyclerView.setAdapter(homeListAdapter);
                            }
                        }


                    } else {
                        SnackBar.makeText(getActivity(), newOrderList.getMessage(), SnackBar.LENGTH_SHORT).show();
                    }

                }
            }
        }

    }

    private void pushNotification() {

        try {

            Intent intent = getActivity().getIntent();
            message = intent.getExtras().getString("ms");
            if (!message.equals("")) {

                new AlertDialog.Builder(getActivity())
                        .setTitle(message)
                        .setMessage(message)
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher)
                        .show();

            } else {

                Log.e("Message", "No Message To Display");

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
