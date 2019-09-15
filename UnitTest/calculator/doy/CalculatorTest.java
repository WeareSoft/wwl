package calculator;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private Calculator cal;

    @Before
    public void setUp() throws Exception {
        cal = new Calculator("2 + 3");
    }

    @Test
    public void getCalculate_plus() {
        assertEquals(5, cal.getCalculate("+", 2, 3));
    }

    @Test
    public void getCalculate_minus() {
        assertEquals(-1, cal.getCalculate("-", 2, 3));
    }

    @Test
    public void getCalculate_multiply() {
        assertEquals(6, cal.getCalculate("*", 2, 3));
    }

    @Test
    public void getCalculate_divide() {
        assertEquals(2, cal.getCalculate("/", 4, 2));
    }

    @Test
    public void continuousCalculationTest() {
        assertThat(cal.continuousCalculation()).isEqualTo(5);
    }
}