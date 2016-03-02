# The Untangled Web Framework

The Untangled web framework is a ClojureScript web framework that blends various web development libraries together
with a good bit of glue code to make single-page webapps a breeze. It is a full-stack environment, but you can
pick and choose the pieces you wish to use.

NOTE: THIS IS ALPHA SOFTWARE, BASED UPON ALPHA VERSIONS OF Om. THIS IS NOT READY FOR PRODUCTION.

We're developing this framework as part of our core software stack at NAVIS for production applications. 
We will not consider this framework stable until Om becomes stable.

The full stack framework includes (See [untangled-web](https://github.com/untangled-web) on Github):

- Untangled Client: A library that builds upon Om 1.0, but greatly simplifies overall application development. 
This results in the following core features:
    - Co-located UI queries
    - The elimination of controller logic
    - The elimination of event systems
    - Fully synchronous UI model
    - Pure functions for rendering
    - Internationalization
        - GNU gettext-style internationalization. Trivial to use.
        - Formatted strings
        - Plural support
        - Date, time, percent, and number formatting
    - Support VCR viewer: Users can submit problem reports that include a configurable number of replayable UI frames so
that support can diagnose issues by watching what the user saw. Including server timestamps for log correlation.
    - A dramatically simpler overall application
- Untangled Server: A component-based server, leveraging Ring
    - Pre-plumbed to support untangled clients with almost no work
    - Easy to customize configuration
    - Pluggable modules for adding databases, handlers, API routes, etc.
    - Modules can be made easily available to all server logic that processes client requests.
- Untangled Datomic: A plugin for Untangled Server that helps you with Datomic as a back-end
    - Extended schema support
    - Schema migration support
    - Injectable component for the server to connect any number of databases
- Untangled Spec: A behavior specification system
    - Write tests that read like specifications
    - Render the client tests as a specification outline in any number of browsers
    - Render server tests as a specification outline.
    - Human readable data output (pretty printed structure) and data diffs
    - Easy-to-use mocking
    - Async timeline simulation
- Untangled Lein i18n: A leiningen plugin for extracting/compiling translations
    - Lein task to extract gettext-style POT files
    - Use PoEdit to generate translations (.po files)
    - Lein task to turn po files into loadable cljs translation modules.
- Untangled Template 
    - A full stack sample application
- Untangled TodoMVC
    - An implementation of the standard Todo MVC application. 
    - Two versions: One client-only. One with full-stack persistence, optimistic updates, support VCR Viewer.
