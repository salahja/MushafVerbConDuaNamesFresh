package org.sj.verbConjugation.trilateral.unaugmented.modifier.vocalizer.lafif.separeted;

import org.sj.verbConjugation.trilateral.Substitution.ExpressionInfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.ExpressionSuffixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.unaugmented.ConjugationResult;
import org.sj.verbConjugation.trilateral.unaugmented.modifier.IUnaugmentedTrilateralModifier;

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
public class Imperative1Vocalizer extends SubstitutionsApplier implements IUnaugmentedTrilateralModifier {

    private final List substitutions = new LinkedList();

    public Imperative1Vocalizer() {

        substitutions.add(new ExpressionSuffixSubstitution("ايْC2ِيْ", "C2ِ"));// EX: (دِ)
        substitutions.add(new ExpressionInfixSubstitution("ايْC2ِيِ", "C2ِ"));// EX: (أنتِ دِي)
        substitutions.add(new ExpressionInfixSubstitution("ايْC2ِيْ", "C2ِي"));// EX: (أنتن دِينَ)
        substitutions.add(new ExpressionInfixSubstitution("ايْC2ِيُ", "C2ُ"));// EX: (أنتم دُوا)
        substitutions.add(new ExpressionInfixSubstitution("ايْC2ِيَ", "C2ِيَ"));// EX: (أنتما دِيا)

    }


    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(ConjugationResult conjugationResult) {
        int kov = conjugationResult.getKov();
        int noc = Integer.parseInt(conjugationResult.getRoot().getConjugation());
        return (conjugationResult.getRoot().getC1() == 'ي' && kov == 30 && noc == 2);
    }

}
