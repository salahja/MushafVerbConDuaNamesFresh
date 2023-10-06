package org.sj.nounConjugation.trilateral.unaugmented.modifier.timeandplace;

import org.sj.nounConjugation.NounLamAlefModifier;
import org.sj.nounConjugation.NounSunLamModifier;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.ConjugationResult;
import org.sj.nounConjugation.trilateral.unaugmented.modifier.IUnaugmentedTrilateralNounModifier;
import org.sj.verbConjugation.trilateral.unaugmented.UnaugmentedTrilateralRoot;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Title: Sarf Program</p>
 *
 * <p>Description: تطبيق المعالجة الخاصة على اسم الفاعل
 * ابتداء بالادغام ثم الاعلال واخيرا الهمزة
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ALEXO</p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public class TimeAndPlaceModifier implements IUnaugmentedTrilateralNounModifier {
    private static final TimeAndPlaceModifier instance = new TimeAndPlaceModifier();
    private final Geminator geminator = new Geminator();
    private final Vocalizer vocalizer = new Vocalizer();
    private final Mahmouz mahmouz = new Mahmouz();

    private TimeAndPlaceModifier() {
    }

    public static TimeAndPlaceModifier getInstance() {
        return instance;
    }

    public ConjugationResult build(UnaugmentedTrilateralRoot root, int kov, List conjugations, String formula) {
        ConjugationResult conjResult = new ConjugationResult(kov, root,   conjugations, formula);
        geminator.apply(conjResult.getFinalResult(), root);
        vocalizer.apply(conjResult);
        mahmouz.apply(conjResult);
        NounLamAlefModifier.getInstance().apply(conjResult);
        NounSunLamModifier.getInstance().apply(conjResult);
        if (conjResult.getNounFormula().equals("مَفْعَل")) {
            conjResult.ismZarfMafalun.nomsinMafalun = conjResult.getFinalResult().get(0).toString();//sinM
            conjResult.ismZarfMafalun.nomdualMafalun =
                    conjResult.getFinalResult().get(2).toString();//[2]//dualM
            conjResult.ismZarfMafalun.nomplurarMafalun = "";//[4]//plurarM
            conjResult.ismZarfMafalun.accsinMafalun =
                    conjResult.getFinalResult().get(6).toString();//[6]//sinM
            conjResult.ismZarfMafalun.accdualMafalun =
                    conjResult.getFinalResult().get(8).toString();//[8]//dualM
            conjResult.ismZarfMafalun.accplurarlMafalun = "";
            conjResult.ismZarfMafalun.gensinMafalun =
                    conjResult.getFinalResult().get(12).toString();//[12]//sinM
            conjResult.ismZarfMafalun.gendualMafalun =
                    conjResult.getFinalResult().get(14).toString();//[14]//dualM
            conjResult.ismZarfMafalun.genplurarMafalun = "";
        } else if (conjResult.getNounFormula().equals("مَفْعِل")) {
            conjResult.ismZarfMafilun.nomsinMafilun = conjResult.getFinalResult().get(0).toString();//sinM
            conjResult.ismZarfMafilun.nomdualMafilun =
                    conjResult.getFinalResult().get(2).toString();//[2]//dualM
            conjResult.ismZarfMafilun.nomplurarMafilun = "";//[4]//plurarM
            conjResult.ismZarfMafilun.accsinMafilun =
                    conjResult.getFinalResult().get(6).toString();//[6]//sinM
            conjResult.ismZarfMafilun.accdualMafilun =
                    conjResult.getFinalResult().get(8).toString();//[8]//dualM
            conjResult.ismZarfMafilun.accpluralMafilun = "";
            conjResult.ismZarfMafilun.gensinMafilun =
                    conjResult.getFinalResult().get(12).toString();//[12]//sinM
            conjResult.ismZarfMafilun.gendualMafilun =
                    conjResult.getFinalResult().get(14).toString();//[14]//dualM
            conjResult.ismZarfMafilun.genplurarMafilun = "";
        } else if (conjResult.getNounFormula().equals("مَفْعَلَة")) {
            conjResult.ismZarfMafalatun.nomsinMafalatun = conjResult.getFinalResult().get(1).toString();//sinM
            conjResult.ismZarfMafalatun.nomdualMafalatun =
                    conjResult.getFinalResult().get(3).toString();//[2]//dualM
            conjResult.ismZarfMafalatun.nomplurarMafalatun = "";//[4]//plurarM
            conjResult.ismZarfMafalatun.accsinMafalatun =
                    conjResult.getFinalResult().get(7).toString();//[6]//sinM
            conjResult.ismZarfMafalatun.accdualMafalatun =
                    conjResult.getFinalResult().get(9).toString();//[8]//dualM
            conjResult.ismZarfMafalatun.accplurarlMafalatun = "";
            conjResult.ismZarfMafalatun.gensinMafalatun =
                    conjResult.getFinalResult().get(13).toString();//[12]//sinM
            conjResult.ismZarfMafalatun.gendualMafalatun =
                    conjResult.getFinalResult().get(15).toString();//[14]//dualM
            conjResult.ismZarfMafalatun.genplurarMafalatun = "";
        }
        return conjResult;
    }

}
