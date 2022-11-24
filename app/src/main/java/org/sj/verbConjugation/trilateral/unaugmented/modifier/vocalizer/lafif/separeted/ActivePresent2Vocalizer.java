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
public class ActivePresent2Vocalizer extends SubstitutionsApplier implements IUnaugmentedTrilateralModifier {

    private final List substitutions = new LinkedList();

    public ActivePresent2Vocalizer() {
        substitutions.add(new ExpressionSuffixSubstitution("َوْC2ِيُ", "َC2ِي")); // EX: (يَقِي، يَلِي)
        substitutions.add(new ExpressionSuffixSubstitution("َوْC2ِيْ", "َC2ِ")); // EX: (لم يَقِ، يَلِ)
        substitutions.add(new ExpressionInfixSubstitution("َوْC2ِيِن", "َC2ِن")); // EX: (أنتِ تَقِنَّ، تَلِنَّ)
        substitutions.add(new ExpressionInfixSubstitution("َوْC2ِيِ", "َC2ِ")); // EX: (أنتِ تَقِينَ، تَلِينَ)
        substitutions.add(new ExpressionInfixSubstitution("َوْC2ِيْ", "َC2ِي")); // EX: (أنتن تَقِينَ، تَلِينَ)
        substitutions.add(new ExpressionInfixSubstitution("َوْC2ِيُ", "َC2ُ")); // EX: (أنتم تَقُون، تَقُنَّ، تَلُون، تَلُنَّ)
        substitutions.add(new ExpressionInfixSubstitution("َوْC2ِيَ", "َC2ِيَ"));// EX: (أنتما تقيان)

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
