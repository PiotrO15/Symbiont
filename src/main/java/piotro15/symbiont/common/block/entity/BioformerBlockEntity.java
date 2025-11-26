package piotro15.symbiont.common.block.entity;

import net.minecraft.core.*;
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
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import piotro15.symbiont.util.DynamicFluidTank;
import piotro15.symbiont.util.DynamicItemStackHandler;
import piotro15.symbiont.util.ItemUtils;
import piotro15.symbiont.common.genetics.Biocode;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.menu.BioformerMenu;
import piotro15.symbiont.common.recipe.BioformerRecipe;
import piotro15.symbiont.common.recipe.BioformerRecipeInput;
import piotro15.symbiont.common.registry.ModBlockEntities;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModItems;
import piotro15.symbiont.common.registry.ModRecipeTypes;

import java.util.Optional;

public class BioformerBlockEntity extends BasicMachineBlockEntity implements MenuProvider {
    private final DynamicFluidTank inputTank = new DynamicFluidTank(4000, this);
    private final EnergyStorage energyStorage = new EnergyStorage(10000, 100);

    protected ItemStackHandler inventory;

    private int progress;
    private static final int MAX_PROGRESS = 200;

    public BioformerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIOFORMER.get(), pos, state);
        inventory = new DynamicItemStackHandler(6, this);

        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> BioformerBlockEntity.this.progress;
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

        inventory.deserializeNBT(lookup, tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
        if (tag.contains("Energy")) {
            Tag energy = tag.get("Energy");

            if (energy != null) {
                energyStorage.deserializeNBT(lookup, energy);
            }
        }
        inputTank.readFromNBT(lookup, tag.getCompound("InputTank"));
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
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInv, @NotNull Player player) {
        return new BioformerMenu(id, playerInv, this, this.data);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.bioformer");
    }

    private void craftRecipe(BioformerRecipe recipe) {
        if (level == null) {
            return;
        }

        double productionMultiplier;
        double consumptionMultiplier = 1.0;
        int countChange;

        ItemStack inputItem = inventory.getStackInSlot(0);

        if (inputItem.is(ModItems.CELL_CULTURE)) {
            productionMultiplier = CellCultureItem.getProduction(inputItem, level);
            consumptionMultiplier = CellCultureItem.getConsumption(inputItem, level);

            countChange = CellCultureItem.getCountChange(inputItem, level.random);
            if (countChange < 0) {
                inventory.extractItem(0, 1, false);
            }
        } else {
            productionMultiplier = 1.0;
            countChange = 0;
            if (!recipe.itemInput().isEmpty()) {
                inventory.extractItem(0, 1, false);
            }
        }
        inputTank.drain(ItemUtils.randomCount(recipe.fluidInput().amount(), consumptionMultiplier, level.random), IFluidHandler.FluidAction.EXECUTE);

        recipe.output().forEach(stack -> {
            ItemStack adjustedResultItem = ItemStack.EMPTY;
            if (stack.is(ModItems.CELL_CULTURE) && inputItem.is(ModItems.CELL_CULTURE)) {
                if (countChange > 0) {
                    Biocode biocode = inputItem.get(ModDataComponents.BIOCODE);

                    adjustedResultItem = stack.copyWithCount(countChange);
                    if (biocode != null) {
                        adjustedResultItem.set(ModDataComponents.BIOCODE, biocode);
                    }
                }
            } else {
                int randomCount = ItemUtils.randomCount(stack.getCount(), productionMultiplier, level.random);
                if (randomCount <= 0) {
                    return;
                }
                adjustedResultItem = stack.copyWithCount(randomCount);
            }
            ItemUtils.insertIntoInventory(inventory, adjustedResultItem, 2, 6);
        });
    }

    private boolean canAcceptOutput(BioformerRecipe recipe) {
        if (level == null) {
            return false;
        }

        NonNullList<ItemStack> result = recipe.output();

        double productionMultiplier = 1.0;
        double stability = 1.0;

        ItemStack inputItem = inventory.getStackInSlot(0);
        if (inputItem.is(ModItems.CELL_CULTURE)) {
            productionMultiplier = CellCultureItem.getProduction(inputItem, level);
            stability = CellCultureItem.getStability(inputItem, level);
        }

        NonNullList<ItemStack> adjustedResult = NonNullList.create();
        for (ItemStack stack : result) {
            if (stack.is(ModItems.CELL_CULTURE) && inputItem.is(ModItems.CELL_CULTURE)) {
                if (stability > 1.0) {
                    Biocode biocode = inputItem.get(ModDataComponents.BIOCODE);

                    ItemStack adjustedResultItem = stack.copyWithCount((int) Math.ceil(stability - 1.0));
                    if (biocode != null) {
                        adjustedResultItem.set(ModDataComponents.BIOCODE, biocode);
                    }
                }
            } else {
                int maxCount = (int) Math.ceil(stack.getCount() * productionMultiplier);
                adjustedResult.add(stack.copyWithCount(maxCount));
            }
        }

        return ItemUtils.canFitOutputs(inventory, adjustedResult, 2, 6);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public FluidTank getInputTank() {
        return inputTank;
    }

    public int getMaxProgress() {
        if (inventory.getStackInSlot(0).is(ModItems.CELL_CULTURE)) {
            double progressMultiplier = CellCultureItem.getGrowth(inventory.getStackInSlot(0), level);
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

        BioformerRecipeInput input = new BioformerRecipeInput(inventory.getStackInSlot(0), inventory.getStackInSlot(1), inputTank.getFluid());

        if (level == null) {
            return;
        }

        Optional<RecipeHolder<BioformerRecipe>> recipeMatch = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.BIOFORMER.get(), input, level);

        if (recipeMatch.isEmpty()) {
            progress = 0;
            return;
        }

        BioformerRecipe match = recipeMatch.get().value();

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
        return this.inventory;
    }

    @Nullable
    public IFluidHandler getFluidHandlerForSide(@Nullable Direction side) {
        return inputTank;
    }

    @Nullable
    public IEnergyStorage getEnergyStorageForSide(@Nullable Direction side) {
        return this.energyStorage;
    }
}
