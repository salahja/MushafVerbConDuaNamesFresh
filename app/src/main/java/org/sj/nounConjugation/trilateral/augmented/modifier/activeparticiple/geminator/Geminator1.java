package org.sj.nounConjugation.trilateral.augmented.modifier.activeparticiple.geminator;

import org.sj.nounConjugation.TrilateralNounSubstitutionApplier;
import org.sj.verbConjugation.trilateral.Substitution.ExpressionInfixSubstitution;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.IAugmentedTrilateralModifier;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Title: Sarf Program</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ALEXO</p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public class Geminator1 extends TrilateralNounSubstitutionApplier implements IAugmentedTrilateralModifier {
    private final List substitutions = new LinkedList();

    public Geminator1() {
        substitutions.add(new ExpressionInfixSubstitution("ْC3ِC3", "ِC3ّ")); // EX: (مُحِبٌّ، مُسْتَحِبٌّ)
        substitutions.add(new ExpressionInfixSubstitution("C3ِC3", "C3ّ")); // EX: (مُحاجٌّ، مُنْقَضٌّ، مُشْتَدٌّ، مُتَصامٌّ)
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        switch (kov) {
            case 1:
            case 17:
            case 20:
                return formulaNo == 6 || formulaNo == 12;
            case 6:
                return formulaNo == 6;
            case 11:
                return formulaNo == 12;
            case 2:
                switch (formulaNo) {
                    case 1:
                    case 3:
                    case 4:
                    case 5:
                    case 7:
                    case 9:
                        return true;
                }
            case 3:
                switch (formulaNo) {
                    case 3:
                    case 5:
                    case 7:
                    case 9:
                        return true;
                }
            case 8:
                return formulaNo == 3 || formulaNo == 7;
        }
        return false;
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
