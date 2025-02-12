package com.st0x0ef.stellaris.common.events.custom;

import com.st0x0ef.stellaris.client.screens.info.CelestialBody;
import com.st0x0ef.stellaris.client.screens.info.MoonInfo;
import com.st0x0ef.stellaris.client.screens.info.PlanetInfo;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import dev.architectury.networking.NetworkManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PlanetSelectionServerEvents {

    Event<LaunchButtonServerEvent> LAUNCH_BUTTON = EventFactory.createEventResult();


    interface LaunchButtonServerEvent {
        /**
         * Invoked when the player clicked the launch button.
         * This is event is fired server side.
         *
         * @param player The player that clicked the button.
         * @param planet The planet that the player selected.
         * @param rocket The rocket that the player is in.
         * @param context The context of the packet.
         * @return A {@link EventResult}.
         */
        EventResult launchButton(Player player, @Nullable Planet planet, @Nullable Entity rocket, NetworkManager.PacketContext context);
    }

}
