package plus.dragons.firetrack.recipe;

import plus.dragons.firetrack.FireTrack;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistry {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, FireTrack.MOD_ID);

    public static final RegistryObject<RecipeType<BootsTrackMaterialRecipe>> BOOTS_MATERIAL_RECIPE = RECIPE_TYPES.register("track_material", () -> new RecipeType<>() {
        public String toString() {
            return "track_material";
        }
    });

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FireTrack.MOD_ID);
    public static final RegistryObject<RecipeSerializer<BootsTrackMaterialRecipe>> BOOTS_MATERIAL_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("track_material",()-> new SimpleRecipeSerializer<>(BootsTrackMaterialRecipe::new));
}
