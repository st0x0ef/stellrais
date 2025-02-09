package com.st0x0ef.stellaris.client.event.custom;

import com.st0x0ef.stellaris.client.screens.info.CelestialBody;
import com.st0x0ef.stellaris.client.screens.info.MoonInfo;
import com.st0x0ef.stellaris.client.screens.info.PlanetInfo;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;

import java.util.List;

public interface PlanetSelectionEvents {

    Event<PostStarPackRegistryEvent> POST_STAR_PACK_REGISTRY = EventFactory.createEventResult();
    Event<PostMoonPackRegistryEvent> POST_MOON_PACK_REGISTRY = EventFactory.createEventResult();
    Event<PostPlanetPackRegistryEvent> POST_PLANET_PACK_REGISTRY = EventFactory.createEventResult();

    interface PostStarPackRegistryEvent {
        /**
         * Invoked when all the stars has been registered.
         * Usefull for adding more stars to the planet selection screen.
         *
         * @param stars The list of all the stars.
         * @return A {@link EventResult} but this events can't be cancelled.
         */
        EventResult starsRegistered(List<CelestialBody> stars);
    }

    interface PostMoonPackRegistryEvent {
        /**
         * Invoked when all the moon has been registered.
         * Usefull for adding more moon to the planet selection screen.
         *
         * @param moonInfos The list of all the moon.
         * @return A {@link EventResult} but this events can't be cancelled.
         */
        EventResult moonRegistered(List<MoonInfo> moonInfos);
    }

    interface PostPlanetPackRegistryEvent {
        /**
         * Invoked when all the planets has been registered.
         * Usefull for adding more planets to the planet selection screen.
         *
         * @param planetInfos The list of all the planets.
         * @return A {@link EventResult} but this events can't be cancelled.
         */
        EventResult moonRegistered(List<PlanetInfo> planetInfos);
    }

}
