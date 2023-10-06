package org.sj.verbConjugation.trilateral.unaugmented;

import org.sj.AmrNahiAmr;
import org.sj.FaelMafool;
import org.sj.IsmAlaMifaalun;
import org.sj.IsmAlaMifalatun;
import org.sj.IsmAlaMifalun;
import org.sj.IsmZarfMafalatun;
import org.sj.IsmZarfMafalun;
import org.sj.IsmZarfMafilun;
import org.sj.MadhiMudharay;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: Sarf Program</p>
 *
 * <p>Description: يمثل نتيجة التصريف مع الجذر ونوع الجذر
 * يستعمل في المعالجة بعد التوليد
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ALEXO</p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public class ConjugationResult {
    protected int kov;
    protected UnaugmentedTrilateralRoot root;

    //13 conjugated verbs
    List originalResult;
    //القائمة بعد  الادغام والاعلال والهمزة
    protected ArrayList finalResult;
    public MadhiMudharay  madhiMudharay = new  MadhiMudharay();
    public FaelMafool  faelMafool = new FaelMafool();
    public AmrNahiAmr  amrNahiAmr= new AmrNahiAmr();
    public IsmZarfMafalun  ismZarfMafalun = new IsmZarfMafalun();
    public IsmZarfMafilun  ismZarfMafilun = new IsmZarfMafilun();
    public IsmZarfMafalatun  ismZarfMafalatun = new IsmZarfMafalatun();
    public IsmAlaMifalun  ismAlaMifalun = new IsmAlaMifalun();
    public IsmAlaMifalatun  ismAlaMifalatun = new IsmAlaMifalatun();
    public IsmAlaMifaalun  ismAlaMifaalun = new IsmAlaMifaalun();

    public MadhiMudharay getMadhiMudharay() {
        return madhiMudharay;
    }

    public void setMadhiMudharay(MadhiMudharay madhiMudharay) {
        this.madhiMudharay = madhiMudharay;
    }

    public FaelMafool getFaelMafool() {
        return faelMafool;
    }

    public void setFaelMafool(FaelMafool faelMafool) {
        this.faelMafool = faelMafool;
    }

    public AmrNahiAmr getAmrNahiAmr() {
        return amrNahiAmr;
    }

    public void setAmrNahiAmr(AmrNahiAmr amrNahiAmr) {
        this.amrNahiAmr = amrNahiAmr;
    }

    public IsmZarfMafalun getIsmZarfMafalun() {
        return ismZarfMafalun;
    }

    public void setIsmZarfMafalun(IsmZarfMafalun ismZarfMafalun) {
        this.ismZarfMafalun = ismZarfMafalun;
    }

    public IsmZarfMafilun getIsmZarfMafilun() {
        return ismZarfMafilun;
    }

    public void setIsmZarfMafilun(IsmZarfMafilun ismZarfMafilun) {
        this.ismZarfMafilun = ismZarfMafilun;
    }

    public IsmZarfMafalatun getIsmZarfMafalatun() {
        return ismZarfMafalatun;
    }

    public void setIsmZarfMafalatun(IsmZarfMafalatun ismZarfMafalatun) {
        this.ismZarfMafalatun = ismZarfMafalatun;
    }

    public IsmAlaMifalun getIsmAlaMifalun() {
        return ismAlaMifalun;
    }

    public void setIsmAlaMifalun(IsmAlaMifalun ismAlaMifalun) {
        this.ismAlaMifalun = ismAlaMifalun;
    }

    public IsmAlaMifalatun getIsmAlaMifalatun() {
        return ismAlaMifalatun;
    }

    public void setIsmAlaMifalatun(IsmAlaMifalatun ismAlaMifalatun) {
        this.ismAlaMifalatun = ismAlaMifalatun;
    }

    public IsmAlaMifaalun getIsmAlaMifaalun() {
        return ismAlaMifaalun;
    }

    public void setIsmAlaMifaalun(IsmAlaMifaalun ismAlaMifaalun) {
        this.ismAlaMifaalun = ismAlaMifaalun;
    }

    public ConjugationResult(int kov, UnaugmentedTrilateralRoot root, List originalResult) {
        this.kov = kov;
        this.originalResult = originalResult;
        this.root = root;
        this.finalResult = new ArrayList<Object>(originalResult);
    }

    public List getFinalResult() {
        return finalResult;
    }

    public int getKov() {
        return kov;
    }

    public UnaugmentedTrilateralRoot getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "ConjugationResult{" +
                "kov=" + kov +
                ", root=" + root +
                ", originalResult=" + originalResult +
                ", finalResult=" + finalResult +
                '}';
    }
}
