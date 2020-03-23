package com.example.farmerskart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemDetailsActivity extends AppCompatActivity implements PaymentResultListener {

    ImageView pImage;
    CircleImageView oImage;
    TextView pName, PPrice, pOwnername, pOwnerMobile, pOwnerdAdd;
    String TAG="Payment Error";
    String pname, pp, pimagepath, pon, pom, pod, poadd, poimage, pol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        oImage = findViewById(R.id.pd_oimage);
        pImage = findViewById(R.id.pd_image);
        pName = findViewById(R.id.pd_name);
        PPrice = findViewById(R.id.pd_price);
        pOwnername = findViewById(R.id.pd_oname);
        pOwnerMobile = findViewById(R.id.pd_omobile);
        pOwnerdAdd = findViewById(R.id.pd_distictadd);
        Intent i = getIntent();
        if (i.hasExtra("pname")) {
            pname = i.getStringExtra("pname");
            pp = i.getStringExtra("pprice");
            pimagepath = i.getStringExtra("pimagepath");
            pon = i.getStringExtra("pownername");
            pom = i.getStringExtra("pownermobile");
            pod = i.getStringExtra("pownerdistrict");
            poadd = i.getStringExtra("powneraddress");
            poimage = i.getStringExtra("pownerimagepath");
            pol = i.getStringExtra("pownerlocation");

            Glide.with(this).load(pimagepath).into(pImage);
            Glide.with(this).load(poimage).into(oImage);
            pName.setText(pname);
            PPrice.setText(pp);
            pOwnername.setText(pon);
            pOwnerMobile.setText(pom);
            pOwnerdAdd.setText(pod + "\n" + poadd);

        }
        Checkout.preload(getApplicationContext());
        pOwnerMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + pom);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                startActivity(i);
            }
        });
    }

    public void viewLocation(View view) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + pol));
        startActivity(intent);

       /* Uri gmmIntentUri = Uri.parse("geo:"+pol);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);*/
    }

    public void payment(View view) {

        String[] list = pp.split("/");
        Toast.makeText(this, "" + list[0], Toast.LENGTH_SHORT).show();

        Checkout checkout = new Checkout();
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Merchant Name");
            options.put("description", "Test Order");
            options.put("currency", "INR");
            options.put("amount", list[0]);
            checkout.open(activity, options);
        }catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
}