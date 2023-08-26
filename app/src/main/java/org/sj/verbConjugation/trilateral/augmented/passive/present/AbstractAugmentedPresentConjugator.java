package org.sj.verbConjugation.trilateral.augmented.passive.present;

import org.sj.verbConjugation.trilateral.augmented.AugmentedPresentVerb;
import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.util.AugmentationFormula;
import org.sj.verbConjugation.util.PresentConjugationDataContainer;

import java.util.LinkedList;
import java.util.List;

public class AbstractAugmentedPresentConjugator {
    private final List lastDprList;
    private final List connectedPronounList;

    public AbstractAugmentedPresentConjugator(List lastDprList, List connectedPronounList) {
        this.lastDprList = lastDprList;
        this.connectedPronounList = connectedPronounList;
    }

    public AugmentedPresentVerb createVerb(AugmentedTrilateralRoot root, int pronounIndex, int formulaNo) {
        String cp = PresentConjugationDataContainer.getInstance().getCp(pronounIndex);
        String lastDpr = (String) lastDprList.get(pronounIndex);
        String connectedPronoun = (String) connectedPronounList.get(pronounIndex);
        String formulaClassName = getClass().getPackage().getName() + ".formula." + "AugmentedPresentVerb" + formulaNo;
        Object[] parameters = {root, cp, lastDpr, connectedPronoun};
        try {
            AugmentedPresentVerb verb = (AugmentedPresentVerb) Class.forName(formulaClassName).getConstructors()[0].newInstance(parameters);
            return verb;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<AugmentedPresentVerb> createVerbList(AugmentedTrilateralRoot root, int formulaNo) {
        AugmentationFormula augmentationFormula = root.getAugmentationFormula(formulaNo);
        if (formulaNo == 29) {
            //   if (augmentationFormula.getTransitive() == 'ل') {
            return createLazzemVerbList(root, formulaNo);
        } else {
            List<AugmentedPresentVerb> result = new LinkedList<>();
            for (int i = 0; i < 13; i++) {
                AugmentedPresentVerb verb = createVerb(root, i, formulaNo);
                result.add(verb);
            }
            return result;
        }
    }

    //������ ������ ������ ��� �� �� �� ��
    public List<AugmentedPresentVerb> createLazzemVerbList(AugmentedTrilateralRoot root, int formulaNo) {
        List<AugmentedPresentVerb> result = new LinkedList<AugmentedPresentVerb>();
        for (int i = 0; i < 13; i++) {
            if (i == 7 || i == 8) {
                AugmentedPresentVerb verb = createVerb(root, i, formulaNo);
                result.add(verb);
            } else {
                result.add(null);
            }
        }
        return result;
    }

}
