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
import piotro15.symbiont.api.ItemApi;
import piotro15.symbiont.common.genetics.Biocode;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.menu.MetabolizerMenu;
import piotro15.symbiont.common.recipe.MetabolizerRecipe;
import piotro15.symbiont.common.recipe.MetabolizerRecipeInput;
import piotro15.symbiont.common.registry.ModBlockEntities;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModItems;
import piotro15.symbiont.common.registry.ModRecipeTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MetabolizerBlockEntity extends BasicMachineBlockEntity implements MenuProvider {
    private final DynamicFluidTank inputTank = new DynamicFluidTank(4000, this);
    private final DynamicFluidTank outputTank = new DynamicFluidTank(4000, this);
    private final EnergyStorage energyStorage = new EnergyStorage(10000, 100);

    private final ItemStackHandler items;

    private int progress;
    private static final int MAX_PROGRESS = 200;

    public MetabolizerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.METABOLIZER.get(), pos, state);
        items = new DynamicItemStackHandler(6, this);

        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> MetabolizerBlockEntity.this.progress;
                    case 1 -> getMaxProgress();
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

        items.deserializeNBT(lookup, tag.getCompound("Inventory"));
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

        tag.put("Inventory", items.serializeNBT(lookup));
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
        return new MetabolizerMenu(id, playerInv, this, this.data);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.metabolizer");
    }

    private void craftRecipe(MetabolizerRecipe recipe) {
        if (level == null) {
            return;
        }

        ItemStack inputItem = items.getStackInSlot(0);
        ItemStack resultItem = recipe.output();
        if (inputItem.is(ModItems.CELL_CULTURE) && resultItem.is(ModItems.CELL_CULTURE)) {
            double productionMultiplier = CellCultureItem.getProduction(inputItem);
            double consumptionMultiplier = CellCultureItem.getConsumption(inputItem);
            int countChange = CellCultureItem.getCountChange(inputItem, level.random);

            if (countChange < 0) {
                items.extractItem(0, 1, false);
            }
            inputTank.drain(ItemApi.randomCount(recipe.fluidInput().amount(), consumptionMultiplier, level.random), IFluidHandler.FluidAction.EXECUTE);

            for (int i = 1; i < recipe.ingredients().size(); i++) {
                ItemApi.extractFromInventory(items, recipe.ingredients().get(i), 1, 5);
            }

            if (countChange > 0) {
                Biocode biocode = inputItem.get(ModDataComponents.BIOCODE);

                resultItem = resultItem.copyWithCount(countChange);
                if (biocode != null) {
                    resultItem.set(ModDataComponents.BIOCODE, biocode);
                }

                items.insertItem(5, resultItem, false);
            }
            outputTank.fill(recipe.fluidOutput().copyWithAmount(ItemApi.randomCount(recipe.fluidOutput().getAmount(), productionMultiplier, level.random)), IFluidHandler.FluidAction.EXECUTE);
        } else {
            items.extractItem(0, 1, false);
            inputTank.drain(recipe.fluidInput().amount(), IFluidHandler.FluidAction.EXECUTE);

            for (int i = 1; i < recipe.ingredients().size(); i++) {
                ItemApi.extractFromInventory(items, recipe.ingredients().get(i), 1, 5);
            }

            items.insertItem(5, recipe.output(), false);
            outputTank.fill(recipe.fluidOutput(), IFluidHandler.FluidAction.EXECUTE);
        }
    }

    private boolean canAcceptOutput(MetabolizerRecipe recipe) {
        if (level == null) {
            return false;
        }

        ItemStack inputItem = items.getStackInSlot(0);
        ItemStack resultItem = recipe.output();

        ItemStack result = ItemStack.EMPTY;
        FluidStack fluidResult;

        if (inputItem.is(ModItems.CELL_CULTURE) && resultItem.is(ModItems.CELL_CULTURE)) {
            double productionMultiplier = CellCultureItem.getProduction(inputItem);
            double stability = CellCultureItem.getStability(inputItem);

            if (stability > 1.0) {
                Biocode biocode = inputItem.get(ModDataComponents.BIOCODE);

                result = resultItem.copyWithCount((int) Math.ceil(stability - 1.0));
                if (biocode != null) {
                    result.set(ModDataComponents.BIOCODE, biocode);
                }
            }
            fluidResult = recipe.fluidOutput().copyWithAmount((int) Math.ceil(recipe.fluidOutput().getAmount() * productionMultiplier));
        } else {
            result = resultItem;
            fluidResult = recipe.fluidOutput();
        }

        boolean itemsFit = items.insertItem(5, result, true).isEmpty();
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

    private int getMaxProgress() {
        if (items.getStackInSlot(0).getItem() instanceof CellCultureItem) {
            double progressMultiplier = CellCultureItem.getGrowth(items.getStackInSlot(0));
            return (int) (MAX_PROGRESS / progressMultiplier);
        }
        return MAX_PROGRESS;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, BasicMachineBlockEntity blockEntity) {
        if (energyStorage.getEnergyStored() < 20) {
            progress = 0;
            return;
        }

        List<ItemStack> inputItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemStack stackInSlot = items.getStackInSlot(i);
            if (!stackInSlot.isEmpty()) {
                inputItems.add(stackInSlot);
            }
        }
        MetabolizerRecipeInput input = new MetabolizerRecipeInput(inputItems, inputTank.getFluid());

        if (level == null) {
            return;
        }

        Optional<RecipeHolder<MetabolizerRecipe>> recipeMatch = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.METABOLIZER.get(), input, level);

        if (recipeMatch.isEmpty()) {
            progress = 0;
            return;
        }

        MetabolizerRecipe match = recipeMatch.get().value();

        if (canAcceptOutput(match)) {
            energyStorage.extractEnergy(20, false);
            progress++;

            if (progress >= getMaxProgress()) {
                craftRecipe(match);
                progress = 0;
            }
        }
    }

    @Nullable
    public IItemHandler getItemHandlerForSide(@Nullable Direction side) {
        return this.items;
    }

    @Nullable
    public IFluidHandler getFluidHandlerForSide(@Nullable Direction side) {
        return new FluidApi.CombinedFluidHandler(inputTank, outputTank);
    }

    @Nullable
    public IEnergyStorage getEnergyStorageForSide(@Nullable Direction side) {
        return this.energyStorage;
    }
}
