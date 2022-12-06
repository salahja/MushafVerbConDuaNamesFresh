package mm.prayer.muslimmate.entity;

import android.content.Context;

import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;

import mm.prayer.muslimmate.ui.LocationInfo;
import mm.prayer.muslimmate.ui.PrayterDatabase;

public class DbUtility {

    private static final String TAG = "Utils";
    private static PrayterDatabase database;
    final Context thiscontext;

    public DbUtility(Context context) {
        database = PrayterDatabase.getInstance(context);
        this.thiscontext = context;



    }


    public  List<Countries> getAllCountries() {
        return database.CountryDao().getAllCountries();

    }

    public static List<Cities> getAllCities() {
        return database.CitiesDAO().getCities();

    }



    public static LocationInfo getLocinfo(Double latitude, Double longitude) {
        String sqlverb = "select cityd.latitude,cityd.longitude,cityd.time_zone,Countries.Number,Countries.mazhab,Countries.way,Countries.dls,Countries.En_Name,Countries.En_Full_Name,\n" +
                "Countries.iso3,Countries.Ar_Name,Countries.Continent_Code from cityd ,countries" +
                "    where   CorpusExpand.surah = \""
                + latitude + "\""
                + "and Corpusexpand.ayah=\""
                + longitude + "\""
               ;
        String sqls = "select  b.En_Name , b.Ar_Name , b.iso3 , a.city ," +
                " b.Continent_Code ,  b.number  , b.mazhab , b.way , b.dls , a.time_zone ," +
                " (latitude - " + latitude + ")*(latitude - " + latitude + ")+(longitude - " + longitude + ")" +
                "*(longitude - " + longitude + ") as ed , a.Ar_Name  from cityd a , countries b where" +
                " b.code = a.country order by ed asc limit 1;";


        SimpleSQLiteQuery query = new SimpleSQLiteQuery(sqls);
        //  List<Book> result = booksDao.getBooks(query);
        return  database.RawDao().getLocinfo(query);
    }


    public List<Countries> GetCountryCitycode(String code) {
        return database.CountryDao().getCountryCodebycityname(code);

    }
    public List<Countries> getCCwithCityname(String code) {
        return database.CountryDao().getCountryCodebycityname(code);

    }
    public List<Cities> GetCitycode(String code) {
        return database.CitiesDAO().getCityCode(code);

    }
}
