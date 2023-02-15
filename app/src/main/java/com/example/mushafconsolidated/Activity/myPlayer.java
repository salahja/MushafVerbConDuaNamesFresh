package com.example.mushafconsolidated.Activity;

import static com.example.mushafconsolidated.Activity.ShowMushafActivity.readerID;
import static com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE;

import android.content.Context;

import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.receivers.FileManager;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class myPlayer {

    private final int surah;
    private List<MediaItem> marray;
    protected PlayerControlView playerView;

   private ExoPlayer player;
Context context;
    private TrackSelectionParameters trackSelectionParameters;
    private Tracks lastSeenTracks;
    private boolean isMusicplaying;
    private boolean pausePlayFlag;
    private int startItemIndex;
    private boolean rangeRecitation;
    private List<MediaItem> marrayrange;
    private int endRange;
    private int startRange;
    private boolean resume;
    private int resumelastplayed;

    public myPlayer(Context context,int surah,List<MediaItem> marray) {
        this.context=context;
        this.surah=surah;
        this.marray=marray;
    }

    public ExoPlayer initplayer(List<MediaItem> marray) {
        if (player != null) {
            player.release();
        }

        marray = createMediaItems();
        if (marray.isEmpty()) {
            return null;
        }

        player = new ExoPlayer.Builder(context).build();
        lastSeenTracks = Tracks.EMPTY;

        player.setTrackSelectionParameters(trackSelectionParameters);

        player.addAnalyticsListener(new EventLogger());
        player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
        player.setPlayWhenReady(true);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);

        playerView.setRepeatToggleModes(REPEAT_TOGGLE_MODE_ONE);




        playerView.setPlayer(player);
        player.addListener(new Player.Listener() {


            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (player.getPlayWhenReady() && playbackState == Player.STATE_READY) {
                    isMusicplaying = true;// media actually playing
                } else if (player.getPlayWhenReady()) {
                    isMusicplaying = false;
                    // might be idle (plays after prepare()),
                    // buffering (plays when data available)
                    // or ended (plays when seek away from end)
                } else {
                    pausePlayFlag = true;
                    // player paused in any state
                }
                Player.Listener.super.onPlaybackStateChanged(playbackState);
            }
        });

        boolean haveStartPosition = startItemIndex != C.INDEX_UNSET;
        if (haveStartPosition) {
            //    player.seekTo(startItemIndex, startPosition);
        }

        if(rangeRecitation) {
            marrayrange = marray.subList(startRange, endRange);
            player.setMediaItems(marrayrange, /* resetPosition= */ !haveStartPosition);
        }else {
            player.setMediaItems(marray, /* resetPosition= */ !haveStartPosition);
        }


        player.prepare();

        if (resume) {
            player.seekToDefaultPosition(resumelastplayed);
        }

            player.play();


        return player;
    }

    private List<MediaItem> createMediaItems() {
        Utils repository = new Utils(context);
        List<ChaptersAnaEntity> chap = repository.getSingleChapter(surah);


        List<String> ayaLocations = new ArrayList<>();

        marray = new ArrayList<>();

            List<QuranEntity> quranbySurah = Utils.getQuranbySurah(surah);
            for (QuranEntity ayaItem : quranbySurah) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(context, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(context, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));

            }


        return marray;


    }
}
