# Übungsblatt
[Link to English version](./README_en.md)

In diesem Übungsblatt lernen Sie, gegebene APIs zu lesen und zu verstehen und sie zur Lösung verschiedener Aufgaben einzusetzen.

## Übung: Restaurantbestellungen

### Problemstellung

In dieser Übung erstellen Sie ein Programm, das ein Restaurant simuliert.
Das Restaurant hat Mitarbeitende und Bestellungen.
Jede&ast;r Mitarbeiter&ast;in hat eine Warteschlange von Bestellungen zu bearbeiten und neue Bestellungen können der am wenigsten beschäftigten Person zugewiesen werden.
Das Restaurant benötigt genau eine&ast;n Mitarbeiter&ast;in als Inhaber&ast;in.

### API

Die API befindet sich im Paket `de.phl.programmingproject.restaurant` und enthält die folgenden Klassen und Operationen.

- `Restaurant`
    - `Restaurant(final Employee owner)` - Erstellt ein neues Restaurant mit dem/der angegebenen Inhaber&ast;in.
    - `void hireEmployee(final Employee employee)` - Stellt eine&ast;n neue&ast;n Mitarbeiter&ast;in ein.
    - `void placeOrder(final Order order)` - Gibt eine neue Bestellung für die am wenigsten beschäftigte Mitarbeiter&ast;in auf.
    - `void process()` - Bearbeitet alle Bestellungen.
- `Employee`
    - `void assignOrder(final Order order)` - Weist einer&ast;m Mitarbeiter&ast;in eine neue Bestellung zu.
    - `int currentOrdersCount()` - Gibt die Anzahl der Mitarbeiter&ast;in zugewiesenen Bestellungen zurück.
    - `Queue<Order> getOrders()` - Gibt die Liste der Mitarbeiter&ast;in zugewiesenen Bestellungen zurück.
    - `void processOrders()` - Bearbeitet alle Bestellungen. Wenn die Mitarbeiter&ast;in keine Bestellungen hat, passiert nichts.
- `Order`
    - `Order(final String name)` - Erstellt eine neue Bestellung mit dem angegebenen Namen.
    - `String getName()` - Gibt den Namen der Bestellung zurück.
    - `void handle()` - Druckt den Bestellnamen aus, um ihn als bearbeitet zu kennzeichnen.

### Aufgaben

1. Implementieren Sie die `main`-Operation der Klasse `RestaurantOrders`, um manuell ein Restaurant mit einem Inhaber und einer&ast;m Mitarbeiter&ast;in zu erstellen.
   Dann geben Sie zehn Bestellungen auf und lassen Sie diese bearbeiten. Die Liste `orderNames` enthält die Namen für alle Bestellungen.
2. Implementieren Sie die Operation `placeOrder` des Restaurants und die Operation `assignOrder` der Mitarbeiter&ast;in. Achten Sie auf defensive Programmierung.
3. Implementieren Sie die Operation `processOrders` der Klasse `Employee`. Eine Bestellung wird bearbeitet, indem ihre Operation `handle` aufgerufen und sie aus der Sammlung der Bestellungen der Mitarbeiter&ast;in entfernt wird.
4. Diskutieren Sie, welche Java-`Collection` am besten geeignet ist, um die Bestellungen zu speichern und begründen Sie warum. Gilt dasselbe für die Mitarbeiter&ast;innen? Geben Sie Ihre Antworten in einer Markdown-Datei `restaurant_orders.md` im Hauptverzeichnis ab.


## Übung: Bibliotheks-API

### Problemstellung

In dieser Übung erstellen Sie ein Programm, das eine bereitgestellte Bibliotheks-API verwendet, um Bücher, Besucher&ast;innen und das Ausleihen von Büchern zu verwalten.
Die Bibliotheks-API bietet eine Reihe von Operationen zum Suchen und Abrufen von Informationen über Bücher, Besucher&ast;innen und das Ausleihen von Büchern.
Ihre Aufgabe ist es, ein Programm zu implementieren, das diese API verwendet, um einige Aufgaben zu lösen.

### API

Die API ist in einer Klasse namens `Library` enthalten und befindet sich im Paket `de.phl.programmingproject.library`. Die API bietet die folgenden Operationen:

- `Library(final Collection<Book> books)` - Erstellt eine neue Bibliothek mit den gegebenen Büchern.
- `Set<Book> searchAvailableBooks(final String searchTerm)` - Sucht nach Büchern basierend auf einem gegebenen Suchbegriff und gibt eine Menge von passenden Büchern zurück.
  Der Suchbegriff kann entweder ein Teil eines Buchtitels oder der Name eines Autors sein.
  Der Suchbegriff darf nicht `null` oder leer sein.
- `void lendBook(final String title, final int visitorId)` - Leiht ein Buch an eine&ast;n Besucher&ast;in aus.
- `void returnBook(final String title, final int visitorId)` - Gibt ein Buch zurück, das zuvor an eine&ast;n registrierte&ast;n Besucher&ast;in verliehen wurde.
    Das Buch muss an die&ast;n Besucher&ast;in verliehen worden sein und die/der Besucher&ast;in muss zuvor registriert worden sein.
- `Visitor getVisitor(final int visitorId)` - Ruft Informationen über eine&ast;n bestimmte&ast;n Besucher&ast;in ab, gegeben deren ID.
- `int registerVisitor(final String name)` - Registriert eine&ast;n Besucher&ast;in und gibt deren ID zurück. Der Name darf nicht `null` oder leer sein und noch nicht registriert sein.

Die Klasse `Book` repräsentiert ein Buch in der Bibliothek und sollte den folgenden Konstruktor und die folgenden Operationen haben:

- `Book(final String title, final String author)` - Erstellt eine neue Instanz der Klasse `Book` mit dem angegebenen Titel und Autor.
- `String getTitle()` - Gibt den Titel des Buches zurück.
- `String getAuthor()` - Gibt den Autor des Buches zurück.

Die Klasse `Visitor` repräsentiert eine&ast;n Besucher&ast;in in der Bibliothek und sollte den folgenden Konstruktor und die folgenden Operationen haben:

- `Visitor(final int id, final String name)` - Erstellt eine neue Instanz der Klasse `Visitor` mit der angegebenen ID und dem Namen.
- `int getId()` - Gibt die ID der&ast;des Besucher&ast;in zurück.
- `String getName()` - Gibt den Namen der&ast;des Besucher&ast;in zurück.
- `Set<Book> getLentBooks()` - Gibt die Menge der Bücher zurück, die die&ast;der Besucher&ast;in ausgeliehen hat.

### Aufgaben

1. Implementieren Sie die `main`-Operation einer Klasse namens `LibraryDay`. Erstellen Sie die Bibliothek `lib` und stellen Sie drei Bücher zur Verfügung.
   Dann erstellen Sie die Besucher&ast;innen Paula und Simon, indem Sie sie in der Bibliothek registrieren.
   Paula und Simon sollten jeweils ein unterschiedliches Buch ausleihen.
   Dann gibt Paula ihr ausgeliehenes Buch zurück.
2. Implementieren Sie die Operation `returnBook`.
   Achten Sie auf defensive Programmierung.
3. Implementieren Sie die Operation `searchAvailableBooks`.
   Achten Sie auf defensive Programmierung.
4. Diskutieren Sie, welche Java-`Collection` am besten geeignet ist, um die Bücher zu speichern und begründen Sie warum. Geben Sie Ihre Antworten in einer Markdown-Datei `library.md` im Stammverzeichnis ab.



## Übung: Süßwarenproduktionslinie

### Problemstellung

In dieser Übung erstellen Sie ein Programm, das eine Süßwarenproduktionslinie simuliert.
Die Produktionslinie hat drei Klassen: `Candy`, `SugarMix` und `JuicyCore`.
Ein Bonbon (`Candy`) besteht aus einer Zucker-Mischung (`SugarMix`) und einem optionalen saftigen Kern (`JuicyCore`).
Eine Zucker-Mischung hat mehrere Geschmacksrichtungen, die durch Strings repräsentiert werden.
Eine `CandyFactory` kann Bonbons herstellen.

### API

Die API befindet sich im Paket `de.phl.programmingproject.candyproduction` und enthält die folgenden Klassen und Operationen.

- `Candy`
  - `Candy(final SugarMix sugarMix)` - Erstellt ein neues Bonbon mit der angegebenen Zucker-Mischung.
  - `Candy(final SugarMix sugarMix, final JuicyCore juicyCore)` - Erstellt ein neues Bonbon mit der angegebenen Zucker-Mischung und saftigen Kern.
  - `SugarMix getSugarMix()` - Gibt die Zucker-Mischung des Bonbons zurück.
  - `JuicyCore getJuicyCore()` - Gibt den saftigen Kern des Bonbons zurück. Wirft eine NoSuchElementException, wenn das Bonbon keinen saftigen Kern hat.
  - `boolean hasJuicyCore()` - Gibt `true` zurück, wenn das Bonbon einen saftigen Kern hat, sonst `false`.
- `SugarMix`
  - `SugarMix(final Set<String> flavors)` - Erstellt eine neue Zucker-Mischung mit den angegebenen Geschmacksrichtungen.
  - `Set<String> getFlavors()` - Gibt die Geschmacksrichtungen der Zucker-Mischung zurück.
- `JuicyCore`
  - `JuicyCore(final String flavor)` - Erstellt einen neuen saftigen Kern
    saftigen Kern mit dem angegebenen Geschmack.
  - `String getFlavor()` - Gibt den Geschmack des saftigen Kerns zurück.
- `CandyFactory`
    - `void addSugarMixFlavors(final List<String> sugarMixFlavors)` - Fügt mehrere Geschmacksrichtungen für Zucker-Mischungen zu den Bonbon-Zutaten hinzu.
    - `void addJuicyCoreFlavors(final List<String> juicyCoreFlavors)` - Fügt mehrere Geschmacksrichtungen für saftige Kerne zu den Bonbon-Zutaten hinzu.
    - `Set<Candy> produceCandies(final int amount)` - Produziert `amount` Bonbons. Jedes Bonbon ist einzigartig in der Geschmackskomposition.

### Aufgaben

1. Implementieren Sie die `main`-Operation der Klasse `CandyProducer`, um manuell ein Bonbon zu erstellen. Das Bonbon sollte aus einer Erdbeer- und Heidelbeer-geschmackten Zucker-Mischung bestehen.
2. Implementieren Sie den zweiten Konstruktor der Klasse `Candy` und die Operation `getJuicyCore`. Der zweite Konstruktor sollte ein Bonbon mit einer Zucker-Mischung und einem saftigen Kern erstellen. Die Operation `getJuicyCore` sollte den saftigen Kern des Bonbons zurückgeben.
3. Erweitern Sie die `main`-Operation und erstellen Sie manuell ein Bonbon, das aus einer Zucker-Mischung mit "Strawberry"- und "Blueberry"-Geschmack besteht und einen saftigen Kern mit "Lemon"-Geschmack enthält.
4. Implementieren Sie die Operation `printCandy(final Candy candy)`, die eine String-Repräsentation des gegebenen Bonbons ausgibt. Verwenden Sie einen Formatstring (`String.format(...)`) um die String-Repräsentation zu erstellen.
5. Implementieren Sie die Operation `produceCandies` in der Klasse `CandyFactory`. Diese Operation sollte `amount` Bonbons mit einzigartigen Geschmackskombinationen produzieren (d.h., jedes Bonbon muss eine einzigartige Mischung von Zucker-Mischungen mit einem saftigen Kern haben).
    * Hinweis: Sie müssen die Zucker-Mischungen und saftigen Kerne zur Bonbonfabrik hinzufügen, bevor Sie `produceCandies(final int amount)` aufrufen!

## Übung: Diskussion

Diskutieren Sie mit Ihrer*m Partner*in, was eine gute API-Beschreibung enthalten sollte.
Versuchen Sie sich daran zu erinnern, welche Informationen Ihnen bei den oben genannten APIs gefehlt haben.

### Aufgaben
1. Geben Sie Ihre Antworten in einer Markdown-Datei `discussion.md` im Stammverzeichnis ab.
