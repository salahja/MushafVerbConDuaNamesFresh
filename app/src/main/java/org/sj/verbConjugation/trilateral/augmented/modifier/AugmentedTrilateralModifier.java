package org.sj.verbConjugation.trilateral.augmented.modifier;

import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer.FormulaApplyingChecker;
import org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer.IFormulaApplyingChecker;
import org.sj.verbConjugation.util.VerbLamAlefModifier;

import java.util.Collections;
import java.util.List;

public class AugmentedTrilateralModifier {
    private static final AugmentedTrilateralModifier instance = new AugmentedTrilateralModifier();
    private final Substituter substituter = new Substituter();
    private final MazeedGeminator mazeedGeminator = new MazeedGeminator();
    private final VocalizerAugmented vocalizerAugmented = new VocalizerAugmented();
    private final PreVocalizer preVocalizer = new PreVocalizer();
    private final SarfHamzaModifier sarfHamzaModifier = new SarfHamzaModifier();

    private AugmentedTrilateralModifier() {
    }

    public static AugmentedTrilateralModifier getInstance() {
        return instance;
    }

    public MazeedConjugationResult build(AugmentedTrilateralRoot root, int kov, int formulaNo, List conjugations, String tense, boolean active, boolean applyGemination, boolean listener) {
        MazeedConjugationResult conjResult = new MazeedConjugationResult(kov, formulaNo, root, conjugations);
        substituter.apply(tense, active, conjResult);
        if (applyGemination) {
            mazeedGeminator.apply(tense, active, conjResult);
        }
        boolean applyVocalization = true;
        int result = FormulaApplyingChecker.getInstance().check(root, formulaNo);
        if (result == IFormulaApplyingChecker.NOT_VOCALIZED) {
            applyVocalization = false;
        } else if (result == IFormulaApplyingChecker.TWO_STATE) {
            //asking the listener to apply or not the vocaliztion
            //    applyVocalization = listener.doSelectVocalization();
            applyVocalization = listener;
        }
        if (applyVocalization) {
            preVocalizer.apply(tense, active, conjResult);
            vocalizerAugmented.apply(tense, active, conjResult);
        }
        sarfHamzaModifier.apply(tense, active, conjResult);
        VerbLamAlefModifier.getInstance().apply(conjResult);
        boolean filterNotNull = conjResult.getFinalResult().removeAll(Collections.singletonList(null));

        List<Object> amrnahi = conjResult.getFinalResult();


        if(filterNotNull){

          conjResult.getAmrNahiAmr().setAnta(amrnahi.get(0).toString());
            conjResult.getAmrNahiAmr().anta= amrnahi.get(0).toString();
            conjResult.getAmrNahiAmr().antuma= amrnahi.get(2).toString();
            conjResult.getAmrNahiAmr().antum= amrnahi.get(3).toString();
            conjResult.getAmrNahiAmr().anti= amrnahi.get(1).toString();
            conjResult.getAmrNahiAmr().antumaf= amrnahi.get(2).toString();
            conjResult.getAmrNahiAmr().antunna= amrnahi.get(4).toString();

        }

        else  if( conjResult.getFinalResult().size()==13) {


            conjResult.getMadhiMudharay().setHua(conjResult.getFinalResult().get(0).toString());
            conjResult.getMadhiMudharay().setHuma(conjResult.getFinalResult().get(1).toString());//[1])
            conjResult.getMadhiMudharay().setHum(conjResult.getFinalResult().get(2).toString());//[2])
            conjResult.getMadhiMudharay().setHia(conjResult.getFinalResult().get(3).toString());//[3])
            conjResult.getMadhiMudharay().setHumaf(conjResult.getFinalResult().get(4).toString());//[4])
            conjResult.getMadhiMudharay().setHunna(conjResult.getFinalResult().get(5).toString());//[5])
            conjResult.getMadhiMudharay().setAnta(conjResult.getFinalResult().get(6).toString());//[6])
            conjResult.getMadhiMudharay().setAntuma(conjResult.getFinalResult().get(7).toString());//[7])
            conjResult.getMadhiMudharay().setAntum(conjResult.getFinalResult().get(8).toString());//[8])
            conjResult.getMadhiMudharay().setAnti(conjResult.getFinalResult().get(9).toString());//[9])
                 conjResult.getMadhiMudharay().setAntumf( conjResult.getFinalResult().get(7).toString());//[7]
                 conjResult.getMadhiMudharay().setAntunna(conjResult.getFinalResult().get(10).toString());//[10]
                 conjResult.getMadhiMudharay().setAna( conjResult.getFinalResult().get(11).toString());//[11]
                 conjResult.getMadhiMudharay().setNahnu(conjResult.getFinalResult().get(12).toString());//[12]









        }

        return conjResult;
    }

    public MazeedConjugationResult build(AugmentedTrilateralRoot root, int kov, int formulaNo, List conjugations, String tense, boolean active, boolean listener) {
        return build(root, kov, formulaNo, conjugations, tense, active, true, listener);
    }

}
