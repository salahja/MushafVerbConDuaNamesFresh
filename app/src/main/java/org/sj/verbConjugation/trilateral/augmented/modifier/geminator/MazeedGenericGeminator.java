package org.sj.verbConjugation.trilateral.augmented.modifier.geminator;

import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.IAugmentedTrilateralModifier;
import org.sj.verbConjugation.trilateral.augmented.modifier.geminator.generic.ActivePastGeminator;
import org.sj.verbConjugation.trilateral.augmented.modifier.geminator.generic.ActivePresentGeminator;
import org.sj.verbConjugation.trilateral.augmented.modifier.geminator.generic.ImperativeGeminator;
import org.sj.verbConjugation.trilateral.augmented.modifier.geminator.generic.PassivePastGeminator;
import org.sj.verbConjugation.trilateral.augmented.modifier.geminator.generic.PassivePresentGeminator;
import org.sj.verbConjugation.util.SystemConstants;

import java.util.HashMap;
import java.util.Map;

public class MazeedGenericGeminator implements IAugmentedTrilateralModifier {
    private final Map<String, SubstitutionsApplier> geminators = new HashMap<String, SubstitutionsApplier>();

    public MazeedGenericGeminator() {
        geminators.put(SystemConstants.PAST_TENSE + "true", new ActivePastGeminator());
        geminators.put(SystemConstants.PRESENT_TENSE + "true", new ActivePresentGeminator());
        ImperativeGeminator imperativeGeminator = new ImperativeGeminator();
        geminators.put(SystemConstants.EMPHASIZED_IMPERATIVE_TENSE + "true", imperativeGeminator);
        geminators.put(SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE + "true", imperativeGeminator);
        geminators.put(SystemConstants.PAST_TENSE + "false", new PassivePastGeminator());
        geminators.put(SystemConstants.PRESENT_TENSE + "false", new PassivePresentGeminator());
    }

    public boolean isApplied(MazeedConjugationResult mazeedConjugationResult) {
        int kov = mazeedConjugationResult.getKov();
        int formulaNo = mazeedConjugationResult.getFormulaNo();
        switch (formulaNo) {
            case 6:
                switch (kov) {
                    case 1:
                    case 6:
                    case 17:
                    case 20:
                        return true;
                }
                return false;
            case 12:
                switch (kov) {
                    case 1:
                    case 11:
                    case 17:
                    case 20:
                        return true;
                }
                return false;
            case 1:
            case 4:
                return kov == 2;
            case 3:
            case 7:
                return kov == 2 || kov == 3 || kov == 8;
            case 5:
            case 9:
                return kov == 2 || kov == 3;
        }
        return false;
    }

    public void apply(String tense, boolean active, MazeedConjugationResult conjResult) {
        SubstitutionsApplier geminator = geminators.get(tense + active);
        geminator.apply(conjResult.getFinalResult(), conjResult.getRoot());
    }
}
