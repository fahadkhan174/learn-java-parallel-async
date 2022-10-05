package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learnjava.service.HelloWorldService;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {
	
	@Mock
	private HelloWorldService helloWorldService = mock(HelloWorldService.class);
	
	@InjectMocks
	CompletableFutureHelloWorldException cfhwe;

	@Test
	void testHelloWorldThreeAsyncHandle() {
		// given
		when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
		when(helloWorldService.world()).thenCallRealMethod();
		String result = cfhwe.helloWorldThreeAsyncHandle();
		assertEquals(" WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncHandle_2() {
		// given
		when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occured"));
		String result = cfhwe.helloWorldThreeAsyncHandle();
		assertEquals(" HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncHandle_3() {
		// given
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();
		String result = cfhwe.helloWorldThreeAsyncHandle();
		assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncHandle_Exceptionally() {
		// given
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();
		String result = cfhwe.helloWorldThreeAsyncHandle();
		assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncHandle_Exceptionally_2() {
		// given
		when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occured"));
		//when
		String result = cfhwe.helloWorldThreeAsyncHandle();
		//then
		assertEquals(" HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncHandle_WhenComplete() {
		// given
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();
		String result = cfhwe.helloWorldThreeAsyncWhenComplete();
		assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
	}
	
	@Test
	void testHelloWorldThreeAsyncHandle_WhenComplete_2() {
		// given
		when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occured"));
		//when
		String result = cfhwe.helloWorldThreeAsyncWhenComplete();
		//then
		assertEquals(" HI COMPLETABLE FUTURE", result);
	}

}
