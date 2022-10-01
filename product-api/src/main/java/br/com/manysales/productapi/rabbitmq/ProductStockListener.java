package br.com.manysales.productapi.rabbitmq;

import br.com.manysales.productapi.entities.DTO.ProductQuantityDTO;
import br.com.manysales.productapi.entities.DTO.ProductStockDTO;
import br.com.manysales.productapi.services.ProductServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductStockListener {

    @Autowired
    private ProductServices productServices;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO productStockDTO) throws JsonProcessingException {
        log.info("Message received: {}", new ObjectMapper().writeValueAsString(productStockDTO));
        productServices.updateProductStock(productStockDTO);
    }
}
