package tacos.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Order;
import tacos.data.OrderRepository;

import java.util.Optional;

@RequestMapping(path = "/order",consumes = "application/json")
@RestController
@CrossOrigin(origins = "http://tacocloud:8080")
public class OrderController {
    private final OrderRepository orderRepository;

    OrderController(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @GetMapping(produces = "application/json")
    public Iterable<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order){
        return orderRepository.save(order);
    }

    @PutMapping(path = "/{orderId}",consumes = "application/json")
    public Order replaceOrder(@PathVariable("orderId") Long id, @RequestBody Order order){
        order.setId(id);
        return orderRepository.save(order);
    }

    @PatchMapping(path = "/{orderId}",consumes = "application/json")
    public Order updateOrder(@PathVariable("orderId") Long id, @RequestBody Order newOrder){
        Order oldOrder = orderRepository.findById(id).get();
        if(newOrder.getZip()!=null){
            oldOrder.setZip(newOrder.getZip());
        }
        if(newOrder.getState()!=null){
            oldOrder.setState(newOrder.getState());
        }
        if(newOrder.getCity()!=null){
            oldOrder.setCity(newOrder.getCity());
        }
        if(newOrder.getStreet()!=null){
            oldOrder.setStreet(newOrder.getStreet());
        }
        if(newOrder.getName()!=null){
            oldOrder.setName(newOrder.getName());
        }
        if(newOrder.getCcNumber()!=null){
            oldOrder.setCcNumber(newOrder.getCcNumber());
        }
        if(newOrder.getCcExpiration()!=null){
            oldOrder.setCcExpiration(newOrder.getCcExpiration());
        }
        if(newOrder.getCcCVV()!=null){
            oldOrder.setCcCVV(newOrder.getCcCVV());
        }
        return orderRepository.save(oldOrder);
    }

    @DeleteMapping(path = "/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable("orderId") Long id){
        try{
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException e){
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
    }
}
