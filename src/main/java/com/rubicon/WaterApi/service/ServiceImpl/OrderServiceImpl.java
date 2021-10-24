package com.rubicon.WaterApi.service.ServiceImpl;

import com.rubicon.WaterApi.Exception.*;
import com.rubicon.WaterApi.constants.ExceptionConstants;
import com.rubicon.WaterApi.constants.StatusConstants;
import com.rubicon.WaterApi.model.WaterOrder;
import com.rubicon.WaterApi.repository.WaterOrderRepository;
import com.rubicon.WaterApi.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.font.DelegatingShape;

import javax.print.attribute.standard.OrientationRequested;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
@Qualifier("OrderService")
public class OrderServiceImpl implements OrderService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private WaterOrderRepository waterOrderRepository;

    @Autowired
    public OrderServiceImpl(WaterOrderRepository waterOrderRepository) {
        this.waterOrderRepository = waterOrderRepository;
    }

    @Override
    public WaterOrder GetStatus(Integer orderId) throws OrderNotFoundException {

        WaterOrder waterOrder = waterOrderRepository.findOrderByorderId(orderId);
        if (waterOrder != null) {
            if (waterOrder.getStatus().equalsIgnoreCase(StatusConstants.Cancelled)) {
                return waterOrder;
            } else if (waterOrder.getStatus().equalsIgnoreCase(StatusConstants.REQUESTED)) {
                updateDuration(waterOrder);
                if (waterOrder.getDuration()==0) {
                    waterOrder.setStatus(StatusConstants.Delivered);
                    LOGGER.info("water order for farm " + waterOrder.getFarmId() + " was started on " + waterOrder.getOrderdateTime() + "  has been delivered on " + waterOrder.getDuration());
                    return waterOrder;
                } else {
                    waterOrder.setStatus(StatusConstants.InProgress);
                    LOGGER.info("water order for farm " + waterOrder.getFarmId() + " is in progress");
                    return waterOrder;
                }
            }
        }


        throw new OrderNotFoundException("The requested Water Order cannot be Found");


    }


    @Override
    public WaterOrder cancelOrder(Integer orderId) throws OrderNotFoundException {
        WaterOrder waterOrder = waterOrderRepository.findOrderByorderId(orderId);
        if (waterOrder == null) {
            throw new OrderNotFoundException(ExceptionConstants.ORDER_DONT_EXIST);
        }
        waterOrder.setStatus(StatusConstants.Cancelled);
        LOGGER.info("water order for farm " + waterOrder.getFarmId() + " has been canceled");
        return waterOrder;
    }


    @Override
    public WaterOrder addNewOrder(WaterOrder waterOrder) throws OrderAlreadyPlacedException {
        List<WaterOrder> ExistingWaterOrders = waterOrderRepository.findAllOrderByfarmId(waterOrder.getFarmId());
        if (ExistingWaterOrders.size() > 0) {
            List<WaterOrder> pendingOrders = new ArrayList<>();

            for (WaterOrder o : ExistingWaterOrders) {
                if (o.getStatus().equalsIgnoreCase(StatusConstants.InProgress) || o.getStatus().equalsIgnoreCase(StatusConstants.REQUESTED)) {
                    pendingOrders.add(o);
                }
            }

            if (pendingOrders.size()>0) {
                for (WaterOrder pendingOrder : pendingOrders) {
                    LocalDateTime OrderDate = pendingOrder.getOrderdateTime();
                    LocalDateTime nowDateAndTime = LocalDateTime.now();
                    if (nowDateAndTime.until(OrderDate, ChronoUnit.HOURS) > 3) {
                        waterOrder.setStatus(StatusConstants.REQUESTED);
                        updateDuration(waterOrder);
                        LOGGER.info("New water order for farm " + waterOrder.getFarmId() + " created");
                        return waterOrderRepository.save(waterOrder);
                    } else {
                        throw new OrderAlreadyPlacedException(ExceptionConstants.ORDER_ALREADY_PALCED);
                    }
                }
            } else {
                waterOrder.setStatus(StatusConstants.REQUESTED);
                LOGGER.info("New water order for farm " + waterOrder.getFarmId() + " created");
                return waterOrderRepository.save(waterOrder);
            }
        } else {
            waterOrder.setStatus(StatusConstants.REQUESTED);
            LOGGER.info("New  water order for farm " + waterOrder.getFarmId() + " created");
            return waterOrderRepository.save(waterOrder);
        }
        return null;
    }

    @Override
    public List<WaterOrder> getAllOrders() {
        return waterOrderRepository.findAll();
    }

    private void updateDuration(WaterOrder waterOrder) {
        LocalDateTime ordertime = waterOrder.getOrderdateTime();
        LocalDateTime currenttime = LocalDateTime.now();
        int calcDuration = (int) ordertime.until(currenttime, ChronoUnit.HOURS);
        if (calcDuration >= 0) {
            waterOrder.setDuration(calcDuration);
        }
    }


}
