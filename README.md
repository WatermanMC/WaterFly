# WaterFly

**WaterFly** is a feature-rich and customizable fly plugin built for **PaperMC 1.21+** servers. It provides players with a survival flying experience while maintaining minimal server overhead. Whether for survival perks or administrative utility, **WaterFly** offers good and customizable control over aerial movement in survival or creative mode.

---

## Key Features & Why You Should Download It

* **Advanced Flight Control**: Seamlessly toggle flight mode and customize a lot of setting on configuration file to suit your needs.
* **Minimal Overhead:** Designed to ensure zero impact or lag on server. Perfect for everything from small private SMPs to large-scale public networks.
* **World Restrictions**: Easily blacklist specific worlds where flight should be prohibited, with bypass permissions available for staff or special players.
* **Customize Plugin Messages:** Customize all WaterFly messages completely on `messages.yml`! Uses MiniMessage so you can have gradients, hex, clickable text and more! No more ugly § color codes! MiniMessage formatter: [https://webui.advntr.dev/](https://webui.advntr.dev/)
* **Intuitive Commands:** Everything the player needs is covered by a very small, super easy-to-remember commands.
* **Modern Server Support:** Built specifically for the latest **PaperMC 1.21+** versions and its forks, guaranteeing up-to-date performance and stability.
* **Open Source:** Developed under the **GNU General Public License v3.0**, allowing for transparency and community contributions.

---

## Commands & Usage
**(<> is optional, [] is required):**

| Command                   | Description             | Permission                  |
|:--------------------------|:------------------------|:----------------------------|
| `/fly`                    | Toggle flight abilities | `waterfly.command.fly`      |
| `/flyspeed <1-10>`        | Set your fly speed.     | `waterfly.command.flyspeed` |
| `/waterfly <info/reload>` | WaterFly admin command  | `waterfly.admin`            |

---

## Default Configs
Default configs used on the plugin.

<details>

<summary>config.yml</summary>

```yaml
# WaterFly - Feature rich fly plugin for your server!
# GitHub Source code: https://github.com/WatermanMC/Waterfly
# Support: https://discord.gg/Scgqfm5EU4


# Should the plugin remove player's fly when changing worlds/quit?
# Will reset fly speed too.
# Will cause player to fall when relogging.
remove-fly:
  quit: false
  world-change: false
  affect-creative-spectators: false


# Disable fly in this world
# Bypass permission: waterfly.bypass.world-restriction
disabled-worlds:
  - "example_world"

# Default fly speeds
default-fly-speeds:
  # The speed when you're flying normally without sprinting
  # Vanilla default: 0.1 (Approx. 10.8 blocks per second)
  # Scale: 0.0 to 1.0 (NOTE: Values above 0.8 might cause player lag)
  normal: 0.1

  # The speed when you're flying and sprinting too
  # Vanilla default: 0.2 (Approx. 21.6 blocks per second)
  # Increasing this might trigger your server's anticheat plugins if you have one
  sprint: 0.2
```
</details>

<details>

<summary>messages.yml</summary>

```yaml
# Only uses MiniMessage!
# MiniMessage formatter: https://webui.advntr.dev

prefix: "<aqua><b>WaterFly</aqua></b><white>:</white> <reset>"

pluginReloaded: "<green>Plugin reloaded!"
nopermission: "<red>You don't have permission to do that!"
disabledworld: "<red>Flying is not allowed in this world."
invalidgamemode-for-fly: "<red>This fly feature is not allowed since you're in {gamemode}!"
fly:
  enabled: "<green>Fly enabled!"
  disabled: "<red>Fly disabled."

flyspeed:
  invalidinput: "<red>Invalid speed. Must be between 1 and 10!"
  invalidnumber: "<red>{number} is not a valid number!"
  set: "<green>Fly speed set to {speed}."
  ```
</details>

---

## Links & Additional Information

**Author:** WatermanMC

* **GitHub Repository** (Source Code & Full Documentation): [https://github.com/WatermanMC/WaterFly](https://github.com/WatermanMC/WaterFly)
* **Discord Support:** [https://discord.gg/Scgqfm5EU4](https://discord.gg/Scgqfm5EU4)