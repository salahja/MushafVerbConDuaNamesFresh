package org.sj.nounConjugation.trilateral.unaugmented.modifier.instrumental;

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
public class InstrumentalModifier implements IUnaugmentedTrilateralNounModifier {
    private static final InstrumentalModifier instance = new InstrumentalModifier();
    private final Geminator geminator = new Geminator();
    private final Vocalizer vocalizer = new Vocalizer();
    private final Mahmouz mahmouz = new Mahmouz();

    private InstrumentalModifier() {
    }

    public static InstrumentalModifier getInstance() {
        return instance;
    }

    public ConjugationResult build(UnaugmentedTrilateralRoot root, int kov, List conjugations, String formula) {
        ConjugationResult conjResult = new ConjugationResult(kov, root,   conjugations, formula);
        geminator.apply(conjResult.getFinalResult(), root);
        vocalizer.apply(conjResult);
        mahmouz.apply(conjResult);
        NounLamAlefModifier.getInstance().apply(conjResult);
        NounSunLamModifier.getInstance().apply(conjResult);

        if (conjResult.getNounFormula().equals("مِفْعَل")) {
            conjResult.ismAlaMifalun.nomsinMifalun = conjResult.getFinalResult().get(0).toString();//sinM
            conjResult.ismAlaMifalun.nomdualMifalun =
                    conjResult.getFinalResult().get(2).toString();//[2];//dualM
            conjResult.ismAlaMifalun.nomplurarMifalun = "";//[4];//plurarM
            conjResult.ismAlaMifalun.accsinMifalun =
                    conjResult.getFinalResult().get(6).toString();//[6];//sinM
            conjResult.ismAlaMifalun.accdualMifalun =
                    conjResult.getFinalResult().get(8).toString();//[8];//dualM
            conjResult.ismAlaMifalun.accplurarlMifalun = "";
            conjResult.ismAlaMifalun.gensinMifalun =
                    conjResult.getFinalResult().get(12).toString();//[12];//sinM
            conjResult.ismAlaMifalun.gendualMifalun =
                    conjResult.getFinalResult().get(14).toString();//[14];//dualM
            conjResult.ismAlaMifalun.genplurarMifalun = "";
        } else if (conjResult.getNounFormula().equals("مِفْعَلَة")) {
            conjResult.ismAlaMifalatun.nomsinMifalatun = conjResult.getFinalResult().get(1).toString();//sinM
            conjResult.ismAlaMifalatun.nomdualMifalatun =
                    conjResult.getFinalResult().get(3).toString();//[2];//dualM
            conjResult.ismAlaMifalatun.nomplurarMifalatun = "";//[4];//plurarM
            conjResult.ismAlaMifalatun.accsinMifalatun =
                    conjResult.getFinalResult().get(7).toString();//[6];//sinM
            conjResult.ismAlaMifalatun.accdualMifalatun =
                    conjResult.getFinalResult().get(9).toString();//[8];//dualM
            conjResult.ismAlaMifalatun.accplurarlMifalatun = "";
            conjResult.ismAlaMifalatun.gensinMifalatun =
                    conjResult.getFinalResult().get(13).toString();//[12];//sinM
            conjResult.ismAlaMifalatun.gendualMifalatun =
                    conjResult.getFinalResult().get(15).toString();//[14];//dualM
            conjResult.ismAlaMifalatun.genplurarMifalatun = "";
        } else if (conjResult.getNounFormula().equals("مِفْعَال")) {
            conjResult.ismAlaMifaalun.nomsinMifaalun = conjResult.getFinalResult().get(0).toString();//sinM
            conjResult.ismAlaMifaalun.nomdualMifaalun=
                    conjResult.getFinalResult().get(2).toString();//[2];//dualM
            conjResult.ismAlaMifaalun.nomplurarMifaalun= "";//[4];//plurarM
            conjResult.ismAlaMifaalun.accsinMifaalun=
                    conjResult.getFinalResult().get(6).toString();//[6];//sinM
            conjResult.ismAlaMifaalun.accdualMifaalun=
                    conjResult.getFinalResult().get(8).toString();//[8];//dualM
            conjResult.ismAlaMifaalun.accplurarlMifaalun= "";
            conjResult.ismAlaMifaalun.gensinMifaalun=
                    conjResult.getFinalResult().get(12).toString();//[12];//sinM
            conjResult.ismAlaMifaalun.gendualMifaalun=
                    conjResult.getFinalResult().get(14).toString();//[14];//dualM
            conjResult.ismAlaMifaalun.genplurarMifaalun= "";
        }

        return conjResult;
    }

}
