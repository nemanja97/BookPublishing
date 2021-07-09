package lu.ftn.kpservice.controller;

import com.netflix.discovery.EurekaClient;
import lu.ftn.kpservice.dto.OrderDto;
import lu.ftn.kpservice.dto.OrderIdDto;
import lu.ftn.kpservice.dto.PaypalResponseDto;
import lu.ftn.kpservice.model.CustomOrder;
import lu.ftn.kpservice.service.LogsService;
import lu.ftn.kpservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value="api/kp")
public class KPController {

    @Qualifier("eurekaClient")
    @Autowired
    EurekaClient eurekaClient;
    private final ServletWebServerApplicationContext webServerAppCtxt;
    private final OrderService orderService;

    public KPController(ServletWebServerApplicationContext webServerAppCtxt, OrderService orderService){
        this.webServerAppCtxt = webServerAppCtxt;
        this.orderService = orderService;
    }

    //Takes request from LU accounting service
    @PostMapping(value="order", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RedirectView> receiveOrder(@RequestBody OrderDto dto){
        CustomOrder o = orderService.saveOrderFromClient(dto);
        String port = "https://localhost:4201/order/"+o.getId();
        return new ResponseEntity<>(new RedirectView(port), HttpStatus.OK);
    }
    //Takes request from KP frontend and calls paypal service
    @PostMapping(value="paypal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaypalResponseDto> payWithPaypal(@RequestBody OrderIdDto dto){
        PaypalResponseDto dtoResponse = orderService.sendPaypalOrder(dto);
        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }
    
    @GetMapping(value = "payment-success/{orderId}")
    public void paymentSuccess(@PathVariable("orderId") Long orderId){
        orderService.sendSuccessfulEvent(orderId);
    }
    
    @GetMapping(value = "payment-failed/{orderId}/{errorCode}")
    public void paymentFailed(@PathVariable("orderId") Long orderId, @PathVariable("errorCode") String errorCode){
        orderService.sendFailedEvent(orderId,errorCode);
    }
}
