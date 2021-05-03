package com.geekhive.foodeydeliveryboy.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.activities.LoginActivity;
import com.geekhive.foodeydeliveryboy.activities.OrdersActivity;
import com.geekhive.foodeydeliveryboy.activities.RevenueActivity;
import com.geekhive.foodeydeliveryboy.beans.floatcash.FloatingCash;
import com.geekhive.foodeydeliveryboy.beans.wallet.WalletBal;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;

public class ProfileFragment extends Fragment implements OnResponseListner {

    View v;
    //BaseActivityInterface setToolBarTitle;
    RecyclerView orderRecyclerView;
    String title;
    TextView tv_ownerName, tv_ownerDesignation;
    LinearLayout linearLogOut;
    CardView dEarn, nOrders;
    TextView tv_earning;
    TextView tv_wallet;
    TextView tv_float;
    ConnectionDetector mDetector;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.profile_fragment, container, false);

        //Log.e("Value Passed : ", title);
        initialiseView(v, this.getActivity());

        setHasOptionsMenu(false);

        /*setToolBarTitle = (BaseActivityInterface) getActivity();
        setToolBarTitle.setToolBarTitle("Profile");*/


        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        tv_ownerName = v.findViewById(R.id.tv_ownerName);
        tv_earning = v.findViewById(R.id.tv_earning);
        tv_ownerDesignation = v.findViewById(R.id.tv_ownerDesignation);
        linearLogOut = v.findViewById(R.id.linearLogOut);
        dEarn = v.findViewById(R.id.dEarn);
        nOrders = v.findViewById(R.id.nOrders);
        tv_wallet = v.findViewById(R.id.tv_wallet);
        tv_float = v.findViewById(R.id.tv_float);

        dEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RevenueActivity.class));
            }
        });

        nOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrdersActivity.class));
            }
        });

        String name = Prefs.getUserFirstName(getActivity()) + " " + Prefs.getUserLastName(getActivity());
        tv_ownerName.setText(name);
        tv_ownerDesignation.setText(Prefs.getMobileNumber(getActivity()));

        /*if (!Prefs.getBikeNo(getActivity()).equals("") || !Prefs.getBikeNo(getActivity()).isEmpty()){
            tv_bikeNo.setText(Prefs.getBikeNo(getActivity()));
        }*/

        linearLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                Prefs.setUserId(getActivity(), "");
                Prefs.setUserEmail(getActivity(), "");
                Prefs.setUserFirstName(getActivity(), "");
                Prefs.setUserLastName(getActivity(), "");
                Prefs.setMobileNumber(getActivity(), "");
                Prefs.setBikeNo(getActivity(), "");
                Prefs.setCityId(getActivity(), "");
                startActivity(intent);
            }
        });

        CallWalletService();

    }

    private void CallWalletService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).DeliveryWallet(WebServices.Foodey_Services,
                    WebServices.ApiType.wallet, Prefs.getUserId(getActivity()));
        }

    }

    private void CallFloatService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).DeliveryFloat(WebServices.Foodey_Services,
                    WebServices.ApiType.floatcash, Prefs.getUserId(getActivity()));
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.wallet) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                WalletBal walletBal = (WalletBal) response;
                if (!isSucces || walletBal == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (walletBal != null) {
                        if (walletBal.getBalance() != null) {
                            String wb = "Bonus Earned:  ₹ " + walletBal.getBalance().getDelWallet().getAmount();
                            tv_earning.setText(wb);

                            String walB = "Wallet Amount: ₹ " + walletBal.getBalance().getDelWallet().getAmount();
                            tv_wallet.setText(walB);

                            CallFloatService();
                        }
                    } else {
                        //SnackBar.makeText(getActivity(), walletBal.getMessage(), SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        } if (apiType == WebServices.ApiType.floatcash) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                FloatingCash floatingCash = (FloatingCash) response;
                if (!isSucces || floatingCash == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (floatingCash != null){
                        if (floatingCash.getBalance() != null){
                            String wb = "Floating Cash: ₹ " + floatingCash.getBalance().getFloatCash().getAmount() + "/3000";
                            tv_float.setText(wb);
                        }
                    } else {
                        //SnackBar.makeText(getActivity(), floatingCash.getMessage(), SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
