package org.sj.nounConjugation.trilateral.unaugmented.modifier.passiveparticiple;

import org.sj.nounConjugation.trilateral.unaugmented.modifier.ConjugationResult;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.IUnaugmentedTrilateralNounModificationApplier;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.passiveparticiple.hamza.EinMahmouz;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.passiveparticiple.hamza.FaaMahmouz;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.passiveparticiple.hamza.LamMahmouz;
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
    private final List modifiers = new LinkedList();
    private final LamMahmouz lamMahmouz = new LamMahmouz();

    public Mahmouz() {
        modifiers.add(new EinMahmouz());
        modifiers.add(new FaaMahmouz());
        modifiers.add(lamMahmouz);
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
        //خصيصا للفعل أثأ
        if (conjResult.getRoot().getC1() == 'ء' && conjResult.getRoot().getC3() == 'ء') {
            lamMahmouz.apply(conjResult.getFinalResult(), conjResult.getRoot());
        }

    }
}
