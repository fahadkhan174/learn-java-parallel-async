package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCFExceptionTest {
	
	@InjectMocks
	private ProductServiceUsingCF pscf;
	
	@Mock
	private ProductInfoService pism;
	@Mock
    private ReviewService rsm;
	@Mock
    private InventoryService ism;

	@Test
	void testRetrieveProductDetailsWithInventoryWithCF() {
		//given
		String productId ="ABC123";
		when(pism.retrieveProductInfo(any())).thenCallRealMethod();
		when(rsm.retrieveReviews(any())).thenThrow(new RuntimeException("Exception Occured"));
		when(ism.addInventory(any())).thenCallRealMethod();
		
		//when
		Product product = pscf.retrieveProductDetailsWithInventoryWithCF(productId);
		
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		product
			.getProductInfo()
			.getProductOptions()
			.forEach(productOption -> assertNotNull(productOption.getInventory()));
		assertNotNull(product.getReview());
		assertEquals(0, product.getReview().getNoOfReviews());
		assertEquals(0.0, product.getReview().getOverallRating());
		
	}
	
	@Test
	void testRetrieveProductDetailsWithInventoryWithCFWhenComplete() {
		//given
		String productId ="ABC123";
		when(pism.retrieveProductInfo(any())).thenThrow(new RuntimeException("Exception Occured"));
		when(rsm.retrieveReviews(any())).thenCallRealMethod();
		
		Assertions.assertThrows(RuntimeException.class, () -> pscf.retrieveProductDetailsWithInventoryWithCF(productId));	
	}

}
