package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.data_components.CappedLongComponent;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.utils.FuelUtils;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.FluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidBucketHooks;
import dev.architectury.platform.Platform;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

@Deprecated(forRemoval = true)
public class FluidTankHelper {

    //Handled by Potentials
    public static final long BUCKET_AMOUNT = 1000;

    public static boolean isEmptyBucket(Item item) {
        return item instanceof BucketItem bucketItem && FluidBucketHooks.getFluid(bucketItem).isSame(Fluids.EMPTY);
    }

    public static <T extends BlockEntity & Container> void extractFluidToItem(T blockEntity, FluidStorage tank, int inputSlot, int outputSlot) {
        ItemStack outputStack = blockEntity.getItem(outputSlot);
        ItemStack inputStack = blockEntity.getItem(inputSlot);
        boolean hasSpace = outputStack.getCount() < outputStack.getMaxStackSize();

        if (!inputStack.isEmpty() && (outputStack.isEmpty() || hasSpace)) {
            boolean canFuel = inputStack.has(DataComponentsRegistry.STORED_FUEL_COMPONENT.get());

            if (!tank.isEmpty() && (tank.getTanks() >= BUCKET_AMOUNT || canFuel)) {
                ItemStack resultStack = ItemStack.EMPTY;

                if (isEmptyBucket(inputStack.getItem())) {
                    resultStack = new ItemStack(tank.getFluidInTank(inputSlot).getFluid().getBucket());
                }
                else if (canFuel) {
                    resultStack = inputStack.copy();
                }

                if (!resultStack.isEmpty()) {
                    boolean success = false;
                    long amount = BUCKET_AMOUNT;

                    if (outputStack.isEmpty()) {
                        blockEntity.setItem(outputSlot, resultStack);
                        success = true;
                    }
                    else if (ItemStack.isSameItem(outputStack, resultStack) && hasSpace) {
                        outputStack.grow(1);
                        success = true;
                    }

                    if (success) {
                        if (canFuel) {
                            long fuel = FuelUtils.getFuel(inputStack);
                            amount = Math.min(FuelUtils.getFuelCapacity(inputStack) - fuel, tank.getTanks());
                            resultStack.set(DataComponentsRegistry.STORED_FUEL_COMPONENT.get(), new CappedLongComponent(
                                    Mth.clamp(fuel + amount, 0, FuelUtils.getFuelCapacity(inputStack)), FuelUtils.getFuelCapacity(inputStack)));
                        }

                        inputStack.shrink(1);
                        tank.fill(FluidStack.create(tank.getFluidInTank(inputSlot), amount), false);
                        blockEntity.setChanged();
                    }
                }
            }
        }
    }

    public static <T extends BlockEntity & Container> boolean addFluidFromBucket(T blockEntity, FluidStorage tank, int inputSlot, int outputSlot) {
        if (tank.getFluidValueInTank(inputSlot) + BUCKET_AMOUNT < tank.getTanks()) {
            ItemStack inputStack = blockEntity.getItem(inputSlot);
            ItemStack outputStack = blockEntity.getItem(outputSlot);
            boolean hasSpace = outputStack.getCount() < outputStack.getMaxStackSize();

            if (!inputStack.isEmpty() && (outputStack.isEmpty() || hasSpace)) {
                if (inputStack.getItem() instanceof BucketItem item) {
                    Fluid fluid = FluidBucketHooks.getFluid(item);

                    if (tank.getFluidInTank(inputSlot).getFluid() == fluid) {
                        if (outputStack.isEmpty()) {
                            blockEntity.setItem(outputSlot, new ItemStack(Items.BUCKET));
                        }
                        else if (outputStack.is(Items.BUCKET) && hasSpace) {
                            outputStack.grow(1);
                        }
                        else {
                            return false;
                        }

                        blockEntity.setItem(inputSlot, ItemStack.EMPTY);
                        tank.fill(FluidStack.create(fluid, BUCKET_AMOUNT), false);
                        blockEntity.setChanged();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static long convertFromNeoMb(long amount) {
        return Platform.isFabric() ? amount * 81L : amount;
    }

    public static int convertFromNeoMb(int amount) {
        return Platform.isFabric() ? amount * 81 : amount;
    }

    public static int convertToNeoMb(int amount) {
        return Platform.isFabric() ? amount / 81 : amount;
    }
}
