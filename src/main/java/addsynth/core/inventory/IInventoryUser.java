package addsynth.core.inventory;

/** It is not recommended to use this, except to check for ANY kind of inventory.
 *  Only implement this in TileEntities if you wish to keep the inventory private.
 */
public interface IInventoryUser {

  /** Anything that has an inventory MUST save whenever that inventory changed. */
  public void onInventoryChanged();

  /** Blocks that have an inventory MUST drop the inventory contents
   *  when the block is destroyed. */
  public void drop_inventory();

}
