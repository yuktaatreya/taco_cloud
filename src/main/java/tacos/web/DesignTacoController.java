package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.Taco;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/design" )
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    private TacoRepository tacoRepository;

    @Autowired
    public  DesignTacoController(IngredientRepository ingredientRepository,TacoRepository tacoRepository){
        this.ingredientRepository=ingredientRepository;
        this.tacoRepository=tacoRepository;
    }

    @ModelAttribute(name="design")
    public Taco taco(){
        return new Taco();
    }

    @ModelAttribute(name="order")
    public Order order(){
        return new Order();
    }
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredientRepository.findAll().forEach(
                ingredient -> ingredients.add(ingredient)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order,Model model) {

        if (errors.hasErrors()) {
            List<Ingredient> ingredients = new ArrayList();
            ingredientRepository.findAll().forEach(
                    ingredient -> ingredients.add(ingredient)
            );
            Type[] types = Ingredient.Type.values();
            for (Type type : types) {
                model.addAttribute(type.toString().toLowerCase(),
                        filterByType(ingredients, type));
            }
            return "design";
        }

        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);
        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
