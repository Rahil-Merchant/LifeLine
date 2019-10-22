package com.example.lifeline;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
//import android.widget.EditText;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
//PAYTM

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;


public class PaytmActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String custid="", orderId="", mid=""/*,amt=""*/;
    String doa,timeSlot;
    int amtInt=0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fname,mname,lname,gender,occupation,organization,mobNo,bloodGrp,dob,uid,email,last_donated;
    int rewards_count,timesDonated;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_turf_details);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        email = mAuth.getCurrentUser().getEmail();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                doa= null;
                timeSlot= null;
            } else {
                doa= extras.getString("doa");
                timeSlot = extras.getString("timeSlot");
            }
        } else {
            doa= (String) savedInstanceState.getSerializable("doa");
            timeSlot= (String) savedInstanceState.getSerializable("timeSlot");
        }




        InitPaytm();
    }
    private void InitPaytm(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // create instance of Random class
        Random rand = new Random();
        // Generate random integers in range 0 to 9999999
        int rand_orderid = rand.nextInt(10000000);
        //Intent intent = getIntent();
        orderId = String.valueOf(rand_orderid);
        custid = "124356457814567956";
        Intent intent = getIntent();
//        amt = intent.getExtras().getString("amount");
//        amtInt=Integer.parseInt(amt);
        mid = "lXJlFV87479245228588"; /// your merchant key
        PaytmActivity.sendUserDetailTOServerdd dl = new PaytmActivity.sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(PaytmActivity.this);

        //private String orderId , mid, custid, amt;
        String url ="https://turfexcelapp.000webhostapp.com/generateChecksum.php";
        String varifyurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderId;
        String CHECKSUMHASH ="";

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {
            jsonparse jsonParser = new jsonparse(PaytmActivity.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" +orderId+
                            "&CUST_ID="+custid+
                            "&TXN_AMOUNT="+amtInt+
                            "&CHANNEL_ID=WAP&WEBSITE=DEFAULT"+
                            "&CALLBACK_URL="+varifyurl+"&INDUSTRY_TYPE_ID=Retail";

            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);


            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {

                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            PaytmPGService Service = PaytmPGService.getProductionService();
            // PaytmPGService  Service = PaytmPGService.getProductionService();

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", Integer.toString(amtInt));
            paramMap.put("WEBSITE", "DEFAULT");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(PaytmActivity.this, true, true,
                    PaytmActivity.this  );


        }

    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        if (ContextCompat.checkSelfPermission(PaytmActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(PaytmActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        finish();
        startActivity(new Intent(this,PaytmActivity.class));

    }


    @Override
    public void networkNotAvailable() {
        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this,homeActivity.class));

    }

    @Override
    public void clientAuthenticationFailed(String s) {
        finish();
        Toast.makeText(this, "Authentication Failure", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,homeActivity.class));
    }

    @Override
    public void someUIErrorOccurred(String s) {

        Log.e("checksum ", " ui fail respon  "+ s );
        Toast.makeText(this, "Some UI error occurred", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this,homeActivity.class));
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
        Toast.makeText(this, "Error loading web page", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this,homeActivity.class));
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  " );
        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
        finish();
        BookingActivity.step = 0;
        startActivity(new Intent(this,BookingActivity.class));
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );
        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this,homeActivity.class));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        saveInfo();
        Toast.makeText(this, "Appointment Fixed", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this,homeActivity.class));
    }


    void createAppl(){
        Map<String, Object> newAppl = new HashMap<>();
        newAppl.put("fname", fname);
        newAppl.put("mname", mname);
        newAppl.put("lname", lname);
        newAppl.put("gender", gender);
        newAppl.put("occupation", occupation);
        newAppl.put("last_donated", last_donated);
        newAppl.put("organization", organization);
        newAppl.put("mobNo", mobNo);
        newAppl.put("bloodGrp", bloodGrp);
        newAppl.put("dob", dob);
        newAppl.put("rewards_count", rewards_count);
        newAppl.put("isAccepted", false);
        newAppl.put("reqReport", false);
        newAppl.put("repdoc", "no");
        newAppl.put("uid", uid);
        newAppl.put("email", email);
        newAppl.put("doa",doa);
        newAppl.put("timeSlot",timeSlot);

        DocumentReference applRef = db.collection("applwait").document();
        applRef.set(newAppl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
                startActivity(new Intent(getContext(),homeActivity.class));
                Toast.makeText(getApplicationContext(), "Appointment Scheduled", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Unexpected Error, Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    void saveInfo(){
        DocumentReference userRef = db.collection("users").document(uid);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fname = documentSnapshot.getString("fname");
                mname = documentSnapshot.getString("mname");
                lname = documentSnapshot.getString("lname");
                gender = documentSnapshot.getString("gender");
                mobNo = documentSnapshot.getString("mobNo");
                last_donated = documentSnapshot.getString("last_donated");
                dob = documentSnapshot.getString("dob");
                occupation = documentSnapshot.getString("occupation");
                organization = documentSnapshot.getString("organization");
                rewards_count = documentSnapshot.getLong("rewards_count").intValue();
                timesDonated = documentSnapshot.getLong("timesDonated").intValue();
                bloodGrp = documentSnapshot.getString("bloodGrp");
                createAppl();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

//public class PaytmActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {
//
//    String custid="", orderId="", mid="",amt="";
//    int amtInt=0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_turf_details);
//
//        InitPaytm();
//    }
//
//    private void InitPaytm(){
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        // create instance of Random class
//        Random rand = new Random();
//        // Generate random integers in range 0 to 9999999
//        int rand_orderid = rand.nextInt(10000000);
//        //Intent intent = getIntent();
//        orderId = String.valueOf(rand_orderid);
//        custid = "124356457814567956";
//        Intent intent = getIntent();
//        amt = intent.getExtras().getString("amount");
//        amtInt=Integer.parseInt(amt);
//        mid = "lXJlFV87479245228588"; /// your merchant key
//        PaytmActivity.sendUserDetailTOServerdd dl = new PaytmActivity.sendUserDetailTOServerdd();
//        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//    }
//
//    //PAYTM
//
//    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {
//
//        private ProgressDialog dialog = new ProgressDialog(PaytmActivity.this);
//
//        //private String orderId , mid, custid, amt;
//        String url ="https://turfexcelapp.000webhostapp.com/generateChecksum.php";
//        String varifyurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderId;
//        String CHECKSUMHASH ="";
//
//        @Override
//        protected void onPreExecute() {
//            this.dialog.setMessage("Please wait");
//            this.dialog.show();
//        }
//
//        protected String doInBackground(ArrayList<String>... alldata) {
//            jsonparse jsonParser = new jsonparse(PaytmActivity.this);
//            String param=
//                    "MID="+mid+
//                            "&ORDER_ID=" +orderId+
//                            "&CUST_ID="+custid+
//                            "&TXN_AMOUNT="+amtInt+
//                            "&CHANNEL_ID=WAP&WEBSITE=DEFAULT"+
//                            "&CALLBACK_URL="+varifyurl+"&INDUSTRY_TYPE_ID=Retail";
//
//            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
//
//
//            Log.e("CheckSum result >>",jsonObject.toString());
//            if(jsonObject != null){
//                Log.e("CheckSum result >>",jsonObject.toString());
//                try {
//
//                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
//                    Log.e("CheckSum result >>",CHECKSUMHASH);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            return CHECKSUMHASH;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.e(" setup acc ","  signup result  " + result);
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//
//            PaytmPGService Service = PaytmPGService.getProductionService();
//            // PaytmPGService  Service = PaytmPGService.getProductionService();
//
//            // now call paytm service here
//            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
//            HashMap<String, String> paramMap = new HashMap<String, String>();
//            //these are mandatory parameters
//            paramMap.put("MID", mid); //MID provided by paytm
//            paramMap.put("ORDER_ID", orderId);
//            paramMap.put("CUST_ID", custid);
//            paramMap.put("CHANNEL_ID", "WAP");
//            paramMap.put("TXN_AMOUNT", amt);
//            paramMap.put("WEBSITE", "DEFAULT");
//            paramMap.put("CALLBACK_URL" ,varifyurl);
//            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
//            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
//            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
//            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
//            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
//
//            PaytmOrder Order = new PaytmOrder(paramMap);
//            Log.e("checksum ", "param "+ paramMap.toString());
//            Service.initialize(Order,null);
//            // start payment service call here
//            Service.startPaymentTransaction(PaytmActivity.this, true, true,
//                    PaytmActivity.this  );
//
//
//        }
//
//    }
//
//
//    @Override
//    public void onTransactionResponse(Bundle bundle) {
//        Log.e("checksum ", " respon true " + bundle.toString());
//        if (ContextCompat.checkSelfPermission(PaytmActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(PaytmActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
//        finish();
//        startActivity(new Intent(this,PaytmActivity.class));
//
//    }
//
//
//    @Override
//    public void networkNotAvailable() {
//        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
//        finish();
//        startActivity(new Intent(this,home.class));
//
//    }
//
//    @Override
//    public void clientAuthenticationFailed(String s) {
//        finish();
//        Toast.makeText(this, "Authentication Failure", Toast.LENGTH_LONG).show();
//        startActivity(new Intent(this,home.class));
//    }
//
//    @Override
//    public void someUIErrorOccurred(String s) {
//
//        Log.e("checksum ", " ui fail respon  "+ s );
//        Toast.makeText(this, "Some UI error occurred", Toast.LENGTH_LONG).show();
//        finish();
//        startActivity(new Intent(this,home.class));
//    }
//
//    @Override
//    public void onErrorLoadingWebPage(int i, String s, String s1) {
//        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
//        Toast.makeText(this, "Error loading web page", Toast.LENGTH_LONG).show();
//        finish();
//        startActivity(new Intent(this,home.class));
//    }
//
//    @Override
//    public void onBackPressedCancelTransaction() {
//        Log.e("checksum ", " cancel call back respon  " );
//        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
//        finish();
//        startActivity(new Intent(this,home.class));
//    }
//
//    @Override
//    public void onTransactionCancel(String s, Bundle bundle) {
//        Log.e("checksum ", "  transaction cancel " );
//        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
//        finish();
//        startActivity(new Intent(this,home.class));
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        finish();
//        startActivity(new Intent(this,home.class));
//    }
//}


