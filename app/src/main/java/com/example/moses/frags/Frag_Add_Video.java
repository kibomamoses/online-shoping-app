package com.example.moses.frags;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moses.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Add_Video extends Fragment {

    Button sel,sendvid;
    TextView metas;
    VideoView mplay;

    int lengt,width,heigh;
    boolean vidpres=false;

    private Uri uri_vid ;

    int reqcod=20,coun=0;

    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"));
    int index = 0;

    public Frag_Add_Video() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_add_video, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add a Video");

        sel = fraghome.findViewById(R.id.buttonSEL);
        sendvid = fraghome.findViewById(R.id.buttonUo);

        metas = fraghome.findViewById(R.id.metas);
        metas.setVisibility(View.INVISIBLE);
        mplay = fraghome.findViewById(R.id.viuvid);

        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picka();
            }
        });

        return fraghome;
    }

    private void Picka(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),reqcod);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == reqcod) {
                String path =  (String)getPath(data.getData());
                metas.setText(path);
                mplay.setVideoURI(data.getData());
                mplay.start();

                MediaController controller = new MediaController(getContext());
                controller.setMediaPlayer(mplay);
                controller.setAnchorView(mplay);
                mplay.setMediaController(controller);

                MediaMetadataRetriever metaRetriver;
                metaRetriver = new MediaMetadataRetriever();
                metaRetriver.setDataSource(getContext(),data.getData());

                Long secon= TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));

                String met="Bitrate -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)+"\n"
                        +"Duration -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)+"\n"
                        +"Has Video -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)+"\n"
                        +"Has Audio -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO)+"\n"
                        +"Video Height -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)+"\n"
                        +"Video Width -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)+"\n"
                        +"Capture Frame Rate -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE)+"\n";

                lengt= (Integer.parseInt(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)))/1000;
                width=(Integer.parseInt(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)));
                heigh=(Integer.parseInt(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)));

                if (metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)=="yes"){
                    vidpres=true;
                }

                metas.append(met+"\n Duration -> "+secon);

                Allow();

                uri_vid=data.getData();

                metas.setVisibility(View.VISIBLE);

            }
        }
    }

    private void Allow(){
        vidpres=true;
        if (vidpres){
            if (lengt <30 && width > 1200 && heigh > 710){
                metas.append("Proceeds");
            }else {
                if (lengt > 30){
                    metas.append("Time Limit exceeded");
                }
                if (width < 1200){
                    metas.append("Width requirement not met");
                }
                if (heigh < 700){
                    metas.append("Height requirement not met");
                }
            }
        }else{
            metas.append("Selected File has no video Stream");
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
