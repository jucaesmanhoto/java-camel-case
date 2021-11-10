import exceptions.InvalidEntryException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@FixMethodOrder
public class CamelCaseTest {
    private CamelCase camelCase;
    @Before
    public void setUp(){
        camelCase = new CamelCase();
    }

    @Test(expected = InvalidEntryException.class)
    public void test01_ShouldThrowAnErrorIfStartsWithANumber(){
        camelCase.converterCamelCase("10Firsts");
    }

    @Test
    public void test02_ShouldReturnTheRightStringForASingleLowerCase(){
        String actual = camelCase.converterCamelCase("name").get(0);
        String expected = "name";

        assertEquals(expected, actual);
    }

    @Test
    public void test03_ShouldThrowAnErrorIfContainsSpecialCharacters(){
        String expected = "Must not contains any special characters.";
        try {
            camelCase.converterCamelCase("name#surname");
            fail();
        }catch (Exception e){
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void test04_ShouldReturnsLowerCaseIfIsOnlyOneWord(){
        String actual = camelCase.converterCamelCase("Name").get(0);
        String expected = "name";

        assertEquals(expected, actual);
    }

    @Test
    public void test05_ShouldReturnAllUpCasedIfOneUpCasedWord(){
        String actual = camelCase.converterCamelCase("CPF").get(0);
        String expected = "CPF";

        assertEquals(expected, actual);
    }

    @Test
    public void test06_ShouldReturnLowerCasedStringsForMultipleEntries(){

        List<String> actual = camelCase.converterCamelCase("nameSurname");

        assertEquals("name", actual.get(0));
        assertEquals("surname", actual.get(1));
        assertEquals(2, actual.size());
    }

    @Test
    public void test07_ShouldReturnLowerCasedStringsForMultipleEntriesStartingWithCapital(){

        List<String> actual = camelCase.converterCamelCase("NameSurname");

        assertEquals("name", actual.get(0));
        assertEquals("surname", actual.get(1));
        assertEquals(2, actual.size());
    }

    @Test
    public void test08_ShouldReturnRightResultsForNumbersInTheString(){

        List<String> actual = camelCase.converterCamelCase("Get10Firsts");

        assertEquals("get", actual.get(0));
        assertEquals("10", actual.get(1));
        assertEquals("firsts", actual.get(2));
        assertEquals(3, actual.size());
    }

    @Test
    public void test09_ShouldReturnRightResultsForCapitalLettersTheString(){

        List<String> actual = camelCase.converterCamelCase("numberCPF");

        assertEquals("number", actual.get(0));
        assertEquals("CPF", actual.get(1));
        assertEquals(2, actual.size());
    }

    @Test
    public void test010_ShouldReturnRightResultsForCapitalLettersTheString(){

        List<String> actual = camelCase.converterCamelCase("numberCPFCitizen");

        assertEquals("number", actual.get(0));
        assertEquals("CPF", actual.get(1));
        assertEquals("citizen", actual.get(2));
        assertEquals(3, actual.size());
    }
}
