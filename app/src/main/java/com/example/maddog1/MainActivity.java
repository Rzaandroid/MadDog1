package com.example.maddog1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity implements MediaPlayer.OnCompletionListener, View.OnTouchListener {
    String url;
    int r;
    int g;
    int b;
    int count=0;
    private MediaPlayer mediaPlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Environment.getExternalStorageDirectory()
        url = this.getExternalFilesDir(null).getPath()+File.separator+"MadDog"+File.separator+"MDMIntro.mp4";
        //String url = File.separator+"mnt"+File.separator+"sdcard"+File.separator+"MadDog"+File.separator+"MDMIntro.mpg";

        //Toast.makeText(this,Uri.parse(url).toString(), Toast.LENGTH_LONG).show();
        //mediaPlayer = MediaPlayer.create(this, Uri.parse(url));
        VideoView v = (VideoView) findViewById(R.id.videoView);
        v.setOnTouchListener(this);
        v.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getApplicationContext(),"completed", Toast.LENGTH_SHORT).show();
            }
        });

        GuideBox guideBox = new GuideBox(this);
        //addContentView(guideBox, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        //v.setOverlay(guideBox);

        MediaController ctrl = new MediaController(this);
        ctrl.setVisibility(v.GONE);
        //try {
            //mediaPlayer.prepare();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //ctrl.setMediaPlayer((MediaController.MediaPlayerControl) mediaPlayer);
        v.setMediaController(ctrl);
                v.setOnCompletionListener(this);
        v.setVideoURI(Uri.parse(url));
                v.start();

        //mediaPlayer.start();

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

        mediaMetadataRetriever.setDataSource(url);

        //not working get view or surfaceview or something get bitmap get pixels
        while (v.isPlaying()==true) {

            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(v.getCurrentPosition()); //unit in microsecond
            int[] pix = new int[v.getHeight() * v.getWidth()];
            bitmap.getPixels(pix, 0, 1, 0, 0, v.getWidth(), v.getHeight());

            for (int i = 0; i < pix.length; i++) {
                r = (pix[i]) >> 16 & 0xff;
                g = (pix[i]) >> 8 & 0xff;
                b = (pix[i]) & 0xff;
            }
            count++;
        }
//calculate all regeions in all videos and time in seconds and save to a file or array
/*
        int colour = bitmap.getPixel(0, 0); //x, y put in for loop and get every pixel
        int red = Color.red(colour);
        int blue = Color.blue(colour);
        int green = Color.green(colour);
        int alpha = Color.alpha(colour);
*/
        //draw bounds on overlay
    }

    @Override
    public void onCompletion(MediaPlayer v) {

                url = this.getExternalFilesDir(null).getPath()+File.separator+"MadDog"+File.separator+"MDstart.m4v";
        try {
            v.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        v.start();
        //finish();
    }

    //Convenience method to show a video
    public static void showRemoteVideo(Context ctx, String url) {
        Intent i = new Intent(ctx, MainActivity.class);
        ctx.startActivity(i);
    }

    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        Toast.makeText(this, ("X==" + String.valueOf(x) + " Y==" + String.valueOf(y) + " frame" + String.valueOf(count)), Toast.LENGTH_SHORT).show();
        return false;
    }
}

/*
  ImageView capturedImageView = (ImageView)findViewById(R.id.capturedimage);

  MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

  mediaMetadataRetriever.setDataSource(uri);
  Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(5000000); //unit in microsecond
  capturedImageView.setImageBitmap(bmFrame);
 */


//overlay view
//compare frame mark the pixels
//create bounds
//detect to see if clicked within bounds in the time frame
//make note of times to change videos

//videoview multiple tracks
//load videos in as files and play so it puts them in ram
