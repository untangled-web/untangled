# The Untangled Web Framework

The Untangled web framework is a ClojureScript web framework that blends various web development libraries together
with a good bit of glue code to make single-page webapps a breeze. It is a full-stack environment, but you can
pick and choose the pieces you wish to use.

NOTE: THIS IS ALPHA SOFTWARE, BASED UPON ALPHA VERSIONS OF Om. THIS IS NOT READY FOR PRODUCTION.

MORE DOCUMENTATION COMING VERY SOON, including an Untangled Tutorial that rounds out what we started
with the Om Tutorial.

We're developing this framework as part of our core software stack at NAVIS for production applications. 
We will not consider this framework stable until Om becomes stable.

The full stack framework includes a number of libraries (See [untangled-web](https://github.com/untangled-web) on Github):

- Untangled Client: A library that builds upon Om 1.0, but greatly simplifies overall application development. 
This results in the following core features:
    - Co-located UI queries
    - The elimination of controller logic
    - The elimination of event systems
    - Fully synchronous UI model
    - One-at-a-time network semantics (no out of order network requests, even in the presence of
    optimistic updates)
    - Pure functions for rendering
    - Internationalization
        - GNU gettext-style internationalization. Trivial to use.
        - Formatted strings
        - Plural support
        - Date, time, percent, and number formatting
    - Support VCR viewer: Users can submit problem reports that include a configurable number of replayable UI frames so
that support can diagnose issues by watching what the user saw. Including server timestamps for log correlation.
    - A dramatically simpler overall application
- Untangled Server: A component-based server
    - Uses components for hot reload during development
    - Pre-plumbed to support untangled clients with almost no work
    - Easy to customize configuration
    - Pluggable components for adding databases, handlers, API routes, etc.
    - Components can be made easily available to all server logic that processes client requests.
- Untangled Datomic: A plugin component for Untangled Server for Datomic persistence
    - Extended schema support
    - Schema migration support
    - Component for the server to connect any number of databases
    - Databases are automatically injected into the processing pipeline
- Untangled Spec: A behavior specification system
    - Write tests that read like specifications
    - Render the client tests as a specification outline in any number of browsers
    - Run client tests via CI systems.
    - Run/Render server tests as a specification outline (using test-refresh).
    - Human readable data output (pretty printed structure) and data diffs
    - Easy-to-use mocking
    - Async timeline simulation
- Untangled Lein i18n: A leiningen plugin for extracting/compiling translations (IN PROGRESS: 75%)
    - Lein task to extract gettext-style POT files
    - Use standard gettext tools like PoEdit to generate translations (.po files)
    - Lein task to turn po files into loadable cljs translation modules.
- Untangled Lein Template (IN PROGRESS: 50%, not deployed to clojars)
    - A full stack sample application 
- Untangled TodoMVC (IN PROGRESS: 95%)
    - An implementation of the standard Todo MVC application. 
    - Two versions: One client-only. One with full-stack persistence, optimistic updates, support VCR Viewer.
    
## This Repository

This Repository is meant to house a simple demo project and the website files. It is in development, and the project
that exists is not very impressive (though it does demonstrate a full-stack with initial and lazy loading). A more
complete project is in the untangled-todomvc repository.

## Understanding/Using the Code in this Repository

### Configure the web server

The server-side of Untangled requires you have a local web server configuration. There
will always need to be a default file (already included) that specifies all 
parameters for the server components. However, the server will not start unless
you define (and create) a localized configuration file with overrides (which can contain just an
empty map).

Copy `resources/config/defaults.edn` to `/usr/local/etc/app.edn`.

You'll note that the web server port is the only parameter for this application.

### 

Please see the Untangled Tour video at:
