package org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.passive;

import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.TrilateralRoot;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.IAugmentedTrilateralModifier;

import java.util.ArrayList;
import java.util.List;

public class RaaPresentMahmouz extends SubstitutionsApplier implements IAugmentedTrilateralModifier {
    private final List<InfixSubstitution> substitutions = new ArrayList<InfixSubstitution>();

    public RaaPresentMahmouz() {
        substitutions.add(new InfixSubstitution("ْءَ", "َ"));// EX: (يُرَى)
    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        TrilateralRoot root = mazeedConjugationResult.getRoot();
        return mazeedConjugationResult.getFormulaNo() == 1 && root.getC1() == 'ر' && root.getC2() == 'ء' && root.getC3() == 'ي';
    }
}
