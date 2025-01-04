# Custom Planets

With Project Stellaris, you can add your own planets with a simple datapack !

## Creation of the datapack

First, you'll need to create the base of the datapack. The structure of the datapack should look like this :

```
├─ data
│  └─ [namespace]
│     └─ planets
│         └─ planet1.json
│         └─ planet2.json
└─ pack.mcmeta
```

::: tip
`[namespace]` should be change to the name of your project
:::

**Creation of the first Planet**

This is what the planet file looks like:

```json
{
  "system": "solar_system",
  "level": "namespace:dimension",
  "name": "Your Planet",
  "translatable": "namespace.planet.your_planet",
  "orbit": "minecraft:the_end",
  "oxygen": false,
  "temperature": 462,
  "distanceFromEarth": 0,
  "gravity": 8.87,
  "textures": {
    "planet": "namespace:textures/planet/your_planet.png",
    "planet_bar": "namespace:textures/planet_bar/your_planet_bar.png"
  }
}
```

Now let see what it means 

`system`: the system your planet is in.

`level`: the resourcekey of your planet dimension

`name`: The name of your planet

`translatable`: the translation of the name of your planet

`orbit`: The Resource Key of your planet orbit

::: warning
For the moment, the orbit is not used.
:::

`oxygen`: Do your planet have oxygen ?

`temperature`: The temperature of the planet (in °C)

`distanceFromEarth`: The distance from the earth 

`gravity`: The gravity on your planet (in $m/s^2$)

`textures`: The texture fields contains two informations
- `planet`: The planet texture location
- `planet_bar`: The planet bar texture location (used when the player is in the rocket)

::: info
The location set in `planet`, `planet_bar` are textures located in a resourcepack.
For the `translatable` fields, it"s a field in `assets/[namespace]/lang/en_us.json` in your resourcepack.
:::

## Examples

You can find exemples in the github page of the [Stellaris Project](https://github.com/st0x0ef/stellaris/tree/master/common/src/main/resources/data/stellaris/planets) 
