package com.example.mushafconsolidated.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.receivers.FileManager;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.audio.AudioAttributes;

import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.DebugTextViewHelper;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/** An activity that plays media using {@link ExoPlayer}. */
public class PlayerActivity extends AppCompatActivity
        implements OnClickListener, StyledPlayerView.ControllerVisibilityListener ,StyledPlayerView.FullscreenButtonClickListener{

    // Saved instance state keys.

    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    private static final String KEY_ITEM_INDEX = "item_index";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    protected StyledPlayerView playerView;
    protected LinearLayout debugRootView;
    protected TextView debugTextView;
    protected @Nullable ExoPlayer player;

    private TrackSelectionParameters trackSelectionParameters;
    private DebugTextViewHelper debugViewHelper;
    private Tracks lastSeenTracks;
    private boolean startAutoPlay;
    private int startItemIndex;
    private long startPosition;
    // For ad playback only.

    @Nullable private AdsLoader clientSideAdsLoader;
    private List<MediaItem> marray;

    // TODO: Annotate this and serverSideAdsLoaderState below with @OptIn when it can be applied to
    // fields (needs http://r.android.com/2004032 to be released into a version of
    // androidx.annotation:annotation-experimental).




    // Activity lifecycle.

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  dataSourceFactory = DemoUtil.getDataSourceFactory(/* context= */ this);

        setContentView();
        debugRootView = findViewById(R.id.controls_root);
        debugTextView = findViewById(R.id.debug_text_view);
        Button selectTracksButton = findViewById(R.id.select_tracks_button);
        selectTracksButton.setOnClickListener(this);

        playerView = findViewById(R.id.player_view);
        playerView.setControllerVisibilityListener(this);
        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        playerView.requestFocus();



        if (savedInstanceState != null) {
            trackSelectionParameters =
                    TrackSelectionParameters.fromBundle(
                            savedInstanceState.getBundle(KEY_TRACK_SELECTION_PARAMETERS));
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startItemIndex = savedInstanceState.getInt(KEY_ITEM_INDEX);
            startPosition = savedInstanceState.getLong(KEY_POSITION);

        } else {
            trackSelectionParameters = new TrackSelectionParameters.Builder(/* context= */ this).build();
            clearStartPosition();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        releasePlayer();
        releaseClientSideAdsLoader();
        clearStartPosition();
        setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
        if (playerView != null) {
            playerView.onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (playerView != null) {
            playerView.onPause();
        }
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseClientSideAdsLoader();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0) {
            // Empty results are triggered if a permission is requested while another request was already
            // pending and can be safely ignored in this case.
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializePlayer();
        } else {
            showToast(R.string.storage_permission_denied);
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateTrackSelectorParameters();
        updateStartPosition();
        outState.putBundle(KEY_TRACK_SELECTION_PARAMETERS, trackSelectionParameters.toBundle());
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_ITEM_INDEX, startItemIndex);
        outState.putLong(KEY_POSITION, startPosition);

    }

    // Activity input

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // See whether the player view wants to handle media or DPAD keys events.
        return playerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    // OnClickListener methods



    // StyledPlayerView.ControllerVisibilityListener implementation

    @Override
    public void onVisibilityChanged(int visibility) {
        debugRootView.setVisibility(visibility);
    }

    // Internal methods

    protected void setContentView() {
        setContentView(R.layout.ex_player_vtwo);
    }

    /**
     * @return Whether initialization was successful.
     */
    protected boolean initializePlayer() {
        if (player == null) {
            createPlaylist();
            marray = createMediaItems();
            if (marray.isEmpty()) {
                return false;
            }
            player = new ExoPlayer.Builder(this).build();
            lastSeenTracks = Tracks.EMPTY;
            //     ExoPlayer.Builder playerBuilder=new ExoPlayer.Builder((Context) this);
       /*     ExoPlayer.Builder playerBuilder =new ExoPlayer.Builder(*//* context= *//* this)
                            .setMediaSourceFactory(createMediaSourceFactory());
            setRenderersFactory(
                    playerBuilder, intent.getBooleanExtra(IntentUtil.PREFER_EXTENSION_DECODERS_EXTRA, false));*/
       //     player = playerBuilder.build();
            player.addListener(new PlayerEventListener());
            player.setTrackSelectionParameters(trackSelectionParameters);
            player.addListener(new PlayerEventListener());
            player.addAnalyticsListener(new EventLogger());
            player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
            player.setPlayWhenReady(startAutoPlay);
            int currentItem = 0;
            long playbackPosition = 0L;
            player.seekTo(currentItem, playbackPosition);

            playerView.setPlayer(player);

            debugViewHelper = new DebugTextViewHelper(player, debugTextView);
            debugViewHelper.start();
        }
        boolean haveStartPosition = startItemIndex != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startItemIndex, startPosition);
        }
        player.setMediaItems(marray, /* resetPosition= */ !haveStartPosition);
        player.prepare();
        //updateButtonVisibility();
        return true;
    }

    public List<String> createPlaylist() {

        List<QuranEntity> quranbySurah = Utils.getQuranbySurah(9);

        List<String> downloadLinks = new ArrayList<>();
        //   ayaList.add(0, new Aya(1, 1, 1));
        //loop for all page ayat
        List<String> ayaLocations = new ArrayList<>();
        ArrayList<MediaItem> marray=new ArrayList<>();
        //Create files locations for the all page ayas
        for (QuranEntity ayaItem : quranbySurah) {
            ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, 20, ayaItem.getAyah(), ayaItem.getSurah()));
            String location = FileManager.createAyaAudioLinkLocation(this, 20, ayaItem.getAyah(), ayaItem.getSurah());
            marray.add(MediaItem.fromUri(location));

        }


        return downloadLinks;
    }





    protected void releasePlayer() {
        if (player != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
         //   releaseServerSideAdsLoader();
            debugViewHelper.stop();
            debugViewHelper = null;
            player.release();
            player = null;
            playerView.setPlayer(/* player= */ null);
        }
        if (clientSideAdsLoader != null) {
            clientSideAdsLoader.setPlayer(null);
        } else {
            Objects.requireNonNull(playerView.getAdViewGroup()).removeAllViews();
        }
    }



    private void releaseClientSideAdsLoader() {
        if (clientSideAdsLoader != null) {
            clientSideAdsLoader.release();
            clientSideAdsLoader = null;
            Objects.requireNonNull(playerView.getAdViewGroup()).removeAllViews();
        }
    }



    private void updateTrackSelectorParameters() {
        if (player != null) {
            trackSelectionParameters = player.getTrackSelectionParameters();
        }
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startItemIndex = player.getCurrentMediaItemIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }

    protected void clearStartPosition() {
        startAutoPlay = true;
        startItemIndex = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    // User controls


    private void showControls() {
        debugRootView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFullscreenButtonClick(boolean isFullScreen) {

    }

    private class PlayerEventListener implements Player.Listener {

        @Override
        public void onPlaybackStateChanged(@Player.State int playbackState) {
            if (playbackState == Player.STATE_ENDED) {
                showControls();
            }
       //     updateButtonVisibility();
        }

        @Override
        public void onPlayerError(PlaybackException error) {
            if (error.errorCode == PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW) {
                assert player != null;
                player.seekToDefaultPosition();
                player.prepare();
            } else {
           //     updateButtonVisibility();
                showControls();
            }
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(@NonNull Tracks tracks) {
         //   updateButtonVisibility();
            assert player != null;
            int ayah=      player.getCurrentMediaItemIndex();
            System.out.println("Ayah"+""+ayah);

            if (tracks == lastSeenTracks) {
                return;
            }

            if (tracks.containsType(C.TRACK_TYPE_AUDIO)
                    && !tracks.isTypeSupported(C.TRACK_TYPE_AUDIO, /* allowExceedsCapabilities= */ true)) {
                showToast(R.string.error_unsupported_audio);
            }
            lastSeenTracks = tracks;
        }
    }

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<PlaybackException> {

        @NonNull
        @Override
        public Pair<Integer, String> getErrorMessage(PlaybackException e) {
            String errorString = getString(R.string.error_generic);
            Throwable cause = e.getCause();
            if (cause instanceof DecoderInitializationException) {
                // Special case for decoder initialization failures.
                DecoderInitializationException decoderInitializationException =
                        (DecoderInitializationException) cause;
                if (decoderInitializationException.codecInfo == null) {
                    if (decoderInitializationException.getCause() instanceof DecoderQueryException) {
                        errorString = getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString =
                                getString(
                                        R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                    } else {
                        errorString =
                                getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                    }
                } else {
                    errorString =
                            getString(
                                    R.string.error_instantiating_decoder,
                                    decoderInitializationException.codecInfo.name);
                }
            }
            return Pair.create(0, errorString);
        }
    }

    private List<MediaItem> createMediaItems() {

        List<QuranEntity> quranbySurah = Utils.getQuranbySurah(9);

        //   int ayaID=0;
        //   quranbySurah.add(0, new QuranEntity(1, 1, 1));
        //   ayaList.add(0, new Aya(1, 1, 1));
        //loop for all page ayat
        List<String> ayaLocations = new ArrayList<>();
        marray=new ArrayList<>();
        //Create files locations for the all page ayas
        for (QuranEntity ayaItem : quranbySurah) {
            ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, 20, ayaItem.getAyah(), ayaItem.getSurah()));
            String location = FileManager.createAyaAudioLinkLocation(this, 20, ayaItem.getAyah(), ayaItem.getSurah());
            marray.add(MediaItem.fromUri(location));

        }



        return marray;
    }

}
