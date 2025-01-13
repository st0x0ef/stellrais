# Custom Planets

With Project Stellaris, you can add your own planets using a datapack! Following this walkthrough will teach you the fundamentals on creating a custom planet for your world.

## Creating the Datapack

First, you'll need to create the base of the datapack. The structure of the datapack should look something like this:

```
├─ data
│  └─ [namespace]
│     └─ planets
│         └─ planet1.json
│         └─ planet2.json
└─ pack.mcmeta
```

::: tip
You should change `[namespace]` to be the name of your project. You can pick any name you like!
:::

## Creating the pack.mcmeta
If you are new to creating Data Packs in Minecraft, then you should follow this step. If you know what you're doing, feel free to skip ahead to the next step.

### Creating the .txt File

To create your first text file, make sure you are on the root directory of your project. In this directory, you should have a folder named `data` and a file named `pack.mcmeta`. To create these two things, you will right click inside of the directory, and you will see an item in the list called `new` or `new text file`. If you see `new`, hover over it and click text file. The text file should have a default name of `New Text Document.txt`. If you are using Microsoft Windows and cannot see file extensions, then you need to enable access to view them. You can do this by going to the top bar, clicking view, and clicking `File Name extensions`, or, hovering over `show`, then clicking `File Name Extensions`. You should rename this file `pack.mcmeta`.

### Modifying pack.mcmeta

Now that you have created the text file, you need to mofify the contents inside of it. To do so, double click the file. If prompted, open the file in a program like `Notepad`, or an IDE like `Visual Studio Code`. Once you have done this, you should modify the contents of the file as shown below:

```json
{
  "pack": {
    "pack_format": 34,
    "description": "[namespace] Data Pack"
  }
}
```
::: warning
The field `pack_format` depends on which version of Minecraft you are using. If you do not know the numerical value of the version of Minecraft you will be using, please visit the [Minecraft Wiki](https://minecraft.fandom.com/wiki/Pack_format). For the purposes of this tutorial, the `pack_format` has already been set for Minecraft version `1.21-1.21.3`.
:::

Now that you have learned how to create these files, please create the rest of the directory on your own, following the format listed above. You do not need to edit any additional files until the next step.

## Creating Your First Planet

Inside of your `planet1.json` file, you can copy and paste the block of code below and change it to the attributes you want your custom planet to have.

```json
{
  "system": "solar_system",
  "level": "[namespace]:dimension",
  "name": "[planetName]",
  "translatable": "[namespace].planet.[planetName]",
  "orbit": "minecraft:the_end",
  "oxygen": false,
  "temperature": 462,
  "distanceFromEarth": 0,
  "gravity": 9.81,
  "textures": {
    "planet": "[namespace]:textures/planet/[planetName].png",
    "planet_bar": "[namespace]:textures/planet_bar/[planetName].png"
  }
}
```

Below is an explanation for each modifier.

`system`: The system your planet is in. Currently, only `solar_system` and `milky_way` are supported.

`level`: The ResourceKey of your planets dimension. Replace `namespace` with the name you chose earlier, and the `dimension` with the basic name of your planet.

`name`: The name of your planet.

`translatable`: The translation of the name of your planet. Fill out the appropriate fields.

`orbit`: The Resource Key of your planets orbit.

::: warning
For the moment, orbit is not used. Please leave it with the default value.
:::

`oxygen`: Input `true` if you would like players to be able to breathe on your planet without a spacesuit. Input `false` to require a spacesuit.

`temperature`: The temperature of the planet (in °C).

`distanceFromEarth`: The Distance from Earth (in km).

`gravity`: The gravational pull of your planet (in $m/s^2$).

`textures`: The texture fields contain two additional values:
- `planet`: The planets texture location.
- `planet_bar`: The planet bar texture location (used when the player is in the rocket).

::: info
The path set in `planet` and `planet_bar` are textures located in the Resource Pack.
The `translatable` attributes are defined in a language file located at `assets/[namespace]/lang/en_us.json` in your Resource Pack. If you are confused, come back to this step later after following along with the next step.
:::

## Examples

You can find exemples in the github page of the [Stellaris Project](https://github.com/st0x0ef/stellaris/tree/master/common/src/main/resources/data/stellaris/planets).
