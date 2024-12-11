package com.example.demo;

import com.example.demo.exception.InvalidMathOperationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.MathOperation.*;

@RestController
@Slf4j
@SpringBootApplication
public class CalculatorApplication {
	public record Operation(String operation,
							double result,
							List<Double> operators){};
	private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorApplication.class);
	private static final List<String> POSSIBLE_OPERATIONS;

	static {
		POSSIBLE_OPERATIONS = new ArrayList<>();

		// Gets the possible math operations
		for (MathOperation op : MathOperation.values())
			POSSIBLE_OPERATIONS.add(op.getOperation());
	}

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

	@GetMapping(path = "{operation}")
	public ResponseEntity<Operation> getOperationResult(@PathVariable String operation,
														@RequestParam double a,
														@RequestParam double b) throws RuntimeException{
		// Verifies if the math operation is valid
		if (!POSSIBLE_OPERATIONS.contains(operation)) {
			LOGGER.error("Invalid operation");
			throw new InvalidMathOperationException("Invalid math operation");
		}

		// Checks if the denominator is zero
		if (operation.equals(DIVISION.getOperation()) && b == 0.0) {
			LOGGER.error("Invalid operator");
			throw new InvalidMathOperationException("In division the denominator can't be zero");
		}

		String operationType = null;
		double result = 0.0;
		switch (operation) {
            case "sum" -> { operationType = "sum"; result = SUM.apply(a, b); }
            case "sub" -> { operationType = "subtraction"; result = SUBTRACTION.apply(a, b);  }
            case "mul" -> { operationType = "multiplication"; result = MULTIPLICATION.apply(a, b); }
            case "div" -> { operationType = "division"; result = DIVISION.apply(a, b); }
        }
		final var operationResult = new Operation(
				operationType, // Math operation
				result, // Result of the operation
				List.of(a, b) // Operators
		);
		return ResponseEntity.ok().body(operationResult);
	}
}
