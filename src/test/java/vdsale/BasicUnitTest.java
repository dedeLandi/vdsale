package vdsale;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class BasicUnitTest {

    @Test
    public void selfTest(){
        Assert.assertThat(true, CoreMatchers.is(true));
    }
}
