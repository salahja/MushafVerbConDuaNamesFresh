package com.example.mushafconsolidated.quranrepo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mushafconsolidated.Utils;
import com.example.utility.QuranGrammarApplication;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import kotlin.jvm.internal.Intrinsics;


public final class testModel extends ViewModel {
 //   public   class PostViewModel @ViewModelInject constructor(private val postRepository: PostRepository) : ViewModel() {

@Inject testrep trepository;
    private MutableLiveData allquran;

    @NotNull
    public final MutableLiveData getQuranCorpusWbwBysurah(int cid) {
        allquran.setValue(this.trepository.getQuranCorpusWbwbysurah(cid));
        return this.allquran;
    }


/*

    @NotNull
    public final MutableLiveData getVersesBySurahLive(int cid) {
        this.allquran.setValue(this.quranRepository.getsurahbychap(cid));
        return this.allquran;
    }

    @NotNull
    public final MutableLiveData getsurahayahVerses(int cid, int ayid) {
        this.allquran.setValue(this.quranRepository.getsurahbyayah(cid, ayid));
        return this.allquran;
    }

    @NotNull
    public final LiveData getquranbySUrah(int cid) {
        this.allquran.setValue(this.quranRepository.getsurahbychap(cid));
        return (LiveData)this.allquran;
    }
*/

/*
    @NotNull
    public final LiveData getsurahayahVerseslist(int cid, int ayid) {
        this.quranlist.setValue(this.quranRepository.getsurahbyayahlist(cid, ayid));
        return (LiveData)this.quranlist;
    }

    @NotNull
    public final LiveData getSurahSummary(int cid) {
        this.sursumm = this.quranRepository.getSurahSummary(cid);
        return this.sursumm;
    }*/






}
