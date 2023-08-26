package org.sj.verbConjugation.trilateral.unaugmented.passive;

import org.sj.verbConjugation.trilateral.unaugmented.UnaugmentedTrilateralRoot;
import org.sj.verbConjugation.util.PastConjugationDataContainer;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Title: Sarf</p>
 *
 * <p>Description: تصريف الأفعال في الماضي المبني للمجهول     </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public class PassivePastConjugator {
    private static final PassivePastConjugator instance = new PassivePastConjugator();

    private PassivePastConjugator() {
    }

    public static PassivePastConjugator getInstance() {
        return instance;
    }

    /**
     * إنشاء الفعل حسب الضمير
     *
     * @param pronounIndex int
     * @param root         TripleVerb
     * @return PassivePastVerb
     */
    public PassivePastVerb createVerb(int pronounIndex, UnaugmentedTrilateralRoot root) {
        //	اظهار مع هو وهي فقط للمجهول اللازم
        if (root.getVerbtype().equals("ل") && pronounIndex != 7 && pronounIndex != 8)
            return null;
        String lastDpa = PastConjugationDataContainer.getInstance().getLastDpa(pronounIndex);
        String connectedPronoun = PastConjugationDataContainer.getInstance().getConnectedPronoun(pronounIndex);
        return new PassivePastVerb(root, lastDpa, connectedPronoun);
    }

    /**
     * إنشاء  قائمة تحتوي الأفعال مع الضمائر الثلاثة عشر
     *
     * @param root TripleVerb
     * @return List
     */
    public List<PassivePastVerb> createVerbList(UnaugmentedTrilateralRoot root) {
        List<PassivePastVerb> result = new LinkedList<PassivePastVerb>();
        for (int i = 0; i < 13; i++) {
            result.add(createVerb(i, root));
        }
        return result;
    }

    public List<PassivePastVerb> createVerbHua(UnaugmentedTrilateralRoot root) {
        List<PassivePastVerb> result = new LinkedList<>();
        for (int i = 0; i < 1; i++) {
            result.add(createVerb(i, root));
        }
        return result;
    }

}
