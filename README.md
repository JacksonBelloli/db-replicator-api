# db-replicator-api

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Installing

1. create a file with the core database (/config/core-db.json). Or the software will use the default:
```json
{
   "dbtype": "mysql",
	"dbname": "replicator",
   "host": "localhost",
   "port": "3000",
	"user": "admin",
	"password": "admin"
}
```
2. `lein ring server-headless`

## Test

    lein midje

## License

Copyright © 2019 FIXME
