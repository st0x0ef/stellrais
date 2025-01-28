# Custom Screen Files

Now that you have your beautiful planets registered, players need to be able to join them. In this part of the tutorial, you will learn how to add your custom planet to our screens interface so players can access them!

## Creating the Resource Pack

First, you'll need to create the base file structure for the Resource Pack. Your root directory for this should be different than the one you were using in the last step. The structure of the pack should look something like this:

```
├─ assets
│  └─ [namespace]
│     └─ renderer
│         └─ planet_screen
│             └─ star
│             └─ planet
│             └─ moon
│     └─ textures
│         └─ planet
│         └─ planet_bar
│         └─ environment
│            └─ [systemType]
│     └─ lang
└─ pack.mcmeta
```

::: tip
`[namespace]` and `[systemType]` should be the same names that you picked in the last step.
:::

### The pack.mcmeta File
Ah yes! Our good ol' friend from the previous step, the pack.mcmeta file. You will need to create another one of these just like you did in the previous step, but this time we need to add an extra definition to it. Make sure you create it in the root directory like before. This time, we are going to need to define our language file, which we will create later, so Minecraft knows what you want the text to say for each thing you've created thus far. Your file should look something like this:

```json
{
  "pack": {
    "pack_format": 34,
    "description": "Tutorial Resource Pack"
  },
  "language": {
    "en_us": {
      "name": "[namespace] Language Pack for en_us",
      "region": "US",
      "bidirectional": false
    }
  }
}
```

Aforementioned above, there are three different folders that you will create inside of the `planet_screen` directory.

## The Star Folder

Inside of this folder, you will be able to place a star of your choice. Everything in your solar system will revolve around this star. As of right now, you can only have one star in your system. We recommend placing your star in the centre of the screen, which is something we have done for you in the below json, however, you are free to change it to whatever you would like!

```json
{
  "texture": "[namespace]:textures/environment/star/[starName].png",
  "name": "[starName]",
  "x": 300,
  "y": 100,
  "width": 36.0,
  "height": 36.0,
  "orbitColor": "Yellow",
  "translatable": "text.[namespace].planetscreen.[starName]",
  "id": "[namespace]:[starName]"
}
```

`texture`: The path to the texture of the star.

`name`: The name of the star.

`x`: The *X* position on the Planet Selection Menu.

`y`: The *Y* position on the Planet Selection Menu.

`width`: The width of the star (in pixels).

`height`: The height of the star (in pixels).

`orbitColor`: The color of the rings around the star. You can find more information at [Orbit Colors](./orbit-colours.md).

`translatable`: The translatable name of the star.

`id`: A special id of the star.

::: info
The planet selection screen is 1600x1200 pixels. This means that your *X* and *Y* positions, as well as the size of your star, are measured in pixels. The star is not a planet that you can actually visit, but what all planets will orbit around. Please ensure you're replacing the bracket tags with the appropriate names.
:::

## The Planet Folder

Inside of this folder, you will be able to put in your planet files so the player client will recogonize them inside of the planet selection menu. This is similar to how you created your star, but you will be able to set the location where your player lands. For each planet that you have created in the previous step, create a json file for it inside of the `planet` folder. This step can take some time and trial and error in figuring out where you want your planets to be.

```json
{
  "texture": "[namespace]:textures/environment/[system]/[planetName].png",
  "name": "[planetName]",
  "distance": 100,
  "period": 8000,
  "width": 10.0,
  "height": 10.0,
  "parent": "[namespace]:[starName]",
  "dimensionId": "minecraft:overworld",
  "translatable": "text.[namespace].planetscreen.[planetName]",
  "id": "[namespace]:earth"
}
```
`texture`: The path to the texture of the planet.

`name`: The name of the planet.

`distance`: The distance the planet is from the Star (in pixels).

`period`: The time a planet takes to travel once around the star (in milliseconds).

`width`: The width of the planet (in pixels).

`height`: The height of the planet (in pixels).

`parent`: The star of the planet.

`dimensionId`: The ResourceKey of the planets dimension.

`translatable`: The translatable name of the planet.

`id`: A special id of the planet.

## The Moon Folder

Inside of this folder, you will be able to put in your moon files so the player client will recogonize them inside of the planet selection menu. This step is similar to how you created your planet, but you will be able to set the parent planet (say that ten times fast) of the moon. For each moon you would like to create for a planet, place it inside of the `moon` folder.

```json
{
  "texture": "[namespace]:textures/environment/[system]/[moonName].png",
  "name": "[moonName]",
  "distance": 30,
  "period": 2000,
  "width": 6.0,
  "height": 6.0,
  "parent": "[namespace]:[planetName]",
  "dimensionId": "stellaris:moon",
  "translatable": "text.[nameSpace].planetscreen.[moonName]",
  "id": "stellaris:moon"
}
```
`texture`: The path to the texture of the moon.

`name`: The name of the moon.

`distance`: The distance from the planet (in pixels).

`period`: The time the moon takes to travel once around the planet (in milliseconds).

`width`: The width of the moon (in pixels).

`height`: The height of the moon (in pixels).

`parent`: The parent planet of the moon.

`dimensionId`: The ResourceKey of the moons dimension.

`translatable`: The translatable name of the moon.

`id`: A special id of the moon.

## The Textures Folder
For each thing that you have created so far, you will need to supply the proper textures for them. This can be done by following the naming scheme that we have provided in the JSON file examples. If you are confused, please resort to our [Github](https://github.com/st0x0ef/stellaris/tree/master/common/src/main/resources/assets/stellaris/renderer) for more examples on what you should be doing. Make sure you are putting the texture files *exactly* where they are listed in the JSON files, or Minecraft will be unable to find them.

## The Language Folder
For each thing you have created so far, you have also supplied a `translatable` tag to that thing. We are now going to give names for each thing so the player client understands what each thing is, instead of it being just a thing. Earlier, we created a path to our language file, and gave it the `LANG_COUNTRY` code of `en_us`. If you are not an american living in the United States, I recommend you change that to your actual country and use your own language for what you will name these things.

In your `assets/[namespace]/lang/` directory, you will create a new file with using the `LANG_COUNTRY` you either put before, or just changed it to in your `pack.mcmeta`, and you will make it a `.json` file type, similar to what you've been using before. In this file, you will now define everything you've put in a `translatable` tag up to this point. We have provided an example for you to follow:

```json
{
  "text.[namespace].planetscreen.[planetName]": "[planetName]",
  etc...
}
```

## Examples

You can find exemples in the github of the [Stellaris Project](https://github.com/st0x0ef/stellaris/tree/master/common/src/main/resources/assets/stellaris/renderer).
