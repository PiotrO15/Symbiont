package piotro15.symbiont.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.registry.ModRecipeSerializers;
import piotro15.symbiont.common.registry.ModRecipeTypes;

public record BoneSamplingRecipe(
        EntityType<?> entityType,
        ItemStack output
) implements Recipe<SingleRecipeInput> {
    // Do not use
    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return false;
    }

    public boolean matches(Entity entity) {
        return entityType == entity.getType();
    }

    @Override
    public ItemStack assemble(SingleRecipeInput singleRecipeInput, HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BONE_SAMPLING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.BONE_SAMPLING.get();
    }

    public static class Serializer implements RecipeSerializer<BoneSamplingRecipe> {
        public static final MapCodec<BoneSamplingRecipe> CODEC =
                RecordCodecBuilder.mapCodec(builder ->
                        builder.group(
                                BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(BoneSamplingRecipe::entityType),
                                ItemStack.CODEC.fieldOf("output").forGetter(BoneSamplingRecipe::output)
                        ).apply(builder, BoneSamplingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, BoneSamplingRecipe> STREAM_CODEC = StreamCodec.of(
                BoneSamplingRecipe.Serializer::toNetwork, BoneSamplingRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<BoneSamplingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, BoneSamplingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BoneSamplingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.byId(buffer.readVarInt());
            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            return new BoneSamplingRecipe(entityType, output);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, BoneSamplingRecipe recipe) {
            buffer.writeVarInt(BuiltInRegistries.ENTITY_TYPE.getId(recipe.entityType));
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}
