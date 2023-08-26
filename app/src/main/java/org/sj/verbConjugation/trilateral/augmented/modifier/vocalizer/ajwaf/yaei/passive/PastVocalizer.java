package org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer.ajwaf.yaei.passive;

import org.sj.verbConjugation.trilateral.Substitution.ExpressionInfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.IAugmentedTrilateralModifier;

import java.util.LinkedList;
import java.util.List;

public class PastVocalizer extends SubstitutionsApplier implements IAugmentedTrilateralModifier {
    private final List<ExpressionInfixSubstitution> substitutions = new LinkedList<ExpressionInfixSubstitution>();

    public PastVocalizer() {
        substitutions.add(new ExpressionInfixSubstitution("ْيِC3ْ", "ِC3ْ"));// EX: (أبِدْتُ، استُقلتُ،)
        substitutions.add(new ExpressionInfixSubstitution("ْيِC3ّ", "ِC3ّ"));// EX: (أُبِتُّ)
        substitutions.add(new ExpressionInfixSubstitution("ْيِC3َ", "ِيC3َ"));// EX: (أبِيدَ، استُقِيل،)
        substitutions.add(new ExpressionInfixSubstitution("ْيِC3ُ", "ِيC3ُ"));// EX: (أبِيدُوا، استُقِيلوا،)
        substitutions.add(new ExpressionInfixSubstitution("ُيِC3ْ", "ِC3ْ"));// EX: (انْهِلْتُ، اكتِلْتُ)
        substitutions.add(new ExpressionInfixSubstitution("ُيِC3ّ", "ِC3ّ"));// EX: (اختِتُّ)
        substitutions.add(new ExpressionInfixSubstitution("ُيِC3َ", "ِيC3َ"));// EX: (انْهِيلَ، اكتِيل)
        substitutions.add(new ExpressionInfixSubstitution("ُيِC3ُ", "ِيC3ُ"));// EX: (انهِيلوا، اكتِيلوا)

    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        return ((kov == 19 || kov == 20) && formulaNo == 1) ||
                (kov == 20 && formulaNo == 4) ||
                ((kov == 20 || kov == 18) && formulaNo == 5) ||
                ((kov == 18 || kov == 19 || kov == 20) && formulaNo == 9);
    }
}
