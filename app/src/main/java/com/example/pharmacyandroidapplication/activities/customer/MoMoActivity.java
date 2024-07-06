package com.example.pharmacyandroidapplication.activities.customer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacyandroidapplication.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;

import org.json.JSONException;
import org.json.JSONObject;

public class MoMoActivity extends AppCompatActivity {

    TextView tvEnvironment, tvMerchantName, tvMerchantCode, edAmount, tvMessage;
    Button btnCancel, btnPayMoMo;
    private String amount = "10000";
//    FirebaseUser user;
    Map<String, Object> listProduct =new HashMap<>();
    Map<String, Object> newOrder = new HashMap<>();
    private String fee = "0";
    public static String KEY_ENVIRONMENT = "key_environment";
    int environment = 2;//developer default
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_mo);

//        user = FirebaseAuth.getInstance().getCurrentUser();
        tvEnvironment=findViewById(R.id.tvEnvironment);
        tvMerchantCode=findViewById(R.id.tvMerchantCode);
        tvMerchantName=findViewById(R.id.tvMerchantName);
        btnPayMoMo=findViewById(R.id.btnPayMoMo);
        btnCancel=findViewById(R.id.btn_cancel);
        edAmount=findViewById(R.id.edAmount);

        Intent intent = getIntent();
        String orderID = intent.getStringExtra("orderID");
        String address = intent.getStringExtra("address");
        String nameReceiver = intent.getStringExtra("name_receiver");
        String noted = intent.getStringExtra("noted");
        String orderDate = intent.getStringExtra("order_date");
        String orderStatus = intent.getStringExtra("order_status");
        String phone = intent.getStringExtra("phone");
        String totalPayment = intent.getStringExtra("total_payment");



        if(environment == 0){
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEBUG);
            tvEnvironment.setText(totalPayment);
            tvMerchantCode.setText(orderID);
        }else if(environment == 1){
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
            tvEnvironment.setText(totalPayment);
            tvMerchantCode.setText(orderID);
        }else if(environment == 2){
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.PRODUCTION);
            tvEnvironment.setText(totalPayment);
            tvMerchantCode.setText(orderID);
        }

        btnPayMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPayment();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MoMoActivity.this, CustomerHomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        if (edAmount.getText().toString() != null && edAmount.getText().toString().trim().length() != 0)
            amount = edAmount.getText().toString().trim();

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, "HaruPharmacy");
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, "SCB01");
        eventValue.put(MoMoParameterNamePayment.AMOUNT, amount);
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, "Online payment");
        //client Optional
        eventValue.put(MoMoParameterNamePayment.FEE, fee);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, "HaruPharmacy");

        eventValue.put(MoMoParameterNamePayment.REQUEST_ID,  "bill id"+"-"+ UUID.randomUUID().toString());
        eventValue.put(MoMoParameterNamePayment.PARTNER_CODE, "CGV19072017");

        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
            objExtraData.put("ticket", "{\"ticket\":{\"01\":{\"type\":\"std\",\"price\":110000,\"qty\":3}}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put(MoMoParameterNamePayment.EXTRA_DATA, objExtraData.toString());
        eventValue.put(MoMoParameterNamePayment.REQUEST_TYPE, "payment");
        eventValue.put(MoMoParameterNamePayment.LANGUAGE, "vi");
        eventValue.put(MoMoParameterNamePayment.EXTRA, "");
        //Request momo app
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));

                    if(data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:

                    } else {
                        tvMessage.setText("message: ");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    tvMessage.setText("message: " + message);
                } else if(data.getIntExtra("status", -1) == 2) {
                    tvMessage.setText("message: " );
                } else {
                    tvMessage.setText("message: " );
                }
            } else {
                tvMessage.setText("message: " );
            }
        } else {
            tvMessage.setText("message: " );
        }
    }




//    private static final int MOMO_REQUEST_CODE = 1000;
//    private String total_amount = "10000";
//    private String fee = "0";
//    int environment = 0;//developer default
//    private String merchantName = "Haru Pharmacy";
//    private String merchantCode = "SCB01";
//    private String merchantNameLabel = "Nhà cung cấp";
//    private String description = "Thanh toán dịch vụ ABC";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mo_mo);
//
//        // Call the MoMo payment request here
//        requestPayment("order123", 50000);
//
//    }
//
//    private void requestPayment(String orderID, int totalAmount) {
//        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
//        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//
//        Map<String, Object> eventValue = new HashMap<>();
//        //client Required
//        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, "HealthyPlus");
//        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, "bill id");
//        eventValue.put(MoMoParameterNamePayment.AMOUNT, total_amount);
//        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, "loi nhan");
//        //client Optional
//        eventValue.put(MoMoParameterNamePayment.FEE, fee);
//        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, "nha cung cap");
//
//        eventValue.put(MoMoParameterNamePayment.REQUEST_ID,  "bill id"+"-"+ UUID.randomUUID().toString());
//        eventValue.put(MoMoParameterNamePayment.PARTNER_CODE, "CGV19072017");
//
//        JSONObject objExtraData = new JSONObject();
//        try {
//            objExtraData.put("site_code", "008");
//            objExtraData.put("site_name", "CGV Cresent Mall");
//            objExtraData.put("screen_code", 0);
//            objExtraData.put("screen_name", "Special");
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
//            objExtraData.put("movie_format", "2D");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        eventValue.put("extraData", objExtraData.toString());
//
//        eventValue.put("extra", "");
//        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
//
//        Log.d("MoMoPayment", "Payment request initiated");
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == MOMO_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    String status = data.getStringExtra("status");
//                    String message = data.getStringExtra("message");
//                    Log.d("MoMoPayment", "Status: " + status + ", Message: " + message);
//
//                    // Handle your result here
//                }
//            } else {
//                Log.d("MoMoPayment", "Payment failed or canceled");
//            }
//        }
//    }
}
