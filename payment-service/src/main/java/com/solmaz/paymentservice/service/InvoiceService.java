package com.solmaz.paymentservice.service;

import com.solmaz.paymentservice.model.Invoice;

public interface InvoiceService {

    void payment(Invoice invoice);
}
