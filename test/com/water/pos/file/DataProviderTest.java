package com.water.pos.file;

import com.water.pos.common.FilePath;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataProviderTest {
    @Test
    public void should_read_data_list_successfully_when_give_file_path() throws Exception {
        List<String> strList = DataProvider.read(FilePath.DISCOUNT_FILE);

        assertThat(strList.get(0), is("ITEM000001:75"));
        assertThat(strList.get(1), is("ITEM000005:90"));
    }
}