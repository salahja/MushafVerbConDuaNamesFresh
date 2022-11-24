package org.sj.verbConjugation.trilateral.augmented.modifier.geminator;

import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.IAugmentedTrilateralModifier;

import java.util.LinkedList;
import java.util.List;

public class NStartedGeminator extends SubstitutionsApplier implements IAugmentedTrilateralModifier {
    private final List substitutions = new LinkedList();

    public NStartedGeminator() {
        substitutions.add(new InfixSubstitution("نْن", "نّ"));// EX: (انَّمَسَ)
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        return (mazeedConjugationResult.getRoot().getC1() == 'ن') && kov == 1 && formulaNo == 4;
    }

    public void apply(String tense, boolean active, MazeedConjugationResult conjResult) {
        apply(conjResult.getFinalResult(), conjResult.getRoot());
    }

    public List getSubstitutions() {
        return substitutions;
    }

}
