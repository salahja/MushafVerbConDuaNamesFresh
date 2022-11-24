package org.sj.nounConjugation.trilateral.augmented.modifier.substituter;

import org.sj.nounConjugation.trilateral.augmented.modifier.AbstractGenericSubstituter;
import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;

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
public class GenericSubstituter3 extends AbstractGenericSubstituter {
    private final List substitutions = new LinkedList();

    public GenericSubstituter3() {
        substitutions.add(new InfixSubstitution("طْت", "طّ")); // EX: (اطِّلاب، )
    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        return mazeedConjugationResult.getRoot().getC1() == 'ط' && super.isApplied(mazeedConjugationResult);
    }
}
