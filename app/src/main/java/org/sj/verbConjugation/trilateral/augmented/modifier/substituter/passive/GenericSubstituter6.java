package org.sj.verbConjugation.trilateral.augmented.modifier.substituter.passive;

import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.substituter.AbstractGenericSubstituter;

import java.util.LinkedList;
import java.util.List;

public class GenericSubstituter6 extends AbstractGenericSubstituter {
    private final List<InfixSubstitution> substitutions = new LinkedList<InfixSubstitution>();

    public GenericSubstituter6() {
        substitutions.add(new InfixSubstitution("صْت", "صْط")); // EX: (اصْطُبِر)

    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        return mazeedConjugationResult.getRoot().getC1() == 'ص' && super.isApplied(mazeedConjugationResult);
    }
}
