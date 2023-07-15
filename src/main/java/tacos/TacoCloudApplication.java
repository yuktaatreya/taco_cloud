package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tacos.data.IngredientRepository;
import tacos.Ingredient.Type;
import tacos.data.TacoRepository;

import java.util.Arrays;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo, TacoRepository tacoRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				Ingredient flourTortilla = new Ingredient(
						"FLTO", "Flour Tortilla", Type.WRAP);
				Ingredient cornTortilla = new Ingredient(
						"COTO", "Corn Tortilla", Type.WRAP);
				Ingredient groundBeef = new Ingredient(
						"GRBF", "Ground Beef", Type.PROTEIN);
				Ingredient carnitas = new Ingredient(
						"CARN", "Carnitas", Type.PROTEIN);
				Ingredient tomatoes = new Ingredient(
						"TMTO", "Diced Tomatoes", Type.VEGGIES);
				Ingredient lettuce = new Ingredient(
						"LETC", "Lettuce", Type.VEGGIES);
				Ingredient cheddar = new Ingredient(
						"CHED", "Cheddar", Type.CHEESE);
				Ingredient jack = new Ingredient(
						"JACK", "Monterrey Jack", Type.CHEESE);
				Ingredient salsa = new Ingredient(
						"SLSA", "Salsa", Type.SAUCE);
				Ingredient sourCream = new Ingredient(
						"SRCR", "Sour Cream", Type.SAUCE);
				repo.save(flourTortilla);
				repo.save(cornTortilla);
				repo.save(groundBeef);
				repo.save(carnitas);
				repo.save(tomatoes);
				repo.save(lettuce);
				repo.save(cheddar);
				repo.save(jack);
				repo.save(salsa);
				repo.save(sourCream);

				Taco taco1=new Taco();
				taco1.setName("Carnivore");
				taco1.setIngredients(Arrays.asList(groundBeef,sourCream,salsa,jack,cornTortilla));
				tacoRepository.save(taco1);

				Taco taco2 = new Taco();
				taco2.setName("Helth");
				taco2.setIngredients(Arrays.asList(tomatoes,lettuce,carnitas,salsa,cornTortilla));
				tacoRepository.save(taco2);

			};
		};

	}
}
