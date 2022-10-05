package com.learnjava.stream;

import static com.learnjava.util.CommonUtil.delay;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;
import com.learnjava.util.DataSet;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamsExample {

	public static void main(String[] args) {
		List<String> names = DataSet.namesList();
		ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();
		startTimer();
		List<String> resultList = parallelStreamsExample.stringTransformParallel(names);
		timeTaken();
		log("resultList" + resultList);

	}

	public List<String> stringTransformParallel(List<String> names) {
		return names.parallelStream().map(this::addNameLengthTransform).collect(Collectors.toList());
	}

	public List<String> stringTransformDynamic(List<String> names, boolean isParallel) {
		Stream<String> namesStream = names.stream();
		if (isParallel) {
			namesStream.parallel();
		}
		return namesStream.map(this::addNameLengthTransform).collect(Collectors.toList());
	}

	private String addNameLengthTransform(String name) {
		delay(500);
		return name.length() + " - " + name;
	}
}
