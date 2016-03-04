# Getting Started

Untangled leverages the concepts defined by [Om](http://www.github.com/omcljs/om). As such, there
are a few concepts that you should understand from that system. Untangled is intended to
fill in a number of gaps where Om does not have an opinion, and does make a number
of concrete decisions the dramatically reduce the overhead of getting a full stack webapp going.

You should watch the tour video to get an some overall exposure to the
pieces. In order to build a full-stack app there are a lot of pieces to
understand. 

Specifically, you must understand the following to get anywhere:

- Basic Clojure/ClojureScript
- How to set up a project
- The default Om client application graph database format
- Co-located queries on the UI, and how those relate to the database format
- How to set up a basic Untangled Client

Interestingly, you don't have to write much of the standard Om plumbing
(parsers, networking code, etc).

With these topics under your belt you'll be able to generate
a general application that appears on the screen and can interact with
the user.

From there, these additions:

- How to make an untangled server
- How to respond to queries in the server code
- How to handle mutations in the server code

will get you very quickly to a full-stack application.

## Setting up the Project

Our project template is under development. Here is the list of features
we want in any project:

- Server and client tests
- Devtools integration with Google Chrome (formats cljs data structures)
- Figwheel hot code push and hot CSS reload
- A server that can be edited, fixed, restarted with no delays

We recommend using IntelliJ with Cursive, and these instructions
stress those tools.

### The figwheel builds

There are three builds we recommend for development:

- dev: Client development build that includes debugging helpers/tools
- cards: A Devcards build, useful for playing with UI components
- test: A build of cljs tests that run in a browser

We also need to be able to do a production build that elides the extras
in the dev build.

The critical files for this are organized as follows:

```
resources
├── config
│   └── defaults.edn    ; Web server configuration defaults
└── public
    ├── cards.html      ; Devcards HTML file
    ├── css
    │   ├── app.css     ; Application CSS
    │   ├── edn.css     ; Spec rendering CSS 
    │   └── test.css
    ├── index.html      ; Main Application HTML
    └── test.html       ; Spec Running HTML
src
├── cards
│   └── app_cards
│       ├── cards_ui.cljs       ; Devcards entry point (must require all other card ns)
│       └── sample_card.cljs    ; Sample Devcard
├── client
│   └── app
│       ├── core.cljs            ; Client creation/initial loading code
│       ├── i18n                 ; generated i18n code
│       ├── main.cljs            ; Production entry point
│       ├── mutations.cljs       ; All client mutations to the app state
│       ├── support_viewer.cljs  ; A VCR Support viewer
│       └── ui.cljs              ; The UI
├── server
│   └── app
│       ├── api.clj              ; The queries and mutations on the server
│       ├── main.clj             ; Production web server entry point
│       └── system.clj           ; Generation of the web server itself
└── shared                       ; Location for cljc files
dev
├── client
│   └── cljs
│       └── user.cljs            ; Development mode client entry point
└── server
    └── user.clj                 ; Development mode entry for server 
script
└── figwheel.clj                 ; Script to start various figwheel builds via -Dbuild-id
specs
├── client
│   └── app
│       ├── all_tests.cljs       ; CI (command line) test runner entry point for browser tests
│       ├── sample_spec.cljs     ; A specification (client)
│       ├── suite.cljs           ; The live spec renderer (browser) for dev mode
│       └── tests_to_run.cljs    ; The common list of specs in a require (needed to make CI and live both work)
├── server
│   └── app
│       └── server_spec.clj      ; A sample server spec (run via `lein test-refresh`)
└── shared                       ; Location for shared spec cljc files
i18n
└── msgs                         ; Generated i18n template, and user-generated translations
    ├── es_MX.mo
    ├── es_MX.po
    └── messages.pot
```


## The Om Basics

You should read the om-tutorial sections on the
[application database](https://awkay.github.io/om-tutorial/#!/om_tutorial.C_App_Database),
[queries](https://awkay.github.io/om-tutorial/#!/om_tutorial.D_Queries), and
[UI queries](https://awkay.github.io/om-tutorial/#!/om_tutorial.E_UI_Queries_and_State).

## Setting up an Untangled Client

Once you have those three relatively simple topics under your belt, 
you're ready to build an untangled client!

Here are the steps:

1. Write some UI using Om `defui` with colocated queries (see `src/client/app/ui.cljs`)
2. Build an initial state database to go with these queries (see `src/client/app/core.cljs`)
3. Make a client and mount it on the DOM (see `src/client/app/main.cljs` and `core.cljs`)

That's it! No need to write a parser, a read function, a reconciler, networking code, etc. All 
of that is created for you behind the scenes. You're actually ready to 
immediately start working on an interactive application without needing
to write any of the glue code.


## Setting up an Untangled Server

We have ambitious goals on the server, too:

- Easily configurable
- Injectable components
- Easy to start/stop/reset (refreshing code)
- All you need to write are the specific handling of application
queries and mutations. 

Here are the steps:

1. Create a defaults.edn config file
2. Make a copy of defaults.edn into an external file for local config
3. Use make-untangled-server and include the external config filename

The minimal `defaults.edn` should go in `resources/config/`, and should
contain the port the webserver should bind to:

```
{ :port 3000 }
```

Creating the server involves making a server-side request parser and
adding it to a server.

### Server API (queries and mutations):

See `src/server/app/api.clj`

### Server Definition

See `src/server/app/system.clj`

### Server Entry Point

For REPL development mode, we support a `dev/server/user.clj` that can be used to start and reload the server
on demand.

For production, the main is simple and is in `src/server/app/main.clj`.

## Internationalization

You can get started really fast with the i18n support. Basically, just use
the `tr`, `trf`, and `trc` macros on raw strings. If you want to translate
something in a variable you must make sure that the raw string itself is
somewhere in a `tr` (so it will get extracted). If doing that, there is
`tr-unsafe` (named that because it accepts non-literal strings that 
are not guaranteed to be translated).

