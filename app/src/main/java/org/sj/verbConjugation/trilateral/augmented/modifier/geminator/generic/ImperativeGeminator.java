package org.sj.verbConjugation.trilateral.augmented.modifier.geminator.generic;

import org.sj.verbConjugation.trilateral.Substitution.ExpressionInfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.ExpressionSuffixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SubstitutionsApplier;

import java.util.LinkedList;
import java.util.List;

public class ImperativeGeminator extends SubstitutionsApplier {
    private final List<org.sj.verbConjugation.trilateral.Substitution.Substitution> substitutions = new LinkedList<org.sj.verbConjugation.trilateral.Substitution.Substitution>();

    public ImperativeGeminator() {
        substitutions.add(new ExpressionInfixSubstitution("C1ْC3ِC3ُ", "C1ِC3ُّ")); // EX: (أَتِمُّوا)
        substitutions.add(new ExpressionInfixSubstitution("C1ْC3ِC3َ", "C1ِC3َّ")); // EX: (أَتِمَّا)
        substitutions.add(new ExpressionInfixSubstitution("C1ْC3ِC3ِ", "C1ِC3ِّ")); // EX: (أَتِمِّي)
        substitutions.add(new ExpressionSuffixSubstitution("C1ْC3ِC3ْ", "C1ِC3َّ")); // EX: (أَتِمَّ)
        substitutions.add(new ExpressionInfixSubstitution("C3ِC3ُ", "C3ُّ")); // EX: (انقَضُّوا، حاجُّوا)
        substitutions.add(new ExpressionInfixSubstitution("C3ِC3َ", "C3َّ")); // EX: (انْقَضَّا، حاجَّا)
        substitutions.add(new ExpressionInfixSubstitution("C3ِC3ِ", "C3ِّ")); // EX: (انْقَضِّي، حاجِّي)
        substitutions.add(new ExpressionSuffixSubstitution("C3ِC3ْ", "C3َّ")); // EX: (انْقَضَّ، حاجَّ)
        substitutions.add(new ExpressionSuffixSubstitution("اC3َC3ْ", "اC3َّ")); // EX: (تآجَّ)
        substitutions.add(new ExpressionInfixSubstitution("اC3َC3ْن", "اC3َC3ْن"));// EX: (تآجَجْنَ)
        substitutions.add(new ExpressionInfixSubstitution("اC3َC3", "اC3ّ")); // EX: (تآجُّوا، تآجَّا، تآجِّي)
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
