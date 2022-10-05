package com.learnjava.completablefuture;

import java.util.concurrent.CompletableFuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;

public class CompletableFutureHelloWorldException {

	private HelloWorldService helloWorldService;

	public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
		super();
		this.helloWorldService = helloWorldService;
	}
	
	public String helloWorldThreeAsyncHandle() {
		CommonUtil.startTimer();
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
		CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(()-> {
			CommonUtil.delay(1000);
			return " Hi Completable Future";
		});
		String result = hello
				.handle((res, exc) -> {
					if(exc != null) {
						LoggerUtil.log("Exception: " + exc.getMessage());
						return "";
					}
					return res;
				})
				.thenCombine(world, (h, w) -> h + w)
				.handle((res, exc) -> {
					if(exc != null) {
						LoggerUtil.log("Exception after world: " + exc.getMessage());
						return "";						
					}
					return res;
				})
				.thenCombine(hiFuture, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join();
		
		CommonUtil.timeTaken();
		
		return result;
	}
	
	public String helloWorldThreeAsyncExceptionally() {
		CommonUtil.startTimer();
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
		CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(()-> {
			CommonUtil.delay(1000);
			return " Hi Completable Future";
		});
		String result = hello
				.exceptionally(exc -> {
					LoggerUtil.log("Exceptionally: " + exc.getMessage());
					return "";
				})
				.thenCombine(world, (h, w) -> h + w)
				.exceptionally(exc -> {
					LoggerUtil.log("Exceptionally after world: " + exc.getMessage());
					return "";	
				})
				.thenCombine(hiFuture, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join();
		
		CommonUtil.timeTaken();
		
		return result;
	}
	
	public String helloWorldThreeAsyncWhenComplete() {
		CommonUtil.startTimer();
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
		CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(()-> {
			CommonUtil.delay(1000);
			return " Hi Completable Future";
		});
		String result = hello
				.whenComplete((res, exc) -> {
					LoggerUtil.log("Log res: " + res);
					if(exc != null) {
						LoggerUtil.log("Exception: " + exc.getMessage());
					}
				})
				.thenCombine(world, (h, w) -> h + w)
				.whenComplete((res, exc) -> {
					LoggerUtil.log("Log res after world: " + res);
					if(exc != null) {
						LoggerUtil.log("Exception after world: " + exc.getMessage());
					}
				})
				.exceptionally(exc -> {
					LoggerUtil.log("Exceptionally after thenCombine: " + exc.getMessage());
					return "";	
				})
				.thenCombine(hiFuture, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join();
		
		CommonUtil.timeTaken();
		
		return result;
	}
}
