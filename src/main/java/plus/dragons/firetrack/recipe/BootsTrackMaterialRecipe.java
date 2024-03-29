package plus.dragons.firetrack.recipe;

import com.google.common.collect.Lists;
import net.minecraft.world.item.crafting.Ingredient;
import plus.dragons.firetrack.capability.TrackTypeData;
import plus.dragons.firetrack.track.TrackType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class BootsTrackMaterialRecipe extends CustomRecipe {
    public BootsTrackMaterialRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    TrackType type;
    Ingredient ingredient;

    public boolean matches(CraftingContainer container, Level level) {
        ItemStack itemstack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack1 = container.getItem(i);
            if (!itemstack1.isEmpty()) {
                if (itemstack1.getItem() instanceof ArmorItem armorItem) {
                    if(armorItem.getSlot() == EquipmentSlot.FEET){
                        if (!itemstack.isEmpty()) {
                            return false;
                        }
                        itemstack = itemstack1;
                    } else {
                        return false;
                    }
                } else {
                    var type = convertToTrackType(itemstack1);
                    if (type.isEmpty()) {
                        return false;
                    }
                    list.add(itemstack1);
                }
            }
        }

        return !itemstack.isEmpty() && !list.isEmpty();
    }

    public ItemStack assemble(CraftingContainer craftingContainer) {
        ItemStack itemstack = ItemStack.EMPTY;
        Optional<TrackType> type = Optional.empty();
        for(int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemstack1 = craftingContainer.getItem(i);
            if (!itemstack1.isEmpty()) {
                if (itemstack1.getItem() instanceof ArmorItem armorItem) {
                    if(armorItem.getSlot() == EquipmentSlot.FEET){
                        if (!itemstack.isEmpty()) {
                            return ItemStack.EMPTY;
                        }
                        itemstack = itemstack1.copy();
                    } else {
                        return ItemStack.EMPTY;
                    }
                } else {
                    type = convertToTrackType(itemstack1);
                    if (type.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }
        var data = itemstack.getCapability(TrackTypeData.CAPABILITY);
        if(data.isPresent()){
            Optional<TrackType> finalType = type;
            data.ifPresent(cap->{
                cap.set(finalType.get());
            });
            return itemstack.copy();
        } else return ItemStack.EMPTY;
    }

    private Optional<TrackType> convertToTrackType(ItemStack itemStack){
        if(itemStack.is(Items.LAVA_BUCKET)){
            return Optional.of(TrackType.LAVA);
        } else if(itemStack.is(Items.MAGMA_CREAM)){
            return Optional.of(TrackType.FIRE);
        } else if(itemStack.is(Items.COAL)){
            return Optional.of(TrackType.SMOKE);
        } else if(itemStack.is(Items.SLIME_BALL)){
            return Optional.of(TrackType.NONE);
        } else return Optional.empty();
    }

    public boolean canCraftInDimensions(int i, int i1) {
        return i * i1 >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.BOOTS_MATERIAL_RECIPE_SERIALIZER.get();
    }
}
