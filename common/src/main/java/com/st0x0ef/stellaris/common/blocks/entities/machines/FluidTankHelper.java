package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.capabilities.FluidTank;
import com.st0x0ef.stellaris.common.data_components.CappedLongComponent;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.utils.FuelUtils;
import com.st0x0ef.stellaris.common.utils.OxygenUtils;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidBucketHooks;
import dev.architectury.hooks.fluid.FluidStackHooks;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class FluidTankHelper {

    //Handled by Potentials
    public static final long BUCKET_AMOUNT = 1000;

    public static <T extends BlockEntity & Container> void extractFluidToItem(T blockEntity, FluidTank tank, int slot) {
        ItemStack inputStack = blockEntity.getItem(slot);
        if (!inputStack.isEmpty()) {
            if (!tank.isEmpty()) {
                boolean isTank = inputStack.has(DataComponentsRegistry.STORED_OXYGEN_COMPONENT.get());

                if (tank.getAmount() >= BUCKET_AMOUNT || (isTank && !tank.isEmpty())) {
                    ItemStack resultStack = ItemStack.EMPTY;

                    if (isTank && tank.getStack().getFluid().isSame(FluidRegistry.OXYGEN_STILL.get())) {
                        resultStack = inputStack.copy();
                        long storedOxygen = OxygenUtils.getOxygen(inputStack);

                        if (storedOxygen + 1 >= OxygenUtils.getOxygenCapacity(inputStack)) {
                            return;
                        }

                        else if (OxygenUtils.getOxygenCapacity(inputStack) - storedOxygen > convertFromNeoMb(10) && tank.getAmount() > convertFromNeoMb(10)) {
                            OxygenUtils.addOxygen(resultStack, convertFromNeoMb(10));
                            tank.shrink(convertFromNeoMb(10));
                        } else if (tank.getAmount() < convertFromNeoMb(10) && storedOxygen + tank.getAmount() <= OxygenUtils.getOxygenCapacity(inputStack)) {
                            OxygenUtils.addOxygen(resultStack, tank.getAmount());
                            tank.shrink(tank.getAmount());
                        }
                        else if (tank.getAmount() > OxygenUtils.getOxygenCapacity(inputStack) - storedOxygen){
                            OxygenUtils.addOxygen(resultStack, OxygenUtils.getOxygenCapacity(inputStack) - storedOxygen);
                            tank.shrink(OxygenUtils.getOxygenCapacity(inputStack) - storedOxygen);
                        }
                    }
                    else if (!isTank && isEmptyBucket(inputStack.getItem())) {
                        ItemStack stack = new ItemStack(tank.getFluidStack().getFluid().getBucket());
                        if (!stack.isEmpty() && !isEmptyBucket(stack.getItem())) {
                            resultStack = stack;
                            tank.drainFluid(FluidStack.create(tank.getBaseFluid(), BUCKET_AMOUNT), false);
                        }
                    }

                    if (!resultStack.isEmpty()) {
                        blockEntity.setItem(slot, resultStack);
                        blockEntity.setChanged();
                    }
                }
            }
        }
    }

    public static boolean isEmptyBucket(Item item) {
        return item instanceof BucketItem bucketItem && FluidBucketHooks.getFluid(bucketItem).isSame(Fluids.EMPTY);
    }

    public static <T extends BlockEntity & Container> void extractFluidToItem(T blockEntity, FluidTank tank, int inputSlot, int outputSlot) {
        ItemStack outputStack = blockEntity.getItem(outputSlot);
        ItemStack inputStack = blockEntity.getItem(inputSlot);
        boolean hasSpace = outputStack.getCount() < outputStack.getMaxStackSize();

        if (!inputStack.isEmpty() && (outputStack.isEmpty() || hasSpace)) {
            boolean canFuel = inputStack.has(DataComponentsRegistry.STORED_FUEL_COMPONENT.get());

            if (!tank.getFluidStack().isEmpty() && (tank.getFluidValue() >= BUCKET_AMOUNT || canFuel)) {
                ItemStack resultStack = ItemStack.EMPTY;

                if (isEmptyBucket(inputStack.getItem())) {
                    resultStack = new ItemStack(tank.getFluidStack().getFluid().getBucket());
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
                            amount = Math.min(FuelUtils.getFuelCapacity(inputStack) - fuel, tank.getFluidValue());
                            resultStack.set(DataComponentsRegistry.STORED_FUEL_COMPONENT.get(), new CappedLongComponent(
                                    Mth.clamp(fuel + amount, 0, FuelUtils.getFuelCapacity(inputStack)), FuelUtils.getFuelCapacity(inputStack)));
                        }

                        inputStack.shrink(1);
                        tank.drainFluid(FluidStack.create(tank.getBaseFluid(), amount), false);
                        blockEntity.setChanged();
                    }
                }
            }
        }
    }

    public static void addToTank(FluidTank tank, FluidStack stack) {
        tank.fillFluid(stack, false);

    }

    public static <T extends BlockEntity & Container> boolean addFluidFromBucket(T blockEntity, FluidTank tank, int inputSlot, int outputSlot) {
        if (tank.getFluidValue() + BUCKET_AMOUNT < tank.getMaxAmount()) {
            ItemStack inputStack = blockEntity.getItem(inputSlot);
            ItemStack outputStack = blockEntity.getItem(outputSlot);
            boolean hasSpace = outputStack.getCount() < outputStack.getMaxStackSize();

            if (!inputStack.isEmpty() && (outputStack.isEmpty() || hasSpace)) {
                if (inputStack.getItem() instanceof BucketItem item) {
                    Fluid fluid = FluidBucketHooks.getFluid(item);

                    if (tank.getFluidStack().getFluid() == fluid) {
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
                        addToTank(tank, FluidStack.create(fluid, BUCKET_AMOUNT));
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
