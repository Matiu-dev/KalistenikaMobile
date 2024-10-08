# KalistenikaMobile

## Technologies

Kotlin, Jetpack Compose, Coroutines, Retrofit, Firebase, Room Database

## Description

The purpose of the application is to make it easier for users to create and perform workouts. The app allows users to create workouts and exercises by setting specific rules such as rest time between sets, number of repetitions, the position of the exercise in the workout, etc. Once the workout is started, audio signals will play at certain intervals to indicate the start or end of a rest period. This is especially useful in workouts with a large variety of exercises, where precisely timing the end of rest periods between sets can become difficult.

## ScreenShots

Main Screen

![image](https://github.com/user-attachments/assets/1da6915c-5a39-4b27-9a62-79ca701e85c2)

Create Training Screen

![image](https://github.com/user-attachments/assets/08286486-4fab-4e15-94da-4072e21c4937)

At the very top of the workout screen, there are three buttons that allow you to move to the previous or next workout. This can also be done by swiping the screen up or down. Below, there is information about the exercise. Next to the exercise name, there is a button that, when pressed, displays information such as the exercise description, what is needed to perform it, which muscles it targets, etc. At the very bottom of the screen, there is a progress step indicator that shows the current exercise number within the workout.

![image](https://github.com/user-attachments/assets/3ec28b11-b9e2-4009-be48-413c6e32c2b5)

Create Exercise Screen

![image](https://github.com/user-attachments/assets/ee0adc07-d517-4e99-9912-62b134bd5a1c)

Edit Exercise Screen

![image](https://github.com/user-attachments/assets/ddd31899-514f-4d9e-bc53-015af17b74b8)


## planned features

- For each exercise, pressing the question mark will display a description of the exercise, with data retrieved from the API: https://api-ninjas.com/api/exercises

- A calendar that allows users to check their workout history

- A user profile that will contain a brief summary of the workout history and the exercises performed, based on the calendar
