package org.sj.nounConjugation.trilateral.unaugmented.instrumental.nonstandard;

import org.sj.nounConjugation.trilateral.unaugmented.instrumental.NonStandardInstrumentalNounFormula;
import org.sj.verbConjugation.trilateral.unaugmented.UnaugmentedTrilateralRoot;
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
public class NounFormula3 extends NonStandardInstrumentalNounFormula {
    public NounFormula3(UnaugmentedTrilateralRoot root, String suffixNo) {
        super(root, suffixNo);
    }

    //to be used in refection getting the formula name
    public NounFormula3() {
    }

    public String form() {
        switch (suffixNo) {
            case 2:
            case 4:
            case 8:
            case 10:
            case 14:
            case 16:
                return root.getC1() + ArabCharUtil.FATHA + ArabCharUtil.Aleph + root.getC2() + ArabCharUtil.KASRA + root.getC3() + suffix;
        }
        return "";
    }

    public String getFormulaName() {
        return "فَاعِلَة";
    }

    public String getSymbol() {
        return "G";
    }

}
