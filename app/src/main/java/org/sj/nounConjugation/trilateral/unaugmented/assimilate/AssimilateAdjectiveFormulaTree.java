package org.sj.nounConjugation.trilateral.unaugmented.assimilate;

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
public class AssimilateAdjectiveFormulaTree {
    private final List formulas = new LinkedList();

    public AssimilateAdjectiveFormulaTree() {
    }

    public void addFormula(AssimilateAdjectiveFormula formula) {
        formulas.add(formula);
    }

    public List getFormulaList() {
        return formulas;
    }

}
