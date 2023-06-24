package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository{
    private JdbcTemplate jdbc;

    private IngredientRepository ingredientRepository;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc,IngredientRepository ingredientRepository){

        this.jdbc=jdbc;
        this.ingredientRepository=ingredientRepository;
    }

    @Override
    public Taco saveDesign(Taco design) {
        long tacoId = saveTacoInfo(design);
        design.setId(tacoId);
        for (String ingredientId:design.getIngredients()) {
            Ingredient ingredient= ingredientRepository.findById(ingredientId);
            saveIngredientToTaco(ingredient,tacoId);
        }
        return design;
    }


    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory("insert into Taco (name,createdAt) values (?,?)",
                Types.VARCHAR,Types.TIMESTAMP);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = preparedStatementCreatorFactory.
                newPreparedStatementCreator(Arrays.asList(taco.getName(),new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc,keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId){
        jdbc.update("insert into Taco_Ingredients (taco,ingredient)  values (?,?)",tacoId,ingredient.getId());
    }
}
