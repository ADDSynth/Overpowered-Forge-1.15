package addsynth.core.recipe.shapeless;

import javax.annotation.Nullable;
import addsynth.core.util.java.JavaUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

/** REPLICA: Code here is copied from {@link net.minecraft.item.crafting.ShapelessRecipe.Serializer ShapelessRecipe.Serializer} */
public class ShapelessRecipeSerializer<T extends AbstractRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

  private final int max_size;
  private final Class<T> recipe_type;
  
  public ShapelessRecipeSerializer(final Class<T> recipe_type, final int max_size){
    this.recipe_type = recipe_type;
    this.max_size = max_size;
  }

  private static final NonNullList<Ingredient> readIngredients(JsonArray json_array){
    NonNullList<Ingredient> nonnulllist = NonNullList.create();
    for(int i = 0; i < json_array.size(); ++i) {
      Ingredient ingredient = Ingredient.deserialize(json_array.get(i));
      if(!ingredient.hasNoMatchingItems()){
        nonnulllist.add(ingredient);
      }
    }
    return nonnulllist;
  }

  // Start Here
  @Override
  public T read(ResourceLocation recipeId, JsonObject json){
    final String group = JSONUtils.getString(json, "group", "");
    NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
    if(nonnulllist.isEmpty()){
      throw new JsonParseException("No ingredients for "+recipe_type.getSimpleName()+" recipe");
    }
    if(nonnulllist.size() > max_size){
      throw new JsonParseException("Too many ingredients for "+recipe_type.getSimpleName()+" recipe. There can only be a max of "+max_size+" ingredients.");
    }
    ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
    return JavaUtils.InvokeConstructor(recipe_type, recipeId, group, itemstack, nonnulllist);
  }

  @Override
  @Nullable
  public T read(ResourceLocation recipeId, PacketBuffer buffer){
    final String group = buffer.readString(32767); // bypasses the ClientOnly annotation, it's what vanilla does as well.
    final int i = buffer.readVarInt();
    NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

    for(int j = 0; j < nonnulllist.size(); ++j) {
       nonnulllist.set(j, Ingredient.read(buffer));
    }

    ItemStack itemstack = buffer.readItemStack();
    return JavaUtils.InvokeConstructor(recipe_type, recipeId, group, itemstack, nonnulllist);
  }

  @Override
  public void write(PacketBuffer buffer, T recipe){
    buffer.writeString(recipe.getGroup());
    buffer.writeVarInt(recipe.getIngredients().size());

    for(Ingredient ingredient : recipe.getIngredients()){
      ingredient.write(buffer);
    }

    buffer.writeItemStack(recipe.getRecipeOutput());
  }

}
