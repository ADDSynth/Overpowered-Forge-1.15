package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredTechnology;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

/** This allows people to add their own Magic Infuser recipes, so long as they use a valid
  * Enchantment that is registered with another mod. They may also be able to alter the
  * existing Magic Infuser recipes as well.
  * @since Overpowered Technology version 1.5 (September 26, 2022)
  * @author ADDSynth
  */
public final class MagicInfuserRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MagicInfuserRecipe> {

  @Override
  public MagicInfuserRecipe read(ResourceLocation recipeId, JsonObject json){
    
    final String group = JSONUtils.getString(json, "group", "");
    
    final NonNullList<Ingredient> nonnulllist = NonNullList.create();
    nonnulllist.add(Ingredient.fromStacks(new ItemStack(Items.BOOK, 1)));
    final Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
    if(!input.hasNoMatchingItems()){
      nonnulllist.add(input);
    }
    
    final String enchantment_string = JSONUtils.getString(json, "enchantment", null);
    if(enchantment_string == null){
      throw new JsonParseException("Could not read enchantment string correctly for recipe '"+recipeId.toString()+"'.");
    }
    final ResourceLocation enchantment_id = new ResourceLocation(enchantment_string);
    final Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(enchantment_id);
    if(enchantment == null){
      OverpoweredTechnology.log.warn("While parsing recipe "+recipeId.toString()+", Enchantment '"+enchantment_id.toString()+"' does not exist or is not registered.");
      return null;
    }
    return new MagicInfuserRecipe(recipeId, group, enchantment, nonnulllist);
  }

  @Override
  @Nullable
  public MagicInfuserRecipe read(ResourceLocation recipeId, PacketBuffer buffer){
    final String group = buffer.readString(32767);
    final NonNullList<Ingredient> nonnulllist = NonNullList.withSize(2, Ingredient.EMPTY);
    nonnulllist.set(0, Ingredient.read(buffer));
    nonnulllist.set(1, Ingredient.read(buffer));
    final ItemStack result = buffer.readItemStack();
    return new MagicInfuserRecipe(recipeId, group, result, nonnulllist);
  }

  @Override
  public void write(PacketBuffer buffer, MagicInfuserRecipe recipe){
    buffer.writeString(recipe.getGroup());
    final NonNullList<Ingredient> nonnulllist = recipe.getIngredients();
    nonnulllist.get(0).write(buffer);
    nonnulllist.get(1).write(buffer);
    buffer.writeItemStack(recipe.getRecipeOutput(), false);
  }

}
