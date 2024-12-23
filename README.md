This is an android app that shows sports and events that are fetched from a mock API, this project uses the following technologies and patterns:


  - MVVM architecture;
  - Hilt for dependency injection;
  - No using a real database, but storing data in DataStore;
  - Jetpack compose for the views;
  - Ktor to make API requests;
  - Mockk for unit testing;
  - Kotlin DSL (KTS) syntax;

Implementation: 
  - The first time the app opens a request is made to fetch all sports and events, after that the sports and events are stored separately in the "database";
  - When the request is finished the first think the user will see is a list with all the sports, and a loading view for all of them (because now the app is loading the events from the database);
  - When the user favorites an event this one is stored separately to make it possible to update sports and events without losing the favorite information;
  - There is a button in the top bar to show/hide finished events;

TODO:
  - Add pull to refresh behaviour so the data can be updated;
  - Make the app multi mmodule;
  - Create more tests, unit and UI;
  - Make an implementation using Room for the database;
  - Icons for all the sports;
