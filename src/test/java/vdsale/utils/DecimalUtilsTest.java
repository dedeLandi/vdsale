package vdsale.utils;

import org.junit.Assert;
import org.junit.Test;
import vdsale.BasicUnitTest;

public class DecimalUtilsTest extends BasicUnitTest {

    @Test
    public void testFormatTwoDecimalPlacesToString(){
        Assert.assertEquals(DecimalUtils.formatTwoDecimalPlacesToString(2.222222222), "2.22");
        Assert.assertEquals(DecimalUtils.formatTwoDecimalPlacesToString(2), "2");
        Assert.assertEquals(DecimalUtils.formatTwoDecimalPlacesToString(2.012), "2.01");
        Assert.assertEquals(DecimalUtils.formatTwoDecimalPlacesToString(2.000000000003), "2");
    }

    @Test
    public void testFormatTwoDecimalPlacesToDouble(){
        Assert.assertEquals(DecimalUtils.formatTwoDecimalPlacesToDouble(2.222222222), 2.22,0);
        Assert.assertEquals(DecimalUtils.formatTwoDecimalPlacesToDouble(2), 2,0);
        Assert.assertEquals(DecimalUtils.formatTwoDecimalPlacesToDouble(2.012), 2.01,0);
        Assert.assertEquals(DecimalUtils.formatTwoDecimalPlacesToDouble(2.000000000003), 2,0);
    }

}
