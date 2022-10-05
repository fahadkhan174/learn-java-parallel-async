package com.learnjava.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.DataSet;

class CheckoutServiceTest {

	PriceValidatorService priceValidatorService = new PriceValidatorService();
	CheckoutService checkoutService = new CheckoutService(priceValidatorService);

	@Test
	void checkCoreCount() {
		System.out.println("Number of cores: " + Runtime.getRuntime().availableProcessors());
		assertEquals(8, Runtime.getRuntime().availableProcessors());
	}

	@BeforeEach
	void clearStopWatch() {
		CommonUtil.stopWatchReset();
	}

	@Test
	void testCheckout6Items() {
		Cart cart = DataSet.createCart(6);

		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
		assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
		assertTrue(checkoutResponse.getFinalRate() > 0);
	}

	@Test
	void testCheckout9Items() {
		Cart cart = DataSet.createCart(9);

		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
		assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
	}

}
