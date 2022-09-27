package com.spectra.fieldforce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClientKaizala;
import com.spectra.fieldforce.kaizalaapp.activity.KaizalaDashboard;
import com.spectra.fieldforce.model.ValidateUserReq;
import com.spectra.fieldforce.model.ValidateUserResponse;
import com.spectra.fieldforce.salesapp.activity.SalesDashboard;
import com.spectra.fieldforce.salesapp.model.ValidateSalesResponse;
import com.spectra.fieldforce.utils.PrefConfig;
import com.spectra.fieldforce.activity.BucketTabActivity;
import com.spectra.fieldforce.activity.GetInsActivity;
import com.spectra.fieldforce.activity.SpectraFfaActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.MainDashboradBinding;
import com.spectra.fieldforce.databinding.MainDashboradadapterBinding;
import com.spectra.fieldforce.model.DashBoardModel;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.salesapp.model.ValidateResponse;
import com.spectra.fieldforce.salesapp.model.ValidateUserRequest;
import com.spectra.fieldforce.utils.AppConstant;
import com.spectra.fieldforce.utils.Constants;
import com.spectra.fieldforce.utils.PrefConfig;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<DashBoardModel> dashBoardModels;
    private MainDashboradadapterBinding binding;
    private String emailId,password;
    public static final String PREF ="Login";
    public static PrefConfig prefConfig;
    public DashBoardAdapter(FragmentActivity activity, ArrayList<DashBoardModel> dashBoardModels) {
        this.context = activity;
        this.dashBoardModels = dashBoardModels;
    }

    @NotNull
    @Override
    public DashBoardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.main_dashboradadapter, parent, false);
        return new DashBoardAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DashBoardAdapter.MyViewHolder holder, int position) {
        prefConfig = new PrefConfig(context);
        SharedPreferences sp1=context.getSharedPreferences("Login",0);
        emailId =sp1.getString("EmailId", null);
        password = sp1.getString("Password", null);
        holder.binding.name.setText(dashBoardModels.get(position).Name);
        holder.binding.image.setImageResource(dashBoardModels.get(position).Image);

        holder.binding.linearSales.setOnClickListener(v -> {
            if(dashBoardModels.get(position).Name.equalsIgnoreCase(AppConstant.SALES)){
                validateSalesUser("S");
            }
            else if(dashBoardModels.get(position).Name.equalsIgnoreCase(AppConstant.INSTALLATION)){
                validateGpon("I");
            }
            else if(dashBoardModels.get(position).Name.equalsIgnoreCase(AppConstant.FAULT_REPAIR)){
                validateffa("F");
            }
            else if(dashBoardModels.get(position).Name.equalsIgnoreCase(AppConstant.PROVISIONG_APP)){
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GPON)));
            }
            else if(dashBoardModels.get(position).Name.equalsIgnoreCase(AppConstant.SALES_HOME)){
                validateSalesHome();
            }

        });

    }

    private void validateffa(String f) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest(Constants.VALIDATE_USER,Constants.AUTH_KEY,emailId,password,f);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ValidateResponse> call = apiService.validateUserReq(validateUserRequest);
        call.enqueue(new Callback<ValidateResponse>() {
            @Override
            public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if(response.body().getStatus().equals("Success")) {
                                SharedPreferences sp = context.getSharedPreferences(PREF , 0);
                                SharedPreferences.Editor myEdit = sp.edit();
                                myEdit.putString("VenderCode", response.body().getResponse().get(0).getVendorCode());
                                myEdit.putString("EnggId", response.body().getResponse().get(0).getUserId());
                                myEdit.putString("EnggName",response.body().getResponse().get(0).getName());
                                myEdit.putString("EmailId",response.body().getResponse().get(0).getEmailId());
                                myEdit.commit();
                                Intent i = new Intent(context, SpectraFfaActivity.class);
                                context.startActivity(i);
                            }else if(response.body().getStatus().equals("Failure")) {
                                Toast.makeText(context, "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateResponse> call, Throwable t) {
                Toast.makeText(context, "Access Denied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void validateSalesHome() {
        ValidateUserReq validate = new ValidateUserReq(Constants.KAUTH,Constants.GET_VALIDATE,emailId,password);
        ApiInterface apiService = ApiClientKaizala.getClient().create(ApiInterface.class);
        Call<ValidateUserResponse> call = apiService.validateReq(validate);
        call.enqueue(new Callback<ValidateUserResponse>() {
            @Override
            public void onResponse(Call<ValidateUserResponse> call, Response<ValidateUserResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if(response.body().getStatusCode().equals("200")) {
                                SharedPreferences sp = context.getSharedPreferences(PREF , 0);
                                SharedPreferences.Editor myEdit = sp.edit();
                                myEdit.putString("UName", response.body().getResponse().getUserName());
                                Intent i = new Intent(context, KaizalaDashboard.class);
                                context.startActivity(i);
                            }else if(response.body().getStatus().equals("401")) {
                                Toast.makeText(context, "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateUserResponse> call, Throwable t) {
                Toast.makeText(context, "Failure"+t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void validateGpon(String f) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest(Constants.VALIDATE_USER,Constants.AUTH_KEY,emailId,password,f);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ValidateResponse> call = apiService.validateUserReq(validateUserRequest);
        call.enqueue(new Callback<ValidateResponse>() {
            @Override
            public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if(response.body().getStatus().equals("Success")) {
                                SharedPreferences sp = context.getSharedPreferences(PREF , 0);
                                SharedPreferences.Editor myEdit = sp.edit();
                                myEdit.putString("VenderCode", response.body().getResponse().get(0).getVendorCode());
                                myEdit.putString("EnggId", response.body().getResponse().get(0).getUserId());
                                myEdit.putString("EnggName",response.body().getResponse().get(0).getName());
                                myEdit.putString("EmailId",response.body().getResponse().get(0).getEmailId());
                                myEdit.commit();
                                Intent i = new Intent(context, BucketTabActivity.class);
                                context.startActivity(i);
                            }else  if(response.body().getStatus().equals("Failure")) {
                                Toast.makeText(context, "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateResponse> call, Throwable t) {
                Toast.makeText(context, "Access Denied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void validateSalesUser(String s) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest(Constants.VALIDATE_USER,Constants.AUTH_KEY,emailId,password,s);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ValidateSalesResponse> call = apiService.validateSalesUserReq(validateUserRequest);
        call.enqueue(new Callback<ValidateSalesResponse>() {
            @Override
            public void onResponse(Call<ValidateSalesResponse> call, Response<ValidateSalesResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponse().getStatusCode() == 200) {
                                SharedPreferences sp = context.getSharedPreferences(PREF , 0);
                                SharedPreferences.Editor myEdit = sp.edit();
                                myEdit.putString("EnggId", response.body().getResponse().getData().getEmployeeID());
                                myEdit.putString("EnggName",response.body().getResponse().getData().getUserName());
                                myEdit.putString("EmailId",response.body().getResponse().getData().getPrimaryEmail());
                                myEdit.commit();
                                Intent i = new Intent(context, SalesDashboard.class);
                                context.startActivity(i);
                            } else if (response.body().getStatus().equals("Failure")) {
                                Toast.makeText(context, "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ValidateSalesResponse> call, Throwable t) {
                Toast.makeText(context, "Access Denied", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dashBoardModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MainDashboradadapterBinding binding;
        MyViewHolder( MainDashboradadapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
