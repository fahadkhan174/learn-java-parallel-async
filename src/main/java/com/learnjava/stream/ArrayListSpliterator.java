package com.learnjava.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.learnjava.util.CommonUtil;

public class ArrayListSpliterator {

	public List<Integer> multipleEachValue(List<Integer> inputList, Integer value, boolean isParallel) {

		CommonUtil.startTimer();
		Stream<Integer> integerStream = inputList.stream();

		if (isParallel) {
			integerStream.parallel();
		}

		List<Integer> resultList = integerStream.map(integer -> integer * value).collect(Collectors.toList());

		CommonUtil.timeTaken();

		return resultList;
	}

}
