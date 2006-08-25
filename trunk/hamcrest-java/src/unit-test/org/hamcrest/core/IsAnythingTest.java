/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsAnything.anything;

public class IsAnythingTest extends AbstractMatcherTest {
    public void testAlwaysEvaluatesToTrue() {
        assertThat(null, anything());
        assertThat(new Object(), anything());
        assertThat("hi", anything());
    }

    public void testHasUsefulDefaultDescription() {
        assertDescription("ANYTHING", anything());
    }

    public void testCanOverrideDescription() {
        String description = "description";
        assertDescription(description, anything(description));
    }
}