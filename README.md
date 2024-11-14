# Interactive Shape 




## Setup Instructions

To run this project, ensure that your environment is set up with the following versions:

- **Gradle Version**: 8.9
- **Gradle Plugin Version**: 8.7.0
- **Java Version**: 21
- **Android Version**: Android Studio Ladybug | 2024.2.1


To sync the project and build it, follow the steps below:

1. Clone the repository:
    ```bash
    git clone https://github.com/Raghav-Hestabit/IntractiveShape.git
    ```

2. Open the project in Android Studio.

3. Sync the Gradle files by selecting **Sync Now** in the notification bar.

4. Build and run the project.

---

## Architecture Explanation

This project follows the **MVVM (Model-View-ViewModel)** architecture pattern, which helps in separating the UI (View) from the business logic (Model) and intermediate handling (ViewModel). **Hilt** is used for Dependency Injection (DI) to manage the app's dependencies efficiently.

### Layers Overview:

- **Model**: Contains the data and logic for managing shapes, their properties, and behaviors.
- **View**: Composed of the UI elements rendered using Jetpack Compose.
- **ViewModel**: Handles UI-related data, business logic, and communication with the model. It provides data to the view and updates it as necessary.

---

## Implemented Features List

- **Shape Creation**: Generate random shapes (circle or rectangle) with unique colors and initial positions.
- **Zoom and Scale**: Implemented pinch-to-zoom functionality to scale shapes within a specified range (0.5x to 2.0x).
- **Rotation**: Shapes can be rotated based on user interaction with touch gestures.
- **Drag and Move**: Shapes can be moved across the screen via drag gestures.
- **Shape Removal**: Shapes can be removed via a double-tap gesture.
- **Multi-Touch Gestures**: The application supports multi-touch gestures for zooming, rotation, and movement of shapes.

---

## Performance Considerations

- The app has been optimized to maintain **smooth performance** during shape interactions (zoom, drag, and rotate) to provide a fluid user experience at 60 FPS.
- The gestures are handled with care to ensure the app remains responsive even with multiple shapes on the screen.

---

## Known Limitations

- **Zooming Issues**: When the shape is zoomed out to the minimum scale, it becomes more complicated to zoom in again. This is an issue with the pinch-to-zoom behavior when the scale reaches its minimum value.
- **Scrolling Behavior**: When zoomed out to a small scale, the scrolling behavior of the shape changes unexpectedly, which might impact the user experience.
