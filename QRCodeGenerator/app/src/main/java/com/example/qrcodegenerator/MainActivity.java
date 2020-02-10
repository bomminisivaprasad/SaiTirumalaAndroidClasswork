package com.example.qrcodegenerator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import java.util.*;
import com.google.common.collect.ImmutableMap;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.qr);

        String QRcode = "Hello This is Mastan Vali";
        new generateQrcode(iv).execute(QRcode);
    }
    private class generateQrcode extends AsyncTask<String, Void, Bitmap> {
        public final static int WIDTH = 400;
        ImageView bmImage;

        public generateQrcode(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String Value = urls[0];
            com.google.zxing.Writer writer = new QRCodeWriter();
            Bitmap bitmap = null;
            BitMatrix bitMatrix = null;
            try {
                bitMatrix = writer.encode(Value, com.google.zxing.BarcodeFormat.QR_CODE, WIDTH, WIDTH,
                        ImmutableMap.of(EncodeHintType.MARGIN, 1));
                bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
                for (int i = 0; i < 400; i++) {
                    for (int j = 0; j < 400; j++) {
                        bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK
                                : Color.WHITE);
                    }
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
