package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);
    
    private final TransactionService transactionService;
    
    public TransactionListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "${general.kafka-topic}")
    public void handleTransaction(@Payload Transaction transaction,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                @Header(KafkaHeaders.OFFSET) long offset) {
        logger.info("Received transaction: {} from topic: {}, partition: {}, offset: {}", 
                   transaction, topic, partition, offset);
        
        // Process the transaction through the service
        boolean processed = transactionService.processTransaction(transaction);
        
        if (processed) {
            logger.info("Transaction successfully processed and recorded");
        } else {
            logger.info("Transaction was discarded due to validation failure");
        }
    }
}