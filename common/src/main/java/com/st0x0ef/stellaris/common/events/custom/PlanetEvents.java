package com.st0x0ef.stellaris.common.events.custom;

import com.st0x0ef.stellaris.common.data.planets.Planet;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;

import java.util.List;

public interface PlanetEvents {

    Event<RegisterPlanetEvent> PLANET_REGISTERED = EventFactory.createLoop();

    Event<PostPlanetRegistryEvent> POST_PLANET_REGISTRY = EventFactory.createLoop();

    interface RegisterPlanetEvent {
        /**
         * Invoked when a Planet is registered.
         *
         * @param planet  The planet registered.
         * @return A {@link EventResult} determining the outcome of the event,
         * the execution of Planet registry may be cancelled by the result.
         */
        EventResult planetRegistered(Planet planet);
    }

    interface PostPlanetRegistryEvent {
        /**
         * Invoked when all the planets has been registered.
         * Usefull for adding more planets to the list.
         *
         * @param planets The planets already registered.
         * @param isSyncing If the planets are being synced from the server to the client.
         * @return A {@link EventResult} but this events can't be cancelled.
         */
        EventResult planetRegistered(List<Planet> planets, boolean isSyncing);
    }
}
