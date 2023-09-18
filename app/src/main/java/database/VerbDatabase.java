package database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import database.Dao.kovDao;
import database.Dao.mazeedDao;
import database.Dao.mujarradDao;
import database.Dao.verbcorpusDao;
import database.entity.BuckwaterEntitiy;
import database.entity.Mazeed;
import database.entity.MujarradVerbs;
import database.entity.QuranVerbsEntity;

import database.Dao.BuckwaterDao;
import database.Dao.QuranVerbsDao;

import database.entity.kov;
import database.entity.verbcorpus;



@Database(entities = {Mazeed.class,MujarradVerbs.class,kov.class,verbcorpus.class,  BuckwaterEntitiy.class, QuranVerbsEntity.class }, version = 2)

public abstract class VerbDatabase extends RoomDatabase {

    public static VerbDatabase verbDatabaseInstance;
    private static final Callback initialCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //  new InitialAsyncTask(quranAppDatabaseInstance).execute();
        }
    };

    public static synchronized VerbDatabase getInstance(Context context) {
        if (null == verbDatabaseInstance) {


            verbDatabaseInstance = Room.databaseBuilder(context,  VerbDatabase.class, "v3-conjugator.db")
                    .createFromAsset("databases/v3-conjugator.db")

         .fallbackToDestructiveMigration()
                    .addCallback(initialCallBack)
                    .allowMainThreadQueries()
                    .build();

        }

        return verbDatabaseInstance;
    }


    public abstract BuckwaterDao BuckwaterDao();

    public abstract QuranVerbsDao QuranVerbsDao();
    public abstract verbcorpusDao verbcorpusDao();
  public abstract kovDao kovDao();
    public abstract mujarradDao mujarradDao();
    public abstract mazeedDao mazeedDao();

}
