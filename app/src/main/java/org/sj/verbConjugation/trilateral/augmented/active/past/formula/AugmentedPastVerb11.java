package org.sj.verbConjugation.trilateral.augmented.active.past.formula;

import org.sj.verbConjugation.trilateral.augmented.AugmentedPastVerb;
import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.util.ArabCharUtil;

public class AugmentedPastVerb11 extends AugmentedPastVerb {
    public AugmentedPastVerb11(AugmentedTrilateralRoot root, String lastDpa, String connectedPronoun) {
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
        //  return "�"+root.getC1()+ArabCharUtil.SKOON+root.getC2()+ArabCharUtil.FATHA+"�"+ArabCharUtil.SHADDA+ArabCharUtil.FATHA+root.getC3()+lastDpa+connectedPronoun;
        return "ا" + root.getC1() + ArabCharUtil.SKOON + root.getC2() + ArabCharUtil.FATHA + "و" + ArabCharUtil.SHADDA + ArabCharUtil.FATHA + root.getC3() + lastDpa + connectedPronoun;

    }
}
