package org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer;

import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;

public class FormulaApplyingChecker {
    private static final FormulaApplyingChecker instance = new FormulaApplyingChecker();
    Formula1ApplyingChecker formula1ApplyingChecker = new Formula1ApplyingChecker();
    Formula5ApplyingChecker formula5ApplyingChecker = new Formula5ApplyingChecker();
    Formula9ApplyingChecker formula9ApplyingChecker = new Formula9ApplyingChecker();

    private FormulaApplyingChecker() {
    }

    public static FormulaApplyingChecker getInstance() {
        return instance;
    }

    public int check(AugmentedTrilateralRoot root, int formulaNo) {
        switch (formulaNo) {
            case 1:
                return formula1ApplyingChecker.check(root);
            case 5:
                return formula5ApplyingChecker.check(root);
            case 9:
                return formula9ApplyingChecker.check(root);
        }
        return IFormulaApplyingChecker.NO_THING;
    }
}
