package org.sj.nounConjugation.trilateral.augmented.passiveparticiple;

import org.sj.nounConjugation.trilateral.augmented.AugmentedTrilateralNoun;
import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.util.ArabCharUtil;

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
public class NounFormula7 extends AugmentedTrilateralNoun {
    public NounFormula7(AugmentedTrilateralRoot root, String suffix) {
        super(root, suffix);
    }

    /**
     * form
     *
     * @return String
     * @todo Implement this sarf.noun.trilateral.TrilateralNoun method
     */
    public String form() {
        return ArabCharUtil.MEEM + ArabCharUtil.DAMMA + ArabCharUtil.TA + ArabCharUtil.FATHA + root.getC1() + ArabCharUtil.FATHA + ArabCharUtil.Aleph + root.getC2() + ArabCharUtil.FATHA + root.getC3() + suffix;
    }
}
