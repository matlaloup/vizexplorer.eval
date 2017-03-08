package com.vizexplorer.eval;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
  public ByteArrayOutputStream outContent = null;
  @Before
  public void setup()
  {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void teardown()
  {
    System.setOut(null);
  }

  @Test
  public void testMain()
  {
    String [] args = new String[]{"", "Biff", "Male", "19950110"};
    App.main(args);

    assertTrue(outContent.toString().startsWith("Person instance created: com.vizexplorer.eval.Person@"));
  }
}
