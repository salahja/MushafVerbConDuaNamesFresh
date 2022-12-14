package org.sj.verbConjugation.trilateral.augmented.passive.past.formula;

import org.sj.verbConjugation.trilateral.augmented.AugmentedPastVerb;
import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.util.ArabCharUtil;

public class AugmentedPastVerb5 extends AugmentedPastVerb {
    public AugmentedPastVerb5(AugmentedTrilateralRoot root, String lastDpa, String connectedPronoun) {
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

        String s="";
        //  return "�"+root.getC1()+ArabCharUtil.SKOON+"�"+ArabCharUtil.FATHA+root.getC2()+ArabCharUtil.FATHA+root.getC3()+lastDpa+connectedPronoun;
        Character c1 = root.getC1();
        if (c1.toString().equals("ء")) {
            s = "اُ" +"ت" + ArabCharUtil.SHADDA +  ArabCharUtil.DAMMA + root.getC2() + ArabCharUtil.KASRA + root.getC3() + lastDpa + connectedPronoun;
        } else {
            return "اُ" + root.getC1() + ArabCharUtil.SKOON + "ت" + ArabCharUtil.DAMMA + root.getC2() + ArabCharUtil.KASRA + root.getC3() + lastDpa + connectedPronoun;
        }
        return s;
    }




}
