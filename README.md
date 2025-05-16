# Durability Tweaks

Changes durability loss on equipment.

Automatically applies limit to all breakable items and enchanted books by default.

## Item component

Enchantment limit is based on a new item component: `durability_tweaks:limit` 

Example command
```
/give @p minecraft:golden_sword[durability_tweaks:limit={"count":3}]
```

## Configuration

- Adjustable default enchantment limit (default: 3)

