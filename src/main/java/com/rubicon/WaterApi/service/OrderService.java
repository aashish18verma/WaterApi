package com.rubicon.WaterApi.service;


import com.rubicon.WaterApi.Exception.OrderAlreadyPlacedException;
import com.rubicon.WaterApi.Exception.OrderNotFoundException;
import com.rubicon.WaterApi.model.WaterOrder;

import java.util.List;

public interface OrderService {

     public WaterOrder GetStatus(Integer orderId) throws OrderNotFoundException;
     public  WaterOrder cancelOrder(Integer orderId) throws OrderNotFoundException;
     public WaterOrder addNewOrder(WaterOrder waterOrder) throws OrderAlreadyPlacedException;

     List getAllOrders();
}
