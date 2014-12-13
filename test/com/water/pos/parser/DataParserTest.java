package com.water.pos.parser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DataParserTest {


    private IParser<String> parser;

    @Before
    public void setUp() throws Exception {

        parser = mock(IParser.class);
    }

    @Test
    public void should_map_data_successfully_when_give_one_line_data() throws Exception {
        //given
        ArrayList<String> inputs = new ArrayList<String>();
        inputs.add("line1");

        //when

        //then
        assertThat(DataParser.map(inputs, new StubIdentityParser()).get(0), is("line1"));
    }

    @Test
    public void should_map_data_successfully_when_give_two_lines_data() throws Exception {
        //given
        ArrayList<String> inputs = new ArrayList<String>();
        inputs.add("line1");
        inputs.add("line2");

        //when

        //then
        assertThat(DataParser.map(inputs, new StubIdentityParser()).get(0), is("line1"));
        assertThat(DataParser.map(inputs, new StubIdentityParser()).get(1), is("line2"));
    }

    @Test
    public void should_map_data_successfully_when_mock_two_lines_data() throws Exception {
        //given
        ArrayList<String> inputs = new ArrayList<String>();
        inputs.add("line1");
        inputs.add("line2");

        given(parser.parse("line1")).willReturn("mapped1");
        given(parser.parse("line2")).willReturn("mapped2");

        //when
        List<String> result = DataParser.map(inputs, parser);

        //then
        assertThat(result.get(0), is("mapped1"));
        assertThat(result.get(1), is("mapped2"));
    }


    public class StubIdentityParser implements IParser<String> {

        @Override
        public String parse(String line) {
            return line;
        }
    }
}