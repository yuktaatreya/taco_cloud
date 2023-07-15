package tacos.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@RequestMapping("/orders")
@SessionAttributes("order")
@Controller
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order){
        if(order.getName()==null){
            order.setName(user.getName());
        }
        if(order.getStreet()==null){
            order.setStreet(user.getStreet());
        }
        if(order.getCity()==null){
            order.setCity(user.getCity());
        }
        if(order.getState()==null){
            order.setState(user.getState());
        }
        if(order.getZip()==null){
            order.setZip(user.getZip());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "orderForm";
        }
        orderRepository.save(order);
        order.setUser(user);
        sessionStatus.setComplete();
        log.info("order submitted :"+ order);
        return  "redirect:/";
    }
}
