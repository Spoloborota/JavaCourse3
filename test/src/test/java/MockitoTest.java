import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.endsWith;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.stub;

import java.util.List;
import java.util.Random;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.exceptions.misusing.UnfinishedStubbingException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoTest {

    // проверяем, что был вызов метода
    @Test
    public void simpleMocking(){
        Foo foo = mock(Foo.class);  // given
        Bar bar = new Bar(foo);

        bar.bar("qwe");             // when

        verify(foo).foo("qwe");     // then
    }

    // а если надо проигнорить входной параметр?
    @Test
    public void ignoreParameter(){
        Foo foo = mock(Foo.class);    // given
        Bar bar = new Bar(foo);

        bar.bar(someRandomString());  // when

        verify(foo).foo(anyString()); // then
    }

    private String someRandomString() {
        return String.valueOf(new Random().nextInt());
    }

    // указываем моку(стабу) что именно он должен вернуть
    @Test
    public void stubParameter(){
        Foo foo = mock(Foo.class);             // given
        Bar bar = new Bar(foo);

        when(foo.foo("qwe")).thenReturn("asd"); // так
        stub(foo.foo("qwe")).toReturn("asd"); // или так
        doReturn("asd").when(foo).foo("qwe"); // или так

        assertEquals("asd", bar.bar("qwe"));  // when, then
    }

    // а что елси передали не то, что застабили?
    @Test
    public void stubParameterWrong(){
        Foo foo = mock(Foo.class);             // given
        Bar bar = new Bar(foo);

        when(foo.foo("qwe")).thenReturn("asd");
        // stub(foo.foo("qwe")).toReturn("asd");
        // doReturn("asd").when(foo).foo("qwe");

        assertNull(bar.bar("zxc"));           // when, then
    }

    // а если метод void?
    @Test
    public void simpleVoidMocking(){
        FooVoid foo = mock(FooVoid.class); // given
        BarVoid bar = new BarVoid(foo);

        doNothing().when(foo).foo("qwe");

        bar.bar("qwe");                    // when
        verify(foo).foo("qwe");            // then
    }

    // и все же смысл есть в этом выове
    @Test(expected = IllegalArgumentException.class)
    public void excluding(){               // then2
        FooVoid foo = mock(FooVoid.class); // given
        BarVoid bar = new BarVoid(foo);

        doThrow(new IllegalArgumentException()).when(foo).foo(anyString());
        doNothing().when(foo).foo("qwe");

        bar.bar("qwe");                    // when
        verify(foo).foo("qwe");            // then

        bar.bar("ss");                     // when2
    }

    // а что, если хотим проигнорить параметр?
    @Test
    public void ignoreString(){
        Foo foo = mock(Foo.class);            // given
        Bar bar = new Bar(foo);

        when(foo.foo(anyString())).thenReturn("asd");
        // или так
        // when(foo.foo(any(String.class))).thenReturn("asd");

        assertEquals("asd", bar.bar("qwe")); // when, then
        assertEquals("asd", bar.bar("zxc")); // when2, then2
    }

    // если хотим проверить по регулярке
    @Test
    public void parameterMathes(){
        Foo foo = mock(Foo.class);       // given
        Bar bar = new Bar(foo);

        bar.bar("qwe");                  // when

        verify(foo).foo(matches("...")); // then
    }

    // если хотим проверить по регулярке
    @Test
    public void scenarioMathes(){
        Foo foo = mock(Foo.class);           // given
        Bar bar = new Bar(foo);

        when(foo.foo(matches("..."))).thenReturn("asd");

        assertEquals("asd", bar.bar("qwe")); // when, then
        assertNull(bar.bar("qwer"));         // when2, then2
    }

    // попроще проверки
    @Test
    public void basicMatchers(){
        Foo foo = mock(Foo.class);             // given
        Bar bar = new Bar(foo);

        when(foo.foo(endsWith("we"))).thenReturn("asd");
        when(foo.foo(contains("w"))).thenReturn("asd");
        when(foo.foo(startsWith("qw"))).thenReturn("asd");

        assertEquals("asd", bar.bar("qwe"));  // when, then
    }

    // проверки строк в verify
    @Test
    public void basicMatchersVerify(){
        Foo foo = mock(Foo.class);       // given
        Bar bar = new Bar(foo);

        bar.bar("qwe");                  // when

        verify(foo).foo(endsWith("we")); // then
        verify(foo).foo(contains("w"));
        verify(foo).foo(startsWith("qw"));
    }

    // если хотим сэмулировать сбой
    @Test(expected = Exception.class)  // then
    public void throwException(){
        Foo foo = mock(Foo.class);     // given
        Bar bar = new Bar(foo);

        when(foo.foo(anyString())).thenThrow(new Exception());

        bar.bar("qwe");                // when
    }

    // а что если метод void?
    @Test(expected = Exception.class)       // then
    public void voidThrows(){
        FooVoid foo = mock(FooVoid.class);  // given
        BarVoid bar = new BarVoid(foo);

        doThrow(new Exception()).when(foo).foo("qwe");

        bar.bar("qwe");                    // when
    }

    // хотим проверить количество вызовов
    @Test
    public void checkTimes(){
        Foo foo = mock(Foo.class); // given
        Bar bar = new Bar(foo);

        bar.bar("qwe");            // when
        bar.bar("qwe");
        bar.bar("qwe");
        bar.bar("asd");
                                   // then
        verify(foo, times(3)).foo("qwe");
        verify(foo, atLeastOnce()).foo("qwe");
        verify(foo, never()).foo("zxc");
        verify(foo, atMost(5)).foo(anyString());
    }

    // мокаем класс и подменяем какой-то его метод
    // Все выводы с реального класса, если не перестабить
    @Test
    public void spyParameter(){
        Foo foo = spy(new FooImpl());           // внимание!
        Bar bar = new Bar(foo);

        assertEquals("qwe", bar.bar("qwe"));    // так было

        when(foo.foo("qwe")).thenReturn("asd"); // подмена
        // или так
        doReturn("asd").when(foo).foo("qwe");
        // если не хотим делать лишнего вызова
        // foo.foo("qwe")

        assertEquals("asd", bar.bar("qwe"));    // так стало
    }

    // шпион от мока класса отличается тем, что
    // в шпионе по умолчанию вызываются реальные методы
    @Test
    public void mockWithoutScenario(){
        Foo foo = mock(FooImpl.class);          // given
        Bar bar = new Bar(foo);

        assertNull(bar.bar("qwe"));             // так было

        doReturn("asd").when(foo).foo("qwe");   // сценарий

        assertEquals("asd", bar.bar("qwe"));    // так стало
    }

    // если хотим запрограммировать хитрое поведение
    @Test
    public void someFlow(){
        Foo foo = mock(Foo.class);            // given
        Bar bar = new Bar(foo);

        when(foo.foo("qwe")).thenReturn("asd");
        when(foo.foo("wer")).thenReturn("sdf");

        assertEquals("sdf", bar.bar("wer"));  // when, then
        assertEquals("asd", bar.bar("qwe"));  // when2, then2
        // обрати внимание, порядок не играет роли
    }

    // и все же если мы хотим указать порядок?
    @Test
    public void someStrongFlow(){
        Foo foo = mock(Foo.class);             // given
        Bar bar = new Bar(foo);

        when(foo.foo("qwe")).thenReturn("asd").thenReturn("sdf");
        // или так
        when(foo.foo("qwe")).thenReturn("asd", "sdf");

        assertEquals("asd", bar.bar("qwe"));   // when, then
        assertEquals("sdf", bar.bar("qwe"));   // when2, then2
        assertEquals("sdf", bar.bar("qwe"));   // when3, then3
    }

    // тут программы дополняются - параметры разные
    @Test
    public void cumulativeFlow(){
        Foo foo = mock(Foo.class);             // given
        Bar bar = new Bar(foo);

        when(foo.foo("qwe1")).thenReturn("asd").thenReturn("sdf");
        when(foo.foo("qwe2")).thenReturn("asd", "sdf");

        assertEquals("asd", bar.bar("qwe2"));   // when, then
        assertEquals("asd", bar.bar("qwe1"));   // when2, then2
        assertEquals("sdf", bar.bar("qwe1"));   // when3, then3
        assertEquals("sdf", bar.bar("qwe2"));   // when4, then4
    }

    // а если интеерсует проверка порядка вызовов двух моков
    @Test
    public void twoMocksFlow(){
        Foo foo1 = mock(Foo.class);              // given
        Foo foo2 = mock(Foo.class);
        Bar2 bar = new Bar2(foo1, foo2);

        InOrder inOrder = inOrder(foo1, foo2);

        bar.bar("qwe");                          // when

        inOrder.verify(foo1).foo("qwe");         // then
        inOrder.verify(foo2).foo("qwe");
    }

    // а можно сделать вызов реального метода
    @Test
    public void stubThenCall(){
        Foo foo = mock(FooImpl.class);       // given
        Bar bar = new Bar(foo);

        when(foo.foo("qwe")).thenReturn("asd").thenCallRealMethod();

        assertEquals("asd", bar.bar("qwe")); // when, then
        assertEquals("qwe", bar.bar("qwe")); // when2, then2
        assertEquals("qwe", bar.bar("qwe")); // when3, then3
        assertEquals("qwe", bar.bar("qwe")); // when4, then4
    }
    // обрати внимание, последняя команда будет повторяться всегда

    // а что, если метод void
    @Test
    public void voidCallRealMethod(){
        FooVoid foo = mock(FooVoidImpl.class); // given
        BarVoid bar = new BarVoid(foo);

        doCallRealMethod().when(foo).foo("qwe");

        bar.bar("qwe");                        // when

        verify(foo).foo("qwe");                // then
    }

    // а если хотим ограничить?
    @Test
    public void expectTimes(){
        Foo foo = mock(FooImpl.class); // тут класс, а не интерфейс

        when(foo.foo("qwe")).thenReturn("asd").thenCallRealMethod();

        Bar bar = new Bar(foo);
        assertEquals("asd", bar.bar("qwe"));
        assertEquals("qwe", bar.bar("qwe"));
        assertEquals("qwe", bar.bar("qwe"));

        // используем verify
        verify(foo, times(3)).foo(anyString());
    }

    // если стабом не обойтись?
    @Test
    public void thenAnswer(){
        Foo foo = mock(Foo.class);           // given
        Bar bar = new Bar(foo);

        when(foo.foo(anyString())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                if (args[0].equals("qwe")) {
                    return "asd";
                } else {
                    return "qwe";
                }    // у invocation еще доступны
            }        // Object mock = invocation.getMock();
        });          // Object value = invocation.callRealMethod();

        assertEquals("asd", bar.bar("qwe")); // when, then
        assertEquals("qwe", bar.bar("asd")); // when2, then2
    }

    // а если метод void?
    @Test(expected = RuntimeException.class)  // then
    public void thenAnswerOnVoid(){
        FooVoid foo = mock(FooVoid.class);    // given
        BarVoid bar = new BarVoid(foo);

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation)  {
                throw new RuntimeException();
            }
        }).when(foo).foo(anyString());

        bar.bar("asd");                       // when
    }

    // а что возвращается по умолчанию?
    @Test
    public void byDefaultReturns(){
        List list = mock(List.class);

        assertEquals(0, list.size());
        assertFalse(list.isEmpty());
        assertNull(list.iterator());
        assertEquals("[]", list.subList(1, 2).toString());
    }
    // для коллекций - пустые списки
    // а для примитивов default значения
    // Все остальные объекты - null

    // mockito для проверки равенства использует equals
    @Test
    public void equals(){
        Foo foo = mock(Foo.class);           // given
        Bar bar = new Bar(foo);

        when(foo.foo(eq("qwe"))).thenReturn("asd");
        // но можно и без eq()
        when(foo.foo("qwe")).thenReturn("asd");

        assertEquals("asd", bar.bar("qwe")); // when, then
    }

    // можно даже написать свой матчер
    @Test
    public void matchers(){
        Foo foo = mock(Foo.class);           // given
        Bar bar = new Bar(foo);

        when(foo.foo(argThat(isQwe()))).thenReturn("asd");

        assertEquals("asd", bar.bar("qwe")); // when, then
        assertNull(bar.bar("asd"));          // when, then
    }
    private Matcher<String> isQwe() {
        return new ArgumentMatcher() {
            @Override
            public boolean matches(Object argument) {
                return argument.equals("qwe");
            }
        };
    }

    // если хотим проверить, что мок не дергался вообще
    @Test
    public void neverCallMock(){
        Foo foo = mock(Foo.class);     // given
        Bar bar = new Bar(foo);

        // bar.bar("qwe"); а мок не трогали!

        verifyZeroInteractions(foo);   // then
    }

    // если хотим проверить, что мок не дергался больше
    @Test
    public void neverCallMockMethod(){
        Foo foo = mock(Foo.class);     // given
        Bar bar = new Bar(foo);

        bar.bar("qwe");                // when

        verify(foo).foo("qwe");        // then
        verifyNoMoreInteractions(foo);
        // аналог по-проще
        verify(foo, only()).foo("qwe");
    }

    // а если хотим подглядеть что передавалось методу мока?
    @Test
    public void captures(){
        Foo foo = mock(Foo.class);      // given
        Bar bar = new Bar(foo);

        bar.bar("qwe");                 // when
        bar.bar("asd");
        bar.bar("zxc");
                                        // then
        ArgumentCaptor<String> argument =
                ArgumentCaptor.forClass(String.class);
        verify(foo, times(3)).foo(argument.capture());
        assertEquals("[qwe, asd, zxc]",
                argument.getAllValues().toString());
    }

    // стоит ли очищать состояние мока посреди теста?
    @Test
    public void resetMock(){
        Foo foo = mock(Foo.class);         // given
        Bar bar = new Bar(foo);

        when(foo.foo("qwe")).thenReturn("asd");

        bar.bar("qwe");                    // when
        bar.bar("qwe");
        verify(foo, times(2)).foo("qwe");  // then

        reset(foo); // это плохо пахнет - разделите тест

        bar.bar("qwe");                    // when2
        bar.bar("qwe");
        bar.bar("qwe");
        verify(foo, times(3)).foo("qwe");  // then2
    }

    // для BDD любителей используйте BDDMockito заместь Mockito
    @Test
    public void shouldSmthWhenSmth(){
        Foo foo = mock(Foo.class);       // given
        Bar bar = new Bar(foo);

        given(foo.foo("asd")).willReturn("qwe");
        // или для void методов
        willReturn("qwe").given(foo).foo("asd");

        String result = bar.bar("asd");  // when

        assertThat(result, isQwe());     // then
    }

    // для BDD любителей используйте BDDMockito заместь Mockito
    @Test
    public void shouldSmthWhenSmthForVoid(){
        Foo foo = mock(Foo.class);       // given
        Bar bar = new Bar(foo);

        willReturn("qwe").given(foo).foo("asd");

        String result = bar.bar("asd");  // when

        assertThat(result, isQwe());     // then
    }

    // если не знаем в чем дело
    @Test(expected = UnfinishedStubbingException.class)
    public void checkUssage(){
        Foo foo = mock(Foo.class);      // given
        Bar bar = new Bar(foo);

        // тут ошибка - не хватает willReturn
        given(foo.foo("asd")); //.willReturn("qwe");

        String result = bar.bar("asd"); // when
        assertThat(result, isQwe());    // then

        validateMockitoUsage(); // метод диагностики
    }
        // получите сообщение:
        // org.mockito.exceptions.misusing.UnfinishedStubbingException:
        // Unfinished stubbing detected here:
        // -> at MockitoTest.checkUssage(MockitoTest.java:412)
        //
        // E.g. thenReturn() may be missing.
        // Examples of correct stubbing:
        //    when(mock.isOk()).thenReturn(true);
        //    when(mock.isOk()).thenThrow(exception);
        //    doThrow(exception).when(mock).someVoidMethod();
        // Hints:
        //   1. missing thenReturn()
        //   2. although stubbed methods may return mocks, you cannot inline mock creation (mock()) call inside a thenReturn method (see issue 53)
        //
        //	at Bar.bar(Bar.java:9)
        //	at MockitoTest.checkUssage(MockitoTest.java:413)

}
