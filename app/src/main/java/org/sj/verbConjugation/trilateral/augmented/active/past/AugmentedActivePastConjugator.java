package org.sj.verbConjugation.trilateral.augmented.active.past;

import org.sj.verbConjugation.trilateral.augmented.AugmentedPastVerb;
import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.util.AugmentationFormula;
import org.sj.verbConjugation.util.PastConjugationDataContainer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AugmentedActivePastConjugator {
    private static final AugmentedActivePastConjugator instance = new AugmentedActivePastConjugator();

    private AugmentedActivePastConjugator() {
    }

    public static AugmentedActivePastConjugator getInstance() {
        return instance;
    }

    public AugmentedPastVerb createVerb(AugmentedTrilateralRoot root, int pronounIndex, int formulaNo) {
        String lastDpa = PastConjugationDataContainer.getInstance().getLastDpa(pronounIndex);
        String connectedPronoun = PastConjugationDataContainer.getInstance().getConnectedPronoun(pronounIndex);
        String formulaClassName = getClass().getPackage().getName() + ".formula." + "AugmentedPastVerb" + formulaNo;
        Object[] parameters = {root, lastDpa, connectedPronoun};
        try {
            AugmentedPastVerb verb = (AugmentedPastVerb) Class.forName(formulaClassName).getConstructors()[0].newInstance(parameters);
            return verb;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<AugmentedPastVerb> createVerbList(AugmentedTrilateralRoot root, int formulaNo) {
        List<AugmentedPastVerb> result = new LinkedList<AugmentedPastVerb>();
        for (int i = 0; i < 13; i++) {
            AugmentedPastVerb verb = createVerb(root, i, formulaNo);
            result.add(verb);
        }
        return result;

    }

    public Map<String, List<AugmentedPastVerb>> createAllVerbList(AugmentedTrilateralRoot root) {
        Map<String, List<AugmentedPastVerb>> result = new HashMap<>();
        Iterator iter = root.getAugmentationList().iterator();
        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            List<AugmentedPastVerb> formulaVerbList = createVerbList(root, formula.getFormulaNo());
            result.put(formula.getFormulaNo() + "", formulaVerbList);
        }
        return result;
    }

}
