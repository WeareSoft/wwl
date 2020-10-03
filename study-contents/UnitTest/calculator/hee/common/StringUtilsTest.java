package calculator.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


public class StringUtilsTest {

    @Test
    public void notEmptyInput_빈문자() {
        String text = "";

        assertThat(StringUtils.notEmptyInput(text)).isEqualTo(false);
    }

    @Test
    public void notEmptyInput_null() {
        String text = null;

        assertThat(StringUtils.notEmptyInput(text)).isEqualTo(false);
    }

    @Test
    public void null_또는_빈문자가_아닌_문자_Input() {
        String text = "heejeong";

//        assertThat(StringUtils.notEmptyInput(text)).isEqualTo(true);
        assertTrue(StringUtils.notEmptyInput(text));
    }


}