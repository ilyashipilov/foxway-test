package name.shipilov.foxway.service.impl;

import name.shipilov.foxway.service.ArrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HOME on 30.03.2017.
 */
@EnableAutoConfiguration
@ComponentScan("name.shipilov.foxway")
public class ArrayServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ArrayService arrayService;

    @Test
    public void minimalSumOfTwoElementsTest() {
        Assert.assertNotNull(arrayService);
        final List<Integer> data = Arrays.asList(1, 7, 3, 9, 6, 2, 7);
        Assert.assertEquals(arrayService.minimalSumOfTwoElements(data), 3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void minimalSumOfTwoElementsTestShort() {
        Assert.assertNotNull(arrayService);
        final List<Integer> data = Arrays.asList(1);
        arrayService.minimalSumOfTwoElements(data);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void minimalSumOfTwoElementsTestIllegalValues() {
        Assert.assertNotNull(arrayService);
        final List<Integer> data = Arrays.asList(-1005, 1, 2);
        arrayService.minimalSumOfTwoElements(data);
    }

    @Test
    public void minimalSumOfTwoElementsNoFirstLastBeside() {
        Assert.assertNotNull(arrayService);
        final List<Integer> data = Arrays.asList(1, 2, 3, 9, 6, 4, 7);
        Assert.assertEquals(arrayService.minimalSumOfTwoElementsNoFirstLastBeside(data), 6);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void minimalSumOfTwoElementsNoFirstLastBesideShort() {

        for (int i = -500; i<400; i++)
            System.out.print(i+",");

        Assert.assertNotNull(arrayService);
        final List<Integer> data = Arrays.asList(1, 2, 3, 7);
        arrayService.minimalSumOfTwoElementsNoFirstLastBeside(data);
    }
}
