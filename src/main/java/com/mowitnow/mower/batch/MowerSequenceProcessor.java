package com.mowitnow.mower.batch;

import com.mowitnow.mower.entities.Position;
import com.mowitnow.mower.exceptions.FileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * A processor for mower movement sequences that converts each input sequence
 * into a final position and orientation of a mower on a lawn.
 * Each input sequence must follow the format: "lawnWidth lawnHeight initialX initialY initialOrientation movementInstructions".
 */
public class MowerSequenceProcessor implements ItemProcessor<String, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MowerSequenceProcessor.class);

	private final Map<Character, UnaryOperator<Position>> movements = initializeMovements();
	private final Map<Character, Character> rightRotations = initializeRightRotations();
	private final Map<Character, Character> leftRotations = initializeLeftRotations();

	private  int lawnWidth ;
	private  int lawnHeight ;

	@Override
	public String process(final String sequence) throws FileFormatException {
		try {
			return processSequence(sequence);
		} catch (RuntimeException e) {
			throw new FileFormatException("Invalid file content, error processing input data: " + sequence, e);
		}
	}

	private String processSequence(final String sequence) {
		final String[] parts = sequence.split(" ");
		  lawnWidth = Integer.parseInt(parts[0]);
		  lawnHeight = Integer.parseInt(parts[1]);
		final StringBuilder finalPositions = new StringBuilder();

		for (int i = 2; i < parts.length; i += 4) {
			final Position position = processMower(parts, i);
			appendPosition(finalPositions, position);
		}

		return finalPositions.toString().trim();
	}

	private Position processMower(final String[] parts, int index) {
		Position position = new Position(Integer.parseInt(parts[index]), Integer.parseInt(parts[index + 1]));
		char orientation = parts[index + 2].charAt(0);
		final String instructions = parts[index + 3];

		for (char instruction : instructions.toCharArray()) {
			if (instruction == 'A') {
				position = movements.get(orientation).apply(position);
			} else {
				orientation = processRotation(instruction, orientation);
			}
		}

		position.setOrientation(orientation);
		return position;
	}

	private char processRotation(char instruction, char orientation) {
        return switch (instruction) {
            case 'D' -> rightRotations.get(orientation);
            case 'G' -> leftRotations.get(orientation);
            default -> {
                LOGGER.warn("Unexpected instruction: {}", instruction);
                yield orientation;
            }
        };
	}

	private void appendPosition(StringBuilder finalPositions, Position position) {
		finalPositions.append(position.getX())
				.append(" ")
				.append(position.getY())
				.append(" ")
				.append(position.getOrientation())
				.append("\n");
	}

	private Map<Character, UnaryOperator<Position>> initializeMovements() {
		return Map.of(
				'N', position -> position.getY() < lawnHeight ? position.moveVertical(1) : position,
				'E', position -> position.getX() < lawnWidth ? position.moveHorizontal(1) : position,
				'S', position -> position.getY() > 0 ? position.moveVertical(-1) : position,
				'W', position -> position.getX() > 0 ? position.moveHorizontal(-1) : position
		);
	}

	private Map<Character, Character> initializeRightRotations() {
		return Map.of(
				'N', 'E',
				'E', 'S',
				'S', 'W',
				'W', 'N'
		);
	}

	private Map<Character, Character> initializeLeftRotations() {
		return Map.of(
				'N', 'W',
				'W', 'S',
				'S', 'E',
				'E', 'N'
		);
	}
}
