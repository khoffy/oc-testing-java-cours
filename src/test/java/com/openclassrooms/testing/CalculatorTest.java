package com.openclassrooms.testing;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(LoggingExtension.class)
public class CalculatorTest {

	private static Instant startedAt;

	private Calculator calculatorUnderTest;

	private static Logger logger;

	public void setLogger(Logger logger) {
		CalculatorTest.logger = logger;
	}
	@BeforeEach
	void initCalculator() {
		System.out.println("Appel avant chaque test");
		calculatorUnderTest = new Calculator();
	}

	@AfterEach
	void undefCalculator() {
		System.out.println("Appel après chaque test");
		calculatorUnderTest = null;
	}

	@BeforeAll
	static void initStartingTime() {
		System.out.println("Appel avant tous les tests");
		startedAt = Instant.now();
	}

	@AfterAll
	static void showTestDuration() {
		System.out.println("Appel après tous les tests");
		Instant endedAt = Instant.now();
		long duration = Duration.between(startedAt, endedAt).toMillis();
		System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
	}

	@Test
	@Tag("FourOperations") // This test is among 4 basis operations tests
	void testAddTwoPositiveNumbers() {
		// Arrange
		int a = 2;
		int b = 3;

		// Act
		int somme = calculatorUnderTest.add(a, b);

		// Assert
		assertThat(somme).isEqualTo(5);
		assertEquals(5, somme);
	}

	@Test
	@Tag("FourOperations")
	void multiply_shouldReturnTheProduct_ofTwoIntegers() {
		// Arrange
		int a = 42;
		int b = 11;

		// Act
		int produit = calculatorUnderTest.multiply(a, b);

		// Assert
		assertEquals(462, produit);
	}

	@ParameterizedTest(name = "{0} x 0 doit être égal à 0")
	@ValueSource(ints = { 1, 2, 42, 1011, 5089 })
	void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
		// Arrange -- Tout est prêt !

		// Act -- Multiplier par zéro
		int actualResult = calculatorUnderTest.multiply(arg, 0);

		// Assert -- ça vaut toujours zéro !
		assertEquals(0, actualResult);
	}

	@ParameterizedTest(name = "{0} + {1} doit être égal à {2}")
	@CsvSource({ "1,1,2", "2,3,5", "42,57,99" })
	void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
		// Arrange -- Tout est prêt !

		// Act
		int actualResult = calculatorUnderTest.add(arg1, arg2);

		// Assert
		assertEquals(expectResult, actualResult);
	}

	@Timeout(1)
	@Test
	void longCalcul_shouldComputeInLessThan1Second() {
		// Arrange

		// Act
		calculatorUnderTest.longCalculation();

		// Assert
		// ...
	}

	@Test
	void listDigits_shouldReturnsTheListOfDigits_ofPositiveInteger() {
		// GIVEN
		int number = 95897;

		// WHEN
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

		// THEN
		assertThat(actualDigits).containsExactlyInAnyOrder(9, 5, 8, 7);
		Set<Integer> expectedDigits = Stream.of(5, 7, 8, 9).collect(Collectors.toSet());
		assertEquals(expectedDigits, actualDigits);
	}

	@Test
	void listDigits_shouldReturnsTheListOfDigits_ofNegativeInteger() {
		int number = -124432;
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);
		assertThat(actualDigits).containsExactlyInAnyOrder(1, 2, 3, 4);
	}

	@Test
	void listDigits_shouldReturnsTheListOfZero_ofZero() {
		int number = 0;
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);
		assertThat(actualDigits).containsExactly(0);
	}

}
