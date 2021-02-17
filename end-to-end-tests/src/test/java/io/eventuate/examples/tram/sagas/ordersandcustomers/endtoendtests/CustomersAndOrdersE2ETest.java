package io.eventuate.examples.tram.sagas.ordersandcustomers.endtoendtests;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.CreateCustomerRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.CreateCustomerResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.OrderState;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.CreateOrderRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.CreateOrderResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.GetOrderResponse;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CustomersAndOrdersE2ETest{

  @RestClient
  CustomerRestClientService customerRestClientService;

  @RestClient
  OrderRestClientService orderRestClientService;

  @Test
  public void shouldApprove() throws Exception {
    CreateCustomerResponse createCustomerResponseResponse =
            customerRestClientService.createCustomer(new CreateCustomerRequest("Fred", new Money("15.00")));

    CreateOrderResponse createOrderResponse =
            orderRestClientService.createOrder(new CreateOrderRequest(createCustomerResponseResponse.getCustomerId(), new Money("12.34")));

    assertOrderState(createOrderResponse.getOrderId(), OrderState.APPROVED);
  }

  @Test
  public void shouldReject() throws Exception {
    CreateCustomerResponse createCustomerResponseResponse =
            customerRestClientService.createCustomer(new CreateCustomerRequest("Fred", new Money("15.00")));

    CreateOrderResponse createOrderResponse =
            orderRestClientService.createOrder(new CreateOrderRequest(createCustomerResponseResponse.getCustomerId(), new Money("123.40")));

    assertOrderState(createOrderResponse.getOrderId(), OrderState.REJECTED);
  }

  private void assertOrderState(Long id, OrderState expectedState) throws InterruptedException {
    GetOrderResponse order = null;
    for (int i = 0; i < 30; i++) {
      order = orderRestClientService.getOrder(id);
      if (order.getOrderState() == expectedState)
        break;
      TimeUnit.MILLISECONDS.sleep(400);
    }

    assertEquals(expectedState, order.getOrderState());
  }
}
