package org.sj.verbConjugation.trilateral.augmented;

import org.sj.AmrNahiAmr;
import org.sj.FaelMafool;
import org.sj.MadhiMudharay;
import org.sj.verbConjugation.trilateral.unaugmented.ConjugationResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MazeedConjugationResult {
    private final int kov;
    private final int formulaNo;
    private final AugmentedTrilateralRoot root;
    //13 conjugated verbs
    private final LinkedList<Objects> originalResult;
    private final ArrayList finalResult;
    public org.sj.MadhiMudharay MadhiMudharay = new  MadhiMudharay();
    public FaelMafool faelMafool = new FaelMafool();
    public AmrNahiAmr amrNahiAmr= new AmrNahiAmr();

    public org.sj.MadhiMudharay getMadhiMudharay() {
        return MadhiMudharay;
    }

    public void setMadhiMudharay(org.sj.MadhiMudharay madhiMudharay) {
        MadhiMudharay = madhiMudharay;
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

    public MazeedConjugationResult(int kov, int formulaNo, AugmentedTrilateralRoot root, List originalResult) {
        this.kov = kov;
        this.formulaNo = formulaNo;
        this.originalResult = (LinkedList) originalResult;
        this.root = root;
        this.finalResult = new ArrayList<>(originalResult);
    }

    public List<Object> getFinalResult() {
        return finalResult;
    }

    public int getKov() {
        return kov;
    }

    public List getOriginalResult() {
        return originalResult;
    }

    public AugmentedTrilateralRoot getRoot() {
        return root;
    }

    public int getFormulaNo() {
        return formulaNo;
    }


}
