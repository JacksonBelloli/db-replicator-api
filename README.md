# db-replicator-api

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Installing

1. create a file with the core database (/config/core-db.json) The default:
```json
{
   "dbtype": "postgresql",
   "dbname": "mypgdatabase",
   "user": "myuser",
   "password": "secret"
}
```
2. `lein ring server-headless`

## Test

    lein midje

## License

Copyright © 2019 FIXME
