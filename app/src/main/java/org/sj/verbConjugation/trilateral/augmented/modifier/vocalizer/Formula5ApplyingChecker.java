package org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer;

import java.util.LinkedList;
import java.util.List;

public class Formula5ApplyingChecker extends IFormulaApplyingChecker {
    private final List<String> twoStateList = new LinkedList<>();
    private final List<String> notVocalizedList = new LinkedList<>();

    public Formula5ApplyingChecker() {
        twoStateList.add("حول");
        twoStateList.add("روح");
        twoStateList.add("شور");
        notVocalizedList.add("جور");
        notVocalizedList.add("حوش");
        notVocalizedList.add("زوج");
        notVocalizedList.add("سوط");
        notVocalizedList.add("عور");
        notVocalizedList.add("هور");
    }

    public List<String> getNotVocalizedList() {
        return notVocalizedList;
    }

    public List<String> getTwoStateList() {
        return twoStateList;
    }
}
