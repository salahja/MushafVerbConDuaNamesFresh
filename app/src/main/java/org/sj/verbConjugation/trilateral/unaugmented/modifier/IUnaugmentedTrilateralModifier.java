package org.sj.verbConjugation.trilateral.unaugmented.modifier;

import org.sj.verbConjugation.trilateral.unaugmented.ConjugationResult;


/**
 * <p>Title: Sarf Program</p>
 *
 * <p>Description: abstract interface to be implemented from the child classes
 * which will modify the verbs
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ALEXO</p>
 *
 * @author Haytham Mohtasseb Billah
 * @version 1.0
 */
public interface IUnaugmentedTrilateralModifier {
    boolean isApplied(ConjugationResult conjugationResult);
}
