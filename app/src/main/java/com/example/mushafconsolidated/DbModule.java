package com.example.mushafconsolidated;


import android.content.Context;
import androidx.room.Room;

import com.example.mushafconsolidated.DAO.QuranDao;
import com.example.mushafconsolidated.Entities.QuranEntity;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Module

@InstallIn({SingletonComponent.class})
public final class DbModule {
    @Provides
    @Singleton
    @NotNull
    public final QuranAppDatabase Providedatabase(@ApplicationContext @NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String QURANDATABASE = "QURAN";
        return (QuranAppDatabase)Room.databaseBuilder(context, QuranAppDatabase.class, QURANDATABASE).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    @NotNull
    public final QuranDao provideDao(@NotNull QuranAppDatabase db) {

        return db.QuranDao();
    }

    @Provides
    @Singleton
    @NotNull
    public final QuranEntity provideEntity() {
        return new QuranEntity();
    }
}
