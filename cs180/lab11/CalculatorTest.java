import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class CalculatorTest extends TestCase {

  Calculator calc = new Calculator();
  
  /**
   * Test the Add method with valid input and negative input
   */
  public void testAdd() {
    //Test Normal functionality
    try {
      assertEquals("Method did not add properly",calc.add(4,5), 9);
    }
    catch(NegativeArgumentException e) {
      fail("Method did not add properly");
    }

    //Test Negative input handling
    try {
      calc.add(-4,5);
      fail("Calculator did not throw NegativeArgumentException");
    }
    catch(NegativeArgumentException e) {
    }
  }
  
  /**
   * Test the subtract method with valid input, negative input, and a negative Result
   */
  public void testSubtract() {
    //Test normal functionality
    try {
      assertEquals("Method did not subtract properly",calc.subtract(5,4), 1);
    }
    catch(NegativeArgumentException e) {
      fail("Method did not subtract properly");
    }
    catch(NegativeResultException e) {
      fail("Method did not subtract properly");
    }
    
    //Test negative input
    try {
      calc.subtract(-4,5);
      fail("Calculator did not throw NegativeArgumentException");
    }
    catch(NegativeArgumentException e) {
    }
    catch(NegativeResultException e) {
      fail("Calculator did not throw NegativeArgumentException");
    }
    
    //test a negative result
    try {
      calc.subtract(4,5);
      fail("Calculator did not throw NegativeResultException");
    }
    catch(NegativeArgumentException e) {
      fail("Calculator did not throw NegativeResultException");
    }
    catch(NegativeResultException e) {
    }
  }
  
  /**
   * Test the multiply method with valid input and negative input
   */
  public void testMultiply() {
    //Test valid input
    try {
      assertEquals("Method did not multiply properly",calc.multiply(3,4), 12);
    }
    catch(NegativeArgumentException e) {
      fail("Method did not multiply properly");
    }
    
    //Test Negative input
    try {
      calc.multiply(-4,5);
      fail("Calculator did not throw NegativeArgumentException");
    }
    catch(NegativeArgumentException e) {
    }
  }
  
  /**
   * Test the divide method with valid input, negative input, and dividing by zero
   */
  public void testDivide() {
    //Test valid input
    try {
      assertEquals("Method did not divide properly",calc.divide(10,5), 2);
    }
    catch(NegativeArgumentException e) {
      fail("Method did not divide properly");
    }
    catch(ArithmeticException e) {
      fail("Method did not divide properly");
    }
    
    //Test negative input
    try {
      calc.divide(-4,5);
      fail("Calculator did not throw NegativeArgumentException");
    }
    catch(NegativeArgumentException e) {
    }
    catch(ArithmeticException e) {
      fail("Calculator did not throw NegativeArgumentException");
    }
    
    //Test divide by zero
    try {
      calc.divide(10,0);
      fail("Calculator did not throw ArithmeticException");
    }
    catch(NegativeArgumentException e) {
      fail("Calculator did not throw ArithmeticException");
    }
    catch(ArithmeticException e) {
    }
  }
  
}
