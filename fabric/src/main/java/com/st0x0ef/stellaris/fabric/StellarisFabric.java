package com.st0x0ef.stellaris.fabric;

import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.registry.BiomeModificationsRegistry;
import com.st0x0ef.stellaris.common.registry.CreativeTabsRegistry;
import com.st0x0ef.stellaris.common.registry.EntityRegistry;
import com.st0x0ef.stellaris.common.registry.ItemsRegistry;
import com.st0x0ef.stellaris.fabric.systems.SystemsFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class StellarisFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Stellaris.init();
        BiomeModificationsRegistry.register();
        onAddReloadListener();
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(Stellaris::onDatapackSyncEvent);
        EntityRegistry.registerAttributes((type, builder) -> FabricDefaultAttributeRegistry.register(type.get(), builder.get()));
        SystemsFabric.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeTabsRegistry.STELLARIS_TAB.getKey()).register(itemGroup -> {
            for (ItemStack stack : ItemsRegistry.fullItemsToAdd()) {
                itemGroup.accept(stack);
            }
        });

    }

    public static void onAddReloadListener() {
        Stellaris.onAddReloadListenerEvent((id, listener) -> ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return id;
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, Executor backgroundExecutor, Executor gameExecutor) {
                return listener.reload(barrier, manager, backgroundExecutor, gameExecutor);
            }
        }));
    }
}