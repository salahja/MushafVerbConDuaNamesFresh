package org.sj.verbConjugation.trilateral.unaugmented.modifier.geminator;

import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.unaugmented.ConjugationResult;
import org.sj.verbConjugation.trilateral.unaugmented.modifier.IUnaugmentedTrilateralModifier;
import org.sj.verbConjugation.trilateral.unaugmented.modifier.geminator.generic.ActivePastGeminator;
import org.sj.verbConjugation.trilateral.unaugmented.modifier.geminator.generic.ActivePresentGeminator;
import org.sj.verbConjugation.trilateral.unaugmented.modifier.geminator.generic.ImperativeGeminator;
import org.sj.verbConjugation.trilateral.unaugmented.modifier.geminator.generic.PassivePastGeminator;
import org.sj.verbConjugation.trilateral.unaugmented.modifier.geminator.generic.PassivePresentGeminator;
import org.sj.verbConjugation.util.SystemConstants;

import java.util.HashMap;
import java.util.Map;

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
public class GenericGeminator implements IUnaugmentedTrilateralModifier {
    private final Map geminators = new HashMap();

    public GenericGeminator() {
        //خمس أنواع للادغام للمعلوم والمبني لمجهول في الماضي والمضارع والأمر
        geminators.put(SystemConstants.PAST_TENSE + "true", new ActivePastGeminator());
        geminators.put(SystemConstants.PRESENT_TENSE + "true", new ActivePresentGeminator());
        ImperativeGeminator imperativeGeminator = new ImperativeGeminator();
        geminators.put(SystemConstants.EMPHASIZED_IMPERATIVE_TENSE + "true", imperativeGeminator);
        geminators.put(SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE + "true", imperativeGeminator);
        geminators.put(SystemConstants.PAST_TENSE + "false", new PassivePastGeminator());
        geminators.put(SystemConstants.PRESENT_TENSE + "false", new PassivePresentGeminator());
    }

    public boolean isApplied(ConjugationResult conjugationResult) {
        int kov = conjugationResult.getKov();
        int noc = Integer.parseInt(conjugationResult.getRoot().getConjugation());
        return (kov == 2 && (noc == 1 || noc == 2 || noc == 3 || noc == 4 || noc == 5))
                || (kov == 3 && (noc == 1 || noc == 2))
                || (kov == 8 && noc == 4)
                || (kov == 12 && (noc == 2 || noc == 4));
    }

    public void apply(String tense, boolean active, ConjugationResult conjResult) {
        SubstitutionsApplier geminator = (SubstitutionsApplier) geminators.get(tense + active);
        geminator.apply(conjResult.getFinalResult(), conjResult.getRoot());
    }
}
