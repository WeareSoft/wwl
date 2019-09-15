package calculator.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class StringUtilsTest {

    @Test
    public void notEmptyInput_null() {
        assertThat(StringUtils.notEmptyInput(null)).isEqualTo(false);
    }

    @Test
    public void notEmptyInput_빈_문자열() {
        assertThat(StringUtils.notEmptyInput("")).isEqualTo(false);
    }

    @Test
    public void notEmptyInput_문자열() {
        assertThat(StringUtils.notEmptyInput("input")).isEqualTo(true);
    }


}