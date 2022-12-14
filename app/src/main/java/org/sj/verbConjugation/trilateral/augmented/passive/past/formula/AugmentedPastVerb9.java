package org.sj.verbConjugation.trilateral.augmented.passive.past.formula;

import org.sj.verbConjugation.trilateral.augmented.AugmentedPastVerb;
import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.util.ArabCharUtil;

public class AugmentedPastVerb9 extends AugmentedPastVerb {
    public AugmentedPastVerb9(AugmentedTrilateralRoot root, String lastDpa, String connectedPronoun) {
        super(root, lastDpa, connectedPronoun);
    }

    /**
     * form
     *
     * @return String
     * @todo Implement this sarf.trilingual.augmented.past.AugmentedPastVerb
     * method
     */
    public String form() {
        String verb = "اس" + ArabCharUtil.SKOON + "ت" + ArabCharUtil.DAMMA + root.getC1() + ArabCharUtil.SKOON + root.getC2() + ArabCharUtil.KASRA + root.getC3() + lastDpa + connectedPronoun;
        return verb;

    }
}
