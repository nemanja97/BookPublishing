package lu.ftn.kpservice.service;

import lu.ftn.kpservice.converter.OrderConverter;
import lu.ftn.kpservice.dto.*;
import lu.ftn.kpservice.model.CustomOrder;
import lu.ftn.kpservice.model.entity.PaypalLog;
import lu.ftn.kpservice.repository.ItemRepository;
import lu.ftn.kpservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final LogsService logsService;

    @LoadBalanced
    @Bean
    public RestTemplate orderRestTemplate2(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("orderRestTemplate2")
    private RestTemplate orderRestTemplate2;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, LogsService logsService) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.logsService = logsService;
    }

    public CustomOrder saveOrderFromClient(OrderDto dto) {
        if (orderRepository.findByOrderUUID(dto.getOrderUUID()).isPresent()) {
            throw new EntityExistsException("Order already exists");
        }
        CustomOrder o = OrderConverter.toOrder(dto);
        o.setCompleted(false);
        o.setItems(o.getItems().stream().map(itemRepository::save).collect(Collectors.toSet()));

        return orderRepository.save(o);
    }

    public CustomOrder findById(Long id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public PaypalResponseDto sendPaypalOrder(OrderIdDto dto) {
       /* System.out.println("Paypal");

        CustomOrder o = findById(dto.getOrderId());

        OrderPaypal orderPaypal = new OrderPaypal(o.getId(), total, currency, description, sellerId, sellerSecret);

        //PaypalLog log = new PaypalLog(null,new Date(),"paypal",dto.getOrderId().toString(),"created","");
        logsService.savePaypalLog(log);
        PaypalResponseDto dtoResponse = new PaypalResponseDto();
        dtoResponse.setUrl(response);
        return dtoResponse;*/
        return null;
    }

    public void sendSuccessfulEvent(Long orderId) {
        CustomOrder order = findById(orderId);
        if (!order.getCompleted()) {
            order.setCompleted(true);
            order.setSuccessful(true);
            orderRepository.save(order);
            OrderResponseDto responseDto = new OrderResponseDto(order.getOrderUUID(), true, null);
            //PaypalLog log = new PaypalLog(null,new Date(),"paypal",orderId.toString(),"Success","");
            //logsService.savePaypalLog(log);
            orderRestTemplate2.postForEntity(order.getSuccessUrl(), responseDto, Void.class);
        }
    }

    public void sendFailedEvent(Long orderId, String errorCode) {
        CustomOrder order = findById(orderId);
        if (!order.getCompleted()) {
            order.setCompleted(true);
            order.setSuccessful(false);
            orderRepository.save(order);
            OrderResponseDto responseDto = new OrderResponseDto(order.getOrderUUID(), false, errorCode);
            //PaypalLog log = new PaypalLog(null,new Date(),"paypal",orderId.toString(),"Failed",errorCode);
            //logsService.savePaypalLog(log);
            orderRestTemplate2.postForEntity(order.getErrorUrl(), responseDto, Void.class);
        }
    }
}
