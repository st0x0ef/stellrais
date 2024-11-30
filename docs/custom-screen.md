# Custom Planets Screen

Now that you have your beautiful planets registered, players need to be able to join them.

## Creation of the Resourcepack

First, you'll need to create the base of the resourcepack. The structure of the resourcepack should look like this :

```
├─ assets
│  └─ [namespace]
│     └─ renderer
│         └─ planet_screen
│             └─ star
│             └─ planet
│             └─ moon
└─ pack.mcmeta
```

::: tip
`[namespace]` should be the same as your datapack
:::

So as you can see, there are three differents folders.

**The star folder**
This folder will contains all of the stars.

```json
{
  "texture": "stellaris:textures/environment/star/sun.png",
  "name": "Sun",
  "x": 300,
  "y": 100,
  "width": 36.0,
  "height": 36.0,
  "orbitColor": "Yellow",
  "translatable": "text.stellaris.planetscreen.sun",
  "id": "stellaris:sun"
}
```
`texture`: The texture location of the star

`name`: The name of the star

`x`: The *X* position on the Planet Selection Menu

`y`: The *Y* position on the Planet Selection Menu

`width`: The width of your star

`height`: The height of your star

`orbitColor`: The color of the rings around the star

`translatable`: The translatable name of your star

`id`: A special id of your star

**The planet folder**
This folder will contains all of the planets. Theses planets will orbit around a star

```json
{
  "texture": "stellaris:textures/environment/solar_system/earth.png",
  "name": "Earth",
  "distance": 100,
  "period": 8000,
  "width": 10.0,
  "height": 10.0,
  "parent": "stellaris:sun",
  "dimensionId": "minecraft:overworld",
  "translatable": "text.stellaris.planetscreen.earth",
  "id": "stellaris:earth"
}
```
`texture`: The texture location of the planet

`name`: The name of the planet

`distance`: The distance from the Star

`period`: The time a planet takes to travel once around the star

`width`: The width of your planet

`height`: The height of your planet

`parent`: The Star of the planet

`dimensionId`: the Resource key of the planet's dimension

`translatable`: The translatable name of your planet

`id`: A special id of your planet

**The moon folder**
This folder contains all of the moons that are orbiting around planets

```json
{
  "texture": "stellaris:textures/environment/solar_system/moon.png",
  "name": "Moon",
  "distance": 30,
  "period": 2000,
  "width": 6.0,
  "height": 6.0,
  "parent": "stellaris:earth",
  "dimensionId": "stellaris:moon",
  "translatable": "text.stellaris.planetscreen.moon",
  "id": "stellaris:moon"
}
```
`texture`: The texture location of the moon

`name`: The name of the moon

`distance`: The distance from the planet

`period`: The time a moon takes to travel once around the planet

`width`: The width of your moon

`height`: The height of your moon

`parent`: The Planet of the moon

`dimensionId`: the Resource key of the moon's dimension

`translatable`: The translatable name of your moon

`id`: A special id of your moon

::: info
For the `translatable` fields, it"s a field in `assets/[namespace]/lang/en_us.json` in your resourcepack.
:::

## Examples

You can find exemples in the github of the [Stellaris Project](https://github.com/st0x0ef/stellaris/tree/master/common/src/main/resources/assets/stellaris/renderer) 
