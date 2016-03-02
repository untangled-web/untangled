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

1. Write some UI using Om `defui` with colocated queries
2. Build an initial state database to go with these queries
3. Make a client and mount it on the DOM

```
(ns myapp.ui
  (:require [om.next :as om :refer-macros [defui]]
            [untangled.client.mutations :as mut]
            [om.dom :as dom]))

(defui Root
  static om/IQuery
  (query [this] [:some-data])
  Object
  (render [this]
    (let [{:keys [some-data]} (om/props this)]
      (dom/div nil some-data))))
```

```
(ns myapp.core
  (:require [untangled.client.core :as uc]))

(defonce app (atom (uc/new-untangled-client
                     :initial-state {:some-data  "Hello world"})))
```

and mount the app on the DOM:

```
(ns myapp.main
  (:require [myapp.core :as uc]
            [myapp.ui :as ui]))
            
(reset! uc/app (uc/mount @uc/app ui/Root "app"))
```

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
2. (optional) Make a copy of defaults.edn into an external file for local config
3. Use make-untangled-server


