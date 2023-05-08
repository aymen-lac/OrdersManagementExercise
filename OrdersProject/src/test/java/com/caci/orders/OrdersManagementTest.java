package com.caci.orders;

import com.caci.orders.exception.MyException;
import com.caci.orders.implem.OrdersManagement;
import com.caci.orders.model.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class OrdersManagementTest {

    private OrdersManagement ordersManagement = new OrdersManagement();

    @Test
    public void myTest()
    {

        // creating 2 orders, test than references are returned and are different (stage 1)
        UUID orderRef1 = ordersManagement.createOrder(4);
        UUID orderRef2 = ordersManagement.createOrder(4);
        Assert.assertNotNull(orderRef1);
        Assert.assertNotNull(orderRef2);
        Assert.assertNotEquals(orderRef1, orderRef2);

        // getting order previously created (stage 1)
        Order order1 = ordersManagement.getOrder(orderRef1);
        Assert.assertNotNull(order1);
        Assert.assertEquals(order1.getOrderReference(), orderRef1);
        Assert.assertEquals(order1.getBricksNumber(), 4);

        // getting order with invalid reference (stage 1)
        Order orderInvalid = ordersManagement.getOrder(UUID.randomUUID());
        Assert.assertNull(orderInvalid);

        // get all orders
        List<Order> allOrders = ordersManagement.getOrders();
        Assert.assertEquals(allOrders.size(), 2);

        // update existing order
        UUID orderRef3 = ordersManagement.updateOrder(orderRef1, 6);
        Assert.assertNotNull(orderRef3);
        Assert.assertNotEquals(orderRef3, orderRef1);
        Assert.assertNotEquals(orderRef3, orderRef2);

        // update mon existing order
        UUID orderInvalidRef = ordersManagement.updateOrder(UUID.randomUUID(), 6);
        Assert.assertNull(orderInvalidRef);

        // dispatch order (stage 3)
        // check order ism't dispatched before
        Order order2Before = ordersManagement.getOrder(orderRef2);
        Assert.assertFalse(order2Before.isDispatched());

        boolean isException = false;
        try{
            ordersManagement.orderRequest(orderRef2);
        }
        catch(MyException e)
        {
           isException = true;
        }
        Assert.assertFalse(isException);
        // check order is dispatched
        Order order2After = ordersManagement.getOrder(orderRef2);
        Assert.assertTrue(order2After.isDispatched());

        // dispatch non existing order (here just checking that code throw exception)
        isException = false;
        try {
            ordersManagement.orderRequest(UUID.randomUUID());
        }
        catch (MyException e)
        {
            isException = true;
        }
        Assert.assertTrue(isException);

        // dispatch already dispatched order (stage 4)
        // reuse the same order, now dispatched
        isException = false;
        try {
            ordersManagement.orderRequest(orderRef2);
        }
        catch (MyException e)
        {
            isException = true;
        }
        Assert.assertTrue(isException);

    }

}
