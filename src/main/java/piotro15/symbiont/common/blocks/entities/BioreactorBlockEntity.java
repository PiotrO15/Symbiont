package piotro15.symbiont.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import piotro15.symbiont.api.DynamicFluidTank;
import piotro15.symbiont.api.DynamicItemStackHandler;
import piotro15.symbiont.api.FluidApi;
import piotro15.symbiont.common.genetics.IntegerTraitModifier;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.menus.BioreactorMenu;
import piotro15.symbiont.common.recipe.BioreactorRecipe;
import piotro15.symbiont.common.recipe.BioreactorRecipeInput;
import piotro15.symbiont.common.registries.ModBlockEntities;
import piotro15.symbiont.common.registries.ModRecipeTypes;

import java.util.Optional;

public class BioreactorBlockEntity extends BasicMachineBlockEntity implements MenuProvider {
    private final DynamicFluidTank inputTank = new DynamicFluidTank(4000, this);
    private final DynamicFluidTank outputTank = new DynamicFluidTank(4000, this);
    private final EnergyStorage energyStorage = new EnergyStorage(10000, 100);
    protected ItemStackHandler items;

    private int progress;
    private static final int MAX_PROGRESS = 200;

    public BioreactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIOREACTOR.get(), pos, state);
        items = new DynamicItemStackHandler(2, this);

        data = new ContainerData() {
            private final int[] ints = new int[4];

            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> BioreactorBlockEntity.this.progress;
                    case 2 -> energyStorage.getEnergyStored();
                    case 3 -> energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> BioreactorBlockEntity.this.progress = value;
//                    case 1: BioreactorBlockEntity.this.maxProgress = value;
                    default -> {}
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
        super.loadAdditional(tag, lookup);

        items.deserializeNBT(lookup, tag.getCompound("Items"));
        progress = tag.getInt("Progress");
        if (tag.contains("Energy")) {
            energyStorage.deserializeNBT(lookup, tag.get("Energy"));
        }
        inputTank.readFromNBT(lookup, tag.getCompound("InputTank"));
        outputTank.readFromNBT(lookup, tag.getCompound("OutputTank"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
        super.saveAdditional(tag, lookup);

        tag.put("Items", items.serializeNBT(lookup));
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
        return new BioreactorMenu(id, playerInv, this, this.data);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.bioreactor");
    }

    private void craftRecipe(BioreactorRecipe recipe) {
        if (level == null) {
            return;
        }

        ItemStack inputItem = items.getStackInSlot(0);

        // consume inputs
        items.extractItem(0, 1, false);
        inputTank.drain(recipe.fluidInput().getStacks()[0].getAmount(), IFluidHandler.FluidAction.EXECUTE);

        // produce outputs
        ItemStack output = recipe.output().copy();
        int outputCount = output.getCount();

        if (inputItem.getItem() instanceof CellCultureItem cultureInput) {
            double stability = cultureInput.getStability(inputItem);
            outputCount = (int) Math.floor(stability);
            if (getLevel().getRandom().nextDouble() > cultureInput.getStability(inputItem) % 1) {
                outputCount++;
            }
        }

        output.setCount(outputCount + items.getStackInSlot(1).getCount());

        items.setStackInSlot(1, output);
        outputTank.fill(recipe.fluidOutput(), IFluidHandler.FluidAction.EXECUTE);
    }

    private boolean canAcceptOutput(BioreactorRecipe recipe) {
        if (level == null) {
            return false;
        }

        ItemStack result = recipe.getResultItem(level.registryAccess());
        FluidStack fluidResult = recipe.fluidOutput();

        // check item
        ItemStack existing = items.getStackInSlot(1);
        boolean itemsFit = (existing.isEmpty() || (ItemStack.isSameItem(existing, result)
                && existing.getCount() + result.getCount() <= existing.getMaxStackSize()));

        // check fluid
        boolean fluidsFit = outputTank.fill(fluidResult, IFluidHandler.FluidAction.SIMULATE) == fluidResult.getAmount();

        return itemsFit && fluidsFit;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public FluidTank getInputTank() {
        return inputTank;
    }

    public FluidTank getOutputTank() {
        return outputTank;
    }

    public EnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, BasicMachineBlockEntity blockEntity) {
        if (energyStorage.getEnergyStored() < 20) {
            progress = 0;
            return;
        }

        BioreactorRecipeInput input = new BioreactorRecipeInput(items.getStackInSlot(0), inputTank.getFluid());

        if (level == null) {
            return;
        }

        Optional<RecipeHolder<BioreactorRecipe>> recipeMatch = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.BIOREACTOR.get(), input, level);

        if (recipeMatch.isEmpty()) {
            progress = 0;
            return;
        }

        BioreactorRecipe match = recipeMatch.get().value();

        if (canAcceptOutput(match)) {
            energyStorage.extractEnergy(20, false); // cost per tick
            progress++;

            if (progress >= MAX_PROGRESS) {
                craftRecipe(match);
                progress = 0;
            }
        }
    }

    @Nullable
    public IItemHandler getItemHandlerForSide(@Nullable Direction side) {
        return this.items; // simple default: all sides see the same handler
    }

    @Nullable
    public IFluidHandler getFluidHandlerForSide(@Nullable Direction side) {
        // You can add side-based rules here (e.g., only allow fill from UP, only allow drain from DOWN).
        return new FluidApi.CombinedFluidHandler(inputTank, outputTank);
    }

    @Nullable
    public IEnergyStorage getEnergyStorageForSide(@Nullable Direction side) {
        return this.energyStorage; // simple: same energy storage on all sides
    }


}

