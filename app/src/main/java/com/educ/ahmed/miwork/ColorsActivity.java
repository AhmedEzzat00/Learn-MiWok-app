package com.educ.ahmed.miwork;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {


    //creating Audio requirements
    private MediaPlayer mediaPlayer;

    //Audio Focus to detect the sound controller
    private AudioManager audioManager;

    //creating a listener to detect if the audio has been completed

    private MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    //This listener gets triggered whenever the audio focus changes
    //Gain,Loss,Loss Transient.....

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int foucsChangeed) {
            if (foucsChangeed==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ||
                    foucsChangeed==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // Pause playback and reset player to the start of the file. That way, we can
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(foucsChangeed== AudioManager.AUDIOFOCUS_GAIN)
            {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            }
            else if(foucsChangeed== AudioManager.AUDIOFOCUS_LOSS)
            {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_word);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //Creating Color array words
        final ArrayList<Word> words=new ArrayList<>();

        //Writing the content
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,
                R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,
                R.raw.color_dusty_yellow));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));

        //Initializing the word adapter with context , array of colors and
        //the color background colorID

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);


        //Linking the list of items with the color array and the adapter
        //Initializing the listview
        ListView listView= (ListView) findViewById(R.id.list_view);

        //Linking
        listView.setAdapter(adapter);

        //Audio preparing
        //Set the listener (onLongClick) test
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Release the mediaplayer if it exist
                //get ready for playing another one
                releaseMediaPlayer();

                //get the current word(color)
                Word word=words.get(position);

                // Request audio focus so in order to play the audio file.
                int result= audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC
                ,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);//AUDIOFOCUS_GAIN_TRANSIENT means for a short time

                //if we gain the audio foucs play the voice
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    //Setup the mediaplayer for audio resource
                    mediaPlayer=MediaPlayer.create(ColorsActivity.this,word.getAudioResourceID());

                    //starting the file
                    mediaPlayer.start();

                    //Setup the on completion listener
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }

            }
        });




    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){

        //check for the existance of the media player object
        if(mediaPlayer!=null){
            //Release the source of the mediaplayer
            mediaPlayer.release();

            //setting the mediaplayer to null to tell that the mediaplayer is not
            ///configured to play sound
            mediaPlayer=null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

}
