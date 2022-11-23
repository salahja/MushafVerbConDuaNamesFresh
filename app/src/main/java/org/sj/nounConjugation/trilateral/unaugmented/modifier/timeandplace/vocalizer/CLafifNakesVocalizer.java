package org.sj.nounConjugation.trilateral.unaugmented.modifier.timeandplace.vocalizer;

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
public class CLafifNakesVocalizer extends TrilateralNounSubstitutionApplier implements IUnaugmentedTrilateralNounModificationApplier {
    List substitutions = new LinkedList();

    public CLafifNakesVocalizer() {
        substitutions.add(new InfixSubstitution("َوَ", "َا")); // EX: (مَشتاة )
        substitutions.add(new InfixSubstitution("َيَ", "َا")); // EX: (مَرقاة )
    }

    public List getSubstitutions() {
        return substitutions;
    }

    public boolean isApplied(ConjugationResult conjugationResult) {
        String nounFormula = conjugationResult.getNounFormula();
        if (!nounFormula.equals("مَفْعَلَة")) {
            return false;
        }
        int kov = conjugationResult.getKov();
        int noc = Integer.parseInt(conjugationResult.getRoot().getConjugation());
        switch (kov) {
            case 21:
                return noc == 1 || noc == 5;
            case 22:
                return noc == 1 || noc == 3;
            case 23:
                switch (noc) {
                    case 1:
                    case 3:
                    case 4:
                    case 5:
                        return true;
                }
            case 24:
            case 26:
                switch (noc) {
                    case 2:
                    case 3:
                    case 4:
                        return true;
                }
            case 25:
                return noc == 3 || noc == 4;
            case 27:
            case 29:
                return noc == 2;
            case 28:
                return noc == 2 || noc == 4;
            case 30:
                switch (noc) {
                    case 2:
                    case 4:
                    case 6:
                        return true;
                }

        }
        return false;
    }

}
