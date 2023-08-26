package org.sj.nounConjugation.trilateral.unaugmented.modifier.activeparticiple.vocalizer;

import org.sj.nounConjugation.TrilateralNounSubstitutionApplier;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.ConjugationResult;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.IUnaugmentedTrilateralNounModificationApplier;
import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;

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
public class Ajwaf3WawiVocalizer extends TrilateralNounSubstitutionApplier implements IUnaugmentedTrilateralNounModificationApplier {
    List<InfixSubstitution> substitutions = new LinkedList<InfixSubstitution>();

    public Ajwaf3WawiVocalizer() {
        substitutions.add(new InfixSubstitution("اوِ", "ائِ"));// EX: (قائِمٌ)
    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(ConjugationResult conjugationResult) {
        int kov = conjugationResult.getKov();
        int noc = Integer.parseInt(conjugationResult.getRoot().getConjugation());
        return kov == 17 && (noc == 1 || noc == 5);
    }
}
