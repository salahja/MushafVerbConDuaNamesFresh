package org.sj.nounConjugation.trilateral.unaugmented.modifier.exaggeration;

import org.sj.nounConjugation.trilateral.unaugmented.modifier.ConjugationResult;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.IUnaugmentedTrilateralNounModificationApplier;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.exaggeration.hamza.EinMahmouz;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.exaggeration.hamza.FaaMahmouz;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.exaggeration.hamza.LamMahmouz;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.exaggeration.hamza.NakesLafifMahmouz;
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
    private final NakesLafifMahmouz nakesLafifMahmouz = new NakesLafifMahmouz();

    public Mahmouz() {
        modifiers.add(new FaaMahmouz());
        modifiers.add(new EinMahmouz());
        modifiers.add(new LamMahmouz());
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
        //apply that after the 3 before
        if (nakesLafifMahmouz.isApplied(conjResult))
            nakesLafifMahmouz.apply(conjResult.getFinalResult(), conjResult.getRoot());
        //الأفعال الثلاثية المجردة المهموزة الفاء والمهموزة اللام (وهي: أبأ، أثأ، أجأ، أزأ، أكأ)
        if (conjResult.getRoot().getC3() == 'ء')
            ((SubstitutionsApplier) modifiers.get(2)).apply(conjResult.getFinalResult(), conjResult.getRoot());
    }
}
