package com.caci.orders.implem;

import com.caci.orders.exception.MyException;
import com.caci.orders.model.Order;

import java.util.*;

public class OrdersManagement {

    // for the purpose of the exercise, store orders in a Map, in normal production context, need a DB to store orders
    Map<UUID, Order> orders = new HashMap<>();

    public UUID createOrder(int numberOfBricks)
    {
        // Simple order creationm, return a unique ID (here using UUID, to be sure, ideally maybe add a check to ensure that random UUID isn't already used)
        UUID id = UUID.randomUUID();
        Order order = new Order();
        order.setOrderReference(id);
        order.setBricksNumber(numberOfBricks);
        orders.put(id, order);
        return id;
    }

    public Order getOrder(UUID orderReference)
    {
        // get order by its reference, or return null
        if(orders.containsKey(orderReference))
        {
            return orders.get(orderReference);
        }
        return null;
    }

    public List<Order> getOrders()
    {
        // get all orders
        return createListOrders(orders.values());
    }

    private List<Order> createListOrders(Collection<Order> orders) {

        // in real prod context, would be a DB query
        List<Order> listOrders = new ArrayList<>();
        if(orders != null)
        {
            for(Order order : orders) {
                listOrders.add(order);
            }
        }else{
            return null;
        }
        return listOrders;
    }

    public UUID updateOrder(UUID orderReference, int numberOfBricks)
    {
        // if orderReference is invalie return null (not explicitely specified in the story)
        Order existingOrder = getOrder(orderReference);
        if(existingOrder == null)
        {
            return null;
        }
        // update an existing order
        // according to the story provided, create a new order which is the update of the previous one
        // I chose to tag the new uorder "updated" and to have a reference to the previous order
        UUID id = UUID.randomUUID();
        Order order = new Order();
        order.setOrderReference(id);
        order.setBricksNumber(numberOfBricks);
        order.setType("update");
        order.setUpdatedOrderReference(orderReference);
        orders.put(id, order);
        return id;
    }

    public void orderRequest(UUID orderReference) throws MyException
    {
        // dispatch an order
        // I chose to generate MyException with code 400 and message Bad Request in the cases ehere 400 answer is expected
        // in prof would be managed my HTTPS responses of this service
        Order order = getOrder(orderReference);
        // check if order exists
        if(order != null)
        {
            // check if order isn't alread dispatched
            if(order.isDispatched())
            {
                // already dispatched => 400
                throw new MyException(400, "bad request");
            }
            // existing and not already dispatched, order become dispatched now
            order.setDispatched(true);
        }
        else
        {
            // invalid order reference, order not existing +> 400
            throw new MyException(400, "bad request");
        }
    }
}
