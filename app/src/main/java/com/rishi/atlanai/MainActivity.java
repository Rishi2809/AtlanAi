package com.rishi.atlanai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    Drawable drawable;
    Bitmap bitmap;
    String ImagePath;
    Context context ;
    Uri URI;
    Button save;
    ImageView imageView,imageView2;
Boolean chec=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        context = getApplicationContext();
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        save = findViewById(R.id.save);
        save.setEnabled(false);

    }

    public void capture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);


    }

    public void save(View view) {

        drawable = imageView.getDrawable();
        Log.d("hello", String.valueOf(drawable));
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        Log.d("hello", String.valueOf(bitmap));
        if (!bitmap.equals(null)) {
            ImagePath = MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    bitmap,
                    "demo_image",
                    "demo_image"
            );

            URI = Uri.parse(ImagePath);

            Toast.makeText(getApplicationContext(), "Image Saved Successfully at " + URI, Toast.LENGTH_LONG).show();
            Log.d("path", String.valueOf(URI));

        } else {
            Toast.makeText(getApplicationContext(), "No data to save", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int newOrientation = newConfig.orientation;

        if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageView = findViewById(R.id.imageView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

            save.setEnabled(true);
            chec=true;
        }
    }
}