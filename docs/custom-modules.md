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
Need to return the name of the Module.
:::

::: details **void renderToGui()**
Allow you to render things on the player screen when the module is on the armor.
:::

::: details **void addToTooltips()**
Allow you to add tooltip to the Space Suit Chestplate.
:::


::: details **int renderStackedGui()**
Allow you to render things on the player screen. The height will change depending if others modules render things too. That is why you need to return the height of the thing you render
:::

::: details **List< Item > requires()**
Return a list of Modules that are needed for the module to be used.
:::


