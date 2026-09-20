# KalistenikaMobile

## Requirements
- JDK 21

## Build
- Open project in Android Studio
- View -> Tool Windows -> Build Variants
- Select "withoutFirebaseDebug"
- Run project

## Technologies

Kotlin, Jetpack Compose, Coroutines, Retrofit, Firebase, Room Database, Hilt, JUnit, Navigation 3

## Description

The purpose of the application is to make it easier for users to create and perform workouts. The app allows users to create workouts and exercises by setting specific rules such as rest time between sets, number of repetitions, the position of the exercise in the workout, etc. Once the workout is started, audio signals will play at certain intervals to indicate the start or end of a rest period. This is especially useful in workouts with a large variety of exercises, where precisely timing the end of rest periods between sets can become difficult.

## ScreenShots

- Main Screen - contains a list of workout names. Clicking on a workout takes the user to the Exercises screen. The button in the bottom-right corner opens a dialog.

<img width="439" height="933" alt="image" src="https://github.com/user-attachments/assets/6349f45e-fa7a-45d6-87d4-3a8acc92447c" />

- Create Training Dialog

<img width="440" height="934" alt="image" src="https://github.com/user-attachments/assets/86221005-6448-43c4-9f9a-8c79952b0411" />

- Exercise Screen - At the very top of the workout screen, there are three buttons that allow you to move to the previous or next workout. This can also be done by swiping the screen up or down. Below, there is information about the exercise. Next to the exercise name, there is a button that, when pressed, displays information such as the exercise description, what is needed to perform it, which muscles it targets, etc. At the center of the screen contains a progress indicator showing the current exercise number within the workout. In the bottom-right corner of the screen, there is a button that takes the user to the Add Exercise screen.

<img width="453" height="959" alt="image" src="https://github.com/user-attachments/assets/70dd7bda-2734-43ff-85d8-880e62150a9d" />

- Create Exercise Screen - Allows the user to choose between two types of exercises:
  - Time-based exercises – The user sets the duration of the exercise and the rest time between sets.
  - Repetition-based exercises – The user sets the number of repetitions for the exercise and can choose whether the workout timer should pause while performing the repetitions.

<img width="454" height="959" alt="image" src="https://github.com/user-attachments/assets/a951f218-ef23-4c2b-8005-9fccf0612a24" />

- Edit Exercise Screen - allows the user to modify the settings of a specific exercise and change its position within the workout.

<img width="453" height="958" alt="image" src="https://github.com/user-attachments/assets/27cabb97-7c9a-4515-bb1b-5fa645f1732a" />

- Training History Screen - contains a custom-built calendar implemented without using a calendar widget.

<img width="453" height="959" alt="image" src="https://github.com/user-attachments/assets/2a2a0f05-ed69-4bc8-b5f5-9e5604ecd4f5" />

- Training Details History Screen - clicking on a date in the calendar takes the user to a detailed screen showing which exercises were performed on that day. The user can add these exercises by clicking the black plus icon on the exercise screen.

<img width="454" height="957" alt="image" src="https://github.com/user-attachments/assets/cdbd379d-6de9-4786-9cd2-cd09efa4a121" />

## planned features

- A user profile that will contain a brief summary of the workout history and the exercises performed, based on the calendar
