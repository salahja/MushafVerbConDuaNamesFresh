package org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer.ajwaf.wawi.active;

import org.sj.verbConjugation.trilateral.Substitution.ExpressionInfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.IAugmentedTrilateralModifier;

import java.util.LinkedList;
import java.util.List;

public class Past1Vocalizer extends SubstitutionsApplier implements IAugmentedTrilateralModifier {
    private final List substitutions = new LinkedList();

    public Past1Vocalizer() {
        substitutions.add(new ExpressionInfixSubstitution("ْوَC3ْ", "َC3ْ"));// EX: (أقَمْتُ، استدرتُ)
        substitutions.add(new ExpressionInfixSubstitution("ْوَC3ّ", "َC3ّ"));// EX: (أمَتُّ)
        substitutions.add(new ExpressionInfixSubstitution("ْوَC3َ", "َاC3َ"));// EX: (أقامَ، استدار)
        substitutions.add(new ExpressionInfixSubstitution("ْوَC3ُ", "َاC3ُ"));// EX: (أقاموا، استداروا)

    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        return ((kov == 16 || kov == 17) && formulaNo == 1) || ((kov == 15 || kov == 16 || kov == 17) && formulaNo == 9);
    }
}
