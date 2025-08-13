package in.Kanika.foodorder.service;

import com.razorpay.RazorpayException;
import in.Kanika.foodorder.io.OrderRequest;
import in.Kanika.foodorder.io.OrderResponse;

import java.util.List;
import java.util.Map;

public interface OrderService {
   OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException;
   void verifyPayments(Map<String,String> paymentData, String status);

   List<OrderResponse> getUserOrders();

   void removeOrder( String orderId);

   List<OrderResponse> getOrdersOfAllUsers();

   void updateOrderStatus(String orderId ,String status);
}
