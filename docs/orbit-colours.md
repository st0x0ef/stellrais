# Orbit Colors
When creating your orbit, you have the ability to choose from multiple different colors. For your convenience, we have created colors for you that you can input as text. Below are those colours

## Default Colors
- Default: <span><code style="background-color: #FFFFFF;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Black: <span><code style="background-color: #000000;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Red: <span><code style="background-color: #FF0000;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Green: <span><code style="background-color: #008000;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Blue: <span><code style="background-color: #0000FF;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Yellow: <span><code style="background-color: #FFFF00;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Cyan: <span><code style="background-color: #00FFFF;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Magenta: <span><code style="background-color: #FF00FF;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Gray: <span><code style="background-color: #808080;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Maroon: <span><code style="background-color: #800000;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Olive: <span><code style="background-color: #808000;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Purple: <span><code style="background-color: #800080;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Teal: <span><code style="background-color: #008080;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Navy: <span><code style="background-color: #000080;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Orange: <span><code style="background-color: #FFA500;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Brown: <span><code style="background-color: #A52A2A;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Lime: <span><code style="background-color: #00FF00;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Pink: <span><code style="background-color: #FFC0CB;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Coral: <span><code style="background-color: #FF7F50;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Gold: <span><code style="background-color: #FFD700;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Silver: <span><code style="background-color: #C0C0C0;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Beige: <span><code style="background-color: #F5F5DC;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Lavender: <span><code style="background-color: #E6E6FA;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Turquoise: <span><code style="background-color: #40E0D0;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Salmon: <span><code style="background-color: #FA8072;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Khaki: <span><code style="background-color: #F0E68C;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Dark Red: <span><code style="background-color: #8B0000;">‎ ‎ ‎ ‎ ‎ ‎ ‎ </code></span>
- Rainbow: Rainbow will generate a random colour to use for orbit each time the game is launched.

## Custom Colors
Optionally, you have the ability to use a custom color of your own using a [hex-based color picker](https://www.google.com/search?q=hex+color+picker). Below is an example of how your color should be inserted into your json file.

```json
{
  "texture": "[namespace]:textures/environment/star/[starName].png",
  "name": "[starName]",
  "x": 300,
  "y": 100,
  "width": 36.0,
  "height": 36.0,
  "orbitColor": "#32A852",
  "translatable": "text.[namespace].planetscreen.[starName]",
  "id": "[namespace]:[starName]"
}
```