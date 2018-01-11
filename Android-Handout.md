# Android Handout

## Module & Gradle
Die wichtigsten Dateien für die Android-Entwicklung sind die build.gradle-Dateien im Modulverzeichnis der Applikation.

Ein Modul ist dabei eine Teilkomponente einer App. Eine App kann dabei aus lediglich einem Modul oder beliebig vielen Modulen bestehen. Ein Beispiel für eine App mit mehreren Modulen ist eine Anwendung die sowohl auf normalen Android-Geräten wie auch auf Smartwatches lauffähig ist. Eine Komponente app und eine andere Komponente _watch_. 

Die Ordnerstruktur ist dabei standardmäßig folgendermaßen (stark vereinfacht):

```
-|
 --| app
    --| build.gradle
    --| src
       --| main
          --| java
             --| com
                --| example
                   --| app
                      --| MainActivity.java
          --| res
             --| drawable
                --| ic_launcher.png
             --| layouts
                --| MainActivity.xml
             --| values
               --| strings.xml
 --| wear
    --| build.gradle
    --| src
       --| main
          ..| java
          --| res
 --| build.gradle
 --| gradle.properties
```

Im Modulverzeichnis (``app/``) wird eine build.gradle-Datei wie im nachfolgenden Beispiel angelegt:
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

## Activities
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

## Activity-Lifecycle
Im Gegensatz zu beispielsweise einem Windows-PC, verwaltet Android standardmäßig alle Prozesse selbst. Das Betriebssystem kann Anwendungen im Hintergrund automatisch schließen und wieder starten. Damit eine App auf derartige Ereignisse reagieren kann und beispielsweise einen Anzeigestatus nach einem Neustart wiederherstellen kann, müssen diese Lebenszyklen innerhalb einer Activity berücksichtig und implementiert werden. 

![Lifecycle](https://raw.githubusercontent.com/MDXDave/UndergroundCityAndroid/master/lifecycle.png?token=AF0UCLzoOmyjOYUSNrgUPS8-AaE44ENBks5aW7a0wA%3D%3D)

Eine Activity wird beim Aufruf mittels _onCreate()_ erzeugt und durch _onStart()_ gestartet (Zustand _Started_). Durch den Aufruf von _onResume()_ befindet sich die Activity im Vordergrund und im Zustand _Running_. Wird eine **transparente** oder nicht-bildschirmfüllende Activity überlagert, wechselt die Activity in den Zustand _Paused_, beim wechsel wird hierbei die Methode _onPause()_ augerufen. Sofern eine **nicht-transparente** oder bildschirmfüllende Activity aufgerufen wird, wird die Activity in den Zustand _Stopped_ versetzt und die Methode _onStop()_ aufgerufen. Wird auf die Activity erneut zugegriffen, wird die Methode _onRestart()_ und _onStart()_ durchgeführt. Die Activity befindet sich nun wieder im Zustand _Started_.

Sowohl im pausierten (_Paused_) als auch im gestoppten (_Stopped_) Zustand, kann die Activivy vom System zerstört werden (_Destroyed_). Dies wird vor Allem durchgeführt, wenn dem System zuwenig Ressourcen zur Verfügung stehen um aktuellere Aufgaben zu erledigen. Sofern die Activity aus dem _Stopped_-Zustand zerstört wird, wird die _onDestroy()_-Methode aufgerufen. Wird die App bzw. Activity nun erneut aufgerufen, wird diese komplett neu erzeugt. 

Möchte man beispielsweise in einer Newsreader-App die Daten des Newsfeed bei jedem Aufruf der entsprechend Aktivität _NewsfeedActivity_ aktualisieren (auch wenn man einen Beitrag gelesen hat und nun mittels Zurück-Taste zum Newsfeed zurückkehrt), führt man die Aktion zum Aktualisieren des Newsfeeds in der _onResume()_-Methode der App aus. Da _onResume()_ **immer** ausgeführt wird, sollten wir die Aktualisierung nicht zusätzlich in der _onCreate()_-Methode aufrufen. Zum Zeitpunkt des Aufrufs sollte das Layout bereits mittels _setContentView()_ gesetzt worden sein, sodass wir in der _onResume()_-Methode darauf zugreifen können. Dynamische Views, die innerhalb der Activity erzeugt werden, könnten jedoch je nach Zeitpunkt des Erstellens noch nicht vorhanden sein!

```java
protected void onResume() {
    super.onResume();
    refreshNewsfeed();
}

protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(...);
}


```


## Intents
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

## Layouts 
Im vorherigen Abschnitt haben wir bereits die Verwendung eines Layouts (_setContentView()_) und ein XML-Layout kennengelernt. Ein XML-Layout darf immer nur aus **einer** Root-View bestehen. In dieser dürfen aber widerrum mehrere Views vorhanden sein. Jeder View **muss** eine Breite (_width_) und eine Höhe (_height_) zugewiesen werden. Diese kann neben einem festen Wert (der in DP angegeben werden sollte, DP = Density-independent Pixels),  entweder _match_parent_ oder _wrap_content_ sein. Ersteres _füllt_ die View bis zur Größe der übergeordneten View, letztes begrenzt die größe der View auf den aktuellen Inhalt.

Die Größe einer View kann auch prozentual angeben werden. Hierfür wird der Wert für Breite oder Höhe auf _0dp_ und der Parameter _layout_weight_ auf einen beliebigen Wert (entsprechend angepasst an die Werte der anderen Views die ebenfalls prozentual angezeigt werden sollen) gesetzt.

Eine der wichtigsten Container-Views ist das _LinearLayout_. In dieser können andere Views platziert werden, die sich _horizontal_ oder _vertikal_ anordnen. Hierfür muss der Parameter _orientation_ gesetzt werden (_horizontal_ oder _vertical_). Daneben existiert auch ein _FrameLayout_ (welches alle Views übereinander platziert), ein _RelativLayout_ (ermöglicht eine relative Positionierung aller Views zueinander) und ein [_ConstraintLayout_](https://developer.android.com/training/constraint-layout/index.html). Mit letzterem kann eine _responsive_ UI ermöglicht werden.  

Alle Views können dabei die verschiedensten Parameter besitzen. Einige Views haben spezifische Parameter wie Textfarbe oder Textausrichtung. Parameter wie _Padding_, _Margin_ oder _Gravitiy_ (Ausrichtung) können aber für alle Views vergeben werden.

Eine Übersicht der möglichen Parameter einer EditText-View können in der [Android Dokumentation](https://developer.android.com/reference/android/widget/EditText.html) gefunden werden.

Neben den Container-Views (oder auch Layouts) gibt es eine große Anzahl Interaktions-Views (auch Widgets genannt). Darunter befinden sich beispielsweise Buttons, Textfelder (_TextView_), Eingabefelder (_EditText_), Bilder (_ImageView_) und viele mehr. 

Ein Layout, mit welchem es möglich ist eine E-Mail zu erstellen und zu senden, könnte beispielsweise folgendermaßen aussehen:

 ![Beispiel-App](https://developer.android.com/images/ui/sample-linearlayout.png) 

Als Root-View wird ein LinearLayout mit _16dp_ Abstand (_Padding_) links und rechts verwendet. Die Ausrichtung ist vertikal, alle untergeordneten Views werden _untereinander_ positioniert. Es folgen drei Eingabefelder (_EditText_), die sich in der Höhe alle an ihren eigenen Inhalt anpassen und die volle Breite des Elternviews (in diesem Fall des LinearLayouts besitzen). Der Parameter _hint_ verweist auf eine String-Ressource, die als Platzhalter-Text verwendet wird (im Beispiel _To_, _Subject_ und _Message_). Das Eingabefeld für die Nachricht wird darüber hinaus mit dem Parameter _layout_weight_ prozentual auf die gesamte Höhe des übergeordneten Layouts (abzüglich der anderen Views) gestreckt. Der Button zum Absenden der Nachricht wird auf _100dp_ Breite festgelegt und mit dem Parameter _gravitiy_ rechts ausgerichtet. Der Text _Send_ wird erneut aus einer String-Ressource bezogen.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingLeft="16dp"
    android:paddingRight="16dp"
    android:orientation="vertical" >
    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/to" />
    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/subject" />
    <EditText
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:gravity="top"
        android:hint="@string/message" />
    <Button
        android:layout_width="100dp"
        android:layout_height="wrap_content"
        android:layout_gravity="right"
        android:text="@string/send" />
</LinearLayout>
```
Beispiel-Code und Bild von [Android Developers, Google Inc., linzenziert unter CC-BY-2.5](https://developer.android.com/guide/topics/ui/layout/linear.html)

## Ressourcen

Alle Inhalte die innerhalb der App verwendet werden, sind Ressourcen. Dazu zählen Texte, Bilder, Layouts, Farbcodes, Themes usw. Diese werden in Unterordnern des Verzeichnis ``app/src/main/res`` gespeichert. Im Ordner ``drawable`` (bzw. ``drawable-XXX``) werden Bitmaps (jpg, png oder gif-Dateien) oder XML-Drawables gespeichert. Eine vollständige Liste der verfügbaren Ressourcen, finden sich in der [Android Developers Dokumentation](https://developer.android.com/guide/topics/resources/drawable-resource.html).

Im Ordner ``layout`` werden die Layouts für die App gespeichert, der ``menu``-Ordner enthält alle notwendigen Dateien für Menüs (unabhängig davon ob Overflow-, ActionBar-, oder NavDrawer-Menüs). Unter ``values`` werden Strings, Farbcodes oder Themes gespeichert. Zusätzlich kann eine App lokalisiert werden, indem an den ``values``-Ordner der zweistellige Länder-, bzw. Sprachcode angehangen wird. Möchten wir unsere App in deutsch und englisch anbieten, erstellen wir die englischen Texte im ``values``-Ordner. Die deutsche Übersetzung platzieren wir (in denselben Dateinamen) im Ordner ``values-de``. 

Zusätzlich können wir auch bestimmte Ressourcen nur für einen mindest SDK-Level verwenden. Hierfür erstellen wir einen Ornder mit beispielsweise ``layout-v21``. Sofern ein Layout aufgerufen wird, welches auch in diesem Ordner exisiert, wird automatisch das Layout für das entsprechende SDK-Level des aktuellen Geräts ausgewählt. 

