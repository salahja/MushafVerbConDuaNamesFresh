package com.example.mushafconsolidated.quranrepo;



import androidx.lifecycle.LiveData;
import com.example.mushafconsolidated.DAO.QuranDao;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;


public final class testrep {
    private final QuranDao qurandao;

    @NotNull
    public final LiveData allquran() {
        return (LiveData) this.qurandao.getAllQuran();
    }


    @NotNull
    public final List getQuranCorpusWbwbysurah(int cid) {
        return this.qurandao.getQuranCorpusWbwbysurah(cid);
    }



    @NotNull
    public final List getQuranCorpusWbw(int surah, int ayah, int wordno) {
        return this.qurandao.getQuranCorpusWbw(surah, ayah, wordno);
    }

    @Inject
    public testrep(@NotNull QuranDao qurandao) {


        this.qurandao = qurandao;
    }
}