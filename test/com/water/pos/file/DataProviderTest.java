package com.water.pos.file;

import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataProviderTest {
    @Test
    public void should_read_data_list_successfully_when_give_buffered_reader() throws Exception {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("Hello ThoughtWorks", "I'm Waterstrong");
        when(reader.ready()).thenReturn(true, true, false);

        List<String> strList = DataProvider.read(reader);

        assertThat(strList.get(0), is("Hello ThoughtWorks"));
        assertThat(strList.get(1), is("I'm Waterstrong"));
    }
}