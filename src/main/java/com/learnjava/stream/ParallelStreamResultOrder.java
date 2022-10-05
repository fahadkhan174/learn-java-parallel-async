package com.learnjava.stream;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.learnjava.util.LoggerUtil;

public class ParallelStreamResultOrder {

	public static void main(String[] args) {

		List<Integer> integerList = List.of(1, 2, 3, 4, 5, 6, 7, 8);
		LoggerUtil.log("inputList: " + integerList);

		List<Integer> resultList = listOrder(integerList);
		LoggerUtil.log("resultList: " + resultList);

		Set<Integer> resultSet = setOrder(integerList);
		LoggerUtil.log("resultList: " + resultSet);

	}

	public static List<Integer> listOrder(List<Integer> inputList) {
		return inputList.parallelStream().map(integer -> integer * 2).collect(Collectors.toList());
	}

	public static Set<Integer> setOrder(List<Integer> inputList) {
		return inputList.parallelStream().map(integer -> integer * 2).collect(Collectors.toSet());
	}

}
