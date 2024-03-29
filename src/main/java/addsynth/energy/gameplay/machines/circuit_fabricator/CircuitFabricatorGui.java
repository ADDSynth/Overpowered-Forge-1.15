package addsynth.energy.gameplay.machines.circuit_fabricator;

import addsynth.core.gui.util.GuiSection;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.item.IngredientWidgetGroup;
import addsynth.core.gui.widgets.scrollbar.ItemListEntry;
import addsynth.core.gui.widgets.scrollbar.ItemListScrollbar;
import addsynth.core.util.StringUtil;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public final class CircuitFabricatorGui extends GuiEnergyBase<TileCircuitFabricator, CircuitFabricatorContainer> {

  private final String selected_text = StringUtil.translate("gui.addsynth_energy.common.selected");
  private String selected_item;

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(239, 125, 58, 5, 8, 245);

  private static final int down_arrow_texture_x = 228;
  private static final int down_arrow_texture_y = 201;
  private static final int up_arrow_texture_x = 228;
  private static final int up_arrow_texture_y = 217;
  private static final int[] arrow_draw_x = {163, 181, 199, 217};
  private static final int[] arrow_draw_y = {65, 113};
  private static final int[] ingredient_draw_x = {162, 180, 198, 216, 162, 180, 198, 216};
  private static final int[] ingredient_draw_y = {48, 48, 48, 48, 122, 122, 122, 122};
  
  private static final IngredientWidgetGroup recipe_ingredients = new IngredientWidgetGroup(8);
  
  private static final GuiSection item_list_section = GuiSection.box(6, 50, 157, 140);
  private static final int[] list_entry_y = {50, 68, 86, 104, 122};
  private final ItemListEntry[] item_list_entries = new ItemListEntry[5];
  private ItemListScrollbar item_scrollbar;
  
  public CircuitFabricatorGui(final CircuitFabricatorContainer container, PlayerInventory player_inventory, ITextComponent title){
    super(317, 239, container, player_inventory, title, GuiReference.circuit_fabricator);
  }

  @Override
  protected final void init(){
    super.init();
    // construct item list entries
    int i;
    for(i = 0; i < 5; i++){
      item_list_entries[i] = new ItemListEntry(
        guiUtil.guiLeft + 6,
        guiUtil.guiTop + list_entry_y[i],
        item_list_section.width - 12, 18
      );
      addButton(item_list_entries[i]);
    }
    // construct scrollbar
    item_scrollbar = new ItemListScrollbar(
      guiUtil.guiLeft + 6 + item_list_section.width - 12,
      guiUtil.guiTop + item_list_section.y,
      item_list_section.height,
      item_list_entries,
      CircuitFabricatorRecipes.getRecipes()
    );
    item_scrollbar.setResponder(this::onItemSelected);
    addButton(item_scrollbar);
    
    // setup data
    tile.updateGui(); // update displayed recipe, in case player opens another Circuit Fabricator
    final ItemStack output = tile.getRecipeOutput();
    selected_item = StringUtil.translate(output.getTranslationKey());
    item_scrollbar.setSelected(output, false);
  }

  private final void onItemSelected(final ItemStack item, final int index){
    if(item != null){
      @SuppressWarnings("null")
      final String item_name = item.getItem().getRegistryName().toString();
      NetworkHandler.INSTANCE.sendToServer(new ChangeCircuitFabricatorRecipe(tile.getPos(), item_name));
      selected_item = StringUtil.translate(item.getTranslationKey());
    }
  }

  /** Called when the player changes the selected recipe on the server side.
      Only updates the IngredientWidgets drawn ItemStacks.  */
  public static final void updateRecipeDisplay(final ItemStack[][] recipe){
    recipe_ingredients.setRecipe(recipe);
  }

  @Override
  public final void tick(){
    recipe_ingredients.tick();
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_custom_background_texture(384, 256);
    work_progress_bar.draw(this, tile);
    // draw arrows and ingredients
    final int length = recipe_ingredients.getLength();
    if(length >= 1){
      guiUtil.draw(arrow_draw_x[0], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(0, guiLeft + ingredient_draw_x[0], guiTop + ingredient_draw_y[0]);
    }
    if(length >= 2){
      guiUtil.draw(arrow_draw_x[1], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(1, guiLeft + ingredient_draw_x[1], guiTop + ingredient_draw_y[1]);
    }
    if(length >= 3){
      guiUtil.draw(arrow_draw_x[2], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(2, guiLeft + ingredient_draw_x[2], guiTop + ingredient_draw_y[2]);
    }
    if(length >= 4){
      guiUtil.draw(arrow_draw_x[3], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(3, guiLeft + ingredient_draw_x[3], guiTop + ingredient_draw_y[3]);
    }
    if(length >= 5){
      guiUtil.draw(arrow_draw_x[0], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(4, guiLeft + ingredient_draw_x[4], guiTop + ingredient_draw_y[4]);
    }
    if(length >= 6){
      guiUtil.draw(arrow_draw_x[1], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(5, guiLeft + ingredient_draw_x[5], guiTop + ingredient_draw_y[5]);
    }
    if(length >= 7){
      guiUtil.draw(arrow_draw_x[2], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(6, guiLeft + ingredient_draw_x[6], guiTop + ingredient_draw_y[6]);
    }
    if(length >= 8){
      guiUtil.draw(arrow_draw_x[3], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(7, guiLeft + ingredient_draw_x[7], guiTop + ingredient_draw_y[7]);
    }
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    draw_status(tile.getStatus());
    GuiUtil.draw_text_left(selected_text+": "+selected_item, 6, 39);
    // GuiUtil.drawItemStack(circuit_stack[tile.getCircuitID()], 102, 29);
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), 270, 113);
    draw_time_left_center(145);
  }

  @Override
  protected final void renderHoveredToolTip(int mouseX, int mouseY){
    super.renderHoveredToolTip(mouseX, mouseY);
    // draw ingredient tooltips
    recipe_ingredients.drawTooltips(this, guiLeft, ingredient_draw_x, guiTop, ingredient_draw_y, mouseX, mouseY);
  }

}
