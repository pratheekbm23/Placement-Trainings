# Hotel Room Booking System (Java, Command Line)

A simple console project that covers:

- **Room allocation** - first vacant room of the requested type; waiting list if none is free
- **Check-in and check-out**
- **Tariff calculation** - nights x rate, 10% discount for 5+ nights, 12% GST
- **Occupancy reports** - occupied / vacant rooms, occupancy %, type-wise count, revenue

## Project structure

| Folder | Purpose |
|---|---|
| `app` | `Main` - menu and user input |
| `model` | `Room`, `Guest`, `Booking`, `User`, `Admin` |
| `service` | `RoomService`, `ReservationService`, `BookingService`, `TariffService` |
| `datastructures` | `WaitingListQueue` - our own linked-list queue |
| `exceptions` | Custom exceptions |
| `util` | `InputHelper`, `OccupancyReport` |

## How a booking flows

`Book Room` -> (room free? **ALLOCATED**, else **WAITING**) -> `Check-in` -> **CHECKED_IN** -> `Check-out` -> bill -> **CHECKED_OUT** -> room freed -> first waiting guest of that type gets it.

## Rooms and rates (per night)

| Type | Rooms | Rate |
|---|---|---|
| Single | 101-104 | Rs.1500 |
| Double | 201-204 | Rs.2500 |
| Suite | 301-302 | Rs.5000 |

## Run

From the project folder:

**Windows (PowerShell / CMD)**
```
javac -d out src\main\java\app\*.java src\main\java\datastructures\*.java src\main\java\exceptions\*.java src\main\java\model\*.java src\main\java\service\*.java src\main\java\util\*.java
java -cp out app.Main
```

**Linux / macOS**
```
javac -d out $(find src -name "*.java")
java -cp out app.Main
```
