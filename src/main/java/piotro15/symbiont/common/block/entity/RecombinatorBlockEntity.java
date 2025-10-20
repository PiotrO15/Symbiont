package piotro15.symbiont.common.block.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.api.DynamicItemStackHandler;
import piotro15.symbiont.common.genetics.Biocode;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.item.BiotraitExtractItem;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.menu.RecombinatorMenu;
import piotro15.symbiont.common.registry.ModBlockEntities;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModItems;
import piotro15.symbiont.common.registry.ModRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecombinatorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler items;

    public RecombinatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RECOMBINATOR.get(), pos, state);
        items = new DynamicItemStackHandler(6, this) {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (slot == 0 && stack.is(ModItems.CELL_CULTURE)) {
                    return true;
                }

                if (slot > 0 && slot < 6 && stack.getItem() instanceof BiotraitExtractItem) {
                    ResourceLocation traitId = stack.get(ModDataComponents.BIOTRAIT);

                    ClientPacketListener connection = Minecraft.getInstance().getConnection();
                    if (connection == null)
                        return false;

                    Registry<Biotrait> registry = connection.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
                    Biotrait biotrait = registry.get(traitId);

                    if (biotrait == null) {
                        return false;
                    }
                    switch (slot) {
                        case 1 -> {
                            return biotrait.type() == Biotrait.BiotraitType.STABILITY;
                        }
                        case 2 -> {
                            return biotrait.type() == Biotrait.BiotraitType.METABOLISM;
                        }
                        case 3 -> {
                            return biotrait.type() == Biotrait.BiotraitType.REPLICATION;
                        }
                        case 4 -> {
                            return biotrait.type() == Biotrait.BiotraitType.ADAPTABILITY;
                        }
                        case 5 -> {
                            return biotrait.type() == Biotrait.BiotraitType.SPECIAL;
                        }
                        default -> {
                            return false;
                        }
                    }
                }
                return false;
            }

            @Override
            public int getSlotLimit(int slot) {
                return slot == 0 ? 1 : super.getSlotLimit(slot);
            }
        };
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(tag, lookup);

        items.deserializeNBT(lookup, tag.getCompound("Inventory"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
        super.saveAdditional(tag, lookup);

        tag.put("Inventory", items.serializeNBT(lookup));
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        return new RecombinatorMenu(id, playerInv, this.getBlockPos());
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.recombinator");
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public List<CellCultureItem.AppliedBiotrait> getResultingBiocode() {
        ItemStack cellStack = items.getStackInSlot(0);
        if (cellStack.isEmpty() || !cellStack.is(ModItems.CELL_CULTURE)) {
            return List.of();
        }

        Biocode baseBiocode = cellStack.get(ModDataComponents.BIOCODE);

        Map<Biotrait.BiotraitType, ResourceLocation> traits;
        if (baseBiocode == null) {
            traits = new HashMap<>();
        } else {
            traits = new HashMap<>(baseBiocode.traits());
        }

        for (int slot = 1; slot <= 5; slot++) {
            ItemStack vialStack = items.getStackInSlot(slot);
            if (vialStack.isEmpty() || !(vialStack.getItem() instanceof BiotraitExtractItem)) {
                continue;
            }

            ResourceLocation traitId = vialStack.get(ModDataComponents.BIOTRAIT);
            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection == null)
                continue;

            Registry<Biotrait> registry = connection.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
            Biotrait biotrait = registry.get(traitId);

            if (biotrait != null) {
                traits.put(biotrait.type(), traitId);
            }
        }

        List<CellCultureItem.AppliedBiotrait> result = new ArrayList<>();
        for (Map.Entry<Biotrait.BiotraitType, ResourceLocation> entry : traits.entrySet()) {
            Biotrait.BiotraitType type = entry.getKey();
            ResourceLocation id = entry.getValue();

            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection == null)
                return List.of();

            Registry<Biotrait> registry = connection.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
            Biotrait resolved = registry.get(id);

            if (resolved != null) {
                result.add(new CellCultureItem.AppliedBiotrait(type, id, resolved));
            }
        }

        return result;
    }

    public void setTriggered() {
        if (!items.getStackInSlot(0).is(ModItems.CELL_CULTURE)) {
            return;
        }

        ItemStack cellStack = items.getStackInSlot(0);
        Biocode biocodeComponent = cellStack.get(ModDataComponents.BIOCODE);

        HashMap<Biotrait.BiotraitType, ResourceLocation> traits;
        if (biocodeComponent == null) {
            traits = new HashMap<>();
        } else {
            traits = new HashMap<>(biocodeComponent.traits());
        }

        boolean changed = false;
        for (int i = 1; i <= 5; i++) {
            ItemStack vialStack = items.getStackInSlot(i);
            if (vialStack.isEmpty() || !(vialStack.getItem() instanceof BiotraitExtractItem)) {
                continue;
            }

            ResourceLocation traitId = vialStack.get(ModDataComponents.BIOTRAIT);
            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection == null)
                continue;
            Registry<Biotrait> registry = connection.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
            Biotrait biotrait = registry.get(traitId);

            if (biotrait != null) {
                traits.put(biotrait.type(), traitId);
                items.extractItem(i, 1, false);
                changed = true;
            }
        }

        if (changed) {
            cellStack.set(ModDataComponents.BIOCODE, new Biocode(traits));
        }
    }
}

