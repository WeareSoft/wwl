package calculator;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class StringParserTest {
    private StringParser stringParser;

    @Before
    public void setUp() {
        stringParser = new StringParser();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInput_빈문자열() {
        stringParser.setInput("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInput_null() {
        stringParser.setInput(null);
    }

    @Test
    public void setInput() {
        String input = "2 + 3 + 5";
//        assertThat(stringParser.setInput(input)).isEqualTo(new String[]{"2","+", "3","+", "5"});
        assertArrayEquals(stringParser.setInput(input), new String[]{"2", "+", "3", "+", "5"});
    }

    @Test
    public void nextNum() {
        stringParser.setInput("2 + 3");
        assertThat(stringParser.nextNum()).isEqualTo(2);
        assertThat(stringParser.nextNum()).isEqualTo(3);
    }

    @Test
    public void nextOperator() {
        stringParser.setInput("2 + 3 * 5");
        assertThat(stringParser.nextOperator()).isEqualTo("+");
        assertThat(stringParser.nextOperator()).isEqualTo("*");
    }


}