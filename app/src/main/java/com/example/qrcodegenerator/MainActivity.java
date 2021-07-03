package com.example.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class MainActivity extends AppCompatActivity {
    String TAG = "QRCODE";
    ImageView Image;
    Button btnGenerate;
    String Username, Company, Product, Price;
    Bitmap bitmap;
    QRGEncoder qrEncoder;
    TextView textInstruction;
    EditText edtName, edtCompany, edtProduct, edtPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Image = (ImageView) findViewById(R.id.image);
        textInstruction = (TextView) findViewById(R.id.textinstruction);
        edtName = (EditText) findViewById(R.id.edtName);
        edtCompany = (EditText) findViewById(R.id.edtCompany);
        edtProduct = (EditText) findViewById(R.id.edtProduct);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        btnGenerate= (Button) findViewById(R.id.btnGenerateQR);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username = edtName.getText().toString().trim();
                Company = edtCompany.getText().toString().trim();
                Product = edtProduct.getText().toString().trim();
                Price = edtPrice.getText().toString().trim();
                if (!Company.equals("") && !Product.equals("") && !Username.equals("") && !Price.equals("")) {
                    String details = "Username =" + Username + "\n" + "Company =" + Company + "\n" + "Product =" + Product + "\n" + "Price =" + Price ;
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = Math.min(width, height);
                    smallerDimension = smallerDimension * 3 / 4;

                    qrEncoder = new QRGEncoder(
                            details, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap = qrEncoder.encodeAsBitmap();
                        Image.setImageBitmap(bitmap);
                        textInstruction.setVisibility(View.VISIBLE);
                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, " Please fill all details Properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
