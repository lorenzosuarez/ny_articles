# New York Times Most Popular Articles App 📰

This application offers a sleek and modern experience for exploring the most popular articles from the New York Times, utilizing the "Most Popular" REST API to fetch the most emailed, shared on Facebook, and viewed articles over recent days. Carefully designed with a clean and modular architecture, the app is divided into five main modules: `domain`, `data`, `presentation`, `core`, and `app`, ensuring a clear separation of concerns and optimal scalability.

## Key Features 🌟

- **REST API Consumption:** Uses specific endpoints to retrieve the most popular articles, including those most emailed, shared, and viewed.
  - API call examples:
    - Most emailed articles in the last 7 days: `GET /emailed/7.json?api-key=yourkey`
    - Most shared articles on Facebook in the last day: `GET /shared/1/facebook.json?api-key=yourkey`
    - Most viewed articles in the last 7 days: `GET /viewed/7.json?api-key=yourkey`

- **Technologies and Libraries:** The application is built using:
  - **Ktor** for network communication.
  - **Koin** for dependency injection.
  - **Room** for data persistence.
  - **Coil** for image loading and handling.
  - **MVVM** (Model-View-ViewModel) as the design pattern for a robust and maintainable architecture.
  - **Material 3** and **Jetpack Compose** for a modern and reactive user interface.

## Project Structure 📂

- `domain`: Contains the application's entities and use cases.
- `data`: Manages access to data, both remote and local.
- `presentation`: Includes screens, UI components, and presentation logic.
- `core`: Provides common functionalities and essential services.
- `app`: The main module that assembles and runs the application.

## Getting Started 🚀

To run this application, you will need a valid New York Times API key. You can obtain one by following the instructions at [New York Times Developer](https://developer.nytimes.com/).


| Mode | Video |
|------|-------|
| Dark | [dark_mode.mp4](./demo/dark_mode.mp4) |
| Light | [light_mode.mp4](./demo/light_mode.mp4) |



## Author ✒️

**Lorenzo Suarez**

Developed by Lorenzo Suarez

---

We hope you enjoy exploring and using this app as much as we enjoyed creating it..
