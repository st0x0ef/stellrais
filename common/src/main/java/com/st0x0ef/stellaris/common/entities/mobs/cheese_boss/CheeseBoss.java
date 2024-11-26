package com.st0x0ef.stellaris.common.entities.mobs.cheese_boss;

import com.mojang.serialization.Dynamic;
import com.st0x0ef.stellaris.common.entities.mobs.cheese_boss.attack_entities.CheeseSpit;
import com.st0x0ef.stellaris.common.registry.EntityRegistry;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class CheeseBoss extends Monster implements Enemy, RangedAttackMob {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState punchAnimationState = new AnimationState();
    private int punchingAnimationTimeout = 0;
    private boolean punching = false;
    public final AnimationState spitAnimationState = new AnimationState();
    private int spittingAnimationTimeout = 0;
    private boolean spitting = false;

    private static final Component CHEESE_BOSS_NAME_COMPONENT = Component.translatable("event.stellaris.cheeseboss");
    private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(CHEESE_BOSS_NAME_COMPONENT, BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true);


    public CheeseBoss(EntityType<? extends CheeseBoss> type, Level level) {
        super(type, level);
    }

    //TODO real attributes
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 500)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.ATTACK_KNOCKBACK, 1.2)
                .add(Attributes.ATTACK_DAMAGE, 6);
    }

    /**Animations*/
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 20;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        if (this.punchingAnimationTimeout <= 0 && isPunching()) {
            this.punchingAnimationTimeout = 20;
            setPunching(false);
            this.punchAnimationState.start(this.tickCount);
        } else {
            --this.punchingAnimationTimeout;
        }
        if (this.spittingAnimationTimeout <= 0 && isSpitting()) {
            this.spittingAnimationTimeout = 30;
            setSpitting(false);
            this.spitAnimationState.start(this.tickCount);
        } else {
            --this.spittingAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
        //this.level().getServer().sendSystemMessage(Component.literal((Boolean.toString(isSpitting()))));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 35.0f));

        //this.goalSelector.addGoal(2, new CheeseMeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.25, 100, 30.0F));

        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
    }

    /**boss bar event*/
    @Override
    protected void customServerAiStep() {
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    /**melee attack*/
    public void setPunching(boolean punching) {
        this.punching = punching;
    }
    public boolean isPunching() {
        return this.punching;
    }

    /**ranged attack*/
    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
//        Random random = new Random();
//        if (random.nextInt(2) == 1) {
//            // add the cheese spike
//        } else {
//            setSpitting(true);
//            spit(target);
//        }
        //spit(target);
    }

    public void setSpitting(boolean spitting) {
        this.spitting = spitting;
    }
    public boolean isSpitting() {
        return this.spitting;
    }

    private void spit(LivingEntity target) {
        CheeseSpit cheeseSpit = CheeseSpit.fromLevelAndEntity(this.level(), this);
        double d = target.getX() - this.getX();
        double e = target.getY(0.3333333333333333) - cheeseSpit.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f) * 0.20000000298023224;
        cheeseSpit.shoot(d, e + g, f, 1.5F, 10.0F);
        if (!this.isSilent()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.LLAMA_SPIT, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

        this.level().addFreshEntity(cheeseSpit);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return NetworkManager.createAddEntityPacket(this, entity);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return CheeseBossAi.makeBrain(this, dynamic);
    }

    @Contract(value="null->false")
    public boolean canTargetEntity(@Nullable Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return false;
        if (this.level() != entity.level()) return false;
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)) return false;
        if (this.isAlliedTo(entity)) return false;
        if (livingEntity.getType() == EntityType.ARMOR_STAND) return false;
        if (livingEntity.getType() == EntityRegistry.CHEESE_BOSS.get()) return false;
        if (livingEntity.isInvulnerable()) return false;
        if (livingEntity.isDeadOrDying()) return false;
        return this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
    }
}
