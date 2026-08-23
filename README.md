# Sensible Survival

A collection of survival gameplay tweaks and fixes. Server side — clients don't need the mod,
apart from two cosmetic details noted below.

Formerly known as **Durability Tweaks**. Updating carries the old config file over automatically.

## Features

### Equipment durability

- Armor durability loss chance, cap and multiplier
- Shield durability loss chance, cap and multiplier

### Anvil

- No prior work penalty: repairing an item never makes the next repair more expensive
- No level limit: removes the "Too Expensive!" wall, you just pay the levels you have

### Experience sharing

- Experience picked up is split evenly with nearby players
- Team aware: vanilla scoreboard teams out of the box, [FTB Teams](https://modrinth.com/mod/ftb-teams)
  when it is installed (same team or mutual allies)
- Experience that doesn't divide evenly goes to a random player of the group
- Can also be set to share with every nearby player, regardless of team

### World generation

Both are off by default, and only affect chunks generated after turning them on.

- No surface lava lakes (cave lava is untouched)
- No zombie villages: villages never generate in their abandoned, cobweb filled variant

### Fixes

- No hurt flash when losing max health, for example when unequipping a shield that grants it

## Configuration

Config file is located in `config/sensible_survival.json`.

## Client side notes

Everything is decided by the server, but two visuals live on the client and are only fixed for
players who have the mod installed:

- The hurt flash fix
- The "Too Expensive!" label, which a vanilla client keeps showing over a result it can still take
