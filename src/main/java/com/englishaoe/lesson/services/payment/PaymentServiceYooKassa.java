package com.englishaoe.lesson.services.payment;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.entity.transactions.Payment;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.PaymentService;
import com.englishaoe.lesson.dto.payment.PaymentDTO;
import com.englishaoe.lesson.dto.payment.PaymentMapper;
import com.englishaoe.lesson.dto.payment.PaymentRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.UUID;


@Service
public class PaymentServiceYooKassa {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CustomerServices customerServices;
    @Value("${YOOKASSA_SHOP_ID}")
    private String shopId;
    @Value("${YOOKASSA_API_KEY}")
    private String apiKey;
    @Value("${YOOKASSA_URL}")
    private String apiUrl;
    @Value("${YOOKASSA_RETURN_URL}")
    private String backUrl;

    public String createPayment(PaymentRequest paymentRequest, Long customerId){

        try{
            Customer customer = customerServices.getCustomerById(customerId);
            Long paymentId = paymentService.createDefaultPayment(paymentRequest, customerId);
            String receipt = String.format(
                    "{\"items\": [{\"description\": \"%s\", \"quantity\": %d, \"amount\": {\"value\": \"%.2f\", \"currency\": \"%s\"}, " +
                            "\"vat_code\": 1, \"payment_subject\": \"commodity\", \"payment_mode\": \"full_payment\"}], " +
                            "\"customer\": {\"email\": \"%s\"}}",
                    String.format("Order number %d", paymentId), // Assuming you have a description in PaymentRequest
                    1, // Assuming you have a quantity in PaymentRequest
                    paymentRequest.getAmount(),
                    paymentRequest.getCurrency(),
                    customer.getEmail()
            );
            String requestBody = String.format(
                    "{\"amount\": {\"value\": \"%s\", \"currency\": \"%s\"}, \"confirmation\": {\"type\": \"redirect\", \"return_url\": \"%s\"}, \"capture\": true, \"receipt\":%s}",
                    paymentRequest.getAmount(), paymentRequest.getCurrency(), backUrl, receipt);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = createHttpRequest(requestBody);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200){
                throw new RuntimeException("Failed to create payment: " + response.body());
            }
            Payment payment = parseResponse(response.body());
            payment.setId(paymentId);
            payment.setCustomerId(customerId);
            paymentService.save(payment);
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest createHttpRequest(String jsonRequestData){
        if (jsonRequestData == null) return null;
        String idempotenceKey = UUID.randomUUID().toString();
        return HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization","Basic " + Base64.getEncoder().encodeToString((shopId + ":" + apiKey).getBytes()))
                .header("Content-Type", "application/json")
                .header("Idempotence-Key", idempotenceKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestData))
                .build();
    }

    private Payment parseResponse(String body) {
        return PaymentMapper.fromDTO(new Gson().fromJson(body, PaymentDTO.class));
    }

}
