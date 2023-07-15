package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Order;
import tacos.Taco;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/tacos",produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {
    private final TacoRepository tacoRepository;
    private final OrderRepository orderRepository;

    public  TacoController(TacoRepository tacoRepository, OrderRepository orderRepository){
        this.tacoRepository=tacoRepository;
        this.orderRepository=orderRepository;
    }

   @GetMapping(params = "recent")
    public Iterable<Taco> findRecentTacos(){
       PageRequest pageRequest=PageRequest.of(0,12, Sort.by("createdAt").descending());
       Iterable<Taco> tacos = tacoRepository.findAll();
       return tacos;
   }

   @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id){
        Optional<Taco> taco=tacoRepository.findById(id);
        if(taco.isPresent()){
            return new ResponseEntity<>(taco.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
   }

   @PostMapping(consumes = "application/json")
   @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
   }

}
