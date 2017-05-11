package com.skyisland.d20.die;

import java.util.Random;

/**
 * Simulates the roll of a die.
 * @author Skyler
 *
 */
public class Die {
	
	/**
	 * Performs a roll of the die.
	 * Returns a number between 1 and the number of sides (inclusive).
	 * @param sides
	 * @return
	 */
	public static int roll(int sides) {
		Random rand = new Random(); //make new random each time
		return rand.nextInt(sides) + 1;
	}
	
}
