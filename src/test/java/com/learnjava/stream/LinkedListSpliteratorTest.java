package com.learnjava.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.RepeatedTest;

import com.learnjava.util.DataSet;

class LinkedListSpliteratorTest {

	LinkedListSpliterator linkedListSpliterator = new LinkedListSpliterator();
	
	@RepeatedTest(value = 5)
	void testMultipleEachValue() {
		int size = 1000000;
		LinkedList<Integer> inputList = (LinkedList<Integer>) DataSet.generateIntegerLinkedList(size);

		assertEquals(size, linkedListSpliterator.multipleEachValue(inputList, 2, false).size());
	}

	@RepeatedTest(value = 5)
	void testMultipleEachValueParallel() {
		int size = 1000000;
		LinkedList<Integer> inputList = (LinkedList<Integer>) DataSet.generateIntegerLinkedList(size);

		assertEquals(size, linkedListSpliterator.multipleEachValue(inputList, 2, true).size());
	}
}
