# KalistenikaMobile

## Technologies

Kotlin, Jetpack Compose, Coroutines, Retrofit, Firebase, Room Database

## Description

The purpose of the application is to make it easier for users to create and perform workouts. The app allows users to create workouts and exercises by setting specific rules such as rest time between sets, number of repetitions, the position of the exercise in the workout, etc. Once the workout is started, audio signals will play at certain intervals to indicate the start or end of a rest period. This is especially useful in workouts with a large variety of exercises, where precisely timing the end of rest periods between sets can become difficult.

## ScreenShots

Main Screen

<img width="439" height="933" alt="image" src="https://github.com/user-attachments/assets/6349f45e-fa7a-45d6-87d4-3a8acc92447c" />

Create Training Screen

<img width="440" height="934" alt="image" src="https://github.com/user-attachments/assets/86221005-6448-43c4-9f9a-8c79952b0411" />

At the very top of the workout screen, there are three buttons that allow you to move to the previous or next workout. This can also be done by swiping the screen up or down. Below, there is information about the exercise. Next to the exercise name, there is a button that, when pressed, displays information such as the exercise description, what is needed to perform it, which muscles it targets, etc. At the very bottom of the screen, there is a progress step indicator that shows the current exercise number within the workout.

<img width="453" height="959" alt="image" src="https://github.com/user-attachments/assets/70dd7bda-2734-43ff-85d8-880e62150a9d" />

Create Exercise Screen

<img width="454" height="959" alt="image" src="https://github.com/user-attachments/assets/a951f218-ef23-4c2b-8005-9fccf0612a24" />

Edit Exercise Screen

<img width="453" height="958" alt="image" src="https://github.com/user-attachments/assets/27cabb97-7c9a-4515-bb1b-5fa645f1732a" />

Training History Screen

<img width="453" height="959" alt="image" src="https://github.com/user-attachments/assets/2a2a0f05-ed69-4bc8-b5f5-9e5604ecd4f5" />

Training Details History Screen

<img width="454" height="957" alt="image" src="https://github.com/user-attachments/assets/cdbd379d-6de9-4786-9cd2-cd09efa4a121" />

## planned features

- For each exercise, pressing the question mark will display a description of the exercise, with data retrieved from the API: https://api-ninjas.com/api/exercises

- A calendar that allows users to check their workout history

- A user profile that will contain a brief summary of the workout history and the exercises performed, based on the calendar
