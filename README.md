![Dark_mode](https://github.com/user-attachments/assets/6904df79-7ea2-4517-8cf2-77f5caa3cc1b)# MangaFace App

MangaFace App is a multi-functional mobile application that allows users to authenticate via email/password or Google Sign-In, view a dashboard of manga content from an external API, and utilize a camera-based feature with ML Kit for real-time face detection. The app also supports dark and light themes.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Configuration](#configuration)
- [API Details](#api-details)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Overview

**MangaFace App** is designed to merge entertainment with utility:
- **Authentication:** Users can sign up or log in using email, password, mobile number, and name or simply use their Google account.
- **Dashboard:** Displays a list of manga fetched from an external API. Tapping on any manga provides detailed information.
- **Face Detection:** The second bottom navigation uses Android's camera2 API in tandem with ML Kit to perform face detection. It shows a red indicator when no face is detected and green when a face is detected.
- **Signout:** The third bottom navigation contains a signout button to safely log the user out.
- **Themes:** Supports both dark and light themes, adapting to user preference or system settings.

---

## Features

- **Authentication:**
  - **Email & Password Sign-Up/Sign-In:** Allows new users to create an account using their email, password, mobile number, and name.
  - **Google Sign-In:** Quick login using a Google account.
- **Manga Dashboard:**
  - Displays a list of manga items retrieved from an API.
  - Tapping an item navigates to a detailed view with more information about the manga.
- **Face Detection:**
  - Uses Camera2 API with ML Kit for real-time face detection.
  - Red indicator when no face is detected.
  - Green indicator when a face is successfully detected.
- **User Session Management:**
  - Sign out option available from the bottom navigation.
- **Theming:**
  - Built-in dark and light theme support for enhanced user experience in different environments.

---

## Tech Stack

- **Programming Language:** Kotlin (for Android)
- **UI Framework:** Jetpack Compose
- **Authentication:** Firebase Authentication (for email/password and Google Sign-In)
- **Networking:** Retrofit/OkHttp (for manga API integration)
- **Image Loading:** Coil or Glide
- **Face Detection:** Camera2 API combined with ML Kit Face Detection
- **Theming:** Material Design Components for dark and light theme support


## Screenshots

### Dark Mode Screenshots
  ![Dark_mode](Screenshots/Dark_mode.png)

  
### Light Mode Screenshots
  ![Light Mode](Screenshots/Light_mode.jpg)


## Videos
   ### Dark Mode Video
   [![Night Dark Mode](Play)](https://github.com/anandkumar9682/Zenithra-App/blob/main/Videos/Night_Dark_mode.mp4?raw=true)

   ### Light Mode Video
   ![Light_mode] (Play)](https://github.com/anandkumar9682/Zenithra-App/blob/main/Videos/Day_Light_mode.mp4?raw=true)


## 📁 Photo and Video on Google Drive

You can also access all project-related media and APKs via Google Drive:

- 🔗 **All in One Drive Folder**  
  [Photos, Videos, APKs](https://drive.google.com/drive/folders/1soZs0psxfll7TnwLifY1cgw4F9yLiOOO?usp=sharing)

- 📱 **APK Files Only**  
  [Download APKs](https://drive.google.com/drive/folders/1_wC_cGxc2K9ouwtq4YOG1hdJp98kwQ0Q?usp=sharing)

- 🖼️ **Screenshots / Images**  
  [View Photos](https://drive.google.com/drive/folders/1Zit2GFDKZkLlvT7x3fkJvz-zg_APGOkL?usp=sharing)

- 🎥 **Demo Videos**  
  [Watch Videos](https://drive.google.com/drive/folders/1i8HS4A2EK_Z4ITIVkxzkEn_hajbdxP5z?usp=sharing)

