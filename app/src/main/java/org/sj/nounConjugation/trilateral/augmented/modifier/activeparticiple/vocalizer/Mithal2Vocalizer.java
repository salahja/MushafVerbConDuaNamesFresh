package org.sj.nounConjugation.trilateral.augmented.modifier.activeparticiple.vocalizer;

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
public class Mithal2Vocalizer extends TrilateralNounSubstitutionApplier implements IAugmentedTrilateralModifier {
    private final List substitutions = new LinkedList();

    public Mithal2Vocalizer() {
        substitutions.add(new InfixSubstitution("ُيْ", "ُو"));// EX: (مُوقِظٌ، )
        substitutions.add(new InfixSubstitution("ُوْ", "ُو"));// EX: (مُوجِبٌ، )

    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        return formulaNo == 1 && (kov == 13 || kov == 14);
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
