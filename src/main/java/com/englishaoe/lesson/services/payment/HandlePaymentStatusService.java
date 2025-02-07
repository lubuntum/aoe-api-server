package com.englishaoe.lesson.services.payment;

import com.englishaoe.lesson.database.entity.transactions.Payment;
import com.englishaoe.lesson.database.entity.transactions.PaymentStatusEnum;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.PaymentService;
import com.englishaoe.lesson.dto.payment.PaymentStatusResponse;
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
import java.util.List;

@Service
public class HandlePaymentStatusService {
    @Value("${YOOKASSA_SHOP_ID}")
    private String shopId;
    @Value("${YOOKASSA_API_KEY}")
    private String apiKey;
    @Value("${YOOKASSA_URL}")
    private String apiUrl;
    /*TODO make API call to payment status depent on payment_id (find by customer id )*/
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CustomerServices customerServices;
    public void checkPaymentsStatusForCustomer(String status, Long customerId){
        List<Payment> payments = paymentService.getPaymentsByStatusAndCustomerId(status, customerId);
        for(Payment payment: payments){
            PaymentStatusResponse response = getPaymentStatus(payment.getPaymentId());
            if (response != null &&
                    response.getStatus().equals(PaymentStatusEnum.SUCCEEDED.getStatus()) &&
                    !payment.getStatus().equals(PaymentStatusEnum.SUCCEEDED.getStatus())){
                payment.setStatus(PaymentStatusEnum.SUCCEEDED.getStatus());
                paymentService.save(payment);
                customerServices.addBalanceToCustomerById(customerId, payment.getValue());
            }
            if (response != null && !response.getStatus().equals(payment.getStatus())){
                payment.setStatus(response.getStatus());
                paymentService.save(payment);
            }
        }
    }

    private PaymentStatusResponse getPaymentStatus(String paymentId) {
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = createHttpRequest(paymentId);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200)
                throw new RuntimeException("Failed to check payment status: " + response.body());
            return new Gson().fromJson(response.body(), PaymentStatusResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while checking payment status", e);
        }

    }
    private HttpRequest createHttpRequest(String paymentId){
        return HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/" + paymentId))
                .header("Authorization","Basic " + Base64.getEncoder().encodeToString((shopId + ":" + apiKey).getBytes()))
                .header("Content-Type", "application/json")
                .GET()
                .build();
    }
}
