# Android Handout

### Module & Gradle
Die wichtigsten Dateien für die Android-Entwicklung sind die build.gradle-Dateien im Modulverzeichnis der Applikation.

Ein Modul ist dabei eine Teilkomponente einer App. Eine App kann dabei aus lediglich einem Modul oder beliebig vielen Modulen bestehen. Ein Beispiel für eine App mit mehreren Modulen ist eine Anwendung die sowohl auf normalen Android-Geräten wie auch auf Smartwatches lauffähig ist. Eine Komponente app und eine andere Komponente _watch_. 

Im Modulverzeichnis wird eine build.gradle-Datei wie im nachfolgenden Beispiel angelegt:
```gradle
apply plugin: 'com.android.application'

android {
    compileSdkVersion 27
    defaultConfig {
        applicationId "com.example.android.app"
        minSdkVersion 16
        targetSdkVersion 27
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:26.1.0'
    implementation 'com.android.support.constraint:constraint-layout:1.0.2'
    implementation 'com.android.support:design:26.1.0'
}
```

Wichtige Inhalte in dieser Datei sind die Parameter _compileSdkVersion_, die festlegen gegen welchen API-Level die App kompiliert wird. Zum Zeitpunkt dieses Handouts (Dezember 2017) ist der aktuellste SDK-Level 27 (Android 8.1 Oreo). Es wird empfohlen immer den aktuellsten SDK-Level zu verwenden. Ab Herbst 2018 erlaubt Google auch nur noch Apps im Google Play Store, die mindestens das SDK-Level des vorherigen Jahres verwenden (ab 2018 also SDL-Level 26/27, ab 2019 SDK-Level 28/29 usw.).

Die _applicationId_ gibt die Package ID der App an. Diese darf auf einem Android-Gerät nur einmalig installiert sein. Es können also nie zwei Apps mit derselben Package ID auf einem Gerät installiert sein. 

_minSdkVersion_ gibt an, welche Android Version auf dem Gerät mindestens installiert sein muss, damit die App ausgeführt werden kann. 

Mit dem Parameter _targetSdkVersion_ wird angegeben welche Features (welches SDK-Level) in der App zur Verfügung stehen. Generell wird auch bei diesem Wert empfohlen, ihn immer auf den aktuellsten SDK-Level zu setzen, aus Kompatibilitätsgründen kann dies aber nicht immer sinnvoll sein. 

Im Abschnitt _dependencies_ werden verwendete Bibliotheken und Abhängigkeiten angegeben, die innerhalb des Moduls verwendet werden. Zunächst wird dabei die Package ID und dann den Namen und die Version der Bibliothek die verwendet werden soll angegeben.

### Activities und Intents
Eine Activity stellt einen Bildschirm dar.

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
 
        TextView helloTextView = (TextView) findViewById(R.id.helloTextView);
        tvTest.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Toast.makeText(MainActivity.this, "Hallo!", 
                  Toast.LENGTH_LONG).show();
              }
          });
        }
    }
}
```

Im obigen Beispiel wird eine neue Activity erzeugt, welche von der Klasse AppCompatActivity erweitert wird. Jeder Bildschirm benötigt eine _onCreate_-Methode, die von der Oberklasse überschrieben werden muss. Wichtig ist der Aufruf der onCreate-Methode der Oberklasse. Dies wird mit dem Aufruf von _super_ erreicht. 

Mittels ``setContentView(resId)`` wird ein XML-Layout als Inhalt des aktuellen Bildschirms geladen (im Beispiel die XML-Datei unter _/res/layout/activity_main.xml_). Innerhalb dieser Klasse stehen nun alle Layoutelemente (Views) zur Verfügung. Auf diese können mittels der Methode ``findViewById(resId)`` zugegriffen werden. Die IDs dieser View werden ebenfalls im XML-Layout festgelegt. 

Bis Android Studio 3.0 sind explizite Casts notwendig, um die Views korrekt zuzuweisen (im obigen Beispiel wird das gefundene Layoutelement explizit als _TextView_ gecastet). 

In der Variable ``helloTextView`` steht nun die entsprechende View zur Verfügung. Es können nun Eigenschaften dieser angepasst werden (beispielsweise der Text innerhalb dieser View), oder Listener hinzugefügt werden. Wir fügen nun einen Listener hinzu, der beim Klick auf die View ausgelöst wird. Innerhalb der _onClick_-Methode können wir nun mittels view auf die TextView zugreifen. Im Beispiel erstellen wir eine Popup-Nachricht (Toast) mit dem Text „Hallo!“.

Im Folgenden findet sich ein Beispiel-XML-Layout. Zunächst wird ein lineares Layout als Root-Element des Layouts festgelegt, da ein Layout immer nur ein einziges Root-Element beinhalten kann (dies gilt auch für andere Layouts wie beispielsweise einer ScrollView). Anschließend wird die Breite und Höhe, sowie die innere Ausrichtung des linearen Layouts festgelegt. Die Ausrichtung (vertical oder horizontal) eines linearen Layouts gibt an, wie die Komponenten innerhalb der View angezeigt werden (über- oder nebeneinander). 

Innerhalb des linearen Layouts wird dann die TextView-Komponenten mit der Id helloTextView und einem vorgegebenen Text festgelegt.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android=http://schemas.android.com/apk/res/android
    xmlns:tools="http://schemas.android.com/tools"    
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
         android:text="Klick mich!"
         android:id="@+id/helloTextView"
         android:layout_width="match_parent"
         android:layout_height="wrap_content" />
</LinearLayout> 
```

### Explizite Intents
Wir könnten innerhalb dieser onClick-Methode beispielsweise auch eine andere Activity zusätzlich öffnen lassen. 

Wir übergeben der startActivity-Methode hierfür einen neuen Intent. Mittels eines Intents können wir innerhalb des Android-Systems beliebig navigieren. Wir können andere Apps, URLs, E-Mails oder auch andere Activities innerhalb derselben App öffnen. Wir unterscheiden zwischen expliziten und impliziten Activities. Der Aufruf einer anderen Activity ist ein expliziter Aufruf:
```java
Intent i = new Intent(MainActivity.this, NeueActivity.class);
i.putString("Titel", "Neuer Titel");
startActivity(i);
```

Wir übergeben dem Intent zusätzlich einen Datensatz. Im Beispiel den String Titel mit dem Inhalt Neuer Titel. 
In der neuen Activity NeueActivity können wir auf die gesendeten Daten innerhalb der onCreate-Methode folgendermaßen zugreifen:
```java
Intent i = getIntent();
String title = i.getStringExtra("Titel");
```

### Implizite Intents
Für einen impliziter Intent müssen wir keine vorgegebene Activity angeben, die ausgeführt werden soll. Das System (bzw. der Nutzer) entscheidet, welche App für die Ausführung des entsprechenden Aufrufs ausgeführt werden soll.

Um beispielsweise eine Website im Browser zu öffnen:
```java
Intent urlIntent = new Intent(Intent.ACTION_VIEW, Url.parseUrl("https://www.th-koeln.de"));
startActivity(urlIntent);
```

Mittels dieser Methode können wir unter anderem auch einen Anruf initiieren:
```java
Intent callIntent = new Intent(Intent.ACTION_CALL); 
callIntent.setData(Uri.parseUrl("tel:022182750"));
startActivity(callIntent);
```

Wichtig ist hierbei die Angabe welche Aktion mit dem Intent ausgeführt werden soll (im Beispiel entsprechend _ACTION_VIEW_  und _ACTION_CALL_). 

### Aktivität 

