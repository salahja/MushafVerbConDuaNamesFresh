package org.sj.nounConjugation.trilateral.augmented.modifier.passiveparticiple.vocalizer;

import org.sj.nounConjugation.TrilateralNounSubstitutionApplier;
import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
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
public class Ajwaf1Vocalizer extends TrilateralNounSubstitutionApplier implements IAugmentedTrilateralModifier {
    private final List substitutions = new LinkedList();

    public Ajwaf1Vocalizer() {
        substitutions.add(new InfixSubstitution("ْوَ", "َا"));// EX: (مُقامٌ، مُسْتَدارٌ)
        substitutions.add(new InfixSubstitution("َوَ", "َا"));// EX: (مُنْقادٌ، مُقْتادٌ)
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        switch (kov) {
            case 15:
                switch (formulaNo) {
                    case 4:
                    case 5:
                    case 9:
                        return true;
                }
            case 16:
                switch (formulaNo) {
                    case 1:
                    case 5:
                    case 9:
                        return true;
                }
            case 17:
                switch (formulaNo) {
                    case 1:
                    case 4:
                    case 5:
                    case 9:
                        return true;
                }

        }
        return false;

    }

    public List getSubstitutions() {
        return substitutions;
    }
}
