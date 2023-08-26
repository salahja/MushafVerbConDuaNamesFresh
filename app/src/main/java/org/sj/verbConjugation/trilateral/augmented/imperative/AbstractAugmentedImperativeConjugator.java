package org.sj.verbConjugation.trilateral.augmented.imperative;

import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.util.AugmentationFormula;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AbstractAugmentedImperativeConjugator {
    private final List lastDimList;
    private final List connectedPronounList;

    public AbstractAugmentedImperativeConjugator(List lastDimList, List connectedPronounList) {
        this.lastDimList = lastDimList;
        this.connectedPronounList = connectedPronounList;
    }

    public AugmentedImperativeVerb createVerb(AugmentedTrilateralRoot root, int pronounIndex, int formulaNo) {
        String lastDim = (String) lastDimList.get(pronounIndex);
        String connectedPronoun = (String) connectedPronounList.get(pronounIndex);
        String formulaClassName = getClass().getPackage().getName() + ".formula." + "AugmentedImperativeVerb" + formulaNo;
        Object[] parameters = {root, lastDim, connectedPronoun};
        try {
            AugmentedImperativeVerb verb = (AugmentedImperativeVerb) Class.forName(formulaClassName).getConstructors()[0].newInstance(parameters);
            return verb;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<AugmentedImperativeVerb> createVerbList(AugmentedTrilateralRoot root, int formulaNo) {
        List<AugmentedImperativeVerb> result = new LinkedList<AugmentedImperativeVerb>();
        result.add(null);
        result.add(null);
        //that indexing because the pronouns is existed only for that indecis
        for (int i = 2; i < 7; i++) {
            AugmentedImperativeVerb verb = createVerb(root, i, formulaNo);
            result.add(verb);
        }
        result.add(null);
        result.add(null);
        result.add(null);
        result.add(null);
        result.add(null);
        result.add(null);
        return result;
    }

    public Map<String, List<AugmentedImperativeVerb>> createAllVerbList(AugmentedTrilateralRoot root) {
        Map<String, List<AugmentedImperativeVerb>> result = new HashMap<>();
        Iterator iter = root.getAugmentationList().iterator();
        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            List<AugmentedImperativeVerb> formulaVerbList = createVerbList(root, formula.getFormulaNo());
            result.put(formula.getFormulaNo() + "", formulaVerbList);
        }
        return result;
    }

}
