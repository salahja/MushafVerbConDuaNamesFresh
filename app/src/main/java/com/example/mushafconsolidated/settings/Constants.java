package com.example.mushafconsolidated.settings;

import android.os.Environment;

import com.example.mushafconsolidated.R;
import com.example.utility.QuranGrammarApplication;

import java.io.File;

public class
Constants {
    //  String DownloadLink = "http://192.168.29.28.xip.io/drupal/sites/default/files/2021-04/QuranImages.zip";
    // data domain
    public static final String HOST = "http://192.168.29.28.sslip.io/drupal/sites/default/files/2021-05/";
    //     public static final String HOSTDATABASEURL ="http://192.168.29.28.xip.io/drupal/sites/default/files/2021-04/QuranDatabase.db_3.zip";
    //   public static final String HOSTDATABASEURL ="http://localhost/drupal/sites/default/files/2021-04/QuranDatabase.db_3.zip";
    public static final String HOSTQURNPAGESURL = "http://192.168.29.28.xip.io/drupal/sites/default/files/2021-04/QuranImages.zip";
    //public static final String HOSTDATABASEURL=  "http://192.168.29.28.sslip.io/drupal/sites/default/files/2021-05/QuranDatabase_0.zip";
    public static final String HOSTDATABASEURL = "http://192.168.29.28.sslip.io/drupal/sites/default/files/2021-05/QuranDatabase.zip";
    public static final String HOSTQURANTRANSLATION = "http://192.168.29.28.sslip.io/drupal/sites/default/files/2021-05/";
    public static final String DATABASEZIP = "qurangrammar.db.zip";
    // public static final String DATABASENAME="QuranDatabase.db";
    public static final String DATABASENAME = "qurangrammar.db";
    public static final String QURANZIP = "QuranImages.zip";
    public static final String VERBDATABASE = "conjugator.db";
    public static final String SQLDATABASENAME = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Mushafapplication" + File.separator + DATABASENAME;
    // Numerics
    public static final int DEFAULT_NIGHT_MODE_TEXT_BRIGHTNESS = 255;
    public static final int DEFAULT_TEXT_SIZE = 15;
    // 10 days in ms
    public static final int TRANSLATION_REFRESH_TIME = 60 * 60 * 24 * 10 * 1000;
    // 1 hour in ms
    public static final int MIN_TRANSLATION_REFRESH_TIME = 60 * 60 * 1000;
    // quranapp
    public static final String QURAN_APP_BASE = "http://quranapp.com/";
    public static final String QURAN_APP_ENDPOINT = "http://quranapp.com/note";
    // Notification Ids
    public static final int NOTIFICATION_ID_DOWNLOADING = 1;
    public static final int NOTIFICATION_ID_DOWNLOADING_COMPLETE = 2;
    public static final int NOTIFICATION_ID_DOWNLOADING_ERROR = 3;
    public static final int NOTIFICATION_ID_AUDIO_PLAYBACK = 4;
    public static final int NOTIFICATION_ID_AUDIO_UPDATE = 5;
    // Notification channels
    public static final String AUDIO_CHANNEL = "quran_audio_playback";
    public static final String DOWNLOAD_CHANNEL = "quran_download_progress";
    // Unique work names
    public static final String AUDIO_UPDATE_UNIQUE_WORK = "audio_update_unique_work";
    // Settings Key (some of these have corresponding values in preference_keys.xml)
    public static final String PREF_SHOW_ERAB = "showErabKey";
    public static final String PREF_SHOW_TRANSLATION = "showTranslationKey";
    public static final String PREF_APP_LOCATION = "appLocation";
    public static final String PREF_USE_ARABIC_NAMES = "useArabicNames";
    public static final String PREF_LAST_PAGE = "lastPage";
    public static final String PREF_LOCK_ORIENTATION = "lockOrientation";
    public static final String PREF_LANDSCAPE_ORIENTATION = "landscapeOrientation";
    public static final String PREF_TRANSLATION_TEXT_SIZE = "translationTextSize";
    public static final String PREF_ACTIVE_TRANSLATION = "activeTranslation";
    public static final String PREF_ACTIVE_TRANSLATIONS = "activeTranslations";
    public static final String PREF_NIGHT_MODE = "nightMode";
    public static final String PREF_NIGHT_MODE_TEXT_BRIGHTNESS = "nightModeTextBrightness";
    public static final String PREF_DEFAULT_QARI = "defaultQari";
    public static final String PREF_SHOULD_FETCH_PAGES = "shouldFetchPages";
    public static final String PREF_OVERLAY_PAGE_INFO = "overlayPageInfo";
    public static final String PREF_DISPLAY_MARKER_POPUP = "displayMarkerPopup";
    public static final String PREF_IMMERSIVE_IN_PORTRAIT = "immersiveInPortrait";
    public static final String PREF_HIGHLIGHT_BOOKMARKS = "highlightBookmarks";
    public static final String PREF_AYAH_BEFORE_TRANSLATION =
            "ayahBeforeTranslation";
    public static final String PREF_PREFER_STREAMING = "preferStreaming";
    public static final String PREF_DOWNLOAD_AMOUNT = "preferredDownloadAmount";
    public static final String PREF_LAST_UPDATED_TRANSLATIONS =
            "lastTranslationsUpdate";
    public static final String PREF_HAVE_UPDATED_TRANSLATIONS =
            "haveUpdatedTranslations";
    public static final String PREF_USE_NEW_BACKGROUND = "useNewBackground";
    public static final String PREF_USE_VOLUME_KEY_NAV = "volumeKeyNavigation";
    public static final String PREF_SORT_BOOKMARKS = "sortBookmarks";
    public static final String PREF_GROUP_BOOKMARKS_BY_TAG = "groupBookmarksByTag";
    public static final String PREF_SHOW_RECENTS = "showRecents";
    public static final String PREF_SHOW_DATE = "showDate";
    public static final String PREF_VERSION = "version";
    public static final String PREF_DEFAULT_IMAGES_DIR = "defaultImagesDir";
    public static final String PREF_TRANSLATION_MANAGER = "translationManagerKey";
    public static final String PREF_AUDIO_MANAGER = "audioManagerKey";
    public static final String PREF_PAGE_TYPE = "pageTypeKey";
    public static final String PREF_DID_PRESENT_PERMISSIONS_DIALOG =
            "didPresentStoragePermissionDialog";
    public static final String PREF_WAS_SHOWING_TRANSLATION = "wasShowingTranslation";
    public static final String DEBUG_DID_DOWNLOAD_PAGES = "debugDidDownloadPages";
    public static final String DEBUG_PAGE_DOWNLOADED_PATH = "debugPageDownloadedPath";
    public static final String DEBUG_PAGES_DOWNLOADED_TIME = "debugPagesDownloadedTime";
    public static final String DEBUG_PAGES_DOWNLOADED = "debugPagesDownloaded";
    public static final String PREF_SURA_TRANSLATED_NAME = "suraTranslatedName";
    public static String FILEPATH = QuranGrammarApplication.getContext().getExternalFilesDir(null).
            getAbsolutePath() + "/" + QuranGrammarApplication.getContext().getResources().getString(R.string.app_folder_path);


    int GREEN = 2;
    int YELLOW = 1;
    int WHITE = 0;

    public static String SURAH_INDEX = "index";
    public static String MUSHAFDISPLAY = "display";

    public static String LAST_INDEX = "index_sura";
    public static String LAST_INDEX_Scroll = "scroll";
    public static String PERMISSION_STATE = "state";
    public static String NightMode_STATE = "night mode ";
    public static String BackColor_STATE = "bg_color";
    public static String SCORE = "score";

    public static String USERS_KEY = "users";
    public static String REVIWES_KEY = "reviews";
    public static String USER_NAME = "user_name";
    public static String USER_UUID = "uuid";
    public static String PAGE_INDEX = "pageIndex";
    public static String JUZ_INDEX = "Juz";
    public static String SURAH_GO_INDEX = "goSuraIndex";
    public static String AYAH_GO_INDEX = "ayahGo";
    int NA = -1;
    public static String RECORD_ITEM = "record";
    public static String AUDIO_ITEMS = "ayahitems";
}
