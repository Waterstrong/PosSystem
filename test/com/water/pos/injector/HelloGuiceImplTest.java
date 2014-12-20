package com.water.pos.injector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HelloGuiceImplTest {
    @Test
    public void should_get_the_right_string_when_use_guice_injector() throws Exception {
        Injector injector = Guice.createInjector(new MyModule());
        HelloGuice helloGuice = injector.getInstance(HelloGuice.class);
        assertThat(helloGuice.sayHello(), is("Hello, world !"));
    }
}