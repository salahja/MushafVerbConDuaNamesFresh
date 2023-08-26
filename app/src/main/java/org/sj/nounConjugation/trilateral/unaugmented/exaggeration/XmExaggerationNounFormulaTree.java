package org.sj.nounConjugation.trilateral.unaugmented.exaggeration;

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
public class XmExaggerationNounFormulaTree {
    private final List<XmExaggerationNounFormula> formulas = new LinkedList<XmExaggerationNounFormula>();

    public XmExaggerationNounFormulaTree() {
    }

    public void addFormula(XmExaggerationNounFormula formula) {
        formulas.add(formula);
    }

    public List<XmExaggerationNounFormula> getFormulaList() {
        return formulas;
    }

}
