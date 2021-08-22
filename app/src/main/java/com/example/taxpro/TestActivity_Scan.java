package com.example.taxpro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

public class TestActivity_Scan extends AppCompatActivity
{
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null)
        {
            if (intentResult.getContents() != null)
            {
                Toast.makeText(TestActivity_Scan.this, "QRcode 내용:"+intentResult.getContents(),
                        Toast.LENGTH_SHORT).show();
                Log.d("TAG!!!", intentResult.getContents());
                Log.d("TAG!!!", intentResult.getFormatName());
            }
            else
            {
                Log.d("TAG!!!", "Cancelled");
            }

        }
        else
        {
            Log.d("TAG!!!", "intentResult is null!!!");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    Button scan_Btn;
    Button createQR_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scan);

        Intent intent = getIntent();

        scan_Btn=findViewById(R.id.Scan_btn_Scan);
        createQR_Btn=findViewById(R.id.Scan_btn_CreateQR);

        scan_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                IntentIntegrator intentIntegrator = new IntentIntegrator(TestActivity_Scan.this);
                intentIntegrator.setPrompt("바코드 혹은 QR코드를 스캔하세요.^^");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
            }
        });

        createQR_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();

                try
                {
                    BitMatrix bitMatrix = qrCodeWriter.encode("Pen", BarcodeFormat.QR_CODE,512,512);
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();

                    Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);
                    for (int x=0; x<width; x++)
                    {
                        for (int y =0; y<height; y++)
                        {
                            bitmap.setPixel(x,y, bitMatrix.get(x,y) ? Color.BLACK : Color.WHITE);

                        }
                    }

                    ((ImageView)findViewById(R.id.Scan_Image_QR)).setImageBitmap(bitmap);



                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });
    }


}