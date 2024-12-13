package com.st0x0ef.stellaris.client.renderers.entities.alien;

import com.mojang.blaze3d.vertex.PoseStack;
import com.st0x0ef.stellaris.Stellaris;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.VillagerDataHolderRenderState;
import net.minecraft.client.resources.metadata.animation.VillagerMetaDataSection;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;

import java.io.IOException;
import java.util.Optional;

public class AlienProfessionLayer<S extends LivingEntityRenderState & VillagerDataHolderRenderState, M extends EntityModel<S> & VillagerHeadModel> extends RenderLayer<S, M> {
    private static final Int2ObjectMap<ResourceLocation> LEVEL_LOCATIONS = Util.make(new Int2ObjectOpenHashMap(), (int2ObjectOpenHashMap) -> {
        int2ObjectOpenHashMap.put(1, ResourceLocation.withDefaultNamespace("stone"));
        int2ObjectOpenHashMap.put(2, ResourceLocation.withDefaultNamespace("iron"));
        int2ObjectOpenHashMap.put(3, ResourceLocation.withDefaultNamespace("gold"));
        int2ObjectOpenHashMap.put(4, ResourceLocation.withDefaultNamespace("emerald"));
        int2ObjectOpenHashMap.put(5, ResourceLocation.withDefaultNamespace("diamond"));
    });

    public static final ResourceLocation ALIEN_TEXTURE = ResourceLocation.fromNamespaceAndPath(Stellaris.MODID,"textures/entity/alien/alien.png");

    private final Object2ObjectMap<VillagerType, VillagerMetaDataSection.Hat> typeHatCache = new Object2ObjectOpenHashMap();
    private final Object2ObjectMap<VillagerProfession, VillagerMetaDataSection.Hat> professionHatCache = new Object2ObjectOpenHashMap();
    private final ResourceManager resourceManager;

    public AlienProfessionLayer(RenderLayerParent<S, M> renderer, ResourceManager resourceManager) {
        super(renderer);
        this.resourceManager = resourceManager;
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        if (!renderState.isInvisible) {
            VillagerData villagerData = renderState.getVillagerData();
            VillagerProfession villagerProfession = villagerData.getProfession();
            VillagerMetaDataSection.Hat hat2 = this.getHatData(this.professionHatCache, BuiltInRegistries.VILLAGER_PROFESSION, villagerProfession);
            M entityModel = this.getParentModel();
            entityModel.hatVisible(hat2 == VillagerMetaDataSection.Hat.NONE || hat2 == VillagerMetaDataSection.Hat.PARTIAL);
            renderColoredCutoutModel(entityModel, ALIEN_TEXTURE, poseStack, bufferSource, packedLight, renderState, -1);
            entityModel.hatVisible(true);
            if (villagerProfession != VillagerProfession.NONE && !renderState.isBaby) {
                ResourceLocation resourceLocation2 = this.getResourceLocation(BuiltInRegistries.VILLAGER_PROFESSION.getKey(villagerProfession));
                renderColoredCutoutModel(entityModel, resourceLocation2, poseStack, bufferSource, packedLight, renderState, -1);
                if (villagerProfession != VillagerProfession.NITWIT) {
                    ResourceLocation resourceLocation3 = this.getResourceLocation(LEVEL_LOCATIONS.get(Mth.clamp(villagerData.getLevel(), 1, LEVEL_LOCATIONS.size())));
                    renderColoredCutoutModel(entityModel, resourceLocation3, poseStack, bufferSource, packedLight, renderState, -1);
                }
            }

        }
    }

    private ResourceLocation getResourceLocation(ResourceLocation location) {
        return location.withPath((string2) -> "textures/entity/alien/" + string2 + ".png");
    }

    public <K> VillagerMetaDataSection.Hat getHatData(Object2ObjectMap<K, VillagerMetaDataSection.Hat> cache, DefaultedRegistry<K> villagerTypeRegistry, K key) {
        return cache.computeIfAbsent(key, (object2) -> this.resourceManager.getResource(this.getResourceLocation(villagerTypeRegistry.getKey(key))).flatMap((resource) -> {
            try {
                return resource.metadata().getSection(VillagerMetaDataSection.SERIALIZER).map(VillagerMetaDataSection::getHat);
            } catch (IOException var2) {
                return Optional.empty();
            }
        }).orElse(VillagerMetaDataSection.Hat.NONE));
    }
}
