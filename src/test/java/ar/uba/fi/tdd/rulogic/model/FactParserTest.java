package ar.uba.fi.tdd.rulogic.model;

import ar.uba.fi.tdd.rulogic.model.exceptions.InvalidFactException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.mockito.MockitoAnnotations.initMocks;

public class FactParserTest {

    @InjectMocks
    private FactParser factParser;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testSingletonImplementation() {
        Assert.assertNotEquals(FactParser.getInstance(), null);
        Assert.assertSame(FactParser.getInstance(), FactParser.getInstance());

    }

    @Test
    public void testFactNameParsing() throws InvalidFactException {
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("name(param1).");
        Assert.assertEquals("name", fact.getName());
    }
    @Test(expected = InvalidFactException.class)
    public void testFactShouldEndWithADot() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("name(param1)");
        Assert.assertEquals("name", fact.getName());
    }
    @Test
    public void testFactNameParsingWithFinalSpace() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("name (param1).");
        Assert.assertEquals("name", fact.getName());
    }
    @Test
    public void testFactNameParsingWithSpaces() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse(" name (param1).");
        Assert.assertEquals("name", fact.getName());
    }
    @Test
    public void testFactParameterParsingWithSpaces() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse(" name (param1,param2).");
        String[] expectedParameters = {"param1","param2"};
        Assert.assertArrayEquals(expectedParameters, fact.getParams());
    }

    @Test
    public void testFactParameterParsingWithoutSpaces() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("name(param1,param2).");
        String[] expectedParameters = {"param1","param2"};
        Assert.assertArrayEquals(expectedParameters, fact.getParams());
    }

    @Test
    public void testFactParameterParsingWithSpacesInParamsStructure() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("name( param1 , param2).");
        String[] expectedParameters = {"param1","param2"};
        Assert.assertArrayEquals(expectedParameters, fact.getParams());
    }

    @Test(expected = InvalidFactException.class)
    public void testFactParameterParsingWithInvalidName() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("n ame( param1 , param2).");
    }
    @Test(expected = InvalidFactException.class)
    public void testFactParameterParsingWithInvalidNameBecauseComma() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("n,ame( param1 , param2).");
    }

    @Test(expected = InvalidFactException.class)
    public void testFactParameterParsingWithInvalidParamBecauseDot() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("n.ame( para.m1 , param2).");
    }
    @Test(expected = InvalidFactException.class)
    public void testFactParameterParsingWithInvalidLastParameter() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("n.ame( para.m1 , ).");
    }
    @Test
    public void testFactParameterParsingWithOnlyOneParameter() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("name( param1 ). ");
        String[] expectedParameters = {"param1"};
        Assert.assertArrayEquals(expectedParameters, fact.getParams());
        Assert.assertEquals("name", fact.getName());
    }

    @Test(expected = InvalidFactException.class)
    public void testFactParameterParsingWithoutName() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("  ( param1 ). ");
    }


    @Test(expected = InvalidFactException.class)
    public void testFactWithoutParamStructure() throws InvalidFactException{
        FactParser factParser = FactParser.getInstance();
        Fact fact = factParser.parse("  name.");
    }


}
