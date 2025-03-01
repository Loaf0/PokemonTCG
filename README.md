# Pokemon TCG
Version : 1.0
<br>
This is a text based Pokemon TCG simulator for CSCI-3327-001 - Probability and Applied Statistics At Stockton.

# Features
- Battle Logs History saved to computer
- Playable against Other Players Locally
- Monte Carlo Simulations for finding Mulligan Chances
- Deck Builder
  - Use prebuilt decks or create your own!
- Modding Support
  - Any Java Classes added to PokemonCards Or TrainerCard Packages will be added to the deck builders list of usable cards
  - Package Locations : PokemonTCG/Cards/PokemonCards  or PokemonTCG/Cards/TrainerCards
  - **WARNING** - _Do not install mods from untrusted sources_
# Cards Added

## Energy Card Types And Codes :
| Type      | Code  |
|-----------|-------|
| Grass     | GRAS  |
| Fire      | FIRE  |
| Water     | WATR  |
| Lightning | LGTN  |
| Psychic   | PSYC  |
| Fighting  | FGTG  |
| Darkness  | DARK  |
| Metal     | METL  |
| Colorless | COLR  |
| Fairy     | FAIR  |
| Dragon    | DRGN  |


## Trainers

| Trainer              | Effects                                                                                      |
|----------------------|----------------------------------------------------------------------------------------------|
| Bill                 | Draw 2 Cards                                                                                 |
| Professor's Research | Discard your Hand and Draw 7 Cards                                                           |
| Cynthia              | Shuffle your hand into your deck. Then, Draw 6 Cards                                         |
| Energy Search        | Draw the first energy card found in your deck. Shuffle Deck                                  |
| PokeDex              | Look at the top 3 cards of your deck.                                                        |
| Pokemon Catcher      | Flip a coin. If heads, switch 1 of your opponent's Benched Pokemon with their Active Pokemon |
| Rare Candy           | Active Pokemon Gains 30 HP _(Temporary effect until Evolution is added)_                     |
| Switch               | Switch your Active with 1 of your benched Pokemon                                            |

## Pokemon

### Arcanine

| Stats         | Value    |
|---------------|----------|
| Type          | Fire     |
| Health        | 130      |
| Resistance    | None     |
| Weakness      | Water    |
| Retreat Costs | 3 Energy |

| Moves     | Description                                                                                                 |
|-----------|-------------------------------------------------------------------------------------------------------------|
| Crunch    | Deal 30 Damage. Flip a coin. If heads discard an energy from your opponents active pokemon Req [FIRE, COLR] |
| Fire Mane | Deal 120 Damage. Req [FIRE, FIRE, COLR]                                                                     |

### Cradily

| Stats         | Value    |
|---------------|----------|
| Type          | Grass    |
| Health        | 120      |
| Resistance    | Water    |
| Weakness      | Fire     |
| Retreat Costs | 2 Energy |

| Moves        | Description                                                              |
|--------------|--------------------------------------------------------------------------|
| Spiral Drain | Deal 60 Damage. Heal 20 damage from this Pokemon. Req [GRAS, COLR, COLR] |

### Bruxish

| Stats         | Value     |
|---------------|-----------|
| Type          | Water     |
| Health        | 110       |
| Resistance    | None      |
| Weakness      | Lightning |
| Retreat Costs | 1 Energy  |

| Moves | Description                             |
|-------|-----------------------------------------|
| Bite  | Deal 20 Damage. Req [COLR]              |
| Surf  | Deal 110 Damage. Req [WATR, WATR, COLR] |

### Duraludon

| Stats         | Value    |
|---------------|----------|
| Type          | Metal    |
| Health        | 130      |
| Resistance    | Grass    |
| Weakness      | Fire     |
| Retreat Costs | 2 Energy |

| Moves         | Description                                                                                      |
|---------------|--------------------------------------------------------------------------------------------------|
| Hammer In     | Deal 30 Damage. Req [METL]                                                                       |
| Raging Hammer | Deal 80 Damage. This attack deals additional damage for all damage taken. Req [METL, METL, COLR] |


### Heatmor

| Stats         | Value    |
|---------------|----------|
| Type          | Fire     |
| Health        | 110      |
| Resistance    | None     |
| Weakness      | Water    |
| Retreat Costs | 1 Energy |

| Moves         | Description                                                                                                                  |
|---------------|------------------------------------------------------------------------------------------------------------------------------|
| Energy Burner | Deal 30 Damage. This attack deals 30 more damage for each energy attached to your opponents Active Pokemon. Req [FIRE, FIRE] |

### Sawk

| Stats         | Value    |
|---------------|----------|
| Type          | Fighting |
| Health        | 90       |
| Resistance    | None     |
| Weakness      | Psychic  |
| Retreat Costs | 1 Energy |

| Moves         | Description                                                                                            |
|---------------|--------------------------------------------------------------------------------------------------------|
| Sweep the Leg | Deal 30 Damage. Flip a coin. If heads discard an energy from your opponents active Pokemon. Req [FGTG] |

### Throh

| Stats         | Value    |
|---------------|----------|
| Type          | Fighting |
| Health        | 100      |
| Resistance    | None     |
| Weakness      | Psychic  |
| Retreat Costs | 3 Energy |

| Moves            | Description                                                                                                             |
|------------------|-------------------------------------------------------------------------------------------------------------------------|
| Freestyle Strike | Deal 30x Damage. Flip 2 coins. This attack does 30 damage times the number of heads. Req [FGTG, COLR]                   |
| Shoulder Throw   | Deal 80 Damage. Does 80 damage minus 20 for each energy in the defending Pokemon's retreat cost. Req [FGTG, COLR, COLR] |


### Mr.Mime
| Stats         | Value    |
|---------------|----------|
| Type          | Psychic  |
| Health        | 80       |
| Resistance    | None     |
| Weakness      | Psychic  |
| Retreat Costs | 1 Energy |

| Moves    | Description                                                                        |
|----------|------------------------------------------------------------------------------------|
| Juggling | Flip 4 coins. This card does 10 damage times the number of heads. Req [PSYC, COLR] |

### Voltorb
| Stats         | Value     |
|---------------|-----------|
| Type          | Lightning |
| Health        | 90        |
| Resistance    | None      |
| Weakness      | Fighting  |
| Retreat Costs | 1 Energy  |

| Moves      | Description                                                                              |
|------------|------------------------------------------------------------------------------------------|
| Sonic Boom | Deal 40 Damage. This attack damage isn't affected by weakness or resistances. Req [COLR] |
| Explosion  | Deal 120 Damage. This attack deals 90 damage to itself. Req [COLR]                       |