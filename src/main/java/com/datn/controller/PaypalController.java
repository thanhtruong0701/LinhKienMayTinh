package com.datn.controller;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.service.OrderService;
import com.datn.service.PaypalService;
import com.datn.config.PaypalPaymentIntent;
import com.datn.config.PaypalPaymentMethod;
import com.datn.utils.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {
	
	public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    PaypalService paypalService;
    @Autowired
    OrderService orderService;
    
    @PostMapping("/pay")
    public String pay(HttpServletRequest request, @RequestParam("price") double price) {
    double priceUSD = price/23200;
	String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
	String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
	try {
	    Payment payment = paypalService.createPayment(priceUSD, "USD", PaypalPaymentMethod.paypal,
		    PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
	    for (Links links : payment.getLinks()) {
		if (links.getRel().equals("approval_url")) {
		    return "redirect:" + links.getHref();
		}
	    }
	} catch (PayPalRESTException e) {
	    log.error(e.getMessage());
	}
	return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay() {
	System.out.println(" >>> cancelPay()");
	return "order/checkout";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
	try {
	    Payment payment = paypalService.executePayment(paymentId, payerId);
	    if (payment.getState().equals("approved")) {
		return "redirect:/order/detail/"+orderService.findFirstByOrderByIdDesc().getId();
	    }
	} catch (PayPalRESTException e) {
	    log.error(e.getMessage());
	}
	return "order/checkout";
    }
}
