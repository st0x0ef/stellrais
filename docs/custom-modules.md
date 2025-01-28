# Custom Modules

With Project Stellaris, you can add your own modules to the jet suit !

## Creation of the Module Class

First, you'll need to create the module class :

```java
public class TestModule extends Item implements SpaceSuitModule {
    
    public TestModule(Properties properties) {
        super(properties);
    }

    @Override
    public MutableComponent displayName() {
        return null;
    }

    @Override
    public List<Item> requires() {
        return List.of(ItemsRegistry.MODULE_FUEL.get());
    }

    @Override
    public int renderStackedGui(GuiGraphics graphics, DeltaTracker deltaTracker, Player player, ItemStack stack, int y) {
      return sizeOfTheGUI
    }

    @Override
    public void renderToGui(GuiGraphics graphics, DeltaTracker deltaTracker, Player player, ItemStack stack) {
    }

    @Override
    public void addToTooltips(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      tooltipComponents.add(YourComponent)
    }

}
```

## Methods

::: details **MutableComponent displayName()**
Needed to return the name of the Module.
:::

::: details **void renderToGui()**
Allows you to render things on the players screen while the module is on the player.
:::

::: details **void addToTooltips()**
Allows you to add tooltips to the Space Suit Chestplate.
:::

::: details **int renderStackedGui()**
Allows you to render things on the player screen. The height will change depending on whether other modules render things too, hence, the requirement to return the height of what you're rendering.
:::

::: details **List \<Item\> requires()**
Returns a list of Modules that are needed for the module to be used.
:::


