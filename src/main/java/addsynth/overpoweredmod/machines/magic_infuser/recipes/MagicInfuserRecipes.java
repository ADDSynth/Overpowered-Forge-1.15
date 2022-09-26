package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import java.util.ArrayList;
import java.util.Random;
import addsynth.core.Debug;
import addsynth.core.util.StringUtil;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public final class MagicInfuserRecipes {

  public static final class MagicInfuserRecipeType implements IRecipeType<MagicInfuserRecipe> {
  }

  public static final MagicInfuserRecipeType recipe_type = new MagicInfuserRecipeType();
  public static final MagicInfuserRecipeSerializer serializer = new MagicInfuserRecipeSerializer();

  public static final ArrayList<MagicInfuserRecipe> recipes = new ArrayList<>();

  public static final void addRecipe(final MagicInfuserRecipe recipe){
    if(recipes.contains(recipe) == false){
      recipes.add(recipe);
      Debug.log_recipe(recipe);
    }
  }

  public static final Item[] getFilter(){
    return MaterialsUtil.getFilter(
      MaterialsUtil.getRubies(), MaterialsUtil.getTopaz(), MaterialsUtil.getCitrine(), MaterialsUtil.getEmeralds(),
      MaterialsUtil.getDiamonds(), MaterialsUtil.getSapphires(), MaterialsUtil.getAmethysts(), MaterialsUtil.getQuartz()
    );
  }

  public static final ItemStack getResult(final ItemStack input){
    final ArrayList<ItemStack> output = new ArrayList<>(recipes.size());
    NonNullList<Ingredient> ingredients;
    for(MagicInfuserRecipe recipe : recipes){
      ingredients = recipe.getIngredients();
      if(ingredients.get(1).test(input)){
        output.add(recipe.getRecipeOutput());
      }
    }
    if(output.size() == 0){
      OverpoweredTechnology.log.error("Invalid Magic Infuser recipe for input: "+StringUtil.getName(input.getItem())+"!");
      return ItemStack.EMPTY;
    }
    final Random random = new Random();
    return output.get(random.nextInt(output.size()));
  }

}