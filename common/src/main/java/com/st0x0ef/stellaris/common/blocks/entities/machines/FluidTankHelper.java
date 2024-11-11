package com.st0x0ef.stellaris.common.blocks.entities.machines;

import com.st0x0ef.stellaris.common.data_components.CappedLongComponent;
import com.st0x0ef.stellaris.common.items.armors.JetSuit;
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

    public static final long BUCKET_AMOUNT = FluidStackHooks.bucketAmount();

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
                        ItemStack stack = new ItemStack(tank.getStack().getFluid().getBucket());
                        if (!stack.isEmpty() && !isEmptyBucket(stack.getItem())) {
                            resultStack = stack;
                            tank.shrink(BUCKET_AMOUNT);
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

            if (!tank.isEmpty() && (tank.getAmount() >= BUCKET_AMOUNT || canFuel)) {
                ItemStack resultStack = ItemStack.EMPTY;

                if (isEmptyBucket(inputStack.getItem())) {
                    resultStack = new ItemStack(tank.getStack().getFluid().getBucket());
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
                            amount = Math.min(FuelUtils.getFuelCapacity(inputStack) - fuel, tank.getAmount());
                            resultStack.set(DataComponentsRegistry.STORED_FUEL_COMPONENT.get(), new CappedLongComponent(
                                    Mth.clamp(fuel + amount, 0, FuelUtils.getFuelCapacity(inputStack)), FuelUtils.getFuelCapacity(inputStack)));
                        }

                        inputStack.shrink(1);
                        tank.shrink(amount);
                        blockEntity.setChanged();
                    }
                }
            }
        }
    }

    public static void addToTank(FluidTank tank, FluidStack stack) {
        FluidStack tankStack = tank.getStack();
        if (tankStack.isEmpty()) {
            tank.setFluid(stack.getFluid(), stack.getAmount());
        }
        else if (tank.getStack().isFluidEqual(stack)) {
            tank.grow(stack.getAmount());
        }
    }

    public static <T extends BlockEntity & Container> boolean addFluidFromBucket(T blockEntity, FluidTank tank, int inputSlot, int outputSlot) {
        if (tank.getAmount() + BUCKET_AMOUNT < tank.getMaxCapacity()) {
            ItemStack inputStack = blockEntity.getItem(inputSlot);
            ItemStack outputStack = blockEntity.getItem(outputSlot);
            boolean hasSpace = outputStack.getCount() < outputStack.getMaxStackSize();

            if (!inputStack.isEmpty() && (outputStack.isEmpty() || hasSpace)) {
                if (inputStack.getItem() instanceof BucketItem item) {
                    Fluid fluid = FluidBucketHooks.getFluid(item);

                    if ((!tank.isEmpty() && tank.getStack().getFluid() == fluid) || (tank.isEmpty() && !fluid.isSame(Fluids.EMPTY))) {
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

    public static void transferFluidNearby(BlockEntity entity, FluidTank fluidTank) {
        BlockPos pos = entity.getBlockPos();
        List<BlockPos> adjacentBlocks = List.of(pos.above(), pos.below(), pos.relative(Direction.SOUTH), pos.relative(Direction.EAST), pos.relative(Direction.NORTH),pos.relative(Direction.WEST));

        BlockPos[] pos1 = adjacentBlocks.toArray(new BlockPos[0]);
        for (BlockPos pos2 : pos1) {
            BlockEntity block = entity.getLevel().getBlockEntity(pos2);
            if(block == null) return;
            if (block instanceof WrappedFluidBlockEntity fluidBlock) {

                for (FluidTank tank : fluidBlock.getFluidTanks()) {

                    if (tank.getStack().getFluid() == fluidTank.getStack().getFluid()) {
                        if (fluidTank.getAmount() - 10 > 0 && tank.canGrow(10)) {
                            tank.grow(10);
                            fluidTank.grow(-10);

                            entity.setChanged();
                        }
                    }
                }
            }
        }
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
