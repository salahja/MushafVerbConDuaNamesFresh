package org.sj.nounConjugation.trilateral.unaugmented.modifier.timeandplace;

import org.sj.nounConjugation.trilateral.unaugmented.modifier.ConjugationResult;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.IUnaugmentedTrilateralNounModificationApplier;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.timeandplace.hamza.EinMahmouz;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.timeandplace.hamza.FaaMahmouz;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.timeandplace.hamza.LamMahmouz;
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
public class Mahmouz {
    private final List<IUnaugmentedTrilateralNounModificationApplier> modifiers = new LinkedList<IUnaugmentedTrilateralNounModificationApplier>();

    public Mahmouz() {
        modifiers.add(new EinMahmouz());
        modifiers.add(new FaaMahmouz());
        modifiers.add(new LamMahmouz());
    }

    public void apply(ConjugationResult conjResult) {
        Iterator<IUnaugmentedTrilateralNounModificationApplier> iter = modifiers.iterator();
        while (iter.hasNext()) {
            IUnaugmentedTrilateralNounModificationApplier modifier = iter.next();
            if (modifier.isApplied(conjResult)) {
                ((SubstitutionsApplier) modifier).apply(conjResult.getFinalResult(), conjResult.getRoot());
                break;
            }
        }
    }
}
