package com.example.mushafconsolidated.Activity;

import static com.example.mushafconsolidated.settings.Constants.DATABASENAME;
import static com.example.mushafconsolidated.settings.Constants.DATABASEZIP;
import static com.example.mushafconsolidated.settings.Constants.FILEPATH;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;

import com.example.mushafconsolidated.Adapters.PageAdapter;
import com.example.mushafconsolidated.Entities.Page;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_WRITE_STORAGE = 112;
    private File newquran;
    RecyclerView recview;
    private List<Page> fullQuranPages;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        switchTheme("brown");
        super.onCreate(savedInstanceState);
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        computeWindowSizeClasses();
        //  setContentView(R.layout.fragment_reading);
        setContentView(R.layout.main_activity);
      recview= findViewById(R.id.recycler_views);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int SPL = 1;
        if (sp.getInt("spl", 0) != SPL) {
            PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
            //  PreferenceManager.setDefaultValues(this, R.xml.prefs2, true);
            sp.edit().putInt("spl", SPL).apply();
        }
        newquran = new File(FILEPATH + "/" + DATABASENAME);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        } else {
            try {
                validateFilesAndDownload();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //  PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }

    private void computeWindowSizeClasses() {
        WindowMetrics metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
        float widthDp = metrics.getBounds().width() / getResources().getDisplayMetrics().density;
        if (widthDp < 600f) {
            editor.putString("width", "compactWidth");
            editor.apply();

        } else if (widthDp < 840f) {
            editor.putString("width", "mediumWidth");
            editor.apply();
        } else {
            // widthWindowSizeClass = WindowSizeClass.EXPANDED;
            editor.putString("width", "expandedWidth");
            editor.apply();

        }
        float heightDp = metrics.getBounds().height() / getResources().getDisplayMetrics().density;
        WindowSizeClass heightWindowSizeClass;
        if (heightDp < 480f) {
            heightWindowSizeClass = WindowSizeClass.COMPACT;

        } else if (heightDp < 900f) {
            heightWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            heightWindowSizeClass = WindowSizeClass.EXPANDED;
        }
        // Use widthWindowSizeClass and heightWindowSizeClass
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check if permission had taken or not
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //valid to download or not
                try {
                    validateFilesAndDownload();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, getString(R.string.permission), Toast.LENGTH_LONG).show();
                MainActivity.this.finish();
            }
        }
    }

    private void validateFilesAndDownload() throws IOException {
        if (!newquran.exists()) {
            // first install copy newquran.db.zip and unzip
            //   new CopyDatabase().execute();
            CopyDatbases();

        } else {
      /*      LinearLayoutManager ln=new LinearLayoutManager(this);
            recview.setLayoutManager(ln);
                List<Page> pages = new ArrayList<>();
                Page page;
                Utils utils=new Utils(this);
                List<QuranEntity> ayahItems;
                for (int i = 1; i <= 604; i++) {
                    ayahItems = utils.getAyahsByPage(i);
                    if (ayahItems.size() > 0) {
                        page = new Page();
                        page.setAyahItems(ayahItems);
                        page.setPageNum(i);
                        page.setJuz(ayahItems.get(0).getJuz());
                        pages.add(page);
                    }
                }

                fullQuranPages = new ArrayList<>(pages);
            PageAdapter pageAdapter=new PageAdapter(pages,this);
            recview.setAdapter(pageAdapter);*/


           Intent homeactivity = new Intent(MainActivity.this, QuranGrammarAct.class);

          startActivity(homeactivity);
         //   MainActivity.this.finish();
            //   initnavigation();
        }

    }



    private void CopyDatbases() {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        AlertDialog dialog = builder.create();
        ex.execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(dialog::show);
                boolean canWrie = canWriteInSDCard();
                if (canWrie) {
                    try {
                        File databaseDirectory = new File(FILEPATH);
                        if (!databaseDirectory.exists()) {
                            boolean cr = databaseDirectory.mkdirs();
                            System.out.println(cr);
                        }
                        File databaseFile = new File(databaseDirectory, DATABASEZIP);
                        databaseFile.getParentFile();
                        if (!databaseFile.exists()) {
                            databaseFile.createNewFile();
                        }
                        //    InputStream inputStream = getApplicationContext().getAssets().open("newquran.db");
                        InputStream inputStream = getApplicationContext().getAssets().open(DATABASEZIP);
                        FileOutputStream outputStream = new FileOutputStream(databaseFile);
                        //   publishProgress(0, fileSize);
                        int copylength = 0;
                        byte[] buffer = new byte[1024];
                        while (true) {
                            int read = inputStream.read(buffer);
                            if (read == -1) break;
                            copylength += read;
                            //   publishProgress(copylength, fileSize);
                            outputStream.write(buffer, 0, read);
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();

                    }
                }
                runOnUiThread(() -> {
                    File zipfile = new File(getExternalFilesDir(null).getAbsolutePath() + getString(R.string.app_folder_path) + File.separator + DATABASEZIP);
                    File targetDirectory = new File(FILEPATH);
                    File mainDatabasesZIP = new File(String.valueOf(zipfile));
                    ZipInputStream zis = null;
                    int progress = 1;
                    try {
                        zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(mainDatabasesZIP)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(MainActivity.this);
                        dialog1.setMessage(e.getCause().toString());
                        AlertDialog alertDialog = dialog1.create();
                        alertDialog.show();

                    }
                    try {
                        ZipEntry ze;
                        int count;
                        byte[] buffer = new byte[8192];
                        while ((ze = zis.getNextEntry()) != null) {
                            File file = new File(targetDirectory, ze.getName());
                            File dir = ze.isDirectory() ? file : file.getParentFile();
                            if (!dir.isDirectory() && !dir.mkdirs())
                                throw new FileNotFoundException("Failed to ensure directory: " + dir.getAbsolutePath());
                            if (ze.isDirectory()) continue;
                            try (FileOutputStream fout = new FileOutputStream(file)) {
                                progress += 1;
                                while ((count = zis.read(buffer)) != -1) {
                                    fout.write(buffer, 0, count);
                                    progress += 1;
                                    //   progressBarDD.setProgress(progress);
                                }
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            zis.close();
                            mainDatabasesZIP.delete();
                            //     progressBarDD.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    ex.shutdown();
                    dialog.dismiss();
                    Intent zipintent = new Intent(MainActivity.this, QuranGrammarAct.class);
                    startActivity(zipintent);
                    MainActivity.this.finish();

                });

            }

            private boolean canWriteInSDCard() {
                String state = Environment.getExternalStorageState();
                return Environment.MEDIA_MOUNTED.equals(state);
            }
        });

    }

    public boolean getDefaultSaveRootPath() {
        boolean useExternalStorage = false;
        boolean mounted = Environment.getExternalStorageState().equals("mounted");
        final long freeSpace = Environment.getExternalStorageDirectory().getFreeSpace();
        if (mounted) {
            if (freeSpace > 0) {
                useExternalStorage = true;

            }
        }
        return useExternalStorage;
    }

    public enum WindowSizeClass {COMPACT, MEDIUM, EXPANDED}

}


