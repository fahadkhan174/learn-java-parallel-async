package com.learnjava.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.RepeatedTest;

import com.learnjava.util.DataSet;

class ArrayListSpliteratorTest {

	ArrayListSpliterator arrayListSpliterator = new ArrayListSpliterator();

	@RepeatedTest(value = 10)
	void testMultipleEachValue() {
		int size = 1000000;
		List<Integer> inputList = DataSet.generateArrayList(size);

		assertEquals(size, arrayListSpliterator.multipleEachValue(inputList, 2, false).size());
	}

	@RepeatedTest(value = 10)
	void testMultipleEachValueParallel() {
		int size = 1000000;
		List<Integer> inputList = DataSet.generateArrayList(size);

		assertEquals(size, arrayListSpliterator.multipleEachValue(inputList, 2, true).size());
	}

}
