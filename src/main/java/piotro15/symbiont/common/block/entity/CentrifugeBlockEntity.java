package piotro15.symbiont.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.menu.CentrifugeMenu;
import piotro15.symbiont.common.registry.*;
import piotro15.symbiont.util.DynamicFluidTank;
import piotro15.symbiont.util.DynamicItemStackHandler;
import piotro15.symbiont.util.FluidUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CentrifugeBlockEntity extends BasicMachineBlockEntity implements MenuProvider {
    private final DynamicFluidTank inputTank = new DynamicFluidTank(4000, this);
    private final DynamicFluidTank outputTank = new DynamicFluidTank(4000, this);
    private final EnergyStorage energyStorage = new EnergyStorage(10000, 100);

    private final ItemStackHandler inventory;

    private int progress;
    private static final int MAX_PROGRESS = 200;

    public CentrifugeBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CENTRIFUGE.get(), pos, blockState);
        inventory = new DynamicItemStackHandler(3, this);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> CentrifugeBlockEntity.this.progress;
                    case 1 -> MAX_PROGRESS;
                    case 2 -> energyStorage.getEnergyStored();
                    case 3 -> energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {}

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(tag, lookup);

        inventory.deserializeNBT(lookup, tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
        if (tag.contains("Energy")) {
            Tag energy = tag.get("Energy");

            if (energy != null) {
                energyStorage.deserializeNBT(lookup, energy);
            }
        }
        inputTank.readFromNBT(lookup, tag.getCompound("InputTank"));
        outputTank.readFromNBT(lookup, tag.getCompound("OutputTank"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
        super.saveAdditional(tag, lookup);

        tag.put("Inventory", inventory.serializeNBT(lookup));
        tag.putInt("Progress", progress);
        tag.put("Energy", energyStorage.serializeNBT(lookup));

        CompoundTag inputTag = new CompoundTag();
        inputTank.writeToNBT(lookup, inputTag);
        tag.put("InputTank", inputTag);

        CompoundTag outputTag = new CompoundTag();
        outputTank.writeToNBT(lookup, outputTag);
        tag.put("OutputTank", outputTag);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInv, @NotNull Player player) {
        return new CentrifugeMenu(id, playerInv, this, this.data);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.centrifuge");
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public FluidTank getInputTank() {
        return inputTank;
    }

    public FluidTank getOutputTank() {
        return outputTank;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, BasicMachineBlockEntity blockEntity) {
        if (energyStorage.getEnergyStored() < 20) {
            progress = 0;
            return;
        }

        if (!inputTank.getFluid().is(ModFluids.BIOPOLYMER_SOLUTION) || inputTank.getFluidAmount() < 1000) {
            progress = 0;
            return;
        }

        if (!inventory.getStackInSlot(0).is(ModItems.CELL_CULTURE)) {
            progress = 0;
            return;
        }

        ItemStack catalystStack = inventory.getStackInSlot(1);
        if (!catalystStack.is(ModTags.Items.CENTRIFUGE_CATALYSTS)) {
            progress = 0;
            return;
        }

        if (canAcceptOutput()) {
            energyStorage.extractEnergy(20, false);
            progress++;

            if (progress >= MAX_PROGRESS) {
                craftRecipe();
                progress = 0;
            }
        }
    }

    private boolean canAcceptOutput() {
        Biotrait.BiotraitType biotraitType;
        if (inventory.getStackInSlot(1).is(ModTags.Items.STABILITY_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.STABILITY;
        } else if (inventory.getStackInSlot(1).is(ModTags.Items.METABOLISM_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.METABOLISM;
        } else if (inventory.getStackInSlot(1).is(ModTags.Items.REPLICATION_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.REPLICATION;
        } else if (inventory.getStackInSlot(1).is(ModTags.Items.ADAPTABILITY_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.ADAPTABILITY;
        } else if (inventory.getStackInSlot(1).is(ModTags.Items.SPECIAL_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.SPECIAL;
        } else {
            biotraitType = null;
        }

        ItemStack extract = new ItemStack(ModItems.BIOTRAIT_EXTRACT.get());
        AtomicBoolean emptyOutput = new AtomicBoolean(true);
        List<CellCultureItem.AppliedBiotrait> traits = CellCultureItem.getBiocode(inventory.getStackInSlot(0), level);
        if (traits != null) {
            traits.stream()
                    .filter(t -> t.type() == biotraitType)
                    .findFirst()
                    .ifPresent(t -> {
                        extract.set(ModDataComponents.BIOTRAIT, t.traitId());
                        emptyOutput.set(false);
                    });
        }
        return emptyOutput.get() || inventory.insertItem(2, extract, true).isEmpty();
    }

    private void craftRecipe() {
        Biotrait.BiotraitType biotraitType;
        if (inventory.getStackInSlot(1).is(ModTags.Items.STABILITY_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.STABILITY;
        } else if (inventory.getStackInSlot(1).is(ModTags.Items.METABOLISM_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.METABOLISM;
        } else if (inventory.getStackInSlot(1).is(ModTags.Items.REPLICATION_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.REPLICATION;
        } else if (inventory.getStackInSlot(1).is(ModTags.Items.ADAPTABILITY_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.ADAPTABILITY;
        } else if (inventory.getStackInSlot(1).is(ModTags.Items.SPECIAL_CATALYSTS)) {
            biotraitType = Biotrait.BiotraitType.SPECIAL;
        } else {
            biotraitType = null;
        }

        List<CellCultureItem.AppliedBiotrait> traits = CellCultureItem.getBiocode(inventory.getStackInSlot(0), level);
        if (traits != null) {
            traits.stream()
                    .filter(t -> t.type() == biotraitType)
                    .findFirst()
                    .ifPresent(t -> {
                        ItemStack extract = new ItemStack(ModItems.BIOTRAIT_EXTRACT.get());
                        extract.set(ModDataComponents.BIOTRAIT, t.traitId());
                        inventory.insertItem(2, extract, false);
                    });
        }

        inputTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
        inventory.extractItem(0, 1, false);
        inventory.extractItem(1, 1, false);
    }

    @Nullable
    public IItemHandler getItemHandlerForSide(@Nullable Direction side) {
        return this.inventory;
    }

    @Nullable
    public IFluidHandler getFluidHandlerForSide(@Nullable Direction side) {
        return new FluidUtils.CombinedFluidHandler(this.inputTank, this.outputTank);
    }

    @Nullable
    public IEnergyStorage getEnergyStorageForSide(@Nullable Direction side) {
        return this.energyStorage;
    }
}
