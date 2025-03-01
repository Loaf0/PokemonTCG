
# Pokemon TCG
Completion 90%
<br>
This is a text based Pokemon TCG simulator for CSCI-3327-001 - Probability and Applied Statistics At Stockton.

make the game playable against yourself or an ai (not needed to think about outcomes, but uses all options)
Document everything in comments / Java doc / Human instruction manual
List of all things that should be extra credit

# Features
- Battle Logs History saved to computer
- Playable against Players or AI
- Monte Carlo Simulations for finding Mulligan Chances
- Deck Builder
  - Use prebuilt decks or create your own!
  - Save share and reuse deck lists
- Modding Support
  - Any Java Classes added to PokemonCards Or TrainerCard Packages will be added to the deck builders list of usable cards
  - Package Locations : PokemonTCG/Cards/PokemonCards  or PokemonTCG/Cards/TrainerCards
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

## Trainers

| Trainer              | Effects                            |
|----------------------|------------------------------------|
| Bill                 | Draw 2 Cards                       |
| Professor's Research | Discard your Hand and Draw 7 Cards |