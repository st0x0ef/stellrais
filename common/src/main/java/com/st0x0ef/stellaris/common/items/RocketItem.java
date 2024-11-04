package com.st0x0ef.stellaris.common.items;

import com.st0x0ef.stellaris.common.blocks.RocketLaunchPad;
import com.st0x0ef.stellaris.common.data_components.RocketComponent;
import com.st0x0ef.stellaris.common.entities.vehicles.RocketEntity;
import com.st0x0ef.stellaris.common.registry.DataComponentsRegistry;
import com.st0x0ef.stellaris.common.registry.EntityRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class RocketItem extends Item {
    public RocketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);
        InteractionHand hand = context.getHand();
        ItemStack itemStack = context.getItemInHand();

        if (state.getBlock() instanceof RocketLaunchPad && state.getValue(RocketLaunchPad.STAGE) && context.getLevel() instanceof ServerLevel level) {
            BlockPlaceContext blockplacecontext = new BlockPlaceContext(context);
            BlockPos blockpos = blockplacecontext.getClickedPos();
            Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos, this.getRocketPlaceHigh());
            AABB aabb = EntityRegistry.BIG_ROCKET.get().getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());

            if (level.noCollision(aabb)) {
                /** POS */
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();

                /** CHECK IF NO ENTITY ON THE LAUNCH PAD */
                AABB scanAbove = new AABB(x, y, z, x + 1, y + 1, z + 1);
                List<Entity> entities = player.getCommandSenderWorld().getEntitiesOfClass(Entity.class, scanAbove);

                if (entities.isEmpty()) {
                    RocketEntity rocket = this.getRocket(context.getLevel(), itemStack);
                    /** SET PRE POS */
                    rocket.setPos(pos.getX() + 0.5D,  pos.getY() + 1.0D, pos.getZ() + 0.5D);

                    double d0 = RocketItem.getYOffset(level, pos, true, rocket.getBoundingBox());
                    float f = (float) Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 45.0F) / 90.0F) * 90.0F;

                    /** SET FINAL POS */
                    rocket.moveTo(pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D, f, 0.0F);

                    rocket.yRotO = rocket.getYRot();

                    if (level.addFreshEntity(rocket)) {
                        /** ITEM REMOVE */
                        if (!player.getAbilities().instabuild) {
                            player.setItemInHand(hand, ItemStack.EMPTY);
                        }

                        /** PLACE SOUND */
                        this.rocketPlaceSound(pos, level);

                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return super.useOn(context);
    }

    public EntityType<? extends RocketEntity> getEntityType(ItemStack stack) {
        RocketComponent rocketComponent = stack.get(DataComponentsRegistry.ROCKET_COMPONENT.get());
        return switch (rocketComponent.getModel().toString()) {
            case "tiny" -> EntityRegistry.TINY_ROCKET.get();
            case "small" -> EntityRegistry.SMALL_ROCKET.get();
            case "normal" -> EntityRegistry.NORMAL_ROCKET.get();
            case "big" -> EntityRegistry.BIG_ROCKET.get();
            default -> EntityRegistry.TINY_ROCKET.get();
        };
    }

    public RocketEntity getRocket(Level level, ItemStack stack) {
        RocketEntity rocket = new RocketEntity(this.getEntityType(stack), level);
        RocketComponent rocketComponent = stack.get(DataComponentsRegistry.ROCKET_COMPONENT.get());
        if(rocketComponent != null) {
            rocket.FUEL = rocketComponent.fuel();
        }
        return rocket;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        RocketComponent rocketComponent = stack.get(DataComponentsRegistry.ROCKET_COMPONENT.get());
        if(rocketComponent == null) return;
        tooltipComponents.add(Component.translatable("tooltip.item.stellaris.rocket.fuel", rocketComponent.fuel()).withStyle(ChatFormatting.GRAY));
    }

    public float getRocketPlaceHigh() {
        return -0.6F;
    }

    public void rocketPlaceSound(BlockPos pos, Level world) {
        world.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1,1);
    }

    protected static double getYOffset(LevelReader reader, BlockPos pos, boolean p_20628_, AABB p_20629_) {
        AABB aabb = new AABB(pos);
        if (p_20628_) {
            aabb = aabb.expandTowards(0.0D, -1.0D, 0.0D);
        }

        Iterable<VoxelShape> iterable = reader.getCollisions(null, aabb);
        return 1.0D + Shapes.collide(Direction.Axis.Y, p_20629_, iterable, p_20628_ ? -2.0D : -1.0D);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        RocketComponent rocketComponent = stack.get(DataComponentsRegistry.ROCKET_COMPONENT.get());
        return 13 * rocketComponent.fuel() / rocketComponent.getTankCapacity();
    }

    @Override
    public int getBarColor(ItemStack stack) {
        RocketComponent rocketComponent = stack.get(DataComponentsRegistry.ROCKET_COMPONENT.get());
        return switch (rocketComponent.getMotorUpgrade().getFuelType()) {
            case FUEL -> 0xA7E6ED;
            case HYDROGEN -> 0x00d8ff;
            case RADIOACTIVE -> 0x00c12f;
            case null -> 0xA7E6ED;

        };
    }

}
