package com.example.hemanthanil.homescopic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.InstrumentInfo;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Apartments extends AppCompatActivity {

private FirebaseFirestore apartment;
    TextView mTextView;
    ImageView mImageView;
    List<String> imagesArray;
    String murl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments);

        mTextView = (TextView)findViewById(R.id.apartmentinformation);
        mImageView = (ImageView) findViewById(R.id.apartmentimage1);
        imagesArray = new ArrayList<>();

        apartment = FirebaseFirestore.getInstance();

            DocumentReference docRef = apartment.collection("Apartments").document("Apartment1");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            // Log.d("FIRESTORE_SUCESS", "DocumentSnapshot data: " + task.getResult().getData());
                            String s = task.getResult().getData().get("Description") + "";
                            //Toast.makeText(Apartments.this, s, Toast.LENGTH_SHORT).show();
                            mTextView.setText(s);
                            murl = task.getResult().getData().get("image3") + "";

                            try {
                                Bitmap image = new SendHttpRequestTask().execute("").get();
                                mImageView.setImageBitmap(image);


                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }


                            // document.get("Apartments/Apartment1").
                        } else {
                            Log.d("FIRESTORE_FAIL", "No such document");
                        }
                    } else {
                        Log.d("FIRESTORE_EXCEPTION", "get failed with ", task.getException());
                    }
                }
            });

        imageList();

//        Toast.makeText(this, imagesArray.get(2)+"", Toast.LENGTH_SHORT).show();
   //     Log.d("ARRRRRAY", imagesArray.get(0)+"");

    }


    public void imageList() {



        for(int i = 1; i < 4; i++) {
            DocumentReference docRef = apartment.collection("Apartments").document("Apartment"+i);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            Log.d("FIRESTORE_SUCESS", "DocumentSnapshot data: " + task.getResult().getData());
                            String s = task.getResult().getData().get("image1")+"";

                            Toast.makeText(Apartments.this, s, Toast.LENGTH_SHORT).show();
                            Log.d("TEEEEST", document.getData().get("image1").toString());
                            //if(document.getData().get("image1") != null) {
                                imagesArray.add(document.getData().get("image1").toString());
                           // }



                           // Log.d("A",imagesArray.toString());
                           // String a =task.getResult().getData().get(j)+"";


                            //Toast.makeText(Apartments.this, task.getResult().getData().get("image1")+"", Toast.LENGTH_SHORT).show();
                          //  Toast.makeText(Apartments.this, imagesArray.get(j), Toast.LENGTH_SHORT).show();
                            // document.get("Apartments/Apartment1").
                        } else {
                            Log.d("FIRESTORE_FAIL", "No such document");
                        }
                    } else {
                        Log.d("FIRESTORE_EXCEPTION", "get failed with ", task.getException());
                    }
                }
            });

//           String s = imagesArray.get(i);
//            Toast.makeText(this, imagesArray+"", Toast.LENGTH_SHORT).show();

        }




    }


    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(murl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                Log.d("Apartments.Class",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mImageView.setImageBitmap(result);
        }
    }
}
