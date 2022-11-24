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
public class NounFormula11 extends NonStandardInstrumentalNounFormula {
    public NounFormula11(UnaugmentedTrilateralRoot root, String suffixNo) {
        super(root, suffixNo);
    }

    //to be used in refection getting the formula name
    public NounFormula11() {
    }

    public String form() {
        if (suffixNo % 2 == 0)
            return root.getC1() + ArabCharUtil.FATHA + root.getC2() + ArabCharUtil.FATHA + root.getC3() + suffix;
        return "";
    }

    public String getFormulaName() {
        return "فَعَلَة";
    }

    public String getSymbol() {
        return "O";
    }

}
