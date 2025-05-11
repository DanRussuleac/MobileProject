# FitnessApp

*A full‑featured fitness tracker built with **Java** in **Android Studio***.

---

## ✨ Key Features

| Module                     | What it does                                                                                                                                             |
| -------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Dashboard**              | Aggregates your daily metrics – steps, active minutes, workouts accomplished and goal progress – in one glance.                                          |
| **Automatic Step Counter** | A foreground `StepCounterService` leverages the `Sensor.TYPE_STEP_COUNTER` hardware sensor to log steps in real‑time, even when the app is backgrounded. |
| **Workout Log**            | Create, edit and delete strength‑ or cardio‑based workouts. Workouts are persisted locally via Room and surfaced in a recycler‑view list.                |
| **Goals & Achievements**   | Define daily or weekly step / workout goals and track progress; see a history of completed goals for motivation.                                         |
| **Health Score**           | A lightweight algorithm combines steps, distance and completed workouts into a single “health score” to visualise overall activity.                      |
| **Local Persistence**      | All user, workout and goal data are stored offline using **Room** with type‑safe DAOs.                                                                   |
| **Modern Android Stack**   | AndroidX, Material 3 components, Kotlin‑DSL Gradle build, vector assets, responsive ConstraintLayout‑based UI.                                           |

---

## 🏗️ Project Structure

```
FitnessApp/
 ├─ app/
 │   ├─ src/
 │   │   ├─ main/
 │   │   │   ├─ java/com/example/fitnessapp/
 │   │   │   │   ├─ database/         # Room database, entities & DAOs
 │   │   │   │   ├─ service/          # Foreground step‑count service
 │   │   │   │   ├─ ui/               # Activities & adapters
 │   │   │   │   └─ MainActivity.java # App entry point
 │   │   │   ├─ res/                  # Layouts, drawables, menus
 │   │   │   └─ AndroidManifest.xml
 │   └─ build.gradle.kts
 └─ build.gradle.kts
```

> **Tip:** Each feature lives in its own concise activity/adapters package. The codebase purposely avoids fragments to stay beginner‑friendly.

---

## ⚙️ Tech Stack

* **Java 17** (source/target 17)
* **Compile SDK 34 · Min SDK 24 · Target SDK 33**
* **Room 2.6** for persistence
* **AndroidX AppCompat 1.6**, **Material 1.10**
* **ConstraintLayout 2.1**
* Gradle Kotlin DSL

---

## 🚀 Getting Started

1. **Clone**

   ```bash
   git clone https://github.com/<you>/FitnessApp.git
   ```

2. **Open in Android Studio**

   * *File ▸ Open* → select the *FitnessApp* folder
   * Let Gradle sync and download dependencies.

3. **Run**

   * Plug in an Android device **with a step‑counter sensor** *or* create an emulator image based on *Pixel 3+ / API 30+* with **“Hardware – Step counter”** enabled.
   * Click ▶︎ **Run**.

4. **Grant permissions**

   On first launch the app requests **ACTIVITY\_RECOGNITION** permission (API 29+) so step counting works in Android 10+.

---

## 📝 Usage Guide

| Screen           | Actions                                                                                    |
| ---------------- | ------------------------------------------------------------------------------------------ |
| **Login**        | Enter a username – data are stored locally, no backend required.                           |
| **Dashboard**    | Real‑time step tally, today’s workouts, goal status. Tap cards to drill into detail pages. |
| **+ Workout**    | Add a workout with name, type, sets/reps or distance.                                      |
| **Goals**        | Set daily/weekly step or workout count targets; view achievements.                         |
| **Health Score** | See an overall score and quick suggestions to improve it.                                  |

---

## 🛠️ Architecture

The project follows a **thin‑activity / Repository** pattern:

```
Activity  →  ViewModel  →  Repository  →  Room DAO  →  SQLite
```

* **Activities/Adapters** only handle UI & user interaction.
* **Repositories** (in `database/`) abstract data sources.
* **Room** provides compile‑time checked SQL and observable queries.

Step data are broadcast from `StepCounterService` into the dashboard using `LocalBroadcastManager`.

---

## 🧪 Testing

*Unit tests*: `ExampleUnitTest.java` demonstrates JUnit 4 tests for Room DAOs.
*Instrumentation tests*: `ExampleInstrumentedTest.java` validates UI flows with Espresso.

---

## 📸 Screenshots

Add screenshots to `/docs/images` and link them here:

```
| Dashboard | Workout Log | Goals |
|-----------|-------------|-------|
| ![dashboard](docs/images/dashboard.png) | ![workout](docs/images/workout.png) | ![goals](docs/images/goals.png) |
```

---

## 🤝 Contributing

Pull requests are welcome!
Please open an issue first to discuss major changes.

1. Fork the repo & create your branch
2. Commit your changes
3. Push to the branch and open a PR

---


