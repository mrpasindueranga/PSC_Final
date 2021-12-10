package com.psc.psc_management.Models;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "inventorys")
public class Inventorys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "request_id")
    private Integer reqId;
    @Column(name = "date")
    private Date date;
    @Column(name = "quantity")
    private String quantity;
    @Column(name = "rate")
    private float rate;
    @Column(name = "btypr")
    private String btype;
    @Column(name = "b_cname")
    private String bcname;
    @Column(name = "vehicle")
    private String vehicle;
    @Column(name = "total")
    private float total;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReqId() {
        return this.reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public float getRate() {
        return this.rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getBtype() {
        return this.btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getBcname() {
        return this.bcname;
    }

    public void setBcname(String bcname) {
        this.bcname = bcname;
    }

    public String getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public float getTotal() {
        return this.total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
