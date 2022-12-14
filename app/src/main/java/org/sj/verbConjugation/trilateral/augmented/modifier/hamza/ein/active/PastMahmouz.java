package org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.active;

import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.augmented.modifier.hamza.ein.AbstractEinMahmouz;

import java.util.LinkedList;
import java.util.List;

public class PastMahmouz extends AbstractEinMahmouz {
    private final List substitutions = new LinkedList();

    public PastMahmouz() {
        substitutions.add(new InfixSubstitution("وْءَ", "وْءَ")); // EX: (اسْتَوْءَى)
        substitutions.add(new InfixSubstitution("يْءَ", "يْئَ")); // EX: (ايْئَسَ)
        substitutions.add(new InfixSubstitution("ْءَ", "ْأَ")); // EX: (أسْأمَ، اجْأَلَّ، استرْأَفَ، اجْأَوَّى)
        substitutions.add(new InfixSubstitution("َءَّ", "َأَّ")); // EX: (رأَّسَ، ترأَّفَ، )
        substitutions.add(new InfixSubstitution("َءَ", "َأَ")); // EX: (انذَأَجَ، ابتَأَسَ، )
        substitutions.add(new InfixSubstitution("َّءَ", "َّأَ")); // EX: (اتَّأَدَ، )
        substitutions.add(new InfixSubstitution("اءَ", "اءَ")); // EX: (لاءَمَ، تفاءَلَ، )
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
