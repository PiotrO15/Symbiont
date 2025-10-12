package piotro15.symbiont.common.item;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.registries.ModDataComponents;
import piotro15.symbiont.common.registries.ModRegistries;

public class CellCultureItem extends Item {
    public CellCultureItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        ResourceLocation cellId = stack.get(ModDataComponents.CELL_TYPE.get());
        if (cellId != null) {
            return Component.translatable(Util.makeDescriptionId("cell_type", cellId));
        }
        return super.getName(stack);
    }

    public double getStability(ItemStack stack) {
        ResourceLocation cellId = stack.get(ModDataComponents.CELL_TYPE.get());

        if (cellId == null)
            return 1;

        if (Minecraft.getInstance().getConnection() == null)
            return 1;

        Registry<CellType> registry = Minecraft.getInstance().getConnection()
                .registryAccess().registryOrThrow(ModRegistries.CELL_TYPE);

        CellType cellType = registry.get(cellId);
        if (cellType == null)
            return 1;

        cellType.biocode().traits();
        return 1;
    }
}
