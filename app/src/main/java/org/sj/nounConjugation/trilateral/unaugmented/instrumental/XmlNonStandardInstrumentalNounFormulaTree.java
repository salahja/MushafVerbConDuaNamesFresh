package org.sj.nounConjugation.trilateral.unaugmented.instrumental;

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
public class XmlNonStandardInstrumentalNounFormulaTree {
    private final List<XmlNonStandardInstrumentalNounFormula> formulas = new LinkedList<XmlNonStandardInstrumentalNounFormula>();

    public XmlNonStandardInstrumentalNounFormulaTree() {
    }

    public void addFormula(XmlNonStandardInstrumentalNounFormula formula) {
        formulas.add(formula);
    }

    public List<XmlNonStandardInstrumentalNounFormula> getFormulaList() {
        return formulas;
    }

}
