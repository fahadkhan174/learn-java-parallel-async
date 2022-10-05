package com.learnjava.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;

public class CompletableFutureHelloWorld {

	private HelloWorldService helloWorldService;

	public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
		super();
		this.helloWorldService = helloWorldService;
	}

	public CompletableFuture<String> helloWorld() {
		return CompletableFuture.supplyAsync(helloWorldService::helloWorld).thenApply(String::toUpperCase);
	}

	public String helloWorldMultipleAsync() {
		CommonUtil.startTimer();
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
		String result = hello.thenCombine(world, (h, w) -> h + w).thenApply(String::toUpperCase).join();
		
		CommonUtil.timeTaken();
		
		return result;

	}
	
	public String helloWorldThreeAsync() {
		CommonUtil.startTimer();
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
		CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(()-> {
			CommonUtil.delay(1000);
			return " Hi Completable Future";
		});
		String result = hello
				.thenCombine(world, (h, w) -> h + w)
				.thenCombine(hiFuture, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join();
		
		CommonUtil.timeTaken();
		
		return result;
	}
	
	public String helloWorldThreeAsyncLogs() {
		CommonUtil.startTimer();
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
		CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(()-> {
			CommonUtil.delay(1000);
			return " Hi Completable Future";
		});
		String result = hello
				.thenCombine(world, (h, w) -> {
					LoggerUtil.log("thenCombine h/w");
					return h + w;
				})
				.thenCombine(hiFuture, (previous, current) -> {
					LoggerUtil.log("thenCombine previous/current");
					return previous + current;
				})
				.thenApply(s -> {
					LoggerUtil.log("thenApply uppercase");
					return s.toUpperCase();
				})
				.join();
		
		CommonUtil.timeTaken();
		
		return result;
	}
	
	public String helloWorldThreeAsyncLogsAsync() {
		CommonUtil.startTimer();
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
		CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(()-> {
			CommonUtil.delay(1000);
			return " Hi Completable Future";
		});
		String result = hello
				.thenCombineAsync(world, (h, w) -> {
					LoggerUtil.log("thenCombine h/w");
					return h + w;
				})
				.thenCombineAsync(hiFuture, (previous, current) -> {
					LoggerUtil.log("thenCombine previous/current");
					return previous + current;
				})
				.thenApplyAsync(s -> {
					LoggerUtil.log("thenApply uppercase");
					return s.toUpperCase();
				})
				.join();
		
		CommonUtil.timeTaken();
		
		return result;
	}
	
	public String helloWorldThreeAsyncCustomThreadPool() {
		CommonUtil.startTimer();
		
		ExecutorService executorService =  Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
		CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(()-> {
			CommonUtil.delay(1000);
			return " Hi Completable Future";
		}, executorService);
		String result = hello
				.thenCombine(world, (h, w) -> {
					LoggerUtil.log("thenCombine h/w");
					return h + w;
				})
				.thenCombine(hiFuture, (previous, current) -> {
					LoggerUtil.log("thenCombine previous/current");
					return previous + current;
				})
				.thenApply(s -> {
					LoggerUtil.log("thenApply uppercase");
					return s.toUpperCase();
				})
				.join();
		
		CommonUtil.timeTaken();
		
		return result;
	}
	
	public String helloWorldThreeAsyncCustomThreadPoolAsync() {
		CommonUtil.startTimer();
		
		ExecutorService executorService =  Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
		CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(()-> {
			CommonUtil.delay(1000);
			return " Hi Completable Future";
		}, executorService);
		String result = hello
				.thenCombineAsync(world, (h, w) -> {
					LoggerUtil.log("thenCombine h/w");
					return h + w;
				})
				.thenCombineAsync(hiFuture, (previous, current) -> {
					LoggerUtil.log("thenCombine previous/current");
					return previous + current;
				}, executorService)
				.thenApplyAsync(s -> {
					LoggerUtil.log("thenApply uppercase");
					return s.toUpperCase();
				}, executorService)
				.join();
		
		CommonUtil.timeTaken();
		
		return result;
	}
	
	public CompletableFuture<String> helloWorldThenCompose() {
		return CompletableFuture
				.supplyAsync(helloWorldService::hello)
				.thenCompose(previous -> helloWorldService.worldFuture(previous))
				.thenApply(String::toUpperCase);
	}

	public static void main(String[] args) {

		LoggerUtil.log("Done!");

	}
}
