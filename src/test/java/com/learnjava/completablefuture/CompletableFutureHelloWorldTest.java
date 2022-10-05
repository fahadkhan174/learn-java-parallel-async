package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.learnjava.service.HelloWorldService;

class CompletableFutureHelloWorldTest {

	HelloWorldService hws = new HelloWorldService();
	CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld(hws);

	@Test
	void testHelloWorld() {

		CompletableFuture<String> cFuture = cfhw.helloWorld();

		cFuture.thenAccept(result -> assertEquals("HELLO WORLD", result)).join();
	}

	@Test
	void testHelloWorldMultipleAsync() {

		String result = cfhw.helloWorldMultipleAsync();

		assertEquals("HELLO WORLD!", result);
	}
	
	@Test
	void testHelloWorldThreeAsync() {

		String result = cfhw.helloWorldThreeAsync();

		assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncLogs() {

		String result = cfhw.helloWorldThreeAsyncLogs();

		assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncLogsAsync() {

		String result = cfhw.helloWorldThreeAsyncLogsAsync();

		assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncCustomThreadPool() {

		String result = cfhw.helloWorldThreeAsyncCustomThreadPool();

		assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncCustomThreadPoolAsync() {

		String result = cfhw.helloWorldThreeAsyncCustomThreadPoolAsync();

		assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThenCompose() {

		CompletableFuture<String> cFuture = cfhw.helloWorldThenCompose();

		cFuture
			.thenAccept(result -> assertEquals("HELLO WORLD!", result))
			.join();
	}

}
