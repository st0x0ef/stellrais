package com.st0x0ef.stellaris.common.blocks;

import com.st0x0ef.stellaris.common.registry.BlocksRegistry;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class CoalTorchBlock extends TorchBlock {

    public CoalTorchBlock(Properties properties) {
        super(ParticleTypes.ASH, properties);
    }

    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack itemstack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockState(pos).getBlock() == BlocksRegistry.WALL_COAL_TORCH_BLOCK.get() && PlanetUtil.hasOxygen(level) && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
            if (!level.isClientSide) {

                state = level.getBlockState(pos);

                level.setBlock(pos, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, state.getValue(WallCoalTorchBlock.FACING)), 3);

                this.flintManager(itemstack, (ServerPlayer) player, pos, (ServerLevel) level);
                return InteractionResult.SUCCESS;
            }
        }

        if (level.getBlockState(pos).getBlock() == BlocksRegistry.COAL_TORCH_BLOCK.get() && PlanetUtil.hasOxygen(level) && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
            if (!level.isClientSide) {

                level.setBlock(pos, Blocks.TORCH.defaultBlockState(), 3);

                this.flintManager(itemstack, (ServerPlayer) player, pos, (ServerLevel) level);
                return InteractionResult.SUCCESS;
            }
        }

        if (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void flintManager(ItemStack itemstack, ServerPlayer player, BlockPos pos, ServerLevel level) {
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, 1);

            itemstack.hurtAndBreak(1, level, player, (item) -> {});
        }

        if (itemstack.getItem() == Items.FIRE_CHARGE) {
            level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1,1);

            if (!player.getAbilities().instabuild && !player.isSpectator()) {
                itemstack.setCount(itemstack.getCount() - 1);
            }
        }
    }
}