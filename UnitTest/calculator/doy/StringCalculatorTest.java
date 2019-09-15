package calculator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {

    @Test(expected = RuntimeException.class)
    public void 음수_입력() {
        StringCalculator.run("-1-7-5");
    }

    @Test(expected = RuntimeException.class)
    public void 숫자_이외의_값_입력() {
        StringCalculator.run("숫자이외의값");
    }

    @Test
    public void 빈_문자열_입력() {
        assertEquals(0, StringCalculator.run(""));
    }

    @Test
    public void null_입력() {
        assertEquals(0, StringCalculator.run(null));
    }

    @Test
    public void findDelimiter_기본_구분자() {
        String text = "1,2,3";
        assertThat(StringCalculator.findDelimiter(text))
                .contains(",|:", text);
    }

    @Test
    public void findDelimiter_커스텀_구분자() {
        String text = "//&/n1&3";
        assertThat(StringCalculator.findDelimiter(text))
                .contains("&", "1&3")
                .doesNotContain(",|:");
    }

    @Test
    public void splitDelimiter() {
        String str = "1,2:3";
        String delimiter = ",|:";
        int[] nums = StringCalculator.split(new String[]{ delimiter, str });
        assertThat(nums).contains(1, 2, 3).doesNotContain(5);
    }

    @Test
    public void convertToInt() {
        String[] stringNumbers = {"1", "2", "3"};
        assertThat(StringCalculator.convertToInt(stringNumbers))
                .contains(1, 2, 3)
                .doesNotContain(6);
    }

    @Test
    public void addTest() {
        int [] nums = {1, 2, 3};
        assertThat(StringCalculator.add(nums)).isEqualTo(6);
    }
}
