package com.usingtone.erweimademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEdit;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_scaner).setOnClickListener(this);
        findViewById(R.id.btn_generate).setOnClickListener(this);
        mEdit = (EditText) findViewById(R.id.et_input);
        image = (ImageView) findViewById(R.id.iv_picture);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_scaner) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivity(intent);
        }else {
          Generate2Bitmap();
        }
    }
    private void Generate2Bitmap() {
        String message = mEdit.getText().toString().trim();
        Bitmap bitmap = null;
        bitmap = generateQRCode(message);
        image.setImageBitmap(bitmap);
    }
        private Bitmap bitMatrix2Bitmap(BitMatrix  matrix) {
            int w = matrix.getWidth();
            int h = matrix.getHeight();
            int[] rawData = new int[w * h];
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int color = Color.WHITE;
                    if (matrix.get(i, j)) {
                        color = Color.BLACK;
                    }
                    rawData[i + (j * w)] = color;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
            return bitmap;
        }

        private Bitmap generateQRCode(String content) {
            try {
                QRCodeWriter writer = new QRCodeWriter();
                // MultiFormatWriter writer = new MultiFormatWriter();
                BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 500, 500);
                return bitMatrix2Bitmap(matrix);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
