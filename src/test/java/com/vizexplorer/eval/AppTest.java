package com.vizexplorer.eval;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{

  private ByteArrayOutputStream outContent;

  @Test(expected = ParseException.class)
  public void testCreateWithInvalidDate() throws ParseException, DatabaseException
  {
    String[] args = new String[]{"create", "", "", "bad date"};
    App.main(args);
  }

  @Test
  public void testValidCreation() throws ParseException, DatabaseException
  {
    String[] args = new String[]{"create", "Biff", "Male", "19950110"};
    App.main(args);

    assertTrue(outContent.toString().startsWith("Person instance created: Person [id="));
  }

  @Test(expected = InvalidParameterException.class)
  public void testInvalidValidCommand() throws ParseException, DatabaseException
  {
    String[] args = new String[]{"duplicate", "Biff", "Male", "19950110"};
    App.main(args);

    assertTrue(outContent.toString().startsWith("Person instance created: com.vizexplorer.eval.Person@"));
  }

  @Before
  public void before()
  {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void after()
  {
    System.setOut(null);
  }
}
