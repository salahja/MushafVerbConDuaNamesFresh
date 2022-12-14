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
public class Imperative2Vocalizer extends SubstitutionsApplier implements IUnaugmentedTrilateralModifier {

    private final List substitutions = new LinkedList();

    public Imperative2Vocalizer() {
        substitutions.add(new ExpressionSuffixSubstitution("اوْC2ِيْ", "C2ِ")); // EX: (قِ، لِ)
        substitutions.add(new ExpressionInfixSubstitution("اوْC2ِيِ", "C2ِ"));// EX: (أنتِ قِي، لِي)
        substitutions.add(new ExpressionInfixSubstitution("اوْC2ِيْ", "C2ِي")); // EX: (أنتن قِينَ، لِينَ)
        substitutions.add(new ExpressionInfixSubstitution("اوْC2ِيُ", "C2ُ")); // EX: (أنتم قُوا، لُوا)
        substitutions.add(new ExpressionInfixSubstitution("اوْC2ِيَ", "C2ِيَ")); // EX: (أنتما قِيا)

    }


    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(ConjugationResult conjugationResult) {
        int kov = conjugationResult.getKov();
        int noc = Integer.parseInt(conjugationResult.getRoot().getConjugation());
        return ((kov == 29 || kov == 30) && (noc == 2)) ||
                (kov == 30 && noc == 6);
    }
}
