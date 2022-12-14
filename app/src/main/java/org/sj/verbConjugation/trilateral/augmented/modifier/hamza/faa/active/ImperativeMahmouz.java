package org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.active;

import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.AbstractFaaMahmouz;

import java.util.LinkedList;
import java.util.List;

public class ImperativeMahmouz extends AbstractFaaMahmouz {
    private final List substitutions = new LinkedList();

    public ImperativeMahmouz() {
        substitutions.add(new InfixSubstitution("أَءْ", "آ"));// EX: (آثِرْ،  )
        substitutions.add(new InfixSubstitution("ءَا", "آ"));// EX: (آجِرْ، تآكَلْ، )
        substitutions.add(new InfixSubstitution("ْءَ", "ْأَ"));// EX: (انْأطِرْ، )
        substitutions.add(new InfixSubstitution("اءْ", "ائْ"));// EX: (ائْتَمِرْ، )
        substitutions.add(new InfixSubstitution("َءَ", "َأَ"));// EX: (تَأَكَّدْ، )
        substitutions.add(new InfixSubstitution("َءْ", "َأْ"));// EX: (اسْتَأْمِرْ، )
        substitutions.add(new InfixSubstitution("َءِ", "َئِ"));// EX: (اسْتَئِمَّ، أَئِسْ)
        substitutions.add(new InfixSubstitution("ءَ", "أَ"));// EX: (أثِّرْ، )
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
