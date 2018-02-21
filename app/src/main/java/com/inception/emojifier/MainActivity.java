package com.inception.emojifier;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

public class MainActivity extends AppCompatActivity {



    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // this function will open camera app

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }

    // listen for the result from camera app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            // get image from camera and store in bitmap object

            Bitmap imageBitmap = (Bitmap) extras.get("data");


            Button go_btn = findViewById(R.id.go_btn);

            TextView emojify_txt = findViewById(R.id.emojify_txt);

            // setting visibility of go button to gone
            go_btn.setVisibility(View.GONE);

            // setting visibility of emojifiy text to gone
            emojify_txt.setVisibility(View.GONE);

            FloatingActionButton save_btn = findViewById(R.id.save_btn);

            FloatingActionButton share_btn = findViewById(R.id.share_btn);

            FloatingActionButton cancel_btn = findViewById(R.id.cancel_btn);


            // setting visibility of save button to visible
            save_btn.setVisibility(View.VISIBLE);

            // setting visibility of share button to visible
            share_btn.setVisibility(View.VISIBLE);

            // setting visibility of cancel button to visible
            cancel_btn.setVisibility(View.VISIBLE);

            ImageView clicked_image = findViewById(R.id.image_);

            clicked_image.setVisibility(View.VISIBLE);

            clicked_image.setImageBitmap(imageBitmap);

            detect_faces(imageBitmap);

        }
    }



    // on click of go  button
    // it will call dispatch take picture function

    public void go_btn(View view) {

        dispatchTakePictureIntent();
    }


    // on click of cancel button
    public void clear_btn(View view) {



        Button go_btn = findViewById(R.id.go_btn);

        TextView emojify_txt = findViewById(R.id.emojify_txt);

        go_btn.setVisibility(View.VISIBLE);

        emojify_txt.setVisibility(View.VISIBLE);

        FloatingActionButton save_btn = findViewById(R.id.save_btn);

        FloatingActionButton share_btn = findViewById(R.id.share_btn);

        FloatingActionButton cancel_btn = findViewById(R.id.cancel_btn);

        save_btn.setVisibility(View.GONE);

        share_btn.setVisibility(View.GONE);

        cancel_btn.setVisibility(View.GONE);

        ImageView clicked_image = findViewById(R.id.image_);

        clicked_image.setVisibility(View.GONE);


    }



    // face detector function to detect faces
    // we will pass bitmap to this function
    public  void detect_faces(Bitmap bmp)
    {


        // creating FaceDetector object

        FaceDetector detector = new FaceDetector.Builder(MainActivity.this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        // creating Frame object
        Frame frame = new Frame.Builder().setBitmap(bmp).build();

        // detect faces in frame and store in SparseArray of type Face
        SparseArray<Face> faces = detector.detect(frame);

        // show number of faces in toast message

        Toast.makeText(MainActivity.this , "number of faces "+faces.size() , Toast.LENGTH_SHORT).show();

        // release detector

        detector.release();


    }

}
