# project-4

* Group Members: Sidhant Puntambekar, Brian Noble, Isaac Pyle

Simulation of daily tasks at your Friendly Neighborhood Music Store

Java JDK Version: 13.0.10

Assume that random products are clones of the original products, with modified sale price. Only randomized numerical values in the updated item.

Compile and Run using:

1. `cd project-4/` or navigate to project root directory
2. `javac -d bin src/*.java`
3. `cd bin/`
4. `java main_class`

Our examples of the 6 OO Principles can be found here:
1. Inheritance: See the Music.java file, line 18.
2. Polymorphism: See the Accessories.java file, line 17
3. Abstraction: See the Accessories.java file, line 18
4. Encapsulation: See the Item.java file, line 24
5. Cohesion: See the Person.java file, line 10
6. Identity: See the Store.java file, line 4

Our examples of Design Patterns:
1. Decorator: See the Decorator.java and Stringed.java files
2. Strategy: See the TuneBehavior.java, ElectricTune.java, ManualTune.java, and HaphazardTune.java files
3. Observer: See the Observer.java, Subject.java, Tracker.java, Logger.java, and Clerk.java files


Add other assumptions here if necessary.
* We assume that the stores do not need to run at an overlapping time for the days that run regularly. This is represented in the logger, which sees the north store first and the south store second on each day. The day that the user interacts with the store has both days running simultaneously to allow the user to switch stores.
* We assume that JUnit version 4.13 is alright to use, as that is what we used for our testing. 
* We assume that you have JUnit installed, but if you do not simply comment or delete the StoreTest.java and ClerkTest.java files.
* We assume that the user can walk into the store randomly and they do not need to be prompted to select the day they would like to walk in.

