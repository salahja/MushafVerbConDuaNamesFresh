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
public class AugmentedTrilateralPassiveParticipleConjugator {
    private static final AugmentedTrilateralPassiveParticipleConjugator instance = new AugmentedTrilateralPassiveParticipleConjugator();
    static List<String> timeAndPlaceIndeciesList = new LinkedList<>();
    static List<String> meemGerundIndeciesList = new LinkedList<>();

    static {
        //��� ������ ������
        timeAndPlaceIndeciesList.add("0");
        timeAndPlaceIndeciesList.add("2");
        timeAndPlaceIndeciesList.add("6");
        timeAndPlaceIndeciesList.add("8");
        timeAndPlaceIndeciesList.add("12");
        timeAndPlaceIndeciesList.add("14");

    }

    static {
        //������ ������
        meemGerundIndeciesList.add("0");
        meemGerundIndeciesList.add("6");
        meemGerundIndeciesList.add("12");
    }

    private AugmentedTrilateralPassiveParticipleConjugator() {
    }

    public static AugmentedTrilateralPassiveParticipleConjugator getInstance() {
        return instance;
    }

    public AugmentedTrilateralNoun createNoun(AugmentedTrilateralRoot root, int suffixIndex, int formulaNo) {
        String suffix = GenericNounSuffixContainer.getInstance().get(suffixIndex);
        String formulaClassName = getClass().getPackage().getName() + ".passiveparticiple." + "NounFormula" + formulaNo;
        Object[] parameters = {root, suffix};
        try {
            AugmentedTrilateralNoun noun = (AugmentedTrilateralNoun) Class.forName(formulaClassName).getConstructors()[0].newInstance(parameters);
            return noun;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<AugmentedTrilateralNoun> createNounList(AugmentedTrilateralRoot root, int formulaNo) {
        List<AugmentedTrilateralNoun> result = new LinkedList<>();
        for (int i = 0; i < 18; i++) {
            AugmentedTrilateralNoun noun = createNoun(root, i, formulaNo);
            result.add(noun);
        }
        return result;

    }

    //������ �� ��� ������ ������� ������� ������
    private List<Object> createNounList(AugmentedTrilateralRoot root, int formulaNo, List<String> indecies) {
        List<Object> result = new LinkedList<>();
        for (int i = 0; i < 18; i++) {
            result.add("");
        }
        for (int i = 0; i < indecies.size(); i++) {
            int index = Integer.parseInt(indecies.get(i).toString());
            AugmentedTrilateralNoun noun = createNoun(root, index, formulaNo);
            result.set(index, noun);
        }
        return result;

    }

    public List<Object> createTimeAndPlaceNounList(AugmentedTrilateralRoot root, int formulaNo) {
        return createNounList(root, formulaNo, timeAndPlaceIndeciesList);
    }

    public List<Object> createMeemGerundNounList(AugmentedTrilateralRoot root, int formulaNo) {
        return createNounList(root, formulaNo, meemGerundIndeciesList);
    }

}
