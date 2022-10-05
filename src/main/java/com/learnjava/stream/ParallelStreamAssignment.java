package com.learnjava.stream;

import java.util.List;
import java.util.stream.Collectors;

public class ParallelStreamAssignment {

	public static void main(String[] args) {
		List<String> names = namesList();
		List<String> resultList = string_toLowerCase(names);
		System.out.println(resultList);

	}

	public static List<String> string_toLowerCase(List<String> names) {
		return names.parallelStream().map(String::toLowerCase).collect(Collectors.toList());
	}

	public static List<String> namesList() {
		return List.of("Bob", "Jamie", "Jill", "Rick");

	}

}
