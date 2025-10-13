package piotro15.symbiont.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.registries.ModDataComponents;

import javax.annotation.Nullable;

public class CellCultureModelWrapper extends BakedModelWrapper<BakedModel> {
    public CellCultureModelWrapper(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return new ItemOverrides() {
            @Override
            public BakedModel resolve(@NotNull BakedModel model, @NotNull ItemStack stack, @Nullable ClientLevel arg3, @Nullable LivingEntity arg4, int k) {
                ResourceLocation cellType = stack.has(ModDataComponents.CELL_TYPE)
                        ? stack.get(ModDataComponents.CELL_TYPE)
                        : null;

                if (cellType != null) {
                    ResourceLocation overrideModelLocation = ResourceLocation.fromNamespaceAndPath(cellType.getNamespace(), "cell_type/" + cellType.getPath());
                    BakedModel overrideModel = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(overrideModelLocation));

                    if (!overrideModel.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
                        return overrideModel;
                    }
                }

                return originalModel;
            }
        };
    }
}
