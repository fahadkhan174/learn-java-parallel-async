package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.learnjava.domain.Inventory;
import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;
import com.learnjava.domain.Review;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import com.learnjava.util.LoggerUtil;

public class ProductServiceUsingCF {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCF(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceUsingCF(ProductInfoService productInfoService, ReviewService reviewService,
			InventoryService inventoryService) {
		this.productInfoService = productInfoService;
		this.reviewService = reviewService;
		this.inventoryService = inventoryService;
	}

	public Product retrieveProductDetails(String productId) {
        stopWatch.start();
        
        CompletableFuture<ProductInfo> productInfoCF =  CompletableFuture
        	.supplyAsync(()-> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCF =  CompletableFuture
            	.supplyAsync(()-> reviewService.retrieveReviews(productId));
        
        Product product = productInfoCF
        	.thenCombine(reviewCF, (productInfo, review) -> {
        		return new Product(productId, productInfo, review);
        	})
        	.join(); // blocking

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }
    
    public CompletableFuture<Product> retrieveProductDetailsServerApproach(String productId) {
        
        CompletableFuture<ProductInfo> productInfoCF =  CompletableFuture
        	.supplyAsync(()-> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCF =  CompletableFuture
            	.supplyAsync(()-> reviewService.retrieveReviews(productId));
        
        return productInfoCF
        	.thenCombine(reviewCF, (productInfo, review) -> {
        		return new Product(productId, productInfo, review);
        	});

    }
    
    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();
        
        CompletableFuture<ProductInfo> productInfoCF =  CompletableFuture
        	.supplyAsync(()-> productInfoService.retrieveProductInfo(productId))
        	.thenApply(productInfo -> {
        		 productInfo.setProductOptions(updateInventory(productInfo));
        		 return productInfo;
        	});
        CompletableFuture<Review> reviewCF =  CompletableFuture
            	.supplyAsync(()-> reviewService.retrieveReviews(productId));
        
        Product product = productInfoCF
        	.thenCombine(reviewCF, (productInfo, review) -> {
        		return new Product(productId, productInfo, review);
        	})
        	.join(); // blocking

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }
    
    public Product retrieveProductDetailsWithInventoryWithCF(String productId) {
        stopWatch.start();
        
        CompletableFuture<ProductInfo> productInfoCF =  CompletableFuture
        	.supplyAsync(()-> productInfoService.retrieveProductInfo(productId))
        	.thenApply(productInfo -> {
        		 productInfo.setProductOptions(updateInventoryWithCF(productInfo));
        		 return productInfo;
        	});
        CompletableFuture<Review> reviewCF =  CompletableFuture
            	.supplyAsync(()-> reviewService.retrieveReviews(productId))
            	.exceptionally(exc -> {
            		LoggerUtil.log("Handled exception from review service" + exc.getMessage());
            		return Review.builder()
            				.noOfReviews(0)
            				.overallRating(0.0)
            				.build();
            	});
        
        Product product = productInfoCF
        	.whenComplete((pr, exc) -> {
        		LoggerUtil.log("Inside whenComplete Product: " + pr);
        		LoggerUtil.log("Exception: " + exc.getMessage());
        	})
        	.thenCombine(reviewCF, (productInfo, review) -> {
        		return new Product(productId, productInfo, review);
        	})
        	.join(); // blocking

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }
    
    private List<ProductOption> updateInventory(ProductInfo productInfo) {
    	return productInfo
    	.getProductOptions()
    	.stream()
    	.map(productOption -> {
    		Inventory inventory = inventoryService.addInventory(productOption);
    		productOption.setInventory(inventory);
    		return productOption;
    	})
    	.collect(Collectors.toList());
    }
    
    private List<ProductOption> updateInventoryWithCF(ProductInfo productInfo) {
    	List<CompletableFuture<ProductOption>> productOptionCFList = productInfo
    	.getProductOptions()
    	.stream()
    	.map(productOption -> {
    		return CompletableFuture
    			.supplyAsync(()-> inventoryService.addInventory(productOption))
    			.thenApply(inventory -> {
    	    		productOption.setInventory(inventory);
    	    		return productOption;
    			});
    	})
    	.collect(Collectors.toList());
    	
    	return productOptionCFList
    		.stream()
    		.map(CompletableFuture::join)
    		.collect(Collectors.toList());
    	
    	
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        InventoryService inventoryService = new InventoryService();
//        ProductServiceUsingCF productService = new ProductServiceUsingCF(productInfoService, reviewService);
        ProductServiceUsingCF productService = new ProductServiceUsingCF(productInfoService, reviewService, inventoryService);
        String productId = "ABC123";
//        Product product = productService.retrieveProductDetails(productId);
        Product product = productService.retrieveProductDetailsWithInventory(productId);
        log("Product is " + product);

    }
}
