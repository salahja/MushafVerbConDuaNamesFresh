package org.sj.verbConjugation.trilateral.augmented.modifier;

import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.util.SystemConstants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SarfHamzaModifier {
    private final Map<String, List<IAugmentedTrilateralModifier>> modifiersMap = new HashMap<String, List<IAugmentedTrilateralModifier>>();

    public SarfHamzaModifier() {
        List<IAugmentedTrilateralModifier> activePastList = new LinkedList<>();
        List<IAugmentedTrilateralModifier> passivePastList = new LinkedList<>();
        List<IAugmentedTrilateralModifier> activePresentList = new LinkedList<>();
        List<IAugmentedTrilateralModifier> passivePresentList = new LinkedList<>();
        List<IAugmentedTrilateralModifier> imperativeList = new LinkedList<IAugmentedTrilateralModifier>();
        modifiersMap.put(SystemConstants.PAST_TENSE + "true", activePastList);
        modifiersMap.put(SystemConstants.PRESENT_TENSE + "true", activePresentList);
        modifiersMap.put(SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE + "true", imperativeList);
        modifiersMap.put(SystemConstants.EMPHASIZED_IMPERATIVE_TENSE + "true", imperativeList);
        modifiersMap.put(SystemConstants.PAST_TENSE + "false", passivePastList);
        modifiersMap.put(SystemConstants.PRESENT_TENSE + "false", passivePresentList);
        activePastList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.active.RaaPastMahmouz());
        activePastList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.active.PastMahmouz());
        activePastList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.active.PastMahmouz());
        activePastList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.lam.active.PastMahmouz());
        passivePastList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.passive.RaaPastMahmouz());
        passivePastList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.passive.PastMahmouz());
        passivePastList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.passive.PastMahmouz());
        passivePastList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.lam.passive.PastMahmouz());
        activePresentList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.active.RaaPresentMahmouz());
        activePresentList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.active.PresentMahmouz());
        activePresentList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.active.PresentMahmouz());
        activePresentList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.lam.active.PresentMahmouz());
        passivePresentList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.passive.RaaPresentMahmouz());
        passivePresentList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.passive.PresentMahmouz());
        passivePresentList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.passive.PresentMahmouz());
        passivePresentList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.lam.passive.PresentMahmouz());
        imperativeList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.active.RaaImperativeMahmouz());
        imperativeList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.active.ImperativeMahmouz());
        imperativeList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.active.ImperativeMahmouz());
        imperativeList.add(new org.sj.verbConjugation.trilateral.augmented.modifier.hamza.lam.active.ImperativeMahmouz());
    }

    /**
     * ����� �����  ������ ��� ������ ���� �� ����� �� ��� ������� �� ������
     * �� �� ���� �� ���
     *
     * @param tense      String
     * @param active     boolean
     * @param conjResult ConjugationResult
     */
    public void apply(String tense, boolean active, MazeedConjugationResult conjResult) {
        List<IAugmentedTrilateralModifier> modifiers = modifiersMap.get(tense + active);
        Iterator<IAugmentedTrilateralModifier> iter = modifiers.iterator();
        while (iter.hasNext()) {
            IAugmentedTrilateralModifier modifier = iter.next();
            if (modifier.isApplied(conjResult)) {
                ((SubstitutionsApplier) modifier).apply(conjResult.getFinalResult(), conjResult.getRoot());
                break;
            }
        }
    }

}
