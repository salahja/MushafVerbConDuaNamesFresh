package org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.passive;

import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.augmented.modifier.hamza.faa.AbstractFaaMahmouz;

import java.util.LinkedList;
import java.util.List;

public class PresentMahmouz extends AbstractFaaMahmouz {
    private final List substitutions = new LinkedList();

    public PresentMahmouz() {
        substitutions.add(new InfixSubstitution("أُءْ", "أُو")); // EX: (أُوثَرُ، أوْتَمَرُ، )
        substitutions.add(new InfixSubstitution("َءَا", "َآ")); // EX: (يُتَآكَلُ، )
        substitutions.add(new InfixSubstitution("ْءَا", "ْآ")); // EX: (يُنآدُ )
        substitutions.add(new InfixSubstitution("ُءْ", "ُؤْ")); // EX: (يُؤْثَرُ، يُؤْتَمَرُ، )
        substitutions.add(new InfixSubstitution("ُءَ", "ُؤَ")); // EX: (يُؤَثَّرُ، يُؤَاجَرُ، )
        substitutions.add(new InfixSubstitution("ْءَ", "ْأَ")); // EX: (يُنْأَطَرُ، )
        substitutions.add(new InfixSubstitution("َءَ", "َأَ")); // EX: (يُتَأكَّدُ، )
        substitutions.add(new InfixSubstitution("َءْ", "َأْ")); // EX: (يُسْتأمَرُ، )
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
