package com.learnjava.service;

import java.util.List;
import java.util.stream.Collectors;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;

public class CheckoutService {

	private PriceValidatorService priceValidatorService;

	public CheckoutService(PriceValidatorService priceValidatorService) {
		super();
		this.priceValidatorService = priceValidatorService;
	}

	public CheckoutResponse checkout(Cart cart) {
		CommonUtil.startTimer();
		List<CartItem> priceValidationList = cart.getCartItemList().parallelStream().map(cartItem -> {
			boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
			cartItem.setExpired(isPriceInvalid);
			return cartItem;
		}).filter(CartItem::isExpired).collect(Collectors.toList());
		CommonUtil.timeTaken();

		if (!priceValidationList.isEmpty()) {
			return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
		}

//		double finalPrice = calculateFinalPrice(cart);
		double finalPrice = calculateFinalPriceReduce(cart);
		LoggerUtil.log("Checkkout complete and finalPrice is " + finalPrice);

		return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
	}

	private double calculateFinalPrice(Cart cart) {
		return cart.getCartItemList().parallelStream().map(cartItem -> cartItem.getRate() * cartItem.getQuantity())
				.collect(Collectors.summingDouble(Double::doubleValue));
	}

	private double calculateFinalPriceReduce(Cart cart) {
		return cart.getCartItemList().parallelStream().map(cartItem -> cartItem.getRate() * cartItem.getQuantity())
				.reduce(0.0, Double::sum);
	}

}
