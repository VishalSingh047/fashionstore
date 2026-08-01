package com.fashionstore.fashionstore.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fashionstore.fashionstore.dto.PaymentRequest;
import com.fashionstore.fashionstore.dto.PaymentResponse;
import com.fashionstore.fashionstore.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Value("${razorpay.base-url}")
    private String razorpayUrl;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public PaymentServiceImpl() {
        this.restClient = RestClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public PaymentResponse createOrder(PaymentRequest request) {

        try {

            Map<String, Object> body = new HashMap<>();

            body.put("amount", request.getAmount() * 100);

            body.put("currency", "INR");

            body.put("receipt", "receipt_" + System.currentTimeMillis());

            String response = restClient.post()
                    .uri(razorpayUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(headers -> {
                        headers.setBasicAuth(keyId, keySecret);
                    })
                    .body(body)
                    .retrieve()
                    .body(String.class);

            JsonNode json = objectMapper.readTree(response);

            return new PaymentResponse(
                    json.get("id").asText(),
                    json.get("amount").asInt(),
                    json.get("currency").asText(),
                    keyId
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create Razorpay Order: " + e.getMessage(), e);
        }
    }
}