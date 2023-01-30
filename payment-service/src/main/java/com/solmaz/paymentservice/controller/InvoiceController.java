package com.solmaz.paymentservice.controller;

import com.solmaz.paymentservice.model.Invoice;
import com.solmaz.paymentservice.service.impl.InvoiceServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class InvoiceController {

    private final InvoiceServiceImpl invoiceService;

    public InvoiceController(InvoiceServiceImpl invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<Invoice> payment(@RequestBody Invoice invoice) {
        invoiceService.payment(invoice);
        return ResponseEntity.ok(invoice);
    }
}
