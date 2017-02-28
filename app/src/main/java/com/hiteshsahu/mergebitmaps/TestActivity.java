package com.hiteshsahu.mergebitmaps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestActivity extends AppCompatActivity {

    private Bitmap[] arts = new Bitmap[2];
    private SeekBar satrtX1SeekBar, satrtX2SeekBar, satrtY1SeekBar, satrtY2SeekBar;
    private ImageView background;
    private SwitchCompat leftHalfBitMap1, rightHalfBitMap2;
    private boolean shouldDrawRightHalf = true;
    private boolean shouldDrawLeftHalf = true;

    private final String TEST_DIR = Environment.getExternalStorageDirectory() + "/Arts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        background = (ImageView) findViewById(R.id.background_image);
        satrtX1SeekBar = (SeekBar) findViewById(R.id.startX1_seekbar);
        satrtX2SeekBar = (SeekBar) findViewById(R.id.startX2_seekbar);
        satrtY1SeekBar = (SeekBar) findViewById(R.id.startY1_seekbar);
        satrtY2SeekBar = (SeekBar) findViewById(R.id.startY2_seekbar);
        leftHalfBitMap1 = (SwitchCompat) findViewById(R.id.left_Half_switch);
        rightHalfBitMap2 = (SwitchCompat) findViewById(R.id.right_Half_switch);

        setUpListeners();
    }

    private void setUpListeners() {

        satrtX1SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mergeBitMaps(getApplicationContext(), satrtX1SeekBar.getProgress(), satrtY1SeekBar.getProgress(), satrtX2SeekBar.getProgress(), satrtY2SeekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        satrtX2SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mergeBitMaps(getApplicationContext(), satrtX1SeekBar.getProgress(), satrtY1SeekBar.getProgress(), satrtX2SeekBar.getProgress(), satrtY2SeekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        satrtY1SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mergeBitMaps(getApplicationContext(), satrtX1SeekBar.getProgress(), satrtY1SeekBar.getProgress(), satrtX2SeekBar.getProgress(), satrtY2SeekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        satrtY2SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mergeBitMaps(getApplicationContext(), satrtX1SeekBar.getProgress(), satrtY1SeekBar.getProgress(), satrtX2SeekBar.getProgress(), satrtY2SeekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        leftHalfBitMap1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                shouldDrawLeftHalf = b;

            }
        });

        rightHalfBitMap2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                shouldDrawRightHalf = b;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mergeBitMaps(getApplicationContext(), 0, 0, 200, 0);

    }

    public void mergeBitMaps(final Context context, final int startX1, final int startY1, final int startX2, final int startY2) {
        Log.e("Merger", "Merging Image 2");

//        if (arts.length == 0) {

        //If Images are not loaded load them

        Glide.with(context)
                .load(R.drawable.raees)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        // Add Bitmap in Array
                        arts[0] = resource;

                        Glide.with(context)
                                .load(R.drawable.raees_edited)
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource1, GlideAnimation<? super Bitmap> glideAnimation) {

                                        // Add another one in array
                                        arts[1] = resource1;

                                        //Set seekbars
                                        satrtX1SeekBar.setMax(arts[0].getWidth());
                                        satrtX2SeekBar.setMax(arts[1].getWidth());
                                        satrtY1SeekBar.setMax(arts[0].getHeight());
                                        satrtY2SeekBar.setMax(arts[1].getHeight());

                                        Bitmap bmOverlay = Bitmap.createBitmap(arts[0].getWidth(), arts[0].getHeight(), Bitmap.Config.ARGB_8888);
                                        Canvas canvas = new Canvas(bmOverlay);

                                        if (shouldDrawLeftHalf) {
                                            // Rect(left, top, right, bottom)
                                            canvas.drawBitmap(arts[0],
                                                    new Rect(0, 0, arts[0].getWidth() / 2, arts[0].getHeight()),
                                                    new Rect(0, 0, arts[0].getWidth() / 2, arts[0].getHeight()), null);

                                        } else {
                                            canvas.drawBitmap(arts[0], startX1, startY1, null);
                                        }

                                        if (shouldDrawRightHalf) {
                                            // Rect(left, top, right, bottom)
                                            canvas.drawBitmap(arts[1],
                                                    new Rect(arts[1].getWidth() / 2, 0, arts[0].getWidth(), arts[1].getHeight()),
                                                    new Rect(arts[1].getWidth() / 2, 0, arts[1].getWidth(), arts[1].getHeight()), null);
                                        } else {
                                            canvas.drawBitmap(arts[1], startX2, startY2, null);
                                        }


                                        background.setImageBitmap(bmOverlay);

                                        // saveToDisk("Test", bmOverlay);
                                    }
                                });
                    }
                });
//        } else {
//
//            //Draw Bitmaps
//            Bitmap bmOverlay = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bmOverlay);
//            canvas.drawBitmap(arts[0], startX1, startY1, null);
//            canvas.drawBitmap(arts[1], startX2, startY2, null);
//
//            background.setImageBitmap(bmOverlay);
//        }
    }


    /**
     * Store image on SD Card
     *
     * @param fileName
     * @param art
     */
    private void saveToDisk(String fileName, Bitmap art) {

        //Create folder in SDCard to store newly generated image
        File mTempFile = new File(TEST_DIR);
        if (!mTempFile.exists()) {
            mTempFile.mkdirs();
        }
        //File name
        FileOutputStream mFileOutputStream = null;

        try {
            String FtoSave = TEST_DIR + fileName + ".png";
            File mFile = new File(FtoSave);
            mFileOutputStream = new FileOutputStream(mFile);
            art.compress(Bitmap.CompressFormat.PNG, 95, mFileOutputStream);
            mFileOutputStream.flush();
            mFileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("Merger", "FileNotFoundExceptionError " + e.toString());
        } catch (IOException e) {
            Log.e("Merger", "IOExceptionError " + e.toString());
        }
        Log.i("Merger", "Image Created");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
