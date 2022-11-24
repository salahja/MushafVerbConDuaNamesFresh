package org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer.ajwaf.wawi.passive;

import org.sj.verbConjugation.trilateral.Substitution.ExpressionInfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.IAugmentedTrilateralModifier;

import java.util.LinkedList;
import java.util.List;

public class Present2Vocalizer extends SubstitutionsApplier implements IAugmentedTrilateralModifier {
    private final List substitutions = new LinkedList();

    public Present2Vocalizer() {
        substitutions.add(new ExpressionInfixSubstitution("َوَC3ُ", "َاC3ُ"));// EX: (هو يُنْقادُ، يُقْتاد)
        substitutions.add(new ExpressionInfixSubstitution("َوَC3ِ", "َاC3ِ"));// EX: (أنتِ تُنْقادِينَ، تُقْتادِينَ)
        substitutions.add(new ExpressionInfixSubstitution("َوَC3َ", "َاC3َ"));// EX: (أنتما تُنْقَادَانِ، تُقْتادَانِ)
        substitutions.add(new ExpressionInfixSubstitution("َوَC3ْ", "َC3ْ"));// EX: (هنّ يُنْقَدْنَ، يُقْتَدْنَ)
    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        return ((kov == 15 || kov == 17) && formulaNo == 4) || ((kov == 15 || kov == 16 || kov == 17) && formulaNo == 5);
    }
}
