package addsynth.overpoweredmod.compatability.jei;

import addsynth.core.util.StringUtil;
import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class GemConverterCategory implements IRecipeCategory<GemConverterRecipe> {

  public static final ResourceLocation id = Names.GEM_CONVERTER;
  private final IDrawable background;
  private final IDrawable icon;

  public GemConverterCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(GuiReference.jei_recipe_background, 18, 16, 74, 18);
    icon = gui_helper.createDrawableIngredient(new ItemStack(OverpoweredBlocks.gem_converter));
  }

  @Override
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  public Class<? extends GemConverterRecipe> getRecipeClass(){
    return GemConverterRecipe.class;
  }

  @Override
  public String getTitle(){
    return StringUtil.translate(OverpoweredBlocks.gem_converter.getTranslationKey());
  }

  @Override
  public IDrawable getBackground(){
    return background;
  }

  @Override
  public IDrawable getIcon(){
    return icon;
  }

  @Override
  public void setIngredients(GemConverterRecipe recipe, IIngredients ingredients){
    ingredients.setInputIngredients(recipe.ingredients);
    ingredients.setOutput(VanillaTypes.ITEM, recipe.result);
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, GemConverterRecipe recipe, IIngredients ingredients){
    final IGuiItemStackGroup gui_item_stacks = recipeLayout.getItemStacks();
    gui_item_stacks.init(0, true,   0, 0);
    gui_item_stacks.init(1, false, 56, 0);
    gui_item_stacks.set(ingredients);
  }

}
