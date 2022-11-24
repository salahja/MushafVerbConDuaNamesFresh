package org.sj.verbConjugation.trilateral.unaugmented.modifier.vocalizer.nakes.yaei.active;

import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.Substitution.SuffixSubstitution;
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
public class PresentVocalizer extends SubstitutionsApplier implements IUnaugmentedTrilateralModifier {

    private final List substitutions = new LinkedList();

    public PresentVocalizer() {

        substitutions.add(new SuffixSubstitution("ِيُ", "ِي"));// EX: (يرمي)
        substitutions.add(new SuffixSubstitution("ِيْ", "ِ"));// EX: (لم يرمِ)
        substitutions.add(new InfixSubstitution("ِيِن", "ِن"));// EX: (أنتِ تَرْمِنَّ)
        substitutions.add(new InfixSubstitution("ِيِ", "ِ"));// EX: (أنتِ ترمين)
        substitutions.add(new InfixSubstitution("ِيْ", "ِي"));// EX: (انتن ترمين)
        substitutions.add(new InfixSubstitution("ِيُ", "ُ"));// EX: (أنتم ترمون، تَرْمُنَّ)
        substitutions.add(new SuffixSubstitution("َيُ", "َى"));// EX: (يسعى، يخشى )
        substitutions.add(new SuffixSubstitution("َيَ", "َى"));// EX: (لن يسعى، يخشى )
        substitutions.add(new SuffixSubstitution("َيْ", "َ"));// EX: (لم يسعَ، يخشَ)
        substitutions.add(new InfixSubstitution("َيِي", "َيْ"));// EX: (أنتِ تسعَيْنَ، تخشين )
        substitutions.add(new InfixSubstitution("َيُو", "َوْ"));// EX: (أنتم تسعَوْنَ، تخشون )
        substitutions.add(new InfixSubstitution("َيُن", "َوُن"));// EX: (أنتم تسعَوُنَّ، تَخْشَوُنَّ )

    }


    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(ConjugationResult conjugationResult) {
        int kov = conjugationResult.getKov();
        int noc = Integer.parseInt(conjugationResult.getRoot().getConjugation());
        return ((kov == 24 || kov == 26) && (noc == 2)) ||
                ((kov == 24 || kov == 25 || kov == 26) && (noc == 3 || noc == 4));
    }
}
