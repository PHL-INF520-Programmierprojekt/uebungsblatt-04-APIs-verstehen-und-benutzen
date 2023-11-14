# Exercise Sheet: Understanding and Usage of Given APIs
[Link to German Version](./README.md)

In this exercise sheet, you will learn to read and understand given APIs and use them to solve different tasks.

## Exercise: Restaurant Orders

### Problem Statement

In this exercise, you will create a program that simulates a restaurant.
The restaurant has employees and orders.
Each employee has a queue of orders to process and new orders can be placed to the least busy employee.
The restaurant needs exactly one employee as its owner.

### API

The API is located in the `de.phl.programmingproject.restaurant` package and contains the following classes and operations.

- `Restaurant`
    - `Restaurant(final Employee owner)` - Creates a new restaurant with the specified owner.
    - `void hireEmployee(final Employee employee)` - Hires a new employee.
    - `void placeOrder(final Order order)` - Places a new order for the least busy employee.
    - `void process()` - Processes all orders.
- `Employee`
    - `void assignOrder(final Order order)` - Assigns a new order to the employee.
    - `int currentOrdersCount()` - Returns the amount of orders assigned to the employee.
    - `Queue<Order> getOrders()` - Returns the list of orders assigned to the employee.
    - `void processOrders()` - Processes all orders. If the employee has no orders, nothing happens.
- `Order`
    - `Order(final String name)` - Creates a new order with the specified name.
    - `String getName()` - Returns the name of the order.
    - `void handle()` - Prints the order name to mark it as processed.

### Tasks

1. Implement the `main` operation of the class `RestaurantOrders` to manually create a restaurant with an owner and one employee. 
   Then, place ten orders and let them be processed. The `orderNames` list contains the names for all orders.
2. Implement the restaurant's `placeOrder` operation and the employee's `assignOrder` operation. Pay attention to defensive programming.
3. Implement the `processOrders` operation of the `Employee` class. An order is processes by calling its `handle` operation and removing it from the employee's collection.
4. Discuss which Java Collection fits most to store the orders and argue why. Does the same apply to the employees?


## Exercise: Library API

### Problem Statement

In this exercise, you will create a program that uses a provided Library API to manage books, visitors, and lending.
The Library API provides a set of operations for searching and retrieving information about books, visitors, and lending books.
Your task is to implement a program that uses this API to solve a few tasks.

### API

The API is contained in a class called `Library` and is located in the `de.phl.programmingproject.library` package. The API provides the following operations:

- `Library(final Collection<Book> books)` - Creates a new library with the given books.
- `Set<Book> searchAvailableBooks(final String searchTerm)` - Searches for books based on a given search term and returns a set of matching books.
  The search term can be either a part of a book title or an author's name.
  The search term must not be `null` or empty.
- `void lendBook(final String title, final int visitorId)` - Lends a book to a visitor.
- `void returnBook(final String title, final int visitorId)` - Returns a book that was previously lent to a visitor.
  The book must not be lent and the visitor hat to be registered.
- `Visitor getVisitor(final int visitorId)` - Retrieves information about a specific visitor, given their ID.
- `int registerVisitor(final String name)` - Registers a visitor and returns their ID. The name should not be `null` or empty and not registered yet.

The `Book` class represents a book in the library and should have the following constructor and operations:

- `Book(final String title, final String author)` - Creates a new instance of the `Book` class with the specified title and author.
- `String getTitle()` - Returns the title of the book.
- `String getAuthor()` - Returns the author of the book.

The `Visitor` class represents a visitor in the library and should have the following constructor and operations:

- `Visitor(final int id, final String name)` - Creates a new instance of the `Visitor` class with the specified ID and name.
- `int getId()` - Returns the ID of the visitor.
- `String getName()` - Returns the name of the visitor.
- `Set<Book> getLentBooks()` - Returns the set of books that the visitor has lent.

### Tasks

1. Implement the `main` operation of a class called `LibraryDay`. Create the Library `lib` and provide three books.
   Then, create the visitors Paula, and Simon by registering them at the library.
   Paula and Simon should lend one different book each.
   Then, Paula returns her lent book.
2. Implement the `returnBook` operation.
   Pay attention to defensive programming.
3. Implement the `searchAvailableBooks` operation.
   Pay attention to defensive programming.
4. Discuss which Java Collection fits most to store the books and argue why.



## Exercise: Candy Production Line

### Problem Statement

In this exercise, you will create a program that simulates a candy production line.
The production line has three classes: `Candy`, `SugarMix`, and `JuicyCore`.
A candy consists of a sugar mix and a juicy core, which is optional.
A sugar mix has multiple flavors, which are represented by strings.
A `CandyFactory` can produce candies.

### API

The API is located in the `de.phl.programmingproject.candyproduction` package and contains the following classes and operations.

- `Candy`
    - `Candy(final SugarMix sugarMix)` - Creates a new candy with the specified sugar mix.
    - `Candy(final SugarMix sugarMix, final JuicyCore juicyCore)` - Creates a new candy with the specified sugar mix and juicy core.
    - `SugarMix getSugarMix()` - Returns the sugar mix of the candy.
    - `JuicyCore getJuicyCore()` - Returns the juicy core of the candy. Throws a NoSuchElementException if the candy has no juicy core.
    - `boolean hasJuicyCore()` - Returns true if the candy has a juicy core, else false.
- `SugarMix`
    - `SugarMix(final Set<String> flavors)` - Creates a new sugar mix with the specified flavors.
    - `Set<String> getFlavors()` - Returns the flavors of the sugar mix.
- `JuicyCore`
    - `JuicyCore(final String flavor)` - Creates a new juicy core with the specified flavor.
    - `String getFlavor()` - Returns the flavor of the juicy core.
- `CandyFactory`
    - `void addSugarMixFlavors(final List<String> sugarMixFlavors)` - Adds multiple sugar mix flavors for candy ingredients.
    - `void addJuicyCoreFlavors(final List<String> juicyCoreFlavors)` - Adds multiple juicy core flavors for candy ingredients.
    - `Set<Candy> produceCandies(final int amount)` - produces `amount` candies. Each candy is unique in flavor composition.

### Tasks

1. Implement the `main` operation of the class `CandyProducer` to manually create a candy. The candy should consist of a strawberry and blueberry flavored sugar mix and contains a lemon flavored juicy core.
2. Implement the operation `printCandy(final Candy candy)` which prints a string representation of the given candy. Use a format string to build the string representation.
3. Implement the second constructor of the `Candy` class and the `getJuicyCore` operation. The second constructor should create a candy with a sugar mix and a juicy core. The `getJuicyCore` operation should return the juicy core of the candy.
4. Implement the `produceCandies` operation in the `CandyFactory` class. This operation should produce `amount` candies with unique flavor combinations.

## Exercise: Discussion

Discuss with your partner what a good API description should contain.
Try to remember which information you were lacking to in the APIs stated above.


**Next Sheet**: [_Writing own operations in given Java classes_](