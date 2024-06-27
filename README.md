# abricot

Mirabelle plugin to send events to Riemann (or Mirabelle)

## Installation

Download a [release](https://github.com/faxm0dem/abricot/releases) and copy the `.jar` to your filesystem.

## Usage

Add a new output to your mirabelle instance by modifying its config file:

```clojure
; mirabelle-config.edn
{:stream :outputs {:riemann-forward {:builder abricot.core/map->RiemannForward
                                     :config {:client {:host "remote-riemann" :port 5555 :auto-connect true}}
                                     :type :custom}}}
```

The `:params` map contains options passed to [`riemann.client/tcp-client`](https://github.com/riemann/riemann-clojure-client/blob/main/src/riemann/client.clj#L120).

Define a stream that will send to the new output. For instance:

```clojure
; mirabelle-streams.clj
(streams
 (stream
  {:name :mystream :default true}
  (over 5
    (output! :riemann-forward))))
```

Don't forget to compile your stream:

```
java -jar mirabelle.jar compile path/to/streams/raw path/to/streams/compiled
```

Run mirabelle with abricot:

```
java -cp "mirabelle.jar:abricot.jar" mirabelle.core
```

## License

Copyright © 2024

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

