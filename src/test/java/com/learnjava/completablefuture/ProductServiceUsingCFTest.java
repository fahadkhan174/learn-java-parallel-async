package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

class ProductServiceUsingCFTest {

	private ProductInfoService productInfoService = new ProductInfoService();
	private ReviewService reviewService = new ReviewService();
	private InventoryService inventoryService = new InventoryService();
	ProductServiceUsingCF pscf = new ProductServiceUsingCF(productInfoService, reviewService, inventoryService);

	@Test
	void testRetrieveProductDetails() {
		String productId = "ABC123";

		Product product = pscf.retrieveProductDetails(productId);

		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		assertNotNull(product.getReview());
	}
	
	@Test
	void testRetrieveProductDetailsWithInventory() {
		String productId = "ABC123";

		Product product = pscf.retrieveProductDetailsWithInventory(productId);

		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		product
			.getProductInfo()
			.getProductOptions()
			.forEach(productOption -> assertNotNull(productOption.getInventory()));
		assertNotNull(product.getReview());
	}
	
	@Test
	void testRetrieveProductDetailsWithInventoryWithCF() {
		String productId = "ABC123";

		Product product = pscf.retrieveProductDetailsWithInventoryWithCF(productId);

		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		product
			.getProductInfo()
			.getProductOptions()
			.forEach(productOption -> assertNotNull(productOption.getInventory()));
		assertNotNull(product.getReview());
	}

	
}
