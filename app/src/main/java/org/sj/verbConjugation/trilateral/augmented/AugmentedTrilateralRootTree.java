package org.sj.verbConjugation.trilateral.augmented;

import java.util.LinkedList;
import java.util.List;

/**
 * Title:
 * <p>
 * Description:
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company:
 *
 * @author not attributable
 * @version 1.0
 */
public class AugmentedTrilateralRootTree {
    private final List<AugmentedTrilateralRoot> roots = new LinkedList<AugmentedTrilateralRoot>();

    public AugmentedTrilateralRootTree() {
    }

    public void addRoot(AugmentedTrilateralRoot root) {
        roots.add(root);
    }

    public List<AugmentedTrilateralRoot> getRoots() {
        return roots;
    }
}
