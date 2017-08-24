![Retargetly](http://beta.retargetly.com/wp-content/uploads/2015/07/Logo.png)

# Retargetly

Retargetly is a tracking library for Android.

### Prerequisites

```
Android Studio
JDK 
```

# Important

If your application uses fragments for full compatibility with the library, we recommend creating fragments with

### Api >= 26

```java

getFragmentManager()

```

### Api < 26

```java

getSupportFragmentManager()

```

### Installing

To get a Git project into your build:

Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' } 
  }
}
```

Add the dependency

```gradle
dependencies {
  compile 'com.github.User:Repo:Tag'
}
```

## Usage

You must create a class that extends of application and in the oncreate adds the following line

```Retargetly.init(this,uid,pid);```

### Example

```java
public class App extends Application {

    String uid = "TESTUID15654";
    int pid    = 123456;
    
    @Override
    public void onCreate() {
        super.onCreate();
        Retargetly.init(this,uid,pid);
    }
}
```


# Retargetly-android-lib

Libreria diseñada para enviar las estadisticas de cambios de vistas entre actividades y fragmentos.

# Instalacion

### Step 1. 

Añadir el repositorio JitPack en tu archivo build.gradle

```xml
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
### Step 2 

Añadir la dependencia

```xml
dependencies {
  compile 'com.github.nextdots:retargetly-android-lib:1.0.+'
}
```

## Usage

You must create a class that extends of application and in the oncreate adds the following line

```Retargetly.init(this,uid,pid);```

### Example

```java
public class App extends Application {

    String uid = "TESTUID15654";
    int pid    = 123456;
    
    @Override
    public void onCreate() {
        super.onCreate();
        Retargetly.init(this,uid,pid);
    }
}
```

In the maninfest file add the ``android:name=".app"`` attribute in the application

### Example

```xml
<application
  android:name=".App"
  android:allowBackup="true"
  android:icon="@mipmap/ic_launcher"
  android:label="@string/app_name">
```
