package org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer.ajwaf.yaei.passive;

import org.sj.verbConjugation.trilateral.Substitution.ExpressionInfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.IAugmentedTrilateralModifier;

import java.util.LinkedList;
import java.util.List;

public class Present1Vocalizer extends SubstitutionsApplier implements IAugmentedTrilateralModifier {
    private final List<ExpressionInfixSubstitution> substitutions = new LinkedList<>();

    public Present1Vocalizer() {
        substitutions.add(new ExpressionInfixSubstitution("ْيَC3ُ", "َاC3ُ"));// EX: (هو يُبادُ، يُستقال)
        substitutions.add(new ExpressionInfixSubstitution("ْيَC3ِ", "َاC3ِ"));// EX: (أنتِ تُبادِين، تُستقالين)
        substitutions.add(new ExpressionInfixSubstitution("ْيَC3َ", "َاC3َ"));// EX: (أنتما تُبادان، تُستقالان)
        substitutions.add(new ExpressionInfixSubstitution("ْيَC3ْ", "َC3ْ"));// EX: (هنّ يُبَدْنَ، يُستَقَلْنَ)
    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        return ((kov == 18 || kov == 19 || kov == 20) && formulaNo == 1) || ((kov == 18 || kov == 19 || kov == 20) && formulaNo == 9);
    }
}
