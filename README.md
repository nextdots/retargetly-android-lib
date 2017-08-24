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

### Custom Event

#### Custom event without response

```java
RetargetlyUtils.callCustomEvent(Application application, String value, String uid, int pid);
```
#### Custom event with response

```java
RetargetlyUtils.callCustomEvent(Application application, String value, String uid, int pid, CustomEventListener customEventListener);
```

#### response

```java
public interface CustomEventListener {
    void customEventSuccess();
    void customEventFailure(String msg);
}
```

## Custom Events Example

#### Custom event without response

```java

String uid = "TESTUID15654";
int pid    = 123456;

RetargetlyUtils.callCustomEvent(getApplication(),"Custom Event",uid,pid);

```

#### Custom event with response

```java
public class MainActivity extends AppCompatActivity implements CustomEventListener {

    String uid = "TESTUID15654";
    int pid    = 123456;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetargetlyUtils.callCustomEvent(getApplication(),"Custom Event", uid, pid,this); 
    }
    
    @Override
    public void customEventSuccess() {
        Toast.makeText(getApplication(),"Custom event send",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void customEventFailure(String msg) {
        Toast.makeText(getApplication(),msg,Toast.LENGTH_SHORT).show();
    }
}
```
