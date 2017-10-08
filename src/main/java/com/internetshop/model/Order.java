package com.internetshop.model;

import java.util.Date;

public class Order {
    private int id;
    private Date date;
    private int payStatus;
    private String comment;
    private Status status;
    private PaymentType Paymenttype;
    private DeliveryMethod deliveryMethod;

}
