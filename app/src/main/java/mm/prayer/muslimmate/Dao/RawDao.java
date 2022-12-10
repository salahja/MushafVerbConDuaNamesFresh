package mm.prayer.muslimmate.Dao;

import androidx.room.Dao;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import mm.prayer.muslimmate.ui.LocationInfo;

@Dao
public interface RawDao {


    @RawQuery
    LocationInfo getLocinfo(SupportSQLiteQuery query);
}
