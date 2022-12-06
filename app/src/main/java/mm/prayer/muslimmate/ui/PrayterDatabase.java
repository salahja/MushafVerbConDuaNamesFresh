package mm.prayer.muslimmate.ui;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

 

import mm.prayer.muslimmate.entity.Cities;
import mm.prayer.muslimmate.entity.CitiesDAO;
import mm.prayer.muslimmate.entity.Countries;
import mm.prayer.muslimmate.entity.CountryDao;
import mm.prayer.muslimmate.entity.RawDao;

import java.io.File;
import com.example.mushafconsolidated.R;
import com.example.utility.QuranGrammarApplication;

//@Database(entities= {VerseEntit.class,ErabEntity.class,ChaptersAnaEntity.class},version= 1)
@Database(entities = {Cities.class, Countries.class }, version = 1)
public abstract class PrayterDatabase extends RoomDatabase {

    //  public static Builder<QuranAppDatabase> quranAppDatabaseInstance;
    private static final RoomDatabase.Callback initialCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //  new InitialAsyncTask(quranAppDatabaseInstance).execute();
        }
    };
    public static PrayterDatabase prayterDatabaseInstance;
    public static PrayterDatabase prayterDatabaseInstanceasset;

    public static synchronized PrayterDatabase getInstance() {
        return getInstance();
    }

    public static synchronized PrayterDatabase getInstance(Context context) {
        if (null == prayterDatabaseInstance) {

 prayterDatabaseInstanceasset = Room.databaseBuilder(context, PrayterDatabase.class, "muslim.db")
                  .createFromAsset("databases/muslim.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(initialCallBack)
                    .allowMainThreadQueries()
              //   .openHelperFactory(factory)
                     .build();



        }
  return prayterDatabaseInstanceasset;
    }

    public abstract CountryDao CountryDao();
    // public abstract WordbywordPojoDao WordbywordPojoDao();

    public abstract CitiesDAO CitiesDAO();
    public abstract RawDao RawDao();

}
