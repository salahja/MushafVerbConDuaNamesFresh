package org.sj.verbConjugation.trilateral.augmented;

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
