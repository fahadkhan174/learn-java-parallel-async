package com.learnjava.stream;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.stopWatchReset;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.learnjava.util.DataSet;

class ParallelStreamsExampleTest {

	ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

	@BeforeEach
	void clearStopWatch() {
		stopWatchReset();
	}

	@Test
	void testStringTransform() {
		// given
		List<String> names = DataSet.namesList();

		// when
		startTimer();
		List<String> resultList = parallelStreamsExample.stringTransformParallel(names);
		timeTaken();

		// then
		assertEquals(4, resultList.size());
		resultList.forEach(name -> assertTrue(name.contains("-")));
	}

	@ParameterizedTest
	@ValueSource(booleans = { false, true })
	void testStringTransformDynamic(boolean isParallel) {
		// given
		List<String> names = DataSet.namesList();

		// when
		startTimer();
		List<String> resultList = parallelStreamsExample.stringTransformDynamic(names, isParallel);
		timeTaken();

		// then
		assertEquals(4, resultList.size());
		resultList.forEach(name -> assertTrue(name.contains("-")));
	}

}
