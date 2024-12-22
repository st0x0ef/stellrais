package com.st0x0ef.stellaris.neoforge.mixin;

import com.st0x0ef.stellaris.common.items.GlobeItem;
import com.st0x0ef.stellaris.common.registry.ItemRendererRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(GlobeItem.class)
public class GlobeItemRendererMixin extends BlockItem {

    public GlobeItemRendererMixin(Block block, Properties properties) {
        super(block, properties);
    }

    @Unique
    public void stellaris$initializeClient(Consumer<IClientItemExtensions> consumer) {

        if(GlobeItemRendererMixin.this.getDefaultInstance().getItem() instanceof GlobeItem item) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return ItemRendererRegistry.GLOBE_ITEM_RENDERER.setTexture(item.getTexture());
                }
            });
        }
    }
}
