package org.sj.verbConjugation.trilateral.augmented.modifier.hamza.lam.passive;

import org.sj.verbConjugation.trilateral.Substitution.InfixSubstitution;
import org.sj.verbConjugation.trilateral.Substitution.SuffixSubstitution;
import org.sj.verbConjugation.trilateral.augmented.modifier.hamza.lam.AbstractLamMahmouz;

import java.util.LinkedList;
import java.util.List;

public class PastMahmouz extends AbstractLamMahmouz {
    private final List substitutions = new LinkedList();

    public PastMahmouz() {
        substitutions.add(new SuffixSubstitution("يءَ", "يءَ")); // EX: (أُسِيءَ، )
        substitutions.add(new InfixSubstitution("يء", "يئ")); // EX: (أسِيئَا، أُسِيئُوا)
        substitutions.add(new InfixSubstitution("ُءْ", "ُؤْ")); // EX: (استُؤْتُ، )
        substitutions.add(new InfixSubstitution("ِء", "ِئ")); // EX: (أُجْزِئَ، كُوفِئَ، انْفُقِئَ، ابتُدِئَ، تُدُورِئَ، استُهْزِئَ، احْزُوزِئَ، )
        substitutions.add(new InfixSubstitution("ِّء", "ِّئ")); // EX: (جُزِّئَ، تُخُبِّئَ، )
    }

    public List getSubstitutions() {
        return substitutions;
    }
}
