package com.solmaz.paymentservice.service.impl;

import com.solmaz.paymentservice.model.Invoice;
import com.solmaz.paymentservice.repository.InvoiceRepository;
import com.solmaz.paymentservice.service.InvoiceService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void payment(Invoice invoice) {
        invoiceRepository.save(invoice);
    }
}
