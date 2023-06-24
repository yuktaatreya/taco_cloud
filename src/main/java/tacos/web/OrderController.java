package tacos.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.data.JdbcOrderRepository;

@Slf4j
@RequestMapping("/orders")
@SessionAttributes("order")
@Controller
public class OrderController {

    private JdbcOrderRepository jdbcOrderRepository;

    @Autowired
    public OrderController(JdbcOrderRepository jdbcOrderRepository){
        this.jdbcOrderRepository=jdbcOrderRepository;
    }

    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus){
        if(errors.hasErrors()){
            return "orderForm";
        }
        jdbcOrderRepository.saveOrder(order);
        sessionStatus.setComplete();
        log.info("order submitted :"+ order);
        return  "redirect:/";
    }
}
