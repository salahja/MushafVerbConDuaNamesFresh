package org.sj.verbConjugation.trilateral.unaugmented.modifier;

import org.sj.verbConjugation.trilateral.unaugmented.ConjugationResult;
import org.sj.verbConjugation.trilateral.unaugmented.UnaugmentedTrilateralRoot;
import org.sj.verbConjugation.util.VerbLamAlefModifier;

import java.util.Collections;
import java.util.List;

/**
 * <p>Title: Sarf Program</p>
 * المعالجة
 * <p>Description: يقوم بفحص واجراء التعديلات المناسبة على الأفعال الثلاثية المجردة
 * بما فيها الاعلال والابدال والهمزة
 * حسب الصيغة ماضي أو مضارع او أمر
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ALEXO</p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public class UnaugmentedTrilateralModifier {
    private static final UnaugmentedTrilateralModifier instance = new UnaugmentedTrilateralModifier();
    private final Geminator geminator = new Geminator();
    private final Vocalizer vocalizer = new Vocalizer();
    private final HamzaModifier hamzaModifier = new HamzaModifier();
    private final PostHamzaModifier postHamzaModifier = new PostHamzaModifier();

    private UnaugmentedTrilateralModifier() {
    }

    public static UnaugmentedTrilateralModifier getInstance() {
        return instance;
    }

    /**
     * اخراج قائمة الأفعال بعد التعديلات
     * البدء بالادغام
     *
     * @param root         UnaugmentedTrilateralRoot
     * @param kov          int
     * @param conjugations List
     * @param tense        String (From SystemConstans class the values are stored)  ماضي أو مضارع او أمر
     * @return ConjugationResult
     */
    public ConjugationResult build(UnaugmentedTrilateralRoot root, int kov, List conjugations, String tense, boolean active) {
        return build(root, kov, conjugations, tense, active, true);
    }

    public ConjugationResult build(UnaugmentedTrilateralRoot root, int kov, List conjugations, String tense, boolean active, boolean applyGemination) {
        ConjugationResult conjResult = new ConjugationResult(kov, root, conjugations);
        if (applyGemination)
            geminator.apply(tense, active, conjResult);
        vocalizer.apply(tense, active, conjResult);
        hamzaModifier.apply(tense, active, conjResult);
        //خصيصاُ للفعل أثأ
        postHamzaModifier.apply(tense, active, conjResult);
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

}
