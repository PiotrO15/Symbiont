package piotro15.symbiont.common.block.entity;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import piotro15.symbiont.api.DynamicFluidTank;
import piotro15.symbiont.api.DynamicItemStackHandler;
import piotro15.symbiont.api.ItemApi;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biocode;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.item.BiotraitExtractItem;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.menu.BioformerMenu;
import piotro15.symbiont.common.recipe.BioformerRecipe;
import piotro15.symbiont.common.recipe.BioformerRecipeInput;
import piotro15.symbiont.common.registry.ModBlockEntities;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModItems;
import piotro15.symbiont.common.registry.ModRecipeTypes;

import java.util.List;
import java.util.Optional;

public class BioformerBlockEntity extends BasicMachineBlockEntity implements MenuProvider {
    private final DynamicFluidTank inputTank = new DynamicFluidTank(4000, this);
    private final EnergyStorage energyStorage = new EnergyStorage(10000, 100);

    protected ItemStackHandler items;

    private int progress;
    private static final int MAX_PROGRESS = 200;

    public BioformerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIOFORMER.get(), pos, state);
        items = new DynamicItemStackHandler(6, this);

        data = new ContainerData() {
            private final int[] ints = new int[4];

            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> BioformerBlockEntity.this.progress;
                    case 2 -> energyStorage.getEnergyStored();
                    case 3 -> energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> BioformerBlockEntity.this.progress = value;
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

        double productionMultiplier = 1.0;
        double consumptionMultiplier = 1.0;

        ItemStack inputItem = items.getStackInSlot(0);
        int countChange = 0;

        if (inputItem.getItem() instanceof CellCultureItem) {
            productionMultiplier = CellCultureItem.getProduction(inputItem);
            consumptionMultiplier = CellCultureItem.getConsumption(inputItem);

            countChange = CellCultureItem.getCountChange(inputItem, level.random);
            if (countChange < 0) {
                items.extractItem(0, 1, false);
            }
        } else if (!recipe.itemInput().isEmpty()) {
            items.extractItem(0, 1, false);
        }

        inputTank.drain(ItemApi.randomCount(recipe.fluidInput().amount(), consumptionMultiplier, level.random), IFluidHandler.FluidAction.EXECUTE);

        // produce outputs
        List<ItemStack> output = recipe.output().stream().map(ItemStack::copy).toList();

        for (ItemStack drop : output) {
            if (drop.is(ModItems.CELL_CULTURE) && inputItem.is(ModItems.CELL_CULTURE)) {
                if (countChange <= 0) {
                    continue;
                }

                drop.setCount(countChange);
                Biocode biocode = inputItem.get(ModDataComponents.BIOCODE);
                if (biocode != null) {
                    drop.set(ModDataComponents.BIOCODE, biocode);
                }
            } else if (!inputItem.is(ModItems.CELL_CULTURE)) {
                drop.setCount(ItemApi.randomCount(drop.getCount(), productionMultiplier, level.random));
            }
            ItemApi.insertIntoInventory(items, drop, 2, 6);
        }
    }

    private boolean canAcceptOutput(BioformerRecipe recipe) {
        if (level == null) {
            return false;
        }

        NonNullList<ItemStack> result = recipe.output();

        double growthMultiplier;
        if (items.getStackInSlot(0).getItem() instanceof CellCultureItem) {
            growthMultiplier = CellCultureItem.getProduction(items.getStackInSlot(0));
        } else {
            growthMultiplier = 1;
        }

        if (growthMultiplier != 1.0) {
            NonNullList<ItemStack> adjustedResult = NonNullList.create();
            for (ItemStack stack : result) {
                ItemStack adjustedStack = stack.copy();
                int newCount = (int) Math.ceil(stack.getCount() * growthMultiplier);
                adjustedStack.setCount(newCount);
                adjustedResult.add(adjustedStack);
            }
            result = adjustedResult;
        }

        return ItemApi.canFitOutputs(items, result, 2, 6);
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public FluidTank getInputTank() {
        return inputTank;
    }

    public EnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
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

        BioformerRecipeInput input = new BioformerRecipeInput(items.getStackInSlot(0), items.getStackInSlot(1), inputTank.getFluid());

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
            energyStorage.extractEnergy(20, false); // cost per tick
            progress++;

            if (progress >= getMaxProgress()) {
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
        return inputTank;
    }

    @Nullable
    public IEnergyStorage getEnergyStorageForSide(@Nullable Direction side) {
        return this.energyStorage; // simple: same energy storage on all sides
    }
}
