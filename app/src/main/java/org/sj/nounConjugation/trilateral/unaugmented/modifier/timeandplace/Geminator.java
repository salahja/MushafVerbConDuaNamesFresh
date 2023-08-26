package org.sj.nounConjugation.trilateral.unaugmented.modifier.timeandplace;

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
    List<ExpressionInfixSubstitution> substitutions = new LinkedList<>();

    public Geminator() {
        substitutions.add(new ExpressionInfixSubstitution("ْC3َC3", "َC3ّ")); // EX: (مَصَدّ، مَصَحَّة)
        substitutions.add(new ExpressionInfixSubstitution("ْC3ِC3", "ِC3ّ")); // EX: (مَرِنّ)
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
