redis.usecase
=============

This Repo is several implementation of redis use case.

Currently, Autocomplete feature is pushed.

Later PV, UV feature will be pushed to this repo

## Autocomplete using TRIE data structuer.

This implementation is using trie data structure.

Few years ago, Salvatore Sanfilippo introduced about Autocomplete with Redis.

Now i need a this feature. so i tried to start java implementation.
But, result is too slow.

I digging more and more. root cause of slow is that frequent call of zrange command.

If there are too many keyword for autocomplete or too long sentences then that need a lots of zrange call.
