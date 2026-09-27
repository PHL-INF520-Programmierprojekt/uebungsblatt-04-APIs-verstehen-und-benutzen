# Übungsblatt
[Link to English version](./README_en.md)

In diesem Übungsblatt lernen Sie, gegebene APIs zu lesen und zu verstehen und sie zur Lösung verschiedener Aufgaben einzusetzen.

## Übung 01: Restaurantbestellungen

### Problemstellung

In dieser Übung erstellen Sie ein Programm, das ein Restaurant simuliert.
Das Restaurant hat Mitarbeitende und Bestellungen.
Jede&ast;r Mitarbeiter&ast;in hat eine Warteschlange von Bestellungen zu bearbeiten und neue Bestellungen können der am wenigsten beschäftigten Person zugewiesen werden.
Das Restaurant benötigt genau eine&ast;n Mitarbeiter&ast;in als Inhaber&ast;in.

### API

Die API befindet sich im Paket [`de.phl.programmingproject.restaurant`](src/main/java/de/phl/programmingproject/restaurant/) und enthält die folgenden Klassen und Operationen.

- [`Restaurant`](src/main/java/de/phl/programmingproject/restaurant/Restaurant.java)
    - `Restaurant(final Employee owner)` - Erstellt ein neues Restaurant mit dem/der angegebenen Inhaber&ast;in.
    - `void hireEmployee(final Employee employee)` - Stellt eine&ast;n neue&ast;n Mitarbeiter&ast;in ein.
    - `void placeOrder(final Order order)` - Gibt eine neue Bestellung für die am wenigsten beschäftigte Mitarbeiter&ast;in auf.
    - `void process()` - Bearbeitet alle Bestellungen.
- [`Employee`](src/main/java/de/phl/programmingproject/restaurant/Employee.java)
    - `void assignOrder(final Order order)` - Weist einer&ast;m Mitarbeiter&ast;in eine neue Bestellung zu.
    - `int currentOrdersCount()` - Gibt die Anzahl der Mitarbeiter&ast;in zugewiesenen Bestellungen zurück.
    - `Queue<Order> getOrders()` - Gibt die Liste der Mitarbeiter&ast;in zugewiesenen Bestellungen zurück.
    - `void processOrders()` - Bearbeitet alle Bestellungen. Wenn die Mitarbeiter&ast;in keine Bestellungen hat, passiert nichts.
- [`Order`](src/main/java/de/phl/programmingproject/restaurant/Order.java)
    - `Order(final String name)` - Erstellt eine neue Bestellung mit dem angegebenen Namen.
    - `String getName()` - Gibt den Namen der Bestellung zurück.
    - `void handle()` - Druckt den Bestellnamen aus, um ihn als bearbeitet zu kennzeichnen.

### Aufgaben

1. Implementieren Sie die `main`-Operation der Klasse [`RestaurantOrders`](src/main/java/de/phl/programmingproject/restaurant/RestaurantOrders.java), um manuell ein Restaurant mit einem Inhaber und einer&ast;m Mitarbeiter&ast;in zu erstellen.
   Dann geben Sie zehn Bestellungen auf und lassen Sie diese bearbeiten. Die Liste `orderNames` enthält die Namen für alle Bestellungen.
2. Implementieren Sie die Operation `placeOrder` des Restaurants und die Operation `assignOrder` der Mitarbeiter&ast;in. Achten Sie auf defensive Programmierung.
3. Implementieren Sie die Operation `processOrders` der Klasse `Employee`. Eine Bestellung wird bearbeitet, indem ihre Operation `handle` aufgerufen und sie aus der Sammlung der Bestellungen der Mitarbeiter&ast;in entfernt wird.
4. Diskutieren Sie, welche Java-`Collection` am besten geeignet ist, um die Bestellungen zu speichern und begründen Sie warum. Gilt dasselbe für die Mitarbeiter&ast;innen? Geben Sie Ihre Antworten in einer Markdown-Datei `restaurant_orders.md` im Hauptverzeichnis ab.


## Übung 02: Bibliotheks-API

### Problemstellung

In dieser Übung erstellen Sie ein Programm, das eine bereitgestellte Bibliotheks-API verwendet, um Bücher, Besucher&ast;innen und das Ausleihen von Büchern zu verwalten.
Die Bibliotheks-API bietet eine Reihe von Operationen zum Suchen und Abrufen von Informationen über Bücher, Besucher&ast;innen und das Ausleihen von Büchern.
Ihre Aufgabe ist es, ein Programm zu implementieren, das diese API verwendet, um einige Aufgaben zu lösen.

### API

Die API ist in einer Klasse namens [`Library`](src/main/java/de/phl/programmingproject/library/Library.java) enthalten und befindet sich im Paket [`de.phl.programmingproject.library`](src/main/java/de/phl/programmingproject/library/). Die API bietet die folgenden Operationen:

- `Library(final Collection<Book> books)` - Erstellt eine neue Bibliothek mit den gegebenen Büchern.
- `Set<Book> searchAvailableBooks(final String searchTerm)` - Sucht nach Büchern basierend auf einem gegebenen Suchbegriff und gibt eine Menge von passenden Büchern zurück.
  Der Suchbegriff kann entweder ein Teil eines Buchtitels oder der Name eines Autors sein.
  Der Suchbegriff darf nicht `null` oder leer sein.
- `void lendBook(final String title, final int visitorId)` - Leiht ein Buch an eine&ast;n Besucher&ast;in aus.
- `void returnBook(final String title, final int visitorId)` - Gibt ein Buch zurück, das zuvor an eine&ast;n registrierte&ast;n Besucher&ast;in verliehen wurde.
    Das Buch muss an die&ast;n Besucher&ast;in verliehen worden sein und die/der Besucher&ast;in muss zuvor registriert worden sein.
- `Visitor getVisitor(final int visitorId)` - Ruft Informationen über eine&ast;n bestimmte&ast;n Besucher&ast;in ab, gegeben deren ID.
- `int registerVisitor(final String name)` - Registriert eine&ast;n Besucher&ast;in und gibt deren ID zurück. Der Name darf nicht `null` oder leer sein und noch nicht registriert sein.

Die Klasse [`Book`](src/main/java/de/phl/programmingproject/library/Book.java) repräsentiert ein Buch in der Bibliothek und sollte den folgenden Konstruktor und die folgenden Operationen haben:

- `Book(final String title, final String author)` - Erstellt eine neue Instanz der Klasse `Book` mit dem angegebenen Titel und Autor.
- `String getTitle()` - Gibt den Titel des Buches zurück.
- `String getAuthor()` - Gibt den Autor des Buches zurück.

Die Klasse [`Visitor`](src/main/java/de/phl/programmingproject/library/Visitor.java) repräsentiert eine&ast;n Besucher&ast;in in der Bibliothek und sollte den folgenden Konstruktor und die folgenden Operationen haben:

- `Visitor(final int id, final String name)` - Erstellt eine neue Instanz der Klasse `Visitor` mit der angegebenen ID und dem Namen.
- `int getId()` - Gibt die ID der&ast;des Besucher&ast;in zurück.
- `String getName()` - Gibt den Namen der&ast;des Besucher&ast;in zurück.
- `Set<Book> getLentBooks()` - Gibt die Menge der Bücher zurück, die die&ast;der Besucher&ast;in ausgeliehen hat.

### Aufgaben

1. Implementieren Sie die `main`-Operation einer Klasse namens [`LibraryDay`](src/main/java/de/phl/programmingproject/library/LibraryDay.java). Erstellen Sie die Bibliothek `lib` und stellen Sie drei Bücher zur Verfügung.
   Dann erstellen Sie die Besucher&ast;innen Paula und Simon, indem Sie sie in der Bibliothek registrieren.
   Paula und Simon sollten jeweils ein unterschiedliches Buch ausleihen.
   Dann gibt Paula ihr ausgeliehenes Buch zurück.
2. Implementieren Sie die Operation `returnBook`.
   Achten Sie auf defensive Programmierung.
3. Implementieren Sie die Operation `searchAvailableBooks`.
   Achten Sie auf defensive Programmierung.
4. Diskutieren Sie, welche Java-`Collection` am besten geeignet ist, um die Bücher zu speichern und begründen Sie warum. Geben Sie Ihre Antworten in einer Markdown-Datei `library.md` im Stammverzeichnis ab.



## Übung 03: Süßwarenproduktionslinie

### Problemstellung

In dieser Übung erstellen Sie ein Programm, das eine Süßwarenproduktionslinie simuliert.
Die Produktionslinie hat drei Klassen: `Candy`, `SugarMix` und `JuicyCore`.
Ein Bonbon (`Candy`) besteht aus einer Zucker-Mischung (`SugarMix`) und einem optionalen saftigen Kern (`JuicyCore`).
Eine Zucker-Mischung hat mehrere Geschmacksrichtungen, die durch Strings repräsentiert werden.
Eine `CandyFactory` kann Bonbons herstellen.

### API

Die API befindet sich im Paket [`de.phl.programmingproject.candyproduction`](src/main/java/de/phl/programmingproject/candyproduction/) und enthält die folgenden Klassen und Operationen.

- [`Candy`](src/main/java/de/phl/programmingproject/candyproduction/Candy.java)
  - `Candy(final SugarMix sugarMix)` - Erstellt ein neues Bonbon mit der angegebenen Zucker-Mischung.
  - `Candy(final SugarMix sugarMix, final JuicyCore juicyCore)` - Erstellt ein neues Bonbon mit der angegebenen Zucker-Mischung und saftigen Kern.
  - `SugarMix getSugarMix()` - Gibt die Zucker-Mischung des Bonbons zurück.
  - `JuicyCore getJuicyCore()` - Gibt den saftigen Kern des Bonbons zurück. Wirft eine NoSuchElementException, wenn das Bonbon keinen saftigen Kern hat.
  - `boolean hasJuicyCore()` - Gibt `true` zurück, wenn das Bonbon einen saftigen Kern hat, sonst `false`.
- [`SugarMix`](src/main/java/de/phl/programmingproject/candyproduction/SugarMix.java)
  - `SugarMix(final Set<String> flavors)` - Erstellt eine neue Zucker-Mischung mit den angegebenen Geschmacksrichtungen.
  - `Set<String> getFlavors()` - Gibt die Geschmacksrichtungen der Zucker-Mischung zurück.
- [`JuicyCore`](src/main/java/de/phl/programmingproject/candyproduction/JuicyCore.java)
  - `JuicyCore(final String flavor)` - Erstellt einen neuen saftigen Kern
    saftigen Kern mit dem angegebenen Geschmack.
  - `String getFlavor()` - Gibt den Geschmack des saftigen Kerns zurück.
- [`CandyFactory`](src/main/java/de/phl/programmingproject/candyproduction/CandyFactory.java)
    - `void addSugarMixFlavors(final List<String> sugarMixFlavors)` - Fügt mehrere Geschmacksrichtungen für Zucker-Mischungen zu den Bonbon-Zutaten hinzu.
    - `void addJuicyCoreFlavors(final List<String> juicyCoreFlavors)` - Fügt mehrere Geschmacksrichtungen für saftige Kerne zu den Bonbon-Zutaten hinzu.
    - `List<Candy> produceCandies(final int amount)` - Produziert `amount` einzelne Bonbons nach dem unten beschriebenen festen Rezept.

### Aufgaben

1. Implementieren Sie die `main`-Operation der Klasse [`CandyProducer`](src/main/java/de/phl/programmingproject/candyproduction/CandyProducer.java), um manuell ein Bonbon zu erstellen. Das Bonbon sollte aus einer Strawberry (Erdbeere) und Blueberry (Heidelbeere) Zucker-Mischung bestehen.
2. Implementieren Sie den zweiten Konstruktor der Klasse `Candy` und die Operation `getJuicyCore`. Der zweite Konstruktor sollte ein Bonbon mit einer Zucker-Mischung und einem saftigen Kern erstellen. Die Operation `getJuicyCore` sollte den saftigen Kern des Bonbons zurückgeben.
3. Erweitern Sie die `main`-Operation und erstellen Sie manuell ein Bonbon, das aus einer Zucker-Mischung mit "Strawberry"- und "Blueberry"-Geschmack besteht und einen saftigen Kern mit "Lemon"-Geschmack enthält.
4. Implementieren Sie die Operation `printCandy(final Candy candy)`, die eine String-Repräsentation des gegebenen Bonbons ausgibt. Verwenden Sie einen Formatstring (`String.format(...)`) um die String-Repräsentation zu erstellen.
5. Implementieren Sie `produceCandies` in `CandyFactory` als **einfache Serienproduktion**:
   - Fügen Sie vor dem Aufruf mindestens zwei Zuckeraromen und ein Kernaroma hinzu.
   - Das feste Rezept verwendet die **ersten beiden** Zuckeraromen und das **erste** Kernaroma. Weitere Aromen werden für diese Aufgabe nicht verwendet.
   - Erzeugen Sie mit einer Schleife genau `amount` neue Bonbon-Objekte nach diesem Rezept und geben Sie diese als `List<Candy>` zurück. Alle dürfen denselben Geschmack haben; dasselbe Bonbon-Objekt darf jedoch nicht mehrfach in die Liste eingetragen werden.
   - Für `amount < 1` werfen Sie eine `IllegalArgumentException`; fehlen Zutaten, werfen Sie eine `IllegalStateException`. Prüfen Sie zuerst `amount`.
   - Beispiel: Zuckeraromen `[Erdbeere, Blaubeere, Vanille]`, Kernaromen `[Zitrone, Kirsche]` und `amount = 3` ergeben drei einzelne Erdbeer-Blaubeer-Bonbons mit Zitronenkern.

**Zulässige Begriffe für Aufgaben 1 und 3:** Erdbeere/Strawberry, Heidelbeere/Blaubeere/Blueberry und Zitrone/Lemon. Groß- und Kleinschreibung spielen keine Rolle; deutsche und englische Begriffe dürfen gemischt werden. Beide Beerenaromen und der Zitronenkern aus Aufgabe 3 müssen zum selben Bonbon gehören. Sie müssen kein Übersetzungsprogramm schreiben.

**Freiwillige Vertiefung:** Überlegen Sie anschließend, wie eine zusätzliche Methode unterschiedliche Geschmackskombinationen erzeugen könnte. Die Pflichtmethode `produceCandies` und ihre Tests bleiben dabei unverändert.

## Übung 04: Diskussion

Diskutieren Sie mit Ihrer\*m Partner\*in, was eine gute API-Beschreibung enthalten sollte.
Versuchen Sie sich daran zu erinnern, welche Informationen Ihnen bei den oben genannten APIs gefehlt haben.

### Aufgaben
1. Geben Sie Ihre Antworten in einer Markdown-Datei `discussion.md` im Hauptverzeichnis ab.
