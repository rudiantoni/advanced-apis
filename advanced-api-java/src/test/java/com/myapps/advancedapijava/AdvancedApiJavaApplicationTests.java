package com.myapps.advancedapijava;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
class AdvancedApiJavaApplicationTests {

	Calculator underTest = new Calculator();

  class Calculator {
    int add(int a, int b) {
      return a + b;
    }
  }

  @Test
  void itShouldAddTwoNumbers() {
		// given
		int numberOne = 20;
		int numberTwo = 30;
		//when
		int result = underTest.add(numberOne, numberTwo);
		// then
    int expected = 50;
		assertThat(result).isEqualTo(expected);
  }

}
