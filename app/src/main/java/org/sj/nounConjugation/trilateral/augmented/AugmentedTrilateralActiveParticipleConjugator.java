package org.sj.nounConjugation.trilateral.augmented;

import org.sj.nounConjugation.GenericNounSuffixContainer;
import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;

import java.util.LinkedList;
import java.util.List;

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
public class AugmentedTrilateralActiveParticipleConjugator {
    private static final AugmentedTrilateralActiveParticipleConjugator instance = new AugmentedTrilateralActiveParticipleConjugator();

    private AugmentedTrilateralActiveParticipleConjugator() {
    }

    public static AugmentedTrilateralActiveParticipleConjugator getInstance() {
        return instance;
    }

    public AugmentedTrilateralNoun createNoun(AugmentedTrilateralRoot root, int suffixIndex, int formulaNo) {
        String suffix = GenericNounSuffixContainer.getInstance().get(suffixIndex);
        String formulaClassName = getClass().getPackage().getName() + ".activeparticiple." + "NounFormula" + formulaNo;
        Object[] parameters = {root, suffix};
        try {
            AugmentedTrilateralNoun noun = (AugmentedTrilateralNoun) Class.forName(formulaClassName).getConstructors()[0].newInstance(parameters);
            return noun;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List createNounList(AugmentedTrilateralRoot root, int formulaNo) {
        LinkedList<AugmentedTrilateralNoun> result = new LinkedList<AugmentedTrilateralNoun>();
        for (int i = 0; i < 18; i++) {
            AugmentedTrilateralNoun noun = createNoun(root, i, formulaNo);
            result.add(noun);
        }
        return result;

    }

}
