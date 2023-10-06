package org.sj.nounConjugation.trilateral.augmented.modifier.activeparticiple;

import org.sj.nounConjugation.NounLamAlefModifier;
import org.sj.nounConjugation.NounSunLamModifier;
import org.sj.nounConjugation.trilateral.augmented.modifier.Substituter;
import org.sj.verbConjugation.trilateral.augmented.AugmentedTrilateralRoot;
import org.sj.verbConjugation.trilateral.augmented.MazeedConjugationResult;
import org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer.FormulaApplyingChecker;
import org.sj.verbConjugation.trilateral.augmented.modifier.vocalizer.IFormulaApplyingChecker;

import java.util.List;

/**
 * <p>Title: Sarf Program</p>
 *
 * <p>Description: ����� �������� ������ ��� ��� ������
 * ������ �������� �� ������� ������ ������
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ALEXO</p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public class ActiveParticipleModifier {
    private static final ActiveParticipleModifier instance = new ActiveParticipleModifier();
    private final Substituter substituter = new Substituter();
    private final Geminator geminator = new Geminator();
    private final Vocalizer vocalizer = new Vocalizer();
    private final Mahmouz mahmouz = new Mahmouz();

    private ActiveParticipleModifier() {
    }

    public static ActiveParticipleModifier getInstance() {
        return instance;
    }

    // AugmentedTrilateralModifierListener listener //todo
    public MazeedConjugationResult build(AugmentedTrilateralRoot root, int kov, int formulaNo, List<Object> conjugations, boolean listener) {
        MazeedConjugationResult conjResult = new MazeedConjugationResult(kov, formulaNo, root, conjugations);
        substituter.apply(conjResult);
        geminator.apply(conjResult);
        boolean applyVocalization = true;
        int result = FormulaApplyingChecker.getInstance().check(root, formulaNo);
        if (result == IFormulaApplyingChecker.NOT_VOCALIZED) {
            applyVocalization = false;
        } else if (result == IFormulaApplyingChecker.TWO_STATE) {
            if (listener)
                //  if (listener == null)
                applyVocalization = true;
            else
                //asking the listener to apply or not the vocaliztion
                //    applyVocalization = listener.doSelectVocalization();
                applyVocalization = true;
        }
        if (applyVocalization) {
            vocalizer.apply(conjResult);
        }
        mahmouz.apply(conjResult);
        NounLamAlefModifier.getInstance().apply(conjResult);
        NounSunLamModifier.getInstance().apply(conjResult);

        conjResult.faelMafool.nomsinM = conjResult.getFinalResult().get(0).toString();//[0]//sinM
        conjResult.faelMafool.nomdualM = conjResult.getFinalResult().get(2).toString();//[2]//dualM
        conjResult.faelMafool.nomplurarM = conjResult.getFinalResult().get(4).toString();//[4]//plurarM
        conjResult.faelMafool.accsinM = conjResult.getFinalResult().get(6).toString();//[6]//sinM
        conjResult.faelMafool.accdualM = conjResult.getFinalResult().get(8).toString();//[8]//dualM
        conjResult.faelMafool.accplurarlM = conjResult.getFinalResult().get(10).toString();//[10]//plurarlM
        conjResult.faelMafool.gensinM = conjResult.getFinalResult().get(12).toString();//[12]//sinM
        conjResult.faelMafool.gendualM = conjResult.getFinalResult().get(14).toString();//[14]//dualM
        conjResult.faelMafool.genplurarM = conjResult.getFinalResult().get(16).toString();//[16]//plurarM
        conjResult.faelMafool.nomsinF = conjResult.getFinalResult().get(1).toString();//[1]//sinF
        conjResult.faelMafool.nomdualF = conjResult.getFinalResult().get(3).toString();//[3]//dualF
        conjResult.faelMafool.nomplurarF = conjResult.getFinalResult().get(5).toString();//[5]//plurarF
        conjResult.faelMafool.accsinF = conjResult.getFinalResult().get(7).toString();//[7]//sinF
        conjResult.faelMafool.accdualF = conjResult.getFinalResult().get(9).toString();//[9]//dualF
        conjResult.faelMafool.accplurarlF = conjResult.getFinalResult().get(11).toString();//[11]//plurarlF
        conjResult.faelMafool.gensinF = conjResult.getFinalResult().get(13).toString();//[13]//sinF
        conjResult.faelMafool.gendualF = conjResult.getFinalResult().get(15).toString();//[15]//dualF
        conjResult.faelMafool.genplurarF = conjResult.getFinalResult().get(17).toString();//[17]//plurarF
        return conjResult;
    }

}

