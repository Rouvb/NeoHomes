# NeoHomes
**NeoHomes** is a lightweight, GUI-based homes plugin for Paper servers, allowing players to manage multiple homes.

## Features
- Multiple homes per player
- GUI-based home selection
- SQLite storage
- Permission-based home limits
- Blacklisted worlds support (prevent home creation in specific worlds)
- Blacklisted words filter for home names
- No unnecessary dependencies

## GUI
![gui](https://i.imgur.com/YDyvHGB.png)

## Requirements
- Java 21 or higher
- Paper (or Paper-compatible) server
- No external dependencies required

## Commands
| Command            | Permission         | Description          |
|--------------------|--------------------|----------------------|
| `/sethome <home>`  | `neohomes.sethome` | Set a new home       |
| `/home [home]`     | `neohomes.home`    | Teleport to a home   |
| `/delhome <home>`  | `neohomes.delhome` | Delete a home        |
| `/neohomes reload` | `neohomes.admin`   | Reload configuration |

## Permissions
| Permission                | Description                 |
|---------------------------|-----------------------------|
| `neohomes.sethome`        | Allows setting homes        |
| `neohomes.home`           | Allows teleporting to homes |
| `neohomes.delhome`        | Allows deleting homes       |
| `neohomes.limit.*`        | Removes home limit          |
| `neohomes.limit.<number>` | Limits number of homes      |
| `neohomes.admin`          | Access to admin commands    |

## Credits
- [ACF (Annotation Command Framework)](https://github.com/aikar/commands)
- [ColorParser](https://github.com/milkdrinkers/ColorParser)
- [InvUI](https://github.com/NichtStudioCode/InvUI)