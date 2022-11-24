package org.sj.nounConjugation.trilateral.unaugmented.modifier.assimilate;

import org.sj.nounConjugation.trilateral.unaugmented.modifier.AbstractGeminator;
import org.sj.verbConjugation.trilateral.Substitution.ExpressionInfixSubstitution;

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
public class Geminator extends AbstractGeminator {
    List substitutions = new LinkedList();

    public Geminator() {
        substitutions.add(new ExpressionInfixSubstitution("ْC3َC3", "َC3ّ"));// EX: (أصَمّ)
        substitutions.add(new ExpressionInfixSubstitution("َC3ْC3", "َC3ّ"));// EX: (صَمَّاء، رَيَّان، رَثٌّ)
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
