# feature App (renamed from CameraX sample) 

CameraXbasic aims to demonstrate how to use CameraX APIs written in Kotlin.

## Build

To build the app directly from the command line, run:
```sh
./gradlew assembleDebug
```

## Test

Unit testing and instrumented device testing share the same code. To test the app using Robolectric, no device required, run:
```sh
./gradlew test
```

To run the same tests in an Android device connected via ADB, run:
```sh
./gradlew connectedAndroidTest
```

Alternatively, test running configurations can be added to Android Studio for convenience (and a nice UI). To do that:
1. Go to: `Run` > `Edit Configurations` > `Add New Configuration`.
1. For Robolectric select `Android JUnit`, for connected device select `Android Instrumented Tests`.
1. Select `app` module and `one.empty3.cameraxbasic.MainInstrumentedTest` class.
1. Optional: Give the run configuration a name, like `test robolectric` or `test device`

Precision about the code.

Code is too long to post here. I read your remarks and I updated my code.
[https://github.com/manuelddahmen/featureApp/blob/main/app/src/main/java/one/empty3/feature/app/ChooseEffectsActivity.kt][1]

 

       I/System.out: Cick on Effect button
    I/System.out: Effect class           : one.empty3.feature.GradProcess
        In picture             : file:/storage/emulated/0/Android/media/one.empty3.feature.app/data/MyImage_43.jpg
        In picture directory   : /file:/storage/emulated/0/Android/media/one.empty3.feature.app/data
        Out  picture           : /file:/storage/emulated/0/Android/media/one.empty3.feature.app/data/one.empty3.feature.GradProcess-1/MyImage_43.jpg
        Out picture directory  : /file:/storage/emulated/0/Android/media/one.empty3.feature.app/data
    I/System.out: File in doesn't exists, or File out exists
        Precision currentProcessDir : false
        Precision currentProcessFile: false
        Precision currentOutputDir  : false
        Precision currentOutputFile : false

  [1]: https://github.com/manuelddahmen/featureApp/blob/main/app/src/main/java/one/empty3/feature/app/ChooseEffectsActivity.kt
