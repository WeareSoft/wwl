package calculator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class StringCalculatorTest {

    @Test
    public void findDelimeter_커스텀구분자() {
        String text = "//&/n1&3";
        assertThat(StringCalculator.findDelimeter(text))
                .contains("&")
                .contains("1&3")
                .doesNotContain(",|:");
        System.out.println(StringCalculator.findDelimeter(text)[0]);

        text = "//#/n1#3#5";
        assertThat(StringCalculator.findDelimeter(text))
                .contains("#")
                .contains("1#3#5")
                .doesNotContain(",|:");
    }

    @Test
    public void findDelimeter_컴마_또는_콜론_기본구분자() {
        String text = "1,2,3";
        assertThat(StringCalculator.findDelimeter(text))
                .contains(",|:")
                .contains(text);

        text = "1:2:3";
        assertThat(StringCalculator.findDelimeter(text))
                .contains(",|:")
                .contains(text);
    }

    @Test
    public void splitDelimeter_기본구분자() {
        String str = "1,2:3";
        String delimiter = ",|:";
        int[] nums = StringCalculator.split(new String[]{delimiter, str});
        assertThat(nums)
                .contains(1)
                .contains(2)
                .doesNotContain(5); // TODO 굳이 체크할 필요가 없다.
        // TODO 순서대로 숫자가 들어가는지 확인
    }

    @Test
    public void splitDelimeter_커스텀구분자() {
        String str = "1&3&5";
        String delimiter = "&";
        int[] nums = StringCalculator.split(new String[] {delimiter, str});
        assertThat(nums)
                .contains(1)
                .contains(3)
                .doesNotContain(6);
    }

//    @Test(expected=RuntimeException.class)
//    public void splitDelimeter_음수() {
//        String str = "-1,4,5";
//        String delimiter = ",|:";
//        int[] nums = StringCalculator.split(new String[] {delimiter, str});
////        assertThrows(RuntimeException.class, () -> {
////        });
//    }
//
//    @Test(expected=RuntimeException.class)
//    public void splitDelimeter_변환불가문자() {
//        String str = "A&4&6";
//        String delimiter = "&";
//        int[] nums = StringCalculator.split(new String[] {delimiter, str});
//    }

    @Test
    public void convertToInt() {
        String[] stringNumbers = {"1", "2", "3"};
        int[] expected = {1, 2, 3}; // TODO 변경

        assertArrayEquals(StringCalculator.convertToInt(stringNumbers), expected);
    }

    @Test(expected = RuntimeException.class)
    public void convertToInt_음수() {
        String [] nums = {"-1", "2", "3"};
        StringCalculator.convertToInt(nums);
    }

    @Test(expected = RuntimeException.class)
    public void convertToInt_변환불가문자() {
        String [] nums = {"A", "2", "3"};
        StringCalculator.convertToInt(nums);
    }

    // 조건에 맞는 매개변수가 들어온다고 생각.
    @Test
    public void addTest() {
        int [] nums = {1, 2, 3};
        assertThat(StringCalculator.add(nums)).isEqualTo(6);
    }
}
