package org.sj.nounConjugation.trilateral.unaugmented.exaggeration;

import org.sj.nounConjugation.IUnaugmentedTrilateralNounConjugator;
import org.sj.nounConjugation.NounFormula;
import org.sj.verbConjugation.trilateral.unaugmented.UnaugmentedTrilateralRoot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class NonStandardExaggerationConjugator implements IUnaugmentedTrilateralNounConjugator {
    private static final NonStandardExaggerationConjugator instance = new NonStandardExaggerationConjugator();
    private final Map formulaClassNamesMap = new HashMap();
    //map <symbol,formulaName>
    private final Map formulaSymbolsNamesMap = new HashMap();

    private NonStandardExaggerationConjugator() {
        for (int i = 1; i <= 10; i++) {
            String formulaClassName = getClass().getPackage().getName() + ".nonstandard.NounFormula" + i;
            try {
                Class formulaClass = Class.forName(formulaClassName);
                NonStandardExaggerationNounFormula nonStandardExaggerationNounFormula = (NonStandardExaggerationNounFormula) formulaClass.newInstance();
                formulaClassNamesMap.put(nonStandardExaggerationNounFormula.getFormulaName(), formulaClass);
                formulaSymbolsNamesMap.put(nonStandardExaggerationNounFormula.getSymbol(), nonStandardExaggerationNounFormula.getFormulaName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static NonStandardExaggerationConjugator getInstance() {
        return instance;
    }

    public NounFormula createNoun(UnaugmentedTrilateralRoot root, int suffixNo, String formulaName) {
        Object[] parameters = {root, suffixNo + ""};
        try {
            Class formulaClass = (Class) formulaClassNamesMap.get(formulaName);
            NounFormula noun = (NounFormula) formulaClass.getConstructors()[1].newInstance(parameters);
            return noun;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List createNounList(UnaugmentedTrilateralRoot root, String formulaName) {
        List result = new LinkedList();
        for (int i = 0; i < 18; i++) {
            NounFormula noun = createNoun(root, i, formulaName);
            result.add(noun);
        }
        return result;

    }

    public List getAppliedFormulaList(UnaugmentedTrilateralRoot root) {
        //todo xml
        //  XmExaggerationNounFormulaTree formulaTree =  DatabaseManager.getInstance().getExaggerationNounFormulaTree(root.getC1());
        XmExaggerationNounFormulaTree formulaTree = null;
        if (formulaTree == null)
            return null;
        List result = new LinkedList();
        Iterator iter = formulaTree.getFormulaList().iterator();
        while (iter.hasNext()) {
            XmExaggerationNounFormula formula = (XmExaggerationNounFormula) iter.next();
            if (formula.getC2() == root.getC2() && formula.getC3() == root.getC3()) {
                if (formula.getForm1() != null && formula.getForm1() != "")
                    //add the formula pattern insteaed of the symbol (form1)
                    result.add(formulaSymbolsNamesMap.get(formula.getForm1()));
                //may the verb has two forms of instumentals
                if (formula.getForm2() != null && formula.getForm2() != "")
                    //add the formula pattern insteaed of the symbol (form2)
                    result.add(formulaSymbolsNamesMap.get(formula.getForm2()));
                //may the verb has two forms of instumentals
                if (formula.getForm3() != null && formula.getForm3() != "")
                    //add the formula pattern insteaed of the symbol (form3)
                    result.add(formulaSymbolsNamesMap.get(formula.getForm3()));

            }
        }
        return result;

    }

}
