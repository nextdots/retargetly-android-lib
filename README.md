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
  compile 'com.github.nextdots:retargetly-android-lib:1.0.0.1'
}
```

# Configuracion

### Step 1

Crea una clase que extienda de Application y en el onCreate añade ``Retargetly.init(this,uid,pid);``

```xml
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Retargetly.init(this,uid,pid);
    }
}
```

### Step 2

En tu archivo maninfest añade el atributo ``android:name=".App"`` en el application

### Example

```xml
<application
  android:name=".App"
  android:allowBackup="true"
  android:icon="@mipmap/ic_launcher"
  android:label="@string/app_name">
```

# Importante

Actualmente la mayorias de las aplicaciones hacen uso de los fragments para consumir menos memoria del sistema creando actividades,
por ende para un 100% de efectividad recomendamos antes del api 26 utilizar getSupportFragmentManager() para la creacion de fragments, 
y mayor que api 26 usar getFragmentManager().


