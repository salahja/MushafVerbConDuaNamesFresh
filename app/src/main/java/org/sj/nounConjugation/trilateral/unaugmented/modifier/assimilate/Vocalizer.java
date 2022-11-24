package org.sj.nounConjugation.trilateral.unaugmented.modifier.assimilate;

import org.sj.nounConjugation.trilateral.unaugmented.modifier.ConjugationResult;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.IUnaugmentedTrilateralNounModificationApplier;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.assimilate.vocalizer.Vocalizer1;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.assimilate.vocalizer.Vocalizer2;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.assimilate.vocalizer.Vocalizer31;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.assimilate.vocalizer.Vocalizer32;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.assimilate.vocalizer.Vocalizer41;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.assimilate.vocalizer.Vocalizer42;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;

import java.util.Iterator;
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
public class Vocalizer {
    private final List modifiers = new LinkedList();

    public Vocalizer() {
        modifiers.add(new Vocalizer1());
        modifiers.add(new Vocalizer2());
        modifiers.add(new Vocalizer31());
        modifiers.add(new Vocalizer32());
        modifiers.add(new Vocalizer41());
        modifiers.add(new Vocalizer42());
    }

    public void apply(ConjugationResult conjResult) {
        Iterator iter = modifiers.iterator();
        while (iter.hasNext()) {
            IUnaugmentedTrilateralNounModificationApplier modifier = (IUnaugmentedTrilateralNounModificationApplier) iter.next();
            if (modifier.isApplied(conjResult)) {
                ((SubstitutionsApplier) modifier).apply(conjResult.getFinalResult(), conjResult.getRoot());
                break;
            }
        }
    }
}
