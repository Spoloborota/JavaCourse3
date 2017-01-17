import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.*;


import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MockitoTestSetup {
    private Foo foo;
    private Bar bar;

    @Before
    public void init() {
        foo = Mockito.mock(Foo.class);
        bar = new Bar(foo);
    }

    @Test
    public void test() {
        bar.bar("qwe");
        Mockito.verify(foo).foo("qwe");
    }
}
