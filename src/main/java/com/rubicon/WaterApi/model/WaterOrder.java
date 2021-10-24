package com.rubicon.WaterApi.model;

import com.rubicon.WaterApi.constants.StatusConstants;
import org.hibernate.annotations.GeneratorType;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class WaterOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    @CreatedDate
    private LocalDateTime OrderdateTime;
    private Integer farmId;
    private Integer duration;
    private String Status ;

    public WaterOrder() {
    }

    public WaterOrder(Integer orderId, LocalDateTime orderdateTime, Integer farmId, Integer duration, String status) {
        this.orderId = orderId;
        OrderdateTime = orderdateTime;
        this.farmId = farmId;
        this.duration = duration;
        Status = status;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderdateTime() {
        return OrderdateTime;
    }

    public void setOrderdateTime(LocalDateTime orderdateTime) {
        OrderdateTime = orderdateTime;
    }

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

}
